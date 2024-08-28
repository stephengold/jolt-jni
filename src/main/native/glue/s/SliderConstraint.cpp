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
#include <Jolt/Jolt.h>
#include <Jolt/Physics/Constraints/SliderConstraint.h>
#include "auto/com_github_stephengold_joltjni_SliderConstraint.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraint
 * Method:    getCurrentPosition
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SliderConstraint_getCurrentPosition
  (JNIEnv *, jclass, jlong constraintVa) {
    const SliderConstraint * const pConstraint
            = reinterpret_cast<SliderConstraint *> (constraintVa);
    const float result = pConstraint->GetCurrentPosition();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraint
 * Method:    getLimitsMax
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SliderConstraint_getLimitsMax
  (JNIEnv *, jclass, jlong constraintVa) {
    const SliderConstraint * const pConstraint
            = reinterpret_cast<SliderConstraint *> (constraintVa);
    const float result = pConstraint->GetLimitsMax();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraint
 * Method:    getLimitsMin
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SliderConstraint_getLimitsMin
  (JNIEnv *, jclass, jlong constraintVa) {
    const SliderConstraint * const pConstraint
            = reinterpret_cast<SliderConstraint *> (constraintVa);
    const float result = pConstraint->GetLimitsMin();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraint
 * Method:    getLimitsSpringSettings
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SliderConstraint_getLimitsSpringSettings
  (JNIEnv *, jclass, jlong constraintVa) {
    SliderConstraint * const pConstraint
            = reinterpret_cast<SliderConstraint *> (constraintVa);
    SpringSettings * const pResult = &pConstraint->GetLimitsSpringSettings();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraint
 * Method:    getMaxFrictionForce
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SliderConstraint_getMaxFrictionForce
  (JNIEnv *, jclass, jlong constraintVa) {
    const SliderConstraint * const pConstraint
            = reinterpret_cast<SliderConstraint *> (constraintVa);
    const float result = pConstraint->GetMaxFrictionForce();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraint
 * Method:    getMotorSettings
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SliderConstraint_getMotorSettings
  (JNIEnv *, jclass, jlong constraintVa) {
    SliderConstraint * const pConstraint
            = reinterpret_cast<SliderConstraint *> (constraintVa);
    MotorSettings * const pResult = &pConstraint->GetMotorSettings();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraint
 * Method:    getMotorState
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SliderConstraint_getMotorState
  (JNIEnv *, jclass, jlong constraintVa) {
    const SliderConstraint * const pConstraint
            = reinterpret_cast<SliderConstraint *> (constraintVa);
    const EMotorState result = pConstraint->GetMotorState();
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraint
 * Method:    getTargetPosition
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SliderConstraint_getTargetPosition
  (JNIEnv *, jclass, jlong constraintVa) {
    const SliderConstraint * const pConstraint
            = reinterpret_cast<SliderConstraint *> (constraintVa);
    const float result = pConstraint->GetTargetPosition();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraint
 * Method:    getTargetVelocity
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SliderConstraint_getTargetVelocity
  (JNIEnv *, jclass, jlong constraintVa) {
    const SliderConstraint * const pConstraint
            = reinterpret_cast<SliderConstraint *> (constraintVa);
    const float result = pConstraint->GetTargetVelocity();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraint
 * Method:    hasLimits
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_SliderConstraint_hasLimits
  (JNIEnv *, jclass, jlong constraintVa) {
    const SliderConstraint * const pConstraint
            = reinterpret_cast<SliderConstraint *> (constraintVa);
    const bool result = pConstraint->HasLimits();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraint
 * Method:    setLimits
 * Signature: (JFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SliderConstraint_setLimits
  (JNIEnv *, jclass, jlong constraintVa, jfloat min, jfloat max) {
    SliderConstraint * const pConstraint
            = reinterpret_cast<SliderConstraint *> (constraintVa);
    pConstraint->SetLimits(min, max);
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraint
 * Method:    setMaxFrictionForce
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SliderConstraint_setMaxFrictionForce
  (JNIEnv *, jclass, jlong constraintVa, jfloat force) {
    SliderConstraint * const pConstraint
            = reinterpret_cast<SliderConstraint *> (constraintVa);
    pConstraint->SetMaxFrictionForce(force);
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraint
 * Method:    setMotorState
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SliderConstraint_setMotorState
  (JNIEnv *, jclass, jlong constraintVa, jint ordinal) {
    SliderConstraint * const pConstraint
            = reinterpret_cast<SliderConstraint *> (constraintVa);
    const EMotorState state = (EMotorState) ordinal;
    pConstraint->SetMotorState(state);
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraint
 * Method:    setTargetPosition
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SliderConstraint_setTargetPosition
  (JNIEnv *, jclass, jlong constraintVa, jfloat position) {
    SliderConstraint * const pConstraint
            = reinterpret_cast<SliderConstraint *> (constraintVa);
    pConstraint->SetTargetPosition(position);
}

/*
 * Class:     com_github_stephengold_joltjni_SliderConstraint
 * Method:    setTargetVelocity
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SliderConstraint_setTargetVelocity
  (JNIEnv *, jclass, jlong constraintVa, jfloat velocity) {
    SliderConstraint * const pConstraint
            = reinterpret_cast<SliderConstraint *> (constraintVa);
    pConstraint->SetTargetVelocity(velocity);
}