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
package com.github.stephengold.joltjni;

import com.github.stephengold.joltjni.template.RefTarget;

/**
 * Control the acceleration and deceleration of a wheeled vehicle.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class WheeledVehicleController
        extends VehicleController
        implements RefTarget {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified native object assigned but not owned.
     *
     * @param constraint the underlying {@code VehicleConstraint} (not null)
     * @param controllerVa the virtual address of the native object to assign
     * (not zero)
     */
    WheeledVehicleController(VehicleConstraint constraint, long controllerVa) {
        super(constraint);
        setVirtualAddress(controllerVa, null);
    }
    // *************************************************************************
    // new methods exposed

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
    // VehicleController methods

    /**
     * Create a counted reference to the native
     * {@code WheeledVehicleController}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public WheeledVehicleControllerRef toRef() {
        long controllerVa = va();
        long copyVa = VehicleController.toRef(controllerVa);
        WheeledVehicleControllerRef result
                = new WheeledVehicleControllerRef(copyVa, true);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static void setDriverInput(long controllerVa, float forward,
            float right, float brake, float handBrake);
}
