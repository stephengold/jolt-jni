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

import com.github.stephengold.joltjni.readonly.ConstSubShape;

/**
 * A {@code Shape} composed from a union of simpler sub-shapes.
 *
 * @author Stephen Gold sgold@sonic.net
 */
abstract public class CompoundShape extends Shape {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a shape with no native object assigned.
     */
    CompoundShape() {
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Count the sub-shapes.
     *
     * @return the count (&ge;0)
     */
    public int getNumSubShapes() {
        long shapeVa = va();
        int result = getNumSubShapes(shapeVa);

        return result;
    }

    /**
     * Access the specified sub-shape.
     *
     * @param subShapeIndex the index of a subshape within the current compound
     * shape (&ge;0)
     * @return a new JVM object with the pre-existing native object assigned
     */
    public ConstSubShape getSubShape(int subShapeIndex) {
        long shapeVa = va();
        long subShapeVa = getSubShape(shapeVa, subShapeIndex);
        SubShape result = new SubShape(subShapeVa);

        return result;
    }

    /**
     * Convert a sub-shape index to a sub-shape ID. (native function:
     * GetSubShapeIDFromIndex)
     *
     * @param subShapeIndex the index of a subshape within the current compound
     * shape (&ge;0)
     * @param parent a path the the current compound shape (not null,
     * unaffected)
     * @return a new object
     */
    public SubShapeIdCreator getSubShapeIdFromIndex(
            int subShapeIndex, SubShapeIdCreator parent) {
        long shapeVa = va();
        long parentVa = parent.va();
        long creatorVa
                = getSubShapeIdFromIndex(shapeVa, subShapeIndex, parentVa);
        SubShapeIdCreator result = new SubShapeIdCreator(creatorVa, true);

        return result;
    }

    /**
     * Access all the sub-shapes as an array.
     *
     * @return a new array of new JVM objects with pre-existing native objects
     * assigned
     */
    public ConstSubShape[] getSubShapes() {
        long shapeVa = va();
        int numSubShapes = getNumSubShapes();
        SubShape[] result = new SubShape[numSubShapes];
        for (int i = 0; i < numSubShapes; ++i) {
            long subShapeVa = getSubShape(shapeVa, i);
            result[i] = new SubShape(subShapeVa);
        }

        return result;
    }

    /**
     * Restore the sub-shape references after invoking
     * {@code sRestoreFromBinaryState()}.
     *
     * @param subshapes the desired sub-shape references (not null)
     */
    public void restoreSubShapeState(ShapeList subshapes) {
        long shapeVa = va();
        long listVa = subshapes.va();
        restoreSubShapeState(shapeVa, listVa);
    }
    // *************************************************************************
    // native private methods

    native private static int getNumSubShapes(long shapeVa);

    native private static long getSubShape(long shapeVa, int subShapeIndex);

    native private static long getSubShapeIdFromIndex(
            long shapeVa, int subShapeIndex, long parentVa);

    native private static void restoreSubShapeState(long shapeVa, long listVa);
}
