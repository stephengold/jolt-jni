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

/**
 * Read-only access to a {@code Vec4}. (native type: const Vec4)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface Vec4Arg {
    /**
     * Return the dot product with the specified vector. The current vector is
     * unaffected.
     *
     * @param factor the vector to dot with the current one (not null,
     * unaffected)
     * @return the dot product
     */
    float dot(Vec4Arg factor);

    /**
     * Return the specified component. The vector is unaffected.
     *
     * @param index 0, 1, 2, or 3
     * @return the X component if index=0, the Y component if index=1, the Z
     * component if index=2, or the W component if index=3
     * @throws IllegalArgumentException if index is not 0, 1, 2, or 3
     */
    float get(int index);

    /**
     * Return the 4th (W) component in single precision. The vector is
     * unaffected.
     *
     * @return the component value
     */
    float getW();

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
     * Copy the components to an array. The vector is unaffected.
     *
     * @return a new array with length=4
     */
    float[] toArray();
}
