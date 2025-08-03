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
#include "Jolt/Physics/Collision/Shape/HeightFieldShape.h"
#include "Jolt/Physics/Collision/Shape/SubShapeID.h"
#include "glue/glue.h"

#include "auto/com_github_stephengold_joltjni_HeightFieldShape.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_HeightFieldShape
 * Method:    getBlockSize
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_HeightFieldShape_getBlockSize
  (JNIEnv *, jclass, jlong shapeVa) {
    const HeightFieldShape * const pShape
            = reinterpret_cast<HeightFieldShape *> (shapeVa);
    const uint result = pShape->GetBlockSize();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HeightFieldShape
 * Method:    getPositionX
 * Signature: (JII)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HeightFieldShape_getPositionX
  (JNIEnv *, jclass, jlong shapeVa, jint x, jint y) {
    const HeightFieldShape * const pShape
            = reinterpret_cast<HeightFieldShape *> (shapeVa);
    const float result = pShape->GetPosition(x, y).GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HeightFieldShape
 * Method:    getPositionY
 * Signature: (JII)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HeightFieldShape_getPositionY
  (JNIEnv *, jclass, jlong shapeVa, jint x, jint y) {
    const HeightFieldShape * const pShape
            = reinterpret_cast<HeightFieldShape *> (shapeVa);
    const float result = pShape->GetPosition(x, y).GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HeightFieldShape
 * Method:    getPositionZ
 * Signature: (JII)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HeightFieldShape_getPositionZ
  (JNIEnv *, jclass, jlong shapeVa, jint x, jint y) {
    const HeightFieldShape * const pShape
            = reinterpret_cast<HeightFieldShape *> (shapeVa);
    const float result = pShape->GetPosition(x, y).GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HeightFieldShape
 * Method:    getSurfaceNormal
 * Signature: (JIFFF[F)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HeightFieldShape_getSurfaceNormal
  (JNIEnv *pEnv, jclass, jlong shapeVa, jint subShapeId,
  jfloat x, jfloat y, jfloat z, jfloatArray storeFloats) {
    const HeightFieldShape * const pShape
            = reinterpret_cast<HeightFieldShape *> (shapeVa);
    SubShapeID id;
    id.SetValue(subShapeId);
    const Vec3 localLocation(x, y, z);
    const Vec3 result = pShape->GetSurfaceNormal(id, localLocation);
    jboolean isCopy;
    jfloat * const pStoreFloats
            = pEnv->GetFloatArrayElements(storeFloats, &isCopy);
    pStoreFloats[0] = result.GetX();
    pStoreFloats[1] = result.GetY();
    pStoreFloats[2] = result.GetZ();
    pEnv->ReleaseFloatArrayElements(storeFloats, pStoreFloats, 0);
}

/*
 * Class:     com_github_stephengold_joltjni_HeightFieldShape
 * Method:    isNoCollision
 * Signature: (JII)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_HeightFieldShape_isNoCollision
  (JNIEnv *, jclass, jlong shapeVa, jint x, jint y) {
    const HeightFieldShape * const pShape
            = reinterpret_cast<HeightFieldShape *> (shapeVa);
    const bool result = pShape->IsNoCollision(x, y);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HeightFieldShape
 * Method:    projectOntoSurface
 * Signature: (JFFF[F[I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_HeightFieldShape_projectOntoSurface
  (JNIEnv *pEnv, jclass, jlong shapeVa, jfloat x, jfloat y, jfloat z,
  jfloatArray storeFloats, jintArray storeSubShapeId) {
    const HeightFieldShape * const pShape
            = reinterpret_cast<HeightFieldShape *> (shapeVa);
    const Vec3 localLocation(x, y, z);
    Vec3 outLocation;
    SubShapeID subShapeId;
    const bool result = pShape->ProjectOntoSurface(
            localLocation, outLocation, subShapeId);
    jboolean isCopy;
    jint * const pStoreId = pEnv->GetIntArrayElements(storeSubShapeId, &isCopy);
    pStoreId[0] = subShapeId.GetValue();
    pEnv->ReleaseIntArrayElements(storeSubShapeId, pStoreId, 0);
    jfloat * const pStoreFloats
            = pEnv->GetFloatArrayElements(storeFloats, &isCopy);
    pStoreFloats[0] = outLocation.GetX();
    pStoreFloats[1] = outLocation.GetY();
    pStoreFloats[2] = outLocation.GetZ();
    pEnv->ReleaseFloatArrayElements(storeFloats, pStoreFloats, 0);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HeightFieldShape
 * Method:    setHeights
 * Signature: (JIIIILjava/nio/FloatBuffer;IJF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HeightFieldShape_setHeights
  (JNIEnv *pEnv, jclass, jlong shapeVa, jint startX, jint startY, jint sizeX,
  jint sizeY, jobject floatBuffer, jint stride, jlong allocatorVa,
  jfloat cosThresholdAngle) {
    HeightFieldShape * const pShape
            = reinterpret_cast<HeightFieldShape *> (shapeVa);
    const DIRECT_FLOAT_BUFFER(pEnv, floatBuffer, pHeightArray, capacityFloats);
    JPH_ASSERT(capacityFloats >= sizeX * sizeY);
    TempAllocator * const pAllocator
            = reinterpret_cast<TempAllocator *> (allocatorVa);
    pShape->SetHeights(startX, startY, sizeX, sizeY, pHeightArray, stride,
            *pAllocator, cosThresholdAngle);
    uint64 revisionCount = pShape->GetUserData();
    ++revisionCount;
    pShape->SetUserData(revisionCount);
}