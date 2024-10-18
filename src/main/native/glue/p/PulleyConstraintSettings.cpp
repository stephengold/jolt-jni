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
#include "Jolt/Physics/Constraints/PulleyConstraint.h"
#include "auto/com_github_stephengold_joltjni_PulleyConstraintSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_PulleyConstraintSettings
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PulleyConstraintSettings_createDefault
  (JNIEnv *, jclass) {
    PulleyConstraintSettings * const pResult = new PulleyConstraintSettings();
    TRACE_NEW("PulleyConstraintSettings", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_PulleyConstraintSettings
 * Method:    getBodyPoint1X
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_PulleyConstraintSettings_getBodyPoint1X
  (JNIEnv *, jclass, jlong settingsVa) {
    const PulleyConstraintSettings * const pSettings
            = reinterpret_cast<PulleyConstraintSettings *> (settingsVa);
    const Real result = pSettings->mBodyPoint1.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PulleyConstraintSettings
 * Method:    getBodyPoint1Y
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_PulleyConstraintSettings_getBodyPoint1Y
  (JNIEnv *, jclass, jlong settingsVa) {
    const PulleyConstraintSettings * const pSettings
            = reinterpret_cast<PulleyConstraintSettings *> (settingsVa);
    const Real result = pSettings->mBodyPoint1.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PulleyConstraintSettings
 * Method:    getBodyPoint1Z
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_PulleyConstraintSettings_getBodyPoint1Z
  (JNIEnv *, jclass, jlong settingsVa) {
    const PulleyConstraintSettings * const pSettings
            = reinterpret_cast<PulleyConstraintSettings *> (settingsVa);
    const Real result = pSettings->mBodyPoint1.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PulleyConstraintSettings
 * Method:    getBodyPoint2X
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_PulleyConstraintSettings_getBodyPoint2X
  (JNIEnv *, jclass, jlong settingsVa) {
    const PulleyConstraintSettings * const pSettings
            = reinterpret_cast<PulleyConstraintSettings *> (settingsVa);
    const Real result = pSettings->mBodyPoint2.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PulleyConstraintSettings
 * Method:    getBodyPoint2Y
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_PulleyConstraintSettings_getBodyPoint2Y
  (JNIEnv *, jclass, jlong settingsVa) {
    const PulleyConstraintSettings * const pSettings
            = reinterpret_cast<PulleyConstraintSettings *> (settingsVa);
    const Real result = pSettings->mBodyPoint2.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PulleyConstraintSettings
 * Method:    getBodyPoint2Z
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_PulleyConstraintSettings_getBodyPoint2Z
  (JNIEnv *, jclass, jlong settingsVa) {
    const PulleyConstraintSettings * const pSettings
            = reinterpret_cast<PulleyConstraintSettings *> (settingsVa);
    const Real result = pSettings->mBodyPoint2.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PulleyConstraintSettings
 * Method:    getFixedPoint1X
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_PulleyConstraintSettings_getFixedPoint1X
  (JNIEnv *, jclass, jlong settingsVa) {
    const PulleyConstraintSettings * const pSettings
            = reinterpret_cast<PulleyConstraintSettings *> (settingsVa);
    const Real result = pSettings->mFixedPoint1.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PulleyConstraintSettings
 * Method:    getFixedPoint1Y
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_PulleyConstraintSettings_getFixedPoint1Y
  (JNIEnv *, jclass, jlong settingsVa) {
    const PulleyConstraintSettings * const pSettings
            = reinterpret_cast<PulleyConstraintSettings *> (settingsVa);
    const Real result = pSettings->mFixedPoint1.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PulleyConstraintSettings
 * Method:    getFixedPoint1Z
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_PulleyConstraintSettings_getFixedPoint1Z
  (JNIEnv *, jclass, jlong settingsVa) {
    const PulleyConstraintSettings * const pSettings
            = reinterpret_cast<PulleyConstraintSettings *> (settingsVa);
    const Real result = pSettings->mFixedPoint1.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PulleyConstraintSettings
 * Method:    getFixedPoint2X
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_PulleyConstraintSettings_getFixedPoint2X
  (JNIEnv *, jclass, jlong settingsVa) {
    const PulleyConstraintSettings * const pSettings
            = reinterpret_cast<PulleyConstraintSettings *> (settingsVa);
    const Real result = pSettings->mFixedPoint2.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PulleyConstraintSettings
 * Method:    getFixedPoint2Y
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_PulleyConstraintSettings_getFixedPoint2Y
  (JNIEnv *, jclass, jlong settingsVa) {
    const PulleyConstraintSettings * const pSettings
            = reinterpret_cast<PulleyConstraintSettings *> (settingsVa);
    const Real result = pSettings->mFixedPoint2.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PulleyConstraintSettings
 * Method:    getFixedPoint2Z
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_PulleyConstraintSettings_getFixedPoint2Z
  (JNIEnv *, jclass, jlong settingsVa) {
    const PulleyConstraintSettings * const pSettings
            = reinterpret_cast<PulleyConstraintSettings *> (settingsVa);
    const Real result = pSettings->mFixedPoint2.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PulleyConstraintSettings
 * Method:    getMaxLength
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PulleyConstraintSettings_getMaxLength
  (JNIEnv *, jclass, jlong settingsVa) {
    const PulleyConstraintSettings * const pSettings
            = reinterpret_cast<PulleyConstraintSettings *> (settingsVa);
    const float result = pSettings->mMaxLength;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PulleyConstraintSettings
 * Method:    getMinLength
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PulleyConstraintSettings_getMinLength
  (JNIEnv *, jclass, jlong settingsVa) {
    const PulleyConstraintSettings * const pSettings
            = reinterpret_cast<PulleyConstraintSettings *> (settingsVa);
    const float result = pSettings->mMinLength;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PulleyConstraintSettings
 * Method:    getRatio
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PulleyConstraintSettings_getRatio
  (JNIEnv *, jclass, jlong settingsVa) {
    const PulleyConstraintSettings * const pSettings
            = reinterpret_cast<PulleyConstraintSettings *> (settingsVa);
    const float result = pSettings->mRatio;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PulleyConstraintSettings
 * Method:    getSpace
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_PulleyConstraintSettings_getSpace
  (JNIEnv *, jclass, jlong settingsVa) {
    const PulleyConstraintSettings * const pSettings
            = reinterpret_cast<PulleyConstraintSettings *> (settingsVa);
    const EConstraintSpace result = pSettings->mSpace;
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_PulleyConstraintSettings
 * Method:    setBodyPoint1
 * Signature: (JDDD)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PulleyConstraintSettings_setBodyPoint1
  (JNIEnv *, jclass, jlong settingsVa, jdouble locX, jdouble locY, jdouble locZ) {
    PulleyConstraintSettings * const pSettings
            = reinterpret_cast<PulleyConstraintSettings *> (settingsVa);
    const RVec3 location(locX, locY, locZ);
    pSettings->mBodyPoint1 = location;
}

/*
 * Class:     com_github_stephengold_joltjni_PulleyConstraintSettings
 * Method:    setBodyPoint2
 * Signature: (JDDD)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PulleyConstraintSettings_setBodyPoint2
  (JNIEnv *, jclass, jlong settingsVa, jdouble locX, jdouble locY, jdouble locZ) {
    PulleyConstraintSettings * const pSettings
            = reinterpret_cast<PulleyConstraintSettings *> (settingsVa);
    const RVec3 location(locX, locY, locZ);
    pSettings->mBodyPoint2 = location;
}

/*
 * Class:     com_github_stephengold_joltjni_PulleyConstraintSettings
 * Method:    setFixedPoint1
 * Signature: (JDDD)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PulleyConstraintSettings_setFixedPoint1
  (JNIEnv *, jclass, jlong settingsVa, jdouble locX, jdouble locY, jdouble locZ) {
    PulleyConstraintSettings * const pSettings
            = reinterpret_cast<PulleyConstraintSettings *> (settingsVa);
    const RVec3 location(locX, locY, locZ);
    pSettings->mFixedPoint1 = location;
}

/*
 * Class:     com_github_stephengold_joltjni_PulleyConstraintSettings
 * Method:    setFixedPoint2
 * Signature: (JDDD)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PulleyConstraintSettings_setFixedPoint2
  (JNIEnv *, jclass, jlong settingsVa, jdouble locX, jdouble locY, jdouble locZ) {
    PulleyConstraintSettings * const pSettings
            = reinterpret_cast<PulleyConstraintSettings *> (settingsVa);
    const RVec3 location(locX, locY, locZ);
    pSettings->mFixedPoint2 = location;
}

/*
 * Class:     com_github_stephengold_joltjni_PulleyConstraintSettings
 * Method:    setMaxLength
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PulleyConstraintSettings_setMaxLength
  (JNIEnv *, jclass, jlong settingsVa, jfloat length) {
    PulleyConstraintSettings * const pSettings
            = reinterpret_cast<PulleyConstraintSettings *> (settingsVa);
    pSettings->mMaxLength = length;
}

/*
 * Class:     com_github_stephengold_joltjni_PulleyConstraintSettings
 * Method:    setMinLength
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PulleyConstraintSettings_setMinLength
  (JNIEnv *, jclass, jlong settingsVa, jfloat length) {
    PulleyConstraintSettings * const pSettings
            = reinterpret_cast<PulleyConstraintSettings *> (settingsVa);
    pSettings->mMinLength = length;
}

/*
 * Class:     com_github_stephengold_joltjni_PulleyConstraintSettings
 * Method:    setRatio
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PulleyConstraintSettings_setRatio
  (JNIEnv *, jclass, jlong settingsVa, jfloat ratio) {
    PulleyConstraintSettings * const pSettings
            = reinterpret_cast<PulleyConstraintSettings *> (settingsVa);
    pSettings->mRatio = ratio;
}

/*
 * Class:     com_github_stephengold_joltjni_PulleyConstraintSettings
 * Method:    setSpace
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PulleyConstraintSettings_setSpace
  (JNIEnv *, jclass, jlong settingsVa, jint ordinal) {
    PulleyConstraintSettings * const pSettings
            = reinterpret_cast<PulleyConstraintSettings *> (settingsVa);
    const EConstraintSpace space = (EConstraintSpace) ordinal;
    pSettings->mSpace = space;
}
