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
#include <Jolt/Physics/Ragdoll/Ragdoll.h>
#include "auto/com_github_stephengold_joltjni_RagdollSettings.h"
#include "auto/com_github_stephengold_joltjni_RagdollSettingsRef.h"
#include "glue/glue.h"

using namespace JPH;

IMPLEMENT_REF(RagdollSettings,
  Java_com_github_stephengold_joltjni_RagdollSettingsRef_copy,
  Java_com_github_stephengold_joltjni_RagdollSettingsRef_createEmpty,
  Java_com_github_stephengold_joltjni_RagdollSettingsRef_free,
  Java_com_github_stephengold_joltjni_RagdollSettingsRef_getPtr)

/*
 * Class:     com_github_stephengold_joltjni_RagdollSettings
 * Method:    calculateBodyIndexToConstraintIndex
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RagdollSettings_calculateBodyIndexToConstraintIndex
  (JNIEnv *, jclass, jlong settingsVa) {
    RagdollSettings * const pSettings
            = reinterpret_cast<RagdollSettings *> (settingsVa);
    pSettings->CalculateBodyIndexToConstraintIndex();
}

/*
 * Class:     com_github_stephengold_joltjni_RagdollSettings
 * Method:    calculateConstraintIndexToBodyIdxPair
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RagdollSettings_calculateConstraintIndexToBodyIdxPair
  (JNIEnv *, jclass, jlong settingsVa) {
    RagdollSettings * const pSettings
            = reinterpret_cast<RagdollSettings *> (settingsVa);
    pSettings->CalculateConstraintIndexToBodyIdxPair();
}

/*
 * Class:     com_github_stephengold_joltjni_RagdollSettings
 * Method:    createRagdoll
 * Signature: (JIJJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_RagdollSettings_createRagdoll
  (JNIEnv *, jclass, jlong settingsVa, jint group, jlong userData, jlong systemVa) {
    RagdollSettings * const pSettings
            = reinterpret_cast<RagdollSettings *> (settingsVa);
    PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    Ragdoll * const pResult
            = pSettings->CreateRagdoll(group, userData, pSystem);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_RagdollSettings
 * Method:    getNumParts
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_RagdollSettings_getNumParts
  (JNIEnv *, jclass, jlong settingsVa) {
    const RagdollSettings * const pSettings
            = reinterpret_cast<RagdollSettings *> (settingsVa);
    RagdollSettings::PartVector::size_type result = pSettings->mParts.size();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RagdollSettings
 * Method:    getPart
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_RagdollSettings_getPart
  (JNIEnv *, jclass, jlong settingsVa, jint partIndex) {
    RagdollSettings * const pSettings
            = reinterpret_cast<RagdollSettings *> (settingsVa);
    RagdollSettings::Part * const pResult = &pSettings->mParts[partIndex];
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_RagdollSettings
 * Method:    getRefCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_RagdollSettings_getRefCount
  (JNIEnv *, jclass, jlong settingsVa) {
    const RagdollSettings * const pSettings
            = reinterpret_cast<RagdollSettings *> (settingsVa);
    const uint32 result = pSettings->GetRefCount();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RagdollSettings
 * Method:    getSkeleton
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_RagdollSettings_getSkeleton
  (JNIEnv *, jclass, jlong settingsVa) {
    RagdollSettings * const pSettings
            = reinterpret_cast<RagdollSettings *> (settingsVa);
    Ref<Skeleton> const ref = pSettings->mSkeleton;
    Skeleton * const pResult = ref.GetPtr();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_RagdollSettings
 * Method:    stabilize
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_RagdollSettings_stabilize
  (JNIEnv *, jclass, jlong settingsVa) {
    RagdollSettings * const pSettings
            = reinterpret_cast<RagdollSettings *> (settingsVa);
    bool result = pSettings->Stabilize();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RagdollSettings
 * Method:    toRef
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_RagdollSettings_toRef
  (JNIEnv *, jclass, jlong settingsVa) {
    RagdollSettings * const pSettings
            = reinterpret_cast<RagdollSettings *> (settingsVa);
    Ref<RagdollSettings> * const pResult = new Ref<RagdollSettings>(pSettings);
    TRACE_NEW("Ref<RagdollSettings>", pResult)
    return reinterpret_cast<jlong> (pResult);
}