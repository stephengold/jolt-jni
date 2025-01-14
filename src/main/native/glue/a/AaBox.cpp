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
#include "Jolt/Geometry/AABox.h"
#include "auto/com_github_stephengold_joltjni_AaBox.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    contains
 * Signature: (JFFF)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_AaBox_contains
  (JNIEnv *, jclass, jlong boxVa, jfloat x, jfloat y, jfloat z) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const Vec3 point(x, y, z);
    const bool result = pBox->Contains(point);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    createAaBox
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_AaBox_createAaBox__
  (JNIEnv *, jclass) {
    AABox * const pBox = new AABox();
    TRACE_NEW("AABox", pBox)
    return reinterpret_cast<jlong> (pBox);
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    createAaBox
 * Signature: (FFFFFF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_AaBox_createAaBox__FFFFFF
  (JNIEnv *, jclass, jfloat minX, jfloat minY, jfloat minZ, jfloat maxX, jfloat maxY, jfloat maxZ) {
    const Vec3 min(minX, minY, minZ);
    const Vec3 max(maxX, maxY, maxZ);
    AABox * const pBox = new AABox(min, max);
    TRACE_NEW("AABox", pBox)
    return reinterpret_cast<jlong> (pBox);
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    encapsulate
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_AaBox_encapsulate
  (JNIEnv *, jclass, jlong boxVa, jfloat locX, jfloat locY, jfloat locZ) {
    AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const Vec3 location(locX, locY, locZ);
    pBox->Encapsulate(location);
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    expandBy
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_AaBox_expandBy
  (JNIEnv *, jclass, jlong boxVa, jfloat dx, jfloat dy, jfloat dz) {
    AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const Vec3 deltas(dx, dy, dz);
    pBox->ExpandBy(deltas);
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_AaBox_free
  (JNIEnv *, jclass, jlong boxVa) {
    AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    TRACE_DELETE("AABox", pBox)
    delete pBox;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    getCenterX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_AaBox_getCenterX
  (JNIEnv *, jclass, jlong boxVa) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const float result = pBox->GetCenter().GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    getCenterY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_AaBox_getCenterY
  (JNIEnv *, jclass, jlong boxVa) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const float result = pBox->GetCenter().GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    getCenterZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_AaBox_getCenterZ
  (JNIEnv *, jclass, jlong boxVa) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const float result = pBox->GetCenter().GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    getExtentX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_AaBox_getExtentX
  (JNIEnv *, jclass, jlong boxVa) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const float result = pBox->GetExtent().GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    getExtentY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_AaBox_getExtentY
  (JNIEnv *, jclass, jlong boxVa) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const float result = pBox->GetExtent().GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    getExtentZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_AaBox_getExtentZ
  (JNIEnv *, jclass, jlong boxVa) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const float result = pBox->GetExtent().GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    getMaxX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_AaBox_getMaxX
  (JNIEnv *, jclass, jlong boxVa) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const float result = pBox->mMax.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    getMaxY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_AaBox_getMaxY
  (JNIEnv *, jclass, jlong boxVa) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const float result = pBox->mMax.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    getMaxZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_AaBox_getMaxZ
  (JNIEnv *, jclass, jlong boxVa) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const float result = pBox->mMax.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    getMinX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_AaBox_getMinX
  (JNIEnv *, jclass, jlong boxVa) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const float result = pBox->mMin.GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    getMinY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_AaBox_getMinY
  (JNIEnv *, jclass, jlong boxVa) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const float result = pBox->mMin.GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    getMinZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_AaBox_getMinZ
  (JNIEnv *, jclass, jlong boxVa) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const float result = pBox->mMin.GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    getSizeX
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_AaBox_getSizeX
  (JNIEnv *, jclass, jlong boxVa) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const float result = pBox->GetSize().GetX();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    getSizeY
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_AaBox_getSizeY
  (JNIEnv *, jclass, jlong boxVa) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const float result = pBox->GetSize().GetY();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    getSizeZ
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_AaBox_getSizeZ
  (JNIEnv *, jclass, jlong boxVa) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const float result = pBox->GetSize().GetZ();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    getVolume
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_AaBox_getVolume
  (JNIEnv *, jclass, jlong boxVa) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const float result = pBox->GetVolume();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    isValid
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_AaBox_isValid
  (JNIEnv *, jclass, jlong boxVa) {
    const AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const bool result = pBox->IsValid();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    sBiggest
 * Signature: (Z)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_AaBox_sBiggest
  (JNIEnv *, jclass, jboolean) {
    AABox * const pResult = new AABox();
    TRACE_NEW("AABox", pResult)
    *pResult = AABox::sBiggest();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    setEmpty
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_AaBox_setEmpty
  (JNIEnv *, jclass, jlong boxVa) {
    AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    pBox->SetEmpty();
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    setMax
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_AaBox_setMax
  (JNIEnv *, jclass, jlong boxVa, jfloat x, jfloat y, jfloat z) {
    AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const Vec3 max(x, y, z);
    pBox->mMax = max;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    setMin
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_AaBox_setMin
  (JNIEnv *, jclass, jlong boxVa, jfloat x, jfloat y, jfloat z) {
    AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const Vec3 min(x, y, z);
    pBox->mMin = min;
}

/*
 * Class:     com_github_stephengold_joltjni_AaBox
 * Method:    translate
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_AaBox_translate
  (JNIEnv *, jclass, jlong boxVa, jfloat x, jfloat y, jfloat z) {
    AABox * const pBox = reinterpret_cast<AABox *> (boxVa);
    const Vec3 offset(x, y, z);
    pBox->Translate(offset);
}