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
#include "Jolt/Jolt.h"
#include "Jolt/Physics/Collision/ContactListener.h"
#include "auto/com_github_stephengold_joltjni_ContactManifold.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_ContactManifold
 * Method:    getBaseOffsetX
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_ContactManifold_getBaseOffsetX
  (JNIEnv *, jclass, jlong manifoldVa) {
    const ContactManifold * const pManifold
            = reinterpret_cast<ContactManifold *> (manifoldVa);
    const Real result = pManifold->mBaseOffset.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ContactManifold
 * Method:    getBaseOffsetY
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_ContactManifold_getBaseOffsetY
  (JNIEnv *, jclass, jlong manifoldVa) {
    const ContactManifold * const pManifold
            = reinterpret_cast<ContactManifold *> (manifoldVa);
    const Real result = pManifold->mBaseOffset.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ContactManifold
 * Method:    getBaseOffsetZ
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_ContactManifold_getBaseOffsetZ
  (JNIEnv *, jclass, jlong manifoldVa) {
    const ContactManifold * const pManifold
            = reinterpret_cast<ContactManifold *> (manifoldVa);
    const Real result = pManifold->mBaseOffset.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ContactManifold
 * Method:    getPenetrationDepth
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ContactManifold_getPenetrationDepth
  (JNIEnv *, jclass, jlong manifoldVa) {
    const ContactManifold * const pManifold
            = reinterpret_cast<ContactManifold *> (manifoldVa);
    const float result = pManifold->mPenetrationDepth;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ContactManifold
 * Method:    getSubShapeId1
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ContactManifold_getSubShapeId1
  (JNIEnv *, jclass, jlong manifoldVa) {
    const ContactManifold * const pManifold
            = reinterpret_cast<ContactManifold *> (manifoldVa);
    const SubShapeID * const pResult = new SubShapeID(pManifold->mSubShapeID1);
    TRACE_NEW("SubShapeID", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_ContactManifold
 * Method:    getSubShapeId2
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ContactManifold_getSubShapeId2
  (JNIEnv *, jclass, jlong manifoldVa) {
    const ContactManifold * const pManifold
            = reinterpret_cast<ContactManifold *> (manifoldVa);
    const SubShapeID * const pResult = new SubShapeID(pManifold->mSubShapeID2);
    TRACE_NEW("SubShapeID", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_ContactManifold
 * Method:    getWorldSpaceNormalX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ContactManifold_getWorldSpaceNormalX
  (JNIEnv *, jclass, jlong manifoldVa) {
    const ContactManifold * const pManifold
            = reinterpret_cast<ContactManifold *> (manifoldVa);
    const float result = pManifold->mWorldSpaceNormal.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ContactManifold
 * Method:    getWorldSpaceNormalY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ContactManifold_getWorldSpaceNormalY
  (JNIEnv *, jclass, jlong manifoldVa) {
    const ContactManifold * const pManifold
            = reinterpret_cast<ContactManifold *> (manifoldVa);
    const float result = pManifold->mWorldSpaceNormal.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ContactManifold
 * Method:    getWorldSpaceNormalZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ContactManifold_getWorldSpaceNormalZ
  (JNIEnv *, jclass, jlong manifoldVa) {
    const ContactManifold * const pManifold
            = reinterpret_cast<ContactManifold *> (manifoldVa);
    const float result = pManifold->mWorldSpaceNormal.GetZ();
    return result;
}