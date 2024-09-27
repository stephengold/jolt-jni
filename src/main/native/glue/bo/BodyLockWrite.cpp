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
#include "Jolt/Physics/Body/BodyLock.h"
#include "auto/com_github_stephengold_joltjni_BodyLockWrite.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_BodyLockWrite
 * Method:    createBodyLockWrite
 * Signature: (JJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BodyLockWrite_createBodyLockWrite
  (JNIEnv *, jclass, jlong lockVa, jlong idVa) {
    const BodyLockInterface * const pInterface
            = reinterpret_cast<BodyLockInterface *> (lockVa);
    const BodyID * const pId = reinterpret_cast<BodyID *> (idVa);
    BodyLockWrite * const pResult = new BodyLockWrite(*pInterface, *pId);
    TRACE_NEW("BodyLockWrite", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyLockWrite
 * Method:    getBody
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BodyLockWrite_getBody
  (JNIEnv *, jclass, jlong lockVa) {
    const BodyLockWrite * const pLock
            = reinterpret_cast<BodyLockWrite *> (lockVa);
    const Body &result = pLock->GetBody();
    return reinterpret_cast<jlong> (&result);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyLockWrite
 * Method:    releaseLock
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyLockWrite_releaseLock
  (JNIEnv *, jclass, jlong lockVa) {
    BodyLockWrite * const pLock = reinterpret_cast<BodyLockWrite *> (lockVa);
    pLock->ReleaseLock();
}

/*
 * Class:     com_github_stephengold_joltjni_BodyLockWrite
 * Method:    succeeded
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_BodyLockWrite_succeeded
  (JNIEnv *, jclass, jlong lockVa) {
    const BodyLockWrite * const pLock
            = reinterpret_cast<BodyLockWrite *> (lockVa);
    const bool result = pLock->Succeeded();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyLockWrite
 * Method:    succeededAndIsInBroadPhase
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_BodyLockWrite_succeededAndIsInBroadPhase
  (JNIEnv *, jclass, jlong lockVa) {
    const BodyLockWrite * const pLock
            = reinterpret_cast<BodyLockWrite *> (lockVa);
    const bool result = pLock->SucceededAndIsInBroadPhase();
    return result;
}