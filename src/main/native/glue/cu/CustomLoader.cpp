/*
Copyright (c) 2026 Stephen Gold

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

/*
 * Author: Stephen Gold
 */
#include "Jolt/Jolt.h"
#include "auto/com_github_stephengold_joltjni_CustomLoader.h"
#include "glue/glue.h"
#include "glue/Loader.h"

using namespace JPH;

class CustomLoader : Loader {
    JavaVM *mpVM;
    jclass mByteBufferClass, mStringClass;
    jmethodID mLoadShaderMethodId;
    jobject mJavaObject;

public:
    CustomLoader(JNIEnv *pEnv, jobject javaObject) {
        pEnv->GetJavaVM(&mpVM);

        mJavaObject = pEnv->NewGlobalRef(javaObject);
        JPH_ASSERT(mJavaObject);

        mByteBufferClass = pEnv->FindClass("java/nio/ByteBuffer");
        JPH_ASSERT(mByteBufferClass);
        EXCEPTION_CHECK(pEnv)

        mStringClass = pEnv->FindClass("java/lang/String");
        JPH_ASSERT(mStringClass);
        EXCEPTION_CHECK(pEnv)

        const jclass clss = pEnv->FindClass(
                "com/github/stephengold/joltjni/CustomLoader");
        JPH_ASSERT(clss);
        EXCEPTION_CHECK(pEnv)

        mLoadShaderMethodId = pEnv->GetMethodID(clss, "loadShader",
                "(Ljava/lang/String;)Ljava/nio/ByteBuffer;");
        JPH_ASSERT(mLoadShaderMethodId);
        EXCEPTION_CHECK(pEnv)
    }

    bool loadShader(const char *inName, Array<uint8> &outData, String &outError) const override {
        JNIEnv *pAttachEnv;
        jint retCode = ATTACH_CURRENT_THREAD(mpVM, &pAttachEnv);
        JPH_ASSERT(retCode == JNI_OK);

        const jstring name = pAttachEnv->NewStringUTF(inName);
        JPH_ASSERT(name);
        EXCEPTION_CHECK(pAttachEnv)

        const jobject data = pAttachEnv->CallObjectMethod(
                mJavaObject, mLoadShaderMethodId, name);
        EXCEPTION_CHECK(pAttachEnv)

        bool success = (data != 0);
        outData.clear();
        if (success) {
            jbyte * bytes = (jbyte *) pAttachEnv->GetDirectBufferAddress(data);
            JPH_ASSERT(bytes);
            jlong numBytes = pAttachEnv->GetDirectBufferCapacity(data);
            JPH_ASSERT(numBytes >= 0);
            for (int i = 0; i < numBytes; ++i) {
                outData.push_back(bytes[i]);
            }
        }
        outError.clear();

        mpVM->DetachCurrentThread();
        return success;
    }

    ~CustomLoader() {
        JNIEnv *pAttachEnv;
        jint retCode = ATTACH_CURRENT_THREAD(mpVM, &pAttachEnv);
        JPH_ASSERT(retCode == JNI_OK);

        pAttachEnv->DeleteGlobalRef(mJavaObject);
        EXCEPTION_CHECK(pAttachEnv)
        mpVM->DetachCurrentThread();
    }
};

/*
 * Class:     com_github_stephengold_joltjni_CustomLoader
 * Method:    create
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CustomLoader_create
  (JNIEnv *pEnv, jobject javaObject) {
    CustomLoader * const pResult = new CustomLoader(pEnv, javaObject);
    TRACE_NEW("CustomLoader", pResult)
    return reinterpret_cast<jlong> (pResult);
}