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
#include "Jolt/Physics/Constraints/DistanceConstraint.h"
#include "auto/com_github_stephengold_joltjni_DistanceConstraintSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_DistanceConstraintSettings
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_DistanceConstraintSettings_createCopy
  BODYOF_CREATE_COPY_TARGET(DistanceConstraintSettings)

/*
 * Class:     com_github_stephengold_joltjni_DistanceConstraintSettings
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_DistanceConstraintSettings_createDefault
  BODYOF_CREATE_DEFAULT_TARGET(DistanceConstraintSettings)

/*
 * Class:     com_github_stephengold_joltjni_DistanceConstraintSettings
 * Method:    getLimitsSpringSettings
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_DistanceConstraintSettings_getLimitsSpringSettings
  (JNIEnv *, jclass, jlong settingsVa) {
    DistanceConstraintSettings * const pSettings
            = reinterpret_cast<DistanceConstraintSettings *> (settingsVa);
    SpringSettings * const pResult = &pSettings->mLimitsSpringSettings;
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_DistanceConstraintSettings
 * Method:    getMaxDistance
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_DistanceConstraintSettings_getMaxDistance
  (JNIEnv *, jclass, jlong settingsVa) {
    const DistanceConstraintSettings * const pSettings
            = reinterpret_cast<DistanceConstraintSettings *> (settingsVa);
    const float result = pSettings->mMaxDistance;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_DistanceConstraintSettings
 * Method:    getMinDistance
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_DistanceConstraintSettings_getMinDistance
  (JNIEnv *, jclass, jlong settingsVa) {
    const DistanceConstraintSettings * const pSettings
            = reinterpret_cast<DistanceConstraintSettings *> (settingsVa);
    const float result = pSettings->mMinDistance;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_DistanceConstraintSettings
 * Method:    getPoint1
 * Signature: (JLjava/nio/DoubleBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DistanceConstraintSettings_getPoint1
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeDoubles) {
    const DistanceConstraintSettings * const pSettings
            = reinterpret_cast<DistanceConstraintSettings *> (settingsVa);
    DIRECT_DOUBLE_BUFFER(pEnv, storeDoubles, pDoubles, capacityDoubles);
    JPH_ASSERT(capacityDoubles >= 3);
    const RVec3& result = pSettings->mPoint1;
    pDoubles[0] = result.GetX();
    pDoubles[1] = result.GetY();
    pDoubles[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_DistanceConstraintSettings
 * Method:    getPoint2
 * Signature: (JLjava/nio/DoubleBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DistanceConstraintSettings_getPoint2
  (JNIEnv *pEnv, jclass, jlong settingsVa, jobject storeDoubles) {
    const DistanceConstraintSettings * const pSettings
            = reinterpret_cast<DistanceConstraintSettings *> (settingsVa);
    DIRECT_DOUBLE_BUFFER(pEnv, storeDoubles, pDoubles, capacityDoubles);
    JPH_ASSERT(capacityDoubles >= 3);
    const RVec3& result = pSettings->mPoint2;
    pDoubles[0] = result.GetX();
    pDoubles[1] = result.GetY();
    pDoubles[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_DistanceConstraintSettings
 * Method:    getSpace
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_DistanceConstraintSettings_getSpace
  (JNIEnv *, jclass, jlong settingsVa) {
    const DistanceConstraintSettings * const pSettings
            = reinterpret_cast<DistanceConstraintSettings *> (settingsVa);
    const EConstraintSpace result = pSettings->mSpace;
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_DistanceConstraintSettings
 * Method:    setMaxDistance
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DistanceConstraintSettings_setMaxDistance
  (JNIEnv *, jclass, jlong settingsVa, jfloat distance) {
    DistanceConstraintSettings * const pSettings
            = reinterpret_cast<DistanceConstraintSettings *> (settingsVa);
    pSettings->mMaxDistance = distance;
}

/*
 * Class:     com_github_stephengold_joltjni_DistanceConstraintSettings
 * Method:    setMinDistance
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DistanceConstraintSettings_setMinDistance
  (JNIEnv *, jclass, jlong settingsVa, jfloat distance) {
    DistanceConstraintSettings * const pSettings
            = reinterpret_cast<DistanceConstraintSettings *> (settingsVa);
    pSettings->mMinDistance = distance;
}

/*
 * Class:     com_github_stephengold_joltjni_DistanceConstraintSettings
 * Method:    setPoint1
 * Signature: (JDDD)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DistanceConstraintSettings_setPoint1
  (JNIEnv *, jclass, jlong settingsVa, jdouble x, jdouble y, jdouble z) {
    DistanceConstraintSettings * const pSettings
            = reinterpret_cast<DistanceConstraintSettings *> (settingsVa);
    const RVec3 location(x, y, z);
    pSettings->mPoint1 = location;
}

/*
 * Class:     com_github_stephengold_joltjni_DistanceConstraintSettings
 * Method:    setPoint2
 * Signature: (JDDD)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DistanceConstraintSettings_setPoint2
  (JNIEnv *, jclass, jlong settingsVa, jdouble x, jdouble y, jdouble z) {
    DistanceConstraintSettings * const pSettings
            = reinterpret_cast<DistanceConstraintSettings *> (settingsVa);
    const RVec3 location(x, y, z);
    pSettings->mPoint2 = location;
}

/*
 * Class:     com_github_stephengold_joltjni_DistanceConstraintSettings
 * Method:    setSpace
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DistanceConstraintSettings_setSpace
  (JNIEnv *, jclass, jlong settingsVa, jint ordinal) {
    DistanceConstraintSettings * const pSettings
            = reinterpret_cast<DistanceConstraintSettings *> (settingsVa);
    pSettings->mSpace = (EConstraintSpace) ordinal;
}