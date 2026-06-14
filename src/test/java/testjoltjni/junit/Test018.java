/*
Copyright (c) 2024-2026 Stephen Gold

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

import com.github.stephengold.joltjni.CollideSettingsBase;
import com.github.stephengold.joltjni.CollideShapeSettings;
import com.github.stephengold.joltjni.CollisionGroup;
import com.github.stephengold.joltjni.GroupFilterTable;
import com.github.stephengold.joltjni.PhysicsSettings;
import com.github.stephengold.joltjni.RayCastSettings;
import com.github.stephengold.joltjni.ShapeCastSettings;
import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.enumerate.EActiveEdgeMode;
import com.github.stephengold.joltjni.enumerate.EBackFaceMode;
import com.github.stephengold.joltjni.enumerate.ECollectFacesMode;
import com.github.stephengold.joltjni.readonly.ConstCollisionGroup;
import com.github.stephengold.joltjni.readonly.ConstGroupFilter;
import org.junit.Assert;
import org.junit.Test;
import testjoltjni.TestUtils;

/**
 * Automated JUnit4 tests for creation, destruction, accessors, and defaults of
 * collision-related classes.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Test018 {
    // *************************************************************************
    // new methods exposed

    /**
     * Test object creation, destruction, accessors, and defaults.
     */
    @Test
    public void test018() {
        TestUtils.loadNativeLibrary();
        TestUtils.initializeNativeLibrary();

        doCollideShapeSettings();
        doCollisionGroup();
        doRayCastSettings();
        doShapeCastSettings();

        TestUtils.cleanup();
    }
    // *************************************************************************
    // Java private methods

    /**
     * Test the {@code CollideShapeSettings} class.
     */
    private static void doCollideShapeSettings() {
        CollideShapeSettings settings = new CollideShapeSettings();

        testCollideShapeSettingsDefaults(settings);
        CollideShapeSettings copy = new CollideShapeSettings(settings);
        testCollideShapeSettingsSetters(settings);
        testCollideShapeSettingsDefaults(copy);
        settings.set(copy);
        testCollideShapeSettingsDefaults(settings);

        TestUtils.testClose(copy, settings);
        System.gc();
    }

    /**
     * Test the {@code CollisionGroup} class.
     */
    private static void doCollisionGroup() {
        CollisionGroup group = new CollisionGroup();

        testCollisionGroupDefaults(group);
        CollisionGroup copy = new CollisionGroup(group);
        testCollisionGroupSetters(group);
        testCollisionGroupDefaults(copy);
        group.set(copy);
        testCollisionGroupDefaults(group);

        TestUtils.testClose(copy, group);
        System.gc();
    }

    /**
     * Test the {@code RayCastSettings} class.
     */
    private static void doRayCastSettings() {
        RayCastSettings settings = new RayCastSettings();

        testRayCastSettingsDefaults(settings);
        RayCastSettings copy = new RayCastSettings(settings);
        testRayCastSettingsSetters(settings);
        testRayCastSettingsDefaults(copy);
        settings.set(copy);
        testRayCastSettingsDefaults(settings);

        TestUtils.testClose(copy, settings);
        System.gc();
    }

    /**
     * Test the {@code ShapeCastSettings} class.
     */
    private static void doShapeCastSettings() {
        ShapeCastSettings settings = new ShapeCastSettings();

        testShapeCastSettingsDefaults(settings);
        ShapeCastSettings copy = new ShapeCastSettings(settings);
        testShapeCastSettingsSetters(settings);
        testShapeCastSettingsDefaults(copy);
        settings.set(copy);
        testShapeCastSettingsDefaults(settings);

        TestUtils.testClose(copy, settings);
        System.gc();
    }

    /**
     * Test the getters and defaults of the specified
     * {@code CollideSettingsBase}.
     *
     * @param settings the settings to test (not {@code null}, unaffected)
     */
    private static void testCollideSettingsBaseDefaults(
            CollideSettingsBase settings) {
        Assert.assertEquals(EActiveEdgeMode.CollideOnlyWithActive,
                settings.getActiveEdgeMode());
        TestUtils.assertEquals(0f, 0f, 0f,
                settings.getActiveEdgeMovementDirection(), 0f);
        Assert.assertEquals(ECollectFacesMode.NoFaces,
                settings.getCollectFacesMode());
        Assert.assertEquals(PhysicsSettings.cDefaultCollisionTolerance,
                settings.getCollisionTolerance(), 0f);
        Assert.assertEquals(PhysicsSettings.cDefaultPenetrationTolerance,
                settings.getPenetrationTolerance(), 0f);
    }

    /**
     * Test the setters of the specified {@code CollideSettingsBase}.
     *
     * @param settings the settings to test (not {@code null})
     */
    private static void testCollideSettingsBaseSetters(
            CollideSettingsBase settings) {
        settings.setActiveEdgeMode(EActiveEdgeMode.CollideWithAll);
        settings.setActiveEdgeMovementDirection(new Vec3(1f, 2f, 3f));
        settings.setCollectFacesMode(ECollectFacesMode.CollectFaces);
        settings.setCollisionTolerance(0.4f);
        settings.setPenetrationTolerance(0.5f);

        Assert.assertEquals(EActiveEdgeMode.CollideWithAll,
                settings.getActiveEdgeMode());
        TestUtils.assertEquals(1f, 2f, 3f,
                settings.getActiveEdgeMovementDirection(), 0f);
        Assert.assertEquals(ECollectFacesMode.CollectFaces,
                settings.getCollectFacesMode());
        Assert.assertEquals(0.4f, settings.getCollisionTolerance(), 0f);
        Assert.assertEquals(0.5f, settings.getPenetrationTolerance(), 0f);
    }

    /**
     * Test the getters and defaults of the specified
     * {@code CollideShapeSettings}.
     *
     * @param settings the settings to test (not {@code null}, unaffected)
     */
    private static void testCollideShapeSettingsDefaults(
            CollideShapeSettings settings) {
        testCollideSettingsBaseDefaults(settings);

        Assert.assertEquals(EBackFaceMode.IgnoreBackFaces,
                settings.getBackFaceMode());
        Assert.assertEquals(0f, settings.getMaxSeparationDistance(), 0f);
    }

    /**
     * Test the setters of the specified {@code CollideShapeSettings}.
     *
     * @param settings the group to test (not {@code null})
     */
    private static void testCollideShapeSettingsSetters(
            CollideShapeSettings settings) {
        settings.setBackFaceMode(EBackFaceMode.CollideWithBackFaces);
        settings.setMaxSeparationDistance(0.2f);

        testCollideSettingsBaseSetters(settings);

        Assert.assertEquals(EBackFaceMode.CollideWithBackFaces,
                settings.getBackFaceMode());
        Assert.assertEquals(0.2f, settings.getMaxSeparationDistance(), 0f);
    }

    /**
     * Test the getters and defaults of the specified {@code CollisionGroup}.
     *
     * @param group the group to test (not {@code null}, unaffected)
     */
    private static void testCollisionGroupDefaults(ConstCollisionGroup group) {
        Assert.assertNull(group.getGroupFilter());
        Assert.assertEquals(CollisionGroup.cInvalidGroup, group.getGroupId());
        Assert.assertEquals(
                CollisionGroup.cInvalidGroup, group.getSubGroupId());
    }

    /**
     * Test the setters of the specified {@code CollisionGroup}.
     *
     * @param group the group to test (not {@code null})
     */
    private static void testCollisionGroupSetters(CollisionGroup group) {
        GroupFilterTable filter = new GroupFilterTable();
        group.setGroupFilter(filter);
        group.setGroupId(101);
        group.setSubGroupId(102);

        ConstGroupFilter actualFilter = group.getGroupFilter();
        Assert.assertEquals(filter, actualFilter);
        Assert.assertEquals(101, group.getGroupId());
        Assert.assertEquals(102, group.getSubGroupId());

        TestUtils.testClose(actualFilter, filter);
    }

    /**
     * Test the getters and defaults of the specified {@code RayCastSettings}.
     *
     * @param settings the settings to test (not {@code null}, unaffected)
     */
    private static void testRayCastSettingsDefaults(
            RayCastSettings settings) {
        Assert.assertEquals(EBackFaceMode.IgnoreBackFaces,
                settings.getBackFaceModeConvex());
        Assert.assertEquals(EBackFaceMode.IgnoreBackFaces,
                settings.getBackFaceModeTriangles());
        Assert.assertTrue(settings.getTreatConvexAsSolid());
    }

    /**
     * Test the setters of the specified {@code RayCastSettings}.
     *
     * @param settings the group to test (not {@code null})
     */
    private static void testRayCastSettingsSetters(
            RayCastSettings settings) {
        settings.setBackFaceModeConvex(EBackFaceMode.CollideWithBackFaces);
        settings.setBackFaceModeTriangles(EBackFaceMode.CollideWithBackFaces);
        settings.setTreatConvexAsSolid(false);

        Assert.assertEquals(EBackFaceMode.CollideWithBackFaces,
                settings.getBackFaceModeConvex());
        Assert.assertEquals(EBackFaceMode.CollideWithBackFaces,
                settings.getBackFaceModeTriangles());
        Assert.assertFalse(settings.getTreatConvexAsSolid());
    }

    /**
     * Test the getters and defaults of the specified {@code ShapeCastSettings}.
     *
     * @param settings the settings to test (not {@code null}, unaffected)
     */
    private static void testShapeCastSettingsDefaults(
            ShapeCastSettings settings) {
        testCollideSettingsBaseDefaults(settings);

        Assert.assertEquals(EBackFaceMode.IgnoreBackFaces,
                settings.getBackFaceModeConvex());
        Assert.assertEquals(EBackFaceMode.IgnoreBackFaces,
                settings.getBackFaceModeTriangles());
        Assert.assertEquals(0f, settings.getExtraConvexRadius(), 0f);
        Assert.assertFalse(settings.getReturnDeepestPoint());
        Assert.assertFalse(settings.getUseShrunkenShapeAndConvexRadius());
    }

    /**
     * Test the setters of the specified {@code ShapeCastSettings}.
     *
     * @param settings the group to test (not {@code null})
     */
    private static void testShapeCastSettingsSetters(
            ShapeCastSettings settings) {
        settings.setBackFaceModeConvex(EBackFaceMode.CollideWithBackFaces);
        settings.setBackFaceModeTriangles(EBackFaceMode.CollideWithBackFaces);
        settings.setExtraConvexRadius(1f);
        settings.setReturnDeepestPoint(true);
        settings.setUseShrunkenShapeAndConvexRadius(true);

        testCollideSettingsBaseSetters(settings);

        Assert.assertEquals(EBackFaceMode.CollideWithBackFaces,
                settings.getBackFaceModeConvex());
        Assert.assertEquals(EBackFaceMode.CollideWithBackFaces,
                settings.getBackFaceModeTriangles());
        Assert.assertEquals(1f, settings.getExtraConvexRadius(), 0f);
        Assert.assertTrue(settings.getReturnDeepestPoint());
        Assert.assertTrue(settings.getUseShrunkenShapeAndConvexRadius());
    }
}
