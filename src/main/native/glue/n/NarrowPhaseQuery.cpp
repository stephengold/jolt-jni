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
#include "Jolt/Physics/Collision/NarrowPhaseQuery.h"
#include "auto/com_github_stephengold_joltjni_NarrowPhaseQuery.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_NarrowPhaseQuery
 * Method:    castRay
 * Signature: (JJJJJJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_NarrowPhaseQuery_castRay__JJJJJJ
  (JNIEnv *, jclass, jlong queryVa, jlong raycastVa, jlong hitVa,
  jlong bplFilterVa, jlong olFilterVa, jlong bodyFilterVa) {
    const NarrowPhaseQuery * const pQuery
            = reinterpret_cast<NarrowPhaseQuery *> (queryVa);
    const RRayCast * const pRayCast = reinterpret_cast<RRayCast *> (raycastVa);
    RayCastResult * const pHit = reinterpret_cast<RayCastResult *> (hitVa);
    const BroadPhaseLayerFilter * const pBplFilter
            = reinterpret_cast<BroadPhaseLayerFilter *> (bplFilterVa);
    const ObjectLayerFilter * const pOlFilter
            = reinterpret_cast<ObjectLayerFilter *> (olFilterVa);
    const BodyFilter * const pBodyFilter
            = reinterpret_cast<BodyFilter *> (bodyFilterVa);
    const bool result = pQuery->CastRay(
            *pRayCast, *pHit, *pBplFilter, *pOlFilter, *pBodyFilter);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_NarrowPhaseQuery
 * Method:    castRay
 * Signature: (JJJJJJJJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_NarrowPhaseQuery_castRay__JJJJJJJJ
  (JNIEnv *, jclass, jlong queryVa, jlong raycastVa, jlong settingsVa,
  jlong collectorVa, jlong bplFilterVa, jlong olFilterVa, jlong bodyFilterVa,
  jlong shapeFilterVa) {
    const NarrowPhaseQuery * const pQuery
            = reinterpret_cast<NarrowPhaseQuery *> (queryVa);
    const RRayCast * const pRayCast = reinterpret_cast<RRayCast *> (raycastVa);
    const RayCastSettings * const pSettings
            = reinterpret_cast<RayCastSettings *> (settingsVa);
    CastRayCollector * const pCollector
            = reinterpret_cast<CastRayCollector *> (collectorVa);
    const BroadPhaseLayerFilter * const pBplFilter
            = reinterpret_cast<BroadPhaseLayerFilter *> (bplFilterVa);
    const ObjectLayerFilter * const pOlFilter
            = reinterpret_cast<ObjectLayerFilter *> (olFilterVa);
    const BodyFilter * const pBodyFilter
            = reinterpret_cast<BodyFilter *> (bodyFilterVa);
    const ShapeFilter * const pShapeFilter
            = reinterpret_cast<ShapeFilter *> (shapeFilterVa);
    pQuery->CastRay(*pRayCast, *pSettings, *pCollector, *pBplFilter, *pOlFilter,
            *pBodyFilter, *pShapeFilter);
}

/*
 * Class:     com_github_stephengold_joltjni_NarrowPhaseQuery
 * Method:    castShape
 * Signature: (JJJDDDJJJJJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_NarrowPhaseQuery_castShape
  (JNIEnv *, jclass, jlong queryVa, jlong shapeCastVa, jlong settingsVa,
  jdouble baseX, jdouble baseY, jdouble baseZ, jlong collectorVa,
  jlong bplFilterVa, jlong olFilterVa, jlong bodyFilterVa, jlong shapeFilterVa) {
    const NarrowPhaseQuery * const pQuery
            = reinterpret_cast<NarrowPhaseQuery *> (queryVa);
    const RShapeCast * const pShapeCast
            = reinterpret_cast<RShapeCast *> (shapeCastVa);
    const ShapeCastSettings * const pSettings
            = reinterpret_cast<ShapeCastSettings *> (settingsVa);
    const RVec3 baseOffset(baseX, baseY, baseZ);
    CastShapeCollector * const pCollector
            = reinterpret_cast<CastShapeCollector *> (collectorVa);
    const BroadPhaseLayerFilter * const pBplFilter
            = reinterpret_cast<BroadPhaseLayerFilter *> (bplFilterVa);
    const ObjectLayerFilter * const pOlFilter
            = reinterpret_cast<ObjectLayerFilter *> (olFilterVa);
    const BodyFilter * const pBodyFilter
            = reinterpret_cast<BodyFilter *> (bodyFilterVa);
    const ShapeFilter * const pShapeFilter
            = reinterpret_cast<ShapeFilter *> (shapeFilterVa);
    pQuery->CastShape(*pShapeCast, *pSettings, baseOffset, *pCollector,
            *pBplFilter, *pOlFilter, *pBodyFilter, *pShapeFilter);
}

/*
 * Class:     com_github_stephengold_joltjni_NarrowPhaseQuery
 * Method:    collectTransformedShapes
 * Signature: (JJJJJJJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_NarrowPhaseQuery_collectTransformedShapes
  (JNIEnv *, jclass, jlong queryVa, jlong boxVa, jlong collectorVa,
  jlong bplFilterVa, jlong olFilterVa, jlong bodyFilterVa, jlong shapeFilterVa) {
    const NarrowPhaseQuery * const pQuery
            = reinterpret_cast<NarrowPhaseQuery *> (queryVa);
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    TransformedShapeCollector * pCollector
            = reinterpret_cast<TransformedShapeCollector *> (collectorVa);
    const BroadPhaseLayerFilter * const pBplFilter
            = reinterpret_cast<BroadPhaseLayerFilter *> (bplFilterVa);
    const ObjectLayerFilter * const pOlFilter
            = reinterpret_cast<ObjectLayerFilter *> (olFilterVa);
    const BodyFilter * const pBodyFilter
            = reinterpret_cast<BodyFilter *> (bodyFilterVa);
    const ShapeFilter * const pShapeFilter
            = reinterpret_cast<ShapeFilter *> (shapeFilterVa);
    pQuery->CollectTransformedShapes(*pBox, *pCollector, *pBplFilter,
            *pOlFilter, *pBodyFilter, *pShapeFilter);
}

/*
 * Class:     com_github_stephengold_joltjni_NarrowPhaseQuery
 * Method:    collidePoint
 * Signature: (JDDDJJJJJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_NarrowPhaseQuery_collidePoint
  (JNIEnv *, jclass, jlong queryVa, jdouble xx, jdouble yy, jdouble zz,
  jlong collectorVa, jlong bplFilterVa, jlong olFilterVa, jlong bodyFilterVa,
  jlong shapeFilterVa) {
    const NarrowPhaseQuery * const pQuery
            = reinterpret_cast<NarrowPhaseQuery *> (queryVa);
    const RVec3 point(xx, yy, zz);
    CollidePointCollector * const pCollector
            = reinterpret_cast<CollidePointCollector *> (collectorVa);
    const BroadPhaseLayerFilter * const pBplFilter
            = reinterpret_cast<BroadPhaseLayerFilter *> (bplFilterVa);
    const ObjectLayerFilter * const pOlFilter
            = reinterpret_cast<ObjectLayerFilter *> (olFilterVa);
    const BodyFilter * const pBodyFilter
            = reinterpret_cast<BodyFilter *> (bodyFilterVa);
    const ShapeFilter * const pShapeFilter
            = reinterpret_cast<ShapeFilter *> (shapeFilterVa);
    pQuery->CollidePoint(point, *pCollector, *pBplFilter, *pOlFilter,
            *pBodyFilter, *pShapeFilter);
}

/*
 * Class:     com_github_stephengold_joltjni_NarrowPhaseQuery
 * Method:    collideShape
 * Signature: (JJFFFJJDDDJJJJJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_NarrowPhaseQuery_collideShape
  (JNIEnv *, jclass, jlong queryVa, jlong shapeVa, jfloat sx, jfloat sy,
  jfloat sz, jlong transformVa, jlong settingsVa, jdouble baseX, jdouble baseY,
  jdouble baseZ, jlong collectorVa, jlong bplFilterVa, jlong olFilterVa,
  jlong bodyFilterVa, jlong shapeFilterVa) {
    const NarrowPhaseQuery * const pQuery
            = reinterpret_cast<NarrowPhaseQuery *> (queryVa);
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    const Vec3 shapeScale(sx, sy, sz);
    const RMat44 * const pComTransform
            = reinterpret_cast<RMat44 *> (transformVa);
    const CollideShapeSettings * const pSettings
            = reinterpret_cast<CollideShapeSettings *> (settingsVa);
    const RVec3 baseOffset(baseX, baseY, baseZ);
    CollideShapeCollector * const pCollector
            = reinterpret_cast<CollideShapeCollector *> (collectorVa);
    const BroadPhaseLayerFilter * const pBplFilter
            = reinterpret_cast<BroadPhaseLayerFilter *> (bplFilterVa);
    const ObjectLayerFilter * const pOlFilter
            = reinterpret_cast<ObjectLayerFilter *> (olFilterVa);
    const BodyFilter * const pBodyFilter
            = reinterpret_cast<BodyFilter *> (bodyFilterVa);
    const ShapeFilter * const pShapeFilter
            = reinterpret_cast<ShapeFilter *> (shapeFilterVa);
    pQuery->CollideShape(pShape, shapeScale, *pComTransform, *pSettings,
            baseOffset, *pCollector, *pBplFilter, *pOlFilter, *pBodyFilter,
            *pShapeFilter);
}