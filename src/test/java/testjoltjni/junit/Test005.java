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

import com.github.stephengold.joltjni.ConeConstraintSettings;
import com.github.stephengold.joltjni.DistanceConstraintSettings;
import com.github.stephengold.joltjni.FixedConstraintSettings;
import com.github.stephengold.joltjni.GearConstraintSettings;
import com.github.stephengold.joltjni.HingeConstraintSettings;
import com.github.stephengold.joltjni.PointConstraintSettings;
import com.github.stephengold.joltjni.RVec3;
import com.github.stephengold.joltjni.SixDofConstraintSettings;
import com.github.stephengold.joltjni.SliderConstraintSettings;
import com.github.stephengold.joltjni.TwoBodyConstraintSettingsRef;
import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.enumerate.EAxis;
import com.github.stephengold.joltjni.enumerate.EConstraintSpace;
import com.github.stephengold.joltjni.enumerate.ESwingType;
import com.github.stephengold.joltjni.readonly.ConstConstraintSettings;
import org.junit.Assert;
import org.junit.Test;
import testjoltjni.TestUtils;

/**
 * Automated JUnit4 tests for creation, destruction, accessors, and defaults of
 * {@code TwoBodyConstraintsSettings} subclasses.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Test005 {
    // *************************************************************************
    // new methods exposed

    /**
     * Test object creation, destruction, accessors, and defaults.
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
        doPointConstraintSettings();
        doSixDofConstraintSettings();
        doSliderConstraintSettings();

        TestUtils.cleanup();
    }
    // *************************************************************************
    // Java private methods

    /**
     * Test the {@code ConeConstraintSettings} class.
     */
    private static void doConeConstraintSettings() {
        ConeConstraintSettings settings = new ConeConstraintSettings();
        TwoBodyConstraintSettingsRef ref = settings.toRef();

        testConeCsDefaults(settings);
        testConeCsSetters(settings);

        TestUtils.testClose(ref);
        System.gc();
    }

    /**
     * Test the {@code DistanceConstraintSettings} class.
     */
    private static void doDistanceConstraintSettings() {
        DistanceConstraintSettings settings = new DistanceConstraintSettings();
        TwoBodyConstraintSettingsRef ref = settings.toRef();

        testDistanceCsDefaults(settings);
        testDistanceCsSetters(settings);

        TestUtils.testClose(ref);
        System.gc();
    }

    /**
     * Test the {@code FixedConstraintSettings} class.
     */
    private static void doFixedConstraintSettings() {
        FixedConstraintSettings settings = new FixedConstraintSettings();
        TwoBodyConstraintSettingsRef ref = settings.toRef();

        testFixedCsDefaults(settings);
        testFixedCsSetters(settings);

        TestUtils.testClose(ref);
        System.gc();
    }

    /**
     * Test the {@code GearConstraintSettings} class.
     */
    private static void doGearConstraintSettings() {
        GearConstraintSettings settings = new GearConstraintSettings();
        TwoBodyConstraintSettingsRef ref = settings.toRef();

        testGearCsDefaults(settings);
        testGearCsSetters(settings);

        TestUtils.testClose(ref);
        System.gc();
    }

    /**
     * Test the {@code HingeConstraintSettings} class.
     */
    private static void doHingeConstraintSettings() {
        HingeConstraintSettings settings = new HingeConstraintSettings();
        TwoBodyConstraintSettingsRef ref = settings.toRef();

        testHingeCsDefaults(settings);
        testHingeCsSetters(settings);

        TestUtils.testClose(ref);
        System.gc();
    }

    /**
     * Test the {@code PointConstraintSettings} class.
     */
    private static void doPointConstraintSettings() {
        PointConstraintSettings settings = new PointConstraintSettings();
        TwoBodyConstraintSettingsRef ref = settings.toRef();

        testPointCsDefaults(settings);
        testPointCsSetters(settings);

        TestUtils.testClose(ref);
        System.gc();
    }

    /**
     * Test the {@code SixDofConstraintSettings} class.
     */
    private static void doSixDofConstraintSettings() {
        SixDofConstraintSettings settings = new SixDofConstraintSettings();
        TwoBodyConstraintSettingsRef ref = settings.toRef();

        testSixDofCsDefaults(settings);
        testSixDofCsSetters(settings);

        TestUtils.testClose(ref);
        System.gc();
    }

    /**
     * Test the {@code SliderConstraintSettings} class.
     */
    private static void doSliderConstraintSettings() {
        SliderConstraintSettings settings = new SliderConstraintSettings();
        TwoBodyConstraintSettingsRef ref = settings.toRef();

        testSliderCsDefaults(settings);
        testSliderCsSetters(settings);

        TestUtils.testClose(ref);
        System.gc();
    }

    /**
     * Test the getters and defaults of the specified
     * {@code ConeConstraintSettings}.
     *
     * @param settings the settings to test (not null, unaffected)
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
     * @param settings the settings to test (not null, modified)
     */
    private static void testConeCsSetters(ConeConstraintSettings settings) {
        settings.setHalfConeAngle(0.01f);
        settings.setPoint1(new RVec3(0.02f, 0.03f, 0.04f));
        settings.setPoint2(new RVec3(0.05f, 0.06f, 0.07f));
        settings.setSpace(EConstraintSpace.LocalToBodyCOM);
        settings.setTwistAxis1(new Vec3(0.12f, 0.13f, 0.14f));
        settings.setTwistAxis2(new Vec3(0.15f, 0.16f, 0.17f));

        Assert.assertEquals(0.01f, settings.getHalfConeAngle(), 0f);
        TestUtils.assertEquals(0.02f, 0.03f, 0.04f, settings.getPoint1(), 0f);
        TestUtils.assertEquals(0.05f, 0.06f, 0.07f, settings.getPoint2(), 0f);
        Assert.assertEquals(
                EConstraintSpace.LocalToBodyCOM, settings.getSpace());
        TestUtils.assertEquals(
                0.12f, 0.13f, 0.14f, settings.getTwistAxis1(), 0f);
        TestUtils.assertEquals(
                0.15f, 0.16f, 0.17f, settings.getTwistAxis2(), 0f);
    }

    /**
     * Test the getters and defaults of the specified
     * {@code ConstraintSettings}.
     *
     * @param settings the settings to test (not null, unaffected)
     */
    private static void testCsDefaults(ConstConstraintSettings settings) {
        Assert.assertTrue(settings.hasAssignedNativeObject());
        Assert.assertFalse(settings.ownsNativeObject());

        Assert.assertEquals(0, settings.getConstraintPriority());
        Assert.assertTrue(settings.getEnabled());
        Assert.assertEquals(0, settings.getNumPositionStepsOverride());
        Assert.assertEquals(0, settings.getNumVelocityStepsOverride());
    }

    /**
     * Test the getters and defaults of the specified
     * {@code DistanceConstraintSettings}.
     *
     * @param settings the settings to test (not null, unaffected)
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
     * @param settings the settings to test (not null, modified)
     */
    private static void testDistanceCsSetters(
            DistanceConstraintSettings settings) {
        settings.setMaxDistance(0.1f);
        settings.setMinDistance(0.01f);
        settings.setPoint1(0.02f, 0.03f, 0.04f);
        settings.setPoint2(0.05f, 0.06f, 0.07f);
        settings.setSpace(EConstraintSpace.LocalToBodyCOM);

        Assert.assertEquals(0.1f, settings.getMaxDistance(), 0f);
        Assert.assertEquals(0.01f, settings.getMinDistance(), 0f);
        TestUtils.assertEquals(0.02f, 0.03f, 0.04f, settings.getPoint1(), 0f);
        TestUtils.assertEquals(0.05f, 0.06f, 0.07f, settings.getPoint2(), 0f);
        Assert.assertEquals(
                EConstraintSpace.LocalToBodyCOM, settings.getSpace());
    }

    /**
     * Test the getters and defaults of the specified
     * {@code FixedConstraintSettings}.
     *
     * @param settings the settings to test (not null, unaffected)
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
     * @param settings the settings to test (not null, modified)
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
        settings.setSpace(EConstraintSpace.LocalToBodyCOM);

        Assert.assertTrue(settings.getAutoDetectPoint());
        TestUtils.assertEquals(0.02f, 0.03f, 0.04f, settings.getAxisX1(), 0f);
        TestUtils.assertEquals(0.05f, 0.06f, 0.07f, settings.getAxisX2(), 0f);
        TestUtils.assertEquals(0.12f, 0.13f, 0.14f, settings.getAxisY1(), 0f);
        TestUtils.assertEquals(0.15f, 0.16f, 0.17f, settings.getAxisY2(), 0f);
        TestUtils.assertEquals(0.22f, 0.23f, 0.24f, settings.getPoint1(), 0f);
        TestUtils.assertEquals(0.25f, 0.26f, 0.27f, settings.getPoint2(), 0f);
        Assert.assertEquals(
                EConstraintSpace.LocalToBodyCOM, settings.getSpace());
    }

    /**
     * Test the getters and defaults of the specified
     * {@code GearConstraintSettings}.
     *
     * @param settings the settings to test (not null, unaffected)
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
     * @param settings the settings to test (not null, modified)
     */
    private static void testGearCsSetters(GearConstraintSettings settings) {
        settings.setHingeAxis1(new Vec3(0.24f, 0.25f, 0.26f));
        settings.setHingeAxis2(new Vec3(0.27f, 0.28f, 0.29f));
        settings.setRatio(0.5f);
        settings.setSpace(EConstraintSpace.LocalToBodyCOM);

        TestUtils.assertEquals(
                0.24f, 0.25f, 0.26f, settings.getHingeAxis1(), 0f);
        TestUtils.assertEquals(
                0.27f, 0.28f, 0.29f, settings.getHingeAxis2(), 0f);
        Assert.assertEquals(0.5f, settings.getRatio(), 0f);
        Assert.assertEquals(
                EConstraintSpace.LocalToBodyCOM, settings.getSpace());
    }

    /**
     * Test the getters and defaults of the specified
     * {@code HingeConstraintSettings}.
     *
     * @param settings the settings to test (not null, unaffected)
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
     * @param settings the settings to test (not null, modified)
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
        settings.setSpace(EConstraintSpace.LocalToBodyCOM);

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
                EConstraintSpace.LocalToBodyCOM, settings.getSpace());
    }

    /**
     * Test the getters and defaults of the specified
     * {@code PointConstraintSettings}.
     *
     * @param settings the settings to test (not null, unaffected)
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
     * @param settings the settings to test (not null, modified)
     */
    private static void testPointCsSetters(
            PointConstraintSettings settings) {
        settings.setPoint1(0.22, 0.23, 0.24);
        settings.setPoint2(0.25, 0.26, 0.27);
        settings.setSpace(EConstraintSpace.LocalToBodyCOM);

        TestUtils.assertEquals(0.22f, 0.23f, 0.24f, settings.getPoint1(), 0f);
        TestUtils.assertEquals(0.25f, 0.26f, 0.27f, settings.getPoint2(), 0f);
        Assert.assertEquals(
                EConstraintSpace.LocalToBodyCOM, settings.getSpace());
    }

    /**
     * Test the getters and defaults of the specified
     * {@code SixDofConstraintSettings}.
     *
     * @param settings the settings to test (not null, unaffected)
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
     * @param settings the settings to test (not null, modified)
     */
    private static void testSixDofCsSetters(SixDofConstraintSettings settings) {
        settings.setAxisX1(new Vec3(0.01f, 0.02f, 0.03f));
        settings.setAxisX2(new Vec3(0.04f, 0.05f, 0.06f));
        settings.setAxisY1(new Vec3(0.07f, 0.08f, 0.09f));
        settings.setAxisY2(new Vec3(0.11f, 0.12f, 0.13f));
        settings.setPosition1(new RVec3(0.14f, 0.15f, 0.16f));
        settings.setPosition2(new RVec3(0.17f, 0.18f, 0.19f));
        settings.setSpace(EConstraintSpace.LocalToBodyCOM);
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
                EConstraintSpace.LocalToBodyCOM, settings.getSpace());
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
     * @param settings the settings to test (not null, unaffected)
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
     * @param settings the settings to test (not null, modified)
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
        settings.setSpace(EConstraintSpace.LocalToBodyCOM);

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
                EConstraintSpace.LocalToBodyCOM, settings.getSpace());
    }
}
