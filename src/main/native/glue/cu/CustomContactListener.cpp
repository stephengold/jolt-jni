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
#include "Jolt/Physics/Constraints/ContactConstraintManager.h"
#include "auto/com_github_stephengold_joltjni_CustomContactListener.h"
#include "glue/glue.h"

using namespace JPH;

class CustomContactListener : ContactListener {
    JavaVM *mpVM;
    jmethodID mAddedMethodId;
    jmethodID mPersistedMethodId;
    jmethodID mRemovedMethodId;
    jmethodID mValidateMethodId;
    jobject mJavaObject;

public:
    CustomContactListener(JNIEnv *pEnv, jobject javaObject) {
        pEnv->GetJavaVM(&mpVM);

        mJavaObject = pEnv->NewGlobalRef(javaObject);
        JPH_ASSERT(!pEnv->ExceptionCheck());

        const jclass clss = pEnv->FindClass(
                "com/github/stephengold/joltjni/CustomContactListener");
        JPH_ASSERT(!pEnv->ExceptionCheck());

        mAddedMethodId = pEnv->GetMethodID(clss, "onContactAdded", "(JJJJ)V");
        JPH_ASSERT(!pEnv->ExceptionCheck());

        mPersistedMethodId = pEnv->GetMethodID(clss, "onContactPersisted", "(JJJJ)V");
        JPH_ASSERT(!pEnv->ExceptionCheck());

        mRemovedMethodId = pEnv->GetMethodID(clss, "onContactRemoved", "(J)V");
        JPH_ASSERT(!pEnv->ExceptionCheck());

        mValidateMethodId = pEnv->GetMethodID(clss, "onContactValidate", "(JJDDDJ)I");
        JPH_ASSERT(!pEnv->ExceptionCheck());
    }

    void OnContactAdded(const Body& inBody1, const Body& inBody2,
            const ContactManifold& inManifold, ContactSettings& ioSettings) override {
        JNIEnv *pAttachEnv;
        jint retCode = ATTACH_CURRENT_THREAD(mpVM, &pAttachEnv);
        JPH_ASSERT(retCode == JNI_OK);

        const jlong body1Va = reinterpret_cast<jlong> (&inBody1);
        const jlong body2Va = reinterpret_cast<jlong> (&inBody2);
        const jlong manifoldVa = reinterpret_cast<jlong> (&inManifold);
        const jlong settingsVa = reinterpret_cast<jlong> (&ioSettings);
        pAttachEnv->CallVoidMethod(mJavaObject, mAddedMethodId, body1Va, body2Va, manifoldVa, settingsVa);
        JPH_ASSERT(!pAttachEnv->ExceptionCheck());
        mpVM->DetachCurrentThread();
    }

    void OnContactPersisted(const Body& inBody1, const Body& inBody2,
            const ContactManifold& inManifold, ContactSettings& ioSettings) override {
        JNIEnv *pAttachEnv;
        jint retCode = ATTACH_CURRENT_THREAD(mpVM, &pAttachEnv);
        JPH_ASSERT(retCode == JNI_OK);

        const jlong body1Va = reinterpret_cast<jlong> (&inBody1);
        const jlong body2Va = reinterpret_cast<jlong> (&inBody2);
        const jlong manifoldVa = reinterpret_cast<jlong> (&inManifold);
        const jlong settingsVa = reinterpret_cast<jlong> (&ioSettings);
        pAttachEnv->CallVoidMethod(mJavaObject, mPersistedMethodId, body1Va,
                body2Va, manifoldVa, settingsVa);
        JPH_ASSERT(!pAttachEnv->ExceptionCheck());
        mpVM->DetachCurrentThread();
    }

    void OnContactRemoved(const SubShapeIDPair& pair) override {
        JNIEnv *pAttachEnv;
        jint retCode = ATTACH_CURRENT_THREAD(mpVM, &pAttachEnv);
        JPH_ASSERT(retCode == JNI_OK);

        const jlong pairVa = reinterpret_cast<jlong> (&pair);
        pAttachEnv->CallVoidMethod(mJavaObject, mRemovedMethodId, pairVa);
        JPH_ASSERT(!pAttachEnv->ExceptionCheck());
        mpVM->DetachCurrentThread();
    }

    ValidateResult OnContactValidate(const Body& inBody1, const Body& inBody2,
            RVec3Arg inBaseOffset, const CollideShapeResult& inCollisionResult) override {
        JNIEnv *pAttachEnv;
        jint retCode = ATTACH_CURRENT_THREAD(mpVM, &pAttachEnv);
        JPH_ASSERT(retCode == JNI_OK);

        const jlong body1Va = reinterpret_cast<jlong> (&inBody1);
        const jlong body2Va = reinterpret_cast<jlong> (&inBody2);
        const jdouble offsetX = inBaseOffset.GetX();
        const jdouble offsetY = inBaseOffset.GetY();
        const jdouble offsetZ = inBaseOffset.GetZ();
        const jlong shapeVa = reinterpret_cast<jlong> (&inCollisionResult);
        const jint jintResult = pAttachEnv->CallIntMethod(mJavaObject,
                mValidateMethodId, body1Va, body2Va, offsetX, offsetY, offsetZ, shapeVa);
        JPH_ASSERT(!pAttachEnv->ExceptionCheck());
        mpVM->DetachCurrentThread();
        return (ValidateResult) jintResult;
    }

    ~CustomContactListener() {
        JNIEnv *pAttachEnv;
        jint retCode = ATTACH_CURRENT_THREAD(mpVM, &pAttachEnv);
        JPH_ASSERT(retCode == JNI_OK);

        pAttachEnv->DeleteGlobalRef(mJavaObject);
        JPH_ASSERT(!pAttachEnv->ExceptionCheck());
        mpVM->DetachCurrentThread();
    }
};

/*
 * Class:     com_github_stephengold_joltjni_CustomContactListener
 * Method:    createCustomContactListener
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CustomContactListener_createCustomContactListener
  (JNIEnv *pEnv, jobject javaObject) {
    CustomContactListener * const pResult
            = new CustomContactListener(pEnv, javaObject);
    TRACE_NEW("CustomContactListener", pResult)
    return reinterpret_cast<jlong> (pResult);
}