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
 * An abstract class to represent a (native) Jolt Physics object.
 *
 * @author Stephen Gold sgold@sonic.net
 */
abstract public class JoltPhysicsObject implements AutoCloseable {
    // *************************************************************************
    // fields

    /**
     * true if the current instance owns (is responsible for freeing) its native
     * object, otherwise false
     */
    private boolean isOwner = false;
    /**
     * virtual address of the native object assigned to the current instance, or
     * 0 for none
     */
    private long virtualAddress;
    // *************************************************************************
    // constructors

    /**
     * Instantiate with no native object assigned.
     */
    protected JoltPhysicsObject() {
        this.virtualAddress = 0L;
    }

    /**
     * Instantiate with the specified native object assigned.
     *
     * @param virtualAddress the virtual address of the native object to assign
     * (not zero)
     */
    protected JoltPhysicsObject(long virtualAddress) {
        assert virtualAddress != 0L;
        this.virtualAddress = virtualAddress;
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Test whether a native object is assigned to the current instance.
     *
     * @return true if one is assigned, otherwise false
     */
    final public boolean hasAssignedNativeObject() {
        if (virtualAddress == 0L) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Test whether the current instance owns (is responsible for freeing) its
     * native object.
     *
     * @return true if owner, otherwise false
     */
    final public boolean ownsNativeObject() {
        return isOwner;
    }

    /**
     * Return the virtual address of the assigned native object, assuming one is
     * assigned.
     *
     * @return the virtual address (not zero)
     */
    final public long va() {
        assert virtualAddress != 0L;
        return virtualAddress;
    }
    // *************************************************************************
    // new protected methods

    /**
     * Assign a native object to this instance, assuming none is already
     * assigned.
     *
     * @param virtualAddress the virtual address of the native object to assign
     * (not zero)
     * @param owner true &rarr; make the current object the owner, false &rarr;
     * the current object isn't the owner
     */
    protected void setVirtualAddress(long virtualAddress, boolean owner) {
        assert virtualAddress != 0L : "invalid virtual address";
        assert !hasAssignedNativeObject() : "native object already assigned";

        this.isOwner = owner;
        this.virtualAddress = virtualAddress;
    }

    /**
     * Unassign (but don't free) the assigned native object, assuming one is
     * assigned.
     */
    final protected void unassignNativeObject() {
        assert hasAssignedNativeObject() : "no native object is assigned";

        this.isOwner = false;
        this.virtualAddress = 0L;
    }
    // *************************************************************************
    // AutoCloseable methods

    @Override
    public void close() {
        if (isOwner) {
            System.out.println(
                    "I don't know how to free " + getClass().getSimpleName());
        }
        unassignNativeObject();
    }
}
