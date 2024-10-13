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
#include "Jolt/Physics/Collision/ShapeCast.h"
#include "auto/com_github_stephengold_joltjni_RShapeCast.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_RShapeCast
 * Method:    createRShapeCast
 * Signature: (JFFFJFFFJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_RShapeCast_createRShapeCast
  (JNIEnv *, jclass, jlong shapeVa, jfloat sx, jfloat sy, jfloat sz,
  jlong comStartVa, jfloat dx, jfloat dy, jfloat dz, jlong boundsVa) {
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    const Vec3 scale(sx, sy, sz);
    const RMat44 * const pStart = reinterpret_cast<RMat44 *> (comStartVa);
    const Vec3 offset(dx, dy, dz);
    const AABox * const pBounds = reinterpret_cast<AABox *> (boundsVa);
    RShapeCast * const pShapeCast
            = new RShapeCast(pShape, scale, *pStart, offset, *pBounds);
    TRACE_NEW("RShapeCast", pShapeCast)
    return reinterpret_cast<jlong> (pShapeCast);
}

/*
 * Class:     com_github_stephengold_joltjni_RShapeCast
 * Method:    createRShapeCastNoBounds
 * Signature: (JFFFJFFF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_RShapeCast_createRShapeCastNoBounds
  (JNIEnv *, jclass, jlong shapeVa, jfloat sx, jfloat sy, jfloat sz,
  jlong comStartVa, jfloat dx, jfloat dy, jfloat dz) {
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    const Vec3 scale(sx, sy, sz);
    const RMat44 * const pStart = reinterpret_cast<RMat44 *> (comStartVa);
    const Vec3 offset(dx, dy, dz);
    RShapeCast * const pShapeCast
            = new RShapeCast(pShape, scale, *pStart, offset);
    TRACE_NEW("RShapeCast", pShapeCast)
    return reinterpret_cast<jlong> (pShapeCast);
}

/*
 * Class:     com_github_stephengold_joltjni_RShapeCast
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RShapeCast_free
  (JNIEnv *, jclass, jlong castVa) {
    RShapeCast * const pShapeCast = reinterpret_cast<RShapeCast *> (castVa);
    TRACE_DELETE("RShapeCast", pShapeCast)
    delete pShapeCast;
}