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
#include "Jolt/Geometry/OrientedBox.h"
#include "auto/com_github_stephengold_joltjni_OrientedBox.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_OrientedBox
 * Method:    createBox
 * Signature: (JFFF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_OrientedBox_createBox
  (JNIEnv *, jclass, jlong transformVa, jfloat hx, jfloat hy, jfloat hz) {
    const Mat44 * const pTransform = reinterpret_cast<Mat44 *> (transformVa);
    const Vec3 halfExtents(hx, hy, hz);
    OrientedBox * const pResult = new OrientedBox(*pTransform, halfExtents);
    TRACE_NEW("OrientedBox", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_OrientedBox
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_OrientedBox_createCopy
  BODYOF_CREATE_COPY(OrientedBox)

/*
 * Class:     com_github_stephengold_joltjni_OrientedBox
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_OrientedBox_createDefault
  BODYOF_CREATE_DEFAULT(OrientedBox)

/*
 * Class:     com_github_stephengold_joltjni_OrientedBox
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_OrientedBox_free
  BODYOF_FREE(OrientedBox)

/*
 * Class:     com_github_stephengold_joltjni_OrientedBox
 * Method:    getHalfExtents
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_OrientedBox_getHalfExtents
  (JNIEnv *pEnv, jclass, jlong boxVa, jobject storeFloats) {
   const OrientedBox * const pBox = reinterpret_cast<OrientedBox *> (boxVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFloats, pFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3& result = pBox->mHalfExtents;
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
 }

/*
 * Class:     com_github_stephengold_joltjni_OrientedBox
 * Method:    getOrientation
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_OrientedBox_getOrientation
  (JNIEnv *, jclass, jlong boxVa) {
    const OrientedBox * const pBox = reinterpret_cast<OrientedBox *> (boxVa);
    const Mat44 * const pResult = new Mat44(pBox->mOrientation);
    TRACE_NEW("Mat44", pResult)
    return reinterpret_cast<jlong> (pResult);
}