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

import com.github.stephengold.joltjni.readonly.QuatArg;

/**
 * A math object used to represent rotations and orientations in 3-dimensional
 * space, without risk of gimbal lock. Each quaternion has 4 single-precision
 * components: 3 imaginary ones (X, Y, and Z) and a real one (W).
 * <p>
 * Mathematically speaking, quaternions are an extension of complex numbers.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class Quat implements QuatArg {
    // *************************************************************************
    // fields

    /**
     * the real (W) component
     */
    private float w;
    /**
     * the first imaginary (X) component
     */
    private float x;
    /**
     * the 2nd imaginary (Y) component
     */
    private float y;
    /**
     * the 3rd imaginary (Z) component
     */
    private float z;
    // *************************************************************************
    // constructors

    /**
     * Instantiate an identity quaternion (0,0,0,1).
     */
    public Quat() {
        this.x = 0f;
        this.y = 0f;
        this.z = 0f;
        this.w = 1f;
    }

    /**
     * Instantiate a quaternion with specified components.
     *
     * @param x the desired X component
     * @param y the desired Y component
     * @param z the desired Z component
     * @param w the desired W component
     */
    public Quat(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the product of the specified quaternions.
     *
     * @param lhs the left factor (not null, unaffected)
     * @param rhs the right factor (not null, unaffected)
     * @return a new quaternion
     */
    public static Quat multiply(QuatArg lhs, QuatArg rhs) {
        float lw = lhs.getW();
        float lx = lhs.getX();
        float ly = lhs.getY();
        float lz = lhs.getZ();

        float rw = rhs.getW();
        float rx = rhs.getX();
        float ry = rhs.getY();
        float rz = rhs.getZ();

        float w = lw * rw - lx * rx - ly * ry - lz * rz;
        float x = lw * rx + lx * rw + ly * rz - lz * ry;
        float y = lw * ry - lx * rz + ly * rw + lz * rx;
        float z = lw * rz + lx * ry - ly * rx + lz * rw;

        Quat result = new Quat(x, y, z, w);
        return result;
    }

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
    // *************************************************************************
    // QuatArg methods

    /**
     * Return the real (W) component in single precision. The quaternion is
     * unaffected.
     *
     * @return the component value
     */
    @Override
    public float getW() {
        return w;
    }

    /**
     * Return the first imaginary (X) component in single precision. The
     * quaternion is unaffected.
     *
     * @return the component value
     */
    @Override
    public float getX() {
        return x;
    }

    /**
     * Return the 2nd imaginary (Y) component in single precision. The
     * quaternion is unaffected.
     *
     * @return the component value
     */
    @Override
    public float getY() {
        return y;
    }

    /**
     * Return the 3rd imaginary (Z) component in single precision. The
     * quaternion is unaffected.
     *
     * @return the component value
     */
    @Override
    public float getZ() {
        return z;
    }
    // *************************************************************************
    // Object methods

    /**
     * Return a string representation of the quaternion, which is unaffected.
     * For example, an identity quaternion is represented by:
     * <pre>
     * Quat(0.0 0.0 0.0 1.0)
     * </pre>
     *
     * @return the string representation (not null, not empty)
     */
    @Override
    public String toString() {
        String result = "Quat(" + x + " " + y + " " + z + " " + w + ")";
        return result;
    }
}
