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

import com.github.stephengold.joltjni.Jolt;
import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.VehicleConstraintSettings;
import com.github.stephengold.joltjni.VehicleControllerSettingsRef;
import com.github.stephengold.joltjni.WheelSettings;
import com.github.stephengold.joltjni.WheelSettingsTv;
import com.github.stephengold.joltjni.WheelSettingsTvRef;
import com.github.stephengold.joltjni.WheelSettingsWv;
import com.github.stephengold.joltjni.WheelSettingsWvRef;
import com.github.stephengold.joltjni.WheeledVehicleControllerSettings;
import com.github.stephengold.joltjni.readonly.ConstWheelSettings;
import com.github.stephengold.joltjni.readonly.ConstWheelSettingsTv;
import com.github.stephengold.joltjni.template.Ref;
import org.junit.Assert;
import org.junit.Test;
import testjoltjni.TestUtils;

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

        doVehicleConstraintSettings();
        doWvControllerSettings();
        doWheelSettingsTv();
        doWheelSettingsWv();

        TestUtils.cleanup();
    }
    // *************************************************************************
    // Java private methods

    /**
     * Test the {@code VehicleConstraintSettings} class.
     */
    private static void doVehicleConstraintSettings() {
        VehicleConstraintSettings vcs = new VehicleConstraintSettings();
        final Ref ref = vcs.toRef();

        testVehicleConstraintSettingsDefaults(vcs);
        testVehicleConstraintSettingsSetters(vcs);

        TestUtils.testClose(ref);
        System.gc();
    }

    /**
     * Test the {@code WheelSettingsTv} class.
     */
    private static void doWheelSettingsTv() {
        WheelSettingsTv wstv = new WheelSettingsTv();
        final WheelSettingsTvRef ref = wstv.toRef();

        testWheelSettingsTvDefaults(wstv);
        testWheelSettingsTvSetters(wstv);

        TestUtils.testClose(ref);
        System.gc();
    }

    /**
     * Test the {@code WheelSettingsWv} class.
     */
    private static void doWheelSettingsWv() {
        WheelSettingsWv wswv = new WheelSettingsWv();
        final WheelSettingsWvRef ref = wswv.toRef();

        testWheelSettingsWvDefaults(wswv);
        testWheelSettingsWvSetters(wswv);

        TestUtils.testClose(ref);
        System.gc();
    }

    /**
     * Test the {@code WheeledVehicleControllerSettings} class.
     */
    private static void doWvControllerSettings() {
        WheeledVehicleControllerSettings wvcs
                = new WheeledVehicleControllerSettings();
        final VehicleControllerSettingsRef ref = wvcs.toRef();

        testWvControllerSettingsDefaults(wvcs);
        testWvControllerSettingsSetters(wvcs);

        TestUtils.testClose(ref);
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
        vcs.addWheels(new WheelSettingsWv());
        vcs.setConstraintPriority(9);
        vcs.setController(new WheeledVehicleControllerSettings());
        vcs.setDrawConstraintSize(8f);
        vcs.setEnabled(false);
        vcs.setForward(new Vec3(0.6f, 0.8f, 0f));
        vcs.setMaxPitchRollAngle(1.2f);
        vcs.setNumAntiRollBars(1);
        vcs.setNumPositionStepsOverride(4);
        vcs.setNumVelocityStepsOverride(5);
        vcs.setUp(new Vec3(0f, 0f, -1f));

        Assert.assertEquals(9, vcs.getConstraintPriority());
        Assert.assertNotNull(vcs.getController());
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
