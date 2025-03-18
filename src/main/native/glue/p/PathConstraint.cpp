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
#include "Jolt/Physics/Constraints/PathConstraint.h"
#include "auto/com_github_stephengold_joltjni_PathConstraint.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_PathConstraint
 * Method:    getMaxFrictionForce
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PathConstraint_getMaxFrictionForce
  (JNIEnv *, jclass, jlong constraintVa) {
    const PathConstraint * const pConstraint
            = reinterpret_cast<PathConstraint *> (constraintVa);
    const float result = pConstraint->GetMaxFrictionForce();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraint
 * Method:    getPath
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PathConstraint_getPath
  (JNIEnv *, jclass, jlong constraintVa) {
    const PathConstraint * const pConstraint
            = reinterpret_cast<PathConstraint *> (constraintVa);
    const PathConstraintPath * const pResult = pConstraint->GetPath();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraint
 * Method:    getPathFraction
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PathConstraint_getPathFraction
  (JNIEnv *, jclass, jlong constraintVa) {
    const PathConstraint * const pConstraint
            = reinterpret_cast<PathConstraint *> (constraintVa);
    const float result = pConstraint->GetPathFraction();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraint
 * Method:    getPositionMotorSettings
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PathConstraint_getPositionMotorSettings
  (JNIEnv *, jclass, jlong constraintVa) {
    PathConstraint * const pConstraint
            = reinterpret_cast<PathConstraint *> (constraintVa);
    MotorSettings * const pResult = &pConstraint->GetPositionMotorSettings();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraint
 * Method:    getPositionMotorState
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_PathConstraint_getPositionMotorState
  (JNIEnv *, jclass, jlong constraintVa) {
    const PathConstraint * const pConstraint
            = reinterpret_cast<PathConstraint *> (constraintVa);
    const EMotorState result = pConstraint->GetPositionMotorState();
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraint
 * Method:    getTargetPathFraction
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PathConstraint_getTargetPathFraction
  (JNIEnv *, jclass, jlong constraintVa) {
    const PathConstraint * const pConstraint
            = reinterpret_cast<PathConstraint *> (constraintVa);
    const float result = pConstraint->GetTargetPathFraction();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraint
 * Method:    getTargetVelocity
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PathConstraint_getTargetVelocity
  (JNIEnv *, jclass, jlong constraintVa) {
    const PathConstraint * const pConstraint
            = reinterpret_cast<PathConstraint *> (constraintVa);
    const float result = pConstraint->GetTargetVelocity();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraint
 * Method:    getTotalLambdaMotor
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PathConstraint_getTotalLambdaMotor
  (JNIEnv *, jclass, jlong constraintVa) {
    const PathConstraint * const pConstraint
            = reinterpret_cast<PathConstraint *> (constraintVa);
    const float result = pConstraint->GetTotalLambdaMotor();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraint
 * Method:    getTotalLambdaPositionLimits
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PathConstraint_getTotalLambdaPositionLimits
  (JNIEnv *, jclass, jlong constraintVa) {
    const PathConstraint * const pConstraint
            = reinterpret_cast<PathConstraint *> (constraintVa);
    const float result = pConstraint->GetTotalLambdaPositionLimits();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraint
 * Method:    getTotalLambdaRotationX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PathConstraint_getTotalLambdaRotationX
  (JNIEnv *, jclass, jlong constraintVa) {
    const PathConstraint * const pConstraint
            = reinterpret_cast<PathConstraint *> (constraintVa);
    const float result = pConstraint->GetTotalLambdaRotation().GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraint
 * Method:    getTotalLambdaRotationY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PathConstraint_getTotalLambdaRotationY
  (JNIEnv *, jclass, jlong constraintVa) {
    const PathConstraint * const pConstraint
            = reinterpret_cast<PathConstraint *> (constraintVa);
    const float result = pConstraint->GetTotalLambdaRotation().GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraint
 * Method:    getTotalLambdaRotationZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PathConstraint_getTotalLambdaRotationZ
  (JNIEnv *, jclass, jlong constraintVa) {
    const PathConstraint * const pConstraint
            = reinterpret_cast<PathConstraint *> (constraintVa);
    const float result = pConstraint->GetTotalLambdaRotation().GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraint
 * Method:    setMaxFrictionForce
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PathConstraint_setMaxFrictionForce
  (JNIEnv *, jclass, jlong constraintVa, jfloat force) {
    PathConstraint * const pConstraint
            = reinterpret_cast<PathConstraint *> (constraintVa);
    pConstraint->SetMaxFrictionForce(force);
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraint
 * Method:    setPath
 * Signature: (JJF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PathConstraint_setPath
  (JNIEnv *, jclass, jlong constraintVa, jlong pathVa, float amount) {
    PathConstraint * const pConstraint
            = reinterpret_cast<PathConstraint *> (constraintVa);
    PathConstraintPath * const pPath
            = reinterpret_cast<PathConstraintPath *> (pathVa);
    pConstraint->SetPath(pPath, amount);
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraint
 * Method:    setPositionMotorState
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PathConstraint_setPositionMotorState
  (JNIEnv *, jclass, jlong constraintVa, jint ordinal) {
    PathConstraint * const pConstraint
            = reinterpret_cast<PathConstraint *> (constraintVa);
    const EMotorState state = (EMotorState) ordinal;
    pConstraint->SetPositionMotorState(state);
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraint
 * Method:    setTargetPathFraction
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PathConstraint_setTargetPathFraction
  (JNIEnv *, jclass, jlong constraintVa, jfloat amount) {
    PathConstraint * const pConstraint
            = reinterpret_cast<PathConstraint *> (constraintVa);
    pConstraint->SetTargetPathFraction(amount);
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraint
 * Method:    setTargetVelocity
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PathConstraint_setTargetVelocity
  (JNIEnv *, jclass, jlong constraintVa, jfloat speed) {
    PathConstraint * const pConstraint
            = reinterpret_cast<PathConstraint *> (constraintVa);
    pConstraint->SetTargetVelocity(speed);
}