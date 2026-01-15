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
#include "auto/com_github_stephengold_joltjni_Gradient.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_Gradient
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Gradient_createCopy
  BODYOF_CREATE_COPY(HairSettings::Gradient)

/*
 * Class:     com_github_stephengold_joltjni_Gradient
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Gradient_createDefault
  BODYOF_CREATE_DEFAULT(HairSettings::Gradient)

/*
 * Class:     com_github_stephengold_joltjni_Gradient
 * Method:    createGradient
 * Signature: (FFFF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Gradient_createGradient
  (JNIEnv *pEnv, jclass, jfloat min, jfloat max, jfloat minFraction,
  jfloat maxFraction) {
    HairSettings::Gradient * const pResult
            = new HairSettings::Gradient(min, max, minFraction, maxFraction);
    TRACE_NEW("HairSettings::Gradient", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Gradient
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Gradient_free
  BODYOF_FREE(HairSettings::Gradient)

/*
 * Class:     com_github_stephengold_joltjni_Gradient
 * Method:    getMax
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Gradient_getMax
  (JNIEnv *, jclass, jlong gradientVa) {
    const HairSettings::Gradient * const pGradient
            = reinterpret_cast<HairSettings::Gradient *> (gradientVa);
    const float result = pGradient->mMax;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Gradient
 * Method:    getMaxFraction
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Gradient_getMaxFraction
  (JNIEnv *, jclass, jlong gradientVa) {
    const HairSettings::Gradient * const pGradient
            = reinterpret_cast<HairSettings::Gradient *> (gradientVa);
    const float result = pGradient->mMaxFraction;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Gradient
 * Method:    getMin
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Gradient_getMin
  (JNIEnv *, jclass, jlong gradientVa) {
    const HairSettings::Gradient * const pGradient
            = reinterpret_cast<HairSettings::Gradient *> (gradientVa);
    const float result = pGradient->mMin;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Gradient
 * Method:    getMinFraction
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Gradient_getMinFraction
  (JNIEnv *, jclass, jlong gradientVa) {
    const HairSettings::Gradient * const pGradient
            = reinterpret_cast<HairSettings::Gradient *> (gradientVa);
    const float result = pGradient->mMinFraction;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Gradient
 * Method:    restoreBinaryState
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Gradient_restoreBinaryState
  (JNIEnv *, jclass, jlong gradientVa, jlong streamVa) {
    HairSettings::Gradient * const pGradient
            = reinterpret_cast<HairSettings::Gradient *> (gradientVa);
    StreamIn * const pStream = reinterpret_cast<StreamIn *> (streamVa);
    pGradient->RestoreBinaryState(*pStream);
}

/*
 * Class:     com_github_stephengold_joltjni_Gradient
 * Method:    saveBinaryState
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Gradient_saveBinaryState
  (JNIEnv *, jclass, jlong gradientVa, jlong streamVa) {
    const HairSettings::Gradient * const pGradient
            = reinterpret_cast<HairSettings::Gradient *> (gradientVa);
    StreamOut * const pStream = reinterpret_cast<StreamOut *> (streamVa);
    pGradient->SaveBinaryState(*pStream);
}

/*
 * Class:     com_github_stephengold_joltjni_Gradient
 * Method:    setMax
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Gradient_setMax
  (JNIEnv *, jclass, jlong gradientVa, jfloat max) {
    HairSettings::Gradient * const pGradient
            = reinterpret_cast<HairSettings::Gradient *> (gradientVa);
    pGradient->mMax = max;
}

/*
 * Class:     com_github_stephengold_joltjni_Gradient
 * Method:    setMaxFraction
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Gradient_setMaxFraction
  (JNIEnv *, jclass, jlong gradientVa, jfloat fraction) {
    HairSettings::Gradient * const pGradient
            = reinterpret_cast<HairSettings::Gradient *> (gradientVa);
    pGradient->mMaxFraction = fraction;
}

/*
 * Class:     com_github_stephengold_joltjni_Gradient
 * Method:    setMin
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Gradient_setMin
  (JNIEnv *, jclass, jlong gradientVa, jfloat min) {
    HairSettings::Gradient * const pGradient
            = reinterpret_cast<HairSettings::Gradient *> (gradientVa);
    pGradient->mMin = min;
}

/*
 * Class:     com_github_stephengold_joltjni_Gradient
 * Method:    setMinFraction
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Gradient_setMinFraction
  (JNIEnv *, jclass, jlong gradientVa, jfloat fraction) {
    HairSettings::Gradient * const pGradient
            = reinterpret_cast<HairSettings::Gradient *> (gradientVa);
    pGradient->mMinFraction = fraction;
}