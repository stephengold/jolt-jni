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
#include <Jolt/ConfigurationString.h>
#include <Jolt/Core/Factory.h>
#include <Jolt/Core/Profiler.h>
#include <Jolt/Core/TempAllocator.h>
#include <Jolt/Physics/DeterminismLog.h>
#include <Jolt/RegisterTypes.h>

#include "auto/com_github_stephengold_joltjni_Jolt.h"
#include "glue/glue.h"
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
    TRACE_DELETE("Factory", Factory::sInstance)
    delete Factory::sInstance;
    Factory::sInstance = nullptr;
}

#ifndef JPH_ENABLE_DETERMINISM_LOG
bool gWarnDetLogIneffective = true;
#endif

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    detLog
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Jolt_detLog
  (JNIEnv *pEnv, jclass, jstring message) {
#ifdef JPH_ENABLE_DETERMINISM_LOG
    jboolean isCopy;
    const char * const pMessage = pEnv->GetStringUTFChars(message, &isCopy);
    JPH_DET_LOG(pMessage);
    pEnv->ReleaseStringUTFChars(message, pMessage);
#else
    if (gWarnDetLogIneffective) {
        Trace("Jolt.detLog() has no effect unless JPH_ENABLE_DETERMINISM_LOG is defined.");
        gWarnDetLogIneffective = false;
    }
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    getConfigurationString
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_github_stephengold_joltjni_Jolt_getConfigurationString
  (JNIEnv *pEnv, jclass) {
    const char *pString = GetConfigurationString();
    jstring result = pEnv->NewStringUTF(pString);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    hashBytes
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Jolt_hashBytes__JI
  (JNIEnv *, jclass, jlong dataVa, jint inSize) {
    const void * const pData = reinterpret_cast<void *> (dataVa);
    const uint64 result = HashBytes(pData, inSize);
    return result;
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
 * Method:    profileDump
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Jolt_profileDump
  (JNIEnv *pEnv, jclass, jstring message) {
    jboolean isCopy;
    const char * const pMessage = pEnv->GetStringUTFChars(message, &isCopy);
    JPH_PROFILE_DUMP(pMessage);
    pEnv->ReleaseStringUTFChars(message, pMessage);
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    profileEnd
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Jolt_profileEnd
  (JNIEnv *, jclass) {
    JPH_PROFILE_END();
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    profileNextFrame
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Jolt_profileNextFrame
  (JNIEnv *, jclass) {
    JPH_PROFILE_NEXTFRAME();
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    profileStart
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Jolt_profileStart
  (JNIEnv *pEnv, jclass, jstring name) {
    jboolean isCopy;
    const char * const pName = pEnv->GetStringUTFChars(name, &isCopy);
    JPH_PROFILE_START(pName);
    pEnv->ReleaseStringUTFChars(name, pName);
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    newFactory
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Jolt_newFactory
  (JNIEnv *, jclass) {
    Factory::sInstance = new Factory();
    TRACE_NEW("Factory", Factory::sInstance)
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

#ifdef _DEBUG
// global flag to enable tracing of new/delete operations in glue code:
bool gTraceAllocations = false;
#endif

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    setTraceAllocations
 * Signature: (Z)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Jolt_setTraceAllocations
  (JNIEnv *, jclass, jboolean setting) {
#ifdef _DEBUG
    gTraceAllocations = setting;
#else
    Trace("setTraceAlloations() has no effect in a Release native library.");
#endif
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
    jstring result = pEnv->NewStringUTF("0.6.1-SNAPSHOT");
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    hashBytes
 * Signature: (DDDJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Jolt_hashBytes__DDDJ
  (JNIEnv *, jclass, jdouble xx, jdouble yy, jdouble zz, jlong oldHash) {
    const RVec3 vector(xx, yy, zz);
    const uint64 result = HashBytes(&vector, sizeof(RVec3), oldHash);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    hashBytes
 * Signature: (FFFFJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Jolt_hashBytes__FFFFJ
  (JNIEnv *, jclass, jfloat qx, jfloat qy, jfloat qz, jfloat qw, jlong oldHash) {
    const Quat quat(qx, qy, qz, qw);
    const uint64 result = HashBytes(&quat, sizeof(Quat), oldHash);
    return result;
}