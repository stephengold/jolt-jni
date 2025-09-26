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

import com.github.stephengold.joltjni.BodyCreationSettings;
import com.github.stephengold.joltjni.BodyIdArray;
import com.github.stephengold.joltjni.BodyInterface;
import com.github.stephengold.joltjni.PhysicsSystem;
import com.github.stephengold.joltjni.Quat;
import com.github.stephengold.joltjni.RVec3;
import com.github.stephengold.joltjni.SphereShape;
import com.github.stephengold.joltjni.enumerate.EActivation;
import com.github.stephengold.joltjni.enumerate.EMotionType;
import org.junit.Assert;
import org.junit.Test;
import testjoltjni.TestUtils;

/**
 * Automated JUnit4 tests for batch removal and destruction operations in
 * {@code BodyInterface}.
 *
 * @author xI-Mx-Ix
 */
public class BodyRemovalTest {
    // *************************************************************************
    // new methods exposed

    /**
     * Test batch removal and destruction in {@code BodyInterface}.
     */
    @Test
    public void testBodyRemoval() {
        TestUtils.loadNativeLibrary();
        TestUtils.initializeNativeLibrary();

        PhysicsSystem physicsSystem = TestUtils.newPhysicsSystem(100);
        BodyInterface bodyInterface = physicsSystem.getBodyInterface();

        testRemoveAndDestroyBodies(bodyInterface);

        TestUtils.cleanupPhysicsSystem(physicsSystem);
        TestUtils.cleanup();
    }
    // *************************************************************************
    // private methods

    /**
     * A helper method to create a simple dynamic sphere and add it to the
     * world.
     *
     * @param bodyInterface the interface to use (not null)
     * @param position the initial position of the body (not null, unaffected)
     * @return the ID of the newly created body
     */
    private static int createAndAddBody(
            BodyInterface bodyInterface, RVec3 position) {
        int objectLayer = TestUtils.objLayerMoving;
        SphereShape shape = new SphereShape(0.5f);
        BodyCreationSettings settings = new BodyCreationSettings(
                shape, position, new Quat(),
                EMotionType.Dynamic, objectLayer);

        int bodyId = bodyInterface.createAndAddBody(
                settings, EActivation.Activate);

        TestUtils.testClose(settings, shape);
        return bodyId;
    }

    /**
     * Test the batch-remove and batch-destroy functionality of the specified
     * {@code BodyInterface}.
     *
     * @param bodyInterface the interface to test (not null)
     */
    private static void testRemoveAndDestroyBodies(
            BodyInterface bodyInterface) {
        final int numBodies = 3;
        BodyIdArray ids = new BodyIdArray(numBodies);

        // 1. Create and add some bodies.
        for (int i = 0; i < numBodies; ++i) {
            RVec3 pos = new RVec3(i * 2f, 10f, 0f);
            int bodyId = createAndAddBody(bodyInterface, pos);
            Assert.assertTrue(bodyInterface.isAdded(bodyId));
            ids.set(i, bodyId);
        }

        // --- Test RemoveBodies ---
        // 2. Remove all bodies in a single batch call.
        bodyInterface.removeBodies(ids);

        // 3. Verify they are all marked as 'not added'.
        for (int i = 0; i < numBodies; ++i) {
            Assert.assertFalse("Body should be removed",
                    bodyInterface.isAdded(ids.get(i)));
        }

        // 4. Re-add the bodies to prove they still exist and were not
        // destroyed.
        for (int i = 0; i < numBodies; ++i) {
            bodyInterface.addBody(ids.get(i), EActivation.Activate);
            Assert.assertTrue("Body should be re-added successfully",
                    bodyInterface.isAdded(ids.get(i)));
        }

        // --- Test DestroyBodies ---
        // 5. Must remove them again from the simulation before destroying.
        bodyInterface.removeBodies(ids);
        for (int i = 0; i < numBodies; ++i) {
            Assert.assertFalse("Body should be removed again before destroy",
                    bodyInterface.isAdded(ids.get(i)));
        }

        // 6. Destroy all bodies permanently in a single batch call.
        bodyInterface.destroyBodies(ids);

        // 7. Verify they cannot be re-added, as their IDs are now invalid.
        for (int i = 0; i < numBodies; ++i) {
            int bodyId = ids.get(i);
            // isAdded() is safe to call on invalid IDs; it returns false.
            Assert.assertFalse(bodyInterface.isAdded(bodyId));

            // Attempting to re-add an invalid ID should fail silently.
            bodyInterface.addBody(bodyId, EActivation.Activate);
            Assert.assertFalse("Body should not be added after being destroyed",
                    bodyInterface.isAdded(bodyId));
        }

        TestUtils.testClose(ids);
    }
}
