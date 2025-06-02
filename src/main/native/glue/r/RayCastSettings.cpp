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
#include "Jolt/Physics/Collision/RayCast.h"
#include "auto/com_github_stephengold_joltjni_RayCastSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_RayCastSettings
 * Method:    createDefaultSettings
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_RayCastSettings_createDefaultSettings
  BODYOF_CREATE_DEFAULT(RayCastSettings)

/*
 * Class:     com_github_stephengold_joltjni_RayCastSettings
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RayCastSettings_free
  BODYOF_FREE(RayCastSettings)

/*
 * Class:     com_github_stephengold_joltjni_RayCastSettings
 * Method:    getBackFaceModeConvex
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_RayCastSettings_getBackFaceModeConvex
  (JNIEnv *, jclass, jlong settingsVa) {
    const RayCastSettings * const pSettings
            = reinterpret_cast<RayCastSettings *> (settingsVa);
    EBackFaceMode result = pSettings->mBackFaceModeConvex;
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_RayCastSettings
 * Method:    getBackFaceModeTriangles
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_RayCastSettings_getBackFaceModeTriangles
  (JNIEnv *, jclass, jlong settingsVa) {
    const RayCastSettings * const pSettings
            = reinterpret_cast<RayCastSettings *> (settingsVa);
    EBackFaceMode result = pSettings->mBackFaceModeTriangles;
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_RayCastSettings
 * Method:    getTreatConvexAsSolid
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_RayCastSettings_getTreatConvexAsSolid
  (JNIEnv *, jclass, jlong settingsVa) {
    const RayCastSettings * const pSettings
            = reinterpret_cast<RayCastSettings *> (settingsVa);
    const bool result = pSettings->mTreatConvexAsSolid;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RayCastSettings
 * Method:    setBackFaceModeConvex
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RayCastSettings_setBackFaceModeConvex
  (JNIEnv *, jclass, jlong settingsVa, jint ordinal) {
    RayCastSettings * const pSettings
            = reinterpret_cast<RayCastSettings *> (settingsVa);
    EBackFaceMode mode = (EBackFaceMode) ordinal;
    pSettings->mBackFaceModeConvex = mode;
}

/*
 * Class:     com_github_stephengold_joltjni_RayCastSettings
 * Method:    setBackFaceModeTriangles
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RayCastSettings_setBackFaceModeTriangles
  (JNIEnv *, jclass, jlong settingsVa, jint ordinal) {
    RayCastSettings * const pSettings
            = reinterpret_cast<RayCastSettings *> (settingsVa);
    EBackFaceMode mode = (EBackFaceMode) ordinal;
    pSettings->mBackFaceModeTriangles = mode;
}

/*
 * Class:     com_github_stephengold_joltjni_RayCastSettings
 * Method:    setTreatConvexAsSolid
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RayCastSettings_setTreatConvexAsSolid
  (JNIEnv *, jclass, jlong settingsVa, jboolean setting) {
    RayCastSettings * const pSettings
            = reinterpret_cast<RayCastSettings *> (settingsVa);
    pSettings->mTreatConvexAsSolid = setting;
}