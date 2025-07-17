/*
Copyright (c) 2025 Stephen Gold

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
#include "Jolt/Physics/SoftBody/SoftBodyContactListener.h"

#include "auto/com_github_stephengold_joltjni_CustomSoftBodyContactListener.h"
#include "glue/glue.h"

using namespace JPH;

class CustomSoftBodyContactListener : SoftBodyContactListener {
    JavaVM *mpVM;
    jmethodID mAddedMethodId;
    jmethodID mValidateMethodId;
    jobject mJavaObject;

public:
    CustomSoftBodyContactListener(JNIEnv *pEnv, jobject javaObject) {
        pEnv->GetJavaVM(&mpVM);

        mJavaObject = pEnv->NewGlobalRef(javaObject);
        EXCEPTION_CHECK(pEnv)

        const jclass clss = pEnv->FindClass(
                "com/github/stephengold/joltjni/CustomSoftBodyContactListener");
        EXCEPTION_CHECK(pEnv)

        mAddedMethodId = pEnv->GetMethodID(clss, "onSoftBodyContactAdded", "(JJ)V");
        EXCEPTION_CHECK(pEnv)

        mValidateMethodId = pEnv->GetMethodID(clss, "onSoftBodyContactValidate", "(JJJ)I");
        EXCEPTION_CHECK(pEnv)
    }

    void OnSoftBodyContactAdded(const Body& inSoftBody, const SoftBodyManifold& inManifold) override {
        JNIEnv *pAttachEnv;
        jint retCode = ATTACH_CURRENT_THREAD(mpVM, &pAttachEnv);
        JPH_ASSERT(retCode == JNI_OK);

        const jlong bodyVa = reinterpret_cast<jlong> (&inSoftBody);
        const jlong manifoldVa = reinterpret_cast<jlong> (&inManifold);
        pAttachEnv->CallVoidMethod(mJavaObject, mAddedMethodId, bodyVa, manifoldVa);
        EXCEPTION_CHECK(pAttachEnv)
        mpVM->DetachCurrentThread();
    }

    SoftBodyValidateResult OnSoftBodyContactValidate(const Body& inSoftBody,
                const Body& inOtherBody, SoftBodyContactSettings& ioSettings) override {
        JNIEnv *pAttachEnv;
        jint retCode = ATTACH_CURRENT_THREAD(mpVM, &pAttachEnv);
        JPH_ASSERT(retCode == JNI_OK);

        const jlong softBodyVa = reinterpret_cast<jlong> (&inSoftBody);
        const jlong otherBodyVa = reinterpret_cast<jlong> (&inOtherBody);
        const jlong settingsVa = reinterpret_cast<jlong> (&ioSettings);
        const jint jintResult = pAttachEnv->CallIntMethod(mJavaObject,
                mValidateMethodId, softBodyVa, otherBodyVa, settingsVa);
        EXCEPTION_CHECK(pAttachEnv)
        mpVM->DetachCurrentThread();
        return (SoftBodyValidateResult) jintResult;
    }

    ~CustomSoftBodyContactListener() {
        JNIEnv *pAttachEnv;
        jint retCode = ATTACH_CURRENT_THREAD(mpVM, &pAttachEnv);
        JPH_ASSERT(retCode == JNI_OK);

        pAttachEnv->DeleteGlobalRef(mJavaObject);
        EXCEPTION_CHECK(pAttachEnv)
        mpVM->DetachCurrentThread();
    }
};

/*
 * Class:     com_github_stephengold_joltjni_CustomSoftBodyContactListener
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CustomSoftBodyContactListener_createDefault
  (JNIEnv *pEnv, jobject javaObject) {
    CustomSoftBodyContactListener * const pResult
            = new CustomSoftBodyContactListener(pEnv, javaObject);
    TRACE_NEW("CustomSoftBodyContactListener", pResult)
    return reinterpret_cast<jlong> (pResult);
}