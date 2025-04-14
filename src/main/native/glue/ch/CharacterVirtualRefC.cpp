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
#include "Jolt/Physics/Character/CharacterVirtual.h"
#include "auto/com_github_stephengold_joltjni_CharacterVirtualRefC.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_CharacterRefC
 * Method:    copy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CharacterRefC_copy
  (JNIEnv *, jclass, jlong refVa) {
    RefConst<CharacterVirtual> * const pRef
            = reinterpret_cast<RefConst<CharacterVirtual> *> (refVa);
    RefConst<CharacterVirtual> * const pResult
            = new RefConst<CharacterVirtual>(*pRef);
    TRACE_NEW("RefConst<CharacterVirtual>", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtualRefC
 * Method:    freeWithSystem
 * Signature: (JLcom/github/stephengold/joltjni/PhysicsSystem;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CharacterVirtualRefC_freeWithSystem
  (JNIEnv *, jclass, jlong refVa, jobject) {
    RefConst<CharacterVirtual> * const pRef
            = reinterpret_cast<RefConst<CharacterVirtual> *> (refVa);
    TRACE_DELETE("RefConst<CharacterVirtual>", pRef)
    delete pRef;
}

/*
 * Class:     com_github_stephengold_joltjni_CharacterVirtualRefC
 * Method:    getPtr
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CharacterVirtualRefC_getPtr
  (JNIEnv *, jclass, jlong refVa) {
    RefConst<CharacterVirtual> * const pRef
            = reinterpret_cast<RefConst<CharacterVirtual> *> (refVa);
    const CharacterVirtual * const pResult = pRef->GetPtr();
    return reinterpret_cast<jlong> (pResult);
}