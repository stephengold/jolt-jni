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
#include <Jolt/Physics/Collision/Shape/CylinderShape.h>
#include "auto/com_github_stephengold_joltjni_CylinderShapeSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_CylinderShapeSettings
 * Method:    createCylinderShapeSettings
 * Signature: (FF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CylinderShapeSettings_createCylinderShapeSettings
  (JNIEnv *, jclass, jfloat halfHeight, jfloat radius) {
    CylinderShapeSettings * const pResult
            = new CylinderShapeSettings(halfHeight, radius);
    TRACE_NEW("CylinderShapeSettings", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_CylinderShapeSettings
 * Method:    getConvexRadius
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CylinderShapeSettings_getConvexRadius
  (JNIEnv *, jclass, jlong settingsVa) {
    const CylinderShapeSettings * const pSettings
            = reinterpret_cast<CylinderShapeSettings *> (settingsVa);
    const float result = pSettings->mConvexRadius;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CylinderShapeSettings
 * Method:    getHalfHeight
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CylinderShapeSettings_getHalfHeight
  (JNIEnv *, jclass, jlong settingsVa) {
    const CylinderShapeSettings * const pSettings
            = reinterpret_cast<CylinderShapeSettings *> (settingsVa);
    const float result = pSettings->mHalfHeight;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CylinderShapeSettings
 * Method:    getRadius
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CylinderShapeSettings_getRadius
  (JNIEnv *, jclass, jlong settingsVa) {
    const CylinderShapeSettings * const pSettings
            = reinterpret_cast<CylinderShapeSettings *> (settingsVa);
    const float result = pSettings->mRadius;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CylinderShapeSettings
 * Method:    setConvexRadius
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CylinderShapeSettings_setConvexRadius
  (JNIEnv *, jclass, jlong settingsVa, jfloat radius) {
    CylinderShapeSettings * const pSettings
            = reinterpret_cast<CylinderShapeSettings *> (settingsVa);
    pSettings->mConvexRadius = radius;
}


/*
 * Class:     com_github_stephengold_joltjni_CylinderShapeSettings
 * Method:    setHalfHeight
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CylinderShapeSettings_setHalfHeight
  (JNIEnv *, jclass, jlong settingsVa, jfloat halfHeight) {
    CylinderShapeSettings * const pSettings
            = reinterpret_cast<CylinderShapeSettings *> (settingsVa);
    pSettings->mHalfHeight = halfHeight;
}

/*
 * Class:     com_github_stephengold_joltjni_CylinderShapeSettings
 * Method:    setRadius
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CylinderShapeSettings_setRadius
  (JNIEnv *, jclass, jlong settingsVa, jfloat radius) {
    CylinderShapeSettings * const pSettings
            = reinterpret_cast<CylinderShapeSettings *> (settingsVa);
    pSettings->mRadius = radius;
}