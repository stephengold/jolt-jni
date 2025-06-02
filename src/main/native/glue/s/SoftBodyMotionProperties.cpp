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
#include "Jolt/Physics/Constraints/Constraint.h"
#include "Jolt/Physics/SoftBody/SoftBodyMotionProperties.h"
#include "auto/com_github_stephengold_joltjni_SoftBodyMotionProperties.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyMotionProperties
 * Method:    countFaces
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SoftBodyMotionProperties_countFaces
  (JNIEnv *, jclass, jlong propertiesVa) {
    const SoftBodyMotionProperties * const pProperties
            = reinterpret_cast<SoftBodyMotionProperties *> (propertiesVa);
    const Array<SoftBodySharedSettings::Face>& faces = pProperties->GetFaces();
    const Array<SoftBodySharedSettings::Face>::size_type result = faces.size();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyMotionProperties
 * Method:    countVertices
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SoftBodyMotionProperties_countVertices
  (JNIEnv *, jclass, jlong propertiesVa) {
    const SoftBodyMotionProperties * const pProperties
            = reinterpret_cast<SoftBodyMotionProperties *> (propertiesVa);
    const Array<SoftBodyVertex>& vertices = pProperties->GetVertices();
    const Array<SoftBodyVertex>::size_type result = vertices.size();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyMotionProperties
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SoftBodyMotionProperties_createDefault
    BODYOF_CREATE_DEFAULT(SoftBodyMotionProperties)

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyMotionProperties
 * Method:    customUpdate
 * Signature: (JFJJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodyMotionProperties_customUpdate
  (JNIEnv *, jclass, jlong propertiesVa, jfloat deltaTime, jlong bodyVa, jlong systemVa) {
    SoftBodyMotionProperties * const pProperties
            = reinterpret_cast<SoftBodyMotionProperties *> (propertiesVa);
    Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    PhysicsSystem * const pSystem = reinterpret_cast<PhysicsSystem *> (systemVa);
    pProperties->CustomUpdate(deltaTime, *pBody, *pSystem);
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyMotionProperties
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodyMotionProperties_free
  BODYOF_FREE(SoftBodyMotionProperties)

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyMotionProperties
 * Method:    getEnableSkinConstraints
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_SoftBodyMotionProperties_getEnableSkinConstraints
  (JNIEnv *, jclass, jlong propertiesVa) {
    const SoftBodyMotionProperties * const pProperties
            = reinterpret_cast<SoftBodyMotionProperties *> (propertiesVa);
    const bool result = pProperties->GetEnableSkinConstraints();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyMotionProperties
 * Method:    getFace
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SoftBodyMotionProperties_getFace
  (JNIEnv *, jclass, jlong propertiesVa, jint index) {
    SoftBodyMotionProperties * const pProperties
            = reinterpret_cast<SoftBodyMotionProperties *> (propertiesVa);
    const SoftBodySharedSettings::Face& result = pProperties->GetFace(index);
    return reinterpret_cast<jlong> (&result);
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyMotionProperties
 * Method:    getNumIterations
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SoftBodyMotionProperties_getNumIterations
  (JNIEnv *, jclass, jlong propertiesVa) {
    const SoftBodyMotionProperties * const pProperties
            = reinterpret_cast<SoftBodyMotionProperties *> (propertiesVa);
    const uint32 result = pProperties->GetNumIterations();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyMotionProperties
 * Method:    getSettings
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SoftBodyMotionProperties_getSettings
  (JNIEnv *, jclass, jlong propertiesVa) {
    SoftBodyMotionProperties * const pProperties
            = reinterpret_cast<SoftBodyMotionProperties *> (propertiesVa);
    const SoftBodySharedSettings * const pResult = pProperties->GetSettings();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyMotionProperties
 * Method:    getSkinnedMaxDistanceMultiplier
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SoftBodyMotionProperties_getSkinnedMaxDistanceMultiplier
  (JNIEnv *, jclass, jlong propertiesVa) {
    const SoftBodyMotionProperties * const pProperties
            = reinterpret_cast<SoftBodyMotionProperties *> (propertiesVa);
    const float result = pProperties->GetSkinnedMaxDistanceMultiplier();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyMotionProperties
 * Method:    getVertex
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SoftBodyMotionProperties_getVertex
  (JNIEnv *, jclass, jlong propertiesVa, jint index) {
    SoftBodyMotionProperties * const pProperties
            = reinterpret_cast<SoftBodyMotionProperties *> (propertiesVa);
    const SoftBodyVertex& result = pProperties->GetVertex(index);
    return reinterpret_cast<jlong> (&result);
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyMotionProperties
 * Method:    putPinLocations
 * Signature: (JFFFILjava/nio/FloatBuffer;)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SoftBodyMotionProperties_putPinLocations
  (JNIEnv *pEnv, jclass, jlong propertiesVa, jfloat x, jfloat y, jfloat z,
  jint bufferPosition, jobject storeFloats) {
    jfloat * const pStoreFloats
            = (jfloat *) pEnv->GetDirectBufferAddress(storeFloats);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const jlong capacityFloats = pEnv->GetDirectBufferCapacity(storeFloats);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const SoftBodyMotionProperties * const pProperties
            = reinterpret_cast<SoftBodyMotionProperties *> (propertiesVa);
    const Vec3 comLocation(x, y, z);
    const Array<SoftBodyVertex>& vertices = pProperties->GetVertices();
    const Array<SoftBodyVertex>::size_type numVertices = vertices.size();
    for (size_t i = 0; i < numVertices && bufferPosition + 2 < capacityFloats; ++i) {
        const SoftBodyVertex& vertex = vertices[i];
        if (vertex.mInvMass == 0) {
            const Vec3& vertexLocation = vertex.mPosition + comLocation;
            pStoreFloats[bufferPosition++] = vertexLocation.GetX();
            pStoreFloats[bufferPosition++] = vertexLocation.GetY();
            pStoreFloats[bufferPosition++] = vertexLocation.GetZ();
        }
    }
    return bufferPosition;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyMotionProperties
 * Method:    putVertexLocations
 * Signature: (JFFFILjava/nio/FloatBuffer;)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SoftBodyMotionProperties_putVertexLocations
  (JNIEnv *pEnv, jclass, jlong propertiesVa, jfloat x, jfloat y, jfloat z,
  jint bufferPosition, jobject storeFloats) {
    jfloat * const pStoreFloats
            = (jfloat *) pEnv->GetDirectBufferAddress(storeFloats);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const jlong capacityFloats = pEnv->GetDirectBufferCapacity(storeFloats);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const SoftBodyMotionProperties * const pProperties
            = reinterpret_cast<SoftBodyMotionProperties *> (propertiesVa);
    const Vec3 comLocation(x, y, z);
    const Array<SoftBodyVertex>& vertices = pProperties->GetVertices();
    const Array<SoftBodyVertex>::size_type numVertices = vertices.size();
    for (size_t i = 0; i < numVertices && bufferPosition + 2 < capacityFloats; ++i) {
        const SoftBodyVertex& vertex = vertices[i];
        const Vec3& vertexLocation = vertex.mPosition + comLocation;
        pStoreFloats[bufferPosition++] = vertexLocation.GetX();
        pStoreFloats[bufferPosition++] = vertexLocation.GetY();
        pStoreFloats[bufferPosition++] = vertexLocation.GetZ();
    }
    return bufferPosition;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyMotionProperties
 * Method:    setEnableSkinConstraints
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodyMotionProperties_setEnableSkinConstraints
  (JNIEnv *, jclass, jlong propertiesVa, jboolean enable) {
    SoftBodyMotionProperties * const pProperties
            = reinterpret_cast<SoftBodyMotionProperties *> (propertiesVa);
    pProperties->SetEnableSkinConstraints(enable);
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyMotionProperties
 * Method:    setNumIterations
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodyMotionProperties_setNumIterations
  (JNIEnv *, jclass, jlong propertiesVa, jint numIterations) {
    SoftBodyMotionProperties * const pProperties
            = reinterpret_cast<SoftBodyMotionProperties *> (propertiesVa);
    pProperties->SetNumIterations(numIterations);
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyMotionProperties
 * Method:    setSkinnedMaxDistanceMultiplier
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodyMotionProperties_setSkinnedMaxDistanceMultiplier
  (JNIEnv *, jclass, jlong propertiesVa, jfloat multiplier) {
    SoftBodyMotionProperties * const pProperties
            = reinterpret_cast<SoftBodyMotionProperties *> (propertiesVa);
    pProperties->SetSkinnedMaxDistanceMultiplier(multiplier);
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyMotionProperties
 * Method:    skinVertices
 * Signature: (JJ[JZJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodyMotionProperties_skinVertices
  (JNIEnv *pEnv, jclass, jlong propertiesVa, jlong comTransformVa,
  jlongArray jointMatrixVas, jboolean hardSkinAll, jlong allocatorVa) {
    const jsize numJoints = pEnv->GetArrayLength(jointMatrixVas);
    Mat44 * const pTempArray = new Mat44[numJoints];
    TRACE_NEW("Mat44[]", pTempArray)
    jboolean isCopy;
    jlong * const pMatrixVas
            = pEnv->GetLongArrayElements(jointMatrixVas, &isCopy);
    for (jsize i = 0; i < numJoints; ++i) {
        const jlong matrixVa = pMatrixVas[i];
        Mat44 * const pMatrix = reinterpret_cast<Mat44 *> (matrixVa);
        pTempArray[i] = *pMatrix;
    }
    pEnv->ReleaseLongArrayElements(jointMatrixVas, pMatrixVas, JNI_ABORT);
    SoftBodyMotionProperties * const pProperties
            = reinterpret_cast<SoftBodyMotionProperties *> (propertiesVa);
    const RMat44 * const pComTransform
            = reinterpret_cast<RMat44 *> (comTransformVa);
    TempAllocator * const pAllocator
            = reinterpret_cast<TempAllocator *> (allocatorVa);
    pProperties->SkinVertices(*pComTransform, pTempArray, numJoints,
            hardSkinAll, *pAllocator);
    TRACE_DELETE("Mat44[]", pTempArray)
    delete[] pTempArray;
}