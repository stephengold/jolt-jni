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
#include "Jolt/Physics/Constraints/SliderConstraint.h"
#include "auto/com_github_stephengold_joltjni_SliderConstraintSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_createCopy
    BODYOF_CREATE_COPY(SliderConstraintSettings)

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_createDefault
    BODYOF_CREATE_DEFAULT(SliderConstraintSettings)

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
 * Method:    getNormalAxis1
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_getNormalAxis1
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeFloats) {
    const SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& result = pSettings->mNormalAxis1;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    getNormalAxis2
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_getNormalAxis2
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeFloats) {
    const SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& result = pSettings->mNormalAxis2;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    getPoint1
 * Signature: (JLjava/nio/DoubleBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_getPoint1
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeDoubles) {
    const SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    DIRECT_DOUBLE_BUFFER(pEnv, storeDoubles, pDoubles, capacityDoubles);
    JPH_ASSERT(capacityDoubles >= 3);
    const RVec3& result = pSettings->mPoint1;
    pDoubles[0] = result.GetX();
    pDoubles[1] = result.GetY();
    pDoubles[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    getPoint2
 * Signature: (JLjava/nio/DoubleBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_getPoint2
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeDoubles) {
    const SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    DIRECT_DOUBLE_BUFFER(pEnv, storeDoubles, pDoubles, capacityDoubles);
    JPH_ASSERT(capacityDoubles >= 3);
    const RVec3& result = pSettings->mPoint2;
    pDoubles[0] = result.GetX();
    pDoubles[1] = result.GetY();
    pDoubles[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    getSliderAxis1
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_getSliderAxis1
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeFloats) {
    const SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& result = pSettings->mSliderAxis1;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraintSettings
 * Method:    getSliderAxis2
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_getSliderAxis2
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeFloats) {
    const SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& result = pSettings->mSliderAxis2;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
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
 * Method:    setSliderAxis
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SliderConstraintSettings_setSliderAxis
  (JNIEnv *, jclass, jlong settingsVa, jfloat dx, jfloat dy, jfloat dz) {
    SliderConstraintSettings * const pSettings
            = reinterpret_cast<SliderConstraintSettings *> (settingsVa);
    const Vec3 sliderAxis(dx, dy, dz);
    pSettings->SetSliderAxis(sliderAxis);
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
