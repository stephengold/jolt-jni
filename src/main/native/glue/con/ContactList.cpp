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
#include "Jolt/Physics/Character/CharacterVirtual.h"
#include "auto/com_github_stephengold_joltjni_ContactList.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_ContactList
 * Method:    capacity
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_ContactList_capacity
  (JNIEnv *, jclass, jlong listVa) {
    const CharacterVirtual::ContactList * const pList
            = reinterpret_cast<CharacterVirtual::ContactList *> (listVa);
    const CharacterVirtual::ContactList::size_type result = pList->capacity();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ContactList
 * Method:    erase
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ContactList_erase
  (JNIEnv *, jclass, jlong listVa, jint startIndex, jint stopIndex) {
    CharacterVirtual::ContactList * const pList
            = reinterpret_cast<CharacterVirtual::ContactList *> (listVa);
    CharacterVirtual::ContactList::iterator origin = pList->begin();
    pList->erase(origin + startIndex, origin + stopIndex);
}

/*
 * Class:     com_github_stephengold_joltjni_ContactList
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ContactList_free
  (JNIEnv *, jclass, jlong listVa) {
    CharacterVirtual::ContactList * const pList
            = reinterpret_cast<CharacterVirtual::ContactList *> (listVa);
    TRACE_DELETE("ContactList", pList)
    delete pList;
}

/*
 * Class:     com_github_stephengold_joltjni_ContactList
 * Method:    get
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ContactList_get
  (JNIEnv *, jclass, jlong listVa, jint elementIndex) {
    CharacterVirtual::ContactList * const pList
            = reinterpret_cast<CharacterVirtual::ContactList *> (listVa);
    CharacterVirtual::Contact& result = pList->at(elementIndex);
    return reinterpret_cast<jlong> (&result);
}

/*
 * Class:     com_github_stephengold_joltjni_ContactList
 * Method:    resize
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ContactList_resize
  (JNIEnv *, jclass, jlong listVa, jint numElements) {
    CharacterVirtual::ContactList * const pList
            = reinterpret_cast<CharacterVirtual::ContactList *> (listVa);
    pList->resize(numElements);
}

/*
 * Class:     com_github_stephengold_joltjni_ContactList
 * Method:    set
 * Signature: (JIJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ContactList_set
  (JNIEnv *, jclass, jlong listVa, jint elementIndex, jlong contactVa) {
    CharacterVirtual::ContactList * const pList
            = reinterpret_cast<CharacterVirtual::ContactList *> (listVa);
    const CharacterVirtual::Contact * const pContact
            = reinterpret_cast<CharacterVirtual::Contact *> (contactVa);
    pList->at(elementIndex) = *pContact;
}

/*
 * Class:     com_github_stephengold_joltjni_ContactList
 * Method:    size
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_ContactList_size
  (JNIEnv *, jclass, jlong listVa) {
    const CharacterVirtual::ContactList * const pList
            = reinterpret_cast<CharacterVirtual::ContactList *> (listVa);
    const CharacterVirtual::ContactList::size_type result = pList->size();
    return result;
}