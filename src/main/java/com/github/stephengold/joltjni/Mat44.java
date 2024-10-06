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

import com.github.stephengold.joltjni.readonly.Mat44Arg;
import com.github.stephengold.joltjni.readonly.QuatArg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * A 4x4 matrix composed of 16 single-precision elements, used to represent
 * transformations of 3-D coordinates.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class Mat44 extends JoltPhysicsObject implements Mat44Arg {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an uninitialized matrix.
     */
    public Mat44() {
        long matrixVa = createUninitialized();
        setVirtualAddress(matrixVa, () -> free(matrixVa));
    }

    /**
     * Instantiate a matrix with the specified elements.
     *
     * @param elements in column-major order (not null)
     */
    public Mat44(float... elements) {
        long matrixVa = createFromColumnMajor(elements);
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
    Mat44(long matrixVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(matrixVa) : null;
        setVirtualAddress(matrixVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Alter the specified element.
     *
     * @param row the zero-origin index of the row (&ge;0, &lt;4)
     * @param column the zero-origin index of the column (&ge;0, &lt;4)
     * @param value the desired value
     */
    public void setElement(int row, int column, float value) {
        long matrixVa = va();
        setElement(matrixVa, row, column, value);
    }

    /**
     * Create an identity matrix.
     *
     * @return a new instance
     */
    public static Mat44 sIdentity() {
        long matrixVa = createIdentity();
        Mat44 result = new Mat44(matrixVa, true);

        return result;
    }

    /**
     * Create a matrix from the specified quaternion.
     *
     * @param rotation the rotation quaternion to use (not null, unaffected)
     * @return a new instance
     */
    public static Mat44 sRotation(QuatArg rotation) {
        float rw = rotation.getW();
        float rx = rotation.getX();
        float ry = rotation.getY();
        float rz = rotation.getZ();
        long matrixVa = sRotation(rx, ry, rz, rw);
        Mat44 result = new Mat44(matrixVa, true);

        return result;
    }

    /**
     * Create an all-zero matrix.
     *
     * @return a new instance
     */
    public static Mat44 sZero() {
        long matrixVa = createZero();
        Mat44 result = new Mat44(matrixVa, true);

        return result;
    }
    // *************************************************************************
    // Mat44Arg methods

    /**
     * Return the specified element. The matrix is unaffected.
     *
     * @param row the zero-origin index of the row (&ge;0, &lt;4)
     * @param column the zero-origin index of the column (&ge;0, &lt;4)
     * @return the element's value
     */
    @Override
    public float getElement(int row, int column) {
        long matrixVa = va();
        float result = getElement(matrixVa, row, column);

        return result;
    }

    /**
     * Convert the rotation to a {@code Quat}. The matrix is unaffected.
     *
     * @return a new rotation quaternion
     */
    @Override
    public Quat getQuaternion() {
        long matrixVa = va();
        float[] storeArray = new float[4];
        getQuaternion(matrixVa, storeArray);
        Quat result = new Quat(
                storeArray[0], storeArray[1], storeArray[2], storeArray[3]);

        return result;
    }

    /**
     * Test whether the current matrix is equal to the argument. The current
     * matrix is unaffected.
     *
     * @param m2 the 2nd matrix to test (not null, unaffected)
     * @return true if equal, false if unequal
     */
    @Override
    public boolean isEqual(Mat44Arg m2) {
        long m1Va = va();
        long m2Va = m2.va();
        boolean result = equals(m1Va, m2Va);

        return result;
    }

    /**
     * Multiply the current 3x3 matrix by the specified 3x3 matrix. The matrix
     * is unaffected.
     *
     * @param arg the factor (not null, unaffected)
     * @return a new matrix
     */
    @Override
    public Mat44 multiply3x3(Mat44Arg arg) {
        long currentVa = va();
        long argVa = arg.va();
        long productVa = multiply3x3(currentVa, argVa);
        Mat44 result = new Mat44(productVa, true);

        return result;
    }

    /**
     * Multiply the 3x3 matrix by the specified vector. The matrix is
     * unaffected.
     *
     * @param vec3Arg the right factor (not null, unaffected)
     * @return a new vector
     */
    @Override
    public Vec3 multiply3x3(Vec3Arg vec3Arg) {
        long matrixVa = va();
        float[] tmpArray = vec3Arg.toArray();
        multiply3x3(matrixVa, tmpArray);
        Vec3 result = new Vec3(tmpArray);

        return result;
    }

    /**
     * Multiply the transpose of the 3x3 matrix by the specified vector. The
     * matrix is unaffected.
     *
     * @param vec3Arg the right factor (not null, unaffected)
     * @return a new vector
     */
    @Override
    public Vec3 multiply3x3Transposed(Vec3Arg vec3Arg) {
        long matrixVa = va();
        float[] tmpArray = vec3Arg.toArray();
        multiply3x3Transposed(matrixVa, tmpArray);
        Vec3 result = new Vec3(tmpArray);

        return result;
    }
    // *************************************************************************
    // Object methods

    /**
     * Return a string representation of the matrix, which is unaffected. For
     * example, an identity matrix is represented by:
     * <pre>
     * Mat44[
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
        StringBuilder result = new StringBuilder("Mat44[");
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

    native private static long createFromColumnMajor(float[] elements);

    native private static long createIdentity();

    native private static long createUninitialized();

    native private static long createZero();

    native private static boolean equals(long m1va, long m2va);

    native private static void free(long matrixVa);

    native private static float getElement(long matrixVa, int row, int column);

    native private static void getQuaternion(
            long matrixVa, float[] storeArray);

    native private static long multiply3x3(long currentVa, long argVa);

    native private static void multiply3x3(long matrixVa, float[] array);

    native private static void multiply3x3Transposed(
            long matrixVa, float[] array);

    native private static void setElement(
            long matrixVa, int row, int column, float value);

    native private static long sRotation(float x, float y, float z, float w);
}
