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
#include <Jolt/Physics/Collision/Shape/ConvexHullShape.h>
#include "auto/com_github_stephengold_joltjni_ConvexHullShape.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_ConvexHullShape
 * Method:    getConvexRadius
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ConvexHullShape_getConvexRadius
  (JNIEnv *, jclass, jlong shapeVa) {
    const ConvexHullShape * const pShape
            = reinterpret_cast<ConvexHullShape *> (shapeVa);
    const float result = pShape->GetConvexRadius();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ConvexHullShape
 * Method:    getFaceVertices
 * Signature: (JII[I)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_ConvexHullShape_getFaceVertices
  (JNIEnv *pEnv, jclass, jlong shapeVa, jint faceIndex, jint maxVertices,
  jintArray storeIndices) {
    const ConvexHullShape * const pShape
            = reinterpret_cast<ConvexHullShape *> (shapeVa);
    uint * const pTempArray = new uint[maxVertices];
    TRACE_NEW("uint[]", pTempArray)
    const uint result
            = pShape->GetFaceVertices(faceIndex, maxVertices, pTempArray);
    jboolean isCopy;
    jint * const pStoreJints = pEnv->GetIntArrayElements(storeIndices, &isCopy);
    for (int i = 0; i < maxVertices; ++i) {
        pStoreJints[i] = pTempArray[i];
    }
    TRACE_DELETE("uint[]", pTempArray)
    delete[] pTempArray;
    pEnv->ReleaseIntArrayElements(storeIndices, pStoreJints, 0);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ConvexHullShape
 * Method:    getNumFaces
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_ConvexHullShape_getNumFaces
  (JNIEnv *, jclass, jlong shapeVa) {
    const ConvexHullShape * const pShape
            = reinterpret_cast<ConvexHullShape *> (shapeVa);
    const uint result = pShape->GetNumFaces();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ConvexHullShape
 * Method:    getNumPoints
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_ConvexHullShape_getNumPoints
  (JNIEnv *, jclass, jlong shapeVa) {
    const ConvexHullShape * const pShape
            = reinterpret_cast<ConvexHullShape *> (shapeVa);
    const uint result = pShape->GetNumPoints();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ConvexHullShape
 * Method:    getNumVerticesInFace
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_ConvexHullShape_getNumVerticesInFace
  (JNIEnv *, jclass, jlong shapeVa, jint faceIndex) {
    const ConvexHullShape * const pShape
            = reinterpret_cast<ConvexHullShape *> (shapeVa);
    const uint result = pShape->GetNumVerticesInFace(faceIndex);
    return result;
}

inline static const Vec3 getPoint(jlong shapeVa, jint pointIndex) {
    const ConvexHullShape * const pShape
            = reinterpret_cast<ConvexHullShape *> (shapeVa);
    const Vec3 result = pShape->GetPoint(pointIndex);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ConvexHullShape
 * Method:    getPointX
 * Signature: (JI)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ConvexHullShape_getPointX
  (JNIEnv *, jclass, jlong shapeVa, jint pointIndex) {
    const Vec3 point = getPoint(shapeVa, pointIndex);
    const float result = point.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ConvexHullShape
 * Method:    getPointY
 * Signature: (JI)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ConvexHullShape_getPointY
  (JNIEnv *, jclass, jlong shapeVa, jint pointIndex) {
    const Vec3 point = getPoint(shapeVa, pointIndex);
    const float result = point.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ConvexHullShape
 * Method:    getPointZ
 * Signature: (JI)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ConvexHullShape_getPointZ
  (JNIEnv *, jclass, jlong shapeVa, jint pointIndex) {
    const Vec3 point = getPoint(shapeVa, pointIndex);
    const float result = point.GetZ();
    return result;
}