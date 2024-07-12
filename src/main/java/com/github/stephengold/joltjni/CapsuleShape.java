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
 * A {@code Shape} to represent a right circular cylinder capped by 2
 * hemispheres.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class CapsuleShape extends ConvexShape {
    // *************************************************************************
    // constructors

    CapsuleShape(long shapeVa) {
        super(shapeVa);
    }

    /**
     * Instantiate a shape with the specified radius.
     *
     * @param halfHeight half the desired height of the cylindrical portion
     * @param radius the desired radius
     */
    public CapsuleShape(float halfHeight, float radius) {
        long shapeVa = createCapsuleShape(halfHeight, radius);
        setVirtualAddress(shapeVa, true);
    }
    // *************************************************************************
    // native private methods

    native private static long createCapsuleShape(
            float halfHeight, float radius);
}
