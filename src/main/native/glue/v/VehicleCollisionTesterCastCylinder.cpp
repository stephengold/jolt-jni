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

#include "auto/com_github_stephengold_joltjni_VehicleCollisionTesterCastCylinder.h"
#include "auto/com_github_stephengold_joltjni_VehicleCollisionTesterCastCylinderRef.h"
#include "glue/glue.h"

using namespace JPH;

IMPLEMENT_REF(VehicleCollisionTesterCastCylinder,
  Java_com_github_stephengold_joltjni_VehicleCollisionTesterCastCylinderRef_copy,
  Java_com_github_stephengold_joltjni_VehicleCollisionTesterCastCylinderRef_createEmpty,
  Java_com_github_stephengold_joltjni_VehicleCollisionTesterCastCylinderRef_free,
  Java_com_github_stephengold_joltjni_VehicleCollisionTesterCastCylinderRef_getPtr)

/*
 * Class:     com_github_stephengold_joltjni_VehicleCollisionTesterCastCylinder
 * Method:    createTester
 * Signature: (IF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_VehicleCollisionTesterCastCylinder_createTester
  (JNIEnv *, jclass, jint layer, jfloat convexRadiusFraction) {
    const ObjectLayer objLayer = (ObjectLayer) layer;
    VehicleCollisionTesterCastCylinder * const pTester
            = new VehicleCollisionTesterCastCylinder(objLayer, convexRadiusFraction);
    TRACE_NEW("VehicleCollisionTesterCastCylinder", pTester)
    return reinterpret_cast<jlong> (pTester);
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleCollisionTesterCastCylinder
 * Method:    getRefCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_VehicleCollisionTesterCastCylinder_getRefCount
  (JNIEnv *, jclass, jlong testerVa) {
    const VehicleCollisionTesterCastCylinder * const pTester
            = reinterpret_cast<VehicleCollisionTesterCastCylinder *> (testerVa);
    const uint32 result = pTester->GetRefCount();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleCollisionTesterCastCylinder
 * Method:    setEmbedded
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VehicleCollisionTesterCastCylinder_setEmbedded
  (JNIEnv *, jclass, jlong testerVa) {
    VehicleCollisionTesterCastCylinder * const pTester
            = reinterpret_cast<VehicleCollisionTesterCastCylinder *> (testerVa);
    pTester->SetEmbedded();
}

/*
 * Class:     com_github_stephengold_joltjni_VehicleCollisionTesterCastCylinder
 * Method:    toRef
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_VehicleCollisionTesterCastCylinder_toRef
  (JNIEnv *, jclass, jlong testerVa) {
    VehicleCollisionTesterCastCylinder * const pTester
            = reinterpret_cast<VehicleCollisionTesterCastCylinder *> (testerVa);
    Ref<VehicleCollisionTesterCastCylinder> * const pResult
            = new Ref<VehicleCollisionTesterCastCylinder>(pTester);
    TRACE_NEW("Ref<VehicleCollisionTesterCastCylinder>", pResult)
    return reinterpret_cast<jlong> (pResult);
}