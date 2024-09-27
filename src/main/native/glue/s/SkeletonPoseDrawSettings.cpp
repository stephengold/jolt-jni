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
#include "Jolt/Skeleton/SkeletonPose.h"
#include "auto/com_github_stephengold_joltjni_SkeletonPoseDrawSettings.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_SkeletonPoseDrawSettings
 * Method:    createDrawSettings
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_SkeletonPoseDrawSettings_createDrawSettings
  (JNIEnv *, jclass) {
#ifdef JPH_DEBUG_RENDERER
    SkeletonPose::DrawSettings * const pResult
            = new SkeletonPose::DrawSettings();
    TRACE_NEW("SkeletonPose::DrawSettings", pResult)
    return reinterpret_cast<jlong> (pResult);
#else
    return 0;
#endif
}

/*
 * Class:     com_github_stephengold_joltjni_SkeletonPoseDrawSettings
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_SkeletonPoseDrawSettings_free
  (JNIEnv *, jclass, jlong settingsVa) {
#ifdef JPH_DEBUG_RENDERER
    SkeletonPose::DrawSettings * const pSettings
            = reinterpret_cast<SkeletonPose::DrawSettings *> (settingsVa);
    TRACE_DELETE("SkeletonPose::DrawSettings", pSettings)
    delete pSettings;
#endif
}