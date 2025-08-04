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
#include "Jolt/Math/Mat44.h"
#include "auto/com_github_stephengold_joltjni_Mat44.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    assign
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Mat44_assign
  (JNIEnv *, jclass, jlong targetVa, jlong sourceVa) {
    Mat44 * const pTarget = reinterpret_cast<Mat44 *> (targetVa);
    const Mat44 * const pSource = reinterpret_cast<Mat44 *> (sourceVa);
    *pTarget = *pSource;
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Mat44_createCopy
  BODYOF_CREATE_COPY(Mat44)

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
    const Vec4 column1(pElements[0], pElements[1], pElements[2], pElements[3]);
    const Vec4 column2(pElements[4], pElements[5], pElements[6], pElements[7]);
    const Vec4 column3(pElements[8], pElements[9], pElements[10], pElements[11]);
    const Vec4 column4(pElements[12], pElements[13], pElements[14], pElements[15]);
    pEnv->ReleaseFloatArrayElements(elementsArray, pElements, JNI_ABORT);
    Mat44 * const pResult = new Mat44(column1, column2, column3, column4);
    TRACE_NEW("Mat44", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    createFromRMatrix
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Mat44_createFromRMatrix
  (JNIEnv *, jclass, jlong rMatrixVa) {
    RMat44 * const pRMatrix = reinterpret_cast<RMat44 *> (rMatrixVa);
    Mat44 * const pResult = new Mat44(pRMatrix->ToMat44());
    TRACE_NEW("Mat44", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    createIdentity
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Mat44_createIdentity
  (JNIEnv *, jclass) {
    Mat44 * const pResult = new Mat44(Mat44::sIdentity());
    TRACE_NEW("Mat44", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    createRotation
 * Signature: (FFFF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Mat44_createRotation
  (JNIEnv *, jclass, jfloat rx, jfloat ry, jfloat rz, jfloat rw) {
    const Quat rotation(rx, ry, rz, rw);
    Mat44 * const pResult = new Mat44();
    TRACE_NEW("Mat44", pResult)
    *pResult = Mat44::sRotation(rotation);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    createRotationAxisAngle
 * Signature: (FFFF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Mat44_createRotationAxisAngle
  (JNIEnv *, jclass, jfloat ax, jfloat ay, jfloat az, jfloat angle) {
    const Vec3 axis(ax, ay, az);
    Mat44 * const pResult = new Mat44();
    TRACE_NEW("Mat44", pResult)
    *pResult = Mat44::sRotation(axis, angle);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    createRotationTranslation
 * Signature: ([F)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Mat44_createRotationTranslation
  (JNIEnv *pEnv, jclass, jfloatArray floatArray) {
    jboolean isCopy;
    jfloat * const pFloats = pEnv->GetFloatArrayElements(floatArray, &isCopy);
    const Quat rotation(pFloats[0], pFloats[1], pFloats[2], pFloats[3]);
    const Vec3 offset(pFloats[4], pFloats[5], pFloats[6]);
    pEnv->ReleaseFloatArrayElements(floatArray, pFloats, JNI_ABORT);
    Mat44 * const pResult = new Mat44();
    TRACE_NEW("Mat44", pResult)
    *pResult = Mat44::sRotationTranslation(rotation, offset);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    createRotationX
 * Signature: (F)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Mat44_createRotationX
  (JNIEnv *, jclass, jfloat angle) {
    Mat44 * const pResult = new Mat44(Mat44::sRotationX(angle));
    TRACE_NEW("Mat44", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    createRotationY
 * Signature: (F)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Mat44_createRotationY
  (JNIEnv *, jclass, jfloat angle) {
    Mat44 * const pResult = new Mat44(Mat44::sRotationY(angle));
    TRACE_NEW("Mat44", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    createRotationZ
 * Signature: (F)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Mat44_createRotationZ
  (JNIEnv *, jclass, jfloat angle) {
    Mat44 * const pResult = new Mat44(Mat44::sRotationZ(angle));
    TRACE_NEW("Mat44", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    createScale
 * Signature: (FFF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Mat44_createScale
  (JNIEnv *, jclass, jfloat sx, jfloat sy, jfloat sz) {
    const Vec3 factors(sx, sy, sz);
    Mat44 * const pResult = new Mat44();
    TRACE_NEW("Mat44", pResult)
    *pResult = Mat44::sScale(factors);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    createTranslation
 * Signature: (FFF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Mat44_createTranslation
  (JNIEnv *, jclass, jfloat x, jfloat y, jfloat z) {
    const Vec3 offset(x, y, z);
    Mat44 * const pResult = new Mat44();
    TRACE_NEW("Mat44", pResult)
    *pResult = Mat44::sTranslation(offset);
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
 * Method:    createZero
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Mat44_createZero
  (JNIEnv *, jclass) {
    Mat44 * const pResult = new Mat44(Mat44::sZero());
    TRACE_NEW("Mat44", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    equals
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_Mat44_equals
  (JNIEnv *, jclass, jlong m1va, jlong m2va) {
    const Mat44 * const pM1 = reinterpret_cast<Mat44 *> (m1va);
    const Mat44 * const pM2 = reinterpret_cast<Mat44 *> (m2va);
    const bool result = (*pM1 == *pM2);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Mat44_free
  BODYOF_FREE(Mat44)

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    getElement
 * Signature: (JII)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Mat44_getElement
  (JNIEnv *, jclass, jlong matrixVa, jint row, jint column) {
    const Mat44 * const pMatrix = reinterpret_cast<Mat44 *> (matrixVa);
    const float result = (*pMatrix)(row, column);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    getQuaternion
 * Signature: (J[F)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Mat44_getQuaternion
  (JNIEnv *pEnv, jclass, jlong matrixVa, jfloatArray storeFloats) {
    const Mat44 * const pMatrix = reinterpret_cast<Mat44 *> (matrixVa);
    const Quat quat = pMatrix->GetQuaternion();
    jboolean isCopy;
    jfloat * const pStore = pEnv->GetFloatArrayElements(storeFloats, &isCopy);
    pStore[0] = quat.GetX();
    pStore[1] = quat.GetY();
    pStore[2] = quat.GetZ();
    pStore[3] = quat.GetW();
    pEnv->ReleaseFloatArrayElements(storeFloats, pStore, 0);
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    getTranslationX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Mat44_getTranslationX
  (JNIEnv *, jclass, jlong matrixVa) {
    const Mat44 * const pMatrix = reinterpret_cast<Mat44 *> (matrixVa);
    const float result = pMatrix->GetTranslation().GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    getTranslationY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Mat44_getTranslationY
  (JNIEnv *, jclass, jlong matrixVa) {
    const Mat44 * const pMatrix = reinterpret_cast<Mat44 *> (matrixVa);
    const float result = pMatrix->GetTranslation().GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    getTranslationZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Mat44_getTranslationZ
  (JNIEnv *, jclass, jlong matrixVa) {
    const Mat44 * const pMatrix = reinterpret_cast<Mat44 *> (matrixVa);
    const float result = pMatrix->GetTranslation().GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    inversed
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Mat44_inversed
  (JNIEnv *, jclass, jlong currentVa) {
    const Mat44 * const pMatrix = reinterpret_cast<Mat44 *> (currentVa);
    Mat44 * const pResult = new Mat44();
    TRACE_NEW("Mat44", pResult)
    *pResult = pMatrix->Inversed();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    inversed3x3
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Mat44_inversed3x3
  (JNIEnv *, jclass, jlong currentVa) {
    const Mat44 * const pCurrent = reinterpret_cast<Mat44 *> (currentVa);
    Mat44 * const pResult = new Mat44();
    TRACE_NEW("Mat44", pResult)
    *pResult = pCurrent->Inversed3x3();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    inversedRotationTranslation
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Mat44_inversedRotationTranslation
  (JNIEnv *, jclass, jlong currentVa) {
    const Mat44 * const pCurrent = reinterpret_cast<Mat44 *> (currentVa);
    Mat44 * const pResult = new Mat44();
    TRACE_NEW("Mat44", pResult)
    *pResult = pCurrent->InversedRotationTranslation();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    isIdentity
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_Mat44_isIdentity
  (JNIEnv *, jclass, jlong matrixVa) {
    const Mat44 * const pMatrix = reinterpret_cast<Mat44 *> (matrixVa);
    const bool result = (*pMatrix == Mat44::sIdentity());
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    leftMultiplyInPlace
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Mat44_leftMultiplyInPlace
  (JNIEnv *, jclass, jlong currentVa, jlong leftVa) {
    Mat44 * const pCurrent = reinterpret_cast<Mat44 *> (currentVa);
    const Mat44 * const pLeft = reinterpret_cast<Mat44 *> (leftVa);
    *pCurrent = (*pLeft) * (*pCurrent);
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    loadIdentity
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Mat44_loadIdentity
  (JNIEnv *, jclass, jlong matrixVa) {
    Mat44 * const pMatrix = reinterpret_cast<Mat44 *> (matrixVa);
    *pMatrix = Mat44::sIdentity();
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    multiply
 * Signature: (JJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Mat44_multiply
  (JNIEnv *, jclass, jlong leftVa, jlong rightVa) {
    const Mat44 * const pLeft = reinterpret_cast<Mat44 *> (leftVa);
    const Mat44 * const pRight = reinterpret_cast<Mat44 *> (rightVa);
    Mat44 * const pResult = new Mat44();
    TRACE_NEW("Mat44", pResult)
    *pResult = (*pLeft) * (*pRight);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    multiply3x3
 * Signature: (JJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Mat44_multiply3x3__JJ
  (JNIEnv *, jclass, jlong leftVa, jlong rightVa) {
    const Mat44 * const pLeft = reinterpret_cast<Mat44 *> (leftVa);
    const Mat44 * const pRight = reinterpret_cast<Mat44 *> (rightVa);
    Mat44 * const pResult = new Mat44();
    TRACE_NEW("Mat44", pResult)
    *pResult = pLeft->Multiply3x3(*pRight);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    multiply3x3
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Mat44_multiply3x3__JLjava_nio_FloatBuffer_2
  (JNIEnv *pEnv, jclass, jlong matrixVa, jobject floatBuffer) {
    const Mat44 * const pMatrix = reinterpret_cast<Mat44 *> (matrixVa);
    DIRECT_FLOAT_BUFFER(pEnv, floatBuffer, pArray, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3 v(pArray[0], pArray[1], pArray[2]);
    const Vec3 result = pMatrix->Multiply3x3(v);
    pArray[0] = result.GetX();
    pArray[1] = result.GetY();
    pArray[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    multiply3x3Transposed
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Mat44_multiply3x3Transposed
  (JNIEnv *pEnv, jclass, jlong matrixVa, jobject floatBuffer) {
    const Mat44 * const pMatrix = reinterpret_cast<Mat44 *> (matrixVa);
    DIRECT_FLOAT_BUFFER(pEnv, floatBuffer, pArray, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3 v(pArray[0], pArray[1], pArray[2]);
    const Vec3 result = pMatrix->Multiply3x3Transposed(v);
    pArray[0] = result.GetX();
    pArray[1] = result.GetY();
    pArray[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    multiply3x4
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Mat44_multiply3x4
  (JNIEnv *pEnv, jclass, jlong matrixVa, jobject floatBuffer) {
    const Mat44 * const pMatrix = reinterpret_cast<Mat44 *> (matrixVa);
    DIRECT_FLOAT_BUFFER(pEnv, floatBuffer, pArray, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3 v(pArray[0], pArray[1], pArray[2]);
    const Vec3 result = (*pMatrix) * v;
    pArray[0] = result.GetX();
    pArray[1] = result.GetY();
    pArray[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    postTranslated
 * Signature: (JFFF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Mat44_postTranslated
  (JNIEnv *, jclass, jlong matrixVa, jfloat x, jfloat y, jfloat z) {
    const Mat44 * const pMatrix = reinterpret_cast<Mat44 *> (matrixVa);
    const Vec3 offset(x, y, z);
    Mat44 * const pResult = new Mat44();
    TRACE_NEW("Mat44", pResult)
    *pResult = pMatrix->PostTranslated(offset);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    putColumnMajor
 * Signature: (JILjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Mat44_putColumnMajor
  (JNIEnv *pEnv, jclass, jlong matrixVa, jint position, jobject storeFloats) {
    const Mat44 * const pMatrix = reinterpret_cast<Mat44 *> (matrixVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pBuffer, capacityFloats);
    for (size_t c = 0; c < 4; ++c) {
        const size_t baseIndex = position + 4 * c;
        for (size_t r = 0; r < 4; ++r) {
            if (baseIndex + r >= capacityFloats) {
                break;
            }
            pBuffer[baseIndex + r] = (*pMatrix)(r, c);
        }
    }
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    rightMultiplyInPlace
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Mat44_rightMultiplyInPlace
  (JNIEnv *, jclass, jlong currentVa, jlong rightVa) {
    Mat44 * const pCurrent = reinterpret_cast<Mat44 *> (currentVa);
    const Mat44 * const pRight = reinterpret_cast<Mat44 *> (rightVa);
    *pCurrent = (*pCurrent) * (*pRight);
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    setAxisX
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Mat44_setAxisX
  (JNIEnv *, jclass, jlong matrixVa, jfloat x , jfloat y, jfloat z) {
    Mat44 * const pMatrix = reinterpret_cast<Mat44 *> (matrixVa);
    const Vec3 vec(x, y, z);
    pMatrix->SetAxisX(vec);
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    setAxisY
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Mat44_setAxisY
  (JNIEnv *, jclass, jlong matrixVa, jfloat x , jfloat y, jfloat z) {
    Mat44 * const pMatrix = reinterpret_cast<Mat44 *> (matrixVa);
    const Vec3 vec(x, y, z);
    pMatrix->SetAxisY(vec);
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    setAxisZ
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Mat44_setAxisZ
  (JNIEnv *, jclass, jlong matrixVa, jfloat x , jfloat y, jfloat z) {
    Mat44 * const pMatrix = reinterpret_cast<Mat44 *> (matrixVa);
    const Vec3 vec(x, y, z);
    pMatrix->SetAxisZ(vec);
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44
 * Method:    setDiagonal3
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Mat44_setDiagonal3
  (JNIEnv *, jclass, jlong matrixVa, jfloat x, jfloat y, jfloat z) {
    Mat44 * const pMatrix = reinterpret_cast<Mat44 *> (matrixVa);
    const Vec3 vec(x, y, z);
    pMatrix->SetDiagonal3(vec);
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
 * Method:    setTranslation
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Mat44_setTranslation
  (JNIEnv *, jclass, jlong matrixVa, jfloat x , jfloat y, jfloat z) {
    Mat44 * const pMatrix = reinterpret_cast<Mat44 *> (matrixVa);
    const Vec3 vec(x, y, z);
    pMatrix->SetTranslation(vec);
}