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
#include <Jolt/Physics/Body/BodyID.h>
#include "auto/com_github_stephengold_joltjni_BodyId.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_BodyId
 * Method:    copy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BodyId_copy
  (JNIEnv *, jclass, jlong idVa) {
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (idVa);
    BodyID * const pResult = new BodyID();
    TRACE_NEW("BodyID", pResult)
    *pResult = *pBodyId;
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyId
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyId_free
  (JNIEnv *, jclass, jlong idVa) {
    BodyID * const pBodyId = reinterpret_cast<BodyID *> (idVa);
    TRACE_DELETE("BodyID", pBodyId)
    delete pBodyId;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyId
 * Method:    getIndex
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_BodyId_getIndex
  (JNIEnv *, jclass, jlong idVa) {
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (idVa);
    uint32 result = pBodyId->GetIndex();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyId
 * Method:    getSequenceNumber
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_BodyId_getSequenceNumber
  (JNIEnv *, jclass, jlong idVa) {
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (idVa);
    uint8 result = pBodyId->GetSequenceNumber();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyId
 * Method:    isInvalid
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_BodyId_isInvalid
  (JNIEnv *, jclass, jlong idVa) {
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (idVa);
    bool result = pBodyId->IsInvalid();
    return result;
}