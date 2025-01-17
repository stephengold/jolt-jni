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
#include "Jolt/Physics/Vehicle/VehicleConstraint.h"
#include "auto/com_github_stephengold_joltjni_VehicleConstraintSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraintSettings
 * Method:    addWheel
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleConstraintSettings_addWheel
  (JNIEnv *, jclass, jlong constraintSettingsVa, jlong wheelSettingsVa) {
    VehicleConstraintSettings * const pConstraintSettings
            = reinterpret_cast<VehicleConstraintSettings *> (constraintSettingsVa);
    WheelSettings * const pWheelSettings
            = reinterpret_cast<WheelSettings *> (wheelSettingsVa);
    Ref<WheelSettings> ref = pWheelSettings;
    pConstraintSettings->mWheels.push_back(ref);
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraintSettings
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_VehicleConstraintSettings_createDefault
  (JNIEnv *, jclass) {
    VehicleConstraintSettings * const pResult = new VehicleConstraintSettings();
    TRACE_NEW("VehicleConstraintSettings", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraintSettings
 * Method:    getAntiRollBar
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_VehicleConstraintSettings_getAntiRollBar
  (JNIEnv *, jclass, jlong settingsVa, jint index) {
    VehicleConstraintSettings * const pSettings
            = reinterpret_cast<VehicleConstraintSettings *> (settingsVa);
    VehicleAntiRollBar * const pResult
            = &pSettings->mAntiRollBars[index];
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraintSettings
 * Method:    getMaxPitchRollAngle
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_VehicleConstraintSettings_getMaxPitchRollAngle
  (JNIEnv *, jclass, jlong settingsVa) {
    VehicleConstraintSettings * const pSettings
            = reinterpret_cast<VehicleConstraintSettings *> (settingsVa);
    const float result = pSettings->mMaxPitchRollAngle;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraintSettings
 * Method:    getForwardX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_VehicleConstraintSettings_getForwardX
  (JNIEnv *, jclass, jlong settingsVa) {
    const VehicleConstraintSettings * const pSettings
            = reinterpret_cast<VehicleConstraintSettings *> (settingsVa);
    const float result = pSettings->mForward.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraintSettings
 * Method:    getForwardY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_VehicleConstraintSettings_getForwardY
  (JNIEnv *, jclass, jlong settingsVa) {
    const VehicleConstraintSettings * const pSettings
            = reinterpret_cast<VehicleConstraintSettings *> (settingsVa);
    const float result = pSettings->mForward.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraintSettings
 * Method:    getForwardZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_VehicleConstraintSettings_getForwardZ
  (JNIEnv *, jclass, jlong settingsVa) {
    const VehicleConstraintSettings * const pSettings
            = reinterpret_cast<VehicleConstraintSettings *> (settingsVa);
    const float result = pSettings->mForward.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraintSettings
 * Method:    getUpX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_VehicleConstraintSettings_getUpX
  (JNIEnv *, jclass, jlong settingsVa) {
    const VehicleConstraintSettings * const pSettings
            = reinterpret_cast<VehicleConstraintSettings *> (settingsVa);
    const float result = pSettings->mUp.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraintSettings
 * Method:    getUpY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_VehicleConstraintSettings_getUpY
  (JNIEnv *, jclass, jlong settingsVa) {
    const VehicleConstraintSettings * const pSettings
            = reinterpret_cast<VehicleConstraintSettings *> (settingsVa);
    const float result = pSettings->mUp.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraintSettings
 * Method:    getUpZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_VehicleConstraintSettings_getUpZ
  (JNIEnv *, jclass, jlong settingsVa) {
    const VehicleConstraintSettings * const pSettings
            = reinterpret_cast<VehicleConstraintSettings *> (settingsVa);
    const float result = pSettings->mUp.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraintSettings
 * Method:    setController
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleConstraintSettings_setController
  (JNIEnv *, jclass, jlong constraintSettingsVa, jlong controllerSettingsVa) {
    VehicleConstraintSettings * const pConstraintSettings
            = reinterpret_cast<VehicleConstraintSettings *> (constraintSettingsVa);
    VehicleControllerSettings * const pControllerSettings
            = reinterpret_cast<VehicleControllerSettings *> (controllerSettingsVa);
    pConstraintSettings->mController
            = Ref<VehicleControllerSettings>(pControllerSettings);
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraintSettings
 * Method:    setForward
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleConstraintSettings_setForward
  (JNIEnv *, jclass, jlong settingsVa, jfloat dx, jfloat dy, jfloat dz) {
    VehicleConstraintSettings * const pSettings
            = reinterpret_cast<VehicleConstraintSettings *> (settingsVa);
    const Vec3 forward(dx, dy, dz);
    pSettings->mForward = forward;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraintSettings
 * Method:    setMaxPitchRollAngle
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleConstraintSettings_setMaxPitchRollAngle
  (JNIEnv *, jclass, jlong settingsVa, jfloat angle) {
    VehicleConstraintSettings * const pSettings
            = reinterpret_cast<VehicleConstraintSettings *> (settingsVa);
    pSettings->mMaxPitchRollAngle = angle;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraintSettings
 * Method:    setNumAntiRollBars
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleConstraintSettings_setNumAntiRollBars
  (JNIEnv *, jclass, jlong settingsVa, int count) {
    VehicleConstraintSettings * const pSettings
            = reinterpret_cast<VehicleConstraintSettings *> (settingsVa);
    pSettings->mAntiRollBars.resize(count);
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraintSettings
 * Method:    setUp
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleConstraintSettings_setUp
  (JNIEnv *, jclass, jlong settingsVa, jfloat dx, jfloat dy, jfloat dz) {
    VehicleConstraintSettings * const pSettings
            = reinterpret_cast<VehicleConstraintSettings *> (settingsVa);
    const Vec3 up(dx, dy, dz);
    pSettings->mUp = up;
}