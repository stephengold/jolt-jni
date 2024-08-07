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
#include <Jolt/Physics/Collision/Shape/ConvexHullShape.h>
#include "auto/com_github_stephengold_joltjni_ConvexHullShapeSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_ConvexHullShapeSettings
 * Method:    createConvexHullShapeSettings
 * Signature: (ILjava/nio/FloatBuffer;)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ConvexHullShapeSettings_createConvexHullShapeSettings
  (JNIEnv *pEnv, jclass, jint numPoints, jobject buffer) {
    const jfloat * const pFloats
            = (jfloat *) pEnv->GetDirectBufferAddress(buffer);
    Vec3 * const pPoints = new Vec3[numPoints];
    TRACE_NEW("Vec3[]", pPoints)
    for (jint i = 0; i < numPoints; ++i) {
        float x = pFloats[3 * i];
        float y = pFloats[3 * i + 1];
        float z = pFloats[3 * i + 2];
        pPoints[i].Set(x, y, z);
    }
    ConvexHullShapeSettings * const pResult
            = new ConvexHullShapeSettings(pPoints, numPoints);
    TRACE_NEW("ConvexHullShapeSettings", pResult)
    TRACE_DELETE("Vec3[]", pPoints)
    delete pPoints;
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_ConvexHullShapeSettings
 * Method:    getMaxConvexRadius
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ConvexHullShapeSettings_getMaxConvexRadius
  (JNIEnv *, jclass, jlong settingsVa) {
    const ConvexHullShapeSettings * const pSettings
            = reinterpret_cast<ConvexHullShapeSettings *> (settingsVa);
    float result = pSettings->mMaxConvexRadius;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ConvexHullShapeSettings
 * Method:    setMaxConvexRadius
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ConvexHullShapeSettings_setMaxConvexRadius
  (JNIEnv *, jclass, jlong settingsVa, jfloat radius) {
    ConvexHullShapeSettings * const pSettings
            = reinterpret_cast<ConvexHullShapeSettings *> (settingsVa);
    pSettings->mMaxConvexRadius = radius;
}