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
#include "Jolt/Physics/Collision/Shape/PlaneShape.h"
#include "auto/com_github_stephengold_joltjni_PlaneShape.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_PlaneShape
 * Method:    createShape
 * Signature: (FFFFJF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PlaneShape_createShape
  (JNIEnv *, jclass, jfloat nx, jfloat ny, jfloat nz, jfloat planeConstant,
  jlong materialVa, jfloat halfExtent) {
    const Vec3 normal(nx, ny, nz);
    const Plane plane(normal, planeConstant);
    const PhysicsMaterial * const pMaterial
            = reinterpret_cast<PhysicsMaterial *> (materialVa);
    PlaneShape * const pShape = new PlaneShape(plane, pMaterial, halfExtent);
    TRACE_NEW("PlaneShape", pShape)
    return reinterpret_cast<jlong> (pShape);
}

/*
 * Class:     com_github_stephengold_joltjni_PlaneShape
 * Method:    getPlaneConstant
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PlaneShape_getPlaneConstant
  (JNIEnv *, jclass, jlong planeVa) {
    const PlaneShape * const pShape
            = reinterpret_cast<PlaneShape *> (planeVa);
    const float result = pShape->GetPlane().GetConstant();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PlaneShape
 * Method:    getPlaneX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PlaneShape_getPlaneX
  (JNIEnv *, jclass, jlong planeVa) {
    const PlaneShape * const pShape
            = reinterpret_cast<PlaneShape *> (planeVa);
    const float result = pShape->GetPlane().GetNormal().GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PlaneShape
 * Method:    getPlaneY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PlaneShape_getPlaneY
  (JNIEnv *, jclass, jlong planeVa) {
    const PlaneShape * const pShape
            = reinterpret_cast<PlaneShape *> (planeVa);
    const float result = pShape->GetPlane().GetNormal().GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PlaneShape
 * Method:    getPlaneZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PlaneShape_getPlaneZ
  (JNIEnv *, jclass, jlong planeVa) {
    const PlaneShape * const pShape
            = reinterpret_cast<PlaneShape *> (planeVa);
    const float result = pShape->GetPlane().GetNormal().GetZ();
    return result;
}