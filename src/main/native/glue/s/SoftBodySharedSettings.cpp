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
#include "Jolt/Physics/SoftBody/SoftBodySharedSettings.h"

#include "auto/com_github_stephengold_joltjni_SoftBodySharedSettings.h"
#include "auto/com_github_stephengold_joltjni_SoftBodySharedSettingsRef.h"
#include "glue/glue.h"

using namespace JPH;

IMPLEMENT_REF(SoftBodySharedSettings,
  Java_com_github_stephengold_joltjni_SoftBodySharedSettingsRef_copy,
  Java_com_github_stephengold_joltjni_SoftBodySharedSettingsRef_createEmpty,
  Java_com_github_stephengold_joltjni_SoftBodySharedSettingsRef_free,
  Java_com_github_stephengold_joltjni_SoftBodySharedSettingsRef_getPtr)

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
    SoftBodySharedSettings * const pSettings
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
    SoftBodySharedSettings * const pSettings
            = reinterpret_cast<SoftBodySharedSettings *> (settingsVa);
    const Array<SoftBodySharedSettings::Face>::size_type result
            = pSettings->mFaces.size();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodySharedSettings
 * Method:    countVertices
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_countVertices
  (JNIEnv *, jclass, jlong settingsVa) {
    SoftBodySharedSettings * const pSettings
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
    SoftBodySharedSettings * const pSettings
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
    for (int i = 0; i < numAttributes; ++i) {
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
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_createDefault
  (JNIEnv *, jclass) {
    SoftBodySharedSettings * const pResult = new SoftBodySharedSettings();
    TRACE_NEW("SoftBodySharedSettings", pResult)
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
 * Method:    getVertexRadius
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SoftBodySharedSettings_getVertexRadius
  (JNIEnv *, jclass, jlong settingsVa) {
    const SoftBodySharedSettings * const pSettings
            = reinterpret_cast<SoftBodySharedSettings *> (settingsVa);
    const float result = pSettings->mVertexRadius;
    return result;
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