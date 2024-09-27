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
#include "Jolt/Geometry/Triangle.h"
#include "auto/com_github_stephengold_joltjni_Triangle.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_Triangle
 * Method:    createDefaultTriangle
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Triangle_createDefaultTriangle
  (JNIEnv *, jclass) {
    Triangle * const pTriangle = new Triangle();
    TRACE_NEW("Triangle", pTriangle)
    return reinterpret_cast<jlong> (pTriangle);
}

/*
 * Class:     com_github_stephengold_joltjni_Triangle
 * Method:    createTriangle
 * Signature: (FFFFFFFFFII)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Triangle_createTriangle
  (JNIEnv *, jclass, jfloat v1x, jfloat v1y, jfloat v1z, jfloat v2x, jfloat v2y,
  jfloat v2z, jfloat v3x, jfloat v3y, jfloat v3z, jint materialIndex, jint userData) {
    const Vec3 v1(v1x, v1y, v1z);
    const Vec3 v2(v2x, v2y, v2z);
    const Vec3 v3(v3x, v3y, v3z);
    Triangle * const pTriangle
            = new Triangle(v1, v2, v3, materialIndex, userData);
    TRACE_NEW("Triangle", pTriangle)
    return reinterpret_cast<jlong> (pTriangle);
}

/*
 * Class:     com_github_stephengold_joltjni_Triangle
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Triangle_free
  (JNIEnv *, jclass, jlong triangleVa) {
    Triangle * const pTriangle = reinterpret_cast<Triangle *> (triangleVa);
    TRACE_DELETE("Triangle", pTriangle)
    delete pTriangle;
}

/*
 * Class:     com_github_stephengold_joltjni_Triangle
 * Method:    getCoordinate
 * Signature: (JII)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Triangle_getCoordinate
  (JNIEnv *, jclass, jlong triangleVa, jint vertexIndex, jint axisIndex) {
    const Triangle * const pTriangle
            = reinterpret_cast<Triangle *> (triangleVa);
    const float result = pTriangle->mV[vertexIndex][axisIndex];
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Triangle
 * Method:    getMaterialIndex
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Triangle_getMaterialIndex
  (JNIEnv *, jclass, jlong triangleVa) {
    const Triangle * const pTriangle
            = reinterpret_cast<Triangle *> (triangleVa);
    const uint32 result = pTriangle->mMaterialIndex;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Triangle
 * Method:    getUserData
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Triangle_getUserData
  (JNIEnv *, jclass, jlong triangleVa) {
    const Triangle * const pTriangle
            = reinterpret_cast<Triangle *> (triangleVa);
    const uint32 result = pTriangle->mUserData;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Triangle
 * Method:    setMaterialIndex
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Triangle_setMaterialIndex
  (JNIEnv *, jclass, jlong triangleVa, jint materialIndex) {
    Triangle * const pTriangle = reinterpret_cast<Triangle *> (triangleVa);
    pTriangle->mMaterialIndex = materialIndex;
}

/*
 * Class:     com_github_stephengold_joltjni_Triangle
 * Method:    setUserData
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Triangle_setUserData
  (JNIEnv *, jclass, jlong triangleVa, jint userData) {
    Triangle * const pTriangle = reinterpret_cast<Triangle *> (triangleVa);
    pTriangle->mUserData = userData;
}
