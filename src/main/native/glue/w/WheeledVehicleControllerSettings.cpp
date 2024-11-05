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
#include "Jolt/Physics/Vehicle/WheeledVehicleController.h"

#include "auto/com_github_stephengold_joltjni_WheeledVehicleControllerSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_WheeledVehicleControllerSettings
 * Method:    countDifferentials
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_WheeledVehicleControllerSettings_countDifferentials
  (JNIEnv *, jclass, jlong settingsVa) {
    const WheeledVehicleControllerSettings * const pSettings
            = reinterpret_cast<WheeledVehicleControllerSettings *> (settingsVa);
    const Array<VehicleDifferentialSettings>::size_type result
            = pSettings->mDifferentials.size();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_WheeledVehicleControllerSettings
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_WheeledVehicleControllerSettings_createDefault
  (JNIEnv *, jclass) {
    WheeledVehicleControllerSettings * const pSettings
            = new WheeledVehicleControllerSettings();
    TRACE_NEW("WheeledVehicleControllerSettings", pSettings)
    return reinterpret_cast<jlong> (pSettings);
}

/*
 * Class:     com_github_stephengold_joltjni_WheeledVehicleControllerSettings
 * Method:    getDifferential
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_WheeledVehicleControllerSettings_getDifferential
  (JNIEnv *, jclass, jlong settingsVa, jint index) {
    WheeledVehicleControllerSettings * const pSettings
            = reinterpret_cast<WheeledVehicleControllerSettings *> (settingsVa);
    VehicleDifferentialSettings * const pResult
            = &pSettings->mDifferentials[index];
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_WheeledVehicleControllerSettings
 * Method:    getEngine
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_WheeledVehicleControllerSettings_getEngine
  (JNIEnv *, jclass, jlong settingsVa) {
    WheeledVehicleControllerSettings * const pSettings
            = reinterpret_cast<WheeledVehicleControllerSettings *> (settingsVa);
    VehicleEngineSettings * const pResult = &pSettings->mEngine;
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_WheeledVehicleControllerSettings
 * Method:    getTransmission
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_WheeledVehicleControllerSettings_getTransmission
  (JNIEnv *, jclass, jlong settingsVa) {
    WheeledVehicleControllerSettings * const pSettings
            = reinterpret_cast<WheeledVehicleControllerSettings *> (settingsVa);
    VehicleTransmissionSettings * const pResult = &pSettings->mTransmission;
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_WheeledVehicleControllerSettings
 * Method:    setNumDifferentials
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_WheeledVehicleControllerSettings_setNumDifferentials
  (JNIEnv *, jclass, jlong settingsVa, jint count) {
    WheeledVehicleControllerSettings * const pSettings
            = reinterpret_cast<WheeledVehicleControllerSettings *> (settingsVa);
    pSettings->mDifferentials.resize(count);
}