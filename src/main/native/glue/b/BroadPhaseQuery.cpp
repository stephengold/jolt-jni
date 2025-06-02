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
#include "Jolt/Physics/Collision/BroadPhase/BroadPhaseQuery.h"
#include "auto/com_github_stephengold_joltjni_BroadPhaseQuery.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_BroadPhaseQuery
 * Method:    castRay
 * Signature: (JJJJJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BroadPhaseQuery_castRay
  (JNIEnv *, jclass, jlong queryVa, jlong raycastVa, jlong collectorVa,
  jlong bplFilterVa, jlong olFilterVa) {
    const BroadPhaseQuery * const pQuery
            = reinterpret_cast<BroadPhaseQuery *> (queryVa);
    const RayCast * const pRaycast
            = reinterpret_cast<RayCast *> (raycastVa);
    RayCastBodyCollector * const pCollector
            = reinterpret_cast<RayCastBodyCollector *> (collectorVa);
    const BroadPhaseLayerFilter * const pBplFilter
            = reinterpret_cast<BroadPhaseLayerFilter *> (bplFilterVa);
    const ObjectLayerFilter * const pOlFilter
            = reinterpret_cast<ObjectLayerFilter *> (olFilterVa);
    pQuery->CastRay(*pRaycast, *pCollector, *pBplFilter, *pOlFilter);
}

/*
 * Class:     com_github_stephengold_joltjni_BroadPhaseQuery
 * Method:    collideAaBox
 * Signature: (JJJJJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BroadPhaseQuery_collideAaBox
  (JNIEnv *, jclass, jlong queryVa, jlong boxVa, jlong collectorVa,
  jlong bplFilterVa, jlong olFilterVa) {
    const BroadPhaseQuery * const pQuery
            = reinterpret_cast<BroadPhaseQuery *> (queryVa);
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    CollideShapeBodyCollector * const pCollector
            = reinterpret_cast<CollideShapeBodyCollector *> (collectorVa);
    const BroadPhaseLayerFilter * const pBplFilter
            = reinterpret_cast<BroadPhaseLayerFilter *> (bplFilterVa);
    const ObjectLayerFilter * const pOlFilter
            = reinterpret_cast<ObjectLayerFilter *> (olFilterVa);
    pQuery->CollideAABox(*pBox, *pCollector, *pBplFilter, *pOlFilter);
}

/*
 * Class:     com_github_stephengold_joltjni_BroadPhaseQuery
 * Method:    collidePoint
 * Signature: (JFFFJJJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BroadPhaseQuery_collidePoint
  (JNIEnv *, jclass, jlong queryVa, jfloat px, jfloat py, jfloat pz,
  jlong collectorVa, jlong bplFilterVa, jlong olFilterVa) {
    const BroadPhaseQuery * const pQuery
            = reinterpret_cast<BroadPhaseQuery *> (queryVa);
    const Vec3 point(px, py, pz);
    CollideShapeBodyCollector * const pCollector
            = reinterpret_cast<CollideShapeBodyCollector *> (collectorVa);
    const BroadPhaseLayerFilter * const pBplFilter
            = reinterpret_cast<BroadPhaseLayerFilter *> (bplFilterVa);
    const ObjectLayerFilter * const pOlFilter
            = reinterpret_cast<ObjectLayerFilter *> (olFilterVa);
    pQuery->CollidePoint(point, *pCollector, *pBplFilter, *pOlFilter);
}

/*
 * Class:     com_github_stephengold_joltjni_BroadPhaseQuery
 * Method:    collideSphere
 * Signature: (JFFFFJJJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BroadPhaseQuery_collideSphere
  (JNIEnv *, jclass, jlong queryVa, jfloat cx, jfloat cy, jfloat cz,
  jfloat radius, jlong collectorVa, jlong bplFilterVa, jlong olFilterVa) {
    const BroadPhaseQuery * const pQuery
            = reinterpret_cast<BroadPhaseQuery *> (queryVa);
    const Vec3 center(cx, cy, cz);
    CollideShapeBodyCollector * const pCollector
            = reinterpret_cast<CollideShapeBodyCollector *> (collectorVa);
    const BroadPhaseLayerFilter * const pBplFilter
            = reinterpret_cast<BroadPhaseLayerFilter *> (bplFilterVa);
    const ObjectLayerFilter * const pOlFilter
            = reinterpret_cast<ObjectLayerFilter *> (olFilterVa);
    pQuery->CollideSphere(center, radius, *pCollector, *pBplFilter, *pOlFilter);
}

/*
 * Class:     com_github_stephengold_joltjni_BroadPhaseQuery
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BroadPhaseQuery_free
  BODYOF_FREE(BroadPhaseQuery)