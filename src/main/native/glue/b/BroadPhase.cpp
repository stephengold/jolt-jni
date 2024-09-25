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
#include <Jolt/Physics/Collision/BroadPhase/BroadPhase.h>
#include "auto/com_github_stephengold_joltjni_BroadPhase.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_BroadPhase
 * Method:    addBodiesAbort
 * Signature: (J[IIJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BroadPhase_addBodiesAbort
  (JNIEnv *pEnv, jclass, jlong phaseVa, jintArray iasns, jint numBodies,
  jlong addState) {
    BroadPhase * const pPhase = reinterpret_cast<BroadPhase *> (phaseVa);
    BodyID * const pTempArray = new BodyID[numBodies];
    TRACE_NEW("BodyID[]", pTempArray)
    jboolean isCopy;
    jint * const pInts = pEnv->GetIntArrayElements(iasns, &isCopy);
    for (int i = 0; i < numBodies; ++i) {
        pTempArray[i] = BodyID(pInts[i]);
    }
    pEnv->ReleaseIntArrayElements(iasns, pInts, JNI_ABORT);
    void * const pState = reinterpret_cast<void *> (addState);
    pPhase->AddBodiesAbort(pTempArray, numBodies, pState);
    TRACE_DELETE("BodyID[]", pTempArray)
    delete[] pTempArray;
}

/*
 * Class:     com_github_stephengold_joltjni_BroadPhase
 * Method:    addBodiesFinalize
 * Signature: (J[IIJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BroadPhase_addBodiesFinalize
  (JNIEnv *pEnv, jclass, jlong phaseVa, jintArray iasns, jint numBodies,
  jlong addState) {
    BroadPhase * const pPhase = reinterpret_cast<BroadPhase *> (phaseVa);
    BodyID * const pTempArray = new BodyID[numBodies];
    TRACE_NEW("BodyID[]", pTempArray)
    jboolean isCopy;
    jint * const pInts = pEnv->GetIntArrayElements(iasns, &isCopy);
    for (int i = 0; i < numBodies; ++i) {
        pTempArray[i] = BodyID(pInts[i]);
    }
    pEnv->ReleaseIntArrayElements(iasns, pInts, JNI_ABORT);
    void * const pState = reinterpret_cast<void *> (addState);
    pPhase->AddBodiesAbort(pTempArray, numBodies, pState);
    TRACE_DELETE("BodyID[]", pTempArray)
    delete[] pTempArray;
}

/*
 * Class:     com_github_stephengold_joltjni_BroadPhase
 * Method:    addBodiesPrepare
 * Signature: (J[II)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BroadPhase_addBodiesPrepare
  (JNIEnv *pEnv, jclass, jlong phaseVa, jintArray iasns, jint numBodies) {
    BroadPhase * const pPhase = reinterpret_cast<BroadPhase *> (phaseVa);
    BodyID * const pTempArray = new BodyID[numBodies];
    TRACE_NEW("BodyID[]", pTempArray)
    jboolean isCopy;
    jint * const pInts = pEnv->GetIntArrayElements(iasns, &isCopy);
    for (int i = 0; i < numBodies; ++i) {
        pTempArray[i] = BodyID(pInts[i]);
    }
    void * const pResult = pPhase->AddBodiesPrepare(pTempArray, numBodies);
    for (int i = 0; i < numBodies; ++i) {
        pInts[i] = pTempArray[i].GetIndexAndSequenceNumber();
    }
    pEnv->ReleaseIntArrayElements(iasns, pInts, 0);
    TRACE_DELETE("BodyID[]", pTempArray)
    delete[] pTempArray;
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_BroadPhase
 * Method:    init
 * Signature: (JJJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BroadPhase_init
  (JNIEnv *, jclass, jlong phaseVa, jlong managerVa, jlong mapVa) {
    BroadPhase * const pPhase = reinterpret_cast<BroadPhase *> (phaseVa);
    BodyManager * const pManager = reinterpret_cast<BodyManager *> (managerVa);
    const BroadPhaseLayerInterface * const pMap
            = reinterpret_cast<BroadPhaseLayerInterface *> (mapVa);
    pPhase->Init(pManager, *pMap);
}

/*
 * Class:     com_github_stephengold_joltjni_BroadPhase
 * Method:    optimize
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BroadPhase_optimize
  (JNIEnv *, jclass, jlong phaseVa) {
    BroadPhase * const pPhase = reinterpret_cast<BroadPhase *> (phaseVa);
    pPhase->Optimize();
}

/*
 * Class:     com_github_stephengold_joltjni_BroadPhase
 * Method:    notifyBodiesAabbChanged
 * Signature: (J[IIZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BroadPhase_notifyBodiesAabbChanged
  (JNIEnv *pEnv, jclass, jlong phaseVa, jintArray iasns, jint numBodies,
  jboolean takeLock) {
    BroadPhase * const pPhase = reinterpret_cast<BroadPhase *> (phaseVa);
    BodyID * const pTempArray = new BodyID[numBodies];
    TRACE_NEW("BodyID[]", pTempArray)
    jboolean isCopy;
    jint * const pInts = pEnv->GetIntArrayElements(iasns, &isCopy);
    for (int i = 0; i < numBodies; ++i) {
        pTempArray[i] = BodyID(pInts[i]);
    }
    pEnv->ReleaseIntArrayElements(iasns, pInts, JNI_ABORT);
    pPhase->NotifyBodiesAABBChanged(pTempArray, numBodies, takeLock);
    TRACE_DELETE("BodyID[]", pTempArray)
    delete[] pTempArray;
}

/*
 * Class:     com_github_stephengold_joltjni_BroadPhase
 * Method:    removeBodies
 * Signature: (J[II)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BroadPhase_removeBodies
  (JNIEnv *pEnv, jclass, jlong phaseVa, jintArray iasns, jint numBodies) {
    BroadPhase * const pPhase = reinterpret_cast<BroadPhase *> (phaseVa);
    BodyID * const pTempArray = new BodyID[numBodies];
    TRACE_NEW("BodyID[]", pTempArray)
    jboolean isCopy;
    jint * const pInts = pEnv->GetIntArrayElements(iasns, &isCopy);
    for (int i = 0; i < numBodies; ++i) {
        pTempArray[i] = BodyID(pInts[i]);
    }
    pPhase->RemoveBodies(pTempArray, numBodies);
    for (int i = 0; i < numBodies; ++i) {
        pInts[i] = pTempArray[i].GetIndexAndSequenceNumber();
    }
    pEnv->ReleaseIntArrayElements(iasns, pInts, 0);
    TRACE_DELETE("BodyID[]", pTempArray)
    delete[] pTempArray;
}