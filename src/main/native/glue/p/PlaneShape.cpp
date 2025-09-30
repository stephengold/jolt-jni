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
    TRACE_NEW_TARGET("PlaneShape", pShape)
    return reinterpret_cast<jlong> (pShape);
}

/*
 * Class:     com_github_stephengold_joltjni_PlaneShape
 * Method:    getPlane
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PlaneShape_getPlane
  (JNIEnv *pEnv, jclass, jlong shapeVa, jobject storeFloats) {
    const PlaneShape * const pShape
            = reinterpret_cast<PlaneShape *> (shapeVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 4);
    const Plane result = pShape->GetPlane();
    const Vec3 normal = result.GetNormal();
    pFloats[0] = normal.GetX();
    pFloats[1] = normal.GetY();
    pFloats[2] = normal.GetZ();
    pFloats[3] = result.GetConstant();
}