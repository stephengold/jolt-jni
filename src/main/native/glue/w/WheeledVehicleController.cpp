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
#include "Jolt/Physics/Vehicle/WheeledVehicleController.h"
#include "auto/com_github_stephengold_joltjni_WheeledVehicleController.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_WheeledVehicleController
 * Method:    countDifferentials
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_WheeledVehicleController_countDifferentials
  (JNIEnv *, jclass, jlong controllerVa) {
    const WheeledVehicleController * const pController
            = reinterpret_cast<WheeledVehicleController *> (controllerVa);
    const Array<VehicleDifferentialSettings>& array
            = pController->GetDifferentials();
    const size_t result = array.size();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_WheeledVehicleController
 * Method:    getBrakeInput
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_WheeledVehicleController_getBrakeInput
  (JNIEnv *, jclass, jlong controllerVa) {
    const WheeledVehicleController * const pController
            = reinterpret_cast<WheeledVehicleController *> (controllerVa);
    float result = pController->GetBrakeInput();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_WheeledVehicleController
 * Method:    getDifferential
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_WheeledVehicleController_getDifferential
  (JNIEnv *, jclass, jlong controllerVa, jint index) {
    const WheeledVehicleController * const pController
            = reinterpret_cast<WheeledVehicleController *> (controllerVa);
    const Array<VehicleDifferentialSettings>& array
            = pController->GetDifferentials();
    const VehicleDifferentialSettings& result = array[index];
    return reinterpret_cast<jlong> (&result);
}

/*
 * Class:     com_github_stephengold_joltjni_WheeledVehicleController
 * Method:    getEngine
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_WheeledVehicleController_getEngine
  (JNIEnv *, jclass, jlong controllerVa) {
    WheeledVehicleController * const pController
            = reinterpret_cast<WheeledVehicleController *> (controllerVa);
    VehicleEngine& result = pController->GetEngine();
    return reinterpret_cast<jlong> (&result);
}

/*
 * Class:     com_github_stephengold_joltjni_WheeledVehicleController
 * Method:    getForwardInput
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_WheeledVehicleController_getForwardInput
  (JNIEnv *, jclass, jlong controllerVa) {
    const WheeledVehicleController * const pController
            = reinterpret_cast<WheeledVehicleController *> (controllerVa);
    float result = pController->GetForwardInput();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_WheeledVehicleController
 * Method:    getHandBrakeInput
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_WheeledVehicleController_getHandBrakeInput
  (JNIEnv *, jclass, jlong controllerVa) {
    const WheeledVehicleController * const pController
            = reinterpret_cast<WheeledVehicleController *> (controllerVa);
    float result = pController->GetHandBrakeInput();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_WheeledVehicleController
 * Method:    getRightInput
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_WheeledVehicleController_getRightInput
  (JNIEnv *, jclass, jlong controllerVa) {
    const WheeledVehicleController * const pController
            = reinterpret_cast<WheeledVehicleController *> (controllerVa);
    float result = pController->GetRightInput();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_WheeledVehicleController
 * Method:    getTransmission
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_WheeledVehicleController_getTransmission
  (JNIEnv *, jclass, jlong controllerVa) {
    WheeledVehicleController * const pController
            = reinterpret_cast<WheeledVehicleController *> (controllerVa);
    VehicleTransmission& result = pController->GetTransmission();
    return reinterpret_cast<jlong> (&result);
}

/*
 * Class:     com_github_stephengold_joltjni_WheeledVehicleController
 * Method:    setBrakeInput
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_WheeledVehicleController_setBrakeInput
  (JNIEnv *, jclass, jlong controllerVa, jfloat pressure) {
    WheeledVehicleController * const pController
            = reinterpret_cast<WheeledVehicleController *> (controllerVa);
    pController->SetBrakeInput(pressure);
}

/*
 * Class:     com_github_stephengold_joltjni_WheeledVehicleController
 * Method:    setDifferentialLimitedSlipRatio
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_WheeledVehicleController_setDifferentialLimitedSlipRatio
  (JNIEnv *, jclass, jlong controllerVa, jfloat ratio) {
    WheeledVehicleController * const pController
            = reinterpret_cast<WheeledVehicleController *> (controllerVa);
    pController->SetDifferentialLimitedSlipRatio(ratio);
}

/*
 * Class:     com_github_stephengold_joltjni_WheeledVehicleController
 * Method:    setDriverInput
 * Signature: (JFFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_WheeledVehicleController_setDriverInput
  (JNIEnv *, jclass, jlong controllerVa, jfloat forward, jfloat right,
  jfloat brake, jfloat handBrake) {
    WheeledVehicleController * const pController
            = reinterpret_cast<WheeledVehicleController *> (controllerVa);
    pController->SetDriverInput(forward, right, brake, handBrake);
}

/*
 * Class:     com_github_stephengold_joltjni_WheeledVehicleController
 * Method:    setForwardInput
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_WheeledVehicleController_setForwardInput
  (JNIEnv *, jclass, jlong controllerVa, jfloat forward) {
    WheeledVehicleController * const pController
            = reinterpret_cast<WheeledVehicleController *> (controllerVa);
    pController->SetForwardInput(forward);
}

/*
 * Class:     com_github_stephengold_joltjni_WheeledVehicleController
 * Method:    setHandBrakeInput
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_WheeledVehicleController_setHandBrakeInput
  (JNIEnv *, jclass, jlong controllerVa, jfloat pressure) {
    WheeledVehicleController * const pController
            = reinterpret_cast<WheeledVehicleController *> (controllerVa);
    pController->SetHandBrakeInput(pressure);
}

/*
 * Class:     com_github_stephengold_joltjni_WheeledVehicleController
 * Method:    setRightInput
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_WheeledVehicleController_setRightInput
  (JNIEnv *, jclass, jlong controllerVa, jfloat right) {
    WheeledVehicleController * const pController
            = reinterpret_cast<WheeledVehicleController *> (controllerVa);
    pController->SetRightInput(right);
}