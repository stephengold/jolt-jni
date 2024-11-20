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
#include "Jolt/Geometry/ConvexHullBuilder.h"
#include "auto/com_github_stephengold_joltjni_ChbFace.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_ChbFace
 * Method:    getCentroid
 * Signature: (J[F)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ChbFace_getCentroid
  (JNIEnv *pEnv, jclass, jlong faceVa, jfloatArray storeFloats) {
    const ConvexHullBuilder::Face * const pFace
            = reinterpret_cast<ConvexHullBuilder::Face *> (faceVa);
    const Vec3 result = pFace->mCentroid;
    jboolean isCopy;
    jfloat * const pStoreFloats
            = pEnv->GetFloatArrayElements(storeFloats, &isCopy);
    pStoreFloats[0] = result.GetX();
    pStoreFloats[1] = result.GetY();
    pStoreFloats[2] = result.GetZ();
    pEnv->ReleaseFloatArrayElements(storeFloats, pStoreFloats, 0);
}

/*
 * Class:     com_github_stephengold_joltjni_ChbFace
 * Method:    getFirstEdge
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ChbFace_getFirstEdge
  (JNIEnv *, jclass, jlong faceVa) {
    const ConvexHullBuilder::Face * const pFace
            = reinterpret_cast<ConvexHullBuilder::Face *> (faceVa);
    const ConvexHullBuilder::Edge * const pResult = pFace->mFirstEdge;
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_ChbFace
 * Method:    getNormal
 * Signature: (J[F)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ChbFace_getNormal
  (JNIEnv *pEnv, jclass, jlong faceVa, jfloatArray storeFloats) {
    const ConvexHullBuilder::Face * const pFace
            = reinterpret_cast<ConvexHullBuilder::Face *> (faceVa);
    const Vec3 result = pFace->mNormal;
    jboolean isCopy;
    jfloat * const pStoreFloats
            = pEnv->GetFloatArrayElements(storeFloats, &isCopy);
    pStoreFloats[0] = result.GetX();
    pStoreFloats[1] = result.GetY();
    pStoreFloats[2] = result.GetZ();
    pEnv->ReleaseFloatArrayElements(storeFloats, pStoreFloats, 0);
}

/*
 * Class:     com_github_stephengold_joltjni_ChbFace
 * Method:    isFacing
 * Signature: (JFFF)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ChbFace_isFacing
  (JNIEnv *, jclass, jlong faceVa, jfloat x, jfloat y, jfloat z) {
    const ConvexHullBuilder::Face * const pFace
            = reinterpret_cast<ConvexHullBuilder::Face *> (faceVa);
    const Vec3 point(x, y, z);
    const bool result = pFace->IsFacing(point);
    return result;
}