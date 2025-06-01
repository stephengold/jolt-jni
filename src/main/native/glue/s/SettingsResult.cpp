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
#include "Jolt/Physics/SoftBody/SoftBodySharedSettings.h"
#include "auto/com_github_stephengold_joltjni_SettingsResult.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_SettingsResult
 * Method:    get
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SettingsResult_get
  (JNIEnv *, jclass, jlong resultVa) {
    const Result<Ref<SoftBodySharedSettings>> * const pResult
            = reinterpret_cast<Result<Ref<SoftBodySharedSettings>> *> (resultVa);
    Ref<SoftBodySharedSettings> * const pRef
            = new Ref<SoftBodySharedSettings>();
    TRACE_NEW("Ref<SoftBodySharedSettings>", pRef)
    *pRef = pResult->Get();
    return reinterpret_cast<jlong> (pRef);
}

IMPLEMENT_RESULT(Ref<SoftBodySharedSettings>,
        Java_com_github_stephengold_joltjni_SettingsResult_free,
        Java_com_github_stephengold_joltjni_SettingsResult_getError,
        Java_com_github_stephengold_joltjni_SettingsResult_hasError,
        Java_com_github_stephengold_joltjni_SettingsResult_isValid)