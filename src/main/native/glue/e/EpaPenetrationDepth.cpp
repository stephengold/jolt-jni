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
 * Class:     com_github_stephengold_joltjni_EpaPenetrationDepth
 * Method:    getPenetrationDepth
 * Signature: (JJJFJJFLjava/nio/FloatBuffer;)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_EpaPenetrationDepth_getPenetrationDepth
  (JNIEnv *pEnv, jclass, jlong epaVa, jlong aExcludingVa, jlong aIncludingVa,
  jfloat convexRadiusA, jlong bExcludingVa, jlong bIncludingVa,
  jfloat convexRadiusB, jobject fBuf) {
    EPAPenetrationDepth * const pEpa
            = reinterpret_cast<EPAPenetrationDepth *> (epaVa);
    const TransformedConvexObject<AABox> * const pAExcluding
            = reinterpret_cast<TransformedConvexObject<AABox> *> (aExcludingVa);
    const TransformedConvexObject<AABox> * const pAIncluding
            = reinterpret_cast<TransformedConvexObject<AABox> *> (aIncludingVa);
    const TransformedConvexObject<Sphere> * const pBExcluding
            = reinterpret_cast<TransformedConvexObject<Sphere> *> (bExcludingVa);
    const TransformedConvexObject<Sphere> * const pBIncluding
            = reinterpret_cast<TransformedConvexObject<Sphere> *> (bIncludingVa);
    jfloat * const pFloats = (jfloat *) pEnv->GetDirectBufferAddress(fBuf);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const jlong capacityFloats = pEnv->GetDirectBufferCapacity(fBuf);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    JPH_ASSERT(capacityFloats >= 11);
    const float collisionToleranceSq = pFloats[0];
    const float penetrationTolerance = pFloats[1];
    Vec3 iov(pFloats[2], pFloats[3], pFloats[4]);
    Vec3 storePointA;
    Vec3 storePointB;
    bool result = pEpa->GetPenetrationDepth(
            *pAExcluding, *pAIncluding, convexRadiusA, *pBExcluding,
            *pBIncluding, convexRadiusB, collisionToleranceSq,
            penetrationTolerance, iov, storePointA, storePointB);
    pFloats[2] = iov.GetX();
    pFloats[3] = iov.GetY();
    pFloats[4] = iov.GetZ();
    pFloats[5] = storePointA.GetX();
    pFloats[6] = storePointA.GetY();
    pFloats[7] = storePointA.GetZ();
    pFloats[8] = storePointB.GetX();
    pFloats[9] = storePointB.GetY();
    pFloats[10] = storePointB.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_EpaPenetrationDepth
 * Method:    getPenetrationDepthPoints
 * Signature: (JJJFJJFLjava/nio/FloatBuffer;)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_EpaPenetrationDepth_getPenetrationDepthPoints
  (JNIEnv *pEnv, jclass, jlong epaVa, jlong aExcludingVa, jlong aIncludingVa,
  jfloat convexRadiusA, jlong bExcludingVa, jlong bIncludingVa,
  jfloat convexRadiusB, jobject fBuf) {
    EPAPenetrationDepth * const pEpa
            = reinterpret_cast<EPAPenetrationDepth *> (epaVa);
    const PointConvexSupport * const pAExcluding
            = reinterpret_cast<PointConvexSupport *> (aExcludingVa);
    const Sphere * const pAIncluding
            = reinterpret_cast<Sphere *> (aIncludingVa);
    const PointConvexSupport * const pBExcluding
            = reinterpret_cast<PointConvexSupport *> (bExcludingVa);
    const Sphere * const pBIncluding
            = reinterpret_cast<Sphere *> (bIncludingVa);
    jfloat * const pFloats = (jfloat *) pEnv->GetDirectBufferAddress(fBuf);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const jlong capacityFloats = pEnv->GetDirectBufferCapacity(fBuf);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    JPH_ASSERT(capacityFloats >= 11);
    const float collisionToleranceSq = pFloats[0];
    const float penetrationTolerance = pFloats[1];
    Vec3 iov(pFloats[2], pFloats[3], pFloats[4]);
    Vec3 storePointA;
    Vec3 storePointB;
    bool result = pEpa->GetPenetrationDepth(
            *pAExcluding, *pAIncluding, convexRadiusA, *pBExcluding,
            *pBIncluding, convexRadiusB, collisionToleranceSq,
            penetrationTolerance, iov, storePointA, storePointB);
    pFloats[2] = iov.GetX();
    pFloats[3] = iov.GetY();
    pFloats[4] = iov.GetZ();
    pFloats[5] = storePointA.GetX();
    pFloats[6] = storePointA.GetY();
    pFloats[7] = storePointA.GetZ();
    pFloats[8] = storePointB.GetX();
    pFloats[9] = storePointB.GetY();
    pFloats[10] = storePointB.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_EpaPenetrationDepth
 * Method:    getPenetrationDepthSpheres
 * Signature: (JJJFJJFLjava/nio/FloatBuffer;)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_EpaPenetrationDepth_getPenetrationDepthSpheres
  (JNIEnv *pEnv, jclass, jlong epaVa, jlong aExcludingVa, jlong aIncludingVa,
  jfloat convexRadiusA, jlong bExcludingVa, jlong bIncludingVa,
  jfloat convexRadiusB, jobject fBuf) {
    EPAPenetrationDepth * const pEpa
            = reinterpret_cast<EPAPenetrationDepth *> (epaVa);
    const Sphere * const pAExcluding
            = reinterpret_cast<Sphere *> (aExcludingVa);
    const Sphere * const pAIncluding
            = reinterpret_cast<Sphere *> (aIncludingVa);
    const Sphere * const pBExcluding
            = reinterpret_cast<Sphere *> (bExcludingVa);
    const Sphere * const pBIncluding
            = reinterpret_cast<Sphere *> (bIncludingVa);
    jfloat * const pFloats = (jfloat *) pEnv->GetDirectBufferAddress(fBuf);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const jlong capacityFloats = pEnv->GetDirectBufferCapacity(fBuf);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    JPH_ASSERT(capacityFloats >= 11);
    const float collisionToleranceSq = pFloats[0];
    const float penetrationTolerance = pFloats[1];
    Vec3 iov(pFloats[2], pFloats[3], pFloats[4]);
    Vec3 storePointA;
    Vec3 storePointB;
    bool result = pEpa->GetPenetrationDepth(
            *pAExcluding, *pAIncluding, convexRadiusA, *pBExcluding,
            *pBIncluding, convexRadiusB, collisionToleranceSq,
            penetrationTolerance, iov, storePointA, storePointB);
    pFloats[2] = iov.GetX();
    pFloats[3] = iov.GetY();
    pFloats[4] = iov.GetZ();
    pFloats[5] = storePointA.GetX();
    pFloats[6] = storePointA.GetY();
    pFloats[7] = storePointA.GetZ();
    pFloats[8] = storePointB.GetX();
    pFloats[9] = storePointB.GetY();
    pFloats[10] = storePointB.GetZ();
    return result;
}