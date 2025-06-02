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
#include "Jolt/Core/JobSystemThreadPool.h"
#include "Jolt/Physics/PhysicsSystem.h"

#include "auto/com_github_stephengold_joltjni_PhysicsSystem.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    addConstraint
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_addConstraint
  (JNIEnv *, jclass, jlong systemVa, jlong constraintVa) {
    PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    Constraint * const pConstraint
            = reinterpret_cast<Constraint *> (constraintVa);
    pSystem->AddConstraint(pConstraint);
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    addStepListener
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_addStepListener
  (JNIEnv *, jclass, jlong systemVa, jlong listenerVa) {
    PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    PhysicsStepListener * const pListener
            = reinterpret_cast<PhysicsStepListener *> (listenerVa);
    pSystem->AddStepListener(pListener);
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    containsConstraint
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_containsConstraint
  (JNIEnv *, jclass, jlong systemVa, jlong constraintVa) {
    const PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    const Constraints constraints = pSystem->GetConstraints();
    for (size_t i = 0; i < constraints.size(); ++i) {
        const RefConst<Constraint> ref = constraints[i];
        const Constraint * const ptr = ref.GetPtr();
        const jlong va = reinterpret_cast<jlong> (ptr);
        if (va == constraintVa) {
            return JNI_TRUE;
        }
    }
    return JNI_FALSE;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    createPhysicsSystem
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_createPhysicsSystem
  BODYOF_CREATE_DEFAULT(PhysicsSystem)

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    destroyAllBodies
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_destroyAllBodies
  (JNIEnv *, jclass, jlong systemVa) {
    PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    BodyIDVector vector;
    pSystem->GetBodies(vector);
    BodyInterface& bi = pSystem->GetBodyInterface();
    const size_t result = vector.size();
    for (size_t i = 0; i < result; ++i) {
        const BodyID id = vector[i];
        if (bi.IsAdded(id)) {
            bi.RemoveBody(id);
        }
        bi.DestroyBody(id);
    }
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    drawBodies
 * Signature: (JJJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_drawBodies
  (JNIEnv *, jclass, jlong systemVa, jlong settingsVa, jlong rendererVa) {
#ifdef JPH_DEBUG_RENDERER
    PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    const BodyManager::DrawSettings * const pSettings
            = reinterpret_cast<BodyManager::DrawSettings *> (settingsVa);
    DebugRenderer * const pRenderer
            = reinterpret_cast<DebugRenderer *> (rendererVa);
    pSystem->DrawBodies(*pSettings, pRenderer);
#elif defined(JPH_DEBUG)
    Trace("PhysicsSystem.drawBodies() has no effect unless JPH_DEBUG_RENDERER is defined.");
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    drawConstraints
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_drawConstraints
  (JNIEnv *, jclass, jlong systemVa, jlong rendererVa) {
#ifdef JPH_DEBUG_RENDERER
    PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    DebugRenderer * const pRenderer
            = reinterpret_cast<DebugRenderer *> (rendererVa);
    pSystem->DrawConstraints(pRenderer);
#elif defined(JPH_DEBUG)
    Trace("PhysicsSystem.drawConstraints() has no effect unless JPH_DEBUG_RENDERER is defined.");
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    drawConstraintLimits
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_drawConstraintLimits
  (JNIEnv *, jclass, jlong systemVa, jlong rendererVa) {
#ifdef JPH_DEBUG_RENDERER
    PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    DebugRenderer * const pRenderer
            = reinterpret_cast<DebugRenderer *> (rendererVa);
    pSystem->DrawConstraintLimits(pRenderer);
#elif defined(JPH_DEBUG)
    Trace("PhysicsSystem.drawConstraintLimits() has no effect unless JPH_DEBUG_RENDERER is defined.");
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    drawConstraintReferenceFrame
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_drawConstraintReferenceFrame
  (JNIEnv *, jclass, jlong systemVa, jlong rendererVa) {
#ifdef JPH_DEBUG_RENDERER
    PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    DebugRenderer * const pRenderer
            = reinterpret_cast<DebugRenderer *> (rendererVa);
    pSystem->DrawConstraintReferenceFrame(pRenderer);
#elif defined(JPH_DEBUG)
    Trace("PhysicsSystem.drawConstraintReferenceFrame() has no effect unless JPH_DEBUG_RENDERER is defined.");
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_free
  BODYOF_FREE(PhysicsSystem)

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    getActiveBodies
 * Signature: (JIJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_getActiveBodies
  (JNIEnv *, jclass, jlong systemVa, jint ordinal, jlong vectorVa) {
    const PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    const EBodyType bodyType = (EBodyType)ordinal;
    BodyIDVector * const pVector = reinterpret_cast<BodyIDVector *> (vectorVa);
    pSystem->GetActiveBodies(bodyType, *pVector);
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    getBodies
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_getBodies
  (JNIEnv *, jclass, jlong systemVa, jlong vectorVa) {
    const PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    BodyIDVector * const pVector = reinterpret_cast<BodyIDVector *> (vectorVa);
    pSystem->GetBodies(*pVector);
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    getBodyActivationListener
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_getBodyActivationListener
  (JNIEnv *, jclass, jlong systemVa) {
    const PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    BodyActivationListener * const pResult
            = pSystem->GetBodyActivationListener();
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
    const BodyInterface& result = pSystem->GetBodyInterface();
    return reinterpret_cast<jlong> (&result);
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    getBodyInterfaceNoLock
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_getBodyInterfaceNoLock
  (JNIEnv *, jclass, jlong systemVa) {
    PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    const BodyInterface& result = pSystem->GetBodyInterfaceNoLock();
    return reinterpret_cast<jlong> (&result);
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    getBodyLockInterface
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_getBodyLockInterface
  (JNIEnv *, jclass, jlong systemVa) {
    const PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    const BodyLockInterface& result = pSystem->GetBodyLockInterface();
    return reinterpret_cast<jlong> (&result);
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    getBodyLockInterfaceNoLock
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_getBodyLockInterfaceNoLock
  (JNIEnv *, jclass, jlong systemVa) {
    const PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    const BodyLockInterfaceNoLock& result
            = pSystem->GetBodyLockInterfaceNoLock();
    return reinterpret_cast<jlong> (&result);
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    getBounds
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_getBounds
  (JNIEnv *, jclass, jlong systemVa) {
    const PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    AABox * const pResult = new AABox();
    TRACE_NEW("AABox", pResult)
    *pResult = pSystem->GetBounds();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    getBroadPhaseQuery
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_getBroadPhaseQuery
  (JNIEnv *, jclass, jlong systemVa) {
    const PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    const BroadPhaseQuery * const pResult = &pSystem->GetBroadPhaseQuery();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    getCombineFriction
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_getCombineFriction
  (JNIEnv *, jclass, jlong systemVa) {
    const PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    ContactConstraintManager::CombineFunction pResult
            = pSystem->GetCombineFriction();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    getCombineRestitution
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_getCombineRestitution
  (JNIEnv *, jclass, jlong systemVa) {
    const PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    ContactConstraintManager::CombineFunction pResult
            = pSystem->GetCombineRestitution();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    getConstraints
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_getConstraints
  (JNIEnv *, jclass, jlong systemVa) {
    const PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    Constraints * const pResult = new Constraints();
    TRACE_NEW("Constraints", pResult)
    *pResult = pSystem->GetConstraints();
    return reinterpret_cast<jlong> (pResult);
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
    const Vec3 gravity = pSystem->GetGravity();
    const float result = gravity.GetX();
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
    const Vec3 gravity = pSystem->GetGravity();
    const float result = gravity.GetY();
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
    const Vec3 gravity = pSystem->GetGravity();
    const float result = gravity.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    getNarrowPhaseQuery
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_getNarrowPhaseQuery
  (JNIEnv *, jclass, jlong systemVa) {
    const PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    const NarrowPhaseQuery * const pResult = &pSystem->GetNarrowPhaseQuery();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    getNarrowPhaseQueryNoLock
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_getNarrowPhaseQueryNoLock
  (JNIEnv *, jclass, jlong systemVa) {
    const PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    const NarrowPhaseQuery * const pResult
            = &pSystem->GetNarrowPhaseQueryNoLock();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    getMaxBodies
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_getMaxBodies
  (JNIEnv *, jclass, jlong systemVa) {
    const PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    const uint result = pSystem->GetMaxBodies();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    getNumActiveBodies
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_getNumActiveBodies
  (JNIEnv *, jclass, jlong systemVa, jint typeOrdinal) {
    const PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    const EBodyType bodyType = (EBodyType)typeOrdinal;
    const uint32 result = pSystem->GetNumActiveBodies(bodyType);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    getNumBodies
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_getNumBodies
  (JNIEnv *, jclass, jlong systemVa) {
    const PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    const uint result = pSystem->GetNumBodies();
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
    TRACE_NEW("PhysicsSettings", pSettings)
    *pSettings = pSystem->GetPhysicsSettings();
    return reinterpret_cast<jlong> (pSettings);
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    init
 * Signature: (JIIIIJJJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_init
  (JNIEnv *, jclass, jlong systemVa, jint maxBodies, jint numBodyMutexes,
  jint maxBodyPairs, jint maxContactConstraints, jlong mapVa, jlong ovbFilterVa,
  jlong ovoFilterVa) {
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
 * Method:    removeAllBodies
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_removeAllBodies
  (JNIEnv *, jclass, jlong systemVa) {
    PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    BodyIDVector vector;
    pSystem->GetBodies(vector);
    BodyInterface& bi = pSystem->GetBodyInterface();
    const size_t numBodies = vector.size();
    jint result = 0;
    for (size_t i = 0; i < numBodies; ++i) {
        const BodyID id = vector[i];
        if (bi.IsAdded(id)) {
            bi.RemoveBody(id);
            ++result;
        }
    }
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    removeAllConstraints
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_removeAllConstraints
  (JNIEnv *, jclass, jlong systemVa) {
    PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    const Array<Ref<Constraint>>& refArray = pSystem->GetConstraints();
    const size_t numConstraints = refArray.size();
    for (size_t i = 0; i < numConstraints; ++i) {
        Constraint * pConstraint = refArray[i].GetPtr();
        pSystem->RemoveConstraint(pConstraint);
    }
    return numConstraints;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    removeConstraint
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_removeConstraint
  (JNIEnv *, jclass, jlong systemVa, jlong constraintVa) {
    PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    Constraint * const pConstraint
            = reinterpret_cast<Constraint *> (constraintVa);
    pSystem->RemoveConstraint(pConstraint);
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    removeStepListener
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_removeStepListener
  (JNIEnv *, jclass, jlong systemVa, jlong listenerVa) {
    PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    PhysicsStepListener * const pListener
            = reinterpret_cast<PhysicsStepListener *> (listenerVa);
    pSystem->RemoveStepListener(pListener);
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    restoreState
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_restoreState
  (JNIEnv *, jclass, jlong systemVa, jlong recorderVa) {
    PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    StateRecorder * const pRecorder
            = reinterpret_cast<StateRecorder *> (recorderVa);
    const bool result = pSystem->RestoreState(*pRecorder);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    saveState
 * Signature: (JJIJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_saveState
  (JNIEnv *, jclass, jlong systemVa, jlong recorderVa, jint bitmask, jlong filterVa) {
    const PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    StateRecorder * const pRecorder
            = reinterpret_cast<StateRecorder *> (recorderVa);
    const EStateRecorderState srs = (EStateRecorderState) bitmask;
    const StateRecorderFilter * const pFilter
            = reinterpret_cast<StateRecorderFilter *> (filterVa);
    pSystem->SaveState(*pRecorder, srs, pFilter);
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    setBodyActivationListener
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_setBodyActivationListener
  (JNIEnv *, jclass, jlong systemVa, jlong listenerVa) {
    PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    BodyActivationListener * const pListener
            = reinterpret_cast<BodyActivationListener *> (listenerVa);
    pSystem->SetBodyActivationListener(pListener);
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    setCombineFriction
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_setCombineFriction
  (JNIEnv *, jclass, jlong systemVa, jlong functionVa) {
    PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    const ContactConstraintManager::CombineFunction pFunction
            = reinterpret_cast<ContactConstraintManager::CombineFunction> (functionVa);
    pSystem->SetCombineFriction(pFunction);
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    setCombineRestitution
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_setCombineRestitution
  (JNIEnv *, jclass, jlong systemVa, jlong functionVa) {
    PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    const ContactConstraintManager::CombineFunction pFunction
            = reinterpret_cast<ContactConstraintManager::CombineFunction> (functionVa);
    pSystem->SetCombineRestitution(pFunction);
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    setContactListener
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_setContactListener
  (JNIEnv *, jclass, jlong systemVa, jlong listenerVa) {
    PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    ContactListener * const pListener
            = reinterpret_cast<ContactListener *> (listenerVa);
    pSystem->SetContactListener(pListener);
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
    const Vec3 gravity(x, y, z);
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
 * Method:    setSoftBodyContactListener
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_setSoftBodyContactListener
  (JNIEnv *, jclass, jlong systemVa, jlong listenerVa) {
    PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    SoftBodyContactListener * const pListener
            = reinterpret_cast<SoftBodyContactListener *> (listenerVa);
    pSystem->SetSoftBodyContactListener(pListener);
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsSystem
 * Method:    update
 * Signature: (JFIJJ)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_PhysicsSystem_update
  (JNIEnv *, jclass, jlong physicsSystemVa, jfloat deltaTime,
  jint collisionSteps, jlong allocatorVa, jlong jobSystemVa) {
    PhysicsSystem * const pPhysicsSystem
            = reinterpret_cast<PhysicsSystem *> (physicsSystemVa);
    TempAllocatorImpl * const pAllocator
            = reinterpret_cast<TempAllocatorImpl *> (allocatorVa);
    JobSystem * const pJobSystem
            = reinterpret_cast<JobSystemThreadPool *> (jobSystemVa);
    const EPhysicsUpdateError result = pPhysicsSystem->Update(
            deltaTime, collisionSteps, pAllocator, pJobSystem);
    return (jint) result;
}