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
#include <Jolt/Physics/Body/Body.h>
#include <Jolt/Physics/Body/BodyCreationSettings.h>
#include "auto/com_github_stephengold_joltjni_Body.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    addAngularImpulse
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Body_addAngularImpulse
  (JNIEnv *, jclass, jlong bodyVa, jfloat x, jfloat y, jfloat z) {
    Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    Vec3 impulse(x, y, z);
    pBody->AddAngularImpulse(impulse);
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    addForce
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Body_addForce__JFFF
  (JNIEnv *, jclass, jlong bodyVa, jfloat fx, jfloat fy, jfloat fz) {
    Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    Vec3 force(fx, fy, fz);
    pBody->AddForce(force);
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    addForce
 * Signature: (JFFFDDD)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Body_addForce__JFFFDDD
  (JNIEnv *, jclass, jlong bodyVa, jfloat fx, jfloat fy, jfloat fz,
  jdouble posX, jdouble posY, jdouble posZ) {
    Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    Vec3 force(fx, fy, fz);
    RVec3 position(posX, posY, posZ);
    pBody->AddForce(force, position);
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    addImpulse
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Body_addImpulse__JFFF
  (JNIEnv *, jclass, jlong bodyVa, jfloat jx, jfloat jy, jfloat jz) {
    Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    Vec3 impulse(jx, jy, jz);
    pBody->AddImpulse(impulse);
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    addImpulse
 * Signature: (JFFFDDD)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Body_addImpulse__JFFFDDD
  (JNIEnv *, jclass, jlong bodyVa, jfloat jx, jfloat jy, jfloat jz,
  jdouble posX, jdouble posY, jdouble posZ) {
    Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    Vec3 impulse(jx, jy, jz);
    RVec3 position(posX, posY, posZ);
    pBody->AddImpulse(impulse, position);
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    addTorque
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Body_addTorque
  (JNIEnv *, jclass, jlong bodyVa, jfloat x, jfloat y, jfloat z) {
    Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    Vec3 torque(x, y, z);
    pBody->AddTorque(torque);
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    canBeKinematicOrDynamic
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_Body_canBeKinematicOrDynamic
  (JNIEnv *, jclass, jlong bodyVa) {
    const Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    bool result = pBody->CanBeKinematicOrDynamic();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    createFixedToWorld
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Body_createFixedToWorld
  (JNIEnv *, jclass) {
    Body * const pResult = &Body::sFixedToWorld;
    return reinterpret_cast<jlong> (pResult);
}

inline static const Vec3 getAccumulatedForce(jlong bodyVa) {
    const Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    const Vec3 result = pBody->GetAccumulatedForce();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getAccumulatedForceX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Body_getAccumulatedForceX
  (JNIEnv *, jclass, jlong bodyVa) {
    const Vec3 vec3 = getAccumulatedForce(bodyVa);
    float result = vec3.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getAccumulatedForceY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Body_getAccumulatedForceY
  (JNIEnv *, jclass, jlong bodyVa) {
    const Vec3 vec3 = getAccumulatedForce(bodyVa);
    float result = vec3.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getAccumulatedForceZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Body_getAccumulatedForceZ
  (JNIEnv *, jclass, jlong bodyVa) {
    const Vec3 vec3 = getAccumulatedForce(bodyVa);
    float result = vec3.GetZ();
    return result;
}

inline static const Vec3 getAccumulatedTorque(jlong bodyVa) {
    const Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    const Vec3 result = pBody->GetAccumulatedTorque();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getAccumulatedTorqueX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Body_getAccumulatedTorqueX
  (JNIEnv *, jclass, jlong bodyVa) {
    const Vec3 vec3 = getAccumulatedTorque(bodyVa);
    float result = vec3.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getAccumulatedTorqueY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Body_getAccumulatedTorqueY
  (JNIEnv *, jclass, jlong bodyVa) {
    const Vec3 vec3 = getAccumulatedTorque(bodyVa);
    float result = vec3.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getAccumulatedTorqueZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Body_getAccumulatedTorqueZ
  (JNIEnv *, jclass, jlong bodyVa) {
    const Vec3 vec3 = getAccumulatedTorque(bodyVa);
    float result = vec3.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getAllowSleeping
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_Body_getAllowSleeping
  (JNIEnv *, jclass, jlong bodyVa) {
    const Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    bool result = pBody->GetAllowSleeping();
    return result;
}

inline static const Vec3 getAngularVelocity(jlong bodyVa) {
    const Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    const Vec3 result = pBody->GetAngularVelocity();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getAngularVelocityX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Body_getAngularVelocityX
  (JNIEnv *, jclass, jlong bodyVa) {
    const Vec3 vec3 = getAngularVelocity(bodyVa);
    float result = vec3.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getAngularVelocityY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Body_getAngularVelocityY
  (JNIEnv *, jclass, jlong bodyVa) {
    const Vec3 vec3 = getAngularVelocity(bodyVa);
    float result = vec3.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getAngularVelocityZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Body_getAngularVelocityZ
  (JNIEnv *, jclass, jlong bodyVa) {
    const Vec3 vec3 = getAngularVelocity(bodyVa);
    float result = vec3.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getBodyCreationSettings
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Body_getBodyCreationSettings
  (JNIEnv *, jclass, jlong bodyVa) {
    const Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    BodyCreationSettings * const pResult = new BodyCreationSettings();
    TRACE_NEW("BodyCreationSettings", pResult)
    *pResult = pBody->GetBodyCreationSettings();
    return reinterpret_cast<jlong> (pResult);
}

inline static const RVec3 getCenterOfMassPosition(jlong bodyVa) {
    const Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    const RVec3 result = pBody->GetCenterOfMassPosition();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getCenterOfMassPositionX
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_Body_getCenterOfMassPositionX
  (JNIEnv *, jclass, jlong bodyVa) {
    const RVec3 rvec3 = getCenterOfMassPosition(bodyVa);
    Real result = rvec3.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getCenterOfMassPositionY
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_Body_getCenterOfMassPositionY
  (JNIEnv *, jclass, jlong bodyVa) {
    const RVec3 rvec3 = getCenterOfMassPosition(bodyVa);
    Real result = rvec3.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getCenterOfMassPositionZ
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_Body_getCenterOfMassPositionZ
  (JNIEnv *, jclass, jlong bodyVa) {
    const RVec3 rvec3 = getCenterOfMassPosition(bodyVa);
    Real result = rvec3.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getFriction
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Body_getFriction
  (JNIEnv *, jclass, jlong bodyVa) {
    const Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    float result = pBody->GetFriction();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getId
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Body_getId
  (JNIEnv *, jclass, jlong bodyVa) {
    const Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    const BodyID &result = pBody->GetID();
    return reinterpret_cast<jlong> (&result);
}

inline static const Vec3 getLinearVelocity(jlong bodyVa) {
    const Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    const Vec3 result = pBody->GetLinearVelocity();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getLinearVelocityX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Body_getLinearVelocityX
  (JNIEnv *, jclass, jlong bodyVa) {
    const Vec3 vec3 = getLinearVelocity(bodyVa);
    float result = vec3.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getLinearVelocityY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Body_getLinearVelocityY
  (JNIEnv *, jclass, jlong bodyVa) {
    const Vec3 vec3 = getLinearVelocity(bodyVa);
    float result = vec3.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getLinearVelocityZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Body_getLinearVelocityZ
  (JNIEnv *, jclass, jlong bodyVa) {
    const Vec3 vec3 = getLinearVelocity(bodyVa);
    float result = vec3.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getMotionProperties
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Body_getMotionProperties
  (JNIEnv *, jclass, jlong bodyVa) {
    const Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    const MotionProperties * const pResult = pBody->GetMotionProperties();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getMotionType
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Body_getMotionType
  (JNIEnv *, jclass, jlong bodyVa) {
    const Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    const EMotionType result = pBody->GetMotionType();
    return (jint) result;
}

inline static const RVec3 getPosition(jlong bodyVa) {
    const Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    const RVec3 result = pBody->GetPosition();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getObjectLayer
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Body_getObjectLayer
  (JNIEnv *, jclass, jlong bodyVa) {
    const Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    ObjectLayer result = pBody->GetObjectLayer();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getPositionX
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_Body_getPositionX
  (JNIEnv *, jclass, jlong bodyVa) {
    const RVec3 rvec3 = getPosition(bodyVa);
    float result = rvec3.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getPositionY
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_Body_getPositionY
  (JNIEnv *, jclass, jlong bodyVa) {
    const RVec3 rvec3 = getPosition(bodyVa);
    float result = rvec3.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getPositionZ
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_Body_getPositionZ
  (JNIEnv *, jclass, jlong bodyVa) {
    const RVec3 rvec3 = getPosition(bodyVa);
    float result = rvec3.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getRestitution
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Body_getRestitution
  (JNIEnv *, jclass, jlong bodyVa) {
    const Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    float result = pBody->GetRestitution();
    return result;
}

inline static const Quat getRotation(jlong bodyVa) {
    const Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    const Quat result = pBody->GetRotation();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getRotationX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Body_getRotationX
  (JNIEnv *, jclass, jlong bodyVa) {
    const Quat rotation = getRotation(bodyVa);
    float result = rotation.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getRotationY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Body_getRotationY
  (JNIEnv *, jclass, jlong bodyVa) {
    const Quat rotation = getRotation(bodyVa);
    float result = rotation.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getRotationZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Body_getRotationZ
  (JNIEnv *, jclass, jlong bodyVa) {
    const Quat rotation = getRotation(bodyVa);
    float result = rotation.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getRotationW
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Body_getRotationW
  (JNIEnv *, jclass, jlong bodyVa) {
    const Quat rotation = getRotation(bodyVa);
    float result = rotation.GetW();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getShape
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Body_getShape
  (JNIEnv *, jclass, jlong bodyVa) {
    const Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    const Shape * const pResult = pBody->GetShape();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getUserData
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Body_getUserData
  (JNIEnv *, jclass, jlong bodyVa) {
    const Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    uint64 result = pBody->GetUserData();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getWorldSpaceBounds
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Body_getWorldSpaceBounds
  (JNIEnv *, jclass, jlong bodyVa) {
    const Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    const AABox& result = pBody->GetWorldSpaceBounds();
    return reinterpret_cast<jlong> (&result);
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    isActive
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_Body_isActive
  (JNIEnv *, jclass, jlong bodyVa) {
    const Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    bool result = pBody->IsActive();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    isDynamic
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_Body_isDynamic
  (JNIEnv *, jclass, jlong bodyVa) {
    const Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    bool result = pBody->IsDynamic();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    isKinematic
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_Body_isKinematic
  (JNIEnv *, jclass, jlong bodyVa) {
    const Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    bool result = pBody->IsKinematic();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    isRigidBody
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_Body_isRigidBody
  (JNIEnv *, jclass, jlong bodyVa) {
    const Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    bool result = pBody->IsRigidBody();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    isSensor
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_Body_isSensor
  (JNIEnv *, jclass, jlong bodyVa) {
    const Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    bool result = pBody->IsSensor();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    isStatic
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_Body_isStatic
  (JNIEnv *, jclass, jlong bodyVa) {
    const Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    bool result = pBody->IsStatic();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    moveKinematic
 * Signature: (JDDDFFFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Body_moveKinematic
  (JNIEnv *, jclass, jlong bodyVa, jdouble xx, jdouble yy, jdouble zz,
  jfloat qx, jfloat qy, jfloat qz, jfloat qw, jfloat deltaTime) {
    Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    RVec3 location(xx, yy, zz);
    Quat orientation(qx, qy, qz, qw);
    pBody->MoveKinematic(location, orientation, deltaTime);
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    resetSleepTimer
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Body_resetSleepTimer
  (JNIEnv *, jclass, jlong bodyVa) {
    Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    pBody->ResetSleepTimer();
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    setAllowSleeping
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Body_setAllowSleeping
  (JNIEnv *, jclass, jlong bodyVa, jboolean allow) {
    Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    pBody->SetAllowSleeping(allow);
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    setAngularVelocity
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Body_setAngularVelocity
  (JNIEnv *, jclass, jlong bodyVa, jfloat wx, jfloat wy, jfloat wz) {
    Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    Vec3 omega(wx, wy, wz);
    pBody->SetAngularVelocity(omega);
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    setAngularVelocityClamped
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Body_setAngularVelocityClamped
  (JNIEnv *, jclass, jlong bodyVa, jfloat wx, jfloat wy, jfloat wz) {
    Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    Vec3 omega(wx, wy, wz);
    pBody->SetAngularVelocityClamped(omega);
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    setFriction
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Body_setFriction
  (JNIEnv *, jclass, jlong bodyVa, jfloat friction) {
    Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    pBody->SetFriction(friction);
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    setIsSensor
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Body_setIsSensor
  (JNIEnv *, jclass, jlong bodyVa, jboolean setting) {
    Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    pBody->SetIsSensor(setting);
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    setLinearVelocity
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Body_setLinearVelocity
  (JNIEnv *, jclass, jlong bodyVa, jfloat vx, jfloat vy, jfloat vz) {
    Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    Vec3 velocity(vx, vy, vz);
    pBody->SetLinearVelocity(velocity);
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    setLinearVelocityClamped
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Body_setLinearVelocityClamped
  (JNIEnv *, jclass, jlong bodyVa, jfloat vx, jfloat vy, jfloat vz) {
    Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    Vec3 velocity(vx, vy, vz);
    pBody->SetLinearVelocityClamped(velocity);
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    setMotionType
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Body_setMotionType
  (JNIEnv *, jclass, jlong bodyVa, jint ordinal) {
    Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    EMotionType motionType = (EMotionType) ordinal;
    pBody->SetMotionType(motionType);
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    setRestitution
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Body_setRestitution
  (JNIEnv *, jclass, jlong bodyVa, jfloat restitution) {
    Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    pBody->SetRestitution(restitution);
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    setUserData
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Body_setUserData
  (JNIEnv *, jclass, jlong bodyVa, jlong value) {
    Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    pBody->SetUserData(value);
}
