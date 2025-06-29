/*
Copyright (c) 2024-2025 Stephen Gold

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
 * A last-in/first-out implementation of {@code TempAllocator}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class TempAllocatorImpl extends TempAllocator {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a LIFO allocator with the specified capacity.
     *
     * @param numBytes the desired capacity (in bytes)
     */
    public TempAllocatorImpl(int numBytes) {
        long allocatorVa = create(numBytes);
        setVirtualAddressAsOwner(allocatorVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the total capacity of the allocator.
     *
     * @return the capacity in bytes
     */
    public int getSize() {
        long allocatorVa = va();
        int result = getSize(allocatorVa);

        return result;
    }

    /**
     * Return the amount of memory currently allocated.
     *
     * @return the number of bytes used (&ge;0)
     */
    public int getUsage() {
        long allocatorVa = va();
        int result = getUsage(allocatorVa);

        return result;
    }

    /**
     * Test whether any memory is currently allocated.
     *
     * @return {@code true} if no memory is allocated, or {@code false} if
     * memory is allocated
     */
    public boolean isEmpty() {
        long allocatorVa = va();
        boolean result = isEmpty(allocatorVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long create(int numBytes);

    native private static int getSize(long allocatorVa);

    native private static int getUsage(long allocatorVa);

    native private static boolean isEmpty(long allocatorVa);
}
