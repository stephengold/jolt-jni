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
     * Access the settings of the specified differential. (native field:
     * mDifferentials)
     *
     * @param index the index of differential to access (&ge;0)
     * @return a new JVM object with the pre-existing native object assigned
     */
    public VehicleDifferentialSettings getDifferential(int index) {
        long vehicleSettingsVa = va();
        long differentialVa = getDifferential(vehicleSettingsVa, index);
        VehicleDifferentialSettings result
                = new VehicleDifferentialSettings(this, differentialVa);

        return result;
    }

    /**
     * Access the engine settings. (native field: mEngine)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public VehicleEngineSettings getEngine() {
        long vehicleSettingsVa = va();
        long engineVa = getEngine(vehicleSettingsVa);
        VehicleEngineSettings result
                = new VehicleEngineSettings(this, engineVa);

        return result;
    }

    /**
     * Count how many differentials the vehicle will have. (native attribute:
     * mDifferentials)
     *
     * @return the count (&ge;0)
     */
    public int getNumDifferentials() {
        long vehicleSettingsVa = va();
        int result = countDifferentials(vehicleSettingsVa);

        return result;
    }

    /**
     * Access the transmission (gearbox) settings. (native attribute:
     * mTransmission)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public VehicleTransmissionSettings getTransmission() {
        long vehicleSettingsVa = va();
        long transmissionVa = getTransmission(vehicleSettingsVa);
        VehicleTransmissionSettings result
                = new VehicleTransmissionSettings(this, transmissionVa);

        return result;
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

    native private static int countDifferentials(long vehicleSettingsVa);

    native private static long createDefault();

    native private static long getDifferential(
            long vehicleSettingsVa, int diffIndex);

    native private static long getEngine(long vehicleSettingsVa);

    native private static long getTransmission(long vehicleSettingsVa);

    native private static void setNumDifferentials(long settingsVa, int count);
}
