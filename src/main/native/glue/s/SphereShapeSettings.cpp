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
#include "Jolt/Physics/Collision/Shape/SphereShape.h"
#include "auto/com_github_stephengold_joltjni_SphereShapeSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_SphereShapeSettings
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SphereShapeSettings_createCopy
  BODYOF_CREATE_COPY_TARGET(SphereShapeSettings)

/*
 * Class:     com_github_stephengold_joltjni_SphereShapeSettings
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SphereShapeSettings_createDefault
  BODYOF_CREATE_DEFAULT_TARGET(SphereShapeSettings)

/*
 * Class:     com_github_stephengold_joltjni_SphereShapeSettings
 * Method:    createSphereShapeSettings
 * Signature: (FJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SphereShapeSettings_createSphereShapeSettings
  (JNIEnv *, jclass, jfloat radius, jlong materialVa) {
    const PhysicsMaterial * const pMaterial
            = reinterpret_cast<PhysicsMaterial *> (materialVa);
    SphereShapeSettings * const pResult
            = new SphereShapeSettings(radius, pMaterial);
    TRACE_NEW_TARGET("SphereShapeSettings", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SphereShapeSettings
 * Method:    getRadius
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_SphereShapeSettings_getRadius
  (JNIEnv *, jclass, jlong settingsVa) {
    const SphereShapeSettings * const pSettings
            = reinterpret_cast<SphereShapeSettings *> (settingsVa);
    const float result = pSettings->mRadius;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SphereShapeSettings
 * Method:    setRadius
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SphereShapeSettings_setRadius
  (JNIEnv *, jclass, jlong settingsVa, jfloat radius) {
    SphereShapeSettings * const pSettings
            = reinterpret_cast<SphereShapeSettings *> (settingsVa);
    pSettings->mRadius = radius;
}