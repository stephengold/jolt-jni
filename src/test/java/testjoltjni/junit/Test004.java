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

import com.github.stephengold.joltjni.BodyInterface;
import com.github.stephengold.joltjni.EBodyType;
import com.github.stephengold.joltjni.MapObj2Bp;
import com.github.stephengold.joltjni.ObjVsBpFilter;
import com.github.stephengold.joltjni.ObjVsObjFilter;
import com.github.stephengold.joltjni.PhysicsSettings;
import com.github.stephengold.joltjni.PhysicsSystem;
import com.github.stephengold.joltjni.Vec3;
import org.junit.Assert;
import org.junit.Test;
import testjoltjni.Utils;

/**
 * Automated JUnit4 tests for jolt-jni object creation, destruction, accessors,
 * and defaults.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Test004 {
    // *************************************************************************
    // new methods exposed

    /**
     * Test creation, destruction, accessors, and defaults of PhysicsSettings
     * and PhysicsSystem.
     */
    @Test
    public void test004() {
        Utils.loadAndInitializeNativeLibrary();

        int numBpLayers = 2;
        int numObjLayers = 3;
        MapObj2Bp mapObj2Bp = new MapObj2Bp(numObjLayers, numBpLayers);
        ObjVsBpFilter objVsBpFilter
                = new ObjVsBpFilter(numObjLayers, numBpLayers);
        ObjVsObjFilter objVsObjFilter = new ObjVsObjFilter(numObjLayers);

        int maxBodies = 1_800;
        int numBodyMutexes = 0; // 0 means "use the default value"
        int maxBodyPairs = 65_536;
        int maxContacts = 20_480;
        PhysicsSystem physicsSystem = new PhysicsSystem();
        physicsSystem.init(maxBodies, numBodyMutexes, maxBodyPairs,
                maxContacts, mapObj2Bp, objVsBpFilter, objVsObjFilter);

        Assert.assertTrue(physicsSystem.ownsNativeObject());
        testGettersAndDefaults(physicsSystem);

        // PhysicsSystem setters:
        physicsSystem.setGravity(new Vec3(0.01f, 0f, -32f));
        Utils.assertEquals(0.01f, 0f, -32f, physicsSystem.getGravity(), 0f);

        // Directly create default PhysicsSettings:
        PhysicsSettings settings2 = new PhysicsSettings();
        Assert.assertTrue(settings2.ownsNativeObject());
        testGettersAndDefaults(settings2);

        // Apply them to the PhysicsSystem:
        physicsSystem.setPhysicsSettings(settings2);
        PhysicsSettings settings3 = physicsSystem.getPhysicsSettings();
        Assert.assertNotEquals(settings2.va(), settings3.va());
        testGettersAndDefaults(settings3);

        Utils.testClose(settings3);
        Utils.testClose(settings2);
        Utils.testClose(physicsSystem);
        Utils.testClose(objVsObjFilter);
        Utils.testClose(objVsBpFilter);
        Utils.testClose(mapObj2Bp);
    }
    // *************************************************************************
    // Java private methods

    /**
     * Test the getters and defaults of the specified {@code PhysicsSettings}.
     *
     * @param settings the settings to test (not null, unaffected)
     */
    private static void testGettersAndDefaults(PhysicsSettings settings) {
        Assert.assertNotNull(settings);
        Assert.assertTrue(settings.hasAssignedNativeObject());
        Assert.assertNotEquals(0L, settings.va());

        Assert.assertTrue(settings.getAllowSleeping());
        Assert.assertEquals(0.2f, settings.getBaumgarte(), 0f);
        Assert.assertTrue(settings.getDeterministicSimulation());
        Assert.assertEquals(2, settings.getNumPositionSteps());
        Assert.assertEquals(10, settings.getNumVelocitySteps());
        Assert.assertEquals(
                0.03f, settings.getPointVelocitySleepThreshold(), 0f);
        Assert.assertEquals(0.5f, settings.getTimeBeforeSleep(), 0f);
    }

    /**
     * Test the getters and defaults of the specified {@code PhysicsSystem}.
     *
     * @param physicsSystem the settings to test (not null, unaffected)
     */
    private void testGettersAndDefaults(PhysicsSystem physicsSystem) {
        Assert.assertNotNull(physicsSystem);
        Assert.assertTrue(physicsSystem.hasAssignedNativeObject());
        Assert.assertNotEquals(0L, physicsSystem.va());

        Utils.assertEquals(0f, -9.81f, 0f, physicsSystem.getGravity(), 0f);
        Assert.assertEquals(0,
                physicsSystem.getNumActiveBodies(EBodyType.RigidBody));
        Assert.assertEquals(0,
                physicsSystem.getNumActiveBodies(EBodyType.SoftBody));
        Assert.assertEquals(0, physicsSystem.getNumBodies());

        {
            BodyInterface bodyInterface = physicsSystem.getBodyInterface();
            Assert.assertNotNull(bodyInterface);
            Assert.assertFalse(bodyInterface.ownsNativeObject());

            Assert.assertTrue(bodyInterface.hasAssignedNativeObject());
            Assert.assertNotEquals(0L, bodyInterface.va());

            // Each invocation of getBodyInterface() returns the same VA:
            BodyInterface bodyInterface1 = physicsSystem.getBodyInterface();
            Assert.assertEquals(bodyInterface.va(), bodyInterface1.va());

            Utils.testClose(bodyInterface1);
            Utils.testClose(bodyInterface);
        }

        {
            PhysicsSettings settings = physicsSystem.getPhysicsSettings();
            Assert.assertTrue(settings.ownsNativeObject());
            testGettersAndDefaults(settings);
            testSetters(settings);

            // Each invocation of getPhysicsSettings() returns a new VA:
            PhysicsSettings settings1 = physicsSystem.getPhysicsSettings();
            Assert.assertNotEquals(settings.va(), settings1.va());
            testGettersAndDefaults(settings1);

            Utils.testClose(settings1);
            Utils.testClose(settings);
        }
    }

    /**
     * Test the setters of the specified {@code PhysicsSettings}.
     *
     * @param settings the settings to test (not null, modified)
     */
    private static void testSetters(PhysicsSettings settings) {
        settings.setAllowSleeping(false);
        Assert.assertFalse(settings.getAllowSleeping());

        settings.setBaumgarte(0.11f);
        Assert.assertEquals(0.11f, settings.getBaumgarte(), 0f);

        settings.setDeterministicSimulation(false);
        Assert.assertFalse(settings.getDeterministicSimulation());

        settings.setNumPositionSteps(3);
        Assert.assertEquals(3, settings.getNumPositionSteps());

        settings.setNumVelocitySteps(12);
        Assert.assertEquals(12, settings.getNumVelocitySteps());

        settings.setPointVelocitySleepThreshold(13f);
        Assert.assertEquals(
                13f, settings.getPointVelocitySleepThreshold(), 0f);

        settings.setTimeBeforeSleep(14f);
        Assert.assertEquals(14f, settings.getTimeBeforeSleep(), 0f);
    }
}