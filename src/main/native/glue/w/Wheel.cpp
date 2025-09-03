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
#include "Jolt/Physics/Vehicle/Wheel.h"
#include "auto/com_github_stephengold_joltjni_Wheel.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_Wheel
 * Method:    getAngularVelocity
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Wheel_getAngularVelocity
  (JNIEnv *, jclass, jlong wheelVa) {
    const Wheel * const pWheel = reinterpret_cast<Wheel *> (wheelVa);
    const float result = pWheel->GetAngularVelocity();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Wheel
 * Method:    getContactBodyId
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Wheel_getContactBodyId
  (JNIEnv *, jclass, jlong wheelVa) {
    const Wheel * const pWheel = reinterpret_cast<Wheel *> (wheelVa);
    const BodyID result = pWheel->GetContactBodyID();
    return result.GetIndexAndSequenceNumber();
}

/*
 * Class:     com_github_stephengold_joltjni_Wheel
 * Method:    getContactLateral
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Wheel_getContactLateral
  (JNIEnv *pEnv, jclass, jlong wheelVa, jobject storeFloats) {
    const Wheel * const pWheel = reinterpret_cast<Wheel *> (wheelVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3 result = pWheel->GetContactLateral();
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_Wheel
 * Method:    getContactLongitudinal
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Wheel_getContactLongitudinal
  (JNIEnv *pEnv, jclass, jlong wheelVa, jobject storeFloats) {
    const Wheel * const pWheel = reinterpret_cast<Wheel *> (wheelVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3 result = pWheel->GetContactLongitudinal();
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_Wheel
 * Method:    getContactNormal
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Wheel_getContactNormal
  (JNIEnv *pEnv, jclass, jlong wheelVa, jobject storeFloats) {
    const Wheel * const pWheel = reinterpret_cast<Wheel *> (wheelVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3 result = pWheel->GetContactNormal();
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_Wheel
 * Method:    getContactPointVelocity
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Wheel_getContactPointVelocity
  (JNIEnv *pEnv, jclass, jlong wheelVa, jobject storeFloats) {
    const Wheel * const pWheel = reinterpret_cast<Wheel *> (wheelVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3 result = pWheel->GetContactPointVelocity();
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_Wheel
 * Method:    getContactPosition
 * Signature: (JLjava/nio/DoubleBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Wheel_getContactPosition
  (JNIEnv *pEnv, jclass, jlong wheelVa, jobject storeDoubles) {
    const Wheel * const pWheel = reinterpret_cast<Wheel *> (wheelVa);
    DIRECT_DOUBLE_BUFFER(pEnv, storeDoubles, pDoubles, capacityDoubles);
    JPH_ASSERT(capacityDoubles >= 3);
    const RVec3 result = pWheel->GetContactPosition();
    pDoubles[0] = result.GetX();
    pDoubles[1] = result.GetY();
    pDoubles[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_Wheel
 * Method:    getContactSubShapeId
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Wheel_getContactSubShapeId
  (JNIEnv *, jclass, jlong wheelVa) {
    const Wheel * const pWheel = reinterpret_cast<Wheel *> (wheelVa);
    const SubShapeID result = pWheel->GetContactSubShapeID();
    return result.GetValue();
}

/*
 * Class:     com_github_stephengold_joltjni_Wheel
 * Method:    getLateralLambda
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Wheel_getLateralLambda
  (JNIEnv *, jclass, jlong wheelVa) {
    const Wheel * const pWheel = reinterpret_cast<Wheel *> (wheelVa);
    const float result = pWheel->GetLateralLambda();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Wheel
 * Method:    getLongitudinalLambda
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Wheel_getLongitudinalLambda
  (JNIEnv *, jclass, jlong wheelVa) {
    const Wheel * const pWheel = reinterpret_cast<Wheel *> (wheelVa);
    const float result = pWheel->GetLongitudinalLambda();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Wheel
 * Method:    getRotationAngle
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Wheel_getRotationAngle
  (JNIEnv *, jclass, jlong wheelVa) {
    const Wheel * const pWheel = reinterpret_cast<Wheel *> (wheelVa);
    const float result = pWheel->GetRotationAngle();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Wheel
 * Method:    getSettings
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Wheel_getSettings
  (JNIEnv *, jclass, jlong wheelVa) {
    const Wheel * const pWheel = reinterpret_cast<Wheel *> (wheelVa);
    const WheelSettings * const pResult = pWheel->GetSettings();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Wheel
 * Method:    getSteerAngle
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Wheel_getSteerAngle
  (JNIEnv *, jclass, jlong wheelVa) {
    const Wheel * const pWheel = reinterpret_cast<Wheel *> (wheelVa);
    const float result = pWheel->GetSteerAngle();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Wheel
 * Method:    getSuspensionLambda
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Wheel_getSuspensionLambda
  (JNIEnv *, jclass, jlong wheelVa) {
    const Wheel * const pWheel = reinterpret_cast<Wheel *> (wheelVa);
    const float result = pWheel->GetSuspensionLambda();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Wheel
 * Method:    getSuspensionLength
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Wheel_getSuspensionLength
  (JNIEnv *, jclass, jlong wheelVa) {
    const Wheel * const pWheel = reinterpret_cast<Wheel *> (wheelVa);
    const float result = pWheel->GetSuspensionLength();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Wheel
 * Method:    hasContact
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_Wheel_hasContact
  (JNIEnv *, jclass, jlong wheelVa) {
    const Wheel * const pWheel = reinterpret_cast<Wheel *> (wheelVa);
    const bool result = pWheel->HasContact();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Wheel
 * Method:    hasHitHardPoint
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_Wheel_hasHitHardPoint
  (JNIEnv *, jclass, jlong wheelVa) {
    const Wheel * const pWheel = reinterpret_cast<Wheel *> (wheelVa);
    const bool result = pWheel->HasHitHardPoint();
    return result;
}