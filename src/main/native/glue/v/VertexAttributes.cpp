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
#include "auto/com_github_stephengold_joltjni_VertexAttributes.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_VertexAttributes
 * Method:    createAttributes
 * Signature: (FFFIF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_VertexAttributes_createAttributes
  (JNIEnv *, jclass, jfloat compliance, jfloat shearCompliance,
  jfloat bendCompliance, jint ordinal, jfloat lraMultiplier) {
    const SoftBodySharedSettings::ELRAType lraType
            = (SoftBodySharedSettings::ELRAType) ordinal;
    SoftBodySharedSettings::VertexAttributes * const pResult
            = new SoftBodySharedSettings::VertexAttributes(compliance,
                    shearCompliance, bendCompliance, lraType, lraMultiplier);
    TRACE_NEW("SoftBodySharedSettings::VertexAttributes", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_VertexAttributes
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_VertexAttributes_createDefault
  BODYOF_CREATE_DEFAULT(SoftBodySharedSettings::VertexAttributes)

/*
 * Class:     com_github_stephengold_joltjni_VertexAttributes
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VertexAttributes_free
  BODYOF_FREE(SoftBodySharedSettings::VertexAttributes)

/*
 * Class:     com_github_stephengold_joltjni_VertexAttributes
 * Method:    getBendCompliance
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_VertexAttributes_getBendCompliance
  (JNIEnv *, jclass, jlong attributesVa) {
    const SoftBodySharedSettings::VertexAttributes * const pAttributes
            = reinterpret_cast<SoftBodySharedSettings::VertexAttributes *> (attributesVa);
    const float result = pAttributes->mBendCompliance;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VertexAttributes
 * Method:    getCompliance
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_VertexAttributes_getCompliance
  (JNIEnv *, jclass, jlong attributesVa) {
    const SoftBodySharedSettings::VertexAttributes * const pAttributes
            = reinterpret_cast<SoftBodySharedSettings::VertexAttributes *> (attributesVa);
    const float result = pAttributes->mCompliance;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VertexAttributes
 * Method:    getLraMaxDistanceMultiplier
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_VertexAttributes_getLraMaxDistanceMultiplier
  (JNIEnv *, jclass, jlong attributesVa) {
    const SoftBodySharedSettings::VertexAttributes * const pAttributes
            = reinterpret_cast<SoftBodySharedSettings::VertexAttributes *> (attributesVa);
    const float result = pAttributes->mLRAMaxDistanceMultiplier;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VertexAttributes
 * Method:    getLraType
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_VertexAttributes_getLraType
  (JNIEnv *, jclass, jlong attributesVa) {
    const SoftBodySharedSettings::VertexAttributes * const pAttributes
            = reinterpret_cast<SoftBodySharedSettings::VertexAttributes *> (attributesVa);
    const SoftBodySharedSettings::ELRAType result = pAttributes->mLRAType;
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_VertexAttributes
 * Method:    getShearCompliance
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_VertexAttributes_getShearCompliance
  (JNIEnv *, jclass, jlong attributesVa) {
    const SoftBodySharedSettings::VertexAttributes * const pAttributes
            = reinterpret_cast<SoftBodySharedSettings::VertexAttributes *> (attributesVa);
    const float result = pAttributes->mShearCompliance;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_VertexAttributes
 * Method:    setBendCompliance
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VertexAttributes_setBendCompliance
  (JNIEnv *, jclass, jlong attributesVa, jfloat compliance) {
    SoftBodySharedSettings::VertexAttributes * const pAttributes
            = reinterpret_cast<SoftBodySharedSettings::VertexAttributes *> (attributesVa);
    pAttributes->mBendCompliance = compliance;
}

/*
 * Class:     com_github_stephengold_joltjni_VertexAttributes
 * Method:    setCompliance
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VertexAttributes_setCompliance
  (JNIEnv *, jclass, jlong attributesVa, jfloat compliance) {
    SoftBodySharedSettings::VertexAttributes * const pAttributes
            = reinterpret_cast<SoftBodySharedSettings::VertexAttributes *> (attributesVa);
    pAttributes->mCompliance = compliance;
}

/*
 * Class:     com_github_stephengold_joltjni_VertexAttributes
 * Method:    setLraMaxDistanceMultiplier
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VertexAttributes_setLraMaxDistanceMultiplier
  (JNIEnv *, jclass, jlong attributesVa, jfloat multiplier) {
    SoftBodySharedSettings::VertexAttributes * const pAttributes
            = reinterpret_cast<SoftBodySharedSettings::VertexAttributes *> (attributesVa);
    pAttributes->mLRAMaxDistanceMultiplier = multiplier;
}

/*
 * Class:     com_github_stephengold_joltjni_VertexAttributes
 * Method:    setLraType
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VertexAttributes_setLraType
  (JNIEnv *, jclass, jlong attributesVa, jint ordinal) {
    SoftBodySharedSettings::VertexAttributes * const pAttributes
            = reinterpret_cast<SoftBodySharedSettings::VertexAttributes *> (attributesVa);
    const SoftBodySharedSettings::ELRAType value
            = (SoftBodySharedSettings::ELRAType) ordinal;
    pAttributes->mLRAType = value;
}

/*
 * Class:     com_github_stephengold_joltjni_VertexAttributes
 * Method:    setShearCompliance
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_VertexAttributes_setShearCompliance
  (JNIEnv *, jclass, jlong attributesVa, jfloat compliance) {
    SoftBodySharedSettings::VertexAttributes * const pAttributes
            = reinterpret_cast<SoftBodySharedSettings::VertexAttributes *> (attributesVa);
    pAttributes->mShearCompliance = compliance;
}