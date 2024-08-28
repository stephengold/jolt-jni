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
#include <Jolt/Physics/Constraints/Constraint.h>
#include "auto/com_github_stephengold_joltjni_Constraint.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_Constraint
 * Method:    getConstraintPriority
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Constraint_getConstraintPriority
  (JNIEnv *, jclass, jlong constraintVa) {
    const Constraint * const pConstraint
            = reinterpret_cast<Constraint *> (constraintVa);
    const uint32 result = pConstraint->GetConstraintPriority();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Constraint
 * Method:    getConstraintSettings
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Constraint_getConstraintSettings
  (JNIEnv *, jclass, jlong constraintVa) {
    const Constraint * const pConstraint
            = reinterpret_cast<Constraint *> (constraintVa);
    Ref<ConstraintSettings> * const pResult = new Ref<ConstraintSettings>();
    TRACE_NEW("Ref<ConstraintSettings>", pResult)
    *pResult = pConstraint->GetConstraintSettings();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Constraint
 * Method:    getEnabled
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_Constraint_getEnabled
  (JNIEnv *, jclass, jlong constraintVa) {
    const Constraint * const pConstraint
            = reinterpret_cast<Constraint *> (constraintVa);
    const bool result = pConstraint->GetEnabled();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Constraint
 * Method:    getNumPositionStepsOverride
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Constraint_getNumPositionStepsOverride
  (JNIEnv *, jclass, jlong constraintVa) {
    const Constraint * const pConstraint
            = reinterpret_cast<Constraint *> (constraintVa);
    const uint result = pConstraint->GetNumPositionStepsOverride();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Constraint
 * Method:    getNumVelocityStepsOverride
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Constraint_getNumVelocityStepsOverride
  (JNIEnv *, jclass, jlong constraintVa) {
    const Constraint * const pConstraint
            = reinterpret_cast<Constraint *> (constraintVa);
    const uint result = pConstraint->GetNumVelocityStepsOverride();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Constraint
 * Method:    getRefCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Constraint_getRefCount
  (JNIEnv *, jclass, jlong constraintVa) {
    const Constraint * const pConstraint
            = reinterpret_cast<Constraint *> (constraintVa);
    const uint32 result = pConstraint->GetRefCount();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Constraint
 * Method:    getSubType
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Constraint_getSubType
  (JNIEnv *, jclass, jlong constraintVa) {
    const Constraint * const pConstraint
            = reinterpret_cast<Constraint *> (constraintVa);
    const EConstraintSubType result = pConstraint->GetSubType();
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_Constraint
 * Method:    getType
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Constraint_getType
  (JNIEnv *, jclass, jlong constraintVa) {
    const Constraint * const pConstraint
            = reinterpret_cast<Constraint *> (constraintVa);
    const EConstraintType result = pConstraint->GetType();
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_Constraint
 * Method:    getUserData
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Constraint_getUserData
  (JNIEnv *, jclass, jlong constraintVa) {
    const Constraint * const pConstraint
            = reinterpret_cast<Constraint *> (constraintVa);
    const uint64 result = pConstraint->GetUserData();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Constraint
 * Method:    setConstraintPriority
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Constraint_setConstraintPriority
  (JNIEnv *, jclass, jlong constraintVa, jint setting) {
    Constraint * const pConstraint
            = reinterpret_cast<Constraint *> (constraintVa);
    pConstraint->SetConstraintPriority(setting);
}

/*
 * Class:     com_github_stephengold_joltjni_Constraint
 * Method:    setEnabled
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Constraint_setEnabled
  (JNIEnv *, jclass, jlong constraintVa, jboolean setting) {
    Constraint * const pConstraint
            = reinterpret_cast<Constraint *> (constraintVa);
    pConstraint->SetEnabled(setting);
}

/*
 * Class:     com_github_stephengold_joltjni_Constraint
 * Method:    setNumPositionStepsOverride
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Constraint_setNumPositionStepsOverride
  (JNIEnv *, jclass, jlong constraintVa, jint setting) {
    Constraint * const pConstraint
            = reinterpret_cast<Constraint *> (constraintVa);
    pConstraint->SetNumPositionStepsOverride(setting);
}

/*
 * Class:     com_github_stephengold_joltjni_Constraint
 * Method:    setNumVelocityStepsOverride
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Constraint_setNumVelocityStepsOverride
  (JNIEnv *, jclass, jlong constraintVa, jint setting) {
    Constraint * const pConstraint
            = reinterpret_cast<Constraint *> (constraintVa);
    pConstraint->SetNumVelocityStepsOverride(setting);
}

/*
 * Class:     com_github_stephengold_joltjni_Constraint
 * Method:    setUserData
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Constraint_setUserData
  (JNIEnv *, jclass, jlong constraintVa, jlong value) {
    Constraint * const pConstraint
            = reinterpret_cast<Constraint *> (constraintVa);
    pConstraint->SetUserData(value);
}

/*
 * Class:     com_github_stephengold_joltjni_Constraint
 * Method:    toRef
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Constraint_toRef
  (JNIEnv *, jclass, jlong constraintVa) {
    Constraint * const pConstraint
            = reinterpret_cast<Constraint *> (constraintVa);
    Ref<Constraint> * const pResult = new Ref<Constraint>(pConstraint);
    TRACE_NEW("Ref<Constraint>", pResult)
    return reinterpret_cast<jlong> (pResult);
}