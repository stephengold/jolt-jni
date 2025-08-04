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
#include "Jolt/Physics/SoftBody/SoftBodySharedSettings.h"
#include "auto/com_github_stephengold_joltjni_Vertex.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_Vertex
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Vertex_createDefault
  BODYOF_CREATE_DEFAULT(SoftBodySharedSettings::Vertex)

/*
 * Class:     com_github_stephengold_joltjni_Vertex
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Vertex_free
  BODYOF_FREE(SoftBodySharedSettings::Vertex)

/*
 * Class:     com_github_stephengold_joltjni_Vertex
 * Method:    getInvMass
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Vertex_getInvMass
  (JNIEnv *, jclass, jlong vertexVa) {
    const SoftBodySharedSettings::Vertex * const pVertex
            = reinterpret_cast<SoftBodySharedSettings::Vertex *> (vertexVa);
    const float result = pVertex->mInvMass;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Vertex
 * Method:    getPosition
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Vertex_getPosition
  (JNIEnv *pEnv, jclass, jlong vertexVa, jobject storeFloats) {
    const SoftBodySharedSettings::Vertex * const pVertex
            = reinterpret_cast<SoftBodySharedSettings::Vertex *> (vertexVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Float3& result = pVertex->mPosition;
    pFloats[0] = result.x;
    pFloats[1] = result.y;
    pFloats[2] = result.z;
}

/*
 * Class:     com_github_stephengold_joltjni_Vertex
 * Method:    getVelocity
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Vertex_getVelocity
   (JNIEnv *pEnv, jclass, jlong vertexVa, jobject storeFloats) {
    const SoftBodySharedSettings::Vertex * const pVertex
            = reinterpret_cast<SoftBodySharedSettings::Vertex *> (vertexVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Float3& result = pVertex->mVelocity;
    pFloats[0] = result.x;
    pFloats[1] = result.y;
    pFloats[2] = result.z;
}

/*
 * Class:     com_github_stephengold_joltjni_Vertex
 * Method:    setInvMass
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Vertex_setInvMass
  (JNIEnv *, jclass, jlong vertexVa, jfloat invMass) {
    SoftBodySharedSettings::Vertex * const pVertex
            = reinterpret_cast<SoftBodySharedSettings::Vertex *> (vertexVa);
    pVertex->mInvMass = invMass;
}

/*
 * Class:     com_github_stephengold_joltjni_Vertex
 * Method:    setPosition
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Vertex_setPosition
  (JNIEnv *, jclass, jlong vertexVa, jfloat x, jfloat y, jfloat z) {
    SoftBodySharedSettings::Vertex * const pVertex
            = reinterpret_cast<SoftBodySharedSettings::Vertex *> (vertexVa);
    const Float3 position(x, y, z);
    pVertex->mPosition = position;
}

/*
 * Class:     com_github_stephengold_joltjni_Vertex
 * Method:    setVelocity
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Vertex_setVelocity
  (JNIEnv *, jclass, jlong vertexVa, jfloat vx, jfloat vy, jfloat vz) {
    SoftBodySharedSettings::Vertex * const pVertex
            = reinterpret_cast<SoftBodySharedSettings::Vertex *> (vertexVa);
    const Float3 velocity(vx, vy, vz);
    pVertex->mVelocity = velocity;
}