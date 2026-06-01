/*
Copyright (c) 2024-2026 Stephen Gold

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

import com.github.stephengold.joltjni.readonly.ConstWheeledVehicleControllerSettings;

/**
 * Settings used to construct a {@code WheeledVehicleController}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class WheeledVehicleControllerSettings
        extends VehicleControllerSettings
        implements ConstWheeledVehicleControllerSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate default settings.
     */
    public WheeledVehicleControllerSettings() {
        long controllerSettingsVa = createDefault();
        setVirtualAddressAsCoOwner(controllerSettingsVa);
    }

    /**
     * Instantiate settings with no native object assigned.
     *
     * @param dummy unused argument to distinguish from the zero-arg constructor
     */
    WheeledVehicleControllerSettings(boolean dummy) {
    }

    /**
     * Instantiate a settings object with the specified native object assigned.
     *
     * @param controllerSettingsVa the virtual address of the native object to
     * assign (not zero)
     */
    WheeledVehicleControllerSettings(long controllerSettingsVa) {
        setVirtualAddressAsCoOwner(controllerSettingsVa);
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public WheeledVehicleControllerSettings(
            ConstWheeledVehicleControllerSettings original) {
        long originalVa = original.targetVa();
        long copyVa = createCopy(originalVa);
        setVirtualAddressAsCoOwner(copyVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Copy the argument to the current settings.
     *
     * @param source the settings to copy (not {@code null}, unaffected)
     */
    public void set(ConstWheeledVehicleControllerSettings source) {
        long targetVa = va();
        long sourceVa = source.targetVa();
        assign(targetVa, sourceVa);
    }

    /**
     * Alter the ratio of the max/min average wheel speed for each differential.
     * (native field: mDifferentialLimitedSlipRatio)
     *
     * @param ratio the desired ratio (&gt;1, default=1.4)
     */
    public void setDifferentialLimitedSlipRatio(float ratio) {
        long controllerSettingsVa = va();
        setDifferentialLimitedSlipRatio(controllerSettingsVa, ratio);
    }

    /**
     * Alter the number of differentials. (native attribute: mDifferentials)
     *
     * @param count the desired number (&ge;0, default=0)
     */
    public void setNumDifferentials(int count) {
        long controllerSettingsVa = va();
        setNumDifferentials(controllerSettingsVa, count);
    }
    // *************************************************************************
    // WheeledVehicleControllerSettings methods

    /**
     * Access the settings for the specified differential. (native field:
     * mDifferentials)
     *
     * @param index the index of the differential to access (&ge;0)
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public VehicleDifferentialSettings getDifferential(int index) {
        long controllerSettingsVa = va();
        long differentialVa = getDifferential(controllerSettingsVa, index);
        VehicleDifferentialSettings result
                = new VehicleDifferentialSettings(this, differentialVa);

        return result;
    }

    /**
     * Return the ratio of the max/min average wheel speed for each
     * differential. The settings are unaffected. (native field:
     * mDifferentialLimitedSlipRatio)
     *
     * @return the ratio
     */
    @Override
    public float getDifferentialLimitedSlipRatio() {
        long controllerSettingsVa = va();
        float result = getDifferentialLimitedSlipRatio(controllerSettingsVa);

        return result;
    }

    /**
     * Access the engine settings. (native field: mEngine)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public VehicleEngineSettings getEngine() {
        long controllerSettingsVa = va();
        long engineVa = getEngine(controllerSettingsVa);
        VehicleEngineSettings result
                = new VehicleEngineSettings(this, engineVa);

        return result;
    }

    /**
     * Count how many differentials the vehicle will have. The settings are
     * unaffected. (native attribute: mDifferentials)
     *
     * @return the count (&ge;0)
     */
    @Override
    public int getNumDifferentials() {
        long controllerSettingsVa = va();
        int result = countDifferentials(controllerSettingsVa);

        return result;
    }

    /**
     * Access the transmission (gearbox) settings. (native attribute:
     * mTransmission)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public VehicleTransmissionSettings getTransmission() {
        long controllerSettingsVa = va();
        long transmissionVa = getTransmission(controllerSettingsVa);
        VehicleTransmissionSettings result
                = new VehicleTransmissionSettings(this, transmissionVa);

        return result;
    }
    // *************************************************************************
    // VehicleControllerSettings methods

    /**
     * Identify the type of {@code VehicleController} to be constructed.
     *
     * @return a numeric code
     */
    @Override
    int controllerTypeOrdinal() {
        return VehicleController.wheeledVehicleType;
    }
    // *************************************************************************
    // native private methods

    native private static void assign(long targetVa, long sourceVa);

    native private static int countDifferentials(long controllerSettingsVa);

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static long getDifferential(
            long controllerSettingsVa, int diffIndex);

    native private static float getDifferentialLimitedSlipRatio(
            long controllerSettingsVa);

    native private static long getEngine(long controllerSettingsVa);

    native private static long getTransmission(long controllerSettingsVa);

    native private static void setDifferentialLimitedSlipRatio(
            long controllerSettingsVa, float ratio);

    native private static void setNumDifferentials(
            long controllerSettingsVa, int count);
}
