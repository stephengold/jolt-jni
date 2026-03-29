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
#include "Jolt/Core/LinearCurve.h"
#include "auto/com_github_stephengold_joltjni_LinearCurve.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_LinearCurve
 * Method:    addPoint
 * Signature: (JFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_LinearCurve_addPoint
  (JNIEnv *, jclass, jlong curveVa, jfloat x, jfloat y) {
    LinearCurve * const pCurve = reinterpret_cast<LinearCurve *> (curveVa);
    pCurve->AddPoint(x, y);
}

/*
 * Class:     com_github_stephengold_joltjni_LinearCurve
 * Method:    clear
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_LinearCurve_clear
  (JNIEnv *, jclass, jlong curveVa) {
    LinearCurve * const pCurve = reinterpret_cast<LinearCurve *> (curveVa);
    pCurve->Clear();
}

/*
 * Class:     com_github_stephengold_joltjni_LinearCurve
 * Method:    countPoints
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_LinearCurve_countPoints
  (JNIEnv *, jclass, jlong curveVa) {
    const LinearCurve * const pCurve
            = reinterpret_cast<LinearCurve *> (curveVa);
    const LinearCurve::Points::size_type result = pCurve->mPoints.size();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_LinearCurve
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_LinearCurve_createCopy
  BODYOF_CREATE_COPY(LinearCurve)

/*
 * Class:     com_github_stephengold_joltjni_LinearCurve
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_LinearCurve_createDefault
  BODYOF_CREATE_DEFAULT(LinearCurve)

/*
 * Class:     com_github_stephengold_joltjni_LinearCurve
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_LinearCurve_free
  BODYOF_FREE(LinearCurve)

/*
 * Class:     com_github_stephengold_joltjni_LinearCurve
 * Method:    getMaxX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_LinearCurve_getMaxX
  (JNIEnv *, jclass, jlong curveVa) {
    const LinearCurve * const pCurve
            = reinterpret_cast<LinearCurve *> (curveVa);
    const float result = pCurve->GetMaxX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_LinearCurve
 * Method:    getMinX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_LinearCurve_getMinX
  (JNIEnv *, jclass, jlong curveVa) {
    const LinearCurve * const pCurve
            = reinterpret_cast<LinearCurve *> (curveVa);
    const float result = pCurve->GetMinX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_LinearCurve
 * Method:    getPointX
 * Signature: (JI)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_LinearCurve_getPointX
  (JNIEnv *, jclass, jlong curveVa, jint pointIndex) {
    const LinearCurve * const pCurve
            = reinterpret_cast<LinearCurve *> (curveVa);
    const float result = pCurve->mPoints.at(pointIndex).mX;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_LinearCurve
 * Method:    getPointY
 * Signature: (JI)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_LinearCurve_getPointY
  (JNIEnv *, jclass, jlong curveVa, jint pointIndex) {
    const LinearCurve * const pCurve
            = reinterpret_cast<LinearCurve *> (curveVa);
    const float result = pCurve->mPoints.at(pointIndex).mY;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_LinearCurve
 * Method:    getValue
 * Signature: (JF)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_LinearCurve_getValue
  (JNIEnv *, jclass, jlong curveVa, jfloat x) {
    const LinearCurve * const pCurve
            = reinterpret_cast<LinearCurve *> (curveVa);
    const float result = pCurve->GetValue(x);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_LinearCurve
 * Method:    restoreBinaryState
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_LinearCurve_restoreBinaryState
  (JNIEnv *, jclass, jlong curveVa, jlong streamVa) {
    LinearCurve * const pCurve = reinterpret_cast<LinearCurve *> (curveVa);
    StreamIn * const pStream = reinterpret_cast<StreamIn *> (streamVa);
    pCurve->RestoreBinaryState(*pStream);
}

/*
 * Class:     com_github_stephengold_joltjni_LinearCurve
 * Method:    saveBinaryState
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_LinearCurve_saveBinaryState
  (JNIEnv *, jclass, jlong curveVa, jlong streamVa) {
    const LinearCurve * const pCurve
            = reinterpret_cast<LinearCurve *> (curveVa);
    StreamOut * const pStream = reinterpret_cast<StreamOut *> (streamVa);
    pCurve->SaveBinaryState(*pStream);
}

/*
 * Class:     com_github_stephengold_joltjni_LinearCurve
 * Method:    sort
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_LinearCurve_sort
  (JNIEnv *, jclass, jlong curveVa) {
    LinearCurve * const pCurve = reinterpret_cast<LinearCurve *> (curveVa);
    pCurve->Sort();
}