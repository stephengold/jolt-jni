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
#include "Jolt/Physics/Hair/HairShaders.h"

#include "auto/com_github_stephengold_joltjni_HairShaders.h"
#include "auto/com_github_stephengold_joltjni_HairShadersRef.h"
#include "glue/glue.h"

using namespace JPH;

IMPLEMENT_REF(HairShaders,
  Java_com_github_stephengold_joltjni_HairShadersRef_copy,
  Java_com_github_stephengold_joltjni_HairShadersRef_createDefault,
  Java_com_github_stephengold_joltjni_HairShadersRef_free,
  Java_com_github_stephengold_joltjni_HairShadersRef_getPtr,
  Java_com_github_stephengold_joltjni_HairShadersRef_toRefC)

/*
 * Class:     com_github_stephengold_joltjni_HairShaders
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_HairShaders_createCopy
  BODYOF_CREATE_COPY_TARGET(HairShaders)

/*
 * Class:     com_github_stephengold_joltjni_HairShaders
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_HairShaders_createDefault
  BODYOF_CREATE_DEFAULT_TARGET(HairShaders)

/*
 * Class:     com_github_stephengold_joltjni_HairShaders
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairShaders_free
  BODYOF_FREE(HairShaders)

/*
 * Class:     com_github_stephengold_joltjni_HairShaders
 * Method:    getRefCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_HairShaders_getRefCount
  (JNIEnv *, jclass, jlong shadersVa) {
    const HairShaders * const pShaders
            = reinterpret_cast<HairShaders *> (shadersVa);
    const uint32 result = pShaders->GetRefCount();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HairShaders
 * Method:    init
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairShaders_init
  (JNIEnv *, jclass, jlong shadersVa, jlong systemVa) {
    HairShaders * const pShaders = reinterpret_cast<HairShaders *> (shadersVa);
    ComputeSystem * const pSystem
            = reinterpret_cast<ComputeSystem *> (systemVa);
    pShaders->Init(pSystem);
}

/*
 * Class:     com_github_stephengold_joltjni_HairShaders
 * Method:    setEmbedded
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairShaders_setEmbedded
  (JNIEnv *, jclass, jlong shadersVa) {
    HairShaders * const pShaders = reinterpret_cast<HairShaders *> (shadersVa);
    pShaders->SetEmbedded();
}

/*
 * Class:     com_github_stephengold_joltjni_HairShaders
 * Method:    toRef
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_HairShaders_toRef
  (JNIEnv *pEnv, jclass, jlong shadersVa) {
    HairShaders * const pShaders = reinterpret_cast<HairShaders *> (shadersVa);
    Ref<HairShaders> * const pResult = new Ref<HairShaders>(pShaders);
    TRACE_NEW("Ref<HairShaders>", pResult)
    return reinterpret_cast<jlong> (pResult);
}