/*
Copyright (c) 2025-2026 Stephen Gold

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
#include "Jolt/Physics/Body/BodyLockMulti.h"
#include "auto/com_github_stephengold_joltjni_BodyLockMultiRead.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_BodyLockMultiRead
 * Method:    create
 * Signature: (JJI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BodyLockMultiRead_create
  (JNIEnv *pEnv, jclass, jlong interfaceVa, jlong idArrayVa, jint numBodies) {
    const BodyLockInterface * const pInterface
            = reinterpret_cast<BodyLockInterface *> (interfaceVa);
    const BodyID * const pArray = reinterpret_cast<BodyID *> (idArrayVa);
    BodyLockMultiRead * const pResult
            = new BodyLockMultiRead(*pInterface, pArray, numBodies);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyLockMultiRead
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyLockMultiRead_free
  BODYOF_FREE(BodyLockMultiRead)

/*
 * Class:     com_github_stephengold_joltjni_BodyLockMultiRead
 * Method:    getBody
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BodyLockMultiRead_getBody
  (JNIEnv *, jclass, jlong lockVa, jint index) {
    const BodyLockMultiRead * const pLock
            = reinterpret_cast<BodyLockMultiRead *> (lockVa);
    const Body * const pResult = pLock->GetBody(index);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyLockMultiRead
 * Method:    getNumBodies
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_BodyLockMultiRead_getNumBodies
  (JNIEnv *, jclass, jlong lockVa) {
    const BodyLockMultiRead * const pLock
            = reinterpret_cast<BodyLockMultiRead *> (lockVa);
    const int result = pLock->GetNumBodies();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyLockMultiRead
 * Method:    releaseLocks
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyLockMultiRead_releaseLocks
  (JNIEnv *, jclass, jlong lockVa) {
    BodyLockMultiRead * const pLock
            = reinterpret_cast<BodyLockMultiRead *> (lockVa);
    pLock->ReleaseLocks();
}