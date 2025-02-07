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
package com.github.stephengold.joltjni.std;

import com.github.stephengold.joltjni.JoltPhysicsObject;

/**
 * Generate a pseudo-random sequence of integers for casual use. (native type:
 * std::default_random_engine)
 * <p>
 * The algorithm isn't specific to Jolt Physics; it's included in jolt-jni for
 * expediency, since BroadPhaseTest.cpp uses it.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class DefaultRandomEngine
        extends JoltPhysicsObject
        implements RandomNumberEngine {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a generator.
     */
    public DefaultRandomEngine() {
        long generatorVa = createDefault();
        setVirtualAddress(generatorVa, () -> free(generatorVa));
    }

    /**
     * Instantiate a generator using the specified 32-bit seed.
     *
     * @param seed for initialization
     */
    public DefaultRandomEngine(int seed) {
        long generatorVa = createSeeded(seed);
        setVirtualAddress(generatorVa, () -> free(generatorVa));
    }
    // *************************************************************************
    // RandomEngine methods

    /**
     * Return the next integer in the sequence.
     *
     * @return an integer value
     */
    @Override
    public int nextInt() {
        long generatorVa = va();
        int result = nextInt(generatorVa);

        return result;
    }

    /**
     * Seed the engine with the specified value.
     *
     * @param value the value to use
     */
    @Override
    public void seed(int value) {
        long generatorVa = va();
        setSeed(generatorVa, value);
    }
    // *************************************************************************
    // native private methods

    native private static long createDefault();

    native private static long createSeeded(int seed);

    native private static void free(long generatorVa);

    native private static int nextInt(long generatorVa);

    native private static void setSeed(long generatorVa, int value);
}
