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
#include "Jolt/Physics/Constraints/GearConstraint.h"
#include "auto/com_github_stephengold_joltjni_GearConstraintSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_GearConstraintSettings
 * Method:    createGearConstraintSettings
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_GearConstraintSettings_createGearConstraintSettings
  (JNIEnv *, jclass) {
    GearConstraintSettings * const pResult = new GearConstraintSettings();
    TRACE_NEW("GearConstraintSettings", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_GearConstraintSettings
 * Method:    getHingeAxis1X
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_GearConstraintSettings_getHingeAxis1X
  (JNIEnv *, jclass, jlong settingsVa) {
    const GearConstraintSettings * const pSettings
            = reinterpret_cast<GearConstraintSettings *> (settingsVa);
    const float result = pSettings->mHingeAxis1.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_GearConstraintSettings
 * Method:    getHingeAxis1Y
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_GearConstraintSettings_getHingeAxis1Y
  (JNIEnv *, jclass, jlong settingsVa) {
    const GearConstraintSettings * const pSettings
            = reinterpret_cast<GearConstraintSettings *> (settingsVa);
    const float result = pSettings->mHingeAxis1.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_GearConstraintSettings
 * Method:    getHingeAxis1Z
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_GearConstraintSettings_getHingeAxis1Z
  (JNIEnv *, jclass, jlong settingsVa) {
    const GearConstraintSettings * const pSettings
            = reinterpret_cast<GearConstraintSettings *> (settingsVa);
    const float result = pSettings->mHingeAxis1.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_GearConstraintSettings
 * Method:    getHingeAxis2X
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_GearConstraintSettings_getHingeAxis2X
  (JNIEnv *, jclass, jlong settingsVa) {
    const GearConstraintSettings * const pSettings
            = reinterpret_cast<GearConstraintSettings *> (settingsVa);
    const float result = pSettings->mHingeAxis2.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_GearConstraintSettings
 * Method:    getHingeAxis2Y
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_GearConstraintSettings_getHingeAxis2Y
  (JNIEnv *, jclass, jlong settingsVa) {
    const GearConstraintSettings * const pSettings
            = reinterpret_cast<GearConstraintSettings *> (settingsVa);
    const float result = pSettings->mHingeAxis2.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_GearConstraintSettings
 * Method:    getHingeAxis2Z
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_GearConstraintSettings_getHingeAxis2Z
  (JNIEnv *, jclass, jlong settingsVa) {
    const GearConstraintSettings * const pSettings
            = reinterpret_cast<GearConstraintSettings *> (settingsVa);
    const float result = pSettings->mHingeAxis2.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_GearConstraintSettings
 * Method:    getRatio
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_GearConstraintSettings_getRatio
  (JNIEnv *, jclass, jlong settingsVa) {
    const GearConstraintSettings * const pSettings
            = reinterpret_cast<GearConstraintSettings *> (settingsVa);
    const float result = pSettings->mRatio;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_GearConstraintSettings
 * Method:    getSpace
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_GearConstraintSettings_getSpace
  (JNIEnv *, jclass, jlong settingsVa) {
    const GearConstraintSettings * const pSettings
            = reinterpret_cast<GearConstraintSettings *> (settingsVa);
    const EConstraintSpace result = pSettings->mSpace;
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_GearConstraintSettings
 * Method:    setHingeAxis1
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_GearConstraintSettings_setHingeAxis1
  (JNIEnv *, jclass, jlong settingsVa, jfloat x, jfloat y, jfloat z) {
    GearConstraintSettings * const pSettings
            = reinterpret_cast<GearConstraintSettings *> (settingsVa);
    const Vec3 axis(x, y, z);
    pSettings->mHingeAxis1 = axis;
}

/*
 * Class:     com_github_stephengold_joltjni_GearConstraintSettings
 * Method:    setHingeAxis2
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_GearConstraintSettings_setHingeAxis2
  (JNIEnv *, jclass, jlong settingsVa, jfloat x, jfloat y, jfloat z) {
    GearConstraintSettings * const pSettings
            = reinterpret_cast<GearConstraintSettings *> (settingsVa);
    const Vec3 axis(x, y, z);
    pSettings->mHingeAxis2 = axis;
}

/*
 * Class:     com_github_stephengold_joltjni_GearConstraintSettings
 * Method:    setRatio
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_GearConstraintSettings_setRatio__JF
  (JNIEnv *, jclass, jlong settingsVa, jfloat ratio) {
    GearConstraintSettings * const pSettings
            = reinterpret_cast<GearConstraintSettings *> (settingsVa);
    pSettings->mRatio = ratio;
}

/*
 * Class:     com_github_stephengold_joltjni_GearConstraintSettings
 * Method:    setRatio
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_GearConstraintSettings_setRatio__JII
  (JNIEnv *, jclass, jlong settingsVa, jint numTeeth1, jint numTeeth2) {
    GearConstraintSettings * const pSettings
            = reinterpret_cast<GearConstraintSettings *> (settingsVa);
    pSettings->SetRatio(numTeeth1, numTeeth2);
}

/*
 * Class:     com_github_stephengold_joltjni_GearConstraintSettings
 * Method:    setSpace
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_GearConstraintSettings_setSpace
  (JNIEnv *, jclass, jlong settingsVa, jint ordinal) {
    GearConstraintSettings * const pSettings
            = reinterpret_cast<GearConstraintSettings *> (settingsVa);
    const EConstraintSpace space = (EConstraintSpace) ordinal;
    pSettings->mSpace = space;
}