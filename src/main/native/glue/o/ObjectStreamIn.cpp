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
#include "Jolt/ObjectStream/ObjectStreamIn.h"
#include "Jolt/Physics/PhysicsScene.h"
#include "Jolt/Physics/Ragdoll/Ragdoll.h"
#include "Jolt/Skeleton/SkeletalAnimation.h"

#include "auto/com_github_stephengold_joltjni_ObjectStreamIn.h"

using namespace JPH;

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
    return result;
}