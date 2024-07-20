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
#include <Jolt/Math/Mat44.h>
#include "auto/com_github_stephengold_joltjni_Mat44.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    createFromColumnMajor
 * Signature: ([F)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Mat44_createFromColumnMajor
  (JNIEnv *pEnv, jclass, jfloatArray elementsArray) {
    jboolean isCopy;
    jfloat * const pElements
            = pEnv->GetFloatArrayElements(elementsArray, &isCopy);
    Vec4 column1(pElements[0], pElements[1], pElements[2], pElements[3]);
    Vec4 column2(pElements[4], pElements[5], pElements[6], pElements[7]);
    Vec4 column3(pElements[8], pElements[9], pElements[10], pElements[11]);
    Vec4 column4(pElements[12], pElements[13], pElements[14], pElements[15]);
    pEnv->ReleaseFloatArrayElements(elementsArray, pElements, JNI_ABORT);
    Mat44 * const pResult = new Mat44(column1, column2, column3, column4);
    TRACE_NEW("Mat44", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    createUninitialized
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Mat44_createUninitialized
  (JNIEnv *, jclass) {
    Mat44 * const pResult = new Mat44();
    TRACE_NEW("Mat44", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Mat44_free
  (JNIEnv *, jclass, jlong matrixVa) {
    Mat44 * const pMatrix = reinterpret_cast<Mat44 *> (matrixVa);
    TRACE_DELETE("Mat44", pMatrix)
    delete pMatrix;
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    getElement
 * Signature: (JII)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Mat44_getElement
  (JNIEnv *, jclass, jlong matrixVa, jint row, jint column) {
    const Mat44 * const pMatrix = reinterpret_cast<Mat44 *> (matrixVa);
    const float result = pMatrix->GetColumn4(column)[row];
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    getQuaternion
 * Signature: (J[F)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Mat44_getQuaternion
  (JNIEnv *pEnv, jclass, jlong matrixVa, jfloatArray storeArray) {
    const Mat44 * const pMatrix = reinterpret_cast<Mat44 *> (matrixVa);
    Quat quat = pMatrix->GetQuaternion();
    jboolean isCopy;
    jfloat * const pStore = pEnv->GetFloatArrayElements(storeArray, &isCopy);
    pStore[0] = quat.GetX();
    pStore[1] = quat.GetY();
    pStore[2] = quat.GetZ();
    pStore[3] = quat.GetW();
    pEnv->ReleaseFloatArrayElements(storeArray, pStore, 0);
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    multiply3x3
 * Signature: (JJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Mat44_multiply3x3__JJ
  (JNIEnv *, jclass, jlong currentVa, jlong argVa) {
    const Mat44 * const pCurrent = reinterpret_cast<Mat44 *> (currentVa);
    const Mat44 * const pArg = reinterpret_cast<Mat44 *> (argVa);
    Mat44 * const pResult = new Mat44();
    TRACE_NEW("Mat44", pResult)
    *pResult = pCurrent->Multiply3x3(*pArg);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    multiply3x3
 * Signature: (J[F)J
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Mat44_multiply3x3__J_3F
  (JNIEnv *pEnv, jclass, jlong matrixVa, jfloatArray array) {
    const Mat44 * const pMatrix = reinterpret_cast<Mat44 *> (matrixVa);
    jboolean isCopy;
    jfloat * const pArray = pEnv->GetFloatArrayElements(array, &isCopy);
    Vec3 v(pArray[0], pArray[1], pArray[2]);
    Vec3 result = pMatrix->Multiply3x3(v);
    pArray[0] = result.GetX();
    pArray[1] = result.GetY();
    pArray[2] = result.GetZ();
    pEnv->ReleaseFloatArrayElements(array, pArray, 0);
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    multiply3x3Transposed
 * Signature: (J[F)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Mat44_multiply3x3Transposed
  (JNIEnv *pEnv, jclass, jlong matrixVa, jfloatArray array) {
    const Mat44 * const pMatrix = reinterpret_cast<Mat44 *> (matrixVa);
    jboolean isCopy;
    jfloat * const pArray = pEnv->GetFloatArrayElements(array, &isCopy);
    Vec3 v(pArray[0], pArray[1], pArray[2]);
    Vec3 result = pMatrix->Multiply3x3Transposed(v);
    pArray[0] = result.GetX();
    pArray[1] = result.GetY();
    pArray[2] = result.GetZ();
    pEnv->ReleaseFloatArrayElements(array, pArray, 0);
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    setElement
 * Signature: (JIIF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Mat44_setElement
  (JNIEnv *, jclass, jlong matrixVa, jint row, jint column, jfloat value) {
    Mat44 * const pMatrix = reinterpret_cast<Mat44 *> (matrixVa);
    pMatrix->GetColumn4(column)[row] = value;
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    sRotation
 * Signature: (FFFF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Mat44_sRotation
  (JNIEnv *, jclass, jfloat x, jfloat y, jfloat z, jfloat w) {
    const Quat rotation(x, y, z, w);
    Mat44 * const pResult = new Mat44();
    TRACE_NEW("Mat44", pResult)
    *pResult = Mat44::sRotation(rotation);
    return reinterpret_cast<jlong> (pResult);
}
