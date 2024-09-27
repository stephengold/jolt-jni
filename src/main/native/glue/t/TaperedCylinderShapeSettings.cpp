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
#include "Jolt/Physics/Collision/Shape/TaperedCylinderShape.h"
#include "auto/com_github_stephengold_joltjni_TaperedCylinderShapeSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_TaperedCylinderShapeSettings
 * Method:    createShapeSettings
 * Signature: (FFFFJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_TaperedCylinderShapeSettings_createShapeSettings
  (JNIEnv *, jclass, jfloat halfHeight, jfloat topRadius, jfloat bottomRadius,
  jfloat convexRadius, jlong materialVa) {
    const PhysicsMaterial * const pMaterial
            = reinterpret_cast<PhysicsMaterial *> (materialVa);
    const TaperedCylinderShapeSettings * const pSettings
            = new TaperedCylinderShapeSettings(halfHeight, topRadius,
                    bottomRadius, convexRadius, pMaterial);
    TRACE_NEW("TaperedCylinderShapeSettings", pSettings)
    return reinterpret_cast<jlong> (pSettings);
}

/*
 * Class:     com_github_stephengold_joltjni_TaperedCylinderShapeSettings
 * Method:    getBottomRadius
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_TaperedCylinderShapeSettings_getBottomRadius
  (JNIEnv *, jclass, jlong settingsVa) {
    const TaperedCylinderShapeSettings * const pSettings
            = reinterpret_cast<TaperedCylinderShapeSettings *> (settingsVa);
    const float result = pSettings->mBottomRadius;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_TaperedCylinderShapeSettings
 * Method:    getConvexRadius
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_TaperedCylinderShapeSettings_getConvexRadius
  (JNIEnv *, jclass, jlong settingsVa) {
    const TaperedCylinderShapeSettings * const pSettings
            = reinterpret_cast<TaperedCylinderShapeSettings *> (settingsVa);
    const float result = pSettings->mConvexRadius;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_TaperedCylinderShapeSettings
 * Method:    getHalfHeight
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_TaperedCylinderShapeSettings_getHalfHeight
  (JNIEnv *, jclass, jlong settingsVa) {
    const TaperedCylinderShapeSettings * const pSettings
            = reinterpret_cast<TaperedCylinderShapeSettings *> (settingsVa);
    const float result = pSettings->mHalfHeight;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_TaperedCylinderShapeSettings
 * Method:    getTopRadius
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_TaperedCylinderShapeSettings_getTopRadius
  (JNIEnv *, jclass, jlong settingsVa) {
    const TaperedCylinderShapeSettings * const pSettings
            = reinterpret_cast<TaperedCylinderShapeSettings *> (settingsVa);
    const float result = pSettings->mTopRadius;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_TaperedCylinderShapeSettings
 * Method:    setBottomRadius
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TaperedCylinderShapeSettings_setBottomRadius
  (JNIEnv *, jclass, jlong settingsVa, jfloat radius) {
    TaperedCylinderShapeSettings * const pSettings
            = reinterpret_cast<TaperedCylinderShapeSettings *> (settingsVa);
    pSettings->mBottomRadius = radius;
}

/*
 * Class:     com_github_stephengold_joltjni_TaperedCylinderShapeSettings
 * Method:    setConvexRadius
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TaperedCylinderShapeSettings_setConvexRadius
  (JNIEnv *, jclass, jlong settingsVa, jfloat radius) {
    TaperedCylinderShapeSettings * const pSettings
            = reinterpret_cast<TaperedCylinderShapeSettings *> (settingsVa);
    pSettings->mConvexRadius = radius;
}

/*
 * Class:     com_github_stephengold_joltjni_TaperedCylinderShapeSettings
 * Method:    setHalfHeight
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TaperedCylinderShapeSettings_setHalfHeight
  (JNIEnv *, jclass, jlong settingsVa, jfloat halfHeight) {
    TaperedCylinderShapeSettings * const pSettings
            = reinterpret_cast<TaperedCylinderShapeSettings *> (settingsVa);
    pSettings->mHalfHeight = halfHeight;
}

/*
 * Class:     com_github_stephengold_joltjni_TaperedCylinderShapeSettings
 * Method:    setTopRadius
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TaperedCylinderShapeSettings_setTopRadius
  (JNIEnv *, jclass, jlong settingsVa, jfloat radius) {
    TaperedCylinderShapeSettings * const pSettings
            = reinterpret_cast<TaperedCylinderShapeSettings *> (settingsVa);
    pSettings->mTopRadius = radius;
}