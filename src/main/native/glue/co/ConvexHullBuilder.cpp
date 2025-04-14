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
#include "Jolt/Geometry/ConvexHullBuilder.h"
#include "auto/com_github_stephengold_joltjni_ConvexHullBuilder.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_ConvexHullBuilder
 * Method:    countFaces
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_ConvexHullBuilder_countFaces
  (JNIEnv *, jclass, jlong builderVa) {
    const ConvexHullBuilder * const pBuilder
            = reinterpret_cast<ConvexHullBuilder *> (builderVa);
    const Array<ConvexHullBuilder::Face *>& array = pBuilder->GetFaces();
    const Array<ConvexHullBuilder::Face *>::size_type result = array.size();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ConvexHullBuilder
 * Method:    create
 * Signature: (Ljava/nio/FloatBuffer;)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ConvexHullBuilder_create
  (JNIEnv *pEnv, jclass, jobject pointBuffer) {
    const jfloat * const pPoints
            = (jfloat *) pEnv->GetDirectBufferAddress(pointBuffer);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const jlong numFloats = pEnv->GetDirectBufferCapacity(pointBuffer);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const uint64 numPoints = numFloats / 3;
    Array<Vec3> * const pArray = new Array<Vec3>(); // TODO memory leak
    TRACE_NEW("Array<Vec3>", pArray)
    for (uint64 i = 0; i < numPoints; ++i) {
        const jfloat x = pPoints[3 * i];
        const jfloat y = pPoints[3 * i + 1];
        const jfloat z = pPoints[3 * i + 2];
        const Vec3 point(x, y, z);
        pArray->push_back(point);
    }
    ConvexHullBuilder * const pResult = new ConvexHullBuilder(*pArray);
    TRACE_NEW("ConvexHullBuilder", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_ConvexHullBuilder
 * Method:    determineMaxError
 * Signature: (J[J[F[I[F)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ConvexHullBuilder_determineMaxError
  (JNIEnv *pEnv, jclass, jlong builderVa, jlongArray storeFaceVa,
  jfloatArray storeMaxError, jintArray storeMaxErrorPositionIndex,
  jfloatArray storeCoplanarDistance) {
    const ConvexHullBuilder * const pBuilder
            = reinterpret_cast<ConvexHullBuilder *> (builderVa);
    ConvexHullBuilder::Face *pFace;
    float maxError;
    int maxErrorPositionIndex;
    float coplanarDistance;
    pBuilder->DetermineMaxError(
            pFace, maxError, maxErrorPositionIndex, coplanarDistance);
    jboolean isCopy;
    jlong * const pFaceVa = pEnv->GetLongArrayElements(storeFaceVa, &isCopy);
    pFaceVa[0] = reinterpret_cast<jlong> (pFace);
    pEnv->ReleaseLongArrayElements(storeFaceVa, pFaceVa, 0);
    jfloat * const pMaxError
            = pEnv->GetFloatArrayElements(storeMaxError, &isCopy);
    pMaxError[0] = maxError;
    pEnv->ReleaseFloatArrayElements(storeMaxError, pMaxError, 0);
    jint * const pMaxErrorPositionIndex
            = pEnv->GetIntArrayElements(storeMaxErrorPositionIndex, &isCopy);
    pMaxErrorPositionIndex[0] = maxErrorPositionIndex;
    pEnv->ReleaseIntArrayElements(
            storeMaxErrorPositionIndex, pMaxErrorPositionIndex, 0);
    jfloat * const pCoplanarDistance
            = pEnv->GetFloatArrayElements(storeCoplanarDistance, &isCopy);
    pCoplanarDistance[0] = coplanarDistance;
    pEnv->ReleaseFloatArrayElements(
            storeCoplanarDistance, pCoplanarDistance, 0);
}

/*
 * Class:     com_github_stephengold_joltjni_ConvexHullBuilder
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ConvexHullBuilder_free
  (JNIEnv *, jclass, jlong builderVa) {
    ConvexHullBuilder * const pBuilder
            = reinterpret_cast<ConvexHullBuilder *> (builderVa);
    TRACE_DELETE("ConvexHullBuilder", pBuilder)
    delete pBuilder;
}

/*
 * Class:     com_github_stephengold_joltjni_ConvexHullBuilder
 * Method:    getCenterOfMassAndVolume
 * Signature: (J[F)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ConvexHullBuilder_getCenterOfMassAndVolume
  (JNIEnv *pEnv, jclass, jlong builderVa, jfloatArray storeFloats) {
    const ConvexHullBuilder * const pBuilder
            = reinterpret_cast<ConvexHullBuilder *> (builderVa);
    Vec3 com;
    float volume;
    pBuilder->GetCenterOfMassAndVolume(com, volume);
    jboolean isCopy;
    jfloat * const pFloats
            = pEnv->GetFloatArrayElements(storeFloats, &isCopy);
    pFloats[0] = com.GetX();
    pFloats[1] = com.GetY();
    pFloats[2] = com.GetZ();
    pFloats[3] = volume;
    pEnv->ReleaseFloatArrayElements(storeFloats, pFloats, 0);
}

/*
 * Class:     com_github_stephengold_joltjni_ConvexHullBuilder
 * Method:    getFaces
 * Signature: (J[J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ConvexHullBuilder_getFaces
  (JNIEnv *pEnv, jclass, jlong builderVa, jlongArray storeVas) {
    const ConvexHullBuilder * const pBuilder
            = reinterpret_cast<ConvexHullBuilder *> (builderVa);
    const Array<ConvexHullBuilder::Face *>& faces = pBuilder->GetFaces();
    jboolean isCopy;
    jlong * const pFaceVas = pEnv->GetLongArrayElements(storeVas, &isCopy);
    for (size_t i = 0; i < faces.size(); ++i) {
        ConvexHullBuilder::Face * const pFace = faces[i];
        pFaceVas[i] = reinterpret_cast<jlong> (pFace);
    }
    pEnv->ReleaseLongArrayElements(storeVas, pFaceVas, 0);
}

/*
 * Class:     com_github_stephengold_joltjni_ConvexHullBuilder
 * Method:    initialize
 * Signature: (JIF[Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_ConvexHullBuilder_initialize
  (JNIEnv *pEnv, jclass, jlong builderVa, jint maxVertices, jfloat tolerance,
  jobjectArray storeMessage) {
    ConvexHullBuilder * const pBuilder
            = reinterpret_cast<ConvexHullBuilder *> (builderVa);
    const char *pMessage;
    const ConvexHullBuilder::EResult result
            = pBuilder->Initialize(maxVertices, tolerance, pMessage);
    if (result == ConvexHullBuilder::EResult::Success) {
        pMessage = "";
    }
    jstring message = pEnv->NewStringUTF(pMessage);
    pEnv->SetObjectArrayElement(storeMessage, (jsize)0, message);
    return (jint) result;
}