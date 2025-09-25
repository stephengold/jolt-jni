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

/**
 * A {@code Shape} to represent a Y-axis conical frustum.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class TaperedCylinderShape extends ConvexShape {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a shape with the specified native object assigned.
     *
     * @param shapeVa the virtual address of the native object to assign (not
     * zero)
     */
    TaperedCylinderShape(long shapeVa) {
        setVirtualAddressAsCoOwner(shapeVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the bottom radius. The shape is unaffected.
     *
     * @return the bottom radius of the tapered cylinder (&ge;convexRadius)
     */
    public float getBottomRadius() {
        long shapeVa = va();
        float result = getBottomRadius(shapeVa);

        return result;
    }

    /**
     * Return the convex radius. The shape is unaffected.
     *
     * @return the convex radius (&ge;0)
     */
    public float getConvexRadius() {
        long shapeVa = va();
        float result = getConvexRadius(shapeVa);

        return result;
    }

    /**
     * Return the half height. The shape is unaffected.
     *
     * @return half the height
     */
    public float getHalfHeight() {
        long shapeVa = va();
        float result = getHalfHeight(shapeVa);

        return result;
    }

    /**
     * Return the top radius. The shape is unaffected.
     *
     * @return the top radius (&ge;convexRadius)
     */
    public float getTopRadius() {
        long shapeVa = va();
        float result = getTopRadius(shapeVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static float getBottomRadius(long shapeVa);

    native private static float getConvexRadius(long shapeVa);

    native private static float getHalfHeight(long shapeVa);

    native private static float getTopRadius(long shapeVa);
}
