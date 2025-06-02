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
#include "Jolt/Skeleton/Skeleton.h"
#include "auto/com_github_stephengold_joltjni_Joint.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_Joint
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Joint_createDefault
  BODYOF_CREATE_DEFAULT(Skeleton::Joint)

/*
 * Class:     com_github_stephengold_joltjni_Joint
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Joint_free
  (JNIEnv *, jclass, jlong jointVa) {
    Skeleton::Joint * const pJoint
            = reinterpret_cast<Skeleton::Joint *> (jointVa);
    TRACE_DELETE("Joint", pJoint)
    delete pJoint;
}

/*
 * Class:     com_github_stephengold_joltjni_Joint
 * Method:    getName
 * Signature: (J)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_github_stephengold_joltjni_Joint_getName
  (JNIEnv *pEnv, jclass, jlong jointVa) {
    const Skeleton::Joint * const pJoint
            = reinterpret_cast<Skeleton::Joint *> (jointVa);
    const String name = pJoint->mName;
    const char * const pName = name.c_str();
    const jstring result = pEnv->NewStringUTF(pName);
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Joint
 * Method:    getParentJointIndex
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Joint_getParentJointIndex
  (JNIEnv *, jclass, jlong jointVa) {
    const Skeleton::Joint * const pJoint
            = reinterpret_cast<Skeleton::Joint *> (jointVa);
    const int result = pJoint->mParentJointIndex;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Joint
 * Method:    getParentName
 * Signature: (J)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_github_stephengold_joltjni_Joint_getParentName
  (JNIEnv *pEnv, jclass, jlong jointVa) {
    const Skeleton::Joint * const pJoint
            = reinterpret_cast<Skeleton::Joint *> (jointVa);
    const String name = pJoint->mParentName;
    const char * const pName = name.c_str();
    const jstring result = pEnv->NewStringUTF(pName);
    return result;
}