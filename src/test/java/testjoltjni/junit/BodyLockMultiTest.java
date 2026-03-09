/*
Copyright (c) 2026 Stephen Gold

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
import com.github.stephengold.joltjni.BodyInterface;
import com.github.stephengold.joltjni.BodyLockMultiBase;
import com.github.stephengold.joltjni.BodyLockMultiRead;
import com.github.stephengold.joltjni.BodyLockMultiWrite;
import com.github.stephengold.joltjni.PhysicsSystem;
import com.github.stephengold.joltjni.Quat;
import com.github.stephengold.joltjni.RVec3;
import com.github.stephengold.joltjni.SphereShape;
import com.github.stephengold.joltjni.enumerate.EActivation;
import com.github.stephengold.joltjni.enumerate.EMotionType;
import com.github.stephengold.joltjni.readonly.ConstBody;
import com.github.stephengold.joltjni.readonly.ConstBodyCreationSettings;
import com.github.stephengold.joltjni.readonly.ConstBodyLockInterface;
import org.junit.Test;
import testjoltjni.TestUtils;

/**
 * Automated JUnit4 tests for {@code BodyLockMultiRead} and
 * {@code BodyLockMultiWrite}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class BodyLockMultiTest {
    // *************************************************************************
    // new methods exposed

    /**
     * Test {@code BodyLockMultiRead} and {@code BodyLockMultiWrite}.
     */
    @Test
    public void testBodyLockMulti() {
        TestUtils.loadNativeLibrary();
        TestUtils.initializeNativeLibrary();

        // Create 20 bodies and collect the IDs of 10 of them:
        int numIds = 10;
        int totalBodies = 2 * numIds;
        PhysicsSystem system = TestUtils.newPhysicsSystem(totalBodies);
        int[] ids = new int[numIds];
        SphereShape shape = new SphereShape(3f);
        BodyInterface bi = system.getBodyInterface();
        ConstBodyCreationSettings settings
                = new BodyCreationSettings(shape, new RVec3(), new Quat(),
                        EMotionType.Dynamic, TestUtils.objLayerMoving);
        for (int i = 0; i < totalBodies; ++i) {
            int bodyId = bi.createAndAddBody(settings, EActivation.Activate);
            if ((i % 2) == 0) {
                ids[i / 2] = bodyId;
            }
        }

        ConstBodyLockInterface lock = system.getBodyLockInterface();
        ConstBodyLockInterface noLock = system.getBodyLockInterfaceNoLock();

        {
            BodyLockMultiRead lockRead = new BodyLockMultiRead(lock, ids);
            testLockMulti(lockRead);
            TestUtils.testClose(lockRead.getBodyIdArray(), lockRead);
        }
        {
            BodyLockMultiRead noLockRead = new BodyLockMultiRead(noLock, ids);
            testLockMulti(noLockRead);
            TestUtils.testClose(noLockRead.getBodyIdArray(), noLockRead);
        }
        {
            BodyLockMultiWrite lockWrite = new BodyLockMultiWrite(lock, ids);
            testLockMulti(lockWrite);
            TestUtils.testClose(lockWrite.getBodyIdArray(), lockWrite);
        }
        {
            BodyLockMultiWrite noLockWrite
                    = new BodyLockMultiWrite(noLock, ids);
            testLockMulti(noLockWrite);
            TestUtils.testClose(noLockWrite.getBodyIdArray(), noLockWrite);
        }

        TestUtils.testClose(noLock, lock, settings, shape);
        TestUtils.cleanupPhysicsSystem(system);
        TestUtils.cleanup();
    }
    // *************************************************************************
    // private methods

    /**
     * Access each body held by the specified object.
     *
     * @param lockMulti the object to test (not {@code null})
     */
    private void testLockMulti(BodyLockMultiBase lockMulti) {
        int numIds = lockMulti.getNumBodies();
        for (int index = 0; index < numIds; ++index) {
            ConstBody body = lockMulti.getBody(index);
            body.getAngularVelocity();
        }

        lockMulti.releaseLocks();
    }
}
