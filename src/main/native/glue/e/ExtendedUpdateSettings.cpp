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
#include <Jolt/Jolt.h>
#include <Jolt/Physics/Character/CharacterVirtual.h>
#include "auto/com_github_stephengold_joltjni_ExtendedUpdateSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_ExtendedUpdateSettings
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ExtendedUpdateSettings_createDefault
  (JNIEnv *, jclass) {
    CharacterVirtual::ExtendedUpdateSettings * const pResult = new CharacterVirtual::ExtendedUpdateSettings();
    TRACE_NEW("ExtendedUpdateSettings", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_ExtendedUpdateSettings
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ExtendedUpdateSettings_free
  (JNIEnv *, jclass, jlong settingsVa) {
    CharacterVirtual::ExtendedUpdateSettings * const pSettings
            = reinterpret_cast<CharacterVirtual::ExtendedUpdateSettings *> (settingsVa);
    TRACE_DELETE("ExtendedUpdateSettings", pSettings)
    delete pSettings;
}

/*
 * Class:     com_github_stephengold_joltjni_ExtendedUpdateSettings
 * Method:    getStickToFloorStepDownX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ExtendedUpdateSettings_getStickToFloorStepDownX
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterVirtual::ExtendedUpdateSettings * const pSettings
            = reinterpret_cast<CharacterVirtual::ExtendedUpdateSettings *> (settingsVa);
    const float result = pSettings->mStickToFloorStepDown.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ExtendedUpdateSettings
 * Method:    getStickToFloorStepDownY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ExtendedUpdateSettings_getStickToFloorStepDownY
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterVirtual::ExtendedUpdateSettings * const pSettings
            = reinterpret_cast<CharacterVirtual::ExtendedUpdateSettings *> (settingsVa);
    const float result = pSettings->mStickToFloorStepDown.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ExtendedUpdateSettings
 * Method:    getStickToFloorStepDownZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ExtendedUpdateSettings_getStickToFloorStepDownZ
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterVirtual::ExtendedUpdateSettings * const pSettings
            = reinterpret_cast<CharacterVirtual::ExtendedUpdateSettings *> (settingsVa);
    const float result = pSettings->mStickToFloorStepDown.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ExtendedUpdateSettings
 * Method:    getWalkStairsCosAngleForwardContact
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ExtendedUpdateSettings_getWalkStairsCosAngleForwardContact
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterVirtual::ExtendedUpdateSettings * const pSettings
            = reinterpret_cast<CharacterVirtual::ExtendedUpdateSettings *> (settingsVa);
    const float result = pSettings->mWalkStairsCosAngleForwardContact;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ExtendedUpdateSettings
 * Method:    getWalkStairsMinStepForward
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ExtendedUpdateSettings_getWalkStairsMinStepForward
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterVirtual::ExtendedUpdateSettings * const pSettings
            = reinterpret_cast<CharacterVirtual::ExtendedUpdateSettings *> (settingsVa);
    const float result = pSettings->mWalkStairsMinStepForward;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ExtendedUpdateSettings
 * Method:    getWalkStairsStepForwardTest
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ExtendedUpdateSettings_getWalkStairsStepForwardTest
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterVirtual::ExtendedUpdateSettings * const pSettings
            = reinterpret_cast<CharacterVirtual::ExtendedUpdateSettings *> (settingsVa);
    const float result = pSettings->mWalkStairsStepForwardTest;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ExtendedUpdateSettings
 * Method:    getWalkStairsStepUpX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ExtendedUpdateSettings_getWalkStairsStepUpX
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterVirtual::ExtendedUpdateSettings * const pSettings
            = reinterpret_cast<CharacterVirtual::ExtendedUpdateSettings *> (settingsVa);
    const float result = pSettings->mWalkStairsStepUp.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ExtendedUpdateSettings
 * Method:    getWalkStairsStepUpY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ExtendedUpdateSettings_getWalkStairsStepUpY
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterVirtual::ExtendedUpdateSettings * const pSettings
            = reinterpret_cast<CharacterVirtual::ExtendedUpdateSettings *> (settingsVa);
    const float result = pSettings->mWalkStairsStepUp.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ExtendedUpdateSettings
 * Method:    getWalkStairsStepUpZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ExtendedUpdateSettings_getWalkStairsStepUpZ
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterVirtual::ExtendedUpdateSettings * const pSettings
            = reinterpret_cast<CharacterVirtual::ExtendedUpdateSettings *> (settingsVa);
    const float result = pSettings->mWalkStairsStepUp.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ExtendedUpdateSettings
 * Method:    setStickToFloorStepDown
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ExtendedUpdateSettings_setStickToFloorStepDown
  (JNIEnv *, jclass, jlong settingsVa, jfloat x, jfloat y, jfloat z) {
    CharacterVirtual::ExtendedUpdateSettings * const pSettings
            = reinterpret_cast<CharacterVirtual::ExtendedUpdateSettings *> (settingsVa);
    const Vec3 offset(x, y, z);
    pSettings->mStickToFloorStepDown = offset;
}

/*
 * Class:     com_github_stephengold_joltjni_ExtendedUpdateSettings
 * Method:    setWalkStairsCosAngleForwardContact
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ExtendedUpdateSettings_setWalkStairsCosAngleForwardContact
  (JNIEnv *, jclass, jlong settingsVa, jfloat cosine) {
    CharacterVirtual::ExtendedUpdateSettings * const pSettings
            = reinterpret_cast<CharacterVirtual::ExtendedUpdateSettings *> (settingsVa);
    pSettings->mWalkStairsCosAngleForwardContact = cosine;
}

/*
 * Class:     com_github_stephengold_joltjni_ExtendedUpdateSettings
 * Method:    setWalkStairsMinStepForward
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ExtendedUpdateSettings_setWalkStairsMinStepForward
  (JNIEnv *, jclass, jlong settingsVa, jfloat minStepForward) {
    CharacterVirtual::ExtendedUpdateSettings * const pSettings
            = reinterpret_cast<CharacterVirtual::ExtendedUpdateSettings *> (settingsVa);
    pSettings->mWalkStairsMinStepForward = minStepForward;
}

/*
 * Class:     com_github_stephengold_joltjni_ExtendedUpdateSettings
 * Method:    setWalkStairsStepForwardTest
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ExtendedUpdateSettings_setWalkStairsStepForwardTest
  (JNIEnv *, jclass, jlong settingsVa, jfloat step) {
    CharacterVirtual::ExtendedUpdateSettings * const pSettings
            = reinterpret_cast<CharacterVirtual::ExtendedUpdateSettings *> (settingsVa);
    pSettings->mWalkStairsStepForwardTest = step;
}

/*
 * Class:     com_github_stephengold_joltjni_ExtendedUpdateSettings
 * Method:    setWalkStairsStepUp
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ExtendedUpdateSettings_setWalkStairsStepUp
  (JNIEnv *, jclass, jlong settingsVa, jfloat x, jfloat y, jfloat z) {
    CharacterVirtual::ExtendedUpdateSettings * const pSettings
            = reinterpret_cast<CharacterVirtual::ExtendedUpdateSettings *> (settingsVa);
    const Vec3 offset(x, y, z);
    pSettings->mWalkStairsStepUp = offset;
}