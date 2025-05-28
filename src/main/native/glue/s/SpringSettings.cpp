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
#include "Jolt/Physics/Constraints/SpringSettings.h"
#include "auto/com_github_stephengold_joltjni_SpringSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_SpringSettings
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SpringSettings_createDefault
  (JNIEnv *, jclass) {
    SpringSettings * const pResult = new SpringSettings();
    TRACE_NEW("SpringSettings", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SpringSettings
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SpringSettings_free
  (JNIEnv *, jclass, jlong settingsVa) {
    SpringSettings * const pSettings
            = reinterpret_cast<SpringSettings *> (settingsVa);
    TRACE_DELETE("SpringSettings", pSettings)
    delete pSettings;
}

/*
 * Class:     com_github_stephengold_joltjni_SpringSettings
 * Method:    getDamping
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SpringSettings_getDamping
  (JNIEnv *, jclass, jlong settingsVa) {
    const SpringSettings * const pSettings
            = reinterpret_cast<SpringSettings *> (settingsVa);
    const float result = pSettings->mDamping;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SpringSettings
 * Method:    getFrequency
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SpringSettings_getFrequency
  (JNIEnv *, jclass, jlong settingsVa) {
    const SpringSettings * const pSettings
            = reinterpret_cast<SpringSettings *> (settingsVa);
    const float result = pSettings->mFrequency;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SpringSettings
 * Method:    getMode
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SpringSettings_getMode
  (JNIEnv *, jclass, jlong settingsVa) {
    const SpringSettings * const pSettings
            = reinterpret_cast<SpringSettings *> (settingsVa);
    const ESpringMode result = pSettings->mMode;
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_SpringSettings
 * Method:    getStiffness
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SpringSettings_getStiffness
  (JNIEnv *, jclass, jlong settingsVa) {
    const SpringSettings * const pSettings
            = reinterpret_cast<SpringSettings *> (settingsVa);
    const float result = pSettings->mStiffness;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SpringSettings
 * Method:    hasStiffness
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_SpringSettings_hasStiffness
  (JNIEnv *, jclass, jlong settingsVa) {
    const SpringSettings * const pSettings
            = reinterpret_cast<SpringSettings *> (settingsVa);
    const bool result = pSettings->HasStiffness();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SpringSettings
 * Method:    restoreBinaryState
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SpringSettings_restoreBinaryState
  (JNIEnv *, jclass, jlong settingsVa, jlong streamVa) {
    SpringSettings * const pSettings
            = reinterpret_cast<SpringSettings *> (settingsVa);
    StreamIn * const pStream = reinterpret_cast<StreamIn *> (streamVa);
    pSettings->RestoreBinaryState(*pStream);
}

/*
 * Class:     com_github_stephengold_joltjni_SpringSettings
 * Method:    saveBinaryState
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SpringSettings_saveBinaryState
  (JNIEnv *, jclass, jlong settingsVa, jlong streamVa) {
    const SpringSettings * const pSettings
            = reinterpret_cast<SpringSettings *> (settingsVa);
    StreamOut * const pStream = reinterpret_cast<StreamOut *> (streamVa);
    pSettings->SaveBinaryState(*pStream);
}

/*
 * Class:     com_github_stephengold_joltjni_SpringSettings
 * Method:    setDamping
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SpringSettings_setDamping
  (JNIEnv *, jclass, jlong settingsVa, jfloat damping) {
    SpringSettings * const pSettings
            = reinterpret_cast<SpringSettings *> (settingsVa);
    pSettings->mDamping = damping;
}

/*
 * Class:     com_github_stephengold_joltjni_SpringSettings
 * Method:    setFrequency
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SpringSettings_setFrequency
  (JNIEnv *, jclass, jlong settingsVa, jfloat frequency) {
    SpringSettings * const pSettings
            = reinterpret_cast<SpringSettings *> (settingsVa);
    pSettings->mFrequency = frequency;
}

/*
 * Class:     com_github_stephengold_joltjni_SpringSettings
 * Method:    setMode
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SpringSettings_setMode
  (JNIEnv *, jclass, jlong settingsVa, jint ordinal) {
    SpringSettings * const pSettings
            = reinterpret_cast<SpringSettings *> (settingsVa);
    pSettings->mMode = (ESpringMode) ordinal;
}

/*
 * Class:     com_github_stephengold_joltjni_SpringSettings
 * Method:    setStiffness
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SpringSettings_setStiffness
  (JNIEnv *, jclass, jlong settingsVa, jfloat stiffness) {
    SpringSettings * const pSettings
            = reinterpret_cast<SpringSettings *> (settingsVa);
    pSettings->mStiffness = stiffness;
}