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
#include "Jolt/Physics/Collision/Shape/OffsetCenterOfMassShape.h"
#include "auto/com_github_stephengold_joltjni_OffsetCenterOfMassShapeSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_OffsetCenterOfMassShapeSettings
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_OffsetCenterOfMassShapeSettings_createCopy
  BODYOF_CREATE_COPY_TARGET(OffsetCenterOfMassShapeSettings)

/*
 * Class:     com_github_stephengold_joltjni_OffsetCenterOfMassShapeSettings
 * Method:    createSettingsFromSettings
 * Signature: (FFFJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_OffsetCenterOfMassShapeSettings_createSettingsFromSettings
  (JNIEnv *, jclass, jfloat offsetX, jfloat offsetY, jfloat offsetZ, jlong baseShapeSettingsVa) {
    const Vec3 offset(offsetX, offsetY, offsetZ);
    const ShapeSettings * const pSettings
            = reinterpret_cast<ShapeSettings *> (baseShapeSettingsVa);
    OffsetCenterOfMassShapeSettings * const pResult
            = new OffsetCenterOfMassShapeSettings(offset, pSettings);
    TRACE_NEW_TARGET("OffsetCenterOfMassShapeSettings", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_OffsetCenterOfMassShapeSettings
 * Method:    createSettingsFromShape
 * Signature: (FFFJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_OffsetCenterOfMassShapeSettings_createSettingsFromShape
  (JNIEnv *, jclass, jfloat offsetX, jfloat offsetY, jfloat offsetZ, jlong baseShapeVa) {
    const Vec3 offset(offsetX, offsetY, offsetZ);
    const Shape * const pBase = reinterpret_cast<Shape *> (baseShapeVa);
    OffsetCenterOfMassShapeSettings * const pResult
            = new OffsetCenterOfMassShapeSettings(offset, pBase);
    TRACE_NEW_TARGET("OffsetCenterOfMassShapeSettings", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_OffsetCenterOfMassShapeSettings
 * Method:    getOffsetX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_OffsetCenterOfMassShapeSettings_getOffsetX
  (JNIEnv *, jclass, jlong ocomssVa) {
    const OffsetCenterOfMassShapeSettings * const pSettings
            = reinterpret_cast<OffsetCenterOfMassShapeSettings *> (ocomssVa);
    const float result = pSettings->mOffset.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_OffsetCenterOfMassShapeSettings
 * Method:    getOffsetY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_OffsetCenterOfMassShapeSettings_getOffsetY
  (JNIEnv *, jclass, jlong ocomssVa) {
    const OffsetCenterOfMassShapeSettings * const pSettings
            = reinterpret_cast<OffsetCenterOfMassShapeSettings *> (ocomssVa);
    const float result = pSettings->mOffset.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_OffsetCenterOfMassShapeSettings
 * Method:    getOffsetZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_OffsetCenterOfMassShapeSettings_getOffsetZ
  (JNIEnv *, jclass, jlong ocomssVa) {
    const OffsetCenterOfMassShapeSettings * const pSettings
            = reinterpret_cast<OffsetCenterOfMassShapeSettings *> (ocomssVa);
    const float result = pSettings->mOffset.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_OffsetCenterOfMassShapeSettings
 * Method:    setOffset
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_OffsetCenterOfMassShapeSettings_setOffset
  (JNIEnv *, jclass, jlong ocomssVa, jfloat offsetX, jfloat offsetY, jfloat offsetZ) {
    OffsetCenterOfMassShapeSettings * const pSettings
            = reinterpret_cast<OffsetCenterOfMassShapeSettings *> (ocomssVa);
    const Vec3 offset(offsetX, offsetY, offsetZ);
    pSettings->mOffset = offset;
}