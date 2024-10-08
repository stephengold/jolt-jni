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
#include "Jolt/Physics/SoftBody/SoftBodySharedSettings.h"
#include "auto/com_github_stephengold_joltjni_Face.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_Face
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Face_createDefault
  (JNIEnv *, jclass) {
    SoftBodySharedSettings::Face * const pResult
            = new SoftBodySharedSettings::Face();
    TRACE_NEW("SoftBodySharedSettings::Face", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Face
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Face_free
  (JNIEnv *, jclass, jlong faceVa) {
    SoftBodySharedSettings::Face * const pFace
            = reinterpret_cast<SoftBodySharedSettings::Face *> (faceVa);
    TRACE_DELETE("SoftBodySharedSettings::Face", pFace)
    delete pFace;
}

/*
 * Class:     com_github_stephengold_joltjni_Face
 * Method:    getMaterialIndex
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Face_getMaterialIndex
  (JNIEnv *, jclass, jlong faceVa) {
    const SoftBodySharedSettings::Face * const pFace
            = reinterpret_cast<SoftBodySharedSettings::Face *> (faceVa);
    const uint32 result = pFace->mMaterialIndex;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Face
 * Method:    getVertex
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Face_getVertex
  (JNIEnv *, jclass, jlong faceVa, jint indexInFace) {
    const SoftBodySharedSettings::Face * const pFace
            = reinterpret_cast<SoftBodySharedSettings::Face *> (faceVa);
    const uint32 result = pFace->mVertex[indexInFace];
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Face
 * Method:    isDegenerate
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_Face_isDegenerate
  (JNIEnv *, jclass, jlong faceVa) {
    const SoftBodySharedSettings::Face * const pFace
            = reinterpret_cast<SoftBodySharedSettings::Face *> (faceVa);
    const bool result = pFace->IsDegenerate();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Face
 * Method:    setMaterialIndex
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Face_setMaterialIndex
  (JNIEnv *, jclass, jlong faceVa, jint material) {
    SoftBodySharedSettings::Face * const pFace
            = reinterpret_cast<SoftBodySharedSettings::Face *> (faceVa);
    pFace->mMaterialIndex = material;
}

/*
 * Class:     com_github_stephengold_joltjni_Face
 * Method:    setVertex
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Face_setVertex
  (JNIEnv *, jclass, jlong faceVa, jint indexInFace, jint indexInMesh) {
    SoftBodySharedSettings::Face * const pFace
            = reinterpret_cast<SoftBodySharedSettings::Face *> (faceVa);
    pFace->mVertex[indexInFace] = indexInMesh;
}