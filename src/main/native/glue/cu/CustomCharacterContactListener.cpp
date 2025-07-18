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
#include "Jolt/Physics/Character/CharacterVirtual.h"
#include "auto/com_github_stephengold_joltjni_CustomCharacterContactListener.h"
#include "glue/glue.h"

using namespace JPH;

class CustomCharacterContactListener : CharacterContactListener {
    JavaVM *mpVM;
    jmethodID mAddedMethodId;
    jmethodID mAdjustMethodId;
    jmethodID mCcAddedMethodId;
    jmethodID mCcPersistedMethodId;
    jmethodID mCcRemovedMethodId;
    jmethodID mCcSolveMethodId;
    jmethodID mCcValidateMethodId;
    jmethodID mPersistedMethodId;
    jmethodID mRemovedMethodId;
    jmethodID mSolveMethodId;
    jmethodID mValidateMethodId;
    jobject mJavaObject;

public:
    CustomCharacterContactListener(JNIEnv *pEnv, jobject javaObject) {
        pEnv->GetJavaVM(&mpVM);

        mJavaObject = pEnv->NewGlobalRef(javaObject);
        EXCEPTION_CHECK(pEnv)

        const jclass clss = pEnv->FindClass(
                "com/github/stephengold/joltjni/CustomCharacterContactListener");
        EXCEPTION_CHECK(pEnv)

        mAddedMethodId = pEnv->GetMethodID(
                clss, "onContactAdded", "(JIIDDDFFFJ)V");
        EXCEPTION_CHECK(pEnv)

        mAdjustMethodId = pEnv->GetMethodID(
                clss, "onAdjustBodyVelocity", "(JJ[F)V");
        EXCEPTION_CHECK(pEnv)

        mCcAddedMethodId = pEnv->GetMethodID(
                clss, "onCharacterContactAdded", "(JJIDDDFFFJ)V");
        EXCEPTION_CHECK(pEnv)

        mCcPersistedMethodId = pEnv->GetMethodID(
                clss, "onCharacterContactPersisted", "(JJIDDDFFFJ)V");
        EXCEPTION_CHECK(pEnv)

        mCcRemovedMethodId = pEnv->GetMethodID(
                clss, "onCharacterContactRemoved", "(JII)V");
        EXCEPTION_CHECK(pEnv)

        mCcSolveMethodId = pEnv->GetMethodID(
                clss, "onCharacterContactSolve", "(JJIDDDFFFFFFJFFF[F)V");
        EXCEPTION_CHECK(pEnv)

        mCcValidateMethodId = pEnv->GetMethodID(
                clss, "onCharacterContactValidate", "(JJI)Z");
        EXCEPTION_CHECK(pEnv)

        mPersistedMethodId = pEnv->GetMethodID(
                clss, "onContactPersisted", "(JIIDDDFFFJ)V");
        EXCEPTION_CHECK(pEnv)

        mRemovedMethodId = pEnv->GetMethodID(
                clss, "onContactRemoved", "(JII)V");
        EXCEPTION_CHECK(pEnv)

        mSolveMethodId = pEnv->GetMethodID(
                clss, "onContactSolve", "(JIIDDDFFFFFFJFFF[F)V");
        EXCEPTION_CHECK(pEnv)

        mValidateMethodId = pEnv->GetMethodID(
                clss, "onContactValidate", "(JII)Z");
        EXCEPTION_CHECK(pEnv)
    }

    void OnAdjustBodyVelocity(const CharacterVirtual *inCharacter,
            const Body& inBody2, Vec3& ioLinearVelocity,
            Vec3& ioAngularVelocity) override {
        JNIEnv *pAttachEnv;
        jint retCode = ATTACH_CURRENT_THREAD(mpVM, &pAttachEnv);
        JPH_ASSERT(retCode == JNI_OK);

        const jlong characterVa = reinterpret_cast<jlong> (inCharacter);
        const jlong body2Va = reinterpret_cast<jlong> (&inBody2);
        const jfloatArray velocities = pAttachEnv->NewFloatArray(6);
        jboolean isCopy;
        jfloat *pFloats
                = pAttachEnv->GetFloatArrayElements(velocities, &isCopy);
        pFloats[0] = ioLinearVelocity.GetX();
        pFloats[1] = ioLinearVelocity.GetY();
        pFloats[2] = ioLinearVelocity.GetZ();
        pFloats[3] = ioAngularVelocity.GetX();
        pFloats[4] = ioAngularVelocity.GetY();
        pFloats[5] = ioAngularVelocity.GetZ();
        pAttachEnv->ReleaseFloatArrayElements(velocities, pFloats, 0);
        EXCEPTION_CHECK(pAttachEnv)

        pAttachEnv->CallVoidMethod(
                mJavaObject, mAdjustMethodId, characterVa, body2Va, velocities);
        EXCEPTION_CHECK(pAttachEnv)

        pFloats = pAttachEnv->GetFloatArrayElements(velocities, &isCopy);
        ioLinearVelocity = Vec3(pFloats[0], pFloats[1], pFloats[2]);
        ioAngularVelocity = Vec3(pFloats[3], pFloats[4], pFloats[5]);
        pAttachEnv->ReleaseFloatArrayElements(velocities, pFloats, JNI_ABORT);
        EXCEPTION_CHECK(pAttachEnv)

        mpVM->DetachCurrentThread();
    }

    void OnCharacterContactAdded(const CharacterVirtual *inCharacter,
            const CharacterVirtual *inOtherCharacter,
            const SubShapeID& inSubShapeID2, RVec3Arg inContactPosition,
            Vec3Arg inContactNormal, CharacterContactSettings& ioSettings) override {
        JNIEnv *pAttachEnv;
        jint retCode = ATTACH_CURRENT_THREAD(mpVM, &pAttachEnv);
        JPH_ASSERT(retCode == JNI_OK);

        const jlong characterVa = reinterpret_cast<jlong> (inCharacter);
        const jlong otherCharacterVa
                = reinterpret_cast<jlong> (inOtherCharacter);
        const jint subShapeId2 = inSubShapeID2.GetValue();
        const jdouble contactLocationX = inContactPosition.GetX();
        const jdouble contactLocationY = inContactPosition.GetY();
        const jdouble contactLocationZ = inContactPosition.GetZ();
        const jfloat contactNormalX = inContactNormal.GetX();
        const jfloat contactNormalY = inContactNormal.GetY();
        const jfloat contactNormalZ = inContactNormal.GetZ();
        const jlong settingsVa = reinterpret_cast<jlong> (&ioSettings);

        pAttachEnv->CallVoidMethod(mJavaObject, mCcAddedMethodId, characterVa,
                otherCharacterVa, subShapeId2, contactLocationX,
                contactLocationY, contactLocationZ, contactNormalX,
                contactNormalY, contactNormalZ, settingsVa);
        EXCEPTION_CHECK(pAttachEnv)

        mpVM->DetachCurrentThread();
    }

    void OnCharacterContactPersisted(const CharacterVirtual *inCharacter,
            const CharacterVirtual *inOtherCharacter,
            const SubShapeID& inSubShapeID2, RVec3Arg inContactPosition,
            Vec3Arg inContactNormal, CharacterContactSettings& ioSettings) override {
        JNIEnv *pAttachEnv;
        jint retCode = ATTACH_CURRENT_THREAD(mpVM, &pAttachEnv);
        JPH_ASSERT(retCode == JNI_OK);

        const jlong characterVa = reinterpret_cast<jlong> (inCharacter);
        const jlong otherCharacterVa
                = reinterpret_cast<jlong> (inOtherCharacter);
        const jint subShapeId2 = inSubShapeID2.GetValue();
        const jdouble contactLocationX = inContactPosition.GetX();
        const jdouble contactLocationY = inContactPosition.GetY();
        const jdouble contactLocationZ = inContactPosition.GetZ();
        const jfloat contactNormalX = inContactNormal.GetX();
        const jfloat contactNormalY = inContactNormal.GetY();
        const jfloat contactNormalZ = inContactNormal.GetZ();
        const jlong settingsVa = reinterpret_cast<jlong> (&ioSettings);

        pAttachEnv->CallVoidMethod(mJavaObject, mCcPersistedMethodId,
                characterVa, otherCharacterVa, subShapeId2, contactLocationX,
                contactLocationY, contactLocationZ, contactNormalX,
                contactNormalY, contactNormalZ, settingsVa);
        EXCEPTION_CHECK(pAttachEnv)

        mpVM->DetachCurrentThread();
    }

    void OnCharacterContactRemoved(const CharacterVirtual *inCharacter,
            const CharacterID& inOtherCharacterID,
            const SubShapeID& inSubShapeID2) override {
        JNIEnv *pAttachEnv;
        jint retCode = ATTACH_CURRENT_THREAD(mpVM, &pAttachEnv);
        JPH_ASSERT(retCode == JNI_OK);

        const jlong characterVa = reinterpret_cast<jlong> (inCharacter);
        const jint otherCharacterId = inOtherCharacterID.GetValue();
        const jint subShapeId2 = inSubShapeID2.GetValue();

        pAttachEnv->CallVoidMethod(mJavaObject, mCcRemovedMethodId, characterVa,
                otherCharacterId, subShapeId2);
        EXCEPTION_CHECK(pAttachEnv)

        mpVM->DetachCurrentThread();
    }

    void OnCharacterContactSolve(const CharacterVirtual *inCharacter,
            const CharacterVirtual *inOtherCharacter,
            const SubShapeID& inSubShapeID2, RVec3Arg inContactPosition,
            Vec3Arg inContactNormal, Vec3Arg inContactVelocity,
            const PhysicsMaterial *inContactMaterial,
            Vec3Arg inCharacterVelocity, Vec3& ioNewCharacterVelocity) override {
        JNIEnv *pAttachEnv;
        jint retCode = ATTACH_CURRENT_THREAD(mpVM, &pAttachEnv);
        JPH_ASSERT(retCode == JNI_OK);

        const jlong characterVa = reinterpret_cast<jlong> (inCharacter);
        const jlong otherCharacterVa
                = reinterpret_cast<jlong> (inOtherCharacter);
        const jint subShapeId2 = inSubShapeID2.GetValue();
        const jdouble contactLocationX = inContactPosition.GetX();
        const jdouble contactLocationY = inContactPosition.GetY();
        const jdouble contactLocationZ = inContactPosition.GetZ();
        const jfloat contactNormalX = inContactNormal.GetX();
        const jfloat contactNormalY = inContactNormal.GetY();
        const jfloat contactNormalZ = inContactNormal.GetZ();
        const jfloat contactVelocityX = inContactVelocity.GetX();
        const jfloat contactVelocityY = inContactVelocity.GetY();
        const jfloat contactVelocityZ = inContactVelocity.GetZ();
        const jlong materialVa = reinterpret_cast<jlong> (inContactMaterial);
        const jfloat characterVelocityX = inCharacterVelocity.GetX();
        const jfloat characterVelocityY = inCharacterVelocity.GetY();
        const jfloat characterVelocityZ = inCharacterVelocity.GetZ();
        const jfloatArray newCharacterVelocity = pAttachEnv->NewFloatArray(3);
        jboolean isCopy;
        jfloat *pFloats = pAttachEnv->GetFloatArrayElements(
                newCharacterVelocity, &isCopy);
        pFloats[0] = ioNewCharacterVelocity.GetX();
        pFloats[1] = ioNewCharacterVelocity.GetY();
        pFloats[2] = ioNewCharacterVelocity.GetZ();
        pAttachEnv->ReleaseFloatArrayElements(newCharacterVelocity, pFloats, 0);
        EXCEPTION_CHECK(pAttachEnv)

        pAttachEnv->CallVoidMethod(mJavaObject, mCcSolveMethodId, characterVa,
                otherCharacterVa, subShapeId2, contactLocationX,
                contactLocationY, contactLocationZ, contactNormalX,
                contactNormalY, contactNormalZ, contactVelocityX,
                contactVelocityY, contactVelocityZ, materialVa,
                characterVelocityX, characterVelocityY, characterVelocityZ,
                newCharacterVelocity);
        EXCEPTION_CHECK(pAttachEnv)

        pFloats = pAttachEnv->GetFloatArrayElements(
                newCharacterVelocity, &isCopy);
        ioNewCharacterVelocity = Vec3(pFloats[0], pFloats[1], pFloats[2]);
        pAttachEnv->ReleaseFloatArrayElements(
                newCharacterVelocity, pFloats, JNI_ABORT);
        EXCEPTION_CHECK(pAttachEnv)

        mpVM->DetachCurrentThread();
    }

    bool OnCharacterContactValidate(const CharacterVirtual *inCharacter,
            const CharacterVirtual *inOtherCharacter,
            const SubShapeID& inSubShapeID2) override {
        JNIEnv *pAttachEnv;
        jint retCode = ATTACH_CURRENT_THREAD(mpVM, &pAttachEnv);
        JPH_ASSERT(retCode == JNI_OK);

        const jlong characterVa = reinterpret_cast<jlong> (inCharacter);
        const jlong otherCharacterVa
                = reinterpret_cast<jlong> (inOtherCharacter);
        const jint subShapeId2 = inSubShapeID2.GetValue();
        const bool result = pAttachEnv->CallBooleanMethod(
                mJavaObject, mCcValidateMethodId, characterVa, otherCharacterVa,
                subShapeId2);
        EXCEPTION_CHECK(pAttachEnv)

        mpVM->DetachCurrentThread();
        return result;
    }

    void OnContactAdded(const CharacterVirtual *inCharacter,
            const BodyID& inBodyID2, const SubShapeID& inSubShapeID2,
            RVec3Arg inContactPosition, Vec3Arg inContactNormal,
            CharacterContactSettings& ioSettings) override {
        JNIEnv *pAttachEnv;
        jint retCode = ATTACH_CURRENT_THREAD(mpVM, &pAttachEnv);
        JPH_ASSERT(retCode == JNI_OK);

        const jlong characterVa = reinterpret_cast<jlong> (inCharacter);
        const jint bodyId2 = inBodyID2.GetIndexAndSequenceNumber();
        const jint subShapeId2 = inSubShapeID2.GetValue();
        const jdouble contactLocationX = inContactPosition.GetX();
        const jdouble contactLocationY = inContactPosition.GetY();
        const jdouble contactLocationZ = inContactPosition.GetZ();
        const jfloat contactNormalX = inContactNormal.GetX();
        const jfloat contactNormalY = inContactNormal.GetY();
        const jfloat contactNormalZ = inContactNormal.GetZ();
        const jlong settingsVa = reinterpret_cast<jlong> (&ioSettings);

        pAttachEnv->CallVoidMethod(mJavaObject, mAddedMethodId, characterVa,
                bodyId2, subShapeId2, contactLocationX, contactLocationY,
                contactLocationZ, contactNormalX, contactNormalY,
                contactNormalZ, settingsVa);
        EXCEPTION_CHECK(pAttachEnv)

        mpVM->DetachCurrentThread();
    }

    void OnContactPersisted(const CharacterVirtual *inCharacter,
            const BodyID& inBodyID2, const SubShapeID& inSubShapeID2,
            RVec3Arg inContactPosition, Vec3Arg inContactNormal,
            CharacterContactSettings& ioSettings) override {
        JNIEnv *pAttachEnv;
        jint retCode = ATTACH_CURRENT_THREAD(mpVM, &pAttachEnv);
        JPH_ASSERT(retCode == JNI_OK);

        const jlong characterVa = reinterpret_cast<jlong> (inCharacter);
        const jint bodyId2 = inBodyID2.GetIndexAndSequenceNumber();
        const jint subShapeId2 = inSubShapeID2.GetValue();
        const jdouble contactLocationX = inContactPosition.GetX();
        const jdouble contactLocationY = inContactPosition.GetY();
        const jdouble contactLocationZ = inContactPosition.GetZ();
        const jfloat contactNormalX = inContactNormal.GetX();
        const jfloat contactNormalY = inContactNormal.GetY();
        const jfloat contactNormalZ = inContactNormal.GetZ();
        const jlong settingsVa = reinterpret_cast<jlong> (&ioSettings);

        pAttachEnv->CallVoidMethod(mJavaObject, mPersistedMethodId, characterVa,
                bodyId2, subShapeId2, contactLocationX, contactLocationY,
                contactLocationZ, contactNormalX, contactNormalY,
                contactNormalZ, settingsVa);
        EXCEPTION_CHECK(pAttachEnv)

        mpVM->DetachCurrentThread();
    }

    void OnContactRemoved(const CharacterVirtual *inCharacter,
            const BodyID& inBodyID2, const SubShapeID& inSubShapeID2) override {
        JNIEnv *pAttachEnv;
        jint retCode = ATTACH_CURRENT_THREAD(mpVM, &pAttachEnv);
        JPH_ASSERT(retCode == JNI_OK);

        const jlong characterVa = reinterpret_cast<jlong> (inCharacter);
        const jint bodyId2 = inBodyID2.GetIndexAndSequenceNumber();
        const jint subShapeId2 = inSubShapeID2.GetValue();

        pAttachEnv->CallVoidMethod(mJavaObject, mRemovedMethodId, characterVa,
                bodyId2, subShapeId2);
        EXCEPTION_CHECK(pAttachEnv)

        mpVM->DetachCurrentThread();
    }

    void OnContactSolve(const CharacterVirtual *inCharacter,
            const BodyID& inBodyID2, const SubShapeID& inSubShapeID2,
            RVec3Arg inContactPosition, Vec3Arg inContactNormal,
            Vec3Arg inContactVelocity, const PhysicsMaterial *inContactMaterial,
            Vec3Arg inCharacterVelocity, Vec3& ioNewCharacterVelocity) override {
        JNIEnv *pAttachEnv;
        jint retCode = ATTACH_CURRENT_THREAD(mpVM, &pAttachEnv);
        JPH_ASSERT(retCode == JNI_OK);

        const jlong characterVa = reinterpret_cast<jlong> (inCharacter);
        const jint bodyId2 = inBodyID2.GetIndexAndSequenceNumber();
        const jint subShapeId2 = inSubShapeID2.GetValue();
        const jdouble contactLocationX = inContactPosition.GetX();
        const jdouble contactLocationY = inContactPosition.GetY();
        const jdouble contactLocationZ = inContactPosition.GetZ();
        const jfloat contactNormalX = inContactNormal.GetX();
        const jfloat contactNormalY = inContactNormal.GetY();
        const jfloat contactNormalZ = inContactNormal.GetZ();
        const jfloat contactVelocityX = inContactVelocity.GetX();
        const jfloat contactVelocityY = inContactVelocity.GetY();
        const jfloat contactVelocityZ = inContactVelocity.GetZ();
        const jlong materialVa = reinterpret_cast<jlong> (inContactMaterial);
        const jfloat characterVelocityX = inCharacterVelocity.GetX();
        const jfloat characterVelocityY = inCharacterVelocity.GetY();
        const jfloat characterVelocityZ = inCharacterVelocity.GetZ();
        const jfloatArray newCharacterVelocity = pAttachEnv->NewFloatArray(3);
        jboolean isCopy;
        jfloat *pFloats = pAttachEnv->GetFloatArrayElements(
                newCharacterVelocity, &isCopy);
        pFloats[0] = ioNewCharacterVelocity.GetX();
        pFloats[1] = ioNewCharacterVelocity.GetY();
        pFloats[2] = ioNewCharacterVelocity.GetZ();
        pAttachEnv->ReleaseFloatArrayElements(newCharacterVelocity, pFloats, 0);
        EXCEPTION_CHECK(pAttachEnv)

        pAttachEnv->CallVoidMethod(mJavaObject, mSolveMethodId, characterVa,
                bodyId2, subShapeId2, contactLocationX, contactLocationY,
                contactLocationZ, contactNormalX, contactNormalY,
                contactNormalZ, contactVelocityX, contactVelocityY,
                contactVelocityZ, materialVa, characterVelocityX,
                characterVelocityY, characterVelocityZ, newCharacterVelocity);
        EXCEPTION_CHECK(pAttachEnv)

        pFloats = pAttachEnv->GetFloatArrayElements(
                newCharacterVelocity, &isCopy);
        ioNewCharacterVelocity = Vec3(pFloats[0], pFloats[1], pFloats[2]);
        pAttachEnv->ReleaseFloatArrayElements(
                newCharacterVelocity, pFloats, JNI_ABORT);
        EXCEPTION_CHECK(pAttachEnv)

        mpVM->DetachCurrentThread();
    }

    bool OnContactValidate(const CharacterVirtual *inCharacter,
            const BodyID& inBodyID2, const SubShapeID& inSubShapeID2) override {
        JNIEnv *pAttachEnv;
        jint retCode = ATTACH_CURRENT_THREAD(mpVM, &pAttachEnv);
        JPH_ASSERT(retCode == JNI_OK);

        const jlong characterVa = reinterpret_cast<jlong> (inCharacter);
        const jint bodyId2 = inBodyID2.GetIndexAndSequenceNumber();
        const jint subShapeId2 = inSubShapeID2.GetValue();
        const bool result = pAttachEnv->CallBooleanMethod(mJavaObject,
                mValidateMethodId, characterVa, bodyId2, subShapeId2);
        EXCEPTION_CHECK(pAttachEnv)

        mpVM->DetachCurrentThread();
        return result;
    }

    ~CustomCharacterContactListener() {
        JNIEnv *pAttachEnv;
        jint retCode = ATTACH_CURRENT_THREAD(mpVM, &pAttachEnv);
        JPH_ASSERT(retCode == JNI_OK);

        pAttachEnv->DeleteGlobalRef(mJavaObject);
        EXCEPTION_CHECK(pAttachEnv)
        mpVM->DetachCurrentThread();
    }
};

/*
 * Class:     com_github_stephengold_joltjni_CustomCharacterContactListener
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CustomCharacterContactListener_createDefault
  (JNIEnv *pEnv, jobject javaObject) {
    CustomCharacterContactListener * const pResult
            = new CustomCharacterContactListener(pEnv, javaObject);
    TRACE_NEW("CustomCharacterContactListener", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_CustomCharacterContactListener
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CustomCharacterContactListener_free
  BODYOF_FREE(CustomCharacterContactListener)