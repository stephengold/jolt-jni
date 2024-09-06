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
package com.github.stephengold.joltjni.readonly;

import com.github.stephengold.joltjni.Mat44;
import com.github.stephengold.joltjni.Quat;
import com.github.stephengold.joltjni.Vec3;

/**
 * Read-only access to a {@code Mat44}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface Mat44Arg extends ConstJoltPhysicsObject {
    // *************************************************************************
    // new methods exposed

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
     * Multiply the current 3x3 matrix by the specified 3x3 matrix. The matrix
     * is unaffected.
     *
     * @param arg the factor (not null, unaffected)
     * @return a new matrix
     */
    Mat44 multiply3x3(Mat44Arg arg);

    /**
     * Multiply the 3x3 matrix by the specified vector. The matrix is
     * unaffected.
     *
     * @param vec3Arg the right factor (not null, unaffected)
     * @return a new vector
     */
    Vec3 multiply3x3(Vec3Arg vec3Arg);

    /**
     * Multiply the transpose of the 3x3 matrix by the specified vector. The
     * matrix is unaffected.
     *
     * @param vec3Arg the right factor (not null, unaffected)
     * @return a new vector
     */
    Vec3 multiply3x3Transposed(Vec3Arg vec3Arg);
}
