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
#include "Jolt/Physics/Ragdoll/Ragdoll.h"
#include "auto/com_github_stephengold_joltjni_Part.h"
#include "glue/glue.h"

using namespace JPH;

extern uint64 cstMask;

/*
 * Class:     com_github_stephengold_joltjni_Part
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Part_createCopy
  (JNIEnv *pEnv, jclass, jlong originalVa) {
    const RagdollSettings::Part * const pOriginal
            = reinterpret_cast<RagdollSettings::Part *> (originalVa);
    RagdollSettings::Part * const pCopy
            = new RagdollSettings::Part(*pOriginal);
    TRACE_NEW("RagdollSettings::Part", pCopy)
    return reinterpret_cast<jlong> (pCopy);
}

/*
 * Class:     com_github_stephengold_joltjni_Part
 * Method:    getToParent
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Part_getToParent
  (JNIEnv *, jclass, jlong partVa, jint ordinal) {
    const RagdollSettings::Part * const pPart
            = reinterpret_cast<RagdollSettings::Part *> (partVa);
    Ref<TwoBodyConstraintSettings> ref = pPart->mToParent;
    TwoBodyConstraintSettings * const pResult = ref.GetPtr();
    if (pResult) {
        pResult->mUserData
                = pResult->mUserData & ~cstMask | ((jlong)ordinal) & cstMask;
    }
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Part
 * Method:    setToParent
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Part_setToParent
  (JNIEnv *, jclass, jlong partVa, jlong settingsVa) {
    RagdollSettings::Part * const pPart
            = reinterpret_cast<RagdollSettings::Part *> (partVa);
    TwoBodyConstraintSettings * const pSettings
            = reinterpret_cast<TwoBodyConstraintSettings *> (settingsVa);
    pPart->mToParent = pSettings;
}