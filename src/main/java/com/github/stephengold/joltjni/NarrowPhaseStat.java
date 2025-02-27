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
 * Gather and report narrow-phase performance statistics.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class NarrowPhaseStat extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * A private constructor to inhibit instantiation of this class.
     */
    private NarrowPhaseStat() {
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Test whether the native library collects narrow-phase statistics. (native
     * macro: JPH_TRACK_NARROWPHASE_STATS)
     *
     * @return {@code true} if collecting statistics, otherwise {@code false}
     */
    native public static boolean isCollecting();

    /**
     * Generate a report using the Jolt Physics {@code Trace} function.
     */
    native public static void sReportStats();
}
