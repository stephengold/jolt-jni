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
#include "Jolt/Physics/Collision/PhysicsMaterial.h"
#include "auto/com_github_stephengold_joltjni_PhysicsMaterialList.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_PhysicsMaterialList
 * Method:    capacity
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_PhysicsMaterialList_capacity
  (JNIEnv *, jclass, jlong listVa) {
    const PhysicsMaterialList * const pList
            = reinterpret_cast<PhysicsMaterialList *> (listVa);
    const PhysicsMaterialList::size_type result = pList->capacity();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsMaterialList
 * Method:    createEmptyList
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PhysicsMaterialList_createEmptyList
  BODYOF_CREATE_DEFAULT(PhysicsMaterialList)

/*
 * Class:     com_github_stephengold_joltjni_PhysicsMaterialList
 * Method:    erase
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsMaterialList_erase
  (JNIEnv *, jclass, jlong listVa, jint startIndex, jint stopIndex) {
    PhysicsMaterialList * const pList
            = reinterpret_cast<PhysicsMaterialList *> (listVa);
    PhysicsMaterialList::iterator origin = pList->begin();
    pList->erase(origin + startIndex, origin + stopIndex);
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsMaterialList
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsMaterialList_free
  BODYOF_FREE(PhysicsMaterialList)

/*
 * Class:     com_github_stephengold_joltjni_PhysicsMaterialList
 * Method:    get
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PhysicsMaterialList_get
  (JNIEnv *, jclass, jlong listVa, jint elementIndex) {
    PhysicsMaterialList * const pList
            = reinterpret_cast<PhysicsMaterialList *> (listVa);
    RefConst<PhysicsMaterial>& element = pList->at(elementIndex);
    RefConst<PhysicsMaterial>* const pResult
            = new RefConst<PhysicsMaterial>(element);
    TRACE_NEW("RefConst<PhysicsMaterial>", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsMaterialList
 * Method:    resize
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsMaterialList_resize
  (JNIEnv *, jclass, jlong listVa, jint numRefs) {
    PhysicsMaterialList * const pList
            = reinterpret_cast<PhysicsMaterialList *> (listVa);
    pList->resize(numRefs);
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsMaterialList
 * Method:    set
 * Signature: (JIJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsMaterialList_set
  (JNIEnv *, jclass, jlong listVa, jint elementIndex, jlong refVa) {
    PhysicsMaterialList * const pList
            = reinterpret_cast<PhysicsMaterialList *> (listVa);
    RefConst<PhysicsMaterial> * const pRef
            = reinterpret_cast<RefConst<PhysicsMaterial> *> (refVa);
    RefConst<PhysicsMaterial> * const pNewRef
            = new RefConst<PhysicsMaterial>(*pRef);
    TRACE_NEW("RefConst<PhysicsMaterial>", pNewRef)
    pList->at(elementIndex) = *pNewRef;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsMaterialList
 * Method:    size
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_PhysicsMaterialList_size
  (JNIEnv *, jclass, jlong listVa) {
    const PhysicsMaterialList * const pList
            = reinterpret_cast<PhysicsMaterialList *> (listVa);
    PhysicsMaterialList::size_type result = pList->size();
    return result;
}