/*
Copyright (c) 2025 Stephen Gold

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
import com.github.stephengold.joltjni.BodyInterface;
import com.github.stephengold.joltjni.BoxShape;
import com.github.stephengold.joltjni.Jolt;
import com.github.stephengold.joltjni.PhysicsSystem;
import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.VehicleCollisionTesterCastCylinder;
import com.github.stephengold.joltjni.VehicleCollisionTesterCastSphere;
import com.github.stephengold.joltjni.VehicleCollisionTesterRay;
import com.github.stephengold.joltjni.VehicleConstraint;
import com.github.stephengold.joltjni.VehicleConstraintSettings;
import com.github.stephengold.joltjni.VehicleControllerSettings;
import com.github.stephengold.joltjni.WheelSettings;
import com.github.stephengold.joltjni.WheelSettingsTv;
import com.github.stephengold.joltjni.WheelSettingsWv;
import com.github.stephengold.joltjni.WheeledVehicleControllerSettings;
import com.github.stephengold.joltjni.enumerate.EActivation;
import com.github.stephengold.joltjni.enumerate.EConstraintSubType;
import com.github.stephengold.joltjni.enumerate.EConstraintType;
import com.github.stephengold.joltjni.readonly.ConstWheelSettings;
import com.github.stephengold.joltjni.readonly.ConstWheelSettingsTv;
import org.junit.Assert;
import org.junit.Test;
import testjoltjni.TestUtils;
import testjoltjni.app.samples.Layers;

/**
 * Automated JUnit4 tests for creation, destruction, accessors, and defaults of
 * vehicle-related Jolt-JNI objects.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Test014 {
    // *************************************************************************
    // new methods exposed

    /**
     * Test object creation, destruction, accessors, and defaults.
     */
    @Test
    public void test014() {
        TestUtils.loadNativeLibrary();
        TestUtils.initializeNativeLibrary();

        doVehicleCollisionTesterCastCylinder();
        doVehicleCollisionTesterCastSphere();
        doVehicleCollisionTesterRay();
        doVehicleConstraint();
        doVehicleConstraintSettings();
        doWvControllerSettings();
        doWheelSettingsTv();
        doWheelSettingsWv();

        TestUtils.cleanup();
    }
    // *************************************************************************
    // Java private methods

    /**
     * Test the {@code VehicleCollisionTesterCastCylinder} class.
     */
    private static void doVehicleCollisionTesterCastCylinder() {
        VehicleCollisionTesterCastCylinder vctcc
                = new VehicleCollisionTesterCastCylinder(Layers.MOVING);

        TestUtils.testClose(vctcc);
        System.gc();
    }

    /**
     * Test the {@code VehicleCollisionTesterCastSphere} class.
     */
    private static void doVehicleCollisionTesterCastSphere() {
        VehicleCollisionTesterCastSphere vctcs
                = new VehicleCollisionTesterCastSphere(Layers.MOVING, 0.3f);

        TestUtils.testClose(vctcs);
        System.gc();
    }

    /**
     * Test the {@code VehicleCollisionTesterRay} class.
     */
    private static void doVehicleCollisionTesterRay() {
        VehicleCollisionTesterRay vctr
                = new VehicleCollisionTesterRay(Layers.MOVING);

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
        BodyCreationSettings bcs = new BodyCreationSettings()
                .setShape(shape);
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
        Assert.assertEquals(Jolt.JPH_PI, vc.getMaxPitchRollAngle(), 0f);

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
        testVehicleConstraintSettingsSetters(vcs);

        TestUtils.testClose(vcs);
        System.gc();
    }

    /**
     * Test the {@code WheelSettingsTv} class.
     */
    private static void doWheelSettingsTv() {
        WheelSettingsTv wstv = new WheelSettingsTv();

        testWheelSettingsTvDefaults(wstv);
        testWheelSettingsTvSetters(wstv);

        TestUtils.testClose(wstv);
        System.gc();
    }

    /**
     * Test the {@code WheelSettingsWv} class.
     */
    private static void doWheelSettingsWv() {
        WheelSettingsWv wswv = new WheelSettingsWv();

        testWheelSettingsWvDefaults(wswv);
        testWheelSettingsWvSetters(wswv);

        TestUtils.testClose(wswv);
        System.gc();
    }

    /**
     * Test the {@code WheeledVehicleControllerSettings} class.
     */
    private static void doWvControllerSettings() {
        WheeledVehicleControllerSettings wvcs
                = new WheeledVehicleControllerSettings();

        testWvControllerSettingsDefaults(wvcs);
        testWvControllerSettingsSetters(wvcs);

        TestUtils.testClose(wvcs);
        System.gc();
    }

    /**
     * Test the getters and defaults of the specified
     * {@code VehicleConstraintSettings}.
     *
     * @param vcs the settings to test (not null, unaffected)
     */
    private static void testVehicleConstraintSettingsDefaults(
            VehicleConstraintSettings vcs) {
        Assert.assertEquals(0, vcs.getConstraintPriority());
        Assert.assertNull(vcs.getController());
        Assert.assertEquals(0, vcs.getControllerType());
        Assert.assertEquals(1f, vcs.getDrawConstraintSize(), 0f);
        Assert.assertTrue(vcs.getEnabled());
        TestUtils.assertEquals(0f, 0f, 1f, vcs.getForward(), 0f);
        Assert.assertEquals(Jolt.JPH_PI, vcs.getMaxPitchRollAngle(), 0f);
        Assert.assertEquals(0, vcs.getNumAntiRollBars());
        Assert.assertEquals(0, vcs.getNumPositionStepsOverride());
        Assert.assertEquals(0, vcs.getNumVelocityStepsOverride());
        Assert.assertEquals(0, vcs.getNumWheels());
        TestUtils.assertEquals(0f, 1f, 0f, vcs.getUp(), 0f);
    }

    /**
     * Test the setters of the specified {@code VehicleConstraintSettings}.
     *
     * @param vcs the settings to test (not null, modified)
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
     * Test the getters and defaults of the specified {@code WheelSettings}.
     *
     * @param ws the settings to test (not null, unaffected)
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
     * @param ws the settings to test (not null, modified)
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
     * @param wstv the settings to test (not null, unaffected)
     */
    private static void testWheelSettingsTvDefaults(ConstWheelSettingsTv wstv) {
        testWheelSettingsDefaults(wstv);

        Assert.assertEquals(2f, wstv.getLateralFriction(), 0f);
        Assert.assertEquals(4f, wstv.getLongitudinalFriction(), 0f);
    }

    /**
     * Test the setters of the specified {@code WheelSettingsTv}.
     *
     * @param wstv the settings to test (not null, modified)
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
     * @param wswv the settings to test (not null, unaffected)
     */
    private static void testWheelSettingsWvDefaults(WheelSettingsWv wswv) {
        testWheelSettingsDefaults(wswv);

        Assert.assertEquals(1_500f, wswv.getMaxBrakeTorque(), 0f);
        Assert.assertEquals(4_000f, wswv.getMaxHandBrakeTorque(), 0f);
        Assert.assertEquals(
                Jolt.degreesToRadians(70f), wswv.getMaxSteerAngle(), 1e-7f);
    }

    /**
     * Test the setters of the specified {@code WheelSettingsWv}.
     *
     * @param wswv the settings to test (not null, modified)
     */
    private static void testWheelSettingsWvSetters(WheelSettingsWv wswv) {
        testWheelSettingsSetters(wswv);

        wswv.setMaxBrakeTorque(333f);
        wswv.setMaxHandBrakeTorque(456f);
        wswv.setMaxSteerAngle(0.1f);

        Assert.assertEquals(333f, wswv.getMaxBrakeTorque(), 0f);
        Assert.assertEquals(456f, wswv.getMaxHandBrakeTorque(), 0f);
        Assert.assertEquals(0.1f, wswv.getMaxSteerAngle(), 0f);
    }

    /**
     * Test the getters and defaults of the specified
     * {@code WheeledVehicleControllerSettings}.
     *
     * @param wvcs the settings to test (not null, unaffected)
     */
    private static void testWvControllerSettingsDefaults(
            WheeledVehicleControllerSettings wvcs) {
        Assert.assertNotNull(wvcs.getEngine());
        Assert.assertEquals(0, wvcs.getNumDifferentials());
        Assert.assertNotNull(wvcs.getTransmission());
    }

    /**
     * Test the setters of the specified
     * {@code WheeledVehicleControllerSettings}.
     *
     * @param wvcs the settings to test (not null, modified)
     */
    private static void testWvControllerSettingsSetters(
            WheeledVehicleControllerSettings wvcs) {
        wvcs.setNumDifferentials(2);

        Assert.assertEquals(2, wvcs.getNumDifferentials());
        Assert.assertNotNull(wvcs.getDifferential(0));
        Assert.assertNotNull(wvcs.getDifferential(1));
    }
}
