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

import com.github.stephengold.joltjni.enumerate.EShapeSubType;
import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * Settings used to construct a {@code TriangleShape}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class TriangleShapeSettings extends ConvexShapeSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate default settings.
     */
    public TriangleShapeSettings() {
        long settingsVa = createDefault();
        setVirtualAddress(settingsVa); // not owner due to ref counting
        setSubType(EShapeSubType.Triangle);
    }

    /**
     * Instantiate with the specified native object assigned but not owned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    TriangleShapeSettings(long settingsVa) {
        super(settingsVa);
        setSubType(EShapeSubType.Triangle);
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public TriangleShapeSettings(TriangleShapeSettings original) {
        long originalVa = original.va();
        long copyVa = createCopy(originalVa);
        setVirtualAddress(copyVa); // not owner due to ref counting
        setSubType(EShapeSubType.Triangle);
    }

    /**
     * Instantiate settings for the specified vertices.
     *
     * @param v1 the location of the first vertex (not null, unaffected)
     * @param v2 the location of the 2nd vertex (not null, unaffected)
     * @param v3 the location of the 3rd vertex (not null, unaffected)
     */
    public TriangleShapeSettings(Vec3Arg v1, Vec3Arg v2, Vec3Arg v3) {
        this(v1, v2, v3, 0f);
    }

    /**
     * Instantiate settings for the specified vertices.
     *
     * @param v1 the location of the first vertex (not null, unaffected)
     * @param v2 the location of the 2nd vertex (not null, unaffected)
     * @param v3 the location of the 3rd vertex (not null, unaffected)
     * @param convexRadius the desired convex radius (default=0)
     */
    public TriangleShapeSettings(
            Vec3Arg v1, Vec3Arg v2, Vec3Arg v3, float convexRadius) {
        this(v1, v2, v3, convexRadius, null);
    }

    /**
     * Instantiate settings for the specified vertices.
     *
     * @param v1 the location of the first vertex (not null, unaffected)
     * @param v2 the location of the 2nd vertex (not null, unaffected)
     * @param v3 the location of the 3rd vertex (not null, unaffected)
     * @param convexRadius the desired convex radius (default=0)
     * @param material the desired surface properties (not null, unaffected) or
     * {@code null} for default properties (default=null)
     */
    public TriangleShapeSettings(Vec3Arg v1, Vec3Arg v2, Vec3Arg v3,
            float convexRadius, PhysicsMaterial material) {
        float v1x = v1.getX();
        float v1y = v1.getY();
        float v1z = v1.getZ();
        float v2x = v2.getX();
        float v2y = v2.getY();
        float v2z = v2.getZ();
        float v3x = v3.getX();
        float v3y = v3.getY();
        float v3z = v3.getZ();
        long materialVa = (material == null) ? 0L : material.va();
        long settingsVa = createTriangleShapeSettings(v1x, v1y, v1z, v2x, v2y,
                v2z, v3x, v3y, v3z, convexRadius, materialVa);
        setVirtualAddress(settingsVa); // not owner due to ref counting
        setSubType(EShapeSubType.Triangle);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the convex radius. The settings are unaffected. (native attribute:
     * mConvexRadius)
     *
     * @return the convex radius (&ge;0)
     */
    public float getConvexRadius() {
        long settingsVa = va();
        float result = getConvexRadius(settingsVa);

        return result;
    }

    /**
     * Alter the convex radius. (native attribute: mConvexRadius)
     *
     * @param radius the desired convex radius (&ge;0)
     */
    public void setConvexRadius(float radius) {
        long settingsVa = va();
        setConvexRadius(settingsVa, radius);
    }
    // *************************************************************************
    // native private methods

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static long createTriangleShapeSettings(float v1x, float v1y,
            float v1z, float v2x, float v2y, float v2z, float v3x, float v3y,
            float v3z, float convexRadius, long materialVa);

    native private static float getConvexRadius(long settingsVa);

    native private static void setConvexRadius(long settingsVa, float radius);
}
