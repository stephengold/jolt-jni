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
package com.github.stephengold.joltjni.readonly;

import com.github.stephengold.joltjni.BodyId;

/**
 * Read-only access to a {@code BodyId}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstBodyId extends ConstJoltPhysicsObject {
    // *************************************************************************
    // new methods exposed

    /**
     * Create a mutable copy.
     *
     * @return a new mutable JVM object with a new native object assigned
     */
    BodyId copy();

    /**
     * Return the body's index in the array. The ID is unaffected.
     *
     * @return the index (&ge;0)
     */
    int getIndex();

    /**
     * Convert the ID to an integer. The ID is unaffected.
     *
     * @return the integer value
     */
    int getIndexAndSequenceNumber();

    /**
     * Return the body's sequence number. The ID is unaffected.
     *
     * @return the sequence number (&ge;0)
     */
    int getSequenceNumber();

    /**
     * Test whether the ID is valid. It is unaffected.
     *
     * @return true if invalid, false if valid
     */
    boolean isInvalid();
}
