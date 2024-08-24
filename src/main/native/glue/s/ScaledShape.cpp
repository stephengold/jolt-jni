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
#include <Jolt/Physics/Collision/Shape/ScaledShape.h>
#include "auto/com_github_stephengold_joltjni_ScaledShape.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_ScaledShape
 * Method:    createScaledShape
 * Signature: (JFFF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ScaledShape_createScaledShape
  (JNIEnv *, jclass, jlong baseShapeVa, jfloat x, jfloat y, jfloat z) {
    const Shape * const pBase = reinterpret_cast<Shape *> (baseShapeVa);
    Vec3 factors(x, y, z);
    ScaledShape * const pResult = new ScaledShape(pBase, factors);
    TRACE_NEW("ScaledShape", pResult)
    return reinterpret_cast<jlong> (pResult);
}

inline static const Vec3 getScale(jlong scaledVa) {
    const ScaledShape * const pScaled
            = reinterpret_cast<ScaledShape *> (scaledVa);
    const Vec3 result = pScaled->GetScale();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ScaledShape
 * Method:    getScaleX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ScaledShape_getScaleX
  (JNIEnv *, jclass, jlong scaledVa) {
    const Vec3 factors = getScale(scaledVa);
    const float result = factors.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ScaledShape
 * Method:    getScaleY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ScaledShape_getScaleY
  (JNIEnv *, jclass, jlong scaledVa) {
    const Vec3 factors = getScale(scaledVa);
    const float result = factors.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ScaledShape
 * Method:    getScaleZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ScaledShape_getScaleZ
  (JNIEnv *, jclass, jlong scaledVa) {
    const Vec3 factors = getScale(scaledVa);
    const float result = factors.GetZ();
    return result;
}
