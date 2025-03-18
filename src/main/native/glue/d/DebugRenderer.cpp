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
#ifdef JPH_DEBUG_RENDERER
#include "Jolt/Renderer/DebugRenderer.h"
#endif
#include "auto/com_github_stephengold_joltjni_DebugRenderer.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_DebugRenderer
 * Method:    nextFrame
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DebugRenderer_nextFrame
  (JNIEnv *, jobject) {
#ifdef JPH_DEBUG_RENDERER
    DebugRenderer::sInstance->NextFrame();
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DebugRenderer
 * Method:    drawArrow
 * Signature: (DDDDDDIF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DebugRenderer_drawArrow
  (JNIEnv *, jclass, jdouble startX, jdouble startY, jdouble startZ,
  jdouble endX, jdouble endY, jdouble endZ, jint colorInt, jfloat size) {
#ifdef JPH_DEBUG_RENDERER
    const RVec3 start(startX, startY, startZ);
    const RVec3 end(endX, endY, endZ);
    const Color color(colorInt);
    DebugRenderer::sInstance->DrawArrow(start, end, color, size);
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DebugRenderer
 * Method:    drawBox
 * Signature: (JIII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DebugRenderer_drawBox__JIII
  (JNIEnv *, jclass, jlong boxVa, jint colorInt, jint csOrdinal,
  jint drawModeOrdinal) {
#ifdef JPH_DEBUG_RENDERER
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const Color color(colorInt);
    const DebugRenderer::ECastShadow castShadow
            = (DebugRenderer::ECastShadow) csOrdinal;
    const DebugRenderer::EDrawMode drawMode
            = (DebugRenderer::EDrawMode) drawModeOrdinal;
    DebugRenderer::sInstance->DrawBox(*pBox, color, castShadow, drawMode);
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DebugRenderer
 * Method:    drawBox
 * Signature: (JJIII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DebugRenderer_drawBox
  (JNIEnv *, jclass, jlong transformVa, jlong boxVa, jint colorInt,
  jint csOrdinal, jint drawModeOrdinal) {
#ifdef JPH_DEBUG_RENDERER
    const RMat44 * const pTransform = reinterpret_cast<RMat44 *> (transformVa);
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const Color color(colorInt);
    const DebugRenderer::ECastShadow castShadow
            = (DebugRenderer::ECastShadow) csOrdinal;
    const DebugRenderer::EDrawMode drawMode
            = (DebugRenderer::EDrawMode) drawModeOrdinal;
    DebugRenderer::sInstance->DrawBox(
            *pTransform, *pBox, color, castShadow, drawMode);
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DebugRenderer
 * Method:    drawCapsule
 * Signature: (JFFIII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DebugRenderer_drawCapsule
  (JNIEnv *, jclass, jlong transformVa, jfloat halfHeight, jfloat radius,
    jint colorInt, jint csOrdinal, jint drawModeOrdinal) {
#ifdef JPH_DEBUG_RENDERER
    const RMat44 * const pTransform = reinterpret_cast<RMat44 *> (transformVa);
    const Color color(colorInt);
    const DebugRenderer::ECastShadow castShadow
            = (DebugRenderer::ECastShadow) csOrdinal;
    const DebugRenderer::EDrawMode drawMode
            = (DebugRenderer::EDrawMode) drawModeOrdinal;
    DebugRenderer::sInstance->DrawCapsule(
            *pTransform, halfHeight, radius, color, castShadow, drawMode);
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DebugRenderer
 * Method:    drawCoordinateSystem
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DebugRenderer_drawCoordinateSystem
  (JNIEnv *, jclass, jlong transformVa, jfloat size) {
#ifdef JPH_DEBUG_RENDERER
    const RMat44 * const pTransform = reinterpret_cast<RMat44 *> (transformVa);
    DebugRenderer::sInstance->DrawCoordinateSystem(*pTransform, size);
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DebugRenderer
 * Method:    drawCylinder
 * Signature: (JFFIII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DebugRenderer_drawCylinder
  (JNIEnv *, jclass, jlong transformVa, jfloat halfHeight, jfloat radius,
  jint colorInt, jint csOrdinal, jint drawModeOrdinal) {
#ifdef JPH_DEBUG_RENDERER
    const RMat44 * const pTransform = reinterpret_cast<RMat44 *> (transformVa);
    const Color color(colorInt);
    const DebugRenderer::ECastShadow castShadow
            = (DebugRenderer::ECastShadow) csOrdinal;
    const DebugRenderer::EDrawMode drawMode
            = (DebugRenderer::EDrawMode) drawModeOrdinal;
    DebugRenderer::sInstance->DrawCylinder(
            *pTransform, halfHeight, radius, color, castShadow, drawMode);
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DebugRenderer
 * Method:    drawLine
 * Signature: (DDDDDDI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DebugRenderer_drawLine
  (JNIEnv *, jclass, jdouble startX, jdouble startY, jdouble startZ,
  jdouble endX, jdouble endY, jdouble endZ, jint colorInt) {
#ifdef JPH_DEBUG_RENDERER
    const RVec3 start(startX, startY, startZ);
    const RVec3 end(endX, endY, endZ);
    const Color color(colorInt);
    DebugRenderer::sInstance->DrawLine(start, end, color);
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DebugRenderer
 * Method:    drawMarker
 * Signature: (DDDIF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DebugRenderer_drawMarker
  (JNIEnv *, jclass, jdouble locX, jdouble locY, jdouble locZ, jint colorInt,
  jfloat size) {
#ifdef JPH_DEBUG_RENDERER
    const RVec3 location(locX, locY, locZ);
    const Color color(colorInt);
    DebugRenderer::sInstance->DrawMarker(location, color, size);
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DebugRenderer
 * Method:    drawPlane
 * Signature: (DDDFFFIF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DebugRenderer_drawPlane
  (JNIEnv *, jclass, jdouble locX, jdouble locY, jdouble locZ, jfloat normX,
  jfloat normY, jfloat normZ, jint colorInt, jfloat size) {
#ifdef JPH_DEBUG_RENDERER
    const RVec3 location(locX, locY, locZ);
    const Vec3 normal(normX, normY, normZ);
    const Color color(colorInt);
    DebugRenderer::sInstance->DrawPlane(location, normal, color, size);
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DebugRenderer
 * Method:    drawSphere
 * Signature: (DDDFIII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DebugRenderer_drawSphere
  (JNIEnv *, jclass, jdouble locX, jdouble locY, jdouble locZ, jfloat radius,
  jint colorInt, jint csOrdinal, jint drawModeOrdinal) {
#ifdef JPH_DEBUG_RENDERER
    const RVec3 location(locX, locY, locZ);
    const Color color(colorInt);
    const DebugRenderer::ECastShadow castShadow
            = (DebugRenderer::ECastShadow) csOrdinal;
    const DebugRenderer::EDrawMode drawMode
            = (DebugRenderer::EDrawMode) drawModeOrdinal;
    DebugRenderer::sInstance->DrawSphere(
            location, radius, color, castShadow, drawMode);
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DebugRenderer
 * Method:    drawText3d
 * Signature: (DDDLjava/lang/String;IF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DebugRenderer_drawText3d
  (JNIEnv *pEnv, jclass, jdouble locX, jdouble locY, jdouble locZ, jstring text,
  jint colorInt, jfloat size) {
#ifdef JPH_DEBUG_RENDERER
    const RVec3 location(locX, locY, locZ);
    jboolean isCopy;
    const char * strText = pEnv->GetStringUTFChars(text, &isCopy);
    const Color color(colorInt);
    DebugRenderer::sInstance->DrawText3D(location, strText, color, size);
    pEnv->ReleaseStringUTFChars(text, strText);
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DebugRenderer
 * Method:    drawTriangle
 * Signature: (DDDDDDDDDI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DebugRenderer_drawTriangle
  (JNIEnv *, jclass, jdouble v1x, jdouble v1y, jdouble v1z, jdouble v2x,
  jdouble v2y, jdouble v2z, jdouble v3x, jdouble v3y, jdouble v3z,
  jint colorInt) {
#ifdef JPH_DEBUG_RENDERER
    const RVec3 v1(v1x, v1y, v1z);
    const RVec3 v2(v2x, v2y, v2z);
    const RVec3 v3(v3x, v3y, v3z);
    const Color color(colorInt);
    DebugRenderer::sInstance->DrawTriangle(v1, v2, v3, color);
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DebugRenderer
 * Method:    drawWireBoxAligned
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DebugRenderer_drawWireBoxAligned
  (JNIEnv *, jclass, jlong boxVa, jint colorInt) {
#ifdef JPH_DEBUG_RENDERER
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const Color color(colorInt);
    DebugRenderer::sInstance->DrawWireBox(*pBox, color);
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DebugRenderer
 * Method:    drawWireBoxOriented
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DebugRenderer_drawWireBoxOriented
  (JNIEnv *, jclass, jlong boxVa, jint colorInt) {
#ifdef JPH_DEBUG_RENDERER
    const OrientedBox * const pBox = reinterpret_cast<OrientedBox *> (boxVa);
    const Color color(colorInt);
    DebugRenderer::sInstance->DrawWireBox(*pBox, color);
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DebugRenderer
 * Method:    drawWireBoxTransformed
 * Signature: (JJI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DebugRenderer_drawWireBoxTransformed
  (JNIEnv *, jclass, jlong transformVa, jlong boxVa, jint colorInt) {
#ifdef JPH_DEBUG_RENDERER
    const RMat44 * const pTransform = reinterpret_cast<RMat44 *> (transformVa);
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const Color color(colorInt);
    DebugRenderer::sInstance->DrawWireBox(*pTransform, *pBox, color);
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DebugRenderer
 * Method:    drawWirePolygon
 * Signature: (JJIF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DebugRenderer_drawWirePolygon
  (JNIEnv *, jclass, jlong transformVa, jlong faceVa, jint colorInt,
  jfloat arrowSize) {
#ifdef JPH_DEBUG_RENDERER
    const RMat44 * const pTransform = reinterpret_cast<RMat44 *> (transformVa);
    const StaticArray<Vec3,32> * const pFace
            = reinterpret_cast<StaticArray<Vec3,32> *> (faceVa);
    const Color color(colorInt);
    DebugRenderer::sInstance->DrawWirePolygon(
            *pTransform, *pFace, color, arrowSize);
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DebugRenderer
 * Method:    drawWireSphere
 * Signature: (DDDFII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DebugRenderer_drawWireSphere
  (JNIEnv *, jclass, jdouble locX, jdouble locY, jdouble locZ, jfloat radius,
  jint colorInt, jint level) {
#ifdef JPH_DEBUG_RENDERER
    const RVec3 location(locX, locY, locZ);
    const Color color(colorInt);
    DebugRenderer::sInstance->DrawWireSphere(location, radius, color, level);
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DebugRenderer
 * Method:    drawWireTriangle
 * Signature: (DDDDDDDDDI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DebugRenderer_drawWireTriangle
  (JNIEnv *, jclass, jdouble v1x, jdouble v1y, jdouble v1z, jdouble v2x,
  jdouble v2y, jdouble v2z, jdouble v3x, jdouble v3y, jdouble v3z,
  jint colorInt) {
#ifdef JPH_DEBUG_RENDERER
    const RVec3 v1(v1x, v1y, v1z);
    const RVec3 v2(v2x, v2y, v2z);
    const RVec3 v3(v3x, v3y, v3z);
    const Color color(colorInt);
    DebugRenderer::sInstance->DrawWireTriangle(v1, v2, v3, color);
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DebugRenderer
 * Method:    drawWireUnitSphere
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DebugRenderer_drawWireUnitSphere
  (JNIEnv *, jclass, jlong transformVa, jint colorInt, jint level) {
#ifdef JPH_DEBUG_RENDERER
    const RMat44 * const pTransform = reinterpret_cast<RMat44 *> (transformVa);
    const Color color(colorInt);
    DebugRenderer::sInstance->DrawWireUnitSphere(*pTransform, color, level);
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_DebugRenderer
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_DebugRenderer_free
  (JNIEnv *, jclass, jlong rendererVa) {
#ifdef JPH_DEBUG_RENDERER
    DebugRenderer * const pRenderer
            = reinterpret_cast<DebugRenderer *> (rendererVa);
    TRACE_DELETE("DebugRenderer", pRenderer)
    delete pRenderer;
#endif
}