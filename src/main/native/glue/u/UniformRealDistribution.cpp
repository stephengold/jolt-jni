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
#include "auto/com_github_stephengold_joltjni_UniformRealDistribution.h"
#include "glue/glue.h"

using namespace std;

/*
 * Class:     com_github_stephengold_joltjni_UniformRealDistribution
 * Method:    createDistribution
 * Signature: (FF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_UniformRealDistribution_createDistribution
  (JNIEnv *, jclass, jfloat min, jfloat max) {
    uniform_real_distribution<float> * const pResult
            = new uniform_real_distribution<float>(min, max);
    TRACE_NEW("uniform_real_distribution<float>", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_UniformRealDistribution
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_UniformRealDistribution_free
  (JNIEnv *, jclass, jlong distributionVa) {
    uniform_real_distribution<float> * const pDistribution
            = reinterpret_cast<uniform_real_distribution<float> *> (distributionVa);
    TRACE_DELETE("uniform_real_distribution<float>", pDistribution)
    delete pDistribution;
}

/*
 * Class:     com_github_stephengold_joltjni_UniformRealDistribution
 * Method:    nextFloat
 * Signature: (JJ)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_UniformRealDistribution_nextFloat
  (JNIEnv *, jclass, jlong distributionVa, jlong generatorVa) {
    uniform_real_distribution<float> * const pDistribution
            = reinterpret_cast<uniform_real_distribution<float> *> (distributionVa);
    mt19937 * const pGenerator = reinterpret_cast<mt19937 *> (generatorVa);
    const float result = (*pDistribution)(*pGenerator);
    return result;
}