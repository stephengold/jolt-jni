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
#include "Jolt/Physics/Vehicle/VehicleEngine.h"
#include "auto/com_github_stephengold_joltjni_VehicleEngine.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_VehicleEngine
 * Method:    allowSleep
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_VehicleEngine_allowSleep
  (JNIEnv *, jclass, jlong engineVa) {
    const VehicleEngine * const pEngine
            = reinterpret_cast<VehicleEngine *> (engineVa);
    const bool result = pEngine->AllowSleep();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleEngine
 * Method:    applyDamping
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleEngine_applyDamping
  (JNIEnv *, jclass, jlong engineVa, jfloat deltaTime) {
    VehicleEngine * const pEngine
            = reinterpret_cast<VehicleEngine *> (engineVa);
    pEngine->ApplyDamping(deltaTime);
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleEngine
 * Method:    applyTorque
 * Signature: (JFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleEngine_applyTorque
  (JNIEnv *, jclass, jlong engineVa, jfloat torque, jfloat deltaTime) {
    VehicleEngine * const pEngine
            = reinterpret_cast<VehicleEngine *> (engineVa);
    pEngine->ApplyTorque(torque, deltaTime);
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleEngine
 * Method:    clampRpm
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleEngine_clampRpm
  (JNIEnv *, jclass, jlong engineVa) {
    VehicleEngine * const pEngine
            = reinterpret_cast<VehicleEngine *> (engineVa);
    pEngine->ClampRPM();
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleEngine
 * Method:    getAngularVelocity
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_VehicleEngine_getAngularVelocity
  (JNIEnv *, jclass, jlong engineVa) {
    const VehicleEngine * const pEngine
            = reinterpret_cast<VehicleEngine *> (engineVa);
    const float result = pEngine->GetAngularVelocity();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleEngine
 * Method:    getCurrentRpm
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_VehicleEngine_getCurrentRpm
  (JNIEnv *, jclass, jlong engineVa) {
    const VehicleEngine * const pEngine
            = reinterpret_cast<VehicleEngine *> (engineVa);
    const float result = pEngine->GetCurrentRPM();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleEngine
 * Method:    getTorque
 * Signature: (JF)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_VehicleEngine_getTorque
  (JNIEnv *, jclass, jlong engineVa, jfloat acceleration) {
    const VehicleEngine * const pEngine
            = reinterpret_cast<VehicleEngine *> (engineVa);
    const float result = pEngine->GetTorque(acceleration);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleEngine
 * Method:    setCurrentRpm
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleEngine_setCurrentRpm
  (JNIEnv *, jclass, jlong engineVa, jfloat rpm) {
    VehicleEngine * const pEngine
            = reinterpret_cast<VehicleEngine *> (engineVa);
    pEngine->SetCurrentRPM(rpm);
}