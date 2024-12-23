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
#include "Jolt/Physics/Collision/Shape/BoxShape.h"
#include "auto/com_github_stephengold_joltjni_BoxShape.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_BoxShape
 * Method:    createBoxShape
 * Signature: (FFFFJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_BoxShape_createBoxShape
  (JNIEnv *, jclass, jfloat xHalfExtent, jfloat yHalfExtent, jfloat zHalfExtent,
  jfloat convexRadius, jlong materialVa) {
    const Vec3 halfExtents(xHalfExtent, yHalfExtent, zHalfExtent);
    const PhysicsMaterial * const pMaterial
            = reinterpret_cast<PhysicsMaterial *> (materialVa);
    BoxShape * const pResult
            = new BoxShape(halfExtents, convexRadius, pMaterial);
    TRACE_NEW("BoxShape", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_BoxShape
 * Method:    getConvexRadius
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BoxShape_getConvexRadius
  (JNIEnv *, jclass, jlong shapeVa) {
    const BoxShape * const pShape = reinterpret_cast<BoxShape *> (shapeVa);
    const float result = pShape->GetConvexRadius();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BoxShape
 * Method:    getHalfExtentX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BoxShape_getHalfExtentX
  (JNIEnv *, jclass, jlong shapeVa) {
    const BoxShape * const pShape = reinterpret_cast<BoxShape *> (shapeVa);
    const float result = pShape->GetHalfExtent().GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BoxShape
 * Method:    getHalfExtentY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BoxShape_getHalfExtentY
  (JNIEnv *, jclass, jlong shapeVa) {
    const BoxShape * const pShape = reinterpret_cast<BoxShape *> (shapeVa);
    const float result = pShape->GetHalfExtent().GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_BoxShape
 * Method:    getHalfExtentZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_BoxShape_getHalfExtentZ
  (JNIEnv *, jclass, jlong shapeVa) {
    const BoxShape * const pShape = reinterpret_cast<BoxShape *> (shapeVa);
    const float result = pShape->GetHalfExtent().GetZ();
    return result;
}