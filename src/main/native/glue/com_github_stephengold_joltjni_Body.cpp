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
#include "auto/com_github_stephengold_joltjni_Body.h"

using namespace JPH;

inline static const Vec3& getAccumulatedForce(jlong bodyVa) {
    const Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    const Vec3& result = pBody->GetAccumulatedForce();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getAccumulatedForceX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Body_getAccumulatedForceX
  (JNIEnv *, jclass, jlong bodyVa) {
    const Vec3& vec3 = getAccumulatedForce(bodyVa);
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
    const Vec3& vec3 = getAccumulatedForce(bodyVa);
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
    const Vec3& vec3 = getAccumulatedForce(bodyVa);
    float result = vec3.GetZ();
    return result;
}

inline static const Vec3& getAccumulatedTorque(jlong bodyVa) {
    const Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    const Vec3& result = pBody->GetAccumulatedTorque();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getAccumulatedTorqueX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Body_getAccumulatedTorqueX
  (JNIEnv *, jclass, jlong bodyVa) {
    const Vec3& vec3 = getAccumulatedTorque(bodyVa);
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
    const Vec3& vec3 = getAccumulatedTorque(bodyVa);
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
    const Vec3& vec3 = getAccumulatedTorque(bodyVa);
    float result = vec3.GetZ();
    return result;
}

inline static const Vec3& getAngularVelocity(jlong bodyVa) {
    const Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    const Vec3& result = pBody->GetAngularVelocity();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getAngularVelocityX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Body_getAngularVelocityX
  (JNIEnv *, jclass, jlong bodyVa) {
    const Vec3& vec3 = getAngularVelocity(bodyVa);
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
    const Vec3& vec3 = getAngularVelocity(bodyVa);
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
    const Vec3& vec3 = getAngularVelocity(bodyVa);
    float result = vec3.GetZ();
    return result;
}

inline static const RVec3& getCenterOfMassPosition(jlong bodyVa) {
    const Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    const RVec3& result = pBody->GetCenterOfMassPosition();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getCenterOfMassPositionX
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_Body_getCenterOfMassPositionX
  (JNIEnv *, jclass, jlong bodyVa) {
    const RVec3& rvec3 = getCenterOfMassPosition(bodyVa);
    jdouble result = rvec3.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getCenterOfMassPositionY
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_Body_getCenterOfMassPositionY
  (JNIEnv *, jclass, jlong bodyVa) {
    const RVec3& rvec3 = getCenterOfMassPosition(bodyVa);
    jdouble result = rvec3.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getCenterOfMassPositionZ
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_Body_getCenterOfMassPositionZ
  (JNIEnv *, jclass, jlong bodyVa) {
    const RVec3& rvec3 = getCenterOfMassPosition(bodyVa);
    jdouble result = rvec3.GetZ();
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

inline static const Vec3& getLinearVelocity(jlong bodyVa) {
    const Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    const Vec3& result = pBody->GetLinearVelocity();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getLinearVelocityX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Body_getLinearVelocityX
  (JNIEnv *, jclass, jlong bodyVa) {
    const Vec3& vec3 = getLinearVelocity(bodyVa);
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
    const Vec3& vec3 = getLinearVelocity(bodyVa);
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
    const Vec3& vec3 = getLinearVelocity(bodyVa);
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
 * Method:    getRestitution
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Body_getRestitution
  (JNIEnv *, jclass, jlong bodyVa) {
    const Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    float result = pBody->GetRestitution();
    return result;
}

inline static const Quat& getRotation(jlong bodyVa) {
    const Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    const Quat& result = pBody->GetRotation();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Body
 * Method:    getRotationX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Body_getRotationX
  (JNIEnv *, jclass, jlong bodyVa) {
    const Quat& rotation = getRotation(bodyVa);
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
    const Quat& rotation = getRotation(bodyVa);
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
    const Quat& rotation = getRotation(bodyVa);
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
    const Quat& rotation = getRotation(bodyVa);
    float result = rotation.GetW();
    return result;
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
 * Method:    setRestitution
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Body_setRestitution
  (JNIEnv *, jclass, jlong bodyVa, jfloat restitution) {
    Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    pBody->SetRestitution(restitution);
}

