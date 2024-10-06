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

import com.github.stephengold.joltjni.readonly.ConstSubShapeId;

/**
 * Identify an element (typically a triangle or other primitive type) in a
 * compound shape. (native type: SubShapeID)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class SubShapeId extends JoltPhysicsObject implements ConstSubShapeId {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an ID with the specified native object assigned but not
     * owned.
     * <p>
     * For use in custom contact listeners.
     *
     * @param idVa the virtual address of the native object to assign (not zero)
     */
    public SubShapeId(long idVa) {
        super(idVa);
    }

    /**
     * Instantiate an ID with the specified native object assigned.
     *
     * @param idVa the virtual address of the native object to assign (not zero)
     * @param owner true &rarr; make the JVM object the owner, false &rarr; it
     * isn't the owner
     */
    SubShapeId(long idVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(idVa) : null;
        setVirtualAddress(idVa, freeingAction);
    }
    // *************************************************************************
    // ConstSubShapeId methods

    /**
     * Test for equality with the specified ID. The current ID is unaffected.
     *
     * @param otherId the ID to compare with (not null, unaffected)
     * @return true if equivalent, otherwise false
     */
    @Override
    public boolean contentEquals(ConstSubShapeId otherId) {
        long idVa = va();
        long otherIdVa = otherId.va();
        boolean result = contentEquals(idVa, otherIdVa);

        return result;
    }

    /**
     * Return the bit pattern of the ID. The ID is unaffected.
     *
     * @return the raw bit pattern
     */
    @Override
    public int getValue() {
        long idVa = va();
        int result = getValue(idVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static boolean contentEquals(long idVa, long otherIdVa);

    native private static void free(long idVa);

    native private static int getValue(long idVa);
}
