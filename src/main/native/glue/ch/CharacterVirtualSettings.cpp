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
#include "Jolt/Physics/Character/CharacterVirtual.h"

#include "auto/com_github_stephengold_joltjni_CharacterVirtualSettings.h"
#include "auto/com_github_stephengold_joltjni_CharacterVirtualSettingsRef.h"
#include "glue/glue.h"

using namespace JPH;

IMPLEMENT_REF(CharacterVirtualSettings,
  Java_com_github_stephengold_joltjni_CharacterVirtualSettingsRef_copy,
  Java_com_github_stephengold_joltjni_CharacterVirtualSettingsRef_createEmpty,
  Java_com_github_stephengold_joltjni_CharacterVirtualSettingsRef_free,
  Java_com_github_stephengold_joltjni_CharacterVirtualSettingsRef_getPtr,
  Java_com_github_stephengold_joltjni_CharacterVirtualSettingsRef_toRefC)

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtualSettings
 * Method:    createCharacterVirtualSettings
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CharacterVirtualSettings_createCharacterVirtualSettings
  (JNIEnv *, jclass) {
    CharacterVirtualSettings * const pResult = new CharacterVirtualSettings();
    TRACE_NEW("CharacterVirtualSettings", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtualSettings
 * Method:    getBackFaceMode
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_CharacterVirtualSettings_getBackFaceMode
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterVirtualSettings * const pSettings
            = reinterpret_cast<CharacterVirtualSettings *> (settingsVa);
    const EBackFaceMode result = pSettings->mBackFaceMode;
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtualSettings
 * Method:    getCharacterPadding
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterVirtualSettings_getCharacterPadding
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterVirtualSettings * const pSettings
            = reinterpret_cast<CharacterVirtualSettings *> (settingsVa);
    const float result = pSettings->mCharacterPadding;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtualSettings
 * Method:    getCollisionTolerance
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterVirtualSettings_getCollisionTolerance
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterVirtualSettings * const pSettings
            = reinterpret_cast<CharacterVirtualSettings *> (settingsVa);
    const float result = pSettings->mCollisionTolerance;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtualSettings
 * Method:    getHitReductionCosMaxAngle
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterVirtualSettings_getHitReductionCosMaxAngle
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterVirtualSettings * const pSettings
            = reinterpret_cast<CharacterVirtualSettings *> (settingsVa);
    const float result = pSettings->mHitReductionCosMaxAngle;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtualSettings
 * Method:    getInnerBodyLayer
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_CharacterVirtualSettings_getInnerBodyLayer
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterVirtualSettings * const pSettings
            = reinterpret_cast<CharacterVirtualSettings *> (settingsVa);
    const ObjectLayer result = pSettings->mInnerBodyLayer;
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtualSettings
 * Method:    getInnerBodyShape
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CharacterVirtualSettings_getInnerBodyShape
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterVirtualSettings * const pSettings
            = reinterpret_cast<CharacterVirtualSettings *> (settingsVa);
    const Shape * const pResult = pSettings->mInnerBodyShape.GetPtr();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtualSettings
 * Method:    getMass
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterVirtualSettings_getMass
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterVirtualSettings * const pSettings
            = reinterpret_cast<CharacterVirtualSettings *> (settingsVa);
    const float result = pSettings->mMass;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtualSettings
 * Method:    getMaxCollisionIterations
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_CharacterVirtualSettings_getMaxCollisionIterations
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterVirtualSettings * const pSettings
            = reinterpret_cast<CharacterVirtualSettings *> (settingsVa);
    const uint result = pSettings->mMaxCollisionIterations;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtualSettings
 * Method:    getMaxConstraintIterations
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_CharacterVirtualSettings_getMaxConstraintIterations
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterVirtualSettings * const pSettings
            = reinterpret_cast<CharacterVirtualSettings *> (settingsVa);
    const uint result = pSettings->mMaxConstraintIterations;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtualSettings
 * Method:    getMaxNumHits
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_CharacterVirtualSettings_getMaxNumHits
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterVirtualSettings * const pSettings
            = reinterpret_cast<CharacterVirtualSettings *> (settingsVa);
    const uint result = pSettings->mMaxConstraintIterations;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtualSettings
 * Method:    getMaxStrength
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterVirtualSettings_getMaxStrength
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterVirtualSettings * const pSettings
            = reinterpret_cast<CharacterVirtualSettings *> (settingsVa);
    const float result = pSettings->mMaxStrength;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtualSettings
 * Method:    getMinTimeRemaining
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterVirtualSettings_getMinTimeRemaining
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterVirtualSettings * const pSettings
            = reinterpret_cast<CharacterVirtualSettings *> (settingsVa);
    const float result = pSettings->mMinTimeRemaining;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtualSettings
 * Method:    getPenetrationRecoverySpeed
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterVirtualSettings_getPenetrationRecoverySpeed
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterVirtualSettings * const pSettings
            = reinterpret_cast<CharacterVirtualSettings *> (settingsVa);
    const float result = pSettings->mPenetrationRecoverySpeed;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtualSettings
 * Method:    getPredictiveContactDistance
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterVirtualSettings_getPredictiveContactDistance
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterVirtualSettings * const pSettings
            = reinterpret_cast<CharacterVirtualSettings *> (settingsVa);
    const float result = pSettings->mPredictiveContactDistance;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtualSettings
 * Method:    getRefCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_CharacterVirtualSettings_getRefCount
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterVirtualSettings * const pSettings
            = reinterpret_cast<CharacterVirtualSettings *> (settingsVa);
    const uint32 result = pSettings->GetRefCount();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtualSettings
 * Method:    getShapeOffsetX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterVirtualSettings_getShapeOffsetX
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterVirtualSettings * const pSettings
            = reinterpret_cast<CharacterVirtualSettings *> (settingsVa);
    const float result = pSettings->mShapeOffset.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtualSettings
 * Method:    getShapeOffsetY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterVirtualSettings_getShapeOffsetY
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterVirtualSettings * const pSettings
            = reinterpret_cast<CharacterVirtualSettings *> (settingsVa);
    const float result = pSettings->mShapeOffset.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtualSettings
 * Method:    getShapeOffsetZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterVirtualSettings_getShapeOffsetZ
  (JNIEnv *, jclass, jlong settingsVa) {
    const CharacterVirtualSettings * const pSettings
            = reinterpret_cast<CharacterVirtualSettings *> (settingsVa);
    const float result = pSettings->mShapeOffset.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtualSettings
 * Method:    setBackFaceMode
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterVirtualSettings_setBackFaceMode
  (JNIEnv *, jclass, jlong settingsVa, jint ordinal) {
    CharacterVirtualSettings * const pSettings
            = reinterpret_cast<CharacterVirtualSettings *> (settingsVa);
    const EBackFaceMode mode = (EBackFaceMode) ordinal;
    pSettings->mBackFaceMode = mode;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtualSettings
 * Method:    setCharacterPadding
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterVirtualSettings_setCharacterPadding
  (JNIEnv *, jclass, jlong settingsVa, jfloat padding) {
    CharacterVirtualSettings * const pSettings
            = reinterpret_cast<CharacterVirtualSettings *> (settingsVa);
    pSettings->mCharacterPadding = padding;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtualSettings
 * Method:    setCollisionTolerance
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterVirtualSettings_setCollisionTolerance
  (JNIEnv *, jclass, jlong settingsVa, jfloat tolerance) {
    CharacterVirtualSettings * const pSettings
            = reinterpret_cast<CharacterVirtualSettings *> (settingsVa);
    pSettings->mCollisionTolerance = tolerance;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtualSettings
 * Method:    setEmbedded
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterVirtualSettings_setEmbedded
  (JNIEnv *, jclass, jlong settingsVa) {
    CharacterVirtualSettings * const pSettings
            = reinterpret_cast<CharacterVirtualSettings *> (settingsVa);
    pSettings->SetEmbedded();
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtualSettings
 * Method:    setHitReductionCosMaxAngle
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterVirtualSettings_setHitReductionCosMaxAngle
  (JNIEnv *, jclass, jlong settingsVa, jfloat cosine) {
    CharacterVirtualSettings * const pSettings
            = reinterpret_cast<CharacterVirtualSettings *> (settingsVa);
    pSettings->mHitReductionCosMaxAngle = cosine;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtualSettings
 * Method:    setInnerBodyLayer
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterVirtualSettings_setInnerBodyLayer
  (JNIEnv *, jclass, jlong settingsVa, jint layerId) {
    CharacterVirtualSettings * const pSettings
            = reinterpret_cast<CharacterVirtualSettings *> (settingsVa);
    const ObjectLayer layer = (ObjectLayer) layerId;
    pSettings->mInnerBodyLayer = layer;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtualSettings
 * Method:    setInnerBodyShape
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterVirtualSettings_setInnerBodyShape
  (JNIEnv *, jclass, jlong settingsVa, jlong shapeVa) {
    CharacterVirtualSettings * const pSettings
            = reinterpret_cast<CharacterVirtualSettings *> (settingsVa);
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    pSettings->mInnerBodyShape = pShape;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtualSettings
 * Method:    setMass
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterVirtualSettings_setMass
  (JNIEnv *, jclass, jlong settingsVa, jfloat mass) {
    CharacterVirtualSettings * const pSettings
            = reinterpret_cast<CharacterVirtualSettings *> (settingsVa);
    pSettings->mMass = mass;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtualSettings
 * Method:    setMaxCollisionIterations
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterVirtualSettings_setMaxCollisionIterations
  (JNIEnv *, jclass, jlong settingsVa, jint numIterations) {
    CharacterVirtualSettings * const pSettings
            = reinterpret_cast<CharacterVirtualSettings *> (settingsVa);
    pSettings->mMaxCollisionIterations = numIterations;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtualSettings
 * Method:    setMaxConstraintIterations
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterVirtualSettings_setMaxConstraintIterations
  (JNIEnv *, jclass, jlong settingsVa, jint numIterations) {
    CharacterVirtualSettings * const pSettings
            = reinterpret_cast<CharacterVirtualSettings *> (settingsVa);
    pSettings->mMaxConstraintIterations = numIterations;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtualSettings
 * Method:    setMaxNumHits
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterVirtualSettings_setMaxNumHits
  (JNIEnv *, jclass, jlong settingsVa, jint numHits) {
    CharacterVirtualSettings * const pSettings
            = reinterpret_cast<CharacterVirtualSettings *> (settingsVa);
    pSettings->mMaxNumHits = numHits;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtualSettings
 * Method:    setMaxStrength
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterVirtualSettings_setMaxStrength
  (JNIEnv *, jclass, jlong settingsVa, jfloat force) {
    CharacterVirtualSettings * const pSettings
            = reinterpret_cast<CharacterVirtualSettings *> (settingsVa);
    pSettings->mMaxStrength = force;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtualSettings
 * Method:    setMinTimeRemaining
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterVirtualSettings_setMinTimeRemaining
  (JNIEnv *, jclass, jlong settingsVa, jfloat interval) {
    CharacterVirtualSettings * const pSettings
            = reinterpret_cast<CharacterVirtualSettings *> (settingsVa);
    pSettings->mMinTimeRemaining = interval;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtualSettings
 * Method:    setPenetrationRecoverySpeed
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterVirtualSettings_setPenetrationRecoverySpeed
  (JNIEnv *, jclass, jlong settingsVa, jfloat fraction) {
    CharacterVirtualSettings * const pSettings
            = reinterpret_cast<CharacterVirtualSettings *> (settingsVa);
    pSettings->mPenetrationRecoverySpeed = fraction;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtualSettings
 * Method:    setPredictiveContactDistance
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterVirtualSettings_setPredictiveContactDistance
  (JNIEnv *, jclass, jlong settingsVa, jfloat distance) {
    CharacterVirtualSettings * const pSettings
            = reinterpret_cast<CharacterVirtualSettings *> (settingsVa);
    pSettings->mPredictiveContactDistance = distance;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtualSettings
 * Method:    setShapeOffset
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterVirtualSettings_setShapeOffset
  (JNIEnv *, jclass, jlong settingsVa, jfloat dx, jfloat dy, jfloat dz) {
    CharacterVirtualSettings * const pSettings
            = reinterpret_cast<CharacterVirtualSettings *> (settingsVa);
    const Vec3 offset(dx, dy, dz);
    pSettings->mShapeOffset = offset;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtualSettings
 * Method:    toRef
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CharacterVirtualSettings_toRef
  (JNIEnv *, jclass, jlong settingsVa) {
    CharacterVirtualSettings * const pSettings
            = reinterpret_cast<CharacterVirtualSettings *> (settingsVa);
    Ref<CharacterVirtualSettings> * const pResult
            = new Ref<CharacterVirtualSettings>(pSettings);
    TRACE_NEW("Ref<CharacterVirtualSettings>", pResult)
    return reinterpret_cast<jlong> (pResult);
}