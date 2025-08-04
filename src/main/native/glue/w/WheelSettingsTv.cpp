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
#include "Jolt/Physics/Vehicle/TrackedVehicleController.h"

#include "auto/com_github_stephengold_joltjni_WheelSettingsTv.h"
#include "auto/com_github_stephengold_joltjni_WheelSettingsTvRef.h"
#include "glue/glue.h"

using namespace JPH;

IMPLEMENT_REF(WheelSettingsTV,
  Java_com_github_stephengold_joltjni_WheelSettingsTvRef_copy,
  Java_com_github_stephengold_joltjni_WheelSettingsTvRef_createDefault,
  Java_com_github_stephengold_joltjni_WheelSettingsTvRef_free,
  Java_com_github_stephengold_joltjni_WheelSettingsTvRef_getPtr,
  Java_com_github_stephengold_joltjni_WheelSettingsTvRef_toRefC)

/*
 * Class:     com_github_stephengold_joltjni_WheelSettingsTv
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_WheelSettingsTv_createCopy
  BODYOF_CREATE_COPY(WheelSettingsTV)

/*
 * Class:     com_github_stephengold_joltjni_WheelSettingsTv
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_WheelSettingsTv_createDefault
  BODYOF_CREATE_DEFAULT(WheelSettingsTV)

/*
 * Class:     com_github_stephengold_joltjni_WheelSettingsTv
 * Method:    getLateralFriction
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_WheelSettingsTv_getLateralFriction
  (JNIEnv *, jclass, jlong settingsVa) {
    const WheelSettingsTV * const pSettings
            = reinterpret_cast<WheelSettingsTV *> (settingsVa);
    const float result = pSettings->mLateralFriction;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettingsTv
 * Method:    getLongitudinalFriction
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_WheelSettingsTv_getLongitudinalFriction
  (JNIEnv *, jclass, jlong settingsVa) {
    const WheelSettingsTV * const pSettings
            = reinterpret_cast<WheelSettingsTV *> (settingsVa);
    const float result = pSettings->mLongitudinalFriction;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettingsTv
 * Method:    getRefCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_WheelSettingsTv_getRefCount
  (JNIEnv *, jclass, jlong settingsVa) {
    const WheelSettingsTV * const pSettings
            = reinterpret_cast<WheelSettingsTV *> (settingsVa);
    const uint32 result = pSettings->GetRefCount();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettingsTv
 * Method:    setEmbedded
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_WheelSettingsTv_setEmbedded
  (JNIEnv *, jclass, jlong settingsVa) {
    WheelSettingsTV * const pSettings
            = reinterpret_cast<WheelSettingsTV *> (settingsVa);
    pSettings->SetEmbedded();
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettingsTv
 * Method:    setLateralFriction
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_WheelSettingsTv_setLateralFriction
  (JNIEnv *, jclass, jlong settingsVa, jfloat friction) {
    WheelSettingsTV * const pSettings
            = reinterpret_cast<WheelSettingsTV *> (settingsVa);
    pSettings->mLateralFriction = friction;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettingsTv
 * Method:    setLongitudinalFriction
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_WheelSettingsTv_setLongitudinalFriction
  (JNIEnv *, jclass, jlong settingsVa, jfloat friction) {
    WheelSettingsTV * const pSettings
            = reinterpret_cast<WheelSettingsTV *> (settingsVa);
    pSettings->mLongitudinalFriction = friction;
}

/*
 * Class:     com_github_stephengold_joltjni_WheelSettingsTv
 * Method:    toRef
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_WheelSettingsTv_toRef
  (JNIEnv *, jclass, jlong settingsVa) {
    WheelSettingsTV * const pSettings
            = reinterpret_cast<WheelSettingsTV *> (settingsVa);
    Ref<WheelSettingsTV> * const pResult = new Ref<WheelSettingsTV>(pSettings);
    TRACE_NEW("Ref<WheelSettingsTV>", pResult)
    return reinterpret_cast<jlong> (pResult);
}