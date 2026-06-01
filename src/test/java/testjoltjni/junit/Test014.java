/*
Copyright (c) 2025-2026 Stephen Gold

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

import com.github.stephengold.joltjni.Body;
import com.github.stephengold.joltjni.BodyCreationSettings;
import com.github.stephengold.joltjni.BodyFilter;
import com.github.stephengold.joltjni.BodyInterface;
import com.github.stephengold.joltjni.BoxShape;
import com.github.stephengold.joltjni.BroadPhaseLayerFilter;
import com.github.stephengold.joltjni.CustomTireMaxImpulseCallback;
import com.github.stephengold.joltjni.Jolt;
import com.github.stephengold.joltjni.JphMath;
import com.github.stephengold.joltjni.LinearCurve;
import com.github.stephengold.joltjni.MotorcycleControllerSettings;
import com.github.stephengold.joltjni.ObjectLayerFilter;
import com.github.stephengold.joltjni.PhysicsSystem;
import com.github.stephengold.joltjni.SimpleTireMaxImpulseCallback;
import com.github.stephengold.joltjni.TireMaxImpulseCallback;
import com.github.stephengold.joltjni.TrackedVehicleControllerSettings;
import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.VehicleAntiRollBar;
import com.github.stephengold.joltjni.VehicleCollisionTester;
import com.github.stephengold.joltjni.VehicleCollisionTesterCastCylinder;
import com.github.stephengold.joltjni.VehicleCollisionTesterCastSphere;
import com.github.stephengold.joltjni.VehicleCollisionTesterRay;
import com.github.stephengold.joltjni.VehicleConstraint;
import com.github.stephengold.joltjni.VehicleConstraintSettings;
import com.github.stephengold.joltjni.VehicleControllerSettings;
import com.github.stephengold.joltjni.VehicleDifferentialSettings;
import com.github.stephengold.joltjni.VehicleEngineSettings;
import com.github.stephengold.joltjni.VehicleTrackSettings;
import com.github.stephengold.joltjni.VehicleTransmissionSettings;
import com.github.stephengold.joltjni.WheelSettings;
import com.github.stephengold.joltjni.WheelSettingsTv;
import com.github.stephengold.joltjni.WheelSettingsTvRef;
import com.github.stephengold.joltjni.WheelSettingsWv;
import com.github.stephengold.joltjni.WheelSettingsWvRef;
import com.github.stephengold.joltjni.WheeledVehicleControllerSettings;
import com.github.stephengold.joltjni.enumerate.EActivation;
import com.github.stephengold.joltjni.enumerate.EConstraintSubType;
import com.github.stephengold.joltjni.enumerate.EConstraintType;
import com.github.stephengold.joltjni.enumerate.ETransmissionMode;
import com.github.stephengold.joltjni.readonly.ConstLinearCurve;
import com.github.stephengold.joltjni.readonly.ConstMotorcycleControllerSettings;
import com.github.stephengold.joltjni.readonly.ConstTrackedVehicleControllerSettings;
import com.github.stephengold.joltjni.readonly.ConstVehicleAntiRollBar;
import com.github.stephengold.joltjni.readonly.ConstVehicleCollisionTester;
import com.github.stephengold.joltjni.readonly.ConstVehicleConstraintSettings;
import com.github.stephengold.joltjni.readonly.ConstVehicleDifferentialSettings;
import com.github.stephengold.joltjni.readonly.ConstVehicleEngineSettings;
import com.github.stephengold.joltjni.readonly.ConstVehicleTrackSettings;
import com.github.stephengold.joltjni.readonly.ConstVehicleTransmissionSettings;
import com.github.stephengold.joltjni.readonly.ConstWheelSettings;
import com.github.stephengold.joltjni.readonly.ConstWheelSettingsTv;
import com.github.stephengold.joltjni.readonly.ConstWheelSettingsWv;
import com.github.stephengold.joltjni.readonly.ConstWheeledVehicleControllerSettings;
import java.nio.FloatBuffer;
import org.junit.Assert;
import org.junit.Test;
import testjoltjni.TestUtils;
import testjoltjni.app.samples.Layers;

/**
 * Automated JUnit4 tests for creation, destruction, copying, accessors, and
 * defaults of vehicle-related Jolt-JNI objects.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Test014 {
    // *************************************************************************
    // new methods exposed

    /**
     * Test object creation, destruction, copying, accessors, and defaults.
     */
    @Test
    public void test014() {
        TestUtils.loadNativeLibrary();
        TestUtils.initializeNativeLibrary();

        doCustomTireMaxImpulseCallback();
        doMcControllerSettings();
        doSimpleTireMaxImpulseCallback();
        doTvControllerSettings();

        doVehicleAntiRollBar();
        doVehicleCollisionTesterCastCylinder();
        doVehicleCollisionTesterCastSphere();
        doVehicleCollisionTesterRay();
        doVehicleConstraint();
        doVehicleConstraintSettings();
        doVehicleDifferentialSettings();
        doVehicleEngineSettings();
        doVehicleTrackSettings();
        doVehicleTransmissionSettings();

        doWvControllerSettings();
        doWheelSettingsTv();
        doWheelSettingsWv();

        TestUtils.cleanup();
    }
    // *************************************************************************
    // Java private methods

    /**
     * Test the {@code CustomTireMaxImpulseCallback} class.
     */
    private static void doCustomTireMaxImpulseCallback() {
        TireMaxImpulseCallback callback = new CustomTireMaxImpulseCallback() {
            /**
             * Calculate maximum lateral tire impulse.
             *
             * @param wi the index of the desired wheel (&ge;0)
             * @param si the suspension impulse
             * @param lof the longitudinal friction coefficient
             * @param laf the lateral friction coefficient
             * @param los the longitudinal slip component
             * @param las the lateral slip component
             * @param dt the duration of the simulation step (in seconds)
             * @return the lateral component of the maximum impulse
             */
            @Override
            public float maxLateralImpulse(int wi, float si, float lof,
                    float laf, float los, float las, float dt) {
                float result = wi + 2f * si + 3f * lof + 4f * laf + 5f * los
                        + 6f * las + 7f * dt;
                return result;
            }

            /**
             * Calculate maximum longitudinal tire impulse.
             *
             * @param wi the index of the desired wheel (&ge;0)
             * @param si the suspension impulse
             * @param lof the longitudinal friction coefficient
             * @param laf the lateral friction coefficient
             * @param los the longitudinal slip component
             * @param las the lateral slip component
             * @param dt the duration of the simulation step (in seconds)
             * @return the longitudinal component of the maximum impulse
             */
            @Override
            public float maxLongitudinalImpulse(int wi, float si, float lof,
                    float laf, float los, float las, float dt) {
                float result = wi + 7f * si + 6f * lof + 5f * laf + 4f * los
                        + 3f * las + 2f * dt;
                return result;
            }
        };
        FloatBuffer storeImpulses = Jolt.newDirectFloatBuffer(2);
        callback.calculate(1, storeImpulses, 2f, 5f, 10f, 20f, 50f, 100f);
        Assert.assertEquals(1_160f, storeImpulses.get(0), 0f);
        Assert.assertEquals(525f, storeImpulses.get(1), 0f);

        TestUtils.testClose(callback);
        System.gc();
    }

    /**
     * Test the {@code MotorcycleControllerSettings} class.
     */
    private static void doMcControllerSettings() {
        MotorcycleControllerSettings mccs = new MotorcycleControllerSettings();
        testMcControllerSettingsDefaults(mccs);

        ConstMotorcycleControllerSettings copy
                = new MotorcycleControllerSettings(mccs);
        testMcControllerSettingsSetters(mccs);
        testMcControllerSettingsDefaults(copy);
        mccs.set(copy);
        testMcControllerSettingsDefaults(mccs);

        TestUtils.testClose(copy, mccs);
        System.gc();
    }

    /**
     * Test the {@code SimpleTireMaxImpulseCallback} class.
     */
    private static void doSimpleTireMaxImpulseCallback() {
        SimpleTireMaxImpulseCallback callback
                = new SimpleTireMaxImpulseCallback(0.1f, 10f);
        FloatBuffer storeImpulses = Jolt.newDirectFloatBuffer(2);
        callback.calculate(1, storeImpulses, 2f, 5f, 10f, 20f, 50f, 100f);
        Assert.assertEquals(2f, storeImpulses.get(0), 0f);
        Assert.assertEquals(100f, storeImpulses.get(1), 0f);

        // Test getters:
        Assert.assertEquals(0.1f, callback.getLateralMultiplier(), 0f);
        Assert.assertEquals(10f, callback.getLongitudinalMultiplier(), 0f);

        // Test setters:
        callback.setLateralMultiplier(2f);
        callback.setLongitudinalMultiplier(20f);
        Assert.assertEquals(2f, callback.getLateralMultiplier(), 0f);
        Assert.assertEquals(20f, callback.getLongitudinalMultiplier(), 0f);

        TestUtils.testClose(callback);
        System.gc();
    }

    /**
     * Test the {@code TrackedVehicleControllerSettings} class.
     */
    private static void doTvControllerSettings() {
        TrackedVehicleControllerSettings tvcs
                = new TrackedVehicleControllerSettings();
        testTvControllerSettingsDefaults(tvcs);

        ConstTrackedVehicleControllerSettings copy
                = new TrackedVehicleControllerSettings(tvcs);
        testTvControllerSettingsDefaults(copy);
        tvcs.set(copy);
        testTvControllerSettingsDefaults(tvcs);

        TestUtils.testClose(copy, tvcs);
        System.gc();
    }

    /**
     * Test the {@code VehicleAntiRollBar} class.
     */
    private static void doVehicleAntiRollBar() {
        VehicleAntiRollBar varb = new VehicleAntiRollBar();
        testVehicleAntiRollBarDefaults(varb);

        ConstVehicleAntiRollBar copy = new VehicleAntiRollBar(varb);
        testVehicleAntiRollBarSetters(varb);
        testVehicleAntiRollBarDefaults(copy);
        varb.set(copy);
        testVehicleAntiRollBarDefaults(varb);

        TestUtils.testClose(copy, varb);
        System.gc();
    }

    /**
     * Test the {@code VehicleCollisionTesterCastCylinder} class.
     */
    private static void doVehicleCollisionTesterCastCylinder() {
        VehicleCollisionTesterCastCylinder vctcc
                = new VehicleCollisionTesterCastCylinder(Layers.MOVING);
        testVctDefaults(vctcc);
        testVctSetters(vctcc);

        TestUtils.testClose(vctcc);
        System.gc();
    }

    /**
     * Test the {@code VehicleCollisionTesterCastSphere} class.
     */
    private static void doVehicleCollisionTesterCastSphere() {
        VehicleCollisionTesterCastSphere vctcs
                = new VehicleCollisionTesterCastSphere(Layers.MOVING, 0.3f);
        testVctDefaults(vctcs);
        testVctSetters(vctcs);

        TestUtils.testClose(vctcs);
        System.gc();
    }

    /**
     * Test the {@code VehicleCollisionTesterRay} class.
     */
    private static void doVehicleCollisionTesterRay() {
        VehicleCollisionTesterRay vctr
                = new VehicleCollisionTesterRay(Layers.MOVING);
        testVctDefaults(vctr);
        testVctSetters(vctr);

        TestUtils.testClose(vctr);
        System.gc();
    }

    /**
     * Test the {@code VehicleConstraint} class.
     */
    private static void doVehicleConstraint() {
        VehicleConstraintSettings vcs = new VehicleConstraintSettings();

        WheelSettingsWv wswv = new WheelSettingsWv();
        vcs.addWheels(wswv);

        vcs.setNumAntiRollBars(2);

        WheeledVehicleControllerSettings wvcs
                = new WheeledVehicleControllerSettings();
        vcs.setController(wvcs);

        BoxShape shape = new BoxShape(2f);
        BodyCreationSettings bcs = new BodyCreationSettings().setShape(shape);
        PhysicsSystem physicsSystem = TestUtils.newPhysicsSystem(2);
        BodyInterface bi = physicsSystem.getBodyInterface();
        Body body = bi.createBody(bcs);
        bi.addBody(body, EActivation.DontActivate);

        VehicleConstraint vc = new VehicleConstraint(body, vcs);

        // Test the getters:
        Assert.assertEquals(2, vc.countAntiRollBars());
        Assert.assertEquals(1, vc.countWheels());
        Assert.assertNotNull(vc.getAntiRollBar(1));

        Assert.assertNotNull(vc.getController());
        Assert.assertEquals("WheeledVehicleController",
                vc.getController().getClass().getSimpleName());

        Assert.assertTrue(vc.getEnabled());

        TestUtils.assertEquals(0f, 0f, 0f, vc.getGravityOverride(), 0f);
        TestUtils.assertEquals(0f, 0f, 1f, vc.getLocalForward(), 0f);
        TestUtils.assertEquals(0f, 1f, 0f, vc.getLocalUp(), 0f);
        Assert.assertEquals(JphMath.JPH_PI, vc.getMaxPitchRollAngle(), 0f);

        Assert.assertEquals(1, vc.getNumStepsBetweenCollisionTestActive());
        Assert.assertEquals(1, vc.getNumStepsBetweenCollisionTestInactive());

        Assert.assertNotNull(vc.getStepListener());
        Assert.assertEquals(EConstraintSubType.Vehicle, vc.getSubType());
        Assert.assertEquals(EConstraintType.Constraint, vc.getType());
        Assert.assertEquals(body, vc.getVehicleBody());
        Assert.assertNull(vc.getVehicleCollisionTester());

        Assert.assertNotNull(vc.getWheel(0));
        Assert.assertEquals(
                "WheelWv", vc.getWheel(0).getClass().getSimpleName());
        TestUtils.assertEquals(0f, 1f, 0f, vc.getWorldUp(), 0f);
        Assert.assertFalse(vc.isActive());
        Assert.assertFalse(vc.isGravityOverridden());

        // Assign a collision tester:
        VehicleCollisionTesterRay vctr
                = new VehicleCollisionTesterRay(Layers.MOVING);
        vc.setVehicleCollisionTester(vctr);
        Assert.assertNotNull(vc.getVehicleCollisionTester());
        Assert.assertEquals("VehicleCollisionTesterRay",
                vc.getVehicleCollisionTester().getClass().getSimpleName());
        Assert.assertEquals(vctr, vc.getVehicleCollisionTester());

        // Override gravity:
        vc.overrideGravity(3f, 4f, 5f);
        TestUtils.assertEquals(3f, 4f, 5f, vc.getGravityOverride(), 0f);
        Assert.assertTrue(vc.isGravityOverridden());

        // Alter the maximum pitch/roll angle:
        vc.setMaxPitchRollAngle(0.12f);
        Assert.assertEquals(0.12f, vc.getMaxPitchRollAngle(), 1e-8f);

        TestUtils.testClose(vctr, vc);
        TestUtils.cleanupPhysicsSystem(physicsSystem);
        TestUtils.testClose(bcs, shape, wvcs, wswv, vcs);
        System.gc();
    }

    /**
     * Test the {@code VehicleConstraintSettings} class.
     */
    private static void doVehicleConstraintSettings() {
        VehicleConstraintSettings vcs = new VehicleConstraintSettings();
        testVehicleConstraintSettingsDefaults(vcs);

        ConstVehicleConstraintSettings copy
                = new VehicleConstraintSettings(vcs);
        testVehicleConstraintSettingsSetters(vcs);
        testVehicleConstraintSettingsDefaults(copy);
        vcs.set(copy);
        testVehicleConstraintSettingsDefaults(vcs);

        TestUtils.testClose(copy, vcs);
        System.gc();
    }

    /**
     * Test the {@code VehicleDifferentialSettings} class.
     */
    private static void doVehicleDifferentialSettings() {
        VehicleDifferentialSettings vds = new VehicleDifferentialSettings();
        testVehicleDifferentialSettingsDefaults(vds);

        ConstVehicleDifferentialSettings copy
                = new VehicleDifferentialSettings(vds);
        testVehicleDifferentialSettingsSetters(vds);
        testVehicleDifferentialSettingsDefaults(copy);
        vds.set(copy);
        testVehicleDifferentialSettingsDefaults(vds);

        TestUtils.testClose(copy, vds);
        System.gc();
    }

    /**
     * Test the {@code VehicleEngineSettings} class.
     */
    private static void doVehicleEngineSettings() {
        VehicleEngineSettings ves = new VehicleEngineSettings();
        testVehicleEngineSettingsDefaults(ves);

        ConstVehicleEngineSettings copy = new VehicleEngineSettings(ves);
        testVehicleEngineSettingsSetters(ves);
        testVehicleEngineSettingsDefaults(copy);
        ves.set(copy);
        testVehicleEngineSettingsDefaults(ves);

        TestUtils.testClose(copy, ves);
        System.gc();
    }

    /**
     * Test the {@code VehicleTrackSettings} class.
     */
    private static void doVehicleTrackSettings() {
        VehicleTrackSettings vts = new VehicleTrackSettings();
        testVehicleTrackSettingsDefaults(vts);

        ConstVehicleTrackSettings copy = new VehicleTrackSettings(vts);
        testVehicleTrackSettingsSetters(vts);
        testVehicleTrackSettingsDefaults(copy);
        vts.set(copy);
        testVehicleTrackSettingsDefaults(vts);

        TestUtils.testClose(copy, vts);
        System.gc();
    }

    /**
     * Test the {@code VehicleTransmissionSettings} class.
     */
    private static void doVehicleTransmissionSettings() {
        VehicleTransmissionSettings vtms = new VehicleTransmissionSettings();
        testVehicleTransmissionSettingsDefaults(vtms);

        ConstVehicleTransmissionSettings copy
                = new VehicleTransmissionSettings(vtms);
        testVehicleTransmissionSettingsSetters(vtms);
        testVehicleTransmissionSettingsDefaults(copy);
        vtms.set(copy);
        testVehicleTransmissionSettingsDefaults(vtms);

        TestUtils.testClose(copy, vtms);
        System.gc();
    }

    /**
     * Test the {@code WheelSettingsTv} class.
     */
    private static void doWheelSettingsTv() {
        WheelSettingsTvRef ref = new WheelSettingsTv().toRef();
        testWheelSettingsTvDefaults(ref);

        WheelSettingsTv wstv = new WheelSettingsTv();
        testWheelSettingsTvDefaults(wstv);

        ConstWheelSettingsTv copy = new WheelSettingsTv(wstv);
        testWheelSettingsTvSetters(wstv);
        testWheelSettingsTvDefaults(copy);
        wstv.set(copy);
        testWheelSettingsTvDefaults(wstv);

        TestUtils.testClose(copy, wstv, ref.getPtr(), ref);
        System.gc();
    }

    /**
     * Test the {@code WheelSettingsWv} class.
     */
    private static void doWheelSettingsWv() {
        WheelSettingsWvRef ref = new WheelSettingsWv().toRef();
        testWheelSettingsWvDefaults(ref);

        WheelSettingsWv wswv = new WheelSettingsWv();
        testWheelSettingsWvDefaults(wswv);

        ConstWheelSettingsWv copy = new WheelSettingsWv(wswv);
        testWheelSettingsWvSetters(wswv);
        testWheelSettingsWvDefaults(copy);
        wswv.set(copy);
        testWheelSettingsWvDefaults(wswv);

        TestUtils.testClose(copy, wswv, ref.getPtr(), ref);
        System.gc();
    }

    /**
     * Test the {@code WheeledVehicleControllerSettings} class.
     */
    private static void doWvControllerSettings() {
        WheeledVehicleControllerSettings wvcs
                = new WheeledVehicleControllerSettings();
        testWvControllerSettingsDefaults(wvcs);

        ConstWheeledVehicleControllerSettings copy
                = new WheeledVehicleControllerSettings(wvcs);
        testWvControllerSettingsSetters(wvcs);
        testWvControllerSettingsDefaults(copy);
        wvcs.set(copy);
        testWvControllerSettingsDefaults(wvcs);

        TestUtils.testClose(copy, wvcs);
        System.gc();
    }

    /**
     * Test the getters and defaults of the specified
     * {@code MotorcycleControllerSettings}.
     *
     * @param mccs the settings to test (not {@code null}, unaffected)
     */
    private static void testMcControllerSettingsDefaults(
            ConstMotorcycleControllerSettings mccs) {
        testWvControllerSettingsDefaults(mccs);

        Assert.assertEquals(0.8f, mccs.getLeanSmoothingFactor(), 0f);
        Assert.assertEquals(5_000f, mccs.getLeanSpringConstant(), 0f);
        Assert.assertEquals(1_000f, mccs.getLeanSpringDamping(), 0f);
        Assert.assertEquals(0f, mccs.getLeanSpringIntegrationCoefficient(), 0f);
        Assert.assertEquals(
                4f, mccs.getLeanSpringIntegrationCoefficientDecay(), 0f);
        Assert.assertEquals(JphMath.JPH_PI / 4, mccs.getMaxLeanAngle(), 0f);
    }

    /**
     * Test the setters of the specified {@code MotorcycleControllerSettings}.
     *
     * @param mccs the settings to test (not {@code null}, modified)
     */
    private static void testMcControllerSettingsSetters(
            MotorcycleControllerSettings mccs) {
        mccs.setLeanSmoothingFactor(0.7f);
        mccs.setLeanSpringConstant(3_300f);
        mccs.setLeanSpringDamping(800f);
        mccs.setLeanSpringIntegrationCoefficient(2f);
        mccs.setLeanSpringIntegrationCoefficientDecay(5f);
        mccs.setMaxLeanAngle(0.4f);

        testWvControllerSettingsSetters(mccs);

        Assert.assertEquals(0.7f, mccs.getLeanSmoothingFactor(), 0f);
        Assert.assertEquals(3_300f, mccs.getLeanSpringConstant(), 0f);
        Assert.assertEquals(800f, mccs.getLeanSpringDamping(), 0f);
        Assert.assertEquals(2f, mccs.getLeanSpringIntegrationCoefficient(), 0f);
        Assert.assertEquals(
                5f, mccs.getLeanSpringIntegrationCoefficientDecay(), 0f);
        Assert.assertEquals(0.4f, mccs.getMaxLeanAngle(), 0f);
    }

    /**
     * Test the getters and defaults of the specified
     * {@code TrackedVehicleControllerSettings}.
     *
     * @param tvcs the settings to test (not {@code null}, unaffected)
     */
    private static void testTvControllerSettingsDefaults(
            ConstTrackedVehicleControllerSettings tvcs) {
        Assert.assertNotNull(tvcs.getEngine());
        Assert.assertEquals(2, tvcs.getNumTracks());
        Assert.assertNotNull(tvcs.getTransmission());
    }

    /**
     * Test the getters and defaults of the specified
     * {@code VehicleCollisionTester}.
     *
     * @param vct the tester to test (not {@code null}, unaffected)
     */
    private static void testVctDefaults(ConstVehicleCollisionTester vct) {
        Assert.assertNull(vct.getBodyFilter());
        Assert.assertNull(vct.getBroadPhaseLayerFilter());
        Assert.assertEquals(Layers.MOVING, vct.getObjectLayer());
        Assert.assertNull(vct.getObjectLayerFilter());
    }

    /**
     * Test the setters of the specified {@code VehicleCollisionTester}.
     *
     * @param vct the tester to test (not {@code null}, modified)
     */
    private static void testVctSetters(VehicleCollisionTester vct) {
        BodyFilter bodyFilter = new BodyFilter();
        vct.setBodyFilter(bodyFilter);

        BroadPhaseLayerFilter bplFilter = new BroadPhaseLayerFilter();
        vct.setBroadPhaseLayerFilter(bplFilter);

        vct.setObjectLayer(2);

        ObjectLayerFilter olFilter = new ObjectLayerFilter();
        vct.setObjectLayerFilter(olFilter);

        Assert.assertEquals(bodyFilter, vct.getBodyFilter());
        Assert.assertEquals(bplFilter, vct.getBroadPhaseLayerFilter());
        Assert.assertEquals(2, vct.getObjectLayer());
        Assert.assertEquals(olFilter, vct.getObjectLayerFilter());
    }

    /**
     * Test the getters and defaults of the specified
     * {@code VehicleAntiRollBar}.
     *
     * @param varb the bar to test (not {@code null}, unaffected)
     */
    private static void testVehicleAntiRollBarDefaults(
            ConstVehicleAntiRollBar varb) {
        Assert.assertEquals(0, varb.getLeftWheel());
        Assert.assertEquals(1, varb.getRightWheel());
        Assert.assertEquals(1_000f, varb.getStiffness(), 0f);
    }

    /**
     * Test the setters of the specified {@code VehicleAntiRollBar}.
     *
     * @param varb the bar to test (not {@code null}, modified)
     */
    private static void testVehicleAntiRollBarSetters(VehicleAntiRollBar varb) {
        varb.setLeftWheel(3);
        varb.setRightWheel(2);
        varb.setStiffness(432f);

        Assert.assertEquals(3, varb.getLeftWheel());
        Assert.assertEquals(2, varb.getRightWheel());
        Assert.assertEquals(432f, varb.getStiffness(), 0f);
    }

    /**
     * Test the getters and defaults of the specified
     * {@code VehicleConstraintSettings}.
     *
     * @param vcs the settings to test (not {@code null}, unaffected)
     */
    private static void testVehicleConstraintSettingsDefaults(
            ConstVehicleConstraintSettings vcs) {
        Assert.assertEquals(0, vcs.getConstraintPriority());
        Assert.assertNull(vcs.getController());
        Assert.assertEquals(0, vcs.getControllerType());
        Assert.assertEquals(1f, vcs.getDrawConstraintSize(), 0f);
        Assert.assertTrue(vcs.getEnabled());
        TestUtils.assertEquals(0f, 0f, 1f, vcs.getForward(), 0f);
        Assert.assertEquals(JphMath.JPH_PI, vcs.getMaxPitchRollAngle(), 0f);
        Assert.assertEquals(0, vcs.getNumAntiRollBars());
        Assert.assertEquals(0, vcs.getNumPositionStepsOverride());
        Assert.assertEquals(0, vcs.getNumVelocityStepsOverride());
        Assert.assertEquals(0, vcs.getNumWheels());
        TestUtils.assertEquals(0f, 1f, 0f, vcs.getUp(), 0f);
    }

    /**
     * Test the setters of the specified {@code VehicleConstraintSettings}.
     *
     * @param vcs the settings to test (not {@code null}, modified)
     */
    private static void testVehicleConstraintSettingsSetters(
            VehicleConstraintSettings vcs) {
        WheelSettingsWv ws = new WheelSettingsWv();
        vcs.addWheels(ws);
        vcs.setConstraintPriority(9);
        WheeledVehicleControllerSettings controller
                = new WheeledVehicleControllerSettings();
        vcs.setController(controller);
        vcs.setDrawConstraintSize(8f);
        vcs.setEnabled(false);
        vcs.setForward(new Vec3(0.6f, 0.8f, 0f));
        vcs.setMaxPitchRollAngle(1.2f);
        vcs.setNumAntiRollBars(1);
        vcs.setNumPositionStepsOverride(4);
        vcs.setNumVelocityStepsOverride(5);
        vcs.setUp(new Vec3(0f, 0f, -1f));
        TestUtils.testClose(controller, ws);

        Assert.assertEquals(9, vcs.getConstraintPriority());

        VehicleControllerSettings settings2 = vcs.getController();
        Assert.assertNotNull(settings2);
        TestUtils.testClose(settings2);

        Assert.assertEquals(4, vcs.getControllerType());
        Assert.assertEquals(8f, vcs.getDrawConstraintSize(), 0f);
        Assert.assertFalse(vcs.getEnabled());
        TestUtils.assertEquals(0.6f, 0.8f, 0f, vcs.getForward(), 0f);
        Assert.assertEquals(1.2f, vcs.getMaxPitchRollAngle(), 0f);
        Assert.assertEquals(1, vcs.getNumAntiRollBars());
        Assert.assertEquals(4, vcs.getNumPositionStepsOverride());
        Assert.assertEquals(5, vcs.getNumVelocityStepsOverride());
        Assert.assertEquals(1, vcs.getNumWheels());
        TestUtils.assertEquals(0f, 0f, -1f, vcs.getUp(), 0f);
    }

    /**
     * Test the getters and defaults of the specified
     * {@code VehicleDifferentialSettings}.
     *
     * @param vds the settings to test (not {@code null}, unaffected)
     */
    private static void testVehicleDifferentialSettingsDefaults(
            ConstVehicleDifferentialSettings vds) {
        Assert.assertEquals(3.42f, vds.getDifferentialRatio(), 0f);
        Assert.assertEquals(1f, vds.getEngineTorqueRatio(), 0f);
        Assert.assertEquals(0.5f, vds.getLeftRightSplit(), 0f);
        Assert.assertEquals(-1, vds.getLeftWheel());
        Assert.assertEquals(1.4f, vds.getLimitedSlipRatio(), 0f);
        Assert.assertEquals(-1, vds.getRightWheel());
    }

    /**
     * Test the setters of the specified {@code VehicleDifferentialSettings}.
     *
     * @param vds the settings to test (not {@code null}, modified)
     */
    private static void testVehicleDifferentialSettingsSetters(
            VehicleDifferentialSettings vds) {
        vds.setDifferentialRatio(3f);
        vds.setEngineTorqueRatio(1.5f);
        vds.setLeftRightSplit(0.56f);
        vds.setLeftWheel(4);
        vds.setLimitedSlipRatio(1.2f);
        vds.setRightWheel(3);

        Assert.assertEquals(3f, vds.getDifferentialRatio(), 0f);
        Assert.assertEquals(1.5f, vds.getEngineTorqueRatio(), 0f);
        Assert.assertEquals(0.56f, vds.getLeftRightSplit(), 0f);
        Assert.assertEquals(4, vds.getLeftWheel());
        Assert.assertEquals(1.2f, vds.getLimitedSlipRatio(), 0f);
        Assert.assertEquals(3, vds.getRightWheel());
    }

    /**
     * Test the getters and defaults of the specified
     * {@code VehicleEngineSettings}.
     *
     * @param ves the settings to test (not {@code null}, unaffected)
     */
    private static void testVehicleEngineSettingsDefaults(
            ConstVehicleEngineSettings ves) {
        Assert.assertEquals(0.2f, ves.getAngularDamping(), 0f);
        Assert.assertEquals(0.5f, ves.getInertia(), 0f);
        Assert.assertEquals(6_000f, ves.getMaxRpm(), 0f);
        Assert.assertEquals(500f, ves.getMaxTorque(), 0f);
        Assert.assertEquals(1_000f, ves.getMinRpm(), 0f);

        ConstLinearCurve nt = ves.getNormalizedTorque();
        Assert.assertEquals(3, nt.countPoints());
        Assert.assertEquals(0f, nt.getPointX(0), 0f);
        Assert.assertEquals(0.66f, nt.getPointX(1), 0f);
        Assert.assertEquals(1f, nt.getPointX(2), 0f);
        Assert.assertEquals(0.8f, nt.getPointY(0), 0f);
        Assert.assertEquals(1f, nt.getPointY(1), 0f);
        Assert.assertEquals(0.8f, nt.getPointY(2), 0f);
    }

    /**
     * Test the setters of the specified {@code VehicleEngineSettings}.
     *
     * @param ves the settings to test (not {@code null}, modified)
     */
    private static void testVehicleEngineSettingsSetters(
            VehicleEngineSettings ves) {
        ves.setAngularDamping(0.33f);
        ves.setInertia(0.44f);
        ves.setMaxRpm(6_600f);
        ves.setMaxTorque(555f);
        ves.setMinRpm(888f);

        LinearCurve nt = new LinearCurve();
        nt.addPoint(0f, 0f);
        nt.addPoint(1f, 9f);
        ves.setNormalizedTorque(nt);

        Assert.assertEquals(0.33f, ves.getAngularDamping(), 0f);
        Assert.assertEquals(0.44f, ves.getInertia(), 0f);
        Assert.assertEquals(6_600f, ves.getMaxRpm(), 0f);
        Assert.assertEquals(555f, ves.getMaxTorque(), 0f);
        Assert.assertEquals(888f, ves.getMinRpm(), 0f);

        ConstLinearCurve ntCopy = ves.getNormalizedTorque();
        Assert.assertEquals(2, ntCopy.countPoints());
        Assert.assertEquals(0f, ntCopy.getPointX(0), 0f);
        Assert.assertEquals(1f, ntCopy.getPointX(1), 0f);
        Assert.assertEquals(0f, ntCopy.getPointY(0), 0f);
        Assert.assertEquals(9f, ntCopy.getPointY(1), 0f);
    }

    /**
     * Test the getters and defaults of the specified
     * {@code VehicleTrackSettings}.
     *
     * @param vts the settings to test (not {@code null}, unaffected)
     */
    private static void testVehicleTrackSettingsDefaults(
            ConstVehicleTrackSettings vts) {
        Assert.assertEquals(0.5f, vts.getAngularDamping(), 0f);
        Assert.assertEquals(6f, vts.getDifferentialRatio(), 0f);
        Assert.assertEquals(0, vts.getDrivenWheel());
        Assert.assertEquals(10f, vts.getInertia(), 0f);
        Assert.assertEquals(0, vts.getNumWheels());
    }

    /**
     * Test the setters of the specified {@code VehicleTrackSettings}.
     *
     * @param vts the settings to test (not {@code null}, modified)
     */
    private static void testVehicleTrackSettingsSetters(
            VehicleTrackSettings vts) {
        vts.addWheel(0);
        vts.addWheel(2);
        vts.addWheel(3);
        vts.addWheel(1);
        vts.setAngularDamping(1.2f);
        vts.setDifferentialRatio(4f);
        vts.setDrivenWheel(1);
        vts.setInertia(8f);

        Assert.assertEquals(1.2f, vts.getAngularDamping(), 0f);
        Assert.assertEquals(4f, vts.getDifferentialRatio(), 0f);
        Assert.assertEquals(1, vts.getDrivenWheel());
        Assert.assertEquals(8f, vts.getInertia(), 0f);
        Assert.assertEquals(4, vts.getNumWheels());
    }

    /**
     * Test the getters and defaults of the specified
     * {@code VehicleTransmissionSettings}.
     *
     * @param vts the settings to test (not {@code null}, unaffected)
     */
    private static void testVehicleTransmissionSettingsDefaults(
            ConstVehicleTransmissionSettings vts) {
        Assert.assertEquals(0.3f, vts.getClutchReleaseTime(), 0f);
        Assert.assertEquals(10f, vts.getClutchStrength(), 0f);

        float[] ratios = vts.getGearRatios();
        Assert.assertEquals(5, ratios.length);
        Assert.assertEquals(2.66f, ratios[0], 0f);
        Assert.assertEquals(1.78f, ratios[1], 0f);
        Assert.assertEquals(1.3f, ratios[2], 0f);
        Assert.assertEquals(1f, ratios[3], 0f);
        Assert.assertEquals(0.74f, ratios[4], 0f);

        Assert.assertEquals(ETransmissionMode.Auto, vts.getMode());

        float[] reverseRatios = vts.getReverseGearRatios();
        Assert.assertEquals(1, reverseRatios.length);
        Assert.assertEquals(-2.9f, reverseRatios[0], 0f);

        Assert.assertEquals(2_000f, vts.getShiftDownRpm(), 0f);
        Assert.assertEquals(4_000f, vts.getShiftUpRpm(), 0f);
        Assert.assertEquals(0.5f, vts.getSwitchLatency(), 0f);
        Assert.assertEquals(0.5f, vts.getSwitchTime(), 0f);
    }

    /**
     * Test the setters of the specified {@code VehicleTransmissionSettings}.
     *
     * @param vts the settings to test (not {@code null}, modified)
     */
    private static void testVehicleTransmissionSettingsSetters(
            VehicleTransmissionSettings vts) {
        vts.setClutchReleaseTime(0.25f);
        vts.setClutchStrength(12f);
        vts.setGearRatios(3f, 2f, 1.5f, 1f);
        vts.setMode(ETransmissionMode.Manual);
        vts.setReverseGearRatios(-3f, -1f);
        vts.setShiftDownRpm(2_400f);
        vts.setShiftUpRpm(4_200f);
        vts.setSwitchLatency(0.6f);
        vts.setSwitchTime(0.44f);

        Assert.assertEquals(0.25f, vts.getClutchReleaseTime(), 0f);
        Assert.assertEquals(12f, vts.getClutchStrength(), 0f);

        float[] ratios = vts.getGearRatios();
        Assert.assertEquals(4, ratios.length);
        Assert.assertEquals(3f, ratios[0], 0f);
        Assert.assertEquals(2f, ratios[1], 0f);
        Assert.assertEquals(1.5f, ratios[2], 0f);
        Assert.assertEquals(1f, ratios[3], 0f);

        Assert.assertEquals(ETransmissionMode.Manual, vts.getMode());

        float[] reverseRatios = vts.getReverseGearRatios();
        Assert.assertEquals(2, reverseRatios.length);
        Assert.assertEquals(-3f, reverseRatios[0], 0f);
        Assert.assertEquals(-1f, reverseRatios[1], 0f);

        Assert.assertEquals(2_400f, vts.getShiftDownRpm(), 0f);
        Assert.assertEquals(4_200f, vts.getShiftUpRpm(), 0f);
        Assert.assertEquals(0.6f, vts.getSwitchLatency(), 0f);
        Assert.assertEquals(0.44f, vts.getSwitchTime(), 0f);
    }

    /**
     * Test the getters and defaults of the specified {@code WheelSettings}.
     *
     * @param ws the settings to test (not {@code null}, unaffected)
     */
    private static void testWheelSettingsDefaults(ConstWheelSettings ws) {
        Assert.assertFalse(ws.getEnableSuspensionForcePoint());
        TestUtils.assertEquals(0f, 0f, 0f, ws.getPosition(), 0f);
        Assert.assertEquals(0.3f, ws.getRadius(), 0f);
        TestUtils.assertEquals(0f, 1f, 0f, ws.getSteeringAxis(), 0f);
        TestUtils.assertEquals(0f, -1f, 0f, ws.getSuspensionDirection(), 0f);
        TestUtils.assertEquals(0f, 0f, 0f, ws.getSuspensionForcePoint(), 0f);
        Assert.assertEquals(0.5f, ws.getSuspensionMaxLength(), 0f);
        Assert.assertEquals(0.3f, ws.getSuspensionMinLength(), 0f);
        Assert.assertEquals(0f, ws.getSuspensionPreloadLength(), 0f);
        Assert.assertNotNull(ws.getSuspensionSpring());
        TestUtils.assertEquals(0f, 0f, 1f, ws.getWheelForward(), 0f);
        TestUtils.assertEquals(0f, 1f, 0f, ws.getWheelUp(), 0f);
        Assert.assertEquals(0.1f, ws.getWidth(), 0f);
    }

    /**
     * Test the setters of the specified {@code WheelSettings}.
     *
     * @param ws the settings to test (not {@code null}, modified)
     */
    private static void testWheelSettingsSetters(WheelSettings ws) {
        ws.setEnableSuspensionForcePoint(true);
        ws.setPosition(new Vec3(1f, 2f, 3f));
        ws.setRadius(0.4f);
        ws.setSteeringAxis(new Vec3(0f, 0f, 1f));
        ws.setSuspensionDirection(new Vec3(0f, -0.8f, 0.6f));
        ws.setSuspensionForcePoint(new Vec3(4f, 2f, 1f));
        ws.setSuspensionMaxLength(1f);
        ws.setSuspensionMinLength(0.2f);
        ws.setSuspensionPreloadLength(0.5f);
        ws.setWheelForward(new Vec3(0f, 0f, -1f));
        ws.setWidth(0.14f);

        Assert.assertTrue(ws.getEnableSuspensionForcePoint());
        TestUtils.assertEquals(1f, 2f, 3f, ws.getPosition(), 0f);
        Assert.assertEquals(0.4f, ws.getRadius(), 0f);
        TestUtils.assertEquals(0f, 0f, 1f, ws.getSteeringAxis(), 0f);
        TestUtils.assertEquals(
                0f, -0.8f, 0.6f, ws.getSuspensionDirection(), 0f);
        TestUtils.assertEquals(4f, 2f, 1f, ws.getSuspensionForcePoint(), 0f);
        Assert.assertEquals(1f, ws.getSuspensionMaxLength(), 0f);
        Assert.assertEquals(0.2f, ws.getSuspensionMinLength(), 0f);
        Assert.assertEquals(0.5f, ws.getSuspensionPreloadLength(), 0f);
        TestUtils.assertEquals(0f, 0f, -1f, ws.getWheelForward(), 0f);
        Assert.assertEquals(0.14f, ws.getWidth(), 0f);
    }

    /**
     * Test the getters and defaults of the specified {@code WheelSettingsTv}.
     *
     * @param wstv the settings to test (not {@code null}, unaffected)
     */
    private static void testWheelSettingsTvDefaults(ConstWheelSettingsTv wstv) {
        testWheelSettingsDefaults(wstv);

        Assert.assertEquals(2f, wstv.getLateralFriction(), 0f);
        Assert.assertEquals(4f, wstv.getLongitudinalFriction(), 0f);
    }

    /**
     * Test the setters of the specified {@code WheelSettingsTv}.
     *
     * @param wstv the settings to test (not {@code null}, modified)
     */
    private static void testWheelSettingsTvSetters(WheelSettingsTv wstv) {
        testWheelSettingsSetters(wstv);

        wstv.setLateralFriction(1f);
        wstv.setLongitudinalFriction(5f);

        Assert.assertEquals(1f, wstv.getLateralFriction(), 0f);
        Assert.assertEquals(5f, wstv.getLongitudinalFriction(), 0f);
    }

    /**
     * Test the getters and defaults of the specified {@code WheelSettingsWv}.
     *
     * @param wswv the settings to test (not {@code null}, unaffected)
     */
    private static void testWheelSettingsWvDefaults(ConstWheelSettingsWv wswv) {
        testWheelSettingsDefaults(wswv);

        Assert.assertEquals(0.2f, wswv.getAngularDamping(), 0f);
        Assert.assertEquals(0.9f, wswv.getInertia(), 0f);

        ConstLinearCurve lat = wswv.getLateralFriction();
        Assert.assertEquals(3, lat.countPoints());
        Assert.assertEquals(0f, lat.getMinX(), 0f);
        Assert.assertEquals(0f, lat.getPointX(0), 0f);
        Assert.assertEquals(3f, lat.getPointX(1), 0f);
        Assert.assertEquals(20f, lat.getPointX(2), 0f);
        Assert.assertEquals(20f, lat.getMaxX(), 0f);
        Assert.assertEquals(0f, lat.getPointY(0), 0f);
        Assert.assertEquals(1.2f, lat.getPointY(1), 0f);
        Assert.assertEquals(1f, lat.getPointY(2), 0f);

        ConstLinearCurve lon = wswv.getLongitudinalFriction();
        Assert.assertEquals(3, lon.countPoints());
        Assert.assertEquals(0f, lon.getMinX(), 0f);
        Assert.assertEquals(0f, lon.getPointX(0), 0f);
        Assert.assertEquals(0.06f, lon.getPointX(1), 0f);
        Assert.assertEquals(0.2f, lon.getPointX(2), 0f);
        Assert.assertEquals(0.2f, lon.getMaxX(), 0f);
        Assert.assertEquals(0f, lon.getPointY(0), 0f);
        Assert.assertEquals(1.2f, lon.getPointY(1), 0f);
        Assert.assertEquals(1f, lon.getPointY(2), 0f);

        Assert.assertEquals(1_500f, wswv.getMaxBrakeTorque(), 0f);
        Assert.assertEquals(4_000f, wswv.getMaxHandBrakeTorque(), 0f);
        Assert.assertEquals(
                JphMath.degreesToRadians(70f), wswv.getMaxSteerAngle(), 1e-7f);
    }

    /**
     * Test the setters of the specified {@code WheelSettingsWv}.
     *
     * @param wswv the settings to test (not {@code null}, modified)
     */
    private static void testWheelSettingsWvSetters(WheelSettingsWv wswv) {
        testWheelSettingsSetters(wswv);

        wswv.setAngularDamping(0.3f);
        wswv.setInertia(0.55f);
        wswv.setMaxBrakeTorque(333f);
        wswv.setMaxHandBrakeTorque(456f);
        wswv.setMaxSteerAngle(0.1f);

        Assert.assertEquals(0.3f, wswv.getAngularDamping(), 0f);
        Assert.assertEquals(0.55f, wswv.getInertia(), 0f);
        Assert.assertEquals(333f, wswv.getMaxBrakeTorque(), 0f);
        Assert.assertEquals(456f, wswv.getMaxHandBrakeTorque(), 0f);
        Assert.assertEquals(0.1f, wswv.getMaxSteerAngle(), 0f);
    }

    /**
     * Test the getters and defaults of the specified
     * {@code WheeledVehicleControllerSettings}.
     *
     * @param wvcs the settings to test (not {@code null}, unaffected)
     */
    private static void testWvControllerSettingsDefaults(
            ConstWheeledVehicleControllerSettings wvcs) {
        Assert.assertEquals(1.4f, wvcs.getDifferentialLimitedSlipRatio(), 0f);
        Assert.assertNotNull(wvcs.getEngine());
        Assert.assertEquals(0, wvcs.getNumDifferentials());
        Assert.assertNotNull(wvcs.getTransmission());
    }

    /**
     * Test the setters of the specified
     * {@code WheeledVehicleControllerSettings}.
     *
     * @param wvcs the settings to test (not {@code null}, modified)
     */
    private static void testWvControllerSettingsSetters(
            WheeledVehicleControllerSettings wvcs) {
        wvcs.setDifferentialLimitedSlipRatio(1.6f);
        wvcs.setNumDifferentials(2);

        Assert.assertEquals(1.6f, wvcs.getDifferentialLimitedSlipRatio(), 0f);
        Assert.assertEquals(2, wvcs.getNumDifferentials());
        Assert.assertNotNull(wvcs.getDifferential(0));
        Assert.assertNotNull(wvcs.getDifferential(1));
    }
}
