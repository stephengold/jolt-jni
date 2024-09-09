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
#include <Jolt/Jolt.h>
#include <Jolt/Physics/Collision/NarrowPhaseQuery.h>
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