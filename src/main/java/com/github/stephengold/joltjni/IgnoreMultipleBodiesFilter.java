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
 * A {@code BodyFilter} that ignores specific bodies.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class IgnoreMultipleBodiesFilter extends BodyFilter {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default filter that selects all bodies.
     */
    public IgnoreMultipleBodiesFilter() {
        super(true);
        long filterVa = createDefault();
        setVirtualAddressAsOwner(filterVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Remove all bodies from the filter.
     */
    public void clear() {
        long filterVa = va();
        clear(filterVa);
    }

    /**
     * Reserve memory for the specified number of bodies.
     *
     * @param capacity the desired capacity (in bodies, &ge;0)
     */
    public void reserve(int capacity) {
        long filterVa = va();
        reserve(filterVa, capacity);
    }

    /**
     * Start ignoring the specified body.
     *
     * @param bodyId the ID of the body to ignore
     */
    public void ignoreBody(int bodyId) {
        long filterVa = va();
        ignoreBody(filterVa, bodyId);
    }
    // *************************************************************************
    // native private methods

    native private static void clear(long filterVa);

    native private static long createDefault();

    native private static void ignoreBody(long filterVa, int bodyId);

    native private static void reserve(long filterVa, int capacity);
}
