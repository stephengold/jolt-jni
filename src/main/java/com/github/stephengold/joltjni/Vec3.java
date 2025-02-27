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
import com.github.stephengold.joltjni.readonly.Mat44Arg;
import com.github.stephengold.joltjni.readonly.QuatArg;
import com.github.stephengold.joltjni.readonly.RVec3Arg;
import com.github.stephengold.joltjni.readonly.UVec4Arg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import com.github.stephengold.joltjni.std.RandomNumberEngine;
import com.github.stephengold.joltjni.std.UniformFloatDistribution;
import java.nio.FloatBuffer;
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
    /**
     * lazily allocated distribution, used in randomization
     */
    private static UniformFloatDistribution distro = null;
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
     * Instantiate a vector with the specified components.
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
     * Instantiate a vector with the specified components.
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
     * Instantiate from the specified array.
     *
     * @param array the desired component values (not null, length&ge;3,
     * unaffected)
     */
    public Vec3(float[] array) {
        this.x = array[0];
        this.y = array[1];
        this.z = array[2];
    }

    /**
     * Instantiate from a {@code Float3}.
     *
     * @param float3 the desired component values (not null, unaffected)
     */
    public Vec3(Float3 float3) {
        this.x = float3.x;
        this.y = float3.y;
        this.z = float3.z;
    }

    /**
     * Instantiate from a location vector.
     *
     * @param vec the vector to convert (not null, unaffected)
     */
    public Vec3(RVec3Arg vec) {
        this.x = vec.x();
        this.y = vec.y();
        this.z = vec.z();
    }

    /**
     * Instantiate a copy of the argument.
     *
     * @param vec the vector to copy (not null, unaffected)
     */
    public Vec3(Vec3Arg vec) {
        this.x = vec.getX();
        this.y = vec.getY();
        this.z = vec.getZ();
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Add the specified offsets.
     *
     * @param xOffset the amount to add to the X component
     * @param yOffset the amount to add to the Y component
     * @param zOffset the amount to add to the Z component
     */
    public void addInPlace(float xOffset, float yOffset, float zOffset) {
        this.x += xOffset;
        this.y += yOffset;
        this.z += zOffset;
    }

    /**
     * Set all components to 1.
     */
    public void loadOne() {
        this.x = 1f;
        this.y = 1f;
        this.z = 1f;
    }

    /**
     * Set all components to 0.
     */
    public void loadZero() {
        this.x = 0f;
        this.y = 0f;
        this.z = 0f;
    }

    /**
     * Change the current vector to a unit vector with the same direction.
     */
    public void normalizeInPlace() {
        float invLength = 1f / length();
        this.x *= invLength;
        this.y *= invLength;
        this.z *= invLength;
    }

    /**
     * Rotate the current vector by the specified quaternion.
     *
     * @param rotation the rotation to apply (not null, normalized, unaffected)
     */
    public void rotateInPlace(QuatArg rotation) {
        assert rotation.isNormalized();

        float lw = rotation.getW();
        float lx = rotation.getX();
        float ly = rotation.getY();
        float lz = rotation.getZ();

        float rx = x;
        float ry = y;
        float rz = z;

        // a = lhs x pure(rhs)
        float aw = -lx * rx - ly * ry - lz * rz;
        float ax = lw * rx + ly * rz - lz * ry;
        float ay = lw * ry - lx * rz + lz * rx;
        float az = lw * rz + lx * ry - ly * rx;

        // result = vec3(a x conjugate(lhs))
        this.x = -aw * lx + ax * lw - ay * lz + az * ly;
        this.y = -aw * ly + ax * lz + ay * lw - az * lx;
        this.z = -aw * lz - ax * ly + ay * lx + az * lw;
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
     * Create a unit vector along the 1st (X) principal axis.
     *
     * @return a new vector
     */
    public static Vec3 sAxisX() {
        Vec3 result = new Vec3(1f, 0f, 0f);
        return result;
    }

    /**
     * Create a unit vector along the 2nd (Y) principal axis.
     *
     * @return a new vector
     */
    public static Vec3 sAxisY() {
        Vec3 result = new Vec3(0f, 1f, 0f);
        return result;
    }

    /**
     * Create a unit vector along the 3rd (Z) principal axis.
     *
     * @return a new vector
     */
    public static Vec3 sAxisZ() {
        Vec3 result = new Vec3(0f, 0f, 1f);
        return result;
    }

    /**
     * Separately scale each component.
     *
     * @param xScale the scale factor to apply to the X component
     * @param yScale the scale factor to apply to the Y component
     * @param zScale the scale factor to apply to the Z component
     */
    public void scaleInPlace(float xScale, float yScale, float zScale) {
        this.x *= xScale;
        this.y *= yScale;
        this.z *= zScale;
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
     * Set all 3 components from the specified location vector.
     *
     * @param source the vector to copy (not null, unaffected)
     */
    public void set(RVec3Arg source) {
        this.x = source.x();
        this.y = source.y();
        this.z = source.z();
    }

    /**
     * Set all 3 components from the argument.
     *
     * @param source the vector to copy (not null, unaffected)
     */
    public void set(Vec3Arg source) {
        this.x = source.getX();
        this.y = source.getY();
        this.z = source.getZ();
    }

    /**
     * Alter the first (X) component.
     *
     * @param x the desired value
     * @return the modified vector, for chaining
     */
    public Vec3 setX(float x) {
        this.x = x;
        return this;
    }

    /**
     * Alter the 2nd (Y) component.
     *
     * @param y the desired value
     * @return the modified vector, for chaining
     */
    public Vec3 setY(float y) {
        this.y = y;
        return this;
    }

    /**
     * Alter the 3rd (Z) component.
     *
     * @param z the desired value
     * @return the modified vector, for chaining
     */
    public Vec3 setZ(float z) {
        this.z = z;
        return this;
    }

    /**
     * Component-wise comparison of 2 vectors.
     *
     * @param v1 the first vector (not null, unaffected)
     * @param v2 the 2nd vector (not null, unaffected)
     * @return a new vector (each component 0 or -1)
     */
    public static UVec4 sGreater(Vec3Arg v1, Vec3Arg v2) {
        int x = (v1.getX() > v2.getX()) ? -1 : 0;
        int y = (v1.getY() > v2.getY()) ? -1 : 0;
        int z = (v1.getZ() > v2.getZ()) ? -1 : 0;
        UVec4 result = new UVec4(x, y, z, z);

        return result;
    }

    /**
     * Component-wise comparison of 2 vectors.
     *
     * @param v1 the first vector (not null, unaffected)
     * @param v2 the 2nd vector (not null, unaffected)
     * @return a new vector (each component 0 or -1)
     */
    public static UVec4 sGreaterOrEqual(Vec3Arg v1, Vec3Arg v2) {
        int x = (v1.getX() >= v2.getX()) ? -1 : 0;
        int y = (v1.getY() >= v2.getY()) ? -1 : 0;
        int z = (v1.getZ() >= v2.getZ()) ? -1 : 0;
        UVec4 result = new UVec4(x, y, z, z);

        return result;
    }

    /**
     * Component-wise comparison of 2 vectors.
     *
     * @param v1 the first vector (not null, unaffected)
     * @param v2 the 2nd vector (not null, unaffected)
     * @return a new vector (each component 0 or -1)
     */
    public static UVec4 sLess(Vec3Arg v1, Vec3Arg v2) {
        int x = (v1.getX() < v2.getX()) ? -1 : 0;
        int y = (v1.getY() < v2.getY()) ? -1 : 0;
        int z = (v1.getZ() < v2.getZ()) ? -1 : 0;
        UVec4 result = new UVec4(x, y, z, z);

        return result;
    }

    /**
     * Component-wise comparison of 2 vectors.
     *
     * @param v1 the first vector (not null, unaffected)
     * @param v2 the 2nd vector (not null, unaffected)
     * @return a new vector (each component 0 or -1)
     */
    public static UVec4 sLessOrEqual(Vec3Arg v1, Vec3Arg v2) {
        int x = (v1.getX() <= v2.getX()) ? -1 : 0;
        int y = (v1.getY() <= v2.getY()) ? -1 : 0;
        int z = (v1.getZ() <= v2.getZ()) ? -1 : 0;
        UVec4 result = new UVec4(x, y, z, z);

        return result;
    }

    /**
     * Create a vector with all components set to one.
     *
     * @return a new vector
     */
    public static Vec3 sOne() {
        Vec3 result = new Vec3(1, 1, 1);
        return result;
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

    /**
     * Generate a pseudo-random unit vector.
     * <p>
     * The results are not uniformly distributed over the unit sphere.
     *
     * @param engine the generator to use (not null)
     * @return a new unit vector
     */
    public static Vec3 sRandom(RandomNumberEngine engine) {
        assert engine != null;
        if (distro == null) {
            distro = new UniformFloatDistribution(0f, 1f);
        }

        float theta = Jolt.JPH_PI * distro.nextFloat(engine);
        float phi = 2f * Jolt.JPH_PI * distro.nextFloat(engine);
        Vec3 result = sUnitSpherical(theta, phi);

        return result;
    }

    /**
     * Create a vector with all components identical.
     *
     * @param value the desired component value
     * @return a new vector
     */
    public static Vec3 sReplicate(float value) {
        Vec3 result = new Vec3(value, value, value);
        return result;
    }

    /**
     * Component-wise selection between 2 specified vectors.
     *
     * @param notSet components to select where the control is zero (not null,
     * unaffected)
     * @param set components to select where the control is non-zero (not null,
     * unaffected)
     * @param control to control the selection (not null, unaffected)
     * @return a new vector
     */
    public static Vec3 sSelect(Vec3Arg notSet, Vec3Arg set, UVec4Arg control) {
        float x = (control.getX() == 0) ? notSet.getX() : set.getX();
        float y = (control.getY() == 0) ? notSet.getY() : set.getY();
        float z = (control.getZ() == 0) ? notSet.getZ() : set.getZ();

        Vec3 result = new Vec3(x, y, z);
        return result;
    }

    /**
     * Replace any -0 elements with +0, in preparation for hashing.
     */
    public void standardizeInPlace() {
        if (Float.compare(x, -0f) == 0) {
            this.x = 0f;
        }
        if (Float.compare(y, -0f) == 0) {
            this.y = 0f;
        }
        if (Float.compare(z, -0f) == 0) {
            this.z = 0f;
        }
    }

    /**
     * Return the component-wise sum of the specified vectors.
     *
     * @param vArray an array of input vectors (not null, unaffected)
     * @return a new vector
     */
    public static Vec3 sum(Vec3Arg... vArray) {
        Vec3 result = new Vec3();
        for (Vec3Arg arg : vArray) {
            Op.plusEquals(result, arg);
        }

        return result;
    }

    /**
     * Generate a unit vector with the specified spherical coordinates.
     *
     * @param theta angle from the +Z axis (in radians)
     * @param phi angle from the +X axis in the X-Y plane (in radians)
     * @return a new unit vector
     */
    public static Vec3 sUnitSpherical(float theta, float phi) {
        float sinTheta = Jolt.sin(theta);
        float vx = sinTheta * Jolt.cos(phi);
        float vy = sinTheta * Jolt.sin(phi);
        float vz = Jolt.cos(theta);
        Vec3 result = new Vec3(vx, vy, vz);

        return result;
    }

    /**
     * Create a vector with all components zero.
     *
     * @return a new vector
     */
    public static Vec3 sZero() {
        Vec3 result = new Vec3();
        return result;
    }

    /**
     * Transform the current vector by the specified matrix.
     *
     * @param matrix the transformation to apply (not null, unaffected)
     */
    public void transformInPlace(Mat44Arg matrix) {
        Vec3Arg temp = matrix.multiply3x4(this); // TODO garbage
        set(temp);
    }
    // *************************************************************************
    // Vec3Arg methods

    /**
     * Return the cross product with the argument. Both vectors are unaffected.
     *
     * @param rightFactor the vector to cross with the current one (not null,
     * unaffected)
     * @return a new product vector
     */
    @Override
    public Vec3 cross(Vec3Arg rightFactor) {
        float rx = rightFactor.getX();
        float ry = rightFactor.getY();
        float rz = rightFactor.getZ();

        float px = y * rz - z * ry;
        float py = z * rx - x * rz;
        float pz = x * ry - y * rx;

        Vec3 result = new Vec3(px, py, pz);
        return result;
    }

    /**
     * Return the dot product with the argument. Both vectors are unaffected.
     *
     * @param factor the vector to dot with the current one (not null,
     * unaffected)
     * @return the dot product
     */
    @Override
    public float dot(Vec3Arg factor) {
        float result
                = x * factor.getX() + y * factor.getY() + z * factor.getZ();
        return result;
    }

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
                throw new IllegalArgumentException("index must be 0, 1 or 2");
        }
    }

    /**
     * Return an arbitrary unit vector perpendicular to the current vector. The
     * current vector is unaffected.
     *
     * @return a new vector
     */
    @Override
    public Vec3 getNormalizedPerpendicular() {
        if (Math.abs(x) > Math.abs(y)) {
            float len = (float) Math.sqrt(x * x + z * z);
            return new Vec3(z / len, 0f, -x / len);
        } else {
            float len = (float) Math.sqrt(y * y + z * z);
            return new Vec3(0f, z / len, -y / len);
        }
    }

    /**
     * Return the component-wise (binary) sign. The current vector is
     * unaffected.
     *
     * @return a new vector (each component 1 or -1)
     */
    @Override
    public Vec3 getSign() {
        float sx = (x < 0) ? -1f : 1f;
        float sy = (y < 0) ? -1f : 1f;
        float sz = (z < 0) ? -1f : 1f;
        Vec3 result = new Vec3(sx, sy, sz);

        return result;
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
     * Test whether the vector contains infinities or NaNs. The vector is
     * unaffected.
     *
     * @return {@code false} if one or more infinities or NaNs, otherwise
     * {@code true}
     */
    @Override
    public boolean isFinite() {
        boolean result
                = Float.isFinite(x) && Float.isFinite(y) && Float.isFinite(z);
        return result;
    }

    /**
     * Test whether the vector contains NaNs. The vector is unaffected.
     *
     * @return {@code true} if one or more NaNs, otherwise {@code false}
     */
    @Override
    public boolean isNan() {
        boolean result = Float.isNaN(x) || Float.isNaN(y) || Float.isNaN(z);
        return result;
    }

    /**
     * Test whether the squared length is within 10^-12 of zero. The vector is
     * unaffected.
     *
     * @return {@code true} if nearly zero, otherwise {@code false}
     */
    @Override
    public boolean isNearZero() {
        boolean result = isNearZero(1e-12f);
        return result;
    }

    /**
     * Test whether the squared length is within the specified tolerance of
     * zero. The vector is unaffected.
     *
     * @param tolerance the desired tolerance (&ge;0, default=1e-12)
     * @return {@code true} if nearly zero, otherwise {@code false}
     */
    @Override
    public boolean isNearZero(float tolerance) {
        float lengthSq = lengthSq();
        if (lengthSq <= tolerance) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Test whether the vector is normalized to within a tolerance of 10^-6. The
     * vector is unaffected.
     *
     * @return {@code true} if normalized, otherwise {@code false}
     */
    @Override
    public boolean isNormalized() {
        boolean result = isNormalized(1e-6f);
        return result;
    }

    /**
     * Test whether the vector is normalized to within the specified tolerance.
     * The vector is unaffected.
     *
     * @param tolerance the desired tolerance (&ge;0, default=1e-6)
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
     * Return the length. The vector is unaffected.
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
     * Return the squared length. The vector is unaffected.
     *
     * @return the squared length
     */
    @Override
    public float lengthSq() {
        float result = x * x + y * y + z * z;
        return result;
    }

    /**
     * Generate a unit vector with the same direction. The current vector is
     * unaffected.
     *
     * @return a new vector
     */
    @Override
    public Vec3 normalized() {
        Vec3 result = Op.star(1f / length(), this);
        return result;
    }

    /**
     * Return a copy of the argument if the length of the current vector is
     * zero. Otherwise, generate a unit vector with the same direction as the
     * current vector. The current vector is unaffected.
     *
     * @param zeroValue the value to return if the length is zero (not null,
     * unaffected)
     * @return a new vector
     */
    @Override
    public Vec3 normalizedOr(Vec3Arg zeroValue) {
        Vec3 result;

        float lengthSq = lengthSq();
        if (lengthSq == 0f) {
            result = new Vec3(zeroValue);
        } else {
            float length = (float) Math.sqrt(lengthSq);
            if (length == 0f) {
                result = new Vec3(zeroValue);
            } else {
                result = Op.star(1f / length, this);
            }
        }

        return result;
    }

    /**
     * Write all 3 components to the specified buffer and advance the buffer's
     * position by 3. The vector is unaffected.
     *
     * @param storeBuffer the destination buffer (not null)
     */
    @Override
    public void put(FloatBuffer storeBuffer) {
        storeBuffer.put(x);
        storeBuffer.put(y);
        storeBuffer.put(z);
    }

    /**
     * Generate the component-wise reciprocal. The current vector is unaffected.
     *
     * @return a new vector
     */
    @Override
    public Vec3 reciprocal() {
        Vec3 result = new Vec3(1f / x, 1f / y, 1f / z);
        return result;
    }

    /**
     * Return the maximum component. The current vector is unaffected.
     *
     * @return a component value
     */
    @Override
    public float reduceMax() {
        float result = Math.max(Math.max(x, y), z);
        return result;
    }

    /**
     * Return the minimum component. The current vector is unaffected.
     *
     * @return a component value
     */
    @Override
    public float reduceMin() {
        float result = Math.min(Math.min(x, y), z);
        return result;
    }

    /**
     * Copy to a {@code Float3} object. The vector is unaffected.
     *
     * @param target the destination (not null, modified)
     */
    @Override
    public void storeFloat3(Float3 target) {
        target.x = x;
        target.y = y;
        target.z = z;
    }

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
    @Override
    public Vec3 swizzle(int xi, int yi, int zi) {
        float rx = get(xi);
        float ry = get(yi);
        float rz = get(zi);
        Vec3 result = new Vec3(rx, ry, rz);

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

    /**
     * Copy the components to a new location vector. The current vector is
     * unaffected.
     *
     * @return a new vector
     */
    @Override
    public RVec3 toRVec3() {
        RVec3 result = new RVec3(x, y, z);
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
