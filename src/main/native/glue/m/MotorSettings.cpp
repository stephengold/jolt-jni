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
#include "Jolt/Physics/Constraints/MotorSettings.h"
#include "auto/com_github_stephengold_joltjni_MotorSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_MotorSettings
 * Method:    createUnlimited
 * Signature: (FF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_MotorSettings_createUnlimited
  (JNIEnv *, jclass, jfloat frequency, jfloat damping) {
    MotorSettings * const pResult = new MotorSettings(frequency, damping);
    TRACE_NEW("MotorSettings", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_MotorSettings
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotorSettings_free
  (JNIEnv *, jclass, jlong settingsVa) {
    MotorSettings * const pSettings
            = reinterpret_cast<MotorSettings *> (settingsVa);
    TRACE_DELETE("MotorSettings", pSettings)
    delete pSettings;
}

/*
 * Class:     com_github_stephengold_joltjni_MotorSettings
 * Method:    getMaxForceLimit
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MotorSettings_getMaxForceLimit
  (JNIEnv *, jclass, jlong settingsVa) {
    const MotorSettings * const pSettings
            = reinterpret_cast<MotorSettings *> (settingsVa);
    const float result = pSettings->mMaxForceLimit;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotorSettings
 * Method:    getMaxTorqueLimit
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MotorSettings_getMaxTorqueLimit
  (JNIEnv *, jclass, jlong settingsVa) {
    const MotorSettings * const pSettings
            = reinterpret_cast<MotorSettings *> (settingsVa);
    const float result = pSettings->mMaxTorqueLimit;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotorSettings
 * Method:    getMinForceLimit
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MotorSettings_getMinForceLimit
  (JNIEnv *, jclass, jlong settingsVa) {
    const MotorSettings * const pSettings
            = reinterpret_cast<MotorSettings *> (settingsVa);
    const float result = pSettings->mMinForceLimit;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotorSettings
 * Method:    getMinTorqueLimit
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MotorSettings_getMinTorqueLimit
  (JNIEnv *, jclass, jlong settingsVa) {
    const MotorSettings * const pSettings
            = reinterpret_cast<MotorSettings *> (settingsVa);
    const float result = pSettings->mMinTorqueLimit;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotorSettings
 * Method:    getSpringSettings
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_MotorSettings_getSpringSettings
  (JNIEnv *, jclass, jlong motorSettingsVa) {
    MotorSettings * const pMotorSettings
            = reinterpret_cast<MotorSettings *> (motorSettingsVa);
    SpringSettings * const pResult = &pMotorSettings->mSpringSettings;
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_MotorSettings
 * Method:    isValid
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_MotorSettings_isValid
  (JNIEnv *, jclass, jlong settingsVa) {
    const MotorSettings * const pSettings
            = reinterpret_cast<MotorSettings *> (settingsVa);
    const bool result = pSettings->IsValid();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotorSettings
 * Method:    setForceLimit
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotorSettings_setForceLimit
  (JNIEnv *, jclass, jlong settingsVa, jfloat limit) {
    MotorSettings * const pSettings
            = reinterpret_cast<MotorSettings *> (settingsVa);
    pSettings->SetForceLimit(limit);
}

/*
 * Class:     com_github_stephengold_joltjni_MotorSettings
 * Method:    setForceLimits
 * Signature: (JFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotorSettings_setForceLimits
  (JNIEnv *, jclass, jlong settingsVa, jfloat min, jfloat max) {
    MotorSettings * const pSettings
            = reinterpret_cast<MotorSettings *> (settingsVa);
    pSettings->SetForceLimits(min, max);
}

/*
 * Class:     com_github_stephengold_joltjni_MotorSettings
 * Method:    setMaxForceLimit
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotorSettings_setMaxForceLimit
  (JNIEnv *, jclass, jlong settingsVa, jfloat force) {
    MotorSettings * const pSettings
            = reinterpret_cast<MotorSettings *> (settingsVa);
    pSettings->mMaxForceLimit = force;
}

/*
 * Class:     com_github_stephengold_joltjni_MotorSettings
 * Method:    setMaxTorqueLimit
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotorSettings_setMaxTorqueLimit
  (JNIEnv *, jclass, jlong settingsVa, jfloat torque) {
    MotorSettings * const pSettings
            = reinterpret_cast<MotorSettings *> (settingsVa);
    pSettings->mMaxTorqueLimit = torque;
}

/*
 * Class:     com_github_stephengold_joltjni_MotorSettings
 * Method:    setMinForceLimit
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotorSettings_setMinForceLimit
  (JNIEnv *, jclass, jlong settingsVa, jfloat force) {
    MotorSettings * const pSettings
            = reinterpret_cast<MotorSettings *> (settingsVa);
    pSettings->mMinForceLimit = force;
}

/*
 * Class:     com_github_stephengold_joltjni_MotorSettings
 * Method:    setMinTorqueLimit
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotorSettings_setMinTorqueLimit
  (JNIEnv *, jclass, jlong settingsVa, jfloat torque) {
    MotorSettings * const pSettings
            = reinterpret_cast<MotorSettings *> (settingsVa);
    pSettings->mMinTorqueLimit = torque;
}

/*
 * Class:     com_github_stephengold_joltjni_MotorSettings
 * Method:    setTorqueLimit
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotorSettings_setTorqueLimit
  (JNIEnv *, jclass, jlong settingsVa, jfloat limit) {
    MotorSettings * const pSettings
            = reinterpret_cast<MotorSettings *> (settingsVa);
    pSettings->SetTorqueLimit(limit);
}

/*
 * Class:     com_github_stephengold_joltjni_MotorSettings
 * Method:    setTorqueLimits
 * Signature: (JFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotorSettings_setTorqueLimits
  (JNIEnv *, jclass, jlong settingsVa, jfloat min, jfloat max) {
    MotorSettings * const pSettings
            = reinterpret_cast<MotorSettings *> (settingsVa);
    pSettings->SetTorqueLimits(min, max);
}