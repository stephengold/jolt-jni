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
 * A {@code Shape} to represent a right circular cylinder.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class CylinderShape extends ConvexShape {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a shape with the specified native object assigned but not
     * owned.
     *
     * @param shapeVa the virtual address of the native object to assign (not
     * zero)
     */
    CylinderShape(long shapeVa) {
        super(shapeVa);
    }

    /**
     * Instantiate a shape with the specified dimensions.
     *
     * @param halfHeight half the desired height
     * @param radius the desired radius
     */
    public CylinderShape(float halfHeight, float radius) {
        this(halfHeight, radius, PhysicsSettings.cDefaultConvexRadius);
    }

    /**
     * Instantiate a shape with the specified dimensions and convex radius.
     *
     * @param halfHeight half the desired height
     * @param radius the desired radius
     * @param convexRadius the desired convex radius (default=0.05)
     */
    public CylinderShape(float halfHeight, float radius, float convexRadius) {
        long shapeVa = createCylinderShape(halfHeight, radius, convexRadius);
        setVirtualAddress(shapeVa, null); // not the owner due to ref counting
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

    native private static long createCylinderShape(
            float halfHeight, float radius, float convexRadius);

    native private static float getConvexRadius(long shapeVa);
}
