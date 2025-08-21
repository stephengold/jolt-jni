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

/**
 * Lock a body for read-write access.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class BodyLockWrite extends NonCopyable {
    // *************************************************************************
    // fields

    /**
     * the interface to use
     */
    final private BodyLockInterface bli;
    // *************************************************************************
    // constructors

    /**
     * Acquire a lock using the specified interface and body ID.
     *
     * @param bli the interface to use (not null, unaffected)
     * @param bodyId the ID of the body to lock
     */
    public BodyLockWrite(BodyLockInterface bli, int bodyId) {
        this.bli = bli;
        long interfaceVa = bli.va();
        long lockVa = createBodyLockWrite(interfaceVa, bodyId);
        setVirtualAddressAsOwner(lockVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Access the body.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public Body getBody() {
        long lockVa = va();
        long bodyVa = getBody(lockVa);
        PhysicsSystem system = bli.getSystem();
        Body result = new Body(system, bodyVa);

        return result;
    }

    /**
     * Explicitly release the lock.
     */
    public void releaseLock() {
        long lockVa = va();
        releaseLock(lockVa);
    }

    /**
     * Test whether the lock was successfully acquired.
     *
     * @return {@code true} if acquired, otherwise {@code false}
     */
    public boolean succeeded() {
        long lockVa = va();
        boolean result = succeeded(lockVa);

        return result;
    }

    /**
     * Test whether the lock was acquired and the body is still in broadphase.
     *
     * @return {@code true} if both conditions are met, otherwise {@code false}
     */
    public boolean succeededAndIsInBroadPhase() {
        long lockVa = va();
        boolean result = succeededAndIsInBroadPhase(lockVa);

        return result;
    }
    // *************************************************************************
    // protected methods

    /**
     * Assign a native object (assuming there's none already assigned) and
     * designate the JVM object as the owner.
     *
     * @param lockVa the virtual address of the native object to assign (not
     * zero)
     */
    final void setVirtualAddressAsOwner(long lockVa) {
        Runnable freeingAction = () -> free(lockVa);
        setVirtualAddress(lockVa, freeingAction);
    }
    // *************************************************************************
    // native private methods

    native private static long createBodyLockWrite(
            long interfaceVa, int bodyId);

    native private static void free(long lockVa);

    native private static long getBody(long lockVa);

    native private static void releaseLock(long lockVa);

    native private static boolean succeeded(long lockVa);

    native private static boolean succeededAndIsInBroadPhase(long lockVa);
}
