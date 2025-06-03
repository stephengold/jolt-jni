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
#include "Jolt/Physics/Collision/Shape/MeshShape.h"
#include "auto/com_github_stephengold_joltjni_MeshShapeSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_MeshShapeSettings
 * Method:    addIndexedTriangle
 * Signature: (JIII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MeshShapeSettings_addIndexedTriangle
  (JNIEnv *, jclass, jlong settingsVa, jint vi0, jint vi1, jint vi2) {
    MeshShapeSettings * const pSettings
            = reinterpret_cast<MeshShapeSettings *> (settingsVa);
    const IndexedTriangle triangle(vi0, vi1, vi2);
    pSettings->mIndexedTriangles.push_back(triangle);
}

/*
 * Class:     com_github_stephengold_joltjni_MeshShapeSettings
 * Method:    addTriangleVertex
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MeshShapeSettings_addTriangleVertex
  (JNIEnv *, jclass, jlong settingsVa, jfloat x, jfloat y, jfloat z) {
    MeshShapeSettings * const pSettings
            = reinterpret_cast<MeshShapeSettings *> (settingsVa);
    const Float3 vertex(x, y, z);
    pSettings->mTriangleVertices.push_back(vertex);
}

/*
 * Class:     com_github_stephengold_joltjni_MeshShapeSettings
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_MeshShapeSettings_createCopy
  BODYOF_CREATE_COPY(MeshShapeSettings)

/*
 * Class:     com_github_stephengold_joltjni_MeshShapeSettings
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_MeshShapeSettings_createDefault
  BODYOF_CREATE_DEFAULT(MeshShapeSettings)

/*
 * Class:     com_github_stephengold_joltjni_MeshShapeSettings
 * Method:    createMeshShapeSettings
 * Signature: (ILjava/nio/FloatBuffer;J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_MeshShapeSettings_createMeshShapeSettings
  (JNIEnv *pEnv, jclass, jint numVertices, jobject buffer, jlong indicesVa) {
    const jfloat * const pFloats
            = (jfloat *) pEnv->GetDirectBufferAddress(buffer);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const jlong capacityFloats = pEnv->GetDirectBufferCapacity(buffer);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    JPH_ASSERT(capacityFloats >= 3*numVertices);
    VertexList vertices;
    for (jint i = 0; i < numVertices; ++i) {
        const float x = pFloats[3 * i];
        const float y = pFloats[3 * i + 1];
        const float z = pFloats[3 * i + 2];
        const Float3 float3(x, y, z);
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
 * Method:    createSettingsFromTriangles
 * Signature: (ILjava/nio/FloatBuffer;J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_MeshShapeSettings_createSettingsFromTriangles
  (JNIEnv *pEnv, jclass, jint numTriangles, jobject buffer, jlong materialsVa) {
    const jfloat * const pFloats
            = (jfloat *) pEnv->GetDirectBufferAddress(buffer);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const jlong capacityFloats = pEnv->GetDirectBufferCapacity(buffer);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    JPH_ASSERT(capacityFloats >= 9*numTriangles);
    TriangleList triangles;
    for (jint i = 0; i < numTriangles; ++i) {
        const float v1x = pFloats[9 * i];
        const float v1y = pFloats[9 * i + 1];
        const float v1z = pFloats[9 * i + 2];
        const Float3 v1(v1x, v1y, v1z);
        const float v2x = pFloats[9 * i + 3];
        const float v2y = pFloats[9 * i + 4];
        const float v2z = pFloats[9 * i + 5];
        const Float3 v2(v2x, v2y, v2z);
        const float v3x = pFloats[9 * i + 6];
        const float v3y = pFloats[9 * i + 7];
        const float v3z = pFloats[9 * i + 8];
        const Float3 v3(v3x, v3y, v3z);
        const Triangle triangle(v1, v2, v3);
        triangles.push_back(triangle);
    }
    PhysicsMaterialList * const pMaterials
            = reinterpret_cast<PhysicsMaterialList *> (materialsVa);
    MeshShapeSettings * const pResult
            = new MeshShapeSettings(triangles, *pMaterials);
    TRACE_NEW("MeshShapeSettings", pResult)
    return reinterpret_cast<jlong> (pResult);
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
 * Method:    reserveIndexedTriangles
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MeshShapeSettings_reserveIndexedTriangles
  (JNIEnv *, jclass, jlong settingsVa, jint numTriangles) {
    MeshShapeSettings * const pSettings
            = reinterpret_cast<MeshShapeSettings *> (settingsVa);
    pSettings->mIndexedTriangles.reserve(numTriangles);
}

/*
 * Class:     com_github_stephengold_joltjni_MeshShapeSettings
 * Method:    reserveTriangleVertices
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MeshShapeSettings_reserveTriangleVertices
  (JNIEnv *, jclass, jlong settingsVa, jint numVertices) {
    MeshShapeSettings * const pSettings
            = reinterpret_cast<MeshShapeSettings *> (settingsVa);
    pSettings->mTriangleVertices.reserve(numVertices);
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