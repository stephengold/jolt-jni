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

import com.github.stephengold.joltjni.readonly.ConstShape;
import com.github.stephengold.joltjni.readonly.QuatArg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.ByteBuffer;

/**
 * A {@code CompoundShape} whose sub-shapes can be modified after the shape is
 * constructed.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class MutableCompoundShape extends CompoundShape {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty compound shape with no sub-shapes.
     */
    public MutableCompoundShape() {
        long shapeVa = createMutableCompoundShape();
        setVirtualAddressAsCoOwner(shapeVa);
    }

    /**
     * Instantiate a shape with the specified native object assigned.
     *
     * @param shapeVa the virtual address of the native object to assign (not
     * zero)
     */
    MutableCompoundShape(long shapeVa) {
        setVirtualAddressAsCoOwner(shapeVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Add a sub-shape in the specified position.
     *
     * @param offset the desired offset (not null, unaffected)
     * @param rotation the desired rotation (not null, not zero, unaffected)
     * @param subshape the desired sub-shape (not null)
     * @return the index of the added sub-shape
     */
    public int addShape(Vec3Arg offset, QuatArg rotation, ConstShape subshape) {
        long compoundVa = va();
        float offsetX = offset.getX();
        float offsetY = offset.getY();
        float offsetZ = offset.getZ();
        float rotW = rotation.getW();
        float rotX = rotation.getX();
        float rotY = rotation.getY();
        float rotZ = rotation.getZ();
        long subShapeVa = subshape.targetVa();
        int result = addShape(compoundVa, offsetX, offsetY, offsetZ,
                rotX, rotY, rotZ, rotW, subShapeVa);

        return result;
    }

    /**
     * Recalculate the center of mass and shift the sub-shapes accordingly.
     */
    public void adjustCenterOfMass() {
        long shapeVa = va();
        adjustCenterOfMass(shapeVa);
    }

    /**
     * Reposition multiple sub-shapes.
     *
     * @param startIndex index of the first shape to reposition (&ge;0)
     * @param numSubshapes the number of sub-shapes to reposition (&ge;0)
     * @param offsets the desired offsets (not null, unaffected,
     * length&ge;numSubShapes)
     * @param rotations the desired rotations (not null, unaffected,
     * length&ge;numSubShapes)
     * @param offsetStride the number of bytes between the starting positions of
     * sequential offsets (&gt;0)
     * @param rotationStride the number of bytes between the starting positions
     * of sequential rotations (&gt;0)
     */
    public void modifyShapes(
            int startIndex, int numSubshapes, ByteBuffer offsets,
            ByteBuffer rotations, int offsetStride, int rotationStride) {
        long shapeVa = va();
        modifyShapes(shapeVa, startIndex, numSubshapes, offsets, rotations,
                offsetStride, rotationStride);
    }

    /**
     * Remove the specified sub-shape.
     *
     * @param index the index (not the ID) of the sub-shape to remove (&ge;0)
     */
    public void removeShape(int index) {
        long shapeVa = va();
        removeShape(shapeVa, index);
    }
    // *************************************************************************
    // native private methods

    native private static int addShape(
            long compoundVa, float offsetX, float offsetY, float offsetZ,
            float rotX, float rotY, float rotZ, float rotW, long subShapeVa);

    native private static void adjustCenterOfMass(long shapeVa);

    native private static long createMutableCompoundShape();

    native private static void modifyShapes(
            long shapeVa, int startIndex, int numSubshapes, ByteBuffer offsets,
            ByteBuffer rotations, int offsetStride, int rotationStride);

    native private static void removeShape(long shapeVa, int index);
}
