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
#include "Jolt/Core/StreamUtils.h"
#include "Jolt/Physics/SoftBody/SoftBodySharedSettings.h"
#include "auto/com_github_stephengold_joltjni_streamutils_IdToSharedSettingsMap.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_streamutils_IdToSharedSettingsMap
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_streamutils_IdToSharedSettingsMap_createDefault
  (JNIEnv *, jclass) {
    StreamUtils::IDToObjectMap<SoftBodySharedSettings> * const pResult
            = new StreamUtils::IDToObjectMap<SoftBodySharedSettings>();
    TRACE_NEW("StreamUtils::IDToObjectMap<SoftBodySharedSettings>", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_streamutils_IdToSharedSettingsMap
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_streamutils_IdToSharedSettingsMap_free
   (JNIEnv *, jclass, jlong mapVa) {
    StreamUtils::IDToObjectMap<SoftBodySharedSettings> * const pMap
            = reinterpret_cast<StreamUtils::IDToObjectMap<SoftBodySharedSettings> *> (mapVa);
    TRACE_DELETE("StreamUtils::IDToObjectMap<SoftBodySharedSettings>", pMap)
    delete pMap;
}