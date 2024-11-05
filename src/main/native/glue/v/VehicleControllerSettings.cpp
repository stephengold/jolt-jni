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
#include "Jolt/Physics/Vehicle/VehicleController.h"

#include "auto/com_github_stephengold_joltjni_VehicleControllerSettings.h"
#include "auto/com_github_stephengold_joltjni_VehicleControllerSettingsRef.h"
#include "glue/glue.h"

using namespace JPH;

IMPLEMENT_REF(VehicleControllerSettings,
  Java_com_github_stephengold_joltjni_VehicleControllerSettingsRef_copy,
  Java_com_github_stephengold_joltjni_VehicleControllerSettingsRef_createEmpty,
  Java_com_github_stephengold_joltjni_VehicleControllerSettingsRef_free,
  Java_com_github_stephengold_joltjni_VehicleControllerSettingsRef_getPtr)

/*
 * Class:     com_github_stephengold_joltjni_VehicleControllerSettings
 * Method:    getRefCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_VehicleControllerSettings_getRefCount
  (JNIEnv *, jclass, jlong settingsVa) {
    const VehicleControllerSettings * const pSettings
            = reinterpret_cast<VehicleControllerSettings *> (settingsVa);
    const uint32 result = pSettings->GetRefCount();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleControllerSettings
 * Method:    setEmbedded
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleControllerSettings_setEmbedded
  (JNIEnv *, jclass, jlong settingsVa) {
    VehicleControllerSettings * const pSettings
            = reinterpret_cast<VehicleControllerSettings *> (settingsVa);
    pSettings->SetEmbedded();
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleControllerSettings
 * Method:    toRef
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_VehicleControllerSettings_toRef
  (JNIEnv *, jclass, jlong settingsVa) {
    VehicleControllerSettings * const pSettings
            = reinterpret_cast<VehicleControllerSettings *> (settingsVa);
    Ref<VehicleControllerSettings> * const pResult
            = new Ref<VehicleControllerSettings>(pSettings);
    TRACE_NEW("Ref<VehicleControllerSettings>", pResult)
    return reinterpret_cast<jlong> (pResult);
}