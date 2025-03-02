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
#include "Jolt/ConfigurationString.h"
#include "Jolt/Core/Factory.h"
#include "Jolt/Core/HashCombine.h"
#include "Jolt/Core/Profiler.h"
#include "Jolt/Core/TempAllocator.h"
#include "Jolt/Geometry/RayAABox.h"
#include "Jolt/Physics/Body/BodyID.h"
#include "Jolt/Physics/Character/CharacterID.h"
#include "Jolt/Physics/DeterminismLog.h"
#include "Jolt/RegisterTypes.h"
#include "TestFramework/External/Perlin.h"

#include "auto/com_github_stephengold_joltjni_Jolt.h"
#include "glue/glue.h"
#include <iostream>

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    aCos
 * Signature: (F)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Jolt_aCos
  (JNIEnv *, jclass, jfloat ratio) {
    const float result = ACos(ratio);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    aTan
 * Signature: (F)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Jolt_aTan
  (JNIEnv *, jclass, jfloat ratio) {
    const float result = ATan(ratio);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    aTan2
 * Signature: (FF)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Jolt_aTan2
  (JNIEnv *, jclass, jfloat opposite, jfloat adjacent) {
    const float result = ATan2(opposite, adjacent);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    buildType
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_github_stephengold_joltjni_Jolt_buildType
  (JNIEnv *pEnv, jclass) {
    jstring result;
#ifdef JPH_NO_DEBUG
    result = pEnv->NewStringUTF("Release");
#else
    result = pEnv->NewStringUTF("Debug");
#endif
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    cos
 * Signature: (F)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Jolt_cos
  (JNIEnv *, jclass, jfloat angle) {
    const float result = Cos(angle);
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
#elif defined(_DEBUG)
    if (gWarnDetLogIneffective) {
        std::cout << "Jolt.detLog() has no effect unless JPH_ENABLE_DETERMINISM_LOG is defined."
                << std::endl;
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
    const char * const pString = GetConfigurationString();
    const jstring result = pEnv->NewStringUTF(pString);
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

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    hashCombine
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Jolt_hashCombine__JI
  (JNIEnv *, jclass, jlong oldHash, jint iValue) {
    uint64 result = oldHash;
    HashCombine(result, (uint32)iValue);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    hashCombine
 * Signature: (JJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Jolt_hashCombine__JJ
  (JNIEnv *, jclass, jlong oldHash, jlong lValue) {
    uint64 result = oldHash;
    HashCombine(result, (uint64)lValue);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    implementsDebugRendering
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_Jolt_implementsDebugRendering
  (JNIEnv *, jclass) {
#ifdef JPH_DEBUG_RENDERER
    return JNI_TRUE;
#else
    return JNI_FALSE;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    implementsDeterminismLog
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_Jolt_implementsDeterminismLog
  (JNIEnv *, jclass) {
#ifdef JPH_DET_LOG
    return JNI_TRUE;
#else
    return JNI_FALSE;
#endif
}

#ifdef JPH_ENABLE_ASSERTS

// Callback for asserts
static bool DefaultAssertFailed(const char *inExpression, const char *inMessage,
        const char *inFile, uint inLine) {
    // Print to the standard output:
    std::cout << inFile << ":" << inLine << ": (" << inExpression << ") "
            << (inMessage != nullptr ? inMessage : "") << std::endl;

    // Request a breakpoint:
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
#elif defined(_DEBUG)
    std::cout << "Jolt.installDefaultAssertCallback() has no effect unless JPH_ENABLE_ASSERTS is defined."
            << std::endl;
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
 * Method:    perlinNoise3
 * Signature: (FFFIII)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Jolt_perlinNoise3
  (JNIEnv *, jclass, jfloat x, jfloat y, jfloat z, jint xWrap, jint yWrap, jint zWrap) {
    const float result = PerlinNoise3(x, y, z, xWrap, yWrap, zWrap);
    return result;
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
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_Jolt_newFactory
  (JNIEnv *, jclass) {
#if !defined(JPH_NO_DEBUG) && !defined(JPH_DISABLE_CUSTOM_ALLOCATOR)
    if (!Allocate) {
        std::cerr << "Can't create a Factory because no default allocator is registered!"
                << std::endl;
        return JNI_FALSE;
    }
#endif
    Factory::sInstance = new Factory();
    TRACE_NEW("Factory", Factory::sInstance)
    return Factory::sInstance != nullptr;
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
    std::cout << "Jolt.setTraceAllocations() has no effect" << " in a Release native library." << std::endl;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    sin
 * Signature: (F)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Jolt_sin
  (JNIEnv *, jclass, jfloat angle) {
    const float result = Sin(angle);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    supportsObjectStream
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_Jolt_supportsObjectStream
  (JNIEnv *, jclass) {
#ifdef JPH_OBJECT_STREAM
    return JNI_TRUE;
#else
    return JNI_FALSE;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    tan
 * Signature: (F)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Jolt_tan
  (JNIEnv *, jclass, jfloat angle) {
    const float result = Tan(angle);
    return result;
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
    const jstring result = pEnv->NewStringUTF("0.9.7-SNAPSHOT");
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

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    hashCombineBodyId
 * Signature: (JJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Jolt_hashCombineBodyId
  (JNIEnv *, jclass, jlong oldHash, jlong idVa) {
    uint64 result = oldHash;
    const BodyID * const pId = reinterpret_cast<BodyID *> (idVa);
    HashCombine(result, *pId);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    hashCombineCharacterId
 * Signature: (JJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Jolt_hashCombineCharacterId
  (JNIEnv *, jclass, jlong oldHash, jlong idVa) {
    uint64 result = oldHash;
    const CharacterID * const pId = reinterpret_cast<CharacterID *> (idVa);
    HashCombine(result, *pId);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    hashCombineRVec3
 * Signature: (JDDD)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Jolt_hashCombineRVec3
  (JNIEnv *, jclass, jlong oldHash, jdouble xx, jdouble yy, jdouble zz) {
    uint64 result = oldHash;
    const RVec3 rvec(xx, yy, zz);
    HashCombine(result, rvec);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    hashCombineVec3
 * Signature: (JFFF)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Jolt_hashCombineVec3
  (JNIEnv *, jclass, jlong oldHash, jfloat x, jfloat y, jfloat z) {
    uint64 result = oldHash;
    const Vec3 vec(x, y, z);
    HashCombine(result, vec);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    rayAaBoxHits
 * Signature: (FFFFFFFFFFFF)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_Jolt_rayAaBoxHits
  (JNIEnv *, jclass, jfloat startX, jfloat startY, jfloat startZ,
  jfloat offsetX, jfloat offsetY, jfloat offsetZ, jfloat minX, jfloat minY,
  jfloat minZ, jfloat maxX, jfloat maxY, jfloat maxZ) {
    const Vec3 start(startX, startY, startZ);
    const Vec3 offset(offsetX, offsetY, offsetZ);
    const Vec3 min(minX, minY, minZ);
    const Vec3 max(maxX, maxY, maxZ);
    const bool result = RayAABoxHits(start, offset, min, max);
    return result;
}
