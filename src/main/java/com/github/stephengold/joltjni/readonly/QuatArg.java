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

import com.github.stephengold.joltjni.Quat;
import com.github.stephengold.joltjni.Vec3;
import java.nio.FloatBuffer;

/**
 * Read-only access to a {@code Quat}. (native type: const Quat)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface QuatArg {
    // *************************************************************************
    // new methods exposed

    /**
     * Return the conjugate. The current object is unaffected.
     *
     * @return a new object
     */
    Quat conjugated();

    /**
     * Return the real (W) component in single precision. The quaternion is
     * unaffected.
     *
     * @return the component value
     */
    float getW();

    /**
     * Return the first imaginary (X) component in single precision. The
     * quaternion is unaffected.
     *
     * @return the component value
     */
    float getX();

    /**
     * Return the 2nd imaginary (Y) component in single precision. The
     * quaternion is unaffected.
     *
     * @return the component value
     */
    float getY();

    /**
     * Return the 3rd imaginary (Z) component in single precision. The
     * quaternion is unaffected.
     *
     * @return the component value
     */
    float getZ();

    /**
     * Test whether the quaternion is normalized to within a tolerance of 10^-5.
     * The quaternion is unaffected.
     *
     * @return {@code true} if normalized, otherwise {@code false}
     */
    boolean isNormalized();

    /**
     * Test whether the quaternion is normalized to within the specified
     * tolerance. The quaternion is unaffected.
     *
     * @param tolerance the desired tolerance (default=1e-5)
     * @return {@code true} if normalized, otherwise {@code false}
     */
    boolean isNormalized(float tolerance);

    /**
     * Test whether the quaternion is zero. The quaternion is unaffected.
     *
     * @return {@code true} if zero, otherwise {@code false}
     */
    boolean isZero();

    /**
     * Return the length. The quaternion is unaffected.
     *
     * @return the length
     */
    float length();

    /**
     * Return the squared length. The quaternion is unaffected.
     *
     * @return the squared length
     */
    float lengthSq();

    /**
     * Generate a normalized quaternion that represents the same rotation. The
     * current object is unaffected.
     *
     * @return a new quaternion
     */
    Quat normalized();

    /**
     * Write all 4 components to the specified buffer and advance the buffer's
     * position by 4. The quaternion is unaffected.
     *
     * @param storeBuffer the destination buffer (not null)
     */
    void put(FloatBuffer storeBuffer);

    /**
     * Apply the rotation to (1,0,0). The quaternion is assumed to be normalized
     * and is unaffected.
     *
     * @return a new vector
     */
    Vec3 rotateAxisX();

    /**
     * Apply the rotation to (0,1,0). The quaternion is assumed to be normalized
     * and is unaffected.
     *
     * @return a new vector
     */
    Vec3 rotateAxisY();

    /**
     * Apply the rotation to (0,0,1). The quaternion is assumed to be normalized
     * and is unaffected.
     *
     * @return a new vector
     */
    Vec3 rotateAxisZ();
}
