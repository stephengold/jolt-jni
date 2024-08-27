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
#include <Jolt/Physics/Collision/Shape/RotatedTranslatedShape.h>
#include "auto/com_github_stephengold_joltjni_RotatedTranslatedShapeSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_RotatedTranslatedShapeSettings
 * Method:    createSettingsFromShapeRef
 * Signature: (FFFFFFFJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_RotatedTranslatedShapeSettings_createSettingsFromShapeRef
  (JNIEnv *, jclass, jfloat offsetX, jfloat offsetY, jfloat offsetZ,
  jfloat rotX, jfloat rotY, jfloat rotZ, jfloat rotW, jlong baseShapeRefVa) {
    const Vec3 offset(offsetX, offsetY, offsetZ);
    const Quat rotation(rotX, rotY, rotZ, rotW);
    const ShapeRefC * const pBaseRef
        = reinterpret_cast<ShapeRefC *> (baseShapeRefVa);
    RotatedTranslatedShapeSettings * const pResult
            = new RotatedTranslatedShapeSettings(
                    offset, rotation, pBaseRef->GetPtr());
    TRACE_NEW("RotatedTranslatedShapeSettings", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_RotatedTranslatedShapeSettings
 * Method:    getPositionX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_RotatedTranslatedShapeSettings_getPositionX
  (JNIEnv *, jclass, jlong rtsVa) {
    const RotatedTranslatedShapeSettings * const pSettings
        = reinterpret_cast<RotatedTranslatedShapeSettings *> (rtsVa);
    const Vec3 position = pSettings->mPosition;
    return position.GetX();
}

/*
 * Class:     com_github_stephengold_joltjni_RotatedTranslatedShapeSettings
 * Method:    getPositionY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_RotatedTranslatedShapeSettings_getPositionY
  (JNIEnv *, jclass, jlong rtsVa) {
    const RotatedTranslatedShapeSettings * const pSettings
        = reinterpret_cast<RotatedTranslatedShapeSettings *> (rtsVa);
    const Vec3 position = pSettings->mPosition;
    return position.GetY();
}

/*
 * Class:     com_github_stephengold_joltjni_RotatedTranslatedShapeSettings
 * Method:    getPositionZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_RotatedTranslatedShapeSettings_getPositionZ
  (JNIEnv *, jclass, jlong rtsVa) {
    const RotatedTranslatedShapeSettings * const pSettings
        = reinterpret_cast<RotatedTranslatedShapeSettings *> (rtsVa);
    const Vec3 position = pSettings->mPosition;
    return position.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_RotatedTranslatedShapeSettings
 * Method:    getRotationW
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_RotatedTranslatedShapeSettings_getRotationW
  (JNIEnv *, jclass, jlong rtsVa) {
    const RotatedTranslatedShapeSettings * const pSettings
        = reinterpret_cast<RotatedTranslatedShapeSettings *> (rtsVa);
    const Quat rotation = pSettings->mRotation;
    return rotation.GetW();
}

/*
 * Class:     com_github_stephengold_joltjni_RotatedTranslatedShapeSettings
 * Method:    getRotationX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_RotatedTranslatedShapeSettings_getRotationX
  (JNIEnv *, jclass, jlong rtsVa) {
    const RotatedTranslatedShapeSettings * const pSettings
        = reinterpret_cast<RotatedTranslatedShapeSettings *> (rtsVa);
    const Quat rotation = pSettings->mRotation;
    return rotation.GetX();
}

/*
 * Class:     com_github_stephengold_joltjni_RotatedTranslatedShapeSettings
 * Method:    getRotationY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_RotatedTranslatedShapeSettings_getRotationY
  (JNIEnv *, jclass, jlong rtsVa) {
    const RotatedTranslatedShapeSettings * const pSettings
        = reinterpret_cast<RotatedTranslatedShapeSettings *> (rtsVa);
    const Quat rotation = pSettings->mRotation;
    return rotation.GetY();
}

/*
 * Class:     com_github_stephengold_joltjni_RotatedTranslatedShapeSettings
 * Method:    getRotationZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_RotatedTranslatedShapeSettings_getRotationZ
  (JNIEnv *, jclass, jlong settingsVa) {
    const RotatedTranslatedShapeSettings * const pSettings
            = reinterpret_cast<RotatedTranslatedShapeSettings *> (settingsVa);
    const Quat rotation = pSettings->mRotation;
    return rotation.GetZ();
}