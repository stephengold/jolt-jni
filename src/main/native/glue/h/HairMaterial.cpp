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
#include "Jolt/Physics/Hair/HairSettings.h"
#include "auto/com_github_stephengold_joltjni_HairMaterial.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_HairMaterial_createCopy
  BODYOF_CREATE_COPY(HairSettings::Material)

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_HairMaterial_createDefault
  BODYOF_CREATE_DEFAULT(HairSettings::Material)

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairMaterial_free
  BODYOF_FREE(HairSettings::Material)

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    getAngularDamping
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HairMaterial_getAngularDamping
  (JNIEnv *, jclass, jlong materialVa) {
    const HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    const float result = pMaterial->mAngularDamping;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    getBendCompliance
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HairMaterial_getBendCompliance
  (JNIEnv *, jclass, jlong materialVa) {
    const HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    const float result = pMaterial->mBendCompliance;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    getBendComplianceMultiplier
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairMaterial_getBendComplianceMultiplier
  (JNIEnv *pEnv, jclass, jlong materialVa, jobject storeFloats) {
    const HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 4);
    const Float4& f4 = pMaterial->mBendComplianceMultiplier;
    pFloats[0] = f4.x;
    pFloats[1] = f4.y;
    pFloats[2] = f4.z;
    pFloats[3] = f4.w;
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    getEnableCollision
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_HairMaterial_getEnableCollision
  (JNIEnv *, jclass, jlong materialVa) {
    const HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    const bool result = pMaterial->mEnableCollision;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    getEnableLra
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_HairMaterial_getEnableLra
  (JNIEnv *, jclass, jlong materialVa) {
    const HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    const bool result = pMaterial->mEnableLRA;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    getFriction
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HairMaterial_getFriction
  (JNIEnv *, jclass, jlong materialVa) {
    const HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    const float result = pMaterial->mFriction;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    getGlobalPose
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_HairMaterial_getGlobalPose
  (JNIEnv *, jclass, jlong materialVa) {
    const HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    const HairSettings::Gradient * const pResult = &pMaterial->mGlobalPose;
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    getGravityFactor
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_HairMaterial_getGravityFactor
  (JNIEnv *, jclass, jlong materialVa) {
    const HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    const HairSettings::Gradient * const pResult = &pMaterial->mGravityFactor;
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    getGravityPreloadFactor
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HairMaterial_getGravityPreloadFactor
  (JNIEnv *, jclass, jlong materialVa) {
    const HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    const float result = pMaterial->mGravityPreloadFactor;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    getGridDensityForceFactor
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HairMaterial_getGridDensityForceFactor
  (JNIEnv *, jclass, jlong materialVa) {
    const HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    const float result = pMaterial->mGridDensityForceFactor;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    getGridVelocityFactor
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_HairMaterial_getGridVelocityFactor
  (JNIEnv *, jclass, jlong materialVa) {
    const HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    const HairSettings::Gradient * const pResult
            = &pMaterial->mGridVelocityFactor;
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    getHairRadius
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_HairMaterial_getHairRadius
  (JNIEnv *, jclass, jlong materialVa) {
    const HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    const HairSettings::Gradient * const pResult  = &pMaterial->mHairRadius;
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    getInertiaMultiplier
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HairMaterial_getInertiaMultiplier
  (JNIEnv *, jclass, jlong materialVa) {
    const HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    const float result = pMaterial->mInertiaMultiplier;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    getLinearDamping
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HairMaterial_getLinearDamping
  (JNIEnv *, jclass, jlong materialVa) {
    const HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    const float result = pMaterial->mLinearDamping;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    getMaxAngularVelocity
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HairMaterial_getMaxAngularVelocity
  (JNIEnv *, jclass, jlong materialVa) {
    const HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    const float result = pMaterial->mMaxAngularVelocity;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    getMaxLinearVelocity
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HairMaterial_getMaxLinearVelocity
  (JNIEnv *, jclass, jlong materialVa) {
    const HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    const float result = pMaterial->mMaxLinearVelocity;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    getSimulationStrandsFraction
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HairMaterial_getSimulationStrandsFraction
  (JNIEnv *, jclass, jlong materialVa) {
    const HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    const float result = pMaterial->mSimulationStrandsFraction;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    getSkinGlobalPose
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_HairMaterial_getSkinGlobalPose
  (JNIEnv *, jclass, jlong materialVa) {
    const HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    const HairSettings::Gradient * const pResult = &pMaterial->mSkinGlobalPose;
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    getStretchCompliance
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HairMaterial_getStretchCompliance
  (JNIEnv *, jclass, jlong materialVa) {
    const HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    const float result = pMaterial->mStretchCompliance;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    getWorldTransformInfluence
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_HairMaterial_getWorldTransformInfluence
  (JNIEnv *, jclass, jlong materialVa) {
    const HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    const HairSettings::Gradient * const pResult
            = &pMaterial->mWorldTransformInfluence;
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    setAngularDamping
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairMaterial_setAngularDamping
  (JNIEnv *, jclass, jlong materialVa, jfloat damping) {
    HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    pMaterial->mAngularDamping = damping;
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    setBendCompliance
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairMaterial_setBendCompliance
  (JNIEnv *, jclass, jlong materialVa, jfloat compliance) {
    HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    pMaterial->mBendCompliance = compliance;
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    setBendComplianceMultiplier
 * Signature: (JFFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairMaterial_setBendComplianceMultiplier
  (JNIEnv *, jclass, jlong materialVa, jfloat x, jfloat y, jfloat z, jfloat w) {
    HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    const Float4 f4(x, y, z, w);
    pMaterial->mBendComplianceMultiplier = f4;
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    setEnableCollision
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairMaterial_setEnableCollision
  (JNIEnv *, jclass, jlong materialVa, jboolean setting) {
    HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    pMaterial->mEnableCollision = setting;
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    setEnableLra
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairMaterial_setEnableLra
  (JNIEnv *, jclass, jlong materialVa, jboolean setting) {
    HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    pMaterial->mEnableLRA = setting;
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    setFriction
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairMaterial_setFriction
  (JNIEnv *, jclass, jlong materialVa, jfloat friction) {
    HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    pMaterial->mFriction = friction;
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    setGlobalPose
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairMaterial_setGlobalPose
  (JNIEnv *, jclass, jlong materialVa, jlong gradientVa) {
    HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    const HairSettings::Gradient * const pGradient
            = reinterpret_cast<HairSettings::Gradient *> (gradientVa);
    pMaterial->mGlobalPose = *pGradient;
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    setGravityFactor
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairMaterial_setGravityFactor
  (JNIEnv *, jclass, jlong materialVa, jlong gradientVa) {
    HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    const HairSettings::Gradient * const pGradient
            = reinterpret_cast<HairSettings::Gradient *> (gradientVa);
    pMaterial->mGravityFactor = *pGradient;
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    setGravityPreloadFactor
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairMaterial_setGravityPreloadFactor
  (JNIEnv *, jclass, jlong materialVa, jfloat factor) {
    HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    pMaterial->mGravityPreloadFactor = factor;
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    setGridDensityForceFactor
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairMaterial_setGridDensityForceFactor
  (JNIEnv *, jclass, jlong materialVa, jfloat factor) {
    HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    pMaterial->mGridDensityForceFactor = factor;
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    setGridVelocityFactor
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairMaterial_setGridVelocityFactor
  (JNIEnv *, jclass, jlong materialVa, jlong gradientVa) {
    HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    const HairSettings::Gradient * const pGradient
            = reinterpret_cast<HairSettings::Gradient *> (gradientVa);
    pMaterial->mGridVelocityFactor = *pGradient;
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    setHairRadius
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairMaterial_setHairRadius
  (JNIEnv *, jclass, jlong materialVa, jlong gradientVa) {
    HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    const HairSettings::Gradient * const pGradient
            = reinterpret_cast<HairSettings::Gradient *> (gradientVa);
    pMaterial->mHairRadius = *pGradient;
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    setInertiaMultiplier
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairMaterial_setInertiaMultiplier
  (JNIEnv *, jclass, jlong materialVa, jfloat multiplier) {
    HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    pMaterial->mInertiaMultiplier = multiplier;
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    setLinearDamping
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairMaterial_setLinearDamping
  (JNIEnv *, jclass, jlong materialVa, jfloat damping) {
    HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    pMaterial->mLinearDamping = damping;
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    setMaxAngularVelocity
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairMaterial_setMaxAngularVelocity
  (JNIEnv *, jclass, jlong materialVa, jfloat angularRate) {
    HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    pMaterial->mMaxAngularVelocity = angularRate;
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    setMaxLinearVelocity
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairMaterial_setMaxLinearVelocity
  (JNIEnv *, jclass, jlong materialVa, jfloat speed) {
    HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    pMaterial->mMaxLinearVelocity = speed;
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    setSimulationStrandsFraction
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairMaterial_setSimulationStrandsFraction
  (JNIEnv *, jclass, jlong materialVa, jfloat fraction) {
    HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    pMaterial->mSimulationStrandsFraction = fraction;
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    setSkinGlobalPose
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairMaterial_setSkinGlobalPose
  (JNIEnv *, jclass, jlong materialVa, jlong gradientVa) {
    HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    const HairSettings::Gradient * const pGradient
            = reinterpret_cast<HairSettings::Gradient *> (gradientVa);
    pMaterial->mSkinGlobalPose = *pGradient;
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    setStretchCompliance
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairMaterial_setStretchCompliance
  (JNIEnv *, jclass, jlong materialVa, jfloat compliance) {
    HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    pMaterial->mStretchCompliance = compliance;
}

/*
 * Class:     com_github_stephengold_joltjni_HairMaterial
 * Method:    setWorldTransformInfluence
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_HairMaterial_setWorldTransformInfluence
  (JNIEnv *, jclass, jlong materialVa, jlong gradientVa) {
    HairSettings::Material * const pMaterial
            = reinterpret_cast<HairSettings::Material *> (materialVa);
    const HairSettings::Gradient * const pGradient
            = reinterpret_cast<HairSettings::Gradient *> (gradientVa);
    pMaterial->mWorldTransformInfluence = *pGradient;
}