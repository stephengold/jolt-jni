/*
Copyright (c) 2026 Stephen Gold

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
#include "Jolt/Physics/Hair/HairSettings.h"
#include "auto/com_github_stephengold_joltjni_SVertex.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_SVertex
 * Method:    create
 * Signature: (FFFF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SVertex_create
  (JNIEnv *, jclass, jfloat x, jfloat y, jfloat z, jfloat invMass) {
    const Float3 location(x, y, z);
    HairSettings::SVertex * const pResult
            = new HairSettings::SVertex(location, invMass);
    TRACE_NEW("HairSettings::SVertex", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SVertex
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SVertex_createDefault
    BODYOF_CREATE_DEFAULT(HairSettings::SVertex)

/*
 * Class:     com_github_stephengold_joltjni_SVertex
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SVertex_free
    BODYOF_FREE(HairSettings::SVertex)

/*
 * Class:     com_github_stephengold_joltjni_SVertex
 * Method:    getBishop
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SVertex_getBishop
  (JNIEnv *pEnv, jclass, jlong vertexVa, jobject storeFloats) {
    const HairSettings::SVertex * const pVertex
            = reinterpret_cast<HairSettings::SVertex *> (vertexVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 4);
    pFloats[0] = pVertex->mBishop.x;
    pFloats[1] = pVertex->mBishop.y;
    pFloats[2] = pVertex->mBishop.z;
    pFloats[3] = pVertex->mBishop.w;
}

/*
 * Class:     com_github_stephengold_joltjni_SVertex
 * Method:    getInvMass
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SVertex_getInvMass
  (JNIEnv *, jclass, jlong vertexVa) {
    const HairSettings::SVertex * const pVertex
            = reinterpret_cast<HairSettings::SVertex *> (vertexVa);
    const float result = pVertex->mInvMass;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SVertex
 * Method:    getLength
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SVertex_getLength
  (JNIEnv *, jclass, jlong vertexVa) {
    const HairSettings::SVertex * const pVertex
            = reinterpret_cast<HairSettings::SVertex *> (vertexVa);
    const float result = pVertex->mLength;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SVertex
 * Method:    getOmega0
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SVertex_getOmega0
  (JNIEnv *pEnv, jclass, jlong vertexVa, jobject storeFloats) {
    const HairSettings::SVertex * const pVertex
            = reinterpret_cast<HairSettings::SVertex *> (vertexVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 4);
    pFloats[0] = pVertex->mOmega0.x;
    pFloats[1] = pVertex->mOmega0.y;
    pFloats[2] = pVertex->mOmega0.z;
    pFloats[3] = pVertex->mOmega0.w;
}

/*
 * Class:     com_github_stephengold_joltjni_SVertex
 * Method:    getPosition
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SVertex_getPosition
  (JNIEnv *pEnv, jclass, jlong vertexVa, jobject storeFloats) {
    const HairSettings::SVertex * const pVertex
            = reinterpret_cast<HairSettings::SVertex *> (vertexVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    pFloats[0] = pVertex->mPosition.x;
    pFloats[1] = pVertex->mPosition.y;
    pFloats[2] = pVertex->mPosition.z;
}

/*
 * Class:     com_github_stephengold_joltjni_SVertex
 * Method:    getStrandFraction
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SVertex_getStrandFraction
  (JNIEnv *, jclass, jlong vertexVa) {
    const HairSettings::SVertex * const pVertex
            = reinterpret_cast<HairSettings::SVertex *> (vertexVa);
    const float result = pVertex->mStrandFraction;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SVertex
 * Method:    setInvMass
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SVertex_setInvMass
  (JNIEnv *, jclass, jlong vertexVa, jfloat inverseMass) {
    HairSettings::SVertex * const pVertex
            = reinterpret_cast<HairSettings::SVertex *> (vertexVa);
    pVertex->mInvMass = inverseMass;
}

/*
 * Class:     com_github_stephengold_joltjni_SVertex
 * Method:    setPosition
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SVertex_setPosition
  (JNIEnv *, jclass, jlong vertexVa, jfloat x, jfloat y, jfloat z) {
    HairSettings::SVertex * const pVertex
            = reinterpret_cast<HairSettings::SVertex *> (vertexVa);
    const Float3 vec(x, y, z);
    pVertex->mPosition = vec;
}