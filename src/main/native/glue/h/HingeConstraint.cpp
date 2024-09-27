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
#include "Jolt/Physics/Constraints/HingeConstraint.h"
#include "auto/com_github_stephengold_joltjni_HingeConstraint.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraint
 * Method:    getCurrentAngle
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HingeConstraint_getCurrentAngle
  (JNIEnv *, jclass, jlong constraintVa) {
    const HingeConstraint * const pConstraint
            = reinterpret_cast<HingeConstraint *> (constraintVa);
    const float result = pConstraint->GetCurrentAngle();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraint
 * Method:    getLimitsMax
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HingeConstraint_getLimitsMax
  (JNIEnv *, jclass, jlong constraintVa) {
    const HingeConstraint * const pConstraint
            = reinterpret_cast<HingeConstraint *> (constraintVa);
    const float result = pConstraint->GetLimitsMax();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraint
 * Method:    getLimitsMin
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HingeConstraint_getLimitsMin
  (JNIEnv *, jclass, jlong constraintVa) {
    const HingeConstraint * const pConstraint
            = reinterpret_cast<HingeConstraint *> (constraintVa);
    const float result = pConstraint->GetLimitsMin();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraint
 * Method:    getLimitsSpringSettings
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_HingeConstraint_getLimitsSpringSettings
  (JNIEnv *, jclass, jlong constraintVa) {
    HingeConstraint * const pConstraint
            = reinterpret_cast<HingeConstraint *> (constraintVa);
    SpringSettings * const pResult = &pConstraint->GetLimitsSpringSettings();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraint
 * Method:    getMaxFrictionTorque
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HingeConstraint_getMaxFrictionTorque
  (JNIEnv *, jclass, jlong constraintVa) {
    const HingeConstraint * const pConstraint
            = reinterpret_cast<HingeConstraint *> (constraintVa);
    const float result = pConstraint->GetMaxFrictionTorque();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraint
 * Method:    getMotorSettings
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_HingeConstraint_getMotorSettings
  (JNIEnv *, jclass, jlong constraintVa) {
    HingeConstraint * const pConstraint
            = reinterpret_cast<HingeConstraint *> (constraintVa);
    MotorSettings * const pResult = &pConstraint->GetMotorSettings();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraint
 * Method:    getMotorState
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_HingeConstraint_getMotorState
  (JNIEnv *, jclass, jlong constraintVa) {
    const HingeConstraint * const pConstraint
            = reinterpret_cast<HingeConstraint *> (constraintVa);
    const EMotorState result = pConstraint->GetMotorState();
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraint
 * Method:    getTargetAngle
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HingeConstraint_getTargetAngle
  (JNIEnv *, jclass, jlong constraintVa) {
    const HingeConstraint * const pConstraint
            = reinterpret_cast<HingeConstraint *> (constraintVa);
    const float result = pConstraint->GetTargetAngle();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraint
 * Method:    getTargetAngularVelocity
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HingeConstraint_getTargetAngularVelocity
  (JNIEnv *, jclass, jlong constraintVa) {
    const HingeConstraint * const pConstraint
            = reinterpret_cast<HingeConstraint *> (constraintVa);
    const float result = pConstraint->GetTargetAngularVelocity();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraint
 * Method:    hasLimits
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_HingeConstraint_hasLimits
  (JNIEnv *, jclass, jlong constraintVa) {
    const HingeConstraint * const pConstraint
            = reinterpret_cast<HingeConstraint *> (constraintVa);
    const bool result = pConstraint->HasLimits();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraint
 * Method:    setLimits
 * Signature: (JFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HingeConstraint_setLimits
  (JNIEnv *, jclass, jlong constraintVa, jfloat min, jfloat max) {
    HingeConstraint * const pConstraint
            = reinterpret_cast<HingeConstraint *> (constraintVa);
    pConstraint->SetLimits(min, max);
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraint
 * Method:    setMaxFrictionTorque
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HingeConstraint_setMaxFrictionTorque
  (JNIEnv *, jclass, jlong constraintVa, jfloat torque) {
    HingeConstraint * const pConstraint
            = reinterpret_cast<HingeConstraint *> (constraintVa);
    pConstraint->SetMaxFrictionTorque(torque);
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraint
 * Method:    setMotorState
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HingeConstraint_setMotorState
  (JNIEnv *, jclass, jlong constraintVa, jint ordinal) {
    HingeConstraint * const pConstraint
            = reinterpret_cast<HingeConstraint *> (constraintVa);
    const EMotorState state = (EMotorState) ordinal;
    pConstraint->SetMotorState(state);
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraint
 * Method:    setTargetAngle
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HingeConstraint_setTargetAngle
  (JNIEnv *, jclass, jlong constraintVa, jfloat angle) {
    HingeConstraint * const pConstraint
            = reinterpret_cast<HingeConstraint *> (constraintVa);
    pConstraint->SetTargetAngle(angle);
}

/*
 * Class:     com_github_stephengold_joltjni_HingeConstraint
 * Method:    setTargetAngularVelocity
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HingeConstraint_setTargetAngularVelocity
  (JNIEnv *, jclass, jlong constraintVa, jfloat omega) {
    HingeConstraint * const pConstraint
            = reinterpret_cast<HingeConstraint *> (constraintVa);
    pConstraint->SetTargetAngularVelocity(omega);
}