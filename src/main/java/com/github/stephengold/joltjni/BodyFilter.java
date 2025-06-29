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
 * Determine which bodies are candidates for a collision test.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class BodyFilter extends NonCopyable {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default filter that selects all layers.
     */
    public BodyFilter() {
        long filterVa = createDefaultFilter();
        setVirtualAddressAsOwner(filterVa);
    }

    /**
     * Instantiate a filter with no native object assigned.
     *
     * @param dummy unused argument to distinguish from the zero-arg constructor
     */
    BodyFilter(boolean dummy) {
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Test whether the specified body is a candidate for collisions. The filter
     * is unaffected.
     *
     * @param bodyId the body to test (not null, unaffected)
     * @return {@code true} if may collide, {@code false} if filtered out
     */
    public boolean shouldCollide(int bodyId) {
        long filterVa = va();
        boolean result = shouldCollide(filterVa, bodyId);

        return result;
    }
    // *************************************************************************
    // new protected methods

    /**
     * Assign a native object (assuming there's none already assigned) and
     * designate the JVM object as the owner.
     *
     * @param collectorVa the virtual address of the native object to assign
     * (not zero)
     */
    final void setVirtualAddressAsOwner(long collectorVa) {
        Runnable freeingAction = () -> free(collectorVa);
        setVirtualAddress(collectorVa, freeingAction);
    }
    // *************************************************************************
    // native private methods

    native private static long createDefaultFilter();

    native private static void free(long filterVa);

    native private static boolean shouldCollide(long filterVa, int bodyId);
}
