/*
Copyright (c) 2025 Stephen Gold

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
#include "Jolt/Physics/Collision/Shape/SubShapeID.h"
#include "Jolt/Physics/Collision/SimShapeFilter.h"

#include "auto/com_github_stephengold_joltjni_SimShapeFilter.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_SimShapeFilter
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SimShapeFilter_createDefault
  BODYOF_CREATE_DEFAULT(SimShapeFilter)

/*
 * Class:     com_github_stephengold_joltjni_SimShapeFilter
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SimShapeFilter_free
  BODYOF_FREE(SimShapeFilter)

/*
 * Class:     com_github_stephengold_joltjni_SimShapeFilter
 * Method:    shouldCollide
 * Signature: (JJJIJJI)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_SimShapeFilter_shouldCollide
  (JNIEnv *, jclass, jlong filterVa, jlong body1Va, jlong shape1Va,
  jint subShapeId1, jlong body2Va, jlong shape2Va, jint subShapeId2) {
    const SimShapeFilter * const pFilter
            = reinterpret_cast<SimShapeFilter *> (filterVa);
    const Body * const pBody1 = reinterpret_cast<Body *> (body1Va);
    const Shape * const pShape1 = reinterpret_cast<Shape *> (shape1Va);
    SubShapeID id1;
    id1.SetValue(subShapeId1);
    const Body * const pBody2 = reinterpret_cast<Body *> (body2Va);
    const Shape * const pShape2 = reinterpret_cast<Shape *> (shape2Va);
    SubShapeID id2;
    id2.SetValue(subShapeId2);
    bool result = pFilter->ShouldCollide(
            *pBody1, pShape1, id1, *pBody2, pShape2, id2);
    return result;
}