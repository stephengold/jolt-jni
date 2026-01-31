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
#include "Jolt/Compute/ComputeSystem.h"
#include "auto/com_github_stephengold_joltjni_CustomShaderLoader.h"
#include "glue/glue.h"

using namespace JPH;

class CustomShaderLoader : ComputeSystem::ShaderLoader {
    JavaVM *mpVM;
    jclass mByteBufferClass, mStringClass;
    jmethodID mStepMethodId;
    jobject mJavaObject;

public:
    CustomShaderLoader(JNIEnv *pEnv, jobject javaObject) {
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
                "com/github/stephengold/joltjni/CustomShaderLoader");
        JPH_ASSERT(clss);
        EXCEPTION_CHECK(pEnv)

        mStepMethodId = pEnv->GetMethodID(clss, "onLoadRequest",
                "(Ljava/lang/String;[Ljava/nio/ByteBuffer;[Ljava/lang/String;)Z"
                );
        JPH_ASSERT(mStepMethodId);
        EXCEPTION_CHECK(pEnv)
    }

    bool operator()(
            const char *inName, Array<uint8> &outData, String &outError) {
        JNIEnv *pAttachEnv;
        jint retCode = ATTACH_CURRENT_THREAD(mpVM, &pAttachEnv);
        JPH_ASSERT(retCode == JNI_OK);

        const jstring name = pAttachEnv->NewStringUTF(inName);
        JPH_ASSERT(name);
        EXCEPTION_CHECK(pAttachEnv)

        jobjectArray data
                = pAttachEnv->NewObjectArray(1, mByteBufferClass, NULL);
        JPH_ASSERT(data);
        EXCEPTION_CHECK(pAttachEnv)

        jobjectArray message
                 = pAttachEnv->NewObjectArray(1, mStringClass, NULL);
        JPH_ASSERT(message);
        EXCEPTION_CHECK(pAttachEnv)

        const jboolean success = pAttachEnv->CallBooleanMethod(
                mJavaObject, mStepMethodId, name, data, message);
        EXCEPTION_CHECK(pAttachEnv)

        jobject data0 = pAttachEnv->GetObjectArrayElement(data, 0);
        JPH_ASSERT(data0);
        EXCEPTION_CHECK(pAttachEnv)
        jbyte * bytes = (jbyte *) pAttachEnv->GetDirectBufferAddress(data0);
        JPH_ASSERT(bytes);
        jlong numBytes = pAttachEnv->GetDirectBufferCapacity(data0);
        JPH_ASSERT(numBytes >= 0);
        outData.clear();
        for (int i = 0; i < numBytes; ++i) {
            outData.push_back(bytes[i]);
        }

        jstring message0
                = (jstring) pAttachEnv->GetObjectArrayElement(message, 0);
        JPH_ASSERT(message0);
        EXCEPTION_CHECK(pAttachEnv)
        jboolean isCopy;
        const jchar * chars = pAttachEnv->GetStringChars(message0, &isCopy);
        JPH_ASSERT(chars);
        jsize numChars = pAttachEnv->GetStringLength(message0);
        outError.clear();
        for (int i = 0; i < numChars; ++i) {
             outError.push_back(chars[i]);
        }

        mpVM->DetachCurrentThread();
        return success;
    }

    ~CustomShaderLoader() {
        JNIEnv *pAttachEnv;
        jint retCode = ATTACH_CURRENT_THREAD(mpVM, &pAttachEnv);
        JPH_ASSERT(retCode == JNI_OK);

        pAttachEnv->DeleteGlobalRef(mJavaObject);
        EXCEPTION_CHECK(pAttachEnv)
        mpVM->DetachCurrentThread();
    }
};

/*
 * Class:     com_github_stephengold_joltjni_CustomShaderLoader
 * Method:    create
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CustomShaderLoader_create
  (JNIEnv *pEnv, jobject javaObject) {
    CustomShaderLoader * const pResult
            = new CustomShaderLoader(pEnv, javaObject);
    TRACE_NEW("CustomShaderLoader", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_CustomShaderLoader
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CustomShaderLoader_free
  BODYOF_FREE(CustomShaderLoader)