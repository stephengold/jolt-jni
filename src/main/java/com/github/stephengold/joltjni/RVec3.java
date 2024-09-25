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
     * Instantiate a vector with specified components.
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
     * Instantiate from a single-precision vector.
     *
     * @param vec the vector to copy (not null, unaffected)
     */
    public RVec3(Vec3Arg vec) {
        this.xx = vec.getX();
        this.yy = vec.getY();
        this.zz = vec.getZ();
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the component-wise sum of the specified vectors. (native operator:
     * binary {@code +})
     *
     * @param v1 the first vector (not null, unaffected)
     * @param v2 the 2nd vector (not null, unaffected)
     * @return a new vector
     */
    public static RVec3 add(RVec3Arg v1, Vec3Arg v2) {
        double xx = v1.xx() + v2.getX();
        double yy = v1.yy() + v2.getY();
        double zz = v1.zz() + v2.getZ();
        RVec3 result = new RVec3(xx, yy, zz);

        return result;
    }

    /**
     * Adds specified amounts to the vector's components and returns the
     * (modified) current instance.
     *
     * @param addX the amount to add to the X component
     * @param addY the amount to add to the Y component
     * @param addZ the amount to add to the Z component
     * @return the (modified) current instance (for chaining)
     */
    public RVec3 addLocal(double addX, double addY, double addZ) {
        this.xx += addX;
        this.yy += addY;
        this.zz += addZ;

        return this;
    }

    /**
     * Test whether all 3 components are finite.
     *
     * @return true if all are finite, otherwise false
     */
    public boolean isFinite() {
        if (Double.isFinite(xx) && Double.isFinite(yy) && Double.isFinite(zz)) {
            return true;
        } else {
            return false;
        }
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
     * Return the component-wise difference of the specified vectors. (native
     * operator: binary {@code -})
     *
     * @param base the base vector (not null, unaffected)
     * @param offset the offset to subtract (not null, unaffected)
     * @return a new vector
     */
    public static RVec3 subtract(RVec3Arg base, Vec3Arg offset) {
        double xx = base.xx() - offset.getX();
        double yy = base.yy() - offset.getY();
        double zz = base.zz() - offset.getZ();
        RVec3 result = new RVec3(xx, yy, zz);

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
    // *************************************************************************
    // RVec3Arg methods

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
