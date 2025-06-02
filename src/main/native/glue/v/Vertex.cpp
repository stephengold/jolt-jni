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
  (JNIEnv *, jclass, jlong vertexVa) {
    SoftBodySharedSettings::Vertex * const pVertex
            = reinterpret_cast<SoftBodySharedSettings::Vertex *> (vertexVa);
    TRACE_DELETE("SoftBodySharedSettings::Vertex", pVertex)
    delete pVertex;
}

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
 * Method:    getPositionX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Vertex_getPositionX
  (JNIEnv *, jclass, jlong vertexVa) {
    const SoftBodySharedSettings::Vertex * const pVertex
            = reinterpret_cast<SoftBodySharedSettings::Vertex *> (vertexVa);
    const float result = pVertex->mPosition.x;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Vertex
 * Method:    getPositionY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Vertex_getPositionY
  (JNIEnv *, jclass, jlong vertexVa) {
    const SoftBodySharedSettings::Vertex * const pVertex
            = reinterpret_cast<SoftBodySharedSettings::Vertex *> (vertexVa);
    const float result = pVertex->mPosition.y;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Vertex
 * Method:    getPositionZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Vertex_getPositionZ
  (JNIEnv *, jclass, jlong vertexVa) {
    const SoftBodySharedSettings::Vertex * const pVertex
            = reinterpret_cast<SoftBodySharedSettings::Vertex *> (vertexVa);
    const float result = pVertex->mPosition.z;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Vertex
 * Method:    getVelocityX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Vertex_getVelocityX
  (JNIEnv *, jclass, jlong vertexVa) {
    const SoftBodySharedSettings::Vertex * const pVertex
            = reinterpret_cast<SoftBodySharedSettings::Vertex *> (vertexVa);
    const float result = pVertex->mVelocity.x;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Vertex
 * Method:    getVelocityY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Vertex_getVelocityY
  (JNIEnv *, jclass, jlong vertexVa) {
    const SoftBodySharedSettings::Vertex * const pVertex
            = reinterpret_cast<SoftBodySharedSettings::Vertex *> (vertexVa);
    const float result = pVertex->mVelocity.y;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Vertex
 * Method:    getVelocityZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Vertex_getVelocityZ
  (JNIEnv *, jclass, jlong vertexVa) {
    const SoftBodySharedSettings::Vertex * const pVertex
            = reinterpret_cast<SoftBodySharedSettings::Vertex *> (vertexVa);
    const float result = pVertex->mVelocity.z;
    return result;
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