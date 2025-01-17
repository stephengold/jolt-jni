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
#include "Jolt/Physics/Constraints/SwingTwistConstraint.h"
#include "auto/com_github_stephengold_joltjni_SwingTwistConstraintSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_createDefault
  (JNIEnv *, jclass) {
    SwingTwistConstraintSettings * const pResult
            = new SwingTwistConstraintSettings();
    TRACE_NEW("SwingTwistConstraintSettings", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    getMaxFrictionTorque
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_getMaxFrictionTorque
  (JNIEnv *, jclass, jlong settingsVa) {
    const SwingTwistConstraintSettings * const pSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (settingsVa);
    const float result = pSettings->mMaxFrictionTorque;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    getNormalHalfConeAngle
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_getNormalHalfConeAngle
  (JNIEnv *, jclass, jlong settingsVa) {
    const SwingTwistConstraintSettings * const pSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (settingsVa);
    const float result = pSettings->mNormalHalfConeAngle;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    getPlaneAxis1X
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_getPlaneAxis1X
  (JNIEnv *, jclass, jlong settingsVa) {
    const SwingTwistConstraintSettings * const pSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (settingsVa);
    const float result = pSettings->mPlaneAxis1.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    getPlaneAxis1Y
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_getPlaneAxis1Y
  (JNIEnv *, jclass, jlong settingsVa) {
    const SwingTwistConstraintSettings * const pSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (settingsVa);
    const float result = pSettings->mPlaneAxis1.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    getPlaneAxis1Z
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_getPlaneAxis1Z
  (JNIEnv *, jclass, jlong settingsVa) {
    const SwingTwistConstraintSettings * const pSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (settingsVa);
    const float result = pSettings->mPlaneAxis1.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    getPlaneAxis2X
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_getPlaneAxis2X
  (JNIEnv *, jclass, jlong settingsVa) {
    const SwingTwistConstraintSettings * const pSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (settingsVa);
    const float result = pSettings->mPlaneAxis2.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    getPlaneAxis2Y
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_getPlaneAxis2Y
  (JNIEnv *, jclass, jlong settingsVa) {
    const SwingTwistConstraintSettings * const pSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (settingsVa);
    const float result = pSettings->mPlaneAxis2.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    getPlaneAxis2Z
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_getPlaneAxis2Z
  (JNIEnv *, jclass, jlong settingsVa) {
    const SwingTwistConstraintSettings * const pSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (settingsVa);
    const float result = pSettings->mPlaneAxis2.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    getPlaneHalfConeAngle
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_getPlaneHalfConeAngle
  (JNIEnv *, jclass, jlong settingsVa) {
    const SwingTwistConstraintSettings * const pSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (settingsVa);
    const float result = pSettings->mPlaneHalfConeAngle;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    getPosition1X
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_getPosition1X
  (JNIEnv *, jclass, jlong settingsVa) {
    const SwingTwistConstraintSettings * const pSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (settingsVa);
    const Real result = pSettings->mPosition1.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    getPosition1Y
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_getPosition1Y
  (JNIEnv *, jclass, jlong settingsVa) {
    const SwingTwistConstraintSettings * const pSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (settingsVa);
    const Real result = pSettings->mPosition1.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    getPosition1Z
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_getPosition1Z
  (JNIEnv *, jclass, jlong settingsVa) {
    const SwingTwistConstraintSettings * const pSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (settingsVa);
    const Real result = pSettings->mPosition1.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    getPosition2X
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_getPosition2X
  (JNIEnv *, jclass, jlong settingsVa) {
    const SwingTwistConstraintSettings * const pSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (settingsVa);
    const Real result = pSettings->mPosition2.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    getPosition2Y
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_getPosition2Y
  (JNIEnv *, jclass, jlong settingsVa) {
    const SwingTwistConstraintSettings * const pSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (settingsVa);
    const Real result = pSettings->mPosition2.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    getPosition2Z
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_getPosition2Z
  (JNIEnv *, jclass, jlong settingsVa) {
    const SwingTwistConstraintSettings * const pSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (settingsVa);
    const Real result = pSettings->mPosition2.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    getSpace
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_getSpace
  (JNIEnv *, jclass, jlong settingsVa) {
    const SwingTwistConstraintSettings * const pSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (settingsVa);
    const EConstraintSpace result = pSettings->mSpace;
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    getSwingMotorSettings
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_getSwingMotorSettings
  (JNIEnv *, jclass, jlong constraintSettingsVa) {
    SwingTwistConstraintSettings * const pConstraintSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (constraintSettingsVa);
    MotorSettings * const pResult = &pConstraintSettings->mSwingMotorSettings;
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    getSwingType
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_getSwingType
  (JNIEnv *, jclass, jlong settingsVa) {
    const SwingTwistConstraintSettings * const pSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (settingsVa);
    const ESwingType result = pSettings->mSwingType;
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    getTwistAxis1X
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_getTwistAxis1X
  (JNIEnv *, jclass, jlong settingsVa) {
    const SwingTwistConstraintSettings * const pSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (settingsVa);
    const float result = pSettings->mTwistAxis1.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    getTwistAxis1Y
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_getTwistAxis1Y
  (JNIEnv *, jclass, jlong settingsVa) {
    const SwingTwistConstraintSettings * const pSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (settingsVa);
    const float result = pSettings->mTwistAxis1.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    getTwistAxis1Z
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_getTwistAxis1Z
  (JNIEnv *, jclass, jlong settingsVa) {
    const SwingTwistConstraintSettings * const pSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (settingsVa);
    const float result = pSettings->mTwistAxis1.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    getTwistAxis2X
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_getTwistAxis2X
  (JNIEnv *, jclass, jlong settingsVa) {
    const SwingTwistConstraintSettings * const pSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (settingsVa);
    const float result = pSettings->mTwistAxis2.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    getTwistAxis2Y
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_getTwistAxis2Y
  (JNIEnv *, jclass, jlong settingsVa) {
    const SwingTwistConstraintSettings * const pSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (settingsVa);
    const float result = pSettings->mTwistAxis2.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    getTwistAxis2Z
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_getTwistAxis2Z
  (JNIEnv *, jclass, jlong settingsVa) {
    const SwingTwistConstraintSettings * const pSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (settingsVa);
    const float result = pSettings->mTwistAxis2.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    getTwistMaxAngle
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_getTwistMaxAngle
  (JNIEnv *, jclass, jlong settingsVa) {
    const SwingTwistConstraintSettings * const pSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (settingsVa);
    const float result = pSettings->mTwistMaxAngle;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    getTwistMinAngle
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_getTwistMinAngle
  (JNIEnv *, jclass, jlong settingsVa) {
    const SwingTwistConstraintSettings * const pSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (settingsVa);
    const float result = pSettings->mTwistMinAngle;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    getTwistMotorSettings
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_getTwistMotorSettings
  (JNIEnv *, jclass, jlong constraintSettingsVa) {
    SwingTwistConstraintSettings * const pConstraintSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (constraintSettingsVa);
    MotorSettings * const pResult = &pConstraintSettings->mTwistMotorSettings;
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    setMaxFrictionTorque
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_setMaxFrictionTorque
  (JNIEnv *, jclass, jlong settingsVa, jfloat torque) {
    SwingTwistConstraintSettings * const pSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (settingsVa);
    pSettings->mMaxFrictionTorque = torque;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    setNormalHalfConeAngle
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_setNormalHalfConeAngle
  (JNIEnv *, jclass, jlong settingsVa, jfloat angle) {
    SwingTwistConstraintSettings * const pSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (settingsVa);
    pSettings->mNormalHalfConeAngle = angle;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    setPlaneAxis1
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_setPlaneAxis1
  (JNIEnv *, jclass, jlong settingsVa, jfloat dx, jfloat dy, jfloat dz) {
    SwingTwistConstraintSettings * const pSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (settingsVa);
    const Vec3 axisDirection(dx, dy, dz);
    pSettings->mPlaneAxis1 = axisDirection;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    setPlaneAxis2
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_setPlaneAxis2
  (JNIEnv *, jclass, jlong settingsVa, jfloat dx, jfloat dy, jfloat dz) {
    SwingTwistConstraintSettings * const pSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (settingsVa);
    const Vec3 axisDirection(dx, dy, dz);
    pSettings->mPlaneAxis2 = axisDirection;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    setPlaneHalfConeAngle
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_setPlaneHalfConeAngle
  (JNIEnv *, jclass, jlong settingsVa, jfloat angle) {
    SwingTwistConstraintSettings * const pSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (settingsVa);
    pSettings->mPlaneHalfConeAngle = angle;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    setPosition1
 * Signature: (JDDD)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_setPosition1
  (JNIEnv *, jclass, jlong settingsVa, jdouble locX, jdouble locY, jdouble locZ) {
    SwingTwistConstraintSettings * const pSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (settingsVa);
    const RVec3 location(locX, locY, locZ);
    pSettings->mPosition1 = location;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    setPosition2
 * Signature: (JDDD)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_setPosition2
  (JNIEnv *, jclass, jlong settingsVa, jdouble locX, jdouble locY, jdouble locZ) {
    SwingTwistConstraintSettings * const pSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (settingsVa);
    const RVec3 location(locX, locY, locZ);
    pSettings->mPosition2 = location;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    setSpace
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_setSpace
  (JNIEnv *, jclass, jlong settingsVa, jint ordinal) {
    SwingTwistConstraintSettings * const pSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (settingsVa);
    const EConstraintSpace space = (EConstraintSpace) ordinal;
    pSettings->mSpace = space;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    setSwingMotorSettings
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_setSwingMotorSettings
  (JNIEnv *, jclass, jlong constraintSettingsVa, jlong motorSettingsVa) {
    SwingTwistConstraintSettings * const pConstraintSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (constraintSettingsVa);
    const MotorSettings * const pMotorSettings
            = reinterpret_cast<MotorSettings *> (motorSettingsVa);
    pConstraintSettings->mSwingMotorSettings = *pMotorSettings;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    setSwingType
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_setSwingType
  (JNIEnv *, jclass, jlong settingsVa, jint ordinal) {
    SwingTwistConstraintSettings * const pSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (settingsVa);
    const ESwingType swingType = (ESwingType) ordinal;
    pSettings->mSwingType = swingType;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    setTwistAxis1
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_setTwistAxis1
  (JNIEnv *, jclass, jlong settingsVa, jfloat dx, jfloat dy, jfloat dz) {
    SwingTwistConstraintSettings * const pSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (settingsVa);
    const Vec3 axisDirection(dx, dy, dz);
    pSettings->mTwistAxis1 = axisDirection;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    setTwistAxis2
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_setTwistAxis2
  (JNIEnv *, jclass, jlong settingsVa, jfloat dx, jfloat dy, jfloat dz) {
    SwingTwistConstraintSettings * const pSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (settingsVa);
    const Vec3 axisDirection(dx, dy, dz);
    pSettings->mTwistAxis2 = axisDirection;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    setTwistMaxAngle
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_setTwistMaxAngle
  (JNIEnv *, jclass, jlong settingsVa, jfloat angle) {
    SwingTwistConstraintSettings * const pSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (settingsVa);
    pSettings->mTwistMaxAngle = angle;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    setTwistMinAngle
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_setTwistMinAngle
  (JNIEnv *, jclass, jlong settingsVa, jfloat angle) {
    SwingTwistConstraintSettings * const pSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (settingsVa);
    pSettings->mTwistMinAngle = angle;
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraintSettings
 * Method:    setTwistMotorSettings
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraintSettings_setTwistMotorSettings
  (JNIEnv *, jclass, jlong constraintSettingsVa, jlong motorSettingsVa) {
    SwingTwistConstraintSettings * const pConstraintSettings
            = reinterpret_cast<SwingTwistConstraintSettings *> (constraintSettingsVa);
    const MotorSettings * const pMotorSettings
            = reinterpret_cast<MotorSettings *> (motorSettingsVa);
    pConstraintSettings->mTwistMotorSettings = *pMotorSettings;
}