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
#include <Jolt/Physics/Body/MotionProperties.h>
#include "auto/com_github_stephengold_joltjni_MotionProperties.h"
#include "glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    createMotionProperties
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_MotionProperties_createMotionProperties
  (JNIEnv *, jclass) {
    MotionProperties * const pProperties = new MotionProperties();
    TRACE_NEW("MotionProperties", pProperties)
    return reinterpret_cast<jlong> (pProperties);
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotionProperties_free
  (JNIEnv *, jclass, jlong propertiesVa) {
    MotionProperties * const pProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    TRACE_DELETE("MotionProperties", pProperties)
    delete pProperties;
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    getAccumulatedForceX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MotionProperties_getAccumulatedForceX
  (JNIEnv *, jclass, jlong propertiesVa) {
    const MotionProperties * const pProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    const float result = pProperties->GetAccumulatedForce().GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    getAccumulatedForceY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MotionProperties_getAccumulatedForceY
  (JNIEnv *, jclass, jlong propertiesVa) {
    const MotionProperties * const pProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    const float result = pProperties->GetAccumulatedForce().GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    getAccumulatedForceZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MotionProperties_getAccumulatedForceZ
  (JNIEnv *, jclass, jlong propertiesVa) {
    const MotionProperties * const pProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    const float result = pProperties->GetAccumulatedForce().GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    getAccumulatedTorqueX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MotionProperties_getAccumulatedTorqueX
  (JNIEnv *, jclass, jlong propertiesVa) {
    const MotionProperties * const pProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    const float result = pProperties->GetAccumulatedTorque().GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    getAccumulatedTorqueY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MotionProperties_getAccumulatedTorqueY
  (JNIEnv *, jclass, jlong propertiesVa) {
    const MotionProperties * const pProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    const float result = pProperties->GetAccumulatedTorque().GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    getAccumulatedTorqueZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MotionProperties_getAccumulatedTorqueZ
  (JNIEnv *, jclass, jlong propertiesVa) {
    const MotionProperties * const pProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    const float result = pProperties->GetAccumulatedTorque().GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    getAllowedDofs
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_MotionProperties_getAllowedDofs
  (JNIEnv *, jclass, jlong propertiesVa) {
    const MotionProperties * const pProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    const EAllowedDOFs result = pProperties->GetAllowedDOFs();
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    getAllowSleeping
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_MotionProperties_getAllowSleeping
  (JNIEnv *, jclass, jlong propertiesVa) {
    const MotionProperties * const pProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    const bool result = pProperties->GetAllowSleeping();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    getAngularDamping
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MotionProperties_getAngularDamping
  (JNIEnv *, jclass, jlong propertiesVa) {
    const MotionProperties * const pProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    const float result = pProperties->GetAngularDamping();
    return result;
}

inline static const Vec3 getAngularVelocity(jlong propertiesVa) {
    const MotionProperties * const pProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    const Vec3 result = pProperties->GetAngularVelocity();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    getAngularVelocityX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MotionProperties_getAngularVelocityX
  (JNIEnv *, jclass, jlong propertiesVa) {
    const Vec3 vec3 = getAngularVelocity(propertiesVa);
    const float result = vec3.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    getAngularVelocityY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MotionProperties_getAngularVelocityY
  (JNIEnv *, jclass, jlong propertiesVa) {
    const Vec3 vec3 = getAngularVelocity(propertiesVa);
    const float result = vec3.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    getAngularVelocityZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MotionProperties_getAngularVelocityZ
  (JNIEnv *, jclass, jlong propertiesVa) {
    const Vec3 vec3 = getAngularVelocity(propertiesVa);
    const float result = vec3.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    getGravityFactor
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MotionProperties_getGravityFactor
  (JNIEnv *, jclass, jlong propertiesVa) {
    const MotionProperties * const pProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    const float result = pProperties->GetGravityFactor();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    getInverseInertiaXX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MotionProperties_getInverseInertiaXX
  (JNIEnv *, jclass, jlong propertiesVa) {
    const MotionProperties * const pProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    const float result = pProperties->GetInverseInertiaDiagonal().GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    getInverseInertiaYY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MotionProperties_getInverseInertiaYY
  (JNIEnv *, jclass, jlong propertiesVa) {
    const MotionProperties * const pProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    const float result = pProperties->GetInverseInertiaDiagonal().GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    getInverseInertiaZZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MotionProperties_getInverseInertiaZZ
  (JNIEnv *, jclass, jlong propertiesVa) {
    const MotionProperties * const pProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    const float result = pProperties->GetInverseInertiaDiagonal().GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    getInverseMass
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MotionProperties_getInverseMass
  (JNIEnv *, jclass, jlong propertiesVa) {
    const MotionProperties * const pProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    const float result = pProperties->GetInverseMass();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    getInverseMassUnchecked
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MotionProperties_getInverseMassUnchecked
  (JNIEnv *, jclass, jlong propertiesVa) {
    const MotionProperties * const pProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    const float result = pProperties->GetInverseMassUnchecked();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    getLinearDamping
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MotionProperties_getLinearDamping
  (JNIEnv *, jclass, jlong propertiesVa) {
    const MotionProperties * const pProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    const float result = pProperties->GetLinearDamping();
    return result;
}

inline static const Vec3 getLinearVelocity(jlong propertiesVa) {
    const MotionProperties * const pProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    const Vec3 result = pProperties->GetLinearVelocity();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    getLinearVelocityX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MotionProperties_getLinearVelocityX
  (JNIEnv *, jclass, jlong propertiesVa) {
    const Vec3 vec3 = getLinearVelocity(propertiesVa);
    const float result = vec3.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    getLinearVelocityY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MotionProperties_getLinearVelocityY
  (JNIEnv *, jclass, jlong propertiesVa) {
    const Vec3 vec3 = getLinearVelocity(propertiesVa);
    const float result = vec3.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    getLinearVelocityZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MotionProperties_getLinearVelocityZ
  (JNIEnv *, jclass, jlong propertiesVa) {
    const Vec3 vec3 = getLinearVelocity(propertiesVa);
    const float result = vec3.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    getMaxAngularVelocity
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MotionProperties_getMaxAngularVelocity
  (JNIEnv *, jclass, jlong propertiesVa) {
    const MotionProperties * const pProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    const float result = pProperties->GetMaxAngularVelocity();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    getMaxLinearVelocity
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MotionProperties_getMaxLinearVelocity
  (JNIEnv *, jclass, jlong propertiesVa) {
    const MotionProperties * const pProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    const float result = pProperties->GetMaxLinearVelocity();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    getMotionQuality
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_MotionProperties_getMotionQuality
  (JNIEnv *, jclass, jlong propertiesVa) {
    const MotionProperties * const pProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    const EMotionQuality result = pProperties->GetMotionQuality();
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    getNumPositionStepsOverride
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_MotionProperties_getNumPositionStepsOverride
  (JNIEnv *, jclass, jlong propertiesVa) {
    const MotionProperties * const pProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    const uint result = pProperties->GetNumPositionStepsOverride();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    getNumVelocityStepsOverride
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_MotionProperties_getNumVelocityStepsOverride
  (JNIEnv *, jclass, jlong propertiesVa) {
    const MotionProperties * const pProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    const uint result = pProperties->GetNumVelocityStepsOverride();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    moveKinematic
 * Signature: (JFFFFFFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotionProperties_moveKinematic
  (JNIEnv *, jclass, jlong propertiesVa, jfloat dx, jfloat dy, jfloat dz,
  jfloat qx, jfloat qy, jfloat qz, jfloat qw, jfloat deltaTime) {
    MotionProperties * const pProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    const Vec3 offset(dx, dy, dz);
    const Quat rotation(qx, qy, qz, qw);
    pProperties->MoveKinematic(offset, rotation, deltaTime);
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    resetForce
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotionProperties_resetForce
  (JNIEnv *, jclass, jlong propertiesVa) {
    MotionProperties * const pProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    pProperties->ResetForce();
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    resetTorque
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotionProperties_resetTorque
  (JNIEnv *, jclass, jlong propertiesVa) {
    MotionProperties * const pProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    pProperties->ResetTorque();
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    setAngularDamping
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotionProperties_setAngularDamping
  (JNIEnv *, jclass, jlong propertiesVa, jfloat damping) {
    MotionProperties * const pProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    pProperties->SetAngularDamping(damping);
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    setAngularVelocity
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotionProperties_setAngularVelocity
  (JNIEnv *, jclass, jlong propertiesVa, jfloat wx, jfloat wy, jfloat wz) {
    MotionProperties * const pProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    const Vec3 omega(wx, wy, wz);
    pProperties->SetAngularVelocity(omega);
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    setGravityFactor
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotionProperties_setGravityFactor
  (JNIEnv *, jclass, jlong propertiesVa, jfloat factor) {
    MotionProperties * const pProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    pProperties->SetGravityFactor(factor);
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    setInverseInertia
 * Signature: (JFFFFFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotionProperties_setInverseInertia
  (JNIEnv *, jclass, jlong propertiesVa, jfloat dx, jfloat dy, jfloat dz,
  jfloat rx, jfloat ry, jfloat rz, jfloat rw) {
    MotionProperties * const pProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    const Vec3 diagonal(dx, dy, dz);
    const Quat rotation(rx, ry, rz, rw);
    pProperties->SetInverseInertia(diagonal, rotation);
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    setInverseMass
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotionProperties_setInverseMass
  (JNIEnv *, jclass, jlong propertiesVa, jfloat inverseMass) {
    MotionProperties * const pProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    pProperties->SetInverseMass(inverseMass);
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    setLinearDamping
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotionProperties_setLinearDamping
  (JNIEnv *, jclass, jlong propertiesVa, jfloat damping) {
    MotionProperties * const pProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    pProperties->SetLinearDamping(damping);
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    setLinearVelocity
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotionProperties_setLinearVelocity
  (JNIEnv *, jclass, jlong propertiesVa, jfloat vx, jfloat vy, jfloat vz) {
    MotionProperties * const pProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    Vec3 velocity(vx, vy, vz);
    pProperties->SetLinearVelocity(velocity);
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    setMassProperties
 * Signature: (JIJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotionProperties_setMassProperties
  (JNIEnv *, jclass, jlong propertiesVa, jint allowedDofs, jlong massPropsVa) {
    MotionProperties * const pMotionProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    const MassProperties * const pMassProperties
            = reinterpret_cast<MassProperties *> (massPropsVa);
    const EAllowedDOFs dofs = (EAllowedDOFs) allowedDofs;
    pMotionProperties->SetMassProperties(dofs, *pMassProperties);
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    setMaxAngularVelocity
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotionProperties_setMaxAngularVelocity
  (JNIEnv *, jclass, jlong propertiesVa, jfloat maxSpeed) {
    MotionProperties * const pMotionProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    pMotionProperties->SetMaxAngularVelocity(maxSpeed);
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    setMaxLinearVelocity
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotionProperties_setMaxLinearVelocity
  (JNIEnv *, jclass, jlong propertiesVa, jfloat maxSpeed) {
    MotionProperties * const pMotionProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    pMotionProperties->SetMaxLinearVelocity(maxSpeed);
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    setNumPositionStepsOverride
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotionProperties_setNumPositionStepsOverride
  (JNIEnv *, jclass, jlong propertiesVa, jint numIterations) {
    MotionProperties * const pMotionProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    pMotionProperties->SetNumPositionStepsOverride(numIterations);
}

/*
 * Class:     com_github_stephengold_joltjni_MotionProperties
 * Method:    setNumVelocityStepsOverride
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotionProperties_setNumVelocityStepsOverride
  (JNIEnv *, jclass, jlong propertiesVa, jint numIterations) {
    MotionProperties * const pMotionProperties
            = reinterpret_cast<MotionProperties *> (propertiesVa);
    pMotionProperties->SetNumVelocityStepsOverride(numIterations);
}
