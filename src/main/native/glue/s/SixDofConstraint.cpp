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
#include <Jolt/Physics/Constraints/SixDOFConstraint.h>
#include "auto/com_github_stephengold_joltjni_SixDofConstraint.h"

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
 * Method:    getRotationInConstraintSpaceW
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getRotationInConstraintSpaceW
  (JNIEnv *, jclass, jlong constraintVa) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const Quat quat = pConstraint->GetRotationInConstraintSpace();
    const float result = quat.GetW();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getRotationInConstraintSpaceX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getRotationInConstraintSpaceX
  (JNIEnv *, jclass, jlong constraintVa) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const Quat quat = pConstraint->GetRotationInConstraintSpace();
    const float result = quat.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getRotationInConstraintSpaceY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getRotationInConstraintSpaceY
  (JNIEnv *, jclass, jlong constraintVa) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const Quat quat = pConstraint->GetRotationInConstraintSpace();
    const float result = quat.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getRotationInConstraintSpaceZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getRotationInConstraintSpaceZ
  (JNIEnv *, jclass, jlong constraintVa) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const Quat quat = pConstraint->GetRotationInConstraintSpace();
    const float result = quat.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getRotationLimitsMaxX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getRotationLimitsMaxX
  (JNIEnv *, jclass, jlong constraintVa) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const Vec3 vec3 = pConstraint->GetRotationLimitsMax();
    const float result = vec3.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getRotationLimitsMaxY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getRotationLimitsMaxY
  (JNIEnv *, jclass, jlong constraintVa) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const Vec3 vec3 = pConstraint->GetRotationLimitsMax();
    const float result = vec3.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getRotationLimitsMaxZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getRotationLimitsMaxZ
  (JNIEnv *, jclass, jlong constraintVa) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const Vec3 vec3 = pConstraint->GetRotationLimitsMax();
    const float result = vec3.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getRotationLimitsMinX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getRotationLimitsMinX
  (JNIEnv *, jclass, jlong constraintVa) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const Vec3 vec3 = pConstraint->GetRotationLimitsMin();
    const float result = vec3.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getRotationLimitsMinY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getRotationLimitsMinY
  (JNIEnv *, jclass, jlong constraintVa) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const Vec3 vec3 = pConstraint->GetRotationLimitsMin();
    const float result = vec3.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getRotationLimitsMinZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getRotationLimitsMinZ
  (JNIEnv *, jclass, jlong constraintVa) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const Vec3 vec3 = pConstraint->GetRotationLimitsMin();
    const float result = vec3.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getTargetAngularVelocityCsX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getTargetAngularVelocityCsX
  (JNIEnv *, jclass, jlong constraintVa) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const Vec3 vec3 = pConstraint->GetTargetAngularVelocityCS();
    const float result = vec3.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getTargetAngularVelocityCsY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getTargetAngularVelocityCsY
  (JNIEnv *, jclass, jlong constraintVa) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const Vec3 vec3 = pConstraint->GetTargetAngularVelocityCS();
    const float result = vec3.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getTargetAngularVelocityCsZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getTargetAngularVelocityCsZ
  (JNIEnv *, jclass, jlong constraintVa) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const Vec3 vec3 = pConstraint->GetTargetAngularVelocityCS();
    const float result = vec3.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getTargetPositionCsX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getTargetPositionCsX
  (JNIEnv *, jclass, jlong constraintVa) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const Vec3 vec3 = pConstraint->GetTargetPositionCS();
    const float result = vec3.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getTargetPositionCsY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getTargetPositionCsY
  (JNIEnv *, jclass, jlong constraintVa) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const Vec3 vec3 = pConstraint->GetTargetPositionCS();
    const float result = vec3.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getTargetPositionCsZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getTargetPositionCsZ
  (JNIEnv *, jclass, jlong constraintVa) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const Vec3 vec3 = pConstraint->GetTargetPositionCS();
    const float result = vec3.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getTargetOrientationCsW
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getTargetOrientationCsW
  (JNIEnv *, jclass, jlong constraintVa) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const Quat quat = pConstraint->GetTargetOrientationCS();
    const float result = quat.GetW();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getTargetOrientationCsX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getTargetOrientationCsX
  (JNIEnv *, jclass, jlong constraintVa) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const Quat quat = pConstraint->GetTargetOrientationCS();
    const float result = quat.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getTargetOrientationCsY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getTargetOrientationCsY
  (JNIEnv *, jclass, jlong constraintVa) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const Quat quat = pConstraint->GetTargetOrientationCS();
    const float result = quat.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getTargetOrientationCsZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getTargetOrientationCsZ
  (JNIEnv *, jclass, jlong constraintVa) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const Quat quat = pConstraint->GetTargetOrientationCS();
    const float result = quat.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getTargetVelocityCsX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getTargetVelocityCsX
  (JNIEnv *, jclass, jlong constraintVa) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const Vec3 vec3 = pConstraint->GetTargetVelocityCS();
    const float result = vec3.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getTargetVelocityCsY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getTargetVelocityCsY
  (JNIEnv *, jclass, jlong constraintVa) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const Vec3 vec3 = pConstraint->GetTargetVelocityCS();
    const float result = vec3.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getTargetVelocityCsZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getTargetVelocityCsZ
  (JNIEnv *, jclass, jlong constraintVa) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const Vec3 vec3 = pConstraint->GetTargetVelocityCS();
    const float result = vec3.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getTranslationLimitsMaxX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getTranslationLimitsMaxX
  (JNIEnv *, jclass, jlong constraintVa) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const Vec3 vec3 = pConstraint->GetTranslationLimitsMax();
    const float result = vec3.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getTranslationLimitsMaxY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getTranslationLimitsMaxY
  (JNIEnv *, jclass, jlong constraintVa) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const Vec3 vec3 = pConstraint->GetTranslationLimitsMax();
    const float result = vec3.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getTranslationLimitsMaxZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getTranslationLimitsMaxZ
  (JNIEnv *, jclass, jlong constraintVa) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const Vec3 vec3 = pConstraint->GetTranslationLimitsMax();
    const float result = vec3.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getTranslationLimitsMinX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getTranslationLimitsMinX
  (JNIEnv *, jclass, jlong constraintVa) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const Vec3 vec3 = pConstraint->GetTranslationLimitsMin();
    const float result = vec3.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getTranslationLimitsMinY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getTranslationLimitsMinY
  (JNIEnv *, jclass, jlong constraintVa) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const Vec3 vec3 = pConstraint->GetTranslationLimitsMin();
    const float result = vec3.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraint
 * Method:    getTranslationLimitsMinZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraint_getTranslationLimitsMinZ
  (JNIEnv *, jclass, jlong constraintVa) {
    const SixDOFConstraint * const pConstraint
            = reinterpret_cast<SixDOFConstraint *> (constraintVa);
    const Vec3 vec3 = pConstraint->GetTranslationLimitsMin();
    const float result = vec3.GetZ();
    return result;
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
            =  reinterpret_cast<SpringSettings *> (springSettingsVa);
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
