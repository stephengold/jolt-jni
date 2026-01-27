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
 * Author: Stephen Gold
 */
#include "Jolt/Jolt.h"
#include "Jolt/Physics/Hair/HairSettings.h"
#include "auto/com_github_stephengold_joltjni_SVertexList.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_SVertexList
 * Method:    capacity
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SVertexList_capacity
  (JNIEnv *, jclass, jlong listVa) {
    const Array<HairSettings::SVertex> * const pList
            = reinterpret_cast<Array<HairSettings::SVertex> *> (listVa);
    const Array<HairSettings::SVertex>::size_type result = pList->capacity();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SVertexList
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SVertexList_createDefault
  BODYOF_CREATE_DEFAULT(Array<HairSettings::SVertex>)

/*
 * Class:     com_github_stephengold_joltjni_SVertexList
 * Method:    erase
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SVertexList_erase
  (JNIEnv *, jclass, jlong listVa, jint startIndex, jint stopIndex) {
    Array<HairSettings::SVertex> * const pList
            = reinterpret_cast<Array<HairSettings::SVertex> *> (listVa);
    Array<HairSettings::SVertex>::iterator origin = pList->begin();
    pList->erase(origin + startIndex, origin + stopIndex);
}

/*
 * Class:     com_github_stephengold_joltjni_SVertexList
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SVertexList_free
  BODYOF_FREE(Array<HairSettings::SVertex>)

/*
 * Class:     com_github_stephengold_joltjni_SVertexList
 * Method:    getVertex
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SVertexList_getVertex
  (JNIEnv *, jclass, jlong listVa, jint elementIndex) {
    Array<HairSettings::SVertex> * const pList
            = reinterpret_cast<Array<HairSettings::SVertex> *> (listVa);
    HairSettings::SVertex& result = pList->at(elementIndex);
    return reinterpret_cast<jlong> (&result);
}

/*
 * Class:     com_github_stephengold_joltjni_SVertexList
 * Method:    resize
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SVertexList_resize
  (JNIEnv *, jclass, jlong listVa, jint numElements) {
    Array<HairSettings::SVertex> * const pList
            = reinterpret_cast<Array<HairSettings::SVertex> *> (listVa);
    pList->resize(numElements);
}

/*
 * Class:     com_github_stephengold_joltjni_SVertexList
 * Method:    setVertex
 * Signature: (JIJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SVertexList_setVertex
  (JNIEnv *, jclass, jlong listVa, jint listIndex, jlong vertexVa) {
    Array<HairSettings::SVertex> * const pList
            = reinterpret_cast<Array<HairSettings::SVertex> *> (listVa);
    const HairSettings::SVertex * const pVertex
            = reinterpret_cast<HairSettings::SVertex *> (vertexVa);
    pList->at(listIndex) = *pVertex;
}

/*
 * Class:     com_github_stephengold_joltjni_SVertexList
 * Method:    size
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SVertexList_size
  (JNIEnv *, jclass, jlong listVa) {
    const Array<HairSettings::SVertex> * const pList
            = reinterpret_cast<Array<HairSettings::SVertex> *> (listVa);
    const Array<HairSettings::SVertex>::size_type result = pList->size();
    return result;
}