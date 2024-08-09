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
#include "auto/com_github_stephengold_joltjni_IndexedTriangleNoMaterial.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_IndexedTriangleNoMaterial
 * Method:    createIndexedTriangleNoMaterial
 * Signature: (III)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_IndexedTriangleNoMaterial_createIndexedTriangleNoMaterial
  (JNIEnv *, jclass, jint vi0, jint vi1, jint vi2) {
    IndexedTriangleNoMaterial * const pTriangle
            = new IndexedTriangleNoMaterial(vi0, vi1, vi2);
    TRACE_NEW("IndexedTriangleNoMaterial", pTriangle)
    return reinterpret_cast<jlong> (pTriangle);
}

/*
 * Class:     com_github_stephengold_joltjni_IndexedTriangleNoMaterial
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_IndexedTriangleNoMaterial_free
  (JNIEnv *, jclass, jlong triangleVa) {
    IndexedTriangleNoMaterial * const pTriangle
           = reinterpret_cast<IndexedTriangleNoMaterial *> (triangleVa);
    TRACE_DELETE("IndexedTriangleNoMaterial", pTriangle)
    delete pTriangle;
}

/*
 * Class:     com_github_stephengold_joltjni_IndexedTriangleNoMaterial
 * Method:    getIdx
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_IndexedTriangleNoMaterial_getIdx
  (JNIEnv *, jclass, jlong triangleVa, jint cornerIndex) {
    const IndexedTriangleNoMaterial * const pTriangle
            = reinterpret_cast<IndexedTriangleNoMaterial *> (triangleVa);
    uint32 result = pTriangle->mIdx[cornerIndex];
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_IndexedTriangleNoMaterial
 * Method:    setIdx
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_IndexedTriangleNoMaterial_setIdx
  (JNIEnv *, jclass, jlong triangleVa, jint cornerIndex, jint meshVertexIndex) {
    IndexedTriangleNoMaterial * const pTriangle
            = reinterpret_cast<IndexedTriangleNoMaterial *> (triangleVa);
    pTriangle->mIdx[cornerIndex] = meshVertexIndex;
}