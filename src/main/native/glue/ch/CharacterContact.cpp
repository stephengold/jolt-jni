/*
Copyright (c) 2024-2026 Stephen Gold

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
#include "auto/com_github_stephengold_joltjni_CharacterContact.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_CharacterContact
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterContact_free
  BODYOF_FREE(CharacterContact)

/*
 * Class:     com_github_stephengold_joltjni_CharacterContact
 * Method:    getCanPushCharacter
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_CharacterContact_getCanPushCharacter
  (JNIEnv *, jclass, jlong contactVa) {
    const CharacterContact * const pContact
            = reinterpret_cast<CharacterContact *> (contactVa);
    const bool result = pContact->mCanPushCharacter;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterContact
 * Method:    getCharacterB
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CharacterContact_getCharacterB
  (JNIEnv *, jclass, jlong contactVa) {
    const CharacterContact * const pContact
            = reinterpret_cast<CharacterContact *> (contactVa);
    const CharacterVirtual * const pResult = pContact->mCharacterB;
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterContact
 * Method:    getContactNormal
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterContact_getContactNormal
  (JNIEnv *pEnv, jclass, jlong contactVa, jobject storeFloats) {
    const CharacterContact * const pContact
            = reinterpret_cast<CharacterContact *> (contactVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& result = pContact->mContactNormal;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterContact
 * Method:    getDistance
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterContact_getDistance
  (JNIEnv *, jclass, jlong contactVa) {
    const CharacterContact * const pContact
            = reinterpret_cast<CharacterContact *> (contactVa);
    const float result = pContact->mDistance;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterContact
 * Method:    getFraction
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterContact_getFraction
  (JNIEnv *, jclass, jlong contactVa) {
    const CharacterContact * const pContact
            = reinterpret_cast<CharacterContact *> (contactVa);
    const float result = pContact->mFraction;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterContact
 * Method:    getHadCollision
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_CharacterContact_getHadCollision
  (JNIEnv *, jclass, jlong contactVa) {
    const CharacterContact * const pContact
            = reinterpret_cast<CharacterContact *> (contactVa);
    const bool result = pContact->mHadCollision;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterContact
 * Method:    getIsBackFacingContact
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_CharacterContact_getIsBackFacingContact
  (JNIEnv *, jclass, jlong contactVa) {
    const CharacterContact * const pContact
            = reinterpret_cast<CharacterContact *> (contactVa);
    const bool result = pContact->mIsBackFacingContact;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterContact
 * Method:    getIsSensorB
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_CharacterContact_getIsSensorB
  (JNIEnv *, jclass, jlong contactVa) {
    const CharacterContact * const pContact
            = reinterpret_cast<CharacterContact *> (contactVa);
    const bool result = pContact->mIsSensorB;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterContact
 * Method:    getLinearVelocity
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterContact_getLinearVelocity
  (JNIEnv *pEnv, jclass, jlong contactVa, jobject storeFloats) {
    const CharacterContact * const pContact
            = reinterpret_cast<CharacterContact *> (contactVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& result = pContact->mLinearVelocity;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterContact
 * Method:    getMotionTypeB
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_CharacterContact_getMotionTypeB
  (JNIEnv *, jclass, jlong contactVa) {
    const CharacterContact * const pContact
            = reinterpret_cast<CharacterContact *> (contactVa);
    const EMotionType result = pContact->mMotionTypeB;
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterContact
 * Method:    getPosition
 * Signature: (JLjava/nio/DoubleBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterContact_getPosition
  (JNIEnv *pEnv, jclass, jlong contactVa, jobject storeDoubles) {
    const CharacterContact * const pContact
            = reinterpret_cast<CharacterContact *> (contactVa);
    DIRECT_DOUBLE_BUFFER(pEnv, storeDoubles, pDoubles, capacityDoubles);
    JPH_ASSERT(capacityDoubles >= 3);
    const RVec3& result = pContact->mPosition;
    pDoubles[0] = result.GetX();
    pDoubles[1] = result.GetY();
    pDoubles[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterContact
 * Method:    getSurfaceNormal
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterContact_getSurfaceNormal
  (JNIEnv *pEnv, jclass, jlong contactVa, jobject storeFloats) {
    const CharacterContact * const pContact
            = reinterpret_cast<CharacterContact *> (contactVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& result = pContact->mSurfaceNormal;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterContact
 * Method:    getUserData
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CharacterContact_getUserData
  (JNIEnv *, jclass, jlong contactVa) {
    const CharacterContact * const pContact
            = reinterpret_cast<CharacterContact *> (contactVa);
    const uint64 result = pContact->mUserData;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterContact
 * Method:    getWasDiscarded
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_CharacterContact_getWasDiscarded
  (JNIEnv *, jclass, jlong contactVa) {
    const CharacterContact * const pContact
            = reinterpret_cast<CharacterContact *> (contactVa);
    const bool result = pContact->mWasDiscarded;
    return result;
}