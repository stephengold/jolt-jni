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
#include "Jolt/Physics/Collision/BroadPhase/BroadPhaseLayerInterfaceTable.h"
#include "auto/com_github_stephengold_joltjni_BroadPhaseLayerInterfaceTable.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_BroadPhaseLayerInterfaceTable
 * Method:    createDefault
 * Signature: (II)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BroadPhaseLayerInterfaceTable_createDefault
  (JNIEnv *, jclass, jint numObjLayers, jint numBpLayers) {
    BroadPhaseLayerInterfaceTable * const pMap
            = new BroadPhaseLayerInterfaceTable(numObjLayers, numBpLayers);
    TRACE_NEW("BroadPhaseLayerInterfaceTable", pMap)
    return reinterpret_cast<jlong> (pMap);
}

/*
 * Class:     com_github_stephengold_joltjni_BroadPhaseLayerInterfaceTable
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BroadPhaseLayerInterfaceTable_free
  BODYOF_FREE(BroadPhaseLayerInterfaceTable)

/*
 * Class:     com_github_stephengold_joltjni_BroadPhaseLayerInterfaceTable
 * Method:    mapObjectToBroadPhaseLayer
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BroadPhaseLayerInterfaceTable_mapObjectToBroadPhaseLayer
  (JNIEnv *, jclass, jlong mapVa, jint objLayer, jint bpLayer) {
    BroadPhaseLayerInterfaceTable * const pMap
            = reinterpret_cast<BroadPhaseLayerInterfaceTable *> (mapVa);
    pMap->MapObjectToBroadPhaseLayer(objLayer, (BroadPhaseLayer) bpLayer);
}