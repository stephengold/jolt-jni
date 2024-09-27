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