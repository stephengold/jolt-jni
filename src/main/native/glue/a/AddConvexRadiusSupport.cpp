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
#include "Jolt/Geometry/ConvexSupport.h"
#include "Jolt/Physics/Collision/Shape/ConvexShape.h"

#include "auto/com_github_stephengold_joltjni_AddConvexRadiusSupport.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_AddConvexRadiusSupport
 * Method:    createAdd
 * Signature: (JF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_AddConvexRadiusSupport_createAdd
  (JNIEnv *, jclass, jlong supportVa, jfloat convexRadius) {
    const ConvexShape::Support * const pSupport
            = reinterpret_cast<ConvexShape::Support *> (supportVa);
    AddConvexRadius<ConvexShape::Support> * const pAdd
            = new AddConvexRadius<ConvexShape::Support>(*pSupport, convexRadius);
    TRACE_NEW("AddConvexRadius<ConvexShape::Support>", pAdd)
    return reinterpret_cast<jlong> (pAdd);
}

/*
 * Class:     com_github_stephengold_joltjni_AddConvexRadiusSupport
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_AddConvexRadiusSupport_free
  (JNIEnv *, jclass, jlong supportVa) {
    AddConvexRadius<ConvexShape::Support> * const pAdd
            = reinterpret_cast<AddConvexRadius<ConvexShape::Support> *> (supportVa);
    TRACE_DELETE("AddConvexRadius<ConvexShape::Support>", pAdd)
    delete pAdd;
}

/*
 * Class:     com_github_stephengold_joltjni_AddConvexRadiusSupport
 * Method:    getSupport
 * Signature: (JFFF[F)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_AddConvexRadiusSupport_getSupport
  (JNIEnv *pEnv, jclass, jlong addVa, jfloat dx, jfloat dy, jfloat dz,
  jfloatArray storeFloats) {
    const AddConvexRadius<ConvexShape::Support> * const pAdd
            = reinterpret_cast<AddConvexRadius<ConvexShape::Support> *> (addVa);
    const Vec3 direction(dx, dy, dz);
    const Vec3 result = pAdd->GetSupport(direction);
    jboolean isCopy;
    jfloat * const pStoreFloats
            = pEnv->GetFloatArrayElements(storeFloats, &isCopy);
    pStoreFloats[0] = result.GetX();
    pStoreFloats[1] = result.GetY();
    pStoreFloats[2] = result.GetZ();
    pEnv->ReleaseFloatArrayElements(storeFloats, pStoreFloats, 0);
}