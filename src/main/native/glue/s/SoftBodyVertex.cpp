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
#include "Jolt/Physics/SoftBody/SoftBodyVertex.h"
#include "auto/com_github_stephengold_joltjni_SoftBodyVertex.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyVertex
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SoftBodyVertex_createDefault
    BODYOF_CREATE_DEFAULT(SoftBodyVertex)

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyVertex
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodyVertex_free
    BODYOF_FREE(SoftBodyVertex)

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyVertex
 * Method:    getCollidingShapeIndex
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SoftBodyVertex_getCollidingShapeIndex
  (JNIEnv *, jclass, jlong vertexVa) {
    const SoftBodyVertex * const pVertex
            = reinterpret_cast<SoftBodyVertex *> (vertexVa);
    const int result = pVertex->mCollidingShapeIndex;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyVertex
 * Method:    getCollisionPlane
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodyVertex_getCollisionPlane
  (JNIEnv *pEnv, jclass, jlong vertexVa, jobject storeFloats) {
    const SoftBodyVertex * const pVertex
            = reinterpret_cast<SoftBodyVertex *> (vertexVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 4);
    const Plane& result = pVertex->mCollisionPlane;
    const Vec3 normal = result.GetNormal();
    pFloats[0] = normal.GetX();
    pFloats[1] = normal.GetY();
    pFloats[2] = normal.GetZ();
    pFloats[3] = result.GetConstant();
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyVertex
 * Method:    getInvMass
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SoftBodyVertex_getInvMass
  (JNIEnv *, jclass, jlong vertexVa) {
    const SoftBodyVertex * const pVertex
            = reinterpret_cast<SoftBodyVertex *> (vertexVa);
    const float result = pVertex->mInvMass;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyVertex
 * Method:    getLargestPenetration
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SoftBodyVertex_getLargestPenetration
  (JNIEnv *, jclass, jlong vertexVa) {
    const SoftBodyVertex * const pVertex
            = reinterpret_cast<SoftBodyVertex *> (vertexVa);
    const float result = pVertex->mLargestPenetration;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyVertex
 * Method:    getPositionX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SoftBodyVertex_getPositionX
  (JNIEnv *, jclass, jlong vertexVa) {
    const SoftBodyVertex * const pVertex
            = reinterpret_cast<SoftBodyVertex *> (vertexVa);
    const float result = pVertex->mPosition.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyVertex
 * Method:    getPositionY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SoftBodyVertex_getPositionY
  (JNIEnv *, jclass, jlong vertexVa) {
    const SoftBodyVertex * const pVertex
            = reinterpret_cast<SoftBodyVertex *> (vertexVa);
    const float result = pVertex->mPosition.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyVertex
 * Method:    getPositionZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SoftBodyVertex_getPositionZ
  (JNIEnv *, jclass, jlong vertexVa) {
    const SoftBodyVertex * const pVertex
            = reinterpret_cast<SoftBodyVertex *> (vertexVa);
    const float result = pVertex->mPosition.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyVertex
 * Method:    getVelocityX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SoftBodyVertex_getVelocityX
  (JNIEnv *, jclass, jlong vertexVa) {
    const SoftBodyVertex * const pVertex
            = reinterpret_cast<SoftBodyVertex *> (vertexVa);
    const float result = pVertex->mVelocity.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyVertex
 * Method:    getVelocityY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SoftBodyVertex_getVelocityY
  (JNIEnv *, jclass, jlong vertexVa) {
    const SoftBodyVertex * const pVertex
            = reinterpret_cast<SoftBodyVertex *> (vertexVa);
    const float result = pVertex->mVelocity.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyVertex
 * Method:    getVelocityZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SoftBodyVertex_getVelocityZ
  (JNIEnv *, jclass, jlong vertexVa) {
    const SoftBodyVertex * const pVertex
            = reinterpret_cast<SoftBodyVertex *> (vertexVa);
    const float result = pVertex->mVelocity.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyVertex
 * Method:    hasContact
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_SoftBodyVertex_hasContact
  (JNIEnv *, jclass, jlong vertexVa) {
    const SoftBodyVertex * const pVertex
            = reinterpret_cast<SoftBodyVertex *> (vertexVa);
    const bool result = pVertex->mHasContact;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyVertex
 * Method:    resetCollision
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodyVertex_resetCollision
  (JNIEnv *, jclass, jlong vertexVa) {
    SoftBodyVertex * const pVertex
            = reinterpret_cast<SoftBodyVertex *> (vertexVa);
    pVertex->ResetCollision();
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyVertex
 * Method:    setCollidingShapeIndex
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodyVertex_setCollidingShapeIndex
  (JNIEnv *, jclass, jlong vertexVa, jint index) {
    SoftBodyVertex * const pVertex
            = reinterpret_cast<SoftBodyVertex *> (vertexVa);
    pVertex->mCollidingShapeIndex = index;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyVertex
 * Method:    setCollisionPlane
 * Signature: (JFFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodyVertex_setCollisionPlane
  (JNIEnv *, jclass, jlong vertexVa, jfloat nx, jfloat ny, jfloat nz, jfloat c) {
    SoftBodyVertex * const pVertex
            = reinterpret_cast<SoftBodyVertex *> (vertexVa);
    const Vec3 normal(nx, ny, nz);
    pVertex->mCollisionPlane = Plane(normal, c);
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyVertex
 * Method:    setInvMass
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodyVertex_setInvMass
  (JNIEnv *, jclass, jlong vertexVa, float invMass) {
    SoftBodyVertex * const pVertex
            = reinterpret_cast<SoftBodyVertex *> (vertexVa);
    pVertex->mInvMass = invMass;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyVertex
 * Method:    setLargestPenetration
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodyVertex_setLargestPenetration
  (JNIEnv *, jclass, jlong vertexVa, jfloat penetration) {
    SoftBodyVertex * const pVertex
            = reinterpret_cast<SoftBodyVertex *> (vertexVa);
    pVertex->mLargestPenetration = penetration;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyVertex
 * Method:    setPosition
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodyVertex_setPosition
  (JNIEnv *, jclass, jlong vertexVa, jfloat x, jfloat y, jfloat z) {
    SoftBodyVertex * const pVertex
            = reinterpret_cast<SoftBodyVertex *> (vertexVa);
    const Vec3 location(x, y, z);
    pVertex->mPosition = location;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyVertex
 * Method:    setVelocity
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodyVertex_setVelocity
  (JNIEnv *, jclass, jlong vertexVa, jfloat vx, jfloat vy, jfloat vz) {
    SoftBodyVertex * const pVertex
            = reinterpret_cast<SoftBodyVertex *> (vertexVa);
    const Vec3 velocity(vx, vy, vz);
    pVertex->mVelocity = velocity;
}