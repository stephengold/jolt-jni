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
 * Identify a particular {@code Body} to a {@code BodyInterface}. (native type:
 * BodyID)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class BodyId extends JoltPhysicsObject implements ConstBodyId {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default ID.
     */
    public BodyId() {
        long idVa = createDefault();
        setVirtualAddress(idVa, () -> free(idVa));
    }

    /**
     * Instantiate with the specified native object assigned but not owned.
     * <p>
     * For use in custom collectors and custom listeners.
     *
     * @param idVa the virtual address of the native object to assign (not zero)
     */
    public BodyId(long idVa) {
        super(idVa);
    }

    /**
     * Instantiate with the specified native object assigned.
     *
     * @param idVa the virtual address of the native object to assign (not zero)
     * @param owner true &rarr; make the JVM object the owner, false &rarr; it
     * isn't the owner
     */
    BodyId(long idVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(idVa) : null;
        setVirtualAddress(idVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Test whether the specified IDs are equal. (native operator: {@code ==})
     *
     * @param id1 the first ID to test (not null, unaffected)
     * @param id2 the 2nd ID to test (not null, unaffected)
     * @return true if equal, false if unequal
     */
    public static boolean equals(ConstBodyId id1, ConstBodyId id2) {
        long id1va = id1.va();
        long id2va = id2.va();
        boolean result = equals(id1va, id2va);

        return result;
    }

    /**
     * Alter the index and sequence number.
     *
     * @param value the desired value
     */
    public void setIndexAndSequenceNumber(int value) {
        long idVa = va();
        setIndexAndSequenceNumber(idVa, value);
    }
    // *************************************************************************
    // ConstBodyId methods

    /**
     * Create a mutable copy.
     *
     * @return a new mutable JVM object with a new native object assigned
     */
    @Override
    public BodyId copy() {
        long idVa = va();
        long copyVa = copy(idVa);
        BodyId result = new BodyId(copyVa, true);

        return result;
    }

    /**
     * Return the body's index in the array. The ID is unaffected.
     *
     * @return the index (&ge;0)
     */
    @Override
    public int getIndex() {
        long idVa = va();
        int result = getIndex(idVa);

        return result;
    }

    /**
     * Convert the ID to an integer. The ID is unaffected.
     *
     * @return the integer value
     */
    @Override
    public int getIndexAndSequenceNumber() {
        long idVa = va();
        int result = getIndexAndSequenceNumber(idVa);

        return result;
    }

    /**
     * Return the body's sequence number. The ID is unaffected.
     *
     * @return the sequence number (&ge;0)
     */
    @Override
    public int getSequenceNumber() {
        long idVa = va();
        int result = getSequenceNumber(idVa);

        return result;
    }

    /**
     * Test whether the ID is valid. It is unaffected.
     *
     * @return true if invalid, false if valid
     */
    @Override
    public boolean isInvalid() {
        long idVa = va();
        boolean result = isInvalid(idVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long copy(long idVa);

    native private static long createDefault();

    native private static boolean equals(long id1Va, long id2Va);

    native private static void free(long idVa);

    native private static int getIndex(long idVa);

    native private static int getIndexAndSequenceNumber(long idVa);

    native private static int getSequenceNumber(long idVa);

    native private static boolean isInvalid(long idVa);

    native private static void setIndexAndSequenceNumber(long idVa, int value);
}
