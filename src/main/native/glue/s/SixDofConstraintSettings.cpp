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
#include "Jolt/Jolt.h"
#include "Jolt/Physics/Constraints/SixDOFConstraint.h"
#include "auto/com_github_stephengold_joltjni_SixDofConstraintSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    createSixDofConstraintSettings
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_createSixDofConstraintSettings
  (JNIEnv *, jclass) {
    SixDOFConstraintSettings * const pResult = new SixDOFConstraintSettings();
    TRACE_NEW("SixDOFConstraintSettings", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    getAxisX1X
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_getAxisX1X
  (JNIEnv *, jclass, jlong settingsVa) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const float result = pSettings->mAxisX1.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    getAxisX1Y
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_getAxisX1Y
  (JNIEnv *, jclass, jlong settingsVa) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const float result = pSettings->mAxisX1.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    getAxisX1Z
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_getAxisX1Z
  (JNIEnv *, jclass, jlong settingsVa) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const float result = pSettings->mAxisX1.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    getAxisX2X
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_getAxisX2X
  (JNIEnv *, jclass, jlong settingsVa) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const float result = pSettings->mAxisX2.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    getAxisX2Y
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_getAxisX2Y
  (JNIEnv *, jclass, jlong settingsVa) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const float result = pSettings->mAxisX2.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    getAxisX2Z
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_getAxisX2Z
  (JNIEnv *, jclass, jlong settingsVa) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const float result = pSettings->mAxisX2.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    getAxisY1X
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_getAxisY1X
  (JNIEnv *, jclass, jlong settingsVa) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const float result = pSettings->mAxisY1.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    getAxisY1Y
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_getAxisY1Y
  (JNIEnv *, jclass, jlong settingsVa) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const float result = pSettings->mAxisY1.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    getAxisY1Z
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_getAxisY1Z
  (JNIEnv *, jclass, jlong settingsVa) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const float result = pSettings->mAxisY1.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    getAxisY2X
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_getAxisY2X
  (JNIEnv *, jclass, jlong settingsVa) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const float result = pSettings->mAxisY2.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    getAxisY2Y
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_getAxisY2Y
  (JNIEnv *, jclass, jlong settingsVa) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const float result = pSettings->mAxisY2.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    getAxisY2Z
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_getAxisY2Z
  (JNIEnv *, jclass, jlong settingsVa) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const float result = pSettings->mAxisY2.GetZ();
    return result;
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
  (JNIEnv *, jclass, jlong settingsVa, jint dof) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const MotorSettings * const pResult = &pSettings->mMotorSettings[dof];
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    getPosition1X
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_getPosition1X
  (JNIEnv *, jclass, jlong settingsVa) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const Real result = pSettings->mPosition1.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    getPosition1Y
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_getPosition1Y
  (JNIEnv *, jclass, jlong settingsVa) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const Real result = pSettings->mPosition1.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    getPosition1Z
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_getPosition1Z
  (JNIEnv *, jclass, jlong settingsVa) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const Real result = pSettings->mPosition1.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    getPosition2X
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_getPosition2X
  (JNIEnv *, jclass, jlong settingsVa) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const Real result = pSettings->mPosition2.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    getPosition2Y
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_getPosition2Y
  (JNIEnv *, jclass, jlong settingsVa) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const Real result = pSettings->mPosition2.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    getPosition2Z
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_getPosition2Z
  (JNIEnv *, jclass, jlong settingsVa) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const Real result = pSettings->mPosition2.GetZ();
    return result;
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
  (JNIEnv *, jclass, jlong settingsVa, jint ordinal) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const SixDOFConstraintSettings::EAxis axis
            = (SixDOFConstraintSettings::EAxis) ordinal;
    const bool result = pSettings->IsFixedAxis(axis);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    isFreeAxis
 * Signature: (JI)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_isFreeAxis
  (JNIEnv *, jclass, jlong settingsVa, jint ordinal) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const SixDOFConstraintSettings::EAxis axis
            = (SixDOFConstraintSettings::EAxis) ordinal;
    const bool result = pSettings->IsFreeAxis(axis);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    makeFixedAxis
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_makeFixedAxis
  (JNIEnv *, jclass, jlong settingsVa, jint ordinal) {
    SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const SixDOFConstraintSettings::EAxis axis
            = (SixDOFConstraintSettings::EAxis) ordinal;
    pSettings->MakeFixedAxis(axis);
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    makeFreeAxis
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_makeFreeAxis
  (JNIEnv *, jclass, jlong settingsVa, jint ordinal) {
    SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const SixDOFConstraintSettings::EAxis axis
            = (SixDOFConstraintSettings::EAxis) ordinal;
    pSettings->MakeFreeAxis(axis);
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
  (JNIEnv *, jclass, jlong settingsVa, jint ordinal, jfloat min, jfloat max) {
    SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const SixDOFConstraintSettings::EAxis axis
            = (SixDOFConstraintSettings::EAxis) ordinal;
    pSettings->SetLimitedAxis(axis, min, max);
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    setLimitMax
 * Signature: (JIF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_setLimitMax
  (JNIEnv *, jclass, jlong settingsVa, jint dof, jfloat max) {
    SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    pSettings->mLimitMax[dof] = max;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    setLimitMin
 * Signature: (JIF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_setLimitMin
  (JNIEnv *, jclass, jlong settingsVa, jint dof, jfloat min) {
    SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    pSettings->mLimitMin[dof] = min;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    setMaxFriction
 * Signature: (JIF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_setMaxFriction
  (JNIEnv *, jclass, jlong settingsVa, jint dof, jfloat friction) {
    SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    pSettings->mMaxFriction[dof] = friction;
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