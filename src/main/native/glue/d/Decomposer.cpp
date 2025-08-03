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

// Include the V-HACD v4 implementation, not just its API:
#define ENABLE_VHACD_IMPLEMENTATION 1
#include "VHACD.h"

#include "auto/com_github_stephengold_joltjni_vhacd_Decomposer.h"
#include "glue/glue.h"

using namespace VHACD;

class Decomposer : public IVHACD::IUserCallback, public IVHACD::IUserLogger {
public:
    /*
     * constructor:
     */
    Decomposer(JNIEnv *pEnv, jobject javaObject) {
        pEnv->GetJavaVM(&mpVM);
        EXCEPTION_CHECK(pEnv)

        mJavaObject = pEnv->NewGlobalRef(javaObject);
        EXCEPTION_CHECK(pEnv)

        const jclass clss = pEnv->FindClass(
                "com/github/stephengold/joltjni/vhacd/Decomposer");
        EXCEPTION_CHECK(pEnv)

        mAddMethodId = pEnv->GetMethodID(clss, "addHull", "(J)V");
        EXCEPTION_CHECK(pEnv)

        mUpdateMethodId = pEnv->GetMethodID(
                clss, "update", "(DDDLjava/lang/String;Ljava/lang/String;)V");
        EXCEPTION_CHECK(pEnv)
    }

    void Decompose(JNIEnv *pEnv,
      const float * const pFloats, const uint32_t numPoints,
      const uint32_t * const pIndices, const uint32_t numTriangles,
      const IVHACD::Parameters * const pParams) {

        IVHACD * const pIvhacd = CreateVHACD();
        const bool success = pIvhacd->Compute(
                pFloats, numPoints, pIndices, numTriangles, *pParams);
        if (success) {
            const uint32_t numHulls = pIvhacd->GetNConvexHulls();
            for (uint32_t i = 0; i < numHulls; ++i) {
                IVHACD::ConvexHull * const pHull = new IVHACD::ConvexHull();
                TRACE_NEW("IVHACD::ConvexHull", pHull)
                pIvhacd->GetConvexHull(i, *pHull);
                const jlong hullVa = reinterpret_cast<jlong> (pHull);

                pEnv->CallVoidMethod(mJavaObject, mAddMethodId, hullVa);
                EXCEPTION_CHECK(pEnv)
            }
        }
        pIvhacd->Clean();
        pIvhacd->Release();
    }

    void Log(const char* const msg) override {
#ifdef JPH_DEBUG
        if (mEnableDebugOutput) {
            std::cout << msg << std::endl;
        }
#endif
    }

    void SetEnableDebugOutput(bool setting) {
        mEnableDebugOutput = setting;
    }

    void Update(const double overallPercent, const double stagePercent,
            const char* const stageName, const char* operationName) override {
        JNIEnv *pAttachEnv;
        jint retCode = ATTACH_CURRENT_THREAD(mpVM, &pAttachEnv);
        JPH_ASSERT(retCode == JNI_OK);

        jfloat arg1 = overallPercent;
        jfloat arg2 = stagePercent;
        jfloat arg3 = 100.0;

        jstring arg4 = pAttachEnv->NewStringUTF(stageName);
        EXCEPTION_CHECK(pAttachEnv)

        jstring arg5 = pAttachEnv->NewStringUTF(operationName);
        EXCEPTION_CHECK(pAttachEnv)

        pAttachEnv->CallVoidMethod(mJavaObject, mUpdateMethodId,
                arg1, arg2, arg3, arg4, arg5);
        EXCEPTION_CHECK(pAttachEnv)
    }

    virtual ~Decomposer() {}

private:
    bool mEnableDebugOutput;
    JavaVM *mpVM;
    jmethodID mAddMethodId;
    jmethodID mUpdateMethodId;
    jobject mJavaObject;
};

/*
 * Class:     com_github_stephengold_joltjni_vhacd_Decomposer
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_vhacd_Decomposer_createDefault
  (JNIEnv *pEnv, jobject javaObject) {
    Decomposer * const pDecomposer = new Decomposer(pEnv, javaObject);
    TRACE_NEW("Decomposer", pDecomposer)
    return reinterpret_cast<jlong> (pDecomposer);
}

/*
 * Class:     com_github_stephengold_joltjni_vhacd_Decomposer
 * Method:    decomposeAa
 * Signature: (J[F[IJZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_vhacd_Decomposer_decomposeAa
  (JNIEnv *pEnv, jclass, jlong decomposerVa, jfloatArray locations,
  jintArray indices, jlong paramsVa, jboolean debugOutput) {
    Decomposer * const pDecomposer
            = reinterpret_cast<Decomposer *> (decomposerVa);
    pDecomposer->SetEnableDebugOutput(debugOutput);

    jboolean isCopy;
    jfloat * const pPoints = pEnv->GetFloatArrayElements(locations, &isCopy);
    JPH_ASSERT(!pEnv->ExceptionCheck());

    const jsize numFloats = pEnv->GetArrayLength(locations);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const int32_t numPoints = numFloats / 3;

    jint * const pIndices = pEnv->GetIntArrayElements(indices, &isCopy);
    JPH_ASSERT(!pEnv->ExceptionCheck());

    const jsize numIndices = pEnv->GetArrayLength(indices);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const int32_t numTriangles = numIndices / 3;

    // on some platforms, jint != uint32_t
    uint32_t * const pCopyIndices = new uint32_t[numIndices];
    for (jsize i = 0; i < numIndices; ++i) {
        pCopyIndices[i] = (uint32_t) pIndices[i];
    }
    pEnv->ReleaseIntArrayElements(indices, pIndices, JNI_ABORT);
    JPH_ASSERT(!pEnv->ExceptionCheck());

    const IVHACD::Parameters * const pParams
            = reinterpret_cast<IVHACD::Parameters *> (paramsVa);
    IVHACD::Parameters * const pCopyParameters
            = new IVHACD::Parameters(*pParams);
    pCopyParameters->m_callback = pDecomposer;
    pCopyParameters->m_logger = pDecomposer;

    pDecomposer->Decompose(pEnv, pPoints, numPoints, pCopyIndices,
            numTriangles, pCopyParameters);

    delete pCopyParameters;
    delete[] pCopyIndices;

    pEnv->ReleaseFloatArrayElements(locations, pPoints, JNI_ABORT);
    JPH_ASSERT(!pEnv->ExceptionCheck());
}

/*
 * Class:     com_github_stephengold_joltjni_vhacd_Decomposer
 * Method:    decomposeBb
 * Signature: (JLjava/nio/FloatBuffer;Ljava/nio/IntBuffer;JZ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_vhacd_Decomposer_decomposeBb
  (JNIEnv *pEnv, jclass, jlong decomposerVa, jobject locations, jobject indices,
  jlong paramsVa, jboolean debugOutput) {
    Decomposer * const pDecomposer
            = reinterpret_cast<Decomposer *> (decomposerVa);
    pDecomposer->SetEnableDebugOutput(debugOutput);

    const DIRECT_FLOAT_BUFFER(pEnv, locations, pPoints, numFloats);
    const int32_t numPoints = numFloats / 3;

    const jint * const pIndices
            = (jint *) pEnv->GetDirectBufferAddress(indices);
    JPH_ASSERT(!pEnv->ExceptionCheck());

    const jlong numIndices = pEnv->GetDirectBufferCapacity(indices);
    JPH_ASSERT(!pEnv->ExceptionCheck());
    const int32_t numTriangles = numIndices / 3;

    // on some platforms, jint != uint32_t
    uint32_t * const pCopyIndices = new uint32_t[numIndices];
    for (jsize i = 0; i < numIndices; ++i) {
        pCopyIndices[i] = (uint32_t) pIndices[i];
    }

    const IVHACD::Parameters * const pParams
            = reinterpret_cast<IVHACD::Parameters *> (paramsVa);
    IVHACD::Parameters * const pCopyParameters
            = new IVHACD::Parameters(*pParams);
    pCopyParameters->m_callback = pDecomposer;
    pCopyParameters->m_logger = pDecomposer;

    pDecomposer->Decompose(pEnv, pPoints, numPoints, pCopyIndices,
            numTriangles, pCopyParameters);

    delete pCopyParameters;
    delete[] pCopyIndices;
}

/*
 * Class:     com_github_stephengold_joltjni_vhacd_Decomposer
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_vhacd_Decomposer_free
  BODYOF_FREE(Decomposer)