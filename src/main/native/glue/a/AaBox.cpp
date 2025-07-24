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
 * Author: wil
 */
#include "Jolt/Jolt.h"
#include "Jolt/Geometry/AABox.h"
#include "auto/com_github_stephengold_joltjni_AaBox.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    contains
 * Signature: (JFFF)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_AaBox_contains
  (JNIEnv *, jclass, jlong boxVa, jfloat x, jfloat y, jfloat z) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const Vec3 point(x, y, z);
    const bool result = pBox->Contains(point);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    containsAaBox
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_AaBox_containsAaBox
  (JNIEnv *, jclass, jlong boxVa, jlong otherBoxVa) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const AABox * const pOther = reinterpret_cast<AABox *> (otherBoxVa);
    const bool result = pBox->Contains(*pOther);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    createAaBox
 * Signature: (FFFFFF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_AaBox_createAaBox
  (JNIEnv *, jclass, jfloat minX, jfloat minY, jfloat minZ, jfloat maxX, jfloat maxY, jfloat maxZ) {
    const Vec3 min(minX, minY, minZ);
    const Vec3 max(maxX, maxY, maxZ);
    AABox * const pResult = new AABox(min, max);
    TRACE_NEW("AABox", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    createBiggest
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_AaBox_createBiggest
  (JNIEnv *, jclass) {
    AABox * const pResult = new AABox();
    TRACE_NEW("AABox", pResult)
    *pResult = AABox::sBiggest();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    createCubic
 * Signature: (FFFF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_AaBox_createCubic
  (JNIEnv *, jclass, jfloat centerX, jfloat centerY, jfloat centerZ,
  jfloat halfExtent) {
    const Vec3 center(centerX, centerY, centerZ);
    AABox * const pResult = new AABox(center, halfExtent);
    TRACE_NEW("AABox", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_AaBox_createCopy
  BODYOF_CREATE_COPY(AABox)

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_AaBox_createDefault
  BODYOF_CREATE_DEFAULT(AABox)

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    encapsulate
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_AaBox_encapsulate
  (JNIEnv *, jclass, jlong boxVa, jfloat locX, jfloat locY, jfloat locZ) {
    AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const Vec3 location(locX, locY, locZ);
    pBox->Encapsulate(location);
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    encapsulateBox
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_AaBox_encapsulateBox
  (JNIEnv *, jclass, jlong boxVa, jlong includeBoxVa) {
    AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const AABox * const pRhs = reinterpret_cast<AABox *> (includeBoxVa);
    pBox->Encapsulate(*pRhs);
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    encapsulateTriangle
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_AaBox_encapsulateTriangle
  (JNIEnv *, jclass, jlong  boxVa, jlong triangleVa) {
    AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const Triangle * const pTriangle = reinterpret_cast<Triangle *> (triangleVa);
    pBox->Encapsulate(*pTriangle);
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    encapsulatedTriangleFromVertices
 * Signature: (JILjava/nio/FloatBuffer;J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_AaBox_encapsulatedTriangleFromVertices
  (JNIEnv *pEnv, jclass, jlong boxVa, jint numVertices, jobject floatBuffer,
  jlong triangleVa) {
    AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const IndexedTriangle * const pIndices
            = reinterpret_cast<IndexedTriangle *> (triangleVa);
    const jfloat * const pFloats
            = (jfloat *) pEnv->GetDirectBufferAddress(floatBuffer);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const jlong capacityFloats = pEnv->GetDirectBufferCapacity(floatBuffer);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    JPH_ASSERT(capacityFloats >= 3*numVertices);
    VertexList vertices;
    for (jint i = 0; i < numVertices; ++i) {
        const float x = pFloats[3 * i];
        const float y = pFloats[3 * i + 1];
        const float z = pFloats[3 * i + 2];
        const Float3 float3(x, y, z);
        vertices.push_back(float3);
    }
    pBox->Encapsulate(vertices, *pIndices);
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    ensureMinimalEdgeLength
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_AaBox_ensureMinimalEdgeLength
  (JNIEnv *, jclass, jlong boxVa, jfloat minEdgeLength) {
    AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    pBox->EnsureMinimalEdgeLength(minEdgeLength);
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    expandBy
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_AaBox_expandBy
  (JNIEnv *, jclass, jlong boxVa, jfloat dx, jfloat dy, jfloat dz) {
    AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const Vec3 deltas(dx, dy, dz);
    pBox->ExpandBy(deltas);
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_AaBox_free
  BODYOF_FREE(AABox)

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    getCenterX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_AaBox_getCenterX
  (JNIEnv *, jclass, jlong boxVa) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const float result = pBox->GetCenter().GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    getCenterY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_AaBox_getCenterY
  (JNIEnv *, jclass, jlong boxVa) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const float result = pBox->GetCenter().GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    getCenterZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_AaBox_getCenterZ
  (JNIEnv *, jclass, jlong boxVa) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const float result = pBox->GetCenter().GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    getClosestPoint
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_AaBox_getClosestPoint
  (JNIEnv *pEnv, jclass, jlong boxVa, jobject floatBuffer) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    jfloat * const pFloats
            = (jfloat *) pEnv->GetDirectBufferAddress(floatBuffer);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const jlong capacityFloats = pEnv->GetDirectBufferCapacity(floatBuffer);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3 location(pFloats[0], pFloats[1], pFloats[2]);
    const Vec3 result = pBox->GetClosestPoint(location);
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    getExtentX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_AaBox_getExtentX
  (JNIEnv *, jclass, jlong boxVa) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const float result = pBox->GetExtent().GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    getExtentY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_AaBox_getExtentY
  (JNIEnv *, jclass, jlong boxVa) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const float result = pBox->GetExtent().GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    getExtentZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_AaBox_getExtentZ
  (JNIEnv *, jclass, jlong boxVa) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const float result = pBox->GetExtent().GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    getMaxX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_AaBox_getMaxX
  (JNIEnv *, jclass, jlong boxVa) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const float result = pBox->mMax.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    getMaxY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_AaBox_getMaxY
  (JNIEnv *, jclass, jlong boxVa) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const float result = pBox->mMax.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    getMaxZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_AaBox_getMaxZ
  (JNIEnv *, jclass, jlong boxVa) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const float result = pBox->mMax.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    getMinX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_AaBox_getMinX
  (JNIEnv *, jclass, jlong boxVa) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const float result = pBox->mMin.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    getMinY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_AaBox_getMinY
  (JNIEnv *, jclass, jlong boxVa) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const float result = pBox->mMin.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    getMinZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_AaBox_getMinZ
  (JNIEnv *, jclass, jlong boxVa) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const float result = pBox->mMin.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    getSizeX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_AaBox_getSizeX
  (JNIEnv *, jclass, jlong boxVa) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const float result = pBox->GetSize().GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    getSizeY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_AaBox_getSizeY
  (JNIEnv *, jclass, jlong boxVa) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const float result = pBox->GetSize().GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    getSizeZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_AaBox_getSizeZ
  (JNIEnv *, jclass, jlong boxVa) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const float result = pBox->GetSize().GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    getSqDistanceTo
 * Signature: (JFFF)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_AaBox_getSqDistanceTo
  (JNIEnv *, jclass, jlong boxVa, jfloat px, jfloat py, jfloat pz) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const Vec3 point(px, py, pz);
    const float result = pBox->GetSqDistanceTo(point);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    getSurfaceArea
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_AaBox_getSurfaceArea
  (JNIEnv *, jclass, jlong boxVa) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const float result = pBox->GetSurfaceArea();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    getSupport
 * Signature: (JFFF)[F
 */
JNIEXPORT jfloatArray JNICALL Java_com_github_stephengold_joltjni_AaBox_getSupport
  (JNIEnv *pEnv, jclass clazz, jlong boxVa, jfloat dx, jfloat dy, jfloat dz) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const Vec3 direction(dx, dy, dz);
    const Vec3 support = pBox->GetSupport(direction);
    jfloatArray result;
    result = pEnv->NewFloatArray(3);
    if (result == NULL) {
        return NULL; /* out of memory error thrown */
    }
    // fill a temp structure to use to populate the java int array
    jfloat fill[3];
    fill[0] = support.GetX();
    fill[1] = support.GetY();
    fill[2] = support.GetZ();
    // move from the temp structure to the java structure
    pEnv->SetFloatArrayRegion(result, 0, 3 /*size*/, fill);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    getVolume
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_AaBox_getVolume
  (JNIEnv *, jclass, jlong boxVa) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const float result = pBox->GetVolume();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    overlapsAaBox
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_AaBox_overlapsAaBox
  (JNIEnv *, jclass, jlong boxVa, jlong otherVa) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const AABox * const pOther = reinterpret_cast<AABox *> (otherVa);
    const bool result = pBox->Overlaps(*pOther);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    overlaps
 * Signature: (JFFFF)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_AaBox_overlaps
  (JNIEnv *, jclass, jlong boxVa, jfloat pc, jfloat pnx, jfloat pny, jfloat pnz) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const Vec3 normal(pnx, pny, pnz);
    const Plane plane(normal, pc);
    const bool result = pBox->Overlaps(plane);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    isValid
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_AaBox_isValid
  (JNIEnv *, jclass, jlong boxVa) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const bool result = pBox->IsValid();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    scaled
 * Signature: (JFFF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_AaBox_scaled
  (JNIEnv *, jclass, jlong boxVa, jfloat sx, jfloat sy, jfloat sz) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const Vec3 factors(sx, sy, sz);
    AABox * const pResult = new AABox();
    TRACE_NEW("AABox", pResult)
    *pResult = pBox->Scaled(factors);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    setEmpty
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_AaBox_setEmpty
  (JNIEnv *, jclass, jlong boxVa) {
    AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    pBox->SetEmpty();
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    setMax
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_AaBox_setMax
  (JNIEnv *, jclass, jlong boxVa, jfloat x, jfloat y, jfloat z) {
    AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const Vec3 max(x, y, z);
    pBox->mMax = max;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    setMin
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_AaBox_setMin
  (JNIEnv *, jclass, jlong boxVa, jfloat x, jfloat y, jfloat z) {
    AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const Vec3 min(x, y, z);
    pBox->mMin = min;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    transformed
 * Signature: (JJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_AaBox_transformed
  (JNIEnv *, jclass, jlong boxVa, jlong matrixVa) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const Mat44 * const pMatrix = reinterpret_cast<Mat44 *> (matrixVa);
    AABox * const pResult = new AABox();
    TRACE_NEW("AABox", pResult)
    *pResult = pBox->Transformed(*pMatrix);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    translate
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_AaBox_translate
  (JNIEnv *, jclass, jlong boxVa, jfloat x, jfloat y, jfloat z) {
    AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const Vec3 offset(x, y, z);
    pBox->Translate(offset);
}