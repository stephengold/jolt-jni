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

import com.github.stephengold.joltjni.Float3;
import com.github.stephengold.joltjni.RVec3;
import com.github.stephengold.joltjni.Vec3;
import java.nio.FloatBuffer;

/**
 * Read-only access to a {@code Vec3}. (native type: const Vec3)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface Vec3Arg {
    // *************************************************************************
    // new methods exposed

    /**
     * Return the cross product with the specified vector. The current vector is
     * unaffected.
     *
     * @param rightFactor the vector to cross with the current one (not null,
     * unaffected)
     * @return a new product vector
     */
    Vec3 cross(Vec3Arg rightFactor);

    /**
     * Return the dot product with the specified vector. Both vectors are
     * unaffected.
     *
     * @param factor the vector to dot with the current one (not null,
     * unaffected)
     * @return the dot product
     */
    float dot(Vec3Arg factor);

    /**
     * Return the specified component. The vector is unaffected.
     *
     * @param index 0, 1, or 2
     * @return the X component if index=0, the Y component if index=1, or the Z
     * component if index=2
     * @throws IllegalArgumentException if index is not 0, 1, or 2
     */
    float get(int index);

    /**
     * Return an arbitrary unit vector perpendicular to the current vector. The
     * current vector is unaffected.
     *
     * @return a new vector
     */
    Vec3 getNormalizedPerpendicular();

    /**
     * Return the component-wise (binary) sign. The current vector is
     * unaffected.
     *
     * @return a new vector (each component 1 or -1)
     */
    Vec3 getSign();

    /**
     * Return the first (X) component in single precision. The vector is
     * unaffected.
     *
     * @return the component value
     */
    float getX();

    /**
     * Return the 2nd (Y) component in single precision. The vector is
     * unaffected.
     *
     * @return the component value
     */
    float getY();

    /**
     * Return the 3rd (Z) component in single precision. The vector is
     * unaffected.
     *
     * @return the component value
     */
    float getZ();

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
     * Test whether the squared length is within 1e-12 of zero. The vector is
     * unaffected.
     *
     * @return {@code true} if nearly zero, otherwise {@code false}
     */
    boolean isNearZero();

    /**
     * Test whether the squared length is within the specified tolerance of
     * zero. The vector is unaffected.
     *
     * @param tolerance the desired tolerance (&ge;0. default=1e-12)
     * @return {@code true} if nearly zero, otherwise {@code false}
     */
    boolean isNearZero(float tolerance);

    /**
     * Test whether the vector is normalized to within a tolerance of 10^-6. The
     * vector is unaffected.
     *
     * @return {@code true} if normalized, otherwise {@code false}
     */
    boolean isNormalized();

    /**
     * Test whether the vector is normalized to within the specified tolerance.
     * The vector is unaffected.
     *
     * @param tolerance the desired tolerance (default=1e-6)
     * @return {@code true} if normalized, otherwise {@code false}
     */
    boolean isNormalized(float tolerance);

    /**
     * Return the length. The vector is unaffected.
     *
     * @return the length
     */
    float length();

    /**
     * Return the squared length. The vector is unaffected.
     *
     * @return the squared length
     */
    float lengthSq();

    /**
     * Generate a normalized vector with the same direction. The current vector
     * is unaffected.
     *
     * @return a new vector
     */
    Vec3 normalized();

    /**
     * Return a copy of the argument if the length of the current vector is
     * zero. Otherwise, generate a normalized vector with the same direction as
     * the current vector. The current vector is unaffected.
     *
     * @param zeroValue the value to return if the length is zero (not null,
     * unaffected)
     * @return a new vector
     */
    Vec3 normalizedOr(Vec3Arg zeroValue);

    /**
     * Write all 3 components to the specified buffer and advance the buffer's
     * position by 3. The vector is unaffected.
     *
     * @param storeBuffer the destination buffer (not null)
     */
    void put(FloatBuffer storeBuffer);

    /**
     * Generate the component-wise reciprocal. The current vector is unaffected.
     *
     * @return a new vector
     */
    Vec3 reciprocal();

    /**
     * Return the maximum component. The current vector is unaffected.
     *
     * @return a component value
     */
    float reduceMax();

    /**
     * Return the minimum component. The current vector is unaffected.
     *
     * @return a component value
     */
    float reduceMin();

    /**
     * Copy to a {@code Float3} object. The vector is unaffected.
     *
     * @param target the destination (not null, modified)
     */
    void storeFloat3(Float3 target);

    /**
     * Copy the specified components to a new vector. The current vector is
     * unaffected.
     *
     * @param xi index of the component to copy to the first (X) component of
     * the result (0, 1, or 2)
     * @param yi index of the component to copy to the 2nd (Y) component of the
     * result (0, 1, or 2)
     * @param zi index of the component to copy to the 3rd (Z) component of the
     * result (0, 1, or 2)
     * @return the new vector
     */
    Vec3 swizzle(int xi, int yi, int zi);

    /**
     * Copy the components to an array. The vector is unaffected.
     *
     * @return a new array with length=3
     */
    float[] toArray();

    /**
     * Copy the components to a new location vector. The current vector is
     * unaffected.
     *
     * @return a new vector
     */
    RVec3 toRVec3();
}
