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
 * A variable-length list (array) of indexed triangles.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class IndexedTriangleList extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty list.
     */
    public IndexedTriangleList() {
        long listVa = createIndexedTriangleList();
        setVirtualAddress(listVa, () -> free(listVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Count how many triangles the currently allocated storage can hold.
     *
     * @return the number of triangles (&ge;size)
     */
    public int capacity() {
        long listVa = va();
        int result = capacity(listVa);
        return result;
    }

    /**
     * Test whether the list contains any triangles.
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
     * Access the triangle at the specified index.
     *
     * @param elementIndex the index from which to get the triangle (&ge;0)
     * @return a new JVM object with the pre-existing native object assigned
     */
    public IndexedTriangle get(int elementIndex) {
        long listVa = va();
        long triangleVa = getTriangle(listVa, elementIndex);
        IndexedTriangle result = new IndexedTriangle(triangleVa);

        return result;
    }

    /**
     * Expand or truncate the list.
     *
     * @param numTriangles the desired size (number of triangles, &ge;0)
     */
    public void resize(int numTriangles) {
        long listVa = va();
        resize(listVa, numTriangles);
    }

    /**
     * Put the specified triangle at the specified index.
     *
     * @param elementIndex the index at which to put the triangle (&ge;0)
     * @param triangle the triangle to put (not null)
     */
    public void set(int elementIndex, IndexedTriangle triangle) {
        long listVa = va();
        long triangleVa = triangle.va();
        setTriangle(listVa, elementIndex, triangleVa);
    }

    /**
     * Count how many triangles are in the list.
     *
     * @return the number of triangles (&ge;0, &le;capacity)
     */
    public int size() {
        long listVa = va();
        int result = size(listVa);
        return result;
    }
    // *************************************************************************
    // native private methods

    native private static int capacity(long listVa);

    native private static long createIndexedTriangleList();

    native private static void free(long listVa);

    native private static long getTriangle(long listVa, int elementIndex);

    native private static void resize(long listVa, int numTriangles);

    native private static void setTriangle(
            long listVa, int elementIndex, long triangleVa);

    native private static int size(long listVa);
}
