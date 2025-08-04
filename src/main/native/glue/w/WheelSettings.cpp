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
#include "auto/com_github_stephengold_joltjni_WheelSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    getEnableSuspensionForcePoint
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_WheelSettings_getEnableSuspensionForcePoint
  (JNIEnv *, jclass, jlong settingsVa) {
    const WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    const bool result = pSettings->mEnableSuspensionForcePoint;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    getPosition
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_WheelSettings_getPosition
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeFloats) {
    const WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& result = pSettings->mPosition;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    getRadius
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_WheelSettings_getRadius
  (JNIEnv *, jclass, jlong settingsVa) {
    const WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    const float result = pSettings->mRadius;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    getSteeringAxis
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_WheelSettings_getSteeringAxis
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeFloats) {
    const WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& result = pSettings->mSteeringAxis;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    getSuspensionDirection
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_WheelSettings_getSuspensionDirection
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeFloats) {
    const WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& result = pSettings->mSuspensionDirection;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    getSuspensionForcePoint
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_WheelSettings_getSuspensionForcePoint
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeFloats) {
    const WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& result = pSettings->mSuspensionForcePoint;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    getSuspensionMaxLength
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_WheelSettings_getSuspensionMaxLength
  (JNIEnv *, jclass, jlong settingsVa) {
    const WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    const float result = pSettings->mSuspensionMaxLength;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    getSuspensionMinLength
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_WheelSettings_getSuspensionMinLength
  (JNIEnv *, jclass, jlong settingsVa) {
    const WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    const float result = pSettings->mSuspensionMinLength;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    getSuspensionPreloadLength
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_WheelSettings_getSuspensionPreloadLength
  (JNIEnv *, jclass, jlong settingsVa) {
    const WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    const float result = pSettings->mSuspensionPreloadLength;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    getSuspensionSpring
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_WheelSettings_getSuspensionSpring
  (JNIEnv *, jclass, jlong settingsVa) {
    WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    SpringSettings * const pResult = &pSettings->mSuspensionSpring;
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    getWheelForward
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_WheelSettings_getWheelForward
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeFloats) {
    const WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& result = pSettings->mWheelForward;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    getWheelUp
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_WheelSettings_getWheelUp
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeFloats) {
    const WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& result = pSettings->mWheelUp;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    getWidth
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_WheelSettings_getWidth
  (JNIEnv *, jclass, jlong settingsVa) {
    const WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    const float result = pSettings->mWidth;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    restoreBinaryState
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_WheelSettings_restoreBinaryState
  (JNIEnv *, jclass, jlong settingsVa, jlong streamVa) {
    WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    StreamIn * const pStream = reinterpret_cast<StreamIn *> (streamVa);
    pSettings->RestoreBinaryState(*pStream);
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    saveBinaryState
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_WheelSettings_saveBinaryState
  (JNIEnv *, jclass, jlong settingsVa, jlong streamVa) {
    const WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    StreamOut * const pStream = reinterpret_cast<StreamOut *> (streamVa);
    pSettings->SaveBinaryState(*pStream);
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    setEnableSuspensionForcePoint
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_WheelSettings_setEnableSuspensionForcePoint
  (JNIEnv *, jclass, jlong settingsVa, jboolean enable) {
    WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    pSettings->mEnableSuspensionForcePoint = enable;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    setPosition
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_WheelSettings_setPosition
  (JNIEnv *, jclass, jlong settingsVa, jfloat x, jfloat y, jfloat z) {
    WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    const Vec3 position(x, y, z);
    pSettings->mPosition = position;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    setRadius
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_WheelSettings_setRadius
  (JNIEnv *, jclass, jlong settingsVa, jfloat radius) {
    WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    pSettings->mRadius = radius;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    setSteeringAxis
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_WheelSettings_setSteeringAxis
  (JNIEnv *, jclass, jlong settingsVa, jfloat dx, jfloat dy, jfloat dz) {
    WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    const Vec3 axis(dx, dy, dz);
    pSettings->mSteeringAxis = axis;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    setSuspensionDirection
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_WheelSettings_setSuspensionDirection
  (JNIEnv *, jclass, jlong settingsVa, jfloat dx, jfloat dy, jfloat dz) {
    WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    const Vec3 direction(dx, dy, dz);
    pSettings->mSuspensionDirection = direction;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    setSuspensionForcePoint
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_WheelSettings_setSuspensionForcePoint
  (JNIEnv *, jclass, jlong settingsVa, jfloat x, jfloat y, jfloat z) {
    WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    const Vec3 location(x, y, z);
    pSettings->mSuspensionForcePoint = location;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    setSuspensionMaxLength
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_WheelSettings_setSuspensionMaxLength
  (JNIEnv *, jclass, jlong settingsVa, jfloat length) {
    WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    pSettings->mSuspensionMaxLength = length;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    setSuspensionMinLength
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_WheelSettings_setSuspensionMinLength
  (JNIEnv *, jclass, jlong settingsVa, jfloat length) {
    WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    pSettings->mSuspensionMinLength = length;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    setSuspensionPreloadLength
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_WheelSettings_setSuspensionPreloadLength
  (JNIEnv *, jclass, jlong settingsVa, jfloat length) {
    WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    pSettings->mSuspensionPreloadLength = length;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    setWheelForward
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_WheelSettings_setWheelForward
  (JNIEnv *, jclass, jlong settingsVa, jfloat dx, jfloat dy, jfloat dz) {
    WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    const Vec3 direction(dx, dy, dz);
    pSettings->mWheelForward = direction;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    setWheelUp
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_WheelSettings_setWheelUp
  (JNIEnv *, jclass, jlong settingsVa, jfloat dx, jfloat dy, jfloat dz) {
    WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    const Vec3 direction(dx, dy, dz);
    pSettings->mWheelUp = direction;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    setWidth
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_WheelSettings_setWidth
  (JNIEnv *, jclass, jlong settingsVa, jfloat width) {
    WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    pSettings->mWidth = width;
}