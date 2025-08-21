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

/**
 * Lock multiple bodies for read-write access.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class BodyLockMultiWrite extends BodyLockMultiBase {
    // *************************************************************************
    // fields

    /**
     * the interface to use
     */
    final private BodyLockInterface bli;
    // *************************************************************************
    // constructors

    /**
     * Acquire body locks using the specified interface and body IDs.
     *
     * @param bli the interface to use (not null, unaffected)
     * @param bodyIds the IDs of the bodies to lock (not empty)
     */
    public BodyLockMultiWrite(BodyLockInterface bli, int... bodyIds) {
        assert bodyIds.length > 0 : bodyIds.length;

        this.bli = bli;
        long interfaceVa = bli.va();
        long lockVa = create(interfaceVa, bodyIds);
        Runnable freeingAction = () -> free(lockVa);
        setVirtualAddress(lockVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Access all the bodies.
     *
     * @return a new array
     */
    @Override
    public Body[] getBodies() {
        long lockVa = va();
        int numRequested = getNumBodies(lockVa);
        Body[] result = new Body[numRequested];
        PhysicsSystem system = bli.getSystem();
        for (int index = 0; index < numRequested; ++index) {
            long bodyVa = getBody(lockVa, index);
            if (bodyVa == 0L) {
                result[index] = null;
            } else {
                result[index] = new Body(system, bodyVa);
            }
        }

        return result;
    }

    /**
     * Access a specific body.
     *
     * @param index into the array of body IDs (&ge;0)
     * @return a new JVM object with the pre-existing native object assigned, or
     * {@code null} if not available
     */
    @Override
    public Body getBody(int index) {
        assert index >= 0 : index;

        long lockVa = va();
        long bodyVa = getBody(lockVa, index);
        Body result;
        if (bodyVa == 0L) {
            result = null;
        } else {
            PhysicsSystem system = bli.getSystem();
            result = new Body(system, bodyVa);
        }

        return result;
    }

    /**
     * Count the bodies that were locked.
     *
     * @return the number of bodies (&ge;0)
     */
    @Override
    public int getNumBodies() {
        long lockVa = va();
        int result = getNumBodies(lockVa);

        return result;
    }

    /**
     * Release all the locks.
     */
    @Override
    public void releaseLocks() {
        long lockVa = va();
        releaseLocks(lockVa);
    }
    // *************************************************************************
    // native private methods

    native private static long create(long interfaceVa, int[] bodyIds);

    native private static void free(long lockVa);

    native private static long getBody(long lockVa, int index);

    native private static int getNumBodies(long lockVa);

    native private static void releaseLocks(long lockVa);
}
