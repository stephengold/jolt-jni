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
#include "Jolt/Skeleton/Skeleton.h"

#include "auto/com_github_stephengold_joltjni_Skeleton.h"
#include "auto/com_github_stephengold_joltjni_SkeletonRef.h"
#include "glue/glue.h"

using namespace JPH;

IMPLEMENT_REF(Skeleton,
  Java_com_github_stephengold_joltjni_SkeletonRef_copy,
  Java_com_github_stephengold_joltjni_SkeletonRef_createEmpty,
  Java_com_github_stephengold_joltjni_SkeletonRef_free,
  Java_com_github_stephengold_joltjni_SkeletonRef_getPtr)

/*
 * Class:     com_github_stephengold_joltjni_Skeleton
 * Method:    addJointWithParent
 * Signature: (JLjava/lang/String;I)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Skeleton_addJointWithParent
  (JNIEnv *pEnv, jclass, jlong skeletonVa, jstring name, jint parentIndex) {
    Skeleton * const pSkeleton = reinterpret_cast<Skeleton *> (skeletonVa);
    jboolean isCopy;
    const char * const pName = pEnv->GetStringUTFChars(name, &isCopy);
    const uint result = pSkeleton->AddJoint(pName, parentIndex);
    pEnv->ReleaseStringUTFChars(name, pName);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Skeleton
 * Method:    addRootJoint
 * Signature: (JLjava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Skeleton_addRootJoint
  (JNIEnv *pEnv, jclass, jlong skeletonVa, jstring name) {
    Skeleton * const pSkeleton = reinterpret_cast<Skeleton *> (skeletonVa);
    jboolean isCopy;
    const char * const pName = pEnv->GetStringUTFChars(name, &isCopy);
    const uint result = pSkeleton->AddJoint(pName);
    pEnv->ReleaseStringUTFChars(name, pName);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Skeleton
 * Method:    areJointsCorrectlyOrdered
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_Skeleton_areJointsCorrectlyOrdered
  (JNIEnv *, jclass, jlong skeletonVa) {
    const Skeleton * const pSkeleton
            = reinterpret_cast<Skeleton *> (skeletonVa);
    const bool result = pSkeleton->AreJointsCorrectlyOrdered();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Skeleton
 * Method:    calculateParentJointIndices
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Skeleton_calculateParentJointIndices
  (JNIEnv *, jclass, jlong skeletonVa) {
    Skeleton * const pSkeleton = reinterpret_cast<Skeleton *> (skeletonVa);
    pSkeleton->CalculateParentJointIndices();
}

/*
 * Class:     com_github_stephengold_joltjni_Skeleton
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Skeleton_createDefault
  (JNIEnv *, jclass) {
    Skeleton * const pSkeleton = new Skeleton();
    TRACE_NEW("Skeleton", pSkeleton)
    return reinterpret_cast<jlong> (pSkeleton);
}

/*
 * Class:     com_github_stephengold_joltjni_Skeleton
 * Method:    getJoint
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Skeleton_getJoint
  (JNIEnv *, jclass, jlong skeletonVa, jint jointIndex) {
    Skeleton * const pSkeleton
            = reinterpret_cast<Skeleton *> (skeletonVa);
    const Skeleton::Joint& result = pSkeleton->GetJoint(jointIndex);
    return reinterpret_cast<jlong> (&result);
}

/*
 * Class:     com_github_stephengold_joltjni_Skeleton
 * Method:    getJointCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Skeleton_getJointCount
  (JNIEnv *, jclass, jlong skeletonVa) {
    const Skeleton * const pSkeleton = reinterpret_cast<Skeleton *> (skeletonVa);
    const int result = pSkeleton->GetJointCount();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Skeleton
 * Method:    getJointIndex
 * Signature: (JLjava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Skeleton_getJointIndex
  (JNIEnv *pEnv, jclass, jlong skeletonVa, jstring name) {
    const Skeleton * const pSkeleton
            = reinterpret_cast<Skeleton *> (skeletonVa);
    jboolean isCopy;
    const char * const pName = pEnv->GetStringUTFChars(name, &isCopy);
    const int result = pSkeleton->GetJointIndex(pName);
    pEnv->ReleaseStringUTFChars(name, pName);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Skeleton
 * Method:    getRefCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Skeleton_getRefCount
  (JNIEnv *, jclass, jlong skeletonVa) {
    const Skeleton * const pSkeleton = reinterpret_cast<Skeleton *> (skeletonVa);
    const uint32 result = pSkeleton->GetRefCount();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Skeleton
 * Method:    saveBinaryState
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Skeleton_saveBinaryState
  (JNIEnv *, jclass, jlong skeletonVa, jlong streamVa) {
    const Skeleton * const pSkeleton
            = reinterpret_cast<Skeleton *> (skeletonVa);
    StreamOut * const pStream = reinterpret_cast<StreamOut *> (streamVa);
    pSkeleton->SaveBinaryState(*pStream);
}

/*
 * Class:     com_github_stephengold_joltjni_Skeleton
 * Method:    setEmbedded
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Skeleton_setEmbedded
  (JNIEnv *, jclass, jlong skeletonVa) {
    Skeleton * const pSkeleton = reinterpret_cast<Skeleton *> (skeletonVa);
    pSkeleton->SetEmbedded();
}

/*
 * Class:     com_github_stephengold_joltjni_Skeleton
 * Method:    toRef
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Skeleton_toRef
  (JNIEnv *, jclass, jlong skeletonVa) {
    Skeleton * const pSkeleton = reinterpret_cast<Skeleton *> (skeletonVa);
    Ref<Skeleton> * const pResult = new Ref<Skeleton>(pSkeleton);
    TRACE_NEW("Ref<Skeleton>", pResult)
    return reinterpret_cast<jlong> (pResult);
}