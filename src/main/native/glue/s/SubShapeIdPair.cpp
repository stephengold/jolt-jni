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
#include <Jolt/Physics/Collision/Shape/SubShapeIDPair.h>
#include "auto/com_github_stephengold_joltjni_SubShapeIdPair.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_SubShapeIdPair
 * Method:    getBody1Id
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SubShapeIdPair_getBody1Id
  (JNIEnv *, jclass, jlong pairVa) {
    const SubShapeIDPair * const pPair
            = reinterpret_cast<SubShapeIDPair *> (pairVa);
    BodyID * const pResult = new BodyID();
    *pResult = pPair->GetBody1ID();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SubShapeIdPair
 * Method:    getBody2Id
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SubShapeIdPair_getBody2Id
  (JNIEnv *, jclass, jlong pairVa) {
    const SubShapeIDPair * const pPair
            = reinterpret_cast<SubShapeIDPair *> (pairVa);
    BodyID * const pResult = new BodyID();
    *pResult = pPair->GetBody2ID();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SubShapeIdPair
 * Method:    getHash
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SubShapeIdPair_getHash
  (JNIEnv *, jclass, jlong pairVa) {
    const SubShapeIDPair * const pPair
            = reinterpret_cast<SubShapeIDPair *> (pairVa);
    const uint64 result = pPair->GetHash();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SubShapeIdPair
 * Method:    getSubShapeId1
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SubShapeIdPair_getSubShapeId1
  (JNIEnv *, jclass, jlong pairVa) {
    const SubShapeIDPair * const pPair
            = reinterpret_cast<SubShapeIDPair *> (pairVa);
    SubShapeID * const pResult = new SubShapeID();
    *pResult = pPair->GetSubShapeID1();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SubShapeIdPair
 * Method:    getSubShapeId2
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SubShapeIdPair_getSubShapeId2
  (JNIEnv *, jclass, jlong pairVa) {
    const SubShapeIDPair * const pPair
            = reinterpret_cast<SubShapeIDPair *> (pairVa);
    SubShapeID * const pResult = new SubShapeID();
    TRACE_NEW("SubShapeID", pResult)
    *pResult = pPair->GetSubShapeID2();
    return reinterpret_cast<jlong> (pResult);
}