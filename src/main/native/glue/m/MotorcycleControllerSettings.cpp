/*
Copyright (c) 2024-2026 Stephen Gold

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
#include "Jolt/Physics/Vehicle/MotorcycleController.h"
#include "auto/com_github_stephengold_joltjni_MotorcycleControllerSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_MotorcycleControllerSettings
 * Method:    assign
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotorcycleControllerSettings_assign
  BODYOF_ASSIGN(MotorcycleControllerSettings)

/*
 * Class:     com_github_stephengold_joltjni_MotorcycleControllerSettings
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_MotorcycleControllerSettings_createCopy
  BODYOF_CREATE_COPY_TARGET(MotorcycleControllerSettings)

/*
 * Class:     com_github_stephengold_joltjni_MotorcycleControllerSettings
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_MotorcycleControllerSettings_createDefault
  BODYOF_CREATE_DEFAULT_TARGET(MotorcycleControllerSettings)

/*
 * Class:     com_github_stephengold_joltjni_MotorcycleControllerSettings
 * Method:    getLeanSmoothingFactor
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MotorcycleControllerSettings_getLeanSmoothingFactor
  (JNIEnv *, jclass, jlong settingsVa) {
    const MotorcycleControllerSettings * const pSettings
            = reinterpret_cast<MotorcycleControllerSettings *> (settingsVa);
    const float result = pSettings->mLeanSmoothingFactor;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotorcycleControllerSettings
 * Method:    getLeanSpringConstant
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MotorcycleControllerSettings_getLeanSpringConstant
  (JNIEnv *, jclass, jlong settingsVa) {
    const MotorcycleControllerSettings * const pSettings
            = reinterpret_cast<MotorcycleControllerSettings *> (settingsVa);
    const float result = pSettings->mLeanSpringConstant;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotorcycleControllerSettings
 * Method:    getLeanSpringDamping
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MotorcycleControllerSettings_getLeanSpringDamping
  (JNIEnv *, jclass, jlong settingsVa) {
    const MotorcycleControllerSettings * const pSettings
            = reinterpret_cast<MotorcycleControllerSettings *> (settingsVa);
    const float result = pSettings->mLeanSpringDamping;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotorcycleControllerSettings
 * Method:    getLeanSpringIntegrationCoefficient
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MotorcycleControllerSettings_getLeanSpringIntegrationCoefficient
  (JNIEnv *, jclass, jlong settingsVa) {
    const MotorcycleControllerSettings * const pSettings
            = reinterpret_cast<MotorcycleControllerSettings *> (settingsVa);
    const float result = pSettings->mLeanSpringIntegrationCoefficient;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotorcycleControllerSettings
 * Method:    getLeanSpringIntegrationCoefficientDecay
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MotorcycleControllerSettings_getLeanSpringIntegrationCoefficientDecay
  (JNIEnv *, jclass, jlong settingsVa) {
    const MotorcycleControllerSettings * const pSettings
            = reinterpret_cast<MotorcycleControllerSettings *> (settingsVa);
    const float result = pSettings->mLeanSpringIntegrationCoefficientDecay;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotorcycleControllerSettings
 * Method:    getMaxLeanAngle
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MotorcycleControllerSettings_getMaxLeanAngle
  (JNIEnv *, jclass, jlong settingsVa) {
    const MotorcycleControllerSettings * const pSettings
            = reinterpret_cast<MotorcycleControllerSettings *> (settingsVa);
    const float result = pSettings->mMaxLeanAngle;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MotorcycleControllerSettings
 * Method:    setLeanSmoothingFactor
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotorcycleControllerSettings_setLeanSmoothingFactor
  (JNIEnv *, jclass, jlong settingsVa, jfloat factor) {
    MotorcycleControllerSettings * const pSettings
            = reinterpret_cast<MotorcycleControllerSettings *> (settingsVa);
    pSettings->mLeanSmoothingFactor = factor;
}

/*
 * Class:     com_github_stephengold_joltjni_MotorcycleControllerSettings
 * Method:    setLeanSpringConstant
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotorcycleControllerSettings_setLeanSpringConstant
  (JNIEnv *, jclass, jlong settingsVa, jfloat k) {
    MotorcycleControllerSettings * const pSettings
            = reinterpret_cast<MotorcycleControllerSettings *> (settingsVa);
    pSettings->mLeanSpringConstant = k;
}

/*
 * Class:     com_github_stephengold_joltjni_MotorcycleControllerSettings
 * Method:    setLeanSpringDamping
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotorcycleControllerSettings_setLeanSpringDamping
  (JNIEnv *, jclass, jlong settingsVa, jfloat damping) {
    MotorcycleControllerSettings * const pSettings
            = reinterpret_cast<MotorcycleControllerSettings *> (settingsVa);
    pSettings->mLeanSpringDamping = damping;
}

/*
 * Class:     com_github_stephengold_joltjni_MotorcycleControllerSettings
 * Method:    setLeanSpringIntegrationCoefficient
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotorcycleControllerSettings_setLeanSpringIntegrationCoefficient
  (JNIEnv *, jclass, jlong settingsVa, jfloat coefficient) {
    MotorcycleControllerSettings * const pSettings
            = reinterpret_cast<MotorcycleControllerSettings *> (settingsVa);
    pSettings->mLeanSpringIntegrationCoefficient = coefficient;
}

/*
 * Class:     com_github_stephengold_joltjni_MotorcycleControllerSettings
 * Method:    setLeanSpringIntegrationCoefficientDecay
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotorcycleControllerSettings_setLeanSpringIntegrationCoefficientDecay
  (JNIEnv *, jclass, jlong settingsVa, jfloat decay) {
    MotorcycleControllerSettings * const pSettings
            = reinterpret_cast<MotorcycleControllerSettings *> (settingsVa);
    pSettings->mLeanSpringIntegrationCoefficientDecay = decay;
}

/*
 * Class:     com_github_stephengold_joltjni_MotorcycleControllerSettings
 * Method:    setMaxLeanAngle
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MotorcycleControllerSettings_setMaxLeanAngle
  (JNIEnv *, jclass, jlong settingsVa, jfloat angle) {
    MotorcycleControllerSettings * const pSettings
            = reinterpret_cast<MotorcycleControllerSettings *> (settingsVa);
    pSettings->mMaxLeanAngle = angle;
}