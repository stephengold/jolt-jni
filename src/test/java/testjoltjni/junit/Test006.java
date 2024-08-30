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

import com.github.stephengold.joltjni.BoxShapeSettings;
import com.github.stephengold.joltjni.CapsuleShapeSettings;
import com.github.stephengold.joltjni.ConvexHullShapeSettings;
import com.github.stephengold.joltjni.ConvexShapeSettings;
import com.github.stephengold.joltjni.CylinderShapeSettings;
import com.github.stephengold.joltjni.HeightFieldShapeSettings;
import com.github.stephengold.joltjni.IndexedTriangleList;
import com.github.stephengold.joltjni.Jolt;
import com.github.stephengold.joltjni.MeshShapeSettings;
import com.github.stephengold.joltjni.MutableCompoundShapeSettings;
import com.github.stephengold.joltjni.PhysicsMaterial;
import com.github.stephengold.joltjni.Plane;
import com.github.stephengold.joltjni.PlaneShapeSettings;
import com.github.stephengold.joltjni.Quat;
import com.github.stephengold.joltjni.RotatedTranslatedShapeSettings;
import com.github.stephengold.joltjni.ScaledShapeSettings;
import com.github.stephengold.joltjni.ShapeRefC;
import com.github.stephengold.joltjni.ShapeSettings;
import com.github.stephengold.joltjni.SphereShape;
import com.github.stephengold.joltjni.SphereShapeSettings;
import com.github.stephengold.joltjni.StaticCompoundShapeSettings;
import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.VertexList;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.FloatBuffer;
import org.junit.Assert;
import org.junit.Test;
import testjoltjni.TestUtils;

/**
 * Automated JUnit4 tests for creation, destruction, accessors, and defaults of
 * {@code ShapeSettings} subclasses.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Test006 {
    // *************************************************************************
    // new methods exposed

    /**
     * Test object creation, destruction, accessors, and defaults.
     */
    @Test
    public void test006() {
        TestUtils.loadAndInitializeNativeLibrary();

        doBoxShapeSettings();
        doCapsuleShapeSettings();
        doConvexHullShapeSettings();
        doCylinderShapeSettings();
        doHeightFieldShapeSettings();
        doMeshShapeSettings();
        doMutableCompoundShapeSettings();
        doPlaneShapeSettings();
        doRotatedTranslatedShapeSettings();
        doSphereShapeSettings();
        doStaticCompoundShapeSettings();
        doScaledShapeSettings();

        TestUtils.cleanup();
    }
    // *************************************************************************
    // Java private methods

    /**
     * Test the {@code BoxShapeSettings} class.
     */
    private static void doBoxShapeSettings() {
        BoxShapeSettings settings = new BoxShapeSettings(new Vec3(1f, 1f, 1f));

        testBoxSsDefaults(settings);
        testBoxSsSetters(settings);

        TestUtils.testClose(settings);
        System.gc();
    }

    /**
     * Test the {@code CapsuleShapeSettings} class.
     */
    private static void doCapsuleShapeSettings() {
        CapsuleShapeSettings settings = new CapsuleShapeSettings(1f, 1f);

        testCapsuleSsDefaults(settings);

        TestUtils.testClose(settings);
        System.gc();
    }

    /**
     * Test the {@code ConvexHullShapeSettings} class.
     */
    private static void doConvexHullShapeSettings() {
        Vec3Arg[] points = {new Vec3()};
        ConvexHullShapeSettings settings = new ConvexHullShapeSettings(points);

        testConvexHullSsDefaults(settings);
        testConvexHullSsSetters(settings);

        TestUtils.testClose(settings);
        System.gc();
    }

    /**
     * Test the {@code CylinderShapeSettings} class.
     */
    private static void doCylinderShapeSettings() {
        CylinderShapeSettings settings = new CylinderShapeSettings(1f, 1f);

        testCylinderSsDefaults(settings);
        testCylinderSsSetters(settings);

        TestUtils.testClose(settings);
        System.gc();
    }

    /**
     * Test the {@code HeightFieldShapeSettings} class.
     */
    private static void doHeightFieldShapeSettings() {
        int sampleCount = 4;
        FloatBuffer samples = Jolt.newDirectFloatBuffer(sampleCount);
        HeightFieldShapeSettings settings = new HeightFieldShapeSettings(
                samples, new Vec3(), new Vec3(1f, 1f, 1f), sampleCount);

        testHeightFieldSsDefaults(settings);
        testHeightFieldSsSetters(settings);

        TestUtils.testClose(settings);
        System.gc();
    }

    /**
     * Test the {@code MeshShapeSettings} class.
     */
    private static void doMeshShapeSettings() {
        VertexList vertices = new VertexList();
        IndexedTriangleList indices = new IndexedTriangleList();
        MeshShapeSettings settings = new MeshShapeSettings(vertices, indices);

        testMeshSsDefaults(settings);
        testMeshSsSetters(settings);

        TestUtils.testClose(settings, indices);
        System.gc();
    }

    /**
     * Test the {@code MutableCompoundShapeSettings} class.
     */
    private static void doMutableCompoundShapeSettings() {
        MutableCompoundShapeSettings settings
                = new MutableCompoundShapeSettings();

        testMutableCompoundSsDefaults(settings);

        TestUtils.testClose(settings);
        System.gc();
    }

    /**
     * Test the {@code PlaneShapeSettings} class.
     */
    private static void doPlaneShapeSettings() {
        Plane plane = new Plane(0f, 1f, 0f, 0f);
        PlaneShapeSettings settings = new PlaneShapeSettings(plane);

        testPlaneSsDefaults(settings);
        testPlaneSsSetters(settings);

        TestUtils.testClose(settings);
        System.gc();
    }

    /**
     * Test the {@code RotatedTranslatedShapeSettings} class.
     */
    private static void doRotatedTranslatedShapeSettings() {
        ShapeRefC baseShapeRef = new SphereShape(1f).toRefC();
        RotatedTranslatedShapeSettings settings
                = new RotatedTranslatedShapeSettings(
                        new Vec3(), new Quat(), baseShapeRef);

        testRotatedTranslatedSsDefaults(settings);
        testRotatedTranslatedSsSetters(settings);

        TestUtils.testClose(settings, baseShapeRef);
        System.gc();
    }

    /**
     * Test the {@code ScaledShapeSettings} class.
     */
    private static void doScaledShapeSettings() {
        ShapeRefC baseShapeRef = new SphereShape(1f).toRefC();
        ScaledShapeSettings settings
                = new ScaledShapeSettings(baseShapeRef, new Vec3(1f, 1f, 1f));

        testScaledSsDefaults(settings);

        TestUtils.testClose(settings, baseShapeRef);
        System.gc();
    }

    /**
     * Test the {@code SphereShapeSettings} class.
     */
    private static void doSphereShapeSettings() {
        SphereShapeSettings settings = new SphereShapeSettings(1f);

        testSphereSsDefaults(settings);
        testSphereSsSetters(settings);

        TestUtils.testClose(settings);
        System.gc();
    }

    /**
     * Test the {@code StaticCompoundShapeSettings} class.
     */
    private static void doStaticCompoundShapeSettings() {
        StaticCompoundShapeSettings settings
                = new StaticCompoundShapeSettings();

        testStaticCompoundSsDefaults(settings);

        TestUtils.testClose(settings);
        System.gc();
    }

    /**
     * Test the getters and defaults of the specified {@code BoxShapeSettings}.
     *
     * @param settings the settings to test (not null, unaffected)
     */
    private static void testBoxSsDefaults(BoxShapeSettings settings) {
        testConvexSsDefaults(settings);

        Assert.assertEquals(0.05f, settings.getConvexRadius(), 0f);
        TestUtils.assertEquals(
                1f, 1f, 1f, settings.getHalfExtent(), 0f);
    }

    /**
     * Test the setters of the specified {@code BoxShapeSettings}.
     *
     * @param settings the settings to test (not null, modified)
     */
    private static void testBoxSsSetters(BoxShapeSettings settings) {
        settings.setConvexRadius(0.1f);
        settings.setHalfExtent(new Vec3(2f, 3f, 4f));
        settings.setMaterial(PhysicsMaterial.sDefault());

        Assert.assertEquals(0.1f, settings.getConvexRadius(), 0f);
        TestUtils.assertEquals(
                2f, 3f, 4f, settings.getHalfExtent(), 0f);
        Assert.assertEquals(PhysicsMaterial.sDefault(), settings.getMaterial());
    }

    /**
     * Test the getters and defaults of the specified
     * {@code CapsuleShapeSettings}.
     *
     * @param settings the settings to test (not null, unaffected)
     */
    private static void testCapsuleSsDefaults(CapsuleShapeSettings settings) {
        testConvexSsDefaults(settings);
    }

    /**
     * Test the getters and defaults of the specified
     * {@code ConvexHullShapeSettings}.
     *
     * @param settings the settings to test (not null, unaffected)
     */
    private static void testConvexHullSsDefaults(
            ConvexHullShapeSettings settings) {
        testConvexSsDefaults(settings);

        Assert.assertEquals(1, settings.countPoints());
        Assert.assertEquals(0.001f, settings.getHullTolerance(), 0f);
        Assert.assertEquals(0.05f, settings.getMaxConvexRadius(), 0f);
        Assert.assertEquals(0.05f, settings.getMaxErrorConvexRadius(), 0f);
    }

    /**
     * Test the setters of the specified {@code ConvexHullShapeSettings}.
     *
     * @param settings the settings to test (not null, modified)
     */
    private static void testConvexHullSsSetters(
            ConvexHullShapeSettings settings) {
        settings.setHullTolerance(0.1f);
        settings.setMaterial(PhysicsMaterial.sDefault());
        settings.setMaxConvexRadius(0.2f);
        settings.setMaxErrorConvexRadius(0.3f);

        Assert.assertEquals(0.1f, settings.getHullTolerance(), 0f);
        Assert.assertEquals(PhysicsMaterial.sDefault(), settings.getMaterial());
        Assert.assertEquals(0.2f, settings.getMaxConvexRadius(), 0f);
        Assert.assertEquals(0.3f, settings.getMaxErrorConvexRadius(), 0f);
    }

    /**
     * Test the getters and defaults of the specified
     * {@code ConvexShapeSettings}.
     *
     * @param settings the settings to test (not null, unaffected)
     */
    private static void testConvexSsDefaults(ConvexShapeSettings settings) {
        testSsDefaults(settings);
        Assert.assertEquals(1_000f, settings.getDensity(), 0f);
        Assert.assertNull(settings.getMaterial());
    }

    /**
     * Test the getters and defaults of the specified
     * {@code CylinderShapeSettings}.
     *
     * @param settings the settings to test (not null, unaffected)
     */
    private static void testCylinderSsDefaults(CylinderShapeSettings settings) {
        testConvexSsDefaults(settings);

        Assert.assertEquals(0.05f, settings.getConvexRadius(), 0f);
        Assert.assertEquals(1f, settings.getHalfHeight(), 0f);
        Assert.assertEquals(1f, settings.getRadius(), 0f);
    }

    /**
     * Test the setters of the specified {@code CylinderShapeSettings}.
     *
     * @param settings the settings to test (not null, modified)
     */
    private static void testCylinderSsSetters(CylinderShapeSettings settings) {
        settings.setConvexRadius(0.1f);
        settings.setHalfHeight(0.2f);
        settings.setMaterial(PhysicsMaterial.sDefault());
        settings.setRadius(0.3f);

        Assert.assertEquals(0.1f, settings.getConvexRadius(), 0f);
        Assert.assertEquals(0.2f, settings.getHalfHeight(), 0f);
        Assert.assertEquals(PhysicsMaterial.sDefault(), settings.getMaterial());
        Assert.assertEquals(0.3f, settings.getRadius(), 0f);
    }

    /**
     * Test the getters and defaults of the specified
     * {@code HeightFieldShapeSettings}.
     *
     * @param settings the settings to test (not null, unaffected)
     */
    private static void testHeightFieldSsDefaults(
            HeightFieldShapeSettings settings) {
        testSsDefaults(settings);

        Assert.assertEquals(
                0.996195f, settings.getActiveEdgeCosThresholdAngle(), 0f);
        Assert.assertEquals(-Float.MAX_VALUE, settings.getMaxHeightValue(), 0f);
        Assert.assertEquals(Float.MAX_VALUE, settings.getMinHeightValue(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, settings.getOffset(), 0f);
        Assert.assertEquals(4, settings.getSampleCount());
        TestUtils.assertEquals(1f, 1f, 1f, settings.getScale(), 0f);
    }

    /**
     * Test the setters of the specified {@code HeightFieldShapeSettings}.
     *
     * @param settings the settings to test (not null, modified)
     */
    private static void testHeightFieldSsSetters(
            HeightFieldShapeSettings settings) {
        settings.setActiveEdgeCosThresholdAngle(0.5f);
        settings.setMaxHeightValue(1f);
        settings.setMinHeightValue(-1f);
        settings.setOffset(new Vec3(2f, 3f, 4f));
        settings.setScale(new Vec3(5f, 6f, 7f));

        Assert.assertEquals(
                0.5f, settings.getActiveEdgeCosThresholdAngle(), 0f);
        Assert.assertEquals(1f, settings.getMaxHeightValue(), 0f);
        Assert.assertEquals(-1f, settings.getMinHeightValue(), 0f);
        TestUtils.assertEquals(2f, 3f, 4f, settings.getOffset(), 0f);
        TestUtils.assertEquals(5f, 6f, 7f, settings.getScale(), 0f);
    }

    /**
     * Test the getters and defaults of the specified {@code MeshShapeSettings}.
     *
     * @param settings the settings to test (not null, unaffected)
     */
    private static void testMeshSsDefaults(MeshShapeSettings settings) {
        testSsDefaults(settings);

        Assert.assertEquals(0, settings.countTriangles());
        Assert.assertEquals(0, settings.countTriangleVertices());
        Assert.assertEquals(
                0.996195f, settings.getActiveEdgeCosThresholdAngle(), 0f);
        Assert.assertEquals(8, settings.getMaxTrianglesPerLeaf());
        Assert.assertFalse(settings.getPerTriangleUserData());
    }

    /**
     * Test the setters of the specified {@code MeshShapeSettings}.
     *
     * @param settings the settings to test (not null, modified)
     */
    private static void testMeshSsSetters(MeshShapeSettings settings) {
        settings.setActiveEdgeCosThresholdAngle(0.8f);
        settings.setMaxTrianglesPerLeaf(4);
        settings.setPerTriangleUserData(true);

        Assert.assertEquals(
                0.8f, settings.getActiveEdgeCosThresholdAngle(), 0f);
        Assert.assertEquals(4, settings.getMaxTrianglesPerLeaf());
        Assert.assertTrue(settings.getPerTriangleUserData());
    }

    /**
     * Test the getters and defaults of the specified
     * {@code MutableCompoundShapeSettings}.
     *
     * @param settings the settings to test (not null, unaffected)
     */
    private static void testMutableCompoundSsDefaults(
            MutableCompoundShapeSettings settings) {
        testSsDefaults(settings);
    }

    /**
     * Test the getters and defaults of the specified
     * {@code PlaneShapeSettings}.
     *
     * @param settings the settings to test (not null, unaffected)
     */
    private static void testPlaneSsDefaults(PlaneShapeSettings settings) {
        testSsDefaults(settings);

        Assert.assertEquals(PlaneShapeSettings.cDefaultHalfExtent,
                settings.getHalfExtent(), 0f);
        Assert.assertNull(settings.getMaterial());
        TestUtils.assertEquals(
                0f, 1f, 0f, settings.getPlane().getNormal(), 0f);
        Assert.assertEquals(0f, settings.getPlane().getConstant(), 0f);
    }

    /**
     * Test the setters of the specified {@code PlaneShapeSettings}.
     *
     * @param settings the settings to test (not null, modified)
     */
    private static void testPlaneSsSetters(PlaneShapeSettings settings) {
        settings.setHalfExtent(99f);
        settings.setMaterial(PhysicsMaterial.sDefault());
        settings.setPlane(new Plane(0.6f, 0.8f, 0f, 2f));

        Assert.assertEquals(99f, settings.getHalfExtent(), 0f);
        Assert.assertEquals(PhysicsMaterial.sDefault(), settings.getMaterial());
        TestUtils.assertEquals(
                0.6f, 0.8f, 0f, settings.getPlane().getNormal(), 0f);
        Assert.assertEquals(2f, settings.getPlane().getConstant(), 0f);
    }

    /**
     * Test the getters and defaults of the specified
     * {@code RotatedTranslatedShapeSettings}.
     *
     * @param settings the settings to test (not null, unaffected)
     */
    private static void testRotatedTranslatedSsDefaults(
            RotatedTranslatedShapeSettings settings) {
        testSsDefaults(settings);

        TestUtils.assertEquals(0f, 0f, 0f, settings.getPosition(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, 1f, settings.getRotation(), 0f);
    }

    /**
     * Test the setters of the specified {@code RotatedTranslatedShapeSettings}.
     *
     * @param settings the settings to test (not null, modified)
     */
    private static void testRotatedTranslatedSsSetters(
            RotatedTranslatedShapeSettings settings) {
        settings.setPosition(new Vec3(2f, 3f, 4f));
        settings.setRotation(new Quat(-0.5f, 0.5f, -0.5f, 0.5f));

        TestUtils.assertEquals(2f, 3f, 4f, settings.getPosition(), 0f);
        TestUtils.assertEquals(
                -0.5f, 0.5f, -0.5f, 0.5f, settings.getRotation(), 0f);
    }

    /**
     * Test the getters and defaults of the specified
     * {@code ScaledShapeSettings}.
     *
     * @param settings the settings to test (not null, unaffected)
     */
    private static void testScaledSsDefaults(ScaledShapeSettings settings) {
        testSsDefaults(settings);
    }

    /**
     * Test the getters and defaults of the specified
     * {@code SphereShapeSettings}.
     *
     * @param settings the settings to test (not null, unaffected)
     */
    private static void testSphereSsDefaults(SphereShapeSettings settings) {
        testConvexSsDefaults(settings);
        Assert.assertEquals(1f, settings.getRadius(), 0f);
    }

    /**
     * Test the setters of the specified {@code SphereShapeSettings}.
     *
     * @param settings the settings to test (not null, modified)
     */
    private static void testSphereSsSetters(SphereShapeSettings settings) {
        settings.setMaterial(PhysicsMaterial.sDefault());
        settings.setRadius(9f);

        Assert.assertEquals(PhysicsMaterial.sDefault(), settings.getMaterial());
        Assert.assertEquals(9f, settings.getRadius(), 0f);
    }

    /**
     * Test the getters and defaults of the specified {@code ShapeSettings}.
     *
     * @param settings the settings to test (not null, unaffected)
     */
    private static void testSsDefaults(ShapeSettings settings) {
        Assert.assertTrue(settings.hasAssignedNativeObject());
        Assert.assertFalse(settings.ownsNativeObject());
        Assert.assertEquals(0, settings.getRefCount());
    }

    /**
     * Test the getters and defaults of the specified
     * {@code StaticCompoundShapeSettings}.
     *
     * @param settings the settings to test (not null, unaffected)
     */
    private static void testStaticCompoundSsDefaults(
            StaticCompoundShapeSettings settings) {
        testSsDefaults(settings);
    }
}
