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

import com.github.stephengold.joltjni.enumerate.EBackFaceMode;
import com.github.stephengold.joltjni.readonly.ConstCollideShapeSettings;

/**
 * Configurable options for a collide-shape query.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class CollideShapeSettings
        extends CollideSettingsBase
        implements ConstCollideShapeSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate default settings.
     */
    public CollideShapeSettings() {
        long settingsVa = createDefault();
        setVirtualAddressAsOwner(settingsVa);
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public CollideShapeSettings(ConstCollideShapeSettings original) {
        long originalVa = original.targetVa();
        long copyVa = createCopy(originalVa);
        setVirtualAddressAsOwner(copyVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Copy the argument to the current settings.
     *
     * @param source the settings to copy (not {@code null}, unaffected)
     */
    public void set(ConstCollideShapeSettings source) {
        long targetVa = va();
        long sourceVa = source.targetVa();
        assign(targetVa, sourceVa);
    }

    /**
     * Alter the treatment of back-facing triangles. (native attribute:
     * mBackFaceMode)
     *
     * @param mode the desired mode (not {@code null}, default=IgnoreBackFaces)
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
    // ConstCollideShapeSettings methods

    /**
     * Return the treatment of back-facing triangles. The settings are
     * unaffected. (native attribute: mBackFaceMode)
     *
     * @return the enum value (not {@code null})
     */
    @Override
    public EBackFaceMode getBackFaceMode() {
        long settingsVa = va();
        int ordinal = getBackFaceMode(settingsVa);
        EBackFaceMode result = EBackFaceMode.values()[ordinal];

        return result;
    }

    /**
     * Return the tolerance used by to determine whether two edges are shared.
     * (native attribute: mInternalEdgeRemovalVertexToleranceSq)
     *
     * @return the tolerance (in meters^2)
     */
    @Override
    public float getInternalEdgeRemovalVertexToleranceSq() {
        long settingsVa = va();
        float result = getInternalEdgeRemovalVertexToleranceSq(settingsVa);

        return result;
    }

    /**
     * Return the maximum separation for which contacts will be reported. The
     * settings are unaffected. (native attribute: mMaxSeparationDistance)
     *
     * @return the separation limit (in meters)
     */
    @Override
    public float getMaxSeparationDistance() {
        long settingsVa = va();
        float result = getMaxSeparationDistance(settingsVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static void assign(long targetVa, long sourceVa);

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static int getBackFaceMode(long settingsVa);

    native private static float getInternalEdgeRemovalVertexToleranceSq(
            long settingsVa);

    native private static float getMaxSeparationDistance(long settingsVa);

    native private static void setBackFaceMode(long settingsVa, int ordinal);

    native private static void setMaxSeparationDistance(
            long settingsVa, float distance);
}
