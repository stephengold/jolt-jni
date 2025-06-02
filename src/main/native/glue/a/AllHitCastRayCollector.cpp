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
#include "Jolt/Physics/Collision/CastResult.h"
#include "Jolt/Physics/Collision/CollisionCollectorImpl.h"
#include "Jolt/Physics/Collision/CollisionDispatch.h"

#include "auto/com_github_stephengold_joltjni_AllHitCastRayCollector.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_AllHitCastRayCollector
 * Method:    countHits
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_AllHitCastRayCollector_countHits
  (JNIEnv *, jclass, jlong collectorVa) {
    const AllHitCollisionCollector<CastRayCollector> * const pCollector
            = reinterpret_cast<AllHitCollisionCollector<CastRayCollector> *> (collectorVa);
    const Array<RayCastResult>::size_type result = pCollector->mHits.size();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AllHitCastRayCollector
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_AllHitCastRayCollector_createDefault
  BODYOF_CREATE_DEFAULT(AllHitCollisionCollector<CastRayCollector>)

/*
 * Class:     com_github_stephengold_joltjni_AllHitCastRayCollector
 * Method:    getHit
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_AllHitCastRayCollector_getHit
  (JNIEnv *, jclass, jlong collectorVa, jint index) {
    const AllHitCollisionCollector<CastRayCollector> * const pCollector
            = reinterpret_cast<AllHitCollisionCollector<CastRayCollector> *> (collectorVa);
    const RayCastResult * const pResult = &pCollector->mHits.at(index);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_AllHitCastRayCollector
 * Method:    sort
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_AllHitCastRayCollector_sort
  (JNIEnv *, jclass, jlong collectorVa) {
    AllHitCollisionCollector<CastRayCollector> * const pCollector
            = reinterpret_cast<AllHitCollisionCollector<CastRayCollector> *> (collectorVa);
    pCollector->Sort();
}