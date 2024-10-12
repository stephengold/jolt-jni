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
import com.github.stephengold.joltjni.readonly.RMat44Arg;
import com.github.stephengold.joltjni.readonly.RVec3Arg;
import com.github.stephengold.joltjni.readonly.Vec4Arg;

/**
 * A 4x4 matrix used to represent transformations of 3-D coordinates.
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

    /**
     * Instantiate from a single-precision matrix.
     *
     * @param spMatrix the matrix to copy (not null, unaffected)
     */
    public RMat44(Mat44Arg spMatrix) {
        long spMatrixVa = spMatrix.va();
        long matrixVa = createFromSpMatrix(spMatrixVa);
        setVirtualAddress(matrixVa, () -> free(matrixVa));
    }

    /**
     * Instantiate from 4 column vectors.
     *
     * @param c1 the desired first column (not null, unaffected)
     * @param c2 the desired 2nd column (not null, unaffected)
     * @param c3 the desired 3rd column (not null, unaffected)
     * @param c4 the desired 4th column (not null, unaffected)
     */
    public RMat44(Vec4Arg c1, Vec4Arg c2, Vec4Arg c3, RVec3Arg c4) {
        float[] floatArray = {
            c1.getX(), c2.getX(), c3.getX(),
            c1.getY(), c2.getY(), c3.getY(),
            c1.getZ(), c2.getZ(), c3.getZ(),
            c1.getW(), c2.getW(), c3.getW()
        };
        double m14 = c4.xx();
        double m24 = c4.yy();
        double m34 = c4.zz();
        // a44 is assumed to be 1.
        long matrixVa = createFromRowMajor(floatArray, m14, m24, m34);
        setVirtualAddress(matrixVa, () -> free(matrixVa));
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
     * Create a translation-and-rotation matrix.
     *
     * @param rotation the amount to rotate (not null, unaffected)
     * @param offset the amount to translate (not null, unaffected)
     * @return a new instance
     */
    public static RMat44 sRotationTranslation(
            QuatArg rotation, RVec3Arg offset) {
        float qw = rotation.getW();
        float qx = rotation.getX();
        float qy = rotation.getY();
        float qz = rotation.getZ();
        double xx = offset.xx();
        double yy = offset.yy();
        double zz = offset.zz();
        long matrixVa = createRotationTranslation(qx, qy, qz, qw, xx, yy, zz);
        RMat44 result = new RMat44(matrixVa, true);

        return result;
    }

    /**
     * Create a pure translation matrix.
     *
     * @param offset the amount to translate (not null, unaffected)
     * @return a new instance
     *
     */
    public static RMat44 sTranslation(RVec3Arg offset) {
        double xx = offset.xx();
        double yy = offset.yy();
        double zz = offset.zz();
        long matrixVa = createTranslation(xx, yy, zz);
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

    /**
     * Return the translation component. The matrix is unaffected.
     *
     * @return a new vector
     */
    @Override
    public RVec3 getTranslation() {
        long matrixVa = va();
        double xx = getTranslationX(matrixVa);
        double yy = getTranslationY(matrixVa);
        double zz = getTranslationZ(matrixVa);
        RVec3 result = new RVec3(xx, yy, zz);

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

    native private static long createFromRowMajor(
            float[] floatArray, double m14, double m24, double m34);

    native private static long createFromSpMatrix(long spMatrixVa);

    native private static long createIdentity();

    native private static long createRotationTranslation(float qx, float qy,
            float qz, float qw, double tx, double ty, double tz);

    native private static long createTranslation(
            double xx, double yy, double zz);

    native private static long createUninitialized();

    native private static long createZero();

    native private static void free(long matrixVa);

    native private static double getElement(long matrixVa, int row, int column);

    native private static double getTranslationX(long matrixVa);

    native private static double getTranslationY(long matrixVa);

    native private static double getTranslationZ(long matrixVa);

    native private static void setElement(
            long matrixVa, int row, int column, double value);
}
