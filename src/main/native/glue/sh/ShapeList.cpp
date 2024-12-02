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
#include "Jolt/Physics/Collision/Shape/Shape.h"
#include "auto/com_github_stephengold_joltjni_ShapeList.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_ShapeList
 * Method:    capacity
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_ShapeList_capacity
  (JNIEnv *, jclass, jlong listVa) {
    const ShapeList * const pList = reinterpret_cast<ShapeList *> (listVa);
    const ShapeList::size_type result = pList->capacity();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ShapeList
 * Method:    createEmpty
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ShapeList_createEmpty
  (JNIEnv *, jclass) {
    ShapeList * const pList = new ShapeList();
    TRACE_NEW("ShapeList", pList)
    return reinterpret_cast<jlong> (pList);
}

/*
 * Class:     com_github_stephengold_joltjni_ShapeList
 * Method:    erase
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ShapeList_erase
  (JNIEnv *, jclass, jlong listVa, jint startIndex, jint stopIndex) {
    ShapeList * const pList = reinterpret_cast<ShapeList *> (listVa);
    ShapeList::iterator origin = pList->begin();
    pList->erase(origin + startIndex, origin + stopIndex);
}

/*
 * Class:     com_github_stephengold_joltjni_ShapeList
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ShapeList_free
  (JNIEnv *, jclass, jlong listVa) {
    ShapeList * const pList = reinterpret_cast<ShapeList *> (listVa);
    TRACE_DELETE("ShapeList", pList)
    delete pList;
}

/*
 * Class:     com_github_stephengold_joltjni_ShapeList
 * Method:    getShapeRefC
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ShapeList_getShapeRefC
  (JNIEnv *, jclass, jlong listVa, jint elementIndex) {
    const ShapeList * const pList = reinterpret_cast<ShapeList *> (listVa);
    ShapeRefC * const pResult = new ShapeRefC();
    TRACE_NEW("ShapeRefC", pResult)
    *pResult = pList->at(elementIndex);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_ShapeList
 * Method:    resize
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ShapeList_resize
  (JNIEnv *, jclass, jlong listVa, jint numElements) {
    ShapeList * const pList = reinterpret_cast<ShapeList *> (listVa);
    pList->resize(numElements);
}

/*
 * Class:     com_github_stephengold_joltjni_ShapeList
 * Method:    setShapeRefC
 * Signature: (JIJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ShapeList_setShapeRefC
  (JNIEnv *, jclass, jlong listVa, jint elementIndex, jlong refVa) {
    ShapeList * const pList = reinterpret_cast<ShapeList *> (listVa);
    ShapeRefC * const pRef = reinterpret_cast<ShapeRefC *> (refVa);
    pList->at(elementIndex) = *pRef;
}

/*
 * Class:     com_github_stephengold_joltjni_ShapeList
 * Method:    size
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_ShapeList_size
  (JNIEnv *, jclass, jlong listVa) {
    const ShapeList * const pList = reinterpret_cast<ShapeList *> (listVa);
    const ShapeList::size_type result = pList->size();
    return result;
}