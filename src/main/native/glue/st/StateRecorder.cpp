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
#include "Jolt/Physics/Body/BodyManager.h"
#include "Jolt/Physics/StateRecorder.h"

#include "auto/com_github_stephengold_joltjni_StateRecorder.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_StateRecorder
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_StateRecorder_free
    BODYOF_FREE(StateRecorder)

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
 * Method:    readBodyIdVector
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_StateRecorder_readBodyIdVector
  (JNIEnv *, jclass, jlong recorderVa, jlong vectorVa) {
    StateRecorder * const pRecorder
            = reinterpret_cast<StateRecorder *> (recorderVa);
    BodyIDVector * const pVector = reinterpret_cast<BodyIDVector *> (vectorVa);
    pRecorder->Read(*pVector);
}

/*
 * Class:     com_github_stephengold_joltjni_StateRecorder
 * Method:    readBoolean
 * Signature: (JZ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_StateRecorder_readBoolean
  (JNIEnv *, jclass, jlong recorderVa, jboolean b) {
    StateRecorder * const pRecorder
            = reinterpret_cast<StateRecorder *> (recorderVa);
    bool result = b;
    pRecorder->Read(result);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_StateRecorder
 * Method:    readFloat
 * Signature: (JF)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_StateRecorder_readFloat
  (JNIEnv *, jclass, jlong recorderVa, jfloat f) {
    StateRecorder * const pRecorder
            = reinterpret_cast<StateRecorder *> (recorderVa);
    float result = f;
    pRecorder->Read(result);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_StateRecorder
 * Method:    readInt
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_StateRecorder_readInt
  (JNIEnv *, jclass, jlong recorderVa, jint i) {
    StateRecorder * const pRecorder
            = reinterpret_cast<StateRecorder *> (recorderVa);
    pRecorder->Read(i);
    return i;
}

/*
 * Class:     com_github_stephengold_joltjni_StateRecorder
 * Method:    readRMat44
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_StateRecorder_readRMat44
  (JNIEnv *, jclass, jlong recorderVa, jlong matrixVa) {
    StateRecorder * const pRecorder
            = reinterpret_cast<StateRecorder *> (recorderVa);
    RMat44 * const pMatrix = reinterpret_cast<RMat44 *> (matrixVa);
    pRecorder->Read(*pMatrix);
}

/*
 * Class:     com_github_stephengold_joltjni_StateRecorder
 * Method:    readRVec3
 * Signature: (J[D)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_StateRecorder_readRVec3
  (JNIEnv *pEnv, jclass, jlong recorderVa, jdoubleArray tmpDoubles) {
    StateRecorder * const pRecorder
            = reinterpret_cast<StateRecorder *> (recorderVa);
    jboolean isCopy;
    jdouble * const pTmpDoubles
            = pEnv->GetDoubleArrayElements(tmpDoubles, &isCopy);
    RVec3 rvec3(pTmpDoubles[0], pTmpDoubles[1], pTmpDoubles[2]);
    pRecorder->Read(rvec3);
    pTmpDoubles[0] = rvec3.GetX();
    pTmpDoubles[1] = rvec3.GetY();
    pTmpDoubles[2] = rvec3.GetZ();
    pEnv->ReleaseDoubleArrayElements(tmpDoubles, pTmpDoubles, 0);
}

/*
 * Class:     com_github_stephengold_joltjni_StateRecorder
 * Method:    readString
 * Signature: (JLjava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_github_stephengold_joltjni_StateRecorder_readString
  (JNIEnv *pEnv, jclass, jlong recorderVa, jstring javaString) {
    StateRecorder * const pRecorder
            = reinterpret_cast<StateRecorder *> (recorderVa);
    jboolean isCopy;
    const char * const cString = pEnv->GetStringUTFChars(javaString, &isCopy);
    std::string cppString(cString);
    pEnv->ReleaseStringUTFChars(javaString, cString);
    pRecorder->Read(cppString);
    const char * const pResult = cppString.c_str();
    const jstring result = pEnv->NewStringUTF(pResult);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_StateRecorder
 * Method:    readVec3
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_StateRecorder_readVec3
  (JNIEnv *pEnv, jclass, jlong recorderVa, jobject floatBuffer) {
    StateRecorder * const pRecorder
            = reinterpret_cast<StateRecorder *> (recorderVa);
    DIRECT_FLOAT_BUFFER(pEnv, floatBuffer, pStoreFloats, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    Vec3 result(pStoreFloats[0], pStoreFloats[1], pStoreFloats[2]);
    pRecorder->Read(result);
    pStoreFloats[0] = result.GetX();
    pStoreFloats[1] = result.GetY();
    pStoreFloats[2] = result.GetZ();
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
 * Method:    writeBodyIdVector
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_StateRecorder_writeBodyIdVector
  (JNIEnv *, jclass, jlong recorderVa, jlong vectorVa) {
    StateRecorder * const pRecorder
            = reinterpret_cast<StateRecorder *> (recorderVa);
    const BodyIDVector * const pVector
            = reinterpret_cast<BodyIDVector *> (vectorVa);
    pRecorder->Write(*pVector);
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
 * Method:    writeInt
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_StateRecorder_writeInt
  (JNIEnv *, jclass, jlong recorderVa, jint i) {
    StateRecorder * const pRecorder
            = reinterpret_cast<StateRecorder *> (recorderVa);
    pRecorder->Write(i);
}

/*
 * Class:     com_github_stephengold_joltjni_StateRecorder
 * Method:    writeRMat44
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_StateRecorder_writeRMat44
  (JNIEnv *, jclass, jlong recorderVa, jlong matrixVa) {
    StateRecorder * const pRecorder
            = reinterpret_cast<StateRecorder *> (recorderVa);
    const RMat44 * const pMatrix = reinterpret_cast<RMat44 *> (matrixVa);
    pRecorder->Write(*pMatrix);
}

/*
 * Class:     com_github_stephengold_joltjni_StateRecorder
 * Method:    writeRVec3
 * Signature: (JDDD)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_StateRecorder_writeRVec3
  (JNIEnv *, jclass, jlong recorderVa, jdouble xx, jdouble yy, jdouble zz) {
    StateRecorder * const pRecorder
            = reinterpret_cast<StateRecorder *> (recorderVa);
    const RVec3 rvec3(xx, yy, zz);
    pRecorder->Write(rvec3);
}

/*
 * Class:     com_github_stephengold_joltjni_StateRecorder
 * Method:    writeString
 * Signature: (JLjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_StateRecorder_writeString
  (JNIEnv *pEnv, jclass, jlong recorderVa, jstring javaString) {
    StateRecorder * const pRecorder
            = reinterpret_cast<StateRecorder *> (recorderVa);
    jboolean isCopy;
    const char * const cString = pEnv->GetStringUTFChars(javaString, &isCopy);
    std::string cppString(cString);
    pEnv->ReleaseStringUTFChars(javaString, cString);
    pRecorder->Write(cppString);
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