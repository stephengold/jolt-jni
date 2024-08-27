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
#include <Jolt/Geometry/IndexedTriangle.h>
#include "auto/com_github_stephengold_joltjni_IndexedTriangle.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_IndexedTriangle
 * Method:    createIndexedTriangle
 * Signature: (IIII)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_IndexedTriangle_createIndexedTriangle
  (JNIEnv *, jclass, jint vi0, jint vi1, jint vi2, jint materialIndex) {
    IndexedTriangle * const pTriangle
            = new IndexedTriangle(vi0, vi1, vi2, materialIndex);
    TRACE_NEW("IndexedTriangle", pTriangle)
    return reinterpret_cast<jlong> (pTriangle);
}

/*
 * Class:     com_github_stephengold_joltjni_IndexedTriangle
 * Method:    getMaterialIndex
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_IndexedTriangle_getMaterialIndex
  (JNIEnv *, jclass, jlong triangleVa) {
    const IndexedTriangle * const pTriangle
            = reinterpret_cast<IndexedTriangle *> (triangleVa);
    const uint32 result = pTriangle->mMaterialIndex;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_IndexedTriangle
 * Method:    setMaterialIndex
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_IndexedTriangle_setMaterialIndex
  (JNIEnv *, jclass, jlong triangleVa, jint materialIndex) {
    IndexedTriangle * const pTriangle
            = reinterpret_cast<IndexedTriangle *> (triangleVa);
    pTriangle->mMaterialIndex = materialIndex;
}