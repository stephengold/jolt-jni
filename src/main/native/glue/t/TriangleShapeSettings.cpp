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
#include "Jolt/Physics/Collision/Shape/TriangleShape.h"
#include "auto/com_github_stephengold_joltjni_TriangleShapeSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_TriangleShapeSettings
 * Method:    createTriangleShapeSettings
 * Signature: (FFFFFFFFFF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_TriangleShapeSettings_createTriangleShapeSettings
  (JNIEnv *, jclass, jfloat v1x, jfloat v1y, jfloat v1z, jfloat v2x, jfloat v2y,
  jfloat v2z, jfloat v3x, jfloat v3y, jfloat v3z, jfloat convexRadius) {
    const Vec3 v1(v1x, v1y, v1z);
    const Vec3 v2(v2x, v2y, v2z);
    const Vec3 v3(v3x, v3y, v3z);
    const TriangleShapeSettings * const pSettings
            = new TriangleShapeSettings(v1, v2, v3, convexRadius);
    TRACE_NEW("TriangleShapeSettings", pSettings)
    return reinterpret_cast<jlong> (pSettings);
}

/*
 * Class:     com_github_stephengold_joltjni_TriangleShapeSettings
 * Method:    getConvexRadius
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_TriangleShapeSettings_getConvexRadius
  (JNIEnv *, jclass, jlong settingsVa) {
    const TriangleShapeSettings * const pSettings
            = reinterpret_cast<TriangleShapeSettings *> (settingsVa);
    const float result = pSettings->mConvexRadius;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_TriangleShapeSettings
 * Method:    setConvexRadius
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TriangleShapeSettings_setConvexRadius
  (JNIEnv *, jclass, jlong settingsVa, jfloat radius) {
    TriangleShapeSettings * const pSettings
            = reinterpret_cast<TriangleShapeSettings *> (settingsVa);
    pSettings->mConvexRadius = radius;
}