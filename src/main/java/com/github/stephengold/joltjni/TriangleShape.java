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

import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * A {@code Shape} to represent a single triangle.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class TriangleShape extends ConvexShape {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a shape with the specified native object assigned but not
     * owned.
     *
     * @param shapeVa the virtual address of the native object to assign (not
     * zero)
     */
    TriangleShape(long shapeVa) {
        super(shapeVa);
    }

    /**
     * Instantiate a shape with the specified vertices.
     *
     * @param v1 the location of the first vertex (not null, unaffected)
     * @param v2 the location of the 2nd vertex (not null, unaffected)
     * @param v3 the location of the 3rd vertex (not null, unaffected)
     */
    public TriangleShape(Vec3Arg v1, Vec3Arg v2, Vec3Arg v3) {
        this(v1, v2, v3, 0f);
    }

    /**
     * Instantiate a shape with the specified vertices and convex radius.
     *
     * @param v1 the location of the first vertex (not null, unaffected)
     * @param v2 the location of the 2nd vertex (not null, unaffected)
     * @param v3 the location of the 3rd vertex (not null, unaffected)
     * @param convexRadius the desired convex radius (default=0)
     */
    public TriangleShape(
            Vec3Arg v1, Vec3Arg v2, Vec3Arg v3, float convexRadius) {
        float v1x = v1.getX();
        float v1y = v1.getY();
        float v1z = v1.getZ();
        float v2x = v2.getX();
        float v2y = v2.getY();
        float v2z = v2.getZ();
        float v3x = v3.getX();
        float v3y = v3.getY();
        float v3z = v3.getZ();
        long shapeVa = createTriangleShape(
                v1x, v1y, v1z, v2x, v2y, v2z, v3x, v3y, v3z, convexRadius);
        setVirtualAddress(shapeVa); // not the owner due to ref counting
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
    // *************************************************************************
    // native private methods

    native private static long createTriangleShape(
            float v1x, float v1y, float v1z, float v2x, float v2y, float v2z,
            float v3x, float v3y, float v3z, float convexRadius);

    native private static float getConvexRadius(long shapeVa);
}
