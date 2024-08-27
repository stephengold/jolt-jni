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
#include <Jolt/Physics/Collision/Shape/RotatedTranslatedShape.h>
#include "auto/com_github_stephengold_joltjni_RotatedTranslatedShape.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_RotatedTranslatedShape
 * Method:    createRotatedTranslatedShape
 * Signature: (FFFFFFFJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_RotatedTranslatedShape_createRotatedTranslatedShape
  (JNIEnv *, jclass, jfloat offsetX, jfloat offsetY, jfloat offsetZ,
  jfloat rotX, jfloat rotY, jfloat rotZ, jfloat rotW, jlong baseShapeVa) {
    const Vec3 offset(offsetX, offsetY, offsetZ);
    const Quat rotation(rotX, rotY, rotZ, rotW);
    const Shape * const pBase = reinterpret_cast<Shape *> (baseShapeVa);
    RotatedTranslatedShape * const pResult
            = new RotatedTranslatedShape(offset, rotation, pBase);
    TRACE_NEW("RotatedTranslatedShape", pResult)
    return reinterpret_cast<jlong> (pResult);
}

inline static const Vec3 getPosition(jlong rtsVa) {
    const RotatedTranslatedShape * const pShape
            = reinterpret_cast<RotatedTranslatedShape *> (rtsVa);
    const Vec3 result = pShape->GetPosition();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RotatedTranslatedShape
 * Method:    getPositionX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_RotatedTranslatedShape_getPositionX
  (JNIEnv *, jclass, jlong rtsVa) {
    const Vec3 position = getPosition(rtsVa);
    const float result = position.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RotatedTranslatedShape
 * Method:    getPositionY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_RotatedTranslatedShape_getPositionY
  (JNIEnv *, jclass, jlong rtsVa) {
    const Vec3 position = getPosition(rtsVa);
    const float result = position.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RotatedTranslatedShape
 * Method:    getPositionZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_RotatedTranslatedShape_getPositionZ
  (JNIEnv *, jclass, jlong rtsVa) {
    const Vec3 position = getPosition(rtsVa);
    const float result = position.GetZ();
    return result;
}

inline static const Quat getRotation(jlong rtsVa) {
    const RotatedTranslatedShape * const pShape
            = reinterpret_cast<RotatedTranslatedShape *> (rtsVa);
    const Quat result = pShape->GetRotation();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RotatedTranslatedShape
 * Method:    getRotationW
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_RotatedTranslatedShape_getRotationW
  (JNIEnv *, jclass, jlong rtsVa) {
    const Quat rotation = getRotation(rtsVa);
    const float result = rotation.GetW();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RotatedTranslatedShape
 * Method:    getRotationX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_RotatedTranslatedShape_getRotationX
  (JNIEnv *, jclass, jlong rtsVa) {
    const Quat rotation = getRotation(rtsVa);
    const float result = rotation.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RotatedTranslatedShape
 * Method:    getRotationY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_RotatedTranslatedShape_getRotationY
  (JNIEnv *, jclass, jlong rtsVa) {
    const Quat rotation = getRotation(rtsVa);
    const float result = rotation.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RotatedTranslatedShape
 * Method:    getRotationZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_RotatedTranslatedShape_getRotationZ
  (JNIEnv *, jclass, jlong rtsVa) {
    const Quat rotation = getRotation(rtsVa);
    const float result = rotation.GetZ();
    return result;
}