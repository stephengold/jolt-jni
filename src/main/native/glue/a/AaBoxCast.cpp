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
#include "Jolt/Physics/Collision/AABoxCast.h"
#include "auto/com_github_stephengold_joltjni_AaBoxCast.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_AaBoxCast
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_AaBoxCast_createCopy
  BODYOF_CREATE_COPY(AABoxCast)

/*
 * Class:     com_github_stephengold_joltjni_AaBoxCast
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_AaBoxCast_createDefault
  BODYOF_CREATE_DEFAULT(AABoxCast)

/*
 * Class:     com_github_stephengold_joltjni_AaBoxCast
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_AaBoxCast_free
  BODYOF_FREE(AABoxCast)

/*
 * Class:     com_github_stephengold_joltjni_AaBoxCast
 * Method:    getBox
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_AaBoxCast_getBox
  (JNIEnv *, jclass, jlong boxCastVa) {
    AABoxCast * const pBoxCast = reinterpret_cast<AABoxCast *> (boxCastVa);
    AABox * const pResult = &pBoxCast->mBox;
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_AaBoxCast
 * Method:    getDirection
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_AaBoxCast_getDirection
  (JNIEnv *pEnv, jclass, jlong boxCastVa, jobject storeFloats) {
    const AABoxCast * const pBoxCast
            = reinterpret_cast<AABoxCast *> (boxCastVa);
    jfloat * const pFloats
            = (jfloat *) pEnv->GetDirectBufferAddress(storeFloats);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const jlong capacityFloats = pEnv->GetDirectBufferCapacity(storeFloats);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    JPH_ASSERT(capacityFloats >= 3);
    pFloats[0] = pBoxCast->mDirection.GetX();
    pFloats[1] = pBoxCast->mDirection.GetY();
    pFloats[2] = pBoxCast->mDirection.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_AaBoxCast
 * Method:    setBox
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_AaBoxCast_setBox
  (JNIEnv *, jclass, jlong boxCastVa, jlong boxVa) {
    AABoxCast * const pBoxCast = reinterpret_cast<AABoxCast *> (boxCastVa);
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    pBoxCast->mBox = *pBox;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBoxCast
 * Method:    setDirection
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_AaBoxCast_setDirection
  (JNIEnv *, jclass, jlong boxCastVa, jfloat dx, jfloat dy, jfloat dz) {
    AABoxCast * const pBoxCast = reinterpret_cast<AABoxCast *> (boxCastVa);
    const Vec3 direction(dx, dy, dz);
    pBoxCast->mDirection = direction;
}