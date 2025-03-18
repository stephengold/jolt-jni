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
#include "Jolt/Physics/Collision/Shape/ConvexHullShape.h"
#include "auto/com_github_stephengold_joltjni_ConvexHullShapeSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_ConvexHullShapeSettings
 * Method:    countPoints
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_ConvexHullShapeSettings_countPoints
  (JNIEnv *, jclass, jlong settingsVa) {
    const ConvexHullShapeSettings * const pSettings
            = reinterpret_cast<ConvexHullShapeSettings *> (settingsVa);
    const Array<Vec3>::size_type result = pSettings->mPoints.size();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ConvexHullShapeSettings
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ConvexHullShapeSettings_createDefault
  (JNIEnv *, jclass) {
    ConvexHullShapeSettings * const pResult = new ConvexHullShapeSettings();
    TRACE_NEW("ConvexHullShapeSettings", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_ConvexHullShapeSettings
 * Method:    createSettings
 * Signature: (ILjava/nio/FloatBuffer;FJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ConvexHullShapeSettings_createSettings
  (JNIEnv *pEnv, jclass, jint numPoints, jobject buffer, jfloat maxConvexRadius, jlong materialVa) {
    const jfloat * const pFloats
            = (jfloat *) pEnv->GetDirectBufferAddress(buffer);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    Vec3 * const pPoints = new Vec3[numPoints];
    TRACE_NEW("Vec3[]", pPoints)
    for (jint i = 0; i < numPoints; ++i) {
        const float x = pFloats[3 * i];
        const float y = pFloats[3 * i + 1];
        const float z = pFloats[3 * i + 2];
        pPoints[i].Set(x, y, z);
    }
    const PhysicsMaterial * const pMaterial
            = reinterpret_cast<PhysicsMaterial *> (materialVa);
    ConvexHullShapeSettings * const pResult = new ConvexHullShapeSettings(
            pPoints, numPoints, maxConvexRadius, pMaterial);
    TRACE_NEW("ConvexHullShapeSettings", pResult)
    TRACE_DELETE("Vec3[]", pPoints)
    delete[] pPoints;
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_ConvexHullShapeSettings
 * Method:    getHullTolerance
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ConvexHullShapeSettings_getHullTolerance
  (JNIEnv *, jclass, jlong settingsVa) {
    const ConvexHullShapeSettings * const pSettings
            = reinterpret_cast<ConvexHullShapeSettings *> (settingsVa);
    const float result = pSettings->mHullTolerance;
    return result;
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
    const float result = pSettings->mMaxConvexRadius;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ConvexHullShapeSettings
 * Method:    getMaxErrorConvexRadius
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ConvexHullShapeSettings_getMaxErrorConvexRadius
  (JNIEnv *, jclass, jlong settingsVa) {
    const ConvexHullShapeSettings * const pSettings
            = reinterpret_cast<ConvexHullShapeSettings *> (settingsVa);
    const float result = pSettings->mMaxErrorConvexRadius;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ConvexHullShapeSettings
 * Method:    setHullTolerance
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ConvexHullShapeSettings_setHullTolerance
  (JNIEnv *, jclass, jlong settingsVa, jfloat tolerance) {
    ConvexHullShapeSettings * const pSettings
            = reinterpret_cast<ConvexHullShapeSettings *> (settingsVa);
    pSettings->mHullTolerance = tolerance;
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

/*
 * Class:     com_github_stephengold_joltjni_ConvexHullShapeSettings
 * Method:    setMaxErrorConvexRadius
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ConvexHullShapeSettings_setMaxErrorConvexRadius
  (JNIEnv *, jclass, jlong settingsVa, jfloat maxError) {
    ConvexHullShapeSettings * const pSettings
            = reinterpret_cast<ConvexHullShapeSettings *> (settingsVa);
    pSettings->mMaxErrorConvexRadius = maxError;
}

/*
 * Class:     com_github_stephengold_joltjni_ConvexHullShapeSettings
 * Method:    setPoints
 * Signature: (JILjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ConvexHullShapeSettings_setPoints
  (JNIEnv *pEnv, jclass, jlong settingsVa, jint numPoints, jobject buffer) {
    ConvexHullShapeSettings * const pSettings
            = reinterpret_cast<ConvexHullShapeSettings *> (settingsVa);
    pSettings->mPoints.clear();
    pSettings->mPoints.reserve(numPoints);
    const jfloat * const pFloats
            = (jfloat *) pEnv->GetDirectBufferAddress(buffer);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    for (jint i = 0; i < numPoints; ++i) {
        const float x = pFloats[3 * i];
        const float y = pFloats[3 * i + 1];
        const float z = pFloats[3 * i + 2];
        const Vec3 vec(x, y, z);
        pSettings->mPoints.push_back(vec);
    }
}