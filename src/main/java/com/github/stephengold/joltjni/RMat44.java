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

import com.github.stephengold.joltjni.readonly.RMat44Arg;

/**
 * A 4x4 matrix composed of 16 {@code Real} elements, used to represent
 * transformations of 3-D coordinates.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class RMat44 extends JoltPhysicsObject implements RMat44Arg {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an uninitialized matrix.
     */
    public RMat44() {
        long matrixVa = createUninitialized();
        setVirtualAddress(matrixVa, () -> free(matrixVa));
    }

    /**
     * Instantiate a matrix with the specified native object assigned.
     *
     * @param matrixVa the virtual address of the native object to assign (not
     * zero)
     * @param owner true &rarr; make the current object the owner, false &rarr;
     * the current object isn't the owner
     */
    RMat44(long matrixVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(matrixVa) : null;
        setVirtualAddress(matrixVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Alter the specified element in double precision.
     *
     * @param row the zero-origin index of the row (&ge;0, &lt;4)
     * @param column the zero-origin index of the column (&ge;0, &lt;4)
     * @param value the desired value
     */
    public void setElement(int row, int column, double value) {
        long matrixVa = va();
        setElement(matrixVa, row, column, value);
    }

    /**
     * Create an identity matrix.
     *
     * @return a new instance
     */
    public static RMat44 sIdentity() {
        long matrixVa = createIdentity();
        RMat44 result = new RMat44(matrixVa, true);

        return result;
    }

    /**
     * Create an all-zero matrix.
     *
     * @return a new instance
     */
    public static RMat44 sZero() {
        long matrixVa = createZero();
        RMat44 result = new RMat44(matrixVa, true);

        return result;
    }
    // *************************************************************************
    // RMat44Arg methods

    /**
     * Return the specified element in double precision. The matrix is
     * unaffected.
     *
     * @param row the zero-origin index of the row (&ge;0, &lt;4)
     * @param column the zero-origin index of the column (&ge;0, &lt;4)
     * @return the element's value
     */
    @Override
    public double getElement(int row, int column) {
        long matrixVa = va();
        double result = getElement(matrixVa, row, column);

        return result;
    }
    // *************************************************************************
    // Object methods

    /**
     * Return a string representation of the matrix, which is unaffected. For
     * example, an identity matrix is represented by:
     * <pre>
     * RMat44[
     *  1.0  0.0  0.0  0.0
     *  0.0  1.0  0.0  0.0
     *  0.0  0.0  1.0  0.0
     *  0.0  0.0  0.0  1.0
     * ]
     * </pre>
     *
     * @return the string representation (not null, not empty)
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("RMat44[");
        for (int rowIndex = 0; rowIndex < 4; ++rowIndex) {
            result.append("\n ");
            result.append(getElement(rowIndex, 0));
            result.append("  ");
            result.append(getElement(rowIndex, 1));
            result.append("  ");
            result.append(getElement(rowIndex, 2));
            result.append("  ");
            result.append(getElement(rowIndex, 3));
        }
        result.append("\n]");

        return result.toString();
    }
    // *************************************************************************
    // native private methods

    native private static long createIdentity();

    native private static long createUninitialized();

    native private static long createZero();

    native private static void free(long matrixVa);

    native private static double getElement(long matrixVa, int row, int column);

    native private static void setElement(
            long matrixVa, int row, int column, double value);
}
