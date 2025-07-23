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
#include "auto/com_github_stephengold_joltjni_std_OfStream.h"
#include "glue/glue.h"
#include <fstream>

using namespace std;

/*
 * Class:     com_github_stephengold_joltjni_std_OfStream
 * Method:    closeStream
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_std_OfStream_closeStream
  (JNIEnv *, jclass, jlong streamVa) {
    ofstream * const pStream = reinterpret_cast<ofstream *> (streamVa);
    pStream->close();
}

/*
 * Class:     com_github_stephengold_joltjni_std_OfStream
 * Method:    create
 * Signature: (Ljava/lang/String;I)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_std_OfStream_create
  (JNIEnv *pEnv, jclass, jstring fileName, jint fileMode) {
    ofstream * const pStream = new ofstream();
    TRACE_NEW("ofstream", pStream)
    jboolean isCopy;
    const char * const pFileName = pEnv->GetStringUTFChars(fileName, &isCopy);
    const ios_base::openmode mode = (ios_base::openmode) fileMode;
    pStream->open(pFileName, mode);
    pEnv->ReleaseStringUTFChars(fileName, pFileName);
    return reinterpret_cast<jlong> (pStream);
}

/*
 * Class:     com_github_stephengold_joltjni_std_OfStream
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_std_OfStream_free
    BODYOF_FREE(ofstream)

/*
 * Class:     com_github_stephengold_joltjni_std_OfStream
 * Method:    tellp
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_std_OfStream_tellp
  (JNIEnv *, jclass, jlong streamVa) {
    ofstream * const pStream = reinterpret_cast<ofstream *> (streamVa);
    ofstream::pos_type result = pStream->tellp();
    return result;
}