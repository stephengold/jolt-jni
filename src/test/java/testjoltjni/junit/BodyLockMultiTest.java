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

import com.github.stephengold.joltjni.Body;
import com.github.stephengold.joltjni.BodyCreationSettings;
import com.github.stephengold.joltjni.BodyIdArray;
import com.github.stephengold.joltjni.BodyInterface;
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
import org.junit.Assert;
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
    // fields

    /**
     * alternate which bodies are sensors on each invocation of
     * {@code testLockMulti()}
     */
    private static boolean evenSensors;
    // *************************************************************************
    // new methods exposed

    /**
     * Test {@code BodyLockMultiRead} and {@code BodyLockMultiWrite}.
     */
    @Test
    public void testBodyLockMulti() {
        TestUtils.loadNativeLibrary();
        TestUtils.initializeNativeLibrary();

        // Create 20 bodies and collect the even and odd IDs:
        int numIds = 10;
        int totalBodies = 2 * numIds;
        PhysicsSystem system = TestUtils.newPhysicsSystem(totalBodies);
        int[] evens = new int[numIds];
        int[] odds = new int[numIds];
        SphereShape shape = new SphereShape(3f);
        BodyInterface bi = system.getBodyInterface();
        ConstBodyCreationSettings settings
                = new BodyCreationSettings(shape, new RVec3(), new Quat(),
                        EMotionType.Dynamic, TestUtils.objLayerMoving);
        for (int i = 0; i < totalBodies; ++i) {
            int bodyId = bi.createAndAddBody(settings, EActivation.Activate);
            if ((i % 2) == 0) {
                evens[i / 2] = bodyId;
            } else {
                odds[i / 2] = bodyId;
            }
        }

        ConstBodyLockInterface lock = system.getBodyLockInterface();
        testLockMulti(lock, evens, odds);

        ConstBodyLockInterface noLock = system.getBodyLockInterfaceNoLock();
        testLockMulti(noLock, evens, odds);

        TestUtils.testClose(noLock, lock, settings, shape);
        TestUtils.cleanupPhysicsSystem(system);
        TestUtils.cleanup();
    }
    // *************************************************************************
    // private methods

    /**
     * Read and write the two groups of bodies using the specified locking
     * interface.
     *
     * @param bli the lock interface to use (not {@code null})
     * @param evens the even body IDs (not {@code null}, unaffected)
     * @param odds the odd body IDs (not {@code null}, unaffected)
     */
    private void testLockMulti(
            ConstBodyLockInterface bli, int[] evens, int[] odds) {
        {
            BodyLockMultiWrite multi = new BodyLockMultiWrite(bli, evens);
            for (Body body : multi.getBodies()) {
                body.setIsSensor(evenSensors);
            }
            multi.releaseLocks();
            TestUtils.testClose(multi.getBodyIdArray(), multi);
        }
        boolean oddSensors = !evenSensors;
        {
            BodyIdArray idArray = new BodyIdArray(odds);
            BodyLockMultiWrite multi = new BodyLockMultiWrite(bli, idArray);
            for (Body body : multi.getBodies()) {
                body.setIsSensor(oddSensors);
            }
            multi.releaseLocks();
            TestUtils.testClose(multi, idArray);
        }
        {
            BodyIdArray idArray = new BodyIdArray(evens);
            BodyLockMultiRead multi = new BodyLockMultiRead(bli, idArray);
            for (ConstBody body : multi.getBodies()) {
                Assert.assertEquals(evenSensors, body.isSensor());
            }
            multi.releaseLocks();
            TestUtils.testClose(multi, idArray);
        }
        {
            BodyLockMultiRead multi = new BodyLockMultiRead(bli, odds);
            for (ConstBody body : multi.getBodies()) {
                Assert.assertEquals(oddSensors, body.isSensor());
            }
            multi.releaseLocks();
            TestUtils.testClose(multi.getBodyIdArray(), multi);
        }
        evenSensors = !evenSensors;
    }
}
