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

import com.github.stephengold.joltjni.readonly.ConstBodyId;

/**
 * Lock a body for read-only access.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class BodyLockRead extends NonCopyable {
    // *************************************************************************
    // constructors

    /**
     * Acquire a lock using the specified interface and ID.
     *
     * @param bli the interface to use (not null, unaffected)
     * @param id the ID of the body (not null, unaffected)
     */
    public BodyLockRead(BodyLockInterface bli, ConstBodyId id) {
        long interfaceVa = bli.va();
        long idVa = id.va();
        long lockVa = createBodyLockRead(interfaceVa, idVa);
        setVirtualAddress(lockVa, true);
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
        Body result = new Body(bodyVa);

        return result;
    }

    /**
     * Explicitly release the lock. Normally this is done in the destructor.
     */
    public void releaseLock() {
        long lockVa = va();
        releaseLock(lockVa);
    }

    /**
     * Test whether the lock was successfully acquired.
     *
     * @return true if acquired, otherwise false
     */
    public boolean succeeded() {
        long lockVa = va();
        boolean result = succeeded(lockVa);

        return result;
    }

    /**
     * Test whether the lock was acquired and the body is still in broadphase.
     *
     * @return true if both conditions are met, otherwise false
     */
    public boolean succeededAndIsInBroadPhase() {
        long lockVa = va();
        boolean result = succeededAndIsInBroadPhase(lockVa);

        return result;
    }
    // *************************************************************************
    // protected methods

    /**
     * Assign a native object, assuming there's none already assigned.
     *
     * @param lockVa the virtual address of the native object to assign (not
     * zero)
     * @param owner true &rarr; make the JVM object the owner, false &rarr; it
     * isn't the owner
     */
    final void setVirtualAddress(long lockVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(lockVa) : null;
        setVirtualAddress(lockVa, freeingAction);
    }
    // *************************************************************************
    // native private methods

    native private static long createBodyLockRead(
            long interfaceVa, long idVa);

    native private static void free(long lockVa);

    native private static long getBody(long lockVa);

    native private static void releaseLock(long lockVa);

    native private static boolean succeeded(long lockVa);

    native private static boolean succeededAndIsInBroadPhase(long lockVa);
}
