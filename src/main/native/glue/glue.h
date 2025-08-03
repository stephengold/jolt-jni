#ifndef _Included_glue
#define _Included_glue
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

/*
 * Pre-processor macros for tracing heap allocations by glue code:
 */
#ifdef JPH_DEBUG

#define EXCEPTION_CHECK(pEnv) \
    if ((pEnv)->ExceptionCheck()) { \
            (pEnv)->ExceptionDescribe(); \
            JPH_ASSERT(false); \
    }

extern bool gTraceAllocations;

#define TRACE_NEW(className, pointer) \
    if (gTraceAllocations) { \
        JPH::Trace("%llx +%s", reinterpret_cast<unsigned long long> (pointer), className); \
    }
#define TRACE_DELETE(className, pointer) \
    if (gTraceAllocations) { \
        JPH::Trace("%llx -%s", reinterpret_cast<unsigned long long> (pointer), className); \
    }

#else
#define EXCEPTION_CHECK(pEnv)
#define TRACE_NEW(className, pointer)
#define TRACE_DELETE(className, pointer)
#endif
/*
 * Pre-processor macro to generate code to access a direct FloatBuffer:
 */
#define DIRECT_FLOAT_BUFFER(pEnv, floatBuffer, pFloats, capacityFloats) \
  jfloat * const pFloats = (jfloat *) (pEnv)->GetDirectBufferAddress(floatBuffer); \
  JPH_ASSERT(!(pEnv)->ExceptionCheck()); \
  const jlong capacityFloats = (pEnv)->GetDirectBufferCapacity(floatBuffer); \
  JPH_ASSERT(!(pEnv)->ExceptionCheck())
/*
 * Pre-processor macro to generate the body of a static createCopy() method
 * to implement a copy constructor:
 */
#define BODYOF_CREATE_COPY(className) \
  (JNIEnv *, jclass, jlong originalVa) { \
    const className * const pOriginal = reinterpret_cast<className *> (originalVa); \
    className * const pResult = new className(*pOriginal); \
    TRACE_NEW(#className, pResult) \
    return reinterpret_cast<jlong> (pResult); \
}
/*
 * Pre-processor macro to generate the body of a static createDefault() method
 * to implement a no-arg constructor:
 */
#define BODYOF_CREATE_DEFAULT(className) \
  (JNIEnv *, jclass) { \
    className * const pResult = new className(); \
    TRACE_NEW(#className, pResult) \
    return reinterpret_cast<jlong> (pResult); \
}
/*
 * Pre-processor macro to generate the body of a static free() method
 * to implement a destructor:
 */
#define BODYOF_FREE(className) \
  (JNIEnv *, jclass, jlong va) { \
    className * const pObject = reinterpret_cast<className *> (va); \
    TRACE_DELETE(#className, pObject) \
    delete pObject; \
}
/*
 * Pre-processor macro to implement 5 methods associated with the
 * com.github.stephengold.template.Ref class:
 */
#define IMPLEMENT_REF(className, copyName, createName, freeName, getPtrName, toRefCName) \
  JNIEXPORT jlong JNICALL copyName BODYOF_CREATE_COPY(Ref<className>) \
  JNIEXPORT jlong JNICALL createName BODYOF_CREATE_DEFAULT(Ref<className>) \
  JNIEXPORT void JNICALL freeName BODYOF_FREE(Ref<className>) \
  JNIEXPORT jlong JNICALL getPtrName(JNIEnv *, jclass, jlong refVa) { \
    Ref<className> * const pRef = reinterpret_cast<Ref<className> *> (refVa); \
    className * const pResult = pRef->GetPtr(); \
    return reinterpret_cast<jlong> (pResult); \
  } \
  JNIEXPORT jlong JNICALL toRefCName(JNIEnv *, jclass, jlong refVa) { \
    Ref<className> * const pRef = reinterpret_cast<Ref<className> *> (refVa); \
    RefConst<className> * const pResult = new RefConst<className>(*pRef); \
    TRACE_NEW("RefConst<" #className ">", pResult) \
    return reinterpret_cast<jlong> (pResult); \
  }
/*
 * Pre-processor macro to implement 4 methods associated with the
 * com.github.stephengold.templace.Result class:
 */
#define IMPLEMENT_RESULT(className, freeName, getErrorName, hasErrorName, isValidName) \
  JNIEXPORT void JNICALL freeName(JNIEnv *, jclass, jlong resultVa) { \
    Result<className> * const pResult = reinterpret_cast<Result<className> *> (resultVa); \
    TRACE_DELETE("Result<" #className ">", pResult) \
    delete pResult; \
  } \
  JNIEXPORT jstring JNICALL getErrorName(JNIEnv *pEnv, jobject, jlong resultVa) { \
    Result<className> * const pResult = reinterpret_cast<Result<className> *> (resultVa); \
    const String& message = pResult->GetError(); \
    const char* const str = message.c_str(); \
    const jstring result = pEnv->NewStringUTF(str); \
    return result; \
  } \
  JNIEXPORT jboolean JNICALL hasErrorName(JNIEnv *, jobject, jlong resultVa) { \
    Result<className> * const pResult = reinterpret_cast<Result<className> *> (resultVa); \
    const bool result = pResult->HasError(); \
    return result; \
  } \
  JNIEXPORT jboolean JNICALL isValidName(JNIEnv *, jobject, jlong resultVa) { \
    Result<className> * const pResult = reinterpret_cast<Result<className> *> (resultVa); \
    const bool result = pResult->IsValid(); \
    return result; \
  }

#ifdef ANDROID
// doesn't match the Invocation API spec
#define ATTACH_CURRENT_THREAD(mpVM, ppAttachEnv) \
    (mpVM)->AttachCurrentThread(ppAttachEnv, NULL)

#else
#define ATTACH_CURRENT_THREAD(mpVM, ppAttachEnv) \
    (mpVM)->AttachCurrentThread((void **)(ppAttachEnv), NULL)

#endif

#endif