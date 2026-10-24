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
#include "Jolt/Physics/Collision/BroadPhase/BroadPhaseLayerInterfaceMask.h"
#include "auto/com_github_stephengold_joltjni_BroadPhaseLayerInterfaceMask.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_BroadPhaseLayerInterfaceMask
 * Method:    configureLayer
 * Signature: (JIII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BroadPhaseLayerInterfaceMask_configureLayer
  (JNIEnv *, jclass, jlong mapVa, jint bpLayer, jint groupsToInclude,
  jint groupsToExclude) {
    BroadPhaseLayerInterfaceMask * const pMap
            = reinterpret_cast<BroadPhaseLayerInterfaceMask *> (mapVa);
    pMap->ConfigureLayer((BroadPhaseLayer) bpLayer, groupsToInclude, groupsToExclude);
}

/*
 * Class:     com_github_stephengold_joltjni_BroadPhaseLayerInterfaceMask
 * Method:    create
 * Signature: (I)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BroadPhaseLayerInterfaceMask_create
  (JNIEnv *, jclass, jint numBpLayers) {
    BroadPhaseLayerInterfaceMask * const pMap
            = new BroadPhaseLayerInterfaceMask(numBpLayers);
    TRACE_NEW("BroadPhaseLayerInterfaceMask", pMap)
    return reinterpret_cast<jlong> (pMap);
}

/*
 * Class:     com_github_stephengold_joltjni_BroadPhaseLayerInterfaceMask
 * Method:    setBroadPhaseLayerName
 * Signature: (JILjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BroadPhaseLayerInterfaceMask_setBroadPhaseLayerName
  (JNIEnv *pEnv, jclass, jlong mapVa, jint bpLayerIndex, jstring name) {
#if defined(JPH_EXTERNAL_PROFILE) || defined(JPH_PROFILE_ENABLED)
    BroadPhaseLayerInterfaceMask * const pMap
            = reinterpret_cast<BroadPhaseLayerInterfaceMask *> (mapVa);
    jboolean isCopy;
    const char * strName = pEnv->GetStringUTFChars(name, &isCopy);
    const BroadPhaseLayer bpLayer = (BroadPhaseLayer) bpLayerIndex;
    pMap->SetBroadPhaseLayerName(bpLayer, strName);
    pEnv->ReleaseStringUTFChars(name, strName);
#endif
}