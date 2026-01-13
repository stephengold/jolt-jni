/*
Copyright (c) 2024-2026 Stephen Gold

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

import com.github.stephengold.joltjni.readonly.Vec3Arg;
import com.github.stephengold.joltjni.readonly.Vec4Arg;
import java.util.Objects;

/**
 * A vector composed of 4 single-precision components, used to construct
 * matrices.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class Vec4 implements Vec4Arg {
    // *************************************************************************
    // fields

    /**
     * the 4th (W) component
     */
    private float w;
    /**
     * the first (X) component
     */
    private float x;
    /**
     * the 2nd (Y) component
     */
    private float y;
    /**
     * the 3rd (Z) component
     */
    private float z;
    // *************************************************************************
    // constructors

    /**
     * Instantiate an all-zero vector (0,0,0,0).
     */
    public Vec4() {
        this.w = 0f;
        this.x = 0f;
        this.y = 0f;
        this.z = 0f;
    }

    /**
     * Instantiate a vector with specified components.
     *
     * @param x the desired X component
     * @param y the desired Y component
     * @param z the desired Z component
     * @param w the desired W component
     */
    public Vec4(double x, double y, double z, double w) {
        this.w = (float) w;
        this.x = (float) x;
        this.y = (float) y;
        this.z = (float) z;
    }

    /**
     * Instantiate a vector with specified components.
     *
     * @param x the desired X component
     * @param y the desired Y component
     * @param z the desired Z component
     * @param w the desired W component
     */
    public Vec4(float x, float y, float z, float w) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Instantiate a vector from the specified array.
     *
     * @param array the desired component values (not null, length&ge;4,
     * unaffected)
     */
    public Vec4(float[] array) {
        this.x = array[0];
        this.y = array[1];
        this.z = array[2];
        this.w = array[3];
    }

    /**
     * Instantiate from the specified 3-D vector.
     *
     * @param vec the vector to copy (not null, unaffected)
     * @param w the desired W component
     */
    public Vec4(Vec3Arg vec, float w) {
        this.x = vec.getX();
        this.y = vec.getY();
        this.z = vec.getZ();
        this.w = w;
    }

    /**
     * Instantiate a copy of the argument.
     *
     * @param vec the vector to copy (not null, unaffected)
     */
    public Vec4(Vec4Arg vec) {
        this.w = vec.getW();
        this.x = vec.getX();
        this.y = vec.getY();
        this.z = vec.getZ();
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Set all 4 components to the specified values.
     *
     * @param x the desired X component
     * @param y the desired Y component
     * @param z the desired Z component
     * @param w the desired W component
     */
    public void set(float x, float y, float z, float w) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Set all 4 components from the specified array.
     *
     * @param array the desired component values (not null, length&ge;4,
     * unaffected)
     */
    public void set(float[] array) {
        this.x = array[0];
        this.y = array[1];
        this.z = array[2];
        this.w = array[3];
    }

    /**
     * Set all 4 components from the argument.
     *
     * @param source the vector to copy (not null, unaffected)
     */
    public void set(Vec4Arg source) {
        this.x = source.getX();
        this.y = source.getY();
        this.z = source.getZ();
        this.w = source.getW();
    }

    /**
     * Alter the 4th (W) component.
     *
     * @param w the desired value
     */
    public void setW(float w) {
        this.w = w;
    }

    /**
     * Alter the first (X) component.
     *
     * @param x the desired value
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Alter the 2nd (Y) component.
     *
     * @param y the desired value
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Alter the 3rd (Z) component.
     *
     * @param z the desired value
     */
    public void setZ(float z) {
        this.z = z;
    }

    /**
     * Copy the W component to all components.
     */
    public void splatW() {
        this.x = w;
        this.y = w;
        this.z = w;
    }

    /**
     * Copy the X component to all components.
     */
    public void splatX() {
        this.w = x;
        this.y = x;
        this.z = x;
    }

    /**
     * Copy the Y component to all components.
     */
    public void splatY() {
        this.w = y;
        this.x = y;
        this.z = y;
    }

    /**
     * Copy the Z component to all components.
     */
    public void splatZ() {
        this.w = z;
        this.x = z;
        this.y = z;
    }

    /**
     * Create a vector with all components identical.
     *
     * @param value the desired component value
     * @return a new vector
     */
    public static Vec4 sReplicate(float value) {
        Vec4 result = new Vec4(value, value, value, value);
        return result;
    }

    /**
     * Create a vector with all components zero.
     *
     * @return a new vector
     */
    public static Vec4 sZero() {
        Vec4 result = new Vec4();
        return result;
    }
    // *************************************************************************
    // Vec4Arg methods

    /**
     * Return the dot product with the specified vector. The current vector is
     * unaffected.
     *
     * @param factor the vector to dot with the current one (not null,
     * unaffected)
     * @return the dot product
     */
    @Override
    public float dot(Vec4Arg factor) {
        float result = w * factor.getW() + x * factor.getX()
                + y * factor.getY() + z * factor.getZ();
        return result;
    }

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
                        "index must be 0, 1, 2, or 3");
        }
    }

    /**
     * Return the 4th (W) component in single precision. The vector is
     * unaffected.
     *
     * @return the component value
     */
    @Override
    public float getW() {
        return w;
    }

    /**
     * Return the first (X) component in single precision. The vector is
     * unaffected.
     *
     * @return the component value
     */
    @Override
    public float getX() {
        return x;
    }

    /**
     * Return the 2nd (Y) component in single precision. The vector is
     * unaffected.
     *
     * @return the component value
     */
    @Override
    public float getY() {
        return y;
    }

    /**
     * Return the 3rd (Z) component in single precision. The vector is
     * unaffected.
     *
     * @return the component value
     */
    @Override
    public float getZ() {
        return z;
    }

    /**
     * Copy the components to an array. The vector is unaffected.
     *
     * @return a new array with length=4
     */
    @Override
    public float[] toArray() {
        float[] result = {x, y, z, w};
        return result;
    }
    // *************************************************************************
    // Object methods

    /**
     * Tests for exact equality with the argument, distinguishing -0 from 0. If
     * {@code other} is null, false is returned. Either way, the current
     * instance is unaffected.
     *
     * @param other the object to compare (unaffected) or {@code null}
     * @return {@code true} if {@code this} and {@code other} have identical
     * values, otherwise {@code false}
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other == null) {
            return false;
        } else if (getClass() != other.getClass()) {
            return false;
        }

        final Vec4Arg otherVector = (Vec4Arg) other;
        if (Float.compare(x, otherVector.getX()) != 0) {
            return false;
        } else if (Float.compare(y, otherVector.getY()) != 0) {
            return false;
        } else if (Float.compare(z, otherVector.getZ()) != 0) {
            return false;
        } else {
            return Float.compare(w, otherVector.getW()) == 0;
        }
    }

    /**
     * Return a hash code. If two vectors have identical values, they will have
     * the same hash code. The vector is unaffected.
     *
     * @return a 32-bit value for use in hashing
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(x, y, z, w);
        return result;
    }

    /**
     * Return a string representation of the vector, which is unaffected. For
     * example, a zero vector is represented by:
     * <pre>
     * Vec4(0.0 0.0 0.0 0.0)
     * </pre>
     *
     * @return the string representation (not null, not empty)
     */
    @Override
    public String toString() {
        String result = "Vec4(" + x + " " + y + " " + z + " " + w + ")";
        return result;
    }
}
