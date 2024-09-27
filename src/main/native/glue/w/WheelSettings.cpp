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
 * Method:    setWidth
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_WheelSettings_setWidth
  (JNIEnv *, jclass, jlong settingsVa, jfloat width) {
    WheelSettings * const pSettings
            = reinterpret_cast<WheelSettings *> (settingsVa);
    pSettings->mWidth = width;
}