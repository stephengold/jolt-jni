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
package com.github.stephengold.joltjni;

import com.github.stephengold.joltjni.operator.Op;
import com.github.stephengold.joltjni.readonly.QuatArg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import com.github.stephengold.joltjni.std.RandomNumberEngine;
import com.github.stephengold.joltjni.std.UniformFloatDistribution;
import java.nio.FloatBuffer;

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
    /**
     * lazily allocated distribution, used in randomization
     */
    private static UniformFloatDistribution distro = null;
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
     * Instantiate a copy of the argument.
     *
     * @param original the quaternion to copy (not null, unaffected)
     */
    public Quat(QuatArg original) {
        this.x = original.getX();
        this.y = original.getY();
        this.z = original.getZ();
        this.w = original.getW();
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
     * Copy all 4 components from the argument.
     *
     * @param source the quaternion to copy (not null, unaffected)
     */
    public void set(QuatArg source) {
        this.w = source.getW();
        this.x = source.getX();
        this.y = source.getY();
        this.z = source.getZ();
    }

    /**
     * Create a rotation quaternion from Euler angles. Rotation order is X then
     * Y then Z.
     *
     * @param angles the desired rotation around each axis (in radians, not
     * null, unaffected)
     * @return a new quaternion
     */
    public static Quat sEulerAngles(Vec3 angles) {
        float halfX = 0.5f * angles.getX();
        float halfY = 0.5f * angles.getY();
        float halfZ = 0.5f * angles.getZ();

        float cx = Jolt.cos(halfX);
        float cy = Jolt.cos(halfY);
        float cz = Jolt.cos(halfZ);
        float sx = Jolt.sin(halfX);
        float sy = Jolt.sin(halfY);
        float sz = Jolt.sin(halfZ);

        Quat result = new Quat(
                cz * sx * cy - sz * cx * sy,
                cz * cx * sy + sz * sx * cy,
                sz * cx * cy - cz * sx * sy,
                cz * cx * cy + sz * sx * sy);

        return result;
    }

    /**
     * Create a rotation quaternion that rotates {@code from} to {@code to}.
     *
     * @param from the first direction vector (not null, unaffected)
     * @param to the 2nd direction vector (not null, unaffected)
     * @return a new quaternion
     */
    public static Quat sFromTo(Vec3Arg from, Vec3Arg to) {
        float lenV1V2 = (float) Math.sqrt(from.lengthSq() * to.lengthSq());
        float w = lenV1V2 + from.dot(to);

        if (w == 0f) {
            if (lenV1V2 == 0f) { // one of the vectors has zero length
                return Quat.sIdentity();
            } else { // vectors are perpendicular
                Vec3 v = from.getNormalizedPerpendicular();
                return new Quat(v, 0f);
            }
        }

        Vec3 v = from.cross(to);
        Quat result = new Quat(v, w).normalized();
        return result;
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
     * Generate a pseudo-random unit quaternion.
     *
     * @param engine the generator to use (not null)
     * @return a new unit quaternion
     */
    public static Quat sRandom(RandomNumberEngine engine) {
        assert engine != null;
        if (distro == null) {
            distro = new UniformFloatDistribution(0f, 1f);
        }

        float x0 = distro.nextFloat(engine);
        float r1 = (float) Math.sqrt(1f - x0);
        float r2 = (float) Math.sqrt(x0);

        float px = 2f * Jolt.JPH_PI * distro.nextFloat(engine);
        float py = 2f * Jolt.JPH_PI * distro.nextFloat(engine);

        float x = r1 * Jolt.sin(px);
        float y = r1 * Jolt.cos(px);
        float z = r2 * Jolt.sin(py);
        float w = r2 * Jolt.cos(py);
        Quat result = new Quat(x, y, z, w);

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

        float qw = Jolt.cos(0.5f * angle);
        float s = Jolt.sin(0.5f * angle);
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
     * @return {@code true} if normalized, otherwise {@code false}
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
     * @return {@code true} if normalized, otherwise {@code false}
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
     * Test whether the quaternion is zero. The quaternion is unaffected.
     *
     * @return {@code true} if exactly zero, otherwise {@code false}
     */
    @Override
    public boolean isZero() {
        boolean result = (w == 0f) && (x == 0f) && (y == 0f) && (z == 0f);
        return result;
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

    /**
     * Generate a normalized quaternion that represents the same rotation. The
     * current object is unaffected.
     *
     * @return a new quaternion
     */
    @Override
    public Quat normalized() {
        Quat result = Op.star(1f / length(), this);
        return result;
    }

    /**
     * Write all 4 components to the specified buffer and advance the buffer's
     * position by 4. The quaternion is unaffected.
     *
     * @param storeBuffer the destination buffer (not null)
     */
    @Override
    public void put(FloatBuffer storeBuffer) {
        storeBuffer.put(x);
        storeBuffer.put(y);
        storeBuffer.put(z);
        storeBuffer.put(w);
    }

    /**
     * Apply the rotation to (1,0,0). The quaternion is assumed to be normalized
     * and is unaffected.
     *
     * @return a new vector
     */
    @Override
    public Vec3 rotateAxisX() {
        assert isNormalized();

        float tx = x + x;
        float tw = w + w;
        float vx = tx * x + tw * w - 1f;
        float vy = tx * y + tw * z;
        float vz = tx * z - tw * y;
        Vec3 result = new Vec3(vx, vy, vz);

        return result;
    }

    /**
     * Apply the rotation to (0,1,0). The quaternion is assumed to be normalized
     * and is unaffected.
     *
     * @return a new vector
     */
    @Override
    public Vec3 rotateAxisY() {
        assert isNormalized();

        float ty = y + y;
        float tw = w + w;
        float vx = ty * x - tw * z;
        float vy = ty * y + tw * w - 1f;
        float vz = ty * z + tw * x;
        Vec3 result = new Vec3(vx, vy, vz);

        return result;
    }

    /**
     * Apply the rotation to (0,0,1). The quaternion is assumed to be normalized
     * and is unaffected.
     *
     * @return a new vector
     */
    @Override
    public Vec3 rotateAxisZ() {
        assert isNormalized();

        float tz = z + z;
        float tw = w + w;
        float vx = tz * x + tw * y;
        float vy = tz * y - tw * x;
        float vz = tz * z + tw * w - 1f;
        Vec3 result = new Vec3(vx, vy, vz);

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
