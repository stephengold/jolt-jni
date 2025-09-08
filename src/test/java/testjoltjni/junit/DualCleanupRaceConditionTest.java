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

import com.github.stephengold.joltjni.JoltPhysicsObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import testjoltjni.TestUtils;

/**
 * A JUnit4 test to verify the thread-safe interaction between manual
 * {@code .close()} calls and the automatic {@code java.lang.ref.Cleaner}
 * service.
 * <p>
 * This test creates a race condition to prove that native resources are freed
 * exactly once, even under concurrent access from both cleanup mechanisms.
 * <p>
 * For this test to be effective, the automatic Cleaner must be active.
 *
 * @author xI-Mx-Ix
 */
public class DualCleanupRaceConditionTest {
    // *************************************************************************
    // constants

    /**
     * The number of objects to create for the stress test.
     */
    final private static int NUM_OBJECTS = 200_000;
    // *************************************************************************
    // new methods exposed

    /**
     * Cleans up Jolt's internal systems after all tests in this class have run.
     */
    @AfterClass
    public static void tearDownClass() {
        TestUtils.cleanup();
        System.out.println("Jolt resources cleaned up after test run.");
    }

    /**
     * Initializes the Jolt native library and the Cleaner service before any
     * tests in this class are run.
     */
    @BeforeClass
    public static void setUpClass() {
        TestUtils.loadNativeLibrary();
        TestUtils.initializeNativeLibrary();
    }

    /**
     * Tests the race condition between a manual .close() loop and the
     * background Cleaner service to ensure each resource is freed exactly once.
     *
     * @throws InterruptedException if the thread is interrupted while waiting
     */
    @Test
    public void testConcurrentCloseAndCleaner() throws InterruptedException {
        System.out.printf(
                "Starting race condition test with %d objects...%n",
                NUM_OBJECTS);

        // A thread-safe queue to hold the objects for the manual closer thread.
        ConcurrentLinkedQueue<OwningTestObject> queue
                = new ConcurrentLinkedQueue<>();

        // An atomic counter to track every execution of the cleanup action.
        AtomicInteger cleanupActionCount = new AtomicInteger(0);

        // A temporary list to hold strong references during creation.
        List<OwningTestObject> strongReferences = new ArrayList<>(NUM_OBJECTS);
        for (int i = 0; i < NUM_OBJECTS; ++i) {
            OwningTestObject obj = new OwningTestObject(cleanupActionCount);
            queue.add(obj);
            strongReferences.add(obj);
        }
        Assert.assertEquals("Queue should be full before starting",
                NUM_OBJECTS, queue.size());

        // This thread will manually call .close() on objects from the queue.
        Thread manualCloserThread = new Thread(() -> {
            while (!queue.isEmpty()) {
                OwningTestObject obj = queue.poll();
                if (obj != null) {
                    obj.close();
                }
            }
        });

        // --- THE RACE BEGINS ---
        manualCloserThread.start();

        // Release our strong references, making objects eligible for the
        // GC/Cleaner as soon as they are polled from the queue.
        strongReferences.clear();
        strongReferences = null;
        System.gc(); // Hint the GC to start looking for objects to clean.

        // Wait for the manual closer thread to finish its work.
        manualCloserThread.join();
        // --- THE RACE ENDS ---

        // Give the Cleaner time to process remaining objects.
        System.gc();
        Thread.sleep(1000);

        System.out.printf("Total cleanup actions executed: %d%n",
                cleanupActionCount.get());

        // --- VERIFICATION ---
        Assert.assertTrue("The queue of objects to close should be empty",
                queue.isEmpty());

        // This is the crucial assertion. If the count is not exactly
        // NUM_OBJECTS, it means the "run-once" guarantee was violated.
        Assert.assertEquals(
                "The cleanup action must be executed exactly once per object",
                NUM_OBJECTS, cleanupActionCount.get());
    }
    // *************************************************************************
    // private methods

    /**
     * A simple JoltPhysicsObject that owns a simulated native resource. Its
     * cleanup action increments a shared atomic counter to verify it runs
     * exactly once.
     */
    private static class OwningTestObject extends JoltPhysicsObject {
        /**
         * A shared counter to generate unique virtual addresses for mock
         * objects.
         */
        final private static AtomicLong nextVirtualAddress = new AtomicLong(1L);

        /**
         * Instantiate an object that owns a mock native resource.
         *
         * @param actionCounter the atomic counter to increment upon cleanup
         */
        OwningTestObject(AtomicInteger actionCounter) {
            long virtualAddress = nextVirtualAddress.getAndIncrement();

            // The runnable passed here is the "freeing action". It will be
            // executed either by .close() or by the Cleaner.
            Runnable cleanupAction = actionCounter::incrementAndGet;

            // Register the object and its cleanup action.
            setVirtualAddress(virtualAddress, cleanupAction);
        }
    }
}
