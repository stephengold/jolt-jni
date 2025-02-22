/*
Copyright (c) 2024-2025 Stephen Gold

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
import com.github.stephengold.joltjni.readonly.Vec3Arg;
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
     * @param owner {@code true} &rarr; make the JVM object the owner,
     * {@code false} &rarr; it isn't the owner
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
        long spMatrixVa = spMatrix.targetVa();
        long matrixVa = createFromSpMatrix(spMatrixVa);
        setVirtualAddress(matrixVa, () -> free(matrixVa));
    }

    /**
     * Instantiate a matrix with the specified columns.
     *
     * @param c1 the desired first/leftmost column (not null, unaffected)
     * @param c2 the desired 2nd column (not null, unaffected)
     * @param c3 the desired 3rd column (not null, unaffected)
     * @param c4 the desired 4th/rightmost column (not null, unaffected)
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
     * Copy all elements of the argument to the current matrix.
     *
     * @param source the matrix to copy (not null, unaffected)
     */
    public void set(RMat44Arg source) {
        long targetVa = va();
        long sourceVa = source.targetVa();
        assign(targetVa, sourceVa);
    }

    /**
     * Alter the specified element in double precision.
     *
     * @param row the zero-origin index of the row (&ge;0, &lt;4)
     * @param column the zero-origin index of the column (&ge;0, &lt;4)
     * @param value the desired value
     */
    public void setElement(int row, int column, double value) {
        assert column >= 0 && column < 4 : "column = " + column;
        assert row >= 0 && row < 4 : "row = " + row;

        long matrixVa = va();
        setElement(matrixVa, row, column, value);
    }

    /**
     * Alter the translation component.
     *
     * @param offset the desired translation (not null, unaffected)
     */
    public void setTranslation(RVec3Arg offset) {
        long matrixVa = va();
        double xx = offset.xx();
        double yy = offset.yy();
        double zz = offset.zz();
        setTranslation(matrixVa, xx, yy, zz);
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
     * Create a rotation matrix from the specified quaternion.
     *
     * @param rotation the rotation quaternion to use (not null, unaffected)
     * @return a new object
     */
    public static RMat44 sRotation(QuatArg rotation) {
        float rw = rotation.getW();
        float rx = rotation.getX();
        float ry = rotation.getY();
        float rz = rotation.getZ();
        long matrixVa = createRotation(rx, ry, rz, rw);
        RMat44 result = new RMat44(matrixVa, true);

        return result;
    }

    /**
     * Create a translation-and-rotation matrix.
     *
     * @param rotation the amount to rotate (not null, unaffected)
     * @param offset the amount to translate (not null, unaffected)
     * @return a new object
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
     * Copy the first column to a {@code Vec3}. The matrix is unaffected.
     *
     * @return a new vector
     */
    @Override
    public Vec3 getAxisX() {
        long matrixVa = va();
        float x = (float) getElement(matrixVa, 0, 0);
        float y = (float) getElement(matrixVa, 1, 0);
        float z = (float) getElement(matrixVa, 2, 0);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Copy the 2nd column to a {@code Vec3}. The matrix is unaffected.
     *
     * @return a new vector
     */
    @Override
    public Vec3 getAxisY() {
        long matrixVa = va();
        float x = (float) getElement(matrixVa, 0, 1);
        float y = (float) getElement(matrixVa, 1, 1);
        float z = (float) getElement(matrixVa, 2, 1);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Copy the 3rd column to a {@code Vec3}. The matrix is unaffected.
     *
     * @return a new vector
     */
    @Override
    public Vec3 getAxisZ() {
        long matrixVa = va();
        float x = (float) getElement(matrixVa, 0, 2);
        float y = (float) getElement(matrixVa, 1, 2);
        float z = (float) getElement(matrixVa, 2, 2);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

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
        assert column >= 0 && column < 4 : "column = " + column;
        assert row >= 0 && row < 4 : "row = " + row;

        long matrixVa = va();
        double result = getElement(matrixVa, row, column);

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
        float[] storeFloats = new float[4];
        getQuaternion(matrixVa, storeFloats);
        Quat result = new Quat(
                storeFloats[0], storeFloats[1], storeFloats[2], storeFloats[3]);

        return result;
    }

    /**
     * Copy the translation component. The matrix is unaffected.
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

    /**
     * Return the inverse of the current matrix, which is unaffected.
     *
     * @return a new matrix
     */
    @Override
    public RMat44 inversed() {
        long currentVa = va();
        long resultVa = inversed(currentVa);
        RMat44 result = new RMat44(resultVa, true);

        return result;
    }

    /**
     * Return the inverse of the current matrix, assuming the current matrix
     * consists entirely of rotation and translation. The current matrix is
     * unaffected.
     *
     * @return a new matrix
     */
    public RMat44 inversedRotationTranslation() {
        long currentVa = va();
        long resultVa = inversedRotationTranslation(currentVa);
        RMat44 result = new RMat44(resultVa, true);

        return result;
    }

    /**
     * Test whether the current matrix is equal to the argument. The current
     * matrix is unaffected.
     *
     * @param m2 the 2nd matrix to test (not null, unaffected)
     * @return {@code true} if equal, {@code false} if unequal
     */
    @Override
    public boolean isEqual(RMat44Arg m2) {
        long m1Va = va();
        long m2Va = m2.targetVa();
        boolean result = equals(m1Va, m2Va);

        return result;
    }

    /**
     * Multiply the current matrix by the argument. The matrix is unaffected.
     *
     * @param m2 the right factor (not null, unaffected)
     * @return a new matrix
     */
    @Override
    public RMat44 multiply(RMat44Arg m2) {
        long m1Va = va();
        long m2Va = m2.targetVa();
        long productVa = multiply(m1Va, m2Va);
        RMat44 result = new RMat44(productVa, true);

        return result;
    }

    /**
     * Multiply the 3x3 matrix by the specified column vector. The matrix is
     * unaffected.
     *
     * @param vec3Arg the right factor (not null, unaffected)
     * @return a new vector
     */
    @Override
    public Vec3 multiply3x3(Vec3Arg vec3Arg) {
        long matrixVa = va();
        float[] tmpFloats = vec3Arg.toArray();
        multiply3x3(matrixVa, tmpFloats);
        Vec3 result = new Vec3(tmpFloats);

        return result;
    }

    /**
     * Multiply the transpose of the 3x3 matrix by the specified column vector.
     * The matrix is unaffected.
     *
     * @param vec3Arg the right factor (not null, unaffected)
     * @return a new vector
     */
    @Override
    public Vec3 multiply3x3Transposed(Vec3Arg vec3Arg) {
        long matrixVa = va();
        float[] tmpFloats = vec3Arg.toArray();
        multiply3x3Transposed(matrixVa, tmpFloats);
        Vec3 result = new Vec3(tmpFloats);

        return result;
    }

    /**
     * Multiply the 3x4 matrix by the specified column vector, with the 4th
     * component of the right factor implied to be one. The matrix is
     * unaffected.
     *
     * @param rvec3Arg the right factor (not null, unaffected)
     * @return a new vector
     */
    @Override
    public RVec3 multiply3x4(RVec3Arg rvec3Arg) {
        long matrixVa = va();
        double[] tmpDoubles = rvec3Arg.toArray();
        multiply3x4r(matrixVa, tmpDoubles);
        RVec3 result = new RVec3(tmpDoubles);

        return result;
    }

    /**
     * Multiply the 3x4 matrix by the specified column vector, with the 4th
     * component of the right factor implied to be one. The matrix is
     * unaffected.
     *
     * @param vec3Arg the right factor (not null, unaffected)
     * @return a new vector
     */
    @Override
    public RVec3 multiply3x4(Vec3Arg vec3Arg) {
        long matrixVa = va();
        float x = vec3Arg.getX();
        float y = vec3Arg.getY();
        float z = vec3Arg.getZ();
        double[] storeDoubles = new double[3];
        multiply3x4(matrixVa, x, y, z, storeDoubles);
        RVec3 result = new RVec3(storeDoubles);

        return result;
    }

    /**
     * Copy the current matrix to a new, single-precision matrix. The current
     * matrix is unaffected.
     *
     * @return the new matrix
     */
    public Mat44 toMat44() {
        Mat44 result = new Mat44(this);
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

    native private static void assign(long targetVa, long sourceVa);

    native private static long createFromRowMajor(
            float[] floatArray, double m14, double m24, double m34);

    native private static long createFromSpMatrix(long spMatrixVa);

    native private static long createIdentity();

    native private static long createRotation(
            float rx, float ry, float rz, float rw);

    native private static long createRotationTranslation(float qx, float qy,
            float qz, float qw, double tx, double ty, double tz);

    native private static long createTranslation(
            double xx, double yy, double zz);

    native private static long createUninitialized();

    native private static long createZero();

    native private static boolean equals(long m1Va, long m2Va);

    native private static void free(long matrixVa);

    native private static double getElement(long matrixVa, int row, int column);

    native private static void getQuaternion(
            long matrixVa, float[] storeFloats);

    native private static double getTranslationX(long matrixVa);

    native private static double getTranslationY(long matrixVa);

    native private static double getTranslationZ(long matrixVa);

    native private static long inversed(long currentVa);

    native private static long inversedRotationTranslation(long currentVa);

    native private static long multiply(long m1Va, long m2Va);

    native private static void multiply3x3(long matrixVa, float[] tmpFloats);

    native private static void multiply3x3Transposed(
            long matrixVa, float[] tmpFloats);

    native private static void multiply3x4(
            long matrixVa, float x, float y, float z, double[] storeDoubles);

    native private static void multiply3x4r(long matrixVa, double[] tmpDoubles);

    native private static void setElement(
            long matrixVa, int row, int column, double value);

    native private static void setTranslation(
            long matrixVa, double xx, double yy, double zz);
}
