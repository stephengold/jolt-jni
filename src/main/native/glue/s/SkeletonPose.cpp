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
#include "Jolt/Skeleton/SkeletonPose.h"
#include "auto/com_github_stephengold_joltjni_SkeletonPose.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_SkeletonPose
 * Method:    calculateJointMatrices
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SkeletonPose_calculateJointMatrices
  (JNIEnv *, jclass, jlong poseVa) {
    SkeletonPose * const pPose = reinterpret_cast<SkeletonPose *> (poseVa);
    pPose->CalculateJointMatrices();
}

/*
 * Class:     com_github_stephengold_joltjni_SkeletonPose
 * Method:    calculateJointStates
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SkeletonPose_calculateJointStates
  (JNIEnv *, jclass, jlong poseVa) {
    SkeletonPose * const pPose = reinterpret_cast<SkeletonPose *> (poseVa);
    pPose->CalculateJointStates();
}

/*
 * Class:     com_github_stephengold_joltjni_SkeletonPose
 * Method:    calculateLocalSpaceJointMatrices
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SkeletonPose_calculateLocalSpaceJointMatrices
  (JNIEnv *pEnv, jclass, jlong poseVa, jlong storeMatsVa) {
    SkeletonPose * const pPose = reinterpret_cast<SkeletonPose *> (poseVa);
    Mat44 * const pStoreMatrices = reinterpret_cast<Mat44 *> (storeMatsVa);
    pPose->CalculateLocalSpaceJointMatrices(pStoreMatrices);
}

/*
 * Class:     com_github_stephengold_joltjni_SkeletonPose
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SkeletonPose_createCopy
  (JNIEnv *, jclass, jlong originalVa) {
    const SkeletonPose * const pOriginal
            = reinterpret_cast<SkeletonPose *> (originalVa);
    SkeletonPose * const pResult = new SkeletonPose(*pOriginal);
    TRACE_NEW("SkeletonPose", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SkeletonPose
 * Method:    createSkeletonPoseDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SkeletonPose_createSkeletonPoseDefault
  (JNIEnv *, jclass) {
    SkeletonPose * const pResult = new SkeletonPose();
    TRACE_NEW("SkeletonPose", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SkeletonPose
 * Method:    draw
 * Signature: (JJJJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SkeletonPose_draw
  (JNIEnv *, jclass, jlong poseVa, jlong settingsVa, jlong rendererVa,
  jlong transformVa) {
#ifdef JPH_DEBUG_RENDERER
    const SkeletonPose * const pPose = reinterpret_cast<SkeletonPose *> (poseVa);
    const SkeletonPose::DrawSettings * const pSettings
            = reinterpret_cast<SkeletonPose::DrawSettings *> (settingsVa);
    DebugRenderer * const pRenderer
            = reinterpret_cast<DebugRenderer *> (rendererVa);
    const RMat44 * const pTransform
            = reinterpret_cast<RMat44 *> (transformVa);
    pPose->Draw(*pSettings, pRenderer, *pTransform);
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_SkeletonPose
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SkeletonPose_free
  (JNIEnv *, jclass, jlong poseVa) {
    SkeletonPose * const pPose = reinterpret_cast<SkeletonPose *> (poseVa);
    TRACE_DELETE("SkeletonPose", pPose)
    delete pPose;
}

/*
 * Class:     com_github_stephengold_joltjni_SkeletonPose
 * Method:    getJoint
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SkeletonPose_getJoint
  (JNIEnv *, jclass, jlong poseVa, jint jointIndex) {
    SkeletonPose * const pPose = reinterpret_cast<SkeletonPose *> (poseVa);
    SkeletonPose::JointState * const pJoint = &pPose->GetJoint(jointIndex);
    return reinterpret_cast<jlong> (pJoint);
}

/*
 * Class:     com_github_stephengold_joltjni_SkeletonPose
 * Method:    getJointCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SkeletonPose_getJointCount
  (JNIEnv *, jclass, jlong poseVa) {
    const SkeletonPose * const pPose
            = reinterpret_cast<SkeletonPose *> (poseVa);
    const uint result = pPose->GetJointCount();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SkeletonPose
 * Method:    getJointMatrices
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SkeletonPose_getJointMatrices
  (JNIEnv *, jclass, jlong poseVa) {
    SkeletonPose * const pPose = reinterpret_cast<SkeletonPose *> (poseVa);
    SkeletonPose::Mat44Vector &vec = pPose->GetJointMatrices();
    Mat44 * pResult = vec.data();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SkeletonPose
 * Method:    getJointMatrix
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SkeletonPose_getJointMatrix
  (JNIEnv *, jclass, jlong poseVa, jint jointIndex) {
    SkeletonPose * const pPose = reinterpret_cast<SkeletonPose *> (poseVa);
    Mat44 * pResult = &pPose->GetJointMatrix(jointIndex);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SkeletonPose
 * Method:    getRootOffset
 * Signature: (J[D)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SkeletonPose_getRootOffset
  (JNIEnv *pEnv, jclass, jlong poseVa, jdoubleArray storeDoubles) {
    const SkeletonPose * const pPose
            = reinterpret_cast<SkeletonPose *> (poseVa);
    const RVec3 offset = pPose->GetRootOffset();
    jboolean isCopy;
    jdouble * const pStoreDoubles
            = pEnv->GetDoubleArrayElements(storeDoubles, &isCopy);
    pStoreDoubles[0] = offset.GetX();
    pStoreDoubles[1] = offset.GetY();
    pStoreDoubles[2] = offset.GetZ();
    pEnv->ReleaseDoubleArrayElements(storeDoubles, pStoreDoubles, 0);
}

/*
 * Class:     com_github_stephengold_joltjni_SkeletonPose
 * Method:    getSkeleton
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SkeletonPose_getSkeleton
  (JNIEnv *, jclass, jlong poseVa) {
    const SkeletonPose * const pPose
            = reinterpret_cast<SkeletonPose *> (poseVa);
    const Skeleton * const pResult = pPose->GetSkeleton();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SkeletonPose
 * Method:    setRootOffset
 * Signature: (JDDD)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SkeletonPose_setRootOffset
  (JNIEnv *, jclass, jlong poseVa, jdouble xx, jdouble yy, jdouble zz) {
    SkeletonPose * const pPose = reinterpret_cast<SkeletonPose *> (poseVa);
    const RVec3 offset(xx, yy, zz);
    pPose->SetRootOffset(offset);
}

/*
 * Class:     com_github_stephengold_joltjni_SkeletonPose
 * Method:    setSkeleton
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SkeletonPose_setSkeleton
  (JNIEnv *, jclass, jlong poseVa, jlong skeletonVa) {
    SkeletonPose * const pPose = reinterpret_cast<SkeletonPose *> (poseVa);
    const Skeleton * const pSkeleton
            = reinterpret_cast<Skeleton *> (skeletonVa);
    pPose->SetSkeleton(pSkeleton);
}