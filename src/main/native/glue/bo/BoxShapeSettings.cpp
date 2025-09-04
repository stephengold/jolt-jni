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
#include "Jolt/Physics/Collision/Shape/BoxShape.h"
#include "auto/com_github_stephengold_joltjni_BoxShapeSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_BoxShapeSettings
 * Method:    createBoxShapeSettings
 * Signature: (FFFFJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BoxShapeSettings_createBoxShapeSettings
  (JNIEnv *, jclass, jfloat hx, jfloat hy, jfloat hz,
  jfloat convexRadius, jlong materialVa) {
    const Vec3 halfExtents(hx, hy, hz);
    const PhysicsMaterial * const pMaterial
            = reinterpret_cast<PhysicsMaterial *> (materialVa);
    BoxShapeSettings * const pResult
            = new BoxShapeSettings(halfExtents, convexRadius, pMaterial);
    TRACE_NEW("BoxShapeSettings", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_BoxShapeSettings
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BoxShapeSettings_createCopy
  BODYOF_CREATE_COPY(BoxShapeSettings)

/*
 * Class:     com_github_stephengold_joltjni_BoxShapeSettings
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BoxShapeSettings_createDefault
  BODYOF_CREATE_DEFAULT(BoxShapeSettings)

/*
 * Class:     com_github_stephengold_joltjni_BoxShapeSettings
 * Method:    getConvexRadius
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BoxShapeSettings_getConvexRadius
  (JNIEnv *, jclass, jlong settingsVa) {
    const BoxShapeSettings * const pSettings
            = reinterpret_cast<BoxShapeSettings *> (settingsVa);
    const float result = pSettings->mConvexRadius;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BoxShapeSettings
 * Method:    getHalfExtent
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BoxShapeSettings_getHalfExtent
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeFloats) {
    const BoxShapeSettings * const pSettings
            = reinterpret_cast<BoxShapeSettings *> (settingsVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& result = pSettings->mHalfExtent;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_BoxShapeSettings
 * Method:    setConvexRadius
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BoxShapeSettings_setConvexRadius
  (JNIEnv *, jclass, jlong settingsVa, jfloat radius) {
    BoxShapeSettings * const pSettings
            = reinterpret_cast<BoxShapeSettings *> (settingsVa);
    pSettings->mConvexRadius = radius;
}

/*
 * Class:     com_github_stephengold_joltjni_BoxShapeSettings
 * Method:    setHalfExtent
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BoxShapeSettings_setHalfExtent
  (JNIEnv *, jclass, jlong settingsVa, jfloat hx, jfloat hy, jfloat hz) {
    BoxShapeSettings * const pSettings
            = reinterpret_cast<BoxShapeSettings *> (settingsVa);
    const Vec3 halfExtents(hx, hy, hz);
    pSettings->mHalfExtent = halfExtents;
}