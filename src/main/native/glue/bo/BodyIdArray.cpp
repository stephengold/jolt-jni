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
#include "Jolt/Physics/Body/BodyID.h"
#include "auto/com_github_stephengold_joltjni_BodyIdArray.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_BodyIdArray
 * Method:    create
 * Signature: (I)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BodyIdArray_create
  (JNIEnv *, jclass, jint length) {
    BodyID * const pArray = new BodyID[length];
    TRACE_NEW("BodyID[]", pArray)
    return reinterpret_cast<jlong> (pArray);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyIdArray
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyIdArray_free
  (JNIEnv *, jclass, jlong arrayVa) {
    BodyID * const pArray = reinterpret_cast<BodyID *> (arrayVa);
    TRACE_DELETE("BodyID[]", pArray)
    delete[] pArray;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyIdArray
 * Method:    getId
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_BodyIdArray_getId
  (JNIEnv *, jclass, jlong arrayVa, jint elementIndex) {
    const BodyID * const pArray = reinterpret_cast<BodyID *> (arrayVa);
    const BodyID result = pArray[elementIndex];
    return result.GetIndexAndSequenceNumber();
}

/*
 * Class:     com_github_stephengold_joltjni_BodyIdArray
 * Method:    setId
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyIdArray_setId
  (JNIEnv *, jclass, jlong arrayVa, jint elementIndex, jint bodyId) {
    BodyID * const pArray = reinterpret_cast<BodyID *> (arrayVa);
    const BodyID id(bodyId);
    pArray[elementIndex] = id;
}