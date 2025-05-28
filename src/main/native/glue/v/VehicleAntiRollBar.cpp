/*
Copyright (c) 2025 Stephen Gold

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
#include "Jolt/Physics/Vehicle/VehicleAntiRollBar.h"
#include "auto/com_github_stephengold_joltjni_VehicleAntiRollBar.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_VehicleAntiRollBar
 * Method:    getLeftWheel
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_VehicleAntiRollBar_getLeftWheel
  (JNIEnv *, jclass, jlong barVa) {
    const VehicleAntiRollBar * const pBar
            = reinterpret_cast<VehicleAntiRollBar *> (barVa);
    const int result = pBar->mLeftWheel;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleAntiRollBar
 * Method:    getRightWheel
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_VehicleAntiRollBar_getRightWheel
  (JNIEnv *, jclass, jlong barVa) {
    const VehicleAntiRollBar * const pBar
            = reinterpret_cast<VehicleAntiRollBar *> (barVa);
    const int result = pBar->mRightWheel;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleAntiRollBar
 * Method:    getStiffness
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_VehicleAntiRollBar_getStiffness
  (JNIEnv *, jclass, jlong barVa) {
    const VehicleAntiRollBar * const pBar
            = reinterpret_cast<VehicleAntiRollBar *> (barVa);
    const float result = pBar->mStiffness;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleAntiRollBar
 * Method:    restoreBinaryState
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleAntiRollBar_restoreBinaryState
  (JNIEnv *, jclass, jlong barVa, jlong streamVa) {
    VehicleAntiRollBar * const pBar
            = reinterpret_cast<VehicleAntiRollBar *> (barVa);
    StreamIn * const pStream = reinterpret_cast<StreamIn *> (streamVa);
    pBar->RestoreBinaryState(*pStream);
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleAntiRollBar
 * Method:    saveBinaryState
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleAntiRollBar_saveBinaryState
  (JNIEnv *, jclass, jlong barVa, jlong streamVa) {
    const VehicleAntiRollBar * const pBar
            = reinterpret_cast<VehicleAntiRollBar *> (barVa);
    StreamOut * const pStream = reinterpret_cast<StreamOut *> (streamVa);
    pBar->SaveBinaryState(*pStream);
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleAntiRollBar
 * Method:    setLeftWheel
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleAntiRollBar_setLeftWheel
  (JNIEnv *, jclass, jlong barVa, jint wheelIndex) {
    VehicleAntiRollBar * const pBar
            = reinterpret_cast<VehicleAntiRollBar *> (barVa);
    pBar->mLeftWheel = wheelIndex;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleAntiRollBar
 * Method:    setRightWheel
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleAntiRollBar_setRightWheel
  (JNIEnv *, jclass, jlong barVa, jint wheelIndex) {
    VehicleAntiRollBar * const pBar
            = reinterpret_cast<VehicleAntiRollBar *> (barVa);
    pBar->mRightWheel = wheelIndex;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleAntiRollBar
 * Method:    setStiffness
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleAntiRollBar_setStiffness
  (JNIEnv *, jclass, jlong barVa, jfloat stiffness) {
    VehicleAntiRollBar * const pBar
            = reinterpret_cast<VehicleAntiRollBar *> (barVa);
    pBar->mStiffness = stiffness;
}