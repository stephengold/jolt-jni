/*
Copyright (c) 2025 Stephen Gold

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
#include "Jolt/Physics/Vehicle/VehicleTransmission.h"
#include "auto/com_github_stephengold_joltjni_VehicleTransmission.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_VehicleTransmission
 * Method:    allowSleep
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_VehicleTransmission_allowSleep
  (JNIEnv *, jclass, jlong transmissionVa) {
    const VehicleTransmission * const pTransmission
            = reinterpret_cast<VehicleTransmission *> (transmissionVa);
    const bool result = pTransmission->AllowSleep();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleTransmission
 * Method:    getClutchFriction
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_VehicleTransmission_getClutchFriction
  (JNIEnv *, jclass, jlong transmissionVa) {
    const VehicleTransmission * const pTransmission
            = reinterpret_cast<VehicleTransmission *> (transmissionVa);
    const float result = pTransmission->GetClutchFriction();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleTransmission
 * Method:    getCurrentGear
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_VehicleTransmission_getCurrentGear
  (JNIEnv *, jclass, jlong transmissionVa) {
    const VehicleTransmission * const pTransmission
            = reinterpret_cast<VehicleTransmission *> (transmissionVa);
    const int result = pTransmission->GetCurrentGear();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleTransmission
 * Method:    getCurrentRatio
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_VehicleTransmission_getCurrentRatio
  (JNIEnv *, jclass, jlong transmissionVa) {
    const VehicleTransmission * const pTransmission
            = reinterpret_cast<VehicleTransmission *> (transmissionVa);
    const float result = pTransmission->GetCurrentRatio();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleTransmission
 * Method:    isSwitchingGear
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_VehicleTransmission_isSwitchingGear
  (JNIEnv *, jclass, jlong transmissionVa) {
    const VehicleTransmission * const pTransmission
            = reinterpret_cast<VehicleTransmission *> (transmissionVa);
    const bool result = pTransmission->IsSwitchingGear();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleTransmission
 * Method:    set
 * Signature: (JIF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleTransmission_set
  (JNIEnv *, jclass, jlong transmissionVa, jint currentGear, jfloat clutchFriction) {
    VehicleTransmission * const pTransmission
            = reinterpret_cast<VehicleTransmission *> (transmissionVa);
    pTransmission->Set(currentGear, clutchFriction);
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleTransmission
 * Method:    update
 * Signature: (JFFFZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleTransmission_update
  (JNIEnv *, jclass, jlong transmissionVa, jfloat deltaTime, jfloat rpm,
  jfloat forwardInput, jboolean canShiftUp) {
    VehicleTransmission * const pTransmission
            = reinterpret_cast<VehicleTransmission *> (transmissionVa);
    pTransmission->Update(deltaTime, rpm, forwardInput, canShiftUp);
}