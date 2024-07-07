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
#include <Jolt/Physics/Body/BodyCreationSettings.h>
#include "auto/com_github_stephengold_joltjni_BodyCreationSettings.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    createBodyCreationSettings
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_createBodyCreationSettings
  (JNIEnv *, jclass) {
    BodyCreationSettings * const pResult = new BodyCreationSettings();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    createBodyCreationSettingsFromShape
 * Signature: (JDDDFFFFII)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_createBodyCreationSettingsFromShape
  (JNIEnv *, jclass, jlong shapeVa, jdouble locX, jdouble locY, jdouble locZ,
   jfloat qx, jfloat qy, jfloat qz, jfloat qw, jint motionTypeOrdinal, jint objLayer) {
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    const RVec3 loc(locX, locY, locZ);
    const Quat orient(qx, qy, qz, qw);
    const EMotionType motionType = (EMotionType) motionTypeOrdinal;
    BodyCreationSettings *pResult = new BodyCreationSettings(
            pShape, loc, orient, motionType, objLayer);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    createBodyCreationSettingsFromShapeSettings
 * Signature: (JDDDFFFFII)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_createBodyCreationSettingsFromShapeSettings
  (JNIEnv *, jclass, jlong shapeSettingsVa, jdouble locX, jdouble locY, jdouble locZ,
   jfloat qx, jfloat qy, jfloat qz, jfloat qw, jint motionTypeOrdinal, jint objLayer) {
    const ShapeSettings * const pShapeSettings
            = reinterpret_cast<ShapeSettings *> (shapeSettingsVa);
    const RVec3 loc(locX, locY, locZ);
    const Quat orient(qx, qy, qz, qw);
    const EMotionType motionType = (EMotionType) motionTypeOrdinal;
    BodyCreationSettings *pResult = new BodyCreationSettings(
            pShapeSettings, loc, orient, motionType, objLayer);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_free
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (bodySettingsVa);
    delete pSettings;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    getAngularDamping
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_getAngularDamping
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (bodySettingsVa);
    float result = pSettings->mAngularDamping;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    getAngularVelocityX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_getAngularVelocityX
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (bodySettingsVa);
    float result = pSettings->mAngularVelocity.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    getAngularVelocityY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_getAngularVelocityY
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (bodySettingsVa);
    float result = pSettings->mAngularVelocity.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    getAngularVelocityZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_getAngularVelocityZ
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (bodySettingsVa);
    float result = pSettings->mAngularVelocity.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    getFriction
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_getFriction
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (bodySettingsVa);
    float result = pSettings->mFriction;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    getGravityFactor
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_getGravityFactor
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (bodySettingsVa);
    float result = pSettings->mGravityFactor;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    getLinearDamping
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_getLinearDamping
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (bodySettingsVa);
    float result = pSettings->mLinearDamping;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    getLinearVelocityX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_getLinearVelocityX
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (bodySettingsVa);
    float result = pSettings->mLinearVelocity.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    getLinearVelocityY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_getLinearVelocityY
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (bodySettingsVa);
    float result = pSettings->mLinearVelocity.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    getLinearVelocityZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_getLinearVelocityZ
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (bodySettingsVa);
    float result = pSettings->mLinearVelocity.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    getMassProperties
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_getMassProperties
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (bodySettingsVa);
    MassProperties * const pResult = new MassProperties();
    *pResult = pSettings->GetMassProperties();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    getMotionQuality
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_getMotionQuality
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (bodySettingsVa);
    EMotionQuality result = pSettings->mMotionQuality;
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    getMotionType
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_getMotionType
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (bodySettingsVa);
    EMotionType result = pSettings->mMotionType;
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    getObjectLayer
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_getObjectLayer
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (bodySettingsVa);
    int result = pSettings->mObjectLayer;
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    getPositionX
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_getPositionX
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (bodySettingsVa);
    double result = pSettings->mPosition.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    getPositionY
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_getPositionY
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (bodySettingsVa);
    double result = pSettings->mPosition.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    getPositionZ
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_getPositionZ
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (bodySettingsVa);
    double result = pSettings->mPosition.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    getRestitution
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_getRestitution
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (bodySettingsVa);
    float result = pSettings->mRestitution;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    getRotationW
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_getRotationW
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (bodySettingsVa);
    double result = pSettings->mRotation.GetW();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    getRotationX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_getRotationX
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (bodySettingsVa);
    double result = pSettings->mRotation.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    getRotationY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_getRotationY
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (bodySettingsVa);
    double result = pSettings->mRotation.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    getRotationZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_getRotationZ
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (bodySettingsVa);
    double result = pSettings->mRotation.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    getShape
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_getShape
  (JNIEnv *, jclass, jlong bodySettingsVa) {
    const BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (bodySettingsVa);
    const Shape * const pShape = pSettings->GetShape();
    return reinterpret_cast<jlong> (pShape);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    setAngularDamping
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_setAngularDamping
  (JNIEnv *, jclass, jlong bodySettingsVa, jfloat damping) {
    BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (bodySettingsVa);
    pSettings->mAngularDamping = damping;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    setAngularVelocity
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_setAngularVelocity
  (JNIEnv *, jclass, jlong bodySettingsVa, jfloat wx, jfloat wy, jfloat wz) {
    BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (bodySettingsVa);
    pSettings->mAngularVelocity.Set(wx, wy, wz);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    setFriction
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_setFriction
  (JNIEnv *, jclass, jlong bodySettingsVa, jfloat friction) {
    BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (bodySettingsVa);
    pSettings->mFriction = friction;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    setGravityFactor
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_setGravityFactor
  (JNIEnv *, jclass, jlong bodySettingsVa, jfloat factor) {
    BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (bodySettingsVa);
    pSettings->mGravityFactor = factor;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    setLinearDamping
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_setLinearDamping
  (JNIEnv *, jclass, jlong bodySettingsVa, jfloat damping) {
    BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (bodySettingsVa);
    pSettings->mLinearDamping = damping;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    setLinearVelocity
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_setLinearVelocity
  (JNIEnv *, jclass, jlong bodySettingsVa, jfloat vx, jfloat vy, jfloat vz) {
    BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (bodySettingsVa);
    pSettings->mLinearVelocity.Set(vx, vy, vz);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    setMotionQuality
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_setMotionQuality
  (JNIEnv *, jclass, jlong bodySettingsVa, jint motionQualityOrdinal) {
    BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (bodySettingsVa);
    const EMotionQuality motionQuality = (EMotionQuality) motionQualityOrdinal;
    pSettings->mMotionQuality = motionQuality;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    setMotionType
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_setMotionType
  (JNIEnv *, jclass, jlong bodySettingsVa, jint motionTypeOrdinal) {
    BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (bodySettingsVa);
    const EMotionType motionType = (EMotionType) motionTypeOrdinal;
    pSettings->mMotionType = motionType;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    setObjectLayer
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_setObjectLayer
  (JNIEnv *, jclass, jlong bodySettingsVa, jint objLayer) {
    BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (bodySettingsVa);
    pSettings->mObjectLayer = objLayer;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    setPosition
 * Signature: (JDDD)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_setPosition
  (JNIEnv *, jclass, jlong bodySettingsVa, jdouble locX, jdouble locY, jdouble locZ) {
    BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (bodySettingsVa);
    RVec3 rvec3(locX, locY, locZ);
    pSettings->mPosition = rvec3;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    setRestitution
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_setRestitution
  (JNIEnv *, jclass, jlong bodySettingsVa, jfloat restitution) {
    BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (bodySettingsVa);
    pSettings->mRestitution = restitution;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    setRotation
 * Signature: (JFFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_setRotation
  (JNIEnv *, jclass, jlong bodySettingsVa, jfloat qx, jfloat qy, jfloat qz, jfloat qw) {
    BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (bodySettingsVa);
    pSettings->mRotation.Set(qx, qy, qz, qw);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    setShape
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_setShape
  (JNIEnv *, jclass, jlong bodySettingsVa, jlong shapeVa) {
    BodyCreationSettings * const pBodySettings
            = reinterpret_cast<BodyCreationSettings *> (bodySettingsVa);
    Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    pBodySettings->SetShape(pShape);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyCreationSettings
 * Method:    setShapeSettings
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyCreationSettings_setShapeSettings
  (JNIEnv *, jclass, jlong bodySettingsVa, jlong shapeSettingsVa) {
    BodyCreationSettings * const pBodySettings
            = reinterpret_cast<BodyCreationSettings *> (bodySettingsVa);
    ShapeSettings * const pShapeSettings
            = reinterpret_cast<ShapeSettings *> (shapeSettingsVa);
    pBodySettings->SetShapeSettings(pShapeSettings);
}
