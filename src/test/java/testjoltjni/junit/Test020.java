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

import com.github.stephengold.joltjni.CapsuleShapeSettings;
import com.github.stephengold.joltjni.ShapeRefC;
import com.github.stephengold.joltjni.ShapeResult;
import com.github.stephengold.joltjni.TaperedCapsuleShapeSettings;
import com.github.stephengold.joltjni.TaperedCylinderShapeSettings;
import com.github.stephengold.joltjni.enumerate.EShapeSubType;
import org.junit.Assert;
import org.junit.Test;
import testjoltjni.TestUtils;

/**
 * Automated JUnit4 tests for creating special-case shapes.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Test020 {
    // *************************************************************************
    // new methods exposed

    /**
     * Test the creation of special-case shapes.
     */
    @Test
    public void test020() {
        TestUtils.loadNativeLibrary();
        TestUtils.initializeNativeLibrary();

        testCapSphere();
        testTcapOffsetSphere();
        testTcapSphere();
        testTcylCylinder();

        TestUtils.cleanup();
    }

    /**
     * Construct a sphere using {@code CapsuleShapeSettings}.
     */
    private static void testCapSphere() {
        float radius = 1f;
        float height = 0f;
        CapsuleShapeSettings ss
                = new CapsuleShapeSettings(height / 2f, radius);
        ShapeResult result = ss.create();
        ShapeRefC shapeRef = result.get();
        Assert.assertEquals(EShapeSubType.Sphere, shapeRef.getSubType());

        TestUtils.testClose(shapeRef.getPtr(), shapeRef, result, ss);
    }

    /**
     * Construct an offset sphere using {@codeTaperedCapsuleShapeSettings}.
     */
    private static void testTcapOffsetSphere() {
        float topR = 4f;
        float bottomR = 1f;
        float height = 1f;
        TaperedCapsuleShapeSettings ss
                = new TaperedCapsuleShapeSettings(height / 2f, topR, bottomR);
        Assert.assertTrue(ss.isSphere());
        Assert.assertTrue(ss.isValid());
        ShapeResult result = ss.create();
        ShapeRefC shapeRef = result.get();
        Assert.assertEquals(
                EShapeSubType.RotatedTranslated, shapeRef.getSubType());

        TestUtils.testClose(shapeRef.getPtr(), shapeRef, result, ss);
    }

    /**
     * Construct a sphere using {@codeTaperedCapsuleShapeSettings}.
     */
    private static void testTcapSphere() {
        float radius = 4f;
        float height = 0f;
        TaperedCapsuleShapeSettings ss
                = new TaperedCapsuleShapeSettings(height / 2f, radius, radius);
        Assert.assertEquals(ss.getTopRadius(), ss.getBottomRadius(), 0f);
        Assert.assertEquals(0f, ss.getHalfHeightOfTaperedCylinder(), 0f);
        Assert.assertTrue(ss.isSphere());
        Assert.assertTrue(ss.isValid());
        ShapeResult result = ss.create();
        ShapeRefC shapeRef = result.get();
        Assert.assertEquals(EShapeSubType.Sphere, shapeRef.getSubType());

        TestUtils.testClose(shapeRef.getPtr(), shapeRef, result, ss);
    }

    /**
     * Construct a cylinder using {@codeTaperedCylinderShapeSettings}.
     */
    private static void testTcylCylinder() {
        float radius = 1f;
        float height = 3f;
        TaperedCylinderShapeSettings ss
                = new TaperedCylinderShapeSettings(height / 2f, radius, radius);
        Assert.assertEquals(ss.getTopRadius(), ss.getBottomRadius(), 0f);
        ShapeResult result = ss.create();
        ShapeRefC shapeRef = result.get();
        Assert.assertEquals(EShapeSubType.Cylinder, shapeRef.getSubType());

        TestUtils.testClose(shapeRef.getPtr(), shapeRef, result, ss);
    }
}
