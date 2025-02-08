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
#include "Jolt/Physics/SoftBody/SoftBodyManifold.h"
#include "auto/com_github_stephengold_joltjni_SoftBodyManifold.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyManifold
 * Method:    countVertices
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SoftBodyManifold_countVertices
  (JNIEnv *, jclass, jlong manifoldVa) {
    const SoftBodyManifold * const pManifold
            = reinterpret_cast<SoftBodyManifold *> (manifoldVa);
    const Array<SoftBodyVertex>& vertices = pManifold->GetVertices();
    const Array<SoftBodyVertex>::size_type result = vertices.size();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyManifold
 * Method:    getContactBodyId
 * Signature: (JJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SoftBodyManifold_getContactBodyId
  (JNIEnv *, jclass, jlong manifoldVa, jlong vertexVa) {
    const SoftBodyManifold * const pManifold
            = reinterpret_cast<SoftBodyManifold *> (manifoldVa);
    const SoftBodyVertex * const pVertex
            = reinterpret_cast<SoftBodyVertex *> (vertexVa);
    const BodyID &id = pManifold->GetContactBodyID(*pVertex);
    BodyID * pResult = new BodyID(id);
    TRACE_NEW("BodyID", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyManifold
 * Method:    getLocalContactNormalX
 * Signature: (JJ)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SoftBodyManifold_getLocalContactNormalX
  (JNIEnv *, jclass, jlong manifoldVa, jlong vertexVa) {
    const SoftBodyManifold * const pManifold
            = reinterpret_cast<SoftBodyManifold *> (manifoldVa);
    const SoftBodyVertex * const pVertex
            = reinterpret_cast<SoftBodyVertex *> (vertexVa);
    const float result = pManifold->GetContactNormal(*pVertex).GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyManifold
 * Method:    getLocalContactNormalY
 * Signature: (JJ)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SoftBodyManifold_getLocalContactNormalY
  (JNIEnv *, jclass, jlong manifoldVa, jlong vertexVa) {
    const SoftBodyManifold * const pManifold
            = reinterpret_cast<SoftBodyManifold *> (manifoldVa);
    const SoftBodyVertex * const pVertex
            = reinterpret_cast<SoftBodyVertex *> (vertexVa);
    const float result = pManifold->GetContactNormal(*pVertex).GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyManifold
 * Method:    getLocalContactNormalZ
 * Signature: (JJ)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SoftBodyManifold_getLocalContactNormalZ
  (JNIEnv *, jclass, jlong manifoldVa, jlong vertexVa) {
    const SoftBodyManifold * const pManifold
            = reinterpret_cast<SoftBodyManifold *> (manifoldVa);
    const SoftBodyVertex * const pVertex
            = reinterpret_cast<SoftBodyVertex *> (vertexVa);
    const float result = pManifold->GetContactNormal(*pVertex).GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyManifold
 * Method:    getLocalContactPointX
 * Signature: (JJ)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SoftBodyManifold_getLocalContactPointX
  (JNIEnv *, jclass, jlong manifoldVa, jlong vertexVa) {
    const SoftBodyManifold * const pManifold
            = reinterpret_cast<SoftBodyManifold *> (manifoldVa);
    const SoftBodyVertex * const pVertex
            = reinterpret_cast<SoftBodyVertex *> (vertexVa);
    const float result = pManifold->GetLocalContactPoint(*pVertex).GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyManifold
 * Method:    getLocalContactPointY
 * Signature: (JJ)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SoftBodyManifold_getLocalContactPointY
  (JNIEnv *, jclass, jlong manifoldVa, jlong vertexVa) {
    const SoftBodyManifold * const pManifold
            = reinterpret_cast<SoftBodyManifold *> (manifoldVa);
    const SoftBodyVertex * const pVertex
            = reinterpret_cast<SoftBodyVertex *> (vertexVa);
    const float result = pManifold->GetLocalContactPoint(*pVertex).GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyManifold
 * Method:    getLocalContactPointZ
 * Signature: (JJ)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SoftBodyManifold_getLocalContactPointZ
  (JNIEnv *, jclass, jlong manifoldVa, jlong vertexVa) {
    const SoftBodyManifold * const pManifold
            = reinterpret_cast<SoftBodyManifold *> (manifoldVa);
    const SoftBodyVertex * const pVertex
            = reinterpret_cast<SoftBodyVertex *> (vertexVa);
    const float result = pManifold->GetLocalContactPoint(*pVertex).GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyManifold
 * Method:    getNumSensorContacts
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SoftBodyManifold_getNumSensorContacts
  (JNIEnv *, jclass, jlong manifoldVa) {
    const SoftBodyManifold * const pManifold
            = reinterpret_cast<SoftBodyManifold *> (manifoldVa);
    const uint result = pManifold->GetNumSensorContacts();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyManifold
 * Method:    getSensorContactBodyId
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SoftBodyManifold_getSensorContactBodyId
  (JNIEnv *, jclass, jlong manifoldVa, jint index) {
    const SoftBodyManifold * const pManifold
            = reinterpret_cast<SoftBodyManifold *> (manifoldVa);
    const BodyID id = pManifold->GetSensorContactBodyID(index);
    BodyID * pResult = new BodyID(id);
    TRACE_NEW("BodyID", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyManifold
 * Method:    getVertex
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SoftBodyManifold_getVertex
  (JNIEnv *, jclass, jlong manifoldVa, jint index) {
    const SoftBodyManifold * const pManifold
            = reinterpret_cast<SoftBodyManifold *> (manifoldVa);
    const Array<SoftBodyVertex>& vertices = pManifold->GetVertices();
    const SoftBodyVertex * const pResult = &vertices.at(index);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyManifold
 * Method:    hasContact
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_SoftBodyManifold_hasContact
  (JNIEnv *, jclass, jlong manifoldVa, jlong vertexVa) {
    const SoftBodyManifold * const pManifold
            = reinterpret_cast<SoftBodyManifold *> (manifoldVa);
    const SoftBodyVertex * const pVertex
            = reinterpret_cast<SoftBodyVertex *> (vertexVa);
    const bool result = pManifold->HasContact(*pVertex);
    return result;
}