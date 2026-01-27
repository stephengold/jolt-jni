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

import com.github.stephengold.joltjni.template.Array;

/**
 * A variable-length list (array) of hair strands. (native type:
 * {@code Array<HairSettings::SStrand>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class SStrandList extends Array<SStrand> {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty list.
     */
    public SStrandList() {
        long listVa = createDefault();
        setVirtualAddress(listVa, () -> free(listVa));
    }

    /**
     * Instantiate a list from a fixed-length array.
     *
     * @param strands the strands to include in the list
     */
    public SStrandList(SStrand... strands) {
        this();
        for (SStrand strand : strands) {
            pushBack(strand);
        }
    }
    // *************************************************************************
    // Array<SStrand> methods

    /**
     * Count how many strands the currently allocated storage can hold. The list
     * is unaffected.
     *
     * @return the number of strands (&ge;size)
     */
    @Override
    public int capacity() {
        long listVa = va();
        int result = capacity(listVa);

        return result;
    }

    /**
     * Remove all strands in the specified range of indices.
     *
     * @param startIndex the index of the first strand to remove (&ge;0)
     * @param stopIndex one plus the index of the last strand to remove (&ge;0)
     */
    @Override
    public void erase(int startIndex, int stopIndex) {
        long listVa = va();
        erase(listVa, startIndex, stopIndex);
    }

    /**
     * Access the strand at the specified index.
     *
     * @param elementIndex the index from which to get the strand (&ge;0)
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public SStrand get(int elementIndex) {
        long listVa = va();
        long strandVa = getStrand(listVa, elementIndex);
        SStrand result = new SStrand(this, strandVa);

        return result;
    }

    /**
     * Extend or truncate the list.
     *
     * @param numStrands the desired size (number of strands, &ge;0)
     */
    @Override
    public void resize(int numStrands) {
        long listVa = va();
        resize(listVa, numStrands);
    }

    /**
     * Put the specified strand at the specified index.
     *
     * @param elementIndex the index at which to put the strand (&ge;0)
     * @param strand the strand to put (not {@code null})
     */
    @Override
    public void set(int elementIndex, SStrand strand) {
        long listVa = va();
        long strandVa = strand.va();
        setStrand(listVa, elementIndex, strandVa);
    }

    /**
     * Count how many strands are in the list. The list is unaffected.
     *
     * @return the number of strands (&ge;0, &le;capacity)
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

    native private static long getStrand(long listVa, int elementIndex);

    native private static void resize(long listVa, int numStrands);

    native private static void setStrand(
            long listVa, int elementIndex, long strandVa);

    native private static int size(long listVa);
}
