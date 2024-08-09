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
#include <Jolt/Physics/Constraints/TwoBodyConstraint.h>
#include "auto/com_github_stephengold_joltjni_TwoBodyConstraintSettings.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_TwoBodyConstraintSettings
 * Method:    createConstraint
 * Signature: (JJJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_TwoBodyConstraintSettings_createConstraint
  (JNIEnv *, jclass, jlong settingsVa, jlong body1Va, jlong body2Va) {
    const TwoBodyConstraintSettings * const pSettings
            = reinterpret_cast<TwoBodyConstraintSettings *> (settingsVa);
    Body * const pBody1 = reinterpret_cast<Body *> (body1Va);
    Body * const pBody2 = reinterpret_cast<Body *> (body2Va);
    TwoBodyConstraint * const pResult = pSettings->Create(*pBody1, *pBody2);
    return reinterpret_cast<jlong> (pResult);
}
