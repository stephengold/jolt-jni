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
#include "auto/com_github_stephengold_joltjni_std_Std.h"

/*
 * Class:     com_github_stephengold_joltjni_std_Std
 * Method:    acos
 * Signature: (F)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_std_Std_acos
  (JNIEnv *, jclass, jfloat ratio) {
    float result = std::acos(ratio);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_std_Std
 * Method:    atan
 * Signature: (F)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_std_Std_atan
  (JNIEnv *, jclass, jfloat ratio) {
    float result = std::atan(ratio);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_std_Std
 * Method:    cos
 * Signature: (F)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_std_Std_cos
  (JNIEnv *, jclass, jfloat angle) {
    float result = std::cos(angle);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_std_Std
 * Method:    exp
 * Signature: (F)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_std_Std_exp
  (JNIEnv *, jclass, jfloat value) {
    float result = std::exp(value);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_std_Std
 * Method:    fmod
 * Signature: (FF)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_std_Std_fmod
  (JNIEnv *, jclass, jfloat numerator, jfloat denominator) {
    float result = std::fmod(numerator, denominator);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_std_Std
 * Method:    pow
 * Signature: (FF)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_std_Std_pow
  (JNIEnv *, jclass, jfloat base, jfloat exponent) {
    float result = std::pow(base, exponent);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_std_Std
 * Method:    sin
 * Signature: (F)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_std_Std_sin
  (JNIEnv *, jclass, jfloat angle) {
    float result = std::sin(angle);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_std_Std
 * Method:    sqrt
 * Signature: (F)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_std_Std_sqrt
  (JNIEnv *, jclass, jfloat value) {
    float result = std::sqrt(value);
    return result;
}