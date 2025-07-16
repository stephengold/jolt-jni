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
#include "Jolt/Physics/Collision/EstimateCollisionResponse.h"
#include "auto/com_github_stephengold_joltjni_CollisionEstimationResult.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_CollisionEstimationResult
 * Method:    countImpulses
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_CollisionEstimationResult_countImpulses
  (JNIEnv *, jclass, jlong estimateVa) {
    const CollisionEstimationResult * const pEstimate
            = reinterpret_cast<CollisionEstimationResult *> (estimateVa);
    size_t result = pEstimate->mImpulses.size();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CollisionEstimationResult
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CollisionEstimationResult_createDefault
  BODYOF_CREATE_DEFAULT(CollisionEstimationResult)

/*
 * Class:     com_github_stephengold_joltjni_CollisionEstimationResult
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CollisionEstimationResult_free
  BODYOF_FREE(CollisionEstimationResult)

/*
 * Class:     com_github_stephengold_joltjni_CollisionEstimationResult
 * Method:    getAngularVelocity1
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CollisionEstimationResult_getAngularVelocity1
  (JNIEnv *pEnv, jclass, jlong estimateVa, jobject floatBuffer) {
    const CollisionEstimationResult * const pEstimate
            = reinterpret_cast<CollisionEstimationResult *> (estimateVa);
    jfloat * const pFloats
            = (jfloat *) pEnv->GetDirectBufferAddress(floatBuffer);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const jlong capacityFloats = pEnv->GetDirectBufferCapacity(floatBuffer);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3 &result = pEstimate->mAngularVelocity1;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_CollisionEstimationResult
 * Method:    getAngularVelocity2
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CollisionEstimationResult_getAngularVelocity2
  (JNIEnv *pEnv, jclass, jlong estimateVa, jobject floatBuffer) {
    const CollisionEstimationResult * const pEstimate
            = reinterpret_cast<CollisionEstimationResult *> (estimateVa);
    jfloat * const pFloats
            = (jfloat *) pEnv->GetDirectBufferAddress(floatBuffer);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const jlong capacityFloats = pEnv->GetDirectBufferCapacity(floatBuffer);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3 &result = pEstimate->mAngularVelocity2;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_CollisionEstimationResult
 * Method:    getImpulse
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CollisionEstimationResult_getImpulse
  (JNIEnv *, jclass, jlong estimateVa, jint index) {
    CollisionEstimationResult * const pEstimate
            = reinterpret_cast<CollisionEstimationResult *> (estimateVa);
    CollisionEstimationResult::Impulse * const pResult
            = &pEstimate->mImpulses[index];
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_CollisionEstimationResult
 * Method:    getLinearVelocity1
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CollisionEstimationResult_getLinearVelocity1
  (JNIEnv *pEnv, jclass, jlong estimateVa, jobject floatBuffer) {
    const CollisionEstimationResult * const pEstimate
            = reinterpret_cast<CollisionEstimationResult *> (estimateVa);
    jfloat * const pFloats
            = (jfloat *) pEnv->GetDirectBufferAddress(floatBuffer);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const jlong capacityFloats = pEnv->GetDirectBufferCapacity(floatBuffer);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3 &result = pEstimate->mLinearVelocity1;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_CollisionEstimationResult
 * Method:    getLinearVelocity2
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CollisionEstimationResult_getLinearVelocity2
  (JNIEnv *pEnv, jclass, jlong estimateVa, jobject floatBuffer) {
    const CollisionEstimationResult * const pEstimate
            = reinterpret_cast<CollisionEstimationResult *> (estimateVa);
    jfloat * const pFloats
            = (jfloat *) pEnv->GetDirectBufferAddress(floatBuffer);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const jlong capacityFloats = pEnv->GetDirectBufferCapacity(floatBuffer);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3 &result = pEstimate->mLinearVelocity2;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}