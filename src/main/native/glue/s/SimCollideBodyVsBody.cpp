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
#include "Jolt/Physics/PhysicsSystem.h"
#include "auto/com_github_stephengold_joltjni_SimCollideBodyVsBody.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_SimCollideBodyVsBody
 * Method:    collide
 * Signature: (JJJJJJJJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SimCollideBodyVsBody_collide
  (JNIEnv *, jclass, jlong colliderVa, jlong body1Va, jlong body2Va,
  jlong comTransform1Va, jlong comTransform2Va, jlong settingsVa,
  jlong collectorVa, jlong filterVa) {
    const PhysicsSystem::SimCollideBodyVsBody * const pCollider
            = reinterpret_cast<PhysicsSystem::SimCollideBodyVsBody *> (colliderVa);
    const Body * const pBody1 = reinterpret_cast<Body *> (body1Va);
    const Body * const pBody2 = reinterpret_cast<Body *> (body2Va);
    const Mat44 * const pComTransform1
            = reinterpret_cast<Mat44 *> (comTransform1Va);
    const Mat44 * const pComTransform2
            = reinterpret_cast<Mat44 *> (comTransform2Va);
    CollideShapeSettings * const pSettings
            = reinterpret_cast<CollideShapeSettings *> (settingsVa);
    CollideShapeCollector * const pCollector
            = reinterpret_cast<CollideShapeCollector *> (collectorVa);
    const ShapeFilter * const pShapeFilter
            = reinterpret_cast<ShapeFilter *> (filterVa);
    (*pCollider) (*pBody1, *pBody2, *pComTransform1, *pComTransform2,
            *pSettings, *pCollector, *pShapeFilter);
}

/*
 * Class:     com_github_stephengold_joltjni_SimCollideBodyVsBody
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SimCollideBodyVsBody_createDefault
  BODYOF_CREATE_DEFAULT(PhysicsSystem::SimCollideBodyVsBody)

/*
 * Class:     com_github_stephengold_joltjni_SimCollideBodyVsBody
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SimCollideBodyVsBody_free
  BODYOF_FREE(PhysicsSystem::SimCollideBodyVsBody)