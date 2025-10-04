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
#include "Jolt/Physics/Constraints/RackAndPinionConstraint.h"
#include "auto/com_github_stephengold_joltjni_RackAndPinionConstraintSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_RackAndPinionConstraintSettings
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_RackAndPinionConstraintSettings_createCopy
  BODYOF_CREATE_COPY_TARGET(RackAndPinionConstraintSettings)

/*
 * Class:     com_github_stephengold_joltjni_RackAndPinionConstraintSettings
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_RackAndPinionConstraintSettings_createDefault
  BODYOF_CREATE_DEFAULT_TARGET(RackAndPinionConstraintSettings)

/*
 * Class:     com_github_stephengold_joltjni_RackAndPinionConstraintSettings
 * Method:    getHingeAxis
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RackAndPinionConstraintSettings_getHingeAxis
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeFloats) {
    const RackAndPinionConstraintSettings * const pSettings
            = reinterpret_cast<RackAndPinionConstraintSettings *> (settingsVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& result = pSettings->mHingeAxis;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_RackAndPinionConstraintSettings
 * Method:    getRatio
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_RackAndPinionConstraintSettings_getRatio
  (JNIEnv *, jclass, jlong settingsVa) {
    const RackAndPinionConstraintSettings * const pSettings
            = reinterpret_cast<RackAndPinionConstraintSettings *> (settingsVa);
    const float result = pSettings->mRatio;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RackAndPinionConstraintSettings
 * Method:    getSliderAxis
 * Signature: (JLjava/nio/FloatBuffer;)F
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RackAndPinionConstraintSettings_getSliderAxis
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeFloats) {
    const RackAndPinionConstraintSettings * const pSettings
            = reinterpret_cast<RackAndPinionConstraintSettings *> (settingsVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& result = pSettings->mSliderAxis;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_RackAndPinionConstraintSettings
 * Method:    getSpace
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_RackAndPinionConstraintSettings_getSpace
  (JNIEnv *, jclass, jlong settingsVa) {
    const RackAndPinionConstraintSettings * const pSettings
            = reinterpret_cast<RackAndPinionConstraintSettings *> (settingsVa);
    const EConstraintSpace result = pSettings->mSpace;
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_RackAndPinionConstraintSettings
 * Method:    setHingeAxis
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RackAndPinionConstraintSettings_setHingeAxis
  (JNIEnv *, jclass, jlong settingsVa, jfloat dx, jfloat dy, jfloat dz) {
    RackAndPinionConstraintSettings * const pSettings
            = reinterpret_cast<RackAndPinionConstraintSettings *> (settingsVa);
    const Vec3 axisDirection(dx, dy, dz);
    pSettings->mHingeAxis = axisDirection;
}

/*
 * Class:     com_github_stephengold_joltjni_RackAndPinionConstraintSettings
 * Method:    setRatio
 * Signature: (JIFI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RackAndPinionConstraintSettings_setRatio
  (JNIEnv *, jclass, jlong settingsVa, jint rackTeeth, jfloat rackLength, jint pinionTeeth) {
    RackAndPinionConstraintSettings * const pSettings
            = reinterpret_cast<RackAndPinionConstraintSettings *> (settingsVa);
    pSettings->SetRatio(rackTeeth, rackLength, pinionTeeth);
}

/*
 * Class:     com_github_stephengold_joltjni_RackAndPinionConstraintSettings
 * Method:    setRatioDirectly
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RackAndPinionConstraintSettings_setRatioDirectly
  (JNIEnv *, jclass, jlong, jfloat);

/*
 * Class:     com_github_stephengold_joltjni_RackAndPinionConstraintSettings
 * Method:    setSliderAxis
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RackAndPinionConstraintSettings_setSliderAxis
  (JNIEnv *, jclass, jlong settingsVa, jfloat dx, jfloat dy, jfloat dz) {
    RackAndPinionConstraintSettings * const pSettings
            = reinterpret_cast<RackAndPinionConstraintSettings *> (settingsVa);
    const Vec3 axisDirection(dx, dy, dz);
    pSettings->mSliderAxis = axisDirection;
}

/*
 * Class:     com_github_stephengold_joltjni_RackAndPinionConstraintSettings
 * Method:    setSpace
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RackAndPinionConstraintSettings_setSpace
  (JNIEnv *, jclass, jlong settingsVa, jint ordinal) {
    RackAndPinionConstraintSettings * const pSettings
            = reinterpret_cast<RackAndPinionConstraintSettings *> (settingsVa);
    const EConstraintSpace space = (EConstraintSpace) ordinal;
    pSettings->mSpace = space;
}