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
#include "Jolt/Physics/Vehicle/VehicleEngine.h"
#include "auto/com_github_stephengold_joltjni_VehicleEngineSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_VehicleEngineSettings
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_VehicleEngineSettings_createCopy
  BODYOF_CREATE_COPY(VehicleEngineSettings)

/*
 * Class:     com_github_stephengold_joltjni_VehicleEngineSettings
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_VehicleEngineSettings_createDefault
  BODYOF_CREATE_DEFAULT(VehicleEngineSettings)

/*
 * Class:     com_github_stephengold_joltjni_VehicleEngineSettings
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleEngineSettings_free
  BODYOF_FREE(VehicleEngineSettings)

/*
 * Class:     com_github_stephengold_joltjni_VehicleEngineSettings
 * Method:    getAngularDamping
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_VehicleEngineSettings_getAngularDamping
  (JNIEnv *, jclass, jlong settingsVa) {
    const VehicleEngineSettings * const pSettings
            = reinterpret_cast<VehicleEngineSettings *> (settingsVa);
    const float result = pSettings->mAngularDamping;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleEngineSettings
 * Method:    getInertia
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_VehicleEngineSettings_getInertia
  (JNIEnv *, jclass, jlong settingsVa) {
    const VehicleEngineSettings * const pSettings
            = reinterpret_cast<VehicleEngineSettings *> (settingsVa);
    const float result = pSettings->mInertia;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleEngineSettings
 * Method:    getMaxRpm
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_VehicleEngineSettings_getMaxRpm
  (JNIEnv *, jclass, jlong settingsVa) {
    const VehicleEngineSettings * const pSettings
            = reinterpret_cast<VehicleEngineSettings *> (settingsVa);
    const float result = pSettings->mMaxRPM;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleEngineSettings
 * Method:    getMaxTorque
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_VehicleEngineSettings_getMaxTorque
  (JNIEnv *, jclass, jlong settingsVa) {
    const VehicleEngineSettings * const pSettings
            = reinterpret_cast<VehicleEngineSettings *> (settingsVa);
    const float result = pSettings->mMaxTorque;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleEngineSettings
 * Method:    getMinRpm
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_VehicleEngineSettings_getMinRpm
  (JNIEnv *, jclass, jlong settingsVa) {
    const VehicleEngineSettings * const pSettings
            = reinterpret_cast<VehicleEngineSettings *> (settingsVa);
    const float result = pSettings->mMinRPM;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleEngineSettings
 * Method:    setAngularDamping
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleEngineSettings_setAngularDamping
  (JNIEnv *, jclass, jlong settingsVa, jfloat damping) {
    VehicleEngineSettings * const pSettings
            = reinterpret_cast<VehicleEngineSettings *> (settingsVa);
    pSettings->mAngularDamping = damping;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleEngineSettings
 * Method:    setInertia
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleEngineSettings_setInertia
  (JNIEnv *, jclass, jlong settingsVa, jfloat inertia) {
    VehicleEngineSettings * const pSettings
            = reinterpret_cast<VehicleEngineSettings *> (settingsVa);
    pSettings->mInertia = inertia;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleEngineSettings
 * Method:    setMaxRpm
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleEngineSettings_setMaxRpm
  (JNIEnv *, jclass, jlong settingsVa, jfloat rpm) {
    VehicleEngineSettings * const pSettings
            = reinterpret_cast<VehicleEngineSettings *> (settingsVa);
    pSettings->mMaxRPM = rpm;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleEngineSettings
 * Method:    setMaxTorque
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleEngineSettings_setMaxTorque
  (JNIEnv *, jclass, jlong settingsVa, jfloat torque) {
    VehicleEngineSettings * const pSettings
            = reinterpret_cast<VehicleEngineSettings *> (settingsVa);
    pSettings->mMaxTorque = torque;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleEngineSettings
 * Method:    setMinRpm
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleEngineSettings_setMinRpm
  (JNIEnv *, jclass, jlong settingsVa, jfloat rpm) {
    VehicleEngineSettings * const pSettings
            = reinterpret_cast<VehicleEngineSettings *> (settingsVa);
    pSettings->mMinRPM = rpm;
}