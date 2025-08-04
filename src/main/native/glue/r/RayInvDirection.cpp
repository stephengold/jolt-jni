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
#include "Jolt/Geometry/RayAABox.h"
#include "auto/com_github_stephengold_joltjni_RayInvDirection.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_RayInvDirection
 * Method:    create
 * Signature: (FFF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_RayInvDirection_create
  (JNIEnv *pEnv, jclass, jfloat dx, jfloat dy, jfloat dz) {
    const Vec3 direction(dx, dy, dz);
    RayInvDirection * const pResult = new RayInvDirection(direction);
    TRACE_NEW("RayInvDirection", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_RayInvDirection
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_RayInvDirection_createDefault
  BODYOF_CREATE_DEFAULT(RayInvDirection)

/*
 * Class:     com_github_stephengold_joltjni_RayInvDirection
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RayInvDirection_free
  BODYOF_FREE(RayInvDirection)

/*
 * Class:     com_github_stephengold_joltjni_RayInvDirection
 * Method:    getInvDirection
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RayInvDirection_getInvDirection
  (JNIEnv *pEnv, jclass, jlong dirVa, jobject storeFloats) {
    const RayInvDirection * const pDir
            = reinterpret_cast<RayInvDirection *> (dirVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3 result = pDir->mInvDirection;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_RayInvDirection
 * Method:    set
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RayInvDirection_set
  (JNIEnv *, jclass, jlong dirVa, jfloat dx, jfloat dy, jfloat dz) {
    RayInvDirection * const pDir = reinterpret_cast<RayInvDirection *> (dirVa);
    const Vec3 direction(dx, dy, dz);
    pDir->Set(direction);
}

/*
 * Class:     com_github_stephengold_joltjni_RayInvDirection
 * Method:    setInvDirection
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RayInvDirection_setInvDirection
  (JNIEnv *, jclass, jlong dirVa, jfloat ix, jfloat iy, jfloat iz) {
    RayInvDirection * const pDir = reinterpret_cast<RayInvDirection *> (dirVa);
    const Vec3 invDirection(ix, iy, iz);
    pDir->mInvDirection = invDirection;
}