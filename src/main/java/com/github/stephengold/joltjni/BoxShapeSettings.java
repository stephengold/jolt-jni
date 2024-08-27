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

import com.github.stephengold.joltjni.enumerate.EShapeSubType;
import com.github.stephengold.joltjni.readonly.ConstPhysicsMaterial;
import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * Settings used to construct a {@code BoxShape}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class BoxShapeSettings extends ConvexShapeSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified native object assigned but not owned.
     *
     * @param virtualAddress the virtual address of the native object to assign
     * (not zero)
     */
    BoxShapeSettings(long virtualAddress) {
        super(virtualAddress);
        setSubType(EShapeSubType.Box);
    }

    /**
     * Instantiate settings for the specified half extents.
     *
     * @param halfExtents the desired half extents on each local axis (not null,
     * all components &ge;0, unaffected)
     */
    public BoxShapeSettings(Vec3Arg halfExtents) {
        this(halfExtents, 0.05f);
    }

    /**
     * Instantiate settings for the specified half extents and convex radius.
     *
     * @param halfExtents the desired half extents on each local axis (not null,
     * all components &ge;0, unaffected)
     * @param convexRadius the desired convex radius (&ge;0, default=0.05)
     */
    public BoxShapeSettings(Vec3Arg halfExtents, float convexRadius) {
        this(halfExtents, convexRadius, null);
    }

    /**
     * Instantiate settings for the specified half extents, convex radius, and
     * material.
     *
     * @param halfExtents the desired half extents on each local axis (not null,
     * all components &ge;0, unaffected)
     * @param convexRadius the desired convex radius (&ge;0, default=0.05)
     * @param material the desired surface properties (not null, unaffected) or
     * {@code null} for default properties (default=null)
     */
    public BoxShapeSettings(Vec3Arg halfExtents, float convexRadius,
            ConstPhysicsMaterial material) {
        float hx = halfExtents.getX();
        float hy = halfExtents.getY();
        float hz = halfExtents.getZ();
        long materialVa = (material == null) ? 0L : material.va();
        long settingsVa = createBoxShapeSettings(
                hx, hy, hz, convexRadius, materialVa);
        setVirtualAddress(settingsVa, null); // not owner due to ref counting
        setSubType(EShapeSubType.Box);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the convex radius. The settings are unaffected. (native field:
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
     * Copy the extent of the box. The settings are unaffected. (native field:
     * mHalfExtent)
     *
     * @return a new vector: one-half of extent on each local axis
     */
    public Vec3 getHalfExtent() {
        long settingsVa = va();
        float hx = getHalfExtentX(settingsVa);
        float hy = getHalfExtentY(settingsVa);
        float hz = getHalfExtentZ(settingsVa);
        Vec3 result = new Vec3(hx, hy, hz);

        return result;
    }

    /**
     * Alter the convex radius. (native field: mConvexRadius)
     *
     * @param radius the desired convex radius (&ge;0, default=0.05)
     */
    public void setConvexRadius(float radius) {
        long settingsVa = va();
        setConvexRadius(settingsVa, radius);
    }

    /**
     * Alter the extent of the box. (native field: mHalfExtent)
     *
     * @param halfExtents the desired half extents on each local axis (not null,
     * all components &ge;0, unaffected, default=(0,0,0))
     */
    public void setHalfExtent(Vec3Arg halfExtents) {
        long settingsVa = va();
        float hx = halfExtents.getX();
        float hy = halfExtents.getY();
        float hz = halfExtents.getZ();
        setHalfExtent(settingsVa, hx, hy, hz);
    }
    // *************************************************************************
    // native private methods

    native private static long createBoxShapeSettings(
            float hx, float hy, float hz, float convexRadius, long materialVa);

    native private static float getConvexRadius(long settingsVa);

    native private static float getHalfExtentX(long settingsVa);

    native private static float getHalfExtentY(long settingsVa);

    native private static float getHalfExtentZ(long settingsVa);

    native private static void setConvexRadius(long settingsVa, float radius);

    native private static void setHalfExtent(
            long settingsVa, float hx, float hy, float hz);
}
