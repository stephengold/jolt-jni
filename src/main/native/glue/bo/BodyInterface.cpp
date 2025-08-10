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
#include "Jolt/Physics/Body/BodyInterface.h"
#include "Jolt/Physics/Collision/BroadPhase/BroadPhase.h"
#include "Jolt/Physics/Collision/Shape/Shape.h"
#include "Jolt/Physics/Collision/TransformedShape.h"

#include "auto/com_github_stephengold_joltjni_BodyInterface.h"
#include "glue/glue.h"

using namespace JPH;

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
 * Method:    activateBody
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_activateBody
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    pInterface->ActivateBody(id);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    activateConstraint
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_activateConstraint
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong constraintVa) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const TwoBodyConstraint * const pConstraint
            = reinterpret_cast<TwoBodyConstraint *> (constraintVa);
    pInterface->ActivateConstraint(pConstraint);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    addAngularImpulse
 * Signature: (JIFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_addAngularImpulse
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId,
  jfloat lx, jfloat ly, jfloat lz) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    const Vec3 angularImpulse(lx, ly, lz);
    pInterface->AddAngularImpulse(id, angularImpulse);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    addBodiesAbort
 * Signature: (JJIJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_addBodiesAbort
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong arrayVa, jint numBodies,
  jlong addState) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    BodyID * const pArray = reinterpret_cast<BodyID *> (arrayVa);
    BroadPhase::AddState const handle
            = reinterpret_cast<BroadPhase::AddState> (addState);
    pInterface->AddBodiesAbort(pArray, numBodies, handle);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    addBodiesFinalize
 * Signature: (JJIJI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_addBodiesFinalize
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong arrayVa, jint numBodies,
  jlong addState, jint activationOrdinal) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    BodyID * const pArray = reinterpret_cast<BodyID *> (arrayVa);
    BroadPhase::AddState const handle
            = reinterpret_cast<BroadPhase::AddState> (addState);
    const EActivation eActivation = (EActivation) activationOrdinal;
    pInterface->AddBodiesFinalize(pArray, numBodies, handle, eActivation);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    addBodiesPrepare
 * Signature: (JJI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BodyInterface_addBodiesPrepare
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong arrayVa, jint numBodies) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    BodyID * const pArray = reinterpret_cast<BodyID *> (arrayVa);
    BroadPhase::AddState const handle
            = pInterface->AddBodiesPrepare(pArray, numBodies);
    return reinterpret_cast<jlong> (handle);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    addBody
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_addBody
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId, jint activationOrdinal) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    const EActivation eActivation = (EActivation) activationOrdinal;
    pInterface->AddBody(id, eActivation);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    addForce
 * Signature: (JIFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_addForce__JIFFF
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId, jfloat fx, jfloat fy, jfloat fz) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    const Vec3 force(fx, fy, fz);
    pInterface->AddForce(id, force);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    addForce
 * Signature: (JIFFFDDD)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_addForce__JIFFFDDD
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId,
  jfloat fx, jfloat fy, jfloat fz, jdouble locX, jdouble locY, jdouble locZ) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    const Vec3 force(fx, fy, fz);
    const RVec3 location(locX, locY, locZ);
    pInterface->AddForce(id, force, location);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    addImpulse
 * Signature: (JIFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_addImpulse__JIFFF
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId, jfloat jx, jfloat jy, jfloat jz) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    const Vec3 impulse(jx, jy, jz);
    pInterface->AddImpulse(id, impulse);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    addImpulse
 * Signature: (JIFFFDDD)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_addImpulse__JIFFFDDD
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId,
  jfloat jx, jfloat jy, jfloat jz, jdouble locX, jdouble locY, jdouble locZ) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    const Vec3 impulse(jx, jy, jz);
    const RVec3 location(locX, locY, locZ);
    pInterface->AddImpulse(id, impulse, location);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    addTorque
 * Signature: (JIFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_addTorque
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId, jfloat x, jfloat y, jfloat z) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    const Vec3 torque(x, y, z);
    pInterface->AddTorque(id, torque);
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
 * Signature: (JJII)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BodyInterface_createConstraint
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong settingsVa, jint body1Id,
  jint body2Id) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const TwoBodyConstraintSettings * const pSettings
            = reinterpret_cast<TwoBodyConstraintSettings *> (settingsVa);
    const BodyID id1(body1Id);
    const BodyID id2(body2Id);
    TwoBodyConstraint * const pResult
            = pInterface->CreateConstraint(pSettings, id1, id2);
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
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_deactivateBody
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    pInterface->DeactivateBody(id);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    destroyBody
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_destroyBody
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    pInterface->DestroyBody(id);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getAngularVelocity
 * Signature: (JILjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getAngularVelocity
  (JNIEnv *pEnv, jclass, jlong bodyInterfaceVa, jint bodyId, jobject storeFloats) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3 result = pInterface->GetAngularVelocity(id);
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getBodyType
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getBodyType
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    const EBodyType result = pInterface->GetBodyType(id);
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getCenterOfMassPosition
 * Signature: (JILjava/nio/DoubleBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getCenterOfMassPosition
  (JNIEnv *pEnv, jclass, jlong bodyInterfaceVa, jint bodyId, jobject storeDoubles) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    DIRECT_DOUBLE_BUFFER(pEnv, storeDoubles, pDoubles, capacityDoubles);
    JPH_ASSERT(capacityDoubles >= 3);
    const RVec3 result = pInterface->GetCenterOfMassPosition(id);
    pDoubles[0] = result.GetX();
    pDoubles[1] = result.GetY();
    pDoubles[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getCenterOfMassTransform
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getCenterOfMassTransform
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    RMat44 * const pResult = new RMat44();
    TRACE_NEW("RMat44", pResult)
    *pResult = pInterface->GetCenterOfMassTransform(id);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getFriction
 * Signature: (JI)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getFriction
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    const float result = pInterface->GetFriction(id);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getGravityFactor
 * Signature: (JI)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getGravityFactor
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    const float result = pInterface->GetGravityFactor(id);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getLinearVelocity
 * Signature: (JILjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getLinearVelocity
  (JNIEnv *pEnv, jclass, jlong bodyInterfaceVa, jint bodyId, jobject storeFloats) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3 result = pInterface->GetLinearVelocity(id);
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getMotionQuality
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getMotionQuality
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    const EMotionQuality result = pInterface->GetMotionQuality(id);
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getMotionType
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getMotionType
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    const EMotionType result = pInterface->GetMotionType(id);
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getObjectLayer
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getObjectLayer
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    const ObjectLayer result = pInterface->GetObjectLayer(id);
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getPosition
 * Signature: (JILjava/nio/DoubleBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getPosition
  (JNIEnv *pEnv, jclass, jlong bodyInterfaceVa, jint bodyId, jobject storeDoubles) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    DIRECT_DOUBLE_BUFFER(pEnv, storeDoubles, pDoubles, capacityDoubles);
    JPH_ASSERT(capacityDoubles >= 3);
    const RVec3 result = pInterface->GetPosition(id);
    pDoubles[0] = result.GetX();
    pDoubles[1] = result.GetY();
    pDoubles[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getPositionAndRotation
 * Signature: (JILjava/nio/DoubleBuffer;Ljava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getPositionAndRotation
  (JNIEnv *pEnv, jclass, jlong bodyInterfaceVa, jint bodyId,
  jobject storeDoubles, jobject storeFloats) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    DIRECT_DOUBLE_BUFFER(pEnv, storeDoubles, pDoubles, capacityDoubles);
    JPH_ASSERT(capacityDoubles >= 3);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 4);
    RVec3 location;
    Quat orientation;
    pInterface->GetPositionAndRotation(id, location, orientation);
    pDoubles[0] = location.GetX();
    pDoubles[1] = location.GetY();
    pDoubles[2] = location.GetZ();
    pFloats[0] = orientation.GetX();
    pFloats[1] = orientation.GetY();
    pFloats[2] = orientation.GetZ();
    pFloats[3] = orientation.GetW();
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getRestitution
 * Signature: (JI)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getRestitution
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    const float result = pInterface->GetRestitution(id);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getRotation
 * Signature: (JILjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getRotation
  (JNIEnv *pEnv, jclass, jlong bodyInterfaceVa, jint bodyId, jobject storeFloats) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 4);
    const Quat result = pInterface->GetRotation(id);
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
    pFloats[3] = result.GetW();
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getShape
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getShape
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    ShapeRefC * const pResult = new ShapeRefC();
    TRACE_NEW("ShapeRefC", pResult)
    *pResult = pInterface->GetShape(id);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getTransformedShape
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getTransformedShape
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    TransformedShape * const pResult = new TransformedShape();
    *pResult = pInterface->GetTransformedShape(id);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getUserData
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getUserData
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    const uint64 result = pInterface->GetUserData(id);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    isActive
 * Signature: (JI)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_BodyInterface_isActive
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    const bool result = pInterface->IsActive(id);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    isAdded
 * Signature: (JI)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_BodyInterface_isAdded
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    const bool result = pInterface->IsAdded(id);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    isSensor
 * Signature: (JI)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_BodyInterface_isSensor
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    const bool result = pInterface->IsSensor(id);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    moveKinematic
 * Signature: (JIDDDFFFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_moveKinematic
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId,
  jdouble xx, jdouble yy, jdouble zz, jfloat qx, jfloat qy, jfloat qz, jfloat qw, jfloat deltaTime) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    const RVec3 location(xx, yy, zz);
    const Quat orientation(qx, qy, qz, qw);
    pInterface->MoveKinematic(id, location, orientation, deltaTime);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    notifyShapeChanged
 * Signature: (JIFFFZI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_notifyShapeChanged
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId, jfloat prevX,
  jfloat prevY, jfloat prevZ, jboolean updateMassProperties, jint activationOrdinal) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    const Vec3 previous(prevX, prevY, prevZ);
    const EActivation activationMode = (EActivation)activationOrdinal;
    pInterface->NotifyShapeChanged(
            id, previous, updateMassProperties, activationMode);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    removeBody
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_removeBody
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    pInterface->RemoveBody(id);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    setAngularVelocity
 * Signature: (JIFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_setAngularVelocity
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId,
    jfloat wx, jfloat wy, jfloat wz) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    const Vec3 omega(wx, wy, wz);
    pInterface->SetAngularVelocity(id, omega);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    setFriction
 * Signature: (JIF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_setFriction
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId, jfloat friction) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    pInterface->SetFriction(id, friction);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    setGravityFactor
 * Signature: (JIF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_setGravityFactor
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId, jfloat factor) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    pInterface->SetGravityFactor(id, factor);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    setIsSensor
 * Signature: (JIZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_setIsSensor
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId, jboolean setting) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    pInterface->SetIsSensor(id, setting);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    setLinearAndAngularVelocity
 * Signature: (JIFFFFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_setLinearAndAngularVelocity
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId, jfloat vx,
  jfloat vy, jfloat vz, jfloat wx, jfloat wy, jfloat wz) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    const Vec3 linearVelocity(vx, vy, vz);
    const Vec3 angularVelocity(wx, wy, wz);
    pInterface->SetLinearAndAngularVelocity(
            id, linearVelocity, angularVelocity);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    setLinearVelocity
 * Signature: (JIFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_setLinearVelocity
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId,
    jfloat vx, jfloat vy, jfloat vz) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    const Vec3 velocity(vx, vy, vz);
    pInterface->SetLinearVelocity(id, velocity);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    setMotionQuality
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_setMotionQuality
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId, jint ordinal) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    const EMotionQuality quality = (EMotionQuality) ordinal;
    pInterface->SetMotionQuality(id, quality);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    setMotionType
 * Signature: (JIII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_setMotionType
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId, jint motionOrdinal,
  jint activationOrdinal) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    const EMotionType motionType = (EMotionType) motionOrdinal;
    const EActivation activationType = (EActivation) activationOrdinal;
    pInterface->SetMotionType(id, motionType, activationType);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    setObjectLayer
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_setObjectLayer
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId, jint layerIndex) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    const ObjectLayer layer = (ObjectLayer) layerIndex;
    pInterface->SetObjectLayer(id, layer);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    setPosition
 * Signature: (JIDDDI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_setPosition
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId, jdouble locX,
  jdouble locY, jdouble locZ, jint ordinal) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    const RVec3 location(locX, locY, locZ);
    const EActivation activationType = (EActivation) ordinal;
    pInterface->SetPosition(id, location, activationType);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    setPositionAndRotation
 * Signature: (JIDDDFFFFI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_setPositionAndRotation
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId, jdouble locX,
  jdouble locY, jdouble locZ, jfloat qx, jfloat qy, jfloat qz, jfloat qw,
  jint ordinal) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    const RVec3 location(locX, locY, locZ);
    const Quat orientation(qx, qy, qz, qw);
    const EActivation activation = (EActivation) ordinal;
    pInterface->SetPositionAndRotation(id, location, orientation, activation);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    setRestitution
 * Signature: (JIF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_setRestitution
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId, jfloat restitution) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    pInterface->SetRestitution(id, restitution);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    setShape
 * Signature: (JIJZI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_setShape
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId, jlong shapeVa,
  jboolean updateMassProperties, jint ordinal) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    const EActivation activation = (EActivation) ordinal;
    pInterface->SetShape(id, pShape, updateMassProperties, activation);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    setUserData
 * Signature: (JIJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_setUserData
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jint bodyId, jlong value) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID id(bodyId);
    pInterface->SetUserData(id, value);
}