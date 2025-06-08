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

import com.github.stephengold.joltjni.BoxShapeSettings;
import com.github.stephengold.joltjni.CapsuleShapeSettings;
import com.github.stephengold.joltjni.ConvexHullShapeSettings;
import com.github.stephengold.joltjni.ConvexShapeSettings;
import com.github.stephengold.joltjni.CylinderShapeSettings;
import com.github.stephengold.joltjni.EmptyShapeSettings;
import com.github.stephengold.joltjni.HeightFieldShapeSettings;
import com.github.stephengold.joltjni.Jolt;
import com.github.stephengold.joltjni.MeshShapeSettings;
import com.github.stephengold.joltjni.MutableCompoundShapeSettings;
import com.github.stephengold.joltjni.OffsetCenterOfMassShapeSettings;
import com.github.stephengold.joltjni.PhysicsMaterial;
import com.github.stephengold.joltjni.Plane;
import com.github.stephengold.joltjni.PlaneShapeSettings;
import com.github.stephengold.joltjni.Quat;
import com.github.stephengold.joltjni.RotatedTranslatedShapeSettings;
import com.github.stephengold.joltjni.ScaledShapeSettings;
import com.github.stephengold.joltjni.ShapeRefC;
import com.github.stephengold.joltjni.ShapeSettings;
import com.github.stephengold.joltjni.ShapeSettingsRef;
import com.github.stephengold.joltjni.SphereShape;
import com.github.stephengold.joltjni.SphereShapeSettings;
import com.github.stephengold.joltjni.StaticCompoundShapeSettings;
import com.github.stephengold.joltjni.TaperedCapsuleShapeSettings;
import com.github.stephengold.joltjni.TaperedCylinderShapeSettings;
import com.github.stephengold.joltjni.Triangle;
import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.readonly.ConstPlane;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
        TestUtils.loadNativeLibrary();
        TestUtils.initializeNativeLibrary();

        doBoxShapeSettings();
        doCapsuleShapeSettings();
        doConvexHullShapeSettings();
        doCylinderShapeSettings();
        doEmptyShapeSettings();
        doHeightFieldShapeSettings();
        doMeshShapeSettings();
        doMutableCompoundShapeSettings();
        doOffsetCenterOfMassShapeSettings();
        doPlaneShapeSettings();
        doRotatedTranslatedShapeSettings();
        doSphereShapeSettings();
        doStaticCompoundShapeSettings();
        doScaledShapeSettings();
        doTaperedCapsuleShapeSettings();
        doTaperedCylinderShapeSettings();

        TestUtils.cleanup();
    }
    // *************************************************************************
    // Java private methods

    /**
     * Test the {@code BoxShapeSettings} class.
     */
    private static void doBoxShapeSettings() {
        BoxShapeSettings settings = new BoxShapeSettings();

        testBoxSsDefaults(settings);
        testBoxSsSetters(settings);

        TestUtils.testClose(settings);
        System.gc();
    }

    /**
     * Test the {@code CapsuleShapeSettings} class.
     */
    private static void doCapsuleShapeSettings() {
        CapsuleShapeSettings settings = new CapsuleShapeSettings();

        testCapsuleSsDefaults(settings);
        testCapsuleSsSetters(settings);

        TestUtils.testClose(settings);
        System.gc();
    }

    /**
     * Test the {@code ConvexHullShapeSettings} class.
     */
    private static void doConvexHullShapeSettings() {
        // create from an array:
        ConvexHullShapeSettings settings = new ConvexHullShapeSettings();

        testConvexHullSsDefaults(settings);
        testConvexHullSsSetters(settings);

        // create from a collection:
        Collection<Vec3Arg> list = List.of();
        ConvexHullShapeSettings settings2 = new ConvexHullShapeSettings(list);

        testConvexHullSsDefaults(settings2);
        testConvexHullSsSetters(settings2);

        TestUtils.testClose(settings2, settings);
        System.gc();
    }

    /**
     * Test the {@code CylinderShapeSettings} class.
     */
    private static void doCylinderShapeSettings() {
        CylinderShapeSettings settings = new CylinderShapeSettings();

        testCylinderSsDefaults(settings);
        testCylinderSsSetters(settings);

        CylinderShapeSettings settings2 = new CylinderShapeSettings(0f, 0f, 0f);

        testCylinderSsDefaults(settings2);
        testCylinderSsSetters(settings2);

        TestUtils.testClose(settings2, settings);
        System.gc();
    }

    /**
     * Test the {@code EmptyShapeSettings} class.
     */
    private static void doEmptyShapeSettings() {
        EmptyShapeSettings settings = new EmptyShapeSettings();

        testEmptySsDefaults(settings);
        testEmptySsSetters(settings);

        TestUtils.testClose(settings);
        System.gc();
    }

    /**
     * Test the {@code HeightFieldShapeSettings} class.
     */
    private static void doHeightFieldShapeSettings() {
        HeightFieldShapeSettings settings0 = new HeightFieldShapeSettings();

        testHeightFieldSsDefaults(settings0);
        testHeightFieldSsSetters(settings0);

        int sampleCount = 0;
        int numFloats = sampleCount * sampleCount;
        FloatBuffer samples = Jolt.newDirectFloatBuffer(numFloats);
        HeightFieldShapeSettings settings = new HeightFieldShapeSettings(
                samples, new Vec3(), new Vec3(1f, 1f, 1f), sampleCount);

        testHeightFieldSsDefaults(settings);
        testHeightFieldSsSetters(settings);

        float[] array = new float[numFloats];
        HeightFieldShapeSettings settings2 = new HeightFieldShapeSettings(
                array, new Vec3(), new Vec3(1f, 1f, 1f), sampleCount);

        testHeightFieldSsDefaults(settings2);
        testHeightFieldSsSetters(settings2);

        TestUtils.testClose(settings2, settings);
        System.gc();
    }

    /**
     * Test the {@code MeshShapeSettings} class.
     */
    private static void doMeshShapeSettings() {
        MeshShapeSettings settings = new MeshShapeSettings();

        testMeshSsDefaults(settings);
        testMeshSsSetters(settings);

        Triangle[] array = new Triangle[0];
        MeshShapeSettings settings2 = new MeshShapeSettings(array);

        testMeshSsDefaults(settings2);
        testMeshSsSetters(settings2);

        List<Triangle> list = new ArrayList<>(1);
        MeshShapeSettings settings3 = new MeshShapeSettings(list);

        testMeshSsDefaults(settings3);
        testMeshSsSetters(settings3);

        TestUtils.testClose(settings3, settings2, settings);
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
     * Test the {@code OffsetCenterOfMassShapeSettings} class.
     */
    private static void doOffsetCenterOfMassShapeSettings() {
        ShapeRefC baseShapeRef = new SphereShape(1f).toRefC();
        OffsetCenterOfMassShapeSettings settings
                = new OffsetCenterOfMassShapeSettings(new Vec3(), baseShapeRef);

        testOffsetCenterOfMassSsDefaults(settings);
        testOffsetCenterOfMassSsSetters(settings);

        TestUtils.testClose(settings, baseShapeRef);
        System.gc();
    }

    /**
     * Test the {@code PlaneShapeSettings} class.
     */
    private static void doPlaneShapeSettings() {
        PlaneShapeSettings settings = new PlaneShapeSettings();

        testPlaneSsDefaults(settings);
        testPlaneSsSetters(settings);

        ConstPlane plane = new Plane(0f, 0f, 0f, 0f);
        PlaneShapeSettings settings2 = new PlaneShapeSettings(plane);

        testPlaneSsDefaults(settings2);
        testPlaneSsSetters(settings2);

        TestUtils.testClose(settings2, settings);
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
     * Test the {@code TaperedCapsuleShapeSettings} class.
     */
    private static void doTaperedCapsuleShapeSettings() {
        TaperedCapsuleShapeSettings settings
                = new TaperedCapsuleShapeSettings(1f, 2f, 1f);

        testTaperedCapsuleSsDefaults(settings);
        testTaperedCapsuleSsSetters(settings);

        TestUtils.testClose(settings);
        System.gc();
    }

    /**
     * Test the {@code TaperedCylinderShapeSettings} class.
     */
    private static void doTaperedCylinderShapeSettings() {
        TaperedCylinderShapeSettings settings
                = new TaperedCylinderShapeSettings(1f, 0.05f, 1f);

        testTaperedCylinderSsDefaults(settings);
        testTaperedCylinderSsSetters(settings);

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

        Assert.assertEquals(0f, settings.getConvexRadius(), 0f);
        TestUtils.assertEquals(
                0f, 0f, 0f, settings.getHalfExtent(), 0f);
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

        ShapeSettingsRef ref = settings.toRef();
        Assert.assertEquals(1, settings.getRefCount());
        Assert.assertEquals(settings, ref.getPtr());

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

        Assert.assertEquals(0f, settings.getHalfHeightOfCylinder(), 0f);
        Assert.assertEquals(0f, settings.getRadius(), 0f);
    }

    /**
     * Test the setters of the specified {@code CapsuleShapeSettings}.
     *
     * @param settings the settings to test (not null, modified)
     */
    private static void testCapsuleSsSetters(CapsuleShapeSettings settings) {
        settings.setHalfHeightOfCylinder(0.2f);
        settings.setMaterial(PhysicsMaterial.sDefault());
        settings.setRadius(0.3f);

        ShapeSettingsRef ref = settings.toRef();
        Assert.assertEquals(1, settings.getRefCount());
        Assert.assertEquals(settings, ref.getPtr());

        Assert.assertEquals(0.2f, settings.getHalfHeightOfCylinder(), 0f);
        Assert.assertEquals(PhysicsMaterial.sDefault(), settings.getMaterial());
        Assert.assertEquals(0.3f, settings.getRadius(), 0f);
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

        Assert.assertEquals(0, settings.countPoints());
        Assert.assertEquals(0.001f, settings.getHullTolerance(), 0f);
        Assert.assertEquals(0f, settings.getMaxConvexRadius(), 0f);
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

        ShapeSettingsRef ref = settings.toRef();
        Assert.assertEquals(1, settings.getRefCount());
        Assert.assertEquals(settings, ref.getPtr());

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

        Assert.assertEquals(0f, settings.getConvexRadius(), 0f);
        Assert.assertEquals(0f, settings.getHalfHeight(), 0f);
        Assert.assertEquals(0f, settings.getRadius(), 0f);
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

        ShapeSettingsRef ref = settings.toRef();
        Assert.assertEquals(1, settings.getRefCount());
        Assert.assertEquals(settings, ref.getPtr());

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
        Assert.assertEquals(8, settings.getBitsPerSample());
        Assert.assertEquals(2, settings.getBlockSize());
        Assert.assertEquals(-1e15f, settings.getMaxHeightValue(), 0f);
        Assert.assertEquals(1e15f, settings.getMinHeightValue(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, settings.getOffset(), 0f);
        Assert.assertEquals(0, settings.getSampleCount());
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
        settings.setBitsPerSample(7);
        settings.setBlockSize(3);
        settings.setMaxHeightValue(1f);
        settings.setMinHeightValue(-1f);
        settings.setOffset(new Vec3(2f, 3f, 4f));
        settings.setScale(new Vec3(5f, 6f, 7f));

        ShapeSettingsRef ref = settings.toRef();
        Assert.assertEquals(1, settings.getRefCount());
        Assert.assertEquals(settings, ref.getPtr());

        Assert.assertEquals(
                0.5f, settings.getActiveEdgeCosThresholdAngle(), 0f);
        Assert.assertEquals(7, settings.getBitsPerSample());
        Assert.assertEquals(3, settings.getBlockSize());
        Assert.assertEquals(1f, settings.getMaxHeightValue(), 0f);
        Assert.assertEquals(-1f, settings.getMinHeightValue(), 0f);
        TestUtils.assertEquals(2f, 3f, 4f, settings.getOffset(), 0f);
        TestUtils.assertEquals(5f, 6f, 7f, settings.getScale(), 0f);
    }

    /**
     * Test the getters and defaults of the specified
     * {@code EmptyShapeSettings}.
     *
     * @param settings the settings to test (not null, unaffected)
     */
    private static void testEmptySsDefaults(EmptyShapeSettings settings) {
        testSsDefaults(settings);
    }

    /**
     * Test the setters of the specified {@code EmptyShapeSettings}.
     *
     * @param settings the settings to test (not null, modified)
     */
    private static void testEmptySsSetters(EmptyShapeSettings settings) {
        ShapeSettingsRef ref = settings.toRef();
        Assert.assertEquals(1, settings.getRefCount());
        Assert.assertEquals(settings, ref.getPtr());
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

        ShapeSettingsRef ref = settings.toRef();
        Assert.assertEquals(1, settings.getRefCount());
        Assert.assertEquals(settings, ref.getPtr());

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
     * {@code OffsetCenterOfMassShapeSettings}.
     *
     * @param settings the settings to test (not null, unaffected)
     */
    private static void testOffsetCenterOfMassSsDefaults(
            OffsetCenterOfMassShapeSettings settings) {
        testSsDefaults(settings);
        TestUtils.assertEquals(0f, 0f, 0f, settings.getOffset(), 0f);
    }

    /**
     * Test the setters of the specified
     * {@code OffsetCenterOfMassShapeSettings}.
     *
     * @param settings the settings to test (not null, modified)
     */
    private static void testOffsetCenterOfMassSsSetters(
            OffsetCenterOfMassShapeSettings settings) {
        settings.setOffset(new Vec3(2f, 3f, 4f));

        ShapeSettingsRef ref = settings.toRef();
        Assert.assertEquals(1, settings.getRefCount());
        Assert.assertEquals(settings, ref.getPtr());

        TestUtils.assertEquals(2f, 3f, 4f, settings.getOffset(), 0f);
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
                0f, 0f, 0f, settings.getPlane().getNormal(), 0f);
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

        ShapeSettingsRef ref = settings.toRef();
        Assert.assertEquals(1, settings.getRefCount());
        Assert.assertEquals(settings, ref.getPtr());

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

        ShapeSettingsRef ref = settings.toRef();
        Assert.assertEquals(1, settings.getRefCount());
        Assert.assertEquals(settings, ref.getPtr());

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

        ShapeSettingsRef ref = settings.toRef();
        Assert.assertEquals(1, settings.getRefCount());
        Assert.assertEquals(settings, ref.getPtr());

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

    /**
     * Test the getters and defaults of the specified
     * {@code TaperedCapsuleShapeSettings}.
     *
     * @param settings the settings to test (not null, unaffected)
     */
    private static void testTaperedCapsuleSsDefaults(
            TaperedCapsuleShapeSettings settings) {
        testConvexSsDefaults(settings);

        Assert.assertEquals(1f, settings.getBottomRadius(), 0f);
        Assert.assertEquals(1f, settings.getHalfHeightOfTaperedCylinder(), 0f);
        Assert.assertEquals(2f, settings.getTopRadius(), 0f);
    }

    /**
     * Test the setters of the specified {@code TaperedCapsuleShapeSettings}.
     *
     * @param settings the settings to test (not null, modified)
     */
    private static void testTaperedCapsuleSsSetters(
            TaperedCapsuleShapeSettings settings) {
        settings.setBottomRadius(0.08f);
        settings.setHalfHeightOfTaperedCylinder(0.2f);
        settings.setMaterial(PhysicsMaterial.sDefault());
        settings.setTopRadius(0.3f);

        ShapeSettingsRef ref = settings.toRef();
        Assert.assertEquals(1, settings.getRefCount());
        Assert.assertEquals(settings, ref.getPtr());

        Assert.assertEquals(0.08f, settings.getBottomRadius(), 0f);
        Assert.assertEquals(
                0.2f, settings.getHalfHeightOfTaperedCylinder(), 0f);
        Assert.assertEquals(PhysicsMaterial.sDefault(), settings.getMaterial());
        Assert.assertEquals(0.3f, settings.getTopRadius(), 0f);
    }

    /**
     * Test the getters and defaults of the specified
     * {@code TaperedCylinderShapeSettings}.
     *
     * @param settings the settings to test (not null, unaffected)
     */
    private static void testTaperedCylinderSsDefaults(
            TaperedCylinderShapeSettings settings) {
        testConvexSsDefaults(settings);

        Assert.assertEquals(1f, settings.getBottomRadius(), 0f);
        Assert.assertEquals(0.05f, settings.getConvexRadius(), 0f);
        Assert.assertEquals(1f, settings.getHalfHeight(), 0f);
        Assert.assertEquals(0.05f, settings.getTopRadius(), 0f);
    }

    /**
     * Test the setters of the specified {@code TaperedCylinderShapeSettings}.
     *
     * @param settings the settings to test (not null, modified)
     */
    private static void testTaperedCylinderSsSetters(
            TaperedCylinderShapeSettings settings) {
        settings.setBottomRadius(0.08f);
        settings.setConvexRadius(0.1f);
        settings.setHalfHeight(0.2f);
        settings.setMaterial(PhysicsMaterial.sDefault());
        settings.setTopRadius(0.3f);

        ShapeSettingsRef ref = settings.toRef();
        Assert.assertEquals(1, settings.getRefCount());
        Assert.assertEquals(settings, ref.getPtr());

        Assert.assertEquals(0.08f, settings.getBottomRadius(), 0f);
        Assert.assertEquals(0.1f, settings.getConvexRadius(), 0f);
        Assert.assertEquals(0.2f, settings.getHalfHeight(), 0f);
        Assert.assertEquals(PhysicsMaterial.sDefault(), settings.getMaterial());
        Assert.assertEquals(0.3f, settings.getTopRadius(), 0f);
    }
}
