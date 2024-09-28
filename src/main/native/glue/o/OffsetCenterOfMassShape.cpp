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
#include "Jolt/Physics/Collision/Shape/OffsetCenterOfMassShape.h"
#include "auto/com_github_stephengold_joltjni_OffsetCenterOfMassShape.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_OffsetCenterOfMassShape
 * Method:    createShape
 * Signature: (JFFF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_OffsetCenterOfMassShape_createShape
  (JNIEnv *, jclass, jlong baseShapeVa, jfloat offsetX, jfloat offsetY,
  jfloat offsetZ) {
    const Shape * const pBase = reinterpret_cast<Shape *> (baseShapeVa);
    const Vec3 offset(offsetX, offsetY, offsetZ);
    OffsetCenterOfMassShape * const pResult
            = new OffsetCenterOfMassShape(pBase, offset);
    TRACE_NEW("OffsetCenterOfMassShape", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_OffsetCenterOfMassShape
 * Method:    getOffsetX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_OffsetCenterOfMassShape_getOffsetX
  (JNIEnv *, jclass, jlong ocomShapeVa) {
    const OffsetCenterOfMassShape * const pShape
            = reinterpret_cast<OffsetCenterOfMassShape *> (ocomShapeVa);
    float result = pShape->GetOffset().GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_OffsetCenterOfMassShape
 * Method:    getOffsetY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_OffsetCenterOfMassShape_getOffsetY
  (JNIEnv *, jclass, jlong ocomShapeVa) {
    const OffsetCenterOfMassShape * const pShape
            = reinterpret_cast<OffsetCenterOfMassShape *> (ocomShapeVa);
    float result = pShape->GetOffset().GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_OffsetCenterOfMassShape
 * Method:    getOffsetZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_OffsetCenterOfMassShape_getOffsetZ
  (JNIEnv *, jclass, jlong ocomShapeVa) {
    const OffsetCenterOfMassShape * const pShape
            = reinterpret_cast<OffsetCenterOfMassShape *> (ocomShapeVa);
    float result = pShape->GetOffset().GetZ();
    return result;
}