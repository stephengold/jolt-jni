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

using namespace JPH;

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
