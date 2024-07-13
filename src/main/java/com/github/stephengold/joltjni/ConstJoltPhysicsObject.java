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
 * An immutable {@code JoltPhysicsObject}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstJoltPhysicsObject
        extends Comparable<ConstJoltPhysicsObject> {
    // *************************************************************************
    // new methods exposed

    /**
     * Free and unassign the native object if the current instance owns it.
     */
    void close();

    /**
     * Test whether a native object is assigned. The physics object is
     * unaffected.
     *
     * @return true if one is assigned, otherwise false
     */
    boolean hasAssignedNativeObject();

    /**
     * Test whether the physics object owns (is responsible for freeing) its
     * native object. The physics object is unaffected.
     *
     * @return true if owner, otherwise false
     */
    boolean ownsNativeObject();

    /**
     * Return the virtual address of the assigned native object, assuming one is
     * assigned. The physics object is unaffected.
     *
     * @return the virtual address (not zero)
     */
    long va();
}
