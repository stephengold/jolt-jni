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
#include "Jolt/Physics/Body/BodyManager.h"
#include "auto/com_github_stephengold_joltjni_BodyManagerDrawSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_BodyManagerDrawSettings
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BodyManagerDrawSettings_createCopy
  (JNIEnv *, jclass, jlong originalVa) {
#ifdef JPH_DEBUG_RENDERER
    BodyManager::DrawSettings * const pOriginal
            = reinterpret_cast<BodyManager::DrawSettings *> (originalVa);
    BodyManager::DrawSettings * const pCopy
            = new BodyManager::DrawSettings(*pOriginal);
    TRACE_NEW("BodyManager::DrawSettings", pCopy)
    return reinterpret_cast<jlong> (pCopy);
#else
    return 0;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_BodyManagerDrawSettings
 * Method:    createDrawSettings
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BodyManagerDrawSettings_createDrawSettings
  (JNIEnv *, jclass) {
#ifdef JPH_DEBUG_RENDERER
    BodyManager::DrawSettings * const pResult = new BodyManager::DrawSettings();
    TRACE_NEW("BodyManager::DrawSettings", pResult)
    return reinterpret_cast<jlong> (pResult);
#else
    return 0;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_BodyManagerDrawSettings
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyManagerDrawSettings_free
  (JNIEnv *, jclass, jlong settingsVa) {
#ifdef JPH_DEBUG_RENDERER
    BodyManager::DrawSettings * const pSettings
            = reinterpret_cast<BodyManager::DrawSettings *> (settingsVa);
    TRACE_DELETE("BodyManager::DrawSettings", pSettings)
    delete pSettings;
#endif
}