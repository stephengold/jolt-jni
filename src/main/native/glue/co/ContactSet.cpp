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
#include "Jolt/Physics/Character/CharacterVirtual.h"

#include "auto/com_github_stephengold_joltjni_ContactSet.h"
#include "glue/glue.h"

using namespace JPH;
using ContactSet = Array<CharacterVirtual::ContactKey>;

/*
 * Class:     com_github_stephengold_joltjni_ContactSet
 * Method:    capacity
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_ContactSet_capacity
  (JNIEnv *, jclass, jlong setVa) {
    const ContactSet * const pSet = reinterpret_cast<ContactSet *> (setVa);
    const ContactSet::size_type result = pSet->capacity();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ContactSet
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ContactSet_createCopy
  BODYOF_CREATE_COPY(ContactSet)

/*
 * Class:     com_github_stephengold_joltjni_ContactSet
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ContactSet_createDefault
  BODYOF_CREATE_DEFAULT(ContactSet)

/*
 * Class:     com_github_stephengold_joltjni_ContactSet
 * Method:    erase
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ContactSet_erase
  (JNIEnv *, jclass, jlong setVa, jint startIndex, jint stopIndex) {
    ContactSet * const pSet = reinterpret_cast<ContactSet *> (setVa);
    const ContactSet::iterator origin = pSet->begin();
    pSet->erase(origin + startIndex, origin + stopIndex);
}

/*
 * Class:     com_github_stephengold_joltjni_ContactSet
 * Method:    find
 * Signature: (JJ)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_ContactSet_find
  (JNIEnv *, jclass, jlong setVa, jlong keyVa) {
    const ContactSet * const pSet = reinterpret_cast<ContactSet *> (setVa);
    const CharacterVirtual::ContactKey * const pKey
            = reinterpret_cast<CharacterVirtual::ContactKey *> (keyVa);
    ContactSet::const_iterator origin = pSet->begin();
    ContactSet::const_iterator end = pSet->end();
    ContactSet::const_iterator found = std::find(origin, end, *pKey);
    const jint result = (found == end) ? -1 : (found - origin);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ContactSet
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ContactSet_free
  BODYOF_FREE(ContactSet)

/*
 * Class:     com_github_stephengold_joltjni_ContactSet
 * Method:    getElement
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ContactSet_getElement
  (JNIEnv *, jclass, jlong setVa, jint elementIndex) {
    ContactSet * const pSet = reinterpret_cast<ContactSet *> (setVa);
    const CharacterVirtual::ContactKey & key = pSet->at(elementIndex);
    const CharacterVirtual::ContactKey * const pResult
            = new CharacterVirtual::ContactKey(key);
    TRACE_NEW("CharacterVirtual::ContactKey", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_ContactSet
 * Method:    resize
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ContactSet_resize
  (JNIEnv *, jclass, jlong setVa, jint numKeys) {
    ContactSet * const pSet = reinterpret_cast<ContactSet *> (setVa);
    pSet->resize(numKeys);
}

/*
 * Class:     com_github_stephengold_joltjni_ContactSet
 * Method:    setElement
 * Signature: (JIJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ContactSet_setElement
  (JNIEnv *, jclass, jlong setVa, jint elementIndex, jlong keyVa) {
    ContactSet * const pSet = reinterpret_cast<ContactSet *> (setVa);
    const CharacterVirtual::ContactKey * const pKey
            = reinterpret_cast<CharacterVirtual::ContactKey *> (keyVa);
    pSet->at(elementIndex) = *pKey;
}

/*
 * Class:     com_github_stephengold_joltjni_ContactSet
 * Method:    size
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_ContactSet_size
  (JNIEnv *, jclass, jlong setVa) {
    const ContactSet * const pSet = reinterpret_cast<ContactSet *> (setVa);
    const ContactSet::size_type result = pSet->size();
    return result;
}