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

import com.github.stephengold.joltjni.readonly.ConstPhysicsMaterial;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.FloatBuffer;

/**
 * A {@code Shape} to represent centered, axis-aligned rectangular solids.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class BoxShape extends ConvexShape {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a cubic shape with the specified half extents.
     *
     * @param halfExtent the desired half extents (&gt;0)
     */
    public BoxShape(float halfExtent) {
        this(halfExtent, halfExtent, halfExtent);
    }

    /**
     * Instantiate a shape with the specified half extents.
     *
     * @param xHalfExtent the desired half extent on the local X axis (&gt;0)
     * @param yHalfExtent the desired half extent on the local Y axis (&gt;0)
     * @param zHalfExtent the desired half extent on the local Z axis (&gt;0)
     */
    public BoxShape(float xHalfExtent, float yHalfExtent, float zHalfExtent) {
        assert xHalfExtent > 0f : xHalfExtent;
        assert yHalfExtent > 0f : yHalfExtent;
        assert zHalfExtent > 0f : zHalfExtent;

        long materialVa = 0L;
        long shapeVa = createBoxShape(xHalfExtent, yHalfExtent, zHalfExtent,
                Jolt.cDefaultConvexRadius, materialVa);
        setVirtualAddressAsCoOwner(shapeVa);
    }

    /**
     * Instantiate a shape with the specified native object assigned.
     *
     * @param shapeVa the virtual address of the native object to assign (not
     * zero)
     */
    BoxShape(long shapeVa) {
        setVirtualAddressAsCoOwner(shapeVa);
    }

    /**
     * Instantiate a shape with the specified half extents.
     *
     * @param halfExtents the desired half extents on each local axis (not null,
     * all components &gt;0, unaffected)
     */
    public BoxShape(Vec3Arg halfExtents) {
        this(halfExtents, Jolt.cDefaultConvexRadius);
    }

    /**
     * Instantiate a shape with the specified half extents and convex radius.
     *
     * @param halfExtents the desired half extents on each local axis (not null,
     * all components &ge;convexRadius, unaffected)
     * @param convexRadius the desired convex radius (default=0.05)
     */
    public BoxShape(Vec3Arg halfExtents, float convexRadius) {
        this(halfExtents, convexRadius, null);
    }

    /**
     * Instantiate a shape with the specified parameters.
     *
     * @param halfExtents the desired half extents on each local axis (not null,
     * all components &gt;0, unaffected)
     * @param convexRadius the desired convex radius (default=0.05)
     * @param material the desired material (default=null)
     */
    public BoxShape(Vec3Arg halfExtents, float convexRadius,
            ConstPhysicsMaterial material) {
        float xHalfExtent = halfExtents.getX();
        float yHalfExtent = halfExtents.getY();
        float zHalfExtent = halfExtents.getZ();
        assert xHalfExtent > 0f : xHalfExtent;
        assert yHalfExtent > 0f : yHalfExtent;
        assert zHalfExtent > 0f : zHalfExtent;

        long materialVa = (material == null) ? 0L : material.targetVa();
        long shapeVa = createBoxShape(xHalfExtent, yHalfExtent, zHalfExtent,
                convexRadius, materialVa);
        setVirtualAddressAsCoOwner(shapeVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the convex radius. The shape is unaffected.
     *
     * @return the radius (&ge;0)
     */
    public float getConvexRadius() {
        long shapeVa = va();
        float result = getConvexRadius(shapeVa);

        return result;
    }

    /**
     * Copy the half extents. The shape is unaffected.
     *
     * @return a new vector
     */
    public Vec3 getHalfExtent() {
        long shapeVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getHalfExtent(shapeVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createBoxShape(
            float xHalfExtent, float yHalfExtent, float zHalfExtent,
            float convexRadius, long materialVa);

    native private static float getConvexRadius(long shapeVa);

    native private static void getHalfExtent(
            long shapeVa, FloatBuffer storeFloats);
}
