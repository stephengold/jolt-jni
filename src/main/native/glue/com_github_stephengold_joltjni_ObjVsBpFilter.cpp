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
#include "auto/com_github_stephengold_joltjni_ObjVsBpFilter.h"

using namespace JPH;

// A configurable filter for collisions between object layers and broadphase layers:
class ObjVsBpFilter final : public ObjectVsBroadPhaseLayerFilter {
private:
    bool **mppEnable;
    uint mNumBpLayers;
    uint mNumObjectLayers;

public:
    // Create a filter with all pairs colliding:
    ObjVsBpFilter(uint numObjectLayers, uint numBpLayers) {
        mNumObjectLayers = numObjectLayers;
        mNumBpLayers = numBpLayers;
        mppEnable = new bool*[numObjectLayers];
        for (ObjectLayer i = 0; i < numObjectLayers; ++i) {
            mppEnable[i] = new bool[numBpLayers];
            for (uint j = 0; j < numBpLayers; ++j) {
                mppEnable[i][j] = true;
            }
        }
    }

    virtual bool ShouldCollide(ObjectLayer inLayer1, BroadPhaseLayer inLayer2) const override {
        bool result = mppEnable[inLayer1][inLayer2.GetValue()];
        return result;
    }

    void DisablePair(ObjectLayer inObjectLayer, BroadPhaseLayer inBpLayer) {
        JPH_ASSERT(inObjectLayer < mNumObjectLayers);
        JPH_ASSERT(inBpLayer.GetValue() < mNumBpLayers);
        mppEnable[inObjectLayer][inBpLayer.GetValue()] = false;
    }

    virtual ~ObjVsBpFilter() {
        for (ObjectLayer i = 0; i < mNumObjectLayers; ++i) {
            delete[] mppEnable[i];
        }
        delete[] mppEnable;
    }
};

/*
 * Class:     com_github_stephengold_joltjni_ObjVsBpFilter
 * Method:    createObjVsBpFilter
 * Signature: (II)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ObjVsBpFilter_createObjVsBpFilter
  (JNIEnv *, jclass, jint numObjectLayers, jint numBpLayers) {
    ObjVsBpFilter * const pFilter
            = new ObjVsBpFilter(numObjectLayers, numBpLayers);
    return reinterpret_cast<jlong> (pFilter);
}

/*
 * Class:     com_github_stephengold_joltjni_ObjVsBpFilter
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ObjVsBpFilter_free
  (JNIEnv *, jclass, jlong filterVa) {
    ObjVsBpFilter * const pFilter
            = reinterpret_cast<ObjVsBpFilter *> (filterVa);
    delete pFilter;
}

/*
 * Class:     com_github_stephengold_joltjni_ObjVsBpFilter
 * Method:    disablePair
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ObjVsBpFilter_disablePair
  (JNIEnv *, jclass, jlong filterVa, jint objLayer, jint bpLayer) {
    ObjVsBpFilter * const pFilter
            = reinterpret_cast<ObjVsBpFilter *> (filterVa);
    pFilter->DisablePair(objLayer, BroadPhaseLayer(bpLayer));
}
