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
#include "auto/com_github_stephengold_joltjni_SkinWeight.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_SkinWeight
 * Method:    create
 * Signature: (IF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SkinWeight_create
  (JNIEnv *, jclass, jint invBindIndex, jfloat weight) {
    SoftBodySharedSettings::SkinWeight * const pResult
            = new SoftBodySharedSettings::SkinWeight(invBindIndex, weight);
    TRACE_NEW("SoftBodySharedSettings::SkinWeight", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SkinWeight
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SkinWeight_createDefault
    BODYOF_CREATE_DEFAULT(SoftBodySharedSettings::SkinWeight)

/*
 * Class:     com_github_stephengold_joltjni_SkinWeight
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SkinWeight_free
  BODYOF_FREE(SoftBodySharedSettings::SkinWeight)

/*
 * Class:     com_github_stephengold_joltjni_SkinWeight
 * Method:    getInvBindIndex
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SkinWeight_getInvBindIndex
  (JNIEnv *, jclass, jlong weightVa) {
    const SoftBodySharedSettings::SkinWeight * const pWeight
            = reinterpret_cast<SoftBodySharedSettings::SkinWeight *> (weightVa);
    const uint32 result = pWeight->mInvBindIndex;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SkinWeight
 * Method:    getWeight
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SkinWeight_getWeight
  (JNIEnv *, jclass, jlong weightVa) {
    const SoftBodySharedSettings::SkinWeight * const pWeight
            = reinterpret_cast<SoftBodySharedSettings::SkinWeight *> (weightVa);
    const float result = pWeight->mWeight;
    return result;
}