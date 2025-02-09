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
#include "VHACD.h"
#include "auto/com_github_stephengold_joltjni_vhacd_ConvexHull.h"
#include "glue/glue.h"

using namespace VHACD;

/*
 * Class:     com_github_stephengold_joltjni_vhacd_ConvexHull
 * Method:    copyPoints
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_vhacd_ConvexHull_copyPoints
  (JNIEnv *pEnv, jclass, jlong hullVa, jobject resultBuffer) {
    const IVHACD::ConvexHull * const pHull
            = reinterpret_cast<IVHACD::ConvexHull *> (hullVa);
    jfloat * const pPoints
            = (jfloat *) pEnv->GetDirectBufferAddress(resultBuffer);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const jlong capacityFloats = pEnv->GetDirectBufferCapacity(resultBuffer);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const size_t numPoints = pHull->m_points.size();
    for (size_t i = 0; i < numPoints; ++i) {
        const size_t baseIndex = 3 * i;
        if (baseIndex + 2 >= capacityFloats) {
            break;
        }
        pPoints[baseIndex] = pHull->m_points[i].mX;
        pPoints[baseIndex + 1] = pHull->m_points[i].mY;
        pPoints[baseIndex + 2] = pHull->m_points[i].mZ;
    }
}

/*
 * Class:     com_github_stephengold_joltjni_vhacd_ConvexHull
 * Method:    countPoints
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_vhacd_ConvexHull_countPoints
  (JNIEnv *, jclass, jlong hullVa) {
    const IVHACD::ConvexHull * const pHull
            = reinterpret_cast<IVHACD::ConvexHull *> (hullVa);
    size_t result = pHull->m_points.size();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_vhacd_ConvexHull
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_vhacd_ConvexHull_free
  (JNIEnv *, jclass, jlong hullVa) {
    IVHACD::ConvexHull * const pHull
            = reinterpret_cast<IVHACD::ConvexHull *> (hullVa);
    TRACE_DELETE("IVHACD::ConvexHull", pHull)
    delete pHull;
}