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
#include "Jolt/Physics/Collision/CollisionCollectorImpl.h"
#include "Jolt/Physics/Collision/TransformedShape.h"

#include "auto/com_github_stephengold_joltjni_TransformedShape.h"
#include "glue/glue.h"

using namespace JPH;

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
    CastShapeCollector * const pCollector
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
    TransformedShapeCollector * const pCollector
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
 * Method:    copyDebugTriangles
 * Signature: (JILjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TransformedShape_copyDebugTriangles
  (JNIEnv *pEnv, jclass, jlong transformedShapeVa, jint numTriangles,
  jobject storeBuffer) {
    const TransformedShape * const pShape
            = reinterpret_cast<TransformedShape *> (transformedShapeVa);
    Float3 *pFloat3 = (Float3 *) pEnv->GetDirectBufferAddress(storeBuffer);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const jlong capacityFloats = pEnv->GetDirectBufferCapacity(storeBuffer);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    JPH_ASSERT(capacityFloats >= 9 * numTriangles);
    const AABox bounds(AABox::sBiggest());
    AllHitCollisionCollector<TransformedShapeCollector> collector;
    pShape->CollectTransformedShapes(bounds, collector);
    for (const TransformedShape& transformedShape : collector.mHits) {
        const Shape * const pSh = transformedShape.mShape;
        Shape::GetTrianglesContext context;
        const Vec3 location(transformedShape.mShapePositionCOM);
        const Vec3 scale(transformedShape.mShapeScale);
        pSh->GetTrianglesStart(
            context, bounds, location, transformedShape.mShapeRotation, scale);
        while (numTriangles > 0) {
            const int maxRequest = std::max((int)numTriangles,
                    Shape::cGetTrianglesMinTrianglesRequested);
            const int numTrianglesCopied
                    = pSh->GetTrianglesNext(context, maxRequest, pFloat3);
            JPH_ASSERT(numTrianglesCopied <= numTriangles);
            if (numTrianglesCopied == 0) {
                break;
            }
            pFloat3 += 3 * numTrianglesCopied;
            numTriangles -= numTrianglesCopied;
        }
    }
}

/*
 * Class:     com_github_stephengold_joltjni_TransformedShape
 * Method:    countDebugTriangles
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_TransformedShape_countDebugTriangles
  (JNIEnv *, jclass, jlong transformedShapeVa) {
    const TransformedShape * const pShape
            = reinterpret_cast<TransformedShape *> (transformedShapeVa);
    uint result = 0;
    const AABox bounds(AABox::sBiggest());
    AllHitCollisionCollector<TransformedShapeCollector> collector;
    pShape->CollectTransformedShapes(bounds, collector);
    for (const TransformedShape& transformedShape : collector.mHits) {
        const Shape * const pSh = transformedShape.mShape;
        Shape::GetTrianglesContext context;
        const Vec3 location(transformedShape.mShapePositionCOM);
        const Vec3 scale(transformedShape.mShapeScale);
        pSh->GetTrianglesStart(
            context, bounds, location, transformedShape.mShapeRotation, scale);
        for (;;) {
            constexpr uint cMaxTriangles = 1000;
            Float3 vertices[3 * cMaxTriangles];
            const int numTrianglesCopied
                    = pSh->GetTrianglesNext(context, cMaxTriangles, vertices);
            if (numTrianglesCopied == 0) {
                break;
            }
            result += numTrianglesCopied;
        }
    }
    return result;
}

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
 * Method:    getCenterOfMassTransform
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_TransformedShape_getCenterOfMassTransform
  (JNIEnv *, jclass, jlong transformedShapeVa) {
    const TransformedShape * const pTransformedShape
            = reinterpret_cast<TransformedShape *> (transformedShapeVa);
    const RMat44& transform = pTransformedShape->GetCenterOfMassTransform();
    const RMat44 * const pResult = new RMat44(transform);
    TRACE_NEW("RMat44", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_TransformedShape
 * Method:    getShape
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_TransformedShape_getShape
  (JNIEnv *, jclass, jlong transformedShapeVa) {
    const TransformedShape * const pTransformedShape
            = reinterpret_cast<TransformedShape *> (transformedShapeVa);
    RefConst<Shape> shape = pTransformedShape->mShape;
    const Shape * const pResult = shape.GetPtr();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_TransformedShape
 * Method:    getShapePositionCom
 * Signature: (JLjava/nio/DoubleBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TransformedShape_getShapePositionCom
  (JNIEnv *pEnv, jclass, jlong transformedShapeVa, jobject storeDoubles) {
    const TransformedShape * const pTransformedShape
            = reinterpret_cast<TransformedShape *> (transformedShapeVa);
    jdouble * const pDoubles
            = (jdouble *) pEnv->GetDirectBufferAddress(storeDoubles);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const jlong capacityDoubles = pEnv->GetDirectBufferCapacity(storeDoubles);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    JPH_ASSERT(capacityDoubles >= 3);
    const RVec3& position = pTransformedShape->mShapePositionCOM;
    pDoubles[0] = position.GetX();
    pDoubles[1] = position.GetY();
    pDoubles[2] = position.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_TransformedShape
 * Method:    getShapeRotation
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TransformedShape_getShapeRotation
  (JNIEnv *pEnv, jclass, jlong transformedShapeVa, jobject storeFloats) {
    const TransformedShape * const pTransformedShape
            = reinterpret_cast<TransformedShape *> (transformedShapeVa);
    jfloat * const pFloats
            = (jfloat *) pEnv->GetDirectBufferAddress(storeFloats);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const jlong capacityFloats = pEnv->GetDirectBufferCapacity(storeFloats);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    JPH_ASSERT(capacityFloats >= 4);
    const Quat& rotation = pTransformedShape->mShapeRotation;
    pFloats[0] = rotation.GetX();
    pFloats[1] = rotation.GetY();
    pFloats[2] = rotation.GetZ();
    pFloats[3] = rotation.GetW();
}

/*
 * Class:     com_github_stephengold_joltjni_TransformedShape
 * Method:    getShapeScale
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TransformedShape_getShapeScale
  (JNIEnv *pEnv, jclass, jlong transformedShapeVa, jobject storeFloats) {
    const TransformedShape * const pTransformedShape
            = reinterpret_cast<TransformedShape *> (transformedShapeVa);
    jfloat * const pFloats
            = (jfloat *) pEnv->GetDirectBufferAddress(storeFloats);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const jlong capacityFloats = pEnv->GetDirectBufferCapacity(storeFloats);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& scale = pTransformedShape->GetShapeScale();
    pFloats[0] = scale.GetX();
    pFloats[1] = scale.GetY();
    pFloats[2] = scale.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_TransformedShape
 * Method:    getSupportingFace
 * Signature: (JIFFFDDDJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TransformedShape_getSupportingFace
  (JNIEnv *, jclass, jlong shapeVa, jint subShapeId, jfloat dx, jfloat dy,
  jfloat dz, jdouble xx, jdouble yy, jdouble zz, jlong faceVa) {
    const TransformedShape * const pShape
            = reinterpret_cast<TransformedShape *> (shapeVa);
    SubShapeID id;
    id.SetValue(subShapeId);
    const Vec3 direction(dx, dy, dz);
    const RVec3 base(xx, yy, zz);
    Shape::SupportingFace * const pFace
            = reinterpret_cast<Shape::SupportingFace *> (faceVa);
    pShape->GetSupportingFace(id, direction, base, *pFace);
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

/*
 * Class:     com_github_stephengold_joltjni_TransformedShape
 * Method:    getWorldSpaceBounds
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_TransformedShape_getWorldSpaceBounds
  (JNIEnv *, jclass, jlong shapeVa) {
    const TransformedShape * const pShape
            = reinterpret_cast<TransformedShape *> (shapeVa);
    const AABox& box = pShape->GetWorldSpaceBounds();
    AABox * const pResult = new AABox(box);
    TRACE_NEW("AABox", pResult)
    return reinterpret_cast<jlong> (pResult);
}