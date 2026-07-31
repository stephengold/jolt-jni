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
#include "Jolt/Physics/PhysicsScene.h"

#include "auto/com_github_stephengold_joltjni_ConnectedConstraint.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_ConnectedConstraint
 * Method:    assign
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ConnectedConstraint_assign
  BODYOF_ASSIGN(PhysicsScene::ConnectedConstraint)

/*
 * Class:     com_github_stephengold_joltjni_ConnectedConstraint
 * Method:    create
 * Signature: (JII)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ConnectedConstraint_create
  (JNIEnv *, jclass, jlong settingsVa, jint body1, jint body2) {
    const TwoBodyConstraintSettings * const pSettings
            = reinterpret_cast<TwoBodyConstraintSettings *> (settingsVa);
    PhysicsScene::ConnectedConstraint * const pResult
            = new PhysicsScene::ConnectedConstraint(pSettings, body1, body2);
    TRACE_NEW("PhysicsScene::ConnectedConstraint", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_ConnectedConstraint
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ConnectedConstraint_createCopy
  BODYOF_CREATE_COPY(PhysicsScene::ConnectedConstraint)

/*
 * Class:     com_github_stephengold_joltjni_ConnectedConstraint
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ConnectedConstraint_createDefault
  BODYOF_CREATE_DEFAULT(PhysicsScene::ConnectedConstraint)

/*
 * Class:     com_github_stephengold_joltjni_ConnectedConstraint
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ConnectedConstraint_free
  BODYOF_FREE(PhysicsScene::ConnectedConstraint)

/*
 * Class:     com_github_stephengold_joltjni_ConnectedConstraint
 * Method:    getBody1
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_ConnectedConstraint_getBody1
  (JNIEnv *, jclass, jlong constraintVa) {
    const PhysicsScene::ConnectedConstraint * const pConstraint
            = reinterpret_cast<PhysicsScene::ConnectedConstraint *> (constraintVa);
    uint32 result = pConstraint->mBody1;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ConnectedConstraint
 * Method:    getBody2
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_ConnectedConstraint_getBody2
  (JNIEnv *, jclass, jlong constraintVa) {
    const PhysicsScene::ConnectedConstraint * const pConstraint
            = reinterpret_cast<PhysicsScene::ConnectedConstraint *> (constraintVa);
    uint32 result = pConstraint->mBody2;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ConnectedConstraint
 * Method:    getSettings
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ConnectedConstraint_getSettings
  (JNIEnv *, jclass, jlong constraintVa) {
    const PhysicsScene::ConnectedConstraint * const pConstraint
            = reinterpret_cast<PhysicsScene::ConnectedConstraint *> (constraintVa);
    RefConst<TwoBodyConstraintSettings> settings = pConstraint->mSettings;
    const TwoBodyConstraintSettings * const ptr = settings.GetPtr();
    const jlong result = reinterpret_cast<jlong> (ptr);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ConnectedConstraint
 * Method:    setBody1
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ConnectedConstraint_setBody1
  (JNIEnv *, jclass, jlong constraintVa, jint body1) {
    PhysicsScene::ConnectedConstraint * const pConstraint
            = reinterpret_cast<PhysicsScene::ConnectedConstraint *> (constraintVa);
    pConstraint->mBody1 = body1;
}

/*
 * Class:     com_github_stephengold_joltjni_ConnectedConstraint
 * Method:    setBody2
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ConnectedConstraint_setBody2
  (JNIEnv *, jclass, jlong constraintVa, jint body2) {
    PhysicsScene::ConnectedConstraint * const pConstraint
            = reinterpret_cast<PhysicsScene::ConnectedConstraint *> (constraintVa);
    pConstraint->mBody2 = body2;
}

/*
 * Class:     com_github_stephengold_joltjni_ConnectedConstraint
 * Method:    setSettings
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ConnectedConstraint_setSettings
  (JNIEnv *, jclass, jlong constraintVa, jlong settingsVa) {
    PhysicsScene::ConnectedConstraint * const pConstraint
            = reinterpret_cast<PhysicsScene::ConnectedConstraint *> (constraintVa);
    const TwoBodyConstraintSettings * const pSettings
            = reinterpret_cast<TwoBodyConstraintSettings *> (settingsVa);
    pConstraint->mSettings = pSettings;
}