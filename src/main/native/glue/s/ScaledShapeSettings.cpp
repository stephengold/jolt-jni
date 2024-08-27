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
#include <Jolt/Physics/Collision/Shape/ScaledShape.h>
#include "auto/com_github_stephengold_joltjni_ScaledShapeSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_ScaledShapeSettings
 * Method:    createScaledShapeSettingsFromRef
 * Signature: (JFFF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ScaledShapeSettings_createScaledShapeSettingsFromRef
  (JNIEnv *, jclass, jlong baseRefVa, jfloat x, jfloat y, jfloat z) {
    const ShapeRefC * const pBaseRef = reinterpret_cast<ShapeRefC *> (baseRefVa);
    const Vec3 factors(x, y, z);
    ScaledShapeSettings * const pResult
            = new ScaledShapeSettings(pBaseRef->GetPtr(), factors);
    TRACE_NEW("ScaledShapeSettings", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_ScaledShapeSettings
 * Method:    createScaledShapeSettingsFromSettings
 * Signature: (JFFF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ScaledShapeSettings_createScaledShapeSettingsFromSettings
  (JNIEnv *, jclass, jlong baseSettingsVa, jfloat x, jfloat y, jfloat z) {
    const ShapeSettings * const pBaseSettings
            = reinterpret_cast<ShapeSettings *> (baseSettingsVa);
    const Vec3 factors(x, y, z);
    ScaledShapeSettings * const pResult
            = new ScaledShapeSettings(pBaseSettings, factors);
    TRACE_NEW("ScaledShapeSettings", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_ScaledShapeSettings
 * Method:    createScaledShapeSettingsFromShape
 * Signature: (JFFF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ScaledShapeSettings_createScaledShapeSettingsFromShape
  (JNIEnv *, jclass, jlong baseShapeVa, jfloat x, jfloat y, jfloat z) {
    const Shape * const pBaseShape = reinterpret_cast<Shape *> (baseShapeVa);
    const Vec3 factors(x, y, z);
    ScaledShapeSettings * const pResult
            = new ScaledShapeSettings(pBaseShape, factors);
    TRACE_NEW("ScaledShapeSettings", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_ScaledShapeSettings
 * Method:    getScaleX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ScaledShapeSettings_getScaleX
  (JNIEnv *, jclass, jlong settingsVa) {
    const ScaledShapeSettings * const pSettings
            = reinterpret_cast<ScaledShapeSettings *> (settingsVa);
    const float result = pSettings->mScale.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ScaledShapeSettings
 * Method:    getScaleY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ScaledShapeSettings_getScaleY
  (JNIEnv *, jclass, jlong settingsVa) {
    const ScaledShapeSettings * const pSettings
            = reinterpret_cast<ScaledShapeSettings *> (settingsVa);
    const float result = pSettings->mScale.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ScaledShapeSettings
 * Method:    getScaleZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ScaledShapeSettings_getScaleZ
  (JNIEnv *, jclass, jlong settingsVa) {
    const ScaledShapeSettings * const pSettings
            = reinterpret_cast<ScaledShapeSettings *> (settingsVa);
    const float result = pSettings->mScale.GetZ();
    return result;
}