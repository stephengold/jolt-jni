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
#include "Jolt/Physics/Vehicle/Wheel.h"
#include "auto/com_github_stephengold_joltjni_WheelSettings.h"

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
 * Method:    getPositionX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_WheelSettings_getPositionX
  (JNIEnv *, jclass, jlong settingsVa) {
    const WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    const float result = pSettings->mPosition.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    getPositionY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_WheelSettings_getPositionY
  (JNIEnv *, jclass, jlong settingsVa) {
    const WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    const float result = pSettings->mPosition.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    getPositionZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_WheelSettings_getPositionZ
  (JNIEnv *, jclass, jlong settingsVa) {
    const WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    const float result = pSettings->mPosition.GetZ();
    return result;
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
 * Method:    getSteeringAxisX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_WheelSettings_getSteeringAxisX
  (JNIEnv *, jclass, jlong settingsVa) {
    const WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    const float result = pSettings->mSteeringAxis.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    getSteeringAxisY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_WheelSettings_getSteeringAxisY
  (JNIEnv *, jclass, jlong settingsVa) {
    const WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    const float result = pSettings->mSteeringAxis.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    getSteeringAxisZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_WheelSettings_getSteeringAxisZ
  (JNIEnv *, jclass, jlong settingsVa) {
    const WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    const float result = pSettings->mSteeringAxis.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    getSuspensionDirectionX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_WheelSettings_getSuspensionDirectionX
  (JNIEnv *, jclass, jlong settingsVa) {
    const WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    const float result = pSettings->mSuspensionDirection.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    getSuspensionDirectionY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_WheelSettings_getSuspensionDirectionY
  (JNIEnv *, jclass, jlong settingsVa) {
    const WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    const float result = pSettings->mSuspensionDirection.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    getSuspensionDirectionZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_WheelSettings_getSuspensionDirectionZ
  (JNIEnv *, jclass, jlong settingsVa) {
    const WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    const float result = pSettings->mSuspensionDirection.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    getSuspensionForcePointX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_WheelSettings_getSuspensionForcePointX
  (JNIEnv *, jclass, jlong settingsVa) {
    const WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    const float result = pSettings->mSuspensionForcePoint.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    getSuspensionForcePointY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_WheelSettings_getSuspensionForcePointY
  (JNIEnv *, jclass, jlong settingsVa) {
    const WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    const float result = pSettings->mSuspensionForcePoint.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    getSuspensionForcePointZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_WheelSettings_getSuspensionForcePointZ
  (JNIEnv *, jclass, jlong settingsVa) {
    const WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    const float result = pSettings->mSuspensionForcePoint.GetZ();
    return result;
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
 * Method:    getWheelForwardX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_WheelSettings_getWheelForwardX
  (JNIEnv *, jclass, jlong settingsVa) {
    const WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    const float result = pSettings->mWheelForward.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    getWheelForwardY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_WheelSettings_getWheelForwardY
  (JNIEnv *, jclass, jlong settingsVa) {
    const WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    const float result = pSettings->mWheelForward.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    getWheelForwardZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_WheelSettings_getWheelForwardZ
  (JNIEnv *, jclass, jlong settingsVa) {
    const WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    const float result = pSettings->mWheelForward.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    getWheelUpX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_WheelSettings_getWheelUpX
  (JNIEnv *, jclass, jlong settingsVa) {
    const WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    const float result = pSettings->mWheelUp.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    getWheelUpY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_WheelSettings_getWheelUpY
  (JNIEnv *, jclass, jlong settingsVa) {
    const WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    const float result = pSettings->mWheelUp.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettings
 * Method:    getWheelUpZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_WheelSettings_getWheelUpZ
  (JNIEnv *, jclass, jlong settingsVa) {
    const WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    const float result = pSettings->mWheelUp.GetZ();
    return result;
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