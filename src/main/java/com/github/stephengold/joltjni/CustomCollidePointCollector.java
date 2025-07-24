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
 * A customizable {@code CollidePointCollector}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
abstract public class CustomCollidePointCollector
        extends CollidePointCollector {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a customizable collector.
     */
    public CustomCollidePointCollector() {
        long collectorVa = createCustomCollector();
        setVirtualAddressAsOwner(collectorVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Callback invoked (by native code) each time a new collision is detected.
     *
     * @param resultVa the virtual address of a {@code CollidePointResult} (not
     * zero)
     */
    abstract public void addHit(long resultVa);
    // *************************************************************************
    // CollidePointCollector methods

    /**
     * Reset the collector so it can be reused. Meant to be overridden.
     */
    @Override
    public void reset() {
    }
    // *************************************************************************
    // native private methods

    native private long createCustomCollector();
}
