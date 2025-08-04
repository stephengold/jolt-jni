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
#include "auto/com_github_stephengold_joltjni_TrackedVehicleControllerSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_TrackedVehicleControllerSettings
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_TrackedVehicleControllerSettings_createCopy
  BODYOF_CREATE_COPY(TrackedVehicleControllerSettings)

/*
 * Class:     com_github_stephengold_joltjni_TrackedVehicleControllerSettings
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_TrackedVehicleControllerSettings_createDefault
  BODYOF_CREATE_DEFAULT(TrackedVehicleControllerSettings)

/*
 * Class:     com_github_stephengold_joltjni_TrackedVehicleControllerSettings
 * Method:    getNumTracks
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_TrackedVehicleControllerSettings_getNumTracks
  (JNIEnv *, jclass, jlong) {
    const jint result = (jint) ETrackSide::Num;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_TrackedVehicleControllerSettings
 * Method:    getTrack
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_TrackedVehicleControllerSettings_getTrack
  (JNIEnv *, jclass, jlong settingsVa, jint trackIndex) {
    TrackedVehicleControllerSettings * const pVehicleSettings
            = reinterpret_cast<TrackedVehicleControllerSettings *> (settingsVa);
    VehicleTrackSettings * const pResult
            = &pVehicleSettings->mTracks[trackIndex];
    return reinterpret_cast<jlong> (pResult);
}