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
package com.github.stephengold.joltjni.readonly;

import com.github.stephengold.joltjni.JoltPhysicsObject;

/**
 * Read-only access to a {@code JoltPhysicsObject}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstJoltPhysicsObject
        extends AutoCloseable, Comparable<JoltPhysicsObject> {
    /**
     * Free and unassign the native object if the JVM object owns it.
     */
    @Override
    void close();

    /**
     * Test whether a native object is assigned. Both objects are unaffected.
     *
     * @return {@code true} if one is assigned, otherwise {@code false}
     */
    boolean hasAssignedNativeObject();

    /**
     * Test whether the JVM object owns (is responsible for freeing) its
     * assigned native object. Both objects are unaffected.
     *
     * @return {@code true} if owner, otherwise {@code false}
     */
    boolean ownsNativeObject();

    /**
     * Return the address of the target object (if this is a counted reference)
     * or else the address of the native object. No objects are affected.
     *
     * @return a virtual address (not zero)
     */
    long targetVa();
}
