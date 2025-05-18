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
package testjoltjni.junit;

import com.github.stephengold.joltjni.Jolt;
import com.github.stephengold.joltjni.Mat44;
import com.github.stephengold.joltjni.Quat;
import com.github.stephengold.joltjni.RMat44;
import com.github.stephengold.joltjni.RVec3;
import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.operator.Op;
import com.github.stephengold.joltjni.readonly.QuatArg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import org.junit.Assert;
import org.junit.Test;
import testjoltjni.TestUtils;

/**
 * Automated JUnit4 tests for matrices and quaternions.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Test008 {
    // *************************************************************************
    // constants

    /**
     * Pi/2
     */
    final private static float halfPi = 0.5f * Jolt.JPH_PI;
    /**
     * square root of 1/2
     */
    final private static float rootHalf = (float) Math.sqrt(0.5);
    // *************************************************************************
    // new methods exposed

    /**
     * Test matrices and quaternions.
     */
    @Test
    public void test008() {
        TestUtils.loadNativeLibrary();
        TestUtils.initializeNativeLibrary();

        testMat44();
        testQuat();
        testRMat44();

        TestUtils.cleanup();
    }
    // *************************************************************************
    // private methods

    /**
     * Test (1,2,3,4), an arbitrary non-normalized quaternion.
     *
     * @param q1234 the quaternion to test (not null, unaffected)
     */
    private static void test1234(QuatArg q1234) {
        TestUtils.assertEquals(1f, 2f, 3f, 4f, q1234, 0f);

        TestUtils.assertEquals(-1f, -2f, -3f, 4f, q1234.conjugated(), 0f);
        Assert.assertFalse(q1234.isNormalized());
        Assert.assertFalse(q1234.isRotationIdentity());
        Assert.assertFalse(q1234.isZero());
        Assert.assertEquals(5.477226f, q1234.length(), 1e-6f);
        Assert.assertEquals(30f, q1234.lengthSq(), 0f);
        TestUtils.assertEquals(0.182574f, 0.365148f, 0.547723f, 0.730297f,
                q1234.normalized(), 1e-6f);
        Assert.assertEquals("Quat(1.0 2.0 3.0 4.0)", q1234.toString());
    }

    /**
     * Verify the order in which rotations are applied by Quat.sEulerAngles().
     */
    private static void testEulerAngles() {
        final Vec3Arg in = new Vec3(4f, 6f, 9f); // test vector, never modified

        // Three arbitrary rotation angles between -halfPi and +halfPi:
        final float xAngle = 1.23f;
        final float yAngle = 0.765f;
        final float zAngle = -0.456f;

        final QuatArg qx = Quat.sRotation(Vec3.sAxisX(), xAngle);
        final QuatArg qy = Quat.sRotation(Vec3.sAxisY(), yAngle);
        final QuatArg qz = Quat.sRotation(Vec3.sAxisZ(), zAngle);
        final QuatArg qea = Quat.sEulerAngles(xAngle, yAngle, zAngle);
        /*
         * Part 1: verify that the extrinsic rotation order is x-y-z
         *
         * Apply extrinsic rotations to the "in" vector in x-y-z order.
         */
        Vec3 extrinsic = Op.star(qx, in);
        extrinsic = Op.star(qy, extrinsic);
        extrinsic = Op.star(qz, extrinsic);
        /*
         * Use the quaternion constructed by sEulerAngles()
         * to rotate the "in" vector.
         */
        final Vec3 out1 = Op.star(qea, in);
        TestUtils.assertEquals(extrinsic, out1, 1e-5f);
        /*
         * Part 2: verify that the intrinsic rotation order is z-y'-x"
         *
         * Apply intrinsic rotations to the "in" vector in z-y'-x" order.
         */
        Vec3 intrinsic = Op.star(in, qz);
        intrinsic = Op.star(intrinsic, qy);
        intrinsic = Op.star(intrinsic, qx);
        /*
         * Use the quaternion constructed by sEulerAngles()
         * to rotate the "in" vector and compare.
         */
        final Vec3 out2 = Op.star(in, qea);
        TestUtils.assertEquals(intrinsic, out2, 1e-5f);
    }

    /**
     * Test an identity quaternion.
     *
     * @param identity the quaternion to test (not null, unaffected)
     */
    private static void testIdentity(QuatArg identity) {
        TestUtils.assertEquals(0f, 0f, 0f, 1f, identity, 0f);

        TestUtils.assertEquals(0f, 0f, 0f, 1f, identity.conjugated(), 0f);
        Assert.assertTrue(identity.isNormalized());
        Assert.assertTrue(identity.isRotationIdentity());
        Assert.assertFalse(identity.isZero());
        Assert.assertEquals(1f, identity.length(), 0f);
        Assert.assertEquals(1f, identity.lengthSq(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, 1f, identity.normalized(), 0f);
        TestUtils.assertEquals(1f, 0f, 0f, identity.rotateAxisX(), 0f);
        TestUtils.assertEquals(0f, 1f, 0f, identity.rotateAxisY(), 0f);
        TestUtils.assertEquals(0f, 0f, 1f, identity.rotateAxisZ(), 0f);
        Assert.assertEquals("Quat(0.0 0.0 0.0 1.0)", identity.toString());
    }

    /**
     * Test the {@code Mat44} class.
     */
    private static void testMat44() {
        // Create an uninitialized matrix:
        Mat44 uninitialized = new Mat44();

        // Test an identity matrix:
        Mat44 identity = Mat44.sIdentity();
        TestUtils.assertEquals(
                1f, 0f, 0f, 0f,
                0f, 1f, 0f, 0f,
                0f, 0f, 1f, 0f,
                0f, 0f, 0f, 1f,
                identity, 0f);

        // Test a matrix whose elements are specified in column-major order:
        Mat44 cMajor = new Mat44(
                0f, 1f, 2f, 3f,
                15f, 14f, 13f, 4f,
                10f, 11f, 12f, 5f,
                9f, 8f, 7f, 6f);
        TestUtils.assertEquals(
                0f, 15f, 10f, 9f,
                1f, 14f, 11f, 8f,
                2f, 13f, 12f, 7f,
                3f, 4f, 5f, 6f,
                cMajor, 0f);

        // Test a rotation matrix:
        Mat44 rot = Mat44.sRotation(new Quat(0.5f, 0.5f, 0.5f, 0.5f));
        TestUtils.assertEquals(
                0f, 0f, 1f, 0f,
                1f, 0f, 0f, 0f,
                0f, 1f, 0f, 0f,
                0f, 0f, 0f, 1f,
                rot, 0f);

        // Test a rotation-and-translation matrix:
        Mat44 rotTrans = Mat44.sRotationTranslation(
                new Quat(0.5f, 0.5f, 0.5f, 0.5f), new Vec3(1f, 2f, 3f));
        TestUtils.assertEquals(
                0f, 0f, 1f, 1f,
                1f, 0f, 0f, 2f,
                0f, 1f, 0f, 3f,
                0f, 0f, 0f, 1f,
                rotTrans, 0f);

        // Test an all-zero matrix:
        Mat44 zero = Mat44.sZero();
        TestUtils.assertEquals(
                0f, 0f, 0f, 0f,
                0f, 0f, 0f, 0f,
                0f, 0f, 0f, 0f,
                0f, 0f, 0f, 0f,
                zero, 0f);

        TestUtils.testClose(
                zero, rotTrans, rot, cMajor, identity, uninitialized);
        System.gc();
    }

    /**
     * Test the {@code Quat} class.
     */
    private static void testQuat() {
        final Vec3Arg xVec = Vec3.sAxisX();
        final Vec3Arg zeroVec = Vec3.sZero();

        // Test identity quaternions:
        final QuatArg identity = new Quat();
        testIdentity(identity);

        testIdentity(new Quat(0f, 0f, 0f, 1f));
        testIdentity(new Quat(identity));
        testIdentity(new Quat(zeroVec, 1f));
        testIdentity(Quat.sEulerAngles(zeroVec));
        testIdentity(Quat.sIdentity());
        testIdentity(Quat.sRotation(xVec, 0f));

        final Quat tmp = new Quat(0.1f, 0.7f, -0.1f, -0.7f);
        tmp.loadIdentity();
        testIdentity(tmp);

        tmp.set(0.1f, 0.7f, -0.1f, -0.7f);
        tmp.set(0f, 0f, 0f, 1f);
        testIdentity(tmp);

        tmp.set(0.1f, 0.7f, -0.1f, -0.7f);
        tmp.set(identity);
        testIdentity(tmp);

        // Test 90-degree rotations around the +X axis:
        final QuatArg x90 = new Quat(rootHalf, 0f, 0f, rootHalf);
        testX90(x90);
        testX90(new Quat(x90));
        testX90(new Quat(new Vec3(rootHalf, 0f, 0f), rootHalf));
        testX90(Quat.sEulerAngles(halfPi, 0f, 0f));
        testX90(Quat.sRotation(xVec, halfPi));
        testX90(new Quat(1f, 0f, 0f, 1f).normalized());

        tmp.set(0.1f, 0.7f, -0.1f, -0.7f);
        tmp.set(rootHalf, 0f, 0f, rootHalf);
        testX90(tmp);

        tmp.set(0.1f, 0.7f, -0.1f, -0.7f);
        tmp.set(x90);
        testX90(tmp);

        // Test (1,2,3,4) quaternions:
        final QuatArg q1234 = new Quat(1f, 2f, 3f, 4f);
        test1234(q1234);
        test1234(new Quat(q1234));
        test1234(new Quat(new Vec3(1f, 2f, 3f), 4f));

        tmp.set(0.1f, 0.7f, -0.1f, -0.7f);
        tmp.set(1f, 2f, 3f, 4f);
        test1234(tmp);

        tmp.set(0.1f, 0.7f, -0.1f, -0.7f);
        tmp.set(q1234);
        test1234(tmp);

        // Test Op.star() methods:
        testQuatStarMethods();

        // Test Quat.sEulerAngles():
        testEulerAngles();

        TestUtils.testClose();
        System.gc();
    }

    /**
     * Test the {@code Op.star()} methods that involve quaternions.
     */
    private static void testQuatStarMethods() {
        // Basis values:
        final QuatArg i = new Quat(1f, 0f, 0f, 0f);
        final QuatArg j = new Quat(0f, 1f, 0f, 0f);
        final QuatArg k = new Quat(0f, 0f, 1f, 0f);
        final QuatArg one = new Quat(0f, 0f, 0f, 1f);

        // Op.star(float, QuatArg) to generate negative values:
        final QuatArg minusI = Op.star(-1f, i);
        TestUtils.assertEquals(-1f, 0f, 0f, 0f, minusI, 0f);

        final QuatArg minusJ = Op.star(-1f, j);
        TestUtils.assertEquals(0f, -1f, 0f, 0f, minusJ, 0f);

        final QuatArg minusK = Op.star(-1f, k);
        TestUtils.assertEquals(0f, 0f, -1f, 0f, minusK, 0f);

        final QuatArg minusOne = Op.star(-1f, one);
        TestUtils.assertEquals(0f, 0f, 0f, -1f, minusOne, 0f);

        // Op.star(QuatArg, QuatArg) fundamental identities:
        TestUtils.assertEquals(minusOne, Op.star(i, i), 0f);
        TestUtils.assertEquals(k, Op.star(i, j), 0f);
        TestUtils.assertEquals(minusJ, Op.star(i, k), 0f);
        TestUtils.assertEquals(i, Op.star(i, one), 0f);

        TestUtils.assertEquals(minusK, Op.star(j, i), 0f);
        TestUtils.assertEquals(minusOne, Op.star(j, j), 0f);
        TestUtils.assertEquals(i, Op.star(j, k), 0f);
        TestUtils.assertEquals(j, Op.star(j, one), 0f);

        TestUtils.assertEquals(j, Op.star(k, i), 0f);
        TestUtils.assertEquals(minusI, Op.star(k, j), 0f);
        TestUtils.assertEquals(minusOne, Op.star(k, k), 0f);
        TestUtils.assertEquals(k, Op.star(k, one), 0f);

        TestUtils.assertEquals(i, Op.star(one, i), 0f);
        TestUtils.assertEquals(j, Op.star(one, j), 0f);
        TestUtils.assertEquals(k, Op.star(one, k), 0f);
        TestUtils.assertEquals(one, Op.star(one, one), 0f);

        // Op.star(QuatArg, QuatArg) on arbitrary values:
        final QuatArg q1 = new Quat(1f, 2f, 3f, 4f);
        final QuatArg q2 = new Quat(5f, 6f, 7f, 8f);
        final QuatArg actual1 = Op.star(q1, q2);
        TestUtils.assertEquals(24f, 48f, 48f, -6f, actual1, 0f);

        // Op.star(QuatArg, Vec3Arg) decomposition:
        final QuatArg qa = new Quat(7f, 5f, -3f, 4f).normalized();
        final QuatArg qaHat = qa.conjugated();
        final Vec3Arg vb = new Vec3(0.56f, 1.3f, -0.82);
        final QuatArg qb = new Quat(vb, 0f);
        final QuatArg actual2 = new Quat(Op.star(qa, vb), 0f);
        TestUtils.assertEquals(Op.star(Op.star(qa, qb), qaHat), actual2, 1e-6f);

        // Op.star(Vec3Arg, QuatArg) decomposition:
        final QuatArg actual3 = new Quat(Op.star(vb, qa), 0f);
        TestUtils.assertEquals(Op.star(Op.star(qaHat, qb), qa), actual3, 1e-6f);
    }

    /**
     * Test the {@code RMat44} class.
     */
    private static void testRMat44() {
        // Create an uninitialized matrix:
        RMat44 uninitialized = new RMat44();

        // Test an identity matrix:
        RMat44 identity = RMat44.sIdentity();
        TestUtils.assertEquals(
                1f, 0f, 0f, 0f,
                0f, 1f, 0f, 0f,
                0f, 0f, 1f, 0f,
                0f, 0f, 0f, 1f,
                identity, 0f);

        // Test a rotation matrix:
        RMat44 rot = RMat44.sRotation(new Quat(0.5f, 0.5f, 0.5f, 0.5f));
        TestUtils.assertEquals(
                0f, 0f, 1f, 0f,
                1f, 0f, 0f, 0f,
                0f, 1f, 0f, 0f,
                0f, 0f, 0f, 1f,
                rot, 0f);

        // Test a rotation-and-translation matrix:
        RMat44 rotTrans = RMat44.sRotationTranslation(
                new Quat(0.5f, 0.5f, 0.5f, 0.5f), new RVec3(1f, 2f, 3f));
        TestUtils.assertEquals(
                0f, 0f, 1f, 1f,
                1f, 0f, 0f, 2f,
                0f, 1f, 0f, 3f,
                0f, 0f, 0f, 1f,
                rotTrans, 0f);

        // Test a translation matrix:
        RMat44 trans = RMat44.sTranslation(new RVec3(1f, 2f, 3f));
        TestUtils.assertEquals(
                1f, 0f, 0f, 1f,
                0f, 1f, 0f, 2f,
                0f, 0f, 1f, 3f,
                0f, 0f, 0f, 1f,
                trans, 0f);

        // Test an all-zero matrix:
        RMat44 zero = RMat44.sZero();
        TestUtils.assertEquals(
                0f, 0f, 0f, 0f,
                0f, 0f, 0f, 0f,
                0f, 0f, 0f, 0f,
                0f, 0f, 0f, 1f,
                zero, 0f);

        TestUtils.testClose(
                zero, trans, rotTrans, rot, identity, uninitialized);
        System.gc();
    }

    /**
     * Test a quaternion that rotates 90 degrees around the +X axis.
     *
     * @param x90 the quaternion to test (not null, unaffected)
     */
    private static void testX90(QuatArg x90) {
        TestUtils.assertEquals(rootHalf, 0f, 0f, rootHalf, x90, 1e-6f);

        TestUtils.assertEquals(-rootHalf, 0f, 0f, rootHalf,
                x90.conjugated(), 1e-6f);
        Assert.assertTrue(x90.isNormalized());
        Assert.assertFalse(x90.isRotationIdentity());
        Assert.assertFalse(x90.isZero());
        Assert.assertEquals(1f, x90.length(), 1e-6f);
        Assert.assertEquals(1f, x90.lengthSq(), 1e-6f);
        TestUtils.assertEquals(rootHalf, 0f, 0f, rootHalf,
                x90.normalized(), 1e-6f);
        TestUtils.assertEquals(1f, 0f, 0f, x90.rotateAxisX(), 1e-6f);
        TestUtils.assertEquals(0f, 0f, 1f, x90.rotateAxisY(), 1e-6f);
        TestUtils.assertEquals(0f, -1f, 0f, x90.rotateAxisZ(), 1e-6f);
        Assert.assertTrue(x90.toString().startsWith("Quat(0.707106"));
    }
}
