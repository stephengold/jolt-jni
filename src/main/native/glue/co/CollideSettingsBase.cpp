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
#include <Jolt/Physics/Collision/CollideShape.h>
#include "auto/com_github_stephengold_joltjni_CollideSettingsBase.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_CollideSettingsBase
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CollideSettingsBase_free
  (JNIEnv *, jclass, jlong settingsVa) {
    CollideSettingsBase * const pSettings
            = reinterpret_cast<CollideSettingsBase *> (settingsVa);
    TRACE_DELETE("CollideSettingsBase", pSettings)
    delete pSettings;
}

/*
 * Class:     com_github_stephengold_joltjni_CollideSettingsBase
 * Method:    getActiveEdgeMode
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_CollideSettingsBase_getActiveEdgeMode
  (JNIEnv *, jclass, jlong settingsVa) {
    const CollideSettingsBase * const pSettings
            = reinterpret_cast<CollideSettingsBase *> (settingsVa);
    const EActiveEdgeMode result = pSettings->mActiveEdgeMode;
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_CollideSettingsBase
 * Method:    getActiveEdgeMovementDirectionX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CollideSettingsBase_getActiveEdgeMovementDirectionX
  (JNIEnv *, jclass, jlong settingsVa) {
    const CollideSettingsBase * const pSettings
            = reinterpret_cast<CollideSettingsBase *> (settingsVa);
    const float result = pSettings->mActiveEdgeMovementDirection.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CollideSettingsBase
 * Method:    getActiveEdgeMovementDirectionY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CollideSettingsBase_getActiveEdgeMovementDirectionY
  (JNIEnv *, jclass, jlong settingsVa) {
    const CollideSettingsBase * const pSettings
            = reinterpret_cast<CollideSettingsBase *> (settingsVa);
    const float result = pSettings->mActiveEdgeMovementDirection.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CollideSettingsBase
 * Method:    getActiveEdgeMovementDirectionZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CollideSettingsBase_getActiveEdgeMovementDirectionZ
  (JNIEnv *, jclass, jlong settingsVa) {
    const CollideSettingsBase * const pSettings
            = reinterpret_cast<CollideSettingsBase *> (settingsVa);
    const float result = pSettings->mActiveEdgeMovementDirection.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CollideSettingsBase
 * Method:    getCollectFacesMode
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_CollideSettingsBase_getCollectFacesMode
  (JNIEnv *, jclass, jlong settingsVa) {
    const CollideSettingsBase * const pSettings
            = reinterpret_cast<CollideSettingsBase *> (settingsVa);
    const ECollectFacesMode result = pSettings->mCollectFacesMode;
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_CollideSettingsBase
 * Method:    getCollisionTolerance
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CollideSettingsBase_getCollisionTolerance
  (JNIEnv *, jclass, jlong settingsVa) {
    const CollideSettingsBase * const pSettings
            = reinterpret_cast<CollideSettingsBase *> (settingsVa);
    const float result = pSettings->mCollisionTolerance;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CollideSettingsBase
 * Method:    getPenetrationTolerance
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CollideSettingsBase_getPenetrationTolerance
  (JNIEnv *, jclass, jlong settingsVa) {
    const CollideSettingsBase * const pSettings
            = reinterpret_cast<CollideSettingsBase *> (settingsVa);
    const float result = pSettings->mPenetrationTolerance;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CollideSettingsBase
 * Method:    setActiveEdgeMode
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CollideSettingsBase_setActiveEdgeMode
  (JNIEnv *, jclass, jlong settingsVa, jint ordinal) {
    CollideSettingsBase * const pSettings
            = reinterpret_cast<CollideSettingsBase *> (settingsVa);
    const EActiveEdgeMode mode = (EActiveEdgeMode) ordinal;
    pSettings->mActiveEdgeMode = mode;
}

/*
 * Class:     com_github_stephengold_joltjni_CollideSettingsBase
 * Method:    setActiveEdgeMovementDirection
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CollideSettingsBase_setActiveEdgeMovementDirection
  (JNIEnv *, jclass, jlong settingsVa, jfloat dx, jfloat dy, jfloat dz) {
    CollideSettingsBase * const pSettings
            = reinterpret_cast<CollideSettingsBase *> (settingsVa);
    const Vec3 direction(dx, dy, dz);
    pSettings->mActiveEdgeMovementDirection = direction;
}

/*
 * Class:     com_github_stephengold_joltjni_CollideSettingsBase
 * Method:    setCollectFacesMode
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CollideSettingsBase_setCollectFacesMode
  (JNIEnv *, jclass, jlong settingsVa, jint ordinal) {
    CollideSettingsBase * const pSettings
            = reinterpret_cast<CollideSettingsBase *> (settingsVa);
    const ECollectFacesMode mode = (ECollectFacesMode) ordinal;
    pSettings->mCollectFacesMode = mode;
}

/*
 * Class:     com_github_stephengold_joltjni_CollideSettingsBase
 * Method:    setCollisionTolerance
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CollideSettingsBase_setCollisionTolerance
  (JNIEnv *, jclass, jlong settingsVa, jfloat tolerance) {
    CollideSettingsBase * const pSettings
            = reinterpret_cast<CollideSettingsBase *> (settingsVa);
    pSettings->mCollisionTolerance = tolerance;
}

/*
 * Class:     com_github_stephengold_joltjni_CollideSettingsBase
 * Method:    setPenetrationTolerance
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CollideSettingsBase_setPenetrationTolerance
  (JNIEnv *, jclass, jlong settingsVa, jfloat tolerance) {
    CollideSettingsBase * const pSettings
            = reinterpret_cast<CollideSettingsBase *> (settingsVa);
    pSettings->mPenetrationTolerance = tolerance;
}