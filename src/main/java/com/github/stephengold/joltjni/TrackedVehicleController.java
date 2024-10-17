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

import com.github.stephengold.joltjni.readonly.Vec3Arg;
import com.github.stephengold.joltjni.template.RefTarget;

/**
 * Control the acceleration and deceleration of a vehicle with tracks.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class TrackedVehicleController
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
    TrackedVehicleController(VehicleConstraint constraint, long controllerVa) {
        super(constraint, controllerVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Update the control inputs from the vehicle's driver.
     *
     * @param forward the desired driving direction and amount of acceleration
     * (&ge;-1, &le;1)
     * @param leftRatio the left-track rotation-rate ratio (&ge;-1, &le;1)
     * @param rightRatio the right-track rotation-rate ratio (&ge;-1, &le;1)
     * @param brake the desired brake pressure (&ge;0, &le;1)
     */
    public void setDriverInput(
            float forward, float leftRatio, float rightRatio, float brake) {
        long controllerVa = va();
        setDriverInput(controllerVa, forward, leftRatio, rightRatio, brake);
    }

    /**
     * Configure debug rendering of the RPM meter.
     *
     * @param location the desired location of the meter (not null, unaffected)
     * @param size the desired size of the meter
     */
    public void setRpmMeter(Vec3Arg location, float size) {
        long controllerVa = va();
        float locX = location.getX();
        float locY = location.getY();
        float locZ = location.getZ();
        setRpmMeter(controllerVa, locX, locY, locZ, size);
    }
    // *************************************************************************
    // VehicleController methods

    /**
     * Create a counted reference to the native
     * {@code TrackedVehicleController}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public TrackedVehicleControllerRef toRef() {
        long controllerVa = va();
        long copyVa = VehicleController.toRef(controllerVa);
        TrackedVehicleControllerRef result
                = new TrackedVehicleControllerRef(copyVa, true);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static void setDriverInput(long controllerVa, float forward,
            float leftRatio, float rightRatio, float brake);

    native private static void setRpmMeter(long controllerVa,
            float locX, float locY, float locZ, float size);
}
