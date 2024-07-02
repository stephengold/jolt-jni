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

import java.nio.FloatBuffer;

/**
 * The abstract base class for collision shapes.
 *
 * @author Stephen Gold sgold@sonic.net
 */
abstract public class Shape extends NonCopyable {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a shape with no native object assigned.
     */
    protected Shape() {
    }

    /**
     * Instantiate a shape with the specified native object assigned.
     *
     * @param virtualAddress the virtual address of the native object to assign
     * (not zero)
     */
    protected Shape(long virtualAddress) {
        super(virtualAddress);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Copy the vertex coordinates of the shape's debug mesh to the specified
     * buffer.
     *
     * @param storeBuffer the buffer to fill with vertex coordinates (not null,
     * modified)
     */
    public void copyDebugTriangles(FloatBuffer storeBuffer) {
        long shapeVa = va();
        int numTriangles = storeBuffer.capacity() / 9;
        copyDebugTriangles(shapeVa, numTriangles, storeBuffer);
    }

    /**
     * Return the number of triangles in the shape's debug mesh.
     *
     * @return the count (&gt;0)
     */
    public int countDebugTriangles() {
        long shapeVa = va();
        int result = countDebugTriangles(shapeVa);

        assert result > 0 : "result = " + result;
        return result;
    }
    /**
     * Return the shape's subtype.
     *
     * @return an enum value
     */
    public EShapeSubType getSubType() {
        long shapeVa = va();
        int ordinal = getSubType(shapeVa);
        EShapeSubType result = EShapeSubType.values()[ordinal];

        return result;
    }

    /**
     * Return the shape's type.
     *
     * @return an enum value
     */
    public EShapeType getType() {
        long shapeVa = va();
        int ordinal = getType(shapeVa);
        EShapeType result = EShapeType.values()[ordinal];

        return result;
    }

    /**
     * Test whether the shape can be used in a dynamic/kinematic body.
     *
     * @return true if it can be only be static, otherwise false
     */
    public boolean mustBeStatic() {
        long shapeVa = va();
        boolean result = mustBeStatic(shapeVa);
        return result;
    }
    // *************************************************************************
    // native private methods

    native private static void copyDebugTriangles(
            long shapeVa, int numTriangles, FloatBuffer storeBuffer);

    native private static int countDebugTriangles(long shapeVa);

    native private static int getSubType(long shapeVa);

    native private static int getType(long shapeVa);

    native private static boolean mustBeStatic(long shapeVa);
}
