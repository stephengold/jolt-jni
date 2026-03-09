/*
Copyright (c) 2025-2026 Stephen Gold

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

import com.github.stephengold.joltjni.readonly.ConstBody;
import com.github.stephengold.joltjni.readonly.ConstBodyIdArray;
import com.github.stephengold.joltjni.readonly.ConstBodyLockInterface;

/**
 * Lock multiple bodies.
 *
 * @author Stephen Gold sgold@sonic.net
 */
abstract public class BodyLockMultiBase extends NonCopyable {
    // *************************************************************************
    // fields

    /**
     * the body IDs
     */
    protected ConstBodyIdArray idArray;
    /**
     * the interface to use
     */
    final protected ConstBodyLockInterface bli;
    // *************************************************************************
    // constructors

    /**
     * Instantiate a lock with no native object assigned.
     *
     * @param bli the lock interface to use (not {@code null}, alias created)
     */
    BodyLockMultiBase(ConstBodyLockInterface bli) {
        assert bli != null;
        this.bli = bli;
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Access all the bodies.
     *
     * @return a new array
     */
    abstract public ConstBody[] getBodies();

    /**
     * Access a specific body.
     *
     * @param index into the array of body IDs (&ge;0)
     * @return a new JVM object with the pre-existing native object assigned, or
     * {@code null} if not available
     */
    abstract public ConstBody getBody(int index);

    /**
     * Access the array of body IDs.
     *
     * @return the pre-existing object
     */
    public ConstBodyIdArray getBodyIdArray() {
        return idArray;
    }

    /**
     * Count the bodies that were locked.
     *
     * @return the number of bodies (&ge;0)
     */
    abstract public int getNumBodies();

    /**
     * Release all the locks.
     */
    abstract public void releaseLocks();
}
