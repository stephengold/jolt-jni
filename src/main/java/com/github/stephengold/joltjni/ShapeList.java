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
 * A variable-length list (array) of shapes. (native type:
 * {@code Array<RefConst<Shape>>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class ShapeList extends Array<ShapeRefC> {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty list.
     */
    public ShapeList() {
        long listVa = createEmpty();
        setVirtualAddress(listVa, () -> free(listVa));
    }

    /**
     * Instantiate a list containing a single shape.
     *
     * @param shapeRef a counted reference to the desired shape (not null)
     */
    public ShapeList(ShapeRefC shapeRef) {
        this();
        pushBack(shapeRef);
    }
    // *************************************************************************
    // Array<ShapeRefC> methods

    /**
     * Count how many shapes the currently allocated storage can hold. The list
     * is unaffected.
     *
     * @return the number of shapes (&ge;size)
     */
    @Override
    public int capacity() {
        long listVa = va();
        int result = capacity(listVa);

        return result;
    }

    /**
     * Remove all shapes in the specified range of indices.
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
     * Access the shape at the specified index.
     *
     * @param elementIndex the index from which to get the shape (&ge;0)
     * @return a new reference
     */
    @Override
    public ShapeRefC get(int elementIndex) {
        long listVa = va();
        long shapeVa = getShapeRefC(listVa, elementIndex);
        ShapeRefC result = new ShapeRefC(shapeVa, true);

        return result;
    }

    /**
     * Expand or truncate the list.
     *
     * @param numElements the desired size (number of shapes, &ge;0)
     */
    @Override
    public void resize(int numElements) {
        long listVa = va();
        resize(listVa, numElements);
    }

    /**
     * Put the a reference to the specified shape at the specified index.
     *
     * @param elementIndex the index at which to put the reference (&ge;0)
     * @param shapeRef the reference to put (not null)
     */
    @Override
    public void set(int elementIndex, ShapeRefC shapeRef) {
        long listVa = va();
        long refVa = shapeRef.va();
        setShapeRefC(listVa, elementIndex, refVa);
    }

    /**
     * Count how many shapes are in the list. The list is unaffected.
     *
     * @return the number of shapes (&ge;0, &le;capacity)
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

    native private static long createEmpty();

    native private static void erase(
            long listVa, int startIndex, int stopIndex);

    native private static void free(long listVa);

    native private static long getShapeRefC(long listVa, int elementIndex);

    native private static void resize(long listVa, int numShapes);

    native private static void setShapeRefC(
            long listVa, int elementIndex, long shapeVa);

    native private static int size(long listVa);
}
