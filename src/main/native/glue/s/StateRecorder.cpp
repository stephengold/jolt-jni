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
#include <Jolt/Physics/StateRecorder.h>
#include "auto/com_github_stephengold_joltjni_StateRecorder.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_StateRecorder
 * Method:    isValidating
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_StateRecorder_isValidating
  (JNIEnv *, jclass, jlong recorderVa) {
    const StateRecorder * const pRecorder
            = reinterpret_cast<StateRecorder *> (recorderVa);
    const bool result = pRecorder->IsValidating();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_StateRecorder
 * Method:    readBoolean
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_StateRecorder_readBoolean
  (JNIEnv *, jclass, jlong recorderVa) {
    StateRecorder * const pRecorder
            = reinterpret_cast<StateRecorder *> (recorderVa);
    bool result;
    pRecorder->Read(result);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_StateRecorder
 * Method:    readFloat
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_StateRecorder_readFloat
  (JNIEnv *, jclass, jlong recorderVa) {
    StateRecorder * const pRecorder
            = reinterpret_cast<StateRecorder *> (recorderVa);
    float result;
    pRecorder->Read(result);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_StateRecorder
 * Method:    readVec3
 * Signature: (J[F)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_StateRecorder_readVec3
  (JNIEnv *pEnv, jclass, jlong recorderVa, jfloatArray storeFloats) {
    StateRecorder * const pRecorder
            = reinterpret_cast<StateRecorder *> (recorderVa);
    Vec3 result;
    pRecorder->Read(result);
    jboolean isCopy;
    jfloat * const pStoreFloats
            = pEnv->GetFloatArrayElements(storeFloats, &isCopy);
    pStoreFloats[0] = result.GetX();
    pStoreFloats[1] = result.GetY();
    pStoreFloats[2] = result.GetZ();
    pEnv->ReleaseFloatArrayElements(storeFloats, pStoreFloats, 0);
}

/*
 * Class:     com_github_stephengold_joltjni_StateRecorder
 * Method:    setValidating
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_StateRecorder_setValidating
  (JNIEnv *, jclass, jlong recorderVa, jboolean setting) {
    StateRecorder * const pRecorder
            = reinterpret_cast<StateRecorder *> (recorderVa);
    pRecorder->SetValidating(setting);
}

/*
 * Class:     com_github_stephengold_joltjni_StateRecorder
 * Method:    writeBoolean
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_StateRecorder_writeBoolean
  (JNIEnv *, jclass, jlong recorderVa, jboolean b) {
    StateRecorder * const pRecorder
            = reinterpret_cast<StateRecorder *> (recorderVa);
    const bool bb = b;
    pRecorder->Write(bb);
}

/*
 * Class:     com_github_stephengold_joltjni_StateRecorder
 * Method:    writeFloat
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_StateRecorder_writeFloat
  (JNIEnv *, jclass, jlong recorderVa, jfloat f) {
    StateRecorder * const pRecorder
            = reinterpret_cast<StateRecorder *> (recorderVa);
    const float ff = f;
    pRecorder->Write(ff);
}

/*
 * Class:     com_github_stephengold_joltjni_StateRecorder
 * Method:    writeVec3
 * Signature: (JFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_StateRecorder_writeVec3
  (JNIEnv *, jclass, jlong recorderVa, jfloat x, jfloat y, jfloat z) {
    StateRecorder * const pRecorder
            = reinterpret_cast<StateRecorder *> (recorderVa);
    const Vec3 vec(x, y, z);
    pRecorder->Write(vec);
}