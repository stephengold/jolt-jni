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
#include "Jolt/Physics/Collision/Shape/TaperedCapsuleShape.h"
#include "auto/com_github_stephengold_joltjni_TaperedCapsuleShapeSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_TaperedCapsuleShapeSettings
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_TaperedCapsuleShapeSettings_createCopy
  BODYOF_CREATE_COPY_TARGET(TaperedCapsuleShapeSettings)

/*
 * Class:     com_github_stephengold_joltjni_TaperedCapsuleShapeSettings
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_TaperedCapsuleShapeSettings_createDefault
  BODYOF_CREATE_DEFAULT_TARGET(TaperedCapsuleShapeSettings)

/*
 * Class:     com_github_stephengold_joltjni_TaperedCapsuleShapeSettings
 * Method:    createShapeSettings
 * Signature: (FFFJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_TaperedCapsuleShapeSettings_createShapeSettings
  (JNIEnv *, jclass, jfloat halfHeight, jfloat topRadius, jfloat bottomRadius,
  jlong materialVa) {
    const PhysicsMaterial * const pMaterial
            = reinterpret_cast<PhysicsMaterial *> (materialVa);
    const TaperedCapsuleShapeSettings * const pSettings
            = new TaperedCapsuleShapeSettings(
                    halfHeight, topRadius, bottomRadius, pMaterial);
    TRACE_NEW_TARGET("TaperedCapsuleShapeSettings", pSettings)
    return reinterpret_cast<jlong> (pSettings);
}

/*
 * Class:     com_github_stephengold_joltjni_TaperedCapsuleShapeSettings
 * Method:    getBottomRadius
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_TaperedCapsuleShapeSettings_getBottomRadius
  (JNIEnv *, jclass, jlong settingsVa) {
    const TaperedCapsuleShapeSettings * const pSettings
            = reinterpret_cast<TaperedCapsuleShapeSettings *> (settingsVa);
    const float result = pSettings->mBottomRadius;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_TaperedCapsuleShapeSettings
 * Method:    getHalfHeightOfTaperedCylinder
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_TaperedCapsuleShapeSettings_getHalfHeightOfTaperedCylinder
  (JNIEnv *, jclass, jlong settingsVa) {
    const TaperedCapsuleShapeSettings * const pSettings
            = reinterpret_cast<TaperedCapsuleShapeSettings *> (settingsVa);
    const float result = pSettings->mHalfHeightOfTaperedCylinder;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_TaperedCapsuleShapeSettings
 * Method:    getTopRadius
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_TaperedCapsuleShapeSettings_getTopRadius
  (JNIEnv *, jclass, jlong settingsVa) {
    const TaperedCapsuleShapeSettings * const pSettings
            = reinterpret_cast<TaperedCapsuleShapeSettings *> (settingsVa);
    const float result = pSettings->mTopRadius;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_TaperedCapsuleShapeSettings
 * Method:    setBottomRadius
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TaperedCapsuleShapeSettings_setBottomRadius
  (JNIEnv *, jclass, jlong settingsVa, jfloat radius) {
    TaperedCapsuleShapeSettings * const pSettings
            = reinterpret_cast<TaperedCapsuleShapeSettings *> (settingsVa);
    pSettings->mBottomRadius = radius;
}

/*
 * Class:     com_github_stephengold_joltjni_TaperedCapsuleShapeSettings
 * Method:    setHalfHeightOfTaperedCylinder
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TaperedCapsuleShapeSettings_setHalfHeightOfTaperedCylinder
  (JNIEnv *, jclass, jlong settingsVa, jfloat halfHeight) {
    TaperedCapsuleShapeSettings * const pSettings
            = reinterpret_cast<TaperedCapsuleShapeSettings *> (settingsVa);
    pSettings->mHalfHeightOfTaperedCylinder = halfHeight;
}

/*
 * Class:     com_github_stephengold_joltjni_TaperedCapsuleShapeSettings
 * Method:    setTopRadius
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TaperedCapsuleShapeSettings_setTopRadius
  (JNIEnv *, jclass, jlong settingsVa, jfloat radius) {
    TaperedCapsuleShapeSettings * const pSettings
            = reinterpret_cast<TaperedCapsuleShapeSettings *> (settingsVa);
    pSettings->mTopRadius = radius;
}