/*
Copyright (c) 2026 Stephen Gold

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
#include "Jolt/Physics/Hair/Hair.h"
#include "auto/com_github_stephengold_joltjni_Hair.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_Hair
 * Method:    create
 * Signature: (JDDDFFFFI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Hair_create
  (JNIEnv *pEnv, jclass, jlong settingsVa, jdouble xx, jdouble yy, jdouble zz,
  jfloat qx, jfloat qy, jfloat qz, jfloat qw, jint objLayer) {
    const HairSettings * const pSettings
            = reinterpret_cast<HairSettings *> (settingsVa);
    const RVec3 location(xx, yy, zz);
    const Quat orientation(qx, qy, qz, qw);
    const ObjectLayer layer = (ObjectLayer)objLayer;
    Hair * const pResult = new Hair(pSettings, location, orientation, layer);
    TRACE_NEW_TARGET("Hair", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Hair
 * Method:    draw
 * Signature: (JJJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Hair_draw
  (JNIEnv *, jclass, jlong hairVa, jlong settingsVa, jlong rendererVa) {
#ifdef JPH_DEBUG_RENDERER
    Hair * const pHair = reinterpret_cast<Hair *> (hairVa);
    const Hair::DrawSettings * const pSettings
            = reinterpret_cast<Hair::DrawSettings *> (settingsVa);
    DebugRenderer * const pRenderer
            = reinterpret_cast<DebugRenderer *> (rendererVa);
    pHair->Draw(*pSettings, pRenderer);
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_Hair
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Hair_free
  BODYOF_FREE(Hair)

/*
 * Class:     com_github_stephengold_joltjni_Hair
 * Method:    getHairSettings
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Hair_getHairSettings
  (JNIEnv *, jclass, jlong hairVa) {
    const Hair * const pHair = reinterpret_cast<Hair *> (hairVa);
    const HairSettings * const pResult = pHair->GetHairSettings();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Hair
 * Method:    getRenderPositions
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Hair_getRenderPositions
  (JNIEnv *pEnv, jclass, jlong hairVa, jobject storeFloats) {
    const Hair * const pHair = reinterpret_cast<Hair *> (hairVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    const Float3 * pResult = pHair->GetRenderPositions();
    int numVectors = capacityFloats/3;
    for (int i = 0; i < numVectors; ++i) {
        pFloats[3*i] = pResult[i].x;
        pFloats[3*i+1] = pResult[i].y;
        pFloats[3*i+2] = pResult[i].z;
    }
}

/*
 * Class:     com_github_stephengold_joltjni_Hair
 * Method:    getScalpVertices
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Hair_getScalpVertices
  (JNIEnv *pEnv, jclass, jlong hairVa, jobject storeFloats) {
    const Hair * const pHair = reinterpret_cast<Hair *> (hairVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    const Float3 * pResult = pHair->GetScalpVertices();
    int numVectors = capacityFloats/3;
    for (int i = 0; i < numVectors; ++i) {
        pFloats[3*i] = pResult[i].x;
        pFloats[3*i+1] = pResult[i].y;
        pFloats[3*i+2] = pResult[i].z;
    }
}

/*
 * Class:     com_github_stephengold_joltjni_Hair
 * Method:    getWorldTransform
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Hair_getWorldTransform
  (JNIEnv *, jclass, jlong hairVa) {
    const Hair * const pHair = reinterpret_cast<Hair *> (hairVa);
    RMat44 *pResult = new RMat44();
    TRACE_NEW_TARGET("RMat44", pResult)
    *pResult = pHair->GetWorldTransform();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Hair
 * Method:    init
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Hair_init
  (JNIEnv *, jclass, jlong hairVa, jlong systemVa) {
    Hair * const pHair = reinterpret_cast<Hair *> (hairVa);
    ComputeSystem * const pSystem
            = reinterpret_cast<ComputeSystem *> (systemVa);
    pHair->Init(pSystem);
}

/*
 * Class:     com_github_stephengold_joltjni_Hair
 * Method:    lockReadBackBuffers
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Hair_lockReadBackBuffers
  (JNIEnv *, jclass, jlong hairVa) {
    Hair * const pHair = reinterpret_cast<Hair *> (hairVa);
    pHair->LockReadBackBuffers();
}

/*
 * Class:     com_github_stephengold_joltjni_Hair
 * Method:    readBackGpuState
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Hair_readBackGpuState
  (JNIEnv *, jclass, jlong hairVa, jlong queueVa) {
    Hair * const pHair = reinterpret_cast<Hair *> (hairVa);
    ComputeQueue * const pQueue = reinterpret_cast<ComputeQueue *> (queueVa);
    pHair->ReadBackGPUState(pQueue);
}

/*
 * Class:     com_github_stephengold_joltjni_Hair
 * Method:    setPosition
 * Signature: (JDDD)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Hair_setPosition
  (JNIEnv *, jclass, jlong hairVa, jdouble xx, jdouble yy, jdouble zz) {
    Hair * const pHair = reinterpret_cast<Hair *> (hairVa);
    const RVec3 location(xx, yy, zz);
    pHair->SetPosition(location);
}

/*
 * Class:     com_github_stephengold_joltjni_Hair
 * Method:    setRotation
 * Signature: (JFFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Hair_setRotation
  (JNIEnv *, jclass, jlong hairVa, jfloat qx, jfloat qy, jfloat qz, jfloat qw) {
    Hair * const pHair = reinterpret_cast<Hair *> (hairVa);
    const Quat orientation(qx, qy, qz, qw);
    pHair->SetRotation(orientation);
}

/*
 * Class:     com_github_stephengold_joltjni_Hair
 * Method:    unlockReadBackBuffers
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Hair_unlockReadBackBuffers
  (JNIEnv *, jclass, jlong hairVa) {
    Hair * const pHair = reinterpret_cast<Hair *> (hairVa);
    pHair->UnlockReadBackBuffers();
}

/*
 * Class:     com_github_stephengold_joltjni_Hair
 * Method:    update
 * Signature: (JFJJJJJJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Hair_update
  (JNIEnv *, jclass, jlong hairVa, jfloat deltaTime, jlong jointToHairVa,
  jlong jointMatricesVa, jlong physicsSystemVa, jlong shadersVa,
  jlong computeSystemVa, jlong queueVa) {
    Hair * const pHair = reinterpret_cast<Hair *> (hairVa);
    const Mat44 * const pJointToHair
            = reinterpret_cast<Mat44 *> (jointToHairVa);
    const Mat44 * const pJointMatrices
            = reinterpret_cast<Mat44 *> (jointMatricesVa);
    const PhysicsSystem * const pPhysicsSystem
            = reinterpret_cast<PhysicsSystem *> (physicsSystemVa);
    const HairShaders * const pShaders
            = reinterpret_cast<HairShaders *> (shadersVa);
    ComputeSystem * const pComputeSystem
            = reinterpret_cast<ComputeSystem *> (computeSystemVa);
    ComputeQueue * const pQueue
            = reinterpret_cast<ComputeQueue *> (queueVa);
    pHair->Update(deltaTime, *pJointToHair, pJointMatrices, *pPhysicsSystem,
            *pShaders, pComputeSystem, pQueue);
}