/*
Copyright (c) 2025-2026 Stephen Gold

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
#ifdef JPH_DEBUG_RENDERER
#include "Jolt/Renderer/DebugRendererSimple.h"
#endif
#include "auto/com_github_stephengold_joltjni_CustomDebugRendererSimple.h"
#include "glue/glue.h"

using namespace JPH;

#ifdef JPH_DEBUG_RENDERER
class CustomDebugRendererSimple : public DebugRendererSimple {
    JavaVM *mpVM;
    jmethodID mDrawLineId, mDrawTextId, mDrawTriangleId;
    jobject mJavaObject;

public:
    CustomDebugRendererSimple(JNIEnv *pEnv, jobject javaObject) {
        pEnv->GetJavaVM(&mpVM);

        mJavaObject = pEnv->NewGlobalRef(javaObject);
        EXCEPTION_CHECK(pEnv)

        const jclass clss = pEnv->FindClass(
                "com/github/stephengold/joltjni/CustomDebugRendererSimple");
        EXCEPTION_CHECK(pEnv)

        mDrawLineId = pEnv->GetMethodID(clss, "drawLine", "(DDDDDDI)V");
        EXCEPTION_CHECK(pEnv)

        mDrawTextId = pEnv->GetMethodID(
                clss, "drawText3d", "(DDDLjava/lang/String;IF)V");
        EXCEPTION_CHECK(pEnv)

        mDrawTriangleId
                = pEnv->GetMethodID(clss, "drawTriangle", "(DDDDDDDDDII)V");
        EXCEPTION_CHECK(pEnv)
    }

    void DrawLine(RVec3Arg inFrom, RVec3Arg inTo, ColorArg inColor) override {
        JNIEnv *pAttachEnv;
        jint retCode = ATTACH_CURRENT_THREAD(mpVM, &pAttachEnv);
        JPH_ASSERT(retCode == JNI_OK);

        const double x1 = inFrom.GetX();
        const double y1 = inFrom.GetY();
        const double z1 = inFrom.GetZ();
        const double x2 = inTo.GetX();
        const double y2 = inTo.GetY();
        const double z2 = inTo.GetZ();
        const Color color(inColor);
        pAttachEnv->CallVoidMethod(
                mJavaObject, mDrawLineId, x1, y1, z1, x2, y2, z2, color);
        EXCEPTION_CHECK(pAttachEnv)
        mpVM->DetachCurrentThread();
    }

    void DrawText3D(RVec3Arg inPosition, const string_view &inString,
      ColorArg inColor, float inHeight) override {
        JNIEnv *pAttachEnv;
        jint retCode = ATTACH_CURRENT_THREAD(mpVM, &pAttachEnv);
        JPH_ASSERT(retCode == JNI_OK);

        const double xx = inPosition.GetX();
        const double yy = inPosition.GetY();
        const double zz = inPosition.GetZ();

        const jsize len = inString.size();
        jchar * const pTmpChars = new jchar[len];
        for (jsize i = 0; i < len; ++i) {
            pTmpChars[i] = inString[i];
        }
        jstring message = pAttachEnv->NewString(pTmpChars, len);
        delete[] pTmpChars;

        const Color color(inColor);
        pAttachEnv->CallVoidMethod(
                mJavaObject, mDrawTextId, xx, yy, zz, message, color, inHeight);
        EXCEPTION_CHECK(pAttachEnv)
        mpVM->DetachCurrentThread();
    }

    void DrawTriangle(RVec3Arg inV1, RVec3Arg inV2, RVec3Arg inV3,
      ColorArg inColor, ECastShadow inCastShadow) override {
        JNIEnv *pAttachEnv;
        jint retCode = ATTACH_CURRENT_THREAD(mpVM, &pAttachEnv);
        JPH_ASSERT(retCode == JNI_OK);

        const double x1 = inV1.GetX();
        const double y1 = inV1.GetY();
        const double z1 = inV1.GetZ();
        const double x2 = inV2.GetX();
        const double y2 = inV2.GetY();
        const double z2 = inV2.GetZ();
        const double x3 = inV3.GetX();
        const double y3 = inV3.GetY();
        const double z3 = inV3.GetZ();
        const Color color(inColor);
        const ECastShadow shadow = (ECastShadow) inCastShadow;
        pAttachEnv->CallVoidMethod(mJavaObject, mDrawTriangleId,
                x1, y1, z1, x2, y2, z2, x3, y3, z3, color, shadow);
        EXCEPTION_CHECK(pAttachEnv)
        mpVM->DetachCurrentThread();
    }

    ~CustomDebugRendererSimple() {
        JNIEnv *pAttachEnv;
        jint retCode = ATTACH_CURRENT_THREAD(mpVM, &pAttachEnv);
        JPH_ASSERT(retCode == JNI_OK);

        pAttachEnv->DeleteGlobalRef(mJavaObject);
        EXCEPTION_CHECK(pAttachEnv)
        mpVM->DetachCurrentThread();
    }
};
#endif

/*
 * Class:     com_github_stephengold_joltjni_CustomDebugRendererSimple
 * Method:    create
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CustomDebugRendererSimple_create
  (JNIEnv *pEnv, jobject javaObject) {
#ifdef JPH_DEBUG_RENDERER
    CustomDebugRendererSimple * const pResult
            = new CustomDebugRendererSimple(pEnv, javaObject);
    TRACE_NEW("CustomDebugRendererSimple", pResult)
    return reinterpret_cast<jlong> (pResult);
#else
    return 0;
#endif
}