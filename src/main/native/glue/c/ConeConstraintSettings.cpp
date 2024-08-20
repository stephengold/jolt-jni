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
#include <Jolt/Physics/Constraints/ConeConstraint.h>
#include "auto/com_github_stephengold_joltjni_ConeConstraintSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_ConeConstraintSettings
 * Method:    createConeConstraintSettings
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ConeConstraintSettings_createConeConstraintSettings
  (JNIEnv *, jclass) {
    ConeConstraintSettings * const pResult = new ConeConstraintSettings();
    TRACE_NEW("ConeConstraintSettings", pResult)
    return reinterpret_cast<jlong> (pResult);
}

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
 * Method:    getPoint1X
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_ConeConstraintSettings_getPoint1X
  (JNIEnv *, jclass, jlong settingsVa) {
    const ConeConstraintSettings * const pSettings
            = reinterpret_cast<ConeConstraintSettings *> (settingsVa);
    const Real result = pSettings->mPoint1.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ConeConstraintSettings
 * Method:    getPoint1Y
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_ConeConstraintSettings_getPoint1Y
  (JNIEnv *, jclass, jlong settingsVa) {
    const ConeConstraintSettings * const pSettings
            = reinterpret_cast<ConeConstraintSettings *> (settingsVa);
    const Real result = pSettings->mPoint1.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ConeConstraintSettings
 * Method:    getPoint1Z
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_ConeConstraintSettings_getPoint1Z
  (JNIEnv *, jclass, jlong settingsVa) {
    const ConeConstraintSettings * const pSettings
            = reinterpret_cast<ConeConstraintSettings *> (settingsVa);
    const Real result = pSettings->mPoint1.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ConeConstraintSettings
 * Method:    getPoint2X
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_ConeConstraintSettings_getPoint2X
  (JNIEnv *, jclass, jlong settingsVa) {
    const ConeConstraintSettings * const pSettings
            = reinterpret_cast<ConeConstraintSettings *> (settingsVa);
    const Real result = pSettings->mPoint2.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ConeConstraintSettings
 * Method:    getPoint2Y
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_ConeConstraintSettings_getPoint2Y
  (JNIEnv *, jclass, jlong settingsVa) {
    const ConeConstraintSettings * const pSettings
            = reinterpret_cast<ConeConstraintSettings *> (settingsVa);
    const Real result = pSettings->mPoint2.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ConeConstraintSettings
 * Method:    getPoint2Z
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_ConeConstraintSettings_getPoint2Z
  (JNIEnv *, jclass, jlong settingsVa) {
    const ConeConstraintSettings * const pSettings
            = reinterpret_cast<ConeConstraintSettings *> (settingsVa);
    const Real result = pSettings->mPoint2.GetZ();
    return result;
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
 * Method:    getTwistAxis1X
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ConeConstraintSettings_getTwistAxis1X
  (JNIEnv *, jclass, jlong settingsVa) {
    const ConeConstraintSettings * const pSettings
            = reinterpret_cast<ConeConstraintSettings *> (settingsVa);
    const float result = pSettings->mTwistAxis1.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ConeConstraintSettings
 * Method:    getTwistAxis1Y
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ConeConstraintSettings_getTwistAxis1Y
  (JNIEnv *, jclass, jlong settingsVa) {
    const ConeConstraintSettings * const pSettings
            = reinterpret_cast<ConeConstraintSettings *> (settingsVa);
    const float result = pSettings->mTwistAxis1.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ConeConstraintSettings
 * Method:    getTwistAxis1Z
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ConeConstraintSettings_getTwistAxis1Z
  (JNIEnv *, jclass, jlong settingsVa) {
    const ConeConstraintSettings * const pSettings
            = reinterpret_cast<ConeConstraintSettings *> (settingsVa);
    const float result = pSettings->mTwistAxis1.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ConeConstraintSettings
 * Method:    getTwistAxis2X
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ConeConstraintSettings_getTwistAxis2X
  (JNIEnv *, jclass, jlong settingsVa) {
    const ConeConstraintSettings * const pSettings
            = reinterpret_cast<ConeConstraintSettings *> (settingsVa);
    const float result = pSettings->mTwistAxis2.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ConeConstraintSettings
 * Method:    getTwistAxis2Y
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ConeConstraintSettings_getTwistAxis2Y
  (JNIEnv *, jclass, jlong settingsVa) {
    const ConeConstraintSettings * const pSettings
            = reinterpret_cast<ConeConstraintSettings *> (settingsVa);
    const float result = pSettings->mTwistAxis2.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ConeConstraintSettings
 * Method:    getTwistAxis2Z
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ConeConstraintSettings_getTwistAxis2Z
  (JNIEnv *, jclass, jlong settingsVa) {
    const ConeConstraintSettings * const pSettings
            = reinterpret_cast<ConeConstraintSettings *> (settingsVa);
    const float result = pSettings->mTwistAxis2.GetZ();
    return result;
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
