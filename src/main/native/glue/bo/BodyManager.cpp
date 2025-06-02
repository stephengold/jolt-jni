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
#include "Jolt/Physics/Body/BodyManager.h"
#include "auto/com_github_stephengold_joltjni_BodyManager.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_BodyManager
 * Method:    activateBodies
 * Signature: (J[I)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyManager_activateBodies
  (JNIEnv *pEnv, jclass, jlong managerVa, jintArray idArray) {
    BodyManager * const pManager = reinterpret_cast<BodyManager *> (managerVa);
    const jsize numBodies = pEnv->GetArrayLength(idArray);
    BodyID * const pTempArray = new BodyID[numBodies];
    TRACE_NEW("BodyID[]", pTempArray)
    jboolean isCopy;
    jint * const pIds = pEnv->GetIntArrayElements(idArray, &isCopy);
    for (jsize i = 0; i < numBodies; ++i) {
        const jint bodyId = pIds[i];
        const BodyID id(bodyId);
        pTempArray[i] = id;
    }
    pEnv->ReleaseIntArrayElements(idArray, pIds, JNI_ABORT);
    pManager->ActivateBodies(pTempArray, numBodies);
    TRACE_DELETE("BodyID[]", pTempArray)
    delete[] pTempArray;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyManager
 * Method:    addBody
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_BodyManager_addBody
  (JNIEnv *, jclass, jlong managerVa, jlong bodyVa) {
    BodyManager * const pManager = reinterpret_cast<BodyManager *> (managerVa);
    Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    const bool result = pManager->AddBody(pBody);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyManager
 * Method:    allocateBody
 * Signature: (JJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BodyManager_allocateBody
  (JNIEnv *, jclass, jlong managerVa, jlong settingsVa) {
    BodyManager * const pManager = reinterpret_cast<BodyManager *> (managerVa);
    const BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (settingsVa);
    Body * const pResult = pManager->AllocateBody(*pSettings);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyManager
 * Method:    createBodyManager
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BodyManager_createBodyManager
  BODYOF_CREATE_DEFAULT(BodyManager)

/*
 * Class:     com_github_stephengold_joltjni_BodyManager
 * Method:    deactivateBodies
 * Signature: (J[I)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyManager_deactivateBodies
  (JNIEnv *pEnv, jclass, jlong managerVa, jintArray idArray) {
    BodyManager * const pManager = reinterpret_cast<BodyManager *> (managerVa);
    const jsize numBodies = pEnv->GetArrayLength(idArray);
    BodyID * const pTempArray = new BodyID[numBodies];
    TRACE_NEW("BodyID[]", pTempArray)
    jboolean isCopy;
    jint * const pIds = pEnv->GetIntArrayElements(idArray, &isCopy);
    for (jsize i = 0; i < numBodies; ++i) {
        const jint bodyId = pIds[i];
        const BodyID id(bodyId);
        pTempArray[i] = id;
    }
    pEnv->ReleaseIntArrayElements(idArray, pIds, JNI_ABORT);
    pManager->DeactivateBodies(pTempArray, numBodies);
    TRACE_DELETE("BodyID[]", pTempArray)
    delete[] pTempArray;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyManager
 * Method:    destroyBodies
 * Signature: (J[I)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyManager_destroyBodies
  (JNIEnv *pEnv, jclass, jlong managerVa, jintArray idArray) {
    BodyManager * const pManager = reinterpret_cast<BodyManager *> (managerVa);
    const jsize numBodies = pEnv->GetArrayLength(idArray);
    BodyID * const pTempArray = new BodyID[numBodies];
    TRACE_NEW("BodyID[]", pTempArray)
    jboolean isCopy;
    jint * const pIds = pEnv->GetIntArrayElements(idArray, &isCopy);
    for (jsize i = 0; i < numBodies; ++i) {
        const jint bodyId = pIds[i];
        const BodyID id(bodyId);
        pTempArray[i] = id;
    }
    pEnv->ReleaseIntArrayElements(idArray, pIds, JNI_ABORT);
    pManager->DestroyBodies(pTempArray, numBodies);
    TRACE_DELETE("BodyID[]", pTempArray)
    delete[] pTempArray;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyManager
 * Method:    draw
 * Signature: (JJJJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyManager_draw
  (JNIEnv *, jclass, jlong managerVa, jlong drawSettingsVa,
  jlong physicsSettingsVa, jlong rendererVa) {
#ifdef JPH_DEBUG_RENDERER
    BodyManager * const pManager
            = reinterpret_cast<BodyManager *> (managerVa);
    const BodyManager::DrawSettings * const pDrawSettings
            = reinterpret_cast<BodyManager::DrawSettings *> (drawSettingsVa);
    const PhysicsSettings * const pPhysicsSettings
            = reinterpret_cast<PhysicsSettings *> (physicsSettingsVa);
    DebugRenderer * const pRenderer
            = reinterpret_cast<DebugRenderer *> (rendererVa);
    pManager->Draw(*pDrawSettings, *pPhysicsSettings, pRenderer);
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_BodyManager
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyManager_free
  BODYOF_FREE(BodyManager)

/*
 * Class:     com_github_stephengold_joltjni_BodyManager
 * Method:    getBodies
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BodyManager_getBodies
  (JNIEnv *, jclass, jlong managerVa) {
    BodyManager * const pManager = reinterpret_cast<BodyManager *> (managerVa);
    const BodyVector * const pResult = &pManager->GetBodies();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyManager
 * Method:    getBody
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BodyManager_getBody
  (JNIEnv *, jclass, jlong managerVa, jint bodyId) {
    BodyManager * const pManager = reinterpret_cast<BodyManager *> (managerVa);
    const BodyID id(bodyId);
    Body * const pResult = &pManager->GetBody(id);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyManager
 * Method:    getMaxBodies
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_BodyManager_getMaxBodies
  (JNIEnv *, jclass, jlong managerVa) {
    const BodyManager * const pManager
            = reinterpret_cast<BodyManager *> (managerVa);
    const uint result = pManager->GetMaxBodies();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyManager
 * Method:    init
 * Signature: (JIIJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyManager_init
  (JNIEnv *, jclass, jlong managerVa, jint maxBodies, jint numBodyMutexes, jlong mapVa) {
    BodyManager * const pManager = reinterpret_cast<BodyManager *> (managerVa);
    const BroadPhaseLayerInterface * const pMap
            = reinterpret_cast<BroadPhaseLayerInterface *> (mapVa);
    pManager->Init(maxBodies, numBodyMutexes, *pMap);
}