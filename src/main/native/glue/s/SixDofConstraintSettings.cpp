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
#include <Jolt/Physics/Constraints/SixDOFConstraint.h>
#include "auto/com_github_stephengold_joltjni_SixDofConstraintSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    createSixDofConstraintSettings
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_createSixDofConstraintSettings
  (JNIEnv *, jclass) {
    SixDOFConstraintSettings * const pResult = new SixDOFConstraintSettings();
    TRACE_NEW("SixDOFConstraintSettings", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    getPosition1X
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_getPosition1X
  (JNIEnv *, jclass, jlong settingsVa) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const double result = pSettings->mPosition1.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    getPosition1Y
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_getPosition1Y
  (JNIEnv *, jclass, jlong settingsVa) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const double result = pSettings->mPosition1.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    getPosition1Z
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_getPosition1Z
  (JNIEnv *, jclass, jlong settingsVa) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const double result = pSettings->mPosition1.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    getPosition2X
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_getPosition2X
  (JNIEnv *, jclass, jlong settingsVa) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const double result = pSettings->mPosition2.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    getPosition2Y
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_getPosition2Y
  (JNIEnv *, jclass, jlong settingsVa) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const double result = pSettings->mPosition2.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    getPosition2Z
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_getPosition2Z
  (JNIEnv *, jclass, jlong settingsVa) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const double result = pSettings->mPosition2.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    getSpace
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_getSpace
  (JNIEnv *, jclass, jlong settingsVa) {
    const SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const EConstraintSpace result = pSettings->mSpace;
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    setPosition1
 * Signature: (JDDD)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_setPosition1
  (JNIEnv *, jclass, jlong settingsVa, jdouble x, jdouble y, jdouble z) {
    SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const RVec3 location(x, y, z);
    pSettings->mPosition1 = location;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    setPosition2
 * Signature: (JDDD)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_setPosition2
  (JNIEnv *, jclass, jlong settingsVa, jdouble x, jdouble y, jdouble z) {
    SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    const RVec3 location(x, y, z);
    pSettings->mPosition2 = location;
}

/*
 * Class:     com_github_stephengold_joltjni_SixDofConstraintSettings
 * Method:    setSpace
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SixDofConstraintSettings_setSpace
  (JNIEnv *, jclass, jlong settingsVa, jint ordinal) {
    SixDOFConstraintSettings * const pSettings
            = reinterpret_cast<SixDOFConstraintSettings *> (settingsVa);
    pSettings->mSpace = (EConstraintSpace) ordinal;
}
