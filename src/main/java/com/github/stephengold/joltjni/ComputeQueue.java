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
 * A command queue for performing calculations on a GPU.
 *
 * @author Stephen Gold sgold@sonic.net
 */
abstract public class ComputeQueue extends NonCopyable implements RefTarget {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a queue with no native object assigned.
     */
    ComputeQueue() {
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

    /**
     * Instantiate a queue from its virtual address.
     *
     * @param queueVa the virtual address of the native object, or zero
     * @return a new JVM object, or {@code null} if the argument was zero
     */
    public static ComputeQueue newQueue(long queueVa) {
        if (queueVa == 0L) {
            return null;
        }
        long rttiVa = SerializableObject.getRtti(queueVa);
        String typeName = Rtti.getName(rttiVa);
        ComputeQueue result;
        switch (typeName) {
            case "ComputeQueueCPU":
            case "ComputeQueueDX12":
            case "ComputeQueueMTL":
            case "ComputeQueueVK":
                result = null; // TODO
                break;

            default:
                throw new RuntimeException("typeName = " + typeName);
        }

        return result;
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
    // *************************************************************************
    // native methods

    native static void executeAndWait(long queueVa);

    native static int getRefCount(long queueVa);

    native private static void setEmbedded(long queueVa);
}
