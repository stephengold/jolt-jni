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
#include <Jolt/Core/StreamWrapper.h>
#include "auto/com_github_stephengold_joltjni_StreamOutWrapper.h"
#include "glue/glue.h"
#include <fstream>

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_StreamOutWrapper
 * Method:    app
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_StreamOutWrapper_app
  (JNIEnv *, jclass) {
    const std::ios_base::openmode result = std::ofstream::app;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_StreamOutWrapper
 * Method:    ate
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_StreamOutWrapper_ate
  (JNIEnv *, jclass) {
    const std::ios_base::openmode result = std::ofstream::ate;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_StreamOutWrapper
 * Method:    binary
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_StreamOutWrapper_binary
  (JNIEnv *, jclass) {
    const std::ios_base::openmode result = std::ofstream::binary;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_StreamOutWrapper
 * Method:    in
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_StreamOutWrapper_in
  (JNIEnv *, jclass) {
    const std::ios_base::openmode result = std::ofstream::in;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_StreamOutWrapper
 * Method:    out
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_StreamOutWrapper_out
  (JNIEnv *, jclass) {
    const std::ios_base::openmode result = std::ofstream::out;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_StreamOutWrapper
 * Method:    trunc
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_StreamOutWrapper_trunc
  (JNIEnv *, jclass) {
    const std::ios_base::openmode result = std::ofstream::trunc;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_StreamOutWrapper
 * Method:    createStreamOutWrapper
 * Signature: (Ljava/lang/String;I)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_StreamOutWrapper_createStreamOutWrapper
  (JNIEnv *pEnv, jclass, jstring fileName, jint fileMode) {
    std::ofstream * const pStream = new std::ofstream(); // TODO garbage
    TRACE_NEW("ofstream", pStream)
    jboolean isCopy;
    const char * const pFileName = pEnv->GetStringUTFChars(fileName, &isCopy);
    const std::ios_base::openmode mode = (std::ios_base::openmode) fileMode;
    pStream->open(pFileName, mode);
    pEnv->ReleaseStringUTFChars(fileName, pFileName);
    StreamOutWrapper * const pResult = new StreamOutWrapper(*pStream);
    TRACE_NEW("StreamOutWrapper", pResult)
    return reinterpret_cast<jlong> (pResult);
}