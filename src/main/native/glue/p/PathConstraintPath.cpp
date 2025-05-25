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
#include "Jolt/Physics/Constraints/PathConstraint.h"

#include "auto/com_github_stephengold_joltjni_PathConstraintPath.h"
#include "auto/com_github_stephengold_joltjni_PathConstraintPathRef.h"
#include "glue/glue.h"

using namespace JPH;

IMPLEMENT_REF(PathConstraintPath,
  Java_com_github_stephengold_joltjni_PathConstraintPathRef_copy,
  Java_com_github_stephengold_joltjni_PathConstraintPathRef_createEmpty,
  Java_com_github_stephengold_joltjni_PathConstraintPathRef_free,
  Java_com_github_stephengold_joltjni_PathConstraintPathRef_getPtr,
  Java_com_github_stephengold_joltjni_PathConstraintPathRef_toRefC)

/*
 * Class:     com_github_stephengold_joltjni_PathConstraintPath
 * Method:    getClosestPoint
 * Signature: (JFFFF)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PathConstraintPath_getClosestPoint
  (JNIEnv *, jclass, jlong pathVa, jfloat x, jfloat y, jfloat z, jfloat fractionHint) {
    const PathConstraintPath * const pPath
            = reinterpret_cast<PathConstraintPath *> (pathVa);
    const Vec3 location(x, y, z);
    const float result = pPath->GetClosestPoint(location, fractionHint);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraintPath
 * Method:    getPathMaxFraction
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PathConstraintPath_getPathMaxFraction
  (JNIEnv *, jclass, jlong pathVa) {
    const PathConstraintPath * const pPath
            = reinterpret_cast<PathConstraintPath *> (pathVa);
    const float result = pPath->GetPathMaxFraction();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraintPath
 * Method:    getPointOnPath
 * Signature: (JF[F)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PathConstraintPath_getPointOnPath
  (JNIEnv *pEnv, jclass, jlong pathVa, jfloat amount, jfloatArray storeFloats) {
    const PathConstraintPath * const pPath
            = reinterpret_cast<PathConstraintPath *> (pathVa);
    Vec3 location, tangent, normal, binormal;
    pPath->GetPointOnPath(amount, location, tangent, normal, binormal);
    jboolean isCopy;
    jfloat * const pStoreFloats
            = pEnv->GetFloatArrayElements(storeFloats, &isCopy);
    pStoreFloats[0] = location.GetX();
    pStoreFloats[1] = location.GetY();
    pStoreFloats[2] = location.GetZ();
    pStoreFloats[3] = tangent.GetX();
    pStoreFloats[4] = tangent.GetY();
    pStoreFloats[5] = tangent.GetZ();
    pStoreFloats[6] = normal.GetX();
    pStoreFloats[7] = normal.GetY();
    pStoreFloats[8] = normal.GetZ();
    pStoreFloats[9] = binormal.GetX();
    pStoreFloats[10] = binormal.GetY();
    pStoreFloats[11] = binormal.GetZ();
    pEnv->ReleaseFloatArrayElements(storeFloats, pStoreFloats, 0);
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraintPath
 * Method:    getRefCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_PathConstraintPath_getRefCount
  (JNIEnv *, jclass, jlong pathVa) {
    const PathConstraintPath * const pPath
            = reinterpret_cast<PathConstraintPath *> (pathVa);
    const uint32 result = pPath->GetRefCount();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraintPath
 * Method:    isLooping
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_PathConstraintPath_isLooping
  (JNIEnv *, jclass, jlong pathVa) {
    const PathConstraintPath * const pPath
            = reinterpret_cast<PathConstraintPath *> (pathVa);
    const bool result = pPath->IsLooping();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraintPath
 * Method:    saveBinaryState
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PathConstraintPath_saveBinaryState
  (JNIEnv *, jclass, jlong pathVa, jlong streamVa) {
    const PathConstraintPath * const pPath
            = reinterpret_cast<PathConstraintPath *> (pathVa);
    StreamOut * const pStream = reinterpret_cast<StreamOut *> (streamVa);
    pPath->SaveBinaryState(*pStream);
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraintPath
 * Method:    setEmbedded
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PathConstraintPath_setEmbedded
  (JNIEnv *, jclass, jlong pathVa) {
    PathConstraintPath * const pPath
            = reinterpret_cast<PathConstraintPath *> (pathVa);
    pPath->SetEmbedded();
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraintPath
 * Method:    setIsLooping
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PathConstraintPath_setIsLooping
  (JNIEnv *, jclass, jlong pathVa, jboolean setting) {
    PathConstraintPath * const pPath
            = reinterpret_cast<PathConstraintPath *> (pathVa);
    pPath->SetIsLooping(setting);
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraintPath
 * Method:    sRestoreFromBinaryState
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PathConstraintPath_sRestoreFromBinaryState
  (JNIEnv *, jclass, jlong streamVa) {
    StreamIn * const pStream = reinterpret_cast<StreamIn *> (streamVa);
    PathConstraintPath::PathResult * const pResult
            = new PathConstraintPath::PathResult();
    TRACE_NEW("PathConstraintPath::PathResult", pResult);
    *pResult = PathConstraintPath::sRestoreFromBinaryState(*pStream);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraintPath
 * Method:    toRef
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PathConstraintPath_toRef
  (JNIEnv *, jclass, jlong pathVa) {
    PathConstraintPath * const pPath
            = reinterpret_cast<PathConstraintPath *> (pathVa);
    Ref<PathConstraintPath> * const pResult
            = new Ref<PathConstraintPath>(pPath);
    TRACE_NEW("Ref<PathConstraintPath>", pResult)
    return reinterpret_cast<jlong> (pResult);
}