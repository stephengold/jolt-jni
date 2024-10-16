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
#include "Jolt/Physics/Vehicle/VehicleTrack.h"
#include "auto/com_github_stephengold_joltjni_VehicleTrackSettings.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_VehicleTrackSettings
 * Method:    addWheel
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleTrackSettings_addWheel
  (JNIEnv *, jclass, jlong settingsVa, jint wheelIndex) {
    VehicleTrackSettings * const pSettings
            = reinterpret_cast<VehicleTrackSettings *> (settingsVa);
    pSettings->mWheels.push_back(wheelIndex);
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleTrackSettings
 * Method:    getAngularDamping
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_VehicleTrackSettings_getAngularDamping
  (JNIEnv *, jclass, jlong settingsVa) {
    const VehicleTrackSettings * const pSettings
            = reinterpret_cast<VehicleTrackSettings *> (settingsVa);
    const float result = pSettings->mAngularDamping;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleTrackSettings
 * Method:    getDifferentialRatio
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_VehicleTrackSettings_getDifferentialRatio
  (JNIEnv *, jclass, jlong settingsVa) {
    const VehicleTrackSettings * const pSettings
            = reinterpret_cast<VehicleTrackSettings *> (settingsVa);
    const float result = pSettings->mDifferentialRatio;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleTrackSettings
 * Method:    getDrivenWheel
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_VehicleTrackSettings_getDrivenWheel
  (JNIEnv *, jclass, jlong settingsVa) {
    const VehicleTrackSettings * const pSettings
            = reinterpret_cast<VehicleTrackSettings *> (settingsVa);
    const uint result = pSettings->mDrivenWheel;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleTrackSettings
 * Method:    getInertia
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_VehicleTrackSettings_getInertia
  (JNIEnv *, jclass, jlong settingsVa) {
    const VehicleTrackSettings * const pSettings
            = reinterpret_cast<VehicleTrackSettings *> (settingsVa);
    const float result = pSettings->mInertia;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleTrackSettings
 * Method:    getNumWheels
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_VehicleTrackSettings_getNumWheels
  (JNIEnv *, jclass, jlong settingsVa) {
    const VehicleTrackSettings * const pSettings
            = reinterpret_cast<VehicleTrackSettings *> (settingsVa);
    const Array<uint>::size_type result = pSettings->mWheels.size();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleTrackSettings
 * Method:    setDrivenWheel
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleTrackSettings_setDrivenWheel
  (JNIEnv *, jclass, jlong settingsVa, jint wheelIndex) {
    VehicleTrackSettings * const pSettings
            = reinterpret_cast<VehicleTrackSettings *> (settingsVa);
    pSettings->mDrivenWheel = wheelIndex;
}