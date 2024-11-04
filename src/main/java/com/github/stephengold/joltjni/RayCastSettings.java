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

import com.github.stephengold.joltjni.enumerate.EBackFaceMode;

/**
 * Raycast configuration options.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class RayCastSettings extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate default settings.
     */
    public RayCastSettings() {
        long settingsVa = createDefaultSettings();
        setVirtualAddress(settingsVa, () -> free(settingsVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the policy for back-facing triangles in convex shapes. The
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
     * Return the policy for back-facing triangles in non-convex shapes. The
     * settings are unaffected. (native attribute: mBackFaceModeTriangles)
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
     * Test whether convex shapes should be treated as solid. The settings are
     * unaffected. (native attribute: mTreatConvexAsSolid)
     *
     * @return {@code true} if treated as solid, otherwise {@code false}
     */
    public boolean getTreatConvexAsSolid() {
        long settingsVa = va();
        boolean result = getTreatConvexAsSolid(settingsVa);

        return result;
    }

    /**
     * Alter the policy for back-facing triangles in convex shapes. (native
     * attribute: mBackFaceModeConvex)
     *
     * @param setting the enum value (not null, default=IgnoreBackFaces)
     */
    public void setBackFaceModeConvex(EBackFaceMode setting) {
        long settingsVa = va();
        int ordinal = setting.ordinal();
        setBackFaceModeConvex(settingsVa, ordinal);
    }

    /**
     * Alter the policy for back-facing triangles in non-convex shapes. (native
     * attribute: mBackFaceModeTriangles)
     *
     * @param setting the enum value (not null, default=IgnoreBackFaces)
     */
    public void setBackFaceModeTriangles(EBackFaceMode setting) {
        long settingsVa = va();
        int ordinal = setting.ordinal();
        setBackFaceModeTriangles(settingsVa, ordinal);
    }

    /**
     * Alter whether convex shapes should be treated as solid. (native
     * attribute: mTreatConvexAsSolid)
     *
     * @param setting true&rarr;solid, false&rarr;hollow (default=true)
     */
    public void setTreatConvexAsSolid(boolean setting) {
        long settingsVa = va();
        setTreatConvexAsSolid(settingsVa, setting);
    }
    // *************************************************************************
    // native private methods

    native private static long createDefaultSettings();

    native private static void free(long settingsVa);

    native private static int getBackFaceModeConvex(long settingsVa);

    native private static int getBackFaceModeTriangles(long settingsVa);

    native private static boolean getTreatConvexAsSolid(long settingsVa);

    native private static void setBackFaceModeConvex(
            long settingsVa, int ordinal);

    native private static void setBackFaceModeTriangles(
            long settingsVa, int ordinal);

    native private static void setTreatConvexAsSolid(
            long settingsVa, boolean setting);
}
