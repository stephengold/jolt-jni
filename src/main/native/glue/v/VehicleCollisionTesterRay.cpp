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
#include "Jolt/Physics/Vehicle/VehicleCollisionTester.h"

#include "auto/com_github_stephengold_joltjni_VehicleCollisionTesterRay.h"
#include "auto/com_github_stephengold_joltjni_VehicleCollisionTesterRayRef.h"
#include "glue/glue.h"

using namespace JPH;

IMPLEMENT_REF(VehicleCollisionTesterRay,
  Java_com_github_stephengold_joltjni_VehicleCollisionTesterRayRef_copy,
  Java_com_github_stephengold_joltjni_VehicleCollisionTesterRayRef_createEmpty,
  Java_com_github_stephengold_joltjni_VehicleCollisionTesterRayRef_free,
  Java_com_github_stephengold_joltjni_VehicleCollisionTesterRayRef_getPtr)

/*
 * Class:     com_github_stephengold_joltjni_VehicleCollisionTesterRay
 * Method:    createTester
 * Signature: (IFFFF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_VehicleCollisionTesterRay_createTester
  (JNIEnv *, jclass, jint layer, jfloat ux, jfloat uy, jfloat uz, jfloat maxSlope) {
    const ObjectLayer objLayer = (ObjectLayer) layer;
    const Vec3 up(ux, uy, uz);
    VehicleCollisionTesterRay * const pTester
            = new VehicleCollisionTesterRay(objLayer, up, maxSlope);
    return reinterpret_cast<jlong> (pTester);
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleCollisionTesterRay
 * Method:    getRefCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_VehicleCollisionTesterRay_getRefCount
  (JNIEnv *, jclass, jlong testerVa) {
    const VehicleCollisionTesterRay * const pTester
            = reinterpret_cast<VehicleCollisionTesterRay *> (testerVa);
    const uint32 result = pTester->GetRefCount();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleCollisionTesterRay
 * Method:    setEmbedded
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleCollisionTesterRay_setEmbedded
  (JNIEnv *, jclass, jlong testerVa) {
    VehicleCollisionTesterRay * const pTester
            = reinterpret_cast<VehicleCollisionTesterRay *> (testerVa);
    pTester->SetEmbedded();
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleCollisionTesterRay
 * Method:    toRef
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_VehicleCollisionTesterRay_toRef
  (JNIEnv *, jclass, jlong testerVa) {
    VehicleCollisionTesterRay * const pTester
            = reinterpret_cast<VehicleCollisionTesterRay *> (testerVa);
    Ref<VehicleCollisionTesterRay> * const pResult
            = new Ref<VehicleCollisionTesterRay>(pTester);
    TRACE_NEW("Ref<VehicleCollisionTesterRay>", pResult)
    return reinterpret_cast<jlong> (pResult);
}
