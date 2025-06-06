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
#include "Jolt/Physics/Vehicle/VehicleTransmission.h"
#include "auto/com_github_stephengold_joltjni_VehicleTransmissionSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_VehicleTransmissionSettings
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_VehicleTransmissionSettings_createCopy
  BODYOF_CREATE_COPY(VehicleTransmissionSettings)

/*
 * Class:     com_github_stephengold_joltjni_VehicleTransmissionSettings
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_VehicleTransmissionSettings_createDefault
  BODYOF_CREATE_DEFAULT(VehicleTransmissionSettings)

/*
 * Class:     com_github_stephengold_joltjni_VehicleTransmissionSettings
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleTransmissionSettings_free
  BODYOF_FREE(VehicleTransmissionSettings)

/*
 * Class:     com_github_stephengold_joltjni_VehicleTransmissionSettings
 * Method:    getClutchReleaseTime
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_VehicleTransmissionSettings_getClutchReleaseTime
  (JNIEnv *, jclass, jlong settingsVa) {
    const VehicleTransmissionSettings * const pSettings
            = reinterpret_cast<VehicleTransmissionSettings *> (settingsVa);
    const float result = pSettings->mClutchReleaseTime;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleTransmissionSettings
 * Method:    getClutchStrength
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_VehicleTransmissionSettings_getClutchStrength
  (JNIEnv *, jclass, jlong settingsVa) {
    const VehicleTransmissionSettings * const pSettings
            = reinterpret_cast<VehicleTransmissionSettings *> (settingsVa);
    const float result = pSettings->mClutchStrength;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleTransmissionSettings
 * Method:    getGearRatios
 * Signature: (J)[F
 */
JNIEXPORT jfloatArray JNICALL Java_com_github_stephengold_joltjni_VehicleTransmissionSettings_getGearRatios
  (JNIEnv *pEnv, jclass, jlong settingsVa) {
    const VehicleTransmissionSettings * const pSettings
            = reinterpret_cast<VehicleTransmissionSettings *> (settingsVa);
    const Array<float>::size_type numElements = pSettings->mGearRatios.size();
    const jfloatArray result = pEnv->NewFloatArray(numElements);
    jboolean isCopy;
    jfloat * const pFloats = pEnv->GetFloatArrayElements(result, &isCopy);
    for (size_t i = 0; i < numElements; ++i) {
        pFloats[i] = pSettings->mGearRatios[i];
    }
    pEnv->ReleaseFloatArrayElements(result, pFloats, 0);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleTransmissionSettings
 * Method:    getMode
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_VehicleTransmissionSettings_getMode
  (JNIEnv *, jclass, jlong settingsVa) {
    const VehicleTransmissionSettings * const pSettings
            = reinterpret_cast<VehicleTransmissionSettings *> (settingsVa);
    const ETransmissionMode mode = pSettings->mMode;
    return (jint) mode;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleTransmissionSettings
 * Method:    getReverseGearRatios
 * Signature: (J)[F
 */
JNIEXPORT jfloatArray JNICALL Java_com_github_stephengold_joltjni_VehicleTransmissionSettings_getReverseGearRatios
  (JNIEnv *pEnv, jclass, jlong settingsVa) {
    const VehicleTransmissionSettings * const pSettings
            = reinterpret_cast<VehicleTransmissionSettings *> (settingsVa);
    const Array<float>::size_type numElements
            = pSettings->mReverseGearRatios.size();
    const jfloatArray result = pEnv->NewFloatArray(numElements);
    jboolean isCopy;
    jfloat * const pFloats = pEnv->GetFloatArrayElements(result, &isCopy);
    for (size_t i = 0; i < numElements; ++i) {
        pFloats[i] = pSettings->mReverseGearRatios[i];
    }
    pEnv->ReleaseFloatArrayElements(result, pFloats, 0);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleTransmissionSettings
 * Method:    getShiftDownRpm
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_VehicleTransmissionSettings_getShiftDownRpm
  (JNIEnv *, jclass, jlong settingsVa) {
    const VehicleTransmissionSettings * const pSettings
            = reinterpret_cast<VehicleTransmissionSettings *> (settingsVa);
    const float result = pSettings->mShiftDownRPM;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleTransmissionSettings
 * Method:    getShiftUpRpm
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_VehicleTransmissionSettings_getShiftUpRpm
  (JNIEnv *, jclass, jlong settingsVa) {
    const VehicleTransmissionSettings * const pSettings
            = reinterpret_cast<VehicleTransmissionSettings *> (settingsVa);
    const float result = pSettings->mShiftUpRPM;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleTransmissionSettings
 * Method:    getSwitchLatency
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_VehicleTransmissionSettings_getSwitchLatency
  (JNIEnv *, jclass, jlong settingsVa) {
    const VehicleTransmissionSettings * const pSettings
            = reinterpret_cast<VehicleTransmissionSettings *> (settingsVa);
    const float result = pSettings->mSwitchLatency;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleTransmissionSettings
 * Method:    getSwitchTime
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_VehicleTransmissionSettings_getSwitchTime
  (JNIEnv *, jclass, jlong settingsVa) {
    const VehicleTransmissionSettings * const pSettings
            = reinterpret_cast<VehicleTransmissionSettings *> (settingsVa);
    const float result = pSettings->mSwitchTime;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleTransmissionSettings
 * Method:    setClutchStrength
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleTransmissionSettings_setClutchStrength
  (JNIEnv *, jclass, jlong settingsVa, jfloat strength) {
    VehicleTransmissionSettings * const pSettings
            = reinterpret_cast<VehicleTransmissionSettings *> (settingsVa);
    pSettings->mClutchStrength = strength;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleTransmissionSettings
 * Method:    setGearRatios
 * Signature: (J[F)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleTransmissionSettings_setGearRatios
  (JNIEnv *pEnv, jclass, jlong settingsVa, jfloatArray ratios) {
    VehicleTransmissionSettings * const pSettings
            = reinterpret_cast<VehicleTransmissionSettings *> (settingsVa);
    pSettings->mGearRatios.clear();
    jboolean isCopy;
    jfloat * const pRatios = pEnv->GetFloatArrayElements(ratios, &isCopy);
    const jsize numElements = pEnv->GetArrayLength(ratios);
    for (jsize i = 0; i < numElements; ++i) {
        jfloat ratio = pRatios[i];
        pSettings->mGearRatios.push_back(ratio);
    }
    pEnv->ReleaseFloatArrayElements(ratios, pRatios, JNI_ABORT);
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleTransmissionSettings
 * Method:    setMode
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleTransmissionSettings_setMode
  (JNIEnv *, jclass, jlong settingsVa, jint ordinal) {
    VehicleTransmissionSettings * const pSettings
            = reinterpret_cast<VehicleTransmissionSettings *> (settingsVa);
    const ETransmissionMode mode = (ETransmissionMode) ordinal;
    pSettings->mMode = mode;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleTransmissionSettings
 * Method:    setReverseGearRatios
 * Signature: (J[F)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleTransmissionSettings_setReverseGearRatios
  (JNIEnv *pEnv, jclass, jlong settingsVa, jfloatArray ratios) {
    VehicleTransmissionSettings * const pSettings
            = reinterpret_cast<VehicleTransmissionSettings *> (settingsVa);
    pSettings->mReverseGearRatios.clear();
    jboolean isCopy;
    jfloat * const pRatios = pEnv->GetFloatArrayElements(ratios, &isCopy);
    const jsize numElements = pEnv->GetArrayLength(ratios);
    for (jsize i = 0; i < numElements; ++i) {
        jfloat ratio = pRatios[i];
        pSettings->mReverseGearRatios.push_back(ratio);
    }
    pEnv->ReleaseFloatArrayElements(ratios, pRatios, JNI_ABORT);
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleTransmissionSettings
 * Method:    setShiftDownRpm
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleTransmissionSettings_setShiftDownRpm
  (JNIEnv *, jclass, jlong settingsVa, jfloat rpm) {
    VehicleTransmissionSettings * const pSettings
            = reinterpret_cast<VehicleTransmissionSettings *> (settingsVa);
    pSettings->mShiftDownRPM = rpm;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleTransmissionSettings
 * Method:    setShiftUpRpm
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleTransmissionSettings_setShiftUpRpm
  (JNIEnv *, jclass, jlong settingsVa, jfloat rpm) {
    VehicleTransmissionSettings * const pSettings
            = reinterpret_cast<VehicleTransmissionSettings *> (settingsVa);
    pSettings->mShiftUpRPM = rpm;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleTransmissionSettings
 * Method:    setSwitchLatency
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleTransmissionSettings_setSwitchLatency
  (JNIEnv *, jclass, jlong settingsVa, jfloat latency) {
    VehicleTransmissionSettings * const pSettings
            = reinterpret_cast<VehicleTransmissionSettings *> (settingsVa);
    pSettings->mSwitchLatency = latency;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleTransmissionSettings
 * Method:    setSwitchTime
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleTransmissionSettings_setSwitchTime
  (JNIEnv *, jclass, jlong settingsVa, jfloat latency) {
    VehicleTransmissionSettings * const pSettings
            = reinterpret_cast<VehicleTransmissionSettings *> (settingsVa);
    pSettings->mSwitchTime = latency;
}