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
#include "Jolt/Physics/Character/CharacterVirtual.h"
#include "auto/com_github_stephengold_joltjni_ExtendedUpdateSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_ExtendedUpdateSettings
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ExtendedUpdateSettings_createCopy
  BODYOF_CREATE_COPY(CharacterVirtual::ExtendedUpdateSettings)

/*
 * Class:     com_github_stephengold_joltjni_ExtendedUpdateSettings
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ExtendedUpdateSettings_createDefault
  BODYOF_CREATE_DEFAULT(CharacterVirtual::ExtendedUpdateSettings)

/*
 * Class:     com_github_stephengold_joltjni_ExtendedUpdateSettings
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ExtendedUpdateSettings_free
  BODYOF_FREE(CharacterVirtual::ExtendedUpdateSettings)

/*
 * Class:     com_github_stephengold_joltjni_ExtendedUpdateSettings
 * Method:    getStickToFloorStepDown
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ExtendedUpdateSettings_getStickToFloorStepDown
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeFloats) {
    const CharacterVirtual::ExtendedUpdateSettings * const pSettings
            = reinterpret_cast<CharacterVirtual::ExtendedUpdateSettings *> (settingsVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& result = pSettings->mStickToFloorStepDown;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();    
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
 * Method:    getWalkStairsStepUp
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ExtendedUpdateSettings_getWalkStairsStepUp
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeFloats) {
    const CharacterVirtual::ExtendedUpdateSettings * const pSettings
            = reinterpret_cast<CharacterVirtual::ExtendedUpdateSettings *> (settingsVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& result = pSettings->mWalkStairsStepUp;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();    
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