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
 * A {@code Shape} to represent a convex hull defined by a collection of
 * vertices.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class ConvexHullShape extends ConvexShape {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a shape with the specified native object assigned.
     *
     * @param shapeVa the virtual address of the native object to assign (not
     * zero)
     */
    ConvexHullShape(long shapeVa) {
        super(shapeVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Count the vertices of the convex hull.
     *
     * @return the count (&ge;0)
     */
    public int getNumPoints() {
        long shapeVa = va();
        int result = getNumPoints(shapeVa);

        return result;
    }

    /**
     * Copy the location of the specified vertex of the convex hull relative to
     * its center of mass.
     *
     * @param pointIndex the index of the point to locate (&ge;0, &lt;numPoints)
     * @return a new vector
     */
    public Vec3 getPoint(int pointIndex) {
        long shapeVa = va();
        float x = getPointX(shapeVa, pointIndex);
        float y = getPointY(shapeVa, pointIndex);
        float z = getPointZ(shapeVa, pointIndex);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static int getNumPoints(long shapeVa);

    native private static float getPointX(long shapeVa, int pointIndex);

    native private static float getPointY(long shapeVa, int pointIndex);

    native private static float getPointZ(long shapeVa, int pointIndex);
}
