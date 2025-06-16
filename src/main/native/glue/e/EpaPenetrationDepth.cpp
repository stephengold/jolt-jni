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
#include "Jolt/Geometry/AABox.h"
#include "Jolt/Geometry/EPAPenetrationDepth.h"
#include "Jolt/Geometry/Sphere.h"

#include "auto/com_github_stephengold_joltjni_EpaPenetrationDepth.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_EpaPenetrationDepth
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_EpaPenetrationDepth_createDefault
  BODYOF_CREATE_DEFAULT(EPAPenetrationDepth)

/*
 * Class:     com_github_stephengold_joltjni_EpaPenetrationDepth
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_EpaPenetrationDepth_free
  BODYOF_FREE(EPAPenetrationDepth)

/*
 * Pre-processor macro to generate the body of a static
 * getPenetrationDepth() method:
 */
#define BODYOF_GET_PENETRATION_DEPTH(T1, T2, T3, T4) \
  (JNIEnv *pEnv, jclass, jlong epaVa, jlong aExcludingVa, jlong aIncludingVa, \
  jfloat convexRadiusA, jlong bExcludingVa, jlong bIncludingVa, \
  jfloat convexRadiusB, jobject fBuf) { \
    EPAPenetrationDepth * const pEpa \
            = reinterpret_cast<EPAPenetrationDepth *> (epaVa); \
    const T1 * const pAExcluding = reinterpret_cast<T1 *> (aExcludingVa); \
    const T2 * const pAIncluding = reinterpret_cast<T2 *> (aIncludingVa); \
    const T3 * const pBExcluding = reinterpret_cast<T3 *> (bExcludingVa); \
    const T4 * const pBIncluding = reinterpret_cast<T4 *> (bIncludingVa); \
    jfloat * const pFloats = (jfloat *) pEnv->GetDirectBufferAddress(fBuf); \
    JPH_ASSERT(!pEnv->ExceptionCheck()); \
    const jlong capacityFloats = pEnv->GetDirectBufferCapacity(fBuf); \
    JPH_ASSERT(!pEnv->ExceptionCheck()); \
    JPH_ASSERT(capacityFloats >= 11); \
    const float collisionToleranceSq = pFloats[0]; \
    const float penetrationTolerance = pFloats[1]; \
    Vec3 iov(pFloats[2], pFloats[3], pFloats[4]); \
    Vec3 storePointA, storePointB; \
    bool result = pEpa->GetPenetrationDepth( \
            *pAExcluding, *pAIncluding, convexRadiusA, *pBExcluding, \
            *pBIncluding, convexRadiusB, collisionToleranceSq, \
            penetrationTolerance, iov, storePointA, storePointB); \
    pFloats[2] = iov.GetX(); \
    pFloats[3] = iov.GetY(); \
    pFloats[4] = iov.GetZ(); \
    pFloats[5] = storePointA.GetX(); \
    pFloats[6] = storePointA.GetY(); \
    pFloats[7] = storePointA.GetZ(); \
    pFloats[8] = storePointB.GetX(); \
    pFloats[9] = storePointB.GetY(); \
    pFloats[10] = storePointB.GetZ(); \
    return result; \
} \

/*
 * Class:     com_github_stephengold_joltjni_EpaPenetrationDepth
 * Method:    getPenetrationDepth
 * Signature: (JJJFJJFLjava/nio/FloatBuffer;)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_EpaPenetrationDepth_getPenetrationDepth
  BODYOF_GET_PENETRATION_DEPTH(
          TransformedConvexObject<AABox>,
          TransformedConvexObject<AABox>,
          TransformedConvexObject<Sphere>,
          TransformedConvexObject<Sphere>)

/*
 * Class:     com_github_stephengold_joltjni_EpaPenetrationDepth
 * Method:    getPenetrationDepthPoints
 * Signature: (JJJFJJFLjava/nio/FloatBuffer;)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_EpaPenetrationDepth_getPenetrationDepthPoints
  BODYOF_GET_PENETRATION_DEPTH(
          PointConvexSupport,
          Sphere,
          PointConvexSupport,
          Sphere)

/*
 * Class:     com_github_stephengold_joltjni_EpaPenetrationDepth
 * Method:    getPenetrationDepthSpheres
 * Signature: (JJJFJJFLjava/nio/FloatBuffer;)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_EpaPenetrationDepth_getPenetrationDepthSpheres
  BODYOF_GET_PENETRATION_DEPTH(Sphere, Sphere, Sphere, Sphere)

/*
 * Class:     com_github_stephengold_joltjni_EpaPenetrationDepth
 * Method:    getPenetrationDepthSphereTab
 * Signature: (JJJFJJFLjava/nio/FloatBuffer;)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_EpaPenetrationDepth_getPenetrationDepthSphereTab
  BODYOF_GET_PENETRATION_DEPTH(
          Sphere,
          Sphere,
          TransformedConvexObject<AABox>,
          AddConvexRadius<TransformedConvexObject<AABox>>)

/*
 * Class:     com_github_stephengold_joltjni_EpaPenetrationDepth
 * Method:    getPenetrationDepthTabs
 * Signature: (JJJFJJFLjava/nio/FloatBuffer;)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_EpaPenetrationDepth_getPenetrationDepthTabs
  BODYOF_GET_PENETRATION_DEPTH(
          TransformedConvexObject<AABox>,
          AddConvexRadius<TransformedConvexObject<AABox>>,
          TransformedConvexObject<AABox>,
          AddConvexRadius<TransformedConvexObject<AABox>>)