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
#include "Jolt/Skeleton/SkeletonPose.h"
#include "auto/com_github_stephengold_joltjni_JointState.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_JointState
 * Method:    getRotation
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_JointState_getRotation
  (JNIEnv *pEnv, jclass, jlong stateVa, jobject storeFloats) {
    const SkeletonPose::JointState * const pState
            = reinterpret_cast<SkeletonPose::JointState *> (stateVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 4);
    const Quat& result = pState->mRotation;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
    pFloats[3] = result.GetW();
}

/*
 * Class:     com_github_stephengold_joltjni_JointState
 * Method:    getTranslation
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_JointState_getTranslation
  (JNIEnv *pEnv, jclass, jlong stateVa, jobject storeFloats) {
    const SkeletonPose::JointState * const pState
            = reinterpret_cast<SkeletonPose::JointState *> (stateVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& result = pState->mTranslation;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_JointState
 * Method:    setRotation
 * Signature: (JFFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_JointState_setRotation
  (JNIEnv *, jclass, jlong stateVa, jfloat qx, jfloat qy, jfloat qz, jfloat qw) {
    SkeletonPose::JointState * const pState
            = reinterpret_cast<SkeletonPose::JointState *> (stateVa);
    const Quat rotation(qx, qy, qz, qw);
    pState->mRotation = rotation;
}

/*
 * Class:     com_github_stephengold_joltjni_JointState
 * Method:    setTranslation
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_JointState_setTranslation
  (JNIEnv *, jclass, jlong stateVa, jfloat x, jfloat y, jfloat z) {
    SkeletonPose::JointState * const pState
            = reinterpret_cast<SkeletonPose::JointState *> (stateVa);
    const Vec3 offset(x, y, z);
    pState->mTranslation = offset;
}