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
#include "Jolt/Jolt.h"
#include "Jolt/Physics/Ragdoll/Ragdoll.h"

#include "auto/com_github_stephengold_joltjni_Ragdoll.h"
#include "auto/com_github_stephengold_joltjni_RagdollRef.h"
#include "glue/glue.h"

using namespace JPH;

IMPLEMENT_REF(Ragdoll,
  Java_com_github_stephengold_joltjni_RagdollRef_copy,
  Java_com_github_stephengold_joltjni_RagdollRef_createEmpty,
  Java_com_github_stephengold_joltjni_RagdollRef_free,
  Java_com_github_stephengold_joltjni_RagdollRef_getPtr)

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
 * Method:    getBodyIds
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Ragdoll_getBodyIds
  (JNIEnv *, jclass, jlong ragdollVa) {
    const Ragdoll * const pRagdoll = reinterpret_cast<Ragdoll *> (ragdollVa);
    const Array<BodyID> * const pResult = &pRagdoll->GetBodyIDs();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Ragdoll
 * Method:    getPose
 * Signature: (J[DJZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Ragdoll_getPose
  (JNIEnv *pEnv, jclass, jlong ragdollVa, jdoubleArray storeDoubles,
  jlong storeMatsVa, jboolean lockBodies) {
    Ragdoll * const pRagdoll = reinterpret_cast<Ragdoll *> (ragdollVa);
    RVec3 rootOffset;
    Mat44 * const pMatrices = reinterpret_cast<Mat44 *> (storeMatsVa);
    pRagdoll->GetPose(rootOffset, pMatrices, lockBodies);
    jboolean isCopy;
    jdouble * const pStoreDoubles
            = pEnv->GetDoubleArrayElements(storeDoubles, &isCopy);
    pStoreDoubles[0] = rootOffset.GetX();
    pStoreDoubles[1] = rootOffset.GetY();
    pStoreDoubles[2] = rootOffset.GetZ();
    pEnv->ReleaseDoubleArrayElements(storeDoubles, pStoreDoubles, 0);
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
 * Signature: (J[D[FZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Ragdoll_getRootTransform
  (JNIEnv *pEnv, jclass, jlong ragdollVa, jdoubleArray storeDoubles,
  jfloatArray storeFloats, jboolean lockBodies) {
    const Ragdoll * const pRagdoll = reinterpret_cast<Ragdoll *> (ragdollVa);
    RVec3 outPosition;
    Quat outRotation;
    pRagdoll->GetRootTransform(outPosition, outRotation, lockBodies);
    jboolean isCopy;
    jdouble * const pStoreDoubles
            = pEnv->GetDoubleArrayElements(storeDoubles, &isCopy);
    pStoreDoubles[0] = outPosition.GetX();
    pStoreDoubles[1] = outPosition.GetY();
    pStoreDoubles[2] = outPosition.GetZ();
    pEnv->ReleaseDoubleArrayElements(storeDoubles, pStoreDoubles, 0);
    jfloat * const pStoreFloats
            = pEnv->GetFloatArrayElements(storeFloats, &isCopy);
    pStoreFloats[0] = outRotation.GetX();
    pStoreFloats[1] = outRotation.GetY();
    pStoreFloats[2] = outRotation.GetZ();
    pStoreFloats[3] = outRotation.GetW();
    pEnv->ReleaseFloatArrayElements(storeFloats, pStoreFloats, 0);
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