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

import com.github.stephengold.joltjni.AaBox;
import com.github.stephengold.joltjni.BodyIdVector;
import com.github.stephengold.joltjni.BodyInterface;
import com.github.stephengold.joltjni.BroadPhaseLayerInterfaceTable;
import com.github.stephengold.joltjni.CombineFunction;
import com.github.stephengold.joltjni.Jolt;
import com.github.stephengold.joltjni.ObjVsBpFilter;
import com.github.stephengold.joltjni.ObjVsObjFilter;
import com.github.stephengold.joltjni.ObjectLayerPairFilterTable;
import com.github.stephengold.joltjni.PhysicsSettings;
import com.github.stephengold.joltjni.PhysicsSystem;
import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.enumerate.EBodyType;
import com.github.stephengold.joltjni.readonly.ConstPhysicsSettings;
import org.junit.Assert;
import org.junit.Test;
import testjoltjni.TestUtils;

/**
 * Automated JUnit4 tests for Jolt-JNI object creation, destruction, accessors,
 * and defaults of {@code PhysicsSettings} and {@code PhysicsSystem}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Test004 {
    // *************************************************************************
    // new methods exposed

    /**
     * Test creation, destruction, accessors, and defaults.
     */
    @Test
    public void test004() {
        TestUtils.loadNativeLibrary();
        TestUtils.initializeNativeLibrary();

        final int numBpLayers = 2;
        final int numObjLayers = 3;

        // Test ObjectLayerPairFilterTable, but don't use it:
        ObjectLayerPairFilterTable tab
                = new ObjectLayerPairFilterTable(numObjLayers);
        for (int oLayer = 0; oLayer < numObjLayers; ++oLayer) {
            Assert.assertFalse(tab.shouldCollide(oLayer, 0));
            Assert.assertFalse(tab.shouldCollide(oLayer, 1));
            Assert.assertFalse(tab.shouldCollide(oLayer, 2));
        }
        tab.enableCollision(1, 2);
        Assert.assertTrue(tab.shouldCollide(1, 2));
        Assert.assertTrue(tab.shouldCollide(2, 1));

        BroadPhaseLayerInterfaceTable mapObj2Bp
                = new BroadPhaseLayerInterfaceTable(numObjLayers, numBpLayers);
        Assert.assertEquals(numBpLayers, mapObj2Bp.getNumBroadPhaseLayers());
        Assert.assertEquals(0, mapObj2Bp.getBroadPhaseLayer(0));
        Assert.assertEquals(0, mapObj2Bp.getBroadPhaseLayer(1));
        Assert.assertEquals(0, mapObj2Bp.getBroadPhaseLayer(2));

        ObjVsBpFilter objVsBpFilter
                = new ObjVsBpFilter(numObjLayers, numBpLayers);
        for (int oLayer = 0; oLayer < numObjLayers; ++oLayer) {
            Assert.assertTrue(objVsBpFilter.shouldCollide(oLayer, 0));
            Assert.assertTrue(objVsBpFilter.shouldCollide(oLayer, 1));
        }

        ObjVsObjFilter objVsObjFilter = new ObjVsObjFilter(numObjLayers);
        for (int oLayer = 0; oLayer < numObjLayers; ++oLayer) {
            Assert.assertTrue(objVsObjFilter.shouldCollide(oLayer, 0));
            Assert.assertTrue(objVsObjFilter.shouldCollide(oLayer, 1));
            Assert.assertTrue(objVsObjFilter.shouldCollide(oLayer, 2));
        }

        int maxBodies = 1_800;
        int numBodyMutexes = 0; // 0 means "use the default value"
        int maxBodyPairs = 65_536;
        int maxContacts = 20_480;
        PhysicsSystem physicsSystem = new PhysicsSystem();
        physicsSystem.init(maxBodies, numBodyMutexes, maxBodyPairs,
                maxContacts, mapObj2Bp, objVsBpFilter, objVsObjFilter);

        Assert.assertTrue(physicsSystem.ownsNativeObject());
        Assert.assertEquals(maxBodies, physicsSystem.getMaxBodies());
        testGettersAndDefaults(physicsSystem);

        // PhysicsSystem setters:
        physicsSystem.setGravity(new Vec3(0.01f, 0f, -32f));
        TestUtils.assertEquals(0.01f, 0f, -32f, physicsSystem.getGravity(), 0f);

        physicsSystem.setCombineFriction(CombineFunction.min(true));
        Assert.assertEquals(CombineFunction.min(true),
                physicsSystem.getCombineFriction());

        physicsSystem.setCombineRestitution(CombineFunction.max(false));
        Assert.assertEquals(CombineFunction.max(false),
                physicsSystem.getCombineRestitution());

        // Directly create default PhysicsSettings:
        PhysicsSettings settings2 = new PhysicsSettings();
        Assert.assertTrue(settings2.ownsNativeObject());
        testGettersAndDefaults(settings2);

        // Apply them to the PhysicsSystem:
        physicsSystem.setPhysicsSettings(settings2);
        PhysicsSettings settings3 = physicsSystem.getPhysicsSettings();
        Assert.assertNotEquals(settings2.va(), settings3.va());
        testGettersAndDefaults(settings3);

        TestUtils.testClose(settings3, settings2, physicsSystem, objVsObjFilter,
                objVsBpFilter, mapObj2Bp);

        TestUtils.cleanup();
    }
    // *************************************************************************
    // Java private methods

    /**
     * Test the getters and defaults of the specified {@code PhysicsSettings}.
     *
     * @param settings the settings to test (not null, unaffected)
     */
    private static void testGettersAndDefaults(ConstPhysicsSettings settings) {
        Assert.assertNotNull(settings);
        Assert.assertTrue(settings.hasAssignedNativeObject());
        Assert.assertNotEquals(0L, settings.targetVa());

        Assert.assertEquals(16384, settings.getMaxInFlightBodyPairs());
        Assert.assertEquals(8, settings.getStepListenersBatchSize());
        Assert.assertEquals(1, settings.getStepListenerBatchesPerJob());
        Assert.assertEquals(0.2f, settings.getBaumgarte(), 0f);
        Assert.assertEquals(
                0.02f, settings.getSpeculativeContactDistance(), 0f);
        Assert.assertEquals(0.02f, settings.getPenetrationSlop(), 0f);
        Assert.assertEquals(0.75f, settings.getLinearCastThreshold(), 0f);
        Assert.assertEquals(0.25f, settings.getLinearCastMaxPenetration(), 0f);
        Assert.assertEquals(1e-3f, settings.getManifoldTolerance(), 0f);
        Assert.assertEquals(0.2f, settings.getMaxPenetrationDistance(), 0f);
        Assert.assertEquals(0.001f * 0.001f,
                settings.getBodyPairCacheMaxDeltaPositionSq(), 0f);
        Assert.assertEquals(Jolt.cos(2 / 2f),
                settings.getBodyPairCacheCosMaxDeltaRotationDiv2(), 0f);
        Assert.assertEquals(Jolt.cos(5f),
                settings.getContactNormalCosMaxDeltaRotation(), 0f);
        Assert.assertEquals(0.01f * 0.01f,
                settings.getContactPointPreserveLambdaMaxDistSq(), 0f);
        Assert.assertEquals(10, settings.getNumVelocitySteps());
        Assert.assertEquals(2, settings.getNumPositionSteps());
        Assert.assertEquals(1f, settings.getMinVelocityForRestitution(), 0f);
        Assert.assertEquals(0.5f, settings.getTimeBeforeSleep(), 0f);
        Assert.assertEquals(
                0.03f, settings.getPointVelocitySleepThreshold(), 0f);
        Assert.assertTrue(settings.getDeterministicSimulation());
        Assert.assertTrue(settings.getConstraintWarmStart());
        Assert.assertTrue(settings.getUseBodyPairContactCache());
        Assert.assertTrue(settings.getUseManifoldReduction());
        Assert.assertTrue(settings.getUseLargeIslandSplitter());
        Assert.assertTrue(settings.getAllowSleeping());
        Assert.assertTrue(settings.getCheckActiveEdges());
    }

    /**
     * Test the getters and defaults of the specified {@code PhysicsSystem}.
     *
     * @param physicsSystem the settings to test (not null, unaffected)
     */
    private static void testGettersAndDefaults(PhysicsSystem physicsSystem) {
        Assert.assertNotNull(physicsSystem);
        Assert.assertTrue(physicsSystem.hasAssignedNativeObject());
        Assert.assertNotEquals(0L, physicsSystem.va());

        BodyIdVector idVector = new BodyIdVector();
        physicsSystem.getActiveBodies(EBodyType.RigidBody, idVector);
        Assert.assertEquals(0, idVector.size());
        Assert.assertTrue(idVector.empty());

        BodyIdVector idVector2 = new BodyIdVector();
        physicsSystem.getBodies(idVector2);
        Assert.assertEquals(0, idVector2.size());
        Assert.assertTrue(idVector2.empty());

        Assert.assertNull(physicsSystem.getBodyActivationListener());

        AaBox bounds = physicsSystem.getBounds();
        Assert.assertFalse(bounds.isValid());

        Assert.assertNotNull(physicsSystem.getCombineFriction());
        Assert.assertNotNull(physicsSystem.getCombineRestitution());
        Assert.assertNull(physicsSystem.getContactListener());

        TestUtils.assertEquals(0f, -9.81f, 0f, physicsSystem.getGravity(), 0f);
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

            TestUtils.testClose(settings1);
            TestUtils.testClose(settings);
        }
    }

    /**
     * Test the setters of the specified {@code PhysicsSettings}.
     *
     * @param settings the settings to test (not null, modified)
     */
    private static void testSetters(PhysicsSettings settings) {
        settings.setMaxInFlightBodyPairs(10);
        Assert.assertEquals(10, settings.getMaxInFlightBodyPairs());

        settings.setStepListenersBatchSize(12);
        Assert.assertEquals(12, settings.getStepListenersBatchSize());

        settings.setStepListenerBatchesPerJob(3);
        Assert.assertEquals(3, settings.getStepListenerBatchesPerJob());

        settings.setBaumgarte(0.11f);
        Assert.assertEquals(0.11f, settings.getBaumgarte(), 0f);

        settings.setSpeculativeContactDistance(1);
        Assert.assertEquals(1, settings.getSpeculativeContactDistance(), 0);

        settings.setPenetrationSlop(2);
        Assert.assertEquals(2, settings.getPenetrationSlop(), 0);

        settings.setLinearCastThreshold(1);
        Assert.assertEquals(1, settings.getLinearCastThreshold(), 0);

        settings.setLinearCastMaxPenetration(1);
        Assert.assertEquals(1, settings.getLinearCastMaxPenetration(), 0);

        settings.setManifoldTolerance(1);
        Assert.assertEquals(1, settings.getManifoldTolerance(), 0);

        settings.setMaxPenetrationDistance(1);
        Assert.assertEquals(1, settings.getMaxPenetrationDistance(), 0);

        settings.setBodyPairCacheMaxDeltaPositionSq(1);
        Assert.assertEquals(
                1, settings.getBodyPairCacheMaxDeltaPositionSq(), 0);

        settings.setBodyPairCacheCosMaxDeltaRotationDiv2(1);
        Assert.assertEquals(
                1, settings.getBodyPairCacheCosMaxDeltaRotationDiv2(), 0);

        settings.setContactNormalCosMaxDeltaRotation(1);
        Assert.assertEquals(
                1, settings.getContactNormalCosMaxDeltaRotation(), 0);

        settings.setContactPointPreserveLambdaMaxDistSq(1);
        Assert.assertEquals(
                1, settings.getContactPointPreserveLambdaMaxDistSq(), 0);

        settings.setNumVelocitySteps(12);
        Assert.assertEquals(12, settings.getNumVelocitySteps());

        settings.setNumPositionSteps(3);
        Assert.assertEquals(3, settings.getNumPositionSteps());

        settings.setMinVelocityForRestitution(10);
        Assert.assertEquals(10, settings.getMinVelocityForRestitution(), 0);

        settings.setTimeBeforeSleep(14f);
        Assert.assertEquals(14f, settings.getTimeBeforeSleep(), 0f);

        settings.setPointVelocitySleepThreshold(13f);
        Assert.assertEquals(13f, settings.getPointVelocitySleepThreshold(), 0f);

        settings.setDeterministicSimulation(false);
        Assert.assertFalse(settings.getDeterministicSimulation());

        settings.setConstraintWarmStart(false);
        Assert.assertFalse(settings.getConstraintWarmStart());

        settings.setUseBodyPairContactCache(false);
        Assert.assertFalse(settings.getUseBodyPairContactCache());

        settings.setUseManifoldReduction(false);
        Assert.assertFalse(settings.getUseManifoldReduction());

        settings.setUseLargeIslandSplitter(false);
        Assert.assertFalse(settings.getUseLargeIslandSplitter());

        settings.setAllowSleeping(false);
        Assert.assertFalse(settings.getAllowSleeping());

        settings.setCheckActiveEdges(false);
        Assert.assertFalse(settings.getCheckActiveEdges());
    }
}
