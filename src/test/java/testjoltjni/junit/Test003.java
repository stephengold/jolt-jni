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

import com.github.stephengold.joltjni.JobSystem;
import com.github.stephengold.joltjni.JobSystemSingleThreaded;
import com.github.stephengold.joltjni.JobSystemThreadPool;
import com.github.stephengold.joltjni.Jolt;
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

        // JobSystemSingleThreaded:
        {
            final int maxJobs = 4_096;
            JobSystem jobSystem = new JobSystemSingleThreaded(maxJobs);

            Assert.assertEquals(1, jobSystem.getMaxConcurrency());
            Assert.assertTrue(jobSystem.hasAssignedNativeObject());
            Assert.assertTrue(jobSystem.ownsNativeObject());
            Assert.assertNotEquals(0L, jobSystem.va());
            jobSystem.close();
        }

        // JobSystemThreadPool:
        {
            final int maxJobs = 4_096;
            final int maxBarriers = 4;
            JobSystem jobSystem
                    = new JobSystemThreadPool(maxJobs, maxBarriers);

            int numCpus = Runtime.getRuntime().availableProcessors();
            Assert.assertEquals(numCpus, jobSystem.getMaxConcurrency());

            Assert.assertTrue(jobSystem.hasAssignedNativeObject());
            Assert.assertTrue(jobSystem.ownsNativeObject());
            Assert.assertNotEquals(0L, jobSystem.va());
            jobSystem.close();
        }
    }
}
