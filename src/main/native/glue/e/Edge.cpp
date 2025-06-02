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
#include "auto/com_github_stephengold_joltjni_Edge.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_Edge
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Edge_createDefault
  BODYOF_CREATE_DEFAULT(SoftBodySharedSettings::Edge)

/*
 * Class:     com_github_stephengold_joltjni_Edge
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Edge_free
  BODYOF_FREE(SoftBodySharedSettings::Edge)

/*
 * Class:     com_github_stephengold_joltjni_Edge
 * Method:    getCompliance
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Edge_getCompliance
  (JNIEnv *, jclass, jlong edgeVa) {
    const SoftBodySharedSettings::Edge * const pEdge
            = reinterpret_cast<SoftBodySharedSettings::Edge *> (edgeVa);
    const float result = pEdge->mCompliance;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Edge
 * Method:    getRestLength
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Edge_getRestLength
  (JNIEnv *, jclass, jlong edgeVa) {
    const SoftBodySharedSettings::Edge * const pEdge
            = reinterpret_cast<SoftBodySharedSettings::Edge *> (edgeVa);
    const float result = pEdge->mRestLength;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Edge
 * Method:    getVertex
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Edge_getVertex
  (JNIEnv *, jclass, jlong edgeVa, jint indexInEdge) {
    const SoftBodySharedSettings::Edge * const pEdge
            = reinterpret_cast<SoftBodySharedSettings::Edge *> (edgeVa);
    const uint32 result = pEdge->mVertex[indexInEdge];
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Edge
 * Method:    setCompliance
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Edge_setCompliance
  (JNIEnv *, jclass, jlong edgeVa, jfloat compliance) {
    SoftBodySharedSettings::Edge * const pEdge
            = reinterpret_cast<SoftBodySharedSettings::Edge *> (edgeVa);
    pEdge->mCompliance = compliance;
}

/*
 * Class:     com_github_stephengold_joltjni_Edge
 * Method:    setRestLength
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Edge_setRestLength
  (JNIEnv *, jclass, jlong edgeVa, jfloat restLength) {
    SoftBodySharedSettings::Edge * const pEdge
            = reinterpret_cast<SoftBodySharedSettings::Edge *> (edgeVa);
    pEdge->mRestLength = restLength;
}

/*
 * Class:     com_github_stephengold_joltjni_Edge
 * Method:    setVertex
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Edge_setVertex
  (JNIEnv *, jclass, jlong edgeVa, jint indexInEdge, jint indexInMesh) {
    SoftBodySharedSettings::Edge * const pEdge
            = reinterpret_cast<SoftBodySharedSettings::Edge *> (edgeVa);
    pEdge->mVertex[indexInEdge] = indexInMesh;
}