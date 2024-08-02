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
#include "auto/com_github_stephengold_joltjni_RefShape.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_RefShape
 * Method:    copy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_RefShape_copy
  (JNIEnv *, jclass, jlong refVa) {
    Ref<Shape> * const pRef = reinterpret_cast<Ref<Shape> *> (refVa);
    Ref<Shape> * const pResult = new Ref<Shape>(*pRef);
    TRACE_NEW("Ref<Shape>", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_RefShape
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_RefShape_free
  (JNIEnv *, jclass, jlong refVa) {
    Ref<Shape> * const pRef = reinterpret_cast<Ref<Shape> *> (refVa);
    TRACE_DELETE("Ref<Shape>", pRef)
    delete pRef;
}

/*
 * Class:     com_github_stephengold_joltjni_RefShape
 * Method:    getPtr
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_RefShape_getPtr
  (JNIEnv *, jclass, jlong refVa) {
    Ref<Shape> * const pRef = reinterpret_cast<Ref<Shape> *> (refVa);
    Shape * const pResult = pRef->GetPtr();
    return reinterpret_cast<jlong> (pResult);
}