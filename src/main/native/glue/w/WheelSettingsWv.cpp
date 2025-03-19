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
#include "Jolt/Physics/Vehicle/WheeledVehicleController.h"

#include "auto/com_github_stephengold_joltjni_WheelSettingsWv.h"
#include "auto/com_github_stephengold_joltjni_WheelSettingsWvRef.h"
#include "glue/glue.h"

using namespace JPH;

IMPLEMENT_REF(WheelSettingsWV,
  Java_com_github_stephengold_joltjni_WheelSettingsWvRef_copy,
  Java_com_github_stephengold_joltjni_WheelSettingsWvRef_createEmpty,
  Java_com_github_stephengold_joltjni_WheelSettingsWvRef_free,
  Java_com_github_stephengold_joltjni_WheelSettingsWvRef_getPtr,
  Java_com_github_stephengold_joltjni_WheelSettingsWvRef_toRefC)

/*
 * Class:     com_github_stephengold_joltjni_WheelSettingsWv
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_WheelSettingsWv_createDefault
  (JNIEnv *, jclass) {
    const WheelSettingsWV * const pSettings = new WheelSettingsWV();
    TRACE_NEW("WheelSettingsWV", pSettings)
    return reinterpret_cast<jlong> (pSettings);
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettingsWv
 * Method:    getMaxBrakeTorque
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_WheelSettingsWv_getMaxBrakeTorque
  (JNIEnv *, jclass, jlong settingsVa) {
    const WheelSettingsWV * const pSettings
            = reinterpret_cast<WheelSettingsWV *> (settingsVa);
    const float result = pSettings->mMaxBrakeTorque;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettingsWv
 * Method:    getMaxHandBrakeTorque
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_WheelSettingsWv_getMaxHandBrakeTorque
  (JNIEnv *, jclass, jlong settingsVa) {
    const WheelSettingsWV * const pSettings
            = reinterpret_cast<WheelSettingsWV *> (settingsVa);
    const float result = pSettings->mMaxHandBrakeTorque;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettingsWv
 * Method:    getMaxSteerAngle
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_WheelSettingsWv_getMaxSteerAngle
  (JNIEnv *, jclass, jlong settingsVa) {
    const WheelSettingsWV * const pSettings
            = reinterpret_cast<WheelSettingsWV *> (settingsVa);
    const float result = pSettings->mMaxSteerAngle;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettingsWv
 * Method:    getRefCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_WheelSettingsWv_getRefCount
  (JNIEnv *, jclass, jlong settingsVa) {
    const WheelSettingsWV * const pSettings
            = reinterpret_cast<WheelSettingsWV *> (settingsVa);
    const uint32 result = pSettings->GetRefCount();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettingsWv
 * Method:    setEmbedded
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_WheelSettingsWv_setEmbedded
  (JNIEnv *, jclass, jlong settingsVa) {
    WheelSettingsWV * const pSettings
            = reinterpret_cast<WheelSettingsWV *> (settingsVa);
    pSettings->SetEmbedded();
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettingsWv
 * Method:    setMaxBrakeTorque
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_WheelSettingsWv_setMaxBrakeTorque
  (JNIEnv *, jclass, jlong settingsVa, jfloat torque) {
    WheelSettingsWV * const pSettings
            = reinterpret_cast<WheelSettingsWV *> (settingsVa);
    pSettings->mMaxBrakeTorque = torque;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettingsWv
 * Method:    setMaxHandBrakeTorque
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_WheelSettingsWv_setMaxHandBrakeTorque
  (JNIEnv *, jclass, jlong settingsVa, jfloat torque) {
    WheelSettingsWV * const pSettings
            = reinterpret_cast<WheelSettingsWV *> (settingsVa);
    pSettings->mMaxHandBrakeTorque = torque;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettingsWv
 * Method:    setMaxSteerAngle
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_WheelSettingsWv_setMaxSteerAngle
  (JNIEnv *, jclass, jlong settingsVa, jfloat angle) {
    WheelSettingsWV * const pSettings
            = reinterpret_cast<WheelSettingsWV *> (settingsVa);
    pSettings->mMaxSteerAngle = angle;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettingsWv
 * Method:    toRef
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_WheelSettingsWv_toRef
  (JNIEnv *, jclass, jlong settingsVa) {
    WheelSettingsWV * const pSettings
            = reinterpret_cast<WheelSettingsWV *> (settingsVa);
    Ref<WheelSettingsWV> * const pResult = new Ref<WheelSettingsWV>(pSettings);
    TRACE_NEW("Ref<WheelSettingsWV>", pResult)
    return reinterpret_cast<jlong> (pResult);
}