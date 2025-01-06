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
#include "Jolt/Physics/Vehicle/TrackedVehicleController.h"
#include "auto/com_github_stephengold_joltjni_TrackedVehicleController.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_TrackedVehicleController
 * Method:    setDriverInput
 * Signature: (JFFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TrackedVehicleController_setDriverInput
  (JNIEnv *, jclass, jlong controllerVa, jfloat forward, jfloat leftRatio,
  jfloat rightRatio, jfloat brake) {
    TrackedVehicleController * const pController
            = reinterpret_cast<TrackedVehicleController *> (controllerVa);
    pController->SetDriverInput(forward, leftRatio, rightRatio, brake);
}

/*
 * Class:     com_github_stephengold_joltjni_TrackedVehicleController
 * Method:    setRpmMeter
 * Signature: (JFFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_TrackedVehicleController_setRpmMeter
  (JNIEnv *, jclass, jlong controllerVa, jfloat locX, jfloat locY, jfloat locZ,
  jfloat size) {
#ifdef JPH_DEBUG_RENDERER
    TrackedVehicleController * const pController
            = reinterpret_cast<TrackedVehicleController *> (controllerVa);
    const Vec3 location(locX, locY, locZ);
    pController->SetRPMMeter(location, size);
#endif
}