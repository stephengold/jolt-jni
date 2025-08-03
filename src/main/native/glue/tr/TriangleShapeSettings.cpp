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
#include "Jolt/Physics/Collision/Shape/TriangleShape.h"
#include "auto/com_github_stephengold_joltjni_TriangleShapeSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_TriangleShapeSettings
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_TriangleShapeSettings_createCopy
  BODYOF_CREATE_COPY(TriangleShapeSettings)

/*
 * Class:     com_github_stephengold_joltjni_TriangleShapeSettings
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_TriangleShapeSettings_createDefault
  BODYOF_CREATE_DEFAULT(TriangleShapeSettings)

/*
 * Class:     com_github_stephengold_joltjni_TriangleShapeSettings
 * Method:    createTriangleShapeSettings
 * Signature: (FFFFFFFFFFJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_TriangleShapeSettings_createTriangleShapeSettings
  (JNIEnv *, jclass, jfloat v1x, jfloat v1y, jfloat v1z, jfloat v2x, jfloat v2y,
  jfloat v2z, jfloat v3x, jfloat v3y, jfloat v3z, jfloat convexRadius,
  jlong materialVa) {
    const Vec3 v1(v1x, v1y, v1z);
    const Vec3 v2(v2x, v2y, v2z);
    const Vec3 v3(v3x, v3y, v3z);
    const PhysicsMaterial * pMaterial;
    if (materialVa) {
        pMaterial = reinterpret_cast<PhysicsMaterial *> (materialVa);
    } else {
        pMaterial = nullptr;
    }
    const TriangleShapeSettings * const pSettings
            = new TriangleShapeSettings(v1, v2, v3, convexRadius, pMaterial);
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
 * Method:    getV1
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TriangleShapeSettings_getV1
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeFloats) {
    const TriangleShapeSettings * const pSettings
            = reinterpret_cast<TriangleShapeSettings *> (settingsVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& result = pSettings->mV1;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_TriangleShapeSettings
 * Method:    getV2
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TriangleShapeSettings_getV2
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeFloats) {
    const TriangleShapeSettings * const pSettings
            = reinterpret_cast<TriangleShapeSettings *> (settingsVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3 result = pSettings->mV2;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_TriangleShapeSettings
 * Method:    getV3
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TriangleShapeSettings_getV3
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeFloats) {
    const TriangleShapeSettings * const pSettings
            = reinterpret_cast<TriangleShapeSettings *> (settingsVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3 result = pSettings->mV3;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
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

/*
 * Class:     com_github_stephengold_joltjni_TriangleShapeSettings
 * Method:    setV1
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TriangleShapeSettings_setV1
  (JNIEnv *, jclass, jlong settingsVa, jfloat x, jfloat y, jfloat z) {
    TriangleShapeSettings * const pSettings
            = reinterpret_cast<TriangleShapeSettings *> (settingsVa);
    const Vec3 location(x, y, z);
    pSettings->mV1 = location;
}

/*
 * Class:     com_github_stephengold_joltjni_TriangleShapeSettings
 * Method:    setV2
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TriangleShapeSettings_setV2
  (JNIEnv *, jclass, jlong settingsVa, jfloat x, jfloat y, jfloat z) {
    TriangleShapeSettings * const pSettings
            = reinterpret_cast<TriangleShapeSettings *> (settingsVa);
    const Vec3 location(x, y, z);
    pSettings->mV2 = location;
}

/*
 * Class:     com_github_stephengold_joltjni_TriangleShapeSettings
 * Method:    setV3
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TriangleShapeSettings_setV3
  (JNIEnv *, jclass, jlong settingsVa, jfloat x, jfloat y, jfloat z) {
    TriangleShapeSettings * const pSettings
            = reinterpret_cast<TriangleShapeSettings *> (settingsVa);
    const Vec3 location(x, y, z);
    pSettings->mV3 = location;
}
