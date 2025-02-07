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
#include "Jolt/Physics/SoftBody/SoftBodyMotionProperties.h"
#include "auto/com_github_stephengold_joltjni_SoftBodyMotionProperties.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyMotionProperties
 * Method:    countFaces
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SoftBodyMotionProperties_countFaces
  (JNIEnv *, jclass, jlong propertiesVa) {
    const SoftBodyMotionProperties * const pProperties
            = reinterpret_cast<SoftBodyMotionProperties *> (propertiesVa);
    const Array<SoftBodySharedSettings::Face>& faces = pProperties->GetFaces();
    const Array<SoftBodySharedSettings::Face>::size_type result = faces.size();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyMotionProperties
 * Method:    countVertices
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SoftBodyMotionProperties_countVertices
  (JNIEnv *, jclass, jlong propertiesVa) {
    const SoftBodyMotionProperties * const pProperties
            = reinterpret_cast<SoftBodyMotionProperties *> (propertiesVa);
    const Array<SoftBodyVertex>& vertices = pProperties->GetVertices();
    const Array<SoftBodyVertex>::size_type result = vertices.size();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyMotionProperties
 * Method:    customUpdate
 * Signature: (JFJJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodyMotionProperties_customUpdate
  (JNIEnv *, jclass, jlong propertiesVa, jfloat deltaTime, jlong bodyVa, jlong systemVa) {
    SoftBodyMotionProperties * const pProperties
            = reinterpret_cast<SoftBodyMotionProperties *> (propertiesVa);
    Body * const pBody = reinterpret_cast<Body *> (bodyVa);
    PhysicsSystem * const pSystem = reinterpret_cast<PhysicsSystem *> (systemVa);
    pProperties->CustomUpdate(deltaTime, *pBody, *pSystem);
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyMotionProperties
 * Method:    getFace
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SoftBodyMotionProperties_getFace
  (JNIEnv *, jclass, jlong propertiesVa, jint index) {
    SoftBodyMotionProperties * const pProperties
            = reinterpret_cast<SoftBodyMotionProperties *> (propertiesVa);
    const SoftBodySharedSettings::Face& result = pProperties->GetFace(index);
    return reinterpret_cast<jlong> (&result);
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyMotionProperties
 * Method:    getNumIterations
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_SoftBodyMotionProperties_getNumIterations
  (JNIEnv *, jclass, jlong propertiesVa) {
    const SoftBodyMotionProperties * const pProperties
            = reinterpret_cast<SoftBodyMotionProperties *> (propertiesVa);
    const uint32 result = pProperties->GetNumIterations();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyMotionProperties
 * Method:    getSettings
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SoftBodyMotionProperties_getSettings
  (JNIEnv *, jclass, jlong propertiesVa) {
    SoftBodyMotionProperties * const pProperties
            = reinterpret_cast<SoftBodyMotionProperties *> (propertiesVa);
    const SoftBodySharedSettings * const pResult = pProperties->GetSettings();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyMotionProperties
 * Method:    getVertex
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SoftBodyMotionProperties_getVertex
  (JNIEnv *, jclass, jlong propertiesVa, jint index) {
    SoftBodyMotionProperties * const pProperties
            = reinterpret_cast<SoftBodyMotionProperties *> (propertiesVa);
    const SoftBodyVertex& result = pProperties->GetVertex(index);
    return reinterpret_cast<jlong> (&result);
}

/*
 * Class:     com_github_stephengold_joltjni_SoftBodyMotionProperties
 * Method:    setNumIterations
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SoftBodyMotionProperties_setNumIterations
  (JNIEnv *, jclass, jlong propertiesVa, jint numIterations) {
    SoftBodyMotionProperties * const pProperties
            = reinterpret_cast<SoftBodyMotionProperties *> (propertiesVa);
    pProperties->SetNumIterations(numIterations);
}