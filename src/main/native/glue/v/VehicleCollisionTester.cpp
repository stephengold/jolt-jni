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
#include "Jolt/Physics/Vehicle/VehicleCollisionTester.h"

#include "auto/com_github_stephengold_joltjni_VehicleCollisionTester.h"
#include "auto/com_github_stephengold_joltjni_VehicleCollisionTesterRef.h"
#include "glue/glue.h"

using namespace JPH;

IMPLEMENT_REF(VehicleCollisionTester,
  Java_com_github_stephengold_joltjni_VehicleCollisionTesterRef_copy,
  Java_com_github_stephengold_joltjni_VehicleCollisionTesterRef_createDefault,
  Java_com_github_stephengold_joltjni_VehicleCollisionTesterRef_free,
  Java_com_github_stephengold_joltjni_VehicleCollisionTesterRef_getPtr,
  Java_com_github_stephengold_joltjni_VehicleCollisionTesterRef_toRefC)

/*
 * Class:     com_github_stephengold_joltjni_VehicleCollisionTester
 * Method:    getRefCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_VehicleCollisionTester_getRefCount
  (JNIEnv *, jclass, jlong testerVa) {
    const VehicleCollisionTester * const pTester
            = reinterpret_cast<VehicleCollisionTester *> (testerVa);
    const uint32 result = pTester->GetRefCount();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleCollisionTester
 * Method:    setEmbedded
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleCollisionTester_setEmbedded
  (JNIEnv *, jclass, jlong testerVa) {
    VehicleCollisionTester * const pTester
            = reinterpret_cast<VehicleCollisionTester *> (testerVa);
    pTester->SetEmbedded();
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleCollisionTester
 * Method:    setObjectLayer
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleCollisionTester_setObjectLayer
  (JNIEnv *, jclass, jlong testerVa, jint objectLayer) {
    VehicleCollisionTester * const pTester
            = reinterpret_cast<VehicleCollisionTester *> (testerVa);
    pTester->SetObjectLayer(objectLayer);
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleCollisionTester
 * Method:    toRef
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_VehicleCollisionTester_toRef
  (JNIEnv *, jclass, jlong testerVa) {
    VehicleCollisionTester * const pTester
            = reinterpret_cast<VehicleCollisionTester *> (testerVa);
    Ref<VehicleCollisionTester> * const pResult
            = new Ref<VehicleCollisionTester>(pTester);
    TRACE_NEW("Ref<VehicleCollisionTester>", pResult)
    return reinterpret_cast<jlong> (pResult);
}