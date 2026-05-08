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
#include "Jolt/Physics/Ragdoll/Ragdoll.h"

#include "auto/com_github_stephengold_joltjni_Ragdoll.h"
#include "auto/com_github_stephengold_joltjni_RagdollRef.h"
#include "glue/glue.h"

using namespace JPH;

IMPLEMENT_REF(Ragdoll,
  Java_com_github_stephengold_joltjni_RagdollRef_copy,
  Java_com_github_stephengold_joltjni_RagdollRef_createDefault,
  Java_com_github_stephengold_joltjni_RagdollRef_free,
  Java_com_github_stephengold_joltjni_RagdollRef_getPtr,
  Java_com_github_stephengold_joltjni_RagdollRef_toRefC)

/*
 * Class:     com_github_stephengold_joltjni_RagdollRef
 * Method:    freeWithSystem
 * Signature: (JLcom/github/stephengold/joltjni/PhysicsSystem;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RagdollRef_freeWithSystem
  (JNIEnv *pEnv, jclass clazz, jlong refVa, jobject) {
    Java_com_github_stephengold_joltjni_RagdollRef_free(pEnv, clazz, refVa);
}

/*
 * Class:     com_github_stephengold_joltjni_Ragdoll
 * Method:    addLinearVelocity
 * Signature: (JFFFZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Ragdoll_addLinearVelocity
  (JNIEnv *, jclass, jlong ragdollVa, jfloat dx, jfloat dy, jfloat dz,
  jboolean lockBodies) {
    Ragdoll * const pRagdoll = reinterpret_cast<Ragdoll *> (ragdollVa);
    const Vec3 deltaV(dx, dy, dz);
    pRagdoll->AddLinearVelocity(deltaV, lockBodies);
}

/*
 * Class:     com_github_stephengold_joltjni_Ragdoll
 * Method:    addToPhysicsSystem
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Ragdoll_addToPhysicsSystem
  (JNIEnv *, jclass, jlong ragdollVa, jint ordinal) {
    Ragdoll * const pRagdoll = reinterpret_cast<Ragdoll *> (ragdollVa);
    const EActivation activate = (EActivation) ordinal;
    pRagdoll->AddToPhysicsSystem(activate);
}

/*
 * Class:     com_github_stephengold_joltjni_Ragdoll
 * Method:    driveToPoseUsingKinematics
 * Signature: (JJFZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Ragdoll_driveToPoseUsingKinematics
  (JNIEnv *, jclass, jlong ragdollVa, jlong poseVa, jfloat time,
  jboolean lockBodies) {
    Ragdoll * const pRagdoll = reinterpret_cast<Ragdoll *> (ragdollVa);
    const SkeletonPose * const pPose
            = reinterpret_cast<SkeletonPose *> (poseVa);
    pRagdoll->DriveToPoseUsingKinematics(*pPose, time, lockBodies);
}

/*
 * Class:     com_github_stephengold_joltjni_Ragdoll
 * Method:    driveToPoseUsingMotors
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Ragdoll_driveToPoseUsingMotors
  (JNIEnv *, jclass, jlong ragdollVa, jlong poseVa) {
    Ragdoll * const pRagdoll = reinterpret_cast<Ragdoll *> (ragdollVa);
    const SkeletonPose * const pPose
            = reinterpret_cast<SkeletonPose *> (poseVa);
    pRagdoll->DriveToPoseUsingMotors(*pPose);
}

/*
 * Class:     com_github_stephengold_joltjni_Ragdoll
 * Method:    driveToPoseUsingMotorsPv
 * Signature: (JJJF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Ragdoll_driveToPoseUsingMotorsPv
  (JNIEnv *, jclass, jlong ragdollVa, jlong prevPoseVa, jlong poseVa,
  float deltaTime) {
    Ragdoll * const pRagdoll = reinterpret_cast<Ragdoll *> (ragdollVa);
    const SkeletonPose * const pPrevPose
            = reinterpret_cast<SkeletonPose *> (prevPoseVa);
    const SkeletonPose * const pPose
            = reinterpret_cast<SkeletonPose *> (poseVa);
    pRagdoll->DriveToPoseUsingMotors(*pPrevPose, *pPose, deltaTime);
}

/*
 * Class:     com_github_stephengold_joltjni_Ragdoll
 * Method:    getBodyCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Ragdoll_getBodyCount
  (JNIEnv *, jclass, jlong ragdollVa) {
    const Ragdoll * const pRagdoll = reinterpret_cast<Ragdoll *> (ragdollVa);
    const size_t result = pRagdoll->GetBodyCount();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Ragdoll
 * Method:    getBodyIds
 * Signature: (J[I)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Ragdoll_getBodyIds
  (JNIEnv *pEnv, jclass, jlong ragdollVa, jintArray storeIds) {
    const Ragdoll * const pRagdoll = reinterpret_cast<Ragdoll *> (ragdollVa);
    const jsize arrayLength = pEnv->GetArrayLength(storeIds);
    jboolean isCopy;
    jint * const pIds = pEnv->GetIntArrayElements(storeIds, &isCopy);
    const Array<BodyID> idArray = pRagdoll->GetBodyIDs();
    const size_t numBodies = idArray.size();
    for (size_t i = 0; i < numBodies && i < arrayLength; ++i) {
        const BodyID& id = idArray[i];
        pIds[i] = id.GetIndexAndSequenceNumber();
    }
    pEnv->ReleaseIntArrayElements(storeIds, pIds, 0);
}

/*
 * Class:     com_github_stephengold_joltjni_Ragdoll
 * Method:    getConstraint
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Ragdoll_getConstraint
  (JNIEnv *, jclass, jlong ragdollVa, jint constraintIndex) {
    Ragdoll * const pRagdoll = reinterpret_cast<Ragdoll *> (ragdollVa);
    TwoBodyConstraint * const pResult
            = pRagdoll->GetConstraint(constraintIndex);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Ragdoll
 * Method:    getConstraintCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Ragdoll_getConstraintCount
  (JNIEnv *, jclass, jlong ragdollVa) {
    const Ragdoll * const pRagdoll = reinterpret_cast<Ragdoll *> (ragdollVa);
    const size_t result = pRagdoll->GetConstraintCount();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Ragdoll
 * Method:    getPose
 * Signature: (JLjava/nio/DoubleBuffer;JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Ragdoll_getPose
  (JNIEnv *pEnv, jclass, jlong ragdollVa, jobject storeDoubles,
  jlong storeMatsVa, jboolean lockBodies) {
    Ragdoll * const pRagdoll = reinterpret_cast<Ragdoll *> (ragdollVa);
    RVec3 rootOffset;
    Mat44 * const pMatrices = reinterpret_cast<Mat44 *> (storeMatsVa);
    pRagdoll->GetPose(rootOffset, pMatrices, lockBodies);
    DIRECT_DOUBLE_BUFFER(pEnv, storeDoubles, pStoreDoubles, capacityDoubles);
    JPH_ASSERT(capacityDoubles >= 3);
    pStoreDoubles[0] = rootOffset.GetX();
    pStoreDoubles[1] = rootOffset.GetY();
    pStoreDoubles[2] = rootOffset.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_Ragdoll
 * Method:    getPoseToObject
 * Signature: (JJZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Ragdoll_getPoseToObject
  (JNIEnv *, jclass, jlong ragdollVa, jlong poseVa, jboolean lockBodies) {
    Ragdoll * const pRagdoll = reinterpret_cast<Ragdoll *> (ragdollVa);
    SkeletonPose * pPose = reinterpret_cast<SkeletonPose *> (poseVa);
    pRagdoll->GetPose(*pPose, lockBodies);
}

/*
 * Class:     com_github_stephengold_joltjni_Ragdoll
 * Method:    getRefCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Ragdoll_getRefCount
  (JNIEnv *, jclass, jlong ragdollVa) {
    const Ragdoll * const pRagdoll = reinterpret_cast<Ragdoll *> (ragdollVa);
    const uint32 result = pRagdoll->GetRefCount();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Ragdoll
 * Method:    getRootTransform
 * Signature: (JLjava/nio/DoubleBuffer;Ljava/nio/FloatBuffer;Z)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Ragdoll_getRootTransform
  (JNIEnv *pEnv, jclass, jlong ragdollVa, jobject storeDoubles,
  jobject storeFloats, jboolean lockBodies) {
    const Ragdoll * const pRagdoll = reinterpret_cast<Ragdoll *> (ragdollVa);
    RVec3 outPosition;
    Quat outRotation;
    pRagdoll->GetRootTransform(outPosition, outRotation, lockBodies);
    DIRECT_DOUBLE_BUFFER(pEnv, storeDoubles, pStoreDoubles, capacityDoubles);
    JPH_ASSERT(capacityDoubles >= 3);
    pStoreDoubles[0] = outPosition.GetX();
    pStoreDoubles[1] = outPosition.GetY();
    pStoreDoubles[2] = outPosition.GetZ();
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pStoreFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 4);
    pStoreFloats[0] = outRotation.GetX();
    pStoreFloats[1] = outRotation.GetY();
    pStoreFloats[2] = outRotation.GetZ();
    pStoreFloats[3] = outRotation.GetW();
}

/*
 * Class:     com_github_stephengold_joltjni_Ragdoll
 * Method:    removeFromPhysicsSystem
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Ragdoll_removeFromPhysicsSystem
  (JNIEnv *, jclass, jlong ragdollVa) {
    Ragdoll * const pRagdoll = reinterpret_cast<Ragdoll *> (ragdollVa);
    pRagdoll->RemoveFromPhysicsSystem();
}

/*
 * Class:     com_github_stephengold_joltjni_Ragdoll
 * Method:    setEmbedded
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Ragdoll_setEmbedded
  (JNIEnv *, jclass, jlong ragdollVa) {
    Ragdoll * const pRagdoll = reinterpret_cast<Ragdoll *> (ragdollVa);
    pRagdoll->SetEmbedded();
}

/*
 * Class:     com_github_stephengold_joltjni_Ragdoll
 * Method:    setPose
 * Signature: (JJZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Ragdoll_setPose
  (JNIEnv *, jclass, jlong ragdollVa, jlong poseVa, jboolean lockBodies) {
    Ragdoll * const pRagdoll = reinterpret_cast<Ragdoll *> (ragdollVa);
    const SkeletonPose * const pPose
            = reinterpret_cast<SkeletonPose *> (poseVa);
    pRagdoll->SetPose(*pPose, lockBodies);
}

/*
 * Class:     com_github_stephengold_joltjni_Ragdoll
 * Method:    toRef
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Ragdoll_toRef
  (JNIEnv *, jclass, jlong ragdollVa) {
    Ragdoll * const pRagdoll = reinterpret_cast<Ragdoll *> (ragdollVa);
    Ref<Ragdoll> * const pResult = new Ref<Ragdoll>(pRagdoll);
    TRACE_NEW("Ref<Ragdoll>", pResult)
    return reinterpret_cast<jlong> (pResult);
}