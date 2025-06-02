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
#include "Jolt/Physics/SoftBody/SoftBodySharedSettings.h"
#include "auto/com_github_stephengold_joltjni_InvBind.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_InvBind
 * Method:    create
 * Signature: (IJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_InvBind_create
  (JNIEnv *, jclass, jint jointIndex, jlong invBindVa) {
    const Mat44 * const pInvBind = reinterpret_cast<Mat44 *> (invBindVa);
    SoftBodySharedSettings::InvBind * const pResult
            = new SoftBodySharedSettings::InvBind(jointIndex, *pInvBind);
    TRACE_NEW("SoftBodySharedSettings::InvBind", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_InvBind
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_InvBind_createDefault
  BODYOF_CREATE_DEFAULT(SoftBodySharedSettings::InvBind)

/*
 * Class:     com_github_stephengold_joltjni_InvBind
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_InvBind_free
  (JNIEnv *, jclass, jlong invBindVa) {
    SoftBodySharedSettings::InvBind * const pInvBind
            = reinterpret_cast<SoftBodySharedSettings::InvBind *> (invBindVa);
    TRACE_DELETE("SoftBodySharedSettings::InvBind", pInvBind)
    delete pInvBind;
}

/*
 * Class:     com_github_stephengold_joltjni_InvBind
 * Method:    getInvBind
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_InvBind_getInvBind
  (JNIEnv *, jclass, jlong invBindVa) {
    const SoftBodySharedSettings::InvBind * const pInvBind
            = reinterpret_cast<SoftBodySharedSettings::InvBind *> (invBindVa);
    Mat44 * const pResult = new Mat44(pInvBind->mInvBind);
    TRACE_NEW("Mat44", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_InvBind
 * Method:    getJointIndex
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_InvBind_getJointIndex
  (JNIEnv *, jclass, jlong invBindVa) {
    const SoftBodySharedSettings::InvBind * const pInvBind
            = reinterpret_cast<SoftBodySharedSettings::InvBind *> (invBindVa);
    const uint32 result = pInvBind->mJointIndex;
    return result;
}