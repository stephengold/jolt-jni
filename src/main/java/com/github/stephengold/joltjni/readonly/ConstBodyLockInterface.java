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
package com.github.stephengold.joltjni.readonly;

import com.github.stephengold.joltjni.PhysicsSystem;
import com.github.stephengold.joltjni.SharedMutex;

/**
 * Read-only access to a {@code BodyLockInterface}. (native type: const
 * BodyLockInterface)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstBodyLockInterface extends ConstJoltPhysicsObject {
    /**
     * Access the underlying {@code PhysicsSystem}.
     *
     * @return the pre-existing instance
     */
    PhysicsSystem getSystem();

    /**
     * Lock the specified body for reading.
     *
     * @param bodyId the ID of the body to read
     * @return a new mutex
     */
    SharedMutex lockRead(int bodyId);

    /**
     * Lock the specified body for writing.
     *
     * @param bodyId the ID of the body to write
     * @return a new mutex
     */
    SharedMutex lockWrite(int bodyId);

    /**
     * Unlock the specified mutex, which was created to read a body.
     *
     * @param mutex the mutex to unlock (not null)
     */
    void unlockRead(SharedMutex mutex);

    /**
     * Unlock the specified mutex, which was created to write a body.
     *
     * @param mutex the mutex to unlock (not null)
     */
    void unlockWrite(SharedMutex mutex);
}
