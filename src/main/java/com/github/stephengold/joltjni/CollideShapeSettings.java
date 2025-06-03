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

import com.github.stephengold.joltjni.enumerate.EBackFaceMode;

/**
 * Configurable options for a collide-shape query.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class CollideShapeSettings extends CollideSettingsBase {
    // *************************************************************************
    // constructors

    /**
     * Instantiate default settings.
     */
    public CollideShapeSettings() {
        long settingsVa = createDefault();
        setVirtualAddress(settingsVa, true);
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public CollideShapeSettings(CollideShapeSettings original) {
        long originalVa = original.va();
        long copyVa = createCopy(originalVa);
        setVirtualAddress(copyVa); // not owner due to ref counting
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the treatment of back-facing triangles. The settings are
     * unaffected. (native attribute: mBackFaceMode)
     *
     * @return the enum value (not null)
     */
    public EBackFaceMode getBackFaceMode() {
        long settingsVa = va();
        int ordinal = getBackFaceMode(settingsVa);
        EBackFaceMode result = EBackFaceMode.values()[ordinal];

        return result;
    }

    /**
     * Return the maximum separation for which contacts will be reported. The
     * settings are unaffected. (native attribute: mMaxSeparationDistance)
     *
     * @return the separation limit (in meters)
     */
    public float getMaxSeparationDistance() {
        long settingsVa = va();
        float result = getMaxSeparationDistance(settingsVa);

        return result;
    }

    /**
     * Alter the treatment of back-facing triangles. (native attribute:
     * mBackFaceMode)
     *
     * @param mode the desired mode (not null, default=IgnoreBackFaces)
     */
    public void setBackFaceMode(EBackFaceMode mode) {
        long settingsVa = va();
        int ordinal = mode.ordinal();
        setBackFaceMode(settingsVa, ordinal);
    }

    /**
     * Alter the maximum separation for which contacts will be reported. (native
     * attribute: mMaxSeparationDistance)
     *
     * @param distance the desired separation limit (in meters, default=0)
     */
    public void setMaxSeparationDistance(float distance) {
        long settingsVa = va();
        setMaxSeparationDistance(settingsVa, distance);
    }
    // *************************************************************************
    // native private methods

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static int getBackFaceMode(long settingsVa);

    native private static float getMaxSeparationDistance(long settingsVa);

    native private static void setBackFaceMode(long settingsVa, int ordinal);

    native private static void setMaxSeparationDistance(
            long settingsVa, float distance);
}
