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
#include "Jolt/Physics/SoftBody/SoftBodySharedSettings.h"

#include "auto/com_github_stephengold_joltjni_SoftBodySharedSettings.h"
#include "auto/com_github_stephengold_joltjni_SoftBodySharedSettingsRef.h"
#include "glue/glue.h"

using namespace JPH;

IMPLEMENT_REF(SoftBodySharedSettings,
  Java_com_github_stephengold_joltjni_SoftBodySharedSettingsRef_copy,
  Java_com_github_stephengold_joltjni_SoftBodySharedSettingsRef_createDefault,
  Java_com_github_stephengold_joltjni_SoftBodySharedSettingsRef_free,
  Java_com_github_stephengold_joltjni_SoftBodySharedSettingsRef_getPtr,
  Java_com_github_stephengold_joltjni_SoftBodySharedSettingsRef_toRefC)

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    addEdgeConstraint
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_addEdgeConstraint
  (JNIEnv *, jclass, jlong settingsVa, jlong edgeVa) {
    SoftBodySharedSettings * const pSettings
            = reinterpret_cast<SoftBodySharedSettings *> (settingsVa);
    const SoftBodySharedSettings::Edge * const pEdge
            =  reinterpret_cast<SoftBodySharedSettings::Edge *> (edgeVa);
    pSettings->mEdgeConstraints.push_back(*pEdge);
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    addFace
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_addFace
  (JNIEnv *, jclass, jlong settingsVa, jlong faceVa) {
    SoftBodySharedSettings * const pSettings
            = reinterpret_cast<SoftBodySharedSettings *> (settingsVa);
    const SoftBodySharedSettings::Face * const pFace
            =  reinterpret_cast<SoftBodySharedSettings::Face *> (faceVa);
    pSettings->AddFace(*pFace);
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    addInvBindMatrix
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_addInvBindMatrix
  (JNIEnv *, jclass, jlong settingsVa, jlong invBindVa) {
    SoftBodySharedSettings * const pSettings
            = reinterpret_cast<SoftBodySharedSettings *> (settingsVa);
    const SoftBodySharedSettings::InvBind * const pInvBind
            = reinterpret_cast<SoftBodySharedSettings::InvBind *> (invBindVa);
    pSettings->mInvBindMatrices.push_back(*pInvBind);
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    addRodBendTwistConstraint
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_addRodBendTwistConstraint
  (JNIEnv *, jclass, jlong settingsVa, jlong constraintVa) {
    SoftBodySharedSettings * const pSettings
            = reinterpret_cast<SoftBodySharedSettings *> (settingsVa);
    const SoftBodySharedSettings::RodBendTwist * const pConstraints
            = reinterpret_cast<SoftBodySharedSettings::RodBendTwist *> (constraintVa);
    pSettings->mRodBendTwistConstraints.push_back(*pConstraints);
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    addRodStretchShearConstraint
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_addRodStretchShearConstraint
  (JNIEnv *, jclass, jlong settingsVa, jlong rodVa) {
    SoftBodySharedSettings * const pSettings
            = reinterpret_cast<SoftBodySharedSettings *> (settingsVa);
    const SoftBodySharedSettings::RodStretchShear * const pRod
            = reinterpret_cast<SoftBodySharedSettings::RodStretchShear *> (rodVa);
    pSettings->mRodStretchShearConstraints.push_back(*pRod);
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    addSkinnedConstraint
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_addSkinnedConstraint
  (JNIEnv *, jclass, jlong settingsVa, jlong skinnedVa) {
    SoftBodySharedSettings * const pSettings
            = reinterpret_cast<SoftBodySharedSettings *> (settingsVa);
    const SoftBodySharedSettings::Skinned * const pSkinned
            = reinterpret_cast<SoftBodySharedSettings::Skinned *> (skinnedVa);
    pSettings->mSkinnedConstraints.push_back(*pSkinned);
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    addVertex
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_addVertex
  (JNIEnv *, jclass, jlong settingsVa, jlong vertexVa) {
    SoftBodySharedSettings * const pSettings
            = reinterpret_cast<SoftBodySharedSettings *> (settingsVa);
    const SoftBodySharedSettings::Vertex * const pVertex
            =  reinterpret_cast<SoftBodySharedSettings::Vertex *> (vertexVa);
    pSettings->mVertices.push_back(*pVertex);
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    addVolumeConstraint
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_addVolumeConstraint
  (JNIEnv *, jclass, jlong settingsVa, jlong volumeVa) {
    SoftBodySharedSettings * const pSettings
            = reinterpret_cast<SoftBodySharedSettings *> (settingsVa);
    const SoftBodySharedSettings::Volume * const pVolume
            =  reinterpret_cast<SoftBodySharedSettings::Volume *> (volumeVa);
    pSettings->mVolumeConstraints.push_back(*pVolume);
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    calculateEdgeLengths
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_calculateEdgeLengths
  (JNIEnv *, jclass, jlong settingsVa) {
    SoftBodySharedSettings * const pSettings
            = reinterpret_cast<SoftBodySharedSettings *> (settingsVa);
    pSettings->CalculateEdgeLengths();
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    calculateRodProperties
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_calculateRodProperties
  (JNIEnv *, jclass, jlong settingsVa) {
    SoftBodySharedSettings * const pSettings
            = reinterpret_cast<SoftBodySharedSettings *> (settingsVa);
    pSettings->CalculateRodProperties();
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    calculateSkinnedConstraintNormals
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_calculateSkinnedConstraintNormals
  (JNIEnv *, jclass, jlong settingsVa) {
    SoftBodySharedSettings * const pSettings
            = reinterpret_cast<SoftBodySharedSettings *> (settingsVa);
    pSettings->CalculateSkinnedConstraintNormals();
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    calculateVolumeConstraintVolumes
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_calculateVolumeConstraintVolumes
  (JNIEnv *, jclass, jlong settingsVa) {
    SoftBodySharedSettings * const pSettings
            = reinterpret_cast<SoftBodySharedSettings *> (settingsVa);
    pSettings->CalculateVolumeConstraintVolumes();
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    countEdgeConstraints
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_countEdgeConstraints
  (JNIEnv *, jclass, jlong settingsVa) {
    const SoftBodySharedSettings * const pSettings
            = reinterpret_cast<SoftBodySharedSettings *> (settingsVa);
    const Array<SoftBodySharedSettings::Edge>::size_type result
            = pSettings->mEdgeConstraints.size();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    countFaces
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_countFaces
  (JNIEnv *, jclass, jlong settingsVa) {
    const SoftBodySharedSettings * const pSettings
            = reinterpret_cast<SoftBodySharedSettings *> (settingsVa);
    const Array<SoftBodySharedSettings::Face>::size_type result
            = pSettings->mFaces.size();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    countPinnedVertices
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_countPinnedVertices
  (JNIEnv *, jclass, jlong settingsVa) {
    const SoftBodySharedSettings * const pSettings
            = reinterpret_cast<SoftBodySharedSettings *> (settingsVa);
    const size_t numVertices = pSettings->mVertices.size();
    jint result = 0;
    for (size_t index = 0; index < numVertices; ++index) {
        const SoftBodySharedSettings::Vertex& vertex
                = pSettings->mVertices.at(index);
        if (vertex.mInvMass == 0) {
            ++result;
        }
    }
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    countRodBendTwistConstraints
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_countRodBendTwistConstraints
  (JNIEnv *, jclass, jlong settingsVa) {
    const SoftBodySharedSettings * const pSettings
            = reinterpret_cast<SoftBodySharedSettings *> (settingsVa);
    const Array<SoftBodySharedSettings::RodBendTwist>::size_type result
            = pSettings->mRodBendTwistConstraints.size();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    countRodStretchShearConstraints
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_countRodStretchShearConstraints
  (JNIEnv *, jclass, jlong settingsVa) {
    const SoftBodySharedSettings * const pSettings
            = reinterpret_cast<SoftBodySharedSettings *> (settingsVa);
    const Array<SoftBodySharedSettings::RodStretchShear>::size_type result
            = pSettings->mRodStretchShearConstraints.size();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    countVertices
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_countVertices
  (JNIEnv *, jclass, jlong settingsVa) {
    const SoftBodySharedSettings * const pSettings
            = reinterpret_cast<SoftBodySharedSettings *> (settingsVa);
    const Array<SoftBodySharedSettings::Vertex>::size_type result
            = pSettings->mVertices.size();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    countVolumeConstraints
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_countVolumeConstraints
  (JNIEnv *, jclass, jlong settingsVa) {
    const SoftBodySharedSettings * const pSettings
            = reinterpret_cast<SoftBodySharedSettings *> (settingsVa);
    const Array<SoftBodySharedSettings::Volume>::size_type result
            = pSettings->mVolumeConstraints.size();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    createConstraints
 * Signature: (J[JIF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_createConstraints
  (JNIEnv *pEnv, jclass, jlong settingsVa, jlongArray attributeVas,
  jint ordinal, jfloat angleTolerance) {
    const jsize numAttributes = pEnv->GetArrayLength(attributeVas);
    SoftBodySharedSettings::VertexAttributes * const pTempArray
            = new SoftBodySharedSettings::VertexAttributes[numAttributes];
    TRACE_NEW("SoftBodySharedSettings::VertexAttributes[]", pTempArray)
    jboolean isCopy;
    jlong * const pVas = pEnv->GetLongArrayElements(attributeVas, &isCopy);
    for (jsize i = 0; i < numAttributes; ++i) {
        const jlong va = pVas[i];
        const SoftBodySharedSettings::VertexAttributes * const pAttribute
                = reinterpret_cast<SoftBodySharedSettings::VertexAttributes *> (va);
        pTempArray[i] = *pAttribute;
    }
    pEnv->ReleaseLongArrayElements(attributeVas, pVas, JNI_ABORT);
    SoftBodySharedSettings * const pSettings
            = reinterpret_cast<SoftBodySharedSettings *> (settingsVa);
    const SoftBodySharedSettings::EBendType bendType
            = (SoftBodySharedSettings::EBendType) ordinal;
    pSettings->CreateConstraints(
            pTempArray, numAttributes, bendType, angleTolerance);
    TRACE_DELETE("SoftBodySharedSettings::VertexAttributes[]", pTempArray)
    delete[] pTempArray;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_createCopy
    BODYOF_CREATE_COPY_TARGET(SoftBodySharedSettings)

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_createDefault
    BODYOF_CREATE_DEFAULT_TARGET(SoftBodySharedSettings)

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    getEdgeConstraint
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_getEdgeConstraint
  (JNIEnv *, jclass, jlong settingsVa, jint index) {
    SoftBodySharedSettings * const pSettings
            = reinterpret_cast<SoftBodySharedSettings *> (settingsVa);
    SoftBodySharedSettings::Edge * const pResult
            = &pSettings->mEdgeConstraints[index];
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    getRefCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_getRefCount
  (JNIEnv *, jclass, jlong settingsVa) {
    const SoftBodySharedSettings * const pSettings
            = reinterpret_cast<SoftBodySharedSettings *> (settingsVa);
    const uint32 result = pSettings->GetRefCount();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    getRodBendTwistConstraint
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_getRodBendTwistConstraint
  (JNIEnv *, jclass, jlong settingsVa, jint index) {
    SoftBodySharedSettings * const pSettings
            = reinterpret_cast<SoftBodySharedSettings *> (settingsVa);
    SoftBodySharedSettings::RodBendTwist& result
            = pSettings->mRodBendTwistConstraints.at(index);
    return reinterpret_cast<jlong> (&result);
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    getRodStretchShearConstraint
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_getRodStretchShearConstraint
  (JNIEnv *, jclass, jlong settingsVa, jint index) {
    SoftBodySharedSettings * const pSettings
            = reinterpret_cast<SoftBodySharedSettings *> (settingsVa);
    SoftBodySharedSettings::RodStretchShear& result
            = pSettings->mRodStretchShearConstraints.at(index);
    return reinterpret_cast<jlong> (&result);
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    getVertex
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_getVertex
  (JNIEnv *, jclass, jlong settingsVa, jint index) {
    SoftBodySharedSettings * const pSettings
            = reinterpret_cast<SoftBodySharedSettings *> (settingsVa);
    SoftBodySharedSettings::Vertex& result = pSettings->mVertices.at(index);
    return reinterpret_cast<jlong> (&result);
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    optimize
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_optimize
  (JNIEnv *, jclass, jlong settingsVa) {
    SoftBodySharedSettings * const pSettings
            = reinterpret_cast<SoftBodySharedSettings *> (settingsVa);
    pSettings->Optimize();
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    putEdgeIndices
 * Signature: (JILjava/nio/IntBuffer;)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_putEdgeIndices
  (JNIEnv *pEnv, jclass, jlong settingsVa, jint bufferPosition,
  jobject storeIndices) {
    jint * const pStoreInts
            = (jint *) pEnv->GetDirectBufferAddress(storeIndices);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const jlong capacityInts = pEnv->GetDirectBufferCapacity(storeIndices);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const SoftBodySharedSettings * const pSettings
            = reinterpret_cast<SoftBodySharedSettings *> (settingsVa);
    const Array<SoftBodySharedSettings::Edge>& edges
            = pSettings->mEdgeConstraints;
    const size_t numEdges = edges.size();
    for (size_t i = 0; i < numEdges && bufferPosition + 1 < capacityInts; ++i) {
        const SoftBodySharedSettings::Edge& edge = edges[i];
        pStoreInts[bufferPosition++] = edge.mVertex[0];
        pStoreInts[bufferPosition++] = edge.mVertex[1];
    }
    return bufferPosition;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    putFaceIndices
 * Signature: (JILjava/nio/IntBuffer;)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_putFaceIndices
  (JNIEnv *pEnv, jclass, jlong settingsVa, jint bufferPosition,
  jobject storeIndices) {
    jint * const pStoreInts
            = (jint *) pEnv->GetDirectBufferAddress(storeIndices);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const jlong capacityInts = pEnv->GetDirectBufferCapacity(storeIndices);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const SoftBodySharedSettings * const pSettings
            = reinterpret_cast<SoftBodySharedSettings *> (settingsVa);
    const Array<SoftBodySharedSettings::Face>& faces = pSettings->mFaces;
    const size_t numFaces = faces.size();
    for (size_t i = 0; i < numFaces && bufferPosition + 2 < capacityInts; ++i) {
        const SoftBodySharedSettings::Face& face = faces[i];
        pStoreInts[bufferPosition++] = face.mVertex[0];
        pStoreInts[bufferPosition++] = face.mVertex[1];
        pStoreInts[bufferPosition++] = face.mVertex[2];
    }
    return bufferPosition;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    putRodIndices
 * Signature: (JILjava/nio/IntBuffer;)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_putRodIndices
  (JNIEnv *pEnv, jclass, jlong settingsVa, jint bufferPosition,
  jobject storeIndices) {
    jint * const pStoreInts
            = (jint *) pEnv->GetDirectBufferAddress(storeIndices);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const jlong capacityInts = pEnv->GetDirectBufferCapacity(storeIndices);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const SoftBodySharedSettings * const pSettings
            = reinterpret_cast<SoftBodySharedSettings *> (settingsVa);
    const Array<SoftBodySharedSettings::RodStretchShear>& rods
            = pSettings->mRodStretchShearConstraints;
    const size_t numRods = rods.size();
    for (size_t i = 0; i < numRods && bufferPosition + 1 < capacityInts; ++i) {
        const SoftBodySharedSettings::RodStretchShear& rod = rods[i];
        pStoreInts[bufferPosition++] = rod.mVertex[0];
        pStoreInts[bufferPosition++] = rod.mVertex[1];
    }
    return bufferPosition;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    restoreBinaryState
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_restoreBinaryState
  (JNIEnv *, jclass, jlong settingsVa, jlong streamVa) {
    SoftBodySharedSettings * const pSettings
            = reinterpret_cast<SoftBodySharedSettings *> (settingsVa);
    StreamIn * const pStream = reinterpret_cast<StreamIn *> (streamVa);
    pSettings->RestoreBinaryState(*pStream);
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    saveBinaryState
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_saveBinaryState
  (JNIEnv *, jclass, jlong bodySettingsVa, jlong streamVa) {
    const SoftBodySharedSettings * const pSettings
            = reinterpret_cast<SoftBodySharedSettings *> (bodySettingsVa);
    StreamOut * const pStream = reinterpret_cast<StreamOut *> (streamVa);
    pSettings->SaveBinaryState(*pStream);
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    saveWithMaterials
 * Signature: (JJJJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_saveWithMaterials
  (JNIEnv *, jclass, jlong settingsVa, jlong streamVa,
            jlong settingsMapVa, jlong materialsMapVa) {
    const SoftBodySharedSettings * const pSettings
            = reinterpret_cast<SoftBodySharedSettings *> (settingsVa);
    StreamOut * const pStream = reinterpret_cast<StreamOut *> (streamVa);
    StreamUtils::ObjectToIDMap<SoftBodySharedSettings> * const pSettingsMap
            = reinterpret_cast<StreamUtils::ObjectToIDMap<SoftBodySharedSettings> *> (settingsMapVa);
    StreamUtils::ObjectToIDMap<PhysicsMaterial> * const pMaterialsMap
            = reinterpret_cast<StreamUtils::ObjectToIDMap<PhysicsMaterial> *> (materialsMapVa);
    pSettings->SaveWithMaterials(*pStream, *pSettingsMap, *pMaterialsMap);
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    sCreateCubeNative
 * Signature: (IF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_sCreateCubeNative
  (JNIEnv *, jclass, jint gridSize, jfloat gridSpacing) {
    Ref<SoftBodySharedSettings> ref
            = SoftBodySharedSettings::sCreateCube(gridSize, gridSpacing);
    Ref<SoftBodySharedSettings> * const pResult
            = new Ref<SoftBodySharedSettings>(ref);
    TRACE_NEW("Ref<SoftBodySharedSettings>", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    setEmbedded
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_setEmbedded
  (JNIEnv *, jclass, jlong settingsVa) {
    SoftBodySharedSettings * const pSettings
            = reinterpret_cast<SoftBodySharedSettings *> (settingsVa);
    pSettings->SetEmbedded();
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    setMaterialsSingle
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_setMaterialsSingle
  (JNIEnv *, jclass, jlong settingsVa, jlong materialVa) {
    SoftBodySharedSettings * const pSettings
            = reinterpret_cast<SoftBodySharedSettings *> (settingsVa);
    const PhysicsMaterial * const pMaterial
            = reinterpret_cast<PhysicsMaterial *> (materialVa);
    RefConst<PhysicsMaterial> ref = pMaterial;
    pSettings->mMaterials.clear();
    pSettings->mMaterials.push_back(ref);
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    toRef
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_toRef
  (JNIEnv *, jclass, jlong settingsVa) {
    SoftBodySharedSettings * const pSettings
            = reinterpret_cast<SoftBodySharedSettings *> (settingsVa);
    Ref<SoftBodySharedSettings> * const pResult
            = new Ref<SoftBodySharedSettings>(pSettings);
    TRACE_NEW("Ref<SoftBodySharedSettings>", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    sRestoreWithMaterials
 * Signature: (JJJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_sRestoreWithMaterials
  (JNIEnv *, jclass, jlong streamVa, jlong settingsMapVa, jlong materialMapVa) {
    StreamIn * const pStream = reinterpret_cast<StreamIn *> (streamVa);
    SoftBodySharedSettings::IDToSharedSettingsMap * const pSettingsMap
            = reinterpret_cast<SoftBodySharedSettings::IDToSharedSettingsMap *> (settingsMapVa);
    SoftBodySharedSettings::IDToMaterialMap * const pMaterialMap
            = reinterpret_cast<SoftBodySharedSettings::IDToMaterialMap *> (materialMapVa);
    SoftBodySharedSettings::SettingsResult * const pResult
            = new SoftBodySharedSettings::SettingsResult();
    TRACE_NEW("SoftBodySharedSettings::SettingsResult", pResult);
    *pResult = SoftBodySharedSettings::sRestoreWithMaterials(
            *pStream, *pSettingsMap, *pMaterialMap);
    return reinterpret_cast<jlong> (pResult);
}