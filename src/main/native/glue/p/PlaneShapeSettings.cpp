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
#include "Jolt/Physics/Collision/Shape/PlaneShape.h"
#include "auto/com_github_stephengold_joltjni_PlaneShapeSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_BoxShapeSettings
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PlaneShapeSettings_createCopy
  BODYOF_CREATE_COPY(PlaneShapeSettings)

/*
 * Class:     com_github_stephengold_joltjni_BoxShapeSettings
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PlaneShapeSettings_createDefault
  BODYOF_CREATE_DEFAULT(PlaneShapeSettings)

/*
 * Class:     com_github_stephengold_joltjni_PlaneShapeSettings
 * Method:    createPlaneShapeSettings
 * Signature: (FFFFJF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PlaneShapeSettings_createPlaneShapeSettings
  (JNIEnv *, jclass, jfloat nx, jfloat ny, jfloat nz, jfloat planeConstant,
  jlong materialVa, jfloat halfExtent) {
    const Vec3 normal(nx, ny, nz);
    const Plane plane(normal, planeConstant);
    const PhysicsMaterial * const pMaterial
            = reinterpret_cast<PhysicsMaterial *> (materialVa);
    PlaneShapeSettings * const pSettings
            = new PlaneShapeSettings(plane, pMaterial, halfExtent);
    TRACE_NEW("PlaneShapeSettings", pSettings)
    return reinterpret_cast<jlong> (pSettings);
}

/*
 * Class:     com_github_stephengold_joltjni_PlaneShapeSettings
 * Method:    getHalfExtent
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PlaneShapeSettings_getHalfExtent
  (JNIEnv *, jclass, jlong settingsVa) {
    const PlaneShapeSettings * const pSettings
            = reinterpret_cast<PlaneShapeSettings *> (settingsVa);
    const float result = pSettings->mHalfExtent;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PlaneShapeSettings
 * Method:    getMaterial
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PlaneShapeSettings_getMaterial
  (JNIEnv *, jclass, jlong settingsVa) {
    const PlaneShapeSettings * const pSettings
            = reinterpret_cast<PlaneShapeSettings *> (settingsVa);
    const PhysicsMaterial * const pResult = pSettings->mMaterial.GetPtr();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_PlaneShapeSettings
 * Method:    getPlane
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PlaneShapeSettings_getPlane
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeFloats) {
    const PlaneShapeSettings * const pSettings
            = reinterpret_cast<PlaneShapeSettings *> (settingsVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 4);
    const Plane& result = pSettings->mPlane;
    const Vec3 normal = result.GetNormal();
    pFloats[0] = normal.GetX();
    pFloats[1] = normal.GetY();
    pFloats[2] = normal.GetZ();
    pFloats[3] = result.GetConstant();
}

/*
 * Class:     com_github_stephengold_joltjni_PlaneShapeSettings
 * Method:    setHalfExtent
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PlaneShapeSettings_setHalfExtent
  (JNIEnv *, jclass, jlong settingsVa, jfloat halfExtent) {
    PlaneShapeSettings * const pSettings
            = reinterpret_cast<PlaneShapeSettings *> (settingsVa);
    pSettings->mHalfExtent = halfExtent;
}

/*
 * Class:     com_github_stephengold_joltjni_PlaneShapeSettings
 * Method:    setMaterial
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PlaneShapeSettings_setMaterial
  (JNIEnv *, jclass, jlong settingsVa, jlong materialVa) {
    PlaneShapeSettings * const pSettings
            = reinterpret_cast<PlaneShapeSettings *> (settingsVa);
    const PhysicsMaterial * const pMaterial
            = reinterpret_cast<PhysicsMaterial *> (materialVa);
    pSettings->mMaterial = pMaterial;
}

/*
 * Class:     com_github_stephengold_joltjni_PlaneShapeSettings
 * Method:    setPlane
 * Signature: (JFFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PlaneShapeSettings_setPlane
  (JNIEnv *, jclass, jlong settingsVa, jfloat nx, jfloat ny, jfloat nz, jfloat c) {
    PlaneShapeSettings * const pSettings
            = reinterpret_cast<PlaneShapeSettings *> (settingsVa);
    const Vec3 normal(nx, ny, nz);
    const Plane plane(normal, c);
    pSettings->mPlane = plane;
}