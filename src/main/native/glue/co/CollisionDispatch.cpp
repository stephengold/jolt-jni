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
#include "Jolt/Physics/Collision/CollisionDispatch.h"
#include "auto/com_github_stephengold_joltjni_CollisionDispatch.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_CollisionDispatch
 * Method:    sCollideShapeVsShape
 * Signature: (JJFFFFFFJJJJJJJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CollisionDispatch_sCollideShapeVsShape
  (JNIEnv *, jclass, jlong shape1Va, jlong shape2Va, jfloat s1x, jfloat s1y,
  jfloat s1z, jfloat s2x, jfloat s2y, jfloat s2z, jlong comTransform1Va,
  jlong comTransform2Va, jlong creator1Va, jlong creator2Va, jlong settingsVa,
  jlong collectorVa, jlong filterVa) {
    const Shape * const pShape1 = reinterpret_cast<Shape *> (shape1Va);
    const Shape * const pShape2 = reinterpret_cast<Shape *> (shape2Va);
    const Vec3 scale1(s1x, s1y, s1z);
    const Vec3 scale2(s2x, s2y, s2z);
    const Mat44 * const pComTransform1
            = reinterpret_cast<Mat44 *> (comTransform1Va);
    const Mat44 * const pComTransform2
            = reinterpret_cast<Mat44 *> (comTransform2Va);
    const SubShapeIDCreator * const pCreator1
            = reinterpret_cast<SubShapeIDCreator *> (creator1Va);
    const SubShapeIDCreator * const pCreator2
            = reinterpret_cast<SubShapeIDCreator *> (creator2Va);
    const CollideShapeSettings * const pSettings
            = reinterpret_cast<CollideShapeSettings *> (settingsVa);
    CollideShapeCollector * const pCollector
            = reinterpret_cast<CollideShapeCollector *> (collectorVa);
    const ShapeFilter * const pFilter
            = reinterpret_cast<ShapeFilter *> (filterVa);
    CollisionDispatch::sCollideShapeVsShape(pShape1, pShape2, scale1, scale2,
            *pComTransform1, *pComTransform2, *pCreator1, *pCreator2,
            *pSettings, *pCollector, *pFilter);
}