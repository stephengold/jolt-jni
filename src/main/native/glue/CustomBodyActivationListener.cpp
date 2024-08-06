/*
Copyright (c) 2024 Stephen Gold

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
#include <Jolt/Jolt.h>
#include <Jolt/Physics/Body/BodyActivationListener.h>
#include "auto/com_github_stephengold_joltjni_CustomBodyActivationListener.h"
#include "glue/glue.h"

using namespace JPH;

class CustomBodyActivationListener : BodyActivationListener {
    JavaVM *mpVM;
    jmethodID mActivatedMethodId;
    jmethodID mDeactivatedMethodId;
    jobject mJavaObject;

public:
    CustomBodyActivationListener(JNIEnv *pEnv, jobject javaObject) {
        pEnv->GetJavaVM(&mpVM);

        mJavaObject = pEnv->NewGlobalRef(javaObject);
        JPH_ASSERT(!pEnv->ExceptionCheck());

        const jclass clss = pEnv->FindClass(
                "com/github/stephengold/joltjni/CustomBodyActivationListener");
        JPH_ASSERT(!pEnv->ExceptionCheck());

        mActivatedMethodId = pEnv->GetMethodID(clss, "onBodyActivated", "(JJ)V");
        JPH_ASSERT(!pEnv->ExceptionCheck());

        mDeactivatedMethodId = pEnv->GetMethodID(clss, "onBodyDeactivated", "(JJ)V");
        JPH_ASSERT(!pEnv->ExceptionCheck());
    }

    void OnBodyActivated(const BodyID &inBodyID, uint64 inBodyUserData) {
        JNIEnv *pAttachEnv;
        jint retCode = mpVM->AttachCurrentThread((void **)&pAttachEnv, NULL);
        JPH_ASSERT(retCode == JNI_OK);

        const jlong idVa = reinterpret_cast<jlong> (&inBodyID);
        const jlong userData = inBodyUserData;
        pAttachEnv->CallVoidMethod(mJavaObject, mActivatedMethodId, idVa, userData);
        JPH_ASSERT(!pAttachEnv->ExceptionCheck());
        mpVM->DetachCurrentThread();
    }

    void OnBodyDeactivated(const BodyID &inBodyID, uint64 inBodyUserData) {
        JNIEnv *pAttachEnv;
        jint retCode = mpVM->AttachCurrentThread((void **)&pAttachEnv, NULL);
        JPH_ASSERT(retCode == JNI_OK);

        const jlong idVa = reinterpret_cast<jlong> (&inBodyID);
        const jlong userData = inBodyUserData;
        pAttachEnv->CallVoidMethod(mJavaObject, mDeactivatedMethodId, idVa, userData);
        JPH_ASSERT(!pAttachEnv->ExceptionCheck());
        mpVM->DetachCurrentThread();
    }

    ~CustomBodyActivationListener() {
        JNIEnv *pAttachEnv;
        jint retCode = mpVM->AttachCurrentThread((void **)&pAttachEnv, NULL);
        JPH_ASSERT(retCode == JNI_OK);

        pAttachEnv->DeleteGlobalRef(mJavaObject);
        JPH_ASSERT(!pAttachEnv->ExceptionCheck());
        mpVM->DetachCurrentThread();
    }
};

/*
 * Class:     com_github_stephengold_joltjni_CustomBodyActivationListener
 * Method:    createCustomBodyActivationListener
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CustomBodyActivationListener_createCustomBodyActivationListener
  (JNIEnv *pEnv, jobject javaObject) {
    CustomBodyActivationListener * const pResult
            = new CustomBodyActivationListener(pEnv, javaObject);
    TRACE_NEW("CustomBodyActivationListener", pResult)
    return reinterpret_cast<jlong> (pResult);
}
