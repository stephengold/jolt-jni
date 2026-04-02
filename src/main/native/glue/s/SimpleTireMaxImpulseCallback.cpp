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
#include "auto/com_github_stephengold_joltjni_SimpleTireMaxImpulseCallback.h"
#include "glue/glue.h"
#include "glue/Tmic.h"

using namespace JPH;

class SimpleTireMaxImpulseCallback : Tmic {

public:
    float mLateralMultiplier, mLongitudinalMultiplier;
    // constructor:
    SimpleTireMaxImpulseCallback(float lateral, float longitudinal):
            mLateralMultiplier(lateral),
            mLongitudinalMultiplier(longitudinal) {
    }

    void calculate(uint inWheelIndex, float &outLongitudinalImpulse,
            float &outLateralImpulse, float inSuspensionImpulse,
            float inLongitudinalFriction, float inLateralFriction,
            float inLongitudinalSlip, float inLateralSlip,
            float inDeltaTime) const override {
	outLateralImpulse = mLateralMultiplier
                * inLateralFriction * inSuspensionImpulse;

        outLongitudinalImpulse = mLongitudinalMultiplier
                * inLongitudinalFriction * inSuspensionImpulse;
    }
};

/*
 * Class:     com_github_stephengold_joltjni_SimpleTireMaxImpulseCallback
 * Method:    create
 * Signature: (FF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SimpleTireMaxImpulseCallback_create
  (JNIEnv *pEnv, jclass, jfloat lateral, jfloat longitudinal) {
    SimpleTireMaxImpulseCallback * const pResult
            = new SimpleTireMaxImpulseCallback(lateral, longitudinal);
    TRACE_NEW("SimpleTireMaxImpulseCallback", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SimpleTireMaxImpulseCallback
 * Method:    getLateralMultiplier
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SimpleTireMaxImpulseCallback_getLateralMultiplier
  (JNIEnv *, jclass, jlong callbackVa) {
    const SimpleTireMaxImpulseCallback * const pCallback
            = reinterpret_cast<SimpleTireMaxImpulseCallback *> (callbackVa);
    const float result = pCallback->mLateralMultiplier;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SimpleTireMaxImpulseCallback
 * Method:    getLongitudinalMultiplier
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SimpleTireMaxImpulseCallback_getLongitudinalMultiplier
  (JNIEnv *, jclass, jlong callbackVa) {
    const SimpleTireMaxImpulseCallback * const pCallback
            = reinterpret_cast<SimpleTireMaxImpulseCallback *> (callbackVa);
    const float result = pCallback->mLongitudinalMultiplier;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SimpleTireMaxImpulseCallback
 * Method:    setLateralMultiplier
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SimpleTireMaxImpulseCallback_setLateralMultiplier
  (JNIEnv *, jclass, jlong callbackVa, jfloat multiplier) {
    SimpleTireMaxImpulseCallback * const pCallback
            = reinterpret_cast<SimpleTireMaxImpulseCallback *> (callbackVa);
    pCallback->mLateralMultiplier = multiplier;
}

/*
 * Class:     com_github_stephengold_joltjni_SimpleTireMaxImpulseCallback
 * Method:    setLongitudinalMultiplier
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SimpleTireMaxImpulseCallback_setLongitudinalMultiplier
  (JNIEnv *, jclass, jlong callbackVa, jfloat multiplier) {
    SimpleTireMaxImpulseCallback * const pCallback
            = reinterpret_cast<SimpleTireMaxImpulseCallback *> (callbackVa);
    pCallback->mLongitudinalMultiplier = multiplier;
}