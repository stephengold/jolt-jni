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
#include "Jolt/Physics/Collision/Shape/CompoundShape.h"
#include "auto/com_github_stephengold_joltjni_CompoundShapeSettings.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_CompoundShapeSettings
 * Method:    addShapeRef
 * Signature: (JFFFFFFFJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CompoundShapeSettings_addShapeRef
  (JNIEnv *, jclass, jlong settingsVa, jfloat offsetX, jfloat offsetY, jfloat offsetZ,
  jfloat rotX, jfloat rotY, jfloat rotZ, jfloat rotW, jlong subRefVa) {
    CompoundShapeSettings * const pSettings
            = reinterpret_cast<CompoundShapeSettings *> (settingsVa);
    const Vec3 offset(offsetX, offsetY, offsetZ);
    const Quat rotation(rotX, rotY, rotZ, rotW);
    const ShapeRefC * const pSubShapeRef = reinterpret_cast<ShapeRefC *> (subRefVa);
    const Shape * const pSubShape = pSubShapeRef->GetPtr();
    pSettings->AddShape(offset, rotation, pSubShape);
}

/*
 * Class:     com_github_stephengold_joltjni_CompoundShapeSettings
 * Method:    addShapeSettings
 * Signature: (JFFFFFFFJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CompoundShapeSettings_addShapeSettings
  (JNIEnv *, jclass, jlong settingsVa, jfloat offsetX, jfloat offsetY, jfloat offsetZ,
  jfloat rotX, jfloat rotY, jfloat rotZ, jfloat rotW, jlong subSettingsVa) {
    CompoundShapeSettings * const pSettings
            = reinterpret_cast<CompoundShapeSettings *> (settingsVa);
    const Vec3 offset(offsetX, offsetY, offsetZ);
    const Quat rotation(rotX, rotY, rotZ, rotW);
    const ShapeSettings * const pSubSettings
            = reinterpret_cast<ShapeSettings *> (subSettingsVa);
    pSettings->AddShape(offset, rotation, pSubSettings);
}