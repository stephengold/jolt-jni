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
import com.github.stephengold.joltjni.readonly.Vec3Arg;

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

    /**
     * Instantiate a quaternion based on a {@code Vec3Arg}.
     *
     * @param v the desired XYZ components
     * @param w the desired W component
     */
    public Quat(Vec3Arg v, float w) {
        this.x = v.getX();
        this.y = v.getY();
        this.z = v.getZ();
        this.w = w;
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return a scaled version of the specified quaternion. (native operator:
     * binary {@code *})
     *
     * @param scale the scale to apply
     * @param v the input quaternion (not null, unaffected)
     * @return a new quaternion
     */
    public static Quat multiply(float scale, QuatArg v) {
        float w = scale * v.getW();
        float x = scale * v.getX();
        float y = scale * v.getY();
        float z = scale * v.getZ();
        Quat result = new Quat(x, y, z, w);

        return result;
    }

    /**
     * Return the product of the specified quaternions. (native operator: binary
     * {@code *})
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
     * Rotate the specified vector by the specified unit quaternion. (native
     * operator: binary {@code *})
     *
     * @param lhs the rotation to apply (not null, normalized, unaffected)
     * @param rhs the vector to apply it to (not null, unaffected)
     * @return a new vector
     */
    public static Vec3 rotate(QuatArg lhs, Vec3Arg rhs) {
        assert lhs.isNormalized();

        float lw = lhs.getW();
        float lx = lhs.getX();
        float ly = lhs.getY();
        float lz = lhs.getZ();

        float rx = rhs.getX();
        float ry = rhs.getY();
        float rz = rhs.getZ();

        // a = lhs x pure(rhs)
        float aw = -lx * rx - ly * ry - lz * rz;
        float ax = lw * rx + ly * rz - lz * ry;
        float ay = lw * ry - lx * rz + lz * rx;
        float az = lw * rz + lx * ry - ly * rx;

        // result = vec3(a x conjugate(lhs))
        float x = -aw * lx + ax * lw - ay * lz + az * ly;
        float y = -aw * ly + ax * lz + ay * lw - az * lx;
        float z = -aw * lz - ax * ly + ay * lx + az * lw;

        Vec3 result = new Vec3(x, y, z);
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

    /**
     * Create an identity quaternion (0,0,0,1).
     *
     * @return a new quaternion
     */
    public static Quat sIdentity() {
        Quat result = new Quat();
        return result;
    }

    /**
     * Create a rotation quaternion from a normalized rotation axis.
     *
     * @param axis the desired rotation axis (not null, normalized)
     * @param angle the desired rotation angle (in radians)
     * @return a new quaternion
     */
    public static Quat sRotation(Vec3 axis, float angle) {
        assert axis.isNormalized();

        float qw = (float) Math.cos(0.5 * angle);
        float s = (float) Math.sin(0.5 * angle);
        float qx = axis.getX() * s;
        float qy = axis.getY() * s;
        float qz = axis.getZ() * s;
        Quat result = new Quat(qx, qy, qz, qw);

        return result;
    }
    // *************************************************************************
    // QuatArg methods

    /**
     * Return the conjugate. The current object is unaffected.
     *
     * @return a new object
     */
    @Override
    public Quat conjugated() {
        Quat result = new Quat(-x, -y, -z, w);
        return result;
    }

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

    /**
     * Test whether the quaternion is normalized to within a tolerance of 10^-5.
     * The quaternion is unaffected.
     *
     * @return true if normalized, otherwise false
     */
    @Override
    public boolean isNormalized() {
        boolean result = isNormalized(1e-5f);
        return result;
    }

    /**
     * Test whether the quaternion is normalized to within the specified
     * tolerance. The quaternion is unaffected.
     *
     * @param tolerance the desired tolerance (default=1e-5)
     * @return true if normalized, otherwise false
     */
    @Override
    public boolean isNormalized(float tolerance) {
        float lengthSq = lengthSq();
        if (Math.abs(lengthSq - 1f) <= tolerance) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Return the length. The quaternion is unaffected.
     *
     * @return the length
     */
    @Override
    public float length() {
        float lengthSq = lengthSq();
        float result = (float) Math.sqrt(lengthSq);

        return result;
    }

    /**
     * Return the squared length. The quaternion is unaffected.
     *
     * @return the squared length
     */
    @Override
    public float lengthSq() {
        float result = w * w + x * x + y * y + z * z;
        return result;
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
