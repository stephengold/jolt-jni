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
#include <Jolt/Physics/Constraints/FixedConstraint.h>
#include "auto/com_github_stephengold_joltjni_FixedConstraintSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_FixedConstraintSettings
 * Method:    createFixedConstraintSettings
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_FixedConstraintSettings_createFixedConstraintSettings
  (JNIEnv *, jclass) {
    FixedConstraintSettings * const pResult = new FixedConstraintSettings();
    TRACE_NEW("FixedConstraintSettings", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_FixedConstraintSettings
 * Method:    getAutoDetectPoint
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_FixedConstraintSettings_getAutoDetectPoint
  (JNIEnv *, jclass, jlong settingsVa) {
    const FixedConstraintSettings * const pSettings
            = reinterpret_cast<FixedConstraintSettings *> (settingsVa);
    const bool result = pSettings->mAutoDetectPoint;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_FixedConstraintSettings
 * Method:    getPoint1X
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_FixedConstraintSettings_getPoint1X
  (JNIEnv *, jclass, jlong settingsVa) {
    const FixedConstraintSettings * const pSettings
            = reinterpret_cast<FixedConstraintSettings *> (settingsVa);
    const Real result = pSettings->mPoint1.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_FixedConstraintSettings
 * Method:    getPoint1Y
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_FixedConstraintSettings_getPoint1Y
  (JNIEnv *, jclass, jlong settingsVa) {
    const FixedConstraintSettings * const pSettings
            = reinterpret_cast<FixedConstraintSettings *> (settingsVa);
    const Real result = pSettings->mPoint1.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_FixedConstraintSettings
 * Method:    getPoint1Z
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_FixedConstraintSettings_getPoint1Z
  (JNIEnv *, jclass, jlong settingsVa) {
    const FixedConstraintSettings * const pSettings
            = reinterpret_cast<FixedConstraintSettings *> (settingsVa);
    const Real result = pSettings->mPoint1.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_FixedConstraintSettings
 * Method:    getPoint2X
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_FixedConstraintSettings_getPoint2X
  (JNIEnv *, jclass, jlong settingsVa) {
    const FixedConstraintSettings * const pSettings
            = reinterpret_cast<FixedConstraintSettings *> (settingsVa);
    const Real result = pSettings->mPoint2.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_FixedConstraintSettings
 * Method:    getPoint2Y
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_FixedConstraintSettings_getPoint2Y
  (JNIEnv *, jclass, jlong settingsVa) {
    const FixedConstraintSettings * const pSettings
            = reinterpret_cast<FixedConstraintSettings *> (settingsVa);
    const Real result = pSettings->mPoint2.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_FixedConstraintSettings
 * Method:    getPoint2Z
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_FixedConstraintSettings_getPoint2Z
  (JNIEnv *, jclass, jlong settingsVa) {
    const FixedConstraintSettings * const pSettings
            = reinterpret_cast<FixedConstraintSettings *> (settingsVa);
    const Real result = pSettings->mPoint2.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_FixedConstraintSettings
 * Method:    getSpace
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_FixedConstraintSettings_getSpace
  (JNIEnv *, jclass, jlong settingsVa) {
    const FixedConstraintSettings * const pSettings
            = reinterpret_cast<FixedConstraintSettings *> (settingsVa);
    const EConstraintSpace result = pSettings->mSpace;
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_FixedConstraintSettings
 * Method:    setAutoDetectPoint
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_FixedConstraintSettings_setAutoDetectPoint
  (JNIEnv *, jclass, jlong settingsVa, jboolean setting) {
    FixedConstraintSettings * const pSettings
            = reinterpret_cast<FixedConstraintSettings *> (settingsVa);
    pSettings->mAutoDetectPoint = setting;
}

/*
 * Class:     com_github_stephengold_joltjni_FixedConstraintSettings
 * Method:    setPoint1
 * Signature: (JDDD)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_FixedConstraintSettings_setPoint1
  (JNIEnv *, jclass, jlong settingsVa, jdouble x, jdouble y, jdouble z) {
    FixedConstraintSettings * const pSettings
            = reinterpret_cast<FixedConstraintSettings *> (settingsVa);
    const RVec3 location(x, y, z);
    pSettings->mPoint1 = location;
}

/*
 * Class:     com_github_stephengold_joltjni_FixedConstraintSettings
 * Method:    setPoint2
 * Signature: (JDDD)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_FixedConstraintSettings_setPoint2
  (JNIEnv *, jclass, jlong settingsVa, jdouble x, jdouble y, jdouble z) {
    FixedConstraintSettings * const pSettings
            = reinterpret_cast<FixedConstraintSettings *> (settingsVa);
    const RVec3 location(x, y, z);
    pSettings->mPoint2 = location;
}

/*
 * Class:     com_github_stephengold_joltjni_FixedConstraintSettings
 * Method:    setSpace
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_FixedConstraintSettings_setSpace
  (JNIEnv *, jclass, jlong settingsVa, jint ordinal) {
    FixedConstraintSettings * const pSettings
            = reinterpret_cast<FixedConstraintSettings *> (settingsVa);
    pSettings->mSpace = (EConstraintSpace) ordinal;
}