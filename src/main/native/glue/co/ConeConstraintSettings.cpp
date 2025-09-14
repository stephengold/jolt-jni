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
#include "Jolt/Physics/Constraints/ConeConstraint.h"
#include "auto/com_github_stephengold_joltjni_ConeConstraintSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_ConeConstraintSettings
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ConeConstraintSettings_createCopy
  BODYOF_CREATE_COPY(ConeConstraintSettings)

/*
 * Class:     com_github_stephengold_joltjni_ConeConstraintSettings
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ConeConstraintSettings_createDefault
  BODYOF_CREATE_DEFAULT(ConeConstraintSettings)

/*
 * Class:     com_github_stephengold_joltjni_ConeConstraintSettings
 * Method:    getHalfConeAngle
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ConeConstraintSettings_getHalfConeAngle
  (JNIEnv *, jclass, jlong settingsVa) {
    const ConeConstraintSettings * const pSettings
            = reinterpret_cast<ConeConstraintSettings *> (settingsVa);
    const float result = pSettings->mHalfConeAngle;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ConeConstraintSettings
 * Method:    getPoint1
 * Signature: (JLjava/nio/DoubleBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ConeConstraintSettings_getPoint1
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeDoubles) {
    const ConeConstraintSettings * const pSettings
            = reinterpret_cast<ConeConstraintSettings *> (settingsVa);
    DIRECT_DOUBLE_BUFFER(pEnv, storeDoubles, pDoubles, capacityDoubles);
    JPH_ASSERT(capacityDoubles >= 3);
    const RVec3& result = pSettings->mPoint1;
    pDoubles[0] = result.GetX();
    pDoubles[1] = result.GetY();
    pDoubles[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_ConeConstraintSettings
 * Method:    getPoint2
 * Signature: (JLjava/nio/DoubleBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ConeConstraintSettings_getPoint2
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeDoubles) {
    const ConeConstraintSettings * const pSettings
            = reinterpret_cast<ConeConstraintSettings *> (settingsVa);
    DIRECT_DOUBLE_BUFFER(pEnv, storeDoubles, pDoubles, capacityDoubles);
    JPH_ASSERT(capacityDoubles >= 3);
    const RVec3& result = pSettings->mPoint2;
    pDoubles[0] = result.GetX();
    pDoubles[1] = result.GetY();
    pDoubles[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_ConeConstraintSettings
 * Method:    getSpace
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_ConeConstraintSettings_getSpace
  (JNIEnv *, jclass, jlong settingsVa) {
    const ConeConstraintSettings * const pSettings
            = reinterpret_cast<ConeConstraintSettings *> (settingsVa);
    const EConstraintSpace result = pSettings->mSpace;
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_ConeConstraintSettings
 * Method:    getTwistAxis1
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ConeConstraintSettings_getTwistAxis1
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeFloats) {
    const ConeConstraintSettings * const pSettings
            = reinterpret_cast<ConeConstraintSettings *> (settingsVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& result = pSettings->mTwistAxis1;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_ConeConstraintSettings
 * Method:    getTwistAxis2
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ConeConstraintSettings_getTwistAxis2
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeFloats) {
    const ConeConstraintSettings * const pSettings
            = reinterpret_cast<ConeConstraintSettings *> (settingsVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& result = pSettings->mTwistAxis2;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_ConeConstraintSettings
 * Method:    setHalfConeAngle
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ConeConstraintSettings_setHalfConeAngle
  (JNIEnv *, jclass, jlong settingsVa, jfloat angle) {
    ConeConstraintSettings * const pSettings
            = reinterpret_cast<ConeConstraintSettings *> (settingsVa);
    pSettings->mHalfConeAngle = angle;
}

/*
 * Class:     com_github_stephengold_joltjni_ConeConstraintSettings
 * Method:    setPoint1
 * Signature: (JDDD)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ConeConstraintSettings_setPoint1
  (JNIEnv *, jclass, jlong settingsVa, jdouble x, jdouble y, jdouble z) {
    ConeConstraintSettings * const pSettings
            = reinterpret_cast<ConeConstraintSettings *> (settingsVa);
    const RVec3 rvec3(x, y, z);
    pSettings->mPoint1 = rvec3;
}

/*
 * Class:     com_github_stephengold_joltjni_ConeConstraintSettings
 * Method:    setPoint2
 * Signature: (JDDD)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ConeConstraintSettings_setPoint2
  (JNIEnv *, jclass, jlong settingsVa, jdouble x, jdouble y, jdouble z) {
    ConeConstraintSettings * const pSettings
            = reinterpret_cast<ConeConstraintSettings *> (settingsVa);
    const RVec3 rvec3(x, y, z);
    pSettings->mPoint2 = rvec3;
}

/*
 * Class:     com_github_stephengold_joltjni_ConeConstraintSettings
 * Method:    setSpace
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ConeConstraintSettings_setSpace
  (JNIEnv *, jclass, jlong settingsVa, jint ordinal) {
    ConeConstraintSettings * const pSettings
            = reinterpret_cast<ConeConstraintSettings *> (settingsVa);
    const EConstraintSpace space = (EConstraintSpace) ordinal;
    pSettings->mSpace = space;
}

/*
 * Class:     com_github_stephengold_joltjni_ConeConstraintSettings
 * Method:    setTwistAxis1
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ConeConstraintSettings_setTwistAxis1
  (JNIEnv *, jclass, jlong settingsVa, jfloat x, jfloat y, jfloat z) {
    ConeConstraintSettings * const pSettings
            = reinterpret_cast<ConeConstraintSettings *> (settingsVa);
    const Vec3 vec3(x, y, z);
    pSettings->mTwistAxis1 = vec3;
}

/*
 * Class:     com_github_stephengold_joltjni_ConeConstraintSettings
 * Method:    setTwistAxis2
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ConeConstraintSettings_setTwistAxis2
  (JNIEnv *, jclass, jlong settingsVa, jfloat x, jfloat y, jfloat z) {
    ConeConstraintSettings * const pSettings
            = reinterpret_cast<ConeConstraintSettings *> (settingsVa);
    const Vec3 vec3(x, y, z);
    pSettings->mTwistAxis2 = vec3;
}