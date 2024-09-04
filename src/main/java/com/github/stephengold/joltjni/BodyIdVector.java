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
 * A variable-length vector (array) of body IDs.
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
    // *************************************************************************
    // Array<BodyId> methods

    /**
     * Count how many IDs the currently allocated storage can hold.
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
     * Put the specified ID at the specified index.
     *
     * @param elementIndex the index at which to put the ID (&ge;0)
     * @param id the ID to put (not null)
     */
    @Override
    public void set(int elementIndex, BodyId id) {
        long vectorId = va();
        long idVa = id.va();
        setId(vectorId, elementIndex, idVa);
    }

    /**
     * Count how many IDs are in the vector.
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

    native private static void free(long vectorVa);

    native private static long getId(long vectorVa, int elementIndex);

    native private static void resize(long vectorVa, int numIds);

    native private static void setId(
            long vectorVa, int elementIndex, long idVa);

    native private static int size(long vectorVa);
}
