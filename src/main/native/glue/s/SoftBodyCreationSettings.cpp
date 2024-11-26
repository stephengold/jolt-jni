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
#include "Jolt/Jolt.h"
#include "Jolt/Physics/SoftBody/SoftBodyCreationSettings.h"
#include "auto/com_github_stephengold_joltjni_SoftBodyCreationSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyCreationSettings
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SoftBodyCreationSettings_createDefault
  (JNIEnv *, jclass) {
    SoftBodyCreationSettings * const pResult = new SoftBodyCreationSettings();
    TRACE_NEW("SoftBodyCreationSettings", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyCreationSettings
 * Method:    createFromSharedSettings
 * Signature: (JDDDFFFFI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SoftBodyCreationSettings_createFromSharedSettings
  (JNIEnv *, jclass, jlong sharedSettingsVa, jdouble xx, jdouble yy, jdouble zz,
  jfloat qx, jfloat qy, jfloat qz, jfloat qw, jint objectLayer) {
    const SoftBodySharedSettings * const pSharedSettings
            = reinterpret_cast<SoftBodySharedSettings *> (sharedSettingsVa);
    const RVec3 location(xx, yy, zz);
    const Quat orientation(qx, qy, qz, qw);
    SoftBodyCreationSettings * const pResult = new SoftBodyCreationSettings(
            pSharedSettings, location, orientation, objectLayer);
    TRACE_NEW("SoftBodyCreationSettings", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyCreationSettings
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodyCreationSettings_free
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    SoftBodyCreationSettings * const pSettings
            = reinterpret_cast<SoftBodyCreationSettings *> (bodySettingsVa);
    TRACE_DELETE("SoftBodyCreationSettings", pSettings)
    delete pSettings;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyCreationSettings
 * Method:    getAllowSleeping
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_SoftBodyCreationSettings_getAllowSleeping
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const SoftBodyCreationSettings * const pSettings
            = reinterpret_cast<SoftBodyCreationSettings *> (bodySettingsVa);
    const bool result = pSettings->mAllowSleeping;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyCreationSettings
 * Method:    getFriction
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SoftBodyCreationSettings_getFriction
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const SoftBodyCreationSettings * const pSettings
            = reinterpret_cast<SoftBodyCreationSettings *> (bodySettingsVa);
    const float result = pSettings->mFriction;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyCreationSettings
 * Method:    getGravityFactor
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SoftBodyCreationSettings_getGravityFactor
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const SoftBodyCreationSettings * const pSettings
            = reinterpret_cast<SoftBodyCreationSettings *> (bodySettingsVa);
    const float result = pSettings->mGravityFactor;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyCreationSettings
 * Method:    getLinearDamping
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SoftBodyCreationSettings_getLinearDamping
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const SoftBodyCreationSettings * const pSettings
            = reinterpret_cast<SoftBodyCreationSettings *> (bodySettingsVa);
    const float result = pSettings->mLinearDamping;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyCreationSettings
 * Method:    getMaxLinearVelocity
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SoftBodyCreationSettings_getMaxLinearVelocity
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const SoftBodyCreationSettings * const pSettings
            = reinterpret_cast<SoftBodyCreationSettings *> (bodySettingsVa);
    const float result = pSettings->mMaxLinearVelocity;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyCreationSettings
 * Method:    getObjectLayer
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SoftBodyCreationSettings_getObjectLayer
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const SoftBodyCreationSettings * const pSettings
            = reinterpret_cast<SoftBodyCreationSettings *> (bodySettingsVa);
    const ObjectLayer result = pSettings->mObjectLayer;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyCreationSettings
 * Method:    getPositionX
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_SoftBodyCreationSettings_getPositionX
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const SoftBodyCreationSettings * const pSettings
            = reinterpret_cast<SoftBodyCreationSettings *> (bodySettingsVa);
    const float result = pSettings->mPosition.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyCreationSettings
 * Method:    getPositionY
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_SoftBodyCreationSettings_getPositionY
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const SoftBodyCreationSettings * const pSettings
            = reinterpret_cast<SoftBodyCreationSettings *> (bodySettingsVa);
    const float result = pSettings->mPosition.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyCreationSettings
 * Method:    getPositionZ
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_SoftBodyCreationSettings_getPositionZ
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const SoftBodyCreationSettings * const pSettings
            = reinterpret_cast<SoftBodyCreationSettings *> (bodySettingsVa);
    const float result = pSettings->mPosition.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyCreationSettings
 * Method:    getPressure
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SoftBodyCreationSettings_getPressure
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const SoftBodyCreationSettings * const pSettings
            = reinterpret_cast<SoftBodyCreationSettings *> (bodySettingsVa);
    const float result = pSettings->mPressure;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyCreationSettings
 * Method:    getRestitution
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SoftBodyCreationSettings_getRestitution
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const SoftBodyCreationSettings * const pSettings
            = reinterpret_cast<SoftBodyCreationSettings *> (bodySettingsVa);
    const float result = pSettings->mRestitution;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyCreationSettings
 * Method:    getRotationW
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SoftBodyCreationSettings_getRotationW
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const SoftBodyCreationSettings * const pSettings
            = reinterpret_cast<SoftBodyCreationSettings *> (bodySettingsVa);
    const float result = pSettings->mRotation.GetW();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyCreationSettings
 * Method:    getRotationX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SoftBodyCreationSettings_getRotationX
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const SoftBodyCreationSettings * const pSettings
            = reinterpret_cast<SoftBodyCreationSettings *> (bodySettingsVa);
    const float result = pSettings->mRotation.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyCreationSettings
 * Method:    getRotationY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SoftBodyCreationSettings_getRotationY
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const SoftBodyCreationSettings * const pSettings
            = reinterpret_cast<SoftBodyCreationSettings *> (bodySettingsVa);
    const float result = pSettings->mRotation.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyCreationSettings
 * Method:    getRotationZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SoftBodyCreationSettings_getRotationZ
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const SoftBodyCreationSettings * const pSettings
            = reinterpret_cast<SoftBodyCreationSettings *> (bodySettingsVa);
    const float result = pSettings->mRotation.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyCreationSettings
 * Method:    getSettings
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SoftBodyCreationSettings_getSettings
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const SoftBodyCreationSettings * const pSettings
            = reinterpret_cast<SoftBodyCreationSettings *> (bodySettingsVa);
    const SoftBodySharedSettings * const pResult
            = pSettings->mSettings.GetPtr();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyCreationSettings
 * Method:    setAllowSleeping
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodyCreationSettings_setAllowSleeping
  (JNIEnv *, jclass, jlong bodySettingsVa, jboolean allow) {
    SoftBodyCreationSettings * const pSettings
            = reinterpret_cast<SoftBodyCreationSettings *> (bodySettingsVa);
    pSettings->mAllowSleeping = allow;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyCreationSettings
 * Method:    setCollisionGroup
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodyCreationSettings_setCollisionGroup
  (JNIEnv *, jclass, jlong bodySettingsVa, jlong groupVa) {
    SoftBodyCreationSettings * const pSettings
            = reinterpret_cast<SoftBodyCreationSettings *> (bodySettingsVa);
    const CollisionGroup * const pGroup
            = reinterpret_cast<CollisionGroup *> (groupVa);
    pSettings->mCollisionGroup = *pGroup;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyCreationSettings
 * Method:    setFriction
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodyCreationSettings_setFriction
  (JNIEnv *, jclass, jlong bodySettingsVa, jfloat friction) {
    SoftBodyCreationSettings * const pSettings
            = reinterpret_cast<SoftBodyCreationSettings *> (bodySettingsVa);
    pSettings->mFriction = friction;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyCreationSettings
 * Method:    setGravityFactor
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodyCreationSettings_setGravityFactor
  (JNIEnv *, jclass, jlong bodySettingsVa, jfloat factor) {
    SoftBodyCreationSettings * const pSettings
            = reinterpret_cast<SoftBodyCreationSettings *> (bodySettingsVa);
    pSettings->mGravityFactor = factor;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyCreationSettings
 * Method:    setLinearDamping
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodyCreationSettings_setLinearDamping
  (JNIEnv *, jclass, jlong bodySettingsVa, jfloat damping) {
    SoftBodyCreationSettings * const pSettings
            = reinterpret_cast<SoftBodyCreationSettings *> (bodySettingsVa);
    pSettings->mLinearDamping = damping;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyCreationSettings
 * Method:    setMaxLinearVelocity
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodyCreationSettings_setMaxLinearVelocity
  (JNIEnv *, jclass, jlong bodySettingsVa, jfloat maxSpeed) {
    SoftBodyCreationSettings * const pSettings
            = reinterpret_cast<SoftBodyCreationSettings *> (bodySettingsVa);
    pSettings->mMaxLinearVelocity = maxSpeed;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyCreationSettings
 * Method:    setNumIterations
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodyCreationSettings_setNumIterations
  (JNIEnv *, jclass, jlong bodySettingsVa, jint numIterations) {
    SoftBodyCreationSettings * const pSettings
            = reinterpret_cast<SoftBodyCreationSettings *> (bodySettingsVa);
    pSettings->mNumIterations = numIterations;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyCreationSettings
 * Method:    setObjectLayer
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodyCreationSettings_setObjectLayer
  (JNIEnv *, jclass, jlong bodySettingsVa, jint objLayer) {
    SoftBodyCreationSettings * const pSettings
            = reinterpret_cast<SoftBodyCreationSettings *> (bodySettingsVa);
    pSettings->mObjectLayer = (ObjectLayer) objLayer;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyCreationSettings
 * Method:    setPosition
 * Signature: (JDDD)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodyCreationSettings_setPosition
  (JNIEnv *, jclass, jlong bodySettingsVa, jdouble xx, jdouble yy, jdouble zz) {
    SoftBodyCreationSettings * const pSettings
            = reinterpret_cast<SoftBodyCreationSettings *> (bodySettingsVa);
    const RVec3 location(xx, yy, zz);
    pSettings->mPosition = location;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyCreationSettings
 * Method:    setPressure
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodyCreationSettings_setPressure
  (JNIEnv *, jclass, jlong bodySettingsVa, jfloat pressure) {
    SoftBodyCreationSettings * const pSettings
            = reinterpret_cast<SoftBodyCreationSettings *> (bodySettingsVa);
    pSettings->mPressure = pressure;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyCreationSettings
 * Method:    setRestitution
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodyCreationSettings_setRestitution
  (JNIEnv *, jclass, jlong bodySettingsVa, jfloat restitution) {
    SoftBodyCreationSettings * const pSettings
            = reinterpret_cast<SoftBodyCreationSettings *> (bodySettingsVa);
    pSettings->mRestitution = restitution;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyCreationSettings
 * Method:    setRotation
 * Signature: (JFFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodyCreationSettings_setRotation
  (JNIEnv *, jclass, jlong bodySettingsVa, jfloat qx, jfloat qy, jfloat qz, jfloat qw) {
    SoftBodyCreationSettings * const pSettings
            = reinterpret_cast<SoftBodyCreationSettings *> (bodySettingsVa);
    const Quat orientation(qx, qy, qz, qw);
    pSettings->mRotation = orientation;
}