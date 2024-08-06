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
#include <Jolt/Physics/Collision/CollideShape.h>
#include "auto/com_github_stephengold_joltjni_CollideShapeResult.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_CollideShapeResult
 * Method:    getBodyId2
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CollideShapeResult_getBodyId2
  (JNIEnv *, jclass, jlong shapeResultVa) {
    const CollideShapeResult * const pShapeResult
            = reinterpret_cast<CollideShapeResult *> (shapeResultVa);
    BodyID * const pResult = new BodyID(pShapeResult->mBodyID2);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_CollideShapeResult
 * Method:    getContactPointOn1X
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CollideShapeResult_getContactPointOn1X
  (JNIEnv *, jclass, jlong shapeResultVa) {
    const CollideShapeResult * const pShapeResult
            = reinterpret_cast<CollideShapeResult *> (shapeResultVa);
    float result = pShapeResult->mContactPointOn1.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CollideShapeResult
 * Method:    getContactPointOn1Y
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CollideShapeResult_getContactPointOn1Y
  (JNIEnv *, jclass, jlong shapeResultVa) {
    const CollideShapeResult * const pShapeResult
            = reinterpret_cast<CollideShapeResult *> (shapeResultVa);
    float result = pShapeResult->mContactPointOn1.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CollideShapeResult
 * Method:    getContactPointOn1Z
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CollideShapeResult_getContactPointOn1Z
  (JNIEnv *, jclass, jlong shapeResultVa) {
    const CollideShapeResult * const pShapeResult
            = reinterpret_cast<CollideShapeResult *> (shapeResultVa);
    float result = pShapeResult->mContactPointOn1.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CollideShapeResult
 * Method:    getContactPointOn2X
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CollideShapeResult_getContactPointOn2X
  (JNIEnv *, jclass, jlong shapeResultVa) {
    const CollideShapeResult * const pShapeResult
            = reinterpret_cast<CollideShapeResult *> (shapeResultVa);
    float result = pShapeResult->mContactPointOn2.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CollideShapeResult
 * Method:    getContactPointOn2Y
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CollideShapeResult_getContactPointOn2Y
  (JNIEnv *, jclass, jlong shapeResultVa) {
    const CollideShapeResult * const pShapeResult
            = reinterpret_cast<CollideShapeResult *> (shapeResultVa);
    float result = pShapeResult->mContactPointOn2.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CollideShapeResult
 * Method:    getContactPointOn2Z
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CollideShapeResult_getContactPointOn2Z
  (JNIEnv *, jclass, jlong shapeResultVa) {
    const CollideShapeResult * const pShapeResult
            = reinterpret_cast<CollideShapeResult *> (shapeResultVa);
    float result = pShapeResult->mContactPointOn2.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CollideShapeResult
 * Method:    getPenetrationAxisX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CollideShapeResult_getPenetrationAxisX
  (JNIEnv *, jclass, jlong shapeResultVa) {
    const CollideShapeResult * const pShapeResult
            = reinterpret_cast<CollideShapeResult *> (shapeResultVa);
    float result = pShapeResult->mPenetrationAxis.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CollideShapeResult
 * Method:    getPenetrationAxisY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CollideShapeResult_getPenetrationAxisY
  (JNIEnv *, jclass, jlong shapeResultVa) {
    const CollideShapeResult * const pShapeResult
            = reinterpret_cast<CollideShapeResult *> (shapeResultVa);
    float result = pShapeResult->mPenetrationAxis.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CollideShapeResult
 * Method:    getPenetrationAxisZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_CollideShapeResult_getPenetrationAxisZ
  (JNIEnv *, jclass, jlong shapeResultVa) {
    const CollideShapeResult * const pShapeResult
            = reinterpret_cast<CollideShapeResult *> (shapeResultVa);
    float result = pShapeResult->mPenetrationAxis.GetZ();
    return result;
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
    float result = pShapeResult->mPenetrationDepth;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CollideShapeResult
 * Method:    getSubShapeId1
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CollideShapeResult_getSubShapeId1
  (JNIEnv *, jclass, jlong shapeResultVa) {
    const CollideShapeResult * const pShapeResult
            = reinterpret_cast<CollideShapeResult *> (shapeResultVa);
    SubShapeID * const pResult = new SubShapeID(pShapeResult->mSubShapeID1);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_CollideShapeResult
 * Method:    getSubShapeId2
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CollideShapeResult_getSubShapeId2
  (JNIEnv *, jclass, jlong shapeResultVa) {
    const CollideShapeResult * const pShapeResult
            = reinterpret_cast<CollideShapeResult *> (shapeResultVa);
    SubShapeID * const pResult = new SubShapeID(pShapeResult->mSubShapeID2);
    return reinterpret_cast<jlong> (pResult);
}