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
#include <Jolt/Physics/Character/CharacterVirtual.h>
#include "auto/com_github_stephengold_joltjni_CharacterVirtual.h"
#include "auto/com_github_stephengold_joltjni_CharacterVirtualRef.h"
#include "glue/glue.h"

using namespace JPH;

IMPLEMENT_REF(CharacterVirtual,
  Java_com_github_stephengold_joltjni_CharacterVirtualRef_copy,
  Java_com_github_stephengold_joltjni_CharacterVirtualRef_free,
  Java_com_github_stephengold_joltjni_CharacterVirtualRef_getPtr)

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    cancelVelocityTowardsSteepSlopes
 * Signature: (JFFF[F)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_cancelVelocityTowardsSteepSlopes
  (JNIEnv *pEnv, jclass, jlong characterVa, jfloat vx, jfloat vy, jfloat vz, jfloatArray storeVelocity) {
    const CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    const Vec3 desiredV(vx, vy, vz);
    const Vec3 outV = pCharacter->CancelVelocityTowardsSteepSlopes(desiredV);
    jboolean isCopy;
    jfloat * const pStoreVelocity
            = pEnv->GetFloatArrayElements(storeVelocity, &isCopy);
    pStoreVelocity[0] = outV.GetX();
    pStoreVelocity[1] = outV.GetY();
    pStoreVelocity[2] = outV.GetZ();
    pEnv->ReleaseFloatArrayElements(storeVelocity, pStoreVelocity, 0);
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    canWalkStairs
 * Signature: (JFFF)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_canWalkStairs
  (JNIEnv *, jclass, jlong characterVa, jfloat vx, jfloat vy, jfloat vz) {
    const CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    const Vec3 desiredV(vx, vy, vz);
    const bool result = pCharacter->CanWalkStairs(desiredV);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    createCharacterVirtual
 * Signature: (JDDDFFFFJJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_createCharacterVirtual
  (JNIEnv *, jclass, jlong settingsVa, jdouble locX, jdouble locY, jdouble locZ,
  jfloat qx, jfloat qy, jfloat qz, jfloat qw, jlong userData, jlong systemVa) {
    const CharacterVirtualSettings * const pSettings
            = reinterpret_cast<CharacterVirtualSettings *> (settingsVa);
    const RVec3 location(locX, locY, locZ);
    const Quat orientation(qx, qy, qz, qw);
    PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    CharacterVirtual * const pResult = new CharacterVirtual(
            pSettings, location, orientation, userData, pSystem);
    TRACE_NEW("CharacterVirtual", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    getActiveContacts
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_getActiveContacts
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    const CharacterVirtual::ContactList * const pResult
            = &pCharacter->GetActiveContacts();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    getCenterOfMassPositionX
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_getCenterOfMassPositionX
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    const Real result = pCharacter->GetCenterOfMassPosition().GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    getCenterOfMassPositionY
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_getCenterOfMassPositionY
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    const Real result = pCharacter->GetCenterOfMassPosition().GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    getCenterOfMassPositionZ
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_getCenterOfMassPositionZ
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    const Real result = pCharacter->GetCenterOfMassPosition().GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    getCenterOfMassTransform
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_getCenterOfMassTransform
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    RMat44 * const pResult = new RMat44();
    TRACE_NEW("RMat44", pResult)
    *pResult = pCharacter->GetCenterOfMassTransform();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    getCharacterPadding
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_getCharacterPadding
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    const float result = pCharacter->GetCharacterPadding();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    getEnhancedInternalEdgeRemoval
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_getEnhancedInternalEdgeRemoval
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    const bool result = pCharacter->GetEnhancedInternalEdgeRemoval();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    getHitReductionCosMaxAngle
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_getHitReductionCosMaxAngle
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    const bool result = pCharacter->GetHitReductionCosMaxAngle();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    getInnerBodyId
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_getInnerBodyId
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    const BodyID id = pCharacter->GetInnerBodyID();
    BodyID * const pResult = new BodyID(id);
    TRACE_NEW("BodyID", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    getLinearVelocityX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_getLinearVelocityX
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    const float result = pCharacter->GetLinearVelocity().GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    getLinearVelocityY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_getLinearVelocityY
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    const float result = pCharacter->GetLinearVelocity().GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    getLinearVelocityZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_getLinearVelocityZ
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    const float result = pCharacter->GetLinearVelocity().GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    getMass
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_getMass
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    const float result = pCharacter->GetMass();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    getMaxHitsExceeded
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_getMaxHitsExceeded
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    const bool result = pCharacter->GetMaxHitsExceeded();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    getMaxNumHits
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_getMaxNumHits
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    const uint result = pCharacter->GetMaxNumHits();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    getMaxStrength
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_getMaxStrength
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    const float result = pCharacter->GetMaxStrength();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    getPenetrationRecoverySpeed
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_getPenetrationRecoverySpeed
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    const float result = pCharacter->GetPenetrationRecoverySpeed();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    getPositionX
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_getPositionX
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    const Real result = pCharacter->GetPosition().GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    getPositionY
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_getPositionY
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    const Real result = pCharacter->GetPosition().GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    getPositionZ
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_getPositionZ
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    const Real result = pCharacter->GetPosition().GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    getRefCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_getRefCount
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    const uint32 result = pCharacter->GetRefCount();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    getRotationW
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_getRotationW
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    const float result = pCharacter->GetRotation().GetW();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    getRotationX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_getRotationX
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    const float result = pCharacter->GetRotation().GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    getRotationY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_getRotationY
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    const float result = pCharacter->GetRotation().GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    getRotationZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_getRotationZ
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    const float result = pCharacter->GetRotation().GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    getShapeOffsetX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_getShapeOffsetX
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    const float result = pCharacter->GetShapeOffset().GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    getShapeOffsetY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_getShapeOffsetY
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    const float result = pCharacter->GetShapeOffset().GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    getShapeOffsetZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_getShapeOffsetZ
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    const float result = pCharacter->GetShapeOffset().GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    getUserData
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_getUserData
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    const uint64 result = pCharacter->GetUserData();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    getWorldTransform
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_getWorldTransform
  (JNIEnv *, jclass, jlong characterVa) {
    const CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    RMat44 * const pResult = new RMat44();
    TRACE_NEW("RMat44", pResult)
    *pResult = pCharacter->GetWorldTransform();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    hasCollidedWithBody
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_hasCollidedWithBody
  (JNIEnv *, jclass, jlong characterVa, jlong idVa) {
    const CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    const BodyID * const pBody = reinterpret_cast<BodyID *> (idVa);
    const bool result = pCharacter->HasCollidedWith(*pBody);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    hasCollidedWithCharacter
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_hasCollidedWithCharacter
  (JNIEnv *, jclass, jlong characterVa, jlong otherVa) {
    const CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    const CharacterVirtual * const pOther
            = reinterpret_cast<CharacterVirtual *> (otherVa);
    const bool result = pCharacter->HasCollidedWith(pOther);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    setEnhancedInternalEdgeRemoval
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_setEnhancedInternalEdgeRemoval
  (JNIEnv *, jclass, jlong characterVa, jboolean enable) {
    CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    pCharacter->SetEnhancedInternalEdgeRemoval(enable);
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    setHitReductionCosMaxAngle
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_setHitReductionCosMaxAngle
  (JNIEnv *, jclass, jlong characterVa, jfloat cosine) {
    CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    pCharacter->SetHitReductionCosMaxAngle(cosine);
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    setInnerBodyShape
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_setInnerBodyShape
  (JNIEnv *, jclass, jlong characterVa, jlong shapeVa) {
    CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    pCharacter->SetInnerBodyShape(pShape);
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    setLinearVelocity
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_setLinearVelocity
  (JNIEnv *, jclass, jlong characterVa, jfloat vx, jfloat vy, jfloat vz) {
    CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    const Vec3 velocity(vx, vy, vz);
    pCharacter->SetLinearVelocity(velocity);
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    setMass
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_setMass
  (JNIEnv *, jclass, jlong characterVa, jfloat mass) {
    CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    pCharacter->SetMass(mass);
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    setMaxNumHits
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_setMaxNumHits
  (JNIEnv *, jclass, jlong characterVa, jint maxHits) {
    CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    pCharacter->SetMaxNumHits(maxHits);
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    setMaxStrength
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_setMaxStrength
  (JNIEnv *, jclass, jlong characterVa, jfloat force) {
    CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    pCharacter->SetMaxStrength(force);
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    setPenetrationRecoverySpeed
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_setPenetrationRecoverySpeed
  (JNIEnv *, jclass, jlong characterVa, jfloat fraction) {
    CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    pCharacter->SetPenetrationRecoverySpeed(fraction);
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    setPosition
 * Signature: (JDDD)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_setPosition
  (JNIEnv *, jclass, jlong characterVa, jdouble locX, jdouble locY, jdouble locZ) {
    CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    const RVec3 location(locX, locY, locZ);
    pCharacter->SetPosition(location);
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    setRotation
 * Signature: (JFFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_setRotation
  (JNIEnv *, jclass, jlong characterVa, jfloat qx, jfloat qy, jfloat qz, jfloat qw) {
    CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    const Quat orientation(qx, qy, qz, qw);
    pCharacter->SetRotation(orientation);
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    setShapeOffset
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_setShapeOffset
  (JNIEnv *, jclass, jlong characterVa, jfloat dx, jfloat dy, jfloat dz) {
    CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    const Vec3 offset(dx, dy, dz);
    pCharacter->SetShapeOffset(offset);
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    setUserData
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_setUserData
  (JNIEnv *, jclass, jlong characterVa, jlong userData) {
    CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    pCharacter->SetUserData(userData);
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    updateGroundVelocity
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_updateGroundVelocity
  (JNIEnv *, jclass, jlong characterVa) {
    CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    pCharacter->UpdateGroundVelocity();
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtual
 * Method:    toRef
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CharacterVirtual_toRef
  (JNIEnv *, jclass, jlong characterVa) {
    CharacterVirtual * const pCharacter
            = reinterpret_cast<CharacterVirtual *> (characterVa);
    Ref<CharacterVirtual> * const pResult
            = new Ref<CharacterVirtual>(pCharacter);
    TRACE_NEW("Ref<CharacterVirtual>", pResult)
    return reinterpret_cast<jlong> (pResult);
}