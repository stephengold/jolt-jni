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

/**
 * A variable-length array of references to constraints.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Constraints extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an array with the specified native object assigned.
     *
     * @param arrayVa the virtual address of the native object to assign (not
     * zero)
     * @param owner true &rarr; make the JVM object the owner, false &rarr; it
     * isn't the owner
     */
    Constraints(long arrayVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(arrayVa) : null;
        setVirtualAddress(arrayVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Count how many references the currently allocated storage can hold.
     *
     * @return the number of references (&ge;size)
     */
    public int capacity() {
        long arrayVa = va();
        int result = capacity(arrayVa);
        return result;
    }

    /**
     * Test whether the array contains any elements.
     *
     * @return true if empty, otherwise false
     */
    public boolean empty() {
        if (size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Access the constraint at the specified index.
     *
     * @param elementIndex the index from which to get the reference (&ge;0)
     * @return a new reference to the same constraint
     */
    public ConstraintRef get(int elementIndex) {
        long arrayVa = va();
        long refVa = get(arrayVa, elementIndex);
        ConstraintRef result = new ConstraintRef(refVa, true);

        return result;
    }

    /**
     * Expand or truncate the array.
     *
     * @param numElements the desired size (number of elements)
     */
    public void resize(int numElements) {
        long arrayVa = va();
        resize(arrayVa, numElements);
    }

    /**
     * Duplicate the specified reference at the specified index.
     *
     * @param elementIndex the index at which to put the reference (&ge;0)
     * @param reference the reference to put (not null)
     */
    public void set(int elementIndex, ConstraintRef reference) {
        long arrayVa = va();
        long refVa = reference.va();
        setRef(arrayVa, elementIndex, refVa);
    }

    /**
     * Count how many references are in the array.
     *
     * @return the number of references (&ge;0, &le;capacity)
     */
    public int size() {
        long arrayVa = va();
        int result = size(arrayVa);
        return result;
    }
    // *************************************************************************
    // native private methods

    native private static int capacity(long arrayVa);

    native private static void free(long arrayVa);

    native private static long get(long arrayVa, int elementIndex);

    native private static void resize(long arrayVa, int numElements);

    native private static void setRef(
            long arrayVa, int elementIndex, long refVa);

    native private static int size(long arrayVa);
}
