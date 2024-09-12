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
#include <Jolt/Physics/Collision/ShapeCast.h>
#include "auto/com_github_stephengold_joltjni_ShapeCastResult.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_ShapeCastResult
 * Method:    getFraction
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_ShapeCastResult_getFraction
  (JNIEnv *, jclass, jlong castResultVa) {
    const ShapeCastResult * const pCastResult
            = reinterpret_cast<ShapeCastResult *> (castResultVa);
    const float result = pCastResult->mFraction;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ShapeCastResult
 * Method:    getIsBackFaceHit
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ShapeCastResult_getIsBackFaceHit
  (JNIEnv *, jclass, jlong castResultVa) {
    const ShapeCastResult * const pCastResult
            = reinterpret_cast<ShapeCastResult *> (castResultVa);
    const bool result = pCastResult->mIsBackFaceHit;
    return result;
}
