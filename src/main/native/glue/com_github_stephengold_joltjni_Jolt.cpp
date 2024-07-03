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
#include <Jolt/Core/Factory.h>
#include <Jolt/Core/TempAllocator.h>
#include <Jolt/RegisterTypes.h>

#include "auto/com_github_stephengold_joltjni_Jolt.h"
#include <iostream>

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    buildType
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_github_stephengold_joltjni_Jolt_buildType
  (JNIEnv *pEnv, jclass) {
    jstring result;
#ifdef JPH_ENABLE_ASSERTS
    result = pEnv->NewStringUTF("Debug");
#else
    result = pEnv->NewStringUTF("Release");
#endif // JPH_ENABLE_ASSERTS
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    destroyFactory
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Jolt_destroyFactory
  (JNIEnv *, jclass) {
    delete Factory::sInstance;
    Factory::sInstance = nullptr;
}

#ifdef JPH_ENABLE_ASSERTS

// Callback for asserts
static bool DefaultAssertFailed(const char *inExpression, const char *inMessage,
        const char *inFile, uint inLine) {
    // Print to the standard output:
    std::cout << inFile << ":" << inLine << ": (" << inExpression << ") "
            << (inMessage != nullptr ? inMessage : "") << std::endl;

    // Breakpoint:
    return true;
};

#endif // JPH_ENABLE_ASSERTS

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    installDefaultAssertCallback
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Jolt_installDefaultAssertCallback
  (JNIEnv *, jclass) {
#ifdef JPH_ENABLE_ASSERTS
    AssertFailed = DefaultAssertFailed;
#endif
}

static void DefaultTrace(const char *inFormat, ...) {
    // Format the message:
    va_list list;
    va_start(list, inFormat);
    char buffer[1024];
    vsnprintf(buffer, sizeof(buffer), inFormat, list);
    va_end(list);

    // Print the standard output:
    std::cout << buffer << std::endl;
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    installDefaultTraceCallback
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Jolt_installDefaultTraceCallback
  (JNIEnv *, jclass) {
    Trace = DefaultTrace;
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    isDoublePrecision
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_Jolt_isDoublePrecision
  (JNIEnv *, jclass) {
#ifdef JPH_DOUBLE_PRECISION
    return JNI_TRUE;
#else
    return JNI_FALSE;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    newFactory
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Jolt_newFactory
  (JNIEnv *, jclass) {
    Factory::sInstance = new Factory();
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    registerDefaultAllocator
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Jolt_registerDefaultAllocator
  (JNIEnv *, jclass) {
    RegisterDefaultAllocator();
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    registerTypes
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Jolt_registerTypes
  (JNIEnv *, jclass) {
    RegisterTypes();
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    unregisterTypes
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Jolt_unregisterTypes
  (JNIEnv *, jclass) {
    UnregisterTypes();
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    versionString
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_github_stephengold_joltjni_Jolt_versionString
  (JNIEnv *pEnv, jclass) {
    jstring result = pEnv->NewStringUTF("0.1.4");
    return result;
}
