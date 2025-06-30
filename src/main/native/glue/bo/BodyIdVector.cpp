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
#include "Jolt/Core/QuickSort.h"
#include "Jolt/Physics/Body/BodyManager.h"

#include "auto/com_github_stephengold_joltjni_BodyIdVector.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_BodyIdVector
 * Method:    capacity
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_BodyIdVector_capacity
  (JNIEnv *, jclass, jlong vectorVa) {
    const BodyIDVector * const pVector
            = reinterpret_cast<BodyIDVector *> (vectorVa);
    const BodyIDVector::size_type result = pVector->capacity();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyIdVector
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BodyIdVector_createCopy
  (JNIEnv *, jclass, jlong);

/*
 * Class:     com_github_stephengold_joltjni_BodyIdVector
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BodyIdVector_createDefault
  BODYOF_CREATE_DEFAULT(BodyIDVector)

/*
 * Class:     com_github_stephengold_joltjni_BodyIdVector
 * Method:    erase
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyIdVector_erase
  (JNIEnv *, jclass, jlong vectorVa, jint startIndex, jint stopIndex) {
    BodyIDVector * const pVector = reinterpret_cast<BodyIDVector *> (vectorVa);
    BodyIDVector::iterator origin = pVector->begin();
    pVector->erase(origin + startIndex, origin + stopIndex);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyIdVector
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyIdVector_free
  BODYOF_FREE(BodyIDVector)

/*
 * Class:     com_github_stephengold_joltjni_BodyIdVector
 * Method:    getId
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_BodyIdVector_getId
  (JNIEnv *, jclass, jlong vectorVa, jint elementIndex) {
    BodyIDVector * const pVector = reinterpret_cast<BodyIDVector *> (vectorVa);
    const BodyID result = pVector->at(elementIndex);
    return result.GetIndexAndSequenceNumber();
}

/*
 * Class:     com_github_stephengold_joltjni_BodyIdVector
 * Method:    resize
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyIdVector_resize
  (JNIEnv *, jclass, jlong vectorVa, jint numElements) {
    BodyIDVector * const pVector = reinterpret_cast<BodyIDVector *> (vectorVa);
    pVector->resize(numElements);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyIdVector
 * Method:    setId
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyIdVector_setId
  (JNIEnv *, jclass, jlong vectorVa, jint elementIndex, jint bodyId) {
    BodyIDVector * const pVector = reinterpret_cast<BodyIDVector *> (vectorVa);
    const BodyID id(bodyId);
    pVector->at(elementIndex) = id;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyIdVector
 * Method:    size
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_BodyIdVector_size
  (JNIEnv *, jclass, jlong vectorVa) {
    const BodyIDVector * const pVector
            = reinterpret_cast<BodyIDVector *> (vectorVa);
    const BodyIDVector::size_type result = pVector->size();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyIdVector
 * Method:    sort
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyIdVector_sort
  (JNIEnv *, jclass, jlong vectorVa) {
    BodyIDVector * const pVector = reinterpret_cast<BodyIDVector *> (vectorVa);
    QuickSort(pVector->begin(), pVector->end());
}