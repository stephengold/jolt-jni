/*
Copyright (c) 2025 Stephen Gold

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
#include "Jolt/Physics/SoftBody/SoftBodyContactListener.h"
#include "auto/com_github_stephengold_joltjni_SoftBodyContactSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyContactSettings
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SoftBodyContactSettings_createDefault
  (JNIEnv *, jclass) {
    SoftBodyContactSettings * const pSettings = new SoftBodyContactSettings();
    TRACE_NEW("SoftBodyContactSettings", pSettings)
    return reinterpret_cast<jlong> (pSettings);
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyContactSettings
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodyContactSettings_free
  (JNIEnv *, jclass, jlong settingsVa) {
    SoftBodyContactSettings * const pSettings
            = reinterpret_cast<SoftBodyContactSettings *> (settingsVa);
    TRACE_DELETE("ContactSettings", pSettings)
    delete pSettings;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyContactSettings
 * Method:    getInvInertiaScale2
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SoftBodyContactSettings_getInvInertiaScale2
  (JNIEnv *, jclass, jlong settingsVa) {
    const SoftBodyContactSettings * const pSettings
            = reinterpret_cast<SoftBodyContactSettings *> (settingsVa);
    const float result = pSettings->mInvInertiaScale2;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyContactSettings
 * Method:    getInvMassScale1
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SoftBodyContactSettings_getInvMassScale1
  (JNIEnv *, jclass, jlong settingsVa) {
    const SoftBodyContactSettings * const pSettings
            = reinterpret_cast<SoftBodyContactSettings *> (settingsVa);
    const float result = pSettings->mInvMassScale1;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyContactSettings
 * Method:    getInvMassScale2
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SoftBodyContactSettings_getInvMassScale2
  (JNIEnv *, jclass, jlong settingsVa) {
    const SoftBodyContactSettings * const pSettings
            = reinterpret_cast<SoftBodyContactSettings *> (settingsVa);
    const float result = pSettings->mInvMassScale2;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyContactSettings
 * Method:    getIsSensor
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_SoftBodyContactSettings_getIsSensor
  (JNIEnv *, jclass, jlong settingsVa) {
    const SoftBodyContactSettings * const pSettings
            = reinterpret_cast<SoftBodyContactSettings *> (settingsVa);
    const bool result = pSettings->mIsSensor;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyContactSettings
 * Method:    setInvInertiaScale2
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodyContactSettings_setInvInertiaScale2
  (JNIEnv *, jclass, jlong settingsVa, jfloat scale) {
    SoftBodyContactSettings * const pSettings
            = reinterpret_cast<SoftBodyContactSettings *> (settingsVa);
    pSettings->mInvInertiaScale2 = scale;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyContactSettings
 * Method:    setInvMassScale1
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodyContactSettings_setInvMassScale1
  (JNIEnv *, jclass, jlong settingsVa, jfloat scale) {
    SoftBodyContactSettings * const pSettings
            = reinterpret_cast<SoftBodyContactSettings *> (settingsVa);
    pSettings->mInvMassScale1 = scale;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyContactSettings
 * Method:    setInvMassScale2
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodyContactSettings_setInvMassScale2
  (JNIEnv *, jclass, jlong settingsVa, jfloat scale) {
    SoftBodyContactSettings * const pSettings
            = reinterpret_cast<SoftBodyContactSettings *> (settingsVa);
    pSettings->mInvMassScale2 = scale;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyContactSettings
 * Method:    setIsSensor
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodyContactSettings_setIsSensor
  (JNIEnv *, jclass, jlong settingsVa, jboolean setting) {
    SoftBodyContactSettings * const pSettings
            = reinterpret_cast<SoftBodyContactSettings *> (settingsVa);
    pSettings->mIsSensor = setting;
}