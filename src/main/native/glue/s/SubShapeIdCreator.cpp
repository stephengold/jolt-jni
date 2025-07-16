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
#include "Jolt/Physics/Collision/Shape/SubShapeID.h"
#include "auto/com_github_stephengold_joltjni_SubShapeIdCreator.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_SubShapeIdCreator
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SubShapeIdCreator_createDefault
    BODYOF_CREATE_DEFAULT(SubShapeIDCreator)

/*
 * Class:     com_github_stephengold_joltjni_SubShapeIdCreator
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SubShapeIdCreator_free
    BODYOF_FREE(SubShapeIDCreator)

/*
 * Class:     com_github_stephengold_joltjni_SubShapeIdCreator
 * Method:    getId
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SubShapeIdCreator_getId
  (JNIEnv *, jclass, jlong creatorVa) {
    const SubShapeIDCreator * const pCreator
            = reinterpret_cast<SubShapeIDCreator *> (creatorVa);
    const SubShapeID id = pCreator->GetID();
    return id.GetValue();
}

/*
 * Class:     com_github_stephengold_joltjni_SubShapeIdCreator
 * Method:    getNumBitsWritten
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SubShapeIdCreator_getNumBitsWritten
  (JNIEnv *, jclass, jlong creatorVa) {
    const SubShapeIDCreator * const pCreator
            = reinterpret_cast<SubShapeIDCreator *> (creatorVa);
    const uint result = pCreator->GetNumBitsWritten();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SubShapeIdCreator
 * Method:    pushId
 * Signature: (JII)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SubShapeIdCreator_pushId
  (JNIEnv *, jclass, jlong creatorVa, jint value, jint bits) {
    const SubShapeIDCreator * const pCreator
            = reinterpret_cast<SubShapeIDCreator *> (creatorVa);
    SubShapeIDCreator * const pResult = new SubShapeIDCreator();
    TRACE_NEW("SubShapeIDCreator", pResult)
    *pResult = pCreator->PushID(value, bits);
    return reinterpret_cast<jlong> (pCreator);
}