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
#include <Jolt/Physics/Character/CharacterVirtual.h>
#include "auto/com_github_stephengold_joltjni_Contact.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_Contact
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Contact_free
  (JNIEnv *, jclass, jlong contactVa) {
    CharacterVirtual::Contact * const pContact
            = reinterpret_cast<CharacterVirtual::Contact *> (contactVa);
    TRACE_DELETE("Contact", pContact)
    delete pContact;
}

/*
 * Class:     com_github_stephengold_joltjni_Contact
 * Method:    getBodyB
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Contact_getBodyB
  (JNIEnv *, jclass, jlong contactVa) {
    const CharacterVirtual::Contact * const pContact
            = reinterpret_cast<CharacterVirtual::Contact *> (contactVa);
    const BodyID id = pContact->mBodyB;
    BodyID * const pResult = new BodyID(id);
    TRACE_NEW("BodyID", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Contact
 * Method:    getCanPushCharacter
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_Contact_getCanPushCharacter
  (JNIEnv *, jclass, jlong contactVa) {
    const CharacterVirtual::Contact * const pContact
            = reinterpret_cast<CharacterVirtual::Contact *> (contactVa);
    const bool result = pContact->mCanPushCharacter;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Contact
 * Method:    getCharacterB
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Contact_getCharacterB
  (JNIEnv *, jclass, jlong contactVa) {
    const CharacterVirtual::Contact * const pContact
            = reinterpret_cast<CharacterVirtual::Contact *> (contactVa);
    CharacterVirtual * const pResult = pContact->mCharacterB;
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Contact
 * Method:    getContactNormalX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Contact_getContactNormalX
  (JNIEnv *, jclass, jlong contactVa) {
    const CharacterVirtual::Contact * const pContact
            = reinterpret_cast<CharacterVirtual::Contact *> (contactVa);
    const float result = pContact->mContactNormal.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Contact
 * Method:    getContactNormalY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Contact_getContactNormalY
  (JNIEnv *, jclass, jlong contactVa) {
    const CharacterVirtual::Contact * const pContact
            = reinterpret_cast<CharacterVirtual::Contact *> (contactVa);
    const float result = pContact->mContactNormal.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Contact
 * Method:    getContactNormalZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Contact_getContactNormalZ
  (JNIEnv *, jclass, jlong contactVa) {
    const CharacterVirtual::Contact * const pContact
            = reinterpret_cast<CharacterVirtual::Contact *> (contactVa);
    const float result = pContact->mContactNormal.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Contact
 * Method:    getDistance
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Contact_getDistance
  (JNIEnv *, jclass, jlong contactVa) {
    const CharacterVirtual::Contact * const pContact
            = reinterpret_cast<CharacterVirtual::Contact *> (contactVa);
    const float result = pContact->mDistance;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Contact
 * Method:    getFraction
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Contact_getFraction
  (JNIEnv *, jclass, jlong contactVa) {
    const CharacterVirtual::Contact * const pContact
            = reinterpret_cast<CharacterVirtual::Contact *> (contactVa);
    const float result = pContact->mFraction;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Contact
 * Method:    getHadCollision
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_Contact_getHadCollision
  (JNIEnv *, jclass, jlong contactVa) {
    const CharacterVirtual::Contact * const pContact
            = reinterpret_cast<CharacterVirtual::Contact *> (contactVa);
    const bool result = pContact->mHadCollision;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Contact
 * Method:    getIsSensorB
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_Contact_getIsSensorB
  (JNIEnv *, jclass, jlong contactVa) {
    const CharacterVirtual::Contact * const pContact
            = reinterpret_cast<CharacterVirtual::Contact *> (contactVa);
    const bool result = pContact->mIsSensorB;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Contact
 * Method:    getLinearVelocityX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Contact_getLinearVelocityX
  (JNIEnv *, jclass, jlong contactVa) {
    const CharacterVirtual::Contact * const pContact
            = reinterpret_cast<CharacterVirtual::Contact *> (contactVa);
    const float result = pContact->mLinearVelocity.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Contact
 * Method:    getLinearVelocityY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Contact_getLinearVelocityY
  (JNIEnv *, jclass, jlong contactVa) {
    const CharacterVirtual::Contact * const pContact
            = reinterpret_cast<CharacterVirtual::Contact *> (contactVa);
    const float result = pContact->mLinearVelocity.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Contact
 * Method:    getLinearVelocityZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Contact_getLinearVelocityZ
  (JNIEnv *, jclass, jlong contactVa) {
    const CharacterVirtual::Contact * const pContact
            = reinterpret_cast<CharacterVirtual::Contact *> (contactVa);
    const float result = pContact->mLinearVelocity.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Contact
 * Method:    getMotionTypeB
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Contact_getMotionTypeB
  (JNIEnv *, jclass, jlong contactVa) {
    const CharacterVirtual::Contact * const pContact
            = reinterpret_cast<CharacterVirtual::Contact *> (contactVa);
    const EMotionType result = pContact->mMotionTypeB;
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_Contact
 * Method:    getPositionX
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_Contact_getPositionX
  (JNIEnv *, jclass, jlong contactVa) {
    const CharacterVirtual::Contact * const pContact
            = reinterpret_cast<CharacterVirtual::Contact *> (contactVa);
    const double result = pContact->mPosition.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Contact
 * Method:    getPositionY
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_Contact_getPositionY
  (JNIEnv *, jclass, jlong contactVa) {
    const CharacterVirtual::Contact * const pContact
            = reinterpret_cast<CharacterVirtual::Contact *> (contactVa);
    const double result = pContact->mPosition.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Contact
 * Method:    getPositionZ
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_Contact_getPositionZ
  (JNIEnv *, jclass, jlong contactVa) {
    const CharacterVirtual::Contact * const pContact
            = reinterpret_cast<CharacterVirtual::Contact *> (contactVa);
    const double result = pContact->mPosition.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Contact
 * Method:    getSubShapeIdB
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Contact_getSubShapeIdB
  (JNIEnv *, jclass, jlong contactVa) {
    const CharacterVirtual::Contact * const pContact
            = reinterpret_cast<CharacterVirtual::Contact *> (contactVa);
    SubShapeID * const pResult = new SubShapeID(pContact->mSubShapeIDB);
    TRACE_NEW("SubShapeID", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Contact
 * Method:    getSurfaceNormalX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Contact_getSurfaceNormalX
  (JNIEnv *, jclass, jlong contactVa) {
    const CharacterVirtual::Contact * const pContact
            = reinterpret_cast<CharacterVirtual::Contact *> (contactVa);
    const float result = pContact->mSurfaceNormal.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Contact
 * Method:    getSurfaceNormalY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Contact_getSurfaceNormalY
  (JNIEnv *, jclass, jlong contactVa) {
    const CharacterVirtual::Contact * const pContact
            = reinterpret_cast<CharacterVirtual::Contact *> (contactVa);
    const float result = pContact->mSurfaceNormal.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Contact
 * Method:    getSurfaceNormalZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Contact_getSurfaceNormalZ
  (JNIEnv *, jclass, jlong contactVa) {
    const CharacterVirtual::Contact * const pContact
            = reinterpret_cast<CharacterVirtual::Contact *> (contactVa);
    const float result = pContact->mSurfaceNormal.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Contact
 * Method:    getUserData
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Contact_getUserData
  (JNIEnv *, jclass, jlong contactVa) {
    const CharacterVirtual::Contact * const pContact
            = reinterpret_cast<CharacterVirtual::Contact *> (contactVa);
    const uint64 result = pContact->mUserData;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Contact
 * Method:    getWasDiscarded
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_Contact_getWasDiscarded
  (JNIEnv *, jclass, jlong contactVa) {
    const CharacterVirtual::Contact * const pContact
            = reinterpret_cast<CharacterVirtual::Contact *> (contactVa);
    const bool result = pContact->mWasDiscarded;
    return result;
}