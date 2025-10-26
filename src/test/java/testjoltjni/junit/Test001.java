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

import com.github.stephengold.joltjni.Body;
import com.github.stephengold.joltjni.BodyActivationListener;
import com.github.stephengold.joltjni.BodyCreationSettings;
import com.github.stephengold.joltjni.BodyInterface;
import com.github.stephengold.joltjni.BoxShapeSettings;
import com.github.stephengold.joltjni.BroadPhaseLayerInterface;
import com.github.stephengold.joltjni.BroadPhaseLayerInterfaceTable;
import com.github.stephengold.joltjni.CollideShapeResult;
import com.github.stephengold.joltjni.ContactListener;
import com.github.stephengold.joltjni.ContactManifold;
import com.github.stephengold.joltjni.ContactSettings;
import com.github.stephengold.joltjni.CustomBodyActivationListener;
import com.github.stephengold.joltjni.CustomContactListener;
import com.github.stephengold.joltjni.JobSystem;
import com.github.stephengold.joltjni.JobSystemThreadPool;
import com.github.stephengold.joltjni.Jolt;
import com.github.stephengold.joltjni.ObjVsBpFilter;
import com.github.stephengold.joltjni.ObjectLayerPairFilter;
import com.github.stephengold.joltjni.ObjectLayerPairFilterTable;
import com.github.stephengold.joltjni.PhysicsSystem;
import com.github.stephengold.joltjni.Quat;
import com.github.stephengold.joltjni.RVec3;
import com.github.stephengold.joltjni.Shape;
import com.github.stephengold.joltjni.ShapeRefC;
import com.github.stephengold.joltjni.ShapeResult;
import com.github.stephengold.joltjni.SphereShape;
import com.github.stephengold.joltjni.SubShapeIdPair;
import com.github.stephengold.joltjni.TempAllocator;
import com.github.stephengold.joltjni.TempAllocatorImpl;
import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.enumerate.EActivation;
import com.github.stephengold.joltjni.enumerate.EMotionType;
import com.github.stephengold.joltjni.enumerate.EPhysicsUpdateError;
import com.github.stephengold.joltjni.enumerate.EShapeSubType;
import com.github.stephengold.joltjni.enumerate.EShapeType;
import com.github.stephengold.joltjni.enumerate.ValidateResult;
import com.github.stephengold.joltjni.readonly.ConstBody;
import com.github.stephengold.joltjni.readonly.ConstContactManifold;
import com.github.stephengold.joltjni.readonly.ConstSubShapeIdPair;
import org.junit.Assert;
import org.junit.Test;
import testjoltjni.TestUtils;

/**
 * An automated JUnit4 test based on HelloWorld.
 * <p>
 * Derived from HelloWorld.cpp by Jorrit Rouwe.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Test001 {
    // *************************************************************************
    // inner classes

    /**
     * An example of a custom activation listener.
     */
    final private static class TestBodyActivationListener
            extends CustomBodyActivationListener {
        /**
         * Callback invoked (by native code) each time a body is activated.
         *
         * @param bodyId the body's ID
         * @param bodyUserData the body's user data
         */
        @Override
        public void onBodyActivated(int bodyId, long bodyUserData) {
            System.out.println("A body got activated");
        }

        /**
         * Callback invoked (by native code) each time a body is deactivated.
         *
         * @param bodyId the body's ID
         * @param bodyUserData the body's user data
         */
        @Override
        public void onBodyDeactivated(int bodyId, long bodyUserData) {
            System.out.println("A body went to sleep");
        }
    }

    /**
     * An example of a custom contact listener.
     */
    final private static class TestContactListener
            extends CustomContactListener {
        /**
         * Callback invoked (by native code) each time a new contact point is
         * detected.
         *
         * @param body1Va the virtual address of the first body in contact (not
         * zero)
         * @param body2Va the virtual address of the 2nd body in contact (not
         * zero)
         * @param manifoldVa the virtual address of the contact manifold (not
         * zero)
         * @param settingsVa the virtual address of the contact settings (not
         * zero)
         */
        @Override
        public void onContactAdded(
                long body1Va, long body2Va, long manifoldVa, long settingsVa) {
            Assert.assertNotEquals(body1Va, body2Va);
            ConstContactManifold manifold = new ContactManifold(manifoldVa);
            float depth = manifold.getPenetrationDepth();
            ContactSettings settings = new ContactSettings(settingsVa);
            System.out.println("A contact was added, combinedFriction = "
                    + settings.getCombinedFriction() + " depth = " + depth);
        }

        /**
         * Callback invoked (by native code) each time a contact is detected
         * that was also detected during the previous update.
         *
         * @param body1Va the virtual address of the first body in contact (not
         * zero)
         * @param body2Va the virtual address of the 2nd body in contact (not
         * zero)
         * @param manifoldVa the virtual address of the contact manifold (not
         * zero)
         * @param settingsVa the virtual address of the contact settings (not
         * zero)
         */
        @Override
        public void onContactPersisted(
                long body1Va, long body2Va, long manifoldVa, long settingsVa) {
            System.out.println("A contact was persisted");
        }

        /**
         * Callback invoked (by native code) each time a contact that was
         * detected during the previous update is no longer detected.
         *
         * @param pairVa the virtual address of the {@code SubShapeIdPair} (not
         * zero)
         */
        @Override
        public void onContactRemoved(long pairVa) {
            ConstSubShapeIdPair pair = new SubShapeIdPair(pairVa);
            int bodyId1 = pair.getBody1Id();
            int bodyId2 = pair.getBody2Id();
            Assert.assertNotEquals(bodyId1, bodyId2);
            TestUtils.testClose(pair);

            System.out.println("A contact was removed");
        }

        /**
         * Callback invoked (by native code) after detecting collision between a
         * pair of bodies, but before invoking {@code onContactAdded()} and
         * before adding the contact constraint.
         *
         * @param body1Va the virtual address of the first body in contact (not
         * zero)
         * @param body2Va the virtual address of the 2nd body in contact (not
         * zero)
         * @param offsetX the X component of the base offset
         * @param offsetY the Y component of the base offset
         * @param offsetZ the Z component of the base offset
         * @param collisionResultVa the virtual address of the
         * {@code CollideShapeResult} (not zero)
         * @return how to the contact should be processed (an ordinal of
         * {@code ValidateResult})
         */
        @Override
        public int onContactValidate(long body1Va, long body2Va, double offsetX,
                double offsetY, double offsetZ, long collisionResultVa) {
            CollideShapeResult result
                    = new CollideShapeResult(collisionResultVa);
            System.out.println("Contact validate callback, depth = "
                    + result.getPenetrationDepth());

            return ValidateResult.AcceptAllContactsForThisBodyPair.ordinal();
        }
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Drop a dynamic spherical ball onto a static box.
     */
    @Test
    public void test001() {
        TestUtils.loadNativeLibrary();
        TestUtils.initializeNativeLibrary();

        // object layers:
        final int objLayerNonMoving = 0;
        final int objLayerMoving = 1;
        final int numObjLayers = 2;

        // broadphase layers:
        final int bpLayerNonMoving = 0;
        final int bpLayerMoving = 1;
        final int numBpLayers = 2;

        BroadPhaseLayerInterface mapObj2Bp
                = new BroadPhaseLayerInterfaceTable(numObjLayers, numBpLayers)
                        .mapObjectToBroadPhaseLayer(
                                objLayerNonMoving, bpLayerNonMoving)
                        .mapObjectToBroadPhaseLayer(
                                objLayerMoving, bpLayerMoving);

        ObjVsBpFilter objVsBpFilter
                = new ObjVsBpFilter(numObjLayers, numBpLayers)
                        .disablePair(objLayerNonMoving, bpLayerNonMoving);

        ObjectLayerPairFilter objVsObjFilter
                = new ObjectLayerPairFilterTable(numObjLayers)
                        .enableCollision(objLayerMoving, objLayerMoving)
                        .enableCollision(objLayerMoving, objLayerNonMoving);

        final int maxBodies = 4;
        final int numBodyMutexes = 0; // 0 means "use the default value"
        final int maxBodyPairs = 4;
        final int maxContacts = 4;
        PhysicsSystem physicsSystem = new PhysicsSystem();
        physicsSystem.init(maxBodies, numBodyMutexes, maxBodyPairs, maxContacts,
                mapObj2Bp, objVsBpFilter, objVsObjFilter);

        ContactListener contactListener = new TestContactListener();
        physicsSystem.setContactListener(contactListener);

        BodyActivationListener activationListener
                = new TestBodyActivationListener();
        physicsSystem.setBodyActivationListener(activationListener);

        BoxShapeSettings floorShapeSettings
                = new BoxShapeSettings(100f, 1f, 100f);
        ShapeResult floorShapeResult = floorShapeSettings.create();
        Assert.assertFalse(floorShapeResult.hasError());
        Assert.assertTrue(floorShapeResult.isValid());

        ShapeRefC floorShapeRef = floorShapeResult.get();
        TestUtils.testClose(floorShapeResult, floorShapeSettings);
        RVec3 floorLocation = new RVec3(0.0, -1.0, 0.0);
        Quat orientation = new Quat();
        BodyCreationSettings floorBodySettings
                = new BodyCreationSettings(floorShapeRef, floorLocation,
                        orientation, EMotionType.Static, objLayerNonMoving);
        BodyInterface bodyInterface = physicsSystem.getBodyInterface();
        Body floor = bodyInterface.createBody(floorBodySettings);
        Assert.assertFalse(floor.ownsNativeObject());
        int floorId = floor.getId();
        Assert.assertNotEquals(Jolt.cInvalidBodyId, floorId);
        bodyInterface.addBody(floorId, EActivation.DontActivate);

        Shape ballShape = new SphereShape(0.5f);
        Assert.assertEquals(1, ballShape.getRefCount());
        Assert.assertEquals(EShapeSubType.Sphere, ballShape.getSubType());
        Assert.assertEquals(EShapeType.Convex, ballShape.getType());

        RVec3 ballLocation = new RVec3(0.0, 2.0, 0.0);
        BodyCreationSettings ballSettings = new BodyCreationSettings(
                ballShape, ballLocation, orientation,
                EMotionType.Dynamic, objLayerMoving);
        int ballId = bodyInterface.createAndAddBody(
                ballSettings, EActivation.Activate);
        Assert.assertNotEquals(Jolt.cInvalidBodyId, ballId);
        Vec3 ballVelocity = new Vec3(0f, -5f, 0f);
        bodyInterface.setLinearVelocity(ballId, ballVelocity);

        physicsSystem.optimizeBroadPhase();

        final int numBytes = 1 << 18; // 256 KiB
        TempAllocator tempAllocator = new TempAllocatorImpl(numBytes);

        int numThreads = TestUtils.numThreads();
        JobSystem jobSystem = new JobSystemThreadPool(
                Jolt.cMaxPhysicsJobs, Jolt.cMaxPhysicsBarriers, numThreads);

        int stepCounter = 0;
        while (bodyInterface.isActive(ballId) && stepCounter < 99) {
            final float deltaTime = 1 / 60f;
            final int numCollisionSteps = 1;
            int errors = physicsSystem.update(
                    deltaTime, numCollisionSteps, tempAllocator, jobSystem);
            Assert.assertEquals(EPhysicsUpdateError.None, errors);

            ++stepCounter;
        }
        Assert.assertEquals(48, stepCounter);
        TestUtils.testClose(
                jobSystem, tempAllocator, contactListener, activationListener);

        ballLocation = bodyInterface.getCenterOfMassPosition(ballId);
        TestUtils.assertEquals(0f, 0.48f, 0f, ballLocation, 1e-5f);

        ballVelocity = bodyInterface.getLinearVelocity(ballId);
        TestUtils.assertEquals(0f, 0f, 0f, ballVelocity, 1e-5f);

        bodyInterface.removeBody(ballId);
        Assert.assertEquals(3, ballShape.getRefCount());
        bodyInterface.destroyBody(ballId);
        Assert.assertEquals(2, ballShape.getRefCount());
        TestUtils.testClose(ballSettings, ballShape);

        bodyInterface.removeBody(floorId);
        bodyInterface.destroyBody(floorId);
        TestUtils.testClose(floorBodySettings, floorShapeRef, floorShapeResult,
                physicsSystem, objVsObjFilter, objVsBpFilter, mapObj2Bp);

        TestUtils.cleanup();
    }
}
