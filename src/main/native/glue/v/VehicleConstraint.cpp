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
#include "Jolt/Physics/Vehicle/VehicleConstraint.h"
#include "auto/com_github_stephengold_joltjni_VehicleConstraint.h"
#include "glue/glue.h"

using namespace JPH;

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