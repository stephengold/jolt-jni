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
#include "auto/com_github_stephengold_joltjni_std_StringStream.h"
#include "glue/glue.h"

using namespace std;

/*
 * Class:     com_github_stephengold_joltjni_std_StringStream
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_std_StringStream_createDefault
  (JNIEnv *, jclass) {
    stringstream * const pResult = new stringstream();
    TRACE_NEW("stringstream", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_std_StringStream
 * Method:    createFromString
 * Signature: (Ljava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_std_StringStream_createFromString
  (JNIEnv *pEnv, jclass, jstring javaString) {
    jboolean isCopy;
    // javaString may contain '\0', so don't use GetStringUTFChars() !
    const jchar * const pTmpUcs2 = pEnv->GetStringChars(javaString, &isCopy);
    const jsize len = pEnv->GetStringLength(javaString);
    char * const pBuffer = new char[len];
    for (int i = 0; i < len; ++i) {
        pBuffer[i] = pTmpUcs2[i];
    }
    pEnv->ReleaseStringChars(javaString, pTmpUcs2);
    const string cppString(pBuffer, len);
    delete[] pBuffer;
    stringstream * const pResult = new stringstream(cppString);
    TRACE_NEW("stringstream", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_std_StringStream
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_std_StringStream_free
  (JNIEnv *, jclass, jlong streamVa) {
    stringstream * const pStream = reinterpret_cast<stringstream *> (streamVa);
    TRACE_DELETE("stringstream", pStream)
    delete pStream;
}

/*
 * Class:     com_github_stephengold_joltjni_std_StringStream
 * Method:    str
 * Signature: (J)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_github_stephengold_joltjni_std_StringStream_str
  (JNIEnv *pEnv, jclass, jlong streamVa) {
    const stringstream * const pStream
            = reinterpret_cast<stringstream *> (streamVa);
    const string cppString = pStream->str();
    const jsize len = cppString.size();
    // cppString may contain '\0', so don't use cppString.c_str() !
    jchar * const pTmpUcs2 = new jchar[len];
    for (int i = 0; i < len; ++i) {
        pTmpUcs2[i] = cppString[i];
    }
    jstring result = pEnv->NewString(pTmpUcs2, len);
    delete[] pTmpUcs2;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_std_StringStream
 * Method:    tellg
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_std_StringStream_tellg
  (JNIEnv *, jclass, jlong streamVa) {
    stringstream * const pStream = reinterpret_cast<stringstream *> (streamVa);
    stringstream::pos_type result = pStream->tellg();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_std_StringStream
 * Method:    tellp
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_std_StringStream_tellp
  (JNIEnv *, jclass, jlong streamVa) {
    stringstream * const pStream = reinterpret_cast<stringstream *> (streamVa);
    stringstream::pos_type result = pStream->tellp();
    return result;
}