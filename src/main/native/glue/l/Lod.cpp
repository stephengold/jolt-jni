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
#ifdef JPH_DEBUG_RENDERER
#include "Jolt/Renderer/DebugRenderer.h"
#endif
#include "auto/com_github_stephengold_joltjni_Lod.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_Lod
 * Method:    createCopy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Lod_createCopy
  (JNIEnv *, jclass, jlong originalVa) {
#ifdef JPH_DEBUG_RENDERER
    const DebugRenderer::LOD * const pOriginal
            = reinterpret_cast<DebugRenderer::LOD *> (originalVa);
    DebugRenderer::LOD * const pResult = new DebugRenderer::LOD(*pOriginal);
    TRACE_NEW("DebugRenderer::LOD", pResult)
    return reinterpret_cast<jlong> (pResult);
#else
    return 0;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_Lod
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Lod_createDefault
  (JNIEnv *, jclass) {
#ifdef JPH_DEBUG_RENDERER
    DebugRenderer::LOD * const pResult = new DebugRenderer::LOD();
    TRACE_NEW("DebugRenderer::LOD", pResult)
    return reinterpret_cast<jlong> (pResult);
#else
    return 0;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_Lod
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Lod_free
  (JNIEnv *, jclass, jlong va) {
#ifdef JPH_DEBUG_RENDERER
    DebugRenderer::LOD * const pObject
            = reinterpret_cast<DebugRenderer::LOD *> (va);
    TRACE_DELETE("DebugRenderer::LOD", pObject)
    delete pObject;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_Lod
 * Method:    getDistance
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Lod_getDistance
  (JNIEnv *, jclass, jlong lodVa) {
#ifdef JPH_DEBUG_RENDERER
    const DebugRenderer::LOD * const pLOD
            = reinterpret_cast<DebugRenderer::LOD *> (lodVa);
    float result = pLOD->mDistance;
    return result;
#else
    return 1;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_Lod
 * Method:    setDistance
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Lod_setDistance
  (JNIEnv *, jclass, jlong lodVa, jfloat distance) {
#ifdef JPH_DEBUG_RENDERER
    DebugRenderer::LOD * const pLOD
            = reinterpret_cast<DebugRenderer::LOD *> (lodVa);
    pLOD->mDistance = distance;
#endif
}