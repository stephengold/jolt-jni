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
#include <Jolt/Physics/Character/CharacterBase.h>
#include "auto/com_github_stephengold_joltjni_CharacterBase.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_CharacterBase
 * Method:    getCosMaxSlopeAngle
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterBase_getCosMaxSlopeAngle
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterBase * const pCharacter
            = reinterpret_cast<CharacterBase *> (characterVa);
    const float result = pCharacter->GetCosMaxSlopeAngle();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBase
 * Method:    getGroundBodyId
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CharacterBase_getGroundBodyId
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterBase * const pCharacter
            = reinterpret_cast<CharacterBase *> (characterVa);
    const BodyID id = pCharacter->GetGroundBodyID();
    BodyID * const pResult = new BodyID(id);
    TRACE_NEW("BodyID", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBase
 * Method:    getGroundNormalX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterBase_getGroundNormalX
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterBase * const pCharacter
            = reinterpret_cast<CharacterBase *> (characterVa);
    const float result = pCharacter->GetGroundNormal().GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBase
 * Method:    getGroundNormalY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterBase_getGroundNormalY
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterBase * const pCharacter
            = reinterpret_cast<CharacterBase *> (characterVa);
    const float result = pCharacter->GetGroundNormal().GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBase
 * Method:    getGroundNormalZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterBase_getGroundNormalZ
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterBase * const pCharacter
            = reinterpret_cast<CharacterBase *> (characterVa);
    const float result = pCharacter->GetGroundNormal().GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBase
 * Method:    getGroundPositionX
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_CharacterBase_getGroundPositionX
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterBase * const pCharacter
            = reinterpret_cast<CharacterBase *> (characterVa);
    const Real result = pCharacter->GetGroundPosition().GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBase
 * Method:    getGroundPositionY
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_CharacterBase_getGroundPositionY
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterBase * const pCharacter
            = reinterpret_cast<CharacterBase *> (characterVa);
    const Real result = pCharacter->GetGroundPosition().GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBase
 * Method:    getGroundPositionZ
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_CharacterBase_getGroundPositionZ
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterBase * const pCharacter
            = reinterpret_cast<CharacterBase *> (characterVa);
    const Real result = pCharacter->GetGroundPosition().GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBase
 * Method:    getGroundState
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_CharacterBase_getGroundState
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterBase * const pCharacter
            = reinterpret_cast<CharacterBase *> (characterVa);
    const CharacterBase::EGroundState result = pCharacter->GetGroundState();
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBase
 * Method:    getGroundSubShapeId
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CharacterBase_getGroundSubShapeId
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterBase * const pCharacter
            = reinterpret_cast<CharacterBase *> (characterVa);
    const SubShapeID id = pCharacter->GetGroundSubShapeID();
    SubShapeID * const pResult = new SubShapeID(id);
    TRACE_NEW("SubShapeID", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBase
 * Method:    getGroundUserData
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CharacterBase_getGroundUserData
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterBase * const pCharacter
            = reinterpret_cast<CharacterBase *> (characterVa);
    const uint64 result = pCharacter->GetGroundUserData();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBase
 * Method:    getGroundVelocityX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterBase_getGroundVelocityX
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterBase * const pCharacter
            = reinterpret_cast<CharacterBase *> (characterVa);
    const float result = pCharacter->GetGroundVelocity().GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBase
 * Method:    getGroundVelocityY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterBase_getGroundVelocityY
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterBase * const pCharacter
            = reinterpret_cast<CharacterBase *> (characterVa);
    const float result = pCharacter->GetGroundVelocity().GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBase
 * Method:    getGroundVelocityZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterBase_getGroundVelocityZ
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterBase * const pCharacter
            = reinterpret_cast<CharacterBase *> (characterVa);
    const float result = pCharacter->GetGroundVelocity().GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBase
 * Method:    getShape
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CharacterBase_getShape
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterBase * const pCharacter
            = reinterpret_cast<CharacterBase *> (characterVa);
    const Shape * const pResult = pCharacter->GetShape();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBase
 * Method:    getUpX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterBase_getUpX
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterBase * const pCharacter
            = reinterpret_cast<CharacterBase *> (characterVa);
    const float result = pCharacter->GetUp().GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBase
 * Method:    getUpY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterBase_getUpY
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterBase * const pCharacter
            = reinterpret_cast<CharacterBase *> (characterVa);
    const float result = pCharacter->GetUp().GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBase
 * Method:    getUpZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterBase_getUpZ
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterBase * const pCharacter
            = reinterpret_cast<CharacterBase *> (characterVa);
    const float result = pCharacter->GetUp().GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBase
 * Method:    isSlopeTooSteep
 * Signature: (JFFF)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_CharacterBase_isSlopeTooSteep
  (JNIEnv *, jclass, jlong characterVa, jfloat nx, jfloat ny, jfloat nz) {
    const CharacterBase * const pCharacter
            = reinterpret_cast<CharacterBase *> (characterVa);
    const Vec3 normal(nx, ny, nz);
    const bool result = pCharacter->IsSlopeTooSteep(normal);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBase
 * Method:    isSupported
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_CharacterBase_isSupported
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterBase * const pCharacter
            = reinterpret_cast<CharacterBase *> (characterVa);
    const bool result = pCharacter->IsSupported();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBase
 * Method:    setMaxSlopeAngle
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterBase_setMaxSlopeAngle
  (JNIEnv *, jclass, jlong characterVa, jfloat angle) {
    CharacterBase * const pCharacter
            = reinterpret_cast<CharacterBase *> (characterVa);
    pCharacter->SetMaxSlopeAngle(angle);
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBase
 * Method:    setUp
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterBase_setUp
  (JNIEnv *, jclass, jlong characterVa, jfloat x, jfloat y, jfloat z) {
    CharacterBase * const pCharacter
            = reinterpret_cast<CharacterBase *> (characterVa);
    const Vec3 up(x, y, z);
    pCharacter->SetUp(up);
}