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
#include "Jolt/Compute/ComputeSystem.h"

#include "auto/com_github_stephengold_joltjni_ComputeSystem.h"
#include "auto/com_github_stephengold_joltjni_ComputeSystemRef.h"
#include "glue/glue.h"

using namespace JPH;

IMPLEMENT_REF(ComputeSystem,
  Java_com_github_stephengold_joltjni_ComputeSystemRef_copy,
  Java_com_github_stephengold_joltjni_ComputeSystemRef_createDefault,
  Java_com_github_stephengold_joltjni_ComputeSystemRef_free,
  Java_com_github_stephengold_joltjni_ComputeSystemRef_getPtr,
  Java_com_github_stephengold_joltjni_ComputeSystemRef_toRefC)

/*
 * Class:     com_github_stephengold_joltjni_ComputeSystem
 * Method:    createComputeQueue
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ComputeSystem_createComputeQueue
  (JNIEnv *pEnv, jclass, jlong systemVa) {
    ComputeSystem * const pSystem
            = reinterpret_cast<ComputeSystem *> (systemVa);
    ComputeQueueResult *pResult = new ComputeQueueResult();
    TRACE_NEW("ComputeQueueResult", pResult)
    *pResult = pSystem->CreateComputeQueue();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_ComputeSystem
 * Method:    getRefCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_ComputeSystem_getRefCount
  (JNIEnv *, jclass, jlong systemVa) {
    const ComputeSystem * const pSystem
            = reinterpret_cast<ComputeSystem *> (systemVa);
    const uint32 result = pSystem->GetRefCount();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ComputeSystem
 * Method:    getRtti
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ComputeSystem_getRtti
  (JNIEnv *, jclass, jlong systemVa) {
    const ComputeSystem * const pSystem
            = reinterpret_cast<ComputeSystem *> (systemVa);
    const RTTI * const pResult = pSystem->GetRTTI();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_ComputeSystem
 * Method:    setEmbedded
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ComputeSystem_setEmbedded
  (JNIEnv *, jclass, jlong systemVa) {
    ComputeSystem * const pSystem
            = reinterpret_cast<ComputeSystem *> (systemVa);
    pSystem->SetEmbedded();
}

/*
 * Class:     com_github_stephengold_joltjni_ComputeSystem
 * Method:    setShaderLoader
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ComputeSystem_setShaderLoader
  (JNIEnv *, jclass, jlong systemVa, jlong loaderVa) {
    ComputeSystem * const pSystem
            = reinterpret_cast<ComputeSystem *> (systemVa);
    ComputeSystem::ShaderLoader * const pLoader
            = reinterpret_cast<ComputeSystem::ShaderLoader *> (loaderVa);
    pSystem->mShaderLoader = *pLoader;
}

/*
 * Class:     com_github_stephengold_joltjni_ComputeSystem
 * Method:    toRef
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ComputeSystem_toRef
  (JNIEnv *, jclass, jlong systemVa) {
    ComputeSystem * const pSystem
            = reinterpret_cast<ComputeSystem *> (systemVa);
    Ref<ComputeSystem> * const pResult = new Ref<ComputeSystem>(pSystem);
    TRACE_NEW("Ref<ComputeSystem>", pResult)
    return reinterpret_cast<jlong> (pResult);
}