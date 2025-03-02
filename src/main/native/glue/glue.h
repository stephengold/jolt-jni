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
#ifdef JPH_DEBUG

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
#define TRACE_NEW(className, pointer)
#define TRACE_DELETE(className, pointer)
#endif

#define IMPLEMENT_REF(className, copyName, createName, freeName, getPtrName) \
  JNIEXPORT jlong JNICALL copyName(JNIEnv *, jclass, jlong refVa) { \
    Ref<className> * const pRef = reinterpret_cast<Ref<className> *> (refVa); \
    Ref<className> * const pResult = new Ref<className>(*pRef); \
    TRACE_NEW("Ref<" #className ">", pResult) \
    return reinterpret_cast<jlong> (pResult); \
  } \
  JNIEXPORT jlong JNICALL createName(JNIEnv *, jclass) { \
    Ref<className> * const pResult = new Ref<className>(); \
    TRACE_NEW("Ref<" #className ">", pResult) \
    return reinterpret_cast<jlong> (pResult); \
  } \
  JNIEXPORT void JNICALL freeName(JNIEnv *, jclass, jlong refVa) { \
    Ref<className> * const pRef = reinterpret_cast<Ref<className> *> (refVa); \
    TRACE_DELETE("Ref<" #className ">", pRef) \
    delete pRef; \
  } \
  JNIEXPORT jlong JNICALL getPtrName(JNIEnv *, jclass, jlong refVa) { \
    Ref<className> * const pRef = reinterpret_cast<Ref<className> *> (refVa); \
    className * const pResult = pRef->GetPtr(); \
    return reinterpret_cast<jlong> (pResult); \
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