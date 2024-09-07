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

import com.github.stephengold.joltjni.BoxShape;
import com.github.stephengold.joltjni.BoxShapeSettings;
import com.github.stephengold.joltjni.CapsuleShape;
import com.github.stephengold.joltjni.CapsuleShapeSettings;
import com.github.stephengold.joltjni.CylinderShape;
import com.github.stephengold.joltjni.CylinderShapeSettings;
import com.github.stephengold.joltjni.ShapeRefC;
import com.github.stephengold.joltjni.ShapeResult;
import com.github.stephengold.joltjni.TaperedCapsuleShape;
import com.github.stephengold.joltjni.TaperedCapsuleShapeSettings;
import com.github.stephengold.joltjni.TaperedCylinderShape;
import com.github.stephengold.joltjni.TaperedCylinderShapeSettings;
import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.enumerate.EShapeSubType;
import com.github.stephengold.joltjni.enumerate.EShapeType;
import org.junit.Assert;
import org.junit.Test;
import testjoltjni.TestUtils;

/**
 * Automated JUnit4 tests for creation, accessors, and defaults of {@code Shape}
 * subclasses.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Test007 {
    // *************************************************************************
    // new methods exposed

    /**
     * Test object creation, accessors, and defaults.
     */
    @Test
    public void test007() {
        TestUtils.loadAndInitializeNativeLibrary();

        doBoxShape();
        doCapsuleShape();
        doCylinderShape();
        doTaperedCapsuleShape();
        doTaperedCylinderShape();

        TestUtils.cleanup();
    }
    // *************************************************************************
    // Java private methods

    /**
     * Test the {@code BoxShape} class.
     */
    private static void doBoxShape() {
        BoxShapeSettings settings = new BoxShapeSettings(new Vec3(1f, 1f, 1f));
        ShapeResult result = settings.create();
        Assert.assertFalse(result.hasError());
        Assert.assertTrue(result.isValid());
        ShapeRefC ref = result.get();
        BoxShape shape = (BoxShape) ref.getPtr();
        testBoxDefaults(shape);

        BoxShape shape2 = new BoxShape(new Vec3(1f, 1f, 1f));
        testBoxDefaults(shape2);

        TestUtils.testClose(shape2, shape, ref, result, settings);
        System.gc();
    }

    /**
     * Test the {@code CapsuleShapeSettings} class.
     */
    private static void doCapsuleShape() {
        CapsuleShapeSettings settings = new CapsuleShapeSettings(1f, 1f);
        ShapeResult result = settings.create();
        Assert.assertFalse(result.hasError());
        Assert.assertTrue(result.isValid());
        ShapeRefC ref = result.get();
        CapsuleShape shape = (CapsuleShape) ref.getPtr();
        testCapsuleDefaults(shape);

        CapsuleShape shape2 = new CapsuleShape(1f, 1f);
        testCapsuleDefaults(shape2);

        TestUtils.testClose(shape2, shape, ref, result, settings);
        System.gc();
    }

    /**
     * Test the {@code CylinderShape} class.
     */
    private static void doCylinderShape() {
        CylinderShapeSettings settings = new CylinderShapeSettings(1f, 1f);
        ShapeResult result = settings.create();
        Assert.assertFalse(result.hasError());
        Assert.assertTrue(result.isValid());
        ShapeRefC ref = result.get();
        CylinderShape shape = (CylinderShape) ref.getPtr();
        testCylinderDefaults(shape);

        CylinderShape shape2 = new CylinderShape(1f, 1f);
        testCylinderDefaults(shape2);

        TestUtils.testClose(shape2, shape, ref, result, settings);
        System.gc();
    }

    /**
     * Test the {@code TaperedCapsuleShape} class.
     */
    private static void doTaperedCapsuleShape() {
        TaperedCapsuleShapeSettings settings
                = new TaperedCapsuleShapeSettings(1f, 2f, 1f);
        ShapeResult result = settings.create();
        Assert.assertFalse(result.hasError());
        Assert.assertTrue(result.isValid());
        ShapeRefC ref = result.get();
        TaperedCapsuleShape shape = (TaperedCapsuleShape) ref.getPtr();

        Assert.assertEquals(1f, shape.getInnerRadius(), 0f);
        Assert.assertEquals(EShapeSubType.TaperedCapsule, shape.getSubType());
        Assert.assertEquals(EShapeType.Convex, shape.getType());
        Assert.assertEquals(0L, shape.getUserData());

        TestUtils.testClose(shape, ref, result, settings);
        System.gc();
    }

    /**
     * Test the {@code TaperedCylinderShape} class.
     */
    private static void doTaperedCylinderShape() {
        TaperedCylinderShapeSettings settings
                = new TaperedCylinderShapeSettings(1f, 0.05f, 1f);
        ShapeResult result = settings.create();
        Assert.assertFalse(result.hasError());
        Assert.assertTrue(result.isValid());
        ShapeRefC ref = result.get();
        TaperedCylinderShape shape = (TaperedCylinderShape) ref.getPtr();

        Assert.assertEquals(0.05f, shape.getInnerRadius(), 0f);
        Assert.assertEquals(EShapeSubType.TaperedCylinder, shape.getSubType());
        Assert.assertEquals(EShapeType.Convex, shape.getType());
        Assert.assertEquals(0L, shape.getUserData());

        TestUtils.testClose(shape, ref, result, settings);
        System.gc();
    }

    /**
     * Test the getters and defaults of the specified {@code BoxShape}.
     *
     * @param shape the settings to test (not null, unaffected)
     */
    private static void testBoxDefaults(BoxShape shape) {
        Assert.assertEquals(0.05f, shape.getConvexRadius(), 0f);
        Assert.assertEquals(1f, shape.getInnerRadius(), 0f);
        Assert.assertEquals(EShapeSubType.Box, shape.getSubType());
        Assert.assertEquals(EShapeType.Convex, shape.getType());
        Assert.assertEquals(0L, shape.getUserData());
    }

    /**
     * Test the getters and defaults of the specified {@code CapsuleShape}.
     *
     * @param shape the settings to test (not null, unaffected)
     */
    private static void testCapsuleDefaults(CapsuleShape shape) {
        Assert.assertEquals(1f, shape.getInnerRadius(), 0f);
        Assert.assertEquals(EShapeSubType.Capsule, shape.getSubType());
        Assert.assertEquals(EShapeType.Convex, shape.getType());
        Assert.assertEquals(0L, shape.getUserData());
    }

    /**
     * Test the getters and defaults of the specified {@code CylinderShape}.
     *
     * @param shape the settings to test (not null, unaffected)
     */
    private static void testCylinderDefaults(CylinderShape shape) {
        Assert.assertEquals(0.05f, shape.getConvexRadius(), 0f);
        Assert.assertEquals(1f, shape.getInnerRadius(), 0f);
        Assert.assertEquals(EShapeSubType.Cylinder, shape.getSubType());
        Assert.assertEquals(EShapeType.Convex, shape.getType());
        Assert.assertEquals(0L, shape.getUserData());
    }
}
