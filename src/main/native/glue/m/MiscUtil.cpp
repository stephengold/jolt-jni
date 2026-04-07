/*
Copyright (c) 2024-2026 Stephen Gold

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
#include "Jolt/Core/Factory.h"
#include "Jolt/Core/HashCombine.h"
#include "Jolt/Geometry/RayAABox.h"
#include "Jolt/Geometry/RayCapsule.h"
#include "Jolt/Geometry/RayCylinder.h"
#include "Jolt/Geometry/RaySphere.h"
#include "Jolt/Geometry/RayTriangle.h"
#include "Jolt/Physics/Character/CharacterID.h"
#include "Jolt/Physics/Collision/EstimateCollisionResponse.h"
#include "TestFramework/External/Perlin.h"

#include "auto/com_github_stephengold_joltjni_MiscUtil.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_MiscUtil
 * Method:    hashBytes
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_MiscUtil_hashBytes__JI
  (JNIEnv *, jclass, jlong dataVa, jint inSize) {
    const void * const pData = reinterpret_cast<void *> (dataVa);
    const uint64 result = HashBytes(pData, inSize);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MiscUtil
 * Method:    hashCombine
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_MiscUtil_hashCombine__JI
  (JNIEnv *, jclass, jlong oldHash, jint iValue) {
    uint64 result = oldHash;
    HashCombine(result, (uint32)iValue);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MiscUtil
 * Method:    hashCombine
 * Signature: (JJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_MiscUtil_hashCombine__JJ
  (JNIEnv *, jclass, jlong oldHash, jlong lValue) {
    uint64 result = oldHash;
    HashCombine(result, (uint64)lValue);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MiscUtil
 * Method:    perlinNoise3
 * Signature: (FFFIII)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MiscUtil_perlinNoise3
  (JNIEnv *, jclass, jfloat x, jfloat y, jfloat z, jint xWrap, jint yWrap, jint zWrap) {
    const float result = PerlinNoise3(x, y, z, xWrap, yWrap, zWrap);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MiscUtil
 * Method:    sSetNextCharacterId
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MiscUtil_sSetNextCharacterId
  (JNIEnv *, jclass, jint nextValue) {
    CharacterID::sSetNextCharacterID(nextValue);
}

/*
 * Class:     com_github_stephengold_joltjni_MiscUtil
 * Method:    estimateCollisionResponse
 * Signature: (JJJJFFFI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MiscUtil_estimateCollisionResponse
  (JNIEnv *, jclass, jlong body1Va, jlong body2Va, jlong manifoldVa,
  jlong resultVa, jfloat combinedFriction, jfloat combinedRestitution,
  jfloat minVelocity, jint numIterations) {
    const Body * const pBody1 = reinterpret_cast<Body *> (body1Va);
    const Body * const pBody2 = reinterpret_cast<Body *> (body2Va);
    const ContactManifold * const pManifold
            = reinterpret_cast<ContactManifold *> (manifoldVa);
    CollisionEstimationResult * const pResult
            = reinterpret_cast<CollisionEstimationResult *> (resultVa);
    EstimateCollisionResponse(*pBody1, *pBody2, *pManifold, *pResult,
            combinedFriction, combinedRestitution, minVelocity, numIterations);
}

/*
 * Class:     com_github_stephengold_joltjni_MiscUtil
 * Method:    hashBytes
 * Signature: (DDDJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_MiscUtil_hashBytes__DDDJ
  (JNIEnv *, jclass, jdouble xx, jdouble yy, jdouble zz, jlong oldHash) {
    const RVec3 vector(xx, yy, zz);
    const uint64 result = HashBytes(&vector, sizeof(RVec3), oldHash);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MiscUtil
 * Method:    hashBytes
 * Signature: (FFFFJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_MiscUtil_hashBytes__FFFFJ
  (JNIEnv *, jclass, jfloat qx, jfloat qy, jfloat qz, jfloat qw, jlong oldHash) {
    const Quat quat(qx, qy, qz, qw);
    const uint64 result = HashBytes(&quat, sizeof(Quat), oldHash);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MiscUtil
 * Method:    hashCombineRVec3
 * Signature: (JDDD)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_MiscUtil_hashCombineRVec3
  (JNIEnv *, jclass, jlong oldHash, jdouble xx, jdouble yy, jdouble zz) {
    uint64 result = oldHash;
    const RVec3 rvec(xx, yy, zz);
    HashCombine(result, rvec);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MiscUtil
 * Method:    hashCombineVec3
 * Signature: (JFFF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_MiscUtil_hashCombineVec3
  (JNIEnv *, jclass, jlong oldHash, jfloat x, jfloat y, jfloat z) {
    uint64 result = oldHash;
    const Vec3 vec(x, y, z);
    HashCombine(result, vec);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MiscUtil
 * Method:    rayAaBox
 * Signature: (FFFJFFFFFF)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MiscUtil_rayAaBox
  (JNIEnv *, jclass, jfloat originX, jfloat originY, jfloat originZ,
  jlong invDirVa, jfloat minX, jfloat minY, jfloat minZ, jfloat maxX,
  jfloat maxY, jfloat maxZ) {
    const Vec3 origin(originZ, originY, originZ);
    const RayInvDirection * const pInvDir
            = reinterpret_cast<RayInvDirection *> (invDirVa);
    const Vec3 min(minZ, minY, minZ);
    const Vec3 max(maxZ, maxY, maxZ);
    const float result = RayAABox(origin, *pInvDir, min, max);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MiscUtil
 * Method:    rayAaBoxHits
 * Signature: (FFFFFFFFFFFF)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_MiscUtil_rayAaBoxHits
  (JNIEnv *, jclass, jfloat startX, jfloat startY, jfloat startZ,
  jfloat offsetX, jfloat offsetY, jfloat offsetZ, jfloat minX, jfloat minY,
  jfloat minZ, jfloat maxX, jfloat maxY, jfloat maxZ) {
    const Vec3 start(startX, startY, startZ);
    const Vec3 offset(offsetX, offsetY, offsetZ);
    const Vec3 min(minX, minY, minZ);
    const Vec3 max(maxX, maxY, maxZ);
    const bool result = RayAABoxHits(start, offset, min, max);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MiscUtil
 * Method:    rayCapsule
 * Signature: (FFFFFFFF)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MiscUtil_rayCapsule
  (JNIEnv *, jclass, jfloat originX, jfloat originY, jfloat originZ,
  jfloat directionX, jfloat directionY, jfloat directionZ,
  jfloat capsuleHalfHeight, jfloat capsuleRadius) {
    const Vec3 origin(originX, originY, originZ);
    const Vec3 direction(directionX, directionY, directionZ);
    const float result
            = RayCapsule(origin, direction, capsuleHalfHeight, capsuleRadius);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MiscUtil
 * Method:    rayFiniteCylinder
 * Signature: (FFFFFFFF)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MiscUtil_rayFiniteCylinder
  (JNIEnv *, jclass, jfloat originX, jfloat originY, jfloat originZ,
  jfloat directionX, jfloat directionY, jfloat directionZ,
  jfloat cylinderHalfHeight, jfloat cylinderRadius) {
    const Vec3 origin(originX, originY, originZ);
    const Vec3 direction(directionX, directionY, directionZ);
    const float result = RayCylinder(
            origin, direction, cylinderHalfHeight, cylinderRadius);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MiscUtil
 * Method:    rayInfiniteCylinder
 * Signature: (FFFFFFF)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MiscUtil_rayInfiniteCylinder
  (JNIEnv *, jclass, jfloat originX, jfloat originY, jfloat originZ,
  jfloat directionX, jfloat directionY, jfloat directionZ,
  jfloat cylinderRadius) {
    const Vec3 origin(originX, originY, originZ);
    const Vec3 direction(directionX, directionY, directionZ);
    const float result = RayCylinder(origin, direction, cylinderRadius);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MiscUtil
 * Method:    raySphere
 * Signature: (FFFFFFFFFF)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MiscUtil_raySphere
  (JNIEnv *, jclass, jfloat originX, jfloat originY, jfloat originZ,
  jfloat directionX, jfloat directionY, jfloat directionZ, jfloat centerX,
  jfloat centerY, jfloat centerZ, jfloat sphereRadius) {
    const Vec3 origin(originX, originY, originZ);
    const Vec3 direction(directionX, directionY, directionZ);
    const Vec3 center(centerX, centerY, centerZ);
    const float result = RaySphere(origin, direction, center, sphereRadius);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MiscUtil
 * Method:    rayTriangle
 * Signature: (FFFFFFFFFFFFFFF)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MiscUtil_rayTriangle
  (JNIEnv *, jclass, jfloat originX, jfloat originY, jfloat originZ,
  jfloat directionX, jfloat directionY, jfloat directionZ,
  jfloat v0x, jfloat v0y, jfloat v0z, jfloat v1x, jfloat v1y, jfloat v1z,
  jfloat v2x, jfloat v2y, jfloat v2z) {
    const Vec3 origin(originX, originY, originZ);
    const Vec3 direction(directionX, directionY, directionZ);
    const Vec3 v0(v0x, v0y, v0z);
    const Vec3 v1(v1x, v1y, v1z);
    const Vec3 v2(v2x, v2y, v2z);
    const float result = RayTriangle(origin, direction, v0, v1, v2);
    return result;
}