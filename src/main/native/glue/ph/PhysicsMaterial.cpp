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
#include "Jolt/Physics/Collision/PhysicsMaterial.h"

#include "auto/com_github_stephengold_joltjni_PhysicsMaterial.h"
#include "auto/com_github_stephengold_joltjni_PhysicsMaterialRef.h"
#include "glue/glue.h"

using namespace JPH;

IMPLEMENT_REF(PhysicsMaterial,
  Java_com_github_stephengold_joltjni_PhysicsMaterialRef_copy,
  Java_com_github_stephengold_joltjni_PhysicsMaterialRef_createEmpty,
  Java_com_github_stephengold_joltjni_PhysicsMaterialRef_free,
  Java_com_github_stephengold_joltjni_PhysicsMaterialRef_getPtr)

/*
 * Class:     com_github_stephengold_joltjni_PhysicsMaterial
 * Method:    getDebugName
 * Signature: (J)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_github_stephengold_joltjni_PhysicsMaterial_getDebugName
  (JNIEnv *pEnv, jclass, jlong materialVa) {
    const PhysicsMaterial * const pMaterial
            = reinterpret_cast<PhysicsMaterial *> (materialVa);
    const char * const pName = pMaterial->GetDebugName();
    const jstring result = pEnv->NewStringUTF(pName);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsMaterial
 * Method:    getRefCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_PhysicsMaterial_getRefCount
  (JNIEnv *, jclass, jlong materialVa) {
    const PhysicsMaterial * const pMaterial
            = reinterpret_cast<PhysicsMaterial *> (materialVa);
    const uint32 result = pMaterial->GetRefCount();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsMaterial
 * Method:    sDefault
 * Signature: (Z)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PhysicsMaterial_sDefault
  (JNIEnv *, jclass, jboolean dummy) {
    const PhysicsMaterial * const pResult = PhysicsMaterial::sDefault.GetPtr();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsMaterial
 * Method:    setEmbedded
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PhysicsMaterial_setEmbedded
  (JNIEnv *, jclass, jlong materialVa) {
    PhysicsMaterial * const pMaterial
            = reinterpret_cast<PhysicsMaterial *> (materialVa);
    pMaterial->SetEmbedded();
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsMaterial
 * Method:    toRef
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PhysicsMaterial_toRef
  (JNIEnv *, jclass, jlong materialVa) {
    PhysicsMaterial * const pMaterial
            = reinterpret_cast<PhysicsMaterial *> (materialVa);
    Ref<PhysicsMaterial> * const pResult = new Ref<PhysicsMaterial>(pMaterial);
    TRACE_NEW("Ref<PhysicsMaterial>", pResult)
    return reinterpret_cast<jlong> (pResult);
}