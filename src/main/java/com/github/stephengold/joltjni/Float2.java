/*
Copyright (c) 2025 Stephen Gold

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

import java.nio.FloatBuffer;

/**
 * A vector composed of 2 single-precision components, used as a storage class.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class Float2 {
    // *************************************************************************
    // fields

    /**
     * the first (X) component
     */
    public float x;
    /**
     * the 2nd (Y) component
     */
    public float y;
    // *************************************************************************
    // constructors

    /**
     * Instantiate an all-zero vector (0,0,0).
     */
    public Float2() {
        this.x = 0f;
        this.y = 0f;
    }

    /**
     * Instantiate a vector with specified components.
     *
     * @param x the desired X component
     * @param y the desired Y component
     */
    public Float2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Instantiate a copy of the specified vector.
     *
     * @param rhs the vector to copy (not null, unaffected)
     */
    public Float2(Float2 rhs) {
        this.x = rhs.x;
        this.y = rhs.y;
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the specified component. The vector is unaffected.
     *
     * @param index 0 or 1
     * @return the X component if index=0 or the Y component if index=1
     * @throws IllegalArgumentException if index is not 0 or 1
     */
    public float get(int index) {
        switch (index) {
            case 0:
                return x;
            case 1:
                return y;
            default:
                throw new IllegalArgumentException("index must be 0 or 1");
        }
    }

    /**
     * Write both components to the specified buffer and advance the buffer's
     * position by 2. The vector is unaffected.
     *
     * @param storeBuffer the destination buffer (not null)
     */
    public void put(FloatBuffer storeBuffer) {
        storeBuffer.put(x);
        storeBuffer.put(y);
    }

    /**
     * Set both components to specified values.
     *
     * @param x the desired X component
     * @param y the desired Y component
     */
    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Set both components from the specified array.
     *
     * @param array the desired component values (not null, length&ge;2,
     * unaffected)
     */
    public void set(float[] array) {
        this.x = array[0];
        this.y = array[1];
    }
    // *************************************************************************
    // Object methods

    /**
     * Return a string representation of the vector, which is unaffected. For
     * example, a zero vector is represented by:
     * <pre>
     * Float2(0.0 0.0)
     * </pre>
     *
     * @return the string representation (not null, not empty)
     */
    @Override
    public String toString() {
        String result = "Float2(" + x + " " + y + ")";
        return result;
    }
}
