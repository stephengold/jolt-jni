/*
Copyright (c) 2026 Stephen Gold

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

import com.github.stephengold.joltjni.readonly.ConstContactKey;
import java.util.ArrayList;
import java.util.List;

/**
 * A variable-length array of contact keys. (native type:
 * {@code Array<CharacterVirtual::ContactKey>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class ContactSet extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty set.
     */
    public ContactSet() {
        long setVa = createDefault();
        setVirtualAddress(setVa, () -> free(setVa));
    }

    /**
     * Instantiate a copy of the specified set.
     *
     * @param original the set to copy (not {@code null}, unaffected)
     */
    public ContactSet(ContactSet original) {
        long originalVa = original.va();
        long copyVa = createCopy(originalVa);
        setVirtualAddress(copyVa, () -> free(copyVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Count how many keys the currently allocated storage can hold. The set is
     * unaffected.
     *
     * @return the number of keys (&ge;size)
     */
    public int capacity() {
        long setVa = va();
        int result = capacity(setVa);

        return result;
    }

    /**
     * Test whether the set contains one or more keys. The set is unaffected.
     *
     * @return {@code true} if empty, otherwise {@code false}
     */
    public boolean empty() {
        if (size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Remove the key at the specified index.
     *
     * @param elementIndex the element index of the key to remove (&ge;0)
     */
    public void erase(int elementIndex) {
        erase(elementIndex, elementIndex + 1);
    }

    /**
     * Remove all keys in the specified range of indices.
     *
     * @param startIndex the index of the first key to remove (&ge;0)
     * @param stopIndex one plus the index of the last key to remove (&ge;0)
     */
    public void erase(int startIndex, int stopIndex) {
        long setVa = va();
        erase(setVa, startIndex, stopIndex);
    }

    /**
     * Find the element index of the first matching key, if any.
     *
     * @param key the key to search for (not {@code null}, unaffected)
     * @return the index of the first matching key, or -1 if not found
     */
    public int find(ConstContactKey key) {
        long setVa = va();
        long keyVa = key.targetVa();
        int result = find(setVa, keyVa);

        return result;
    }

    /**
     * Copy the key at the specified index.
     *
     * @param elementIndex the index from which to copy the key (&ge;0)
     * @return a new object
     */
    public ContactKey get(int elementIndex) {
        long setVa = va();
        long keyVa = getElement(setVa, elementIndex);
        ContactKey result = new ContactKey(keyVa, true);

        return result;
    }

    /**
     * Append a copy of the specified key to the end.
     *
     * @param key the key to append (not {@code null}, unaffected)
     */
    public void pushBack(ConstContactKey key) {
        int numKeys = size();
        resize(numKeys + 1);
        set(numKeys, key);
    }

    /**
     * Extend or truncate the set.
     *
     * @param numKeys the desired size (number of keys, &ge;0)
     */
    public void resize(int numKeys) {
        long setVa = va();
        resize(setVa, numKeys);
    }

    /**
     * Store a copy of the specified key at the specified index.
     *
     * @param elementIndex the index at which to store the key (&ge;0)
     * @param key the key to store (not {@code null}, unaffected)
     */
    public void set(int elementIndex, ConstContactKey key) {
        long setVa = va();
        long keyVa = key.targetVa();
        setElement(setVa, elementIndex, keyVa);
    }

    /**
     * Count how many keys are in the set. The set is unaffected.
     *
     * @return the number of keys (&ge;0, &le;capacity)
     */
    public int size() {
        long setVa = va();
        int result = size(setVa);

        return result;
    }

    /**
     * Copy all the keys (in order) to a Java list.
     *
     * @return a new Java list
     */
    public List<ContactKey> toList() {
        int numKeys = size();
        List<ContactKey> result = new ArrayList<>(numKeys);
        for (int i = 0; i < numKeys; ++i) {
            ContactKey key = get(i);
            result.add(key);
        }

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static int capacity(long setVa);

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static void erase(long setVa, int startIndex, int stopIndex);

    native private static int find(long setVa, long keyVa);

    native private static void free(long setVa);

    native private static long getElement(long setVa, int elementIndex);

    native private static void resize(long setVa, int numKeys);

    native private static void setElement(
            long setVa, int elementIndex, long keyVa);

    native private static int size(long setVa);
}
