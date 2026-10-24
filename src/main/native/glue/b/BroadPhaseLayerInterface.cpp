/*
Copyright (c) 2024-2026 Stephen Gold

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
#include "Jolt/Physics/Collision/BroadPhase/BroadPhaseLayer.h"
#include "auto/com_github_stephengold_joltjni_BroadPhaseLayerInterface.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_BroadPhaseLayerInterface
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BroadPhaseLayerInterface_free
  BODYOF_FREE(BroadPhaseLayerInterface)

/*
 * Class:     com_github_stephengold_joltjni_BroadPhaseLayerInterface
 * Method:    getBroadPhaseLayer
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_BroadPhaseLayerInterface_getBroadPhaseLayer
  (JNIEnv *, jclass, jlong mapVa, jint objLayer) {
    const BroadPhaseLayerInterface * const pMap
            = reinterpret_cast<BroadPhaseLayerInterface *> (mapVa);
    const BroadPhaseLayer bpLayer = pMap->GetBroadPhaseLayer(objLayer);
    const uint8 result = bpLayer.GetValue();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BroadPhaseLayerInterface
 * Method:    getBroadPhaseLayerName
 * Signature: (JI)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_github_stephengold_joltjni_BroadPhaseLayerInterface_getBroadPhaseLayerName
  (JNIEnv *pEnv, jclass, jlong mapVa, jint bpLayerIndex) {
#if defined(JPH_EXTERNAL_PROFILE) || defined(JPH_PROFILE_ENABLED)
    const BroadPhaseLayerInterface * const pMap
            = reinterpret_cast<BroadPhaseLayerInterface *> (mapVa);
    const BroadPhaseLayer bpLayer = (BroadPhaseLayer) bpLayerIndex;
    const char* const name = pMap->GetBroadPhaseLayerName(bpLayer);
    const jstring result = pEnv->NewStringUTF(name);
    JPH_ASSERT(result);
    EXCEPTION_CHECK(pEnv)
    return result;
#else
    return (jstring)NULL;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_BroadPhaseLayerInterface
 * Method:    getNumBroadPhaseLayers
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_BroadPhaseLayerInterface_getNumBroadPhaseLayers
  (JNIEnv *, jclass, jlong mapVa) {
    const BroadPhaseLayerInterface * const pMap
            = reinterpret_cast<BroadPhaseLayerInterface *> (mapVa);
    const uint result = pMap->GetNumBroadPhaseLayers();
    return result;
}