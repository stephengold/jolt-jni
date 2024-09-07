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
 * A system for scheduling units of work for execution.
 *
 * @author Stephen Gold sgold@sonic.net
 */
abstract public class JobSystem extends NonCopyable {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a job system with no native object assigned.
     */
    JobSystem() {
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return maximum number of jobs that can execute concurrently.
     *
     * @return the number (&ge;1)
     */
    public int getMaxConcurrency() {
        long systemVa = va();
        int result = getMaxConcurrency(systemVa);
        return result;
    }
    // *************************************************************************
    // native private methods

    native private static int getMaxConcurrency(long systemVa);
}
