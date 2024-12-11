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
#include "Jolt/Geometry/ClosestPoint.h"
#include "auto/com_github_stephengold_joltjni_ClosestPoint.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_ClosestPoint
 * Method:    getClosestPointOnLine
 * Signature: (FFFFFF[I[F)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ClosestPoint_getClosestPointOnLine
  (JNIEnv *pEnv, jclass, jfloat ax, jfloat ay, jfloat az, jfloat bx, jfloat by,
  jfloat bz, jintArray storeSet, jfloatArray storePoint) {
    const Vec3 a(ax, ay, az);
    const Vec3 b(bx, by, bz);
    uint32 outSet;
    const Vec3 result = ClosestPoint::GetClosestPointOnLine(a, b, outSet);
    jboolean isCopy;
    jint * const pSet = pEnv->GetIntArrayElements(storeSet, &isCopy);
    pSet[0] = outSet;
    pEnv->ReleaseIntArrayElements(storeSet, pSet, 0);
    jfloat * const pPoint = pEnv->GetFloatArrayElements(storePoint, &isCopy);
    pPoint[0] = result.GetX();
    pPoint[1] = result.GetY();
    pPoint[2] = result.GetZ();
    pEnv->ReleaseFloatArrayElements(storePoint, pPoint, 0);
}

/*
 * Class:     com_github_stephengold_joltjni_ClosestPoint
 * Method:    getClosestPointOnTetrahedron
 * Signature: (FFFFFFFFFFFF[I[F)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ClosestPoint_getClosestPointOnTetrahedron
  (JNIEnv *pEnv, jclass, jfloat ax, jfloat ay, jfloat az, jfloat bx, jfloat by,
  jfloat bz, jfloat cx, jfloat cy, jfloat cz, jfloat dx, jfloat dy, jfloat dz,
  jintArray storeSet, jfloatArray storePoint) {
    const Vec3 a(ax, ay, az);
    const Vec3 b(bx, by, bz);
    const Vec3 c(cx, cy, cz);
    const Vec3 d(dx, dy, dz);
    uint32 outSet;
    const Vec3 result = ClosestPoint::GetClosestPointOnTetrahedron(a, b, c, d, outSet);
    jboolean isCopy;
    jint * const pSet = pEnv->GetIntArrayElements(storeSet, &isCopy);
    pSet[0] = outSet;
    pEnv->ReleaseIntArrayElements(storeSet, pSet, 0);
    jfloat * const pPoint = pEnv->GetFloatArrayElements(storePoint, &isCopy);
    pPoint[0] = result.GetX();
    pPoint[1] = result.GetY();
    pPoint[2] = result.GetZ();
    pEnv->ReleaseFloatArrayElements(storePoint, pPoint, 0);
}

/*
 * Class:     com_github_stephengold_joltjni_ClosestPoint
 * Method:    getClosestPointOnTriangle
 * Signature: (FFFFFFFFF[I[F)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ClosestPoint_getClosestPointOnTriangle
  (JNIEnv *pEnv, jclass, jfloat ax, jfloat ay, jfloat az, jfloat bx, jfloat by,
  jfloat bz, jfloat cx, jfloat cy, jfloat cz, jintArray storeSet,
  jfloatArray storePoint) {
    const Vec3 a(ax, ay, az);
    const Vec3 b(bx, by, bz);
    const Vec3 c(cx, cy, cz);
    uint32 outSet;
    const Vec3 result = ClosestPoint::GetClosestPointOnTriangle(a, b, c, outSet);
    jboolean isCopy;
    jint * const pSet = pEnv->GetIntArrayElements(storeSet, &isCopy);
    pSet[0] = outSet;
    pEnv->ReleaseIntArrayElements(storeSet, pSet, 0);
    jfloat * const pPoint = pEnv->GetFloatArrayElements(storePoint, &isCopy);
    pPoint[0] = result.GetX();
    pPoint[1] = result.GetY();
    pPoint[2] = result.GetZ();
    pEnv->ReleaseFloatArrayElements(storePoint, pPoint, 0);
}

/*
 * Class:     com_github_stephengold_joltjni_ClosestPoint
 * Method:    getBaryCentricCoordinates2
 * Signature: (FFFFFF[F)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ClosestPoint_getBaryCentricCoordinates2
  (JNIEnv *pEnv, jclass, jfloat ax, jfloat ay, jfloat az, jfloat bx, jfloat by,
  jfloat bz, jfloatArray storeUv) {
    const Vec3 a(ax, ay, az);
    const Vec3 b(bx, by, bz);
    float u, v;
    const bool result = ClosestPoint::GetBaryCentricCoordinates(a, b, u, v);
    jboolean isCopy;
    jfloat * const pUv = pEnv->GetFloatArrayElements(storeUv, &isCopy);
    pUv[0] = u;
    pUv[1] = v;
    pEnv->ReleaseFloatArrayElements(storeUv, pUv, 0);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ClosestPoint
 * Method:    getBaryCentricCoordinates3
 * Signature: (FFFFFFFFF[F)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ClosestPoint_getBaryCentricCoordinates3
  (JNIEnv *pEnv, jclass, jfloat ax, jfloat ay, jfloat az, jfloat bx, jfloat by,
  jfloat bz, jfloat cx, jfloat cy, jfloat cz, jfloatArray storeUvw) {
    const Vec3 a(ax, ay, az);
    const Vec3 b(bx, by, bz);
    const Vec3 c(cx, cy, cz);
    float u, v, w;
    const bool result = ClosestPoint::GetBaryCentricCoordinates(a, b, c, u, v, w);
    jboolean isCopy;
    jfloat * const pUvw = pEnv->GetFloatArrayElements(storeUvw, &isCopy);
    pUvw[0] = u;
    pUvw[1] = v;
    pUvw[2] = w;
    pEnv->ReleaseFloatArrayElements(storeUvw, pUvw, 0);
    return result;
}