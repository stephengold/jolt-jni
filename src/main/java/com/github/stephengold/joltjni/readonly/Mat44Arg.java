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
package com.github.stephengold.joltjni.readonly;

import com.github.stephengold.joltjni.Mat44;
import com.github.stephengold.joltjni.Quat;
import com.github.stephengold.joltjni.Vec3;
import java.nio.FloatBuffer;

/**
 * Read-only access to a {@code Mat44}. (native type: const Mat44)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface Mat44Arg extends ConstJoltPhysicsObject {
    /**
     * Copy the first column to a {@code Vec3}. The matrix is unaffected.
     *
     * @return a new vector
     */
    Vec3 getAxisX();

    /**
     * Copy the 2nd column to a {@code Vec3}. The matrix is unaffected.
     *
     * @return a new vector
     */
    Vec3 getAxisY();

    /**
     * Copy the 3rd column to a {@code Vec3}. The matrix is unaffected.
     *
     * @return a new vector
     */
    Vec3 getAxisZ();

    /**
     * Copy the diagonal elements to a {@code Vec3}. The matrix is unaffected.
     *
     * @return a new vector
     */
    Vec3 getDiagonal3();

    /**
     * Return the specified element. The matrix is unaffected.
     *
     * @param row the zero-origin index of the row (&ge;0, &lt;4)
     * @param column the zero-origin index of the column (&ge;0, &lt;4)
     * @return the element's value
     */
    float getElement(int row, int column);

    /**
     * Convert the rotation to a {@code Quat}. The matrix is unaffected.
     *
     * @return a new rotation quaternion
     */
    Quat getQuaternion();

    /**
     * Copy the translation component. The matrix is unaffected.
     *
     * @return a new vector
     */
    Vec3 getTranslation();

    /**
     * Return the inverse of the current matrix, which is unaffected.
     *
     * @return a new matrix
     */
    Mat44 inversed();

    /**
     * Return the inverse of the 3x3 portion. The current matrix is unaffected.
     *
     * @return a new matrix
     */
    Mat44 inversed3x3();

    /**
     * Return the inverse of the current matrix, assuming the current matrix
     * consists entirely of rotation and translation. The current matrix is
     * unaffected.
     *
     * @return a new matrix
     */
    Mat44 inversedRotationTranslation();

    /**
     * Test whether the current matrix is equal to the argument. The current
     * matrix is unaffected.
     *
     * @param m2 the 2nd matrix to test (not null, unaffected)
     * @return {@code true} if equal, {@code false} if unequal
     */
    boolean isEqual(Mat44Arg m2);

    /**
     * Test whether the current matrix is an identity matrix. The matrix is
     * unaffected.
     *
     * @return {@code true} if exactly equal, otherwise {@code false}
     */
    boolean isIdentity();

    /**
     * Multiply the current matrix by the argument. The current matrix is
     * unaffected.
     *
     * @param right the right factor (not null, unaffected)
     * @return a new matrix
     */
    Mat44 multiply(Mat44Arg right);

    /**
     * Multiply the current 3x3 matrix by the specified 3x3 matrix. The current
     * matrix is unaffected.
     *
     * @param right the right factor (not null, unaffected)
     * @return a new matrix
     */
    Mat44 multiply3x3(Mat44Arg right);

    /**
     * Multiply the 3x3 matrix by the specified column vector. The matrix is
     * unaffected.
     *
     * @param vec3Arg the right factor (not null, unaffected)
     * @return a new vector
     */
    Vec3 multiply3x3(Vec3Arg vec3Arg);

    /**
     * Multiply the transpose of the 3x3 matrix by the specified column vector.
     * The matrix is unaffected.
     *
     * @param vec3Arg the right factor (not null, unaffected)
     * @return a new vector
     */
    Vec3 multiply3x3Transposed(Vec3Arg vec3Arg);

    /**
     * Multiply the 3x4 matrix by the specified column vector, with the 4th
     * component of the right factor implied to be one. The matrix is
     * unaffected.
     *
     * @param vec3Arg the right factor (not null, unaffected)
     * @return a new vector
     */
    Vec3 multiply3x4(Vec3Arg vec3Arg);

    /**
     * Post multiply by the specified translation vector. The current matrix is
     * unaffected.
     *
     * @param vec3 the left factor (not null, unaffected)
     * @return a new matrix
     */
    Mat44 postTranslated(Vec3Arg vec3);

    /**
     * Write all 16 components to the specified buffer in column-major order and
     * advance the buffer's position by 16. The matrix is unaffected.
     *
     * @param storeBuffer the destination buffer (not null)
     */
    void putColumnMajor(FloatBuffer storeBuffer);
}
