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
#include "Jolt/Physics/SoftBody/SoftBodySharedSettings.h"
#include "auto/com_github_stephengold_joltjni_Skinned.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_Skinned
 * Method:    createBackstopped
 * Signature: (IFFF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Skinned_createBackstopped
  (JNIEnv *, jclass, jint vertexIndex, jfloat maxDistance,
  jfloat backstopDistance, jfloat backstopRadius) {
    SoftBodySharedSettings::Skinned * const pResult
            = new SoftBodySharedSettings::Skinned(
                    vertexIndex, maxDistance, backstopDistance, backstopRadius);
    TRACE_NEW("SoftBodySharedSettings::Skinned", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Skinned
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Skinned_createDefault
    BODYOF_CREATE_DEFAULT(SoftBodySharedSettings::Skinned)

/*
 * Class:     com_github_stephengold_joltjni_Skinned
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Skinned_free
  (JNIEnv *, jclass, jlong skinnedVa) {
    SoftBodySharedSettings::Skinned * const pSkinned
            = reinterpret_cast<SoftBodySharedSettings::Skinned *> (skinnedVa);
    TRACE_DELETE("SoftBodySharedSettings::Skinned", pSkinned)
    delete pSkinned;
}

/*
 * Class:     com_github_stephengold_joltjni_Skinned
 * Method:    getWeight
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Skinned_getWeight
  (JNIEnv *, jclass, jlong skinnedVa, jint index) {
    SoftBodySharedSettings::Skinned * const pSkinned
            = reinterpret_cast<SoftBodySharedSettings::Skinned *> (skinnedVa);
    SoftBodySharedSettings::SkinWeight * const pResult
            = &pSkinned->mWeights[index];
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Skinned
 * Method:    normalizeWeights
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Skinned_normalizeWeights
  (JNIEnv *, jclass, jlong skinnedVa) {
    SoftBodySharedSettings::Skinned * const pSkinned
            = reinterpret_cast<SoftBodySharedSettings::Skinned *> (skinnedVa);
    pSkinned->NormalizeWeights();
}

/*
 * Class:     com_github_stephengold_joltjni_Skinned
 * Method:    setWeight
 * Signature: (JIJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Skinned_setWeight
  (JNIEnv *, jclass, jlong skinnedVa, jint index, jlong weightVa) {
    SoftBodySharedSettings::Skinned * const pSkinned
            = reinterpret_cast<SoftBodySharedSettings::Skinned *> (skinnedVa);
    const SoftBodySharedSettings::SkinWeight * const pSkinWeight
            = reinterpret_cast<SoftBodySharedSettings::SkinWeight *> (weightVa);
    pSkinned->mWeights[index] = *pSkinWeight;
}