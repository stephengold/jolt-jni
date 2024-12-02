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
 * Settings used to construct a {@code WheelTv}. (native type: WheelSettingsTV)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class WheelSettingsTv extends WheelSettings {
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
     * Instantiate with the specified native object assigned but not owned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    WheelSettingsTv(long settingsVa) {
        setVirtualAddress(settingsVa); // not owner due to ref counting
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

    native private static long createDefault();

    native private static int getRefCount(long settingsVa);

    native private static void setEmbedded(long settingsVa);

    native private static long toRef(long settingsVa);
}
