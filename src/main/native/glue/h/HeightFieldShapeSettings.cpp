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
#include <Jolt/Physics/Collision/Shape/HeightFieldShape.h>
#include "auto/com_github_stephengold_joltjni_HeightFieldShapeSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_HeightFieldShapeSettings
 * Method:    createSettingsFromArray
 * Signature: ([FFFFFFFI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_HeightFieldShapeSettings_createSettingsFromArray
  (JNIEnv *pEnv, jclass, jfloatArray samples, jfloat offsetX, jfloat offsetY,
  jfloat offsetZ, jfloat scaleX, jfloat scaleY, jfloat scaleZ, jint sampleCount) {
    jboolean isCopy;
    float * const pSamples = pEnv->GetFloatArrayElements(samples, &isCopy);
    const Vec3 offset(offsetX, offsetY, offsetZ);
    const Vec3 scale(scaleX, scaleY, scaleZ);
    HeightFieldShapeSettings * const pSettings
            = new HeightFieldShapeSettings(pSamples, offset, scale, sampleCount);
    TRACE_NEW("HeightFieldShapeSettings", pSettings)
    pEnv->ReleaseFloatArrayElements(samples, pSamples, JNI_ABORT);
    return reinterpret_cast<jlong> (pSettings);
}

/*
 * Class:     com_github_stephengold_joltjni_HeightFieldShapeSettings
 * Method:    createHeightFieldShapeSettings
 * Signature: (Ljava/nio/FloatBuffer;FFFFFFI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_HeightFieldShapeSettings_createHeightFieldShapeSettings
  (JNIEnv *pEnv, jclass, jobject buffer, jfloat offsetX, jfloat offsetY, jfloat offsetZ,
  jfloat scaleX, jfloat scaleY, jfloat scaleZ, jint sampleCount) {
    const float * const pFloats
            = (float *) pEnv->GetDirectBufferAddress(buffer);
    const Vec3 offset(offsetX, offsetY, offsetZ);
    const Vec3 scale(scaleX, scaleY, scaleZ);
    HeightFieldShapeSettings * const pSettings
            = new HeightFieldShapeSettings(pFloats, offset, scale, sampleCount);
    TRACE_NEW("HeightFieldShapeSettings", pSettings)
    return reinterpret_cast<jlong> (pSettings);
}

/*
 * Class:     com_github_stephengold_joltjni_HeightFieldShapeSettings
 * Method:    getActiveEdgeCosThresholdAngle
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HeightFieldShapeSettings_getActiveEdgeCosThresholdAngle
  (JNIEnv *, jclass, jlong settingsVa) {
    const HeightFieldShapeSettings * const pSettings
            = reinterpret_cast<HeightFieldShapeSettings *> (settingsVa);
    const float result = pSettings->mActiveEdgeCosThresholdAngle;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HeightFieldShapeSettings
 * Method:    getBitsPerSample
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_HeightFieldShapeSettings_getBitsPerSample
  (JNIEnv *, jclass, jlong settingsVa) {
    const HeightFieldShapeSettings * const pSettings
            = reinterpret_cast<HeightFieldShapeSettings *> (settingsVa);
    const uint32 result = pSettings->mBitsPerSample;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HeightFieldShapeSettings
 * Method:    getBlockSize
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_HeightFieldShapeSettings_getBlockSize
  (JNIEnv *, jclass, jlong settingsVa) {
    const HeightFieldShapeSettings * const pSettings
            = reinterpret_cast<HeightFieldShapeSettings *> (settingsVa);
    const uint32 result = pSettings->mBlockSize;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HeightFieldShapeSettings
 * Method:    getMaxHeightValue
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HeightFieldShapeSettings_getMaxHeightValue
  (JNIEnv *, jclass, jlong settingsVa) {
    const HeightFieldShapeSettings * const pSettings
            = reinterpret_cast<HeightFieldShapeSettings *> (settingsVa);
    const float result = pSettings->mMaxHeightValue;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HeightFieldShapeSettings
 * Method:    getMinHeightValue
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HeightFieldShapeSettings_getMinHeightValue
  (JNIEnv *, jclass, jlong settingsVa) {
    const HeightFieldShapeSettings * const pSettings
            = reinterpret_cast<HeightFieldShapeSettings *> (settingsVa);
    const float result = pSettings->mMinHeightValue;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HeightFieldShapeSettings
 * Method:    getOffsetX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HeightFieldShapeSettings_getOffsetX
  (JNIEnv *, jclass, jlong settingsVa) {
    const HeightFieldShapeSettings * const pSettings
            = reinterpret_cast<HeightFieldShapeSettings *> (settingsVa);
    const float result = pSettings->mOffset.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HeightFieldShapeSettings
 * Method:    getOffsetY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HeightFieldShapeSettings_getOffsetY
  (JNIEnv *, jclass, jlong settingsVa) {
    const HeightFieldShapeSettings * const pSettings
            = reinterpret_cast<HeightFieldShapeSettings *> (settingsVa);
    const float result = pSettings->mOffset.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HeightFieldShapeSettings
 * Method:    getOffsetZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HeightFieldShapeSettings_getOffsetZ
  (JNIEnv *, jclass, jlong settingsVa) {
    const HeightFieldShapeSettings * const pSettings
            = reinterpret_cast<HeightFieldShapeSettings *> (settingsVa);
    const float result = pSettings->mOffset.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HeightFieldShapeSettings
 * Method:    getSampleCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_HeightFieldShapeSettings_getSampleCount
  (JNIEnv *, jclass, jlong settingsVa) {
    const HeightFieldShapeSettings * const pSettings
            = reinterpret_cast<HeightFieldShapeSettings *> (settingsVa);
    const uint32 result = pSettings->mSampleCount;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HeightFieldShapeSettings
 * Method:    getScaleX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HeightFieldShapeSettings_getScaleX
  (JNIEnv *, jclass, jlong settingsVa) {
    const HeightFieldShapeSettings * const pSettings
            = reinterpret_cast<HeightFieldShapeSettings *> (settingsVa);
    const float result = pSettings->mScale.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HeightFieldShapeSettings
 * Method:    getScaleY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HeightFieldShapeSettings_getScaleY
  (JNIEnv *, jclass, jlong settingsVa) {
    const HeightFieldShapeSettings * const pSettings
            = reinterpret_cast<HeightFieldShapeSettings *> (settingsVa);
    const float result = pSettings->mScale.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HeightFieldShapeSettings
 * Method:    getScaleZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HeightFieldShapeSettings_getScaleZ
  (JNIEnv *, jclass, jlong settingsVa) {
    const HeightFieldShapeSettings * const pSettings
            = reinterpret_cast<HeightFieldShapeSettings *> (settingsVa);
    const float result = pSettings->mScale.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HeightFieldShapeSettings
 * Method:    setActiveEdgeCosThresholdAngle
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HeightFieldShapeSettings_setActiveEdgeCosThresholdAngle
  (JNIEnv *, jclass, jlong settingsVa, jfloat cosine) {
    HeightFieldShapeSettings * const pSettings
            = reinterpret_cast<HeightFieldShapeSettings *> (settingsVa);
    pSettings->mActiveEdgeCosThresholdAngle = cosine;
}

/*
 * Class:     com_github_stephengold_joltjni_HeightFieldShapeSettings
 * Method:    setBitsPerSample
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HeightFieldShapeSettings_setBitsPerSample
  (JNIEnv *, jclass, jlong settingsVa, jint numBits) {
    HeightFieldShapeSettings * const pSettings
            = reinterpret_cast<HeightFieldShapeSettings *> (settingsVa);
    pSettings->mBitsPerSample = numBits;
}

/*
 * Class:     com_github_stephengold_joltjni_HeightFieldShapeSettings
 * Method:    setBlockSize
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HeightFieldShapeSettings_setBlockSize
  (JNIEnv *, jclass, jlong settingsVa, jint numRows) {
    HeightFieldShapeSettings * const pSettings
            = reinterpret_cast<HeightFieldShapeSettings *> (settingsVa);
    pSettings->mBlockSize = numRows;
}

/*
 * Class:     com_github_stephengold_joltjni_HeightFieldShapeSettings
 * Method:    setMaxHeightValue
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HeightFieldShapeSettings_setMaxHeightValue
  (JNIEnv *, jclass, jlong settingsVa, jfloat height) {
    HeightFieldShapeSettings * const pSettings
            = reinterpret_cast<HeightFieldShapeSettings *> (settingsVa);
    pSettings->mMaxHeightValue = height;
}

/*
 * Class:     com_github_stephengold_joltjni_HeightFieldShapeSettings
 * Method:    setMinHeightValue
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HeightFieldShapeSettings_setMinHeightValue
  (JNIEnv *, jclass, jlong settingsVa, jfloat height) {
    HeightFieldShapeSettings * const pSettings
            = reinterpret_cast<HeightFieldShapeSettings *> (settingsVa);
    pSettings->mMinHeightValue = height;
}

/*
 * Class:     com_github_stephengold_joltjni_HeightFieldShapeSettings
 * Method:    setOffset
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HeightFieldShapeSettings_setOffset
  (JNIEnv *, jclass, jlong settingsVa, jfloat dx, jfloat dy, jfloat dz) {
    HeightFieldShapeSettings * const pSettings
            = reinterpret_cast<HeightFieldShapeSettings *> (settingsVa);
    const Vec3 offset(dx, dy, dz);
    pSettings->mOffset = offset;
}

/*
 * Class:     com_github_stephengold_joltjni_HeightFieldShapeSettings
 * Method:    setScale
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HeightFieldShapeSettings_setScale
  (JNIEnv *, jclass, jlong settingsVa, jfloat sx, jfloat sy, jfloat sz) {
    HeightFieldShapeSettings * const pSettings
            = reinterpret_cast<HeightFieldShapeSettings *> (settingsVa);
    const Vec3 scale(sx, sy, sz);
    pSettings->mScale = scale;
}