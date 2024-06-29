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
 * A {@code Shape} to represent centered, axis-aligned rectangular solids.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class BoxShape extends ConvexShape {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a shape with the specified native object assigned.
     *
     * @param virtualAddress the virtual address of the native object to assign
     * (not zero)
     */
    BoxShape(long virtualAddress) {
        super(virtualAddress);
    }

    /**
     * Instantiate a shape with the specified half extents.
     *
     * @param halfExtents the desired half extents on each local axis (not null,
     * all components &ge;0, unaffected)
     */
    public BoxShape(Vec3Arg halfExtents) {
        float xHalfExtent = halfExtents.getX();
        float yHalfExtent = halfExtents.getY();
        float zHalfExtent = halfExtents.getZ();
        long shapeVa = createBoxShape(xHalfExtent, yHalfExtent, zHalfExtent);
        setVirtualAddress(shapeVa, true);
    }
    // *************************************************************************
    // native private methods

    native private static long createBoxShape(
            float xHalfExtent, float yHalfExtent, float zHalfExtent);
}
