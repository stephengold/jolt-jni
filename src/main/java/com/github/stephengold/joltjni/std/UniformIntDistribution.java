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
package com.github.stephengold.joltjni.std;

import com.github.stephengold.joltjni.JoltPhysicsObject;

/**
 * Generate uniformly distributed {@code int} values. (native type:
 * {@code std::uniform_int_distribution<int>})
 * <p>
 * The algorithm isn't specific to Jolt Physics; it's included in jolt-jni for
 * expediency, since BroadPhaseInsertionTest.cpp uses it.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class UniformIntDistribution extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a uniform distribution with the specified range of output
     * values.
     *
     * @param min the desired (inclusive) lower limit
     * @param max the desired (inclusive) upper limit
     */
    public UniformIntDistribution(int min, int max) {
        long distributionVa = createDistribution(min, max);
        setVirtualAddress(distributionVa, () -> free(distributionVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Iterate and return the next {@code int} value in the sequence.
     *
     * @param generator the sequence generator to use (not null)
     * @return the value
     */
    public int nextInt(DefaultRandomEngine generator) {
        long distributionVa = va();
        long generatorVa = generator.va();
        int result = nextIntDre(distributionVa, generatorVa);

        return result;
    }

    /**
     * Iterate and return the next {@code int} value in the sequence.
     *
     * @param generator the sequence generator to use (not null)
     * @return the value
     */
    public int nextInt(Mt19937 generator) {
        long distributionVa = va();
        long generatorVa = generator.va();
        int result = nextIntMt(distributionVa, generatorVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createDistribution(int min, int max);

    native private static void free(long distributionVa);

    native private static int nextIntDre(long distributionVa, long generatorVa);

    native private static int nextIntMt(long distributionVa, long generatorVa);
}
