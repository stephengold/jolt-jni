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
#include <Jolt/Physics/Collision/ShapeCast.h>
#include "auto/com_github_stephengold_joltjni_ShapeCastSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_ShapeCastSettings
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ShapeCastSettings_createDefault
  (JNIEnv *, jclass) {
    ShapeCastSettings * const pSettings = new ShapeCastSettings();
    TRACE_NEW("ShapeCastSettings", pSettings);
    return reinterpret_cast<jlong> (pSettings);
}

/*
 * Class:     com_github_stephengold_joltjni_ShapeCastSettings
 * Method:    getBackFaceModeConvex
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_ShapeCastSettings_getBackFaceModeConvex
  (JNIEnv *, jclass, jlong settingsVa) {
    const ShapeCastSettings * const pSettings
            = reinterpret_cast<ShapeCastSettings *> (settingsVa);
    const EBackFaceMode result = pSettings->mBackFaceModeConvex;
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_ShapeCastSettings
 * Method:    getBackFaceModeTriangles
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_ShapeCastSettings_getBackFaceModeTriangles
  (JNIEnv *, jclass, jlong settingsVa) {
    const ShapeCastSettings * const pSettings
            = reinterpret_cast<ShapeCastSettings *> (settingsVa);
    const EBackFaceMode result = pSettings->mBackFaceModeTriangles;
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_ShapeCastSettings
 * Method:    getReturnDeepestPoint
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ShapeCastSettings_getReturnDeepestPoint
  (JNIEnv *, jclass, jlong settingsVa) {
    const ShapeCastSettings * const pSettings
            = reinterpret_cast<ShapeCastSettings *> (settingsVa);
    const bool result = pSettings->mReturnDeepestPoint;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ShapeCastSettings
 * Method:    getUseShrunkenShapeAndConvexRadius
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ShapeCastSettings_getUseShrunkenShapeAndConvexRadius
  (JNIEnv *, jclass, jlong settingsVa) {
    const ShapeCastSettings * const pSettings
            = reinterpret_cast<ShapeCastSettings *> (settingsVa);
    const bool result = pSettings->mUseShrunkenShapeAndConvexRadius;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ShapeCastSettings
 * Method:    setBackFaceModeConvex
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ShapeCastSettings_setBackFaceModeConvex
  (JNIEnv *, jclass, jlong settingsVa, jint ordinal) {
    ShapeCastSettings * const pSettings
            = reinterpret_cast<ShapeCastSettings *> (settingsVa);
    const EBackFaceMode mode = (EBackFaceMode) ordinal;
    pSettings->mBackFaceModeConvex = mode;
}

/*
 * Class:     com_github_stephengold_joltjni_ShapeCastSettings
 * Method:    setBackFaceModeTriangles
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ShapeCastSettings_setBackFaceModeTriangles
  (JNIEnv *, jclass, jlong settingsVa, jint ordinal) {
    ShapeCastSettings * const pSettings
            = reinterpret_cast<ShapeCastSettings *> (settingsVa);
    const EBackFaceMode mode = (EBackFaceMode) ordinal;
    pSettings->mBackFaceModeTriangles = mode;
}

/*
 * Class:     com_github_stephengold_joltjni_ShapeCastSettings
 * Method:    setReturnDeepestPoint
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ShapeCastSettings_setReturnDeepestPoint
  (JNIEnv *, jclass, jlong settingsVa, jboolean enable) {
    ShapeCastSettings * const pSettings
            = reinterpret_cast<ShapeCastSettings *> (settingsVa);
    pSettings->mReturnDeepestPoint = enable;
}

/*
 * Class:     com_github_stephengold_joltjni_ShapeCastSettings
 * Method:    setUseShrunkenShapeAndConvexRadius
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ShapeCastSettings_setUseShrunkenShapeAndConvexRadius
  (JNIEnv *, jclass, jlong settingsVa, jboolean enable) {
    ShapeCastSettings * const pSettings
            = reinterpret_cast<ShapeCastSettings *> (settingsVa);
    pSettings->mUseShrunkenShapeAndConvexRadius = enable;
}