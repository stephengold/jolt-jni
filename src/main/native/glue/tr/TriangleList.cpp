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
#include "Jolt/Geometry/Triangle.h"
#include "auto/com_github_stephengold_joltjni_TriangleList.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_TriangleList
 * Method:    capacity
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_TriangleList_capacity
  (JNIEnv *, jclass, jlong listVa) {
    const TriangleList * const pList
            = reinterpret_cast<TriangleList *> (listVa);
    const TriangleList::size_type result = pList->capacity();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_TriangleList
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_TriangleList_createDefault
  BODYOF_CREATE_DEFAULT(TriangleList)

/*
 * Class:     com_github_stephengold_joltjni_TriangleList
 * Method:    erase
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TriangleList_erase
  (JNIEnv *, jclass, jlong listVa, jint startIndex, jint stopIndex) {
    TriangleList * const pList = reinterpret_cast<TriangleList *> (listVa);
    TriangleList::iterator origin = pList->begin();
    pList->erase(origin + startIndex, origin + stopIndex);
}

/*
 * Class:     com_github_stephengold_joltjni_TriangleList
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TriangleList_free
  BODYOF_FREE(TriangleList)

/*
 * Class:     com_github_stephengold_joltjni_TriangleList
 * Method:    getTriangle
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_TriangleList_getTriangle
  (JNIEnv *, jclass, jlong listVa, jint elementIndex) {
    TriangleList * const pList = reinterpret_cast<TriangleList *> (listVa);
    Triangle& result = pList->at(elementIndex);
    return reinterpret_cast<jlong> (&result);
}

/*
 * Class:     com_github_stephengold_joltjni_TriangleList
 * Method:    resize
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TriangleList_resize
  (JNIEnv *, jclass, jlong listVa, jint numElements) {
    TriangleList * const pList = reinterpret_cast<TriangleList *> (listVa);
    pList->resize(numElements);
}

/*
 * Class:     com_github_stephengold_joltjni_TriangleList
 * Method:    setTriangle
 * Signature: (JIJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TriangleList_setTriangle
  (JNIEnv *, jclass, jlong listVa, jint listIndex, jlong triangleVa) {
    TriangleList * const pList = reinterpret_cast<TriangleList *> (listVa);
    Triangle * const pTriangle = reinterpret_cast<Triangle *> (triangleVa);
    pList->at(listIndex) = *pTriangle;
}

/*
 * Class:     com_github_stephengold_joltjni_TriangleList
 * Method:    size
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_TriangleList_size
  (JNIEnv *, jclass, jlong listVa) {
    const TriangleList * const pList
            = reinterpret_cast<TriangleList *> (listVa);
    const TriangleList::size_type result = pList->size();
    return result;
}