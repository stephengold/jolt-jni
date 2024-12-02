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

import com.github.stephengold.joltjni.readonly.ConstBody;
import com.github.stephengold.joltjni.readonly.ConstBodyId;
import com.github.stephengold.joltjni.readonly.ConstConstraint;

/**
 * Determine which parts of a physics system should be saved.
 *
 * @author Stephen Gold sgold@sonic.net
 */
abstract public class StateRecorderFilter extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a filter with no native object assigned.
     */
    StateRecorderFilter() {
    }

    /**
     * Instantiate a filter with the specified native object assigned.
     *
     * @param filterVa the virtual address of the native object to assign (not
     * zero)
     * @param owner {@code true} &rarr; make the JVM object the owner,
     * {@code false} &rarr; it isn't the owner
     */
    StateRecorderFilter(long filterVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(filterVa) : null;
        setVirtualAddress(filterVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Test whether the specified body should be saved. Meant to be overridden.
     *
     * @param body the body to test (not null, unaffected)
     * @return {@code true} to save, otherwise {@code false}
     */
    public boolean shouldSaveBody(ConstBody body) {
        return true;
    }

    /**
     * Test whether the specified constraint should be saved. Meant to be
     * overridden.
     *
     * @param constraint the constraint to test (not null, unaffected)
     * @return {@code true} to save, otherwise {@code false}
     */
    public boolean shouldSaveConstraint(ConstConstraint constraint) {
        return true;
    }

    /**
     * Test whether contacts between the specified bodies should be saved. Meant
     * to be overridden.
     *
     * @param bodyId1 the first body (not null, unaffected)
     * @param bodyId2 the second body (not null, unaffected)
     * @return {@code true} to save, otherwise {@code false}
     */
    public boolean shouldSaveContact(ConstBodyId bodyId1, ConstBodyId bodyId2) {
        return true;
    }
    // *************************************************************************
    // native private methods

    native private static void free(long filterVa);
}
