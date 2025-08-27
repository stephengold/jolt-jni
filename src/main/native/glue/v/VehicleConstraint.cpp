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
#include "Jolt/Physics/PhysicsStepListener.h"
#include "Jolt/Physics/Vehicle/VehicleConstraint.h"

#include "auto/com_github_stephengold_joltjni_VehicleConstraint.h"
#include "auto/com_github_stephengold_joltjni_VehicleConstraintRef.h"
#include "glue/glue.h"

using namespace JPH;

IMPLEMENT_REF(VehicleConstraint,
  Java_com_github_stephengold_joltjni_VehicleConstraintRef_copy,
  Java_com_github_stephengold_joltjni_VehicleConstraintRef_createDefault,
  Java_com_github_stephengold_joltjni_VehicleConstraintRef_free,
  Java_com_github_stephengold_joltjni_VehicleConstraintRef_getPtr,
  Java_com_github_stephengold_joltjni_VehicleConstraintRef_toRefC)

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraint
 * Method:    countAntiRollBars
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_VehicleConstraint_countAntiRollBars
  (JNIEnv *, jclass, jlong constraintVa) {
    const VehicleConstraint * const pConstraint
            = reinterpret_cast<VehicleConstraint *> (constraintVa);
    const VehicleAntiRollBars &array = pConstraint->GetAntiRollBars();
    const VehicleAntiRollBars::size_type result = array.size();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraint
 * Method:    countWheels
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_VehicleConstraint_countWheels
  (JNIEnv *, jclass, jlong constraintVa) {
    const VehicleConstraint * const pConstraint
            = reinterpret_cast<VehicleConstraint *> (constraintVa);
    const Wheels::size_type result = pConstraint->GetWheels().size();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraint
 * Method:    createConstraint
 * Signature: (JJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_VehicleConstraint_createConstraint
  (JNIEnv *, jclass, jlong bodyVa, jlong settingsVa) {
    Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    VehicleConstraintSettings * const pSettings
            = reinterpret_cast<VehicleConstraintSettings *> (settingsVa);
    VehicleConstraint * const pResult
            = new VehicleConstraint(*pBody, *pSettings);
    TRACE_NEW("VehicleConstraint", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraint
 * Method:    getAntiRollBar
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_VehicleConstraint_getAntiRollBar
  (JNIEnv *, jclass, jlong constraintVa, jint index) {
    VehicleConstraint * const pConstraint
            = reinterpret_cast<VehicleConstraint *> (constraintVa);
    VehicleAntiRollBars &array = pConstraint->GetAntiRollBars();
    VehicleAntiRollBar &result = array[index];
    return reinterpret_cast<jlong> (&result);
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraint
 * Method:    getController
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_VehicleConstraint_getController
  (JNIEnv *, jclass, jlong constraintVa) {
    VehicleConstraint * const pConstraint
            = reinterpret_cast<VehicleConstraint *> (constraintVa);
    VehicleController * const pResult = pConstraint->GetController();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraint
 * Method:    getGravityOverride
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleConstraint_getGravityOverride
  (JNIEnv *pEnv, jclass, jlong constraintVa, jobject storeFloats) {
    const VehicleConstraint * const pConstraint
            = reinterpret_cast<VehicleConstraint *> (constraintVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3 result = pConstraint->GetGravityOverride();
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraint
 * Method:    getLocalForward
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleConstraint_getLocalForward
  (JNIEnv *pEnv, jclass, jlong constraintVa, jobject storeFloats) {
    const VehicleConstraint * const pConstraint
            = reinterpret_cast<VehicleConstraint *> (constraintVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3 result = pConstraint->GetLocalForward();
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraint
 * Method:    getLocalUp
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleConstraint_getLocalUp
  (JNIEnv *pEnv, jclass, jlong constraintVa, jobject storeFloats) {
    const VehicleConstraint * const pConstraint
            = reinterpret_cast<VehicleConstraint *> (constraintVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3 result = pConstraint->GetLocalUp();
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraint
 * Method:    getMaxPitchRollAngle
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_VehicleConstraint_getMaxPitchRollAngle
  (JNIEnv *, jclass, jlong constraintVa) {
    const VehicleConstraint * const pConstraint
            = reinterpret_cast<VehicleConstraint *> (constraintVa);
    const float result = pConstraint->GetMaxPitchRollAngle();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraint
 * Method:    getNumStepsBetweenCollisionTestActive
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_VehicleConstraint_getNumStepsBetweenCollisionTestActive
  (JNIEnv *, jclass, jlong constraintVa) {
    const VehicleConstraint * const pConstraint
            = reinterpret_cast<VehicleConstraint *> (constraintVa);
    const uint result = pConstraint->GetNumStepsBetweenCollisionTestActive();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraint
 * Method:    getNumStepsBetweenCollisionTestInactive
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_VehicleConstraint_getNumStepsBetweenCollisionTestInactive
  (JNIEnv *, jclass, jlong constraintVa) {
    const VehicleConstraint * const pConstraint
            = reinterpret_cast<VehicleConstraint *> (constraintVa);
    const uint result = pConstraint->GetNumStepsBetweenCollisionTestInactive();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraint
 * Method:    getStepListener
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_VehicleConstraint_getStepListener
  (JNIEnv *, jclass, jlong constraintVa) {
    VehicleConstraint * const pConstraint
            = reinterpret_cast<VehicleConstraint *> (constraintVa);
    PhysicsStepListener * const pListener = pConstraint;
    return reinterpret_cast<jlong> (pListener);
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraint
 * Method:    getVehicleBody
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_VehicleConstraint_getVehicleBody
  (JNIEnv *, jclass, jlong constraintVa) {
    VehicleConstraint * const pConstraint
            = reinterpret_cast<VehicleConstraint *> (constraintVa);
    Body * const pResult = pConstraint->GetVehicleBody();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraint
 * Method:    getVehicleCollisionTester
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_VehicleConstraint_getVehicleCollisionTester
  (JNIEnv *, jclass, jlong constraintVa) {
    const VehicleConstraint * const pConstraint
            = reinterpret_cast<VehicleConstraint *> (constraintVa);
    const VehicleCollisionTester * pResult
            = pConstraint->GetVehicleCollisionTester();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraint
 * Method:    getWheel
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_VehicleConstraint_getWheel
  (JNIEnv *, jclass, jlong constraintVa, jint wheelIndex) {
    VehicleConstraint * const pConstraint
            = reinterpret_cast<VehicleConstraint *> (constraintVa);
    Wheel * const pResult = pConstraint->GetWheel(wheelIndex);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraint
 * Method:    getWheelLocalBasis
 * Signature: (JJLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleConstraint_getWheelLocalBasis
  (JNIEnv *pEnv, jclass, jlong constraintVa, jlong wheelVa, jobject storeFloats) {
    const VehicleConstraint * const pConstraint
            = reinterpret_cast<VehicleConstraint *> (constraintVa);
    const Wheel * const pWheel = reinterpret_cast<Wheel *> (wheelVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 9);
    Vec3 forward, up, right;
    pConstraint->GetWheelLocalBasis(pWheel, forward, up, right);
    pFloats[0] = forward.GetX();
    pFloats[1] = forward.GetY();
    pFloats[2] = forward.GetZ();
    pFloats[3] = up.GetX();
    pFloats[4] = up.GetY();
    pFloats[5] = up.GetZ();
    pFloats[6] = right.GetX();
    pFloats[7] = right.GetY();
    pFloats[8] = right.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraint
 * Method:    getWheelWorldTransform
 * Signature: (JIFFFFFF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_VehicleConstraint_getWheelWorldTransform
  (JNIEnv *, jclass, jlong constraintVa, jint wheelIndex, jfloat rx, jfloat ry, jfloat rz,
  jfloat ux, jfloat uy, jfloat uz) {
    const VehicleConstraint * const pConstraint
            = reinterpret_cast<VehicleConstraint *> (constraintVa);
    const Vec3 right(rx, ry, rz);
    const Vec3 up(ux, uy, uz);
    const RMat44 matrix
            = pConstraint->GetWheelWorldTransform(wheelIndex, right, up);
    RMat44 * const pResult = new RMat44(matrix);
    TRACE_NEW("RMat44", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraint
 * Method:    getWorldUp
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleConstraint_getWorldUp
  (JNIEnv *pEnv, jclass, jlong constraintVa, jobject storeFloats) {
    const VehicleConstraint * const pConstraint
            = reinterpret_cast<VehicleConstraint *> (constraintVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3 result = pConstraint->GetWorldUp();
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraint
 * Method:    isGravityOverridden
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_VehicleConstraint_isGravityOverridden
  (JNIEnv *, jclass, jlong constraintVa) {
    const VehicleConstraint * const pConstraint
            = reinterpret_cast<VehicleConstraint *> (constraintVa);
    const bool result = pConstraint->IsGravityOverridden();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraint
 * Method:    overrideGravity
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleConstraint_overrideGravity
  (JNIEnv *, jclass, jlong constraintVa, jfloat ax, jfloat ay, jfloat az) {
    VehicleConstraint * const pConstraint
            = reinterpret_cast<VehicleConstraint *> (constraintVa);
    const Vec3 acceleration(ax, ay, az);
    pConstraint->OverrideGravity(acceleration);
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraint
 * Method:    resetGravityOverride
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleConstraint_resetGravityOverride
  (JNIEnv *, jclass, jlong constraintVa) {
    VehicleConstraint * const pConstraint
            = reinterpret_cast<VehicleConstraint *> (constraintVa);
    pConstraint->ResetGravityOverride();
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraint
 * Method:    setMaxPitchRollAngle
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleConstraint_setMaxPitchRollAngle
  (JNIEnv *, jclass, jlong constraintVa, jfloat angle) {
    VehicleConstraint * const pConstraint
            = reinterpret_cast<VehicleConstraint *> (constraintVa);
    pConstraint->SetMaxPitchRollAngle(angle);
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraint
 * Method:    setNumStepsBetweenCollisionTestActive
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleConstraint_setNumStepsBetweenCollisionTestActive
  (JNIEnv *, jclass, jlong constraintVa, jint numSteps) {
    VehicleConstraint * const pConstraint
            = reinterpret_cast<VehicleConstraint *> (constraintVa);
    pConstraint->SetNumStepsBetweenCollisionTestActive(numSteps);
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraint
 * Method:    setNumStepsBetweenCollisionTestInactive
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleConstraint_setNumStepsBetweenCollisionTestInactive
  (JNIEnv *, jclass, jlong constraintVa, jint numSteps) {
    VehicleConstraint * const pConstraint
            = reinterpret_cast<VehicleConstraint *> (constraintVa);
    pConstraint->SetNumStepsBetweenCollisionTestInactive(numSteps);
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraint
 * Method:    setVehicleCollisionTester
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleConstraint_setVehicleCollisionTester
  (JNIEnv *, jclass, jlong constraintVa, jlong testerVa) {
    VehicleConstraint * const pConstraint
            = reinterpret_cast<VehicleConstraint *> (constraintVa);
    const VehicleCollisionTester * const pTester
            = reinterpret_cast<VehicleCollisionTester *> (testerVa);
    pConstraint->SetVehicleCollisionTester(pTester);
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleConstraint
 * Method:    toRef
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_VehicleConstraint_toRef
  (JNIEnv *, jclass, jlong constraintVa) {
    VehicleConstraint * const pConstraint
            = reinterpret_cast<VehicleConstraint *> (constraintVa);
    Ref<VehicleConstraint> * const pResult
            = new Ref<VehicleConstraint>(pConstraint);
    TRACE_NEW("Ref<VehicleConstraint>", pResult)
    return reinterpret_cast<jlong> (pResult);
}