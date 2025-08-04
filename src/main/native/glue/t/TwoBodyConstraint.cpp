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
#include "Jolt/Physics/Constraints/TwoBodyConstraint.h"

#include "auto/com_github_stephengold_joltjni_TwoBodyConstraint.h"
#include "auto/com_github_stephengold_joltjni_TwoBodyConstraintRef.h"
#include "glue/glue.h"

using namespace JPH;

IMPLEMENT_REF(TwoBodyConstraint,
  Java_com_github_stephengold_joltjni_TwoBodyConstraintRef_copy,
  Java_com_github_stephengold_joltjni_TwoBodyConstraintRef_createDefault,
  Java_com_github_stephengold_joltjni_TwoBodyConstraintRef_free,
  Java_com_github_stephengold_joltjni_TwoBodyConstraintRef_getPtr,
  Java_com_github_stephengold_joltjni_TwoBodyConstraintRef_toRefC)

/*
 * Class:     com_github_stephengold_joltjni_TwoBodyConstraint
 * Method:    getBody1
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_TwoBodyConstraint_getBody1
  (JNIEnv *, jclass, jlong constraintVa) {
    const TwoBodyConstraint * const pConstraint
            = reinterpret_cast<TwoBodyConstraint *> (constraintVa);
    Body * const pResult = pConstraint->GetBody1();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_TwoBodyConstraint
 * Method:    getBody1PivotLocation
 * Signature: (JLjava/nio/DoubleBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TwoBodyConstraint_getBody1PivotLocation
  (JNIEnv *pEnv, jclass, jlong constraintVa, jobject storeResult) {
    const TwoBodyConstraint * const pConstraint
            = reinterpret_cast<TwoBodyConstraint *> (constraintVa);
    const Body * const pBody = pConstraint->GetBody1();
    const RMat44 comToWorld = pBody->GetCenterOfMassTransform();
    const Mat44 pivotToCom = pConstraint->GetConstraintToBody1Matrix();
    const RMat44 pivotToWorld = comToWorld * pivotToCom;
    const RVec3 location = pivotToWorld.GetTranslation();
    DIRECT_DOUBLE_BUFFER(pEnv, storeResult, pDoubles, capacityDoubles);
    JPH_ASSERT(capacityDoubles >= 3);
    pDoubles[0] = location.GetX();
    pDoubles[1] = location.GetY();
    pDoubles[2] = location.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_TwoBodyConstraint
 * Method:    getBody2
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_TwoBodyConstraint_getBody2
  (JNIEnv *, jclass, jlong constraintVa) {
    const TwoBodyConstraint * const pConstraint
            = reinterpret_cast<TwoBodyConstraint *> (constraintVa);
    Body * const pResult = pConstraint->GetBody2();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_TwoBodyConstraint
 * Method:    getBody2PivotLocation
 * Signature: (JLjava/nio/DoubleBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TwoBodyConstraint_getBody2PivotLocation
  (JNIEnv *pEnv, jclass, jlong constraintVa, jobject storeResult) {
    const TwoBodyConstraint * const pConstraint
            = reinterpret_cast<TwoBodyConstraint *> (constraintVa);
    const Body * const pBody = pConstraint->GetBody2();
    const RMat44 comToWorld = pBody->GetCenterOfMassTransform();
    const Mat44 pivotToCom = pConstraint->GetConstraintToBody2Matrix();
    const RMat44 pivotToWorld = comToWorld * pivotToCom;
    const RVec3 location = pivotToWorld.GetTranslation();
    DIRECT_DOUBLE_BUFFER(pEnv, storeResult, pDoubles, capacityDoubles);
    JPH_ASSERT(capacityDoubles >= 3);
    pDoubles[0] = location.GetX();
    pDoubles[1] = location.GetY();
    pDoubles[2] = location.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_TwoBodyConstraint
 * Method:    getConstraintToBody1Matrix
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_TwoBodyConstraint_getConstraintToBody1Matrix
  (JNIEnv *, jclass, jlong constraintVa) {
    const TwoBodyConstraint * const pConstraint
            = reinterpret_cast<TwoBodyConstraint *> (constraintVa);
    Mat44 * const pResult = new Mat44();
    TRACE_NEW("Mat44", pResult)
    *pResult = pConstraint->GetConstraintToBody1Matrix();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_TwoBodyConstraint
 * Method:    getConstraintToBody2Matrix
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_TwoBodyConstraint_getConstraintToBody2Matrix
  (JNIEnv *, jclass, jlong constraintVa) {
    const TwoBodyConstraint * const pConstraint
            = reinterpret_cast<TwoBodyConstraint *> (constraintVa);
    Mat44 * const pResult = new Mat44();
    TRACE_NEW("Mat44", pResult)
    *pResult = pConstraint->GetConstraintToBody2Matrix();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_TwoBodyConstraint
 * Method:    toRef
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_TwoBodyConstraint_toRef
  (JNIEnv *, jclass, jlong constraintVa) {
    TwoBodyConstraint * const pConstraint
            = reinterpret_cast<TwoBodyConstraint *> (constraintVa);
    Ref<TwoBodyConstraint> * const pResult
            = new Ref<TwoBodyConstraint>(pConstraint);
    TRACE_NEW("Ref<TwoBodyConstraint>", pResult)
    return reinterpret_cast<jlong> (pResult);
}