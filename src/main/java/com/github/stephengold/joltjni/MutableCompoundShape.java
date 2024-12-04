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

import com.github.stephengold.joltjni.readonly.QuatArg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.ByteBuffer;

/**
 * A {@code CompoundShape} whose subshapes can be modified after the shape is
 * constructed.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class MutableCompoundShape extends CompoundShape {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty compound shape with no subshapes.
     */
    public MutableCompoundShape() {
        long shapeVa = createMutableCompoundShape();
        setVirtualAddress(shapeVa); // not the owner due to ref counting
    }

    /**
     * Instantiate a shape with the specified native object assigned but not
     * owned.
     *
     * @param shapeVa the virtual address of the native object to assign (not
     * zero)
     */
    MutableCompoundShape(long shapeVa) {
        super(shapeVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Add a subshape in the specified position.
     *
     * @param offset the desired offset (not null, unaffected)
     * @param rotation the desired rotation (not null, not zero, unaffected)
     * @param shapeRef a reference to the desired subshape (not null)
     * @return the index of the added subshape
     */
    public int addShape(Vec3Arg offset, QuatArg rotation, ShapeRefC shapeRef) {
        long shapeVa = va();
        float offsetX = offset.getX();
        float offsetY = offset.getY();
        float offsetZ = offset.getZ();
        float rotW = rotation.getW();
        float rotX = rotation.getX();
        float rotY = rotation.getY();
        float rotZ = rotation.getZ();
        long shapeRefVa = shapeRef.va();
        int result = addShape(shapeVa, offsetX, offsetY, offsetZ,
                rotX, rotY, rotZ, rotW, shapeRefVa);

        return result;
    }

    /**
     * Recalculate the center of mass and shift the subshapes accordingly.
     */
    public void adjustCenterOfMass() {
        long shapeVa = va();
        adjustCenterOfMass(shapeVa);
    }

    /**
     * Re-position multiple subshapes.
     *
     * @param startIndex index of the first shape to reposition (&ge;0)
     * @param numSubshapes the number of subshapes to reposition (&ge;0)
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
     * Remove the specified subshape.
     *
     * @param index the index of the subshape to remove (&ge;0)
     */
    public void removeShape(int index) {
        long shapeVa = va();
        removeShape(shapeVa, index);
    }
    // *************************************************************************
    // native private methods

    native private static int addShape(
            long shapeVa, float offsetX, float offsetY, float offsetZ,
            float rotX, float rotY, float rotZ, float rotW, long shapeRefVa);

    native private static void adjustCenterOfMass(long shapeVa);

    native private static long createMutableCompoundShape();

    native private static void modifyShapes(
            long shapeVa, int startIndex, int numSubshapes, ByteBuffer offsets,
            ByteBuffer rotations, int offsetStride, int rotationStide);

    native private static void removeShape(long shapeVa, int index);
}
