/*
Copyright (c) 2024-2026 Stephen Gold

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
#include "Jolt/ObjectStream/ObjectStreamOut.h"
#include "Jolt/Physics/Hair/HairSettings.h"
#include "Jolt/Physics/PhysicsScene.h"
#include "Jolt/Physics/Ragdoll/Ragdoll.h"
#include "auto/com_github_stephengold_joltjni_ObjectStreamOut.h"

using namespace JPH;

/*
 * Pre-processor macro to generate the body of a static sWrite{T}ToFile method:
 */
#define BODYOF_SWRITE_TO_FILE(T) \
  (JNIEnv *pEnv, jclass, jstring fileName, jint ordinal, jlong tVa) { \
    jboolean isCopy; \
    const char * const pFileName = pEnv->GetStringUTFChars(fileName, &isCopy); \
    const ObjectStream::EStreamType streamType = (ObjectStream::EStreamType) ordinal; \
    const T * const pt = reinterpret_cast<T *> (tVa); \
    const bool result = ObjectStreamOut::sWriteObject(pFileName, streamType, *pt); \
    pEnv->ReleaseStringUTFChars(fileName, pFileName); \
    return result; \
}
/*
 * Pre-processor macro to generate the body of a static sWrite{T} method:
 */
#define BODYOF_SWRITE_TO_STREAM(T) \
  (JNIEnv *, jclass, jlong streamVa, jint ordinal, jlong tVa) { \
    std::stringstream * const pStream = reinterpret_cast<std::stringstream *> (streamVa); \
    const ObjectStream::EStreamType streamType = (ObjectStream::EStreamType) ordinal; \
    const T * const pt = reinterpret_cast<T *> (tVa); \
    const bool result = ObjectStreamOut::sWriteObject(*pStream, streamType, *pt); \
    return result; \
}

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamOut
 * Method:    sWriteBcs
 * Signature: (JIJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamOut_sWriteBcs
  BODYOF_SWRITE_TO_STREAM(BodyCreationSettings)

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamOut
 * Method:    sWriteBcsToFile
 * Signature: (Ljava/lang/String;IJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamOut_sWriteBcsToFile
  BODYOF_SWRITE_TO_FILE(BodyCreationSettings)

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamOut
 * Method:    sWriteHairSettings
 * Signature: (JIJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamOut_sWriteHairSettings
  BODYOF_SWRITE_TO_STREAM(HairSettings)

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamOut
 * Method:    sWriteHairSettingsToFile
 * Signature: (Ljava/lang/String;IJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamOut_sWriteHairSettingsToFile
  BODYOF_SWRITE_TO_FILE(HairSettings)

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamOut
 * Method:    sWritePhysicsScene
 * Signature: (JIJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamOut_sWritePhysicsScene
  BODYOF_SWRITE_TO_STREAM(PhysicsScene)

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamOut
 * Method:    sWritePhysicsSceneToFile
 * Signature: (Ljava/lang/String;IJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamOut_sWritePhysicsSceneToFile
  BODYOF_SWRITE_TO_FILE(PhysicsScene)

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamOut
 * Method:    sWriteRagdollSettings
 * Signature: (JIJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamOut_sWriteRagdollSettings
  BODYOF_SWRITE_TO_STREAM(RagdollSettings)

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamOut
 * Method:    sWriteRagdollSettingsToFile
 * Signature: (Ljava/lang/String;IJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamOut_sWriteRagdollSettingsToFile
  BODYOF_SWRITE_TO_FILE(RagdollSettings)

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamOut
 * Method:    sWriteSbcs
 * Signature: (JIJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamOut_sWriteSbcs
  BODYOF_SWRITE_TO_STREAM(SoftBodyCreationSettings)

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamOut
 * Method:    sWriteSbcsToFile
 * Signature: (Ljava/lang/String;IJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamOut_sWriteSbcsToFile
  BODYOF_SWRITE_TO_FILE(SoftBodyCreationSettings)

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamOut
 * Method:    sWriteSbss
 * Signature: (JIJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamOut_sWriteSbss
  BODYOF_SWRITE_TO_STREAM(SoftBodySharedSettings)

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamOut
 * Method:    sWriteSbssToFile
 * Signature: (Ljava/lang/String;IJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamOut_sWriteSbssToFile
  BODYOF_SWRITE_TO_FILE(SoftBodySharedSettings)

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamOut
 * Method:    sWriteSerializableObject
 * Signature: (JIJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamOut_sWriteSerializableObject
  BODYOF_SWRITE_TO_STREAM(SerializableObject)

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamOut
 * Method:    sWriteSerializableObjectToFile
 * Signature: (Ljava/lang/String;IJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamOut_sWriteSerializableObjectToFile
  BODYOF_SWRITE_TO_FILE(SerializableObject)