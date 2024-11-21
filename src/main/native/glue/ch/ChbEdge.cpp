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
#include "Jolt/Geometry/ConvexHullBuilder.h"
#include "auto/com_github_stephengold_joltjni_ChbEdge.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_ChbEdge
 * Method:    getNextEdge
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ChbEdge_getNextEdge
  (JNIEnv *, jclass, jlong edgeVa) {
    const ConvexHullBuilder::Edge * const pEdge
            = reinterpret_cast<ConvexHullBuilder::Edge *> (edgeVa);
    const ConvexHullBuilder::Edge * const pResult = pEdge->mNextEdge;
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_ChbEdge
 * Method:    getStartIdx
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_ChbEdge_getStartIdx
  (JNIEnv *, jclass, jlong edgeVa) {
    const ConvexHullBuilder::Edge * const pEdge
            = reinterpret_cast<ConvexHullBuilder::Edge *> (edgeVa);
    const int result = pEdge->mStartIdx;
    return result;
}