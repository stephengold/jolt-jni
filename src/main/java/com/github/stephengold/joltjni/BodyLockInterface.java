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
 * An interface to a {@code PhysicsSystem} that is aware of body locking.
 *
 * @author Stephen Gold sgold@sonic.net
 */
abstract public class BodyLockInterface extends NonCopyable {
    // *************************************************************************
    // fields

    /**
     * prevent premature garbage collection of the underlying
     * {@code PhysicsSystem}
     */
    final private PhysicsSystem system;
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified native object assigned but not owned.
     *
     * @param system the underlying {@code PhysicsSystem} (not null)
     * @param interfaceVa the virtual address of the native object to assign
     * (not zero)
     */
    protected BodyLockInterface(PhysicsSystem system, long interfaceVa) {
        this.system = system;
        setVirtualAddress(interfaceVa, null);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Access the underlying {@code PhysicsSystem}.
     *
     * @return the pre-existing instance
     */
    public PhysicsSystem getSystem() {
        return system;
    }

    /**
     * Lock the specified body for reading.
     *
     * @param bodyId the body to read (not null, unaffected)
     * @return a new mutex
     */
    abstract public SharedMutex lockRead(ConstBodyId bodyId);

    /**
     * Lock the specified body for writing.
     *
     * @param bodyId the body to write (not null, unaffected)
     * @return a new mutex
     */
    abstract public SharedMutex lockWrite(ConstBodyId bodyId);

    /**
     * Unlock the specified mutex, which was created to read a body.
     *
     * @param mutex the mutex to unlock (not null)
     */
    abstract public void unlockRead(SharedMutex mutex);

    /**
     * Unlock the specified mutex, which was created to write a body.
     *
     * @param mutex the mutex to unlock (not null)
     */
    abstract public void unlockWrite(SharedMutex mutex);
}
