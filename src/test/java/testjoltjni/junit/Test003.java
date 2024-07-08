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

import com.github.stephengold.joltjni.AaBox;
import com.github.stephengold.joltjni.JobSystem;
import com.github.stephengold.joltjni.JobSystemSingleThreaded;
import com.github.stephengold.joltjni.JobSystemThreadPool;
import com.github.stephengold.joltjni.Jolt;
import com.github.stephengold.joltjni.JoltPhysicsObject;
import com.github.stephengold.joltjni.TempAllocator;
import com.github.stephengold.joltjni.TempAllocatorImpl;
import com.github.stephengold.joltjni.TempAllocatorMalloc;
import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.Vec3Arg;
import org.junit.Assert;
import org.junit.Test;
import testjoltjni.Utils;

/**
 * An automated JUnit4 test for jolt-jni object creation, destruction, and
 * defaults.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Test003 {
    // *************************************************************************
    // new methods exposed

    /**
     * Test jolt-jni object creation, destruction, and defaults.
     */
    @Test
    public void test003() {
        Utils.loadNativeLibrary();

        Jolt.registerDefaultAllocator();
        Jolt.installDefaultTraceCallback();
        Jolt.installDefaultAssertCallback();
        Jolt.newFactory();
        Jolt.registerTypes();

        // AaBox:
        {
            AaBox box1 = new AaBox();
            Assert.assertTrue(box1.hasAssignedNativeObject());
            Assert.assertTrue(box1.ownsNativeObject());
            Assert.assertNotEquals(0L, box1.va());
            box1.close();

            Vec3Arg max = new Vec3(1f, 2f, 3f);
            Vec3Arg min = new Vec3(4f, 5f, 6f);
            AaBox box2 = new AaBox(min, max);
            Assert.assertTrue(box2.hasAssignedNativeObject());
            Assert.assertTrue(box2.ownsNativeObject());
            Assert.assertNotEquals(0L, box2.va());
            box2.close();
        }

        // JobSystemSingleThreaded:
        {
            JobSystem jobSystem
                    = new JobSystemSingleThreaded(Jolt.cMaxPhysicsJobs);

            Assert.assertEquals(1, jobSystem.getMaxConcurrency());
            Assert.assertTrue(jobSystem.hasAssignedNativeObject());
            Assert.assertTrue(jobSystem.ownsNativeObject());
            Assert.assertNotEquals(0L, jobSystem.va());

            testClose(jobSystem);
        }

        // JobSystemThreadPool:
        {
            JobSystemThreadPool jobSystem = new JobSystemThreadPool(
                    Jolt.cMaxPhysicsJobs, Jolt.cMaxPhysicsBarriers);

            int numCpus = Runtime.getRuntime().availableProcessors();
            Assert.assertEquals(numCpus, jobSystem.getMaxConcurrency());

            Assert.assertTrue(jobSystem.hasAssignedNativeObject());
            Assert.assertTrue(jobSystem.ownsNativeObject());
            Assert.assertNotEquals(0L, jobSystem.va());

            jobSystem.setNumThreads(3);
            Assert.assertEquals(4, jobSystem.getMaxConcurrency());

            testClose(jobSystem);
        }

        // TempAllocatorImpl:
        {
            int numBytes = 1 << 8;
            TempAllocator tempAllocator = new TempAllocatorImpl(numBytes);

            Assert.assertTrue(tempAllocator.hasAssignedNativeObject());
            Assert.assertTrue(tempAllocator.ownsNativeObject());
            Assert.assertNotEquals(0L, tempAllocator.va());

            testClose(tempAllocator);
        }

        // TempAllocatorMalloc:
        {
            TempAllocator tempAllocator = new TempAllocatorMalloc();

            Assert.assertTrue(tempAllocator.hasAssignedNativeObject());
            Assert.assertTrue(tempAllocator.ownsNativeObject());
            Assert.assertNotEquals(0L, tempAllocator.va());

            testClose(tempAllocator);
        }
    }
    // *************************************************************************
    // Java private methods

    /**
     * Test the {@code close()} method of the specified object.
     *
     * @param object the object to test (not null)
     */
    private static void testClose(JoltPhysicsObject object) {
        object.close();
        Assert.assertFalse(object.hasAssignedNativeObject());
        Assert.assertFalse(object.ownsNativeObject());
    }
}
