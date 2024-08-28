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
#include <Jolt/Physics/Collision/Shape/MeshShape.h>
#include "auto/com_github_stephengold_joltjni_MeshShapeSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_MeshShapeSettings
 * Method:    createMeshShapeSettings
 * Signature: (ILjava/nio/FloatBuffer;J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_MeshShapeSettings_createMeshShapeSettings
  (JNIEnv *pEnv, jclass, jint numVertices, jobject buffer, jlong indicesVa) {
    const jfloat * const pFloats
        = (jfloat *) pEnv->GetDirectBufferAddress(buffer);
    VertexList vertices;
    for (jint i = 0; i < numVertices; ++i) {
        const float x = pFloats[3 * i];
        const float y = pFloats[3 * i + 1];
        const float z = pFloats[3 * i + 2];
        Float3 float3(x, y, z);
        vertices.push_back(float3);
    }
    IndexedTriangleList * const pIndices
            = reinterpret_cast<IndexedTriangleList *> (indicesVa);
    MeshShapeSettings * const pSettings
            = new MeshShapeSettings(vertices, *pIndices);
    TRACE_NEW("MeshShapeSettings", pSettings)
    return reinterpret_cast<jlong> (pSettings);
}

/*
 * Class:     com_github_stephengold_joltjni_MeshShapeSettings
 * Method:    countTriangles
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_MeshShapeSettings_countTriangles
  (JNIEnv *, jclass, jlong settingsVa) {
    const MeshShapeSettings * const pSettings
            = reinterpret_cast<MeshShapeSettings *> (settingsVa);
    const IndexedTriangleList::size_type result
            = pSettings->mIndexedTriangles.size();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MeshShapeSettings
 * Method:    countTriangleVertices
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_MeshShapeSettings_countTriangleVertices
  (JNIEnv *, jclass, jlong settingsVa) {
    const MeshShapeSettings * const pSettings
            = reinterpret_cast<MeshShapeSettings *> (settingsVa);
    const VertexList::size_type result = pSettings->mTriangleVertices.size();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MeshShapeSettings
 * Method:    getActiveEdgeCosThresholdAngle
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MeshShapeSettings_getActiveEdgeCosThresholdAngle
  (JNIEnv *, jclass, jlong settingsVa) {
    const MeshShapeSettings * const pSettings
            = reinterpret_cast<MeshShapeSettings *> (settingsVa);
    const float result = pSettings->mActiveEdgeCosThresholdAngle;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MeshShapeSettings
 * Method:    getMaxTrianglesPerLeaf
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_MeshShapeSettings_getMaxTrianglesPerLeaf
  (JNIEnv *, jclass, jlong settingsVa) {
    const MeshShapeSettings * const pSettings
            = reinterpret_cast<MeshShapeSettings *> (settingsVa);
    const uint result = pSettings->mMaxTrianglesPerLeaf;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MeshShapeSettings
 * Method:    getPerTriangleUserData
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_MeshShapeSettings_getPerTriangleUserData
  (JNIEnv *, jclass, jlong settingsVa) {
    const MeshShapeSettings * const pSettings
            = reinterpret_cast<MeshShapeSettings *> (settingsVa);
    const bool result = pSettings->mPerTriangleUserData;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MeshShapeSettings
 * Method:    setActiveEdgeCosThresholdAngle
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MeshShapeSettings_setActiveEdgeCosThresholdAngle
  (JNIEnv *, jclass, jlong settingsVa, jfloat cosine) {
    MeshShapeSettings * const pSettings
            = reinterpret_cast<MeshShapeSettings *> (settingsVa);
    pSettings->mActiveEdgeCosThresholdAngle = cosine;
}

/*
 * Class:     com_github_stephengold_joltjni_MeshShapeSettings
 * Method:    setMaxTrianglesPerLeaf
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MeshShapeSettings_setMaxTrianglesPerLeaf
  (JNIEnv *, jclass, jlong settingsVa, jint maxTriangles) {
    MeshShapeSettings * const pSettings
            = reinterpret_cast<MeshShapeSettings *> (settingsVa);
    pSettings->mMaxTrianglesPerLeaf = maxTriangles;
}

/*
 * Class:     com_github_stephengold_joltjni_MeshShapeSettings
 * Method:    setPerTriangleUserData
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MeshShapeSettings_setPerTriangleUserData
  (JNIEnv *, jclass, jlong settingsVa, jboolean include) {
    MeshShapeSettings * const pSettings
            = reinterpret_cast<MeshShapeSettings *> (settingsVa);
    pSettings->mPerTriangleUserData = include;
}