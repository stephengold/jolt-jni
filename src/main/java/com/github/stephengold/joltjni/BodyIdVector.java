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
import com.github.stephengold.joltjni.template.Array;
import java.util.Arrays;

/**
 * A variable-length vector (array) of body IDs. (native type:
 * {@code Array<BodyID>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class BodyIdVector extends Array<BodyId> {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty vector.
     */
    public BodyIdVector() {
        long vectorVa = createBodyIdVector();
        setVirtualAddress(vectorVa, () -> free(vectorVa));
    }

    /**
     * Instantiate with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param vectorVa the virtual address of the native object to assign (not
     * zero)
     */
    BodyIdVector(JoltPhysicsObject container, long vectorVa) {
        super(container, vectorVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Find the element index of the matching ID, if any.
     *
     * @param id the ID to search for (not null, unaffected)
     * @return the index of the matching element, or -1 if not found
     */
    public int find(ConstBodyId id) {
        long vectorVa = va();
        long idVa = id.targetVa();
        int numIds = size(vectorVa);
        for (int i = 0; i < numIds; ++i) {
            long id2Va = getId(vectorVa, i);
            if (BodyId.equals(idVa, id2Va)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Arrange the IDs in ascending order.
     */
    public void sort() {
        long vectorVa = va();
        int numIds = size(vectorVa);

        int[] tempValues = new int[numIds];
        long[] tempVas = new long[numIds];
        for (int i = 0; i < numIds; ++i) {
            long idVa = getId(vectorVa, i);
            tempVas[i] = idVa;
            tempValues[i] = BodyId.getIndexAndSequenceNumber(idVa);
        }
        Arrays.sort(tempValues);
        for (int i = 0; i < numIds; ++i) {
            BodyId.setIndexAndSequenceNumber(tempVas[i], tempValues[i]);
        }
    }
    // *************************************************************************
    // Array<BodyId> methods

    /**
     * Count how many IDs the currently allocated storage can hold. The vector
     * is unaffected.
     *
     * @return the number of IDs (&ge;size)
     */
    @Override
    public int capacity() {
        long vectorVa = va();
        int result = capacity(vectorVa);

        return result;
    }

    /**
     * Remove all IDs in the specified range of indices.
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
     * Access the ID at the specified index.
     *
     * @param elementIndex the index from which to get the ID (&ge;0)
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public BodyId get(int elementIndex) {
        long vectorVa = va();
        long idVa = getId(vectorVa, elementIndex);
        BodyId result = new BodyId(idVa, false);

        return result;
    }

    /**
     * Expand or truncate the vector.
     *
     * @param numIds the desired size (number of IDs, &ge;0)
     */
    @Override
    public void resize(int numIds) {
        long vectorVa = va();
        resize(vectorVa, numIds);
    }

    /**
     * Store the specified ID at the specified index.
     *
     * @param elementIndex the index at which to store the ID (&ge;0)
     * @param id the ID to store (not null)
     */
    @Override
    public void set(int elementIndex, BodyId id) {
        long vectorVa = va();
        long idVa = id.va();
        setId(vectorVa, elementIndex, idVa);
    }

    /**
     * Count how many IDs are in the vector. The vector is unaffected.
     *
     * @return the number of IDs (&ge;0, &le;capacity)
     */
    @Override
    public int size() {
        long vectorVa = va();
        int result = size(vectorVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static int capacity(long vectorVa);

    native private static long createBodyIdVector();

    native private static void erase(
            long vectorVa, int startIndex, int stopIndex);

    native private static void free(long vectorVa);

    native private static long getId(long vectorVa, int elementIndex);

    native private static void resize(long vectorVa, int numIds);

    native private static void setId(
            long vectorVa, int elementIndex, long idVa);

    native private static int size(long vectorVa);
}
