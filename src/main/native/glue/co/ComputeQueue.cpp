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
#include "Jolt/Compute/ComputeQueue.h"

#include "auto/com_github_stephengold_joltjni_ComputeQueue.h"
#include "auto/com_github_stephengold_joltjni_ComputeQueueRef.h"
#include "glue/glue.h"

using namespace JPH;

IMPLEMENT_REF(ComputeQueue,
  Java_com_github_stephengold_joltjni_ComputeQueueRef_copy,
  Java_com_github_stephengold_joltjni_ComputeQueueRef_createDefault,
  Java_com_github_stephengold_joltjni_ComputeQueueRef_free,
  Java_com_github_stephengold_joltjni_ComputeQueueRef_getPtr,
  Java_com_github_stephengold_joltjni_ComputeQueueRef_toRefC)

/*
 * Class:     com_github_stephengold_joltjni_ComputeQueue
 * Method:    executeAndWait
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ComputeQueue_executeAndWait
  (JNIEnv *, jclass, jlong queueVa) {
    ComputeQueue * const pQueue = reinterpret_cast<ComputeQueue *> (queueVa);
    pQueue->ExecuteAndWait();
}

/*
 * Class:     com_github_stephengold_joltjni_ComputeQueue
 * Method:    getRefCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_ComputeQueue_getRefCount
  (JNIEnv *, jclass, jlong queueVa) {
    const ComputeQueue * const pQueue
            = reinterpret_cast<ComputeQueue *> (queueVa);
    const uint32 result = pQueue->GetRefCount();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ComputeQueue
 * Method:    setEmbedded
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ComputeQueue_setEmbedded
  (JNIEnv *, jclass, jlong queueVa) {
    const ComputeQueue * const pQueue
            = reinterpret_cast<ComputeQueue *> (queueVa);
    pQueue->SetEmbedded();
}

/*
 * Class:     com_github_stephengold_joltjni_ComputeQueue
 * Method:    toRef
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ComputeQueue_toRef
  (JNIEnv *, jclass, jlong queueVa) {
    ComputeQueue * const pQueue = reinterpret_cast<ComputeQueue *> (queueVa);
    Ref<ComputeQueue> * const pResult = new Ref<ComputeQueue>(pQueue);
    TRACE_NEW("Ref<ComputeQueue>", pResult)
    return reinterpret_cast<jlong> (pResult);
}