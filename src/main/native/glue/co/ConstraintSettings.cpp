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
#include "Jolt/Physics/Constraints/Constraint.h"

#include "auto/com_github_stephengold_joltjni_ConstraintSettings.h"
#include "auto/com_github_stephengold_joltjni_ConstraintSettingsRef.h"
#include "glue/glue.h"

using namespace JPH;

IMPLEMENT_REF(ConstraintSettings,
  Java_com_github_stephengold_joltjni_ConstraintSettingsRef_copy,
  Java_com_github_stephengold_joltjni_ConstraintSettingsRef_createEmpty,
  Java_com_github_stephengold_joltjni_ConstraintSettingsRef_free,
  Java_com_github_stephengold_joltjni_ConstraintSettingsRef_getPtr,
  Java_com_github_stephengold_joltjni_ConstraintSettingsRef_toRefC)

// The constraint subtype occupies the 2 least-significant bytes of mUserData.
uint64 cstMask = 0xffff;

// The controller type occupies the next byte of mUserData.
uint64 ctMask = 0xff0000;
uint32 ctShift = 16;

/*
 * Class:     com_github_stephengold_joltjni_ConstraintSettings
 * Method:    getConstraintPriority
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_ConstraintSettings_getConstraintPriority
  (JNIEnv *, jclass, jlong settingsVa) {
    const ConstraintSettings * const pSettings
            = reinterpret_cast<ConstraintSettings *> (settingsVa);
    const uint32 result = pSettings->mConstraintPriority;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ConstraintSettings
 * Method:    getConstraintSubType
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_ConstraintSettings_getConstraintSubType
  (JNIEnv *, jclass, jlong settingsVa) {
    const ConstraintSettings * const pSettings
            = reinterpret_cast<ConstraintSettings *> (settingsVa);
    const jlong result = pSettings->mUserData & cstMask;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ConstraintSettings
 * Method:    getControllerType
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_ConstraintSettings_getControllerType
  (JNIEnv *, jclass, jlong settingsVa) {
    const ConstraintSettings * const pSettings
            = reinterpret_cast<ConstraintSettings *> (settingsVa);
    const jlong result = (pSettings->mUserData & ctMask) >> ctShift;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ConstraintSettings
 * Method:    getDrawConstraintSize
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ConstraintSettings_getDrawConstraintSize
  (JNIEnv *, jclass, jlong settingsVa) {
    const ConstraintSettings * const pSettings
            = reinterpret_cast<ConstraintSettings *> (settingsVa);
    const float result = pSettings->mDrawConstraintSize;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ConstraintSettings
 * Method:    getEnabled
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ConstraintSettings_getEnabled
  (JNIEnv *, jclass, jlong settingsVa) {
    const ConstraintSettings * const pSettings
            = reinterpret_cast<ConstraintSettings *> (settingsVa);
    const bool result = pSettings->mEnabled;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ConstraintSettings
 * Method:    getNumPositionStepsOverride
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_ConstraintSettings_getNumPositionStepsOverride
  (JNIEnv *, jclass, jlong settingsVa) {
    const ConstraintSettings * const pSettings
            = reinterpret_cast<ConstraintSettings *> (settingsVa);
    const uint result = pSettings->mNumPositionStepsOverride;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ConstraintSettings
 * Method:    getNumVelocityStepsOverride
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_ConstraintSettings_getNumVelocityStepsOverride
  (JNIEnv *, jclass, jlong settingsVa) {
    const ConstraintSettings * const pSettings
            = reinterpret_cast<ConstraintSettings *> (settingsVa);
    const uint result = pSettings->mNumVelocityStepsOverride;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ConstraintSettings
 * Method:    getRefCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_ConstraintSettings_getRefCount
  (JNIEnv *, jclass, jlong settingsVa) {
    const ConstraintSettings * const pSettings
            = reinterpret_cast<ConstraintSettings *> (settingsVa);
    const uint32 result = pSettings->GetRefCount();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ConstraintSettings
 * Method:    setConstraintPriority
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ConstraintSettings_setConstraintPriority
  (JNIEnv *, jclass, jlong settingsVa, jint setting) {
    ConstraintSettings * const pSettings
            = reinterpret_cast<ConstraintSettings *> (settingsVa);
    pSettings->mConstraintPriority = setting;
}

/*
 * Class:     com_github_stephengold_joltjni_ConstraintSettings
 * Method:    setConstraintSubType
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ConstraintSettings_setConstraintSubType
  (JNIEnv *, jclass, jlong settingsVa, jint ordinal) {
    ConstraintSettings * const pSettings
            = reinterpret_cast<ConstraintSettings *> (settingsVa);
    pSettings->mUserData
            = pSettings->mUserData & ~cstMask | ((jlong)ordinal) & cstMask;
}

/*
 * Class:     com_github_stephengold_joltjni_ConstraintSettings
 * Method:    setControllerType
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ConstraintSettings_setControllerType
  (JNIEnv *, jclass, jlong settingsVa, jint ordinal) {
    ConstraintSettings * const pSettings
            = reinterpret_cast<ConstraintSettings *> (settingsVa);
    pSettings->mUserData
            = pSettings->mUserData & ~ctMask | ( ((jlong)ordinal) << ctShift ) & ctMask;
}

/*
 * Class:     com_github_stephengold_joltjni_ConstraintSettings
 * Method:    setDrawConstraintSize
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ConstraintSettings_setDrawConstraintSize
  (JNIEnv *, jclass, jlong settingsVa, jfloat size) {
    ConstraintSettings * const pSettings
            = reinterpret_cast<ConstraintSettings *> (settingsVa);
    pSettings->mDrawConstraintSize = size;
}

/*
 * Class:     com_github_stephengold_joltjni_ConstraintSettings
 * Method:    setEmbedded
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ConstraintSettings_setEmbedded
  (JNIEnv *, jclass, jlong settingsVa) {
    ConstraintSettings * const pSettings
            = reinterpret_cast<ConstraintSettings *> (settingsVa);
    pSettings->SetEmbedded();
}

/*
 * Class:     com_github_stephengold_joltjni_ConstraintSettings
 * Method:    setEnabled
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ConstraintSettings_setEnabled
  (JNIEnv *, jclass, jlong settingsVa, jboolean setting) {
    ConstraintSettings * const pSettings
            = reinterpret_cast<ConstraintSettings *> (settingsVa);
    pSettings->mEnabled = setting;
}

/*
 * Class:     com_github_stephengold_joltjni_ConstraintSettings
 * Method:    setNumPositionStepsOverride
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ConstraintSettings_setNumPositionStepsOverride
  (JNIEnv *, jclass, jlong settingsVa, jint setting) {
    ConstraintSettings * const pSettings
            = reinterpret_cast<ConstraintSettings *> (settingsVa);
    pSettings->mNumPositionStepsOverride = setting;
}

/*
 * Class:     com_github_stephengold_joltjni_ConstraintSettings
 * Method:    setNumVelocityStepsOverride
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ConstraintSettings_setNumVelocityStepsOverride
  (JNIEnv *, jclass, jlong settingsVa, jint setting) {
    ConstraintSettings * const pSettings
            = reinterpret_cast<ConstraintSettings *> (settingsVa);
    pSettings->mNumVelocityStepsOverride = setting;
}

/*
 * Class:     com_github_stephengold_joltjni_ConstraintSettings
 * Method:    sRestoreFromBinaryState
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ConstraintSettings_sRestoreFromBinaryState
  (JNIEnv *, jclass, jlong streamVa) {
    StreamIn * const pStream = reinterpret_cast<StreamIn *> (streamVa);
    ConstraintSettings::ConstraintResult * const pResult
            = new ConstraintSettings::ConstraintResult();
    TRACE_NEW("ConstraintSettings::ConstraintResult", pResult);
    *pResult = ConstraintSettings::sRestoreFromBinaryState(*pStream);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_ConstraintSettings
 * Method:    toRef
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ConstraintSettings_toRef
  (JNIEnv *, jclass, jlong settingsVa) {
    ConstraintSettings * const pSettings
            = reinterpret_cast<ConstraintSettings *> (settingsVa);
    Ref<ConstraintSettings> * const pResult
            = new Ref<ConstraintSettings>(pSettings);
    TRACE_NEW("Ref<ConstraintSettings>", pResult)
    return reinterpret_cast<jlong> (pResult);
}