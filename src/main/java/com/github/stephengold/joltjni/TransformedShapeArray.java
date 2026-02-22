/*
Copyright (c) 2026 Stephen Gold

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

import com.github.stephengold.joltjni.readonly.ConstTransformedShape;

/**
 * A fixed-length array of coordinate-transformed shapes. (native type:
 * {@code TransformedShape[]})
 *
 * @author xI-Mx-Ix
 */
public class TransformedShapeArray extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an array with the specified length.
     *
     * @param length the desired number of shapes (&ge;0)
     */
    public TransformedShapeArray(int length) {
        long arrayVa = create(length);
        setVirtualAddress(arrayVa, () -> free(arrayVa));
    }

    /**
     * Instantiate with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param arrayVa the virtual address of the native object to assign (not
     * zero)
     */
    TransformedShapeArray(JoltPhysicsObject container, long arrayVa) {
        super(container, arrayVa);
    }

    // *************************************************************************
    // new methods exposed

    /**
     * Access the shape at the specified index.
     *
     * @param elementIndex the index (&ge;0)
     * @return a new JVM object with the pre-existing native object assigned
     */
    public TransformedShape get(int elementIndex) {
        long arrayVa = va();
        long shapeVa = getShape(arrayVa, elementIndex);
        TransformedShape result = new TransformedShape(this, shapeVa);

        return result;
    }

    /**
     * Store the specified shape at the specified index.
     *
     * @param elementIndex the index at which to store the shape (&ge;0)
     * @param shape the shape to store (not null, unaffected)
     */
    public void set(int elementIndex, ConstTransformedShape shape) {
        long arrayVa = va();
        long shapeVa = shape.targetVa();
        setShape(arrayVa, elementIndex, shapeVa);
    }

    // *************************************************************************
    // native private methods

    native private static long create(int length);

    native private static void free(long arrayVa);

    native private static long getShape(long arrayVa, int elementIndex);

    native private static void setShape(
            long arrayVa, int elementIndex, long shapeVa);
}
