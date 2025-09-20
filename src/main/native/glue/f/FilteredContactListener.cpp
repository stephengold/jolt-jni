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
#include "Jolt/Physics/Body/Body.h"
#include "Jolt/Physics/Body/BodyFilter.h"
#include "Jolt/Physics/Collision/BroadPhase/BroadPhaseLayer.h"
#include "Jolt/Physics/Collision/ContactListener.h"
#include "Jolt/Physics/Collision/ObjectLayer.h"

#include "auto/com_github_stephengold_joltjni_FilteredContactListener.h"
#include "glue/glue.h"

using namespace JPH;

enum class EFilterMode : jint {
    Both,
    Either,
    Neither,
    NotBoth,
    Skip
};
class FilteredContactListener : ContactListener {
    JavaVM *mpVM;
    jmethodID mAddedMethodId;
    jmethodID mPersistedMethodId;
    jmethodID mRemovedMethodId;
    jmethodID mValidateMethodId;
    jobject mJavaObject;
public:
    bool mEnableAdded = true;
    bool mEnablePersisted = true;
    bool mEnableRemoved = true;
    bool mEnableValidate = true;
    EFilterMode mBodyFilterMode = EFilterMode::Both;
    EFilterMode mBplFilterMode = EFilterMode::Both;
    BodyFilter *mpBodyFilter = nullptr;
    BroadPhaseLayerFilter *mpBplFilter = nullptr;
    ObjectLayerPairFilter *mpOlpFilter = nullptr;
    // constructor:
    FilteredContactListener(JNIEnv *pEnv, jobject javaObject) {
        pEnv->GetJavaVM(&mpVM);
        mJavaObject = pEnv->NewGlobalRef(javaObject);
        EXCEPTION_CHECK(pEnv)
        const jclass clss = pEnv->FindClass(
                "com/github/stephengold/joltjni/FilteredContactListener");
        EXCEPTION_CHECK(pEnv)
        mAddedMethodId = pEnv->GetMethodID(clss, "onContactAdded", "(JJJJ)V");
        EXCEPTION_CHECK(pEnv)
        mPersistedMethodId
                = pEnv->GetMethodID(clss, "onContactPersisted", "(JJJJ)V");
        EXCEPTION_CHECK(pEnv)
        mRemovedMethodId = pEnv->GetMethodID(clss, "onContactRemoved", "(J)V");
        EXCEPTION_CHECK(pEnv)
        mValidateMethodId
                = pEnv->GetMethodID(clss, "onContactValidate", "(JJDDDJ)I");
        EXCEPTION_CHECK(pEnv)
    }
    void OnContactAdded(const Body& inBody1, const Body& inBody2,
            const ContactManifold& inManifold,
            ContactSettings& ioSettings) override {
        if (!mEnableAdded) {
            return;
        }
        if (SkipBodyPair(inBody1, inBody2)) {
            return;
        }
        JNIEnv *pAttachEnv;
        jint retCode = ATTACH_CURRENT_THREAD(mpVM, &pAttachEnv);
        JPH_ASSERT(retCode == JNI_OK);
        const jlong body1Va = reinterpret_cast<jlong> (&inBody1);
        const jlong body2Va = reinterpret_cast<jlong> (&inBody2);
        const jlong manifoldVa = reinterpret_cast<jlong> (&inManifold);
        const jlong settingsVa = reinterpret_cast<jlong> (&ioSettings);
        pAttachEnv->CallVoidMethod(mJavaObject, mAddedMethodId,
                body1Va, body2Va, manifoldVa, settingsVa);
        EXCEPTION_CHECK(pAttachEnv)
        mpVM->DetachCurrentThread();
    }
    void OnContactPersisted(const Body& inBody1, const Body& inBody2,
            const ContactManifold& inManifold,
            ContactSettings& ioSettings) override {
        if (!mEnablePersisted) {
            return;
        }
        if (SkipBodyPair(inBody1, inBody2)) {
            return;
        }
        JNIEnv *pAttachEnv;
        jint retCode = ATTACH_CURRENT_THREAD(mpVM, &pAttachEnv);
        JPH_ASSERT(retCode == JNI_OK);
        const jlong body1Va = reinterpret_cast<jlong> (&inBody1);
        const jlong body2Va = reinterpret_cast<jlong> (&inBody2);
        const jlong manifoldVa = reinterpret_cast<jlong> (&inManifold);
        const jlong settingsVa = reinterpret_cast<jlong> (&ioSettings);
        pAttachEnv->CallVoidMethod(mJavaObject, mPersistedMethodId, body1Va,
                body2Va, manifoldVa, settingsVa);
        EXCEPTION_CHECK(pAttachEnv)
        mpVM->DetachCurrentThread();
    }
    void OnContactRemoved(const SubShapeIDPair& pair) override {
        JNIEnv *pAttachEnv;
        jint retCode = ATTACH_CURRENT_THREAD(mpVM, &pAttachEnv);
        JPH_ASSERT(retCode == JNI_OK);

        const jlong pairVa = reinterpret_cast<jlong> (&pair);
        pAttachEnv->CallVoidMethod(mJavaObject, mRemovedMethodId, pairVa);
        EXCEPTION_CHECK(pAttachEnv)
        mpVM->DetachCurrentThread();
    }
    ValidateResult OnContactValidate(const Body& inBody1, const Body& inBody2,
            RVec3Arg inBaseOffset,
            const CollideShapeResult& inCollisionResult) override {
        if (!mEnableValidate) {
            return ValidateResult::AcceptAllContactsForThisBodyPair;
        }
        if (SkipBodyPair(inBody1, inBody2)) {
            return ValidateResult::AcceptAllContactsForThisBodyPair;
        }
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
                mValidateMethodId, body1Va, body2Va,
                offsetX, offsetY, offsetZ, shapeVa);
        EXCEPTION_CHECK(pAttachEnv)
        mpVM->DetachCurrentThread();
        return (ValidateResult) jintResult;
    }
    bool SkipBodyPair(const Body& inBody1, const Body& inBody2) {
        if (mBodyFilterMode == EFilterMode::Skip
                || mBplFilterMode == EFilterMode::Skip) {
            return true;
        }
        if (mpBodyFilter) {
            const BodyID& id1 = inBody1.GetID();
            const bool collide1 = mpBodyFilter->ShouldCollide(id1);
            if (mBodyFilterMode == EFilterMode::Both && !collide1) return true;
            if (mBodyFilterMode == EFilterMode::Neither && collide1) return true;
            const BodyID& id2 = inBody2.GetID();
            const bool collide2 = mpBodyFilter->ShouldCollide(id2);
            switch (mBodyFilterMode) {
                case EFilterMode::Both:
                    if (!collide2) return true;
                    break;
                case EFilterMode::Either:
                    if (!(collide1 || collide2)) return true;
                    break;
                case EFilterMode::Neither:
                    if (collide2) return true;
                    break;
                case EFilterMode::NotBoth:
                    if (collide1 && collide2) return true;
                    break;
                case EFilterMode::Skip:
                    JPH_ASSERT(false);
            }
        }
        if (mpBplFilter) {
            const BroadPhaseLayer bpl1 = inBody1.GetBroadPhaseLayer();
            const bool collide1 = mpBplFilter->ShouldCollide(bpl1);
            if (mBodyFilterMode == EFilterMode::Both && !collide1) return true;
            if (mBodyFilterMode == EFilterMode::Neither && collide1) return true;
            const BroadPhaseLayer bpl2 = inBody2.GetBroadPhaseLayer();
            const bool collide2 = mpBplFilter->ShouldCollide(bpl2);
            switch (mBodyFilterMode) {
                case EFilterMode::Both:
                    if (!collide2) return true;
                    break;
                case EFilterMode::Either:
                    if (!(collide1 || collide2)) return true;
                    break;
                case EFilterMode::Neither:
                    if (collide2) return true;
                    break;
                case EFilterMode::NotBoth:
                    if (collide1 && collide2) return true;
                case EFilterMode::Skip:
                    JPH_ASSERT(false);
            }
        }
        if (mpOlpFilter) {
            const ObjectLayer ol1 = inBody1.GetObjectLayer();
            const ObjectLayer ol2 = inBody2.GetObjectLayer();
            const bool collide = mpOlpFilter->ShouldCollide(ol1, ol2);
            if (!collide) return true;
        }
        return false; // don't skip the callback
    }
    // destructor:
    ~FilteredContactListener() {
        JNIEnv *pAttachEnv;
        jint retCode = ATTACH_CURRENT_THREAD(mpVM, &pAttachEnv);
        JPH_ASSERT(retCode == JNI_OK);
        pAttachEnv->DeleteGlobalRef(mJavaObject);
        EXCEPTION_CHECK(pAttachEnv)
        mpVM->DetachCurrentThread();
    }
};

/*
 * Class:     com_github_stephengold_joltjni_FilteredContactListener
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_FilteredContactListener_createDefault
  (JNIEnv *pEnv, jobject javaObject) {
    FilteredContactListener * const pResult
            = new FilteredContactListener(pEnv, javaObject);
    TRACE_NEW("FilteredContactListener", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_FilteredContactListener
 * Method:    getBodyFilterMode
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_FilteredContactListener_getBodyFilterMode
  (JNIEnv *, jclass, jlong listenerVa) {
    const FilteredContactListener * const pListener
            = reinterpret_cast<FilteredContactListener *> (listenerVa);
    const EFilterMode result = pListener->mBodyFilterMode;
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_FilteredContactListener
 * Method:    getBroadPhaseLayerFilterMode
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_FilteredContactListener_getBroadPhaseLayerFilterMode
  (JNIEnv *, jclass, jlong listenerVa) {
    const FilteredContactListener * const pListener
            = reinterpret_cast<FilteredContactListener *> (listenerVa);
    const EFilterMode result = pListener->mBplFilterMode;
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_FilteredContactListener
 * Method:    getEnableAdded
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_FilteredContactListener_getEnableAdded
  (JNIEnv *, jclass, jlong listenerVa) {
    const FilteredContactListener * const pListener
            = reinterpret_cast<FilteredContactListener *> (listenerVa);
    const bool result = pListener->mEnableAdded;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_FilteredContactListener
 * Method:    getEnablePersisted
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_FilteredContactListener_getEnablePersisted
  (JNIEnv *, jclass, jlong listenerVa) {
    const FilteredContactListener * const pListener
            = reinterpret_cast<FilteredContactListener *> (listenerVa);
    const bool result = pListener->mEnablePersisted;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_FilteredContactListener
 * Method:    getEnableRemoved
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_FilteredContactListener_getEnableRemoved
  (JNIEnv *, jclass, jlong listenerVa) {
    const FilteredContactListener * const pListener
            = reinterpret_cast<FilteredContactListener *> (listenerVa);
    const bool result = pListener->mEnableRemoved;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_FilteredContactListener
 * Method:    getEnableValidate
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_FilteredContactListener_getEnableValidate
  (JNIEnv *, jclass, jlong listenerVa) {
    const FilteredContactListener * const pListener
            = reinterpret_cast<FilteredContactListener *> (listenerVa);
    const bool result = pListener->mEnableValidate;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_FilteredContactListener
 * Method:    setBodyFilter
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_FilteredContactListener_setBodyFilter
  (JNIEnv *, jclass, jlong listenerVa, jlong bodyFilterVa) {
    FilteredContactListener * const pListener
            = reinterpret_cast<FilteredContactListener *> (listenerVa);
    BodyFilter * const pFilter = reinterpret_cast<BodyFilter *> (bodyFilterVa);
    pListener->mpBodyFilter = pFilter;
}

/*
 * Class:     com_github_stephengold_joltjni_FilteredContactListener
 * Method:    setBodyFilterMode
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_FilteredContactListener_setBodyFilterMode
  (JNIEnv *, jclass, jlong listenerVa, jint ordinal) {
    FilteredContactListener * const pListener
            = reinterpret_cast<FilteredContactListener *> (listenerVa);
    const EFilterMode mode = (EFilterMode) ordinal;
    pListener->mBodyFilterMode = mode;
}

/*
 * Class:     com_github_stephengold_joltjni_FilteredContactListener
 * Method:    setBroadPhaseLayerFilter
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_FilteredContactListener_setBroadPhaseLayerFilter
  (JNIEnv *, jclass, jlong listenerVa, jlong bplFilterVa) {
    FilteredContactListener * const pListener
            = reinterpret_cast<FilteredContactListener *> (listenerVa);
    BroadPhaseLayerFilter * const pFilter
            = reinterpret_cast<BroadPhaseLayerFilter *> (bplFilterVa);
    pListener->mpBplFilter = pFilter;
}

/*
 * Class:     com_github_stephengold_joltjni_FilteredContactListener
 * Method:    setBroadPhaseLayerFilterMode
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_FilteredContactListener_setBroadPhaseLayerFilterMode
  (JNIEnv *, jclass, jlong listenerVa, jint ordinal) {
    FilteredContactListener * const pListener
            = reinterpret_cast<FilteredContactListener *> (listenerVa);
    const EFilterMode mode = (EFilterMode) ordinal;
    pListener->mBplFilterMode = mode;
}

/*
 * Class:     com_github_stephengold_joltjni_FilteredContactListener
 * Method:    setEnableAdded
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_FilteredContactListener_setEnableAdded
  (JNIEnv *, jclass, jlong listenerVa, jboolean enable) {
    FilteredContactListener * const pListener
            = reinterpret_cast<FilteredContactListener *> (listenerVa);
    pListener->mEnableAdded = enable;
}

/*
 * Class:     com_github_stephengold_joltjni_FilteredContactListener
 * Method:    setEnablePersisted
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_FilteredContactListener_setEnablePersisted
  (JNIEnv *, jclass, jlong listenerVa, jboolean enable) {
    FilteredContactListener * const pListener
            = reinterpret_cast<FilteredContactListener *> (listenerVa);
    pListener->mEnablePersisted = enable;
}

/*
 * Class:     com_github_stephengold_joltjni_FilteredContactListener
 * Method:    setEnableRemoved
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_FilteredContactListener_setEnableRemoved
  (JNIEnv *, jclass, jlong listenerVa, jboolean enable) {
    FilteredContactListener * const pListener
            = reinterpret_cast<FilteredContactListener *> (listenerVa);
    pListener->mEnableRemoved = enable;
}

/*
 * Class:     com_github_stephengold_joltjni_FilteredContactListener
 * Method:    setEnableValidate
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_FilteredContactListener_setEnableValidate
  (JNIEnv *, jclass, jlong listenerVa, jboolean enable) {
    FilteredContactListener * const pListener
            = reinterpret_cast<FilteredContactListener *> (listenerVa);
    pListener->mEnableValidate = enable;
}

/*
 * Class:     com_github_stephengold_joltjni_FilteredContactListener
 * Method:    setObjectLayerPairFilter
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_FilteredContactListener_setObjectLayerPairFilter
  (JNIEnv *, jclass, jlong listenerVa, jlong olpFilterVa) {
    FilteredContactListener * const pListener
            = reinterpret_cast<FilteredContactListener *> (listenerVa);
    ObjectLayerPairFilter * const pFilter
            = reinterpret_cast<ObjectLayerPairFilter *> (olpFilterVa);
    pListener->mpOlpFilter = pFilter;
}