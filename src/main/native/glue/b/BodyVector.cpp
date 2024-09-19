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
#include <Jolt/Physics/Body/BodyManager.h>
#include "auto/com_github_stephengold_joltjni_BodyVector.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_BodyVector
 * Method:    capacity
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_BodyVector_capacity
  (JNIEnv *, jclass, jlong vectorVa) {
    const BodyVector * const pVector
            = reinterpret_cast<BodyVector *> (vectorVa);
    const BodyVector::size_type result = pVector->capacity();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyVector
 * Method:    getBody
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BodyVector_getBody
  (JNIEnv *, jclass, jlong vectorVa, jint elementIndex) {
    BodyVector * const pVector = reinterpret_cast<BodyVector *> (vectorVa);
    Body * pResult = pVector->at(elementIndex);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyVector
 * Method:    resize
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyVector_resize
  (JNIEnv *, jclass, jlong vectorVa, jint numElements) {
    BodyVector * const pVector = reinterpret_cast<BodyVector *> (vectorVa);
    pVector->resize(numElements);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyVector
 * Method:    setBody
 * Signature: (JIJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyVector_setBody
  (JNIEnv *, jclass, jlong vectorVa, jint elementIndex, jlong bodyVa) {
    BodyVector * const pVector = reinterpret_cast<BodyVector *> (vectorVa);
    Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    pVector->at(elementIndex) = pBody;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyVector
 * Method:    size
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_BodyVector_size
  (JNIEnv *, jclass, jlong vectorVa) {
    const BodyVector * const pVector
            = reinterpret_cast<BodyVector *> (vectorVa);
    const BodyVector::size_type result = pVector->size();
    return result;
}