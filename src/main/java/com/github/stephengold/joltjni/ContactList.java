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

import com.github.stephengold.joltjni.template.Array;

/**
 * A variable-length list (array) of character contacts. (native type:
 * {@code Array<Contact>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class ContactList extends Array<Contact> {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a list with the specified native object assigned.
     *
     * @param listVa the virtual address of the native object to assign (not
     * zero)
     * @param owner true &rarr; make the JVM object the owner, false &rarr; it
     * isn't the owner
     */
    ContactList(long listVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(listVa) : null;
        setVirtualAddress(listVa, freeingAction);
    }
    // *************************************************************************
    // Array<Contact> methods

    /**
     * Count how many contacts the currently allocated storage can hold. The
     * list is unaffected.
     *
     * @return the number of contacts (&ge;size)
     */
    @Override
    public int capacity() {
        long listVa = va();
        int result = capacity(listVa);

        return result;
    }

    /**
     * Remove all contacts in the specified range of indices.
     *
     * @param startIndex the index of the first element to remove (&ge;0)
     * @param stopIndex one plus the index of the last element to remove (&ge;0)
     */
    @Override
    public void erase(int startIndex, int stopIndex) {
        long vectorVa = va();
        erase(vectorVa, startIndex, stopIndex);
    }

    /**
     * Access the contact at the specified index.
     *
     * @param elementIndex the index from which to get the contact (&ge;0)
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public Contact get(int elementIndex) {
        long listVa = va();
        long contactVa = get(listVa, elementIndex);
        Contact result = new Contact(contactVa, true);

        return result;
    }

    /**
     * Expand or truncate the list.
     *
     * @param numContacts the desired size (number of elements, &ge;0)
     */
    @Override
    public void resize(int numContacts) {
        long listVa = va();
        resize(listVa, numContacts);
    }

    /**
     * Put the specified contact at the specified index.
     *
     * @param elementIndex the index at which to put the contact (&ge;0)
     * @param contact the contact to put (not null)
     */
    @Override
    public void set(int elementIndex, Contact contact) {
        long listVa = va();
        long contactVa = contact.va();
        set(listVa, elementIndex, contactVa);
    }

    /**
     * Count how many contacts are in the list. The list is unaffected.
     *
     * @return the number of contacts (&ge;0, &le;capacity)
     */
    @Override
    public int size() {
        long listVa = va();
        int result = size(listVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static int capacity(long listVa);

    native private static void erase(
            long vectorVa, int startIndex, int stopIndex);

    native private static void free(long listVa);

    native private static long get(long listVa, int elementIndex);

    native private static void resize(long listVa, int numContacts);

    native private static void set(
            long listVa, int elementIndex, long contactVa);

    native private static int size(long listVa);
}
