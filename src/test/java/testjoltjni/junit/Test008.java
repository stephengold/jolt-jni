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
package testjoltjni.junit;

import com.github.stephengold.joltjni.Mat44;
import com.github.stephengold.joltjni.Quat;
import com.github.stephengold.joltjni.RMat44;
import com.github.stephengold.joltjni.RVec3;
import com.github.stephengold.joltjni.Vec3;
import org.junit.Test;
import testjoltjni.TestUtils;

/**
 * Automated JUnit4 tests for matrices.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Test008 {
    // *************************************************************************
    // new methods exposed

    /**
     * Test matrices.
     */
    @Test
    public void test008() {
        TestUtils.loadAndInitializeNativeLibrary();

        testMat44();
        testRMat44();

        TestUtils.cleanup();
    }
    // *************************************************************************
    // private methods

    /**
     * Test the {@code Mat44} class.
     */
    private void testMat44() {
        // Create an uninitialized matrix:
        Mat44 uninit = new Mat44();

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

        TestUtils.testClose(zero, rotTrans, rot, cMajor, identity, uninit);
        System.gc();
    }

    /**
     * Test the {@code RMat44} class.
     */
    private void testRMat44() {
        // Create an uninitialized matrix:
        RMat44 uninit = new RMat44();

        // Test an identity matrix:
        RMat44 identity = RMat44.sIdentity();
        TestUtils.assertEquals(1f, 0f, 0f, 0f,
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
        TestUtils.assertEquals(0f, 0f, 0f, 0f,
                0f, 0f, 0f, 0f,
                0f, 0f, 0f, 0f,
                0f, 0f, 0f, 0f,
                zero, 0f);

        TestUtils.testClose(zero, trans, rotTrans, rot, identity, uninit);
        System.gc();
    }
}
