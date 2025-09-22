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
#include "Jolt/Physics/Collision/Shape/ConvexShape.h"
#include "auto/com_github_stephengold_joltjni_Support.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_Support
 * Method:    getConvexRadius
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Support_getConvexRadius
  (JNIEnv *, jclass, jlong supportVa) {
    const ConvexShape::Support * const pSupport
            = reinterpret_cast<ConvexShape::Support *> (supportVa);
    const float result = pSupport->GetConvexRadius();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Support
 * Method:    getSupport
 * Signature: (JFFFLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Support_getSupport
  (JNIEnv *pEnv, jclass, jlong supportVa, jfloat dx, jfloat dy, jfloat dz,
  jobject storeFloats) {
    const ConvexShape::Support * const pSupport
            = reinterpret_cast<ConvexShape::Support *> (supportVa);
    const Vec3 direction(dx, dy, dz);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3 result = pSupport->GetSupport(direction);
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_Support
 * Method:    getSupportBulk
 * Signature: (JLjava/nio/FloatBuffer;Ljava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Support_getSupportBulk
  (JNIEnv *pEnv, jclass, jlong supportVa, jobject directions, jobject storePoints) {
    const ConvexShape::Support * const pSupport
            = reinterpret_cast<ConvexShape::Support *> (supportVa);
    const DIRECT_FLOAT_BUFFER(pEnv, directions, pFloatsIn, capacityFloatsIn);
    JPH_ASSERT((capacityFloatsIn % 3) == 0);
    DIRECT_FLOAT_BUFFER(pEnv, storePoints, pFloatsOut, capacityFloatsOut);
    JPH_ASSERT(capacityFloatsOut == capacityFloatsIn);
    for (int offset = 0; offset < capacityFloatsIn; offset += 3) {
        const float dx = pFloatsIn[offset];
        const float dy = pFloatsIn[offset + 1];
        const float dz = pFloatsIn[offset + 2];
        const Vec3 direction(dx, dy, dz);
        const Vec3 point = pSupport->GetSupport(direction);
        pFloatsOut[offset] = point.GetX();
        pFloatsOut[offset + 1] = point.GetY();
        pFloatsOut[offset + 2] = point.GetZ();
    }
}