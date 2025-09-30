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

#include "auto/com_github_stephengold_joltjni_VehicleCollisionTesterRay.h"
#include "auto/com_github_stephengold_joltjni_VehicleCollisionTesterRayRef.h"
#include "glue/glue.h"

using namespace JPH;

IMPLEMENT_REF(VehicleCollisionTesterRay,
  Java_com_github_stephengold_joltjni_VehicleCollisionTesterRayRef_copy,
  Java_com_github_stephengold_joltjni_VehicleCollisionTesterRayRef_createDefault,
  Java_com_github_stephengold_joltjni_VehicleCollisionTesterRayRef_free,
  Java_com_github_stephengold_joltjni_VehicleCollisionTesterRayRef_getPtr,
  Java_com_github_stephengold_joltjni_VehicleCollisionTesterRayRef_toRefC)

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
    TRACE_NEW_TARGET("VehicleCollisionTesterRay", pTester)
    return reinterpret_cast<jlong> (pTester);
}