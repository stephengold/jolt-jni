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
#include "Jolt/Skeleton/SkeletonMapper.h"

#include "auto/com_github_stephengold_joltjni_SkeletonMapper.h"
#include "auto/com_github_stephengold_joltjni_SkeletonMapperRef.h"
#include "glue/glue.h"

using namespace JPH;

IMPLEMENT_REF(SkeletonMapper,
  Java_com_github_stephengold_joltjni_SkeletonMapperRef_copy,
  Java_com_github_stephengold_joltjni_SkeletonMapperRef_createDefault,
  Java_com_github_stephengold_joltjni_SkeletonMapperRef_free,
  Java_com_github_stephengold_joltjni_SkeletonMapperRef_getPtr,
  Java_com_github_stephengold_joltjni_SkeletonMapperRef_toRefC)

/*
 * Class:     com_github_stephengold_joltjni_SkeletonMapper
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SkeletonMapper_createDefault
  BODYOF_CREATE_DEFAULT_TARGET(SkeletonMapper)

/*
 * Class:     com_github_stephengold_joltjni_SkeletonMapper
 * Method:    getRefCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SkeletonMapper_getRefCount
  (JNIEnv *, jclass, jlong mapperVa) {
    const SkeletonMapper * const pMapper
            = reinterpret_cast<SkeletonMapper *> (mapperVa);
    const uint32 result = pMapper->GetRefCount();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SkeletonMapper
 * Method:    initialize
 * Signature: (JJJJJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SkeletonMapper_initialize
  (JNIEnv *pEnv, jclass, jlong mapperVa, jlong skeleton1Va, jlong pose1Va,
  jlong skeleton2Va, jlong pose2Va) {
    SkeletonMapper * const pMapper
            = reinterpret_cast<SkeletonMapper *> (mapperVa);
    const Skeleton * const pSkeleton1
            = reinterpret_cast<Skeleton *> (skeleton1Va);
    const Mat44 * const pNeutralPose1 = reinterpret_cast<Mat44 *> (pose1Va);
    const Skeleton * const pSkeleton2
            = reinterpret_cast<Skeleton *> (skeleton2Va);
    const Mat44 * const pNeutralPose2 = reinterpret_cast<Mat44 *> (pose2Va);
    pMapper->Initialize(pSkeleton1, pNeutralPose1, pSkeleton2, pNeutralPose2);
}

/*
 * Class:     com_github_stephengold_joltjni_SkeletonMapper
 * Method:    lockAllTranslations
 * Signature: (JJJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SkeletonMapper_lockAllTranslations
  (JNIEnv *pEnv, jclass, jlong mapperVa, jlong skeletonVa, jlong poseVa) {
    SkeletonMapper * const pMapper
            = reinterpret_cast<SkeletonMapper *> (mapperVa);
    const Skeleton * const pSkeleton
            = reinterpret_cast<Skeleton *> (skeletonVa);
    const Mat44 * const pPose = reinterpret_cast<Mat44 *> (poseVa);
    pMapper->LockAllTranslations(pSkeleton, pPose);
}

/*
 * Class:     com_github_stephengold_joltjni_SkeletonMapper
 * Method:    map
 * Signature: (JJJJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SkeletonMapper_map
  (JNIEnv *pEnv, jclass, jlong mapperVa, jlong pose1Va, jlong pose2LocalVa,
  jlong storePose2ModelVa) {
    const SkeletonMapper * const pMapper
            = reinterpret_cast<SkeletonMapper *> (mapperVa);
    const Mat44 * const pPose1 = reinterpret_cast<Mat44 *> (pose1Va);
    const Mat44 * const pPose2Local = reinterpret_cast<Mat44 *> (pose2LocalVa);
    Mat44 * const pStorePose2ModelSpace
            = reinterpret_cast<Mat44 *> (storePose2ModelVa);
    pMapper->Map(pPose1, pPose2Local, pStorePose2ModelSpace);
}

/*
 * Class:     com_github_stephengold_joltjni_SkeletonMapper
 * Method:    mapReverse
 * Signature: (JJJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SkeletonMapper_mapReverse
  (JNIEnv *pEnv, jclass, jlong mapperVa, jlong pose2Va, jlong storePose1Va) {
    const SkeletonMapper * const pMapper
            = reinterpret_cast<SkeletonMapper *> (mapperVa);
    const Mat44 * const pPose2 = reinterpret_cast<Mat44 *> (pose2Va);
    Mat44 * const pStorePose1 = reinterpret_cast<Mat44 *> (storePose1Va);
    pMapper->MapReverse(pPose2, pStorePose1);
}

/*
 * Class:     com_github_stephengold_joltjni_SkeletonMapper
 * Method:    setEmbedded
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SkeletonMapper_setEmbedded
  (JNIEnv *, jclass, jlong mapperVa) {
    SkeletonMapper * const pMapper
            = reinterpret_cast<SkeletonMapper *> (mapperVa);
    pMapper->SetEmbedded();
}

/*
 * Class:     com_github_stephengold_joltjni_SkeletonMapper
 * Method:    toRef
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SkeletonMapper_toRef
  (JNIEnv *, jclass, jlong mapperVa) {
    SkeletonMapper * const pMapper
            = reinterpret_cast<SkeletonMapper *> (mapperVa);
    Ref<SkeletonMapper> * const pResult = new Ref<SkeletonMapper>(pMapper);
    TRACE_NEW("Ref<SkeletonMapper>", pResult)
    return reinterpret_cast<jlong> (pResult);
}