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

import com.github.stephengold.joltjni.Vec3;

/**
 * Read-only access to a {@code Vec3}. (native type: const Vec3)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface Vec3Arg {
    // *************************************************************************
    // new methods exposed

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
     * Test whether the vector is normalized to within a tolerance of 10^-6. The
     * vector is unaffected.
     *
     * @return true if normalized, otherwise false
     */
    boolean isNormalized();

    /**
     * Test whether the vector is normalized to within the specified tolerance.
     * The vector is unaffected.
     *
     * @param tolerance the desired tolerance
     * @return true if normalized, otherwise false
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
     * Return the component-wise reciprocal. The vector is unaffected.
     *
     * @return a new vector
     */
    Vec3 reciprocal();

    /**
     * Copy the components to an array. The vector is unaffected.
     *
     * @return a new array with length=3
     */
    float[] toArray();
}
