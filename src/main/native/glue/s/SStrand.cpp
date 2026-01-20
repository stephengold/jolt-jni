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
#include "Jolt/Physics/Hair/HairSettings.h"
#include "auto/com_github_stephengold_joltjni_SStrand.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_SStrand
 * Method:    create
 * Signature: (III)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SStrand_create
  (JNIEnv *, jclass, jint startVertex, jint endVertex, jint materialIndex) {
    HairSettings::SStrand * const pResult
            = new HairSettings::SStrand(startVertex, endVertex, materialIndex);
    TRACE_NEW("HairSettings::SStrand", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SStrand
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SStrand_createDefault
    BODYOF_CREATE_DEFAULT(HairSettings::SStrand)

/*
 * Class:     com_github_stephengold_joltjni_SStrand
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SStrand_free
    BODYOF_FREE(HairSettings::SStrand)

/*
 * Class:     com_github_stephengold_joltjni_SStrand
 * Method:    getMaterialIndex
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SStrand_getMaterialIndex
  (JNIEnv *, jclass, jlong strandVa) {
    const HairSettings::SStrand * const pStrand
            = reinterpret_cast<HairSettings::SStrand *> (strandVa);
    const uint32 result = pStrand->mMaterialIndex;
    return result;
}