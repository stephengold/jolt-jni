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
#include "Jolt/Physics/Collision/RayCast.h"
#include "auto/com_github_stephengold_joltjni_RayCast.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_RayCast
 * Method:    createRayCast
 * Signature: (FFFFFF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_RayCast_createRayCast
  (JNIEnv *, jclass, jfloat startX, jfloat startY, jfloat startZ,
  jfloat dx, jfloat dy, jfloat dz) {
    const Vec3 startLocation(startX, startY, startZ);
    const Vec3 direction(dx, dy, dz);
    RayCast * const pResult = new RayCast(startLocation, direction);
    TRACE_NEW("RayCast", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_RayCast
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RayCast_free
  (JNIEnv *, jclass, jlong raycastVa) {
    RayCast * const pRayCast = reinterpret_cast<RayCast *> (raycastVa);
    TRACE_DELETE("RayCast", pRayCast)
    delete pRayCast;
}

/*
 * Class:     com_github_stephengold_joltjni_RayCast
 * Method:    getDirectionX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_RayCast_getDirectionX
  (JNIEnv *, jclass, jlong raycastVa) {
    const RayCast * const pRayCast = reinterpret_cast<RayCast *> (raycastVa);
    const float result = pRayCast->mDirection.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RayCast
 * Method:    getDirectionY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_RayCast_getDirectionY
  (JNIEnv *, jclass, jlong raycastVa) {
    const RayCast * const pRayCast = reinterpret_cast<RayCast *> (raycastVa);
    const float result = pRayCast->mDirection.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RayCast
 * Method:    getDirectionZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_RayCast_getDirectionZ
  (JNIEnv *, jclass, jlong raycastVa) {
    const RayCast * const pRayCast = reinterpret_cast<RayCast *> (raycastVa);
    const float result = pRayCast->mDirection.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RayCast
 * Method:    getOriginX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_RayCast_getOriginX
  (JNIEnv *, jclass, jlong raycastVa) {
    const RayCast * const pRayCast = reinterpret_cast<RayCast *> (raycastVa);
    const float result = pRayCast->mOrigin.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RayCast
 * Method:    getOriginY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_RayCast_getOriginY
  (JNIEnv *, jclass, jlong raycastVa) {
    const RayCast * const pRayCast = reinterpret_cast<RayCast *> (raycastVa);
    const float result = pRayCast->mOrigin.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RayCast
 * Method:    getOriginZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_RayCast_getOriginZ
  (JNIEnv *, jclass, jlong raycastVa) {
    const RayCast * const pRayCast = reinterpret_cast<RayCast *> (raycastVa);
    const float result = pRayCast->mOrigin.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RayCast
 * Method:    getPointOnRayX
 * Signature: (JF)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_RayCast_getPointOnRayX
  (JNIEnv *, jclass, jlong raycastVa, jfloat fraction) {
    const RayCast * const pRayCast = reinterpret_cast<RayCast *> (raycastVa);
    const Vec3 vec3 = pRayCast->GetPointOnRay(fraction);
    const float result = vec3.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RayCast
 * Method:    getPointOnRayY
 * Signature: (JF)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_RayCast_getPointOnRayY
  (JNIEnv *, jclass, jlong raycastVa, jfloat fraction) {
    const RayCast * const pRayCast = reinterpret_cast<RayCast *> (raycastVa);
    const Vec3 vec3 = pRayCast->GetPointOnRay(fraction);
    const float result = vec3.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RayCast
 * Method:    getPointOnRayZ
 * Signature: (JF)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_RayCast_getPointOnRayZ
  (JNIEnv *, jclass, jlong raycastVa, jfloat fraction) {
    const RayCast * const pRayCast = reinterpret_cast<RayCast *> (raycastVa);
    const Vec3 vec3 = pRayCast->GetPointOnRay(fraction);
    const float result = vec3.GetZ();
    return result;
}