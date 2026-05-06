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
package testjoltjni.app.samples;

import com.beust.jcommander.Parameter;

/**
 * Command-line parameters of the SmokeTestAll application.
 *
 * @author Stephen Gold sgold@sonic.net
 */
class TestParameters {
    // *************************************************************************
    // fields

    /**
     * whether to simply display the usage message and exit
     */
    @Parameter(names = {"-h", "--help"},
            description = "Display this usage message and then exit.")
    private boolean helpOnly;
    /**
     * number of physics steps to simulate for each test
     */
    @Parameter(names = {"-n", "--numSteps"},
            description = "Number of physics steps to simulate for each test.")
    private int numSteps = 1;
    /**
     * whether to trace heap allocations in the JNI glue code
     */
    @Parameter(names = {"-t", "--traceAllocations"},
            description = "Trace heap allocations in JNI glue code.")
    private boolean traceAllocations;
    /**
     * whether log output should be verbose
     */
    @Parameter(names = {"-v", "--verbose"},
            description = "Generate additional log output.")
    private boolean verboseLogging;
    // *************************************************************************
    // new methods exposed

    /**
     * Test whether to display the usage message and then exit.
     *
     * @return {@code true} to display the message, otherwise {@code false}
     */
    boolean helpOnly() {
        return helpOnly;
    }

    /**
     * Return the number of physics steps to simulate for each test.
     *
     * @return the number
     */
    int numSteps() {
        return numSteps;
    }

    /**
     * Test whether to trace heap allocations in the JNI glue code.
     *
     * @return {@code true} to trace, otherwise {@code false}
     */
    boolean traceAllocations() {
        return traceAllocations;
    }

    /**
     * Test whether the verbose-logging option was specified.
     *
     * @return true if specified, otherwise false
     */
    boolean verboseLogging() {
        return verboseLogging;
    }
}
