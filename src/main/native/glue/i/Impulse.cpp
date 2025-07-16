/*
Copyright (c) 2025 Stephen Gold

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
#include "Jolt/Physics/Collision/EstimateCollisionResponse.h"
#include "auto/com_github_stephengold_joltjni_Impulse.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_Impulse
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Impulse_createDefault
  BODYOF_CREATE_DEFAULT(CollisionEstimationResult::Impulse)

/*
 * Class:     com_github_stephengold_joltjni_Impulse
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_streamutils_IdToShapeMap_free
  BODYOF_FREE(CollisionEstimationResult::Impulse)


/*
 * Class:     com_github_stephengold_joltjni_Impulse
 * Method:    getContactImpulse
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Impulse_getContactImpulse
  (JNIEnv *, jclass, jlong estimateVa) {
    CollisionEstimationResult::Impulse * const pImpulse
            = reinterpret_cast<CollisionEstimationResult::Impulse *> (estimateVa);
    float result = pImpulse->mContactImpulse;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Impulse
 * Method:    getFrictionImpulse1
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Impulse_getFrictionImpulse1
  (JNIEnv *, jclass, jlong estimateVa) {
    CollisionEstimationResult::Impulse * const pImpulse
            = reinterpret_cast<CollisionEstimationResult::Impulse *> (estimateVa);
    float result = pImpulse->mFrictionImpulse1;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Impulse
 * Method:    getFrictionImpulse2
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Impulse_getFrictionImpulse2
  (JNIEnv *, jclass, jlong estimateVa) {
    CollisionEstimationResult::Impulse * const pImpulse
            = reinterpret_cast<CollisionEstimationResult::Impulse *> (estimateVa);
    float result = pImpulse->mFrictionImpulse2;
    return result;
}