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
#include "auto/com_github_stephengold_joltjni_GearConstraint.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_GearConstraint
 * Method:    setConstraints
 * Signature: (JJJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_GearConstraint_setConstraints
  (JNIEnv *, jclass, jlong gearVa, jlong hinge1Va, jlong hinge2Va) {
    GearConstraint * const pGear = reinterpret_cast<GearConstraint *> (gearVa);
    const Constraint * const pHinge1 = reinterpret_cast<Constraint *> (hinge1Va);
    const Constraint * const pHinge2 = reinterpret_cast<Constraint *> (hinge2Va);
    pGear->SetConstraints(pHinge1, pHinge2);
}