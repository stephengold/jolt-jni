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
#include <Jolt/Physics/Collision/TransformedShape.h>

#include "auto/com_github_stephengold_joltjni_Shape.h"
#include "glue/glue.h"
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
 * Method:    getCenterOfMassX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Shape_getCenterOfMassX
  (JNIEnv *, jclass, jlong shapeVa) {
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    float result = pShape->GetCenterOfMass().GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Shape
 * Method:    getCenterOfMassY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Shape_getCenterOfMassY
  (JNIEnv *, jclass, jlong shapeVa) {
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    float result = pShape->GetCenterOfMass().GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Shape
 * Method:    getCenterOfMassZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Shape_getCenterOfMassZ
  (JNIEnv *, jclass, jlong shapeVa) {
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    float result = pShape->GetCenterOfMass().GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Shape
 * Method:    getInnerRadius
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Shape_getInnerRadius
  (JNIEnv *, jclass, jlong shapeVa) {
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    float result = pShape->GetInnerRadius();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Shape
 * Method:    getLocalBounds
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Shape_getLocalBounds
  (JNIEnv *, jclass, jlong shapeVa) {
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    AABox *pResult = new AABox();
    TRACE_NEW("AABox", pResult)
    *pResult = pShape->GetLocalBounds();
    return reinterpret_cast<jlong> (pResult);
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
    TRACE_NEW("MassProperties", pProperties)
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
 * Method:    getUserData
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Shape_getUserData
  (JNIEnv *, jclass, jlong shapeVa) {
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    uint64 result = pShape->GetUserData();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Shape
 * Method:    getWorldSpaceBounds
 * Signature: (JJFFF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Shape_getWorldSpaceBounds
  (JNIEnv *, jclass, jlong shapeVa, jlong matrixVa, jfloat sx, jfloat sy, jfloat sz) {
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    const Mat44 * const pMatrix = reinterpret_cast<Mat44 *> (matrixVa);
    const Vec3 scale(sx, sy, sz);
    AABox * const pResult = new AABox();
    TRACE_NEW("AABox", pResult)
    *pResult = pShape->GetWorldSpaceBounds(*pMatrix, scale);
    return reinterpret_cast<jlong> (pResult);
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

/*
 * Class:     com_github_stephengold_joltjni_Shape
 * Method:    setUserData
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Shape_setUserData
  (JNIEnv *, jclass, jlong shapeVa, jlong value) {
    Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    pShape->SetUserData(value);
}

/*
 * Class:     com_github_stephengold_joltjni_Shape
 * Method:    toRef
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Shape_toRef
  (JNIEnv *, jclass, jlong shapeVa) {
    Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    Ref<Shape> * const pResult = new Ref<Shape>(pShape);
    TRACE_NEW("Ref<Shape>", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Shape
 * Method:    toRefC
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Shape_toRefC
  (JNIEnv *, jclass, jlong shapeVa) {
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    ShapeRefC * const pResult = new ShapeRefC(pShape);
    TRACE_NEW("ShapeRefC", pResult)
    return reinterpret_cast<jlong> (pResult);
}