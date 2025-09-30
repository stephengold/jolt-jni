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
#include "Jolt/Physics/Constraints/GearConstraint.h"
#include "auto/com_github_stephengold_joltjni_GearConstraintSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_GearConstraintSettings
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_GearConstraintSettings_createCopy
  BODYOF_CREATE_COPY_TARGET(GearConstraintSettings)

/*
 * Class:     com_github_stephengold_joltjni_GearConstraintSettings
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_GearConstraintSettings_createDefault
  BODYOF_CREATE_DEFAULT_TARGET(GearConstraintSettings)

/*
 * Class:     com_github_stephengold_joltjni_GearConstraintSettings
 * Method:    getHingeAxis1
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_GearConstraintSettings_getHingeAxis1
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeFloats) {
    const GearConstraintSettings * const pSettings
            = reinterpret_cast<GearConstraintSettings *> (settingsVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& result = pSettings->mHingeAxis1;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_GearConstraintSettings
 * Method:    getHingeAxis2
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_GearConstraintSettings_getHingeAxis2
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeFloats) {
    const GearConstraintSettings * const pSettings
            = reinterpret_cast<GearConstraintSettings *> (settingsVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& result = pSettings->mHingeAxis2;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
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