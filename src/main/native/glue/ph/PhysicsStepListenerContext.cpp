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
#include "Jolt/Physics/PhysicsStepListener.h"
#include "auto/com_github_stephengold_joltjni_PhysicsStepListenerContext.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_PhysicsStepListenerContext
 * Method:    getDeltaTime
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_PhysicsStepListenerContext_getDeltaTime
  (JNIEnv *, jclass, jlong contextVa) {
    const PhysicsStepListenerContext * const pContext
            = reinterpret_cast<PhysicsStepListenerContext *> (contextVa);
    const float result = pContext->mDeltaTime;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsStepListenerContext
 * Method:    getIsFirstStep
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_PhysicsStepListenerContext_getIsFirstStep
  (JNIEnv *, jclass, jlong contextVa) {
    const PhysicsStepListenerContext * const pContext
            = reinterpret_cast<PhysicsStepListenerContext *> (contextVa);
    const bool result = pContext->mIsFirstStep;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsStepListenerContext
 * Method:    getIsLastStep
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_PhysicsStepListenerContext_getIsLastStep
  (JNIEnv *, jclass, jlong contextVa) {
    const PhysicsStepListenerContext * const pContext
            = reinterpret_cast<PhysicsStepListenerContext *> (contextVa);
    const bool result = pContext->mIsLastStep;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_PhysicsStepListenerContext
 * Method:    getPhysicsSystem
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_PhysicsStepListenerContext_getPhysicsSystem
  (JNIEnv *, jclass, jlong contextVa) {
    const PhysicsStepListenerContext * const pContext
            = reinterpret_cast<PhysicsStepListenerContext *> (contextVa);
    PhysicsSystem * const pResult = pContext->mPhysicsSystem;
    return reinterpret_cast<jlong> (pResult);
}