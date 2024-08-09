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
#include <Jolt/Physics/Collision/BroadPhase/BroadPhaseLayer.h>
#include "auto/com_github_stephengold_joltjni_MapObj2Bp.h"
#include "glue/glue.h"

using namespace JPH;

// A configurable mapping from object layers to broadphase layers:
class MapObj2Bp final : public BroadPhaseLayerInterface {
private:
    BroadPhaseLayer *mpObjToBp;
    uint mNumBpLayers;
    uint mNumObjLayers;

public:
    MapObj2Bp(uint numObjLayers, uint numBpLayers) {
        mNumObjLayers = numObjLayers;
        mNumBpLayers = numBpLayers;
        mpObjToBp = new BroadPhaseLayer[numObjLayers];
        TRACE_NEW("BroadPhaseLayer[]", mpObjToBp)
        for (ObjectLayer i = 0; i < numObjLayers; ++i) {
            mpObjToBp[i] = BroadPhaseLayer(-1);
        }
    }

    void Add(ObjectLayer inObjLayer, BroadPhaseLayer inBpLayer) {
        JPH_ASSERT(inObjLayer < mNumObjLayers);
        JPH_ASSERT(inBpLayer.GetValue() < mNumBpLayers);
        mpObjToBp[inObjLayer] = inBpLayer;
    }

    BroadPhaseLayer GetBroadPhaseLayer(ObjectLayer inLayer) const override {
	JPH_ASSERT(inLayer < mNumObjLayers);
        BroadPhaseLayer result = mpObjToBp[inLayer];
        return result;
    }

    uint GetNumBroadPhaseLayers() const override {
        return mNumBpLayers;
    }

    virtual ~MapObj2Bp() {
        TRACE_DELETE("BroadPhaseLayer[]", mpObjToBp)
        delete[] mpObjToBp;
    }
};

/*
 * Class:     com_github_stephengold_joltjni_MapObj2Bp
 * Method:    add
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MapObj2Bp_add
  (JNIEnv *, jclass, jlong mapVa, jint objLayer, jint bpLayer) {
    MapObj2Bp * const pMap = reinterpret_cast<MapObj2Bp *> (mapVa);
    pMap->Add(objLayer, (BroadPhaseLayer) bpLayer);
}

/*
 * Class:     com_github_stephengold_joltjni_MapObj2Bp
 * Method:    createMapObj2Bp
 * Signature: (II)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_MapObj2Bp_createMapObj2Bp
  (JNIEnv *, jclass, jint numObjLayers, jint numBpLayers) {
    MapObj2Bp * const pMap = new MapObj2Bp(numObjLayers, numBpLayers);
    TRACE_NEW("MapObj2Bp", pMap)
    return reinterpret_cast<jlong> (pMap);
}

/*
 * Class:     com_github_stephengold_joltjni_MapObj2Bp
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MapObj2Bp_free
  (JNIEnv *, jclass, jlong mapVa) {
    MapObj2Bp * const pMap = reinterpret_cast<MapObj2Bp *> (mapVa);
    TRACE_DELETE("MapObj2Bp", pMap)
    delete pMap;
}
