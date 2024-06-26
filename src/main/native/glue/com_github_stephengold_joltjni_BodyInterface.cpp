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
#include <Jolt/Physics/Body/BodyInterface.h>
#include "auto/com_github_stephengold_joltjni_BodyInterface.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    addBody
 * Signature: (JJI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_addBody
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa, jint activationOrdinal) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    const EActivation eActivation = (EActivation) activationOrdinal;
    pInterface->AddBody(*pBodyId, eActivation);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    createAndAddBody
 * Signature: (JJI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BodyInterface_createAndAddBody
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong settingsVa, jint activationOrdinal) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (settingsVa);
    const EActivation eActivation = (EActivation) activationOrdinal;
    const BodyID &result
            = pInterface->CreateAndAddBody(*pSettings, eActivation);
    return reinterpret_cast<jlong> (&result);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    createBody
 * Signature: (JJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BodyInterface_createBody
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong settingsVa) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (settingsVa);
    const Body * const pResult = pInterface->CreateBody(*pSettings);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    destroyBody
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_destroyBody
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    pInterface->DestroyBody(*pBodyId);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getCenterOfMassPositionX
 * Signature: (JJ)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getCenterOfMassPositionX
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    RVec3 position = pInterface->GetCenterOfMassPosition(*pBodyId);
    return position.GetX();
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getCenterOfMassPositionY
 * Signature: (JJ)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getCenterOfMassPositionY
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    RVec3 position = pInterface->GetCenterOfMassPosition(*pBodyId);
    return position.GetY();
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getCenterOfMassPositionZ
 * Signature: (JJ)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getCenterOfMassPositionZ
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    RVec3 position = pInterface->GetCenterOfMassPosition(*pBodyId);
    return position.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getLinearVelocityX
 * Signature: (JJ)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getLinearVelocityX
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    Vec3 velocity = pInterface->GetLinearVelocity(*pBodyId);
    return velocity.GetX();
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getLinearVelocityY
 * Signature: (JJ)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getLinearVelocityY
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    Vec3 velocity = pInterface->GetLinearVelocity(*pBodyId);
    return velocity.GetY();
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    getLinearVelocityZ
 * Signature: (JJ)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BodyInterface_getLinearVelocityZ
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    Vec3 velocity = pInterface->GetLinearVelocity(*pBodyId);
    return velocity.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    isActive
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_BodyInterface_isActive
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    bool result = pInterface->IsActive(*pBodyId);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    removeBody
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_removeBody
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    pInterface->RemoveBody(*pBodyId);
}

/*
 * Class:     com_github_stephengold_joltjni_BodyInterface
 * Method:    setLinearVelocity
 * Signature: (JJFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BodyInterface_setLinearVelocity
  (JNIEnv *, jclass, jlong bodyInterfaceVa, jlong bodyIdVa,
    jfloat vx, jfloat vy, jfloat vz) {
    BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pBodyId = reinterpret_cast<BodyID *> (bodyIdVa);
    const Vec3 velocity(vx, vy, vz);
    pInterface->SetLinearVelocity(*pBodyId, velocity);
}
