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
#include "auto/com_github_stephengold_joltjni_RodStretchShear.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_RodStretchShear
 * Method:    create
 * Signature: (IIF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_RodStretchShear_create
  (JNIEnv *, jclass, jint vertex0, jint vertex1, jfloat compliance) {
    SoftBodySharedSettings::RodStretchShear * const pResult
            = new SoftBodySharedSettings::RodStretchShear(
                    vertex0, vertex1, compliance);
    TRACE_NEW("SoftBodySharedSettings::RodStretchShear", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_RodStretchShear
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_RodStretchShear_createDefault
  BODYOF_CREATE_DEFAULT(SoftBodySharedSettings::RodStretchShear)

/*
 * Class:     com_github_stephengold_joltjni_RodStretchShear
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RodStretchShear_free
  BODYOF_FREE(SoftBodySharedSettings::RodStretchShear)

/*
 * Class:     com_github_stephengold_joltjni_RodStretchShear
 * Method:    getBishop
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RodStretchShear_getBishop
  (JNIEnv *pEnv, jclass, jlong rodVa, jobject storeFloats) {
    const SoftBodySharedSettings::RodStretchShear * const pRod
            = reinterpret_cast<SoftBodySharedSettings::RodStretchShear *> (rodVa);
    jfloat * const pFloats
            = (jfloat *) pEnv->GetDirectBufferAddress(storeFloats);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const jlong capacityFloats = pEnv->GetDirectBufferCapacity(storeFloats);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    JPH_ASSERT(capacityFloats >= 4);
    const Quat& result = pRod->mBishop;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
    pFloats[3] = result.GetW();
}

/*
 * Class:     com_github_stephengold_joltjni_RodStretchShear
 * Method:    getCompliance
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_RodStretchShear_getCompliance
  (JNIEnv *, jclass, jlong rodVa) {
    const SoftBodySharedSettings::RodStretchShear * const pRod
            = reinterpret_cast<SoftBodySharedSettings::RodStretchShear *> (rodVa);
    const float result = pRod->mCompliance;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RodStretchShear
 * Method:    getInvMass
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_RodStretchShear_getInvMass
  (JNIEnv *, jclass, jlong rodVa) {
    const SoftBodySharedSettings::RodStretchShear * const pRod
            = reinterpret_cast<SoftBodySharedSettings::RodStretchShear *> (rodVa);
    const float result = pRod->mInvMass;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RodStretchShear
 * Method:    getLength
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_RodStretchShear_getLength
  (JNIEnv *, jclass, jlong rodVa) {
    const SoftBodySharedSettings::RodStretchShear * const pRod
            = reinterpret_cast<SoftBodySharedSettings::RodStretchShear *> (rodVa);
    const float result = pRod->mLength;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RodStretchShear
 * Method:    getVertex
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_RodStretchShear_getVertex
  (JNIEnv *, jclass, jlong rodVa, jint indexInRod) {
    const SoftBodySharedSettings::RodStretchShear * const pRod
            = reinterpret_cast<SoftBodySharedSettings::RodStretchShear *> (rodVa);
    const uint32 result = pRod->mVertex[indexInRod];
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RodStretchShear
 * Method:    setBishop
 * Signature: (JFFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RodStretchShear_setBishop
  (JNIEnv *, jclass, jlong rodVa, jfloat qx, jfloat qy, jfloat qz, jfloat qw) {
    SoftBodySharedSettings::RodStretchShear * const pRod
            = reinterpret_cast<SoftBodySharedSettings::RodStretchShear *> (rodVa);
    const Quat rotation(qx, qy, qz, qw);
    pRod->mBishop = rotation;
}

/*
 * Class:     com_github_stephengold_joltjni_RodStretchShear
 * Method:    setCompliance
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RodStretchShear_setCompliance
  (JNIEnv *, jclass, jlong rodVa, jfloat compliance) {
    SoftBodySharedSettings::RodStretchShear * const pRod
            = reinterpret_cast<SoftBodySharedSettings::RodStretchShear *> (rodVa);
    pRod->mCompliance = compliance;
}

/*
 * Class:     com_github_stephengold_joltjni_RodStretchShear
 * Method:    setInvMass
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RodStretchShear_setInvMass
  (JNIEnv *, jclass, jlong rodVa, jfloat invMass) {
    SoftBodySharedSettings::RodStretchShear * const pRod
            = reinterpret_cast<SoftBodySharedSettings::RodStretchShear *> (rodVa);
    pRod->mInvMass = invMass;
}

/*
 * Class:     com_github_stephengold_joltjni_RodStretchShear
 * Method:    setLength
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RodStretchShear_setLength
  (JNIEnv *, jclass, jlong rodVa, jfloat length) {
    SoftBodySharedSettings::RodStretchShear * const pRod
            = reinterpret_cast<SoftBodySharedSettings::RodStretchShear *> (rodVa);
    pRod->mLength = length;
}

/*
 * Class:     com_github_stephengold_joltjni_RodStretchShear
 * Method:    setVertex
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RodStretchShear_setVertex
  (JNIEnv *, jclass, jlong rodVa, jint indexInRod, jint indexInMesh) {
    SoftBodySharedSettings::RodStretchShear * const pRod
            = reinterpret_cast<SoftBodySharedSettings::RodStretchShear *> (rodVa);
    pRod->mVertex[indexInRod] = indexInMesh;
}