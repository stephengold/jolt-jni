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
package com.github.stephengold.joltjni;

import java.util.Objects;

/**
 * A vector composed of 3 single-precision components, used to represent
 * directions, extents, forces, impulses, offsets, scaling factors, torques, and
 * velocities in 3-dimensional space.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class Vec3 implements Vec3Arg {
    // *************************************************************************
    // fields

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
     * Instantiate an all-zero vector (0,0,0).
     */
    public Vec3() {
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
     */
    public Vec3(double x, double y, double z) {
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
     */
    public Vec3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Instantiate a vector from the specified array.
     *
     * @param array the desired component values (not null, length&ge;3,
     * unaffected)
     */
    public Vec3(float[] array) {
        this.x = array[0];
        this.y = array[1];
        this.z = array[2];
    }
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
    @Override
    public float get(int index) {
        switch (index) {
            case 0:
                return x;
            case 1:
                return y;
            case 2:
                return z;
            default:
                throw new IllegalArgumentException(
                        "index must be either 0, 1 or 2");
        }
    }

    /**
     * Return the component-wise product of the specified vectors.
     *
     * @param v1 the first vector (not null, unaffected)
     * @param v2 the 2nd vector (not null, unaffected)
     * @return a new vector
     */
    public static Vec3 multiply(Vec3Arg v1, Vec3Arg v2) {
        float x = v1.getX() * v2.getX();
        float y = v1.getY() * v2.getY();
        float z = v1.getZ() * v2.getZ();
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Return the bitwise AND of the specified vectors.
     *
     * @param v1 the first vector (not null, unaffected)
     * @param v2 the 2nd vector (not null, unaffected)
     * @return a new vector
     */
    public static Vec3 sAnd(Vec3Arg v1, Vec3Arg v2) {
        int x1 = Float.floatToRawIntBits(v1.getX());
        int x2 = Float.floatToRawIntBits(v2.getX());
        float ax = Float.intBitsToFloat(x1 & x2);

        int y1 = Float.floatToRawIntBits(v1.getY());
        int y2 = Float.floatToRawIntBits(v2.getY());
        float ay = Float.intBitsToFloat(y1 & y2);

        int z1 = Float.floatToRawIntBits(v1.getZ());
        int z2 = Float.floatToRawIntBits(v2.getZ());
        float az = Float.intBitsToFloat(z1 & z2);

        Vec3 result = new Vec3(ax, ay, az);
        return result;
    }

    /**
     * Set all 3 components to specified values.
     *
     * @param x the desired X component
     * @param y the desired Y component
     * @param z the desired Z component
     */
    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Set all 3 components from the specified array.
     *
     * @param array the desired component values (not null, length&ge;3,
     * unaffected)
     */
    public void set(float[] array) {
        this.x = array[0];
        this.y = array[1];
        this.z = array[2];
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
     * Copy the X component to all components.
     */
    public void splatX() {
        this.y = x;
        this.z = x;
    }

    /**
     * Copy the Y component to all components.
     */
    public void splatY() {
        this.x = y;
        this.z = y;
    }

    /**
     * Copy the Z component to all components.
     */
    public void splatZ() {
        this.x = z;
        this.y = z;
    }
    // *************************************************************************
    // Vec3Arg methods

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
     * Return the component-wise reciprocal. The vector is unaffected.
     *
     * @return a new vector
     */
    @Override
    public Vec3 reciprocal() {
        Vec3 result = new Vec3(1f / x, 1f / y, 1f / z);
        return result;
    }

    /**
     * Copy the components to an array. The vector is unaffected.
     *
     * @return a new array with length=3
     */
    @Override
    public float[] toArray() {
        float[] result = new float[3];
        result[0] = x;
        result[1] = y;
        result[2] = z;

        return result;
    }
    // *************************************************************************
    // Object methods

    /**
     * Return a hash code. If two vectors have identical values, they will have
     * the same hash code. The vector is unaffected.
     *
     * @return a 32-bit value for use in hashing
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(x, y, z);
        return result;
    }

    /**
     * Tests for exact equality with the argument, distinguishing -0 from 0. If
     * {@code other} is null, false is returned. Either way, the current
     * instance is unaffected.
     *
     * @param other the object to compare (may be null, unaffected)
     * @return true if {@code this} and {@code other} have identical values,
     * otherwise false
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

        final Vec3Arg otherVector = (Vec3Arg) other;
        if (Float.compare(x, otherVector.getX()) != 0) {
            return false;
        } else if (Float.compare(y, otherVector.getY()) != 0) {
            return false;
        } else {
            return Float.compare(z, otherVector.getZ()) == 0;
        }
    }

    /**
     * Return a string representation of the vector, which is unaffected. For
     * example, a zero vector is represented by:
     * <pre>
     * Vec3(0.0 0.0 0.0)
     * </pre>
     *
     * @return the string representation (not null, not empty)
     */
    @Override
    public String toString() {
        String result = "Vec3(" + x + " " + y + " " + z + ")";
        return result;
    }
}
