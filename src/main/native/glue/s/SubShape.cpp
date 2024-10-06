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
#include "Jolt/Physics/Collision/Shape/CompoundShape.h"
#include "auto/com_github_stephengold_joltjni_SubShape.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_SubShape
 * Method:    getRotationW
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SubShape_getRotationW
  (JNIEnv *, jclass, jlong subShapeVa) {
    const CompoundShape::SubShape * const pSubShape
            = reinterpret_cast<CompoundShape::SubShape *> (subShapeVa);
    const float result = pSubShape->GetRotation().GetW();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SubShape
 * Method:    getRotationX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SubShape_getRotationX
  (JNIEnv *, jclass, jlong subShapeVa) {
    const CompoundShape::SubShape * const pSubShape
            = reinterpret_cast<CompoundShape::SubShape *> (subShapeVa);
    const float result = pSubShape->GetRotation().GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SubShape
 * Method:    getRotationY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SubShape_getRotationY
  (JNIEnv *, jclass, jlong subShapeVa) {
    const CompoundShape::SubShape * const pSubShape
            = reinterpret_cast<CompoundShape::SubShape *> (subShapeVa);
    const float result = pSubShape->GetRotation().GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SubShape
 * Method:    getRotationZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SubShape_getRotationZ
  (JNIEnv *, jclass, jlong subShapeVa) {
    const CompoundShape::SubShape * const pSubShape
            = reinterpret_cast<CompoundShape::SubShape *> (subShapeVa);
    const float result = pSubShape->GetRotation().GetZ();
    return result;
}