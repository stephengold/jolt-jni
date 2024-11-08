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

import com.github.stephengold.joltjni.readonly.ConstJoltPhysicsObject;
import com.github.stephengold.joltjni.template.Ref;
import com.github.stephengold.joltjni.template.RefTarget;
import java.lang.ref.Cleaner;

/**
 * An abstract class to represent a (native) Jolt Physics object.
 *
 * @author Stephen Gold sgold@sonic.net
 */
abstract public class JoltPhysicsObject
        implements AutoCloseable, ConstJoltPhysicsObject {
    // *************************************************************************
    // fields

    /**
     * manage references to (native) Jolt Physics objects, or {@code null} if
     * none
     */
    private static Cleaner cleaner;
    /**
     * containing object or a counted reference thereto (to prevent premature
     * garbage collection), or {@code null} if none
     */
    final private JoltPhysicsObject containingObject;
    /**
     * virtual address of the assigned native object, or 0 for none
     */
    private long virtualAddress;
    /**
     * freeing action if the JVM object owns (is responsible for freeing) its
     * assigned native object, otherwise {@code null}
     */
    private Runnable freeingAction;
    // *************************************************************************
    // constructors

    /**
     * Instantiate with no containing object and no native object assigned.
     */
    protected JoltPhysicsObject() {
        this.virtualAddress = 0L;
        this.containingObject = null;
    }

    /**
     * Instantiate with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param virtualAddress the virtual address of the native object to assign
     * (not zero)
     */
    protected JoltPhysicsObject(
            JoltPhysicsObject container, long virtualAddress) {
        assert virtualAddress != 0L;
        this.virtualAddress = virtualAddress;

        if (container instanceof RefTarget) {
            container = ((RefTarget) container).toRef();
        }
        assert container == null || container.ownsNativeObject() : container;
        this.containingObject = container;
    }

    /**
     * Instantiate with no containing object and the specified native object
     * assigned but not owned.
     *
     * @param virtualAddress the virtual address of the native object to assign
     * (not zero)
     */
    JoltPhysicsObject(long virtualAddress) {
        assert virtualAddress != 0L;
        this.virtualAddress = virtualAddress;

        this.containingObject = null;
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Start a daemon thread to process the phantom reachable objects and invoke
     * freeing actions.
     */
    public static void startCleaner() {
        cleaner = Cleaner.create();
    }

    /**
     * Return the virtual address of the assigned native object, assuming one is
     * assigned. Both objects are unaffected.
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
     * Access the containing object, if any.
     *
     * @return the pre-existing instance, or {@code null} if none
     */
    final protected JoltPhysicsObject getContainingObject() {
        return containingObject;
    }

    /**
     * Assign a native object, assuming there's none already assigned.
     *
     * @param virtualAddress the virtual address of the native object to assign
     * (not zero)
     * @param action freeing action if the JVM object will own (be responsible
     * for freeing) the native object, or {@code null} if not responsible
     */
    final protected void setVirtualAddress(
            long virtualAddress, Runnable action) {
        assert virtualAddress != 0L : "invalid virtual address";
        assert !hasAssignedNativeObject() : "native object already assigned";
        assert freeingAction == null : freeingAction;

        this.virtualAddress = virtualAddress;
        if (action != null) {
            assert containingObject == null : containingObject;
            assert !(this instanceof RefTarget) : "RefTarget cannot be owner";
            this.freeingAction = action;
            if (cleaner != null) {
                cleaner.register(this, action);
            }
        }
    }
    // *************************************************************************
    // AutoCloseable/ConstJoltPhysicsObject methods

    /**
     * Free and unassign the native object if the JVM object owns it.
     */
    @Override
    public void close() {
        if (freeingAction != null) {
            assert hasAssignedNativeObject() : "no native object is assigned";
            freeingAction.run(); // TODO possible race condition
            this.freeingAction = null;
            this.virtualAddress = 0L;
        }
    }

    /**
     * Compare (by virtual address) with another physics object. The current
     * instance is unaffected.
     *
     * @param other (not null, unaffected)
     * @return 0 if the objects have the same virtual address; negative if this
     * comes before other; positive if this comes after other
     */
    @Override
    public int compareTo(JoltPhysicsObject other) {
        long otherVa = other.va();
        int result = Long.compare(virtualAddress, otherVa);

        return result;
    }

    /**
     * Test whether a native object is assigned. Both objects are unaffected.
     *
     * @return {@code true} if one is assigned, otherwise {@code false}
     */
    @Override
    final public boolean hasAssignedNativeObject() {
        if (virtualAddress == 0L) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Test whether the JVM object owns (is responsible for freeing) its
     * assigned native object. Both objects are unaffected.
     *
     * @return {@code true} if owner, otherwise {@code false}
     */
    @Override
    final public boolean ownsNativeObject() {
        if (freeingAction == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Return the address of the native object, assuming this is not a counted
     * reference. No objects are affected.
     *
     * @return a virtual address (not zero)
     */
    @Override
    public long targetVa() {
        assert !(this instanceof Ref) :
                getClass().getSimpleName() + " must override targetVa()";
        assert virtualAddress != 0L;
        return virtualAddress;
    }
    // *************************************************************************
    // Object methods

    /**
     * Test for type and virtual-address equality with another object. The
     * current instance is unaffected.
     *
     * @param otherObject the object to compare (may be null, unaffected)
     * @return {@code true} if {@code this} and {@code otherObject} have the
     * same type and virtual address, otherwise {@code false}
     */
    @Override
    public boolean equals(Object otherObject) {
        boolean result;
        if (otherObject == this) {
            result = true;
        } else if (otherObject != null
                && otherObject.getClass() == getClass()) {
            JoltPhysicsObject otherJpo = (JoltPhysicsObject) otherObject;
            long otherVa = otherJpo.va();
            result = (virtualAddress == otherVa);
        } else {
            result = false;
        }

        return result;
    }

    /**
     * Return the hash code of the physics object. The object is unaffected.
     * <p>
     * Note: operations that alter the virtual address are likely to affect the
     * hash code as well!
     *
     * @return a 32-bit value for use in hashing
     */
    @Override
    public int hashCode() {
        int hash = (int) (virtualAddress >> 4);
        return hash;
    }

    /**
     * Represent the physics object as a String. The physics object is
     * unaffected.
     *
     * @return a descriptive string of text (not null, not empty)
     */
    @Override
    public String toString() {
        String result = getClass().getSimpleName();
        result += "#" + Long.toHexString(virtualAddress);

        return result;
    }
}
