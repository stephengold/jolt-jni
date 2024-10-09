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
#include "Jolt/Jolt.h"
#include "Jolt/Physics/SoftBody/SoftBodySharedSettings.h"
#include "auto/com_github_stephengold_joltjni_Volume.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_Volume
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Volume_createDefault
  (JNIEnv *, jclass) {
    SoftBodySharedSettings::Volume * const pResult
            = new SoftBodySharedSettings::Volume();
    TRACE_NEW("SoftBodySharedSettings::Volume", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_Volume
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Volume_free
  (JNIEnv *, jclass, jlong volumeVa) {
    SoftBodySharedSettings::Volume * const pVolume
            = reinterpret_cast<SoftBodySharedSettings::Volume *> (volumeVa);
    TRACE_DELETE("SoftBodySharedSettings::Volume", pVolume)
    delete pVolume;
}

/*
 * Class:     com_github_stephengold_joltjni_Volume
 * Method:    getCompliance
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Volume_getCompliance
  (JNIEnv *, jclass, jlong volumeVa) {
    const SoftBodySharedSettings::Volume * const pVolume
            = reinterpret_cast<SoftBodySharedSettings::Volume *> (volumeVa);
    const float result = pVolume->mCompliance;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Volume
 * Method:    getSixRestVolume
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_github_stephengold_joltjni_Volume_getSixRestVolume
  (JNIEnv *, jclass, jlong volumeVa) {
    const SoftBodySharedSettings::Volume * const pVolume
            = reinterpret_cast<SoftBodySharedSettings::Volume *> (volumeVa);
    const float result = pVolume->mSixRestVolume;
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Volume
 * Method:    getVertex
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Volume_getVertex
  (JNIEnv *, jclass, jlong volumeVa, jint indexInVolume) {
    const SoftBodySharedSettings::Volume * const pVolume
            = reinterpret_cast<SoftBodySharedSettings::Volume *> (volumeVa);
    const uint32 result = pVolume->mVertex[indexInVolume];
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Volume
 * Method:    setCompliance
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Volume_setCompliance
  (JNIEnv *, jclass, jlong volumeVa, jfloat compliance) {
    SoftBodySharedSettings::Volume * const pVolume
            = reinterpret_cast<SoftBodySharedSettings::Volume *> (volumeVa);
    pVolume->mCompliance = compliance;
}

/*
 * Class:     com_github_stephengold_joltjni_Volume
 * Method:    setSixRestVolume
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Volume_setSixRestVolume
  (JNIEnv *, jclass, jlong volumeVa, jfloat restValue) {
    SoftBodySharedSettings::Volume * const pVolume
            = reinterpret_cast<SoftBodySharedSettings::Volume *> (volumeVa);
    pVolume->mSixRestVolume = restValue;
}

/*
 * Class:     com_github_stephengold_joltjni_Volume
 * Method:    setVertex
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Volume_setVertex
  (JNIEnv *, jclass, jlong volumeVa, jint indexInVolume, jint indexInMesh) {
    SoftBodySharedSettings::Volume * const pVolume
            = reinterpret_cast<SoftBodySharedSettings::Volume *> (volumeVa);
    pVolume->mVertex[indexInVolume] = indexInMesh;
}