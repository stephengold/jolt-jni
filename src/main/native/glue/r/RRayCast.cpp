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
#include "Jolt/Physics/Collision/RayCast.h"
#include "auto/com_github_stephengold_joltjni_RRayCast.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_RRayCast
 * Method:    createRRayCast
 * Signature: (DDDFFF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_RRayCast_createRRayCast
  (JNIEnv *, jclass, jdouble xx, jdouble yy, jdouble zz,
  jfloat dx, jfloat dy, jfloat dz) {
    const RVec3 startLocation(xx, yy, zz);
    const Vec3 direction(dx, dy, dz);
    RRayCast * const pResult = new RRayCast(startLocation, direction);
    TRACE_NEW("RRayCast", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_RRayCast
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RRayCast_free
  BODYOF_FREE(RRayCast)

/*
 * Class:     com_github_stephengold_joltjni_RRayCast
 * Method:    getDirection
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RRayCast_getDirection
  (JNIEnv *pEnv, jclass, jlong raycastVa, jobject storeFloats) {
    const RRayCast * const pRayCast = reinterpret_cast<RRayCast *> (raycastVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& result = pRayCast->mDirection;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_RRayCast
 * Method:    getOrigin
 * Signature: (JLjava/nio/DoubleBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RRayCast_getOrigin
  (JNIEnv *pEnv, jclass, jlong raycastVa, jobject storeDoubles) {
    const RRayCast * const pRayCast = reinterpret_cast<RRayCast *> (raycastVa);
    DIRECT_DOUBLE_BUFFER(pEnv, storeDoubles, pDoubles, capacityDoubles);
    JPH_ASSERT(capacityDoubles >= 3);
    const RVec3 result = pRayCast->mOrigin;
    pDoubles[0] = result.GetX();
    pDoubles[1] = result.GetY();
    pDoubles[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_RRayCast
 * Method:    getPointOnRay
 * Signature: (JFLjava/nio/DoubleBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RRayCast_getPointOnRay
  (JNIEnv *pEnv, jclass, jlong raycastVa, jfloat fraction, jobject storeDoubles) {
    const RRayCast * const pRayCast = reinterpret_cast<RRayCast *> (raycastVa);
    DIRECT_DOUBLE_BUFFER(pEnv, storeDoubles, pDoubles, capacityDoubles);
    JPH_ASSERT(capacityDoubles >= 3);
    const RVec3 result = pRayCast->GetPointOnRay(fraction);
    pDoubles[0] = result.GetX();
    pDoubles[1] = result.GetY();
    pDoubles[2] = result.GetZ();
}