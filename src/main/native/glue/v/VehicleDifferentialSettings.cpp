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
#include "Jolt/Physics/Vehicle/VehicleDifferential.h"
#include "auto/com_github_stephengold_joltjni_VehicleDifferentialSettings.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_VehicleDifferentialSettings
 * Method:    getDifferentialRatio
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_VehicleDifferentialSettings_getDifferentialRatio
  (JNIEnv *, jclass, jlong settingsVa) {
    const VehicleDifferentialSettings * const pSettings
            = reinterpret_cast<VehicleDifferentialSettings *> (settingsVa);
    const float result = pSettings->mDifferentialRatio;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleDifferentialSettings
 * Method:    getEngineTorqueRatio
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_VehicleDifferentialSettings_getEngineTorqueRatio
  (JNIEnv *, jclass, jlong settingsVa) {
    const VehicleDifferentialSettings * const pSettings
            = reinterpret_cast<VehicleDifferentialSettings *> (settingsVa);
    const float result = pSettings->mEngineTorqueRatio;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleDifferentialSettings
 * Method:    getLeftWheel
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_VehicleDifferentialSettings_getLeftWheel
  (JNIEnv *, jclass, jlong settingsVa) {
    const VehicleDifferentialSettings * const pSettings
            = reinterpret_cast<VehicleDifferentialSettings *> (settingsVa);
    const int result = pSettings->mLeftWheel;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleDifferentialSettings
 * Method:    getLimitedSlipRatio
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_VehicleDifferentialSettings_getLimitedSlipRatio
  (JNIEnv *, jclass, jlong settingsVa) {
    const VehicleDifferentialSettings * const pSettings
            = reinterpret_cast<VehicleDifferentialSettings *> (settingsVa);
    const float result = pSettings->mLimitedSlipRatio;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleDifferentialSettings
 * Method:    getRightWheel
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_VehicleDifferentialSettings_getRightWheel
  (JNIEnv *, jclass, jlong settingsVa) {
    const VehicleDifferentialSettings * const pSettings
            = reinterpret_cast<VehicleDifferentialSettings *> (settingsVa);
    const int result = pSettings->mRightWheel;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleDifferentialSettings
 * Method:    setDifferentialRatio
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleDifferentialSettings_setDifferentialRatio
  (JNIEnv *, jclass, jlong settingsVa, jfloat ratio) {
    VehicleDifferentialSettings * const pSettings
            = reinterpret_cast<VehicleDifferentialSettings *> (settingsVa);
    pSettings->mDifferentialRatio = ratio;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleDifferentialSettings
 * Method:    setEngineTorqueRatio
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleDifferentialSettings_setEngineTorqueRatio
  (JNIEnv *, jclass, jlong settingsVa, jfloat fraction) {
    VehicleDifferentialSettings * const pSettings
            = reinterpret_cast<VehicleDifferentialSettings *> (settingsVa);
    pSettings->mEngineTorqueRatio = fraction;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleDifferentialSettings
 * Method:    setLeftWheel
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleDifferentialSettings_setLeftWheel
  (JNIEnv *, jclass, jlong settingsVa, jint wheelIndex) {
    VehicleDifferentialSettings * const pSettings
            = reinterpret_cast<VehicleDifferentialSettings *> (settingsVa);
    pSettings->mLeftWheel = wheelIndex;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleDifferentialSettings
 * Method:    setLimitedSlipRatio
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleDifferentialSettings_setLimitedSlipRatio
  (JNIEnv *, jclass, jlong settingsVa, jfloat ratio) {
    VehicleDifferentialSettings * const pSettings
            = reinterpret_cast<VehicleDifferentialSettings *> (settingsVa);
    pSettings->mLimitedSlipRatio = ratio;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleDifferentialSettings
 * Method:    setRightWheel
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleDifferentialSettings_setRightWheel
  (JNIEnv *, jclass, jlong settingsVa, jint wheelIndex) {
    VehicleDifferentialSettings * const pSettings
            = reinterpret_cast<VehicleDifferentialSettings *> (settingsVa);
    pSettings->mRightWheel = wheelIndex;
}