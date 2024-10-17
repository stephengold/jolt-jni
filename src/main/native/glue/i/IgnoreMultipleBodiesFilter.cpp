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
#include "Jolt/Jolt.h"
#include "Jolt/Physics/Body/BodyFilter.h"
#include "auto/com_github_stephengold_joltjni_IgnoreMultipleBodiesFilter.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_IgnoreMultipleBodiesFilter
 * Method:    clear
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_IgnoreMultipleBodiesFilter_clear
  (JNIEnv *, jclass, jlong filterVa) {
    IgnoreMultipleBodiesFilter * const pFilter
            = reinterpret_cast<IgnoreMultipleBodiesFilter *> (filterVa);
    pFilter->Clear();
}

/*
 * Class:     com_github_stephengold_joltjni_IgnoreMultipleBodiesFilter
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_IgnoreMultipleBodiesFilter_createDefault
  (JNIEnv *, jclass) {
    IgnoreMultipleBodiesFilter * const pResult
            = new IgnoreMultipleBodiesFilter();
    TRACE_NEW("IgnoreMultipleBodiesFilter", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_IgnoreMultipleBodiesFilter
 * Method:    ignoreBody
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_IgnoreMultipleBodiesFilter_ignoreBody
  (JNIEnv *, jclass, jlong filterVa, jlong bodyIdVa) {
    IgnoreMultipleBodiesFilter * const pFilter
            = reinterpret_cast<IgnoreMultipleBodiesFilter *> (filterVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    pFilter->IgnoreBody(*pBodyId);
}

/*
 * Class:     com_github_stephengold_joltjni_IgnoreMultipleBodiesFilter
 * Method:    reserve
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_IgnoreMultipleBodiesFilter_reserve
  (JNIEnv *, jclass, jlong filterVa, jint capacity) {
    IgnoreMultipleBodiesFilter * const pFilter
            = reinterpret_cast<IgnoreMultipleBodiesFilter *> (filterVa);
    pFilter->Reserve(capacity);
}