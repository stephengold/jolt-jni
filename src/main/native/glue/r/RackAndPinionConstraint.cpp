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
#include "Jolt/Physics/Constraints/RackAndPinionConstraint.h"
#include "auto/com_github_stephengold_joltjni_RackAndPinionConstraint.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_RackAndPinionConstraint
 * Method:    setConstraints
 * Signature: (JJJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RackAndPinionConstraint_setConstraints
  (JNIEnv *, jclass, jlong randpVa, jlong pinionVa, jlong rackVa) {
    RackAndPinionConstraint * const pRAndP
            = reinterpret_cast<RackAndPinionConstraint *> (randpVa);
    const Constraint * const pPinion
            = reinterpret_cast<Constraint *> (pinionVa);
    const Constraint * const pRack = reinterpret_cast<Constraint *> (rackVa);
    pRAndP->SetConstraints(pPinion, pRack);
}