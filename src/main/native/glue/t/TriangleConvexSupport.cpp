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
#include "Jolt/Geometry/ConvexSupport.h"
#include "auto/com_github_stephengold_joltjni_TriangleConvexSupport.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_TriangleConvexSupport
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_TriangleConvexSupport_createCopy
    BODYOF_CREATE_COPY(TriangleConvexSupport)

/*
 * Class:     com_github_stephengold_joltjni_TriangleConvexSupport
 * Method:    create
 * Signature: (FFFFFFFFF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_TriangleConvexSupport_create
  (JNIEnv *, jclass, jfloat v1x, jfloat v1y, jfloat v1z, jfloat v2x, jfloat v2y,
  jfloat v2z, jfloat v3x, jfloat v3y, jfloat v3z) {
    const Vec3 v1(v1x, v1y, v1z);
    const Vec3 v2(v2x, v2y, v2z);
    const Vec3 v3(v3x, v3y, v3z);
    TriangleConvexSupport * const pTriangle
            = new TriangleConvexSupport(v1, v2, v3);
    TRACE_NEW("TriangleConvexSupport", pTriangle)
    return reinterpret_cast<jlong> (pTriangle);
}

/*
 * Class:     com_github_stephengold_joltjni_TriangleConvexSupport
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TriangleConvexSupport_free
    BODYOF_FREE(TriangleConvexSupport)

/*
 * Class:     com_github_stephengold_joltjni_TriangleConvexSupport
 * Method:    getVertex
 * Signature: (JILjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TriangleConvexSupport_getVertex
  (JNIEnv *pEnv, jclass, jlong triangleVa, jint vertexIndex, jobject storeFloats) {
    TriangleConvexSupport * const pTriangle
            = reinterpret_cast<TriangleConvexSupport *> (triangleVa);
    jfloat * const pFloats
            = (jfloat *) pEnv->GetDirectBufferAddress(storeFloats);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const jlong capacityFloats = pEnv->GetDirectBufferCapacity(storeFloats);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    JPH_ASSERT(capacityFloats >= 3);
    Vec3 result;
    if (vertexIndex == 0) {
        result = pTriangle->mV1;
    } else if (vertexIndex == 1) {
        result = pTriangle->mV2;
    } else if (vertexIndex == 2) {
        result = pTriangle->mV3;
    }
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}