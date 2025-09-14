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
#include "Jolt/Physics/Collision/CollideShape.h"
#include "auto/com_github_stephengold_joltjni_CollideShapeResult.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_CollideShapeResult
 * Method:    getBodyId2
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_CollideShapeResult_getBodyId2
  (JNIEnv *, jclass, jlong shapeResultVa) {
    const CollideShapeResult * const pShapeResult
            = reinterpret_cast<CollideShapeResult *> (shapeResultVa);
    const BodyID result = pShapeResult->mBodyID2;
    return result.GetIndexAndSequenceNumber();
}

/*
 * Class:     com_github_stephengold_joltjni_CollideShapeResult
 * Method:    getContactPointOn1
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CollideShapeResult_getContactPointOn1
  (JNIEnv *pEnv, jclass, jlong shapeResultVa, jobject storeFloats) {
    const CollideShapeResult * const pShapeResult
            = reinterpret_cast<CollideShapeResult *> (shapeResultVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& result = pShapeResult->mContactPointOn1;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_CollideShapeResult
 * Method:    getContactPointOn2
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CollideShapeResult_getContactPointOn2
  (JNIEnv *pEnv, jclass, jlong shapeResultVa, jobject storeFloats) {
    const CollideShapeResult * const pShapeResult
            = reinterpret_cast<CollideShapeResult *> (shapeResultVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& result = pShapeResult->mContactPointOn2;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_CollideShapeResult
 * Method:    getPenetrationAxis
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CollideShapeResult_getPenetrationAxis
  (JNIEnv *pEnv, jclass, jlong shapeResultVa, jobject storeFloats) {
    const CollideShapeResult * const pShapeResult
            = reinterpret_cast<CollideShapeResult *> (shapeResultVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& result = pShapeResult->mPenetrationAxis;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_CollideShapeResult
 * Method:    getPenetrationDepth
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CollideShapeResult_getPenetrationDepth
  (JNIEnv *, jclass, jlong shapeResultVa) {
    const CollideShapeResult * const pShapeResult
            = reinterpret_cast<CollideShapeResult *> (shapeResultVa);
    const float result = pShapeResult->mPenetrationDepth;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CollideShapeResult
 * Method:    getShape1Face
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CollideShapeResult_getShape1Face
  (JNIEnv *, jclass, jlong shapeResultVa) {
    CollideShapeResult * const pShapeResult
            = reinterpret_cast<CollideShapeResult *> (shapeResultVa);
    CollideShapeResult::Face& result = pShapeResult->mShape1Face;
    return reinterpret_cast<jlong> (&result);
}

/*
 * Class:     com_github_stephengold_joltjni_CollideShapeResult
 * Method:    getShape2Face
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CollideShapeResult_getShape2Face
  (JNIEnv *, jclass, jlong shapeResultVa) {
    CollideShapeResult * const pShapeResult
            = reinterpret_cast<CollideShapeResult *> (shapeResultVa);
    CollideShapeResult::Face& result = pShapeResult->mShape2Face;
    return reinterpret_cast<jlong> (&result);
}

/*
 * Class:     com_github_stephengold_joltjni_CollideShapeResult
 * Method:    getSubShapeId1
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_CollideShapeResult_getSubShapeId1
  (JNIEnv *, jclass, jlong shapeResultVa) {
    const CollideShapeResult * const pShapeResult
            = reinterpret_cast<CollideShapeResult *> (shapeResultVa);
    const SubShapeID result = pShapeResult->mSubShapeID1;
    return result.GetValue();
}

/*
 * Class:     com_github_stephengold_joltjni_CollideShapeResult
 * Method:    getSubShapeId2
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_CollideShapeResult_getSubShapeId2
  (JNIEnv *, jclass, jlong shapeResultVa) {
    const CollideShapeResult * const pShapeResult
            = reinterpret_cast<CollideShapeResult *> (shapeResultVa);
    const SubShapeID result = pShapeResult->mSubShapeID2;
    return result.GetValue();
}