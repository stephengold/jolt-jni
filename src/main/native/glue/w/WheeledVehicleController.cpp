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
#include "Jolt/Physics/Vehicle/WheeledVehicleController.h"
#include "auto/com_github_stephengold_joltjni_WheeledVehicleController.h"
#include "auto/com_github_stephengold_joltjni_WheeledVehicleControllerRef.h"
#include "glue/glue.h"

using namespace JPH;

IMPLEMENT_REF(WheeledVehicleController,
  Java_com_github_stephengold_joltjni_WheeledVehicleControllerRef_copy,
  Java_com_github_stephengold_joltjni_WheeledVehicleControllerRef_createEmpty,
  Java_com_github_stephengold_joltjni_WheeledVehicleControllerRef_free,
  Java_com_github_stephengold_joltjni_WheeledVehicleControllerRef_getPtr)

/*
 * Class:     com_github_stephengold_joltjni_WheeledVehicleController
 * Method:    getRefCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_WheeledVehicleController_getRefCount
  (JNIEnv *, jclass, jlong controllerVa) {
    const WheeledVehicleController * const pController
            = reinterpret_cast<WheeledVehicleController *> (controllerVa);
    const uint32 result = pController->GetRefCount();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_WheeledVehicleController
 * Method:    setDriverInput
 * Signature: (JFFFF)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_WheeledVehicleController_setDriverInput
  (JNIEnv *, jclass, jlong controllerVa, jfloat forward, jfloat right,
  jfloat brake, jfloat handBrake) {
    WheeledVehicleController * const pController
            = reinterpret_cast<WheeledVehicleController *> (controllerVa);
    pController->SetDriverInput(forward, right, brake, handBrake);
}

/*
 * Class:     com_github_stephengold_joltjni_WheeledVehicleController
 * Method:    setEmbedded
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_WheeledVehicleController_setEmbedded
  (JNIEnv *, jclass, jlong controllerVa) {
    WheeledVehicleController * const pController
            = reinterpret_cast<WheeledVehicleController *> (controllerVa);
    pController->SetEmbedded();
}

/*
 * Class:     com_github_stephengold_joltjni_WheeledVehicleController
 * Method:    toRef
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_WheeledVehicleController_toRef
  (JNIEnv *, jclass, jlong controllerVa) {
    WheeledVehicleController * const pController
            = reinterpret_cast<WheeledVehicleController *> (controllerVa);
    Ref<WheeledVehicleController> * const pResult
            = new Ref<WheeledVehicleController>(pController);
    TRACE_NEW("Ref<WheeledVehicleController>", pResult)
    return reinterpret_cast<jlong> (pResult);
}