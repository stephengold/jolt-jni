/*
Copyright (c) 2024-2025 Stephen Gold

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
#include "Jolt/Geometry/Sphere.h"
#include "auto/com_github_stephengold_joltjni_Sphere.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_Sphere
 * Method:    create
 * Signature: (FFFF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Sphere_create
  (JNIEnv *, jclass, jfloat cx, jfloat cy, jfloat cz, jfloat radius) {
    const Vec3 center(cx, cy, cz);
    Sphere * const pResult = new Sphere(center, radius);
    TRACE_NEW("Sphere", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Sphere
 * Method:    encapsulatePoint
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Sphere_encapsulatePoint
  (JNIEnv *, jclass, jlong sphereVa, jfloat x, jfloat y, jfloat z) {
    Sphere * const pSphere = reinterpret_cast<Sphere *> (sphereVa);
    const Vec3 point(x, y, z);
    pSphere->EncapsulatePoint(point);
}

/*
 * Class:     com_github_stephengold_joltjni_Sphere
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Sphere_free
  BODYOF_FREE(Sphere)

/*
 * Class:     com_github_stephengold_joltjni_Sphere
 * Method:    getCenterX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Sphere_getCenterX
  (JNIEnv *, jclass, jlong sphereVa) {
    const Sphere * const pSphere = reinterpret_cast<Sphere *> (sphereVa);
    const float result = pSphere->GetCenter().GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Sphere
 * Method:    getCenterY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Sphere_getCenterY
  (JNIEnv *, jclass, jlong sphereVa) {
    const Sphere * const pSphere = reinterpret_cast<Sphere *> (sphereVa);
    const float result = pSphere->GetCenter().GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Sphere
 * Method:    getCenterZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Sphere_getCenterZ
  (JNIEnv *, jclass, jlong sphereVa) {
    const Sphere * const pSphere = reinterpret_cast<Sphere *> (sphereVa);
    const float result = pSphere->GetCenter().GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Sphere
 * Method:    getRadius
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Sphere_getRadius
  (JNIEnv *, jclass, jlong sphereVa) {
    const Sphere * const pSphere = reinterpret_cast<Sphere *> (sphereVa);
    const float result = pSphere->GetRadius();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Sphere
 * Method:    getSupport
 * Signature: (JFFF[F)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Sphere_getSupport
  (JNIEnv *pEnv, jclass, jlong sphereVa, jfloat dx, jfloat dy, jfloat dz,
  jfloatArray storeFloats) {
    const Sphere * const pSphere = reinterpret_cast<Sphere *> (sphereVa);
    const Vec3 direction(dx, dy, dz);
    const Vec3 result = pSphere->GetSupport(direction);
    jboolean isCopy;
    jfloat * const pStoreFloats
            = pEnv->GetFloatArrayElements(storeFloats, &isCopy);
    pStoreFloats[0] = result.GetX();
    pStoreFloats[1] = result.GetY();
    pStoreFloats[2] = result.GetZ();
    pEnv->ReleaseFloatArrayElements(storeFloats, pStoreFloats, 0);
}