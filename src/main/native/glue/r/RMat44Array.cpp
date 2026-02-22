/*
Copyright (c) 2026 Stephen Gold

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
 * Author: xI-Mx-Ix
 */
#include "Jolt/Jolt.h"
#include "Jolt/Math/Mat44.h"
#include "Jolt/Math/DMat44.h"
#include "auto/com_github_stephengold_joltjni_RMat44Array.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_RMat44Array
 * Method:    create
 * Signature: (I)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_RMat44Array_create
  (JNIEnv *, jclass, jint length) {
    RMat44 * const pArray = new RMat44[length];
    TRACE_NEW("RMat44[]", pArray)
    for (int32_t i = 0; i < length; ++i) {
        pArray[i] = RMat44::sIdentity();
    }
    return reinterpret_cast<jlong> (pArray);
}

/*
 * Class:     com_github_stephengold_joltjni_RMat44Array
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RMat44Array_free
  (JNIEnv *, jclass, jlong arrayVa) {
    RMat44 * const pArray = reinterpret_cast<RMat44 *> (arrayVa);
    TRACE_DELETE("RMat44[]", pArray)
    delete[] pArray;
}

/*
 * Class:     com_github_stephengold_joltjni_RMat44Array
 * Method:    getMatrix
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_RMat44Array_getMatrix
  (JNIEnv *, jclass, jlong arrayVa, jint elementIndex) {
    RMat44 * const pArray = reinterpret_cast<RMat44 *> (arrayVa);
    RMat44 * const pResult = &pArray[elementIndex];
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_RMat44Array
 * Method:    setMatrix
 * Signature: (JIJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RMat44Array_setMatrix
  (JNIEnv *, jclass, jlong arrayVa, jint elementIndex, jlong matrixVa) {
    RMat44 * const pArray = reinterpret_cast<RMat44 *> (arrayVa);
    const RMat44 * const pValue = reinterpret_cast<RMat44 *> (matrixVa);
    pArray[elementIndex] = *pValue;
}