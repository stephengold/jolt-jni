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
#include "Jolt/Physics/Constraints/SixDOFConstraint.h"
#include "auto/com_github_stephengold_joltjni_SixDofConstraint.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getLimitsMax
 * Signature: (JI)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getLimitsMax
  (JNIEnv *, jclass, jlong constraintVa, jint axisOrdinal) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const SixDOFConstraintSettings::EAxis axis
            = (SixDOFConstraintSettings::EAxis) axisOrdinal;
    const float result = pConstraint->GetLimitsMax(axis);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getLimitsMin
 * Signature: (JI)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getLimitsMin
  (JNIEnv *, jclass, jlong constraintVa, jint axisOrdinal) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const SixDOFConstraintSettings::EAxis axis
            = (SixDOFConstraintSettings::EAxis) axisOrdinal;
    const float result = pConstraint->GetLimitsMin(axis);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getLimitsSpringSettings
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getLimitsSpringSettings
  (JNIEnv *, jclass, jlong constraintVa, jint axisOrdinal) {
    SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const SixDOFConstraintSettings::EAxis axis
            = (SixDOFConstraintSettings::EAxis) axisOrdinal;
    const SpringSettings * const pResult
            = &pConstraint->GetLimitsSpringSettings(axis);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getMaxFriction
 * Signature: (JI)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getMaxFriction
  (JNIEnv *, jclass, jlong constraintVa, jint axisOrdinal) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const SixDOFConstraintSettings::EAxis axis
            = (SixDOFConstraintSettings::EAxis) axisOrdinal;
    const float result = pConstraint->GetLimitsMax(axis);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getMotorSettings
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getMotorSettings
  (JNIEnv *, jclass, jlong constraintVa, jint axisOrdinal) {
    SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const SixDOFConstraintSettings::EAxis axis
            = (SixDOFConstraintSettings::EAxis) axisOrdinal;
    MotorSettings * const pSettings = &pConstraint->GetMotorSettings(axis);
    return reinterpret_cast<jlong> (pSettings);
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getMotorState
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getMotorState
  (JNIEnv *, jclass, jlong constraintVa, jint axisOrdinal) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const SixDOFConstraintSettings::EAxis axis
            = (SixDOFConstraintSettings::EAxis) axisOrdinal;
    const EMotorState result = pConstraint->GetMotorState(axis);
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getRotationInConstraintSpace
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getRotationInConstraintSpace
  (JNIEnv *pEnv, jclass, jlong constraintVa, jobject storeFloats) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 4);
    const Quat result = pConstraint->GetRotationInConstraintSpace();
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
    pFloats[3] = result.GetW();
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getRotationLimitsMax
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getRotationLimitsMax
  (JNIEnv *pEnv, jclass, jlong constraintVa, jobject storeFloats) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3 result = pConstraint->GetRotationLimitsMax();
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getRotationLimitsMin
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getRotationLimitsMin
  (JNIEnv *pEnv, jclass, jlong constraintVa, jobject storeFloats) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3 result = pConstraint->GetRotationLimitsMin();
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getTargetAngularVelocityCs
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getTargetAngularVelocityCs
  (JNIEnv *pEnv, jclass, jlong constraintVa, jobject storeFloats) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3 result = pConstraint->GetTargetAngularVelocityCS();
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getTargetPositionCs
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getTargetPositionCs
  (JNIEnv *pEnv, jclass, jlong constraintVa, jobject storeFloats) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3 result = pConstraint->GetTargetPositionCS();
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getTargetOrientationCs
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getTargetOrientationCs
  (JNIEnv *pEnv, jclass, jlong constraintVa, jobject storeFloats) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 4);
    const Quat result = pConstraint->GetTargetOrientationCS();
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
    pFloats[3] = result.GetW();
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getTargetVelocityCs
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getTargetVelocityCs
  (JNIEnv *pEnv, jclass, jlong constraintVa, jobject storeFloats) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3 result = pConstraint->GetTargetVelocityCS();
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getTranslationLimitsMax
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getTranslationLimitsMax
  (JNIEnv *pEnv, jclass, jlong constraintVa, jobject storeFloats) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3 result = pConstraint->GetTranslationLimitsMax();
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getTranslationLimitsMin
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getTranslationLimitsMin
  (JNIEnv *pEnv, jclass, jlong constraintVa, jobject storeFloats) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3 result = pConstraint->GetTranslationLimitsMin();
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    isFixedAxis
 * Signature: (JI)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_isFixedAxis
  (JNIEnv *, jclass, jlong constraintVa, jint axisOrdinal) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const SixDOFConstraintSettings::EAxis axis
            = (SixDOFConstraintSettings::EAxis) axisOrdinal;
    const bool result = pConstraint->IsFixedAxis(axis);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    isFreeAxis
 * Signature: (JI)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_isFreeAxis
  (JNIEnv *, jclass, jlong constraintVa, jint axisOrdinal) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const SixDOFConstraintSettings::EAxis axis
            = (SixDOFConstraintSettings::EAxis) axisOrdinal;
    const bool result = pConstraint->IsFreeAxis(axis);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    setLimitsSpringSettings
 * Signature: (JIJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_setLimitsSpringSettings
  (JNIEnv *, jclass, jlong constraintVa, jint axisOrdinal, jlong springSettingsVa) {
    SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const SixDOFConstraintSettings::EAxis axis
            = (SixDOFConstraintSettings::EAxis) axisOrdinal;
    const SpringSettings * const pSpringSettings
            = reinterpret_cast<SpringSettings *> (springSettingsVa);
    pConstraint->SetLimitsSpringSettings(axis, *pSpringSettings);
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    setMaxFriction
 * Signature: (JIF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_setMaxFriction
  (JNIEnv *, jclass, jlong constraintVa, jint axisOrdinal, jfloat friction) {
    SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const SixDOFConstraintSettings::EAxis axis
            = (SixDOFConstraintSettings::EAxis) axisOrdinal;
    pConstraint->SetMaxFriction(axis, friction);
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    setMotorState
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_setMotorState
  (JNIEnv *, jclass, jlong constraintVa, jint axisOrdinal, jint stateOrdinal) {
    SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const SixDOFConstraintSettings::EAxis axis
            = (SixDOFConstraintSettings::EAxis) axisOrdinal;
    const EMotorState motorState = (EMotorState) stateOrdinal;
    pConstraint->SetMotorState(axis, motorState);
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    setRotationLimits
 * Signature: (JFFFFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_setRotationLimits
  (JNIEnv *, jclass, jlong constraintVa, jfloat minX, jfloat minY, jfloat minZ,
  jfloat maxX, jfloat maxY, jfloat maxZ) {
    SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const Vec3 min(minX, minY, minZ);
    const Vec3 max(maxX, maxY, maxZ);
    pConstraint->SetRotationLimits(min, max);
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    setTargetAngularVelocityCs
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_setTargetAngularVelocityCs
  (JNIEnv *, jclass, jlong constraintVa, jfloat wx, jfloat wy, jfloat wz) {
    SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const Vec3 omega(wx, wy, wz);
    pConstraint->SetTargetAngularVelocityCS(omega);
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    setTargetPositionCs
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_setTargetPositionCs
  (JNIEnv *, jclass, jlong constraintVa, jfloat offsetX, jfloat offsetY, jfloat offsetZ) {
    SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const Vec3 offset(offsetX, offsetY, offsetZ);
    pConstraint->SetTargetPositionCS(offset);
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    setTargetOrientationBs
 * Signature: (JFFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_setTargetOrientationBs
  (JNIEnv *, jclass, jlong constraintVa, jfloat qx, jfloat qy, jfloat qz, jfloat qw) {
    SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const Quat quat(qx, qy, qz, qw);
    pConstraint->SetTargetOrientationBS(quat);
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    setTargetOrientationCs
 * Signature: (JFFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_setTargetOrientationCs
  (JNIEnv *, jclass, jlong constraintVa, jfloat qx, jfloat qy, jfloat qz, jfloat qw) {
    SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const Quat quat(qx, qy, qz, qw);
    pConstraint->SetTargetOrientationCS(quat);
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    setTargetVelocityCs
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_setTargetVelocityCs
  (JNIEnv *, jclass, jlong constraintVa, jfloat vx, jfloat vy, jfloat vz) {
    SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const Vec3 velocity(vx, vy, vz);
    pConstraint->SetTargetVelocityCS(velocity);
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    setTranslationLimits
 * Signature: (JFFFFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_setTranslationLimits
  (JNIEnv *, jclass, jlong constraintVa, jfloat minX, jfloat minY, jfloat minZ,
  jfloat maxX, jfloat maxY, jfloat maxZ) {
    SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const Vec3 min(minX, minY, minZ);
    const Vec3 max(maxX, maxY, maxZ);
    pConstraint->SetTranslationLimits(min, max);
}