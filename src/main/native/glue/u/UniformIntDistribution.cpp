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
#include "auto/com_github_stephengold_joltjni_UniformIntDistribution.h"
#include "glue/glue.h"

using namespace std;

/*
 * Class:     com_github_stephengold_joltjni_UniformIntDistribution
 * Method:    createDistribution
 * Signature: (II)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_UniformIntDistribution_createDistribution
  (JNIEnv *, jclass, jint min, jint max) {
    uniform_int_distribution<int> * const pResult
            = new uniform_int_distribution<int>(min, max);
    TRACE_NEW("uniform_int_distribution<int>", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_UniformIntDistribution
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_UniformIntDistribution_free
  (JNIEnv *, jclass, jlong distributionVa) {
    uniform_int_distribution<int> * const pDistribution
            = reinterpret_cast<uniform_int_distribution<int> *> (distributionVa);
    TRACE_DELETE("uniform_int_distribution<int>", pDistribution)
    delete pDistribution;
}

/*
 * Class:     com_github_stephengold_joltjni_UniformIntDistribution
 * Method:    nextIntDre
 * Signature: (JJ)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_UniformIntDistribution_nextIntDre
  (JNIEnv *, jclass, jlong distributionVa, jlong generatorVa) {
    uniform_int_distribution<int> * const pDistribution
            = reinterpret_cast<uniform_int_distribution<int> *> (distributionVa);
    default_random_engine * const pGenerator
            = reinterpret_cast<default_random_engine *> (generatorVa);
    const int result = (*pDistribution)(*pGenerator);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_UniformIntDistribution
 * Method:    nextIntMt
 * Signature: (JJ)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_UniformIntDistribution_nextIntMt
  (JNIEnv *, jclass, jlong distributionVa, jlong generatorVa) {
    uniform_int_distribution<int> * const pDistribution
            = reinterpret_cast<uniform_int_distribution<int> *> (distributionVa);
    mt19937 * const pGenerator = reinterpret_cast<mt19937 *> (generatorVa);
    const int result = (*pDistribution)(*pGenerator);
    return result;
}