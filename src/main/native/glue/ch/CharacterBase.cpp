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
#include "Jolt/Physics/Character/CharacterBase.h"
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
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_CharacterBase_getGroundBodyId
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterBase * const pCharacter
            = reinterpret_cast<CharacterBase *> (characterVa);
    const BodyID result = pCharacter->GetGroundBodyID();
    return result.GetIndexAndSequenceNumber();
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBase
 * Method:    getGroundMaterial
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CharacterBase_getGroundMaterial
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterBase * const pCharacter
            = reinterpret_cast<CharacterBase *> (characterVa);
    const PhysicsMaterial * const pResult = pCharacter->GetGroundMaterial();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBase
 * Method:    getGroundNormal
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterBase_getGroundNormal
  (JNIEnv *pEnv, jclass, jlong characterVa, jobject storeFloats) {
    const CharacterBase * const pCharacter
            = reinterpret_cast<CharacterBase *> (characterVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3 result = pCharacter->GetGroundNormal();
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBase
 * Method:    getGroundPosition
 * Signature: (JLjava/nio/DoubleBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterBase_getGroundPosition
  (JNIEnv *pEnv, jclass, jlong characterVa, jobject storeDoubles) {
    const CharacterBase * const pCharacter
            = reinterpret_cast<CharacterBase *> (characterVa);
    DIRECT_DOUBLE_BUFFER(pEnv, storeDoubles, pDoubles, capacityDoubles);
    JPH_ASSERT(capacityDoubles >= 3);
    const RVec3 result = pCharacter->GetGroundPosition();
    pDoubles[0] = result.GetX();
    pDoubles[1] = result.GetY();
    pDoubles[2] = result.GetZ();
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
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_CharacterBase_getGroundSubShapeId
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterBase * const pCharacter
            = reinterpret_cast<CharacterBase *> (characterVa);
    const SubShapeID result = pCharacter->GetGroundSubShapeID();
    return result.GetValue();
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
 * Method:    getGroundVelocity
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterBase_getGroundVelocity
  (JNIEnv *pEnv, jclass, jlong characterVa, jobject storeFloats) {
    const CharacterBase * const pCharacter
            = reinterpret_cast<CharacterBase *> (characterVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3 result = pCharacter->GetGroundVelocity();
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBase
 * Method:    getRefCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_CharacterBase_getRefCount
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterBase * const pCharacter
            = reinterpret_cast<CharacterBase *> (characterVa);
    const uint32 result = pCharacter->GetRefCount();
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
 * Method:    getShapeUpdate
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterBase_getShapeUpdate
  (JNIEnv *, jclass, jlong characterVa, jlong refVa) {
    const CharacterBase * const pCharacter
            = reinterpret_cast<CharacterBase *> (characterVa);
    RefConst<Shape> * const pRef = reinterpret_cast<RefConst<Shape> *> (refVa);
    (*pRef) = pCharacter->GetShape();
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBase
 * Method:    getUp
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterBase_getUp
  (JNIEnv *pEnv, jclass, jlong characterVa, jobject storeFloats) {
    const CharacterBase * const pCharacter
            = reinterpret_cast<CharacterBase *> (characterVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3 result = pCharacter->GetUp();
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
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
 * Method:    restoreState
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterBase_restoreState
  (JNIEnv *, jclass, jlong characterVa, jlong recorderVa) {
    CharacterBase * const pCharacter
            = reinterpret_cast<CharacterBase *> (characterVa);
    StateRecorder * const pRecorder
            = reinterpret_cast<StateRecorder *> (recorderVa);
    pCharacter->RestoreState(*pRecorder);
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBase
 * Method:    saveState
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterBase_saveState
  (JNIEnv *, jclass, jlong characterVa, jlong recorderVa) {
    const CharacterBase * const pCharacter
            = reinterpret_cast<CharacterBase *> (characterVa);
    StateRecorder * const pRecorder
            = reinterpret_cast<StateRecorder *> (recorderVa);
    pCharacter->SaveState(*pRecorder);
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBase
 * Method:    setEmbedded
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterBase_setEmbedded
  (JNIEnv *, jclass, jlong characterVa) {
    CharacterBase * const pCharacter
            = reinterpret_cast<CharacterBase *> (characterVa);
    pCharacter->SetEmbedded();
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