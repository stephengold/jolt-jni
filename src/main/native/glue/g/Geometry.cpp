/*
Copyright (c) 2025-2026 Stephen Gold

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
#ifdef JPH_DEBUG_RENDERER
#include "Jolt/Renderer/DebugRenderer.h"
#endif

#include "auto/com_github_stephengold_joltjni_Geometry.h"
#include "auto/com_github_stephengold_joltjni_GeometryRef.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_GeometryRef
 * Method:    copy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL com_github_stephengold_joltjni_GeometryRef_copy
  (JNIEnv *, jclass, jlong originalVa) {
#ifdef JPH_DEBUG_RENDERER
    const Ref<DebugRenderer::Geometry> * const pOriginal
            = reinterpret_cast<Ref<DebugRenderer::Geometry> *> (originalVa);
    Ref<DebugRenderer::Geometry> * const pResult
            = new Ref<DebugRenderer::Geometry>(*pOriginal);
    TRACE_NEW("Ref<DebugRenderer::Geometry>", pResult)
    return reinterpret_cast<jlong> (pResult);
#else
    return 0;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_GeometryRef
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL com_github_stephengold_joltjni_GeometryRef_create
  (JNIEnv *, jclass) {
#ifdef JPH_DEBUG_RENDERER
    Ref<DebugRenderer::Geometry> * const pResult
            = new Ref<DebugRenderer::Geometry>();
    TRACE_NEW("Ref<DebugRenderer::Geometry>", pResult)
    return reinterpret_cast<jlong> (pResult);
#else
    return 0;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_GeometryRef
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL com_github_stephengold_joltjni_GeometryRef_free
  (JNIEnv *, jclass, jlong va) {
#ifdef JPH_DEBUG_RENDERER
    Ref<DebugRenderer::Geometry> * const pObject
            = reinterpret_cast<Ref<DebugRenderer::Geometry> *> (va);
    TRACE_DELETE("Ref<DebugRenderer::Geometry>", pObject)
    delete pObject;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_GeometryRef
 * Method:    getPtr
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL com_github_stephengold_joltjni_GeometryRef_getPtr
  (JNIEnv *, jclass, jlong refVa) {
#ifdef JPH_DEBUG_RENDERER
    Ref<DebugRenderer::Geometry> * const pRef
            = reinterpret_cast<Ref<DebugRenderer::Geometry> *> (refVa);
    DebugRenderer::Geometry * const pResult = pRef->GetPtr();
    return reinterpret_cast<jlong> (pResult);
#else
    return 0;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_Geometry
 * Method:    countLods
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Geometry_countLods
  (JNIEnv *, jclass, jlong geometryVa) {
#ifdef JPH_DEBUG_RENDERER
    const DebugRenderer::Geometry * const pGeometry
            = reinterpret_cast<DebugRenderer::Geometry *> (geometryVa);
    const size_t result = pGeometry->mLODs.size();
    return result;
#else
    return 0;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_Geometry
 * Method:    create
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Geometry_create
  (JNIEnv *, jclass, jlong boundsVa) {
#ifdef JPH_DEBUG_RENDERER
    const AABox * const pBounds = reinterpret_cast<AABox *> (boundsVa);
    DebugRenderer::Geometry * const pResult
            = new DebugRenderer::Geometry(*pBounds);
    TRACE_NEW("DebugRenderer::Geometry", pResult)
    return reinterpret_cast<jlong> (pResult);
#else
    return 0;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_Geometry
 * Method:    createWithBatch
 * Signature: (JJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Geometry_createWithBatch
  (JNIEnv *, jclass, jlong batchVa, jlong boundsVa) {
#ifdef JPH_DEBUG_RENDERER
    const DebugRenderer::Batch * const pBatch
            = reinterpret_cast<DebugRenderer::Batch *> (batchVa);
    const AABox * const pBounds = reinterpret_cast<AABox *> (boundsVa);
    DebugRenderer::Geometry * const pResult
            = new DebugRenderer::Geometry(*pBatch, *pBounds);
    TRACE_NEW("DebugRenderer::Geometry", pResult)
    return reinterpret_cast<jlong> (pResult);
#else
    return 0;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_Geometry
 * Method:    getBounds
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Geometry_getBounds
  (JNIEnv *, jclass, jlong geometryVa) {
#ifdef JPH_DEBUG_RENDERER
    const DebugRenderer::Geometry * const pGeometry
            = reinterpret_cast<DebugRenderer::Geometry *> (geometryVa);
    const AABox * const pResult = &pGeometry->mBounds;
    return reinterpret_cast<jlong> (pResult);
#else
    return 0;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_Geometry
 * Method:    getLodFromIndex
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Geometry_getLodFromIndex
  (JNIEnv *, jclass, jlong geometryVa, jint index) {
#ifdef JPH_DEBUG_RENDERER
    const DebugRenderer::Geometry * const pGeometry
            = reinterpret_cast<DebugRenderer::Geometry *> (geometryVa);
    const DebugRenderer::LOD * const pResult = &pGeometry->mLODs[index];
    return reinterpret_cast<jlong> (pResult);
#else
    return 0;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_Geometry
 * Method:    getLodForCamera
 * Signature: (JFFFJF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Geometry_getLodForCamera
  (JNIEnv *, jclass, jlong geometryVa, jfloat camX, jfloat camY, jfloat camZ,
  jlong boundsVa, jfloat lodScaleSq) {
#ifdef JPH_DEBUG_RENDERER
    const DebugRenderer::Geometry * const pGeometry
            = reinterpret_cast<DebugRenderer::Geometry *> (geometryVa);
    const Vec3 cameraPosition(camX, camY, camZ);
    const AABox * const pBounds = reinterpret_cast<AABox *> (boundsVa);
    const DebugRenderer::LOD& result
            = pGeometry->GetLOD(cameraPosition, *pBounds, lodScaleSq);
    return reinterpret_cast<jlong> (&result);
#else
    return 0;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_Geometry
 * Method:    getRefCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Geometry_getRefCount
  (JNIEnv *, jclass, jlong geometryVa) {
#ifdef JPH_DEBUG_RENDERER
    const DebugRenderer::Geometry * const pGeometry
            = reinterpret_cast<DebugRenderer::Geometry *> (geometryVa);
    const uint32 result = pGeometry->GetRefCount();
    return result;
#else
    return 0;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_Geometry
 * Method:    setEmbedded
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Geometry_setEmbedded
  (JNIEnv *, jclass, jlong geometryVa) {
#ifdef JPH_DEBUG_RENDERER
    const DebugRenderer::Geometry * const pGeometry
            = reinterpret_cast<DebugRenderer::Geometry *> (geometryVa);
    pGeometry->SetEmbedded();
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_Geometry
 * Method:    toRef
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Geometry_toRef
  (JNIEnv *, jclass, jlong geometryVa) {
#ifdef JPH_DEBUG_RENDERER
    DebugRenderer::Geometry * const pGeometry
            = reinterpret_cast<DebugRenderer::Geometry *> (geometryVa);
    Ref<DebugRenderer::Geometry> * const pResult
            = new Ref<DebugRenderer::Geometry>(pGeometry);
    TRACE_NEW("Ref<DebugRenderer::Geometry>", pResult)
    return reinterpret_cast<jlong> (pResult);
#else
    return 0;
#endif
}