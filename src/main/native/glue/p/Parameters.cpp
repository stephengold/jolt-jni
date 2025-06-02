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
#include "auto/com_github_stephengold_joltjni_vhacd_Parameters.h"
#include "glue/glue.h"

using namespace VHACD;

/*
 * Class:     com_github_stephengold_joltjni_vhacd_Parameters
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_vhacd_Parameters_createDefault
  BODYOF_CREATE_DEFAULT(IVHACD::Parameters)

/*
 * Class:     com_github_stephengold_joltjni_vhacd_Parameters
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_vhacd_Parameters_free
  BODYOF_FREE(IVHACD::Parameters)

/*
 * Class:     com_github_stephengold_joltjni_vhacd_Parameters
 * Method:    getAsyncAcd
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_vhacd_Parameters_getAsyncAcd
  (JNIEnv *, jclass, jlong parametersVa) {
    const IVHACD::Parameters * const pParameters
            = reinterpret_cast<IVHACD::Parameters *> (parametersVa);
    const bool result = pParameters->m_asyncACD;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_vhacd_Parameters
 * Method:    getFillMode
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_vhacd_Parameters_getFillMode
  (JNIEnv *, jclass, jlong parametersVa) {
    const IVHACD::Parameters * const pParameters
            = reinterpret_cast<IVHACD::Parameters *> (parametersVa);
    const FillMode result = pParameters->m_fillMode;
    return (jint) result;
}

/*
 * Class:     com_github_stephengold_joltjni_vhacd_Parameters
 * Method:    getFindBestPlane
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_vhacd_Parameters_getFindBestPlane
  (JNIEnv *, jclass, jlong parametersVa) {
    const IVHACD::Parameters * const pParameters
            = reinterpret_cast<IVHACD::Parameters *> (parametersVa);
    const bool result = pParameters->m_findBestPlane;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_vhacd_Parameters
 * Method:    getMaxConvexHulls
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_vhacd_Parameters_getMaxConvexHulls
  (JNIEnv *, jclass, jlong parametersVa) {
    const IVHACD::Parameters * const pParameters
            = reinterpret_cast<IVHACD::Parameters *> (parametersVa);
    const uint32_t result = pParameters->m_maxConvexHulls;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_vhacd_Parameters
 * Method:    getMaxNumVerticesPerCh
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_vhacd_Parameters_getMaxNumVerticesPerCh
  (JNIEnv *, jclass, jlong parametersVa) {
    const IVHACD::Parameters * const pParameters
            = reinterpret_cast<IVHACD::Parameters *> (parametersVa);
    const uint32_t result = pParameters->m_maxNumVerticesPerCH;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_vhacd_Parameters
 * Method:    getMaxRecursionDepth
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_vhacd_Parameters_getMaxRecursionDepth
  (JNIEnv *, jclass, jlong parametersVa) {
    const IVHACD::Parameters * const pParameters
            = reinterpret_cast<IVHACD::Parameters *> (parametersVa);
    const uint32_t result = pParameters->m_maxRecursionDepth;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_vhacd_Parameters
 * Method:    getMinEdgeLength
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_vhacd_Parameters_getMinEdgeLength
  (JNIEnv *, jclass, jlong parametersVa) {
    const IVHACD::Parameters * const pParameters
            = reinterpret_cast<IVHACD::Parameters *> (parametersVa);
    const uint32_t result = pParameters->m_minEdgeLength;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_vhacd_Parameters
 * Method:    getMinimumVolumePercentErrorAllowed
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_github_stephengold_joltjni_vhacd_Parameters_getMinimumVolumePercentErrorAllowed
  (JNIEnv *, jclass, jlong parametersVa) {
    const IVHACD::Parameters * const pParameters
            = reinterpret_cast<IVHACD::Parameters *> (parametersVa);
    const double result = pParameters->m_minimumVolumePercentErrorAllowed;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_vhacd_Parameters
 * Method:    getResolution
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_vhacd_Parameters_getResolution
  (JNIEnv *, jclass, jlong parametersVa) {
    const IVHACD::Parameters * const pParameters
            = reinterpret_cast<IVHACD::Parameters *> (parametersVa);
    const uint32_t result = pParameters->m_resolution;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_vhacd_Parameters
 * Method:    getShrinkWrap
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_vhacd_Parameters_getShrinkWrap
  (JNIEnv *, jclass, jlong parametersVa) {
    const IVHACD::Parameters * const pParameters
            = reinterpret_cast<IVHACD::Parameters *> (parametersVa);
    const bool result = pParameters->m_shrinkWrap;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_vhacd_Parameters
 * Method:    setAsyncAcd
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_vhacd_Parameters_setAsyncAcd
  (JNIEnv *, jclass, jlong parametersVa, jboolean setting) {
    IVHACD::Parameters * const pParameters
            = reinterpret_cast<IVHACD::Parameters *> (parametersVa);
    pParameters->m_asyncACD = setting;
}

/*
 * Class:     com_github_stephengold_joltjni_vhacd_Parameters
 * Method:    setFillMode
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_vhacd_Parameters_setFillMode
  (JNIEnv *, jclass, jlong parametersVa, jint ordinal) {
    IVHACD::Parameters * const pParameters
            = reinterpret_cast<IVHACD::Parameters *> (parametersVa);
    pParameters->m_fillMode = (FillMode) ordinal;
}

/*
 * Class:     com_github_stephengold_joltjni_vhacd_Parameters
 * Method:    setFindBestPlane
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_vhacd_Parameters_setFindBestPlane
  (JNIEnv *, jclass, jlong parametersVa, jboolean setting) {
    IVHACD::Parameters * const pParameters
            = reinterpret_cast<IVHACD::Parameters *> (parametersVa);
    pParameters->m_findBestPlane = setting;
}

/*
 * Class:     com_github_stephengold_joltjni_vhacd_Parameters
 * Method:    setMaxConvexHulls
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_vhacd_Parameters_setMaxConvexHulls
  (JNIEnv *, jclass, jlong parametersVa, jint number) {
    IVHACD::Parameters * const pParameters
            = reinterpret_cast<IVHACD::Parameters *> (parametersVa);
    pParameters->m_maxConvexHulls = number;
}

/*
 * Class:     com_github_stephengold_joltjni_vhacd_Parameters
 * Method:    setMaxNumVerticesPerCh
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_vhacd_Parameters_setMaxNumVerticesPerCh
  (JNIEnv *, jclass, jlong parametersVa, jint number) {
    IVHACD::Parameters * const pParameters
            = reinterpret_cast<IVHACD::Parameters *> (parametersVa);
    pParameters->m_maxNumVerticesPerCH = number;
}

/*
 * Class:     com_github_stephengold_joltjni_vhacd_Parameters
 * Method:    setMaxRecursionDepth
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_vhacd_Parameters_setMaxRecursionDepth
  (JNIEnv *, jclass, jlong parametersVa, jint depth) {
    IVHACD::Parameters * const pParameters
            = reinterpret_cast<IVHACD::Parameters *> (parametersVa);
    pParameters->m_maxRecursionDepth = depth;
}

/*
 * Class:     com_github_stephengold_joltjni_vhacd_Parameters
 * Method:    setMinEdgeLength
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_vhacd_Parameters_setMinEdgeLength
  (JNIEnv *, jclass, jlong parametersVa, jint length) {
    IVHACD::Parameters * const pParameters
            = reinterpret_cast<IVHACD::Parameters *> (parametersVa);
    pParameters->m_minEdgeLength = length;
}

/*
 * Class:     com_github_stephengold_joltjni_vhacd_Parameters
 * Method:    setMinimumVolumePercentErrorAllowed
 * Signature: (JD)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_vhacd_Parameters_setMinimumVolumePercentErrorAllowed
  (JNIEnv *, jclass, jlong parametersVa, jdouble percentage) {
    IVHACD::Parameters * const pParameters
            = reinterpret_cast<IVHACD::Parameters *> (parametersVa);
    pParameters->m_minimumVolumePercentErrorAllowed = percentage;
}

/*
 * Class:     com_github_stephengold_joltjni_vhacd_Parameters
 * Method:    setResolution
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_vhacd_Parameters_setResolution
  (JNIEnv *, jclass, jlong parametersVa, jint maxVoxels) {
    IVHACD::Parameters * const pParameters
            = reinterpret_cast<IVHACD::Parameters *> (parametersVa);
    pParameters->m_resolution = maxVoxels;
}

/*
 * Class:     com_github_stephengold_joltjni_vhacd_Parameters
 * Method:    setShrinkWrap
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_vhacd_Parameters_setShrinkWrap
  (JNIEnv *, jclass, jlong parametersVa, jboolean setting) {
    IVHACD::Parameters * const pParameters
            = reinterpret_cast<IVHACD::Parameters *> (parametersVa);
    pParameters->m_shrinkWrap = setting;
}