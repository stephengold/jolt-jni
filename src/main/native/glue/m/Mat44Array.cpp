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
#include "auto/com_github_stephengold_joltjni_Mat44Array.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_Mat44Array
 * Method:    create
 * Signature: (I)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Mat44Array_create
  (JNIEnv *, jclass, jint length) {
    Mat44 * const pArray = new Mat44[length];
    TRACE_NEW("Mat44[]", pArray)
    for (int32_t i = 0; i < length; ++i) {
        pArray[i] = Mat44::sIdentity();
    }
    return reinterpret_cast<jlong> (pArray);
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44Array
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Mat44Array_free
  (JNIEnv *, jclass, jlong arrayVa) {
    Mat44 * const pArray = reinterpret_cast<Mat44 *> (arrayVa);
    TRACE_DELETE("Mat44[]", pArray)
    delete[] pArray;
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44Array
 * Method:    getMatrix
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Mat44Array_getMatrix
  (JNIEnv *, jclass, jlong arrayVa, jint elementIndex) {
    Mat44 * const pArray = reinterpret_cast<Mat44 *> (arrayVa);
    Mat44 * const pResult = &pArray[elementIndex];
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Mat44Array
 * Method:    setMatrix
 * Signature: (JIJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Mat44Array_setMatrix
  (JNIEnv *, jclass, jlong arrayVa, jint elementIndex, jlong matrixVa) {
    Mat44 * const pArray = reinterpret_cast<Mat44 *> (arrayVa);
    const Mat44 * const pId = reinterpret_cast<Mat44 *> (matrixVa);
    pArray[elementIndex] = *pId;
}