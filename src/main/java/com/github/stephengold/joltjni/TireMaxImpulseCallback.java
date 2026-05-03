/*
Copyright (c) 2026 Stephen Gold

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
package com.github.stephengold.joltjni;

import java.nio.FloatBuffer;

/**
 * Calculate maximum tire impulses for a wheeled vehicle. (native type:
 * {@code WheeledVehicleController::TireMaxImpulseCallback})
 *
 * @author Stephen Gold sgold@sonic.net
 */
abstract public class TireMaxImpulseCallback extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a callback with no native object assigned.
     */
    TireMaxImpulseCallback() {
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Perform the calculations.
     *
     * @param wheelIndex the index of the desired wheel (&ge;0)
     * @param suspensionImpulse the suspension impulse
     * @param storeFloats storage for the results (not {@code null},
     * capacity&ge;2, 0:lateral, 1:longitudinal)
     * @param longitudinalFriction the longitudinal friction coefficient
     * @param lateralFriction the lateral friction coefficient
     * @param longitudinalSlip the longitudinal slip velocity
     * @param lateralSlip the lateral slip angle (in radians)
     * @param deltaTime the duration of the simulation step (in seconds)
     */
    public void calculate(int wheelIndex, FloatBuffer storeFloats,
            float suspensionImpulse, float longitudinalFriction,
            float lateralFriction, float longitudinalSlip, float lateralSlip,
            float deltaTime) {
        long callbackVa = va();
        calculate(callbackVa, wheelIndex, storeFloats, suspensionImpulse,
                longitudinalFriction, lateralFriction, longitudinalSlip,
                lateralSlip, deltaTime);
    }
    // *************************************************************************
    // new protected methods

    /**
     * Assign a native object (assuming there's none already assigned) and
     * designate the JVM object as the owner.
     *
     * @param callbackVa the virtual address of the native object to assign (not
     * zero)
     */
    final void setVirtualAddressAsOwner(long callbackVa) {
        Runnable freeingAction = () -> free(callbackVa);
        setVirtualAddress(callbackVa, freeingAction);
    }
    // *************************************************************************
    // native private methods

    native private static void calculate(long callbackVa, int wheelIndex,
            FloatBuffer storeFloats, float suspensionImpulse,
            float longitudinalFriction, float lateralFriction,
            float longitudinalSlip, float lateralSlip, float deltaTime);

    native private static void free(long callbackVa);
}
