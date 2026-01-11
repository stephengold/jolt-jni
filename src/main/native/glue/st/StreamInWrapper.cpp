/*
Copyright (c) 2024-2026 Stephen Gold

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
#include "Jolt/Core/StreamWrapper.h"
#include "auto/com_github_stephengold_joltjni_StreamInWrapper.h"
#include "glue/glue.h"
#include <fstream>

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_StreamInWrapper
 * Method:    app
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_StreamInWrapper_app
  (JNIEnv *, jclass) {
    const std::ios_base::openmode result = std::ifstream::app;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_StreamInWrapper
 * Method:    ate
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_StreamInWrapper_ate
  (JNIEnv *, jclass) {
    const std::ios_base::openmode result = std::ifstream::ate;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_StreamInWrapper
 * Method:    binary
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_StreamInWrapper_binary
  (JNIEnv *, jclass) {
    const std::ios_base::openmode result = std::ifstream::binary;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_StreamInWrapper
 * Method:    in
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_StreamInWrapper_in
  (JNIEnv *, jclass) {
    const std::ios_base::openmode result = std::ifstream::in;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_StreamInWrapper
 * Method:    out
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_StreamInWrapper_out
  (JNIEnv *, jclass) {
    const std::ios_base::openmode result = std::ifstream::out;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_StreamInWrapper
 * Method:    trunc
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_StreamInWrapper_trunc
  (JNIEnv *, jclass) {
    const std::ios_base::openmode result = std::ifstream::trunc;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_StreamInWrapper
 * Method:    createFromStringStream
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_StreamInWrapper_createFromStringStream
  (JNIEnv *, jclass, jlong streamVa) {
    std::stringstream * const pStream
            = reinterpret_cast<std::stringstream *> (streamVa);
    StreamInWrapper * const pResult = new StreamInWrapper(*pStream);
    TRACE_NEW("StreamInWrapper", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_StreamInWrapper
 * Method:    createStreamInWrapper
 * Signature: (Ljava/lang/String;I)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_StreamInWrapper_createStreamInWrapper
  (JNIEnv *pEnv, jclass, jstring fileName, jint fileMode) {
    std::ifstream * const pStream = new std::ifstream(); // TODO garbage
    TRACE_NEW("ifstream", pStream)
    jboolean isCopy;
    const char * const pFileName = pEnv->GetStringUTFChars(fileName, &isCopy);
    const std::ios_base::openmode mode = (std::ios_base::openmode) fileMode;
    pStream->open(pFileName, mode);
    pEnv->ReleaseStringUTFChars(fileName, pFileName);
    StreamInWrapper *pResult = nullptr;
    if (pStream->is_open()) {
        pResult = new StreamInWrapper(*pStream);
        TRACE_NEW("StreamInWrapper", pResult)
    }
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_StreamInWrapper
 * Method:    isEof
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_StreamInWrapper_isEof
  (JNIEnv *, jclass, jlong streamVa) {
    const StreamInWrapper * const pWrapper
            = reinterpret_cast<StreamInWrapper *> (streamVa);
    const bool result = pWrapper->IsEOF();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_StreamInWrapper
 * Method:    readFloat3
 * Signature: (JLjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_StreamInWrapper_readFloat3
  (JNIEnv *pEnv, jclass, jlong streamVa, jobject buffer) {
    StreamInWrapper * const pWrapper
            = reinterpret_cast<StreamInWrapper *> (streamVa);
    Float3 result;
    pWrapper->ReadBytes((char *)&result, sizeof(result));
    DIRECT_FLOAT_BUFFER(pEnv, buffer, pData, capacityFloats);
    JPH_ASSERT(capacityFloats >= 3);
    pData[0] = result.x;
    pData[1] = result.y;
    pData[2] = result.z;
}

/*
 * Class:     com_github_stephengold_joltjni_StreamInWrapper
 * Method:    readInt
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_StreamInWrapper_readInt
  (JNIEnv *, jclass, jlong streamVa) {
    StreamInWrapper * const pWrapper
            = reinterpret_cast<StreamInWrapper *> (streamVa);
    jint result;
    pWrapper->ReadBytes((char *)&result, sizeof(result));
    return result;
}