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
#include "Jolt/Physics/Collision/ObjectLayerPairFilterTable.h"
#include "auto/com_github_stephengold_joltjni_ObjectLayerPairFilterTable.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_ObjectLayerPairFilterTable
 * Method:    create
 * Signature: (I)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ObjectLayerPairFilterTable_create
  (JNIEnv *, jclass, jint numObjectLayers) {
    ObjectLayerPairFilterTable * const pFilter
            = new ObjectLayerPairFilterTable(numObjectLayers);
    TRACE_NEW("ObjectLayerPairFilterTable", pFilter)
    return reinterpret_cast<jlong> (pFilter);
}

/*
 * Class:     com_github_stephengold_joltjni_ObjectLayerPairFilterTable
 * Method:    disableCollision
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ObjectLayerPairFilterTable_disableCollision
  (JNIEnv *, jclass, jlong filterVa, jint layer1, jint layer2) {
    ObjectLayerPairFilterTable * const pFilter
            = reinterpret_cast<ObjectLayerPairFilterTable *> (filterVa);
    pFilter->DisableCollision(layer1, layer2);
}

/*
 * Class:     com_github_stephengold_joltjni_ObjectLayerPairFilterTable
 * Method:    enableCollision
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ObjectLayerPairFilterTable_enableCollision
  (JNIEnv *, jclass, jlong filterVa, jint layer1, jint layer2) {
    ObjectLayerPairFilterTable * const pFilter
            = reinterpret_cast<ObjectLayerPairFilterTable *> (filterVa);
    pFilter->EnableCollision(layer1, layer2);
}

/*
 * Class:     com_github_stephengold_joltjni_ObjectLayerPairFilterTable
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ObjectLayerPairFilterTable_free
  (JNIEnv *, jclass, jlong filterVa) {
    ObjectLayerPairFilterTable * const pFilter
            = reinterpret_cast<ObjectLayerPairFilterTable *> (filterVa);
    TRACE_DELETE("ObjectLayerPairFilterTable", pFilter)
    delete pFilter;
}