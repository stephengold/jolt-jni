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
#include "Jolt/Physics/PhysicsScene.h"

#include "auto/com_github_stephengold_joltjni_PhysicsScene.h"
#include "auto/com_github_stephengold_joltjni_PhysicsSceneRef.h"
#include "glue/glue.h"

using namespace JPH;

IMPLEMENT_REF(PhysicsScene,
  Java_com_github_stephengold_joltjni_PhysicsSceneRef_copy,
  Java_com_github_stephengold_joltjni_PhysicsSceneRef_createEmpty,
  Java_com_github_stephengold_joltjni_PhysicsSceneRef_free,
  Java_com_github_stephengold_joltjni_PhysicsSceneRef_getPtr)

/*
 * Class:     com_github_stephengold_joltjni_PhysicsScene
 * Method:    createBodies
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_PhysicsScene_createBodies
  (JNIEnv *, jclass, jlong sceneVa, jlong systemVa) {
    const PhysicsScene * const pScene
            = reinterpret_cast<PhysicsScene *> (sceneVa);
    PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    const bool result = pScene->CreateBodies(pSystem);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsScene
 * Method:    createDefaultScene
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PhysicsScene_createDefaultScene
  (JNIEnv *, jclass) {
    PhysicsScene * const pScene = new PhysicsScene();
    TRACE_NEW("PhysicsScene", pScene)
    return reinterpret_cast<jlong> (pScene);
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsScene
 * Method:    fixInvalidScales
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_PhysicsScene_fixInvalidScales
  (JNIEnv *, jclass, jlong sceneVa) {
    PhysicsScene * const pScene = reinterpret_cast<PhysicsScene *> (sceneVa);
    const bool result = pScene->FixInvalidScales();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsScene
 * Method:    fromPhysicsSystem
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsScene_fromPhysicsSystem
  (JNIEnv *, jclass, jlong sceneVa, jlong systemVa) {
    PhysicsScene * const pScene = reinterpret_cast<PhysicsScene *> (sceneVa);
    const PhysicsSystem * const pSystem
            = reinterpret_cast<PhysicsSystem *> (systemVa);
    pScene->FromPhysicsSystem(pSystem);
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsScene
 * Method:    getBody
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PhysicsScene_getBody
  (JNIEnv *, jclass, jlong sceneVa, jint bodyIndex) {
    PhysicsScene * const pScene = reinterpret_cast<PhysicsScene *> (sceneVa);
    BodyCreationSettings * const pResult = &pScene->GetBodies()[bodyIndex];
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsScene
 * Method:    getNumBodies
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_PhysicsScene_getNumBodies
  (JNIEnv *, jclass, jlong sceneVa) {
    const PhysicsScene * const pScene
            = reinterpret_cast<PhysicsScene *> (sceneVa);
    const Array<BodyCreationSettings>::size_type result
            = pScene->GetBodies().size();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsScene
 * Method:    getRefCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_PhysicsScene_getRefCount
  (JNIEnv *, jclass, jlong sceneVa) {
    const PhysicsScene * const pScene
            = reinterpret_cast<PhysicsScene *> (sceneVa);
    const uint32 result = pScene->GetRefCount();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsScene
 * Method:    saveBinaryState
 * Signature: (JJZZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsScene_saveBinaryState
  (JNIEnv *, jclass, jlong sceneVa, jlong streamVa, jboolean saveShapes,
  jboolean saveGroupFilter) {
    const PhysicsScene * const pScene
            = reinterpret_cast<PhysicsScene *> (sceneVa);
    StreamOut * const pStream = reinterpret_cast<StreamOut *> (streamVa);
    pScene->SaveBinaryState(*pStream, saveShapes, saveGroupFilter);
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsScene
 * Method:    setEmbedded
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsScene_setEmbedded
  (JNIEnv *, jclass, jlong sceneVa) {
    PhysicsScene * const pScene = reinterpret_cast<PhysicsScene *> (sceneVa);
    pScene->SetEmbedded();
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsScene
 * Method:    toRef
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PhysicsScene_toRef
  (JNIEnv *, jclass, jlong sceneVa) {
    PhysicsScene * const pScene = reinterpret_cast<PhysicsScene *> (sceneVa);
    Ref<PhysicsScene> * const pResult = new Ref<PhysicsScene>(pScene);
    TRACE_NEW("Ref<PhysicsScene>", pResult)
    return reinterpret_cast<jlong> (pResult);
}