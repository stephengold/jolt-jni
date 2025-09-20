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
#include "Jolt/Physics/Collision/Shape/ConvexShape.h"
#include "auto/com_github_stephengold_joltjni_ConvexShapeSettings.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_ConvexShapeSettings
 * Method:    getDensity
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ConvexShapeSettings_getDensity
  (JNIEnv *, jclass, jlong settingsVa) {
    const ConvexShapeSettings * const pSettings
            = reinterpret_cast<ConvexShapeSettings *> (settingsVa);
    const float result = pSettings->mDensity;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ConvexShapeSettings
 * Method:    getMaterial
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ConvexShapeSettings_getMaterial
  (JNIEnv *, jclass, jlong settingsVa) {
    const ConvexShapeSettings * const pSettings
            = reinterpret_cast<ConvexShapeSettings *> (settingsVa);
    const PhysicsMaterial * const pResult = pSettings->mMaterial.GetPtr();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_ConvexShapeSettings
 * Method:    setDensity
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ConvexShapeSettings_setDensity
  (JNIEnv *, jclass, jlong settingsVa, jfloat density) {
    ConvexShapeSettings * const pSettings
            = reinterpret_cast<ConvexShapeSettings *> (settingsVa);
    pSettings->SetDensity(density);
}

/*
 * Class:     com_github_stephengold_joltjni_ConvexShapeSettings
 * Method:    setMaterial
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ConvexShapeSettings_setMaterial
  (JNIEnv *, jclass, jlong settingsVa, jlong materialVa) {
    ConvexShapeSettings * const pSettings
            = reinterpret_cast<ConvexShapeSettings *> (settingsVa);
    PhysicsMaterial * const pMaterial
            = reinterpret_cast<PhysicsMaterial *> (materialVa);
    pSettings->mMaterial = pMaterial;
}