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
#include "Jolt/Physics/Vehicle/VehicleController.h"
#include "Jolt/Skeleton/SkeletalAnimation.h"

#include "auto/com_github_stephengold_joltjni_ObjectStreamIn.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Pre-processor macro to generate the body of a static
 * sRead{T}() method for a RefTarget type T:
 */
#define BODYOF_SREAD_FROM_FILE_REF(T) \
  (JNIEnv *pEnv, jclass, jstring fileName, jlong refVa) { \
    jboolean isCopy; \
    const char * const pFileName = pEnv->GetStringUTFChars(fileName, &isCopy); \
    Ref<T> * const pStoreRef = reinterpret_cast<Ref<T> *> (refVa); \
    const bool result = ObjectStreamIn::sReadObject(pFileName, *pStoreRef); \
    pEnv->ReleaseStringUTFChars(fileName, pFileName); \
    return result; \
}
/*
 * Pre-processor macro to generate the body of a static
 * sRead{T}FromStream() method for a non-RefTarget type T:
 */
#define BODYOF_SREAD_FROM_STREAM_NONREF(T) \
  (JNIEnv *pEnv, jclass, jlong streamVa, jlongArray storeVa) { \
    std::stringstream * const pStream = reinterpret_cast<std::stringstream *> (streamVa); \
    T * pSettings = new T(); \
    TRACE_NEW(#T, pSettings) \
    const bool result = ObjectStreamIn::sReadObject(*pStream, pSettings); \
    jboolean isCopy; \
    jlong * const pStoreVa = pEnv->GetLongArrayElements(storeVa, &isCopy); \
    pStoreVa[0] = reinterpret_cast<jlong> (pSettings); \
    pEnv->ReleaseLongArrayElements(storeVa, pStoreVa, 0); \
    return result; \
}
/*
 * Pre-processor macro to generate the body of a static
 * sRead{T}FromStream() method for a RefTarget type T:
 */
#define BODYOF_SREAD_FROM_STREAM_REF(T) \
  (JNIEnv *pEnv, jclass, jlong streamVa, jlong settingsRefVa) { \
    std::stringstream * const pStream = reinterpret_cast<std::stringstream *> (streamVa); \
    Ref<T> * const pSettingsRef = reinterpret_cast<Ref<T> *> (settingsRefVa); \
    const bool result = ObjectStreamIn::sReadObject(*pStream, *pSettingsRef); \
    return result; \
}

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamIn
 * Method:    sReadBcsFromStream
 * Signature: (J[J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamIn_sReadBcsFromStream
  BODYOF_SREAD_FROM_STREAM_NONREF(BodyCreationSettings)

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamIn
 * Method:    sReadConstraintSettings
 * Signature: (Ljava/lang/String;J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamIn_sReadConstraintSettings
  BODYOF_SREAD_FROM_FILE_REF(ConstraintSettings)

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamIn
 * Method:    sReadConstraintSettingsFromStream
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamIn_sReadConstraintSettingsFromStream
  BODYOF_SREAD_FROM_STREAM_REF(ConstraintSettings)

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamIn
 * Method:    sReadControllerSettingsFromStream
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamIn_sReadControllerSettingsFromStream
  BODYOF_SREAD_FROM_STREAM_REF(VehicleControllerSettings)

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamIn
 * Method:    sReadGroupFilterTable
 * Signature: (Ljava/lang/String;J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamIn_sReadGroupFilterTable
  BODYOF_SREAD_FROM_FILE_REF(GroupFilterTable)

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamIn
 * Method:    sReadGroupFilterTableFromStream
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamIn_sReadGroupFilterTableFromStream
  BODYOF_SREAD_FROM_STREAM_REF(GroupFilterTable)

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamIn
 * Method:    sReadPhysicsMaterial
 * Signature: (Ljava/lang/String;J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamIn_sReadPhysicsMaterial
  BODYOF_SREAD_FROM_FILE_REF(PhysicsMaterial)

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamIn
 * Method:    sReadPhysicsMaterialFromStream
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamIn_sReadPhysicsMaterialFromStream
  BODYOF_SREAD_FROM_STREAM_REF(PhysicsMaterial)

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamIn
 * Method:    sReadPhysicsScene
 * Signature: (Ljava/lang/String;J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamIn_sReadPhysicsScene
  BODYOF_SREAD_FROM_FILE_REF(PhysicsScene)

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamIn
 * Method:    sReadPhysicsSceneFromStream
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamIn_sReadPhysicsSceneFromStream
  BODYOF_SREAD_FROM_STREAM_REF(PhysicsScene)

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamIn
 * Method:    sReadRagdollSettings
 * Signature: (Ljava/lang/String;J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamIn_sReadRagdollSettings
  BODYOF_SREAD_FROM_FILE_REF(RagdollSettings)

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamIn
 * Method:    sReadRagdollSettingsFromStream
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamIn_sReadRagdollSettingsFromStream
  BODYOF_SREAD_FROM_STREAM_REF(RagdollSettings)

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamIn
 * Method:    sReadSbcsFromStream
 * Signature: (J[J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamIn_sReadSbcsFromStream
  BODYOF_SREAD_FROM_STREAM_NONREF(SoftBodyCreationSettings)

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamIn
 * Method:    sReadSbssFromStream
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamIn_sReadSbssFromStream
  BODYOF_SREAD_FROM_STREAM_REF(SoftBodySharedSettings)

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamIn
 * Method:    sReadShapeSettingsFromStream
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamIn_sReadShapeSettingsFromStream
  BODYOF_SREAD_FROM_STREAM_REF(ShapeSettings)

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamIn
 * Method:    sReadSkeletalAnimation
 * Signature: (Ljava/lang/String;J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamIn_sReadSkeletalAnimation
  BODYOF_SREAD_FROM_FILE_REF(SkeletalAnimation)

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamIn
 * Method:    sReadSkeleton
 * Signature: (Ljava/lang/String;J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamIn_sReadSkeleton
  BODYOF_SREAD_FROM_FILE_REF(Skeleton)

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamIn
 * Method:    sReadVehicleControllerSettings
 * Signature: (Ljava/lang/String;J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamIn_sReadVehicleControllerSettings
  BODYOF_SREAD_FROM_FILE_REF(VehicleControllerSettings)