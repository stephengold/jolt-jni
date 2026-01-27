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
#include "auto/com_github_stephengold_joltjni_SStrandList.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_SStrandList
 * Method:    capacity
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SStrandList_capacity
  (JNIEnv *, jclass, jlong listVa) {
    const Array<HairSettings::SStrand> * const pList
            = reinterpret_cast<Array<HairSettings::SStrand> *> (listVa);
    const Array<HairSettings::SStrand>::size_type result = pList->capacity();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SStrandList
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SStrandList_createDefault
  BODYOF_CREATE_DEFAULT(Array<HairSettings::SStrand>)

/*
 * Class:     com_github_stephengold_joltjni_SStrandList
 * Method:    erase
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SStrandList_erase
  (JNIEnv *, jclass, jlong listVa, jint startIndex, jint stopIndex) {
    Array<HairSettings::SStrand> * const pList
            = reinterpret_cast<Array<HairSettings::SStrand> *> (listVa);
    Array<HairSettings::SStrand>::iterator origin = pList->begin();
    pList->erase(origin + startIndex, origin + stopIndex);
}

/*
 * Class:     com_github_stephengold_joltjni_SStrandList
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SStrandList_free
  BODYOF_FREE(Array<HairSettings::SStrand>)

/*
 * Class:     com_github_stephengold_joltjni_SStrandList
 * Method:    getStrand
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SStrandList_getStrand
  (JNIEnv *, jclass, jlong listVa, jint elementIndex) {
    Array<HairSettings::SStrand> * const pList
            = reinterpret_cast<Array<HairSettings::SStrand> *> (listVa);
    HairSettings::SStrand& result = pList->at(elementIndex);
    return reinterpret_cast<jlong> (&result);
}

/*
 * Class:     com_github_stephengold_joltjni_SStrandList
 * Method:    resize
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SStrandList_resize
  (JNIEnv *, jclass, jlong listVa, jint numElements) {
    Array<HairSettings::SStrand> * const pList
            = reinterpret_cast<Array<HairSettings::SStrand> *> (listVa);
    pList->resize(numElements);
}

/*
 * Class:     com_github_stephengold_joltjni_SStrandList
 * Method:    setStrand
 * Signature: (JIJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SStrandList_setStrand
  (JNIEnv *, jclass, jlong listVa, jint listIndex, jlong strandVa) {
    Array<HairSettings::SStrand> * const pList
            = reinterpret_cast<Array<HairSettings::SStrand> *> (listVa);
    const HairSettings::SStrand * const pStrand
            = reinterpret_cast<HairSettings::SStrand *> (strandVa);
    pList->at(listIndex) = *pStrand;
}

/*
 * Class:     com_github_stephengold_joltjni_SStrandList
 * Method:    size
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SStrandList_size
  (JNIEnv *, jclass, jlong listVa) {
    const Array<HairSettings::SStrand> * const pList
            = reinterpret_cast<Array<HairSettings::SStrand> *> (listVa);
    const Array<HairSettings::SStrand>::size_type result = pList->size();
    return result;
}