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
#include "Jolt/Physics/Collision/Shape/Shape.h"
#include "auto/com_github_stephengold_joltjni_ShapeRefCArray.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_ShapeRefCArray
 * Method:    create
 * Signature: (I)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ShapeRefCArray_create
  (JNIEnv *, jclass, jint length) {
    ShapeRefC * const pArray = new ShapeRefC[length];
    TRACE_NEW("ShapeRefC[]", pArray)
    return reinterpret_cast<jlong> (pArray);
}

/*
 * Class:     com_github_stephengold_joltjni_ShapeRefCArray
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ShapeRefCArray_free
  (JNIEnv *, jclass, jlong arrayVa) {
    ShapeRefC * const pArray = reinterpret_cast<ShapeRefC *> (arrayVa);
    TRACE_DELETE("ShapeRefC[]", pArray)
    delete[] pArray;
}

/*
 * Class:     com_github_stephengold_joltjni_ShapeRefCArray
 * Method:    getRef
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ShapeRefCArray_getRef
  (JNIEnv *, jclass, jlong arrayVa, jint elementIndex) {
    ShapeRefC * const pArray = reinterpret_cast<ShapeRefC *> (arrayVa);
    ShapeRefC * const pResult = &pArray[elementIndex];
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_ShapeRefCArray
 * Method:    setRef
 * Signature: (JIJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ShapeRefCArray_setRef
  (JNIEnv *, jclass, jlong arrayVa, jint elementIndex, jlong refVa) {
    ShapeRefC * const pArray = reinterpret_cast<ShapeRefC *> (arrayVa);
    const ShapeRefC * const pValue = reinterpret_cast<ShapeRefC *> (refVa);
    pArray[elementIndex] = *pValue;
}