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
#include "Jolt/Physics/Body/MassProperties.h"
#include "auto/com_github_stephengold_joltjni_MassProperties.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_createCopy
 * Method:    createMassProperties
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_MassProperties_createCopy
  BODYOF_CREATE_COPY(MassProperties)

/*
 * Class:     com_github_stephengold_joltjni_MassProperties
 * Method:    createMassProperties
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_MassProperties_createMassProperties
  BODYOF_CREATE_DEFAULT(MassProperties)

/*
 * Class:     com_github_stephengold_joltjni_MassProperties
 * Method:    decomposePrincipalMomentsOfInertia
 * Signature: (JJ[F)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_MassProperties_decomposePrincipalMomentsOfInertia
  (JNIEnv *pEnv, jclass, jlong propertiesVa, jlong matrixVa, jfloatArray diagonalArray) {
    const MassProperties * const pProperties
            = reinterpret_cast<MassProperties *> (propertiesVa);
    Mat44 * const pStoreRotation = reinterpret_cast<Mat44 *> (matrixVa);
    Vec3 diagonal;
    const bool success = pProperties->DecomposePrincipalMomentsOfInertia(
            *pStoreRotation, diagonal);
    jboolean isCopy;
    jfloat * const pDiagonal
            = pEnv->GetFloatArrayElements(diagonalArray, &isCopy);
    pDiagonal[0] = diagonal.GetX();
    pDiagonal[1] = diagonal.GetY();
    pDiagonal[2] = diagonal.GetZ();
    pEnv->ReleaseFloatArrayElements(diagonalArray, pDiagonal, 0);
    return success;
}

/*
 * Class:     com_github_stephengold_joltjni_MassProperties
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MassProperties_free
  BODYOF_FREE(MassProperties)

/*
 * Class:     com_github_stephengold_joltjni_MassProperties
 * Method:    getInertia
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_MassProperties_getInertia
  (JNIEnv *, jclass, jlong propertiesVa) {
    const MassProperties * const pProperties
            = reinterpret_cast<MassProperties *> (propertiesVa);
    const Mat44 * const pResult = new Mat44(pProperties->mInertia);
    TRACE_NEW("Mat44", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_MassProperties
 * Method:    getMass
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_MassProperties_getMass
  (JNIEnv *, jclass, jlong propertiesVa) {
    const MassProperties * const pProperties
            = reinterpret_cast<MassProperties *> (propertiesVa);
    const float result = pProperties->mMass;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_MassProperties
 * Method:    rotate
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MassProperties_rotate
  (JNIEnv *, jclass, jlong propertiesVa, jlong matrixVa) {
    MassProperties * const pProperties
            = reinterpret_cast<MassProperties *> (propertiesVa);
    const Mat44 * const pMatrix = reinterpret_cast<Mat44 *> (matrixVa);
    pProperties->Rotate(*pMatrix);
}

/*
 * Class:     com_github_stephengold_joltjni_MassProperties
 * Method:    scale
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MassProperties_scale
  (JNIEnv *, jclass, jlong propertiesVa, jfloat x, jfloat y, jfloat z) {
    MassProperties * const pProperties
            = reinterpret_cast<MassProperties *> (propertiesVa);
    const Vec3 scaleFactors(x, y, z);
    pProperties->Scale(scaleFactors);
}

/*
 * Class:     com_github_stephengold_joltjni_MassProperties
 * Method:    scaleToMass
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MassProperties_scaleToMass
  (JNIEnv *, jclass, jlong propertiesVa, jfloat mass) {
    MassProperties * const pProperties
            = reinterpret_cast<MassProperties *> (propertiesVa);
    pProperties->ScaleToMass(mass);
}

/*
 * Class:     com_github_stephengold_joltjni_MassProperties
 * Method:    setInertia
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MassProperties_setInertia
  (JNIEnv *, jclass, jlong propertiesVa, jlong matrixVa) {
    MassProperties * const pProperties
            = reinterpret_cast<MassProperties *> (propertiesVa);
    const Mat44 * const pMatrix = reinterpret_cast<Mat44 *> (matrixVa);
    pProperties->mInertia = *pMatrix;
}

/*
 * Class:     com_github_stephengold_joltjni_MassProperties
 * Method:    setMass
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MassProperties_setMass
  (JNIEnv *, jclass, jlong propertiesVa, jfloat mass) {
    MassProperties * const pProperties
            = reinterpret_cast<MassProperties *> (propertiesVa);
    pProperties->mMass = mass;
}

/*
 * Class:     com_github_stephengold_joltjni_MassProperties
 * Method:    setMassAndInertiaOfSolidBox
 * Signature: (JFFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MassProperties_setMassAndInertiaOfSolidBox
  (JNIEnv *, jclass, jlong propertiesVa, jfloat sx, jfloat sy, jfloat sz,
  jfloat density) {
    MassProperties * const pProperties
            = reinterpret_cast<MassProperties *> (propertiesVa);
    const Vec3 boxSize(sx, sy, sz);
    pProperties->SetMassAndInertiaOfSolidBox(boxSize, density);
}

/*
 * Class:     com_github_stephengold_joltjni_MassProperties
 * Method:    translate
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_MassProperties_translate
  (JNIEnv *, jclass, jlong propertiesVa, jfloat x, jfloat y, jfloat z) {
    MassProperties * const pProperties
            = reinterpret_cast<MassProperties *> (propertiesVa);
    const Vec3 offset(x, y, z);
    pProperties->Translate(offset);
}