/*
Copyright (c) 2026 Stephen Gold

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
#include "Jolt/Physics/Hair/Hair.h"
#include "auto/com_github_stephengold_joltjni_DrawSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_DrawSettings
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_DrawSettings_createCopy
  (JNIEnv *pEnv, jclass, jlong originalVa) {
#ifdef JPH_DEBUG_RENDERER
    Hair::DrawSettings * const pOriginal
            = reinterpret_cast<Hair::DrawSettings *> (originalVa);
    Hair::DrawSettings * const pCopy = new Hair::DrawSettings(*pOriginal);
    TRACE_NEW("Hair::DrawSettings", pCopy)
    return reinterpret_cast<jlong> (pCopy);
#else
    return 0;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DrawSettings
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_DrawSettings_createDefault
 (JNIEnv *pEnv, jclass) {
#ifdef JPH_DEBUG_RENDERER
    Hair::DrawSettings * const pResult = new Hair::DrawSettings();
    TRACE_NEW("Hair::DrawSettings", pResult)
    return reinterpret_cast<jlong> (pResult);
#else
    return 0;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DrawSettings
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DrawSettings_free
  (JNIEnv *, jclass, jlong settingsVa) {
#ifdef JPH_DEBUG_RENDERER
    Hair::DrawSettings * const pSettings
            = reinterpret_cast<Hair::DrawSettings *> (settingsVa);
    TRACE_DELETE("Hair::DrawSettings", pSettings)
    delete pSettings;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DrawSettings
 * Method:    getDrawAngularVelocity
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_DrawSettings_getDrawAngularVelocity
  (JNIEnv *, jclass, jlong settingsVa) {
#ifdef JPH_DEBUG_RENDERER
    const Hair::DrawSettings * const pSettings
        = reinterpret_cast<Hair::DrawSettings *> (settingsVa);
    const bool result = pSettings->mDrawAngularVelocity;
    return result;
#else
    return JNI_FALSE;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DrawSettings
 * Method:    getDrawGridDensity
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_DrawSettings_getDrawGridDensity
  (JNIEnv *, jclass, jlong settingsVa) {
#ifdef JPH_DEBUG_RENDERER
    const Hair::DrawSettings * const pSettings
        = reinterpret_cast<Hair::DrawSettings *> (settingsVa);
    const bool result = pSettings->mDrawGridDensity;
    return result;
#else
    return JNI_FALSE;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DrawSettings
 * Method:    getDrawGridVelocity
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_DrawSettings_getDrawGridVelocity
  (JNIEnv *, jclass, jlong settingsVa) {
#ifdef JPH_DEBUG_RENDERER
    const Hair::DrawSettings * const pSettings
        = reinterpret_cast<Hair::DrawSettings *> (settingsVa);
    const bool result = pSettings->mDrawGridVelocity;
    return result;
#else
    return JNI_FALSE;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DrawSettings
 * Method:    getDrawInitialGravity
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_DrawSettings_getDrawInitialGravity
  (JNIEnv *, jclass, jlong settingsVa) {
#ifdef JPH_DEBUG_RENDERER
    const Hair::DrawSettings * const pSettings
        = reinterpret_cast<Hair::DrawSettings *> (settingsVa);
    const bool result = pSettings->mDrawInitialGravity;
    return result;
#else
    return JNI_FALSE;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DrawSettings
 * Method:    getDrawNeutralDensity
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_DrawSettings_getDrawNeutralDensity
  (JNIEnv *, jclass, jlong settingsVa) {
#ifdef JPH_DEBUG_RENDERER
    const Hair::DrawSettings * const pSettings
        = reinterpret_cast<Hair::DrawSettings *> (settingsVa);
    const bool result = pSettings->mDrawNeutralDensity;
    return result;
#else
    return JNI_FALSE;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DrawSettings
 * Method:    getDrawOrientations
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_DrawSettings_getDrawOrientations
  (JNIEnv *, jclass, jlong settingsVa) {
#ifdef JPH_DEBUG_RENDERER
    const Hair::DrawSettings * const pSettings
        = reinterpret_cast<Hair::DrawSettings *> (settingsVa);
    const bool result = pSettings->mDrawOrientations;
    return result;
#else
    return JNI_FALSE;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DrawSettings
 * Method:    getDrawRenderStrands
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_DrawSettings_getDrawRenderStrands
  (JNIEnv *, jclass, jlong settingsVa) {
#ifdef JPH_DEBUG_RENDERER
    const Hair::DrawSettings * const pSettings
        = reinterpret_cast<Hair::DrawSettings *> (settingsVa);
    const bool result = pSettings->mDrawRenderStrands;
    return result;
#else
    return JNI_FALSE;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DrawSettings
 * Method:    getDrawRods
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_DrawSettings_getDrawRods
  (JNIEnv *, jclass, jlong settingsVa) {
#ifdef JPH_DEBUG_RENDERER
    const Hair::DrawSettings * const pSettings
        = reinterpret_cast<Hair::DrawSettings *> (settingsVa);
    const bool result = pSettings->mDrawRods;
    return result;
#else
    return JNI_FALSE;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DrawSettings
 * Method:    getDrawSkinPoints
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_DrawSettings_getDrawSkinPoints
  (JNIEnv *, jclass, jlong settingsVa) {
#ifdef JPH_DEBUG_RENDERER
    const Hair::DrawSettings * const pSettings
        = reinterpret_cast<Hair::DrawSettings *> (settingsVa);
    const bool result = pSettings->mDrawSkinPoints;
    return result;
#else
    return JNI_FALSE;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DrawSettings
 * Method:    getDrawUnloadedRods
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_DrawSettings_getDrawUnloadedRods
  (JNIEnv *, jclass, jlong settingsVa) {
#ifdef JPH_DEBUG_RENDERER
    const Hair::DrawSettings * const pSettings
        = reinterpret_cast<Hair::DrawSettings *> (settingsVa);
    const bool result = pSettings->mDrawUnloadedRods;
    return result;
#else
    return JNI_FALSE;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DrawSettings
 * Method:    getDrawVertexVelocity
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_DrawSettings_getDrawVertexVelocity
  (JNIEnv *, jclass, jlong settingsVa) {
#ifdef JPH_DEBUG_RENDERER
    const Hair::DrawSettings * const pSettings
        = reinterpret_cast<Hair::DrawSettings *> (settingsVa);
    const bool result = pSettings->mDrawVertexVelocity;
    return result;
#else
    return JNI_FALSE;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DrawSettings
 * Method:    getRenderStrandColor
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_DrawSettings_getRenderStrandColor
  (JNIEnv *, jclass, jlong settingsVa) {
#ifdef JPH_DEBUG_RENDERER
    const Hair::DrawSettings * const pSettings
        = reinterpret_cast<Hair::DrawSettings *> (settingsVa);
    const int result = (int) pSettings->mRenderStrandColor;
    return result;
#else
    return 0;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DrawSettings
 * Method:    getSimulationStrandBegin
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_DrawSettings_getSimulationStrandBegin
  (JNIEnv *, jclass, jlong settingsVa) {
#ifdef JPH_DEBUG_RENDERER
    const Hair::DrawSettings * const pSettings
        = reinterpret_cast<Hair::DrawSettings *> (settingsVa);
    const uint result = pSettings->mSimulationStrandBegin;
    return result;
#else
    return 0;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DrawSettings
 * Method:    getSimulationStrandEnd
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_DrawSettings_getSimulationStrandEnd
  (JNIEnv *, jclass, jlong settingsVa) {
#ifdef JPH_DEBUG_RENDERER
    const Hair::DrawSettings * const pSettings
        = reinterpret_cast<Hair::DrawSettings *> (settingsVa);
    const uint result = pSettings->mSimulationStrandEnd;
    return result;
#else
    return 0;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DrawSettings
 * Method:    setDrawAngularVelocity
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DrawSettings_setDrawAngularVelocity
  (JNIEnv *, jclass, jlong settingsVa, jboolean enable) {
#ifdef JPH_DEBUG_RENDERER
    Hair::DrawSettings * const pSettings
        = reinterpret_cast<Hair::DrawSettings *> (settingsVa);
    pSettings->mDrawAngularVelocity = enable;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DrawSettings
 * Method:    setDrawGridDensity
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DrawSettings_setDrawGridDensity
  (JNIEnv *, jclass, jlong settingsVa, jboolean enable) {
#ifdef JPH_DEBUG_RENDERER
    Hair::DrawSettings * const pSettings
        = reinterpret_cast<Hair::DrawSettings *> (settingsVa);
    pSettings->mDrawGridDensity = enable;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DrawSettings
 * Method:    setDrawGridVelocity
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DrawSettings_setDrawGridVelocity
  (JNIEnv *, jclass, jlong settingsVa, jboolean enable) {
#ifdef JPH_DEBUG_RENDERER
    Hair::DrawSettings * const pSettings
        = reinterpret_cast<Hair::DrawSettings *> (settingsVa);
    pSettings->mDrawGridVelocity = enable;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DrawSettings
 * Method:    setDrawInitialGravity
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DrawSettings_setDrawInitialGravity
  (JNIEnv *, jclass, jlong settingsVa, jboolean enable) {
#ifdef JPH_DEBUG_RENDERER
    Hair::DrawSettings * const pSettings
        = reinterpret_cast<Hair::DrawSettings *> (settingsVa);
    pSettings->mDrawInitialGravity = enable;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DrawSettings
 * Method:    setDrawNeutralDensity
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DrawSettings_setDrawNeutralDensity
  (JNIEnv *, jclass, jlong settingsVa, jboolean enable) {
#ifdef JPH_DEBUG_RENDERER
    Hair::DrawSettings * const pSettings
        = reinterpret_cast<Hair::DrawSettings *> (settingsVa);
    pSettings->mDrawNeutralDensity = enable;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DrawSettings
 * Method:    setDrawOrientations
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DrawSettings_setDrawOrientations
  (JNIEnv *, jclass, jlong settingsVa, jboolean enable) {
#ifdef JPH_DEBUG_RENDERER
    Hair::DrawSettings * const pSettings
        = reinterpret_cast<Hair::DrawSettings *> (settingsVa);
    pSettings->mDrawOrientations = enable;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DrawSettings
 * Method:    setDrawRenderStrands
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DrawSettings_setDrawRenderStrands
  (JNIEnv *, jclass, jlong settingsVa, jboolean enable) {
#ifdef JPH_DEBUG_RENDERER
    Hair::DrawSettings * const pSettings
        = reinterpret_cast<Hair::DrawSettings *> (settingsVa);
    pSettings->mDrawRenderStrands = enable;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DrawSettings
 * Method:    setDrawRods
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DrawSettings_setDrawRods
  (JNIEnv *, jclass, jlong settingsVa, jboolean enable) {
#ifdef JPH_DEBUG_RENDERER
    Hair::DrawSettings * const pSettings
        = reinterpret_cast<Hair::DrawSettings *> (settingsVa);
    pSettings->mDrawRods = enable;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DrawSettings
 * Method:    setDrawSkinPoints
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DrawSettings_setDrawSkinPoints
  (JNIEnv *, jclass, jlong settingsVa, jboolean enable) {
#ifdef JPH_DEBUG_RENDERER
    Hair::DrawSettings * const pSettings
        = reinterpret_cast<Hair::DrawSettings *> (settingsVa);
    pSettings->mDrawSkinPoints = enable;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DrawSettings
 * Method:    setDrawUnloadedRods
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DrawSettings_setDrawUnloadedRods
  (JNIEnv *, jclass, jlong settingsVa, jboolean enable) {
#ifdef JPH_DEBUG_RENDERER
    Hair::DrawSettings * const pSettings
        = reinterpret_cast<Hair::DrawSettings *> (settingsVa);
    pSettings->mDrawUnloadedRods = enable;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DrawSettings
 * Method:    setDrawVertexVelocity
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DrawSettings_setDrawVertexVelocity
  (JNIEnv *, jclass, jlong settingsVa, jboolean enable) {
#ifdef JPH_DEBUG_RENDERER
    Hair::DrawSettings * const pSettings
        = reinterpret_cast<Hair::DrawSettings *> (settingsVa);
    pSettings->mDrawVertexVelocity = enable;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DrawSettings
 * Method:    setRenderStrandColor
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DrawSettings_setRenderStrandColor
  (JNIEnv *, jclass, jlong settingsVa, jint ordinal) {
#ifdef JPH_DEBUG_RENDERER
    Hair::DrawSettings * const pSettings
        = reinterpret_cast<Hair::DrawSettings *> (settingsVa);
    pSettings->mRenderStrandColor = (Hair::ERenderStrandColor) ordinal;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DrawSettings
 * Method:    setSimulationStrandBegin
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DrawSettings_setSimulationStrandBegin
  (JNIEnv *, jclass, jlong settingsVa, jint strandIndex) {
#ifdef JPH_DEBUG_RENDERER
    Hair::DrawSettings * const pSettings
        = reinterpret_cast<Hair::DrawSettings *> (settingsVa);
    pSettings->mSimulationStrandBegin = strandIndex;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DrawSettings
 * Method:    setSimulationStrandEnd
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DrawSettings_setSimulationStrandEnd
  (JNIEnv *, jclass, jlong settingsVa, jint strandIndex) {
#ifdef JPH_DEBUG_RENDERER
    Hair::DrawSettings * const pSettings
        = reinterpret_cast<Hair::DrawSettings *> (settingsVa);
    pSettings->mSimulationStrandEnd = strandIndex;
#endif
}