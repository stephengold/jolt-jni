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
#include "Jolt/Physics/Collision/ObjectLayer.h"
#include "auto/com_github_stephengold_joltjni_ObjVsObjFilter.h"
#include "glue/glue.h"

using namespace JPH;

// A configurable filter for collisions between object layers:
class ObjVsObjFilter final : public ObjectLayerPairFilter {
private:
    bool **mppEnable;
    uint mNumObjectLayers;

public:
    // Create a filter with all pairs colliding:
    ObjVsObjFilter(uint numObjectLayers) {
        mNumObjectLayers = numObjectLayers;
        mppEnable = new bool*[numObjectLayers];
        TRACE_NEW("bool*[]", mppEnable)
        for (ObjectLayer i = 0; i < numObjectLayers; ++i) {
            mppEnable[i] = new bool[numObjectLayers];
            TRACE_NEW("bool[]", mppEnable[i])
            for (ObjectLayer j = 0; j < numObjectLayers; ++j) {
                mppEnable[i][j] = true;
            }
        }
    }

    virtual bool ShouldCollide(ObjectLayer inLayer1, ObjectLayer inLayer2) const override {
        const bool result = mppEnable[inLayer1][inLayer2];
        return result;
    }

    void DisableLayer(ObjectLayer inLayer) {
        JPH_ASSERT(inLayer < mNumObjectLayers);
        for (ObjectLayer j = 0; j < mNumObjectLayers; ++j) {
            mppEnable[inLayer][j] = false;
            mppEnable[j][inLayer] = false;
        }
    }

    void DisablePair(ObjectLayer inLayer1, ObjectLayer inLayer2) {
        JPH_ASSERT(inLayer1 < mNumObjectLayers);
        JPH_ASSERT(inLayer2 < mNumObjectLayers);
        mppEnable[inLayer1][inLayer2] = false;
        mppEnable[inLayer2][inLayer1] = false;
    }

    virtual ~ObjVsObjFilter() {
        for (ObjectLayer i = 0; i < mNumObjectLayers; ++i) {
            TRACE_DELETE("bool[]", mppEnable[i])
            delete[] mppEnable[i];
        }
        TRACE_DELETE("bool*[]", mppEnable)
        delete[] mppEnable;
    }
};

/*
 * Class:     com_github_stephengold_joltjni_ObjVsObjFilter
 * Method:    createObjVsObjFilter
 * Signature: (I)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ObjVsObjFilter_createObjVsObjFilter
  (JNIEnv *, jclass, jint numObjectLayers) {
    ObjVsObjFilter * const pFilter = new ObjVsObjFilter(numObjectLayers);
    TRACE_NEW("ObjVsObjFilter", pFilter)
    return reinterpret_cast<jlong> (pFilter);
}

/*
 * Class:     com_github_stephengold_joltjni_ObjVsObjFilter
 * Method:    disableLayer
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ObjVsObjFilter_disableLayer
  (JNIEnv *, jclass, jlong filterVa, jint layer) {
    ObjVsObjFilter * const pFilter
            = reinterpret_cast<ObjVsObjFilter *> (filterVa);
    pFilter->DisableLayer(layer);
}

/*
 * Class:     com_github_stephengold_joltjni_ObjVsObjFilter
 * Method:    disablePair
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ObjVsObjFilter_disablePair
  (JNIEnv *, jclass, jlong filterVa, jint layer1, jint layer2) {
    ObjVsObjFilter * const pFilter
            = reinterpret_cast<ObjVsObjFilter *> (filterVa);
    pFilter->DisablePair(layer1, layer2);
}

/*
 * Class:     com_github_stephengold_joltjni_ObjVsObjFilter
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ObjVsObjFilter_free
  (JNIEnv *, jclass, jlong filterVa) {
    ObjVsObjFilter * const pFilter
            = reinterpret_cast<ObjVsObjFilter *> (filterVa);
    TRACE_DELETE("ObjVsObjFilter", pFilter)
    delete pFilter;
}