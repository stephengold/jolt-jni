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
#include "Jolt/Physics/Collision/CollideShape.h"
#include "auto/com_github_stephengold_joltjni_CollideShapeSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_CollideShapeSettings
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CollideShapeSettings_createDefault
  BODYOF_CREATE_DEFAULT(CollideShapeSettings)

/*
 * Class:     com_github_stephengold_joltjni_CollideShapeSettings
 * Method:    getBackFaceMode
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_CollideShapeSettings_getBackFaceMode
  (JNIEnv *, jclass, jlong settingsVa) {
    const CollideShapeSettings * const pSettings
            = reinterpret_cast<CollideShapeSettings *> (settingsVa);
    const EBackFaceMode result = pSettings->mBackFaceMode;
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_CollideShapeSettings
 * Method:    getMaxSeparationDistance
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CollideShapeSettings_getMaxSeparationDistance
  (JNIEnv *, jclass, jlong settingsVa) {
    const CollideShapeSettings * const pSettings
            = reinterpret_cast<CollideShapeSettings *> (settingsVa);
    const float result = pSettings->mMaxSeparationDistance;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CollideShapeSettings
 * Method:    setBackFaceMode
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CollideShapeSettings_setBackFaceMode
  (JNIEnv *, jclass, jlong settingsVa, jint ordinal) {
    CollideShapeSettings * const pSettings
            = reinterpret_cast<CollideShapeSettings *> (settingsVa);
    const EBackFaceMode mode = (EBackFaceMode) ordinal;
    pSettings->mBackFaceMode = mode;
}

/*
 * Class:     com_github_stephengold_joltjni_CollideShapeSettings
 * Method:    setMaxSeparationDistance
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CollideShapeSettings_setMaxSeparationDistance
  (JNIEnv *, jclass, jlong settingsVa, jfloat distance) {
    CollideShapeSettings * const pSettings
            = reinterpret_cast<CollideShapeSettings *> (settingsVa);
    pSettings->mMaxSeparationDistance = distance;
}