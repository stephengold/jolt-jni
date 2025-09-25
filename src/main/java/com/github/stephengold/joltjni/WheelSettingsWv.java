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

import com.github.stephengold.joltjni.readonly.ConstWheelSettingsWv;

/**
 * Settings used to construct a {@code WheelWv}. (native type: WheelSettingsWV)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class WheelSettingsWv
        extends WheelSettings
        implements ConstWheelSettingsWv {
    // *************************************************************************
    // constructors

    /**
     * Instantiate default settings.
     */
    public WheelSettingsWv() {
        long settingsVa = createDefault();
        setVirtualAddress(settingsVa); // not owner due to ref counting
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public WheelSettingsWv(ConstWheelSettingsWv original) {
        long originalVa = original.targetVa();
        long copyVa = createCopy(originalVa);
        setVirtualAddress(copyVa); // not owner due to ref counting
    }

    /**
     * Instantiate with the specified native object assigned but not owned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    WheelSettingsWv(long settingsVa) {
        setVirtualAddress(settingsVa); // not owner due to ref counting
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Alter the maximum torque that the main brake can exert on the wheel.
     * (native attribute: mMaxBrakeTorque)
     *
     * @param torque the desired torque (in Newton.meters, default=1500)
     */
    public void setMaxBrakeTorque(float torque) {
        long settingsVa = va();
        setMaxBrakeTorque(settingsVa, torque);
    }

    /**
     * Alter the maximum torque that the hand brake can exert on the wheel.
     * (native attribute: mMaxHandBrakeTorque)
     *
     * @param torque the desired torque (in Newton.meters, default=4000)
     */
    public void setMaxHandBrakeTorque(float torque) {
        long settingsVa = va();
        setMaxHandBrakeTorque(settingsVa, torque);
    }

    /**
     * Alter the maximum steering angle. (native attribute: mMaxSteerAngle)
     *
     * @param angle the desired maximum steering angle (in radians,
     * default=7*Pi/18)
     */
    public void setMaxSteerAngle(float angle) {
        long settingsVa = va();
        setMaxSteerAngle(settingsVa, angle);
    }
    // *************************************************************************
    // ConstWheelSettingsWv methods

    /**
     * Return the maximum torque that the main brake can exert on the wheel. The
     * settings are unaffected. (native attribute: mMaxBrakeTorque)
     *
     * @return the maximum torque (in Newton.meters)
     */
    @Override
    public float getMaxBrakeTorque() {
        long settingsVa = va();
        float result = getMaxBrakeTorque(settingsVa);

        return result;
    }

    /**
     * Return the maximum torque that the hand brake can exert on the wheel. The
     * settings are unaffected. (native attribute: mMaxHandBrakeTorque)
     *
     * @return the maximum torque (in Newton.meters)
     */
    @Override
    public float getMaxHandBrakeTorque() {
        long settingsVa = va();
        float result = getMaxHandBrakeTorque(settingsVa);

        return result;
    }

    /**
     * Return the maximum steering angle. The settings are unaffected. (native
     * attribute: mMaxSteerAngle)
     *
     * @return the maximum steering angle (in radians)
     */
    @Override
    public float getMaxSteerAngle() {
        long settingsVa = va();
        float result = getMaxSteerAngle(settingsVa);

        return result;
    }
    // *************************************************************************
    // WheelSettings methods

    /**
     * Count the active references to the native {@code WheelSettingsWv}. The
     * settings are unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int getRefCount() {
        long settingsVa = va();
        int result = getRefCount(settingsVa);

        return result;
    }

    /**
     * Mark the native {@code WheelSettingsWV} as embedded.
     */
    @Override
    public void setEmbedded() {
        long settingsVa = va();
        setEmbedded(settingsVa);
    }

    /**
     * Create a counted reference to the native {@code WheelSettingsWV}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public WheelSettingsWvRef toRef() {
        long settingsVa = va();
        long refVa = toRef(settingsVa);
        WheelSettingsWvRef result = new WheelSettingsWvRef(refVa, true);

        return result;
    }
    // *************************************************************************
    // native methods

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native static float getMaxBrakeTorque(long settingsVa);

    native static float getMaxHandBrakeTorque(long settingsVa);

    native static float getMaxSteerAngle(long settingsVa);

    native private static int getRefCount(long settingsVa);

    native private static void setEmbedded(long settingsVa);

    native static void setMaxBrakeTorque(long settingsVa, float torque);

    native static void setMaxHandBrakeTorque(long settingsVa, float torque);

    native static void setMaxSteerAngle(long settingsVa, float angle);

    native private static long toRef(long settingsVa);
}
