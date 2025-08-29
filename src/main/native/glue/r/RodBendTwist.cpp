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
#include "Jolt/Physics/SoftBody/SoftBodySharedSettings.h"
#include "auto/com_github_stephengold_joltjni_RodBendTwist.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_RodBendTwist
 * Method:    create
 * Signature: (IIF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_RodBendTwist_create
  (JNIEnv *, jclass, jint vertex0, jint vertex1, jfloat compliance) {
    SoftBodySharedSettings::RodBendTwist * const pResult
            = new SoftBodySharedSettings::RodBendTwist(
                    vertex0, vertex1, compliance);
    TRACE_NEW("SoftBodySharedSettings::RodBendTwist", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_RodBendTwist
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_RodBendTwist_createDefault
  BODYOF_CREATE_DEFAULT(SoftBodySharedSettings::RodBendTwist)

/*
 * Class:     com_github_stephengold_joltjni_RodBendTwist
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RodBendTwist_free
  BODYOF_FREE(SoftBodySharedSettings::RodBendTwist)

/*
 * Class:     com_github_stephengold_joltjni_RodBendTwist
 * Method:    getCompliance
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_RodBendTwist_getCompliance
  (JNIEnv *, jclass, jlong constraintVa) {
    const SoftBodySharedSettings::RodBendTwist * const pConstraint
            = reinterpret_cast<SoftBodySharedSettings::RodBendTwist *> (constraintVa);
    const float result = pConstraint->mCompliance;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RodBendTwist
 * Method:    getOmega0
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RodBendTwist_getOmega0
  (JNIEnv *pEnv, jclass, jlong constraintVa, jobject storeFloats) {
    const SoftBodySharedSettings::RodBendTwist * const pConstraint
            = reinterpret_cast<SoftBodySharedSettings::RodBendTwist *> (constraintVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 4);
    const Quat& result = pConstraint->mOmega0;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
    pFloats[3] = result.GetW();
}

/*
 * Class:     com_github_stephengold_joltjni_RodBendTwist
 * Method:    getRod
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_RodBendTwist_getRod
  (JNIEnv *, jclass, jlong constraintVa, jint indexInRod) {
    const SoftBodySharedSettings::RodBendTwist * const pConstraint
            = reinterpret_cast<SoftBodySharedSettings::RodBendTwist *> (constraintVa);
    const uint32 result = pConstraint->mRod[indexInRod];
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RodBendTwist
 * Method:    setCompliance
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RodBendTwist_setCompliance
  (JNIEnv *, jclass, jlong constraintVa, jfloat compliance) {
    SoftBodySharedSettings::RodBendTwist * const pConstraint
            = reinterpret_cast<SoftBodySharedSettings::RodBendTwist *> (constraintVa);
    pConstraint->mCompliance = compliance;
}

/*
 * Class:     com_github_stephengold_joltjni_RodBendTwist
 * Method:    setOmega0
 * Signature: (JFFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RodBendTwist_setOmega0
  (JNIEnv *, jclass, jlong constraintVa, jfloat qx, jfloat qy, jfloat qz, jfloat qw) {
    SoftBodySharedSettings::RodBendTwist * const pConstraint
            = reinterpret_cast<SoftBodySharedSettings::RodBendTwist *> (constraintVa);
    const Quat rotation(qx, qy, qz, qw);
    pConstraint->mOmega0 = rotation;
}

/*
 * Class:     com_github_stephengold_joltjni_RodBendTwist
 * Method:    setRod
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RodBendTwist_setRod
  (JNIEnv *, jclass, jlong constraintVa, jint indexInRod, jint indexInMesh) {
    SoftBodySharedSettings::RodBendTwist * const pConstraint
            = reinterpret_cast<SoftBodySharedSettings::RodBendTwist *> (constraintVa);
    pConstraint->mRod[indexInRod] = indexInMesh;
}