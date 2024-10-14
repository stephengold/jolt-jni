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
#include "Jolt/Physics/Vehicle/MotorcycleController.h"
#include "auto/com_github_stephengold_joltjni_MotorcycleController.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_MotorcycleController
 * Method:    enableLeanController
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotorcycleController_enableLeanController
  (JNIEnv *, jclass, jlong controllerVa, jboolean enable) {
    MotorcycleController * const pController
            = reinterpret_cast<MotorcycleController *> (controllerVa);
    pController->EnableLeanController(enable);
}

/*
 * Class:     com_github_stephengold_joltjni_MotorcycleController
 * Method:    enableLeanSteeringLimit
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotorcycleController_enableLeanSteeringLimit
  (JNIEnv *, jclass, jlong controllerVa, jboolean enable) {
    MotorcycleController * const pController
            = reinterpret_cast<MotorcycleController *> (controllerVa);
    pController->EnableLeanSteeringLimit(enable);
}

/*
 * Class:     com_github_stephengold_joltjni_MotorcycleController
 * Method:    getLeanSmoothingFactor
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MotorcycleController_getLeanSmoothingFactor
  (JNIEnv *, jclass, jlong controllerVa) {
    const MotorcycleController * const pController
            = reinterpret_cast<MotorcycleController *> (controllerVa);
    const float result = pController->GetLeanSmoothingFactor();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotorcycleController
 * Method:    getLeanSpringConstant
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MotorcycleController_getLeanSpringConstant
  (JNIEnv *, jclass, jlong controllerVa) {
    const MotorcycleController * const pController
            = reinterpret_cast<MotorcycleController *> (controllerVa);
    const float result = pController->GetLeanSpringConstant();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotorcycleController
 * Method:    getLeanSpringDamping
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MotorcycleController_getLeanSpringDamping
  (JNIEnv *, jclass, jlong controllerVa) {
    const MotorcycleController * const pController
            = reinterpret_cast<MotorcycleController *> (controllerVa);
    const float result = pController->GetLeanSpringDamping();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotorcycleController
 * Method:    getLeanSpringIntegrationCoefficient
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MotorcycleController_getLeanSpringIntegrationCoefficient
  (JNIEnv *, jclass, jlong controllerVa) {
    const MotorcycleController * const pController
            = reinterpret_cast<MotorcycleController *> (controllerVa);
    const float result = pController->GetLeanSpringIntegrationCoefficient();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotorcycleController
 * Method:    getLeanSpringIntegrationCoefficientDecay
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MotorcycleController_getLeanSpringIntegrationCoefficientDecay
  (JNIEnv *, jclass, jlong controllerVa) {
    const MotorcycleController * const pController
            = reinterpret_cast<MotorcycleController *> (controllerVa);
    const float result = pController->GetLeanSpringIntegrationCoefficientDecay();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotorcycleController
 * Method:    getWheelBase
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MotorcycleController_getWheelBase
  (JNIEnv *, jclass, jlong controllerVa) {
    const MotorcycleController * const pController
            = reinterpret_cast<MotorcycleController *> (controllerVa);
    const float result = pController->GetWheelBase();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotorcycleController
 * Method:    isLeanControllerEnabled
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_MotorcycleController_isLeanControllerEnabled
  (JNIEnv *, jclass, jlong controllerVa) {
    const MotorcycleController * const pController
            = reinterpret_cast<MotorcycleController *> (controllerVa);
    const bool result = pController->IsLeanControllerEnabled();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotorcycleController
 * Method:    isLeanSteeringLimitEnabled
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_MotorcycleController_isLeanSteeringLimitEnabled
  (JNIEnv *, jclass, jlong controllerVa) {
    const MotorcycleController * const pController
            = reinterpret_cast<MotorcycleController *> (controllerVa);
    const bool result = pController->IsLeanSteeringLimitEnabled();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotorcycleController
 * Method:    setLeanSmoothingFactor
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotorcycleController_setLeanSmoothingFactor
  (JNIEnv *, jclass, jlong controllerVa, jfloat factor) {
    MotorcycleController * const pController
            = reinterpret_cast<MotorcycleController *> (controllerVa);
    pController->SetLeanSmoothingFactor(factor);
}

/*
 * Class:     com_github_stephengold_joltjni_MotorcycleController
 * Method:    setLeanSpringConstant
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotorcycleController_setLeanSpringConstant
  (JNIEnv *, jclass, jlong controllerVa, jfloat stiffness) {
    MotorcycleController * const pController
            = reinterpret_cast<MotorcycleController *> (controllerVa);
    pController->SetLeanSpringConstant(stiffness);
}

/*
 * Class:     com_github_stephengold_joltjni_MotorcycleController
 * Method:    setLeanSpringDamping
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotorcycleController_setLeanSpringDamping
  (JNIEnv *, jclass, jlong controllerVa, jfloat damping) {
    MotorcycleController * const pController
            = reinterpret_cast<MotorcycleController *> (controllerVa);
    pController->SetLeanSpringDamping(damping);
}

/*
 * Class:     com_github_stephengold_joltjni_MotorcycleController
 * Method:    setLeanSpringIntegrationCoefficient
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotorcycleController_setLeanSpringIntegrationCoefficient
  (JNIEnv *, jclass, jlong controllerVa, jfloat coefficient) {
    MotorcycleController * const pController
            = reinterpret_cast<MotorcycleController *> (controllerVa);
    pController->SetLeanSpringIntegrationCoefficient(coefficient);
}

/*
 * Class:     com_github_stephengold_joltjni_MotorcycleController
 * Method:    setLeanSpringIntegrationCoefficientDecay
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotorcycleController_setLeanSpringIntegrationCoefficientDecay
  (JNIEnv *, jclass, jlong controllerVa, jfloat decay) {
    MotorcycleController * const pController
            = reinterpret_cast<MotorcycleController *> (controllerVa);
    pController->SetLeanSpringIntegrationCoefficientDecay(decay);
}