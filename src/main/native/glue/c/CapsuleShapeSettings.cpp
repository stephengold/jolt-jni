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
#include "Jolt/Physics/Collision/Shape/CapsuleShape.h"
#include "auto/com_github_stephengold_joltjni_CapsuleShapeSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_CapsuleShapeSettings
 * Method:    createShapeSettings
 * Signature: (FFJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CapsuleShapeSettings_createShapeSettings
  (JNIEnv *, jclass, jfloat halfHeight, jfloat radius, jlong materialVa) {
    const PhysicsMaterial * const pMaterial
            = reinterpret_cast<PhysicsMaterial *> (materialVa);
    CapsuleShapeSettings * const pResult
            = new CapsuleShapeSettings(halfHeight, radius, pMaterial);
    TRACE_NEW("CapsuleShapeSettings", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_CapsuleShapeSettings
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CapsuleShapeSettings_createCopy
  BODYOF_CREATE_COPY(CapsuleShapeSettings)

/*
 * Class:     com_github_stephengold_joltjni_CapsuleShapeSettings
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CapsuleShapeSettings_createDefault
  BODYOF_CREATE_DEFAULT(CapsuleShapeSettings)

/*
 * Class:     com_github_stephengold_joltjni_CapsuleShapeSettings
 * Method:    getHalfHeightOfCylinder
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CapsuleShapeSettings_getHalfHeightOfCylinder
  (JNIEnv *, jclass, jlong settingsVa) {
    const CapsuleShapeSettings * const pSettings
            = reinterpret_cast<CapsuleShapeSettings *> (settingsVa);
    const float result = pSettings->mHalfHeightOfCylinder;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CapsuleShapeSettings
 * Method:    getRadius
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CapsuleShapeSettings_getRadius
  (JNIEnv *, jclass, jlong settingsVa) {
    const CapsuleShapeSettings * const pSettings
            = reinterpret_cast<CapsuleShapeSettings *> (settingsVa);
    const float result = pSettings->mRadius;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CapsuleShapeSettings
 * Method:    setHalfHeightOfCylinder
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CapsuleShapeSettings_setHalfHeightOfCylinder
  (JNIEnv *, jclass, jlong settingsVa, jfloat halfHeight) {
    CapsuleShapeSettings * const pSettings
            = reinterpret_cast<CapsuleShapeSettings *> (settingsVa);
    pSettings->mHalfHeightOfCylinder = halfHeight;
}

/*
 * Class:     com_github_stephengold_joltjni_CapsuleShapeSettings
 * Method:    setRadius
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CapsuleShapeSettings_setRadius
  (JNIEnv *, jclass, jlong settingsVa, jfloat radius) {
    CapsuleShapeSettings * const pSettings
            = reinterpret_cast<CapsuleShapeSettings *> (settingsVa);
    pSettings->mRadius = radius;
}