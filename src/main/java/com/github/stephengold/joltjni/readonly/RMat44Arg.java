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

import com.github.stephengold.joltjni.RVec3;
import com.github.stephengold.joltjni.Vec3;

/**
 * Read-only access to an {@code RMat44}. (native type: const RMat44)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface RMat44Arg extends ConstJoltPhysicsObject {
    // *************************************************************************
    // new methods exposed

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
     * Return the specified element in double precision. The matrix is
     * unaffected.
     *
     * @param row the zero-origin index of the row (&ge;0, &lt;4)
     * @param column the zero-origin index of the column (&ge;0, &lt;4)
     * @return the element's value
     */
    double getElement(int row, int column);

    /**
     * Return the translation component. The matrix is unaffected.
     *
     * @return a new vector
     */
    RVec3 getTranslation();

    /**
     * Multiply the 3x4 matrix by the specified column vector, with the 4th
     * component of the right factor implied to be one. The matrix is
     * unaffected.
     *
     * @param arg the right factor (not null, unaffected)
     * @return a new vector
     */
    RVec3 multiply3x4(Vec3Arg arg);
}
