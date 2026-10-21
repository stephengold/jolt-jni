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
#include "auto/com_github_stephengold_joltjni_std_IntPair.h"
#include "glue/glue.h"

using namespace std;

/*
 * Class:     com_github_stephengold_joltjni_std_IntPair
 * Method:    create
 * Signature: (II)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_std_IntPair_create
  (JNIEnv *, jclass, jint v0, jint v1) {
    pair<int, int> * const pPair = new pair<int, int>((int) v0, (int)v1);
    TRACE_NEW("pair<int, int>", pPair)
    return reinterpret_cast<jlong> (pPair);
}

/*
 * Class:     com_github_stephengold_joltjni_std_IntPair
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_std_IntPair_free
  (JNIEnv *, jclass, jlong pairVa) {
    pair<int, int> * const pPair = reinterpret_cast<pair<int, int> *> (pairVa);
    TRACE_DELETE("pair<int, int>", pPair)
    delete pPair;
}

/*
 * Class:     com_github_stephengold_joltjni_std_IntPair
 * Method:    get
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_std_IntPair_get
  (JNIEnv *, jclass, jlong pairVa, jint index) {
    pair<int, int> * const pPair = reinterpret_cast<pair<int, int> *> (pairVa);
    const int result = index ? pPair->second : pPair->first;
    return result;
}