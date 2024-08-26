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
#include <Jolt/Physics/Character/CharacterBase.h>
#include "auto/com_github_stephengold_joltjni_CharacterBaseSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_CharacterBaseSettings
 * Method:    getEnhancedInternalEdgeRemoval
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_CharacterBaseSettings_getEnhancedInternalEdgeRemoval
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterBaseSettings * const pSettings
            = reinterpret_cast<CharacterBaseSettings *> (settingsVa);
    const bool result = pSettings->mEnhancedInternalEdgeRemoval;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBaseSettings
 * Method:    getMaxSlopeAngle
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterBaseSettings_getMaxSlopeAngle
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterBaseSettings * const pSettings
            = reinterpret_cast<CharacterBaseSettings *> (settingsVa);
    const float result = pSettings->mMaxSlopeAngle;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBaseSettings
 * Method:    getShape
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CharacterBaseSettings_getShape
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterBaseSettings * const pSettings
            = reinterpret_cast<CharacterBaseSettings *> (settingsVa);
    const Shape * const pResult = pSettings->mShape;
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBaseSettings
 * Method:    getSupportingVolumeC
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterBaseSettings_getSupportingVolumeC
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterBaseSettings * const pSettings
            = reinterpret_cast<CharacterBaseSettings *> (settingsVa);
    const float result = pSettings->mSupportingVolume.GetConstant();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBaseSettings
 * Method:    getSupportingVolumeNx
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterBaseSettings_getSupportingVolumeNx
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterBaseSettings * const pSettings
            = reinterpret_cast<CharacterBaseSettings *> (settingsVa);
    const float result = pSettings->mSupportingVolume.GetNormal().GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBaseSettings
 * Method:    getSupportingVolumeNy
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterBaseSettings_getSupportingVolumeNy
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterBaseSettings * const pSettings
            = reinterpret_cast<CharacterBaseSettings *> (settingsVa);
    const float result = pSettings->mSupportingVolume.GetNormal().GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBaseSettings
 * Method:    getSupportingVolumeNz
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterBaseSettings_getSupportingVolumeNz
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterBaseSettings * const pSettings
            = reinterpret_cast<CharacterBaseSettings *> (settingsVa);
    const float result = pSettings->mSupportingVolume.GetNormal().GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBaseSettings
 * Method:    getUpX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterBaseSettings_getUpX
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterBaseSettings * const pSettings
            = reinterpret_cast<CharacterBaseSettings *> (settingsVa);
    const float result = pSettings->mUp.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBaseSettings
 * Method:    getUpY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterBaseSettings_getUpY
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterBaseSettings * const pSettings
            = reinterpret_cast<CharacterBaseSettings *> (settingsVa);
    const float result = pSettings->mUp.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBaseSettings
 * Method:    getUpZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterBaseSettings_getUpZ
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterBaseSettings * const pSettings
            = reinterpret_cast<CharacterBaseSettings *> (settingsVa);
    const float result = pSettings->mUp.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBaseSettings
 * Method:    setEnhancedInternalEdgeRemoval
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterBaseSettings_setEnhancedInternalEdgeRemoval
  (JNIEnv *, jclass, jlong settingsVa, jboolean remove) {
    CharacterBaseSettings * const pSettings
            = reinterpret_cast<CharacterBaseSettings *> (settingsVa);
    pSettings->mEnhancedInternalEdgeRemoval = remove;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBaseSettings
 * Method:    setMaxSlopeAngle
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterBaseSettings_setMaxSlopeAngle
  (JNIEnv *, jclass, jlong settingsVa, jfloat angle) {
    CharacterBaseSettings * const pSettings
            = reinterpret_cast<CharacterBaseSettings *> (settingsVa);
    pSettings->mMaxSlopeAngle = angle;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBaseSettings
 * Method:    setShape
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterBaseSettings_setShape
  (JNIEnv *, jclass, jlong settingsVa, jlong shapeVa) {
    CharacterBaseSettings * const pSettings
            = reinterpret_cast<CharacterBaseSettings *> (settingsVa);
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    pSettings->mShape = pShape;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBaseSettings
 * Method:    setSupportingVolume
 * Signature: (JFFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterBaseSettings_setSupportingVolume
  (JNIEnv *, jclass, jlong settingsVa, jfloat nx, jfloat ny, jfloat nz, jfloat c) {
    CharacterBaseSettings * const pSettings
            = reinterpret_cast<CharacterBaseSettings *> (settingsVa);
    const Vec3 normal(nx, ny, nz);
    const Plane plane(normal, c);
    pSettings->mSupportingVolume = plane;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterBaseSettings
 * Method:    setUp
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterBaseSettings_setUp
  (JNIEnv *, jclass, jlong settingsVa, jfloat dx, jfloat dy, jfloat dz) {
    CharacterBaseSettings * const pSettings
            = reinterpret_cast<CharacterBaseSettings *> (settingsVa);
    const Vec3 direction(dx, dy, dz);
    pSettings->mUp = direction;
}