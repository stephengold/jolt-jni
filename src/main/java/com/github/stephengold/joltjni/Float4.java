/*
Copyright (c) 2026 Stephen Gold

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

import com.github.stephengold.joltjni.readonly.ConstFloat4;
import java.nio.FloatBuffer;

/**
 * A vector composed of 4 single-precision components, used as a storage class.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class Float4 implements ConstFloat4 {
    // *************************************************************************
    // fields

    /**
     * the 4th (W) component
     */
    public float w;
    /**
     * the first (X) component
     */
    public float x;
    /**
     * the 2nd (Y) component
     */
    public float y;
    /**
     * the 3rd (Z) component
     */
    public float z;
    // *************************************************************************
    // constructors

    /**
     * Instantiate an all-zero vector (0,0,0,0).
     */
    public Float4() {
        this.x = 0f;
        this.y = 0f;
        this.z = 0f;
        this.w = 0f;
    }

    /**
     * Instantiate a vector with specified components.
     *
     * @param x the desired X component
     * @param y the desired Y component
     * @param z the desired Z component
     * @param w the desired W component
     */
    public Float4(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * Instantiate a copy of the specified vector.
     *
     * @param rhs the vector to copy (not {@code null}, unaffected)
     */
    public Float4(ConstFloat4 rhs) {
        this.x = rhs.x();
        this.y = rhs.y();
        this.z = rhs.z();
        this.w = rhs.w();
    }

    /**
     * Instantiate from the specified buffer.
     *
     * @param buffer the desired component values (not {@code null}, unaffected,
     * capacity&ge;4)
     */
    public Float4(FloatBuffer buffer) {
        this.x = buffer.get(0);
        this.y = buffer.get(1);
        this.z = buffer.get(2);
        this.w = buffer.get(3);
    }

    /**
     * Instantiate from the specified position in the specified buffer.
     *
     * @param buffer the desired component values (not {@code null}, unaffected)
     * @param startPos the starting position in the buffer (&ge;0)
     */
    public Float4(FloatBuffer buffer, int startPos) {
        this.x = buffer.get(startPos);
        this.y = buffer.get(startPos + 1);
        this.z = buffer.get(startPos + 2);
        this.w = buffer.get(startPos + 3);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Set all 4 components to specified values.
     *
     * @param x the desired X component
     * @param y the desired Y component
     * @param z the desired Z component
     * @param w the desired W component
     */
    public void set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * Set all 4 components from the specified array.
     *
     * @param array the desired component values (not {@code null}, length&ge;4,
     * unaffected)
     */
    public void set(float[] array) {
        this.x = array[0];
        this.y = array[1];
        this.z = array[2];
        this.w = array[3];
    }

    /**
     * Set all 4 components from the specified buffer.
     *
     * @param buffer the desired component values (not {@code null},
     * length&ge;4, unaffected)
     */
    public void set(FloatBuffer buffer) {
        this.x = buffer.get(0);
        this.y = buffer.get(1);
        this.z = buffer.get(2);
        this.w = buffer.get(3);
    }
    // *************************************************************************
    // ConstFloat4 methods

    /**
     * Return the specified component. The vector is unaffected.
     *
     * @param index 0, 1, 2, or 3
     * @return the X component if index=0, the Y component if index=1, the Z
     * component if index=2, or the W component if index=3
     * @throws IllegalArgumentException if index is not 0, 1, 2, or 3
     */
    @Override
    public float get(int index) {
        switch (index) {
            case 0:
                return x;
            case 1:
                return y;
            case 2:
                return z;
            case 3:
                return w;
            default:
                throw new IllegalArgumentException(
                        "index must be 0, 1, 2 or 3");
        }
    }

    /**
     * Write all 4 components to the specified buffer and advance the buffer's
     * position by 4. The vector is unaffected.
     *
     * @param storeBuffer the destination buffer (not {@code null})
     */
    @Override
    public void put(FloatBuffer storeBuffer) {
        storeBuffer.put(x);
        storeBuffer.put(y);
        storeBuffer.put(z);
        storeBuffer.put(w);
    }

    /**
     * Return the 4th (W) component. The vector is unaffected.
     *
     * @return the component value
     */
    @Override
    public float w() {
        return w;
    }

    /**
     * Return the first (X) component. The vector is unaffected.
     *
     * @return the component value
     */
    @Override
    public float x() {
        return x;
    }

    /**
     * Return the 2nd (Y) component. The vector is unaffected.
     *
     * @return the component value
     */
    @Override
    public float y() {
        return y;
    }

    /**
     * Return the 3rd (Z) component. The vector is unaffected.
     *
     * @return the component value
     */
    @Override
    public float z() {
        return z;
    }
    // *************************************************************************
    // Object methods

    /**
     * Return a string representation of the vector, which is unaffected. For
     * example, a zero vector is represented by:
     * <pre>
     * Float4(0.0 0.0 0.0 0.0)
     * </pre>
     *
     * @return the string representation (not {@code null}, not empty)
     */
    @Override
    public String toString() {
        String result = "Float4(" + x + " " + y + " " + z + " " + w + ")";
        return result;
    }
}
