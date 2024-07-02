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
package com.github.stephengold.joltjni;

/**
 * A sample implementation of {@code JobSystem} using a pool of worker threads.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class JobSystemThreadPool extends JobSystem {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a job system with the specified limits.
     *
     * @param maxJobs the maximum number of jobs that can be allocated at one
     * time
     * @param maxBarriers the maximum number of barriers that can be allocated
     * at one time
     *
     * @see com.github.stephengold.joltjni.Jolt#cMaxPhysicsBarriers
     * @see com.github.stephengold.joltjni.Jolt#cMaxPhysicsJobs
     */
    public JobSystemThreadPool(int maxJobs, int maxBarriers) {
        this(maxJobs, maxBarriers, -1);
    }

    /**
     * Instantiate a job system with the specified limits.
     *
     * @param maxJobs the maximum number of jobs that can be allocated at one
     * time
     * @param maxBarriers the maximum number of barriers that can be allocated
     * at one time
     * @param numThreads the number of worker threads to start (&ge;1) or -1 to
     * auto detect
     */
    public JobSystemThreadPool(int maxJobs, int maxBarriers, int numThreads) {
        long systemVa = createJobSystem(maxJobs, maxBarriers, numThreads);
        setVirtualAddress(systemVa, true);
    }
    // *************************************************************************
    // native private methods

    native private static long createJobSystem(
            int maxJobs, int maxBarriers, int numThreads);
}
