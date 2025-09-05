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
package com.github.stephengold.joltjni;

import com.github.stephengold.joltjni.readonly.ConstJoltPhysicsObject;
import com.github.stephengold.joltjni.template.Ref;
import com.github.stephengold.joltjni.template.RefTarget;
import java.lang.ref.Cleaner;
import java.util.concurrent.atomic.AtomicReference;

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
     * manage references to (native) Jolt-Physics objects, or {@code null} if
     * none
     */
    private static Cleaner cleaner;
    /**
     * containing object or a counted reference thereto (to prevent premature
     * garbage collection), or {@code null} if none
     */
    final private JoltPhysicsObject containingObject;
    /**
     * freeing action if the JVM object owns its assigned native object.
     * <p>
     * An AtomicReference is used to ensure the freeing action is executed
     * exactly once, either by a manual call to close() or by the Cleaner.
     */
    private final AtomicReference<Runnable> freeingActionRef
            = new AtomicReference<>();
    /**
     * virtual address of the assigned native object, or 0 for none
     * <p>
     * Marked "volatile" to ensure that writes (especially setting it to zero)
     * are immediately visible to all other threads, preventing use-after-free
     * errors.
     */
    private volatile long virtualAddress;
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
     * Test whether a daemon thread to invoke freeing actions has been started.
     *
     * @return {@code true} if a thread has been started, otherwise
     * {@code false}
     */
    public static boolean isCleanerStarted() {
        return cleaner != null;
    }

    /**
     * Start a daemon thread to process the phantom-reachable objects and invoke
     * freeing actions, unless one has already been started.
     */
    public static void startCleaner() {
        if (cleaner == null) {
            cleaner = Cleaner.create();
        } else {
            System.out.println("A cleaner has already been started.");
        }
    }

    /**
     * Return the virtual address of the assigned native object, assuming one is
     * assigned. Both objects are unaffected.
     *
     * @return the virtual address (not zero)
     */
    final public long va() {
        long addr = virtualAddress; // Read the volatile field once.
        assert addr != 0L : "Attempted to use an object that has already been"
                + " freed: " + this;
        return addr;
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
     */
    final protected void setVirtualAddress(long virtualAddress) {
        assert virtualAddress != 0L : "invalid virtual address";
        assert !hasAssignedNativeObject() : "native object already assigned";
        assert freeingActionRef.get() == null;

        this.virtualAddress = virtualAddress;
    }

    /**
     * Assign a native object (assuming there's none already assigned) and
     * freeing action.
     *
     * @param virtualAddress the virtual address of the native object to assign
     * (not zero)
     * @param action freeing action if the JVM object will own (be responsible
     * for freeing) the native object, or {@code null} if not responsible
     */
    final protected void setVirtualAddress(
            long virtualAddress, Runnable action) {
        setVirtualAddress(virtualAddress);

        if (action != null) {
            assert containingObject == null : containingObject;
            assert !(this instanceof RefTarget) : "RefTarget cannot be owner";

            this.freeingActionRef.set(action);

            if (cleaner != null) {
                // Register the object with the cleaner. The provided Runnable
                // MUST NOT hold a strong reference back to 'this'.
                // Our static CleanerRunnable correctly avoids this leak.
                cleaner.register(this, new CleanerRunnable(this.freeingActionRef));
            }
        }
    }
    // *************************************************************************
    // AutoCloseable/ConstJoltPhysicsObject methods

    /**
     * Free and unassign the native object if the JVM object owns it. This can
     * be invoked explicitly or by exiting a try-with-resources block.
     */
    @Override
    public void close() {
        // Atomically retrieve the freeing action and replace it with null.
        // This is the core of the thread-safe, "run-once" guarantee.
        Runnable action = freeingActionRef.getAndSet(null);

        if (action != null) {
            try {
                // If we successfully "claimed" the action, execute it.
                action.run();
            } finally {
                // Crucially, update the volatile address *after* the native
                // resource has been freed. This write is visible to all threads.
                this.virtualAddress = 0L;
            }
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
        long thisVa = this.virtualAddress;
        long otherVa = other.virtualAddress;
        return Long.compare(thisVa, otherVa);
    }

    /**
     * Test whether a native object is assigned. Both objects are unaffected.
     *
     * @return {@code true} if one is assigned, otherwise {@code false}
     */
    @Override
    final public boolean hasAssignedNativeObject() {
        return virtualAddress != 0L;
    }

    /**
     * Test whether the JVM object owns (is responsible for freeing) its
     * assigned native object. Both objects are unaffected.
     *
     * @return {@code true} if owner, otherwise {@code false}
     */
    @Override
    final public boolean ownsNativeObject() {
        return freeingActionRef.get() != null;
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
        return va();
    }
    // *************************************************************************
    // Object methods

    /**
     * Test for type and virtual-address equality with another object. The
     * current instance is unaffected.
     *
     * @param otherObject the object to compare (unaffected) or {@code null}
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
            result = (this.virtualAddress == otherJpo.virtualAddress);
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
        long va = this.virtualAddress;
        return (int) (va ^ (va >>> 32));
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
    // *************************************************************************
    // private methods

    /**
     * A static, non-capturing Runnable for the Cleaner.
     * <p>
     * This holds the one piece of state it needs to coordinate with manual
     * close(): the AtomicReference containing the freeing action. This avoids a
     * memory leak that would occur if a non-static inner class were used.
     */
    private static class CleanerRunnable implements Runnable {
        /**
         * The shared atomic reference that holds the native freeing action.
         */
        private final AtomicReference<Runnable> actionRef;

        /**
         * Instantiate a cleaner action.
         *
         * @param actionRef the shared atomic reference (not null)
         */
        CleanerRunnable(AtomicReference<Runnable> actionRef) {
            this.actionRef = actionRef;
        }

        @Override
        public void run() {
            // Atomically get and set the reference to null.
            Runnable action = actionRef.getAndSet(null);
            // If we successfully "claimed" the action, run it.
            if (action != null) {
                action.run();
            }
        }
    }
}