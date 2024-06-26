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
 * Error conditions reported by {@code PhysicsSystem.update()}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class EPhysicsUpdateError {
    // *************************************************************************
    // constants

    /**
     * no errors
     */
    final public static int None = 0x0;
    /**
     * manifold cache is full
     * <p>
     * Increase the {@code maxContactConstraints} argument to the
     * {@code PhysicsSystem} constructor.
     */
    final public static int ManifoldCacheFull = 0x1;
    /**
     * body pair cache is full
     * <p>
     * Increase the {@code maxBodyPairs} argument to the {@code PhysicsSystem}
     * constructor.
     */
    final public static int BodyPairCacheFull = 0x2;
    /**
     * contact constraints buffer is full
     * <p>
     * Increase {@code maxContactConstraints} argument to the
     * {@code PhysicsSystem} constructor.
     */
    final public static int ContactConstraintsFull = 0x4;
}
