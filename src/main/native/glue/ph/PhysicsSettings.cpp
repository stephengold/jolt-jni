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
#include "Jolt/Physics/PhysicsSettings.h"
#include "auto/com_github_stephengold_joltjni_PhysicsSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_createCopy
  BODYOF_CREATE_COPY(PhysicsSettings)

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_createDefault
  BODYOF_CREATE_DEFAULT(PhysicsSettings)

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_free
  BODYOF_FREE(PhysicsSettings)

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    getAllowSleeping
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_getAllowSleeping
  (JNIEnv *, jclass, jlong settingsVa) {
    const PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    const bool result = pSettings->mAllowSleeping;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    getBaumgarte
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_getBaumgarte
  (JNIEnv *, jclass, jlong settingsVa) {
    const PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    const float result = pSettings->mBaumgarte;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    getDeterministicSimulation
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_getDeterministicSimulation
  (JNIEnv *, jclass, jlong settingsVa) {
    const PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    const bool result = pSettings->mDeterministicSimulation;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    getNumPositionSteps
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_getNumPositionSteps
  (JNIEnv *, jclass, jlong settingsVa) {
    const PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    const uint result = pSettings->mNumPositionSteps;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    getNumVelocitySteps
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_getNumVelocitySteps
  (JNIEnv *, jclass, jlong settingsVa) {
    const PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    const uint result = pSettings->mNumVelocitySteps;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    getPenetrationSlop
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_getPenetrationSlop
  (JNIEnv *, jclass, jlong settingsVa) {
    const PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    const float result = pSettings->mPenetrationSlop;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    getPointVelocitySleepThreshold
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_getPointVelocitySleepThreshold
  (JNIEnv *, jclass, jlong settingsVa) {
    const PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    const float result = pSettings->mPointVelocitySleepThreshold;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    getTimeBeforeSleep
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_getTimeBeforeSleep
  (JNIEnv *, jclass, jlong settingsVa) {
    const PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    const float result = pSettings->mTimeBeforeSleep;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    setAllowSleeping
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_setAllowSleeping
  (JNIEnv *, jclass, jlong settingsVa, jboolean allow) {
    PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    pSettings->mAllowSleeping = allow;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    setBaumgarte
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_setBaumgarte
  (JNIEnv *, jclass, jlong settingsVa, jfloat fraction) {
    PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    pSettings->mBaumgarte = fraction;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    setDeterministicSimulation
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_setDeterministicSimulation
  (JNIEnv *, jclass, jlong settingsVa, jboolean setting) {
    PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    pSettings->mDeterministicSimulation = setting;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    setNumPositionSteps
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_setNumPositionSteps
  (JNIEnv *, jclass, jlong settingsVa, jint numSteps) {
    PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    pSettings->mNumPositionSteps = numSteps;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    setNumVelocitySteps
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_setNumVelocitySteps
  (JNIEnv *, jclass, jlong settingsVa, jint numSteps) {
    PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    pSettings->mNumVelocitySteps = numSteps;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    setPenetrationSlop
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_setPenetrationSlop
  (JNIEnv *, jclass, jlong settingsVa, jfloat slop) {
    PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    pSettings->mPenetrationSlop = slop;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    setPointVelocitySleepThreshold
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_setPointVelocitySleepThreshold
  (JNIEnv *, jclass, jlong settingsVa, jfloat speed) {
    PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    pSettings->mPointVelocitySleepThreshold = speed;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    setTimeBeforeSleep
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_setTimeBeforeSleep
  (JNIEnv *, jclass, jlong settingsVa, jfloat interval) {
    PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    pSettings->mTimeBeforeSleep = interval;
}