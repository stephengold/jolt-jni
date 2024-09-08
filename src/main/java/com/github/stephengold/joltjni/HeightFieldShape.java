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
 * A {@code Shape} to represent a surface defined by a matrix of heights.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class HeightFieldShape extends Shape {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a shape with the specified native object assigned but not
     * owned.
     *
     * @param shapeVa the virtual address of the native object to assign (not
     * zero)
     */
    HeightFieldShape(long shapeVa) {
        super(shapeVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the block size. The shape is unaffected.
     *
     * @return the number of height values in a block
     */
    public int getBlockSize() {
        long shapeVa = va();
        int result = getBlockSize(shapeVa);

        return result;
    }

    /**
     * Copy the location of specified sample. The shape is unaffected.
     *
     * @param x the X coordinate of the sample (&ge;0, &lt;sampleCount)
     * @param y the Y coordinate of the sample (&ge;0, &lt;sampleCount)
     * @return a new location vector
     */
    public Vec3 getPosition(int x, int y) {
        long shapeVa = va();
        float posX = getPositionX(shapeVa, x, y);
        float posY = getPositionY(shapeVa, x, y);
        float posZ = getPositionZ(shapeVa, x, y);
        Vec3 result = new Vec3(posX, posY, posZ);

        return result;
    }

    /**
     * Test whether the shape has a hole at the specified sample. The shape is
     * unaffected.
     *
     * @param x the X coordinate of the sample (&ge;0, &lt;sampleCount)
     * @param y the Y coordinate of the sample (&ge;0, &lt;sampleCount)
     * @return true if there's a hole, otherwise false
     */
    public boolean isNoCollision(int x, int y) {
        long shapeVa = va();
        boolean result = isNoCollision(shapeVa, x, y);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static int getBlockSize(long shapeVa);

    native private static float getPositionX(long shapeVa, int x, int y);

    native private static float getPositionY(long shapeVa, int x, int y);

    native private static float getPositionZ(long shapeVa, int x, int y);

    native private static boolean isNoCollision(long shapeVa, int x, int y);
}
