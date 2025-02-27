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
#include "Jolt/Physics/Body/BodyInterface.h"
#include "Jolt/Physics/Collision/Shape/Shape.h"

#include "auto/com_github_stephengold_joltjni_BodyInterface.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    activateBody
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_activateBody
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    pInterface->ActivateBody(*pBodyId);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    activateBodiesInAaBox
 * Signature: (JJJJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_activateBodiesInAaBox
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong boxVa, jlong bplFilterVa,
  jlong olFilterVa) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const BroadPhaseLayerFilter * const pBplFilter
            = reinterpret_cast<BroadPhaseLayerFilter *> (bplFilterVa);
    const ObjectLayerFilter * const pOlFilter
            = reinterpret_cast<ObjectLayerFilter *> (olFilterVa);
    pInterface->ActivateBodiesInAABox(*pBox, *pBplFilter, *pOlFilter);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    addBody
 * Signature: (JJI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_addBody
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa, jint activationOrdinal) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    const EActivation eActivation = (EActivation) activationOrdinal;
    pInterface->AddBody(*pBodyId, eActivation);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    addForce
 * Signature: (JJFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_addForce__JJFFF
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa, jfloat fx, jfloat fy, jfloat fz) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    const Vec3 force(fx, fy, fz);
    pInterface->AddForce(*pBodyId, force);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    addForce
 * Signature: (JJFFFDDD)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_addForce__JJFFFDDD
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa,
  jfloat fx, jfloat fy, jfloat fz, jdouble locX, jdouble locY, jdouble locZ) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    const Vec3 force(fx, fy, fz);
    const RVec3 location(locX, locY, locZ);
    pInterface->AddForce(*pBodyId, force, location);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    addImpulse
 * Signature: (JJFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_addImpulse__JJFFF
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa, jfloat jx, jfloat jy, jfloat jz) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    const Vec3 impulse(jx, jy, jz);
    pInterface->AddImpulse(*pBodyId, impulse);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    addImpulse
 * Signature: (JJFFFDDD)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_addImpulse__JJFFFDDD
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa,
  jfloat jx, jfloat jy, jfloat jz, jdouble locX, jdouble locY, jdouble locZ) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    const Vec3 impulse(jx, jy, jz);
    const RVec3 location(locX, locY, locZ);
    pInterface->AddImpulse(*pBodyId, impulse, location);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    addTorque
 * Signature: (JJFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_addTorque
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa, jfloat x, jfloat y, jfloat z) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    const Vec3 torque(x, y, z);
    pInterface->AddTorque(*pBodyId, torque);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    createBody
 * Signature: (JJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BodyInterface_createBody
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong settingsVa) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (settingsVa);
    const Body * const pResult = pInterface->CreateBody(*pSettings);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    createConstraint
 * Signature: (JJJJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BodyInterface_createConstraint
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong settingsVa, jlong body1IdVa,
  jlong body2IdVa) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const TwoBodyConstraintSettings * const pSettings
            = reinterpret_cast<TwoBodyConstraintSettings *> (settingsVa);
    const BodyID * const pBody1Id = reinterpret_cast<BodyID *> (body1IdVa);
    const BodyID * const pBody2Id = reinterpret_cast<BodyID *> (body2IdVa);
    TwoBodyConstraint * const pResult
            = pInterface->CreateConstraint(pSettings, *pBody1Id, *pBody2Id);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    createSoftBody
 * Signature: (JJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BodyInterface_createSoftBody
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong settingsVa) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const SoftBodyCreationSettings * const pSettings
            = reinterpret_cast<SoftBodyCreationSettings *> (settingsVa);
    const Body * const pResult = pInterface->CreateSoftBody(*pSettings);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    deactivateBody
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_deactivateBody
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    pInterface->DeactivateBody(*pBodyId);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    destroyBody
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_destroyBody
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    pInterface->DestroyBody(*pBodyId);
}

inline static const Vec3 getAngularVelocity(jlong bodyInterfaceVa, jlong bodyIdVa) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    const Vec3 result = pInterface->GetAngularVelocity(*pBodyId);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getAngularVelocityX
 * Signature: (JJ)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getAngularVelocityX
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    const Vec3 velocity = getAngularVelocity(bodyInterfaceVa, bodyIdVa);
    return velocity.GetX();
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getAngularVelocityY
 * Signature: (JJ)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getAngularVelocityY
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    const Vec3 velocity = getAngularVelocity(bodyInterfaceVa, bodyIdVa);
    return velocity.GetY();
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getAngularVelocityZ
 * Signature: (JJ)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getAngularVelocityZ
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    const Vec3 velocity = getAngularVelocity(bodyInterfaceVa, bodyIdVa);
    return velocity.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getBodyType
 * Signature: (JJ)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getBodyType
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    const EBodyType result = pInterface->GetBodyType(*pBodyId);
    return (jint) result;
}

inline static const RVec3 getCenterOfMassPosition(jlong bodyInterfaceVa, jlong bodyIdVa) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    const RVec3 result = pInterface->GetCenterOfMassPosition(*pBodyId);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getCenterOfMassPositionX
 * Signature: (JJ)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getCenterOfMassPositionX
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    const RVec3 position = getCenterOfMassPosition(bodyInterfaceVa, bodyIdVa);
    return position.GetX();
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getCenterOfMassPositionY
 * Signature: (JJ)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getCenterOfMassPositionY
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    const RVec3 position = getCenterOfMassPosition(bodyInterfaceVa, bodyIdVa);
    return position.GetY();
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getCenterOfMassPositionZ
 * Signature: (JJ)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getCenterOfMassPositionZ
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    const RVec3 position = getCenterOfMassPosition(bodyInterfaceVa, bodyIdVa);
    return position.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getCenterOfMassTransform
 * Signature: (JJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getCenterOfMassTransform
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    RMat44 * const pResult = new RMat44();
    TRACE_NEW("RMat44", pResult)
    *pResult = pInterface->GetCenterOfMassTransform(*pBodyId);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getFriction
 * Signature: (JJ)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getFriction
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    const float result = pInterface->GetFriction(*pBodyId);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getGravityFactor
 * Signature: (JJ)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getGravityFactor
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    const float result = pInterface->GetGravityFactor(*pBodyId);
    return result;
}

inline static const Vec3 getLinearVelocity(jlong bodyInterfaceVa, jlong bodyIdVa) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    const Vec3 result = pInterface->GetLinearVelocity(*pBodyId);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getLinearVelocityX
 * Signature: (JJ)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getLinearVelocityX
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    const Vec3 velocity = getLinearVelocity(bodyInterfaceVa, bodyIdVa);
    return velocity.GetX();
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getLinearVelocityY
 * Signature: (JJ)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getLinearVelocityY
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    const Vec3 velocity = getLinearVelocity(bodyInterfaceVa, bodyIdVa);
    return velocity.GetY();
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getLinearVelocityZ
 * Signature: (JJ)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getLinearVelocityZ
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    const Vec3 velocity = getLinearVelocity(bodyInterfaceVa, bodyIdVa);
    return velocity.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getMotionQuality
 * Signature: (JJ)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getMotionQuality
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    const EMotionQuality result = pInterface->GetMotionQuality(*pBodyId);
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getMotionType
 * Signature: (JJ)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getMotionType
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    const EMotionType result = pInterface->GetMotionType(*pBodyId);
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getObjectLayer
 * Signature: (JJ)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getObjectLayer
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    const ObjectLayer  result = pInterface->GetObjectLayer(*pBodyId);
    return (jint) result;
}

inline static const RVec3 getPosition(jlong bodyInterfaceVa, jlong bodyIdVa) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    const RVec3 result = pInterface->GetPosition(*pBodyId);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getPositionX
 * Signature: (JJ)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getPositionX
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    const RVec3 position = getPosition(bodyInterfaceVa, bodyIdVa);
    return position.GetX();
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getPositionY
 * Signature: (JJ)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getPositionY
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    const RVec3 position = getPosition(bodyInterfaceVa, bodyIdVa);
    return position.GetY();
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getPositionZ
 * Signature: (JJ)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getPositionZ
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    const RVec3 position = getPosition(bodyInterfaceVa, bodyIdVa);
    return position.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getRestitution
 * Signature: (JJ)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getRestitution
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    const float result = pInterface->GetRestitution(*pBodyId);
    return result;
}

inline static const Quat getRotation(jlong bodyInterfaceVa, jlong bodyIdVa) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    const Quat result = pInterface->GetRotation(*pBodyId);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getRotationW
 * Signature: (JJ)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getRotationW
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    const Quat rotation = getRotation(bodyInterfaceVa, bodyIdVa);
    return rotation.GetW();
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getRotationX
 * Signature: (JJ)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getRotationX
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    const Quat rotation = getRotation(bodyInterfaceVa, bodyIdVa);
    return rotation.GetX();
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getRotationY
 * Signature: (JJ)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getRotationY
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    const Quat rotation = getRotation(bodyInterfaceVa, bodyIdVa);
    return rotation.GetY();
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getRotationZ
 * Signature: (JJ)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getRotationZ
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    const Quat rotation = getRotation(bodyInterfaceVa, bodyIdVa);
    return rotation.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getShape
 * Signature: (JJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getShape
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    ShapeRefC * const pResult = new ShapeRefC();
    TRACE_NEW("ShapeRefC", pResult)
    *pResult = pInterface->GetShape(*pBodyId);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getUserData
 * Signature: (JJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getUserData
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    const uint64 result = pInterface->GetUserData(*pBodyId);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    isActive
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_BodyInterface_isActive
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    const bool result = pInterface->IsActive(*pBodyId);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    isAdded
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_BodyInterface_isAdded
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    const bool result = pInterface->IsAdded(*pBodyId);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    moveKinematic
 * Signature: (JJDDDFFFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_moveKinematic
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa,
  jdouble xx, jdouble yy, jdouble zz, jfloat qx, jfloat qy, jfloat qz, jfloat qw, jfloat deltaTime) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    const RVec3 location(xx, yy, zz);
    const Quat orientation(qx, qy, qz, qw);
    pInterface->MoveKinematic(*pBodyId, location, orientation, deltaTime);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    notifyShapeChanged
 * Signature: (JJFFFZI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_notifyShapeChanged
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa, jfloat prevX,
  jfloat prevY, jfloat prevZ, jboolean updateMassProperties, jint activationOrdinal) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    const Vec3 previous(prevX, prevY, prevZ);
    const EActivation activationMode = (EActivation)activationOrdinal;
    pInterface->NotifyShapeChanged(
            *pBodyId, previous, updateMassProperties, activationMode);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    removeBody
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_removeBody
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    pInterface->RemoveBody(*pBodyId);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    setLinearAndAngularVelocity
 * Signature: (JJFFFFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_setLinearAndAngularVelocity
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa, jfloat vx,
  jfloat vy, jfloat vz, jfloat wx, jfloat wy, jfloat wz) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    const Vec3 linearVelocity(vx, vy, vz);
    const Vec3 angularVelocity(wx, wy, wz);
    pInterface->SetLinearAndAngularVelocity(
            *pBodyId, linearVelocity, angularVelocity);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    setAngularVelocity
 * Signature: (JJFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_setAngularVelocity
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa,
    jfloat wx, jfloat wy, jfloat wz) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    const Vec3 omega(wx, wy, wz);
    pInterface->SetAngularVelocity(*pBodyId, omega);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    setFriction
 * Signature: (JJF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_setFriction
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa, jfloat friction) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    pInterface->SetFriction(*pBodyId, friction);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    setGravityFactor
 * Signature: (JJF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_setGravityFactor
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa, jfloat factor) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    pInterface->SetGravityFactor(*pBodyId, factor);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    setLinearVelocity
 * Signature: (JJFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_setLinearVelocity
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa,
    jfloat vx, jfloat vy, jfloat vz) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    const Vec3 velocity(vx, vy, vz);
    pInterface->SetLinearVelocity(*pBodyId, velocity);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    setMotionQuality
 * Signature: (JJI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_setMotionQuality
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa, jint ordinal) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    const EMotionQuality quality = (EMotionQuality) ordinal;
    pInterface->SetMotionQuality(*pBodyId, quality);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    setMotionType
 * Signature: (JJII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_setMotionType
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa, jint motionOrdinal,
  jint activationOrdinal) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    const EMotionType motionType = (EMotionType) motionOrdinal;
    const EActivation activationType = (EActivation) activationOrdinal;
    pInterface->SetMotionType(*pBodyId, motionType, activationType);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    setObjectLayer
 * Signature: (JJI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_setObjectLayer
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa, jint layerIndex) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    const ObjectLayer layer = (ObjectLayer) layerIndex;
    pInterface->SetObjectLayer(*pBodyId, layer);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    setPosition
 * Signature: (JJDDDI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_setPosition
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa, jdouble locX,
  jdouble locY, jdouble locZ, jint ordinal) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    const RVec3 location(locX, locY, locZ);
    const EActivation activationType = (EActivation) ordinal;
    pInterface->SetPosition(*pBodyId, location, activationType);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    setPositionAndRotation
 * Signature: (JJDDDFFFFI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_setPositionAndRotation
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa, jdouble locX,
  jdouble locY, jdouble locZ, jfloat qx, jfloat qy, jfloat qz, jfloat qw,
  jint ordinal) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    const RVec3 location(locX, locY, locZ);
    const Quat orientation(qx, qy, qz, qw);
    const EActivation activation = (EActivation) ordinal;
    pInterface->SetPositionAndRotation(
            *pBodyId, location, orientation, activation);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    setRestitution
 * Signature: (JJF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_setRestitution
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa, jfloat restitution) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    pInterface->SetRestitution(*pBodyId, restitution);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    setShape
 * Signature: (JJJZI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_setShape
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa, jlong shapeVa,
  jboolean updateMassProperties, jint ordinal) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    const EActivation activation = (EActivation) ordinal;
    pInterface->SetShape(*pBodyId, pShape, updateMassProperties, activation);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    setUserData
 * Signature: (JJJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_setUserData
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa, jlong value) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    pInterface->SetUserData(*pBodyId, value);
}