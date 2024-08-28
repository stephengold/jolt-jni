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
#include <Jolt/Physics/Constraints/SliderConstraint.h>
#include "auto/com_github_stephengold_joltjni_SliderConstraintSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    createSliderConstraintSettings
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_createSliderConstraintSettings
  (JNIEnv *, jclass) {
    SliderConstraintSettings * const pResult = new SliderConstraintSettings();
    TRACE_NEW("SliderConstraintSettings", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    getAutoDetectPoint
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_getAutoDetectPoint
  (JNIEnv *, jclass, jlong settingsVa) {
    const SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    const bool result = pSettings->mAutoDetectPoint;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    getLimitsMax
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_getLimitsMax
  (JNIEnv *, jclass, jlong settingsVa) {
    const SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    const float result = pSettings->mLimitsMax;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    getLimitsMin
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_getLimitsMin
  (JNIEnv *, jclass, jlong settingsVa) {
    const SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    const float result = pSettings->mLimitsMin;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    getLimitsSpringSettings
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_getLimitsSpringSettings
  (JNIEnv *, jclass, jlong settingsVa) {
    SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    SpringSettings * const pResult = &pSettings->mLimitsSpringSettings;
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    getMaxFrictionForce
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_getMaxFrictionForce
  (JNIEnv *, jclass, jlong settingsVa) {
    const SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    const float result = pSettings->mMaxFrictionForce;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    getMotorSettings
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_getMotorSettings
  (JNIEnv *, jclass, jlong settingsVa) {
    SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    MotorSettings * const pResult = &pSettings->mMotorSettings;
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    getNormalAxis1X
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_getNormalAxis1X
  (JNIEnv *, jclass, jlong settingsVa) {
    const SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    const float result = pSettings->mNormalAxis1.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    getNormalAxis1Y
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_getNormalAxis1Y
  (JNIEnv *, jclass, jlong settingsVa) {
    const SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    const float result = pSettings->mNormalAxis1.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    getNormalAxis1Z
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_getNormalAxis1Z
  (JNIEnv *, jclass, jlong settingsVa) {
    const SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    const float result = pSettings->mNormalAxis1.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    getNormalAxis2X
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_getNormalAxis2X
  (JNIEnv *, jclass, jlong settingsVa) {
    const SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    const float result = pSettings->mNormalAxis2.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    getNormalAxis2Y
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_getNormalAxis2Y
  (JNIEnv *, jclass, jlong settingsVa) {
    const SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    const float result = pSettings->mNormalAxis2.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    getNormalAxis2Z
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_getNormalAxis2Z
  (JNIEnv *, jclass, jlong settingsVa) {
    const SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    const float result = pSettings->mNormalAxis2.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    getPoint1X
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_getPoint1X
  (JNIEnv *, jclass, jlong settingsVa) {
    const SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    const Real result = pSettings->mPoint1.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    getPoint1Y
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_getPoint1Y
  (JNIEnv *, jclass, jlong settingsVa) {
    const SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    const Real result = pSettings->mPoint1.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    getPoint1Z
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_getPoint1Z
  (JNIEnv *, jclass, jlong settingsVa) {
    const SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    const Real result = pSettings->mPoint1.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    getPoint2X
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_getPoint2X
  (JNIEnv *, jclass, jlong settingsVa) {
    const SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    const Real result = pSettings->mPoint2.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    getPoint2Y
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_getPoint2Y
  (JNIEnv *, jclass, jlong settingsVa) {
    const SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    const Real result = pSettings->mPoint2.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    getPoint2Z
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_getPoint2Z
  (JNIEnv *, jclass, jlong settingsVa) {
    const SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    const Real result = pSettings->mPoint2.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    getSliderAxis1X
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_getSliderAxis1X
  (JNIEnv *, jclass, jlong settingsVa) {
    const SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    const float result = pSettings->mSliderAxis1.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    getSliderAxis1Y
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_getSliderAxis1Y
  (JNIEnv *, jclass, jlong settingsVa) {
    const SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    const float result = pSettings->mSliderAxis1.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    getSliderAxis1Z
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_getSliderAxis1Z
  (JNIEnv *, jclass, jlong settingsVa) {
    const SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    const float result = pSettings->mSliderAxis1.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    getSliderAxis2X
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_getSliderAxis2X
  (JNIEnv *, jclass, jlong settingsVa) {
    const SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    const float result = pSettings->mSliderAxis2.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    getSliderAxis2Y
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_getSliderAxis2Y
  (JNIEnv *, jclass, jlong settingsVa) {
    const SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    const float result = pSettings->mSliderAxis2.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    getSliderAxis2Z
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_getSliderAxis2Z
  (JNIEnv *, jclass, jlong settingsVa) {
    const SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    const float result = pSettings->mSliderAxis2.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    getSpace
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_getSpace
  (JNIEnv *, jclass, jlong settingsVa) {
    const SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    const EConstraintSpace result = pSettings->mSpace;
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    setAutoDetectPoint
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_setAutoDetectPoint
  (JNIEnv *, jclass, jlong settingsVa, jboolean setting) {
    SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    pSettings->mAutoDetectPoint = setting;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    setLimitsMax
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_setLimitsMax
  (JNIEnv *, jclass, jlong settingsVa, jfloat limit) {
    SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    pSettings->mLimitsMax = limit;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    setLimitsMin
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_setLimitsMin
  (JNIEnv *, jclass, jlong settingsVa, jfloat limit) {
    SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    pSettings->mLimitsMin = limit;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    setMaxFrictionForce
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_setMaxFrictionForce
  (JNIEnv *, jclass, jlong settingsVa, jfloat force) {
    SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    pSettings->mMaxFrictionForce = force;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    setNormalAxis1
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_setNormalAxis1
  (JNIEnv *, jclass, jlong settingsVa, jfloat x, jfloat y, jfloat z) {
    SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    const Vec3 vec3(x, y, z);
    pSettings->mNormalAxis1 = vec3;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    setNormalAxis2
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_setNormalAxis2
  (JNIEnv *, jclass, jlong settingsVa, jfloat x, jfloat y, jfloat z) {
    SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    const Vec3 vec3(x, y, z);
    pSettings->mNormalAxis2 = vec3;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    setPoint1
 * Signature: (JDDD)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_setPoint1
  (JNIEnv *, jclass, jlong settingsVa, jdouble x, jdouble y, jdouble z) {
    SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    const RVec3 rvec3(x, y, z);
    pSettings->mPoint1 = rvec3;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    setPoint2
 * Signature: (JDDD)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_setPoint2
  (JNIEnv *, jclass, jlong settingsVa, jdouble x, jdouble y, jdouble z) {
    SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    const RVec3 rvec3(x, y, z);
    pSettings->mPoint2 = rvec3;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    setSliderAxis1
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_setSliderAxis1
  (JNIEnv *, jclass, jlong settingsVa, jfloat x, jfloat y, jfloat z) {
    SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    const Vec3 vec3(x, y, z);
    pSettings->mSliderAxis1 = vec3;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    setSliderAxis2
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_setSliderAxis2
  (JNIEnv *, jclass, jlong settingsVa, jfloat x, jfloat y, jfloat z) {
    SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    const Vec3 vec3(x, y, z);
    pSettings->mSliderAxis2 = vec3;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    setSpace
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_setSpace
  (JNIEnv *, jclass, jlong settingsVa, jint ordinal) {
    SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    const EConstraintSpace space = (EConstraintSpace) ordinal;
    pSettings->mSpace = space;
}