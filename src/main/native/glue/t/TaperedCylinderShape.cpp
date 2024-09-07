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
#include <Jolt/Jolt.h>
#include <Jolt/Physics/Collision/Shape/TaperedCylinderShape.h>
#include "auto/com_github_stephengold_joltjni_TaperedCylinderShape.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_TaperedCylinderShape
 * Method:    getBottomRadius
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_TaperedCylinderShape_getBottomRadius
  (JNIEnv *, jclass, jlong shapeVa) {
    const TaperedCylinderShape * const pShape
            = reinterpret_cast<TaperedCylinderShape *> (shapeVa);
    const float result = pShape->GetBottomRadius();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_TaperedCylinderShape
 * Method:    getConvexRadius
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_TaperedCylinderShape_getConvexRadius
  (JNIEnv *, jclass, jlong shapeVa) {
    const TaperedCylinderShape * const pShape
            = reinterpret_cast<TaperedCylinderShape *> (shapeVa);
    const float result = pShape->GetConvexRadius();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_TaperedCylinderShape
 * Method:    getHalfHeight
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_TaperedCylinderShape_getHalfHeight
  (JNIEnv *, jclass, jlong shapeVa) {
    const TaperedCylinderShape * const pShape
            = reinterpret_cast<TaperedCylinderShape *> (shapeVa);
    const float result = pShape->GetHalfHeight();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_TaperedCylinderShape
 * Method:    getTopRadius
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_TaperedCylinderShape_getTopRadius
  (JNIEnv *, jclass, jlong shapeVa) {
    const TaperedCylinderShape * const pShape
            = reinterpret_cast<TaperedCylinderShape *> (shapeVa);
    const float result = pShape->GetTopRadius();
    return result;
}