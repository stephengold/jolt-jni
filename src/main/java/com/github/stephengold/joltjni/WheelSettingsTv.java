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

import com.github.stephengold.joltjni.readonly.ConstWheelSettingsTv;

/**
 * Settings used to construct a {@code WheelTv}. (native type: WheelSettingsTV)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class WheelSettingsTv
        extends WheelSettings
        implements ConstWheelSettingsTv {
    // *************************************************************************
    // constructors

    /**
     * Instantiate default settings.
     */
    public WheelSettingsTv() {
        long settingsVa = createDefault();
        setVirtualAddress(settingsVa); // not owner due to ref counting
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public WheelSettingsTv(ConstWheelSettingsTv original) {
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
    WheelSettingsTv(long settingsVa) {
        setVirtualAddress(settingsVa); // not owner due to ref counting
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Alter the wheel's friction in the sideways direction. (native attribute:
     * mLateralFriction)
     *
     * @param friction the desired friction (default=2)
     */
    public void setLateralFriction(float friction) {
        long settingsVa = va();
        setLateralFriction(settingsVa, friction);
    }

    /**
     * Alter the wheel's friction in the forward direction. (native attribute:
     * mLongitudinalFriction)
     *
     * @param friction the desired friction (default=4)
     */
    public void setLongitudinalFriction(float friction) {
        long settingsVa = va();
        setLongitudinalFriction(settingsVa, friction);
    }
    // *************************************************************************
    // ConstWheelSettingsTv methods

    /**
     * Return the tire's friction in the sideways direction. The settings are
     * unaffected. (native attribute: mLateralFriction)
     *
     * @return the friction
     */
    @Override
    public float getLateralFriction() {
        long settingsVa = va();
        float result = getLateralFriction(settingsVa);

        return result;
    }

    /**
     * Return the tire's friction in the forward direction. The settings are
     * unaffected. (native attribute: mLongitudinalFriction)
     *
     * @return the friction
     */
    @Override
    public float getLongitudinalFriction() {
        long settingsVa = va();
        float result = getLongitudinalFriction(settingsVa);

        return result;
    }
    // *************************************************************************
    // WheelSettings methods

    /**
     * Count the active references to the native {@code WheelSettingsTV}. The
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
     * Mark the native {@code WheelSettingsTV} as embedded.
     */
    @Override
    public void setEmbedded() {
        long settingsVa = va();
        setEmbedded(settingsVa);
    }

    /**
     * Create a counted reference to the native {@code WheelSettingsTV}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public WheelSettingsTvRef toRef() {
        long settingsVa = va();
        long refVa = toRef(settingsVa);
        WheelSettingsTvRef result = new WheelSettingsTvRef(refVa, true);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native static float getLateralFriction(long settingsVa);

    native static float getLongitudinalFriction(long settingsVa);

    native private static int getRefCount(long settingsVa);

    native private static void setEmbedded(long settingsVa);

    native static void setLateralFriction(long settingsVa, float friction);

    native static void setLongitudinalFriction(long settingsVa, float friction);

    native private static long toRef(long settingsVa);
}
