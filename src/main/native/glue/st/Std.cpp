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
#include "auto/com_github_stephengold_joltjni_std_Std.h"

/*
 * Class:     com_github_stephengold_joltjni_std_Std
 * Method:    exp
 * Signature: (F)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_std_Std_exp
  (JNIEnv *, jclass, jfloat value) {
    const float result = std::exp(value);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_std_Std
 * Method:    fmod
 * Signature: (FF)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_std_Std_fmod
  (JNIEnv *, jclass, jfloat numerator, jfloat denominator) {
    const float result = std::fmod(numerator, denominator);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_std_Std
 * Method:    hypot
 * Signature: (FF)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_std_Std_hypot
  (JNIEnv *, jclass, jfloat opposite, jfloat adjacent) {
    const float result = std::hypot(opposite, adjacent);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_std_Std
 * Method:    pow
 * Signature: (FF)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_std_Std_pow
  (JNIEnv *, jclass, jfloat base, jfloat exponent) {
    const float result = std::pow(base, exponent);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_std_Std
 * Method:    sqrt
 * Signature: (F)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_std_Std_sqrt
  (JNIEnv *, jclass, jfloat value) {
    const float result = std::sqrt(value);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_std_Std
 * Method:    shuffle
 * Signature: ([IJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_std_Std_shuffle
  (JNIEnv *pEnv, jclass, jintArray indices, jlong generatorVa) {
    jboolean isCopy;
    jint * const pIndices = pEnv->GetIntArrayElements(indices, &isCopy);
    const jsize numIndices = pEnv->GetArrayLength(indices);
    JPH::Array<jint> arr(numIndices);
    for (jsize i = 0; i < numIndices; ++i) {
        arr[i] = pIndices[i];
    }
    std::default_random_engine * const pGenerator
            = reinterpret_cast<std::default_random_engine *> (generatorVa);
    std::shuffle(arr.begin(), arr.end(), *pGenerator);
    for (jsize i = 0; i < numIndices; ++i) {
        pIndices[i] = arr[i];
    }
    pEnv->ReleaseIntArrayElements(indices, pIndices, 0);
}