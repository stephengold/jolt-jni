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
#include "auto/com_github_stephengold_joltjni_AdditionalConstraint.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_AdditionalConstraint
 * Method:    createConstraint
 * Signature: (IIJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_AdditionalConstraint_createConstraint
  (JNIEnv *, jclass, jint bodyIndex1, jint bodyIndex2, jlong settingsVa) {
    TwoBodyConstraintSettings * const pSettings =
            reinterpret_cast<TwoBodyConstraintSettings *> (settingsVa);
    RagdollSettings::AdditionalConstraint * const pConstraint =
            new RagdollSettings::AdditionalConstraint(bodyIndex1, bodyIndex2, pSettings);
    TRACE_NEW("AdditionalConstraint", pConstraint)
    return reinterpret_cast<jlong> (pConstraint);
}

/*
 * Class:     com_github_stephengold_joltjni_AdditionalConstraint
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_AdditionalConstraint_free
  BODYOF_FREE(RagdollSettings::AdditionalConstraint)