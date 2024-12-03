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

import com.github.stephengold.joltjni.readonly.ConstPlane;

/**
 * A {@code Shape} to represent a surface defined by a plane.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class PlaneShape extends Shape {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a shape with the specified native object assigned but not
     * owned.
     *
     * @param shapeVa the virtual address of the native object to assign (not
     * zero)
     */
    PlaneShape(long shapeVa) {
        super(shapeVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Copy the shape's surface. The shape is unaffected.
     *
     * @return a new object
     */
    public ConstPlane getPlane() {
        long shapeVa = va();
        float c = getPlaneConstant(shapeVa);
        float nx = getPlaneX(shapeVa);
        float ny = getPlaneY(shapeVa);
        float nz = getPlaneZ(shapeVa);
        ConstPlane result = new Plane(nx, ny, nz, c);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static float getPlaneConstant(long shapeVa);

    native private static float getPlaneX(long shapeVa);

    native private static float getPlaneY(long shapeVa);

    native private static float getPlaneZ(long shapeVa);
}
