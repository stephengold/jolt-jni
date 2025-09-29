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

import com.github.stephengold.joltjni.CustomPhysicsStepListener;
import com.github.stephengold.joltjni.JobSystemThreadPool;
import com.github.stephengold.joltjni.Jolt;
import com.github.stephengold.joltjni.PhysicsStepListener;
import com.github.stephengold.joltjni.PhysicsSystem;
import com.github.stephengold.joltjni.TempAllocator;
import com.github.stephengold.joltjni.TempAllocatorMalloc;
import com.github.stephengold.joltjni.enumerate.EPhysicsUpdateError;
import org.junit.Assert;
import org.junit.Test;
import testjoltjni.TestUtils;

/**
 * Verify that JoltPhysics issue #1545 is solved.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Test010 {
    // *************************************************************************
    // fields

    /**
     * keep track of whether the step listener has been invoked
     */
    private static boolean invoked;
    // *************************************************************************
    // new methods exposed

    /**
     * Test for JoltPhysics issue #1545: step listeners are not invoked when no
     * bodies are active.
     */
    @Test
    public void test010() {
        TestUtils.loadNativeLibrary();
        TestUtils.initializeNativeLibrary();

        // Configure a physics system with a simple step listener:
        PhysicsSystem physicsSystem = TestUtils.newPhysicsSystem(2);
        PhysicsStepListener listener = new CustomPhysicsStepListener() {
            @Override
            public void onStep(long contextVa) {
                invoked = true;
            }
        };
        physicsSystem.addStepListener(listener);

        int numThreads = 1;
        TempAllocator allocator = new TempAllocatorMalloc();
        JobSystemThreadPool jobSystem = new JobSystemThreadPool(
                Jolt.cMaxPhysicsJobs, Jolt.cMaxPhysicsBarriers, numThreads);

        invoked = false;

        // Update the physics system:
        float deltaTime = 0.01f;
        int numSteps = 1;
        int errors = physicsSystem.update(
                deltaTime, numSteps, allocator, jobSystem);
        assert errors == EPhysicsUpdateError.None : errors;

        Assert.assertTrue(invoked);

        TestUtils.testClose(jobSystem, allocator, listener);
        TestUtils.cleanupPhysicsSystem(physicsSystem);
        TestUtils.cleanup();
    }
}
