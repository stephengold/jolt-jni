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
#include "auto/com_github_stephengold_joltjni_std_DefaultRandomEngine.h"
#include "glue/glue.h"

using namespace std;

/*
 * Class:     com_github_stephengold_joltjni_std_DefaultRandomEngine
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_std_DefaultRandomEngine_createDefault
  BODYOF_CREATE_DEFAULT(default_random_engine)

/*
 * Class:     com_github_stephengold_joltjni_std_DefaultRandomEngine
 * Method:    createSeeded
 * Signature: (I)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_std_DefaultRandomEngine_createSeeded
  (JNIEnv *, jclass, jint seed) {
    default_random_engine * const pResult = new default_random_engine(seed);
    TRACE_NEW("default_random_engine", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_std_DefaultRandomEngine
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_std_DefaultRandomEngine_free
  BODYOF_FREE(default_random_engine)

/*
 * Class:     com_github_stephengold_joltjni_std_DefaultRandomEngine
 * Method:    nextInt
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_std_DefaultRandomEngine_nextInt
  (JNIEnv *, jclass, jlong generatorVa) {
    default_random_engine * const pGenerator
            = reinterpret_cast<default_random_engine *> (generatorVa);
    default_random_engine::result_type result = (*pGenerator)();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_std_DefaultRandomEngine
 * Method:    setSeed
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_std_DefaultRandomEngine_setSeed
  (JNIEnv *, jclass, jlong generatorVa, jint value) {
    default_random_engine * const pGenerator
            = reinterpret_cast<default_random_engine *> (generatorVa);
    pGenerator->seed(value);
}