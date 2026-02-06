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
#include "Jolt/ConfigurationString.h"
#include "Jolt/Core/Factory.h"
#include "Jolt/Core/HashCombine.h"
#include "Jolt/Core/Profiler.h"
#include "Jolt/Core/TempAllocator.h"
#include "Jolt/Geometry/RayAABox.h"
#include "Jolt/Geometry/RayCapsule.h"
#include "Jolt/Geometry/RayCylinder.h"
#include "Jolt/Geometry/RaySphere.h"
#include "Jolt/Geometry/RayTriangle.h"
#include "Jolt/Math/Swizzle.h"
#include "Jolt/Physics/Body/Body.h"
#include "Jolt/Physics/Body/BodyID.h"
#include "Jolt/Physics/Character/CharacterID.h"
#include "Jolt/Physics/Collision/ContactListener.h"
#include "Jolt/Physics/Collision/EstimateCollisionResponse.h"
#include "Jolt/Physics/DeterminismLog.h"
#include "Jolt/Physics/PhysicsSettings.h"
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
#ifdef JPH_DEBUG
    result = pEnv->NewStringUTF("Debug");
#else
    result = pEnv->NewStringUTF("Release");
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
 * Method:    countDeletes
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Jolt_countDeletes
  (JNIEnv *, jclass) {
#ifdef JPH_DEBUG
    return gDeleteCount;
#else
    std::cout << "Jolt.countDeletes() returns 0 in a Release native library."
            << std::endl;
    return 0;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    countNews
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Jolt_countNews
  (JNIEnv *, jclass) {
#ifdef JPH_DEBUG
    return gNewCount;
#else
    std::cout << "Jolt.countNews() returns 0 in a Release native library."
            << std::endl;
    return 0;
#endif
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
#elif defined(JPH_DEBUG)
    if (gWarnDetLogIneffective) {
        std::cout << "Jolt.detLog() has no effect unless JPH_ENABLE_DETERMINISM_LOG is defined."
                << std::endl;
        gWarnDetLogIneffective = false;
    }
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    getAssertCallback
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Jolt_getAssertCallback
  (JNIEnv *, jclass) {
#ifdef JPH_ENABLE_ASSERTS
    return reinterpret_cast<jlong> (AssertFailed);
#else
    return 0;
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
 * Method:    implementsComputeCpu
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_Jolt_implementsComputeCpu
  (JNIEnv *, jclass) {
#ifdef JPH_USE_CPU_COMPUTE
    return JNI_TRUE;
#else
    return JNI_FALSE;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    implementsComputeDx12
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_Jolt_implementsComputeDx12
  (JNIEnv *, jclass) {
#ifdef JPH_USE_DX12
    return JNI_TRUE;
#else
    return JNI_FALSE;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    implementsComputeMtl
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_Jolt_implementsComputeMtl
  (JNIEnv *, jclass) {
#ifdef JPH_USE_MTL
    return JNI_TRUE;
#else
    return JNI_FALSE;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    implementsComputeVk
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_Jolt_implementsComputeVk
  (JNIEnv *, jclass) {
#ifdef JPH_USE_VK
    return JNI_TRUE;
#else
    return JNI_FALSE;
#endif
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
#ifdef JPH_ENABLE_DETERMINISM_LOG
    return JNI_TRUE;
#else
    return JNI_FALSE;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    installAssertCallback
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Jolt_installAssertCallback
  (JNIEnv *, jclass, jint callback) {
#ifdef JPH_ENABLE_ASSERTS
    AssertFailed = reinterpret_cast<AssertFailedFunction> (callback);
#elif defined(JPH_DEBUG)
    std::cout << "Jolt.installAssertCallback() has no effect unless JPH_ENABLE_ASSERTS is defined."
            << std::endl;
#endif
}

static void CerrTrace(const char *inFormat, ...) {
    // Format the message:
    va_list list;
    va_start(list, inFormat);
    char buffer[1024];
    vsnprintf(buffer, sizeof(buffer), inFormat, list);
    va_end(list);
    // Append it to the C++ standard error output stream:
    std::cerr << buffer << std::endl;
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    installCerrTraceCallback
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Jolt_installCerrTraceCallback
  (JNIEnv *, jclass) {
    Trace = CerrTrace;
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
#elif defined(JPH_DEBUG)
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
    // Append it to the C++ standard output stream:
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

JavaVM *gpVM;
jmethodID gFlushMethodId, gPrintMethodId;
jobject gTraceStream;
static void JavaTrace(const char *inFormat, ...) {
    // Format the message:
    va_list list;
    va_start(list, inFormat);
    char buffer[1024];
    vsnprintf(buffer, sizeof(buffer), inFormat, list);
    va_end(list);
    // Attach to the JVM:
    JNIEnv *pAttachEnv;
    jint retCode = ATTACH_CURRENT_THREAD(gpVM, &pAttachEnv);
    JPH_ASSERT(retCode == JNI_OK);
    // Create a Java string:
    jstring javaString = pAttachEnv->NewStringUTF(buffer);
    JPH_ASSERT(javaString != NULL);
    // Print to the configured PrintStream:
    pAttachEnv->CallVoidMethod(gTraceStream, gPrintMethodId, javaString);
    EXCEPTION_CHECK(pAttachEnv)
    // Flush the PrintStream:
    pAttachEnv->CallVoidMethod(gTraceStream, gFlushMethodId);
    EXCEPTION_CHECK(pAttachEnv)
    // Detach from the JVM:
    gpVM->DetachCurrentThread();
}

#ifdef JPH_ENABLE_ASSERTS

// Callback for asserts that are silently ignored:
static bool IgnoreAssertFailed(const char *inExpression, const char *inMessage,
        const char *inFile, uint inLine) {
    return false;
};

#endif // JPH_ENABLE_ASSERTS

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    installIgnoreAssertCallback
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Jolt_installIgnoreAssertCallback
  (JNIEnv *, jclass) {
#ifdef JPH_ENABLE_ASSERTS
    AssertFailed = IgnoreAssertFailed;
#elif defined(JPH_DEBUG)
    std::cout << "Jolt.installIgnoreAssertCallback() has no effect unless JPH_ENABLE_ASSERTS is defined."
            << std::endl;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    installJavaTraceCallback
 * Signature: (Ljava/io/PrintStream;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Jolt_installJavaTraceCallback
  (JNIEnv *pEnv, jclass, jobject stream) {
    pEnv->GetJavaVM(&gpVM);
    gTraceStream = pEnv->NewGlobalRef(stream);
    EXCEPTION_CHECK(pEnv)
    const jclass clss = pEnv->FindClass("java/io/PrintStream");
    EXCEPTION_CHECK(pEnv)
    gFlushMethodId = pEnv->GetMethodID(clss, "flush", "()V");
    EXCEPTION_CHECK(pEnv)
    gPrintMethodId
            = pEnv->GetMethodID(clss, "println", "(Ljava/lang/String;)V");
    EXCEPTION_CHECK(pEnv)
    //
    Trace = JavaTrace;
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
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_stephengold_joltjni_Jolt_newFactory
  (JNIEnv *, jclass) {
    // Verify global constants defined in Jolt.java:
    JPH_ASSERT(com_github_stephengold_joltjni_Jolt_cDefaultConvexRadius == cDefaultConvexRadius);
    JPH_ASSERT(com_github_stephengold_joltjni_Jolt_JPH_PI == JPH_PI);
    JPH_ASSERT((int)com_github_stephengold_joltjni_Jolt_cEmptySubShapeId == SubShapeID().GetValue());
    JPH_ASSERT((int)com_github_stephengold_joltjni_Jolt_cInvalidBodyId == BodyID::cInvalidBodyID);
    JPH_ASSERT(com_github_stephengold_joltjni_Jolt_cMaxPhysicsBarriers == cMaxPhysicsBarriers);
    JPH_ASSERT(com_github_stephengold_joltjni_Jolt_cMaxPhysicsJobs == cMaxPhysicsJobs);
    JPH_ASSERT(com_github_stephengold_joltjni_Jolt_SWIZZLE_X == SWIZZLE_X);
    JPH_ASSERT(com_github_stephengold_joltjni_Jolt_SWIZZLE_Y == SWIZZLE_Y);
    JPH_ASSERT(com_github_stephengold_joltjni_Jolt_SWIZZLE_Z == SWIZZLE_Z);

#if defined(JPH_DEBUG) && !defined(JPH_DISABLE_CUSTOM_ALLOCATOR)
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
 * Method:    registerCustomAllocator
 * Signature: (JJJJJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Jolt_registerCustomAllocator
  (JNIEnv *, jclass, jlong allocateVa, jlong reallocateVa, jlong freeVa,
            jlong alignedAllocateVa, jlong alignedFreeVa) {
#ifdef JPH_DISABLE_CUSTOM_ALLOCATOR
    std::cerr << "Can't register a custom allocator!" << std::endl;
#else
    Allocate = reinterpret_cast<AllocateFunction> (allocateVa);
    Reallocate = reinterpret_cast<ReallocateFunction> (reallocateVa);
    Free = reinterpret_cast<FreeFunction> (freeVa);
    AlignedAllocate = reinterpret_cast<AlignedAllocateFunction> (alignedAllocateVa);
    AlignedFree = reinterpret_cast<AlignedFreeFunction> (alignedFreeVa);
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    registerDefaultAllocator
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Jolt_registerDefaultAllocator
  (JNIEnv *, jclass) {
#ifndef JPH_DISABLE_CUSTOM_ALLOCATOR
    RegisterDefaultAllocator();
#endif
}

// track whether RegisterTypes()/UnregisterTypes() have been invoked:
bool gTypesAreRegistered = false;

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    registerHair
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Jolt_registerHair
  (JNIEnv *, jclass) {
    if (!gTypesAreRegistered) {
        std::cout << "Invoke Jolt.registerTypes() before registerHair()!"
                << std::endl;
        return;
    }
    RegisterHair();
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    registerTypes
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Jolt_registerTypes
  (JNIEnv *, jclass) {
    if (gTypesAreRegistered) {
        std::cout << "Multiple invocations of Jolt.registerTypes()!"
                << std::endl;
        return;
    }
    RegisterTypes();
    gTypesAreRegistered = true;
}

#ifdef JPH_DEBUG
// global count of delete operations in glue code:
std::atomic<JPH::uint32> gDeleteCount{0};

// global count of new operations in glue code:
std::atomic<JPH::uint32> gNewCount{0};

// global flag to enable counting/tracing of new/delete operations in glue code:
bool gTraceAllocations = false;
#endif

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    setTraceAllocations
 * Signature: (Z)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Jolt_setTraceAllocations
  (JNIEnv *, jclass, jboolean setting) {
#ifdef JPH_DEBUG
    gTraceAllocations = setting;
#else
    std::cout << "Jolt.setTraceAllocations() has no effect in a Release native library."
            << std::endl;
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
 * Method:    sSetNextCharacterId
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Jolt_sSetNextCharacterId
  (JNIEnv *, jclass, jint nextValue) {
    CharacterID::sSetNextCharacterID(nextValue);
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
 * Method:    test000
 * Signature: ([Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Jolt_test000
  (JNIEnv *, jclass, jobjectArray) {
#ifdef JPH_DEBUG
    RegisterDefaultAllocator();
    Trace = DefaultTrace;
    AssertFailed = DefaultAssertFailed;
    Factory::sInstance = new Factory;
    TRACE_NEW("Factory", Factory::sInstance)
    RegisterTypes();

    UnregisterTypes();
    TRACE_DELETE("Factory", Factory::sInstance)
    delete Factory::sInstance;
    Factory::sInstance = nullptr;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    unregisterTypes
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Jolt_unregisterTypes
  (JNIEnv *, jclass) {
    if (!gTypesAreRegistered) {
        std::cout << "Tried to unregister types before Jolt.registerTypes()!"
                << std::endl;
        return;
    }
    gTypesAreRegistered = false;
    UnregisterTypes();
}

#define STRING(arg) STRINGIFY(arg)
#define STRINGIFY(arg) #arg
/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    versionString
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_github_stephengold_joltjni_Jolt_versionString
  (JNIEnv *pEnv, jclass) {
    const jstring result = pEnv->NewStringUTF(STRING(JOLT_JNI_VERSION_STRING));
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    estimateCollisionResponse
 * Signature: (JJJJFFFI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Jolt_estimateCollisionResponse
  (JNIEnv *, jclass, jlong body1Va, jlong body2Va, jlong manifoldVa,
  jlong resultVa, jfloat combinedFriction, jfloat combinedRestitution,
  jfloat minVelocity, jint numIterations) {
    const Body * const pBody1 = reinterpret_cast<Body *> (body1Va);
    const Body * const pBody2 = reinterpret_cast<Body *> (body2Va);
    const ContactManifold * const pManifold
            = reinterpret_cast<ContactManifold *> (manifoldVa);
    CollisionEstimationResult * const pResult
            = reinterpret_cast<CollisionEstimationResult *> (resultVa);
    EstimateCollisionResponse(*pBody1, *pBody2, *pManifold, *pResult,
            combinedFriction, combinedRestitution, minVelocity, numIterations);
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
 * Method:    rayAaBox
 * Signature: (FFFJFFFFFF)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Jolt_rayAaBox
  (JNIEnv *, jclass, jfloat originX, jfloat originY, jfloat originZ,
  jlong invDirVa, jfloat minX, jfloat minY, jfloat minZ, jfloat maxX,
  jfloat maxY, jfloat maxZ) {
    const Vec3 origin(originZ, originY, originZ);
    const RayInvDirection * const pInvDir
            = reinterpret_cast<RayInvDirection *> (invDirVa);
    const Vec3 min(minZ, minY, minZ);
    const Vec3 max(maxZ, maxY, maxZ);
    const float result = RayAABox(origin, *pInvDir, min, max);
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

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    rayCapsule
 * Signature: (FFFFFFFF)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Jolt_rayCapsule
  (JNIEnv *, jclass, jfloat originX, jfloat originY, jfloat originZ,
  jfloat directionX, jfloat directionY, jfloat directionZ,
  jfloat capsuleHalfHeight, jfloat capsuleRadius) {
    const Vec3 origin(originX, originY, originZ);
    const Vec3 direction(directionX, directionY, directionZ);
    const float result
            = RayCapsule(origin, direction, capsuleHalfHeight, capsuleRadius);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    rayFiniteCylinder
 * Signature: (FFFFFFFF)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Jolt_rayFiniteCylinder
  (JNIEnv *, jclass, jfloat originX, jfloat originY, jfloat originZ,
  jfloat directionX, jfloat directionY, jfloat directionZ,
  jfloat cylinderHalfHeight, jfloat cylinderRadius) {
    const Vec3 origin(originX, originY, originZ);
    const Vec3 direction(directionX, directionY, directionZ);
    const float result = RayCylinder(
            origin, direction, cylinderHalfHeight, cylinderRadius);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    rayInfiniteCylinder
 * Signature: (FFFFFFF)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Jolt_rayInfiniteCylinder
  (JNIEnv *, jclass, jfloat originX, jfloat originY, jfloat originZ,
  jfloat directionX, jfloat directionY, jfloat directionZ,
  jfloat cylinderRadius) {
    const Vec3 origin(originX, originY, originZ);
    const Vec3 direction(directionX, directionY, directionZ);
    const float result = RayCylinder(origin, direction, cylinderRadius);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    raySphere
 * Signature: (FFFFFFFFFF)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Jolt_raySphere
  (JNIEnv *, jclass, jfloat originX, jfloat originY, jfloat originZ,
  jfloat directionX, jfloat directionY, jfloat directionZ, jfloat centerX,
  jfloat centerY, jfloat centerZ, jfloat sphereRadius) {
    const Vec3 origin(originX, originY, originZ);
    const Vec3 direction(directionX, directionY, directionZ);
    const Vec3 center(centerX, centerY, centerZ);
    const float result = RaySphere(origin, direction, center, sphereRadius);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Jolt
 * Method:    rayTriangle
 * Signature: (FFFFFFFFFFFFFFF)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Jolt_rayTriangle
  (JNIEnv *, jclass, jfloat originX, jfloat originY, jfloat originZ,
  jfloat directionX, jfloat directionY, jfloat directionZ,
  jfloat v0x, jfloat v0y, jfloat v0z, jfloat v1x, jfloat v1y, jfloat v1z,
  jfloat v2x, jfloat v2y, jfloat v2z) {
    const Vec3 origin(originX, originY, originZ);
    const Vec3 direction(directionX, directionY, directionZ);
    const Vec3 v0(v0x, v0y, v0z);
    const Vec3 v1(v1x, v1y, v1z);
    const Vec3 v2(v2x, v2y, v2z);
    const float result = RayTriangle(origin, direction, v0, v1, v2);
    return result;
}
