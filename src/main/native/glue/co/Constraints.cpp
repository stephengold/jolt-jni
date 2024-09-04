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
#include <Jolt/Physics/Constraints/ConstraintManager.h>
#include "auto/com_github_stephengold_joltjni_Constraints.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_Constraints
 * Method:    capacity
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Constraints_capacity
  (JNIEnv *, jclass, jlong arrayVa) {
    const Constraints * const pArray
            = reinterpret_cast<Constraints *> (arrayVa);
    const Constraints::size_type result = pArray->capacity();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Constraints
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Constraints_free
  (JNIEnv *, jclass, jlong arrayVa) {
    Constraints * const pArray = reinterpret_cast<Constraints *> (arrayVa);
    TRACE_DELETE("Constraints", pArray)
    delete pArray;
}

/*
 * Class:     com_github_stephengold_joltjni_Constraints
 * Method:    get
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Constraints_get
  (JNIEnv *, jclass, jlong arrayVa, jint elementIndex) {
    Constraints * const pArray = reinterpret_cast<Constraints *> (arrayVa);
    Ref<Constraint>& element = pArray->at(elementIndex);
    Ref<Constraint>* pResult = new Ref<Constraint>(element);
    TRACE_NEW("Ref<Constraint>", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Constraints
 * Method:    resize
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Constraints_resize
  (JNIEnv *, jclass, jlong arrayVa, jint numElements) {
    Constraints * const pArray = reinterpret_cast<Constraints *> (arrayVa);
    pArray->resize(numElements);
}

/*
 * Class:     com_github_stephengold_joltjni_Constraints
 * Method:    setRef
 * Signature: (JIJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Constraints_setRef
  (JNIEnv *, jclass, jlong arrayVa, jint elementIndex, jlong refVa) {
    Constraints * const pArray = reinterpret_cast<Constraints *> (arrayVa);
    Ref<Constraint> * const pRef = reinterpret_cast<Ref<Constraint> *> (refVa);
    Ref<Constraint> * const pNewRef = new Ref<Constraint>(*pRef);
    TRACE_NEW("Ref<Constraint>", pNewRef)
    pArray->at(elementIndex) = *pNewRef;
}

/*
 * Class:     com_github_stephengold_joltjni_Constraints
 * Method:    size
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Constraints_size
  (JNIEnv *, jclass, jlong arrayVa) {
    const Constraints * const pArray
            = reinterpret_cast<Constraints *> (arrayVa);
    const Constraints::size_type result = pArray->size();
    return result;
}