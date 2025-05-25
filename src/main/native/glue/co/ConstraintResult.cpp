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
#include "Jolt/Physics/Constraints/Constraint.h"
#include "auto/com_github_stephengold_joltjni_ConstraintResult.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_ConstraintResult
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ConstraintResult_free
  (JNIEnv *, jclass, jlong resultVa) {
    Result<Ref<ConstraintSettings>> * const pResult
            = reinterpret_cast<Result<Ref<ConstraintSettings>> *> (resultVa);
    TRACE_DELETE("Result<Ref<ConstraintSettings>>", pResult)
    delete pResult;
}

/*
 * Class:     com_github_stephengold_joltjni_ConstraintResult
 * Method:    get
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ConstraintResult_get
  (JNIEnv *, jclass, jlong resultVa) {
    const Result<Ref<ConstraintSettings>> * const pResult
            = reinterpret_cast<Result<Ref<ConstraintSettings>> *> (resultVa);
    ConstraintSettings * const pSettings = pResult->Get();
    return reinterpret_cast<jlong> (pSettings);
}

/*
 * Class:     com_github_stephengold_joltjni_ConstraintResult
 * Method:    getError
 * Signature: (J)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_github_stephengold_joltjni_ConstraintResult_getError
  (JNIEnv *pEnv, jclass, jlong resultVa) {
    const Result<Ref<ConstraintSettings>> * const pResult
            = reinterpret_cast<Result<Ref<ConstraintSettings>> *> (resultVa);
    const String& message = pResult->GetError();
    const char* const str = message.c_str();
    const jstring result = pEnv->NewStringUTF(str);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ConstraintResult
 * Method:    hasError
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ConstraintResult_hasError
  (JNIEnv *, jclass, jlong resultVa) {
    const Result<Ref<ConstraintSettings>> * const pResult
            = reinterpret_cast<Result<Ref<ConstraintSettings>> *> (resultVa);
    const bool result = pResult->HasError();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_ConstraintResult
 * Method:    isValid
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_ConstraintResult_isValid
  (JNIEnv *, jclass, jlong resultVa) {
    const Result<Ref<ConstraintSettings>> * const pResult
            = reinterpret_cast<Result<Ref<ConstraintSettings>> *> (resultVa);
    const bool result = pResult->IsValid();
    return result;
}