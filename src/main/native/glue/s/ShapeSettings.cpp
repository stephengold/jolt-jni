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
#include <Jolt/Physics/Collision/Shape/Shape.h>
#include "auto/com_github_stephengold_joltjni_ShapeSettings.h"
#include "auto/com_github_stephengold_joltjni_ShapeSettingsRef.h"
#include "glue/glue.h"

using namespace JPH;

IMPLEMENT_REF(ShapeSettings,
  Java_com_github_stephengold_joltjni_ShapeSettingsRef_copy,
  Java_com_github_stephengold_joltjni_ShapeSettingsRef_free,
  Java_com_github_stephengold_joltjni_ShapeSettingsRef_getPtr)

/*
 * Class:     com_github_stephengold_joltjni_ShapeSettings
 * Method:    clearCachedResult
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ShapeSettings_clearCachedResult
  (JNIEnv *, jclass, jlong settingsVa) {
    ShapeSettings * const pSettings = reinterpret_cast<ShapeSettings *> (settingsVa);
    pSettings->ClearCachedResult();
}

/*
 * Class:     com_github_stephengold_joltjni_ShapeSettings
 * Method:    create
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ShapeSettings_create
  (JNIEnv *, jclass, jlong settingsVa) {
    const ShapeSettings * const pSettings
            = reinterpret_cast<ShapeSettings *> (settingsVa);
    ShapeSettings::ShapeResult *pResult = new ShapeSettings::ShapeResult();
    TRACE_NEW("ShapeResult", pResult)
    *pResult = pSettings->Create();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_ShapeSettings
 * Method:    getRefCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_ShapeSettings_getRefCount
  (JNIEnv *, jclass, jlong settingsVa) {
    const ShapeSettings * const pSettings
            = reinterpret_cast<ShapeSettings *> (settingsVa);
    const uint32 result = pSettings->GetRefCount();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ShapeSettings
 * Method:    getUserData
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ShapeSettings_getUserData
  (JNIEnv *, jclass, jlong settingsVa) {
    const ShapeSettings * const pSettings
            = reinterpret_cast<ShapeSettings *> (settingsVa);
    const uint64 result = pSettings->mUserData;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ShapeSettings
 * Method:    setUserData
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ShapeSettings_setUserData
  (JNIEnv *, jclass, jlong settingsVa, jlong value) {
    ShapeSettings * const pSettings
            = reinterpret_cast<ShapeSettings *> (settingsVa);
    pSettings->mUserData = value;
}

/*
 * Class:     com_github_stephengold_joltjni_ShapeSettings
 * Method:    toRef
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ShapeSettings_toRef
  (JNIEnv *, jclass, jlong settingsVa) {
    ShapeSettings * const pSettings
            = reinterpret_cast<ShapeSettings *> (settingsVa);
    Ref<ShapeSettings> * const pResult = new Ref<ShapeSettings>(pSettings);
    TRACE_NEW("Ref<ShapeSettings>", pResult)
    return reinterpret_cast<jlong> (pResult);
}