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
#include <Jolt/Math/Real.h>
#include "auto/com_github_stephengold_joltjni_RMat44.h"
#include "glue/glue.h"

using namespace JPH;

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
 * Method:    setElement
 * Signature: (JIID)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RMat44_setElement
  (JNIEnv *, jclass, jlong matrixVa, jint row, jint column, jfloat value) {
    RMat44 * const pMatrix = reinterpret_cast<RMat44 *> (matrixVa);
    pMatrix->GetColumn4(column)[row] = value;
}