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
#include <Jolt/Physics/Character/Character.h>
#include "auto/com_github_stephengold_joltjni_CharacterSettingsRef.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_CharacterSettingsRef
 * Method:    copy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CharacterSettingsRef_copy
  (JNIEnv *, jclass, jlong refVa) {
    Ref<CharacterSettings> * const pRef
            = reinterpret_cast<Ref<CharacterSettings> *> (refVa);
    Ref<CharacterSettings> * const pResult = new Ref<CharacterSettings>(*pRef);
    TRACE_NEW("Ref<CharacterSettings>", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterSettingsRef
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterSettingsRef_free
  (JNIEnv *, jclass, jlong refVa) {
    Ref<CharacterSettings> * const pRef
            = reinterpret_cast<Ref<CharacterSettings> *> (refVa);
    TRACE_DELETE("Ref<CharacterSettings>", pRef)
    delete pRef;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterSettingsRef
 * Method:    getPtr
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CharacterSettingsRef_getPtr
  (JNIEnv *, jclass, jlong refVa) {
    Ref<CharacterSettings> * const pRef
            = reinterpret_cast<Ref<CharacterSettings> *> (refVa);
    CharacterSettings * const pResult = pRef->GetPtr();
    return reinterpret_cast<jlong> (pResult);
}