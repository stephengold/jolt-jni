/*
Copyright (c) 2024-2026 Stephen Gold

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
 * A variable-length list (array) of material references. (native type:
 * {@code Array<RefConst<PhysicsMaterial>>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class PhysicsMaterialList extends Array<PhysicsMaterialRef> {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty list.
     */
    public PhysicsMaterialList() {
        long listVa = createDefault();
        setVirtualAddress(listVa, () -> free(listVa));
    }
    // *************************************************************************
    // Array<PhysicsMaterialRef> methods

    /**
     * Count how many references the currently allocated storage can hold. The
     * list is unaffected.
     *
     * @return the number of references (&ge;size)
     */
    @Override
    public int capacity() {
        long listVa = va();
        int result = capacity(listVa);

        return result;
    }

    /**
     * Remove all references in the specified range of indices.
     *
     * @param startIndex the index of the first element to remove (&ge;0)
     * @param stopIndex one plus the index of the last element to remove (&ge;0)
     */
    @Override
    public void erase(int startIndex, int stopIndex) {
        long listVa = va();
        erase(listVa, startIndex, stopIndex);
    }

    /**
     * Copy the reference at the specified index.
     *
     * @param elementIndex the index from which to get the reference (&ge;0)
     * @return a new reference to the same material
     */
    @Override
    public PhysicsMaterialRef get(int elementIndex) {
        long listVa = va();
        long refVa = get(listVa, elementIndex);
        PhysicsMaterialRef result = new PhysicsMaterialRef(refVa, true);

        return result;
    }

    /**
     * Extend or truncate the list.
     *
     * @param numElements the desired size (number of references, &ge;0)
     */
    @Override
    public void resize(int numElements) {
        long listVa = va();
        resize(listVa, numElements);
    }

    /**
     * Duplicate the specified reference at the specified index.
     *
     * @param elementIndex the index at which to put the reference (&ge;0)
     * @param reference the reference to put (not {@code null})
     */
    @Override
    public void set(int elementIndex, PhysicsMaterialRef reference) {
        long listVa = va();
        long refVa = reference.va();
        set(listVa, elementIndex, refVa);
    }

    /**
     * Count how many references are in the list. The list is unaffected.
     *
     * @return the number of references (&ge;0, &le;capacity)
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

    native private static long createDefault();

    native private static void erase(
            long listVa, int startIndex, int stopIndex);

    native private static void free(long listVa);

    native private static long get(long listVa, int elementIndex);

    native private static void resize(long listVa, int numElements);

    native private static void set(
            long listVa, int elementIndex, long refVa);

    native private static int size(long listVa);
}
