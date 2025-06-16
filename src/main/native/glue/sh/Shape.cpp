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
#include "Jolt/Physics/Collision/CollisionCollectorImpl.h"
#include "Jolt/Physics/Collision/TransformedShape.h"

#include "auto/com_github_stephengold_joltjni_Shape.h"
#include "auto/com_github_stephengold_joltjni_ShapeRef.h"
#include "glue/glue.h"

using namespace JPH;

IMPLEMENT_REF(Shape,
  Java_com_github_stephengold_joltjni_ShapeRef_copy,
  Java_com_github_stephengold_joltjni_ShapeRef_createEmpty,
  Java_com_github_stephengold_joltjni_ShapeRef_free,
  Java_com_github_stephengold_joltjni_ShapeRef_getPtr,
  Java_com_github_stephengold_joltjni_ShapeRef_toRefC)

/*
 * Class:     com_github_stephengold_joltjni_Shape
 * Method:    copyDebugTriangles
 * Signature: (JILjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Shape_copyDebugTriangles
  (JNIEnv *pEnv, jclass, jlong shapeVa, jint numTriangles, jobject storeBuffer) {
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    Float3 *pFloat3 = (Float3 *) pEnv->GetDirectBufferAddress(storeBuffer);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const jlong capacityFloats = pEnv->GetDirectBufferCapacity(storeBuffer);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    JPH_ASSERT(capacityFloats >= 9 * numTriangles);
    const AABox bounds(AABox::sBiggest());
    const Vec3 center = pShape->GetCenterOfMass();
    AllHitCollisionCollector<TransformedShapeCollector> collector;
    pShape->CollectTransformedShapes(
            bounds, center, Quat::sIdentity(), Vec3::sReplicate(1.0f),
            SubShapeIDCreator(), collector, ShapeFilter());
    for (const TransformedShape& transformedShape : collector.mHits) {
        const Shape * const pSh = transformedShape.mShape;
        Shape::GetTrianglesContext context;
        const Vec3 location(transformedShape.mShapePositionCOM);
        const Vec3 scale(transformedShape.mShapeScale);
        pSh->GetTrianglesStart(
            context, bounds, location, transformedShape.mShapeRotation, scale);
        while (numTriangles > 0) {
            const int maxRequest = std::max((int)numTriangles,
                    Shape::cGetTrianglesMinTrianglesRequested);
            const int numTrianglesCopied
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
    const AABox bounds(AABox::sBiggest());
    const Vec3 center = pShape->GetCenterOfMass();
    AllHitCollisionCollector<TransformedShapeCollector> collector;
    pShape->CollectTransformedShapes(
            bounds, center, Quat::sIdentity(), Vec3::sReplicate(1.0f),
            SubShapeIDCreator(), collector, ShapeFilter());
    for (const TransformedShape& transformedShape : collector.mHits) {
        const Shape * const pSh = transformedShape.mShape;
        Shape::GetTrianglesContext context;
        const Vec3 location(transformedShape.mShapePositionCOM);
        const Vec3 scale(transformedShape.mShapeScale);
        pSh->GetTrianglesStart(
            context, bounds, location, transformedShape.mShapeRotation, scale);
        for (;;) {
            constexpr uint cMaxTriangles = 1000;
            Float3 vertices[3 * cMaxTriangles];
            const int numTrianglesCopied
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
 * Method:    draw
 * Signature: (JJJFFFIZZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Shape_draw
  (JNIEnv *, jclass, jlong shapeVa, jlong rendererVa, jlong transformVa,
  jfloat scaleX, jfloat scaleY, jfloat scaleZ, jint colorInt,
  jboolean useMaterialColors, jboolean wireframe) {
#ifdef JPH_DEBUG_RENDERER
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    DebugRenderer * const pRenderer
            = reinterpret_cast<DebugRenderer *> (rendererVa);
    const RMat44 * const pTransform
            = reinterpret_cast<RMat44 *> (transformVa);
    const Vec3 scale(scaleX, scaleY, scaleZ);
    const Color color(colorInt);
    pShape->Draw(
            pRenderer, *pTransform, scale, color, useMaterialColors, wireframe);
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_Shape
 * Method:    drawGetSupportFunction
 * Signature: (JJJFFFIZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Shape_drawGetSupportFunction
  (JNIEnv *, jclass, jlong shapeVa, jlong rendererVa, jlong transformVa,
  jfloat scaleX, jfloat scaleY, jfloat scaleZ, jint colorInt,
  jboolean drawSupportDirection) {
#ifdef JPH_DEBUG_RENDERER
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    DebugRenderer * const pRenderer
            = reinterpret_cast<DebugRenderer *> (rendererVa);
    const RMat44 * const pTransform
            = reinterpret_cast<RMat44 *> (transformVa);
    const Vec3 scale(scaleX, scaleY, scaleZ);
    const Color color(colorInt);
    pShape->DrawGetSupportFunction(
            pRenderer, *pTransform, scale, color, drawSupportDirection);
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_Shape
 * Method:    getCenterOfMassX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Shape_getCenterOfMassX
  (JNIEnv *, jclass, jlong shapeVa) {
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    const float result = pShape->GetCenterOfMass().GetX();
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
    const float result = pShape->GetCenterOfMass().GetY();
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
    const float result = pShape->GetCenterOfMass().GetZ();
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
    const float result = pShape->GetInnerRadius();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Shape
 * Method:    getLeafShape
 * Signature: (JI[I)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Shape_getLeafShape
  (JNIEnv *pEnv, jclass, jlong currentVa, jint subShapeId,
  jintArray storeRemainderId) {
    const Shape * const pCurrent = reinterpret_cast<Shape *> (currentVa);
    SubShapeID id;
    id.SetValue(subShapeId);
    SubShapeID remainder;
    const Shape * const pResult = pCurrent->GetLeafShape(id, remainder);
    jboolean isCopy;
    jint * const pStoreRemainder
            = pEnv->GetIntArrayElements(storeRemainderId, &isCopy);
    pStoreRemainder[0] = remainder.GetValue();
    pEnv->ReleaseIntArrayElements(storeRemainderId, pStoreRemainder, 0);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Shape
 * Method:    getLocalBounds
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Shape_getLocalBounds
  (JNIEnv *, jclass, jlong shapeVa) {
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    AABox * const pResult = new AABox();
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
 * Method:    getMaterial
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Shape_getMaterial
  (JNIEnv *, jclass, jlong shapeVa, jint subShapeId) {
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    SubShapeID id;
    id.SetValue(subShapeId);
    const PhysicsMaterial * const pResult = pShape->GetMaterial(id);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Shape
 * Method:    getRefCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Shape_getRefCount
  (JNIEnv *, jclass, jlong shapeVa) {
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    const uint32 result = pShape->GetRefCount();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Shape
 * Method:    getStats
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Shape_getStats
  (JNIEnv *, jclass, jlong shapeVa) {
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    Shape::Stats * const pResult = new Shape::Stats(0, 0);
    TRACE_NEW("Shape::Stats", pResult)
    *pResult = pShape->GetStats();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Shape
 * Method:    getSubShapeIdBitsRecursive
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Shape_getSubShapeIdBitsRecursive
  (JNIEnv *, jclass, jlong shapeVa) {
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    const uint result = pShape->GetSubShapeIDBitsRecursive();
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
    const EShapeSubType subType = pShape->GetSubType();
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
    const EShapeType type = pShape->GetType();
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
    const uint64 result = pShape->GetUserData();
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
 * Method:    getWorldSpaceBoundsReal
 * Signature: (JJFFF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Shape_getWorldSpaceBoundsReal
  (JNIEnv *, jclass, jlong shapeVa, jlong rMat44Va, jfloat sx, jfloat sy, jfloat sz) {
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    const RMat44 * const pMatrix = reinterpret_cast<RMat44 *> (rMat44Va);
    const Vec3 scale(sx, sy, sz);
    AABox * const pResult = new AABox();
    TRACE_NEW("AABox", pResult)
    *pResult = pShape->GetWorldSpaceBounds(*pMatrix, scale);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Shape
 * Method:    isValidScale
 * Signature: (JFFF)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_Shape_isValidScale
  (JNIEnv *, jclass, jlong shapeVa, jfloat sx, jfloat sy, jfloat sz) {
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    const Vec3 scale(sx, sy, sz);
    const bool result = pShape->IsValidScale(scale);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Shape
 * Method:    makeScaleValid
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Shape_makeScaleValid
  (JNIEnv *pEnv, jclass, jlong shapeVa, jobject storeFloats) {
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    jfloat * const pFactors
            = (jfloat *) pEnv->GetDirectBufferAddress(storeFloats);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const jlong capacityFloats = pEnv->GetDirectBufferCapacity(storeFloats);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    JPH_ASSERT(capacityFloats >= 3);
    const Vec3 vec(pFactors[0], pFactors[1], pFactors[2]);
    const Vec3 result = pShape->MakeScaleValid(vec);
    pFactors[0] = result.GetX();
    pFactors[1] = result.GetY();
    pFactors[2] = result.GetZ();
}

/*
 * Class:     com_github_stephengold_joltjni_Shape
 * Method:    mustBeStatic
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_Shape_mustBeStatic
  (JNIEnv *, jclass, jlong shapeVa) {
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    const bool result = pShape->MustBeStatic();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Shape
 * Method:    saveBinaryState
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Shape_saveBinaryState
  (JNIEnv *, jclass, jlong shapeVa, jlong streamVa) {
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    StreamOut * const pStream = reinterpret_cast<StreamOut *> (streamVa);
    pShape->SaveBinaryState(*pStream);
}

/*
 * Class:     com_github_stephengold_joltjni_Shape
 * Method:    scaleShape
 * Signature: (JFFF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Shape_scaleShape
  (JNIEnv *, jclass, jlong shapeVa, jfloat sx, jfloat sy, jfloat sz) {
    const Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    const Vec3 scale(sx, sy, sz);
    ShapeSettings::ShapeResult *pResult = new ShapeSettings::ShapeResult();
    TRACE_NEW("ShapeSettings::ShapeResult", pResult)
    *pResult = pShape->ScaleShape(scale);
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Shape
 * Method:    setEmbedded
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Shape_setEmbedded
  (JNIEnv *, jclass, jlong shapeVa) {
    Shape * const pShape = reinterpret_cast<Shape *> (shapeVa);
    pShape->SetEmbedded();
}

/*
 * Class:     com_github_stephengold_joltjni_Shape
 * Method:    sRestoreFromBinaryState
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Shape_sRestoreFromBinaryState
  (JNIEnv *, jclass, jlong streamVa) {
    StreamIn * const pStream = reinterpret_cast<StreamIn *> (streamVa);
    ShapeSettings::ShapeResult *pResult = new ShapeSettings::ShapeResult();
    TRACE_NEW("ShapeResult", pResult)
    *pResult = Shape::sRestoreFromBinaryState(*pStream);
    return reinterpret_cast<jlong> (pResult);
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