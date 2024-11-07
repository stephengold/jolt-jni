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
 * A {@code BodyLockInterface} that actually locks bodies.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class BodyLockInterfaceLocking extends BodyLockInterface {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified container and native object.
     *
     * @param system the containing object, or {@code null} if none
     * @param interfaceVa the virtual address of the native object to assign
     * (not zero)
     */
    BodyLockInterfaceLocking(PhysicsSystem system, long interfaceVa) {
        super(system, interfaceVa);
    }
    // *************************************************************************
    // BodyLockInterface methods

    /**
     * Lock the specified body for reading.
     *
     * @param bodyId the body to read (not null, unaffected)
     * @return a new mutex
     */
    @Override
    public SharedMutex lockRead(ConstBodyId bodyId) {
        long interfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        long mutexVa = lockRead(interfaceVa, bodyIdVa);
        SharedMutex result = new SharedMutex(mutexVa, true);

        return result;
    }

    /**
     * Lock the specified body for writing.
     *
     * @param bodyId the body to write (not null, unaffected)
     * @return a new mutex
     */
    @Override
    public SharedMutex lockWrite(ConstBodyId bodyId) {
        long interfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        long mutexVa = lockWrite(interfaceVa, bodyIdVa);
        SharedMutex result = new SharedMutex(mutexVa, true);

        return result;
    }

    /**
     * Unlock the specified mutex, which was created to read a body.
     *
     * @param mutex the mutex to unlock (not null)
     */
    @Override
    public void unlockRead(SharedMutex mutex) {
        long interfaceVa = va();
        long mutexVa = mutex.va();
        unlockRead(interfaceVa, mutexVa);
    }

    /**
     * Unlock the specified mutex, which was created to write a body.
     *
     * @param mutex the mutex to unlock (not null)
     */
    @Override
    public void unlockWrite(SharedMutex mutex) {
        long interfaceVa = va();
        long mutexVa = mutex.va();
        unlockWrite(interfaceVa, mutexVa);
    }
    // *************************************************************************
    // native private methods

    native private static long lockRead(long interfaceVa, long bodyIdVa);

    native private static long lockWrite(long interfaceVa, long bodyIdVa);

    native private static void unlockRead(long interfaceVa, long mutexVa);

    native private static void unlockWrite(long interfaceVa, long mutexVa);
}
