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
#include <Jolt/Physics/Collision/Shape/Shape.h>
#include "auto/com_github_stephengold_joltjni_ShapeResult.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_ShapeResult
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ShapeResult_free
  (JNIEnv *, jclass, jlong resultVa) {
    ShapeSettings::ShapeResult * const pResult
            = reinterpret_cast<ShapeSettings::ShapeResult *> (resultVa);
    TRACE_DELETE("ShapeResult", pResult)
    delete pResult;
}

/*
 * Class:     com_github_stephengold_joltjni_ShapeResult
 * Method:    get
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ShapeResult_get
  (JNIEnv *, jclass, jlong resultVa) {
    const ShapeSettings::ShapeResult * const pResult
            = reinterpret_cast<ShapeSettings::ShapeResult *> (resultVa);
    ShapeRefC * const pRef = new ShapeRefC();
    TRACE_NEW("ShapeRefC", pRef)
    *pRef = pResult->Get();
    return reinterpret_cast<jlong> (pRef);
}

/*
 * Class:     com_github_stephengold_joltjni_ShapeResult
 * Method:    getError
 * Signature: (J)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_github_stephengold_joltjni_ShapeResult_getError
  (JNIEnv *pEnv, jclass, jlong resultVa) {
    const ShapeSettings::ShapeResult * const pResult
            = reinterpret_cast<ShapeSettings::ShapeResult *> (resultVa);
    const String& message = pResult->GetError();
    const char* const str = message.c_str();
    const jstring result = pEnv->NewStringUTF(str);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ShapeResult
 * Method:    hasError
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ShapeResult_hasError
  (JNIEnv *, jclass, jlong resultVa) {
    const ShapeSettings::ShapeResult * const pResult
            = reinterpret_cast<ShapeSettings::ShapeResult *> (resultVa);
    bool result = pResult->HasError();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ShapeResult
 * Method:    isValid
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ShapeResult_isValid
  (JNIEnv *, jclass, jlong resultVa) {
    const ShapeSettings::ShapeResult * const pResult
            = reinterpret_cast<ShapeSettings::ShapeResult *> (resultVa);
    bool result = pResult->IsValid();
    return result;
}
