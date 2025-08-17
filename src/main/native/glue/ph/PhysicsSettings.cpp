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
#include "Jolt/Physics/PhysicsSettings.h"
#include "auto/com_github_stephengold_joltjni_PhysicsSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_createCopy
  BODYOF_CREATE_COPY(PhysicsSettings)

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_createDefault
  BODYOF_CREATE_DEFAULT(PhysicsSettings)

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_free
  BODYOF_FREE(PhysicsSettings)

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    getAllowSleeping
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_getAllowSleeping
  (JNIEnv *, jclass, jlong settingsVa) {
    const PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    const bool result = pSettings->mAllowSleeping;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    getBaumgarte
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_getBaumgarte
  (JNIEnv *, jclass, jlong settingsVa) {
    const PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    const float result = pSettings->mBaumgarte;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    getBodyPairCacheCosMaxDeltaRotationDiv2
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_getBodyPairCacheCosMaxDeltaRotationDiv2
  (JNIEnv *, jclass, jlong settingsVa) {
    const PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    const float result = pSettings->mBodyPairCacheCosMaxDeltaRotationDiv2;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    getBodyPairCacheMaxDeltaPositionSq
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_getBodyPairCacheMaxDeltaPositionSq
  (JNIEnv *, jclass, jlong settingsVa) {
    const PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    const float result = pSettings->mBodyPairCacheMaxDeltaPositionSq;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    getCheckActiveEdges
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_getCheckActiveEdges
  (JNIEnv *, jclass, jlong settingsVa) {
    const PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    const bool result = pSettings->mCheckActiveEdges;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    getConstraintWarmStart
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_getConstraintWarmStart
  (JNIEnv *, jclass, jlong settingsVa) {
    const PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    const bool result = pSettings->mConstraintWarmStart;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    getContactNormalCosMaxDeltaRotation
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_getContactNormalCosMaxDeltaRotation
  (JNIEnv *, jclass, jlong settingsVa) {
    const PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    const float result = pSettings->mContactNormalCosMaxDeltaRotation;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    getContactPointPreserveLambdaMaxDistSq
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_getContactPointPreserveLambdaMaxDistSq
  (JNIEnv *, jclass, jlong settingsVa) {
    const PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    const float result = pSettings->mContactPointPreserveLambdaMaxDistSq;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    getDeterministicSimulation
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_getDeterministicSimulation
  (JNIEnv *, jclass, jlong settingsVa) {
    const PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    const bool result = pSettings->mDeterministicSimulation;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    getLinearCastMaxPenetration
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_getLinearCastMaxPenetration
  (JNIEnv *, jclass, jlong settingsVa) {
    const PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    const float result = pSettings->mLinearCastMaxPenetration;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    getLinearCastThreshold
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_getLinearCastThreshold
  (JNIEnv *, jclass, jlong settingsVa) {
    const PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    const float result = pSettings->mLinearCastThreshold;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    getManifoldTolerance
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_getManifoldTolerance
  (JNIEnv *, jclass, jlong settingsVa) {
    const PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    const float result = pSettings->mManifoldTolerance;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    getMaxInFlightBodyPairs
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_getMaxInFlightBodyPairs
  (JNIEnv *, jclass, jlong settingsVa) {
    const PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    const int result = pSettings->mMaxInFlightBodyPairs;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    getMaxPenetrationDistance
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_getMaxPenetrationDistance
  (JNIEnv *, jclass, jlong settingsVa) {
    const PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    const float result = pSettings->mMaxPenetrationDistance;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    getMinVelocityForRestitution
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_getMinVelocityForRestitution
  (JNIEnv *, jclass, jlong settingsVa) {
    const PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    const float result = pSettings->mMinVelocityForRestitution;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    getNumPositionSteps
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_getNumPositionSteps
  (JNIEnv *, jclass, jlong settingsVa) {
    const PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    const uint result = pSettings->mNumPositionSteps;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    getNumVelocitySteps
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_getNumVelocitySteps
  (JNIEnv *, jclass, jlong settingsVa) {
    const PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    const uint result = pSettings->mNumVelocitySteps;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    getPenetrationSlop
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_getPenetrationSlop
  (JNIEnv *, jclass, jlong settingsVa) {
    const PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    const float result = pSettings->mPenetrationSlop;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    getPointVelocitySleepThreshold
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_getPointVelocitySleepThreshold
  (JNIEnv *, jclass, jlong settingsVa) {
    const PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    const float result = pSettings->mPointVelocitySleepThreshold;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    getSpeculativeContactDistance
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_getSpeculativeContactDistance
  (JNIEnv *, jclass, jlong settingsVa) {
    const PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    const float result = pSettings->mSpeculativeContactDistance;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    getStepListenerBatchesPerJob
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_getStepListenerBatchesPerJob
  (JNIEnv *, jclass, jlong settingsVa) {
    const PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    const int result = pSettings->mStepListenerBatchesPerJob;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    getStepListenersBatchSize
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_getStepListenersBatchSize
  (JNIEnv *, jclass, jlong settingsVa) {
    const PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    const int result = pSettings->mStepListenersBatchSize;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    getTimeBeforeSleep
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_getTimeBeforeSleep
  (JNIEnv *, jclass, jlong settingsVa) {
    const PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    const float result = pSettings->mTimeBeforeSleep;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    getUseBodyPairContactCache
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_getUseBodyPairContactCache
  (JNIEnv *, jclass, jlong settingsVa) {
    const PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    const bool result = pSettings->mUseBodyPairContactCache;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    getUseLargeIslandSplitter
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_getUseLargeIslandSplitter
  (JNIEnv *, jclass, jlong settingsVa) {
    const PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    const bool result = pSettings->mUseLargeIslandSplitter;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    getUseManifoldReduction
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_getUseManifoldReduction
  (JNIEnv *, jclass, jlong settingsVa) {
    const PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    const bool result = pSettings->mUseManifoldReduction;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    setAllowSleeping
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_setAllowSleeping
  (JNIEnv *, jclass, jlong settingsVa, jboolean allow) {
    PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    pSettings->mAllowSleeping = allow;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    setBaumgarte
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_setBaumgarte
  (JNIEnv *, jclass, jlong settingsVa, jfloat fraction) {
    PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    pSettings->mBaumgarte = fraction;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    setBodyPairCacheCosMaxDeltaRotationDiv2
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_setBodyPairCacheCosMaxDeltaRotationDiv2
  (JNIEnv *, jclass, jlong settingsVa, jfloat cosineOfHalfAngle) {
    PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    pSettings->mBodyPairCacheCosMaxDeltaRotationDiv2 = cosineOfHalfAngle;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    setBodyPairCacheMaxDeltaPositionSq
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_setBodyPairCacheMaxDeltaPositionSq
  (JNIEnv *, jclass, jlong settingsVa, jfloat squaredDistance) {
    PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    pSettings->mBodyPairCacheMaxDeltaPositionSq = squaredDistance;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    setCheckActiveEdges
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_setCheckActiveEdges
  (JNIEnv *, jclass, jlong settingsVa, jboolean check) {
    PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    pSettings->mCheckActiveEdges = check;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    setConstraintWarmStart
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_setConstraintWarmStart
  (JNIEnv *, jclass, jlong settingsVa, jboolean setting) {
    PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    pSettings->mConstraintWarmStart = setting;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    setContactNormalCosMaxDeltaRotation
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_setContactNormalCosMaxDeltaRotation
  (JNIEnv *, jclass, jlong settingsVa, jfloat cosine) {
    PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    pSettings->mContactNormalCosMaxDeltaRotation = cosine;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    setContactPointPreserveLambdaMaxDistSq
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_setContactPointPreserveLambdaMaxDistSq
  (JNIEnv *, jclass, jlong settingsVa, jfloat squaredDistance) {
    PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    pSettings->mContactPointPreserveLambdaMaxDistSq = squaredDistance;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    setDeterministicSimulation
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_setDeterministicSimulation
  (JNIEnv *, jclass, jlong settingsVa, jboolean setting) {
    PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    pSettings->mDeterministicSimulation = setting;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    setLinearCastMaxPenetration
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_setLinearCastMaxPenetration
  (JNIEnv *, jclass, jlong settingsVa, jfloat penetration) {
    PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    pSettings->mLinearCastMaxPenetration = penetration;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    setLinearCastThreshold
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_setLinearCastThreshold
  (JNIEnv *, jclass, jlong settingsVa, jfloat threshold) {
    PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    pSettings->mLinearCastThreshold = threshold;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    setManifoldTolerance
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_setManifoldTolerance
  (JNIEnv *, jclass, jlong settingsVa, jfloat tolerance) {
    PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    pSettings->mManifoldTolerance = tolerance;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    setMaxInFlightBodyPairs
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_setMaxInFlightBodyPairs
  (JNIEnv *, jclass, jlong settingsVa, jint maxPairs) {
    PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    pSettings->mMaxInFlightBodyPairs = maxPairs;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    setMaxPenetrationDistance
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_setMaxPenetrationDistance
  (JNIEnv *, jclass, jlong settingsVa, jfloat distance) {
    PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    pSettings->mMaxPenetrationDistance = distance;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    setMinVelocityForRestitution
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_setMinVelocityForRestitution
  (JNIEnv *, jclass, jlong settingsVa, jfloat speed) {
    PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    pSettings->mMinVelocityForRestitution = speed;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    setNumPositionSteps
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_setNumPositionSteps
  (JNIEnv *, jclass, jlong settingsVa, jint numSteps) {
    PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    pSettings->mNumPositionSteps = numSteps;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    setNumVelocitySteps
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_setNumVelocitySteps
  (JNIEnv *, jclass, jlong settingsVa, jint numSteps) {
    PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    pSettings->mNumVelocitySteps = numSteps;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    setPenetrationSlop
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_setPenetrationSlop
  (JNIEnv *, jclass, jlong settingsVa, jfloat slop) {
    PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    pSettings->mPenetrationSlop = slop;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    setPointVelocitySleepThreshold
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_setPointVelocitySleepThreshold
  (JNIEnv *, jclass, jlong settingsVa, jfloat speed) {
    PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    pSettings->mPointVelocitySleepThreshold = speed;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    setSpeculativeContactDistance
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_setSpeculativeContactDistance
  (JNIEnv *, jclass, jlong settingsVa, jfloat distance) {
    PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    pSettings->mSpeculativeContactDistance = distance;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    setStepListenerBatchesPerJob
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_setStepListenerBatchesPerJob
  (JNIEnv *, jclass, jlong settingsVa, jint numBatches) {
    PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    pSettings->mStepListenerBatchesPerJob = numBatches;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    setStepListenersBatchSize
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_setStepListenersBatchSize
  (JNIEnv *, jclass, jlong settingsVa, jint numListeners) {
    PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    pSettings->mStepListenersBatchSize = numListeners;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    setTimeBeforeSleep
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_setTimeBeforeSleep
  (JNIEnv *, jclass, jlong settingsVa, jfloat interval) {
    PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    pSettings->mTimeBeforeSleep = interval;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    setUseBodyPairContactCache
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_setUseBodyPairContactCache
  (JNIEnv *, jclass, jlong settingsVa, jboolean setting) {
    PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    pSettings->mUseBodyPairContactCache = setting;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    setUseLargeIslandSplitter
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_setUseLargeIslandSplitter
  (JNIEnv *, jclass, jlong settingsVa, jboolean setting) {
    PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    pSettings->mUseLargeIslandSplitter = setting;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSettings
 * Method:    setUseManifoldReduction
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSettings_setUseManifoldReduction
  (JNIEnv *, jclass, jlong settingsVa, jboolean setting) {
    PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    pSettings->mUseManifoldReduction = setting;
}
