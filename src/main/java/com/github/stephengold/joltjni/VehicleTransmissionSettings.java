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

import com.github.stephengold.joltjni.enumerate.ETransmissionMode;

/**
 * Settings used to configure the transmission (gearbox) of a
 * {@code WheeledVehicleController}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class VehicleTransmissionSettings extends JoltPhysicsObject {
    // *************************************************************************
    // fields

    /**
     * prevent premature garbage collection of the underlying
     * {@code WheeledVehicleControllerSettings}
     */
    final private WheeledVehicleControllerSettings vehicle;
    // *************************************************************************
    // constructors

    /**
     * Instantiate a settings with the specified native object assigned but not
     * owned.
     *
     * @param vehicle the underlying {@code WheeledVehicleControllerSettings}
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    VehicleTransmissionSettings(
            WheeledVehicleControllerSettings vehicle, long settingsVa) {
        this.vehicle = vehicle;
        setVirtualAddress(settingsVa, null);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return how long it takes to release the clutch in {@code Auto} mode. The
     * settings are unaffected. (native attribute: mClutchReleaseTime)
     *
     * @return the latency (in seconds)
     */
    public float getClutchReleaseTime() {
        long settingsVa = va();
        float result = getClutchReleaseTime(settingsVa);

        return result;
    }

    /**
     * Return the total torque applied by the clutch when fully engaged. The
     * settings are unaffected. (native attribute: mClutchStrength)
     *
     * @return the strength
     */
    public float getClutchStrength() {
        long settingsVa = va();
        float result = getClutchStrength(settingsVa);

        return result;
    }

    /**
     * Return the number of engine rotations per gearbox rotation for each
     * forward gear. The first element is for 1st gear. The settings are
     * unaffected. (native attribute: mGearRatios)
     *
     * @return a new array
     */
    public float[] getGearRatios() {
        long settingsVa = va();
        float[] result = getGearRatios(settingsVa);

        return result;
    }

    /**
     * Return the type of transmission. The settings are unaffected. (native
     * attribute: mMode)
     *
     * @return an enum value (not null)
     */
    public ETransmissionMode getMode() {
        long settingsVa = va();
        int ordinal = getMode(settingsVa);
        ETransmissionMode result = ETransmissionMode.values()[ordinal];

        return result;
    }

    /**
     * Return the number of engine rotations per gearbox rotation for each
     * reverse gear. The settings are unaffected. (native attribute:
     * mReverseGearRatios)
     *
     * @return a new array
     */
    public float[] getReverseGearRatios() {
        long settingsVa = va();
        float[] result = getReverseGearRatios(settingsVa);

        return result;
    }

    /**
     * Return the maximum engine RPMs for downshifting in {@code Auto} mode. The
     * settings are unaffected. (native attribute: mShiftDownRPM)
     *
     * @return the threshold rate (in revolutions per minute)
     */
    public float getShiftDownRpm() {
        long settingsVa = va();
        float result = getShiftDownRpm(settingsVa);

        return result;
    }

    /**
     * Return the minimum engine RPMs for upshifting in {@code Auto} mode. The
     * settings are unaffected. (native attribute: mShiftUpRPM)
     *
     * @return the threshold rate (in revolutions per minute)
     */
    public float getShiftUpRpm() {
        long settingsVa = va();
        float result = getShiftUpRpm(settingsVa);

        return result;
    }

    /**
     * Return the minimum time between shifts in {@code Auto} mode. The settings
     * are unaffected. (native attribute: mSwitchLatency)
     *
     * @return the latency (in seconds)
     */
    public float getSwitchLatency() {
        long settingsVa = va();
        float result = getSwitchLatency(settingsVa);

        return result;
    }

    /**
     * Return how long it takes to shift gears in {@code Auto} mode. The
     * settings are unaffected. (native attribute: mSwitchTime)
     *
     * @return the latency (in seconds)
     */
    public float getSwitchTime() {
        long settingsVa = va();
        float result = getSwitchTime(settingsVa);

        return result;
    }

    /**
     * Alter how long it takes to release the clutch in {@code Auto} mode.
     * (native attribute: mClutchReleaseTime)
     *
     * @param latency the desired latency (in seconds, default=0.3)
     */
    public void setClutchReleaseTime(float latency) {
        long settingsVa = va();
        setClutchStrength(settingsVa, latency);
    }

    /**
     * Alter the total torque applied by the clutch when fully engaged. (native
     * attribute: mClutchStrength)
     *
     * @param strength the desired strength (default=10)
     */
    public void setClutchStrength(float strength) {
        long settingsVa = va();
        setClutchStrength(settingsVa, strength);
    }

    /**
     * Alter the number of engine rotations per gearbox rotation for each
     * forward gear. The first element is for 1st gear. (native attribute:
     * mGearRatios)
     *
     * @param ratios the desired ratios (not null)
     */
    public void setGearRatios(float... ratios) {
        long settingsVa = va();
        setGearRatios(settingsVa, ratios);
    }

    /**
     * Alter the type of transmission. (native attribute: mMode)
     *
     * @param mode the desired mode (not null, default=Auto)
     */
    public void setMode(ETransmissionMode mode) {
        long settingsVa = va();
        int ordinal = mode.ordinal();
        setMode(settingsVa, ordinal);
    }

    /**
     * Alter the number of engine rotations per gearbox rotation for each
     * reverse gear. (native attribute: mReverseGearRatios)
     *
     * @param ratios the desired ratios (not null)
     */
    public void setReverseGearRatios(float... ratios) {
        long settingsVa = va();
        setReverseGearRatios(settingsVa, ratios);
    }

    /**
     * Alter the maximum engine RPMs for downshifting in {@code Auto} mode.
     * (native attribute: mShiftDownRPM)
     *
     * @param rpm (default=2000)
     */
    public void setShiftDownRpm(float rpm) {
        long settingsVa = va();
        setShiftDownRpm(settingsVa, rpm);
    }

    /**
     * Alter the minimum engine RPMs for upshifting in {@code Auto} mode.
     * (native attribute: mShiftUpRPM)
     *
     * @param rpm (default=4000)
     */
    public void setShiftUpRpm(float rpm) {
        long settingsVa = va();
        setShiftUpRpm(settingsVa, rpm);
    }

    /**
     * Alter the minimum time between shifts in {@code Auto} mode. (native
     * attribute: mSwitchLatency)
     *
     * @param latency the desired latency (in seconds)
     */
    public void setSwitchLatency(float latency) {
        long settingsVa = va();
        setSwitchLatency(settingsVa, latency);
    }

    /**
     * Alter how long it takes to shift gears in {@code Auto} mode. (native
     * attribute: mSwitchTime)
     *
     * @param latency the desired latency (in seconds)
     */
    public void setSwitchTime(float latency) {
        long settingsVa = va();
        setSwitchTime(settingsVa, latency);
    }
    // *************************************************************************
    // native private methods

    native private static float getClutchReleaseTime(long settingsVa);

    native private static float getClutchStrength(long settingsVa);

    native private static float[] getGearRatios(long settingsVa);

    native private static int getMode(long settingsVa);

    native private static float[] getReverseGearRatios(long settingsVa);

    native private static float getShiftDownRpm(long settingsVa);

    native private static float getShiftUpRpm(long settingsVa);

    native private static float getSwitchLatency(long settingsVa);

    native private static float getSwitchTime(long settingsVa);

    native private static void setClutchStrength(
            long settingsVa, float strength);

    native private static void setGearRatios(long settingsVa, float[] ratios);

    native private static void setMode(long settingsVa, int mode);

    native private static void setReverseGearRatios(
            long settingsVa, float[] ratios);

    native private static void setShiftDownRpm(long settingsVa, float rpm);

    native private static void setShiftUpRpm(long settingsVa, float rpm);

    native private static void setSwitchLatency(long settingsVa, float latency);

    native private static void setSwitchTime(long settingsVa, float latency);
}
