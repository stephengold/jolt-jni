/*
Copyright (c) 2025 Stephen Gold

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
#include "Jolt/Physics/Collision/TransformedShape.h"
#include "auto/com_github_stephengold_joltjni_TransformedShape.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_TransformedShape
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TransformedShape_free
  (JNIEnv *, jclass, jlong shapeVa) {
    TransformedShape * const pShape
            = reinterpret_cast<TransformedShape *> (shapeVa);
    TRACE_DELETE("TransformedShape", pShape)
    delete pShape;
}

/*
 * Class:     com_github_stephengold_joltjni_TransformedShape
 * Method:    castRay
 * Signature: (JJJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_TransformedShape_castRay
  (JNIEnv *, jclass, jlong shapeVa, jlong raycastVa, jlong resultVa) {
    const TransformedShape * const pShape
            = reinterpret_cast<TransformedShape *> (shapeVa);
    const RRayCast * const pRaycast = reinterpret_cast<RRayCast *> (raycastVa);
    RayCastResult * const pResult
            = reinterpret_cast<RayCastResult *> (resultVa);
    bool result = pShape->CastRay(*pRaycast, *pResult);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_TransformedShape
 * Method:    castRayAndCollect
 * Signature: (JJJJJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TransformedShape_castRayAndCollect
  (JNIEnv *, jclass, jlong shapeVa, jlong raycastVa, jlong settingsVa,
  jlong collectorVa, jlong filterVa) {
    const TransformedShape * const pShape
            = reinterpret_cast<TransformedShape *> (shapeVa);
    const RRayCast * const pRaycast = reinterpret_cast<RRayCast *> (raycastVa);
    const RayCastSettings * const pSettings
            = reinterpret_cast<RayCastSettings *> (settingsVa);
    CastRayCollector * const pCollector
            = reinterpret_cast<CastRayCollector *> (collectorVa);
    const ShapeFilter * const pFilter
            = reinterpret_cast<ShapeFilter *> (filterVa);
    pShape->CastRay(*pRaycast, *pSettings, *pCollector, *pFilter);
}

/*
 * Class:     com_github_stephengold_joltjni_TransformedShape
 * Method:    castShape
 * Signature: (JJJDDDJJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TransformedShape_castShape
  (JNIEnv *, jclass, jlong shapeVa, jlong shapecastVa, jlong settingsVa,
  jdouble xx, jdouble yy, jdouble zz, jlong collectorVa, jlong filterVa) {
    const TransformedShape * const pShape
            = reinterpret_cast<TransformedShape *> (shapeVa);
    const RShapeCast * const pShapecast
            = reinterpret_cast<RShapeCast *> (shapecastVa);
    const ShapeCastSettings * const pSettings
            = reinterpret_cast<ShapeCastSettings *> (settingsVa);
    const RVec3 base(xx, yy, zz);
    CastShapeCollector * pCollector
            = reinterpret_cast<CastShapeCollector *> (collectorVa);
    const ShapeFilter * const pFilter
            = reinterpret_cast<ShapeFilter *> (filterVa);
    pShape->CastShape(*pShapecast, *pSettings, base, *pCollector, *pFilter);
}

/*
 * Class:     com_github_stephengold_joltjni_TransformedShape
 * Method:    collectTransformedShapes
 * Signature: (JJJJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TransformedShape_collectTransformedShapes
  (JNIEnv *, jclass, jlong shapeVa, jlong boxVa, jlong collectorVa,
  jlong filterVa) {
    const TransformedShape * const pShape
            = reinterpret_cast<TransformedShape *> (shapeVa);
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    TransformedShapeCollector * pCollector
            = reinterpret_cast<TransformedShapeCollector *> (collectorVa);
    const ShapeFilter * const pFilter
            = reinterpret_cast<ShapeFilter *> (filterVa);
    pShape->CollectTransformedShapes(*pBox, *pCollector, *pFilter);
}

/*
 * Class:     com_github_stephengold_joltjni_TransformedShape
 * Method:    collidePoint
 * Signature: (JDDDJJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TransformedShape_collidePoint
  (JNIEnv *, jclass, jlong shapeVa, jdouble xx, jdouble yy, jdouble zz,
  jlong collectorVa, jlong filterVa) {
    const TransformedShape * const pShape
            = reinterpret_cast<TransformedShape *> (shapeVa);
    const RVec3 point(xx, yy, zz);
    CollidePointCollector * const pCollector
            = reinterpret_cast<CollidePointCollector *> (collectorVa);
    const ShapeFilter * const pFilter
            = reinterpret_cast<ShapeFilter *> (filterVa);
    pShape->CollidePoint(point, *pCollector, *pFilter);
}

/*
 * Class:     com_github_stephengold_joltjni_TransformedShape
 * Method:    collideShape
 * Signature: (JJFFFJJDDDJJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TransformedShape_collideShape
  (JNIEnv *, jclass, jlong transformedShapeVa, jlong testShapeVa, jfloat sx,
  jfloat sy, jfloat sz, jlong transformVa, jlong settingsVa, jdouble xx,
  jdouble yy, jdouble zz, jlong collectorVa, jlong filterVa) {
    const TransformedShape * const pTransformedShape
            = reinterpret_cast<TransformedShape *> (transformedShapeVa);
    const Shape * const pTestShape = reinterpret_cast<Shape *> (testShapeVa);
    const Vec3 shapeScale(sx, sy, sz);
    const RMat44 * const pComTransform
            = reinterpret_cast<RMat44 *> (transformVa);
    const CollideShapeSettings * const pSettings
            = reinterpret_cast<CollideShapeSettings *> (settingsVa);
    const RVec3 base(xx, yy, zz);
    CollideShapeCollector * const pCollector
            = reinterpret_cast<CollideShapeCollector *> (collectorVa);
    const ShapeFilter * const pFilter
            = reinterpret_cast<ShapeFilter *> (filterVa);
    pTransformedShape->CollideShape(pTestShape, shapeScale, *pComTransform,
            *pSettings, base, *pCollector, *pFilter);
}

/*
 * Class:     com_github_stephengold_joltjni_TransformedShape
 * Method:    getSupportingFace
 * Signature: (JJFFFDDDJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TransformedShape_getSupportingFace
  (JNIEnv *, jclass, jlong shapeVa, jlong idVa, jfloat dx, jfloat dy, jfloat dz,
  jdouble xx, jdouble yy, jdouble zz, jlong faceVa) {
    const TransformedShape * const pShape
            = reinterpret_cast<TransformedShape *> (shapeVa);
    const SubShapeID * const pId = reinterpret_cast<SubShapeID *> (idVa);
    const Vec3 direction(dx, dy, dz);
    const RVec3 base(xx, yy, zz);
    Shape::SupportingFace * const pFace
            = reinterpret_cast<Shape::SupportingFace *> (faceVa);
    pShape->GetSupportingFace(*pId, direction, base, *pFace);
}

/*
 * Class:     com_github_stephengold_joltjni_TransformedShape
 * Method:    getTrianglesNext
 * Signature: (JJILjava/nio/FloatBuffer;)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_TransformedShape_getTrianglesNext
  (JNIEnv *pEnv, jclass, jlong shapeVa, jlong contextVa, jint maxTriangles,
  jobject storeVertices) {
    const TransformedShape * const pShape
            = reinterpret_cast<TransformedShape *> (shapeVa);
    Shape::GetTrianglesContext * const pContext
            = reinterpret_cast<Shape::GetTrianglesContext *> (contextVa);
    Float3 * const pFloats
            = (Float3 *) pEnv->GetDirectBufferAddress(storeVertices);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const jlong capacityFloats = pEnv->GetDirectBufferCapacity(storeVertices);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    JPH_ASSERT(capacityFloats >= 3*maxTriangles);
    int result = pShape->GetTrianglesNext(*pContext, maxTriangles, pFloats);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_TransformedShape
 * Method:    getTrianglesStart
 * Signature: (JJJDDD)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TransformedShape_getTrianglesStart
  (JNIEnv *, jclass, jlong shapeVa, jlong contextVa, jlong boxVa,
  jdouble xx, jdouble yy, jdouble zz) {
    const TransformedShape * const pShape
            = reinterpret_cast<TransformedShape *> (shapeVa);
    Shape::GetTrianglesContext * const pContext
            = reinterpret_cast<Shape::GetTrianglesContext *> (contextVa);
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const RVec3 base(xx, yy, zz);
    pShape->GetTrianglesStart(*pContext, *pBox, base);
}