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
#include "Jolt/Physics/Collision/BroadPhase/BroadPhase.h"
#include "auto/com_github_stephengold_joltjni_BroadPhase.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_BroadPhase
 * Method:    addBodiesAbort
 * Signature: (JJIJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BroadPhase_addBodiesAbort
  (JNIEnv *, jclass, jlong phaseVa, jlong arrayVa, jint numBodies,
  jlong addState) {
    BroadPhase * const pPhase = reinterpret_cast<BroadPhase *> (phaseVa);
    BodyID * const pArray = reinterpret_cast<BodyID *> (arrayVa);
    BroadPhase::AddState const handle
            = reinterpret_cast<BroadPhase::AddState> (addState);
    pPhase->AddBodiesAbort(pArray, numBodies, handle);
}

/*
 * Class:     com_github_stephengold_joltjni_BroadPhase
 * Method:    addBodiesFinalize
 * Signature: (JJIJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BroadPhase_addBodiesFinalize
  (JNIEnv *, jclass, jlong phaseVa, jlong arrayVa, jint numBodies,
  jlong addState) {
    BroadPhase * const pPhase = reinterpret_cast<BroadPhase *> (phaseVa);
    BodyID * const pArray = reinterpret_cast<BodyID *> (arrayVa);
    BroadPhase::AddState const handle
            = reinterpret_cast<BroadPhase::AddState> (addState);
    pPhase->AddBodiesFinalize(pArray, numBodies, handle);
}

/*
 * Class:     com_github_stephengold_joltjni_BroadPhase
 * Method:    addBodiesPrepare
 * Signature: (JJI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BroadPhase_addBodiesPrepare
  (JNIEnv *, jclass, jlong phaseVa, jlong arrayVa, jint numBodies) {
    BroadPhase * const pPhase = reinterpret_cast<BroadPhase *> (phaseVa);
    BodyID * const pArray = reinterpret_cast<BodyID *> (arrayVa);
    BroadPhase::AddState const handle
            = pPhase->AddBodiesPrepare(pArray, numBodies);
    return reinterpret_cast<jlong> (handle);
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
 * Signature: (JJIZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BroadPhase_notifyBodiesAabbChanged
  (JNIEnv *, jclass, jlong phaseVa, jlong arrayVa, jint numBodies,
  jboolean takeLock) {
    BroadPhase * const pPhase = reinterpret_cast<BroadPhase *> (phaseVa);
    BodyID * const pArray = reinterpret_cast<BodyID *> (arrayVa);
    pPhase->NotifyBodiesAABBChanged(pArray, numBodies, takeLock);
}

/*
 * Class:     com_github_stephengold_joltjni_BroadPhase
 * Method:    removeBodies
 * Signature: (JJI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BroadPhase_removeBodies
  (JNIEnv *, jclass, jlong phaseVa, jlong arrayVa, jint numBodies) {
    BroadPhase * const pPhase = reinterpret_cast<BroadPhase *> (phaseVa);
    BodyID * const pArray = reinterpret_cast<BodyID *> (arrayVa);
    pPhase->RemoveBodies(pArray, numBodies);
}