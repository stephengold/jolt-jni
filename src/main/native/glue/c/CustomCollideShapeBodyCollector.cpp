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
#include "Jolt/Jolt.h"
#include "Jolt/Physics/Collision/BroadPhase/BroadPhaseQuery.h"
#include "auto/com_github_stephengold_joltjni_CustomCollideShapeBodyCollector.h"
#include "glue/glue.h"

using namespace JPH;

class CustomCollideShapeBodyCollector : CollideShapeBodyCollector {
    JavaVM *mpVM;
    jmethodID mAddMethodId;
    jobject mJavaObject;

public:
    CustomCollideShapeBodyCollector(JNIEnv *pEnv, jobject javaObject) {
        pEnv->GetJavaVM(&mpVM);

        mJavaObject = pEnv->NewGlobalRef(javaObject);
        JPH_ASSERT(!pEnv->ExceptionCheck());

        const jclass clss = pEnv->FindClass(
                "com/github/stephengold/joltjni/CustomCollideShapeBodyCollector");
        JPH_ASSERT(!pEnv->ExceptionCheck());

        mAddMethodId = pEnv->GetMethodID(clss, "addHit", "(J)V");
        JPH_ASSERT(!pEnv->ExceptionCheck());
    }

    void AddHit(const BodyID &inResult) {
        JNIEnv *pAttachEnv;
        jint retCode = mpVM->AttachCurrentThread((void **)&pAttachEnv, NULL);
        JPH_ASSERT(retCode == JNI_OK);

        const jlong resultVa = reinterpret_cast<jlong> (&inResult);
        pAttachEnv->CallVoidMethod(mJavaObject, mAddMethodId, resultVa);
        JPH_ASSERT(!pAttachEnv->ExceptionCheck());
        mpVM->DetachCurrentThread();
    }

    ~CustomCollideShapeBodyCollector() {
        JNIEnv *pAttachEnv;
        jint retCode = mpVM->AttachCurrentThread((void **)&pAttachEnv, NULL);
        JPH_ASSERT(retCode == JNI_OK);

        pAttachEnv->DeleteGlobalRef(mJavaObject);
        JPH_ASSERT(!pAttachEnv->ExceptionCheck());
        mpVM->DetachCurrentThread();
    }
};

/*
 * Class:     com_github_stephengold_joltjni_CustomCollideShapeBodyCollector
 * Method:    createCustomCollector
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CustomCollideShapeBodyCollector_createCustomCollector
  (JNIEnv *pEnv, jobject javaObject) {
    CustomCollideShapeBodyCollector * const pResult
            = new CustomCollideShapeBodyCollector(pEnv, javaObject);
    TRACE_NEW("CustomCollideShapeBodyCollector", pResult)
    return reinterpret_cast<jlong> (pResult);
}