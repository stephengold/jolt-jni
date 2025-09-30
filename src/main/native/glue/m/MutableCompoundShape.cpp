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
#include "Jolt/Physics/Collision/Shape/MutableCompoundShape.h"
#include "auto/com_github_stephengold_joltjni_MutableCompoundShape.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_MutableCompoundShape
 * Method:    addShape
 * Signature: (JFFFFFFFJ)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_MutableCompoundShape_addShape
  (JNIEnv *, jclass, jlong compoundVa, jfloat offsetX, jfloat offsetY, jfloat offsetZ,
  jfloat rotX, jfloat rotY, jfloat rotZ, jfloat rotW, jlong subShapeVa) {
    MutableCompoundShape * const pCompound
            = reinterpret_cast<MutableCompoundShape *> (compoundVa);
    const Vec3 offset(offsetX, offsetY, offsetZ);
    const Quat rotation(rotX, rotY, rotZ, rotW);
    const Shape * const pSubShape = reinterpret_cast<Shape *> (subShapeVa);
    const uint result = pCompound->AddShape(offset, rotation, pSubShape);
    uint64 revisionCount = pCompound->GetUserData();
    ++revisionCount;
    pCompound->SetUserData(revisionCount);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MutableCompoundShape
 * Method:    adjustCenterOfMass
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MutableCompoundShape_adjustCenterOfMass
  (JNIEnv *, jclass, jlong shapeVa) {
    MutableCompoundShape * const pCompound
            = reinterpret_cast<MutableCompoundShape *> (shapeVa);
    pCompound->AdjustCenterOfMass();
    uint64 revisionCount = pCompound->GetUserData();
    ++revisionCount;
    pCompound->SetUserData(revisionCount);
}

/*
 * Class:     com_github_stephengold_joltjni_MutableCompoundShape
 * Method:    createMutableCompoundShape
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_MutableCompoundShape_createMutableCompoundShape
  (JNIEnv *, jclass) {
    MutableCompoundShape * const pCompound = new MutableCompoundShape();
    TRACE_NEW_TARGET("MutableCompoundShape", pCompound)
    return reinterpret_cast<jlong> (pCompound);
}

/*
 * Class:     com_github_stephengold_joltjni_MutableCompoundShape
 * Method:    modifyShapes
 * Signature: (JIILjava/nio/ByteBuffer;Ljava/nio/ByteBuffer;II)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MutableCompoundShape_modifyShapes
  (JNIEnv *pEnv, jclass, jlong shapeVa, jint startIndex, jint numSubShapes,
  jobject offsets, jobject rotations, jint offsetStride, jint rotationStride) {
    MutableCompoundShape * const pCompound
            = reinterpret_cast<MutableCompoundShape *> (shapeVa);
    const Vec3 * const pOffsets
            = (Vec3 *) pEnv->GetDirectBufferAddress(offsets);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const Quat * const pRotations
            = (Quat *) pEnv->GetDirectBufferAddress(rotations);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    pCompound->ModifyShapes(startIndex, numSubShapes, pOffsets, pRotations,
            offsetStride, rotationStride);
    uint64 revisionCount = pCompound->GetUserData();
    ++revisionCount;
    pCompound->SetUserData(revisionCount);
}

/*
 * Class:     com_github_stephengold_joltjni_MutableCompoundShape
 * Method:    removeShape
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MutableCompoundShape_removeShape
  (JNIEnv *, jclass, jlong shapeVa, jint index) {
    MutableCompoundShape * const pCompound
            = reinterpret_cast<MutableCompoundShape *> (shapeVa);
    pCompound->RemoveShape(index);
    uint64 revisionCount = pCompound->GetUserData();
    ++revisionCount;
    pCompound->SetUserData(revisionCount);
}