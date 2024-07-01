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
#include <Jolt/Core/JobSystemThreadPool.h>
#include <Jolt/Physics/PhysicsSystem.h>
#include "auto/com_github_stephengold_joltjni_PhysicsSystem.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    createPhysicsSystem
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_createPhysicsSystem
  (JNIEnv *, jclass) {
    PhysicsSystem * const pResult = new PhysicsSystem();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    getBodyInterface
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_getBodyInterface
  (JNIEnv *, jclass, jlong systemVa) {
    PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    BodyInterface &result = pSystem->GetBodyInterface();
    return reinterpret_cast<jlong> (&result);
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    getGravityX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_getGravityX
  (JNIEnv *, jclass, jlong systemVa) {
    const PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    Vec3 gravity = pSystem->GetGravity();
    float result = gravity.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    getGravityY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_getGravityY
  (JNIEnv *, jclass, jlong systemVa) {
    const PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    Vec3 gravity = pSystem->GetGravity();
    float result = gravity.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    getGravityZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_getGravityZ
  (JNIEnv *, jclass, jlong systemVa) {
    const PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    Vec3 gravity = pSystem->GetGravity();
    float result = gravity.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    getNumActiveBodies
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_getNumActiveBodies
  (JNIEnv *, jclass, jlong systemVa, int typeOrdinal) {
    const PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    EBodyType bodyType = (EBodyType)typeOrdinal;
    uint result = pSystem->GetNumActiveBodies(bodyType);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    getNumBodies
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_getNumActiveBodies
  (JNIEnv *, jclass, jlong systemVa) {
    const PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    uint result = pSystem->GetNumBodies();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    getPhysicsSettings
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_getPhysicsSettings
  (JNIEnv *, jclass, jlong systemVa) {
    const PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    PhysicsSettings * const pSettings = new PhysicsSettings();
    *pSettings = pSystem->GetPhysicsSettings();
    return reinterpret_cast<jlong> (pSettings);
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    init
 * Signature: (JIIIIJJJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_init
  (JNIEnv *, jclass, jlong systemVa, jint maxBodies, jint numBodyMutexes, jint maxBodyPairs,
        jint maxContactConstraints, jlong mapVa, jlong ovbFilterVa, jlong ovoFilterVa) {
    PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    const BroadPhaseLayerInterface * const pMap
            = reinterpret_cast<BroadPhaseLayerInterface *> (mapVa);
    const ObjectVsBroadPhaseLayerFilter * const pOvbplf
            = reinterpret_cast<ObjectVsBroadPhaseLayerFilter *> (ovbFilterVa);
    const ObjectLayerPairFilter * const pOlpf
            = reinterpret_cast<ObjectLayerPairFilter *> (ovoFilterVa);
    pSystem->Init(maxBodies, numBodyMutexes, maxBodyPairs,
            maxContactConstraints, *pMap, *pOvbplf, *pOlpf);
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    optimizeBroadPhase
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_optimizeBroadPhase
  (JNIEnv *, jclass, jlong systemVa) {
    PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    pSystem->OptimizeBroadPhase();
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    setGravity
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_setGravity
  (JNIEnv *, jclass, jlong systemVa, jfloat x, jfloat y, jfloat z) {
    PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    Vec3 gravity(x, y, z);
    pSystem->SetGravity(gravity);
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    setPhysicsSettings
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_setPhysicsSettings
  (JNIEnv *, jclass, jlong systemVa, jlong settingsVa) {
    PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    const PhysicsSettings * const pSettings
            = reinterpret_cast<PhysicsSettings *> (settingsVa);
    pSystem->SetPhysicsSettings(*pSettings);
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    update
 * Signature: (JFIJJ)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_update
  (JNIEnv *, jclass, jlong physicsSystemVa, jfloat deltaTime, jint collisionSteps,
    jlong allocatorVa, jlong jobSystemVa) {
    PhysicsSystem * const pPhysicsSystem
            = reinterpret_cast<PhysicsSystem *> (physicsSystemVa);
    TempAllocatorImpl * const pAllocator
            = reinterpret_cast<TempAllocatorImpl *> (allocatorVa);
    JobSystem * const pJobSystem
            = reinterpret_cast<JobSystemThreadPool *> (jobSystemVa);
    EPhysicsUpdateError result = pPhysicsSystem->Update(
            deltaTime, collisionSteps, pAllocator, pJobSystem);
    return (int) result;
}
