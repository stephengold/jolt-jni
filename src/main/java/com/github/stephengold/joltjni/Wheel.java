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

import com.github.stephengold.joltjni.readonly.ConstWheelSettings;
import com.github.stephengold.joltjni.template.Ref;

/**
 * A single wheel of a vehicle.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Wheel extends NonCopyable {
    // *************************************************************************
    // fields

    /**
     * prevent premature garbage collection of the settings
     */
    final private Ref settings;
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param wheelVa the virtual address of the native object to assign (not
     * zero)
     */
    Wheel(VehicleConstraint container, long wheelVa) {
        super(container, wheelVa);

        long settingsVa = getSettings(wheelVa);
        if (this instanceof WheelTv) {
            this.settings = new WheelSettingsTv(settingsVa).toRef();
        } else if (this instanceof WheelWv) {
            this.settings = new WheelSettingsWv(settingsVa).toRef();
        } else {
            throw new IllegalStateException(this.getClass().getSimpleName());
        }
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Access the settings used to create this wheel.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public ConstWheelSettings getSettings() {
        WheelSettings result = (WheelSettings) settings.getPtr();
        return result;
    }

    /**
     * Instantiate a {@code Wheel} from its virtual address.
     *
     * @param wheelVa the virtual address of the native object, or zero
     * @param ordinal the type of {@code VehicleController}
     * @param container the containing object, or {@code null} if none
     * @return a new JVM object, or {@code null} if {@code wheelVa} was zero
     */
    public static Wheel newWheel(
            long wheelVa, int ordinal, VehicleConstraint container) {
        if (wheelVa == 0L) {
            return null;
        }

        Wheel result;
        switch (ordinal) {
            case VehicleController.motorcycleType:
            case VehicleController.wheeledVehicleType:
                result = new WheelWv(container, wheelVa);
                break;
            case VehicleController.trackedVehicleType:
                result = new WheelTv(container, wheelVa);
                break;
            default:
                throw new IllegalArgumentException("ordinal = " + ordinal);
        }

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long getSettings(long wheelVa);
}
