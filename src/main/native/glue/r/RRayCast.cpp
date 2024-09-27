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
#include "auto/com_github_stephengold_joltjni_RRayCast.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_RRayCast
 * Method:    createRRayCast
 * Signature: (DDDFFF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_RRayCast_createRRayCast
  (JNIEnv *, jclass, jdouble xx, jdouble yy, jdouble zz,
  jfloat dx, jfloat dy, jfloat dz) {
    const RVec3 startLocation(xx, yy, zz);
    const Vec3 direction(dx, dy, dz);
    RRayCast * const pResult = new RRayCast(startLocation, direction);
    TRACE_NEW("RRayCast", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_RRayCast
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RRayCast_free
  (JNIEnv *, jclass, jlong raycastVa) {
    RRayCast * const pRayCast = reinterpret_cast<RRayCast *> (raycastVa);
    TRACE_DELETE("RRayCast", pRayCast)
    delete pRayCast;
}

/*
 * Class:     com_github_stephengold_joltjni_RRayCast
 * Method:    getDirectionX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_RRayCast_getDirectionX
  (JNIEnv *, jclass, jlong raycastVa) {
    const RRayCast * const pRayCast = reinterpret_cast<RRayCast *> (raycastVa);
    const float result = pRayCast->mDirection.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RRayCast
 * Method:    getDirectionY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_RRayCast_getDirectionY
  (JNIEnv *, jclass, jlong raycastVa) {
    const RRayCast * const pRayCast = reinterpret_cast<RRayCast *> (raycastVa);
    const float result = pRayCast->mDirection.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RRayCast
 * Method:    getDirectionZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_RRayCast_getDirectionZ
  (JNIEnv *, jclass, jlong raycastVa) {
    const RRayCast * const pRayCast = reinterpret_cast<RRayCast *> (raycastVa);
    const float result = pRayCast->mDirection.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RRayCast
 * Method:    getOriginX
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_RRayCast_getOriginX
  (JNIEnv *, jclass, jlong raycastVa) {
    const RRayCast * const pRayCast = reinterpret_cast<RRayCast *> (raycastVa);
    const RVec3 rvec3 = pRayCast->mOrigin;
    const double result = rvec3.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RRayCast
 * Method:    getOriginY
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_RRayCast_getOriginY
  (JNIEnv *, jclass, jlong raycastVa) {
    const RRayCast * const pRayCast = reinterpret_cast<RRayCast *> (raycastVa);
    const RVec3 rvec3 = pRayCast->mOrigin;
    const double result = rvec3.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RRayCast
 * Method:    getOriginZ
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_RRayCast_getOriginZ
  (JNIEnv *, jclass, jlong raycastVa) {
    const RRayCast * const pRayCast = reinterpret_cast<RRayCast *> (raycastVa);
    const RVec3 rvec3 = pRayCast->mOrigin;
    const double result = rvec3.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RRayCast
 * Method:    getPointOnRayX
 * Signature: (JF)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_RRayCast_getPointOnRayX
  (JNIEnv *, jclass, jlong raycastVa, jfloat fraction) {
    const RRayCast * const pRayCast = reinterpret_cast<RRayCast *> (raycastVa);
    const RVec3 rvec3 = pRayCast->GetPointOnRay(fraction);
    const double result = rvec3.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RRayCast
 * Method:    getPointOnRayY
 * Signature: (JF)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_RRayCast_getPointOnRayY
  (JNIEnv *, jclass, jlong raycastVa, jfloat fraction) {
    const RRayCast * const pRayCast = reinterpret_cast<RRayCast *> (raycastVa);
    const RVec3 rvec3 = pRayCast->GetPointOnRay(fraction);
    const double result = rvec3.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_RRayCast
 * Method:    getPointOnRayZ
 * Signature: (JF)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_RRayCast_getPointOnRayZ
  (JNIEnv *, jclass, jlong raycastVa, jfloat fraction) {
    const RRayCast * const pRayCast = reinterpret_cast<RRayCast *> (raycastVa);
    const RVec3 rvec3 = pRayCast->GetPointOnRay(fraction);
    const double result = rvec3.GetZ();
    return result;
}