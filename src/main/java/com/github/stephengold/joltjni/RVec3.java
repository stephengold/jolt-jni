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
import com.github.stephengold.joltjni.readonly.RMat44Arg;
import com.github.stephengold.joltjni.readonly.RVec3Arg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * A vector composed of 3 double-precision components, used to represent
 * locations in 3-dimensional space.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class RVec3 implements RVec3Arg {
    // *************************************************************************
    // fields

    /**
     * the first (X) component
     */
    private double xx;
    /**
     * the 2nd (Y) component
     */
    private double yy;
    /**
     * the 3rd (Z) component
     */
    private double zz;
    // *************************************************************************
    // constructors

    /**
     * Instantiate an all-zero vector (0,0,0).
     */
    public RVec3() {
        this.xx = 0.;
        this.yy = 0.;
        this.zz = 0.;
    }

    /**
     * Instantiate a vector with the specified components.
     *
     * @param x the desired X component
     * @param y the desired Y component
     * @param z the desired Z component
     */
    public RVec3(double x, double y, double z) {
        this.xx = x;
        this.yy = y;
        this.zz = z;
    }

    /**
     * Instantiate from the specified array.
     *
     * @param array the desired component values (not null, length&ge;3,
     * unaffected)
     */
    public RVec3(double[] array) {
        this.xx = array[0];
        this.yy = array[1];
        this.zz = array[2];
    }

    /**
     * Instantiate from a {@code Float3}.
     *
     * @param float3 the desired component values (not null, unaffected)
     */
    public RVec3(Float3 float3) {
        this.xx = float3.x;
        this.yy = float3.y;
        this.zz = float3.z;
    }

    /**
     * Instantiate a copy of the argument.
     *
     * @param vec the vector to copy (not null, unaffected)
     */
    public RVec3(RVec3Arg vec) {
        this.xx = vec.xx();
        this.yy = vec.yy();
        this.zz = vec.zz();
    }

    /**
     * Instantiate from a single-precision vector.
     *
     * @param vec the vector to convert (not null, unaffected)
     */
    public RVec3(Vec3Arg vec) {
        this.xx = vec.getX();
        this.yy = vec.getY();
        this.zz = vec.getZ();
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
    public void addInPlace(double xOffset, double yOffset, double zOffset) {
        this.xx += xOffset;
        this.yy += yOffset;
        this.zz += zOffset;
    }

    /**
     * Set all components to 1.
     */
    public void loadOne() {
        this.xx = 1.;
        this.yy = 1.;
        this.zz = 1.;
    }

    /**
     * Set all components to 0.
     */
    public void loadZero() {
        this.xx = 0.;
        this.yy = 0.;
        this.zz = 0.;
    }

    /**
     * Change the current vector to a unit vector with the same direction.
     */
    public void normalizeInPlace() {
        double invLength = 1. / length();
        this.xx *= invLength;
        this.yy *= invLength;
        this.zz *= invLength;
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

        double rx = xx;
        double ry = yy;
        double rz = zz;

        // a = lhs x pure(rhs)
        double aw = -lx * rx - ly * ry - lz * rz;
        double ax = lw * rx + ly * rz - lz * ry;
        double ay = lw * ry - lx * rz + lz * rx;
        double az = lw * rz + lx * ry - ly * rx;

        // result = vec3(a x conjugate(lhs))
        this.xx = -aw * lx + ax * lw - ay * lz + az * ly;
        this.yy = -aw * ly + ax * lz + ay * lw - az * lx;
        this.zz = -aw * lz - ax * ly + ay * lx + az * lw;
    }

    /**
     * Create a unit vector along the 1st (X) principal axis.
     *
     * @return a new vector
     */
    public static RVec3 sAxisX() {
        RVec3 result = new RVec3(1., 0., 0.);
        return result;
    }

    /**
     * Create a unit vector along the 2nd (Y) principal axis.
     *
     * @return a new vector
     */
    public static RVec3 sAxisY() {
        RVec3 result = new RVec3(0., 1., 0.);
        return result;
    }

    /**
     * Create a unit vector along the 3rd (Z) principal axis.
     *
     * @return a new vector
     */
    public static RVec3 sAxisZ() {
        RVec3 result = new RVec3(0., 0., 1.);
        return result;
    }

    /**
     * Separately scale each component.
     *
     * @param xScale the scale factor to apply to the X component
     * @param yScale the scale factor to apply to the Y component
     * @param zScale the scale factor to apply to the Z component
     */
    public void scaleInPlace(double xScale, double yScale, double zScale) {
        this.xx *= xScale;
        this.yy *= yScale;
        this.zz *= zScale;
    }

    /**
     * Set all 3 components to specified values.
     *
     * @param x the desired X component
     * @param y the desired Y component
     * @param z the desired Z component
     */
    public void set(double x, double y, double z) {
        this.xx = x;
        this.yy = y;
        this.zz = z;
    }

    /**
     * Set all 3 components from the specified array.
     *
     * @param array the desired component values (not null, length&ge;3,
     * unaffected)
     */
    public void set(double[] array) {
        this.xx = array[0];
        this.yy = array[1];
        this.zz = array[2];
    }

    /**
     * Set all 3 components from the argument.
     *
     * @param source the vector to copy (not null, unaffected)
     */
    public void set(RVec3Arg source) {
        this.xx = source.xx();
        this.yy = source.yy();
        this.zz = source.zz();
    }

    /**
     * Set all 3 components from the specified single-precision vector.
     *
     * @param source the vector to copy (not null, unaffected)
     */
    public void set(Vec3Arg source) {
        this.xx = source.getX();
        this.yy = source.getY();
        this.zz = source.getZ();
    }

    /**
     * Alter the first (X) component.
     *
     * @param x the desired value
     * @return the modified vector, for chaining
     */
    public RVec3 setX(double x) {
        this.xx = x;
        return this;
    }

    /**
     * Alter the 2nd (Y) component.
     *
     * @param y the desired value
     * @return the modified vector, for chaining
     */
    public RVec3 setY(double y) {
        this.yy = y;
        return this;
    }

    /**
     * Alter the 3rd (Z) component.
     *
     * @param z the desired value
     * @return the modified vector, for chaining
     */
    public RVec3 setZ(double z) {
        this.zz = z;
        return this;
    }

    /**
     * Create a vector with all components identical.
     *
     * @param value the desired component value
     * @return a new vector
     */
    public static RVec3 sReplicate(double value) {
        RVec3 result = new RVec3(value, value, value);
        return result;
    }

    /**
     * Return the component-wise sum of the specified vectors.
     *
     * @param vArray an array of input vectors (not null, unaffected)
     * @return a new vector
     */
    public static RVec3 sum(RVec3Arg... vArray) {
        RVec3 result = new RVec3();
        for (RVec3Arg arg : vArray) {
            Op.plusEquals(result, arg);
        }

        return result;
    }

    /**
     * Create a vector with all components zero.
     *
     * @return a new vector
     */
    public static RVec3 sZero() {
        RVec3 result = new RVec3();
        return result;
    }

    /**
     * Transform the current vector by the specified matrix.
     *
     * @param matrix the transformation to apply (not null, unaffected)
     */
    public void transformInPlace(RMat44Arg matrix) {
        RVec3Arg temp = matrix.multiply3x4(this); // TODO garbage
        set(temp);
    }
    // *************************************************************************
    // RVec3Arg methods

    /**
     * Return the cross product with the argument. Both vectors are unaffected.
     *
     * @param rightFactor the vector to cross with the current one (not null,
     * unaffected)
     * @return a new product vector
     */
    @Override
    public RVec3 cross(RVec3Arg rightFactor) {
        double rx = rightFactor.xx();
        double ry = rightFactor.yy();
        double rz = rightFactor.zz();

        double px = yy * rz - zz * ry;
        double py = zz * rx - xx * rz;
        double pz = xx * ry - yy * rx;

        RVec3 result = new RVec3(px, py, pz);
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
    public double dot(RVec3Arg factor) {
        double result = xx * factor.xx() + yy * factor.yy() + zz * factor.zz();
        return result;
    }

    /**
     * Return the specified component in double precision. The vector is
     * unaffected.
     *
     * @param index 0, 1, or 2
     * @return the X component if index=0, the Y component if index=1, or the Z
     * component if index=2
     * @throws IllegalArgumentException if index is not 0, 1, or 2
     */
    @Override
    public double get(int index) {
        switch (index) {
            case 0:
                return xx;
            case 1:
                return yy;
            case 2:
                return zz;
            default:
                throw new IllegalArgumentException("index must be 0, 1 or 2");
        }
    }

    /**
     * Return the first (X) component at positional precision. The vector is
     * unaffected.
     *
     * @return the component value
     */
    @Override
    public Object getX() {
        Object result;
        if (Jolt.isDoublePrecision()) {
            result = xx;
        } else {
            result = (float) xx;
        }

        return result;
    }

    /**
     * Return the 2nd (Y) component at positional precision. The vector is
     * unaffected.
     *
     * @return the component value
     */
    @Override
    public Object getY() {
        Object result;
        if (Jolt.isDoublePrecision()) {
            result = yy;
        } else {
            result = (float) yy;
        }

        return result;
    }

    /**
     * Return the 3rd (Z) component at positional precision. The vector is
     * unaffected.
     *
     * @return the component value
     */
    @Override
    public Object getZ() {
        Object result;
        if (Jolt.isDoublePrecision()) {
            result = zz;
        } else {
            result = (float) zz;
        }

        return result;
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
        boolean result = Double.isFinite(xx)
                && Double.isFinite(yy) && Double.isFinite(zz);
        return result;
    }

    /**
     * Test whether the vector contains NaNs. The vector is unaffected.
     *
     * @return {@code true} if one or more NaNs, otherwise {@code false}
     */
    @Override
    public boolean isNan() {
        boolean result
                = Double.isNaN(xx) || Double.isNaN(yy) || Double.isNaN(zz);
        return result;
    }

    /**
     * Test whether the squared length is within 10^-12 (single-precision) or
     * 10^-24 (double-precision) of zero. The vector is unaffected.
     *
     * @return {@code true} if nearly zero, otherwise {@code false}
     */
    @Override
    public boolean isNearZero() {
        double tolerance = Jolt.isDoublePrecision() ? 1e-24 : 1e-12;
        boolean result = isNearZero(tolerance);
        return result;
    }

    /**
     * Test whether the squared length is within the specified tolerance of
     * zero. The vector is unaffected.
     *
     * @param tolerance the desired tolerance (&ge;0, default=1e-12 or 1e-24)
     * @return {@code true} if nearly zero, otherwise {@code false}
     */
    @Override
    public boolean isNearZero(double tolerance) {
        double lengthSq = lengthSq();
        if (lengthSq <= tolerance) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Test whether the vector is normalized to within a tolerance of 10^-6
     * (single precision) or 10^-12 (double precision). The vector is
     * unaffected.
     *
     * @return {@code true} if normalized, otherwise {@code false}
     */
    @Override
    public boolean isNormalized() {
        double tolerance = Jolt.isDoublePrecision() ? 1e-12 : 1e-6;
        boolean result = isNormalized(tolerance);
        return result;
    }

    /**
     * Test whether the vector is normalized to within the specified tolerance.
     * The vector is unaffected.
     *
     * @param tolerance the desired tolerance (&ge;0, default=1e-6 or 1e-12)
     * @return {@code true} if normalized, otherwise {@code false}
     */
    @Override
    public boolean isNormalized(double tolerance) {
        double lengthSq = lengthSq();
        if (Math.abs(lengthSq - 1.) <= tolerance) {
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
    public double length() {
        double lengthSq = lengthSq();
        double result = Math.sqrt(lengthSq);

        return result;
    }

    /**
     * Return the squared length. The vector is unaffected.
     *
     * @return the squared length
     */
    @Override
    public double lengthSq() {
        double result = xx * xx + yy * yy + zz * zz;
        return result;
    }

    /**
     * Generate a unit vector with the same direction. The current vector is
     * unaffected.
     *
     * @return a new vector
     */
    @Override
    public RVec3 normalized() {
        RVec3 result = Op.star(1. / length(), this);
        return result;
    }

    /**
     * Generate the component-wise reciprocal. The current vector is unaffected.
     *
     * @return a new vector
     */
    @Override
    public RVec3 reciprocal() {
        RVec3 result = new RVec3(1. / xx, 1. / yy, 1. / zz);
        return result;
    }

    /**
     * Copy the components to an array. The vector is unaffected.
     *
     * @return a new array with length=3
     */
    @Override
    public double[] toArray() {
        double[] result = new double[3];
        result[0] = xx;
        result[1] = yy;
        result[2] = zz;

        return result;
    }

    /**
     * Convert to single-precision vector. The current vector is unaffected.
     *
     * @return a new vector
     */
    @Override
    public Vec3 toVec3() {
        Vec3 result = new Vec3(xx, yy, zz);
        return result;
    }

    /**
     * Return the first (X) component in single precision. The vector is
     * unaffected.
     *
     * @return the component value
     */
    @Override
    public float x() {
        float result = (float) xx;
        return result;
    }

    /**
     * Return the first (X) component in double precision. The vector is
     * unaffected.
     *
     * @return the component value
     */
    @Override
    public double xx() {
        return xx;
    }

    /**
     * Return the 2nd (Y) component in single precision. The vector is
     * unaffected.
     *
     * @return the component value
     */
    @Override
    public float y() {
        float result = (float) yy;
        return result;
    }

    /**
     * Return the 2nd (Y) component in double precision. The vector is
     * unaffected.
     *
     * @return the component value
     */
    @Override
    public double yy() {
        return yy;
    }

    /**
     * Return the 3rd (Z) component in single precision. The vector is
     * unaffected.
     *
     * @return the component value
     */
    @Override
    public float z() {
        float result = (float) zz;
        return result;
    }

    /**
     * Return the 3rd (Z) component in double precision. The vector is
     * unaffected.
     *
     * @return the component value
     */
    @Override
    public double zz() {
        return zz;
    }
    // *************************************************************************
    // Object methods

    /**
     * Return a string representation of the vector, which is unaffected. For
     * example, a zero vector is represented by:
     * <pre>
     * RVec3(0.0 0.0 0.0)
     * </pre>
     *
     * @return the string representation (not null, not empty)
     */
    @Override
    public String toString() {
        String result = "RVec3(" + getX() + " " + getY() + " " + getZ() + ")";
        return result;
    }
}
