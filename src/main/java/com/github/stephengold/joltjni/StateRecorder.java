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
 * Record the state of a physics system. Can also compare against an expected
 * state.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class StateRecorder extends NonCopyable {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a recorder with no native object assigned.
     */
    protected StateRecorder() {
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Test whether the recorder is validating.
     *
     * @return true if validating, otherwise false
     */
    public boolean isValidating() {
        long recorderVa = va();
        boolean result = isValidating(recorderVa);

        return result;
    }

    /**
     * Alter whether the recorder is validating.
     *
     * @param setting true to begin validating, false to stop validating
     */
    public void setValidating(boolean setting) {
        long recorderVa = va();
        setValidating(recorderVa, setting);
    }
    // *************************************************************************
    // native private methods

    native private static boolean isValidating(long recorderVa);

    native private static void setValidating(long recorderVa, boolean setting);
}
