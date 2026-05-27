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

import com.github.stephengold.joltjni.ConeConstraintSettings;
import com.github.stephengold.joltjni.DistanceConstraintSettings;
import com.github.stephengold.joltjni.FixedConstraintSettings;
import com.github.stephengold.joltjni.GearConstraintSettings;
import com.github.stephengold.joltjni.HingeConstraintSettings;
import com.github.stephengold.joltjni.PathConstraintSettings;
import com.github.stephengold.joltjni.PointConstraintSettings;
import com.github.stephengold.joltjni.PulleyConstraintSettings;
import com.github.stephengold.joltjni.Quat;
import com.github.stephengold.joltjni.RVec3;
import com.github.stephengold.joltjni.RackAndPinionConstraintSettings;
import com.github.stephengold.joltjni.SixDofConstraintSettings;
import com.github.stephengold.joltjni.SliderConstraintSettings;
import com.github.stephengold.joltjni.SwingTwistConstraintSettings;
import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.enumerate.EAxis;
import com.github.stephengold.joltjni.enumerate.EConstraintSpace;
import com.github.stephengold.joltjni.enumerate.EPathRotationConstraintType;
import com.github.stephengold.joltjni.enumerate.ESwingType;
import com.github.stephengold.joltjni.readonly.ConstConstraintSettings;
import org.junit.Assert;
import org.junit.Test;
import testjoltjni.TestUtils;

/**
 * Automated JUnit4 tests for creation, destruction, copying, accessors, and
 * defaults of {@code TwoBodyConstraintsSettings} subclasses.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Test005 {
    // *************************************************************************
    // new methods exposed

    /**
     * Test object creation, destruction, copying, accessors, and defaults.
     */
    @Test
    public void test005() {
        TestUtils.loadNativeLibrary();
        TestUtils.initializeNativeLibrary();

        doConeConstraintSettings();
        doDistanceConstraintSettings();
        doFixedConstraintSettings();
        doGearConstraintSettings();
        doHingeConstraintSettings();
        doPathConstraintSettings();
        doPointConstraintSettings();
        doPulleyConstraintSettings();
        doRapConstraintSettings();
        doSixDofConstraintSettings();
        doSliderConstraintSettings();
        doSwingTwistConstraintSettings();

        TestUtils.cleanup();
    }
    // *************************************************************************
    // Java private methods

    /**
     * Test the {@code ConeConstraintSettings} class.
     */
    private static void doConeConstraintSettings() {
        ConeConstraintSettings settings = new ConeConstraintSettings();
        testConeCsDefaults(settings);

        ConeConstraintSettings copy = new ConeConstraintSettings(settings);
        testConeCsSetters(settings);
        testConeCsDefaults(copy);
        settings.set(copy);
        testConeCsDefaults(settings);

        TestUtils.testClose(copy, settings);
        System.gc();
    }

    /**
     * Test the {@code DistanceConstraintSettings} class.
     */
    private static void doDistanceConstraintSettings() {
        DistanceConstraintSettings settings = new DistanceConstraintSettings();
        testDistanceCsDefaults(settings);

        DistanceConstraintSettings copy
                = new DistanceConstraintSettings(settings);
        testDistanceCsSetters(settings);
        testDistanceCsDefaults(copy);
        settings.set(copy);
        testDistanceCsDefaults(settings);

        TestUtils.testClose(copy, settings);
        System.gc();
    }

    /**
     * Test the {@code FixedConstraintSettings} class.
     */
    private static void doFixedConstraintSettings() {
        FixedConstraintSettings settings = new FixedConstraintSettings();
        testFixedCsDefaults(settings);

        FixedConstraintSettings copy = new FixedConstraintSettings(settings);
        testFixedCsSetters(settings);
        testFixedCsDefaults(copy);
        settings.set(copy);
        testFixedCsDefaults(settings);

        TestUtils.testClose(copy, settings);
        System.gc();
    }

    /**
     * Test the {@code GearConstraintSettings} class.
     */
    private static void doGearConstraintSettings() {
        GearConstraintSettings settings = new GearConstraintSettings();
        testGearCsDefaults(settings);

        GearConstraintSettings copy = new GearConstraintSettings(settings);
        testGearCsSetters(settings);
        testGearCsDefaults(copy);
        settings.set(copy);
        testGearCsDefaults(settings);

        TestUtils.testClose(copy, settings);
        System.gc();
    }

    /**
     * Test the {@code HingeConstraintSettings} class.
     */
    private static void doHingeConstraintSettings() {
        HingeConstraintSettings settings = new HingeConstraintSettings();
        testHingeCsDefaults(settings);

        HingeConstraintSettings copy = new HingeConstraintSettings(settings);
        testHingeCsSetters(settings);
        testHingeCsDefaults(copy);
        settings.set(copy);
        testHingeCsDefaults(settings);

        TestUtils.testClose(copy, settings);
        System.gc();
    }

    /**
     * Test the {@code PathConstraintSettings} class.
     */
    private static void doPathConstraintSettings() {
        PathConstraintSettings settings = new PathConstraintSettings();
        testPathCsDefaults(settings);

        PathConstraintSettings copy = new PathConstraintSettings(settings);
        testPathCsSetters(settings);
        testPathCsDefaults(copy);
        settings.set(copy);
        testPathCsDefaults(settings);

        TestUtils.testClose(copy, settings);
        System.gc();
    }

    /**
     * Test the {@code PointConstraintSettings} class.
     */
    private static void doPointConstraintSettings() {
        PointConstraintSettings settings = new PointConstraintSettings();
        testPointCsDefaults(settings);

        PointConstraintSettings copy = new PointConstraintSettings(settings);
        testPointCsSetters(settings);
        testPointCsDefaults(copy);
        settings.set(copy);
        testPointCsDefaults(settings);

        TestUtils.testClose(copy, settings);
        System.gc();
    }

    /**
     * Test the {@code PulleyConstraintSettings} class.
     */
    private static void doPulleyConstraintSettings() {
        PulleyConstraintSettings settings = new PulleyConstraintSettings();
        testPulleyCsDefaults(settings);

        PulleyConstraintSettings copy = new PulleyConstraintSettings(settings);
        testPulleyCsSetters(settings);
        testPulleyCsDefaults(copy);
        settings.set(copy);
        testPulleyCsDefaults(settings);

        TestUtils.testClose(copy, settings);
        System.gc();
    }

    /**
     * Test the {@code RackAndPinionConstraintSettings} class.
     */
    private static void doRapConstraintSettings() {
        RackAndPinionConstraintSettings settings
                = new RackAndPinionConstraintSettings();
        testRapCsDefaults(settings);

        RackAndPinionConstraintSettings copy
                = new RackAndPinionConstraintSettings(settings);
        testRapCsSetters(settings);
        testRapCsDefaults(copy);
        settings.set(copy);
        testRapCsDefaults(settings);

        TestUtils.testClose(copy, settings);
        System.gc();
    }

    /**
     * Test the {@code SixDofConstraintSettings} class.
     */
    private static void doSixDofConstraintSettings() {
        SixDofConstraintSettings settings = new SixDofConstraintSettings();
        testSixDofCsDefaults(settings);

        SixDofConstraintSettings copy = new SixDofConstraintSettings(settings);
        testSixDofCsSetters(settings);
        testSixDofCsDefaults(copy);
        settings.set(copy);
        testSixDofCsDefaults(settings);

        TestUtils.testClose(copy, settings);
        System.gc();
    }

    /**
     * Test the {@code SliderConstraintSettings} class.
     */
    private static void doSliderConstraintSettings() {
        SliderConstraintSettings settings = new SliderConstraintSettings();
        testSliderCsDefaults(settings);

        SliderConstraintSettings copy = new SliderConstraintSettings(settings);
        testSliderCsSetters(settings);
        testSliderCsDefaults(copy);
        settings.set(copy);
        testSliderCsDefaults(settings);

        TestUtils.testClose(copy, settings);
        System.gc();
    }

    /**
     * Test the {@code SwingTwistConstraintSettings} class.
     */
    private static void doSwingTwistConstraintSettings() {
        SwingTwistConstraintSettings settings
                = new SwingTwistConstraintSettings();
        testSwingTwistCsDefaults(settings);

        SwingTwistConstraintSettings copy
                = new SwingTwistConstraintSettings(settings);
        testSwingTwistCsSetters(settings);
        testSwingTwistCsDefaults(copy);
        settings.set(copy);
        testSwingTwistCsDefaults(settings);

        TestUtils.testClose(copy, settings);
        System.gc();
    }

    /**
     * Test the getters and defaults of the specified
     * {@code ConeConstraintSettings}.
     *
     * @param settings the settings to test (not {@code null}, unaffected)
     */
    private static void testConeCsDefaults(ConeConstraintSettings settings) {
        testCsDefaults(settings);

        Assert.assertEquals(0f, settings.getHalfConeAngle(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, settings.getPoint1(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, settings.getPoint2(), 0f);
        Assert.assertEquals(EConstraintSpace.WorldSpace, settings.getSpace());
        TestUtils.assertEquals(1f, 0f, 0f, settings.getTwistAxis1(), 0f);
        TestUtils.assertEquals(1f, 0f, 0f, settings.getTwistAxis2(), 0f);
    }

    /**
     * Test the setters of the specified {@code ConeConstraintSettings}.
     *
     * @param settings the settings to test (not {@code null}, modified)
     */
    private static void testConeCsSetters(ConeConstraintSettings settings) {
        settings.setHalfConeAngle(0.01f);
        settings.setPoint1(new RVec3(0.02f, 0.03f, 0.04f));
        settings.setPoint2(new RVec3(0.05f, 0.06f, 0.07f));
        settings.setSpace(EConstraintSpace.LocalToBodyCom);
        settings.setTwistAxis1(new Vec3(0.12f, 0.13f, 0.14f));
        settings.setTwistAxis2(new Vec3(0.15f, 0.16f, 0.17f));

        Assert.assertEquals(0.01f, settings.getHalfConeAngle(), 0f);
        TestUtils.assertEquals(0.02f, 0.03f, 0.04f, settings.getPoint1(), 0f);
        TestUtils.assertEquals(0.05f, 0.06f, 0.07f, settings.getPoint2(), 0f);
        Assert.assertEquals(
                EConstraintSpace.LocalToBodyCom, settings.getSpace());
        TestUtils.assertEquals(
                0.12f, 0.13f, 0.14f, settings.getTwistAxis1(), 0f);
        TestUtils.assertEquals(
                0.15f, 0.16f, 0.17f, settings.getTwistAxis2(), 0f);
    }

    /**
     * Test the getters and defaults of the specified
     * {@code ConstraintSettings}.
     *
     * @param settings the settings to test (not {@code null}, unaffected)
     */
    private static void testCsDefaults(ConstConstraintSettings settings) {
        Assert.assertTrue(settings.hasAssignedNativeObject());
        Assert.assertTrue(settings.ownsNativeObject());

        Assert.assertEquals(0, settings.getConstraintPriority());
        Assert.assertTrue(settings.getEnabled());
        Assert.assertEquals(0, settings.getNumPositionStepsOverride());
        Assert.assertEquals(0, settings.getNumVelocityStepsOverride());
    }

    /**
     * Test the getters and defaults of the specified
     * {@code DistanceConstraintSettings}.
     *
     * @param settings the settings to test (not {@code null}, unaffected)
     */
    private static void testDistanceCsDefaults(
            DistanceConstraintSettings settings) {
        testCsDefaults(settings);

        Assert.assertNotNull(settings.getLimitsSpringSettings());
        Assert.assertEquals(-1f, settings.getMaxDistance(), 0f);
        Assert.assertEquals(-1f, settings.getMinDistance(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, settings.getPoint1(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, settings.getPoint2(), 0f);
        Assert.assertEquals(EConstraintSpace.WorldSpace, settings.getSpace());
    }

    /**
     * Test the setters of the specified {@code DistanceConstraintSettings}.
     *
     * @param settings the settings to test (not {@code null}, modified)
     */
    private static void testDistanceCsSetters(
            DistanceConstraintSettings settings) {
        settings.setMaxDistance(0.1f);
        settings.setMinDistance(0.01f);
        settings.setPoint1(0.02f, 0.03f, 0.04f);
        settings.setPoint2(0.05f, 0.06f, 0.07f);
        settings.setSpace(EConstraintSpace.LocalToBodyCom);

        Assert.assertEquals(0.1f, settings.getMaxDistance(), 0f);
        Assert.assertEquals(0.01f, settings.getMinDistance(), 0f);
        TestUtils.assertEquals(0.02f, 0.03f, 0.04f, settings.getPoint1(), 0f);
        TestUtils.assertEquals(0.05f, 0.06f, 0.07f, settings.getPoint2(), 0f);
        Assert.assertEquals(
                EConstraintSpace.LocalToBodyCom, settings.getSpace());
    }

    /**
     * Test the getters and defaults of the specified
     * {@code FixedConstraintSettings}.
     *
     * @param settings the settings to test (not {@code null}, unaffected)
     */
    private static void testFixedCsDefaults(
            FixedConstraintSettings settings) {
        testCsDefaults(settings);

        Assert.assertFalse(settings.getAutoDetectPoint());
        TestUtils.assertEquals(1f, 0f, 0f, settings.getAxisX1(), 0f);
        TestUtils.assertEquals(1f, 0f, 0f, settings.getAxisX2(), 0f);
        TestUtils.assertEquals(0f, 1f, 0f, settings.getAxisY1(), 0f);
        TestUtils.assertEquals(0f, 1f, 0f, settings.getAxisY2(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, settings.getPoint1(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, settings.getPoint2(), 0f);
        Assert.assertEquals(EConstraintSpace.WorldSpace, settings.getSpace());
    }

    /**
     * Test the setters of the specified {@code FixedConstraintSettings}.
     *
     * @param settings the settings to test (not {@code null}, modified)
     */
    private static void testFixedCsSetters(
            FixedConstraintSettings settings) {
        settings.setAutoDetectPoint(true);
        settings.setAxisX1(new Vec3(0.02f, 0.03f, 0.04f));
        settings.setAxisX2(new Vec3(0.05f, 0.06f, 0.07f));
        settings.setAxisY1(new Vec3(0.12f, 0.13f, 0.14f));
        settings.setAxisY2(new Vec3(0.15f, 0.16f, 0.17f));
        settings.setPoint1(new RVec3(0.22f, 0.23f, 0.24f));
        settings.setPoint2(new RVec3(0.25f, 0.26f, 0.27f));
        settings.setSpace(EConstraintSpace.LocalToBodyCom);

        Assert.assertTrue(settings.getAutoDetectPoint());
        TestUtils.assertEquals(0.02f, 0.03f, 0.04f, settings.getAxisX1(), 0f);
        TestUtils.assertEquals(0.05f, 0.06f, 0.07f, settings.getAxisX2(), 0f);
        TestUtils.assertEquals(0.12f, 0.13f, 0.14f, settings.getAxisY1(), 0f);
        TestUtils.assertEquals(0.15f, 0.16f, 0.17f, settings.getAxisY2(), 0f);
        TestUtils.assertEquals(0.22f, 0.23f, 0.24f, settings.getPoint1(), 0f);
        TestUtils.assertEquals(0.25f, 0.26f, 0.27f, settings.getPoint2(), 0f);
        Assert.assertEquals(
                EConstraintSpace.LocalToBodyCom, settings.getSpace());
    }

    /**
     * Test the getters and defaults of the specified
     * {@code GearConstraintSettings}.
     *
     * @param settings the settings to test (not {@code null}, unaffected)
     */
    private static void testGearCsDefaults(
            GearConstraintSettings settings) {
        testCsDefaults(settings);

        TestUtils.assertEquals(1f, 0f, 0f, settings.getHingeAxis1(), 0f);
        TestUtils.assertEquals(1f, 0f, 0f, settings.getHingeAxis2(), 0f);
        Assert.assertEquals(1f, settings.getRatio(), 0f);
        Assert.assertEquals(EConstraintSpace.WorldSpace, settings.getSpace());
    }

    /**
     * Test the setters of the specified {@code GearConstraintSettings}.
     *
     * @param settings the settings to test (not {@code null}, modified)
     */
    private static void testGearCsSetters(GearConstraintSettings settings) {
        settings.setHingeAxis1(new Vec3(0.24f, 0.25f, 0.26f));
        settings.setHingeAxis2(new Vec3(0.27f, 0.28f, 0.29f));
        settings.setRatio(0.5f);
        settings.setSpace(EConstraintSpace.LocalToBodyCom);

        TestUtils.assertEquals(
                0.24f, 0.25f, 0.26f, settings.getHingeAxis1(), 0f);
        TestUtils.assertEquals(
                0.27f, 0.28f, 0.29f, settings.getHingeAxis2(), 0f);
        Assert.assertEquals(0.5f, settings.getRatio(), 0f);
        Assert.assertEquals(
                EConstraintSpace.LocalToBodyCom, settings.getSpace());
    }

    /**
     * Test the getters and defaults of the specified
     * {@code HingeConstraintSettings}.
     *
     * @param settings the settings to test (not {@code null}, unaffected)
     */
    private static void testHingeCsDefaults(
            HingeConstraintSettings settings) {
        testCsDefaults(settings);

        Assert.assertEquals((float) Math.PI, settings.getLimitsMax(), 0f);
        Assert.assertEquals(-(float) Math.PI, settings.getLimitsMin(), 0f);
        Assert.assertNotNull(settings.getLimitsSpringSettings());
        Assert.assertEquals(0f, settings.getMaxFrictionTorque(), 0f);
        TestUtils.assertEquals(0f, 1f, 0f, settings.getHingeAxis1(), 0f);
        TestUtils.assertEquals(0f, 1f, 0f, settings.getHingeAxis2(), 0f);
        TestUtils.assertEquals(1f, 0f, 0f, settings.getNormalAxis1(), 0f);
        TestUtils.assertEquals(1f, 0f, 0f, settings.getNormalAxis2(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, settings.getPoint1(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, settings.getPoint2(), 0f);
        Assert.assertEquals(EConstraintSpace.WorldSpace, settings.getSpace());
    }

    /**
     * Test the setters of the specified {@code HingeConstraintSettings}.
     *
     * @param settings the settings to test (not {@code null}, modified)
     */
    private static void testHingeCsSetters(HingeConstraintSettings settings) {
        settings.setLimitsMax(0.01f);
        settings.setLimitsMin(-0.02f);
        settings.setMaxFrictionTorque(0.03f);
        settings.setHingeAxis1(new Vec3(0.24f, 0.25f, 0.26f));
        settings.setHingeAxis2(new Vec3(0.27f, 0.28f, 0.29f));
        settings.setNormalAxis1(new Vec3(0.04f, 0.05f, 0.06f));
        settings.setNormalAxis2(new Vec3(0.07f, 0.08f, 0.09f));
        settings.setPoint1(new RVec3(0.14f, 0.15f, 0.16f));
        settings.setPoint2(new RVec3(0.17f, 0.18f, 0.19f));
        settings.setSpace(EConstraintSpace.LocalToBodyCom);

        Assert.assertEquals(0.01f, settings.getLimitsMax(), 0f);
        Assert.assertEquals(-0.02f, settings.getLimitsMin(), 0f);
        Assert.assertEquals(0.03f, settings.getMaxFrictionTorque(), 0f);
        TestUtils.assertEquals(
                0.24f, 0.25f, 0.26f, settings.getHingeAxis1(), 0f);
        TestUtils.assertEquals(
                0.27f, 0.28f, 0.29f, settings.getHingeAxis2(), 0f);
        TestUtils.assertEquals(
                0.04f, 0.05f, 0.06f, settings.getNormalAxis1(), 0f);
        TestUtils.assertEquals(
                0.07f, 0.08f, 0.09f, settings.getNormalAxis2(), 0f);
        TestUtils.assertEquals(0.14f, 0.15f, 0.16f, settings.getPoint1(), 0f);
        TestUtils.assertEquals(0.17f, 0.18f, 0.19f, settings.getPoint2(), 0f);
        Assert.assertEquals(
                EConstraintSpace.LocalToBodyCom, settings.getSpace());
    }

    /**
     * Test the getters and defaults of the specified
     * {@code PathConstraintSettings}.
     *
     * @param settings the settings to test (not {@code null}, unaffected)
     */
    private static void testPathCsDefaults(PathConstraintSettings settings) {
        testCsDefaults(settings);

        Assert.assertEquals(0f, settings.getMaxFrictionForce(), 0f);
        Assert.assertNull(settings.getPath());
        Assert.assertEquals(0f, settings.getPathFraction(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, settings.getPathPosition(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, 1f, settings.getPathRotation(), 0f);
        Assert.assertEquals(EPathRotationConstraintType.Free,
                settings.getRotationConstraintType());
    }

    /**
     * Test the setters of the specified {@code PathConstraintSettings}.
     *
     * @param settings the settings to test (not {@code null}, modified)
     */
    private static void testPathCsSetters(PathConstraintSettings settings) {
        settings.setMaxFrictionForce(0.22f);
        settings.setPathFraction(0.27f);
        settings.setPathPosition(new Vec3(0.31f, 0.32f, 0.33f));
        settings.setPathRotation(new Quat(0.5f, -0.5f, -0.5f, 0.5f));
        settings.setRotationConstraintType(
                EPathRotationConstraintType.ConstrainAroundTangent);

        Assert.assertEquals(0.22f, settings.getMaxFrictionForce(), 0f);
        Assert.assertEquals(0.27f, settings.getPathFraction(), 0f);
        TestUtils.assertEquals(
                0.31f, 0.32f, 0.33f, settings.getPathPosition(), 0f);
        TestUtils.assertEquals(
                0.5f, -0.5f, -0.5f, 0.5f, settings.getPathRotation(), 0f);
        Assert.assertEquals(EPathRotationConstraintType.ConstrainAroundTangent,
                settings.getRotationConstraintType());
    }

    /**
     * Test the getters and defaults of the specified
     * {@code PointConstraintSettings}.
     *
     * @param settings the settings to test (not {@code null}, unaffected)
     */
    private static void testPointCsDefaults(
            PointConstraintSettings settings) {
        testCsDefaults(settings);

        TestUtils.assertEquals(0f, 0f, 0f, settings.getPoint1(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, settings.getPoint2(), 0f);
        Assert.assertEquals(EConstraintSpace.WorldSpace, settings.getSpace());
    }

    /**
     * Test the setters of the specified {@code PointConstraintSettings}.
     *
     * @param settings the settings to test (not {@code null}, modified)
     */
    private static void testPointCsSetters(
            PointConstraintSettings settings) {
        settings.setPoint1(0.22, 0.23, 0.24);
        settings.setPoint2(0.25, 0.26, 0.27);
        settings.setSpace(EConstraintSpace.LocalToBodyCom);

        TestUtils.assertEquals(0.22f, 0.23f, 0.24f, settings.getPoint1(), 0f);
        TestUtils.assertEquals(0.25f, 0.26f, 0.27f, settings.getPoint2(), 0f);
        Assert.assertEquals(
                EConstraintSpace.LocalToBodyCom, settings.getSpace());
    }

    /**
     * Test the getters and defaults of the specified
     * {@code PulleyConstraintSettings}.
     *
     * @param settings the settings to test (not {@code null}, unaffected)
     */
    private static void testPulleyCsDefaults(
            PulleyConstraintSettings settings) {
        testCsDefaults(settings);

        TestUtils.assertEquals(0f, 0f, 0f, settings.getBodyPoint1(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, settings.getBodyPoint2(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, settings.getFixedPoint1(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, settings.getFixedPoint2(), 0f);
        Assert.assertEquals(-1f, settings.getMaxLength(), 0f);
        Assert.assertEquals(0f, settings.getMinLength(), 0f);
        Assert.assertEquals(1f, settings.getRatio(), 0f);
        Assert.assertEquals(EConstraintSpace.WorldSpace, settings.getSpace());
    }

    /**
     * Test the setters of the specified {@code PulleyConstraintSettings}.
     *
     * @param settings the settings to test (not {@code null}, modified)
     */
    private static void testPulleyCsSetters(
            PulleyConstraintSettings settings) {
        settings.setBodyPoint1(new RVec3(0.12, 0.13, 0.14));
        settings.setBodyPoint2(new RVec3(0.22, 0.23, 0.24));
        settings.setFixedPoint1(new RVec3(0.32, 0.33, 0.34));
        settings.setFixedPoint2(new RVec3(0.42, 0.43, 0.44));
        settings.setMaxLength(5f);
        settings.setMinLength(0.6f);
        settings.setRatio(3f);
        settings.setSpace(EConstraintSpace.LocalToBodyCom);

        TestUtils.assertEquals(
                0.12f, 0.13f, 0.14f, settings.getBodyPoint1(), 0f);
        TestUtils.assertEquals(
                0.22f, 0.23f, 0.24f, settings.getBodyPoint2(), 0f);
        TestUtils.assertEquals(
                0.32f, 0.33f, 0.34f, settings.getFixedPoint1(), 0f);
        TestUtils.assertEquals(
                0.42f, 0.43f, 0.44f, settings.getFixedPoint2(), 0f);
        Assert.assertEquals(5f, settings.getMaxLength(), 0f);
        Assert.assertEquals(0.6f, settings.getMinLength(), 0f);
        Assert.assertEquals(3f, settings.getRatio(), 0f);
        Assert.assertEquals(
                EConstraintSpace.LocalToBodyCom, settings.getSpace());
    }

    /**
     * Test the getters and defaults of the specified
     * {@code RackAndPinionConstraintSettings}.
     *
     * @param settings the settings to test (not {@code null}, unaffected)
     */
    private static void testRapCsDefaults(
            RackAndPinionConstraintSettings settings) {
        testCsDefaults(settings);

        TestUtils.assertEquals(1f, 0f, 0f, settings.getHingeAxis(), 0f);
        Assert.assertEquals(1f, settings.getRatio(), 0f);
        TestUtils.assertEquals(1f, 0f, 0f, settings.getSliderAxis(), 0f);
        Assert.assertEquals(EConstraintSpace.WorldSpace, settings.getSpace());
    }

    /**
     * Test the setters of the specified
     * {@code RackAndPinionConstraintSettings}.
     *
     * @param settings the settings to test (not {@code null}, modified)
     */
    private static void testRapCsSetters(
            RackAndPinionConstraintSettings settings) {
        settings.setHingeAxis(new Vec3(0.22f, 0.23f, 0.24f));
        settings.setRatio(0.5f);
        settings.setSliderAxis(new Vec3(0.25f, 0.26f, 0.27f));
        settings.setSpace(EConstraintSpace.LocalToBodyCom);

        TestUtils.assertEquals(
                0.22f, 0.23f, 0.24f, settings.getHingeAxis(), 0f);
        Assert.assertEquals(0.5f, settings.getRatio(), 0f);
        TestUtils.assertEquals(
                0.25f, 0.26f, 0.27f, settings.getSliderAxis(), 0f);
        Assert.assertEquals(
                EConstraintSpace.LocalToBodyCom, settings.getSpace());
    }

    /**
     * Test the getters and defaults of the specified
     * {@code SixDofConstraintSettings}.
     *
     * @param settings the settings to test (not {@code null}, unaffected)
     */
    private static void testSixDofCsDefaults(
            SixDofConstraintSettings settings) {
        testCsDefaults(settings);

        TestUtils.assertEquals(1f, 0f, 0f, settings.getAxisX1(), 0f);
        TestUtils.assertEquals(1f, 0f, 0f, settings.getAxisX2(), 0f);
        TestUtils.assertEquals(0f, 1f, 0f, settings.getAxisY1(), 0f);
        TestUtils.assertEquals(0f, 1f, 0f, settings.getAxisY2(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, settings.getPosition1(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, settings.getPosition2(), 0f);
        Assert.assertEquals(EConstraintSpace.WorldSpace, settings.getSpace());
        Assert.assertEquals(ESwingType.Cone, settings.getSwingType());

        for (int axisIndex = 0; axisIndex < EAxis.Num.ordinal(); ++axisIndex) {
            EAxis axis = EAxis.values()[axisIndex];

            Assert.assertEquals(
                    Float.MAX_VALUE, settings.getLimitMax(axis), 0f);
            Assert.assertEquals(
                    -Float.MAX_VALUE, settings.getLimitMin(axis), 0f);
            if (axisIndex < 3) {
                Assert.assertNotNull(settings.getLimitsSpringSettings(axis));
            }
            Assert.assertEquals(0f, settings.getMaxFriction(axis), 0f);
            Assert.assertNotNull(settings.getMotorSettings(axis));
            Assert.assertFalse(settings.isFixedAxis(axis));
            Assert.assertTrue(settings.isFreeAxis(axis));
        }
    }

    /**
     * Test the setters of the specified {@code SixDofConstraintSettings}.
     *
     * @param settings the settings to test (not {@code null}, modified)
     */
    private static void testSixDofCsSetters(SixDofConstraintSettings settings) {
        settings.setAxisX1(new Vec3(0.01f, 0.02f, 0.03f));
        settings.setAxisX2(new Vec3(0.04f, 0.05f, 0.06f));
        settings.setAxisY1(new Vec3(0.07f, 0.08f, 0.09f));
        settings.setAxisY2(new Vec3(0.11f, 0.12f, 0.13f));
        settings.setPosition1(new RVec3(0.14f, 0.15f, 0.16f));
        settings.setPosition2(new RVec3(0.17f, 0.18f, 0.19f));
        settings.setSpace(EConstraintSpace.LocalToBodyCom);
        settings.setSwingType(ESwingType.Pyramid);

        for (int i = 0; i < EAxis.Num.ordinal(); ++i) {
            EAxis axis = EAxis.values()[i];

            settings.setLimitMax(axis, i + 1f);
            settings.setLimitMin(axis, -i - 2f);
            settings.setMaxFriction(axis, i + 1.5f);
        }

        TestUtils.assertEquals(0.01f, 0.02f, 0.03f, settings.getAxisX1(), 0f);
        TestUtils.assertEquals(0.04f, 0.05f, 0.06f, settings.getAxisX2(), 0f);
        TestUtils.assertEquals(0.07f, 0.08f, 0.09f, settings.getAxisY1(), 0f);
        TestUtils.assertEquals(0.11f, 0.12f, 0.13f, settings.getAxisY2(), 0f);
        TestUtils.assertEquals(
                0.14f, 0.15f, 0.16f, settings.getPosition1(), 0f);
        TestUtils.assertEquals(
                0.17f, 0.18f, 0.19f, settings.getPosition2(), 0f);
        Assert.assertEquals(
                EConstraintSpace.LocalToBodyCom, settings.getSpace());
        Assert.assertEquals(ESwingType.Pyramid, settings.getSwingType());

        for (int i = 0; i < EAxis.Num.ordinal(); ++i) {
            EAxis axis = EAxis.values()[i];

            settings.setLimitMax(axis, i + 1f);
            settings.setLimitMin(axis, -i - 2f);
            settings.setMaxFriction(axis, i + 1.5f);
        }
    }

    /**
     * Test the getters and defaults of the specified
     * {@code SliderConstraintSettings}.
     *
     * @param settings the settings to test (not {@code null}, unaffected)
     */
    private static void testSliderCsDefaults(
            SliderConstraintSettings settings) {
        testCsDefaults(settings);

        Assert.assertFalse(settings.getAutoDetectPoint());
        Assert.assertEquals(Float.MAX_VALUE, settings.getLimitsMax(), 0f);
        Assert.assertEquals(-Float.MAX_VALUE, settings.getLimitsMin(), 0f);
        Assert.assertNotNull(settings.getLimitsSpringSettings());
        Assert.assertEquals(0f, settings.getMaxFrictionForce(), 0f);
        TestUtils.assertEquals(0f, 1f, 0f, settings.getNormalAxis1(), 0f);
        TestUtils.assertEquals(0f, 1f, 0f, settings.getNormalAxis2(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, settings.getPoint1(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, settings.getPoint2(), 0f);
        TestUtils.assertEquals(1f, 0f, 0f, settings.getSliderAxis1(), 0f);
        TestUtils.assertEquals(1f, 0f, 0f, settings.getSliderAxis2(), 0f);
        Assert.assertEquals(EConstraintSpace.WorldSpace, settings.getSpace());
    }

    /**
     * Test the setters of the specified {@code SliderConstraintSettings}.
     *
     * @param settings the settings to test (not {@code null}, modified)
     */
    private static void testSliderCsSetters(SliderConstraintSettings settings) {
        settings.setAutoDetectPoint(true);
        settings.setLimitsMax(0.01f);
        settings.setLimitsMin(-0.02f);
        settings.setMaxFrictionForce(0.03f);

        settings.setNormalAxis1(new Vec3(0.04f, 0.05f, 0.06f));
        settings.setNormalAxis2(new Vec3(0.07f, 0.08f, 0.09f));
        settings.setPoint1(new RVec3(0.14f, 0.15f, 0.16f));
        settings.setPoint2(new RVec3(0.17f, 0.18f, 0.19f));
        settings.setSliderAxis1(new Vec3(0.24f, 0.25f, 0.26f));
        settings.setSliderAxis2(new Vec3(0.27f, 0.28f, 0.29f));
        settings.setSpace(EConstraintSpace.LocalToBodyCom);

        Assert.assertTrue(settings.getAutoDetectPoint());
        Assert.assertEquals(0.01f, settings.getLimitsMax(), 0f);
        Assert.assertEquals(-0.02f, settings.getLimitsMin(), 0f);
        Assert.assertEquals(0.03f, settings.getMaxFrictionForce(), 0f);
        TestUtils.assertEquals(
                0.04f, 0.05f, 0.06f, settings.getNormalAxis1(), 0f);
        TestUtils.assertEquals(
                0.07f, 0.08f, 0.09f, settings.getNormalAxis2(), 0f);
        TestUtils.assertEquals(0.14f, 0.15f, 0.16f, settings.getPoint1(), 0f);
        TestUtils.assertEquals(0.17f, 0.18f, 0.19f, settings.getPoint2(), 0f);
        TestUtils.assertEquals(
                0.24f, 0.25f, 0.26f, settings.getSliderAxis1(), 0f);
        TestUtils.assertEquals(
                0.27f, 0.28f, 0.29f, settings.getSliderAxis2(), 0f);
        Assert.assertEquals(
                EConstraintSpace.LocalToBodyCom, settings.getSpace());
    }

    /**
     * Test the getters and defaults of the specified
     * {@code SwingTwistConstraintSettings}.
     *
     * @param settings the settings to test (not {@code null}, unaffected)
     */
    private static void testSwingTwistCsDefaults(
            SwingTwistConstraintSettings settings) {
        testCsDefaults(settings);

        Assert.assertEquals(0f, settings.getMaxFrictionTorque(), 0f);
        Assert.assertEquals(0f, settings.getNormalHalfConeAngle(), 0f);
        TestUtils.assertEquals(0f, 1f, 0f, settings.getPlaneAxis1(), 0f);
        TestUtils.assertEquals(0f, 1f, 0f, settings.getPlaneAxis2(), 0f);
        Assert.assertEquals(0f, settings.getPlaneHalfConeAngle(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, settings.getPosition1(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, settings.getPosition2(), 0f);
        Assert.assertEquals(EConstraintSpace.WorldSpace, settings.getSpace());
        Assert.assertEquals(ESwingType.Cone, settings.getSwingType());
        TestUtils.assertEquals(1f, 0f, 0f, settings.getTwistAxis1(), 0f);
        TestUtils.assertEquals(1f, 0f, 0f, settings.getTwistAxis2(), 0f);
        Assert.assertEquals(0f, settings.getTwistMaxAngle(), 0f);
        Assert.assertEquals(0f, settings.getTwistMinAngle(), 0f);
    }

    /**
     * Test the setters of the specified {@code SwingTwistConstraintSettings}.
     *
     * @param settings the settings to test (not {@code null}, modified)
     */
    private static void testSwingTwistCsSetters(
            SwingTwistConstraintSettings settings) {
        settings.setMaxFrictionTorque(0.01f);
        settings.setNormalHalfConeAngle(0.02f);
        settings.setPlaneAxis1(new Vec3(0.04f, 0.05f, 0.06f));
        settings.setPlaneAxis2(new Vec3(0.07f, 0.08f, 0.09f));
        settings.setPlaneHalfConeAngle(0.11f);
        settings.setPosition1(new RVec3(0.14f, 0.15f, 0.16f));
        settings.setPosition2(new RVec3(0.17f, 0.18f, 0.19f));
        settings.setSpace(EConstraintSpace.LocalToBodyCom);
        settings.setSwingType(ESwingType.Pyramid);
        settings.setTwistAxis1(new Vec3(0.24f, 0.25f, 0.26f));
        settings.setTwistAxis2(new Vec3(0.27f, 0.28f, 0.29f));
        settings.setTwistMaxAngle(0.4f);
        settings.setTwistMinAngle(0.3f);

        Assert.assertEquals(0.01f, settings.getMaxFrictionTorque(), 0f);
        Assert.assertEquals(0.02f, settings.getNormalHalfConeAngle(), 0f);
        TestUtils.assertEquals(
                0.04f, 0.05f, 0.06f, settings.getPlaneAxis1(), 0f);
        TestUtils.assertEquals(
                0.07f, 0.08f, 0.09f, settings.getPlaneAxis2(), 0f);
        Assert.assertEquals(0.11f, settings.getPlaneHalfConeAngle(), 0f);
        TestUtils.assertEquals(
                0.14f, 0.15f, 0.16f, settings.getPosition1(), 0f);
        TestUtils.assertEquals(
                0.17f, 0.18f, 0.19f, settings.getPosition2(), 0f);
        Assert.assertEquals(
                EConstraintSpace.LocalToBodyCom, settings.getSpace());
        Assert.assertEquals(ESwingType.Pyramid, settings.getSwingType());
        TestUtils.assertEquals(
                0.24f, 0.25f, 0.26f, settings.getTwistAxis1(), 0f);
        TestUtils.assertEquals(
                0.27f, 0.28f, 0.29f, settings.getTwistAxis2(), 0f);
        Assert.assertEquals(0.4f, settings.getTwistMaxAngle(), 0f);
        Assert.assertEquals(0.3f, settings.getTwistMinAngle(), 0f);
    }
}
