/*
Copyright (c) 2024-2025 Stephen Gold

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
 * A variable-length vector (array) of body IDs. (native type:
 * {@code Array<BodyID>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class BodyIdVector extends JoltPhysicsObject {
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
     * Count how many IDs the currently allocated storage can hold. The vector
     * is unaffected.
     *
     * @return the number of IDs (&ge;size)
     */
    public int capacity() {
        long vectorVa = va();
        int result = capacity(vectorVa);

        return result;
    }

    /**
     * Test whether the array contains one or more elements. The array is
     * unaffected.
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
     * Remove the specified element.
     *
     * @param elementIndex the index of the element to remove (&ge;0)
     */
    public void erase(int elementIndex) {
        erase(elementIndex, elementIndex + 1);
    }

    /**
     * Remove all IDs in the specified range of indices.
     *
     * @param startIndex the index of the first element to remove (&ge;0)
     * @param stopIndex one plus the index of the last element to remove (&ge;0)
     */
    public void erase(int startIndex, int stopIndex) {
        long vectorVa = va();
        erase(vectorVa, startIndex, stopIndex);
    }

    /**
     * Find the element index of the matching ID, if any.
     *
     * @param id the ID to search for
     * @return the index of the matching element, or -1 if not found
     */
    public int find(int id) {
        long vectorVa = va();
        int numIds = size(vectorVa);
        for (int i = 0; i < numIds; ++i) {
            int id2 = getId(vectorVa, i);
            if (id == id2) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Return the ID at the specified index.
     *
     * @param elementIndex the index from which to copy the ID (&ge;0)
     * @return the {@code BodyID} value
     */
    public int get(int elementIndex) {
        long vectorVa = va();
        int result = getId(vectorVa, elementIndex);

        return result;
    }

    /**
     * Append the specified object to the end.
     *
     * @param bodyId the ID to append
     */
    public void pushBack(int bodyId) {
        int numElements = size();
        resize(numElements + 1);
        set(numElements, bodyId);
    }

    /**
     * Expand or truncate the vector.
     *
     * @param numIds the desired size (number of IDs, &ge;0)
     */
    public void resize(int numIds) {
        long vectorVa = va();
        resize(vectorVa, numIds);
    }

    /**
     * Store the specified ID at the specified index.
     *
     * @param elementIndex the index at which to store the ID (&ge;0)
     * @param bodyId the ID to store
     */
    public void set(int elementIndex, int bodyId) {
        long vectorVa = va();
        setId(vectorVa, elementIndex, bodyId);
    }

    /**
     * Count how many IDs are in the vector. The vector is unaffected.
     *
     * @return the number of IDs (&ge;0, &le;capacity)
     */
    public int size() {
        long vectorVa = va();
        int result = size(vectorVa);

        return result;
    }

    /**
     * Arrange the IDs in ascending order.
     */
    public void sort() {
        long vectorVa = va();
        sort(vectorVa);
    }

    /**
     * Copy all the elements (in order) to a Java list.
     *
     * @return a new Java list
     */
    public List<Integer> toList() {
        int numElements = size();
        List<Integer> result = new ArrayList<>(numElements);
        for (int i = 0; i < numElements; ++i) {
            int element = get(i);
            result.add(element);
        }

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static int capacity(long vectorVa);

    native private static long createBodyIdVector();

    native private static void erase(
            long vectorVa, int startIndex, int stopIndex);

    native private static void free(long vectorVa);

    native private static int getId(long vectorVa, int elementIndex);

    native private static void resize(long vectorVa, int numIds);

    native private static void setId(
            long vectorVa, int elementIndex, int bodyId);

    native private static int size(long vectorVa);

    native private static void sort(long vectorVa);
}
