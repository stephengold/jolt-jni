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
#include "Jolt/Physics/Constraints/PathConstraint.h"
#include "auto/com_github_stephengold_joltjni_PathConstraintSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_PathConstraintSettings
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PathConstraintSettings_createDefault
  BODYOF_CREATE_DEFAULT(PathConstraintSettings)

/*
 * Class:     com_github_stephengold_joltjni_PathConstraintSettings
 * Method:    getMaxFrictionForce
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PathConstraintSettings_getMaxFrictionForce
  (JNIEnv *, jclass, jlong settingsVa) {
    const PathConstraintSettings * const pSettings
            = reinterpret_cast<PathConstraintSettings *> (settingsVa);
    const float result = pSettings->mMaxFrictionForce;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraintSettings
 * Method:    getPath
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PathConstraintSettings_getPath
  (JNIEnv *, jclass, jlong settingsVa) {
    const PathConstraintSettings * const pSettings
            = reinterpret_cast<PathConstraintSettings *> (settingsVa);
    RefConst<PathConstraintPath> const ref = pSettings->mPath;
    const PathConstraintPath * const pResult = ref.GetPtr();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraintSettings
 * Method:    getPathFraction
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PathConstraintSettings_getPathFraction
  (JNIEnv *, jclass, jlong settingsVa) {
    const PathConstraintSettings * const pSettings
            = reinterpret_cast<PathConstraintSettings *> (settingsVa);
    const float result = pSettings->mPathFraction;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraintSettings
 * Method:    getPathPositionX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PathConstraintSettings_getPathPositionX
  (JNIEnv *, jclass, jlong settingsVa) {
    const PathConstraintSettings * const pSettings
            = reinterpret_cast<PathConstraintSettings *> (settingsVa);
    const float result = pSettings->mPathPosition.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraintSettings
 * Method:    getPathPositionY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PathConstraintSettings_getPathPositionY
  (JNIEnv *, jclass, jlong settingsVa) {
    const PathConstraintSettings * const pSettings
            = reinterpret_cast<PathConstraintSettings *> (settingsVa);
    const float result = pSettings->mPathPosition.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraintSettings
 * Method:    getPathPositionZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PathConstraintSettings_getPathPositionZ
  (JNIEnv *, jclass, jlong settingsVa) {
    const PathConstraintSettings * const pSettings
            = reinterpret_cast<PathConstraintSettings *> (settingsVa);
    const float result = pSettings->mPathPosition.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraintSettings
 * Method:    getPathRotationW
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PathConstraintSettings_getPathRotationW
  (JNIEnv *, jclass, jlong settingsVa) {
    const PathConstraintSettings * const pSettings
            = reinterpret_cast<PathConstraintSettings *> (settingsVa);
    const float result = pSettings->mPathRotation.GetW();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraintSettings
 * Method:    getPathRotationX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PathConstraintSettings_getPathRotationX
  (JNIEnv *, jclass, jlong settingsVa) {
    const PathConstraintSettings * const pSettings
            = reinterpret_cast<PathConstraintSettings *> (settingsVa);
    const float result = pSettings->mPathRotation.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraintSettings
 * Method:    getPathRotationY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PathConstraintSettings_getPathRotationY
  (JNIEnv *, jclass, jlong settingsVa) {
    const PathConstraintSettings * const pSettings
            = reinterpret_cast<PathConstraintSettings *> (settingsVa);
    const float result = pSettings->mPathRotation.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraintSettings
 * Method:    getPathRotationZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PathConstraintSettings_getPathRotationZ
  (JNIEnv *, jclass, jlong settingsVa) {
    const PathConstraintSettings * const pSettings
            = reinterpret_cast<PathConstraintSettings *> (settingsVa);
    const float result = pSettings->mPathRotation.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraintSettings
 * Method:    getPositionMotorSettings
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PathConstraintSettings_getPositionMotorSettings
  (JNIEnv *, jclass, jlong constraintSettingsVa) {
    PathConstraintSettings * const pConstraintSettings
            = reinterpret_cast<PathConstraintSettings *> (constraintSettingsVa);
    MotorSettings * const pResult
            = &pConstraintSettings->mPositionMotorSettings;
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraintSettings
 * Method:    getRotationConstraintType
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_PathConstraintSettings_getRotationConstraintType
  (JNIEnv *, jclass, jlong settingsVa) {
    const PathConstraintSettings * const pSettings
            = reinterpret_cast<PathConstraintSettings *> (settingsVa);
    const EPathRotationConstraintType result
            = pSettings->mRotationConstraintType;
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraintSettings
 * Method:    setMaxFrictionForce
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PathConstraintSettings_setMaxFrictionForce
  (JNIEnv *, jclass, jlong settingsVa, jfloat force) {
    PathConstraintSettings * const pSettings
            = reinterpret_cast<PathConstraintSettings *> (settingsVa);
    pSettings->mMaxFrictionForce = force;
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraintSettings
 * Method:    setPath
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PathConstraintSettings_setPath
  (JNIEnv *, jclass, jlong settingsVa, jlong pathVa) {
    PathConstraintSettings * const pSettings
            = reinterpret_cast<PathConstraintSettings *> (settingsVa);
    const PathConstraintPath * const pPath = pSettings->mPath;
    pSettings->mPath = pPath;
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraintSettings
 * Method:    setPathFraction
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PathConstraintSettings_setPathFraction
  (JNIEnv *, jclass, jlong settingsVa, jfloat amount) {
    PathConstraintSettings * const pSettings
            = reinterpret_cast<PathConstraintSettings *> (settingsVa);
    pSettings->mPathFraction = amount;
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraintSettings
 * Method:    setPathPosition
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PathConstraintSettings_setPathPosition
  (JNIEnv *, jclass, jlong settingsVa, jfloat x, jfloat y, jfloat z) {
    PathConstraintSettings * const pSettings
            = reinterpret_cast<PathConstraintSettings *> (settingsVa);
    const Vec3 position(x, y, z);
    pSettings->mPathPosition = position;
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraintSettings
 * Method:    setPathRotation
 * Signature: (JFFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PathConstraintSettings_setPathRotation
  (JNIEnv *, jclass, jlong settingsVa, jfloat qx, jfloat qy, jfloat qz, jfloat qw) {
    PathConstraintSettings * const pSettings
            = reinterpret_cast<PathConstraintSettings *> (settingsVa);
    const Quat orientation(qx, qy, qz, qw);
    pSettings->mPathRotation = orientation;
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraintSettings
 * Method:    setPositionMotorSettings
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PathConstraintSettings_setPositionMotorSettings
  (JNIEnv *, jclass, jlong constraintSettingsVa, jlong motorSettingsVa) {
    PathConstraintSettings * const pConstraintSettings
            = reinterpret_cast<PathConstraintSettings *> (constraintSettingsVa);
    const MotorSettings * const pMotorSettings
            = reinterpret_cast<MotorSettings *> (motorSettingsVa);
    pConstraintSettings->mPositionMotorSettings = *pMotorSettings;
}

/*
 * Class:     com_github_stephengold_joltjni_PathConstraintSettings
 * Method:    setRotationConstraintType
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_PathConstraintSettings_setRotationConstraintType
  (JNIEnv *, jclass, jlong settingsVa, jint ordinal) {
    PathConstraintSettings * const pSettings
            = reinterpret_cast<PathConstraintSettings *> (settingsVa);
    const EPathRotationConstraintType constraintType
            = (EPathRotationConstraintType)ordinal;
    pSettings->mRotationConstraintType = constraintType;
}