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
package com.github.stephengold.joltjni.operator;

import com.github.stephengold.joltjni.Quat;
import com.github.stephengold.joltjni.RVec3;
import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.readonly.ConstBodyId;
import com.github.stephengold.joltjni.readonly.ConstColor;
import com.github.stephengold.joltjni.readonly.Mat44Arg;
import com.github.stephengold.joltjni.readonly.QuatArg;
import com.github.stephengold.joltjni.readonly.RVec3Arg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * Java equivalents for the overloaded operators of Jolt Physics.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class Op {
    // *************************************************************************
    // constructors

    /**
     * A private constructor to inhibit instantiation of this class.
     */
    private Op() {
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the component-wise sum of the specified vectors. (native operator:
     * binary {@code +})
     *
     * @param left the base vector (not null, unaffected)
     * @param right the offset to add (not null, unaffected)
     * @return a new vector
     */
    public static RVec3 add(RVec3Arg left, Vec3Arg right) {
        double xx = left.xx() + right.getX();
        double yy = left.yy() + right.getY();
        double zz = left.zz() + right.getZ();
        RVec3 result = new RVec3(xx, yy, zz);

        return result;
    }

    /**
     * Return the component-wise sum of the specified vectors. (native operator:
     * binary {@code +})
     *
     * @param v1 the first vector (not null, unaffected)
     * @param v2 the 2nd vector (not null, unaffected)
     * @return a new vector
     */
    public static Vec3 add(Vec3Arg v1, Vec3Arg v2) {
        float x = v1.getX() + v2.getX();
        float y = v1.getY() + v2.getY();
        float z = v1.getZ() + v2.getZ();
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Return a scaled version of the specified vector. (native operator: binary
     * {@code /})
     *
     * @param v the input vector (not null, unaffected)
     * @param scale the inverse scale to apply
     * @return a new vector
     */
    public static Vec3 divide(Vec3Arg v, float scale) {
        float x = v.getX() / scale;
        float y = v.getY() / scale;
        float z = v.getZ() / scale;
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Divide the left argument by the right argument. (native operator: binary
     * {@code /=})
     *
     * @param left the accumulating vector (not null, modified)
     * @param right the denominator (not null, unaffected)
     */
    public static void divideEquals(RVec3 left, double right) {
        double xx = left.xx() / right;
        double yy = left.yy() / right;
        double zz = left.zz() / right;
        left.set(xx, yy, zz);
    }

    /**
     * Test whether the specified IDs are equal. (native operator: {@code ==})
     *
     * @param id1 the first ID to test (not null, unaffected)
     * @param id2 the 2nd ID to test (not null, unaffected)
     * @return true if equal, false if unequal
     */
    public static boolean equals(ConstBodyId id1, ConstBodyId id2) {
        boolean result = id1.isEqual(id2);
        return result;
    }

    /**
     * Test whether the specified colors are equal. (native operator:
     * {@code ==})
     *
     * @param c1 the first color to test (not null, unaffected)
     * @param c2 the 2nd color to test (not null, unaffected)
     * @return true if equal, false if unequal
     */
    public static boolean equals(ConstColor c1, ConstColor c2) {
        boolean result = c1.getA() == c2.getA()
                && c1.getB() == c2.getB()
                && c1.getG() == c2.getG()
                && c1.getR() == c2.getR();
        return result;
    }

    /**
     * Test whether the specified matrices are equal. (native operator:
     * {@code ==})
     *
     * @param m1 the first matrix (not null, unaffected)
     * @param m2 the 2nd matrix (not null, unaffected)
     * @return true if equal, otherwise false
     */
    public static boolean equals(Mat44Arg m1, Mat44Arg m2) {
        boolean result = m1.isEqual(m2);
        return result;
    }

    /**
     * Subtract the 2nd argument from the first argument. (native operator:
     * binary {@code -=})
     *
     * @param left the accumulating vector (not null, modified)
     * @param right the vector to subtract (not null, unaffected)
     */
    public static void minusEquals(Vec3 left, Vec3Arg right) {
        left.setX(left.getX() - right.getX());
        left.setY(left.getY() - right.getY());
        left.setZ(left.getZ() - right.getZ());
    }

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
     * Return a scaled version of the specified vector. (native operator: binary
     * {@code *})
     *
     * @param scale the scale to apply
     * @param v the input vector (not null, unaffected)
     * @return a new vector
     */
    public static Vec3 multiply(float scale, Vec3Arg v) {
        float x = scale * v.getX();
        float y = scale * v.getY();
        float z = scale * v.getZ();
        Vec3 result = new Vec3(x, y, z);

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
     * Return a scaled version of the specified vector. (native operator: binary
     * {@code *})
     *
     * @param v the input vector (not null, unaffected)
     * @param scale the scale to apply
     * @return a new vector
     */
    public static Vec3 multiply(Vec3Arg v, float scale) {
        float x = scale * v.getX();
        float y = scale * v.getY();
        float z = scale * v.getZ();
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Return the component-wise product of the specified vectors. (native
     * operator: binary {@code *})
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
     * Return the negative of the specified vector. (native operator: unary
     * {@code -})
     *
     * @param v the input vector (not null, unaffected)
     * @return a new vector
     */
    public static Vec3 negate(Vec3Arg v) {
        float x = -v.getX();
        float y = -v.getY();
        float z = -v.getZ();
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Test whether the specified colors are unequal. (native operator:
     * {@code !=})
     *
     * @param c1 the first color to test (not null, unaffected)
     * @param c2 the 2nd color to test (not null, unaffected)
     * @return false if equal, true if unequal
     */
    public static boolean notEqual(ConstColor c1, ConstColor c2) {
        boolean result = c1.getA() != c2.getA()
                || c1.getB() != c2.getB()
                || c1.getG() != c2.getG()
                || c1.getR() != c2.getR();
        return result;
    }

    /**
     * Add the right argument to the left argument. (native operator: binary
     * {@code +=})
     *
     * @param left the accumulating vector (not null, modified)
     * @param right the vector to add (not null, unaffected)
     */
    public static void plusEquals(RVec3 left, RVec3Arg right) {
        double xx = left.xx() + right.xx();
        double yy = left.yy() + right.yy();
        double zz = left.zz() + right.zz();
        left.set(xx, yy, zz);
    }

    /**
     * Add the right argument to the left argument. (native operator: binary
     * {@code +=})
     *
     * @param left the accumulating vector (not null, modified)
     * @param right the vector to add (not null, unaffected)
     */
    public static void plusEquals(RVec3 left, Vec3Arg right) {
        double xx = left.xx() + right.getX();
        double yy = left.yy() + right.getY();
        double zz = left.zz() + right.getZ();
        left.set(xx, yy, zz);
    }

    /**
     * Add the 2nd argument to the first argument. (native operator: binary
     * {@code +=})
     *
     * @param left the accumulating vector (not null, modified)
     * @param right the vector to add (not null, unaffected)
     */
    public static void plusEquals(Vec3 left, Vec3Arg right) {
        left.setX(left.getX() + right.getX());
        left.setY(left.getY() + right.getY());
        left.setZ(left.getZ() + right.getZ());
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
     * Return the component-wise difference of the specified vectors. (native
     * operator: binary {@code -})
     *
     * @param left the base vector (not null, unaffected)
     * @param right the offset to subtract (not null, unaffected)
     * @return a new vector
     */
    public static RVec3 subtract(RVec3Arg left, RVec3Arg right) {
        double xx = left.xx() - right.xx();
        double yy = left.yy() - right.yy();
        double zz = left.zz() - right.zz();
        RVec3 result = new RVec3(xx, yy, zz);

        return result;
    }

    /**
     * Return the component-wise difference of the specified vectors. (native
     * operator: binary {@code -})
     *
     * @param left the base vector (not null, unaffected)
     * @param right the offset to subtract (not null, unaffected)
     * @return a new vector
     */
    public static RVec3 subtract(RVec3Arg left, Vec3Arg right) {
        double xx = left.xx() - right.getX();
        double yy = left.yy() - right.getY();
        double zz = left.zz() - right.getZ();
        RVec3 result = new RVec3(xx, yy, zz);

        return result;
    }

    /**
     * Return the component-wise difference of the specified vectors. (native
     * operator: binary {@code -})
     *
     * @param v1 the first vector (not null, unaffected)
     * @param v2 the vector to subtract (not null, unaffected)
     * @return a new vector
     */
    public static Vec3 subtract(Vec3Arg v1, Vec3Arg v2) {
        float x = v1.getX() - v2.getX();
        float y = v1.getY() - v2.getY();
        float z = v1.getZ() - v2.getZ();
        Vec3 result = new Vec3(x, y, z);

        return result;
    }
}
