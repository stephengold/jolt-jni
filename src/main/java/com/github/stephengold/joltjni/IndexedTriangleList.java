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
 * A variable-length vector of {@code IndexedTriangle} instances.
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
        setVirtualAddress(listVa, true);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the triangle at the specified index.
     *
     * @param listIndex the index from which to get the triangle
     * @return the triangle
     */
    public IndexedTriangle get(int listIndex) {
        long listVa = va();
        long triangleVa = getTriangle(listVa, listIndex);
        IndexedTriangle result = new IndexedTriangle(triangleVa);

        return result;
    }

    /**
     * Expand or truncate the list.
     *
     * @param numTriangles the desired size (number of triangles)
     */
    public void resize(int numTriangles) {
        long listVa = va();
        resize(listVa, numTriangles);
    }

    /**
     * Put the specified triangle at the specified index.
     *
     * @param listIndex the index at which to put the triangle
     * @param triangle the triangle to put (not null)
     */
    public void set(int listIndex, IndexedTriangle triangle) {
        long listVa = va();
        long triangleVa = triangle.va();
        setTriangle(listVa, listIndex, triangleVa);
    }
    // *************************************************************************
    // JoltPhysicsObject methods

    /**
     * Unassign the assigned native object, assuming there is one. Free the
     * native object if the current list owns it.
     */
    @Override
    public void close() {
        if (ownsNativeObject()) {
            long listVa = va();
            free(listVa);
        }

        unassignNativeObject();
    }
    // *************************************************************************
    // native private methods

    native private static long createIndexedTriangleList();

    native private static void free(long listVa);

    native private static long getTriangle(long listVa, int listIndex);

    native private static void resize(long listVa, int numTriangles);

    native private static void setTriangle(
            long listVa, int listIndex, long triangleVa);
}
