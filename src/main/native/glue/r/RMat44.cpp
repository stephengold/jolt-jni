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
#include "Jolt/Math/Real.h"
#include "auto/com_github_stephengold_joltjni_RMat44.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_RMat44
 * Method:    createFromRowMajor
 * Signature: ([FDDD)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_RMat44_createFromRowMajor
  (JNIEnv *pEnv, jclass, jfloatArray floatArray,
  jdouble m14, jdouble m24, jdouble m34) {
    jboolean isCopy;
    float * const pArray = pEnv->GetFloatArrayElements(floatArray, &isCopy);
    const Vec4 c1(pArray[0], pArray[3], pArray[6], pArray[9]);
    const Vec4 c2(pArray[1], pArray[4], pArray[7], pArray[10]);
    const Vec4 c3(pArray[2], pArray[5], pArray[8], pArray[11]);
    pEnv->ReleaseFloatArrayElements(floatArray, pArray, JNI_ABORT);
    RVec3 c4(m14, m24, m34);
    RMat44 * const pResult = new RMat44(c1, c2, c3, c4);
    TRACE_NEW("RMat44", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_RMat44
 * Method:    createFromSpMatrix
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_RMat44_createFromSpMatrix
  (JNIEnv *, jclass, jlong spMatrixVa) {
    const Mat44 * const pSpMatrix = reinterpret_cast<Mat44 *> (spMatrixVa);
    RMat44 * const pResult = new RMat44(*pSpMatrix);
    TRACE_NEW("RMat44", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_RMat44
 * Method:    createIdentity
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_RMat44_createIdentity
  (JNIEnv *, jclass) {
    RMat44 * const pResult = new RMat44(RMat44::sIdentity());
    TRACE_NEW("RMat44", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_RMat44
 * Method:    createRotationTranslation
 * Signature: (FFFFDDD)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_RMat44_createRotationTranslation
  (JNIEnv *, jclass, jfloat qx, jfloat qy, jfloat qz, jfloat qw,
  jdouble xx, jdouble yy, jdouble zz) {
    const Quat rotation(qx, qy, qz, qw);
    const RVec3 offset(xx, yy, zz);
    const RMat44 matrix = RMat44::sRotationTranslation(rotation, offset);
    RMat44 * const pResult = new RMat44(matrix);
    TRACE_NEW("RMat44", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_RMat44
 * Method:    createTranslation
 * Signature: (DDD)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_RMat44_createTranslation
  (JNIEnv *, jclass, jdouble xx, jdouble yy, jdouble zz) {
    const RVec3 offset(xx, yy, zz);
    const RMat44 matrix = RMat44::sTranslation(offset);
    RMat44 * const pResult = new RMat44(matrix);
    TRACE_NEW("RMat44", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_RMat44
 * Method:    createUninitialized
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_RMat44_createUninitialized
  (JNIEnv *, jclass) {
    RMat44 * const pResult = new RMat44();
    TRACE_NEW("RMat44", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_RMat44
 * Method:    createZero
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_RMat44_createZero
  (JNIEnv *, jclass) {
    RMat44 * const pResult = new RMat44(RMat44::sZero());
    TRACE_NEW("RMat44", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_RMat44
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RMat44_free
  (JNIEnv *, jclass, jlong matrixVa) {
    RMat44 * const pMatrix = reinterpret_cast<RMat44 *> (matrixVa);
    TRACE_DELETE("RMat44", pMatrix)
    delete pMatrix;
}

/*
 * Class:     com_github_stephengold_joltjni_RMat44
 * Method:    getElement
 * Signature: (JII)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_RMat44_getElement
  (JNIEnv *, jclass, jlong matrixVa, jint row, jint column) {
    const RMat44 * const pMatrix = reinterpret_cast<RMat44 *> (matrixVa);
    const Real result = pMatrix->GetColumn4(column)[row];
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RMat44
 * Method:    getTranslationX
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_RMat44_getTranslationX
  (JNIEnv *, jclass, jlong matrixVa) {
    const RMat44 * const pMatrix = reinterpret_cast<RMat44 *> (matrixVa);
    const Real result = pMatrix->GetTranslation().GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RMat44
 * Method:    getTranslationY
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_RMat44_getTranslationY
  (JNIEnv *, jclass, jlong matrixVa) {
    const RMat44 * const pMatrix = reinterpret_cast<RMat44 *> (matrixVa);
    const Real result = pMatrix->GetTranslation().GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RMat44
 * Method:    getTranslationZ
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_RMat44_getTranslationZ
  (JNIEnv *, jclass, jlong matrixVa) {
    const RMat44 * const pMatrix = reinterpret_cast<RMat44 *> (matrixVa);
    const Real result = pMatrix->GetTranslation().GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RMat44
 * Method:    equals
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_RMat44_equals
  (JNIEnv *, jclass, jlong m1Va, jlong m2Va) {
    const RMat44 * const pM1 = reinterpret_cast<RMat44 *> (m1Va);
    const RMat44 * const pM2 = reinterpret_cast<RMat44 *> (m2Va);
    const bool result = (*pM1 == *pM2);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RMat44
 * Method:    inversed
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_RMat44_inversed
  (JNIEnv *, jclass, jlong currentVa) {
    const RMat44 * const pCurrent = reinterpret_cast<RMat44 *> (currentVa);
    RMat44 * const pResult = new RMat44();
    TRACE_NEW("RMat44", pResult)
    *pResult = pCurrent->Inversed();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_RMat44
 * Method:    multiply3x3
 * Signature: (J[F)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RMat44_multiply3x3
  (JNIEnv *pEnv, jclass, jlong matrixVa, jfloatArray tmpFloats) {
    const RMat44 * const pMatrix = reinterpret_cast<RMat44 *> (matrixVa);
    jboolean isCopy;
    jfloat * const pTmpFloats
            = pEnv->GetFloatArrayElements(tmpFloats, &isCopy);
    const Vec3 vec3Arg(pTmpFloats[0], pTmpFloats[1], pTmpFloats[2]);
    const Vec3 result = pMatrix->Multiply3x3(vec3Arg);
    pTmpFloats[0] = result.GetX();
    pTmpFloats[1] = result.GetY();
    pTmpFloats[2] = result.GetZ();
    pEnv->ReleaseFloatArrayElements(tmpFloats, pTmpFloats, 0);
}

/*
 * Class:     com_github_stephengold_joltjni_RMat44
 * Method:    multiply3x3Transposed
 * Signature: (J[F)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RMat44_multiply3x3Transposed
  (JNIEnv *pEnv, jclass, jlong matrixVa, jfloatArray tmpFloats) {
    const RMat44 * const pMatrix = reinterpret_cast<RMat44 *> (matrixVa);
    jboolean isCopy;
    jfloat * const pTmpFloats = pEnv->GetFloatArrayElements(tmpFloats, &isCopy);
    const Vec3 vec3Arg(pTmpFloats[0], pTmpFloats[1], pTmpFloats[2]);
    const Vec3 result = pMatrix->Multiply3x3Transposed(vec3Arg);
    pTmpFloats[0] = result.GetX();
    pTmpFloats[1] = result.GetY();
    pTmpFloats[2] = result.GetZ();
    pEnv->ReleaseFloatArrayElements(tmpFloats, pTmpFloats, 0);
}

/*
 * Class:     com_github_stephengold_joltjni_RMat44
 * Method:    multiply3x4
 * Signature: (JFFF[D)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RMat44_multiply3x4
  (JNIEnv *pEnv, jclass, jlong matrixVa, jfloat x, jfloat y, jfloat z,
  jdoubleArray array) {
    const RMat44 * const pMatrix = reinterpret_cast<RMat44 *> (matrixVa);
    jboolean isCopy;
    jdouble * const pArray = pEnv->GetDoubleArrayElements(array, &isCopy);
    const Vec3 v(x, y, z);
    const RVec3 result = (*pMatrix) * v;
    pArray[0] = result.GetX();
    pArray[1] = result.GetY();
    pArray[2] = result.GetZ();
    pEnv->ReleaseDoubleArrayElements(array, pArray, 0);
}

/*
 * Class:     com_github_stephengold_joltjni_RMat44
 * Method:    multiply3x4r
 * Signature: (J[D)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RMat44_multiply3x4r
  (JNIEnv *pEnv, jclass, jlong matrixVa, jdoubleArray tmpDoubles) {
    const RMat44 * const pMatrix = reinterpret_cast<RMat44 *> (matrixVa);
    jboolean isCopy;
    jdouble * const pTmpDoubles
            = pEnv->GetDoubleArrayElements(tmpDoubles, &isCopy);
    const RVec3 rvec3Arg(pTmpDoubles[0], pTmpDoubles[1], pTmpDoubles[2]);
    const RVec3 result = (*pMatrix) * rvec3Arg;
    pTmpDoubles[0] = result.GetX();
    pTmpDoubles[1] = result.GetY();
    pTmpDoubles[2] = result.GetZ();
    pEnv->ReleaseDoubleArrayElements(tmpDoubles, pTmpDoubles, 0);
}

/*
 * Class:     com_github_stephengold_joltjni_RMat44
 * Method:    setElement
 * Signature: (JIID)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RMat44_setElement
  (JNIEnv *, jclass, jlong matrixVa, jint row, jint column, jdouble value) {
    RMat44 * const pMatrix = reinterpret_cast<RMat44 *> (matrixVa);
    pMatrix->GetColumn4(column)[row] = value;
}