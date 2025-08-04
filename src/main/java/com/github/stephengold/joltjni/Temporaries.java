/*
Copyright (c) 2025 Stephen Gold

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

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;

/**
 * Thread-local temporary storage for internal use of the Jolt-JNI library.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final class Temporaries {
    // *************************************************************************
    // fields

    /**
     * a reusable, direct DoubleBuffer
     */
    final static ThreadLocal<DoubleBuffer> doubleBuffer1
            = ThreadLocal.withInitial(() -> Jolt.newDirectDoubleBuffer(3));
    /**
     * a reusable, direct FloatBuffer
     */
    final static ThreadLocal<FloatBuffer> floatBuffer1
            = ThreadLocal.withInitial(() -> Jolt.newDirectFloatBuffer(12));
    // *************************************************************************
    // constructors

    /**
     * A private constructor to inhibit instantiation of this class.
     */
    private Temporaries() {
    }
}
