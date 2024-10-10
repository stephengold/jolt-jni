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
     * @param left the first vector (not null, unaffected)
     * @param right the 2nd vector (not null, unaffected)
     * @return a new vector
     */
    public static RVec3 add(RVec3Arg left, RVec3Arg right) {
        double xx = left.xx() + right.xx();
        double yy = left.yy() + right.yy();
        double zz = left.zz() + right.zz();
        RVec3 result = new RVec3(xx, yy, zz);

        return result;
    }

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
     * @param left the first vector (not null, unaffected)
     * @param right the 2nd vector (not null, unaffected)
     * @return a new vector
     */
    public static Vec3 add(Vec3Arg left, Vec3Arg right) {
        float x = left.getX() + right.getX();
        float y = left.getY() + right.getY();
        float z = left.getZ() + right.getZ();
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Return a scaled version of the specified vector. (native operator: binary
     * {@code /})
     *
     * @param left the input vector (not null, unaffected)
     * @param right the inverse scale to apply
     * @return a new vector
     */
    public static Vec3 divide(Vec3Arg left, float right) {
        float x = left.getX() / right;
        float y = left.getY() / right;
        float z = left.getZ() / right;
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
     * @param left the first ID to test (not null, unaffected)
     * @param right the 2nd ID to test (not null, unaffected)
     * @return true if equal, false if unequal
     */
    public static boolean equals(ConstBodyId left, ConstBodyId right) {
        boolean result = left.isEqual(right);
        return result;
    }

    /**
     * Test whether the specified colors are equal. (native operator:
     * {@code ==})
     *
     * @param left the first color to test (not null, unaffected)
     * @param right the 2nd color to test (not null, unaffected)
     * @return true if equal, false if unequal
     */
    public static boolean equals(ConstColor left, ConstColor right) {
        boolean result = left.getA() == right.getA()
                && left.getB() == right.getB()
                && left.getG() == right.getG()
                && left.getR() == right.getR();
        return result;
    }

    /**
     * Test whether the specified matrices are equal. (native operator:
     * {@code ==})
     *
     * @param left the first matrix (not null, unaffected)
     * @param right the 2nd matrix (not null, unaffected)
     * @return true if equal, otherwise false
     */
    public static boolean equals(Mat44Arg left, Mat44Arg right) {
        boolean result = left.isEqual(right);
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
     * @param left the scale to apply
     * @param right the input quaternion (not null, unaffected)
     * @return a new quaternion
     */
    public static Quat multiply(float left, QuatArg right) {
        float w = left * right.getW();
        float x = left * right.getX();
        float y = left * right.getY();
        float z = left * right.getZ();
        Quat result = new Quat(x, y, z, w);

        return result;
    }

    /**
     * Return a scaled version of the specified vector. (native operator: binary
     * {@code *})
     *
     * @param left the scale to apply
     * @param right the input vector (not null, unaffected)
     * @return a new vector
     */
    public static Vec3 multiply(float left, Vec3Arg right) {
        float x = left * right.getX();
        float y = left * right.getY();
        float z = left * right.getZ();
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Return the specified matrix multiplied by the specified column vector,
     * with the 4th component of the right factor implied to be one. (native
     * operator: binary {@code *})
     *
     * @param left the left factor (not null, unaffected)
     * @param right the right factor (not null, unaffected)
     * @return a new vector
     */
    public static Vec3 multiply(Mat44Arg left, Vec3Arg right) {
        Vec3 result = left.multiply3x4(right);
        return result;
    }

    /**
     * Return the product of the specified quaternions. (native operator: binary
     * {@code *})
     *
     * @param left the left factor (not null, unaffected)
     * @param right the right factor (not null, unaffected)
     * @return a new quaternion
     */
    public static Quat multiply(QuatArg left, QuatArg right) {
        float lw = left.getW();
        float lx = left.getX();
        float ly = left.getY();
        float lz = left.getZ();

        float rw = right.getW();
        float rx = right.getX();
        float ry = right.getY();
        float rz = right.getZ();

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
     * @param left the input vector (not null, unaffected)
     * @param right the scale to apply
     * @return a new vector
     */
    public static Vec3 multiply(Vec3Arg left, float right) {
        float x = right * left.getX();
        float y = right * left.getY();
        float z = right * left.getZ();
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Return the component-wise product of the specified vectors. (native
     * operator: binary {@code *})
     *
     * @param left the first vector (not null, unaffected)
     * @param right the 2nd vector (not null, unaffected)
     * @return a new vector
     */
    public static Vec3 multiply(Vec3Arg left, Vec3Arg right) {
        float x = left.getX() * right.getX();
        float y = left.getY() * right.getY();
        float z = left.getZ() * right.getZ();
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Return the negative of the specified vector. (native operator: unary
     * {@code -})
     *
     * @param right the input vector (not null, unaffected)
     * @return a new vector
     */
    public static Vec3 negate(Vec3Arg right) {
        float x = -right.getX();
        float y = -right.getY();
        float z = -right.getZ();
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Test whether the specified colors are unequal. (native operator:
     * {@code !=})
     *
     * @param left the first color to test (not null, unaffected)
     * @param right the 2nd color to test (not null, unaffected)
     * @return false if equal, true if unequal
     */
    public static boolean notEqual(ConstColor left, ConstColor right) {
        boolean result = left.getA() != right.getA()
                || left.getB() != right.getB()
                || left.getG() != right.getG()
                || left.getR() != right.getR();
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
     * @param left the rotation to apply (not null, normalized, unaffected)
     * @param right the vector to apply it to (not null, unaffected)
     * @return a new vector
     */
    public static Vec3 rotate(QuatArg left, Vec3Arg right) {
        assert left.isNormalized();

        float lw = left.getW();
        float lx = left.getX();
        float ly = left.getY();
        float lz = left.getZ();

        float rx = right.getX();
        float ry = right.getY();
        float rz = right.getZ();

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
     * @param left the first vector (not null, unaffected)
     * @param right the vector to subtract (not null, unaffected)
     * @return a new vector
     */
    public static Vec3 subtract(Vec3Arg left, Vec3Arg right) {
        float x = left.getX() - right.getX();
        float y = left.getY() - right.getY();
        float z = left.getZ() - right.getZ();
        Vec3 result = new Vec3(x, y, z);

        return result;
    }
}
