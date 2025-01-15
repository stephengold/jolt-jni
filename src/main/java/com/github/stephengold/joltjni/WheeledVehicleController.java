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
package com.github.stephengold.joltjni;

/**
 * Control the acceleration and deceleration of a wheeled vehicle.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class WheeledVehicleController extends VehicleController {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified native object assigned but not owned.
     *
     * @param constraint the containing object, or {@code null} if none
     * @param controllerVa the virtual address of the native object to assign
     * (not zero)
     */
    WheeledVehicleController(VehicleConstraint constraint, long controllerVa) {
        super(constraint, controllerVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Access the vehicle's engine.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public VehicleEngine getEngine() {
        long controllerVa = va();
        long engineVa = getEngine(controllerVa);
        VehicleEngine result = new VehicleEngine(this, engineVa);

        return result;
    }

    /**
     * Update the control inputs from the vehicle's driver.
     *
     * @param forward the desired driving direction and amount of acceleration
     * (&ge;-1, &le;1)
     * @param right the desired steering angle (&ge;-1, &le;1)
     * @param brake the desired main brake pressure (&ge;0, &le;1)
     * @param handBrake the desired amount of hand braking (&ge;0, &le;1)
     */
    public void setDriverInput(
            float forward, float right, float brake, float handBrake) {
        long controllerVa = va();
        setDriverInput(controllerVa, forward, right, brake, handBrake);
    }
    // *************************************************************************
    // native private methods

    native private static long getEngine(long controllerVa);

    native private static void setDriverInput(long controllerVa, float forward,
            float right, float brake, float handBrake);
}
