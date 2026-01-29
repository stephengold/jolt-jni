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
#include "Jolt/Physics/Hair/HairSettings.h"

#include "auto/com_github_stephengold_joltjni_HairSettings.h"
#include "auto/com_github_stephengold_joltjni_HairSettingsRef.h"
#include "glue/glue.h"

using namespace JPH;

IMPLEMENT_REF(HairSettings,
  Java_com_github_stephengold_joltjni_HairSettingsRef_copy,
  Java_com_github_stephengold_joltjni_HairSettingsRef_createDefault,
  Java_com_github_stephengold_joltjni_HairSettingsRef_free,
  Java_com_github_stephengold_joltjni_HairSettingsRef_getPtr,
  Java_com_github_stephengold_joltjni_HairSettingsRef_toRefC)

/*
 * Class:     com_github_stephengold_joltjni_HairSettings
 * Method:    addMaterial
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairSettings_addMaterial
  (JNIEnv *, jclass, jlong settingsVa, jlong materialVa) {
    HairSettings * const pSettings
            = reinterpret_cast<HairSettings *> (settingsVa);
    const HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    pSettings->mMaterials.push_back(*pMaterial);
}

/*
 * Class:     com_github_stephengold_joltjni_HairSettings
 * Method:    countMaterials
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_HairSettings_countMaterials
  (JNIEnv *, jclass, jlong settingsVa) {
    const HairSettings * const pSettings
            = reinterpret_cast<HairSettings *> (settingsVa);
    const jlong result = pSettings->mMaterials.size();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HairSettings
 * Method:    countRenderStrands
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_HairSettings_countRenderStrands
  (JNIEnv *, jclass, jlong settingsVa) {
    const HairSettings * const pSettings
            = reinterpret_cast<HairSettings *> (settingsVa);
    const jlong result = pSettings->mRenderStrands.size();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HairSettings
 * Method:    countScalpTriangles
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_HairSettings_countScalpTriangles
  (JNIEnv *, jclass, jlong settingsVa) {
    const HairSettings * const pSettings
            = reinterpret_cast<HairSettings *> (settingsVa);
    const jlong result = pSettings->mScalpTriangles.size();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HairSettings
 * Method:    countScalpVertices
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_HairSettings_countScalpVertices
  (JNIEnv *, jclass, jlong settingsVa) {
    const HairSettings * const pSettings
            = reinterpret_cast<HairSettings *> (settingsVa);
    const jlong result = pSettings->mScalpVertices.size();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HairSettings
 * Method:    countSimStrands
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_HairSettings_countSimStrands
  (JNIEnv *, jclass, jlong settingsVa) {
    const HairSettings * const pSettings
            = reinterpret_cast<HairSettings *> (settingsVa);
    const jlong result = pSettings->mSimStrands.size();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HairSettings
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_HairSettings_createCopy
  BODYOF_CREATE_COPY_TARGET(HairSettings)

/*
 * Class:     com_github_stephengold_joltjni_HairSettings
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_HairSettings_createDefault
  BODYOF_CREATE_DEFAULT_TARGET(HairSettings)

/*
 * Class:     com_github_stephengold_joltjni_HairSettings
 * Method:    getInitialGravity
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairSettings_getInitialGravity
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeFloats) {
    const HairSettings * const pSettings
            = reinterpret_cast<HairSettings *> (settingsVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& result = pSettings->mInitialGravity;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_HairSettings
 * Method:    getNumIterationsPerSecond
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_HairSettings_getNumIterationsPerSecond
  (JNIEnv *, jclass, jlong settingsVa) {
    const HairSettings * const pSettings
            = reinterpret_cast<HairSettings *> (settingsVa);
    const uint32 result = pSettings->mNumIterationsPerSecond;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HairSettings
 * Method:    getRefCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_HairSettings_getRefCount
  (JNIEnv *, jclass, jlong settingsVa) {
    const HairSettings * const pSettings
            = reinterpret_cast<HairSettings *> (settingsVa);
    const uint32 result = pSettings->GetRefCount();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HairSettings
 * Method:    getRenderStrand
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_HairSettings_getRenderStrand
  (JNIEnv *, jclass, jlong settingsVa, jint index) {
    HairSettings * const pSettings
            = reinterpret_cast<HairSettings *> (settingsVa);
    HairSettings::RStrand& result = pSettings->mRenderStrands.at(index);
    return reinterpret_cast<jlong> (&result);
}

/*
 * Class:     com_github_stephengold_joltjni_HairSettings
 * Method:    getScalpNumSkinWeightsPerVertex
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_HairSettings_getScalpNumSkinWeightsPerVertex
  (JNIEnv *, jclass, jlong settingsVa) {
    const HairSettings * const pSettings
            = reinterpret_cast<HairSettings *> (settingsVa);
    const uint result = pSettings->mScalpNumSkinWeightsPerVertex;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HairSettings
 * Method:    getScalpTriangle
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_HairSettings_getScalpTriangle
  (JNIEnv *, jclass, jlong settingsVa, jint index) {
    HairSettings * const pSettings
            = reinterpret_cast<HairSettings *> (settingsVa);
    IndexedTriangleNoMaterial& result = pSettings->mScalpTriangles.at(index);
    return reinterpret_cast<jlong> (&result);
}

/*
 * Class:     com_github_stephengold_joltjni_HairSettings
 * Method:    getSimulationBounds
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_HairSettings_getSimulationBounds
  (JNIEnv *, jclass, jlong settingsVa) {
    HairSettings * const pSettings
            = reinterpret_cast<HairSettings *> (settingsVa);
    AABox * pResult = &pSettings->mSimulationBounds;
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_HairSettings
 * Method:    init
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HairSettings_init
  (JNIEnv *, jclass, jlong settingsVa) {
    HairSettings * const pSettings
            = reinterpret_cast<HairSettings *> (settingsVa);
    float result;
    pSettings->Init(result);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HairSettings
 * Method:    initCompute
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairSettings_initCompute
  (JNIEnv *, jclass, jlong settingsVa, jlong systemVa) {
    HairSettings * const pSettings
            = reinterpret_cast<HairSettings *> (settingsVa);
    ComputeSystem * const pSystem
            = reinterpret_cast<ComputeSystem *> (systemVa);
    pSettings->InitCompute(pSystem);
}

/*
 * Class:     com_github_stephengold_joltjni_HairSettings
 * Method:    initRenderAndSimulationStrands
 * Signature: (JJJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairSettings_initRenderAndSimulationStrands
  (JNIEnv *, jclass, jlong settingsVa, jlong verticesVa, jlong strandsVa) {
    HairSettings * const pSettings
            = reinterpret_cast<HairSettings *> (settingsVa);
    const Array<HairSettings::SVertex> * const pVertices
            = reinterpret_cast<Array<HairSettings::SVertex> *> (verticesVa);
    const Array<HairSettings::SStrand> * const pStrands
            = reinterpret_cast<Array<HairSettings::SStrand> *> (strandsVa);
    pSettings->InitRenderAndSimulationStrands(*pVertices, *pStrands);
}

/*
 * Class:     com_github_stephengold_joltjni_HairSettings
 * Method:    restoreBinaryState
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairSettings_restoreBinaryState
  (JNIEnv *, jclass, jlong settingsVa, jlong streamInVa) {
    HairSettings * const pSettings
            = reinterpret_cast<HairSettings *> (settingsVa);
    StreamIn * const pStream = reinterpret_cast<StreamIn *> (streamInVa);
    pSettings->RestoreBinaryState(*pStream);
}

/*
 * Class:     com_github_stephengold_joltjni_HairSettings
 * Method:    saveBinaryState
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairSettings_saveBinaryState
  (JNIEnv *, jclass, jlong settingsVa, jlong streamOutVa) {
    const HairSettings * const pSettings
            = reinterpret_cast<HairSettings *> (settingsVa);
    StreamOut * const pStream = reinterpret_cast<StreamOut *> (streamOutVa);
    pSettings->SaveBinaryState(*pStream);
}

/*
 * Class:     com_github_stephengold_joltjni_HairSettings
 * Method:    setEmbedded
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairSettings_setEmbedded
  (JNIEnv *, jclass, jlong settingsVa) {
    HairSettings * const pSettings
            = reinterpret_cast<HairSettings *> (settingsVa);
    pSettings->SetEmbedded();
}

/*
 * Class:     com_github_stephengold_joltjni_HairSettings
 * Method:    setInitialGravity
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairSettings_setInitialGravity
  (JNIEnv *, jclass, jlong settingsVa, jfloat gx, jfloat gy, jfloat gz) {
    HairSettings * const pSettings
            = reinterpret_cast<HairSettings *> (settingsVa);
    const Vec3 gravity(gx, gy, gz);
    pSettings->mInitialGravity = gravity;
}

/*
 * Class:     com_github_stephengold_joltjni_HairSettings
 * Method:    setNumIterationsPerSecond
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairSettings_setNumIterationsPerSecond
  (JNIEnv *, jclass, jlong settingsVa, jint numIterations) {
    HairSettings * const pSettings
            = reinterpret_cast<HairSettings *> (settingsVa);
    pSettings->mNumIterationsPerSecond = numIterations;
}

/*
 * Class:     com_github_stephengold_joltjni_HairSettings
 * Method:    setScalpInverseBindPose
 * Signature: (JLjava/nio/LongBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairSettings_setScalpInverseBindPose
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject longBuffer) {
    HairSettings * const pSettings
            = reinterpret_cast<HairSettings *> (settingsVa);
    pSettings->mScalpInverseBindPose.clear();
    const jlong * const pLongs
            = (jlong *) pEnv->GetDirectBufferAddress(longBuffer);
    JPH_ASSERT(pLongs != NULL);
    const jlong capacityLongs = pEnv->GetDirectBufferCapacity(longBuffer);
    JPH_ASSERT(capacityLongs >= 0);
    for (int i = 0; i < capacityLongs; ++i) {
        const jlong matrixVa = pLongs[i];
        const Mat44 * const pMatrix
                = reinterpret_cast<Mat44 *> (matrixVa);
        pSettings->mScalpInverseBindPose.push_back(*pMatrix);
    }
}

/*
 * Class:     com_github_stephengold_joltjni_HairSettings
 * Method:    setScalpNumSkinWeightsPerVertex
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairSettings_setScalpNumSkinWeightsPerVertex
  (JNIEnv *, jclass, jlong settingsVa, jint numWeights) {
    HairSettings * const pSettings
            = reinterpret_cast<HairSettings *> (settingsVa);
    pSettings->mScalpNumSkinWeightsPerVertex = numWeights;
}

/*
 * Class:     com_github_stephengold_joltjni_HairSettings
 * Method:    setScalpSkinWeights
 * Signature: (JLjava/nio/LongBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairSettings_setScalpSkinWeights
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject longBuffer) {
    HairSettings * const pSettings
            = reinterpret_cast<HairSettings *> (settingsVa);
    pSettings->mScalpSkinWeights.clear();
    const jlong * const pLongs
            = (jlong *) pEnv->GetDirectBufferAddress(longBuffer);
    JPH_ASSERT(pLongs != NULL);
    const jlong capacityLongs = pEnv->GetDirectBufferCapacity(longBuffer);
    JPH_ASSERT(capacityLongs >= 0);
    for (int i = 0; i < capacityLongs; ++i) {
        const jlong weightVa = pLongs[i];
        const HairSettings::SkinWeight * const pWeight
                = reinterpret_cast<HairSettings::SkinWeight *> (weightVa);
        pSettings->mScalpSkinWeights.push_back(*pWeight);
    }
}

/*
 * Class:     com_github_stephengold_joltjni_HairSettings
 * Method:    setScalpTriangles
 * Signature: (JLjava/nio/LongBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairSettings_setScalpTriangles
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject longBuffer) {
    HairSettings * const pSettings
            = reinterpret_cast<HairSettings *> (settingsVa);
    pSettings->mScalpTriangles.clear();
    const jlong * const pLongs
            = (jlong *) pEnv->GetDirectBufferAddress(longBuffer);
    JPH_ASSERT(pLongs != NULL);
    const jlong capacityLongs = pEnv->GetDirectBufferCapacity(longBuffer);
    JPH_ASSERT(capacityLongs >= 0);
    for (int i = 0; i < capacityLongs; ++i) {
        const jlong triangleVa = pLongs[i];
        const IndexedTriangleNoMaterial * const pTriangle
                = reinterpret_cast<IndexedTriangleNoMaterial *> (triangleVa);
        pSettings->mScalpTriangles.push_back(*pTriangle);
    }
}

/*
 * Class:     com_github_stephengold_joltjni_HairSettings
 * Method:    setScalpVertices
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairSettings_setScalpVertices
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject floatBuffer) {
    HairSettings * const pSettings
            = reinterpret_cast<HairSettings *> (settingsVa);
    pSettings->mScalpVertices.clear();
    const DIRECT_FLOAT_BUFFER(pEnv, floatBuffer, pFloats, capacityFloats);
    const int numVertices = capacityFloats/3;
    for (int i = 0; i < numVertices; ++i) {
        const float x = pFloats[3*i];
        const float y = pFloats[3*i + 1];
        const float z = pFloats[3*i + 2];
        const Float3 vertex(x, y, z);
        pSettings->mScalpVertices.push_back(vertex);
    }
}

/*
 * Class:     com_github_stephengold_joltjni_HairSettings
 * Method:    setSimulationBoundsPadding
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairSettings_setSimulationBoundsPadding
  (JNIEnv *, jclass, jlong settingsVa, jfloat px, jfloat py, jfloat pz) {
    HairSettings * const pSettings
            = reinterpret_cast<HairSettings *> (settingsVa);
    const Vec3 padding(px, py, pz);
    pSettings->mSimulationBoundsPadding = padding;
}

/*
 * Class:     com_github_stephengold_joltjni_HairSettings
 * Method:    sResample
 * Signature: (JJI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairSettings_sResample
  (JNIEnv *, jclass, jlong verticesVa, jlong strandsVa,
  jint numVerticesPerStrand) {
    Array<HairSettings::SVertex> * const pVertices
            = reinterpret_cast<Array<HairSettings::SVertex> *> (verticesVa);
    Array<HairSettings::SStrand> * const pStrands
            = reinterpret_cast<Array<HairSettings::SStrand> *> (strandsVa);
    HairSettings::sResample(*pVertices, *pStrands, numVerticesPerStrand);
}

/*
 * Class:     com_github_stephengold_joltjni_HairSettings
 * Method:    toRef
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_HairSettings_toRef
  (JNIEnv *pEnv, jclass, jlong settingsVa) {
    HairSettings * const pSettings
            = reinterpret_cast<HairSettings *> (settingsVa);
    Ref<HairSettings> * const pResult = new Ref<HairSettings>(pSettings);
    TRACE_NEW("Ref<HairSettings>", pResult)
    return reinterpret_cast<jlong> (pResult);
}