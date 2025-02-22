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
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import com.github.stephengold.joltjni.readonly.Vec4Arg;

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
     * @param elementArray in column-major order (not null)
     */
    public Mat44(float... elementArray) {
        long matrixVa = createFromColumnMajor(elementArray);
        setVirtualAddress(matrixVa, () -> free(matrixVa));
    }

    /**
     * Instantiate with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param matrixVa the virtual address of the native object to assign (not
     * zero)
     */
    Mat44(JoltPhysicsObject container, long matrixVa) {
        super(container, matrixVa);
    }

    /**
     * Instantiate a matrix with the specified native object assigned.
     *
     * @param matrixVa the virtual address of the native object to assign (not
     * zero)
     * @param owner {@code true} &rarr; make the JVM object the owner,
     * {@code false} &rarr; it isn't the owner
     */
    Mat44(long matrixVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(matrixVa) : null;
        setVirtualAddress(matrixVa, freeingAction);
    }

    /**
     * Instantiate a copy of the specified matrix.
     *
     * @param original the matrix to duplicate (not null, unaffected)
     */
    public Mat44(Mat44Arg original) {
        long originalVa = original.targetVa();
        long matrixVa = createCopy(originalVa);
        setVirtualAddress(matrixVa, () -> free(matrixVa));
    }

    /**
     * Instantiate from a location-transform matrix.
     *
     * @param rMatrix the matrix to copy (not null, unaffected)
     */
    public Mat44(RMat44Arg rMatrix) {
        long rMatrixVa = rMatrix.targetVa();
        long matrixVa = createFromRMatrix(rMatrixVa);
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
    public Mat44(Vec4Arg c1, Vec4Arg c2, Vec4Arg c3, Vec4Arg c4) {
        this(c1.getX(), c1.getY(), c1.getZ(), c1.getW(),
                c2.getX(), c2.getY(), c2.getZ(), c2.getW(),
                c3.getX(), c3.getY(), c3.getZ(), c3.getW(),
                c4.getX(), c4.getY(), c4.getZ(), c4.getW());
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Set the current matrix to identity.
     */
    public void loadIdentity() {
        long matrixVa = va();
        loadIdentity(matrixVa);
    }

    /**
     * Return the product of the specified matrices.
     *
     * @param mArray an array of input matrices (not null, unaffected)
     * @return a new matrix
     */
    public static Mat44 product(Mat44Arg... mArray) {
        Mat44 result = Mat44.sIdentity();
        for (Mat44Arg arg : mArray) {
            result.multiply(arg);
        }

        return result;
    }

    /**
     * Copy all elements of the argument to the current matrix.
     *
     * @param source the matrix to copy (not null, unaffected)
     */
    public void set(Mat44Arg source) {
        long targetVa = va();
        long sourceVa = source.targetVa();
        assign(targetVa, sourceVa);
    }

    /**
     * Set the first column to the specified vector.
     *
     * @param vec the vector to use (not null, unaffected)
     */
    public void setAxisX(Vec3Arg vec) {
        long matrixVa = va();
        float x = vec.getX();
        float y = vec.getY();
        float z = vec.getZ();
        setAxisX(matrixVa, x, y, z);
    }

    /**
     * Set the 2nd column to the specified vector.
     *
     * @param vec the vector to use (not null, unaffected)
     */
    public void setAxisY(Vec3Arg vec) {
        long matrixVa = va();
        float x = vec.getX();
        float y = vec.getY();
        float z = vec.getZ();
        setAxisY(matrixVa, x, y, z);
    }

    /**
     * Set the 3rd column to the specified vector.
     *
     * @param vec the vector to use (not null, unaffected)
     */
    public void setAxisZ(Vec3Arg vec) {
        long matrixVa = va();
        float x = vec.getX();
        float y = vec.getY();
        float z = vec.getZ();
        setAxisZ(matrixVa, x, y, z);
    }

    /**
     * Set the diagonal to the specified vector.
     *
     * @param vec the vector to use (not null, unaffected)
     */
    public void setDiagonal3(Vec3Arg vec) {
        long matrixVa = va();
        float x = vec.getX();
        float y = vec.getY();
        float z = vec.getZ();
        setDiagonal3(matrixVa, x, y, z);
    }

    /**
     * Alter the specified element.
     *
     * @param row the zero-origin index of the row (&ge;0, &lt;4)
     * @param column the zero-origin index of the column (&ge;0, &lt;4)
     * @param value the desired value
     */
    public void setElement(int row, int column, float value) {
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
    public void setTranslation(Vec3Arg offset) {
        long matrixVa = va();
        float x = offset.getX();
        float y = offset.getY();
        float z = offset.getZ();
        setTranslation(matrixVa, x, y, z);
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
     * Create a rotation matrix from the specified quaternion.
     *
     * @param rotation the rotation quaternion to use (not null, unaffected)
     * @return a new object
     */
    public static Mat44 sRotation(QuatArg rotation) {
        float rw = rotation.getW();
        float rx = rotation.getX();
        float ry = rotation.getY();
        float rz = rotation.getZ();
        long matrixVa = createRotation(rx, ry, rz, rw);
        Mat44 result = new Mat44(matrixVa, true);

        return result;
    }

    /**
     * Create a matrix for the specified rotation.
     *
     * @param axis the rotation axis (not null, unaffected)
     * @param angle the desired rotation angle (in radians)
     * @return a new instance
     */
    public static Mat44 sRotation(Vec3Arg axis, float angle) {
        float ax = axis.getX();
        float ay = axis.getY();
        float az = axis.getZ();
        long matrixVa = createRotationAxisAngle(ax, ay, az, angle);
        Mat44 result = new Mat44(matrixVa, true);

        return result;
    }

    /**
     * Create a translation-and-rotation matrix.
     *
     * @param rotation the amount to rotate (not null, unaffected)
     * @param offset the amount to translate (not null, unaffected)
     * @return a new object
     */
    public static Mat44 sRotationTranslation(QuatArg rotation, Vec3Arg offset) {
        float[] floatArray = new float[7];
        floatArray[0] = rotation.getX();
        floatArray[1] = rotation.getY();
        floatArray[2] = rotation.getZ();
        floatArray[3] = rotation.getW();
        floatArray[4] = offset.getX();
        floatArray[5] = offset.getY();
        floatArray[6] = offset.getZ();
        long matrixVa = createRotationTranslation(floatArray);
        Mat44 result = new Mat44(matrixVa, true);

        return result;
    }

    /**
     * Create a matrix for the specified X-axis rotation.
     *
     * @param angle the rotation angle (in radians)
     * @return a new instance
     */
    public static Mat44 sRotationX(float angle) {
        long matrixVa = createRotationX(angle);
        Mat44 result = new Mat44(matrixVa, true);

        return result;
    }

    /**
     * Create a matrix for the specified Y-axis rotation.
     *
     * @param angle the rotation angle (in radians)
     * @return a new instance
     */
    public static Mat44 sRotationY(float angle) {
        long matrixVa = createRotationY(angle);
        Mat44 result = new Mat44(matrixVa, true);

        return result;
    }

    /**
     * Create a matrix for the specified Z-axis rotation.
     *
     * @param angle the rotation angle (in radians)
     * @return a new instance
     */
    public static Mat44 sRotationZ(float angle) {
        long matrixVa = createRotationZ(angle);
        Mat44 result = new Mat44(matrixVa, true);

        return result;
    }

    /**
     * Create a uniform scaling matrix.
     *
     * @param factor the amount to scale each axis
     * @return a new instance
     *
     */
    public static Mat44 sScale(float factor) {
        long matrixVa = createScale(factor, factor, factor);
        Mat44 result = new Mat44(matrixVa, true);

        return result;
    }

    /**
     * Create a pure scaling matrix.
     *
     * @param factors the amount to scale each axis (not null, unaffected)
     * @return a new instance
     *
     */
    public static Mat44 sScale(Vec3Arg factors) {
        float sx = factors.getX();
        float sy = factors.getY();
        float sz = factors.getZ();
        long matrixVa = createScale(sx, sy, sz);
        Mat44 result = new Mat44(matrixVa, true);

        return result;
    }

    /**
     * Create a pure translation matrix.
     *
     * @param offset the amount to translate (not null, unaffected)
     * @return a new instance
     *
     */
    public static Mat44 sTranslation(Vec3Arg offset) {
        float x = offset.getX();
        float y = offset.getY();
        float z = offset.getZ();
        long matrixVa = createTranslation(x, y, z);
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
     * Copy the first column to a {@code Vec3}. The matrix is unaffected.
     *
     * @return a new vector
     */
    @Override
    public Vec3 getAxisX() {
        long matrixVa = va();
        float x = getElement(matrixVa, 0, 0);
        float y = getElement(matrixVa, 1, 0);
        float z = getElement(matrixVa, 2, 0);
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
        float x = getElement(matrixVa, 0, 1);
        float y = getElement(matrixVa, 1, 1);
        float z = getElement(matrixVa, 2, 1);
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
        float x = getElement(matrixVa, 0, 2);
        float y = getElement(matrixVa, 1, 2);
        float z = getElement(matrixVa, 2, 2);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Copy the diagonal elements to a {@code Vec3}. The matrix is unaffected.
     *
     * @return a new vector
     */
    @Override
    public Vec3 getDiagonal3() {
        long matrixVa = va();
        float x = getElement(matrixVa, 0, 0);
        float y = getElement(matrixVa, 1, 1);
        float z = getElement(matrixVa, 2, 2);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Return the specified element. The matrix is unaffected.
     *
     * @param row the zero-origin index of the row (&ge;0, &lt;4)
     * @param column the zero-origin index of the column (&ge;0, &lt;4)
     * @return the element's value
     */
    @Override
    public float getElement(int row, int column) {
        assert column >= 0 && column < 4 : "column = " + column;
        assert row >= 0 && row < 4 : "row = " + row;

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
    public Vec3 getTranslation() {
        long matrixVa = va();
        float x = getTranslationX(matrixVa);
        float y = getTranslationY(matrixVa);
        float z = getTranslationZ(matrixVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Return the inverse of the current matrix, which is unaffected.
     *
     * @return a new matrix
     */
    @Override
    public Mat44 inversed() {
        long currentVa = va();
        long resultVa = inversed(currentVa);
        Mat44 result = new Mat44(resultVa, true);

        return result;
    }

    /**
     * Return the inverse of the 3x3 portion. The current matrix is unaffected.
     *
     * @return a new matrix
     */
    @Override
    public Mat44 inversed3x3() {
        long currentVa = va();
        long resultVa = inversed3x3(currentVa);
        Mat44 result = new Mat44(resultVa, true);

        return result;
    }

    /**
     * Return the inverse, assuming the current matrix consists entirely of
     * rotation and translation.
     *
     * @return a new matrix
     */
    @Override
    public Mat44 inversedRotationTranslation() {
        long currentVa = va();
        long resultVa = inversedRotationTranslation(currentVa);
        Mat44 result = new Mat44(resultVa, true);

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
    public boolean isEqual(Mat44Arg m2) {
        long m1Va = va();
        long m2Va = m2.targetVa();
        boolean result = equals(m1Va, m2Va);

        return result;
    }

    /**
     * Test whether the current matrix is an identity matrix. The matrix is
     * unaffected.
     *
     * @return {@code true} if exactly equal, otherwise {@code false}
     */
    @Override
    public boolean isIdentity() {
        long matrixVa = va();
        boolean result = isIdentity(matrixVa);

        return result;
    }

    /**
     * Multiply the current matrix by the argument. The current matrix is
     * unaffected.
     *
     * @param right the right factor (not null, unaffected)
     * @return a new matrix
     */
    @Override
    public Mat44 multiply(Mat44Arg right) {
        long leftVa = va();
        long rightVa = right.targetVa();
        long resultVa = multiply(leftVa, rightVa);
        Mat44 result = new Mat44(resultVa, true);

        return result;
    }

    /**
     * Multiply the current 3x3 matrix by the specified 3x3 matrix. The current
     * matrix is unaffected.
     *
     * @param right the right factor (not null, unaffected)
     * @return a new matrix
     */
    @Override
    public Mat44 multiply3x3(Mat44Arg right) {
        long leftVa = va();
        long rightVa = right.targetVa();
        long resultVa = multiply3x3(leftVa, rightVa);
        Mat44 result = new Mat44(resultVa, true);

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
     * @param vec3Arg the right factor (not null, unaffected)
     * @return a new vector
     */
    @Override
    public Vec3 multiply3x4(Vec3Arg vec3Arg) {
        long matrixVa = va();
        float[] tmpFloats = vec3Arg.toArray();
        multiply3x4(matrixVa, tmpFloats);
        Vec3 result = new Vec3(tmpFloats);

        return result;
    }

    /**
     * Post multiply by the specified translation vector. The current matrix is
     * unaffected.
     *
     * @param vec3 the left factor (not null, unaffected)
     * @return a new matrix
     */
    @Override
    public Mat44 postTranslated(Vec3Arg vec3) {
        long matrixVa = va();
        float x = vec3.getX();
        float y = vec3.getY();
        float z = vec3.getZ();
        long resultVa = postTranslated(matrixVa, x, y, z);
        Mat44 result = new Mat44(resultVa, true);

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

    native private static void assign(long targetVa, long sourceVa);

    native private static long createCopy(long originalVa);

    native private static long createFromColumnMajor(float[] elementsArray);

    native private static long createFromRMatrix(long rMatrixVa);

    native private static long createIdentity();

    native private static long createRotation(
            float rx, float ry, float rz, float rw);

    native private static long createRotationAxisAngle(
            float ax, float ay, float az, float angle);

    native private static long createRotationTranslation(float[] floatArray);

    native private static long createRotationX(float angle);

    native private static long createRotationY(float angle);

    native private static long createRotationZ(float angle);

    native private static long createScale(float sx, float sy, float sz);

    native private static long createTranslation(float x, float y, float z);

    native private static long createUninitialized();

    native private static long createZero();

    native private static boolean equals(long m1Va, long m2Va);

    native private static void free(long matrixVa);

    native private static float getElement(long matrixVa, int row, int column);

    native private static void getQuaternion(
            long matrixVa, float[] storeFloats);

    native private static float getTranslationX(long matrixVa);

    native private static float getTranslationY(long matrixVa);

    native private static float getTranslationZ(long matrixVa);

    native private static long inversed(long currentVa);

    native private static long inversed3x3(long currentVa);

    native private static long inversedRotationTranslation(long currentVa);

    native private static boolean isIdentity(long matrixVa);

    native private static void loadIdentity(long matrixVa);

    native private static long multiply(long leftVa, long rightVa);

    native private static long multiply3x3(long leftVa, long rightVa);

    native private static void multiply3x3(long matrixVa, float[] tmpFloats);

    native private static void multiply3x3Transposed(
            long matrixVa, float[] tmpFloats);

    native private static void multiply3x4(long matrixVa, float[] tmpFloats);

    native private static long postTranslated(
            long matrixVa, float x, float y, float z);

    native private static void setAxisX(
            long matrixVa, float x, float y, float z);

    native private static void setAxisY(
            long matrixVa, float x, float y, float z);

    native private static void setAxisZ(
            long matrixVa, float x, float y, float z);

    native private static void setDiagonal3(
            long matrixVa, float x, float y, float z);

    native private static void setElement(
            long matrixVa, int row, int column, float value);

    native private static void setTranslation(
            long matrixVa, float x, float y, float z);
}
