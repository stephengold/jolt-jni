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
#include <Jolt/Jolt.h>
#include <Jolt/Physics/Collision/CollisionCollectorImpl.h>
#include <Jolt/Physics/Collision/Shape/Shape.h>
#include <Jolt/Physics/Collision/Shape/SubShapeID.h>
#include <Jolt/Physics/Collision/TransformedShape.h>

#include "auto/com_github_stephengold_joltjni_Shape.h"
#include <algorithm>

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_Shape
 * Method:    copyDebugTriangles
 * Signature: (JILjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Shape_copyDebugTriangles
  (JNIEnv *pEnv, jclass, jlong shapeVa, jint numTriangles, jobject storeBuffer) {
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    Float3 * pFloat3 = (Float3 *) pEnv->GetDirectBufferAddress(storeBuffer);
    AllHitCollisionCollector<TransformedShapeCollector> collector;
    pShape->CollectTransformedShapes(AABox::sBiggest(),
            Vec3::sZero(), Quat::sIdentity(), Vec3::sReplicate(1.0f),
            SubShapeIDCreator(), collector, ShapeFilter());
    for (const TransformedShape& transformedShape : collector.mHits) {
        const Shape *pSh = transformedShape.mShape;
        Shape::GetTrianglesContext context;
        pSh->GetTrianglesStart(context, AABox::sBiggest(),
            Vec3::sZero(), Quat::sIdentity(), Vec3::sReplicate(1.0f));
        while (numTriangles > 0) {
            int maxRequest = std::max((int) numTriangles,
                    Shape::cGetTrianglesMinTrianglesRequested);
            uint numTrianglesCopied
                    = pSh->GetTrianglesNext(context, maxRequest, pFloat3);
            JPH_ASSERT(numTrianglesCopied <= numTriangles);
            if (numTrianglesCopied == 0) {
                break;
            }
            pFloat3 += 3 * numTrianglesCopied;
            numTriangles -= numTrianglesCopied;
	}
    }
}

/*
 * Class:     com_github_stephengold_joltjni_Shape
 * Method:    countDebugTriangles
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Shape_countDebugTriangles
  (JNIEnv *, jclass, jlong shapeVa) {
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    uint result = 0;
    AllHitCollisionCollector<TransformedShapeCollector> collector;
    pShape->CollectTransformedShapes(AABox::sBiggest(),
            Vec3::sZero(), Quat::sIdentity(), Vec3::sReplicate(1.0f),
            SubShapeIDCreator(), collector, ShapeFilter());
    for (const TransformedShape &transformedShape : collector.mHits) {
        const Shape *pSh = transformedShape.mShape;
        Shape::GetTrianglesContext context;
        pSh->GetTrianglesStart(context, AABox::sBiggest(),
            Vec3::sZero(), Quat::sIdentity(), Vec3::sReplicate(1.0f));
        for (;;) {
            constexpr uint cMaxTriangles = 1000;
            Float3 vertices[3 * cMaxTriangles];
            uint numTrianglesCopied
                    = pSh->GetTrianglesNext(context, cMaxTriangles, vertices);
            if (numTrianglesCopied == 0) {
                break;
            }
            result += numTrianglesCopied;
	}
    }
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Shape
 * Method:    getMassProperties
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Shape_getMassProperties
  (JNIEnv *, jclass, jlong shapeVa) {
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    MassProperties * const pProperties = new MassProperties();
    *pProperties = pShape->GetMassProperties();
    return reinterpret_cast<jlong> (pProperties);
}

/*
 * Class:     com_github_stephengold_joltjni_Shape
 * Method:    getRefCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Shape_getRefCount
  (JNIEnv *, jclass, jlong shapeVa) {
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    uint32 result = pShape->GetRefCount();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Shape
 * Method:    getSubType
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Shape_getSubType
  (JNIEnv *, jclass, jlong shapeVa) {
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    EShapeSubType subType = pShape->GetSubType();
    return (jint) subType;
}

/*
 * Class:     com_github_stephengold_joltjni_Shape
 * Method:    getType
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Shape_getType
  (JNIEnv *, jclass, jlong shapeVa) {
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    EShapeType type = pShape->GetType();
    return (jint) type;
}

/*
 * Class:     com_github_stephengold_joltjni_Shape
 * Method:    mustBeStatic
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_Shape_mustBeStatic
  (JNIEnv *, jclass, jlong shapeVa) {
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    bool result = pShape->MustBeStatic();
    return result;
}
