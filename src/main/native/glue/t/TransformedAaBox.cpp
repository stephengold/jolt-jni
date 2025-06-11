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
#include "Jolt/Geometry/AABox.h"
#include "Jolt/Geometry/ConvexSupport.h"

#include "auto/com_github_stephengold_joltjni_TransformedAaBox.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_TransformedAaBox
 * Method:    create
 * Signature: (JJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_TransformedAaBox_create
  (JNIEnv *, jclass, jlong transformVa, jlong boxVa) {
    const Mat44 * const pTransform = reinterpret_cast<Mat44 *> (transformVa);
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    TransformedConvexObject<AABox> * const pResult
            = new TransformedConvexObject(*pTransform, *pBox);
    TRACE_NEW("TransformedConvexObject<AABox>", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_TransformedAaBox
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TransformedAaBox_freeBox
    BODYOF_FREE(TransformedConvexObject<AABox>)

/*
 * Class:     com_github_stephengold_joltjni_TransformedAaBox
 * Method:    getSupport
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TransformedAaBox_getSupport
  (JNIEnv *pEnv, jclass, jlong objectVa, jobject floatBuffer) {
    const TransformedConvexObject<AABox> * const pObject
            = reinterpret_cast<TransformedConvexObject<AABox> *> (objectVa);
    jfloat * const pFloats
            = (jfloat *) pEnv->GetDirectBufferAddress(floatBuffer);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const jlong capacityFloats = pEnv->GetDirectBufferCapacity(floatBuffer);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3 direction(pFloats[0], pFloats[1], pFloats[2]);
    Vec3 result = pObject->GetSupport(direction);
    pFloats[0] = result.GetX();
    pFloats[1] = result.GetY();
    pFloats[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_TransformedAaBox
 * Method:    getTransform
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_TransformedAaBox_getTransform
  (JNIEnv *pEnv, jclass, jlong transformVa) {
    const TransformedConvexObject<AABox> * const pObject
            = reinterpret_cast<TransformedConvexObject<AABox> *> (transformVa);
    const Mat44 * const pResult = new Mat44(pObject->mTransform);
    TRACE_NEW("Mat44", pResult)
    return reinterpret_cast<jlong> (pResult);
}