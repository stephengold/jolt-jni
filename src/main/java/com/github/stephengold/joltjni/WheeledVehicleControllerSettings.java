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

/**
 * Settings used to construct a {@code WheeledVehicleController}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class WheeledVehicleControllerSettings
        extends VehicleControllerSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate default settings.
     */
    public WheeledVehicleControllerSettings() {
        long settingsVa = createDefault();
        setVirtualAddress(settingsVa, true);
    }

    /**
     * Instantiate settings with the specified native object assigned but not
     * owned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    WheeledVehicleControllerSettings(long settingsVa) {
        super(settingsVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Alter the left wheel assigned to the specified differential.
     *
     * @param diffIndex the index of the differential (&ge;0)
     * @param wheelIndex the index of the wheel (&ge;0)
     */
    public void setDifferentialsLeftWheel(int diffIndex, int wheelIndex) {
        long settingsVa = va();
        setDifferentialsLeftWheel(settingsVa, diffIndex, wheelIndex);
    }

    /**
     * Alter the right wheel assigned to the specified differential.
     *
     * @param diffIndex the index of the differential (&ge;0)
     * @param wheelIndex the index of the wheel (&ge;0)
     */
    public void setDifferentialsRightWheel(int diffIndex, int wheelIndex) {
        long settingsVa = va();
        setDifferentialsLRightWheel(settingsVa, diffIndex, wheelIndex);
    }

    /**
     * Alter the number of differentials. (native attribute: mDifferentials)
     *
     * @param count the desired number (&ge;0)
     */
    public void setNumDifferentials(int count) {
        long settingsVa = va();
        setNumDifferentials(settingsVa, count);
    }
    // *************************************************************************
    // native private methods

    native private static long createDefault();

    native private static void setDifferentialsLeftWheel(
            long settingsVa, int diffIndex, int wheelIndex);

    native private static void setDifferentialsLRightWheel(
            long settingsVa, int diffIndex, int wheelIndex);

    native private static void setNumDifferentials(long settingsVa, int count);
}
