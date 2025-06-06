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
 * Configurable options for a shape-cast query.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class ShapeCastSettings extends CollideSettingsBase {
    // *************************************************************************
    // constructors

    /**
     * Instantiate default settings.
     */
    public ShapeCastSettings() {
        long settingsVa = createDefault();
        setVirtualAddress(settingsVa, true);
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public ShapeCastSettings(ShapeCastSettings original) {
        long originalVa = original.va();
        long copyVa = createCopy(originalVa);
        setVirtualAddress(copyVa, true);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the treatment of back-facing triangles in convex shapes. The
     * settings are unaffected. (native attribute: mBackFaceModeConvex)
     *
     * @return the enum value (not null)
     */
    public EBackFaceMode getBackFaceModeConvex() {
        long settingsVa = va();
        int ordinal = getBackFaceModeConvex(settingsVa);
        EBackFaceMode result = EBackFaceMode.values()[ordinal];

        return result;
    }

    /**
     * Return the treatment of back-facing triangles in triangle-based shapes.
     * The settings are unaffected. (native attribute: mBackFaceModeTriangles)
     *
     * @return the enum value (not null)
     */
    public EBackFaceMode getBackFaceModeTriangles() {
        long settingsVa = va();
        int ordinal = getBackFaceModeTriangles(settingsVa);
        EBackFaceMode result = EBackFaceMode.values()[ordinal];

        return result;
    }

    /**
     * Test whether to calculate penetration for the starting point. The
     * settings are unaffected. (native attribute: mReturnDeepestPoint)
     *
     * @return {@code true} to enable, {@code false} to disable
     */
    public boolean getReturnDeepestPoint() {
        long settingsVa = va();
        boolean result = getReturnDeepestPoint(settingsVa);

        return result;
    }

    /**
     * Test whether the shape should be shrunk and then expanded by the convex
     * radius. The settings are unaffected. (native attribute:
     * mUseShrunkenShapeAndConvexRadius)
     *
     * @return {@code true} to enable, {@code false} to disable
     */
    public boolean getUseShrunkenShapeAndConvexRadius() {
        long settingsVa = va();
        boolean result = getUseShrunkenShapeAndConvexRadius(settingsVa);

        return result;
    }

    /**
     * Alter the treatment of back-facing triangles in convex shapes. (native
     * attribute: mBackFaceModeConvex)
     *
     * @param mode the desired mode (not null, default=IgnoreBackFaces)
     */
    public void setBackFaceModeConvex(EBackFaceMode mode) {
        long settingsVa = va();
        int ordinal = mode.ordinal();
        setBackFaceModeConvex(settingsVa, ordinal);
    }

    /**
     * Alter the treatment of back-facing triangles in triangle-based shapes.
     * (native attribute: mBackFaceModeTriangles)
     *
     * @param mode the desired mode (not null, default=IgnoreBackFaces)
     */
    public void setBackFaceModeTriangles(EBackFaceMode mode) {
        long settingsVa = va();
        int ordinal = mode.ordinal();
        setBackFaceModeTriangles(settingsVa, ordinal);
    }

    /**
     * Alter whether to calculate penetration for the starting point. (native
     * attribute: mReturnDeepestPoint)
     *
     * @param enable {@code true} to enable the feature, {@code false} to
     * disable it (default=false)
     */
    public void setReturnDeepestPoint(boolean enable) {
        long settingsVa = va();
        setReturnDeepestPoint(settingsVa, enable);
    }

    /**
     * Alter whether the shape should be shrunk and then expanded by the convex
     * radius. (native attribute: mUseShrunkenShapeAndConvexRadius)
     *
     * @param enable {@code true} to enable the feature, {@code false} to
     * disable it (default=false)
     */
    public void setUseShrunkenShapeAndConvexRadius(boolean enable) {
        long settingsVa = va();
        setUseShrunkenShapeAndConvexRadius(settingsVa, enable);
    }
    // *************************************************************************
    // native private methods

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static int getBackFaceModeConvex(long settingsVa);

    native private static int getBackFaceModeTriangles(long settingsVa);

    native private static boolean getReturnDeepestPoint(long settingsVa);

    native private static boolean getUseShrunkenShapeAndConvexRadius(
            long settingsVa);

    native private static void setBackFaceModeConvex(
            long settingsVa, int ordinal);

    native private static void setBackFaceModeTriangles(
            long settingsVa, int ordinal);

    native private static void setReturnDeepestPoint(
            long settingsVa, boolean enable);

    native private static void setUseShrunkenShapeAndConvexRadius(
            long settingsVa, boolean enable);
}
