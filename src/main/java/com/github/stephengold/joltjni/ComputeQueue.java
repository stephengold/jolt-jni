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
package com.github.stephengold.joltjni;

import com.github.stephengold.joltjni.template.RefTarget;

/**
 * A command queue for performing calculations on a specific
 * {@code ComputeSystem}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class ComputeQueue extends NonCopyable implements RefTarget {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a queue with the specified native object assigned.
     *
     * @param queueVa the virtual address of the native object to assign (not
     * zero)
     */
    ComputeQueue(long queueVa) {
        long refVa = toRef(queueVa);
        Runnable freeingAction = () -> ComputeQueueRef.free(refVa);
        setVirtualAddress(queueVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Execute the command list and wait for it to finish.
     */
    public void executeAndWait() {
        long queueVa = va();
        executeAndWait(queueVa);
    }
    // *************************************************************************
    // RefTarget methods

    /**
     * Count the active references to the native {@code ComputeQueue}. The queue
     * is unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int getRefCount() {
        long queueVa = va();
        int result = getRefCount(queueVa);

        return result;
    }

    /**
     * Mark the native {@code ComputeQueue} as embedded.
     */
    @Override
    public void setEmbedded() {
        long queueVa = va();
        setEmbedded(queueVa);
    }

    /**
     * Create a counted reference to the native {@code ComputeQueue}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public ConstraintRef toRef() {
        long queueVa = va();
        long refVa = toRef(queueVa);
        ConstraintRef result = new ConstraintRef(refVa, true);

        return result;
    }
    // *************************************************************************
    // native methods

    native static void executeAndWait(long queueVa);

    native static int getRefCount(long queueVa);

    native private static void setEmbedded(long queueVa);

    native private static long toRef(long queueVa);
}
