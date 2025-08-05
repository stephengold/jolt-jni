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
#include "auto/com_github_stephengold_joltjni_SixDofConstraintSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_createCopy
  BODYOF_CREATE_COPY(SixDOFConstraintSettings)

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_createDefault
  BODYOF_CREATE_DEFAULT(SixDOFConstraintSettings)

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    getAxisX1
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_getAxisX1
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeFloats) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& result = pSettings->mAxisX1;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    getAxisX2
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_getAxisX2
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeFloats) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& result = pSettings->mAxisX2;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    getAxisY1
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_getAxisY1
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeFloats) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& result = pSettings->mAxisY1;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    getAxisY2
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_getAxisY2
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeFloats) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& result = pSettings->mAxisY2;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    getLimitMax
 * Signature: (JI)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_getLimitMax
  (JNIEnv *, jclass, jlong settingsVa, jint dof) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const float result = pSettings->mLimitMax[dof];
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    getLimitMin
 * Signature: (JI)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_getLimitMin
  (JNIEnv *, jclass, jlong settingsVa, jint dof) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const float result = pSettings->mLimitMin[dof];
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    getLimitsSpringSettings
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_getLimitsSpringSettings
  (JNIEnv *, jclass, jlong settingsVa, jint dof) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const SpringSettings * const pResult
            = &pSettings->mLimitsSpringSettings[dof];
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    getMaxFriction
 * Signature: (JI)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_getMaxFriction
  (JNIEnv *, jclass, jlong settingsVa, jint dof) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const float result = pSettings->mMaxFriction[dof];
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    getMotorSettings
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_getMotorSettings
  (JNIEnv *, jclass, jlong settingsVa, jint dofIndex) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const MotorSettings * const pResult = &pSettings->mMotorSettings[dofIndex];
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    getPosition1
 * Signature: (JLjava/nio/DoubleBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_getPosition1
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeDoubles) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    DIRECT_DOUBLE_BUFFER(pEnv, storeDoubles, pDoubles, capacityDoubles);
    JPH_ASSERT(capacityDoubles >= 3);
    const RVec3& result = pSettings->mPosition1;
    pDoubles[0] = result.GetX();
    pDoubles[1] = result.GetY();
    pDoubles[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    getPosition2
 * Signature: (JLjava/nio/DoubleBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_getPosition2
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeDoubles) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    DIRECT_DOUBLE_BUFFER(pEnv, storeDoubles, pDoubles, capacityDoubles);
    JPH_ASSERT(capacityDoubles >= 3);
    const RVec3& result = pSettings->mPosition2;
    pDoubles[0] = result.GetX();
    pDoubles[1] = result.GetY();
    pDoubles[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    getSpace
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_getSpace
  (JNIEnv *, jclass, jlong settingsVa) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const EConstraintSpace result = pSettings->mSpace;
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    getSwingType
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_getSwingType
  (JNIEnv *, jclass, jlong settingsVa) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const ESwingType result = pSettings->mSwingType;
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    isFixedAxis
 * Signature: (JI)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_isFixedAxis
  (JNIEnv *, jclass, jlong settingsVa, jint dofIndex) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const SixDOFConstraintSettings::EAxis dof
            = (SixDOFConstraintSettings::EAxis) dofIndex;
    const bool result = pSettings->IsFixedAxis(dof);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    isFreeAxis
 * Signature: (JI)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_isFreeAxis
  (JNIEnv *, jclass, jlong settingsVa, jint dofIndex) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const SixDOFConstraintSettings::EAxis dof
            = (SixDOFConstraintSettings::EAxis) dofIndex;
    const bool result = pSettings->IsFreeAxis(dof);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    makeFixedAxis
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_makeFixedAxis
  (JNIEnv *, jclass, jlong settingsVa, jint dofIndex) {
    SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const SixDOFConstraintSettings::EAxis dof
            = (SixDOFConstraintSettings::EAxis) dofIndex;
    pSettings->MakeFixedAxis(dof);
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    makeFreeAxis
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_makeFreeAxis
  (JNIEnv *, jclass, jlong settingsVa, jint dofIndex) {
    SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const SixDOFConstraintSettings::EAxis dof
            = (SixDOFConstraintSettings::EAxis) dofIndex;
    pSettings->MakeFreeAxis(dof);
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    setAxisX1
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_setAxisX1
  (JNIEnv *, jclass, jlong settingsVa, jfloat x, jfloat y, jfloat z) {
    SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const Vec3 vec3(x, y, z);
    pSettings->mAxisX1 = vec3;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    setAxisX2
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_setAxisX2
  (JNIEnv *, jclass, jlong settingsVa, jfloat x, jfloat y, jfloat z) {
    SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const Vec3 vec3(x, y, z);
    pSettings->mAxisX2 = vec3;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    setAxisY1
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_setAxisY1
  (JNIEnv *, jclass, jlong settingsVa, jfloat x, jfloat y, jfloat z) {
    SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const Vec3 vec3(x, y, z);
    pSettings->mAxisY1 = vec3;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    setAxisY2
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_setAxisY2
  (JNIEnv *, jclass, jlong settingsVa, jfloat x, jfloat y, jfloat z) {
    SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const Vec3 vec3(x, y, z);
    pSettings->mAxisY2 = vec3;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    setLimitedAxis
 * Signature: (JIFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_setLimitedAxis
  (JNIEnv *, jclass, jlong settingsVa, jint dofIndex, jfloat min, jfloat max) {
    SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const SixDOFConstraintSettings::EAxis dof
            = (SixDOFConstraintSettings::EAxis) dofIndex;
    pSettings->SetLimitedAxis(dof, min, max);
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    setLimitMax
 * Signature: (JIF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_setLimitMax
  (JNIEnv *, jclass, jlong settingsVa, jint dofIndex, jfloat max) {
    SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    pSettings->mLimitMax[dofIndex] = max;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    setLimitMin
 * Signature: (JIF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_setLimitMin
  (JNIEnv *, jclass, jlong settingsVa, jint dofIndex, jfloat min) {
    SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    pSettings->mLimitMin[dofIndex] = min;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    setMaxFriction
 * Signature: (JIF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_setMaxFriction
  (JNIEnv *, jclass, jlong settingsVa, jint dofIndex, jfloat friction) {
    SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    pSettings->mMaxFriction[dofIndex] = friction;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    setMotorSettings
 * Signature: (JIJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_setMotorSettings
  (JNIEnv *, jclass, jlong constraintSettingsVa, jint dofIndex, jlong motorSettingsVa) {
    SixDOFConstraintSettings * const pConstraintSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (constraintSettingsVa);
    const MotorSettings * const pMotorSettings
            = reinterpret_cast<MotorSettings *> (motorSettingsVa);
    pConstraintSettings->mMotorSettings[dofIndex] = *pMotorSettings;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    setPosition1
 * Signature: (JDDD)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_setPosition1
  (JNIEnv *, jclass, jlong settingsVa, jdouble x, jdouble y, jdouble z) {
    SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const RVec3 location(x, y, z);
    pSettings->mPosition1 = location;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    setPosition2
 * Signature: (JDDD)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_setPosition2
  (JNIEnv *, jclass, jlong settingsVa, jdouble x, jdouble y, jdouble z) {
    SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const RVec3 location(x, y, z);
    pSettings->mPosition2 = location;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    setSpace
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_setSpace
  (JNIEnv *, jclass, jlong settingsVa, jint ordinal) {
    SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    pSettings->mSpace = (EConstraintSpace) ordinal;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    setSwingType
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_setSwingType
  (JNIEnv *, jclass, jlong settingsVa, jint ordinal) {
    SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    pSettings->mSwingType = (ESwingType) ordinal;
}