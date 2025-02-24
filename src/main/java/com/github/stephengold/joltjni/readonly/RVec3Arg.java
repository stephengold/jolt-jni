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

import com.github.stephengold.joltjni.RVec3;
import com.github.stephengold.joltjni.Vec3;

/**
 * Read-only access to an {@code RVec3}. (native type: const RVec3)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface RVec3Arg {
    // *************************************************************************
    // new methods exposed

    /**
     * Return the cross product with the argument. Both vectors are unaffected.
     *
     * @param rightFactor the vector to cross with the current one (not null,
     * unaffected)
     * @return a new product vector
     */
    RVec3 cross(RVec3Arg rightFactor);

    /**
     * Return the dot product with the argument. Both vectors are unaffected.
     *
     * @param factor the vector to dot with the current one (not null,
     * unaffected)
     * @return the dot product
     */
    double dot(RVec3Arg factor);

    /**
     * Return the specified component in double precision. The vector is
     * unaffected.
     *
     * @param index 0, 1, or 2
     * @return the X component if index=0, the Y component if index=1, or the Z
     * component if index=2
     * @throws IllegalArgumentException if index is not 0, 1, or 2
     */
    double get(int index);

    /**
     * Return the first (X) component in positional precision. The vector is
     * unaffected.
     *
     * @return the component value
     */
    Object getX();

    /**
     * Return the 2nd (Y) component in positional precision. The vector is
     * unaffected.
     *
     * @return the component value
     */
    Object getY();

    /**
     * Return the 3rd (Z) component in positional precision. The vector is
     * unaffected.
     *
     * @return the component value
     */
    Object getZ();

    /**
     * Test whether the vector contains infinities or NaNs. The vector is
     * unaffected.
     *
     * @return {@code false} if one or more infinities or NaNs, otherwise
     * {@code true}
     */
    boolean isFinite();

    /**
     * Test whether the vector contains NaNs. The vector is unaffected.
     *
     * @return {@code true} if one or more NaNs, otherwise {@code false}
     */
    boolean isNan();

    /**
     * Test whether the squared length is within 10^-12 (single-precision) or
     * 10^-24 (double-precision) of zero. The vector is unaffected.
     *
     * @return {@code true} if nearly zero, otherwise {@code false}
     */
    boolean isNearZero();

    /**
     * Test whether the squared length is within the specified tolerance of
     * zero. The vector is unaffected.
     *
     * @param tolerance the desired tolerance (&ge;0, default=1e-12 or 1e-24)
     * @return {@code true} if nearly zero, otherwise {@code false}
     */
    boolean isNearZero(double tolerance);

    /**
     * Test whether the vector is normalized to within a tolerance of 10^-6
     * (single precision) or 10^-12 (double precision). The vector is
     * unaffected.
     *
     * @return {@code true} if normalized, otherwise {@code false}
     */
    boolean isNormalized();

    /**
     * Test whether the vector is normalized to within the specified tolerance.
     * The vector is unaffected.
     *
     * @param tolerance the desired tolerance (&ge;0, default=1e-6 or 1e-12)
     * @return {@code true} if normalized, otherwise {@code false}
     */
    boolean isNormalized(double tolerance);

    /**
     * Return the length. The vector is unaffected.
     *
     * @return the length
     */
    double length();

    /**
     * Return the squared length. The vector is unaffected.
     *
     * @return the squared length
     */
    double lengthSq();

    /**
     * Generate a unit vector with the same direction. The current vector is
     * unaffected.
     *
     * @return a new vector
     */
    RVec3 normalized();

    /**
     * Generate the component-wise reciprocal. The current vector is unaffected.
     *
     * @return a new vector
     */
    RVec3 reciprocal();

    /**
     * Copy the components to an array. The vector is unaffected.
     *
     * @return a new array with length=3
     */
    double[] toArray();

    /**
     * Convert to single-precision vector. The current vector is unaffected.
     *
     * @return a new vector
     */
    Vec3 toVec3();

    /**
     * Return the first (X) component in single precision. The vector is
     * unaffected.
     *
     * @return the component value
     */
    float x();

    /**
     * Return the first (X) component in double precision. The vector is
     * unaffected.
     *
     * @return the component value
     */
    double xx();

    /**
     * Return the 2nd (Y) component in single precision. The vector is
     * unaffected.
     *
     * @return the component value
     */
    float y();

    /**
     * Return the 2nd (Y) component in double precision. The vector is
     * unaffected.
     *
     * @return the component value
     */
    double yy();

    /**
     * Return the 3rd (Z) component in single precision. The vector is
     * unaffected.
     *
     * @return the component value
     */
    float z();

    /**
     * Return the 3rd (Z) component in double precision. The vector is
     * unaffected.
     *
     * @return the component value
     */
    double zz();
}
