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
#include "Jolt/Physics/Constraints/FixedConstraint.h"
#include "auto/com_github_stephengold_joltjni_FixedConstraintSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_FixedConstraintSettings
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_FixedConstraintSettings_createCopy
  BODYOF_CREATE_COPY(FixedConstraintSettings)

/*
 * Class:     com_github_stephengold_joltjni_FixedConstraintSettings
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_FixedConstraintSettings_createDefault
  BODYOF_CREATE_DEFAULT(FixedConstraintSettings)

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
 * Method:    getAxisX1
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_FixedConstraintSettings_getAxisX1
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeFloats) {
    const FixedConstraintSettings * const pSettings
            = reinterpret_cast<FixedConstraintSettings *> (settingsVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& result = pSettings->mAxisX1;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_FixedConstraintSettings
 * Method:    getAxisX2
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_FixedConstraintSettings_getAxisX2
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeFloats) {
    const FixedConstraintSettings * const pSettings
            = reinterpret_cast<FixedConstraintSettings *> (settingsVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& result = pSettings->mAxisX2;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_FixedConstraintSettings
 * Method:    getAxisY1
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_FixedConstraintSettings_getAxisY1
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeFloats) {
    const FixedConstraintSettings * const pSettings
            = reinterpret_cast<FixedConstraintSettings *> (settingsVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& result = pSettings->mAxisY1;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_FixedConstraintSettings
 * Method:    getAxisY2
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_FixedConstraintSettings_getAxisY2
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeFloats) {
    const FixedConstraintSettings * const pSettings
            = reinterpret_cast<FixedConstraintSettings *> (settingsVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& result = pSettings->mAxisY2;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_FixedConstraintSettings
 * Method:    getPoint1
 * Signature: (JLjava/nio/DoubleBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_FixedConstraintSettings_getPoint1
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeDoubles) {
   const FixedConstraintSettings * const pSettings
            = reinterpret_cast<FixedConstraintSettings *> (settingsVa);
    DIRECT_DOUBLE_BUFFER(pEnv, storeDoubles, pDoubles, capacityDoubles);
    JPH_ASSERT(capacityDoubles >= 3);
    const RVec3& result = pSettings->mPoint1;
    pDoubles[0] = result.GetX();
    pDoubles[1] = result.GetY();
    pDoubles[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_FixedConstraintSettings
 * Method:    getPoint2
 * Signature: (JLjava/nio/DoubleBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_FixedConstraintSettings_getPoint2
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeDoubles) {
   const FixedConstraintSettings * const pSettings
            = reinterpret_cast<FixedConstraintSettings *> (settingsVa);
    DIRECT_DOUBLE_BUFFER(pEnv, storeDoubles, pDoubles, capacityDoubles);
    JPH_ASSERT(capacityDoubles >= 3);
    const RVec3& result = pSettings->mPoint2;
    pDoubles[0] = result.GetX();
    pDoubles[1] = result.GetY();
    pDoubles[2] = result.GetZ();
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
 * Method:    setAxisX1
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_FixedConstraintSettings_setAxisX1
  (JNIEnv *, jclass, jlong settingsVa, jfloat x, jfloat y, jfloat z) {
    FixedConstraintSettings * const pSettings
            = reinterpret_cast<FixedConstraintSettings *> (settingsVa);
    const Vec3 vec3(x, y, z);
    pSettings->mAxisX1 = vec3;
}

/*
 * Class:     com_github_stephengold_joltjni_FixedConstraintSettings
 * Method:    setAxisX2
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_FixedConstraintSettings_setAxisX2
  (JNIEnv *, jclass, jlong settingsVa, jfloat x, jfloat y, jfloat z) {
    FixedConstraintSettings * const pSettings
            = reinterpret_cast<FixedConstraintSettings *> (settingsVa);
    const Vec3 vec3(x, y, z);
    pSettings->mAxisX2 = vec3;
}

/*
 * Class:     com_github_stephengold_joltjni_FixedConstraintSettings
 * Method:    setAxisY1
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_FixedConstraintSettings_setAxisY1
  (JNIEnv *, jclass, jlong settingsVa, jfloat x, jfloat y, jfloat z) {
    FixedConstraintSettings * const pSettings
            = reinterpret_cast<FixedConstraintSettings *> (settingsVa);
    const Vec3 vec3(x, y, z);
    pSettings->mAxisY1 = vec3;
}

/*
 * Class:     com_github_stephengold_joltjni_FixedConstraintSettings
 * Method:    setAxisY2
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_FixedConstraintSettings_setAxisY2
  (JNIEnv *, jclass, jlong settingsVa, jfloat x, jfloat y, jfloat z) {
    FixedConstraintSettings * const pSettings
            = reinterpret_cast<FixedConstraintSettings *> (settingsVa);
    const Vec3 vec3(x, y, z);
    pSettings->mAxisY2 = vec3;
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