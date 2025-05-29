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
#include "Jolt/Physics/Character/Character.h"

#include "auto/com_github_stephengold_joltjni_Character.h"
#include "auto/com_github_stephengold_joltjni_CharacterRef.h"
#include "glue/glue.h"

using namespace JPH;

IMPLEMENT_REF(Character,
  Java_com_github_stephengold_joltjni_CharacterRef_copy,
  Java_com_github_stephengold_joltjni_CharacterRef_createEmpty,
  Java_com_github_stephengold_joltjni_CharacterRef_free,
  Java_com_github_stephengold_joltjni_CharacterRef_getPtr,
  Java_com_github_stephengold_joltjni_CharacterRef_toRefC)

/*
 * Class:     com_github_stephengold_joltjni_CharacterRef
 * Method:    freeWithSystem
 * Signature: (JLcom/github/stephengold/joltjni/PhysicsSystem;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterRef_freeWithSystem
  (JNIEnv *pEnv, jclass clazz, jlong refVa, jobject) {
    Java_com_github_stephengold_joltjni_CharacterRef_free(pEnv, clazz, refVa);
}

/*
 * Class:     com_github_stephengold_joltjni_Character
 * Method:    activate
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Character_activate
  (JNIEnv *, jclass, jlong characterVa, jboolean lockBodies) {
    Character * const pCharacter = reinterpret_cast<Character *> (characterVa);
    pCharacter->Activate(lockBodies);
}

/*
 * Class:     com_github_stephengold_joltjni_Character
 * Method:    addImpulse
 * Signature: (JFFFZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Character_addImpulse
  (JNIEnv *, jclass, jlong characterVa, jfloat x, jfloat y, jfloat z, jboolean lockBodies) {
    Character * const pCharacter = reinterpret_cast<Character *> (characterVa);
    const Vec3 impulse(x, y, z);
    pCharacter->AddImpulse(impulse, lockBodies);
}

/*
 * Class:     com_github_stephengold_joltjni_Character
 * Method:    addLinearVelocity
 * Signature: (JFFFZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Character_addLinearVelocity
  (JNIEnv *, jclass, jlong characterVa, jfloat vx, jfloat vy, jfloat vz, jboolean lockBodies) {
    Character * const pCharacter = reinterpret_cast<Character *> (characterVa);
    const Vec3 deltaV(vx, vy, vz);
    pCharacter->AddLinearVelocity(deltaV, lockBodies);
}

/*
 * Class:     com_github_stephengold_joltjni_Character
 * Method:    addToPhysicsSystem
 * Signature: (JIZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Character_addToPhysicsSystem
  (JNIEnv *, jclass, jlong characterVa, jint ordinal, jboolean lockBodies) {
    Character * const pCharacter = reinterpret_cast<Character *> (characterVa);
    const EActivation activation = (EActivation) ordinal;
    pCharacter->AddToPhysicsSystem(activation, lockBodies);
}

/*
 * Class:     com_github_stephengold_joltjni_Character
 * Method:    createCharacter
 * Signature: (JDDDFFFFJJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Character_createCharacter
  (JNIEnv *, jclass, jlong settingsVa, jdouble locX, jdouble locY, jdouble locZ,
  jfloat qx, jfloat qy, jfloat qz, jfloat qw, jlong userData, jlong systemVa) {
    const CharacterSettings * const pSettings
            = reinterpret_cast<CharacterSettings *> (settingsVa);
    const RVec3 location(locX, locY, locZ);
    const Quat orientation(qx, qy, qz, qw);
    PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    Character * const pResult = new Character(
            pSettings, location, orientation, userData, pSystem);
    TRACE_NEW("Character", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Character
 * Method:    getBodyId
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Character_getBodyId
  (JNIEnv *, jclass, jlong characterVa) {
    const Character * const pCharacter
            = reinterpret_cast<Character *> (characterVa);
    const BodyID result = pCharacter->GetBodyID();
    return result.GetIndexAndSequenceNumber();
}

/*
 * Class:     com_github_stephengold_joltjni_Character
 * Method:    getCenterOfMassPosition
 * Signature: (J[DZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Character_getCenterOfMassPosition
  (JNIEnv *pEnv, jclass, jlong characterVa, jdoubleArray storeDoubles, jboolean lockBodies) {
    const Character * const pCharacter
            = reinterpret_cast<Character *> (characterVa);
    const RVec3 location = pCharacter->GetCenterOfMassPosition(lockBodies);
    jboolean isCopy;
    jdouble * const pStoreDoubles
            = pEnv->GetDoubleArrayElements(storeDoubles, &isCopy);
    pStoreDoubles[0] = location.GetX();
    pStoreDoubles[1] = location.GetY();
    pStoreDoubles[2] = location.GetZ();
    pEnv->ReleaseDoubleArrayElements(storeDoubles, pStoreDoubles, 0);
}

/*
 * Class:     com_github_stephengold_joltjni_Character
 * Method:    getCharacterSettings
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Character_getCharacterSettings
  (JNIEnv *, jclass, jlong characterVa) {
    const Character * const pCharacter
            = reinterpret_cast<Character *> (characterVa);
    CharacterSettings * const pResult = new CharacterSettings();
    TRACE_NEW("CharacterSettings", pResult)
    *pResult = pCharacter->GetCharacterSettings();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Character
 * Method:    getLayer
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Character_getLayer
  (JNIEnv *, jclass, jlong characterVa) {
    const Character * const pCharacter
            = reinterpret_cast<Character *> (characterVa);
    const ObjectLayer result = pCharacter->GetLayer();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Character
 * Method:    getLinearVelocity
 * Signature: (J[FZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Character_getLinearVelocity
  (JNIEnv *pEnv, jclass, jlong characterVa, jfloatArray storeFloats, jboolean lockBodies) {
    const Character * const pCharacter
            = reinterpret_cast<Character *> (characterVa);
    const Vec3 velocity = pCharacter->GetLinearVelocity(lockBodies);
    jboolean isCopy;
    jfloat * const pStoreFloats
            = pEnv->GetFloatArrayElements(storeFloats, &isCopy);
    pStoreFloats[0] = velocity.GetX();
    pStoreFloats[1] = velocity.GetY();
    pStoreFloats[2] = velocity.GetZ();
    pEnv->ReleaseFloatArrayElements(storeFloats, pStoreFloats, 0);
}

/*
 * Class:     com_github_stephengold_joltjni_Character
 * Method:    getPosition
 * Signature: (J[DZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Character_getPosition
  (JNIEnv *pEnv, jclass, jlong characterVa, jdoubleArray storeDoubles, jboolean lockBodies) {
    const Character * const pCharacter
            = reinterpret_cast<Character *> (characterVa);
    const RVec3 location = pCharacter->GetPosition(lockBodies);
    jboolean isCopy;
    jdouble * const pStoreDoubles
            = pEnv->GetDoubleArrayElements(storeDoubles, &isCopy);
    pStoreDoubles[0] = location.GetX();
    pStoreDoubles[1] = location.GetY();
    pStoreDoubles[2] = location.GetZ();
    pEnv->ReleaseDoubleArrayElements(storeDoubles, pStoreDoubles, 0);
}

/*
 * Class:     com_github_stephengold_joltjni_Character
 * Method:    getPositionAndRotation
 * Signature: (J[D[FZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Character_getPositionAndRotation
  (JNIEnv *pEnv, jclass, jlong characterVa, jdoubleArray storeDoubles,
  jfloatArray storeFloats, jboolean lockBodies) {
    const Character * const pCharacter
            = reinterpret_cast<Character *> (characterVa);
    RVec3 location;
    Quat orientation;
    pCharacter->GetPositionAndRotation(location, orientation, lockBodies);
    jboolean isCopy;
    jdouble * const pStoreDoubles
            = pEnv->GetDoubleArrayElements(storeDoubles, &isCopy);
    pStoreDoubles[0] = location.GetX();
    pStoreDoubles[1] = location.GetY();
    pStoreDoubles[2] = location.GetZ();
    pEnv->ReleaseDoubleArrayElements(storeDoubles, pStoreDoubles, 0);
    jfloat * const pStoreFloats
            = pEnv->GetFloatArrayElements(storeFloats, &isCopy);
    pStoreFloats[0] = orientation.GetX();
    pStoreFloats[1] = orientation.GetY();
    pStoreFloats[2] = orientation.GetZ();
    pStoreFloats[3] = orientation.GetW();
    pEnv->ReleaseFloatArrayElements(storeFloats, pStoreFloats, 0);
}

/*
 * Class:     com_github_stephengold_joltjni_Character
 * Method:    getRotation
 * Signature: (J[FZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Character_getRotation
  (JNIEnv *pEnv, jclass, jlong characterVa, jfloatArray storeFloats, jboolean lockBodies) {
    const Character * const pCharacter
            = reinterpret_cast<Character *> (characterVa);
    const Quat orientation = pCharacter->GetRotation(lockBodies);
    jboolean isCopy;
    jfloat * const pStoreFloats
            = pEnv->GetFloatArrayElements(storeFloats, &isCopy);
    pStoreFloats[0] = orientation.GetX();
    pStoreFloats[1] = orientation.GetY();
    pStoreFloats[2] = orientation.GetZ();
    pStoreFloats[3] = orientation.GetW();
    pEnv->ReleaseFloatArrayElements(storeFloats, pStoreFloats, 0);
}

/*
 * Class:     com_github_stephengold_joltjni_Character
 * Method:    getTransformedShape
 * Signature: (JZ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Character_getTransformedShape
  (JNIEnv *, jclass, jlong characterVa, jboolean lockBodies) {
    const Character * const pCharacter
            = reinterpret_cast<Character *> (characterVa);
    const TransformedShape& shape = pCharacter->GetTransformedShape(lockBodies);
    TransformedShape * const pResult = new TransformedShape(shape);
    TRACE_NEW("TransformedShape", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Character
 * Method:    getWorldTransform
 * Signature: (JZ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Character_getWorldTransform
  (JNIEnv *, jclass, jlong characterVa, jboolean lockBodies) {
    const Character * const pCharacter
            = reinterpret_cast<Character *> (characterVa);
    RMat44 * const pResult = new RMat44();
    TRACE_NEW("RMat44", pResult)
    *pResult = pCharacter->GetWorldTransform(lockBodies);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Character
 * Method:    postSimulation
 * Signature: (JFZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Character_postSimulation
  (JNIEnv *, jclass, jlong characterVa, jfloat maxSeparation, jboolean lockBodies) {
    Character * const pCharacter = reinterpret_cast<Character *> (characterVa);
    pCharacter->PostSimulation(maxSeparation, lockBodies);
}

/*
 * Class:     com_github_stephengold_joltjni_Character
 * Method:    removeFromPhysicsSystem
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Character_removeFromPhysicsSystem
  (JNIEnv *, jclass, jlong characterVa, jboolean lockBodies) {
    Character * const pCharacter = reinterpret_cast<Character *> (characterVa);
    pCharacter->RemoveFromPhysicsSystem(lockBodies);
}

/*
 * Class:     com_github_stephengold_joltjni_Character
 * Method:    setLayer
 * Signature: (JIZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Character_setLayer
  (JNIEnv *, jclass, jlong characterVa, jint layer, jboolean lockBodies) {
    Character * const pCharacter = reinterpret_cast<Character *> (characterVa);
    const ObjectLayer objectLayer = (ObjectLayer) layer;
    pCharacter->SetLayer(objectLayer, lockBodies);
}

/*
 * Class:     com_github_stephengold_joltjni_Character
 * Method:    setLinearAndAngularVelocity
 * Signature: (JFFFFFFZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Character_setLinearAndAngularVelocity
  (JNIEnv *, jclass, jlong characterVa, jfloat vx, jfloat vy, jfloat vz,
  jfloat wx, jfloat wy, jfloat wz, jboolean lockBodies) {
    Character * const pCharacter = reinterpret_cast<Character *> (characterVa);
    const Vec3 linearVelocity(vx, vy, vz);
    const Vec3 omega(wx, wy, wz);
    pCharacter->SetLinearAndAngularVelocity(linearVelocity, omega, lockBodies);
}

/*
 * Class:     com_github_stephengold_joltjni_Character
 * Method:    setLinearVelocity
 * Signature: (JFFFZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Character_setLinearVelocity
  (JNIEnv *, jclass, jlong characterVa, jfloat vx, jfloat vy, jfloat vz, jboolean lockBodies) {
    Character * const pCharacter = reinterpret_cast<Character *> (characterVa);
    const Vec3 linearVelocity(vx, vy, vz);
    pCharacter->SetLinearVelocity(linearVelocity, lockBodies);
}

/*
 * Class:     com_github_stephengold_joltjni_Character
 * Method:    setPosition
 * Signature: (JDDDIZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Character_setPosition
  (JNIEnv *, jclass, jlong characterVa, jdouble locX, jdouble locY,
  jdouble locZ, jint ordinal, jboolean lockBodies) {
    Character * const pCharacter = reinterpret_cast<Character *> (characterVa);
    const RVec3 location(locX, locY, locZ);
    const EActivation activation = (EActivation) ordinal;
    pCharacter->SetPosition(location, activation, lockBodies);
}

/*
 * Class:     com_github_stephengold_joltjni_Character
 * Method:    setPositionAndRotation
 * Signature: (JDDDFFFFIZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Character_setPositionAndRotation
  (JNIEnv *, jclass, jlong characterVa, jdouble locX, jdouble locY,
  jdouble locZ, jfloat qx, jfloat qy, jfloat qz, jfloat qw, jint ordinal, jboolean lockBodies) {
    Character * const pCharacter = reinterpret_cast<Character *> (characterVa);
    const RVec3 location(locX, locY, locZ);
    const Quat orientation(qx, qy, qz, qw);
    const EActivation activation = (EActivation) ordinal;
    pCharacter->SetPositionAndRotation(
            location, orientation, activation, lockBodies);
}

/*
 * Class:     com_github_stephengold_joltjni_Character
 * Method:    setRotation
 * Signature: (JFFFFIZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Character_setRotation
  (JNIEnv *, jclass, jlong characterVa, jfloat qx, jfloat qy, jfloat qz,
  jfloat qw, jint ordinal, jboolean lockBodies) {
    Character * const pCharacter = reinterpret_cast<Character *> (characterVa);
    const Quat orientation(qx, qy, qz, qw);
    const EActivation activation = (EActivation) ordinal;
    pCharacter->SetRotation(orientation, activation, lockBodies);
}

/*
 * Class:     com_github_stephengold_joltjni_Character
 * Method:    setShape
 * Signature: (JJFZ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_Character_setShape
  (JNIEnv *, jclass, jlong characterVa, jlong shapeVa,
  jfloat maxPenetrationDepth, jboolean lockBodies) {
    Character * const pCharacter = reinterpret_cast<Character *> (characterVa);
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    const bool result = pCharacter->SetShape(pShape, maxPenetrationDepth, lockBodies);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Character
 * Method:    toRef
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Character_toRef
  (JNIEnv *, jclass, jlong characterVa) {
    Character * const pCharacter = reinterpret_cast<Character *> (characterVa);
    Ref<Character> * const pResult = new Ref<Character>(pCharacter);
    TRACE_NEW("Ref<Character>", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Character
 * Method:    toRefC
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Character_toRefC
  (JNIEnv *, jclass, jlong characterVa) {
    const Character * const pCharacter
            = reinterpret_cast<Character *> (characterVa);
    RefConst<Character> * const pResult
            = new RefConst<Character>(pCharacter);
    TRACE_NEW("RefConst<Character>", pResult)
    return reinterpret_cast<jlong> (pResult);
}