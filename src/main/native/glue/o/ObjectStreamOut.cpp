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
#include "Jolt/ObjectStream/ObjectStreamOut.h"
#include "Jolt/Physics/PhysicsScene.h"
#include "Jolt/Physics/Ragdoll/Ragdoll.h"
#include "auto/com_github_stephengold_joltjni_ObjectStreamOut.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamOut
 * Method:    sWriteBcs
 * Signature: (JIJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamOut_sWriteBcs
  (JNIEnv *, jclass, jlong streamVa, jint ordinal, jlong settingsVa) {
    std::stringstream * const pStream
            = reinterpret_cast<std::stringstream *> (streamVa);
    const ObjectStream::EStreamType streamType
            = (ObjectStream::EStreamType) ordinal;
    const BodyCreationSettings * const pSettings
            = reinterpret_cast<BodyCreationSettings *> (settingsVa);
    const bool result
            = ObjectStreamOut::sWriteObject(*pStream, streamType, *pSettings);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamOut
 * Method:    sWritePhysicsScene
 * Signature: (JIJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamOut_sWritePhysicsScene
  (JNIEnv *, jclass, jlong streamVa, jint ordinal, jlong sceneVa) {
    std::stringstream * const pStream
            = reinterpret_cast<std::stringstream *> (streamVa);
    const ObjectStream::EStreamType streamType
            = (ObjectStream::EStreamType) ordinal;
    const PhysicsScene * const pScene
            = reinterpret_cast<PhysicsScene *> (sceneVa);
    const bool result
            = ObjectStreamOut::sWriteObject(*pStream, streamType, *pScene);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamOut
 * Method:    sWriteRagdollSettings
 * Signature: (JIJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamOut_sWriteRagdollSettings
  (JNIEnv *, jclass, jlong streamVa, jint ordinal, jlong settingsVa) {
    std::stringstream * const pStream
            = reinterpret_cast<std::stringstream *> (streamVa);
    const ObjectStream::EStreamType streamType
            = (ObjectStream::EStreamType) ordinal;
    const RagdollSettings * const pSettings
            = reinterpret_cast<RagdollSettings *> (settingsVa);
    const bool result
            = ObjectStreamOut::sWriteObject(*pStream, streamType, *pSettings);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamOut
 * Method:    sWriteSbcs
 * Signature: (JIJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamOut_sWriteSbcs
  (JNIEnv *, jclass, jlong streamVa, jint ordinal, jlong settingsVa) {
    std::stringstream * const pStream
            = reinterpret_cast<std::stringstream *> (streamVa);
    const ObjectStream::EStreamType streamType
            = (ObjectStream::EStreamType) ordinal;
    const SoftBodyCreationSettings * const pSettings
            = reinterpret_cast<SoftBodyCreationSettings *> (settingsVa);
    const bool result
            = ObjectStreamOut::sWriteObject(*pStream, streamType, *pSettings);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamOut
 * Method:    sWriteSbss
 * Signature: (JIJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamOut_sWriteSbss
  (JNIEnv *, jclass, jlong streamVa, jint ordinal, jlong settingsVa) {
    std::stringstream * const pStream
            = reinterpret_cast<std::stringstream *> (streamVa);
    const ObjectStream::EStreamType streamType
            = (ObjectStream::EStreamType) ordinal;
    const SoftBodySharedSettings * const pSettings
            = reinterpret_cast<SoftBodySharedSettings *> (settingsVa);
    const bool result
            = ObjectStreamOut::sWriteObject(*pStream, streamType, *pSettings);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ObjectStreamOut
 * Method:    sWriteSerializableObject
 * Signature: (JIJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ObjectStreamOut_sWriteSerializableObject
  (JNIEnv *, jclass, jlong streamVa, jint ordinal, jlong serializableObjectVa) {
    std::stringstream * const pStream
            = reinterpret_cast<std::stringstream *> (streamVa);
    const ObjectStream::EStreamType streamType
            = (ObjectStream::EStreamType) ordinal;
    const SerializableObject * const pSerializableObject
            = reinterpret_cast<SerializableObject *> (serializableObjectVa);
    const bool result
            = ObjectStreamOut::sWriteObject(*pStream, streamType, *pSerializableObject);
    return result;
}