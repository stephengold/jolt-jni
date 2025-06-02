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
#include "Jolt/ObjectStream/ObjectStreamIn.h"
#include "Jolt/Physics/Collision/GroupFilterTable.h"
#include "Jolt/Physics/Constraints/Constraint.h"
#include "Jolt/Physics/PhysicsScene.h"
#include "Jolt/Physics/Ragdoll/Ragdoll.h"
#include "Jolt/Skeleton/SkeletalAnimation.h"

#include "auto/com_github_stephengold_joltjni_ObjectStreamIn.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamIn
 * Method:    sReadBcsFromStream
 * Signature: (J[J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamIn_sReadBcsFromStream
  (JNIEnv *pEnv, jclass, jlong streamVa, jlongArray storeVa) {
    std::stringstream * const pStream
            = reinterpret_cast<std::stringstream *> (streamVa);
    BodyCreationSettings * pSettings = new BodyCreationSettings();
    TRACE_NEW("BodyCreationSettings", pSettings)
    const bool result = ObjectStreamIn::sReadObject(*pStream, pSettings);
    jboolean isCopy;
    jlong * const pStoreVa = pEnv->GetLongArrayElements(storeVa, &isCopy);
    pStoreVa[0] = reinterpret_cast<jlong> (pSettings);
    pEnv->ReleaseLongArrayElements(storeVa, pStoreVa, 0);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamIn
 * Method:    sReadConstraintSettingsFromStream
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamIn_sReadConstraintSettingsFromStream
  (JNIEnv *, jclass, jlong streamVa, jlong settingsRefVa) {
    std::stringstream * const pStream
            = reinterpret_cast<std::stringstream *> (streamVa);
    Ref<ConstraintSettings> * const pSettingsRef
            = reinterpret_cast<Ref<ConstraintSettings> *> (settingsRefVa);
    const bool result = ObjectStreamIn::sReadObject(*pStream, *pSettingsRef);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamIn
 * Method:    sReadGroupFilterTableFromStream
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamIn_sReadGroupFilterTableFromStream
  (JNIEnv *, jclass, jlong streamVa, jlong refVa) {
    std::stringstream * const pStream
            = reinterpret_cast<std::stringstream *> (streamVa);
    Ref<GroupFilterTable> * const pStoreRef
            = reinterpret_cast<Ref<GroupFilterTable> *> (refVa);
    const bool result = ObjectStreamIn::sReadObject(*pStream, *pStoreRef);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamIn
 * Method:    sReadPhysicsMaterialFromStream
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamIn_sReadPhysicsMaterialFromStream
  (JNIEnv *, jclass, jlong streamVa, jlong refVa) {
    std::stringstream * const pStream
            = reinterpret_cast<std::stringstream *> (streamVa);
    Ref<PhysicsMaterial> * const pStoreRef
            = reinterpret_cast<Ref<PhysicsMaterial> *> (refVa);
    const bool result = ObjectStreamIn::sReadObject(*pStream, *pStoreRef);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamIn
 * Method:    sReadPhysicsScene
 * Signature: (Ljava/lang/String;J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamIn_sReadPhysicsScene
  (JNIEnv *pEnv, jclass, jstring fileName, jlong refVa) {
    jboolean isCopy;
    const char * const pFileName = pEnv->GetStringUTFChars(fileName, &isCopy);
    Ref<PhysicsScene> * const pStoreRef
            = reinterpret_cast<Ref<PhysicsScene> *> (refVa);
    const bool result = ObjectStreamIn::sReadObject(pFileName, *pStoreRef);
    pEnv->ReleaseStringUTFChars(fileName, pFileName);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamIn
 * Method:    sReadPhysicsSceneFromStream
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamIn_sReadPhysicsSceneFromStream
  (JNIEnv *, jclass, jlong streamVa, jlong refVa) {
    std::stringstream * const pStream
            = reinterpret_cast<std::stringstream *> (streamVa);
    Ref<PhysicsScene> * const pStoreRef
            = reinterpret_cast<Ref<PhysicsScene> *> (refVa);
    const bool result = ObjectStreamIn::sReadObject(*pStream, *pStoreRef);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamIn
 * Method:    sReadRagdollSettings
 * Signature: (Ljava/lang/String;J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamIn_sReadRagdollSettings
  (JNIEnv *pEnv, jclass, jstring fileName, jlong refVa) {
    jboolean isCopy;
    const char * const pFileName = pEnv->GetStringUTFChars(fileName, &isCopy);
    Ref<RagdollSettings> * const pStoreRef
            = reinterpret_cast<Ref<RagdollSettings> *> (refVa);
    const bool result = ObjectStreamIn::sReadObject(pFileName, *pStoreRef);
    pEnv->ReleaseStringUTFChars(fileName, pFileName);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamIn
 * Method:    sReadRagdollSettingsFromStream
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamIn_sReadRagdollSettingsFromStream
  (JNIEnv *, jclass, jlong streamVa, jlong refVa) {
    std::stringstream * const pStream
            = reinterpret_cast<std::stringstream *> (streamVa);
    Ref<RagdollSettings> * const pStoreRef
            = reinterpret_cast<Ref<RagdollSettings> *> (refVa);
    const bool result = ObjectStreamIn::sReadObject(*pStream, *pStoreRef);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamIn
 * Method:    sReadSbcsFromStream
 * Signature: (J[J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamIn_sReadSbcsFromStream
  (JNIEnv *pEnv, jclass, jlong streamVa, jlongArray storeVa) {
    std::stringstream * const pStream
            = reinterpret_cast<std::stringstream *> (streamVa);
    SoftBodyCreationSettings * pSettings = new SoftBodyCreationSettings();
    TRACE_NEW("SoftBodyCreationSettings", pSettings)
    const bool result = ObjectStreamIn::sReadObject(*pStream, pSettings);
    jboolean isCopy;
    jlong * const pStoreVa = pEnv->GetLongArrayElements(storeVa, &isCopy);
    pStoreVa[0] = reinterpret_cast<jlong> (pSettings);
    pEnv->ReleaseLongArrayElements(storeVa, pStoreVa, 0);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamIn
 * Method:    sReadSbssFromStream
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamIn_sReadSbssFromStream
  (JNIEnv *, jclass, jlong streamVa, jlong refVa) {
    std::stringstream * const pStream
            = reinterpret_cast<std::stringstream *> (streamVa);
    Ref<SoftBodySharedSettings> * const pStoreRef
            = reinterpret_cast<Ref<SoftBodySharedSettings> *> (refVa);
    const bool result = ObjectStreamIn::sReadObject(*pStream, *pStoreRef);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamIn
 * Method:    sReadSkeletalAnimation
 * Signature: (Ljava/lang/String;J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamIn_sReadSkeletalAnimation
  (JNIEnv *pEnv, jclass, jstring fileName, jlong refVa) {
    jboolean isCopy;
    const char * const pFileName = pEnv->GetStringUTFChars(fileName, &isCopy);
    Ref<SkeletalAnimation> * const pStoreRef
            = reinterpret_cast<Ref<SkeletalAnimation> *> (refVa);
    const bool result = ObjectStreamIn::sReadObject(pFileName, *pStoreRef);
    pEnv->ReleaseStringUTFChars(fileName, pFileName);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamIn
 * Method:    sReadSkeleton
 * Signature: (Ljava/lang/String;J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamIn_sReadSkeleton
  (JNIEnv *pEnv, jclass, jstring fileName, jlong refVa) {
    jboolean isCopy;
    const char * const pFileName = pEnv->GetStringUTFChars(fileName, &isCopy);
    Ref<Skeleton> * const pStoreRef = reinterpret_cast<Ref<Skeleton> *> (refVa);
    const bool result = ObjectStreamIn::sReadObject(pFileName, *pStoreRef);
    pEnv->ReleaseStringUTFChars(fileName, pFileName);
    return result;
}