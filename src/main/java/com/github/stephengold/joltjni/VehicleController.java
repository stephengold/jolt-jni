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

import com.github.stephengold.joltjni.readonly.ConstVehicleController;

/**
 * Control the acceleration and deceleration of a vehicle.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class VehicleController
        extends NonCopyable
        implements ConstVehicleController {
    // *************************************************************************
    // constants

    /**
     * controller type for a generic vehicle
     */
    final static int genericType = 1;
    /**
     * controller type for a motorcycle
     */
    final static int motorcycleType = 2;
    /**
     * controller type for a vehicle with tracks
     */
    final static int trackedVehicleType = 3;
    /**
     * controller type for a trackless vehicle that isn't a motorcycle
     */
    final static int wheeledVehicleType = 4;
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param virtualAddress the virtual address of the native object to assign
     * (not zero)
     */
    VehicleController(VehicleConstraint container, long virtualAddress) {
        super(container, virtualAddress);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Access the underlying constraint.
     *
     * @return the pre-existing object or {@code null}
     */
    public VehicleConstraint getConstraint() {
        return (VehicleConstraint) getContainingObject();
    }

    /**
     * Instantiate a {@code VehicleController} from its virtual address.
     *
     * @param container the constraint that contains the controller
     * @param controllerVa the virtual address of the native object, or zero
     * @return a new JVM object, or {@code null} if {@code controllerVa} was
     * zero
     */
    static VehicleController newController(
            VehicleConstraint container, long controllerVa) {
        if (controllerVa == 0L) {
            return null;
        }

        long constraintVa = getConstraint(controllerVa);
        int ordinal = Constraint.getControllerType(constraintVa);
        VehicleController result;
        switch (ordinal) {
            case motorcycleType:
                result = new MotorcycleController(container, controllerVa);
                break;
            case trackedVehicleType:
                result = new TrackedVehicleController(container, controllerVa);
                break;
            case wheeledVehicleType:
                result = new WheeledVehicleController(container, controllerVa);
                break;
            default:
                throw new IllegalArgumentException("ordinal = " + ordinal);
        }

        return result;
    }
    // *************************************************************************
    // ConstVehicleController methods

    /**
     * Recreate the settings for this controller. The controller is unaffected.
     *
     * @return a new settings object
     */
    @Override
    public VehicleControllerSettings getSettings() {
        long controllerVa = va();
        long settingsVa = getSettings(controllerVa);
        VehicleControllerSettings result
                = new VehicleControllerSettings(settingsVa);

        return result;
    }
    // *************************************************************************
    // native methods

    native private static long getConstraint(long controllerVa);

    native private static long getSettings(long controllerVa);
}
