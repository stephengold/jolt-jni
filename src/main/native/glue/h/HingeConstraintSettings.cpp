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
#include "Jolt/Physics/Constraints/HingeConstraint.h"
#include "auto/com_github_stephengold_joltjni_HingeConstraintSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraintSettings
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_HingeConstraintSettings_createCopy
  BODYOF_CREATE_COPY(HingeConstraintSettings)

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraintSettings
 * Method:    createHingeConstraintSettings
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_HingeConstraintSettings_createHingeConstraintSettings
  (JNIEnv *, jclass) {
    HingeConstraintSettings * const pResult = new HingeConstraintSettings();
    TRACE_NEW("HingeConstraintSettings", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraintSettings
 * Method:    getHingeAxis1X
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HingeConstraintSettings_getHingeAxis1X
  (JNIEnv *, jclass, jlong settingsVa) {
    const HingeConstraintSettings * const pSettings
            = reinterpret_cast<HingeConstraintSettings *> (settingsVa);
    const float result = pSettings->mHingeAxis1.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraintSettings
 * Method:    getHingeAxis1Y
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HingeConstraintSettings_getHingeAxis1Y
  (JNIEnv *, jclass, jlong settingsVa) {
    const HingeConstraintSettings * const pSettings
            = reinterpret_cast<HingeConstraintSettings *> (settingsVa);
    const float result = pSettings->mHingeAxis1.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraintSettings
 * Method:    getHingeAxis1Z
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HingeConstraintSettings_getHingeAxis1Z
  (JNIEnv *, jclass, jlong settingsVa) {
    const HingeConstraintSettings * const pSettings
            = reinterpret_cast<HingeConstraintSettings *> (settingsVa);
    const float result = pSettings->mHingeAxis1.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraintSettings
 * Method:    getHingeAxis2X
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HingeConstraintSettings_getHingeAxis2X
  (JNIEnv *, jclass, jlong settingsVa) {
    const HingeConstraintSettings * const pSettings
            = reinterpret_cast<HingeConstraintSettings *> (settingsVa);
    const float result = pSettings->mHingeAxis2.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraintSettings
 * Method:    getHingeAxis2Y
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HingeConstraintSettings_getHingeAxis2Y
  (JNIEnv *, jclass, jlong settingsVa) {
    const HingeConstraintSettings * const pSettings
            = reinterpret_cast<HingeConstraintSettings *> (settingsVa);
    const float result = pSettings->mHingeAxis2.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraintSettings
 * Method:    getHingeAxis2Z
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HingeConstraintSettings_getHingeAxis2Z
  (JNIEnv *, jclass, jlong settingsVa) {
    const HingeConstraintSettings * const pSettings
            = reinterpret_cast<HingeConstraintSettings *> (settingsVa);
    const float result = pSettings->mHingeAxis2.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraintSettings
 * Method:    getLimitsMax
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HingeConstraintSettings_getLimitsMax
  (JNIEnv *, jclass, jlong settingsVa) {
    const HingeConstraintSettings * const pSettings
            = reinterpret_cast<HingeConstraintSettings *> (settingsVa);
    const float result = pSettings->mLimitsMax;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraintSettings
 * Method:    getLimitsMin
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HingeConstraintSettings_getLimitsMin
  (JNIEnv *, jclass, jlong settingsVa) {
    const HingeConstraintSettings * const pSettings
            = reinterpret_cast<HingeConstraintSettings *> (settingsVa);
    const float result = pSettings->mLimitsMin;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraintSettings
 * Method:    getLimitsSpringSettings
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_HingeConstraintSettings_getLimitsSpringSettings
  (JNIEnv *, jclass, jlong constraintSettingsVa) {
    HingeConstraintSettings * const pSettings
            = reinterpret_cast<HingeConstraintSettings *> (constraintSettingsVa);
    SpringSettings * const pResult = &pSettings->mLimitsSpringSettings;
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraintSettings
 * Method:    getMaxFrictionTorque
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HingeConstraintSettings_getMaxFrictionTorque
  (JNIEnv *, jclass, jlong settingsVa) {
    const HingeConstraintSettings * const pSettings
            = reinterpret_cast<HingeConstraintSettings *> (settingsVa);
    const float result = pSettings->mMaxFrictionTorque;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraintSettings
 * Method:    getMotorSettings
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_HingeConstraintSettings_getMotorSettings
  (JNIEnv *, jclass, jlong settingsVa) {
    HingeConstraintSettings * const pSettings
            = reinterpret_cast<HingeConstraintSettings *> (settingsVa);
    MotorSettings * const pResult = &pSettings->mMotorSettings;
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraintSettings
 * Method:    getNormalAxis1X
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HingeConstraintSettings_getNormalAxis1X
  (JNIEnv *, jclass, jlong settingsVa) {
    const HingeConstraintSettings * const pSettings
            = reinterpret_cast<HingeConstraintSettings *> (settingsVa);
    const float result = pSettings->mNormalAxis1.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraintSettings
 * Method:    getNormalAxis1Y
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HingeConstraintSettings_getNormalAxis1Y
  (JNIEnv *, jclass, jlong settingsVa) {
    const HingeConstraintSettings * const pSettings
            = reinterpret_cast<HingeConstraintSettings *> (settingsVa);
    const float result = pSettings->mNormalAxis1.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraintSettings
 * Method:    getNormalAxis1Z
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HingeConstraintSettings_getNormalAxis1Z
  (JNIEnv *, jclass, jlong settingsVa) {
    const HingeConstraintSettings * const pSettings
            = reinterpret_cast<HingeConstraintSettings *> (settingsVa);
    const float result = pSettings->mNormalAxis1.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraintSettings
 * Method:    getNormalAxis2X
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HingeConstraintSettings_getNormalAxis2X
  (JNIEnv *, jclass, jlong settingsVa) {
    const HingeConstraintSettings * const pSettings
            = reinterpret_cast<HingeConstraintSettings *> (settingsVa);
    const float result = pSettings->mNormalAxis2.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraintSettings
 * Method:    getNormalAxis2Y
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HingeConstraintSettings_getNormalAxis2Y
  (JNIEnv *, jclass, jlong settingsVa) {
    const HingeConstraintSettings * const pSettings
            = reinterpret_cast<HingeConstraintSettings *> (settingsVa);
    const float result = pSettings->mNormalAxis2.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraintSettings
 * Method:    getNormalAxis2Z
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HingeConstraintSettings_getNormalAxis2Z
  (JNIEnv *, jclass, jlong settingsVa) {
    const HingeConstraintSettings * const pSettings
            = reinterpret_cast<HingeConstraintSettings *> (settingsVa);
    const float result = pSettings->mNormalAxis2.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraintSettings
 * Method:    getPoint1X
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_HingeConstraintSettings_getPoint1X
  (JNIEnv *, jclass, jlong settingsVa) {
    const HingeConstraintSettings * const pSettings
            = reinterpret_cast<HingeConstraintSettings *> (settingsVa);
    const Real result = pSettings->mPoint1.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraintSettings
 * Method:    getPoint1Y
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_HingeConstraintSettings_getPoint1Y
  (JNIEnv *, jclass, jlong settingsVa) {
    const HingeConstraintSettings * const pSettings
            = reinterpret_cast<HingeConstraintSettings *> (settingsVa);
    const Real result = pSettings->mPoint1.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraintSettings
 * Method:    getPoint1Z
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_HingeConstraintSettings_getPoint1Z
  (JNIEnv *, jclass, jlong settingsVa) {
    const HingeConstraintSettings * const pSettings
            = reinterpret_cast<HingeConstraintSettings *> (settingsVa);
    const Real result = pSettings->mPoint1.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraintSettings
 * Method:    getPoint2X
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_HingeConstraintSettings_getPoint2X
  (JNIEnv *, jclass, jlong settingsVa) {
    const HingeConstraintSettings * const pSettings
            = reinterpret_cast<HingeConstraintSettings *> (settingsVa);
    const Real result = pSettings->mPoint2.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraintSettings
 * Method:    getPoint2Y
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_HingeConstraintSettings_getPoint2Y
  (JNIEnv *, jclass, jlong settingsVa) {
    const HingeConstraintSettings * const pSettings
            = reinterpret_cast<HingeConstraintSettings *> (settingsVa);
    const Real result = pSettings->mPoint2.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraintSettings
 * Method:    getPoint2Z
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_HingeConstraintSettings_getPoint2Z
  (JNIEnv *, jclass, jlong settingsVa) {
    const HingeConstraintSettings * const pSettings
            = reinterpret_cast<HingeConstraintSettings *> (settingsVa);
    const Real result = pSettings->mPoint2.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraintSettings
 * Method:    getSpace
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_HingeConstraintSettings_getSpace
  (JNIEnv *, jclass, jlong settingsVa) {
    const HingeConstraintSettings * const pSettings
            = reinterpret_cast<HingeConstraintSettings *> (settingsVa);
    const EConstraintSpace result = pSettings->mSpace;
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraintSettings
 * Method:    setHingeAxis1
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HingeConstraintSettings_setHingeAxis1
  (JNIEnv *, jclass, jlong settingsVa, jfloat x, jfloat y, jfloat z) {
    HingeConstraintSettings * const pSettings
            = reinterpret_cast<HingeConstraintSettings *> (settingsVa);
    const Vec3 axis(x, y, z);
    pSettings->mHingeAxis1 = axis;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraintSettings
 * Method:    setHingeAxis2
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HingeConstraintSettings_setHingeAxis2
  (JNIEnv *, jclass, jlong settingsVa, jfloat x, jfloat y, jfloat z) {
    HingeConstraintSettings * const pSettings
            = reinterpret_cast<HingeConstraintSettings *> (settingsVa);
    const Vec3 axis(x, y, z);
    pSettings->mHingeAxis2 = axis;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraintSettings
 * Method:    setLimitsMax
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HingeConstraintSettings_setLimitsMax
  (JNIEnv *, jclass, jlong settingsVa, jfloat limit) {
    HingeConstraintSettings * const pSettings
            = reinterpret_cast<HingeConstraintSettings *> (settingsVa);
    pSettings->mLimitsMax = limit;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraintSettings
 * Method:    setLimitsMin
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HingeConstraintSettings_setLimitsMin
  (JNIEnv *, jclass, jlong settingsVa, jfloat limit) {
    HingeConstraintSettings * const pSettings
            = reinterpret_cast<HingeConstraintSettings *> (settingsVa);
    pSettings->mLimitsMin = limit;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraintSettings
 * Method:    setMaxFrictionTorque
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HingeConstraintSettings_setMaxFrictionTorque
  (JNIEnv *, jclass, jlong settingsVa, jfloat torque) {
    HingeConstraintSettings * const pSettings
            = reinterpret_cast<HingeConstraintSettings *> (settingsVa);
    pSettings->mMaxFrictionTorque = torque;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraintSettings
 * Method:    setMotorSettings
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HingeConstraintSettings_setMotorSettings
  (JNIEnv *, jclass, jlong constraintSettingsVa, jlong motorSettingsVa) {
    HingeConstraintSettings * const pConstraintSettings
            = reinterpret_cast<HingeConstraintSettings *> (constraintSettingsVa);
    const MotorSettings * const pMotorSettings
            = reinterpret_cast<MotorSettings *> (motorSettingsVa);
    pConstraintSettings->mMotorSettings = *pMotorSettings;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraintSettings
 * Method:    setNormalAxis1
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HingeConstraintSettings_setNormalAxis1
  (JNIEnv *, jclass, jlong settingsVa, jfloat x, jfloat y, jfloat z) {
    HingeConstraintSettings * const pSettings
            = reinterpret_cast<HingeConstraintSettings *> (settingsVa);
    const Vec3 axis(x, y, z);
    pSettings->mNormalAxis1 = axis;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraintSettings
 * Method:    setNormalAxis2
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HingeConstraintSettings_setNormalAxis2
  (JNIEnv *, jclass, jlong settingsVa, jfloat x, jfloat y, jfloat z) {
    HingeConstraintSettings * const pSettings
            = reinterpret_cast<HingeConstraintSettings *> (settingsVa);
    const Vec3 axis(x, y, z);
    pSettings->mNormalAxis2 = axis;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraintSettings
 * Method:    setPoint1
 * Signature: (JDDD)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HingeConstraintSettings_setPoint1
  (JNIEnv *, jclass, jlong settingsVa, jdouble x, jdouble y, jdouble z) {
    HingeConstraintSettings * const pSettings
            = reinterpret_cast<HingeConstraintSettings *> (settingsVa);
    const RVec3 rvec3(x, y, z);
    pSettings->mPoint1 = rvec3;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraintSettings
 * Method:    setPoint2
 * Signature: (JDDD)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HingeConstraintSettings_setPoint2
  (JNIEnv *, jclass, jlong settingsVa, jdouble x, jdouble y, jdouble z) {
    HingeConstraintSettings * const pSettings
            = reinterpret_cast<HingeConstraintSettings *> (settingsVa);
    const RVec3 rvec3(x, y, z);
    pSettings->mPoint2 = rvec3;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraintSettings
 * Method:    setSpace
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HingeConstraintSettings_setSpace
  (JNIEnv *, jclass, jlong settingsVa, jint ordinal) {
    HingeConstraintSettings * const pSettings
            = reinterpret_cast<HingeConstraintSettings *> (settingsVa);
    const EConstraintSpace space = (EConstraintSpace) ordinal;
    pSettings->mSpace = space;
}