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
package testjoltjni;

import testjoltjni.junit.BodyBatchQueryTest;
import testjoltjni.junit.BodyLockMultiTest;
import testjoltjni.junit.BodyRemovalTest;
import testjoltjni.junit.DualCleanupRaceConditionTest;
import testjoltjni.junit.Test001;
import testjoltjni.junit.Test002;
import testjoltjni.junit.Test003;
import testjoltjni.junit.Test004;
import testjoltjni.junit.Test005;
import testjoltjni.junit.Test006;
import testjoltjni.junit.Test007;
import testjoltjni.junit.Test008;
import testjoltjni.junit.Test009;
import testjoltjni.junit.Test010;
import testjoltjni.junit.Test011;
import testjoltjni.junit.Test012;
import testjoltjni.junit.Test013;
import testjoltjni.junit.Test014;
import testjoltjni.junit.Test015;
import testjoltjni.junit.Test016;

/**
 * Run specific automated tests while logging heap allocations in the glue code,
 * for debugging memory leaks.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class RunTest {
    // *************************************************************************
    // constructors

    /**
     * A private constructor to inhibit instantiation of this class.
     */
    private RunTest() {
        // do nothing
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Main entry point for the RunTest application.
     *
     * @param arguments array of command-line arguments (not null)
     */
    public static void main(String[] arguments) {
        TestUtils.traceAllocations = true;

        new BodyBatchQueryTest().testBodyBatchQueries();
        new BodyLockMultiTest().testBodyLockMulti();
        new BodyRemovalTest().testBodyRemoval();
        try {
            new DualCleanupRaceConditionTest().testConcurrentCloseAndCleaner();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
            System.exit(1);
        }

        new Test001().test001();
        new Test002().test002();
        new Test003().test003();
        new Test004().test004();
        new Test005().test005();
        new Test006().test006();
        new Test007().test007();
        new Test008().test008();
        new Test009().test009();
        new Test010().test010();
        new Test011().test011();
        new Test012().test012();
        new Test013().test013();
        new Test014().test014();
        new Test015().test015();
        new Test016().test016();
    }
}
