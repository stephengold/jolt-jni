/*
Copyright (c) 2024-2025 Stephen Gold

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
#include "Jolt/Physics/Collision/Shape/Shape.h"
#include "auto/com_github_stephengold_joltjni_CustomCastRayCollector.h"
#include "glue/glue.h"

using namespace JPH;

class CustomCastRayCollector : CastRayCollector {
    JavaVM *mpVM;
    jmethodID mAddMethodId;
    jobject mJavaObject;

public:
    CustomCastRayCollector(JNIEnv *pEnv, jobject javaObject) {
        pEnv->GetJavaVM(&mpVM);

        mJavaObject = pEnv->NewGlobalRef(javaObject);
        EXCEPTION_CHECK(pEnv)

        const jclass clss = pEnv->FindClass(
                "com/github/stephengold/joltjni/CustomCastRayCollector");
        EXCEPTION_CHECK(pEnv)

        mAddMethodId = pEnv->GetMethodID(clss, "addHit", "(J)V");
        EXCEPTION_CHECK(pEnv)
    }

    void AddHit(const RayCastResult& inResult) override {
        JNIEnv *pAttachEnv;
        jint retCode = ATTACH_CURRENT_THREAD(mpVM, &pAttachEnv);
        JPH_ASSERT(retCode == JNI_OK);

        const jlong resultVa = reinterpret_cast<jlong> (&inResult);
        pAttachEnv->CallVoidMethod(mJavaObject, mAddMethodId, resultVa);
        EXCEPTION_CHECK(pAttachEnv)
        mpVM->DetachCurrentThread();
    }

    ~CustomCastRayCollector() {
        JNIEnv *pAttachEnv;
        jint retCode = ATTACH_CURRENT_THREAD(mpVM, &pAttachEnv);
        JPH_ASSERT(retCode == JNI_OK);

        pAttachEnv->DeleteGlobalRef(mJavaObject);
        EXCEPTION_CHECK(pAttachEnv)
        mpVM->DetachCurrentThread();
    }
};

/*
 * Class:     com_github_stephengold_joltjni_CustomCastRayCollector
 * Method:    createCustomCollector
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CustomCastRayCollector_createCustomCollector
  (JNIEnv *pEnv, jobject javaObject) {
    CustomCastRayCollector * const pResult
            = new CustomCastRayCollector(pEnv, javaObject);
    TRACE_NEW("CustomCastRayCollector", pResult)
    return reinterpret_cast<jlong> (pResult);
}