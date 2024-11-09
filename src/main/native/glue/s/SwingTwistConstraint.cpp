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
#include "Jolt/Physics/Constraints/SwingTwistConstraint.h"
#include "auto/com_github_stephengold_joltjni_SwingTwistConstraint.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraint
 * Method:    getSwingMotorSettings
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraint_getSwingMotorSettings
  (JNIEnv *, jclass, jlong constraintVa) {
    SwingTwistConstraint * const pConstraint
            = reinterpret_cast<SwingTwistConstraint *> (constraintVa);
    MotorSettings * const pResult = &pConstraint->GetSwingMotorSettings();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraint
 * Method:    getTwistMotorSettings
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraint_getTwistMotorSettings
  (JNIEnv *, jclass, jlong constraintVa) {
    SwingTwistConstraint * const pConstraint
            = reinterpret_cast<SwingTwistConstraint *> (constraintVa);
    MotorSettings * const pResult = &pConstraint->GetTwistMotorSettings();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraint
 * Method:    setMaxFrictionTorque
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraint_setMaxFrictionTorque
  (JNIEnv *, jclass, jlong constraintVa, jfloat torque) {
    SwingTwistConstraint * const pConstraint
            = reinterpret_cast<SwingTwistConstraint *> (constraintVa);
    pConstraint->SetMaxFrictionTorque(torque);
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraint
 * Method:    setNormalHalfConeAngle
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraint_setNormalHalfConeAngle
  (JNIEnv *, jclass, jlong constraintVa, jfloat angle) {
    SwingTwistConstraint * const pConstraint
            = reinterpret_cast<SwingTwistConstraint *> (constraintVa);
    pConstraint->SetNormalHalfConeAngle(angle);
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraint
 * Method:    setPlaneHalfConeAngle
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraint_setPlaneHalfConeAngle
  (JNIEnv *, jclass, jlong constraintVa, jfloat angle) {
    SwingTwistConstraint * const pConstraint
            = reinterpret_cast<SwingTwistConstraint *> (constraintVa);
    pConstraint->SetPlaneHalfConeAngle(angle);
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraint
 * Method:    setSwingMotorState
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraint_setSwingMotorState
  (JNIEnv *, jclass, jlong constraintVa, jint ordinal) {
    SwingTwistConstraint * const pConstraint
            = reinterpret_cast<SwingTwistConstraint *> (constraintVa);
    const EMotorState state = (EMotorState) ordinal;
    pConstraint->SetSwingMotorState(state);
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraint
 * Method:    setTwistMaxAngle
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraint_setTwistMaxAngle
  (JNIEnv *, jclass, jlong constraintVa, jfloat angle) {
    SwingTwistConstraint * const pConstraint
            = reinterpret_cast<SwingTwistConstraint *> (constraintVa);
    pConstraint->SetTwistMaxAngle(angle);
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraint
 * Method:    setTwistMinAngle
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraint_setTwistMinAngle
  (JNIEnv *, jclass, jlong constraintVa, jfloat angle) {
    SwingTwistConstraint * const pConstraint
            = reinterpret_cast<SwingTwistConstraint *> (constraintVa);
    pConstraint->SetTwistMinAngle(angle);
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraint
 * Method:    setTwistMotorState
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraint_setTwistMotorState
  (JNIEnv *, jclass, jlong constraintVa, jint ordinal) {
    SwingTwistConstraint * const pConstraint
            = reinterpret_cast<SwingTwistConstraint *> (constraintVa);
    const EMotorState state = (EMotorState) ordinal;
    pConstraint->SetTwistMotorState(state);
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraint
 * Method:    setTargetAngularVelocityCs
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraint_setTargetAngularVelocityCs
  (JNIEnv *, jclass, jlong constraintVa, jfloat wx, jfloat wy, jfloat wz) {
    SwingTwistConstraint * const pConstraint
            = reinterpret_cast<SwingTwistConstraint *> (constraintVa);
    const Vec3 omega(wx, wy, wz);
    pConstraint->SetTargetAngularVelocityCS(omega);
}

/*
 * Class:     com_github_stephengold_joltjni_SwingTwistConstraint
 * Method:    setTargetOrientationCs
 * Signature: (JFFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SwingTwistConstraint_setTargetOrientationCs
  (JNIEnv *, jclass, jlong constraintVa, jfloat qx, jfloat qy, jfloat qz, jfloat qw) {
    SwingTwistConstraint * const pConstraint
            = reinterpret_cast<SwingTwistConstraint *> (constraintVa);
    const Quat orientation(qx, qy, qz, qw);
    pConstraint->SetTargetOrientationCS(orientation);
}