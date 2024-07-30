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

/**
 * A {@code CompoundShape} whose sub shapes can be modified after the shape is
 * constructed.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class MutableCompoundShape extends CompoundShape {
    // *************************************************************************
    // constructors

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
     * Add a sub shape in the specified position.
     *
     * @param offset the desired offset (not null, unaffected)
     * @param rotation the desired rotation (not null, not zero, unaffected)
     * @param shapeRef a reference to the desired sub shape (not null)
     * @return the index of the added sub shape
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
     * Recalculate the center of mass and shift the sub shapes accordingly.
     */
    public void adjustCenterOfMass() {
        long shapeVa = va();
        adjustCenterOfMass(shapeVa);
    }

    /**
     * Remove the specified sub shape.
     *
     * @param index the index of the sub shape to remove (&ge;0)
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

    native private static void removeShape(long shapeVa, int index);
}
