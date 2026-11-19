/*
Copyright (c) 2026 Stephen Gold

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

import com.github.stephengold.joltjni.JphMath;
import electrostatic4j.snaploader.platform.util.NativeVariant;
import org.junit.Assert;
import org.junit.Test;
import testjoltjni.TestUtils;

/**
 * Automated JUnit4 tests for global static functions.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Test021 {
    // *************************************************************************
    // new methods exposed

    /**
     * Test the JphMath functions.
     */
    @Test
    public void test021() {
        TestUtils.loadNativeLibrary();
        TestUtils.initializeNativeLibrary();

        Assert.assertEquals(1.3694384f, JphMath.aCos(0.2f), 0f);
        Assert.assertEquals(3.1415927f, JphMath.aCos(-2f), 0f);
        Assert.assertEquals(0f, JphMath.aCos(Float.MAX_VALUE), 0f);
        Assert.assertEquals(1.5707964f, JphMath.aCos(Float.MIN_VALUE), 0f);
        float acosNan = JphMath.aCos(Float.NaN);
        if (NativeVariant.Cpu.isARM()) { // TODO: see JoltPhysics issue #2224
            assertIsNan(acosNan);
        } else {
            Assert.assertEquals(0f, acosNan, 0f);
        }
        Assert.assertEquals(3.1415927f,
                JphMath.aCos(Float.NEGATIVE_INFINITY), 0f);
        Assert.assertEquals(0f, JphMath.aCos(Float.POSITIVE_INFINITY), 0f);

        Assert.assertEquals(0.29145682f, JphMath.aTan(0.3f), 0f);
        Assert.assertEquals(1.5707964f, JphMath.aTan(Float.MAX_VALUE), 0f);
        Assert.assertEquals(1.4e-45f, JphMath.aTan(Float.MIN_VALUE), 0f);
        assertIsNan(JphMath.aTan(Float.NaN));
        Assert.assertEquals(-1.5707964f,
                JphMath.aTan(Float.NEGATIVE_INFINITY), 0f);
        Assert.assertEquals(1.5707964f,
                JphMath.aTan(Float.POSITIVE_INFINITY), 0f);

        Assert.assertEquals(2.4668517f, JphMath.aTan2(0.4f, -0.5f), 0f);
        Assert.assertEquals(0.7853982f,
                JphMath.aTan2(Float.MAX_VALUE, Float.MAX_VALUE), 0f);
        Assert.assertEquals(0.7853982f,
                JphMath.aTan2(Float.MIN_VALUE, Float.MIN_VALUE), 0f);
        assertIsNan(JphMath.aTan2(0f, Float.NaN));
        assertIsNan(JphMath.aTan2(Float.NaN, 0f));
        assertIsNan(JphMath.aTan2(Float.NaN, Float.NaN));
        assertIsNan(
                JphMath.aTan2(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
        );

        Assert.assertEquals(-0.5f, JphMath.clamp(-0.7f, -0.5f, 0.6f), 0f);
        Assert.assertEquals(2f, JphMath.clamp(2f, 1f, 3f), 0f);
        Assert.assertEquals(-0.5f, JphMath.clamp(-0.7f, -0.5f, 0.6f), 0f);
        Assert.assertTrue(Float.isNaN(JphMath.clamp(Float.NaN, -0.5f, 0.6f)));

        Assert.assertEquals(0.7648422f, JphMath.cos(0.7f), 0f);
        float cosMax = JphMath.cos(Float.MAX_VALUE);
        if (NativeVariant.Cpu.isARM()) { // TODO: see JoltPhysics issue #2224
            Assert.assertEquals(Float.NEGATIVE_INFINITY, cosMax, 0f);
        } else {
            assertIsNan(cosMax);
        }
        Assert.assertEquals(1f, JphMath.cos(Float.MIN_VALUE), 0f);
        assertIsNan(JphMath.cos(Float.NaN));
        assertIsNan(JphMath.cos(Float.NEGATIVE_INFINITY));
        assertIsNan(JphMath.cos(Float.POSITIVE_INFINITY));

        Assert.assertEquals(0.013962634f, JphMath.degreesToRadians(0.8f), 0f);
        Assert.assertEquals(5.939047e36f,
                JphMath.degreesToRadians(Float.MAX_VALUE), 0f);
        Assert.assertEquals(0f, JphMath.degreesToRadians(Float.MIN_VALUE), 0f);

        Assert.assertEquals(51.566196f, JphMath.radiansToDegrees(0.9f), 0f);
        Assert.assertEquals(Float.POSITIVE_INFINITY,
                JphMath.radiansToDegrees(Float.MAX_VALUE), 0f);
        Assert.assertEquals(7.987401246651457e-44,
                JphMath.radiansToDegrees(Float.MIN_VALUE), 0f);

        Assert.assertEquals(-1f, JphMath.sign(-1.1f), 0f);
        Assert.assertEquals(1f, JphMath.sign(-0f), 0f);
        Assert.assertEquals(1f, JphMath.sign(Float.NaN), 0f);
        Assert.assertEquals(-1f, JphMath.sign(Float.NEGATIVE_INFINITY), 0f);
        Assert.assertEquals(1f, JphMath.sign(Float.POSITIVE_INFINITY), 0f);

        Assert.assertEquals(0.09983342f, JphMath.sin(0.1f), 0f);
        float sinMax = JphMath.sin(Float.MAX_VALUE);
        if (NativeVariant.Cpu.isARM()) { // TODO: see JoltPhysics issue #2224
            assertIsNan(sinMax);
        } else {
            Assert.assertEquals(Float.NEGATIVE_INFINITY, sinMax, 0f);
        }
        Assert.assertEquals(1.4e-45f, JphMath.sin(Float.MIN_VALUE), 0f);
        assertIsNan(JphMath.sin(Float.NaN));
        assertIsNan(JphMath.sin(Float.NEGATIVE_INFINITY));
        assertIsNan(JphMath.sin(Float.POSITIVE_INFINITY));

        Assert.assertEquals(0.31622776f, JphMath.sqrt(0.1f), 0f);
        Assert.assertEquals(0f, JphMath.sqrt(-0f), 0f);
        assertIsNan(JphMath.sqrt(-1f));
        Assert.assertEquals(1.8446743e19f, JphMath.sqrt(Float.MAX_VALUE), 0f);
        Assert.assertEquals(3.743392e-23f, JphMath.sqrt(Float.MIN_VALUE), 0f);
        assertIsNan(JphMath.sqrt(Float.NaN));
        assertIsNan(JphMath.sqrt(Float.NEGATIVE_INFINITY));
        Assert.assertEquals(Float.POSITIVE_INFINITY,
                JphMath.sqrt(Float.POSITIVE_INFINITY), 0);

        Assert.assertEquals(0.040000003f, JphMath.square(0.2f), 0f);
        Assert.assertEquals(81, JphMath.square(-9));
        Assert.assertEquals(Float.POSITIVE_INFINITY,
                JphMath.square(Float.MAX_VALUE), 0f);
        Assert.assertEquals(0f, JphMath.square(Float.MIN_VALUE), 0f);
        assertIsNan(JphMath.square(Float.NaN));
        Assert.assertEquals(Float.POSITIVE_INFINITY,
                JphMath.square(Float.NEGATIVE_INFINITY), 0);
        Assert.assertEquals(Float.POSITIVE_INFINITY,
                JphMath.square(Float.POSITIVE_INFINITY), 0);

        testTan();

        TestUtils.cleanup();
    }
    // *************************************************************************
    // private methods

    /**
     * Verify that a single-precision value is Not A Number (NaN).
     *
     * @param actual the value to test
     */
    private static void assertIsNan(float actual) {
        Assert.assertEquals(Float.NaN, actual, 0f);
    }

    /**
     * Test {@code JphMath.tan()}.
     */
    private static void testTan() {
        Assert.assertEquals(0.42279324f, JphMath.tan(0.4f), 0f);
        Assert.assertEquals(1.4e-45f, JphMath.tan(Float.MIN_VALUE), 0f);
        Assert.assertEquals(2.3915296e14f, JphMath.tan(1e9f), 0f);
        Assert.assertEquals(1.9576378e18f, JphMath.tan(2e9f), 0f);
        Assert.assertEquals(8.942755e24f, JphMath.tan(3e9f), 0f);
        Assert.assertEquals(-2.0515669e19f, JphMath.tan(3.2e9f), 0f);
        Assert.assertEquals(1.9054293e27f, JphMath.tan(3.24e9f), 0f);
        Assert.assertEquals(1.4174114e26f, JphMath.tan(3.28e9f), 0f);
        Assert.assertEquals(5.490515e24f, JphMath.tan(3.32e9f), 0f);
        Assert.assertEquals(7.1206887e22f, JphMath.tan(3.36e9f), 0f);
        Assert.assertEquals(1.8405395e22f, JphMath.tan(3.37e9f), 0f);
        Assert.assertEquals(-1.8405395e22f, JphMath.tan(-3.37e9f), 0f);

        assertIsNan(JphMath.tan(Float.NaN));

        float tanBig = JphMath.tan(3.38e9f);
        float tanMax = JphMath.tan(Float.MAX_VALUE);
        float tanNinf = JphMath.tan(Float.NEGATIVE_INFINITY);
        float tanPinf = JphMath.tan(Float.POSITIVE_INFINITY);
        if (NativeVariant.Cpu.isARM()) { // TODO: see JoltPhysics issue #2224
            Assert.assertEquals(4.014894e21f, tanBig, 0f);
            Assert.assertEquals(-0f, tanMax, 0f);
            Assert.assertEquals(0f, tanNinf, 0f);
            Assert.assertEquals(-0f, tanPinf, 0f);
        } else {
            Assert.assertEquals(Float.POSITIVE_INFINITY, tanBig, 0f);
            Assert.assertEquals(Float.POSITIVE_INFINITY, tanMax, 0f);
            Assert.assertEquals(Float.NEGATIVE_INFINITY, tanNinf, 0f);
            Assert.assertEquals(Float.POSITIVE_INFINITY, tanPinf, 0f);
        }
    }
}
