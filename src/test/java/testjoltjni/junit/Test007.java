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
import com.github.stephengold.joltjni.ConvexHullShape;
import com.github.stephengold.joltjni.ConvexHullShapeSettings;
import com.github.stephengold.joltjni.CylinderShape;
import com.github.stephengold.joltjni.CylinderShapeSettings;
import com.github.stephengold.joltjni.Float3;
import com.github.stephengold.joltjni.HeightFieldShape;
import com.github.stephengold.joltjni.HeightFieldShapeSettings;
import com.github.stephengold.joltjni.IndexedTriangle;
import com.github.stephengold.joltjni.IndexedTriangleList;
import com.github.stephengold.joltjni.Jolt;
import com.github.stephengold.joltjni.MeshShape;
import com.github.stephengold.joltjni.MeshShapeSettings;
import com.github.stephengold.joltjni.MutableCompoundShape;
import com.github.stephengold.joltjni.MutableCompoundShapeSettings;
import com.github.stephengold.joltjni.Plane;
import com.github.stephengold.joltjni.PlaneShape;
import com.github.stephengold.joltjni.PlaneShapeSettings;
import com.github.stephengold.joltjni.Quat;
import com.github.stephengold.joltjni.RotatedTranslatedShape;
import com.github.stephengold.joltjni.RotatedTranslatedShapeSettings;
import com.github.stephengold.joltjni.ScaledShape;
import com.github.stephengold.joltjni.ScaledShapeSettings;
import com.github.stephengold.joltjni.ShapeRefC;
import com.github.stephengold.joltjni.ShapeResult;
import com.github.stephengold.joltjni.SphereShape;
import com.github.stephengold.joltjni.SphereShapeSettings;
import com.github.stephengold.joltjni.StaticCompoundShape;
import com.github.stephengold.joltjni.StaticCompoundShapeSettings;
import com.github.stephengold.joltjni.TaperedCapsuleShape;
import com.github.stephengold.joltjni.TaperedCapsuleShapeSettings;
import com.github.stephengold.joltjni.TaperedCylinderShape;
import com.github.stephengold.joltjni.TaperedCylinderShapeSettings;
import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.VertexList;
import com.github.stephengold.joltjni.enumerate.EShapeSubType;
import com.github.stephengold.joltjni.enumerate.EShapeType;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.FloatBuffer;
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
        doConvexHullShape();
        doCylinderShape();
        doHeightFieldShape();
        doMeshShape();
        doMutableCompoundShape();
        doPlaneShape();
        doRotatedTranslatedShape();
        doSphereShape();
        doStaticCompoundShape();
        doScaledShape();
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
        Assert.assertEquals(3, shape.getRefCount());

        BoxShape shape2 = new BoxShape(new Vec3(1f, 1f, 1f));
        testBoxDefaults(shape2);
        Assert.assertEquals(0, shape2.getRefCount());

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
        Assert.assertEquals(3, shape.getRefCount());

        CapsuleShape shape2 = new CapsuleShape(1f, 1f);
        testCapsuleDefaults(shape2);
        Assert.assertEquals(0, shape2.getRefCount());

        TestUtils.testClose(shape2, shape, ref, result, settings);
        System.gc();
    }

    /**
     * Test the {@code ConvexHullShape} class.
     */
    private static void doConvexHullShape() {
        // Create a tetrahedron:
        Vec3Arg[] points = {new Vec3(3f, 3f, 3f), new Vec3(-3f, 3f, -3f),
            new Vec3(3f, -3f, -3f), new Vec3(-3f, -3f, 3f)};
        ConvexHullShapeSettings settings = new ConvexHullShapeSettings(points);
        ShapeResult result = settings.create();
        Assert.assertFalse(result.hasError());
        Assert.assertTrue(result.isValid());
        ShapeRefC ref = result.get();
        ConvexHullShape shape = (ConvexHullShape) ref.getPtr();

        TestUtils.assertEquals(0f, 0f, 0f, shape.getCenterOfMass(), 0f);
        Assert.assertEquals(3, shape.getFaceVertices(3, new int[3]));
        Assert.assertEquals(Math.sqrt(3.), shape.getInnerRadius(), 1e-6f);
        Assert.assertEquals(4, shape.getNumFaces());
        Assert.assertEquals(3, shape.getNumVerticesInFace(3));
        Assert.assertEquals(3, shape.getRefCount());
        Assert.assertEquals(EShapeSubType.ConvexHull, shape.getSubType());
        Assert.assertEquals(EShapeType.Convex, shape.getType());
        Assert.assertEquals(0L, shape.getUserData());
        Assert.assertFalse(shape.mustBeStatic());

        TestUtils.testClose(shape, ref, result, settings);
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
        Assert.assertEquals(3, shape.getRefCount());

        CylinderShape shape2 = new CylinderShape(1f, 1f);
        testCylinderDefaults(shape2);
        Assert.assertEquals(0, shape2.getRefCount());

        TestUtils.testClose(shape2, shape, ref, result, settings);
        System.gc();
    }

    /**
     * Test the {@code HeightFieldShape} class.
     */
    private static void doHeightFieldShape() {
        int sampleCount = 4;
        FloatBuffer samples = Jolt.newDirectFloatBuffer(sampleCount);
        HeightFieldShapeSettings settings = new HeightFieldShapeSettings(
                samples, new Vec3(), new Vec3(1f, 1f, 1f), sampleCount);
        ShapeResult result = settings.create();
        Assert.assertFalse(result.hasError());
        Assert.assertTrue(result.isValid());
        ShapeRefC ref = result.get();
        HeightFieldShape shape = (HeightFieldShape) ref.getPtr();

        TestUtils.assertEquals(0f, 0f, 0f, shape.getCenterOfMass(), 0f);
        Assert.assertEquals(0f, shape.getInnerRadius(), 0f);
        Assert.assertEquals(3, shape.getRefCount());
        Assert.assertEquals(EShapeSubType.HeightField, shape.getSubType());
        Assert.assertEquals(EShapeType.HeightField, shape.getType());
        Assert.assertEquals(0L, shape.getUserData());
        Assert.assertTrue(shape.mustBeStatic());

        TestUtils.testClose(shape, ref, result, settings);
        System.gc();
    }

    /**
     * Test the {@code MeshShape} class.
     */
    private static void doMeshShape() {
        // Create a square:
        VertexList vertices = new VertexList();
        vertices.resize(4);
        vertices.set(0, new Float3(1f, 0f, 1f));
        vertices.set(1, new Float3(1f, 0f, -1f));
        vertices.set(2, new Float3(-1f, 0f, 1f));
        vertices.set(3, new Float3(-1f, 0f, -1f));
        IndexedTriangleList indices = new IndexedTriangleList();
        IndexedTriangle triangle1 = new IndexedTriangle(0, 2, 3);
        IndexedTriangle triangle2 = new IndexedTriangle(3, 1, 0);
        indices.pushBack(triangle1);
        indices.pushBack(triangle2);
        MeshShapeSettings settings = new MeshShapeSettings(vertices, indices);
        ShapeResult result = settings.create();
        Assert.assertFalse(result.hasError());
        Assert.assertTrue(result.isValid());
        ShapeRefC ref = result.get();
        MeshShape shape = (MeshShape) ref.getPtr();

        TestUtils.assertEquals(0f, 0f, 0f, shape.getCenterOfMass(), 0f);
        Assert.assertEquals(0f, shape.getInnerRadius(), 0f);
        Assert.assertEquals(3, shape.getRefCount());
        Assert.assertEquals(EShapeSubType.Mesh, shape.getSubType());
        Assert.assertEquals(EShapeType.Mesh, shape.getType());
        Assert.assertEquals(0L, shape.getUserData());
        Assert.assertTrue(shape.mustBeStatic());

        TestUtils.testClose(
                shape, ref, result, settings, triangle2, triangle1, indices);
        System.gc();
    }

    /**
     * Test the {@code MutableCompoundShape} class.
     */
    private static void doMutableCompoundShape() {
        MutableCompoundShapeSettings settings
                = new MutableCompoundShapeSettings();
        ShapeResult result = settings.create();
        Assert.assertFalse(result.hasError());
        Assert.assertTrue(result.isValid());
        ShapeRefC ref = result.get();
        MutableCompoundShape shape = (MutableCompoundShape) ref.getPtr();

        TestUtils.assertEquals(0f, 0f, 0f, shape.getCenterOfMass(), 0f);
        Assert.assertEquals(Float.MAX_VALUE, shape.getInnerRadius(), 0f);
        Assert.assertEquals(3, shape.getRefCount());
        Assert.assertEquals(EShapeSubType.MutableCompound, shape.getSubType());
        Assert.assertEquals(EShapeType.Compound, shape.getType());
        Assert.assertEquals(0L, shape.getUserData());
        Assert.assertFalse(shape.mustBeStatic());

        TestUtils.testClose(shape, ref, result, settings);
        System.gc();
    }

    /**
     * Test the {@code PlaneShape} class.
     */
    private static void doPlaneShape() {
        Plane plane = new Plane(0f, 1f, 0f, 0f);
        PlaneShapeSettings settings = new PlaneShapeSettings(plane);
        ShapeResult result = settings.create();
        Assert.assertFalse(result.hasError());
        Assert.assertTrue(result.isValid());
        ShapeRefC ref = result.get();
        PlaneShape shape = (PlaneShape) ref.getPtr();

        TestUtils.assertEquals(0f, 0f, 0f, shape.getCenterOfMass(), 0f);
        Assert.assertEquals(0f, shape.getInnerRadius(), 0f);
        Assert.assertEquals(3, shape.getRefCount());
        Assert.assertEquals(EShapeSubType.Plane, shape.getSubType());
        Assert.assertEquals(EShapeType.Plane, shape.getType());
        Assert.assertEquals(0L, shape.getUserData());
        Assert.assertTrue(shape.mustBeStatic());

        TestUtils.testClose(shape, result, settings);
        System.gc();
    }

    /**
     * Test the {@code RotatedTranslatedShape} class.
     */
    private static void doRotatedTranslatedShape() {
        ShapeRefC baseShapeRef = new SphereShape(1f).toRefC();
        RotatedTranslatedShapeSettings settings
                = new RotatedTranslatedShapeSettings(
                        Vec3.sAxisX(), new Quat(), baseShapeRef);
        ShapeResult result = settings.create();
        Assert.assertFalse(result.hasError());
        Assert.assertTrue(result.isValid());
        ShapeRefC ref = result.get();
        RotatedTranslatedShape shape = (RotatedTranslatedShape) ref.getPtr();

        TestUtils.assertEquals(1f, 0f, 0f, shape.getCenterOfMass(), 0f);
        Assert.assertEquals(1f, shape.getInnerRadius(), 0f);
        Assert.assertEquals(3, shape.getRefCount());
        Assert.assertEquals(
                EShapeSubType.RotatedTranslated, shape.getSubType());
        Assert.assertEquals(EShapeType.Decorated, shape.getType());
        Assert.assertEquals(0L, shape.getUserData());
        Assert.assertFalse(shape.mustBeStatic());

        TestUtils.testClose(shape, ref, result, settings, baseShapeRef);
        System.gc();
    }

    /**
     * Test the {@code ScaledShape} class.
     */
    private static void doScaledShape() {
        ShapeRefC baseShapeRef = new SphereShape(1f).toRefC();
        ScaledShapeSettings settings
                = new ScaledShapeSettings(baseShapeRef, new Vec3(1f, 1f, 1f));
        ShapeResult result = settings.create();
        Assert.assertFalse(result.hasError());
        Assert.assertTrue(result.isValid());
        ShapeRefC ref = result.get();
        ScaledShape shape = (ScaledShape) ref.getPtr();

        TestUtils.assertEquals(0f, 0f, 0f, shape.getCenterOfMass(), 0f);
        Assert.assertEquals(1f, shape.getInnerRadius(), 0f);
        Assert.assertEquals(3, shape.getRefCount());
        Assert.assertEquals(EShapeSubType.Scaled, shape.getSubType());
        Assert.assertEquals(EShapeType.Decorated, shape.getType());
        Assert.assertEquals(0L, shape.getUserData());
        Assert.assertFalse(shape.mustBeStatic());

        TestUtils.testClose(shape, ref, result, settings, baseShapeRef);
        System.gc();
    }

    /**
     * Test the {@code SphereShape} class.
     */
    private static void doSphereShape() {
        SphereShapeSettings settings = new SphereShapeSettings(1f);
        ShapeResult result = settings.create();
        Assert.assertFalse(result.hasError());
        Assert.assertTrue(result.isValid());
        ShapeRefC ref = result.get();
        SphereShape shape = (SphereShape) ref.getPtr();
        testSphereDefaults(shape);
        Assert.assertEquals(3, shape.getRefCount());

        SphereShape shape2 = new SphereShape(1f);
        testSphereDefaults(shape2);
        Assert.assertEquals(0, shape2.getRefCount());

        TestUtils.testClose(shape2, shape, result, settings);
        System.gc();
    }

    /**
     * Test the {@code StaticCompoundShape} class.
     */
    private static void doStaticCompoundShape() {
        StaticCompoundShapeSettings settings
                = new StaticCompoundShapeSettings();
        SphereShapeSettings settings1 = new SphereShapeSettings(1f);
        settings.addShape(Vec3.sAxisX(), Quat.sIdentity(), settings1);
        settings.addShape(new Vec3(-1f, 0f, 0f), Quat.sIdentity(), settings1);
        ShapeResult result = settings.create();
        Assert.assertFalse(result.hasError());
        Assert.assertTrue(result.isValid());
        ShapeRefC ref = result.get();
        StaticCompoundShape shape = (StaticCompoundShape) ref.getPtr();

        TestUtils.assertEquals(0f, 0f, 0f, shape.getCenterOfMass(), 0f);
        Assert.assertEquals(1f, shape.getInnerRadius(), 0f);
        Assert.assertEquals(3, shape.getRefCount());
        Assert.assertEquals(EShapeSubType.StaticCompound, shape.getSubType());
        Assert.assertEquals(EShapeType.Compound, shape.getType());
        Assert.assertEquals(0L, shape.getUserData());
        Assert.assertFalse(shape.mustBeStatic());

        TestUtils.testClose(shape, ref, result, settings1, settings);
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

        Assert.assertEquals(1f, shape.getBottomRadius(), 0f);
        TestUtils.assertEquals(0f, 0.5f, 0f, shape.getCenterOfMass(), 0f);
        Assert.assertEquals(1f, shape.getHalfHeight(), 0f);
        Assert.assertEquals(1f, shape.getInnerRadius(), 0f);
        Assert.assertEquals(3, shape.getRefCount());
        Assert.assertEquals(EShapeSubType.TaperedCapsule, shape.getSubType());
        Assert.assertEquals(2f, shape.getTopRadius(), 0f);
        Assert.assertEquals(EShapeType.Convex, shape.getType());
        Assert.assertEquals(0L, shape.getUserData());
        Assert.assertFalse(shape.mustBeStatic());

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

        Assert.assertEquals(1f, shape.getBottomRadius(), 0f);
        TestUtils.assertEquals(
                0f, -0.473872f, 0f, shape.getCenterOfMass(), 1e-6f);
        Assert.assertEquals(0.05f, shape.getConvexRadius(), 0f);
        Assert.assertEquals(1f, shape.getHalfHeight(), 0f);
        Assert.assertEquals(0.05f, shape.getInnerRadius(), 0f);
        Assert.assertEquals(3, shape.getRefCount());
        Assert.assertEquals(EShapeSubType.TaperedCylinder, shape.getSubType());
        Assert.assertEquals(0.05f, shape.getTopRadius(), 0f);
        Assert.assertEquals(EShapeType.Convex, shape.getType());
        Assert.assertEquals(0L, shape.getUserData());
        Assert.assertFalse(shape.mustBeStatic());

        TestUtils.testClose(shape, ref, result, settings);
        System.gc();
    }

    /**
     * Test the getters and defaults of the specified {@code BoxShape}.
     *
     * @param shape the settings to test (not null, unaffected)
     */
    private static void testBoxDefaults(BoxShape shape) {
        TestUtils.assertEquals(0f, 0f, 0f, shape.getCenterOfMass(), 0f);
        Assert.assertEquals(0.05f, shape.getConvexRadius(), 0f);
        TestUtils.assertEquals(1f, 1f, 1f, shape.getHalfExtent(), 0f);
        Assert.assertEquals(1f, shape.getInnerRadius(), 0f);
        Assert.assertEquals(EShapeSubType.Box, shape.getSubType());
        Assert.assertEquals(EShapeType.Convex, shape.getType());
        Assert.assertEquals(0L, shape.getUserData());
        Assert.assertFalse(shape.mustBeStatic());
    }

    /**
     * Test the getters and defaults of the specified {@code CapsuleShape}.
     *
     * @param shape the settings to test (not null, unaffected)
     */
    private static void testCapsuleDefaults(CapsuleShape shape) {
        TestUtils.assertEquals(0f, 0f, 0f, shape.getCenterOfMass(), 0f);
        Assert.assertEquals(1f, shape.getInnerRadius(), 0f);
        Assert.assertEquals(EShapeSubType.Capsule, shape.getSubType());
        Assert.assertEquals(EShapeType.Convex, shape.getType());
        Assert.assertEquals(0L, shape.getUserData());
        Assert.assertFalse(shape.mustBeStatic());
    }

    /**
     * Test the getters and defaults of the specified {@code CylinderShape}.
     *
     * @param shape the settings to test (not null, unaffected)
     */
    private static void testCylinderDefaults(CylinderShape shape) {
        TestUtils.assertEquals(0f, 0f, 0f, shape.getCenterOfMass(), 0f);
        Assert.assertEquals(0.05f, shape.getConvexRadius(), 0f);
        Assert.assertEquals(1f, shape.getInnerRadius(), 0f);
        Assert.assertEquals(EShapeSubType.Cylinder, shape.getSubType());
        Assert.assertEquals(EShapeType.Convex, shape.getType());
        Assert.assertEquals(0L, shape.getUserData());
        Assert.assertFalse(shape.mustBeStatic());
    }

    /**
     * Test the getters and defaults of the specified {@code SphereShape}.
     *
     * @param shape the settings to test (not null, unaffected)
     */
    private static void testSphereDefaults(SphereShape shape) {
        TestUtils.assertEquals(0f, 0f, 0f, shape.getCenterOfMass(), 0f);
        Assert.assertEquals(1f, shape.getInnerRadius(), 0f);
        Assert.assertEquals(EShapeSubType.Sphere, shape.getSubType());
        Assert.assertEquals(EShapeType.Convex, shape.getType());
        Assert.assertEquals(0L, shape.getUserData());
        Assert.assertFalse(shape.mustBeStatic());
    }
}
