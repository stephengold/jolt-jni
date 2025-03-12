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
package com.github.stephengold.joltjni.operator;

import com.github.stephengold.joltjni.Color;
import com.github.stephengold.joltjni.Float3;
import com.github.stephengold.joltjni.Mat44;
import com.github.stephengold.joltjni.Quat;
import com.github.stephengold.joltjni.RMat44;
import com.github.stephengold.joltjni.RVec3;
import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.readonly.ConstBodyId;
import com.github.stephengold.joltjni.readonly.ConstColor;
import com.github.stephengold.joltjni.readonly.Mat44Arg;
import com.github.stephengold.joltjni.readonly.QuatArg;
import com.github.stephengold.joltjni.readonly.RMat44Arg;
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
     * Copy the right argument to the left one. (native operator: binary
     * {@code =})
     *
     * @param left the matrix to modify (not null)
     * @param right the value to assign (not null, unaffected)
     * @return the assigned value, for chaining
     */
    public static ConstColor assign(Color left, ConstColor right) {
        left.set(right);
        return right;
    }

    /**
     * Copy the right argument to the left one. (native operator: binary
     * {@code =})
     *
     * @param left the matrix to modify (not null)
     * @param right the value to assign (not null, unaffected)
     * @return the assigned value, for chaining
     */
    public static Mat44Arg assign(Mat44 left, Mat44Arg right) {
        left.set(right);
        return right;
    }

    /**
     * Copy the right argument to the left one. (native operator: binary
     * {@code =})
     *
     * @param left the matrix to modify (not null)
     * @param right the value to assign (not null, unaffected)
     * @return the assigned value, for chaining
     */
    public static RMat44Arg assign(RMat44 left, RMat44Arg right) {
        left.set(right);
        return right;
    }

    /**
     * Copy the right argument to the left one. (native operator: binary
     * {@code =})
     *
     * @param left the vector to modify (not null)
     * @param right the value to assign (not null, unaffected)
     * @return the assigned value, for chaining
     */
    public static RVec3Arg assign(RVec3 left, RVec3Arg right) {
        double rx = right.xx();
        double ry = right.yy();
        double rz = right.zz();
        left.set(rx, ry, rz);

        return right;
    }

    /**
     * Copy the right argument to the left one. (native operator: binary
     * {@code =})
     *
     * @param left the vector to modify (not null)
     * @param right the value to assign (not null, unaffected)
     * @return the assigned value, for chaining
     */
    public static Vec3Arg assign(Vec3 left, Vec3Arg right) {
        float rx = right.getX();
        float ry = right.getY();
        float rz = right.getZ();
        left.set(rx, ry, rz);

        return right;
    }

    /**
     * Test whether the specified IDs are equal. (native operator: binary
     * {@code ==})
     *
     * @param left the first ID to test (not null, unaffected)
     * @param right the 2nd ID to test (not null, unaffected)
     * @return {@code true} if equal, {@code false} if unequal
     */
    public static boolean isEqual(ConstBodyId left, ConstBodyId right) {
        boolean result = left.isEqual(right);
        return result;
    }

    /**
     * Test whether the specified colors are equal. (native operator: binary
     * {@code ==})
     *
     * @param left the first color to test (not null, unaffected)
     * @param right the 2nd color to test (not null, unaffected)
     * @return {@code true} if equal, {@code false} if unequal
     */
    public static boolean isEqual(ConstColor left, ConstColor right) {
        boolean result = left.getA() == right.getA()
                && left.getB() == right.getB()
                && left.getG() == right.getG()
                && left.getR() == right.getR();
        return result;
    }

    /**
     * Test whether the specified matrices are equal. (native operator: binary
     * {@code ==})
     *
     * @param left the first matrix (not null, unaffected)
     * @param right the 2nd matrix (not null, unaffected)
     * @return {@code true} if equal, otherwise {@code false}
     */
    public static boolean isEqual(Mat44Arg left, Mat44Arg right) {
        boolean result = left.isEqual(right);
        return result;
    }

    /**
     * Return the negative of the specified vector. (native operator: unary
     * {@code -})
     *
     * @param right the input vector (not null, unaffected)
     * @return a new vector
     */
    public static RVec3 minus(RVec3Arg right) {
        double xx = -right.xx();
        double yy = -right.yy();
        double zz = -right.zz();
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
    public static RVec3 minus(RVec3Arg left, RVec3Arg right) {
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
    public static RVec3 minus(RVec3Arg left, Vec3Arg right) {
        double xx = left.xx() - right.getX();
        double yy = left.yy() - right.getY();
        double zz = left.zz() - right.getZ();
        RVec3 result = new RVec3(xx, yy, zz);

        return result;
    }

    /**
     * Return the negative of the specified vector. (native operator: unary
     * {@code -})
     *
     * @param right the input vector (not null, unaffected)
     * @return a new vector
     */
    public static Vec3 minus(Vec3Arg right) {
        float x = -right.getX();
        float y = -right.getY();
        float z = -right.getZ();
        Vec3 result = new Vec3(x, y, z);

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
    public static Vec3 minus(Vec3Arg left, Vec3Arg right) {
        float x = left.getX() - right.getX();
        float y = left.getY() - right.getY();
        float z = left.getZ() - right.getZ();
        Vec3 result = new Vec3(x, y, z);

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
     * Test whether the specified colors are unequal. (native operator: binary
     * {@code !=})
     *
     * @param left the first color to test (not null, unaffected)
     * @param right the 2nd color to test (not null, unaffected)
     * @return {@code false} if equal, {@code true} if unequal
     */
    public static boolean notEqual(ConstColor left, ConstColor right) {
        boolean result = left.getA() != right.getA()
                || left.getB() != right.getB()
                || left.getG() != right.getG()
                || left.getR() != right.getR();
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
    public static RVec3 plus(RVec3Arg left, Float3 right) {
        double xx = left.xx() + right.x;
        double yy = left.yy() + right.y;
        double zz = left.zz() + right.z;
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
    public static RVec3 plus(RVec3Arg left, RVec3Arg right) {
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
    public static RVec3 plus(RVec3Arg left, Vec3Arg right) {
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
    public static Vec3 plus(Vec3Arg left, Vec3Arg right) {
        float x = left.getX() + right.getX();
        float y = left.getY() + right.getY();
        float z = left.getZ() + right.getZ();
        Vec3 result = new Vec3(x, y, z);

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
    public static void plusEquals(Vec3 left, RVec3Arg right) {
        left.setX(left.getX() + right.x());
        left.setY(left.getY() + right.y());
        left.setZ(left.getZ() + right.z());
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
     * Return a scaled version of the specified vector. (native operator: binary
     * {@code /})
     *
     * @param left the input vector (not null, unaffected)
     * @param right the inverse scale to apply
     * @return a new vector
     */
    public static RVec3 slash(RVec3Arg left, double right) {
        double xx = left.xx() / right;
        double yy = left.yy() / right;
        double zz = left.zz() / right;
        RVec3 result = new RVec3(xx, yy, zz);

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
    public static Vec3 slash(Vec3Arg left, float right) {
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
    public static void slashEquals(RVec3 left, double right) {
        double xx = left.xx() / right;
        double yy = left.yy() / right;
        double zz = left.zz() / right;
        left.set(xx, yy, zz);
    }

    /**
     * Divide the left argument by the right argument. (native operator: binary
     * {@code /=})
     *
     * @param left the accumulating vector (not null, modified)
     * @param right the denominator (not null, unaffected)
     */
    public static void slashEquals(Vec3 left, float right) {
        float xx = left.getX() / right;
        float yy = left.getY() / right;
        float zz = left.getZ() / right;
        left.set(xx, yy, zz);
    }

    /**
     * Return a scaled version of the specified vector. (native operator: binary
     * {@code *})
     *
     * @param left the scale to apply
     * @param right the input vector (not null, unaffected)
     * @return a new vector
     */
    public static RVec3 star(double left, RVec3Arg right) {
        double x = left * right.xx();
        double y = left * right.yy();
        double z = left * right.zz();
        RVec3 result = new RVec3(x, y, z);

        return result;
    }

    /**
     * Return a scaled version of the specified quaternion. (native operator:
     * binary {@code *})
     *
     * @param left the scale to apply
     * @param right the input quaternion (not null, unaffected)
     * @return a new quaternion
     */
    public static Quat star(float left, QuatArg right) {
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
    public static Vec3 star(float left, Vec3Arg right) {
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
    public static Vec3 star(Mat44Arg left, Vec3Arg right) {
        Vec3 result = left.multiply3x4(right);
        return result;
    }

    /**
     * Return the product of the specified matrices. (native operator: binary
     * {@code *})
     *
     * @param left the left factor (not null, unaffected)
     * @param right the right factor (not null, unaffected)
     * @return a new matrix
     */
    public static Mat44 star(Mat44Arg left, Mat44Arg right) {
        Mat44 result = left.multiply(right);
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
    public static Quat star(QuatArg left, QuatArg right) {
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
     * Rotate the specified vector by the specified unit quaternion. (native
     * operator: binary {@code *})
     *
     * @param left the rotation to apply (not null, normalized, unaffected)
     * @param right the vector to apply it to (not null, unaffected)
     * @return a new vector
     */
    public static Vec3 star(QuatArg left, Vec3Arg right) {
        assert left.isNormalized();

        float lw = left.getW();
        float lx = left.getX();
        float ly = left.getY();
        float lz = left.getZ();

        float rx = right.getX();
        float ry = right.getY();
        float rz = right.getZ();

        // a = left x pure(right)
        float aw = -lx * rx - ly * ry - lz * rz;
        float ax = lw * rx + ly * rz - lz * ry;
        float ay = lw * ry - lx * rz + lz * rx;
        float az = lw * rz + lx * ry - ly * rx;

        // result = vec3(a x conjugate(left))
        float x = -aw * lx + ax * lw - ay * lz + az * ly;
        float y = -aw * ly + ax * lz + ay * lw - az * lx;
        float z = -aw * lz - ax * ly + ay * lx + az * lw;

        Vec3 result = new Vec3(x, y, z);
        return result;
    }

    /**
     * Return the product of the specified matrices. (native operator: binary
     * {@code *})
     *
     * @param left the left factor (not null, unaffected)
     * @param right the right factor (not null, unaffected)
     * @return a new matrix
     */
    public static RMat44 star(RMat44Arg left, Mat44Arg right) {
        RMat44 result = left.multiply(right);
        return result;
    }

    /**
     * Return the product of the specified matrices. (native operator: binary
     * {@code *})
     *
     * @param left the left factor (not null, unaffected)
     * @param right the right factor (not null, unaffected)
     * @return a new vector
     */
    public static RMat44 star(RMat44Arg left, RMat44Arg right) {
        RMat44 result = left.multiply(right);
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
    public static RVec3 star(RMat44Arg left, RVec3Arg right) {
        RVec3 result = left.multiply3x4(right);
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
    public static RVec3 star(RMat44Arg left, Vec3Arg right) {
        RVec3 result = left.multiply3x4(right);
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
    public static Vec3 star(Vec3Arg left, float right) {
        Vec3 result = star(right, left);
        return result;
    }

    /**
     * Rotate the specified vector by the conjugate of the specified unit
     * quaternion. (native operator: binary {@code *})
     *
     * @param left the input vector (not null, unaffected)
     * @param right the conjugate of the rotation to apply (not null,
     * normalized, unaffected)
     * @return a new vector
     */
    public static Vec3 star(Vec3Arg left, QuatArg right) {
        assert right.isNormalized();

        float lx = left.getX();
        float ly = left.getY();
        float lz = left.getZ();

        float rw = right.getW();
        float rx = right.getX();
        float ry = right.getY();
        float rz = right.getZ();

        // a = pure(left) x right
        float aw = -lx * rx - ly * ry - lz * rz;
        float ax = lx * rw + ly * rz - lz * ry;
        float ay = ly * rw - lx * rz + lz * rx;
        float az = lz * rw + lx * ry - ly * rx;

        // result = vec3(conjugate(right) x a)
        float x = rw * ax - rx * aw - ry * az + rz * ay;
        float y = rw * ay + rx * az - ry * aw - rz * ax;
        float z = rw * az - rx * ay + ry * ax - rz * aw;

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
    public static Vec3 star(Vec3Arg left, Vec3Arg right) {
        float x = left.getX() * right.getX();
        float y = left.getY() * right.getY();
        float z = left.getZ() * right.getZ();
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Scale the left argument by the right argument. (native operator: binary
     * {@code *=})
     *
     * @param left the accumulating vector (not null, modified)
     * @param right the scale factor to apply (not null, unaffected)
     */
    public static void starEquals(Vec3 left, float right) {
        left.setX(left.getX() * right);
        left.setY(left.getY() * right);
        left.setZ(left.getZ() * right);
    }
}
