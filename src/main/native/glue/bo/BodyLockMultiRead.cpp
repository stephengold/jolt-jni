/*
Copyright (c) 2025 Stephen Gold

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
 * Signature: (J[I)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BodyLockMultiRead_create
  (JNIEnv *pEnv, jclass, jlong interfaceVa, jintArray bodyIds) {
    const BodyLockInterface * const pInterface
            = reinterpret_cast<BodyLockInterface *> (interfaceVa);
    const jsize numBodies = pEnv->GetArrayLength(bodyIds);
    BodyID * const pTempArray = new BodyID[numBodies];
    TRACE_NEW("BodyID[]", pTempArray)
    jboolean isCopy;
    jint * const pIds = pEnv->GetIntArrayElements(bodyIds, &isCopy);
    for (jsize i = 0; i < numBodies; ++i) {
        const jint bodyId = pIds[i];
        const BodyID id(bodyId);
        pTempArray[i] = id;
    }
    pEnv->ReleaseIntArrayElements(bodyIds, pIds, JNI_ABORT);
    BodyLockMultiRead * const pResult
            = new BodyLockMultiRead(*pInterface, pTempArray, numBodies);
    TRACE_DELETE("BodyID[]", pTempArray)
    delete[] pTempArray;
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