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

import java.util.ArrayList;
import java.util.List;

/**
 * A variable-length array of contact listeners.
 * <p>
 * When employed as a contact listener, it invokes each sub-listener in order,
 * and the value returned by {@code OnContactValidate()} will be the one with
 * the highest ordinal.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class ContactListenerList extends ContactListener {
    // *************************************************************************
    // fields

    /**
     * protect the sub-listeners from garbage collection
     */
    private static List<ContactListener> sublisteners = new ArrayList<>(8);
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty list.
     */
    public ContactListenerList() {
        long listVa = createDefault();
        setVirtualAddress(listVa, () -> free(listVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Remove all sub-listeners.
     */
    public synchronized void clear() {
        int size = size();
        sublisteners.clear();

        long listVa = va();
        erase(listVa, 0, size);
    }

    /**
     * Test whether the list contains one or more sub-listeners. The list is
     * unaffected.
     *
     * @return {@code true} if empty, otherwise {@code false}
     */
    public synchronized boolean empty() {
        boolean result = sublisteners.isEmpty();
        return result;
    }

    /**
     * Access the sub-listener at the specified index.
     *
     * @param index the index of the listener (&ge;0, &lt;size)
     * @return the pre-existing object
     */
    public synchronized ContactListener get(int index) {
        ContactListener result = sublisteners.get(index);
        return result;
    }

    /**
     * Append the specified listener to the end.
     *
     * @param listener the object to append (not {@code null})
     */
    public synchronized void pushBack(ContactListener listener) {
        sublisteners.add(listener);

        long listVa = va();
        long listenerVa = listener.va();
        pushBack(listVa, listenerVa);
    }

    /**
     * Remove all sub-listeners equal to the argument.
     *
     * @param listener the listener to remove (not {@code null})
     */
    public synchronized void remove(ContactListener listener) {
        int oldSize = sublisteners.size();
        long listVa = va();
        for (int i = oldSize - 1; i >= 0; --i) {
            ContactListener current = sublisteners.get(i);
            if (current.equals(listener)) {
                sublisteners.remove(i);
                erase(listVa, i, i + 1);
            }
        }
    }

    /**
     * Count how many sub-listeners there are. The list is unaffected.
     *
     * @return the number of sub-listeners (&ge;0)
     */
    public synchronized int size() {
        int result = sublisteners.size();
        return result;
    }

    /**
     * Add all the sub-listeners (in order) to a Java list.
     *
     * @return a new Java list
     */
    public synchronized List<ContactListener> toList() {
        int size = sublisteners.size();
        List<ContactListener> result = new ArrayList<>(size);
        result.addAll(sublisteners);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createDefault();

    native private static void erase(
            long listVa, int startIndex, int stopIndex);

    native private static void free(long listVa);

    native private static void pushBack(long listVa, long listenerVa);
}
