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
#include <Jolt/Physics/Collision/Shape/HeightFieldShape.h>
#include "auto/com_github_stephengold_joltjni_HeightFieldShape.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_HeightFieldShape
 * Method:    getBlockSize
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_HeightFieldShape_getBlockSize
  (JNIEnv *, jclass, jlong shapeVa) {
    const HeightFieldShape * const pShape
            = reinterpret_cast<HeightFieldShape *> (shapeVa);
    const uint result = pShape->GetBlockSize();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HeightFieldShape
 * Method:    getPositionX
 * Signature: (JII)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HeightFieldShape_getPositionX
  (JNIEnv *, jclass, jlong shapeVa, jint x, jint y) {
    const HeightFieldShape * const pShape
            = reinterpret_cast<HeightFieldShape *> (shapeVa);
    const float result = pShape->GetPosition(x, y).GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HeightFieldShape
 * Method:    getPositionY
 * Signature: (JII)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HeightFieldShape_getPositionY
  (JNIEnv *, jclass, jlong shapeVa, jint x, jint y) {
    const HeightFieldShape * const pShape
            = reinterpret_cast<HeightFieldShape *> (shapeVa);
    const float result = pShape->GetPosition(x, y).GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HeightFieldShape
 * Method:    getPositionZ
 * Signature: (JII)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_HeightFieldShape_getPositionZ
  (JNIEnv *, jclass, jlong shapeVa, jint x, jint y) {
    const HeightFieldShape * const pShape
            = reinterpret_cast<HeightFieldShape *> (shapeVa);
    const float result = pShape->GetPosition(x, y).GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_HeightFieldShape
 * Method:    isNoCollision
 * Signature: (JII)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_HeightFieldShape_isNoCollision
  (JNIEnv *, jclass, jlong shapeVa, jint x, jint y) {
    const HeightFieldShape * const pShape
            = reinterpret_cast<HeightFieldShape *> (shapeVa);
    const bool result = pShape->IsNoCollision(x, y);
    return result;
}