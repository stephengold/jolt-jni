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
#include <Jolt/Jolt.h>
#include <Jolt/Skeleton/SkeletalAnimation.h>
#include "auto/com_github_stephengold_joltjni_SkeletalAnimation.h"
#include "auto/com_github_stephengold_joltjni_SkeletalAnimationRef.h"
#include "glue/glue.h"

using namespace JPH;

IMPLEMENT_REF(SkeletalAnimation,
  Java_com_github_stephengold_joltjni_SkeletalAnimationRef_copy,
  Java_com_github_stephengold_joltjni_SkeletalAnimationRef_free,
  Java_com_github_stephengold_joltjni_SkeletalAnimationRef_getPtr)

/*
 * Class:     com_github_stephengold_joltjni_SkeletalAnimation
 * Method:    getDuration
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SkeletalAnimation_getDuration
  (JNIEnv *, jclass, jlong animationVa) {
    const SkeletalAnimation * const pAnimation
            = reinterpret_cast<SkeletalAnimation *> (animationVa);
    const float result = pAnimation->GetDuration();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SkeletalAnimation
 * Method:    getRefCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SkeletalAnimation_getRefCount
  (JNIEnv *, jclass, jlong animationVa) {
    const SkeletalAnimation * const pAnimation
            = reinterpret_cast<SkeletalAnimation *> (animationVa);
    const uint32 result = pAnimation->GetRefCount();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SkeletalAnimation
 * Method:    sample
 * Signature: (JFJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SkeletalAnimation_sample
  (JNIEnv *, jclass, jlong animationVa, jfloat time, jlong poseVa) {
    const SkeletalAnimation * const pAnimation
            = reinterpret_cast<SkeletalAnimation *> (animationVa);
    SkeletonPose * const pStorePose = reinterpret_cast<SkeletonPose *> (poseVa);
    pAnimation->Sample(time, *pStorePose);
}

/*
 * Class:     com_github_stephengold_joltjni_SkeletalAnimation
 * Method:    toRef
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SkeletalAnimation_toRef
  (JNIEnv *, jclass, jlong animationVa) {
    SkeletalAnimation * const pAnimation
            = reinterpret_cast<SkeletalAnimation *> (animationVa);
    Ref<SkeletalAnimation> * const pResult
            = new Ref<SkeletalAnimation>(pAnimation);
    TRACE_NEW("Ref<SkeletalAnimation>", pResult)
    return reinterpret_cast<jlong> (pResult);
}