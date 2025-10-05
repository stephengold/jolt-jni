/*
Copyright (c) 2024-2025 Stephen Gold

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
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_SubShape
 * Method:    getLocalTransformNoScale
 * Signature: (JFFF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SubShape_getLocalTransformNoScale
  (JNIEnv *, jclass, jlong subShapeVa, jfloat sx, jfloat sy, jfloat sz) {
    const CompoundShape::SubShape * const pSubShape
            = reinterpret_cast<CompoundShape::SubShape *> (subShapeVa);
    const Vec3 scale(sx, sy, sz);
    Mat44 * const pResult = new Mat44();
    TRACE_NEW("Mat44", pResult)
    *pResult = pSubShape->GetLocalTransformNoScale(scale);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SubShape
 * Method:    getPositionCom
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SubShape_getPositionCom
  (JNIEnv *pEnv, jclass, jlong subShapeVa, jobject storeFloats) {
    const CompoundShape::SubShape * const pSubShape
            = reinterpret_cast<CompoundShape::SubShape *> (subShapeVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3 result = pSubShape->GetPositionCOM();
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_SubShape
 * Method:    getRotation
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SubShape_getRotation
  (JNIEnv *pEnv, jclass, jlong subShapeVa, jobject storeFloats) {
    const CompoundShape::SubShape * const pSubShape
            = reinterpret_cast<CompoundShape::SubShape *> (subShapeVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 4);
    const Quat result = pSubShape->GetRotation();
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
    pFloats[3] = result.GetW();
}

/*
 * Class:     com_github_stephengold_joltjni_SubShape
 * Method:    getShape
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SubShape_getShape
  (JNIEnv *, jclass, jlong subShapeVa) {
    const CompoundShape::SubShape * const pSubShape
            = reinterpret_cast<CompoundShape::SubShape *> (subShapeVa);
    const RefConst<Shape> ref = pSubShape->mShape;
    const Shape *pResult = ref.GetPtr();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SubShape
 * Method:    isValidScale
 * Signature: (JFFF)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_SubShape_isValidScale
  (JNIEnv *, jclass, jlong subShapeVa, jfloat sx, jfloat sy, jfloat sz) {
    const CompoundShape::SubShape * const pSubShape
            = reinterpret_cast<CompoundShape::SubShape *> (subShapeVa);
    const Vec3 scale(sx, sy, sz);
    const bool result = pSubShape->IsValidScale(scale);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SubShape
 * Method:    setPositionCom
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SubShape_setPositionCom
  (JNIEnv *, jclass, jlong subShapeVa, jfloat x, jfloat y, jfloat z) {
    CompoundShape::SubShape * const pSubShape
            = reinterpret_cast<CompoundShape::SubShape *> (subShapeVa);
    const Vec3 location(x, y, z);
    pSubShape->SetPositionCOM(location);
}

/*
 * Class:     com_github_stephengold_joltjni_SubShape
 * Method:    setRotation
 * Signature: (JFFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SubShape_setRotation
  (JNIEnv *, jclass, jlong subShapeVa, jfloat qx, jfloat qy, jfloat qz, jfloat qw) {
    CompoundShape::SubShape * const pSubShape
            = reinterpret_cast<CompoundShape::SubShape *> (subShapeVa);
    const Quat rotation(qx, qy, qz, qw);
    pSubShape->SetRotation(rotation);
}

/*
 * Class:     com_github_stephengold_joltjni_SubShape
 * Method:    setTransform
 * Signature: (JFFFFFFFFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SubShape_setTransform
  (JNIEnv *, jclass, jlong subShapeVa, jfloat ox, jfloat oy, jfloat oz,
  jfloat qx, jfloat qy, jfloat qz, jfloat qw, jfloat cx, jfloat cy, jfloat cz) {
    CompoundShape::SubShape * const pSubShape
            = reinterpret_cast<CompoundShape::SubShape *> (subShapeVa);
    const Vec3 offset(ox, oy, oz);
    const Quat rotation(qx, qy, qz, qw);
    const Vec3 com(cx, cy, cz);
    pSubShape->SetTransform(offset, rotation, com);
}

/*
 * Class:     com_github_stephengold_joltjni_SubShape
 * Method:    transformScale
 * Signature: (JFFF[F)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SubShape_transformScale
  (JNIEnv *pEnv, jclass, jlong subShapeVa, jfloat sx, jfloat sy, jfloat sz,
  jfloatArray storeFloats) {
    CompoundShape::SubShape * const pSubShape
            = reinterpret_cast<CompoundShape::SubShape *> (subShapeVa);
    const Vec3 scale(sx, sy, sz);
    Vec3 result = pSubShape->TransformScale(scale);
    jboolean isCopy;
    jfloat * const pStoreFloats
            = pEnv->GetFloatArrayElements(storeFloats, &isCopy);
    pStoreFloats[0] = result.GetX();
    pStoreFloats[1] = result.GetY();
    pStoreFloats[2] = result.GetZ();
    pEnv->ReleaseFloatArrayElements(storeFloats, pStoreFloats, 0);
}