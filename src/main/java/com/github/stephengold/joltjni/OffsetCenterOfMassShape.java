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

import com.github.stephengold.joltjni.readonly.ConstShape;
import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * Translate the center of mass of a child shape.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class OffsetCenterOfMassShape extends DecoratedShape {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a shape with the specified native object assigned but not
     * owned.
     *
     * @param ocomShapeVa the virtual address of the native object to assign
     * (not zero)
     */
    OffsetCenterOfMassShape(long ocomShapeVa) {
        super(ocomShapeVa);
    }

    /**
     * Instantiate a shape on the specified offset and base shape.
     *
     * @param baseShape the base shape (not null)
     * @param offset (not null, unaffected)
     */
    public OffsetCenterOfMassShape(ConstShape baseShape, Vec3Arg offset) {
        float offsetX = offset.getX();
        float offsetY = offset.getY();
        float offsetZ = offset.getZ();
        long baseShapeVa = baseShape.targetVa();
        long ocomShapeVa = createShape(baseShapeVa, offsetX, offsetY, offsetZ);
        setVirtualAddress(ocomShapeVa, null); // no owner due to ref counting
    }

    /**
     * Instantiate a with on the specified offset, rotation, and base shape.
     *
     * @param baseShapeRef a reference to the base shape (not null)
     * @param offset (not null, unaffected)
     */
    public OffsetCenterOfMassShape(ShapeRefC baseShapeRef, Vec3Arg offset) {
        float offsetX = offset.getX();
        float offsetY = offset.getY();
        float offsetZ = offset.getZ();
        ConstShape baseShape = baseShapeRef.getPtr();
        long baseShapeVa = baseShape.targetVa();
        long ocomShapeVa = createShape(baseShapeVa, offsetX, offsetY, offsetZ);
        setVirtualAddress(ocomShapeVa, null); // no owner due to ref counting
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Copy the shape's offset relative to the base shape. The shape is
     * unaffected.
     *
     * @return a new, mutable offset vector
     */
    public Vec3 getOffset() {
        long ocomShapeVa = va();
        float offsetX = getOffsetX(ocomShapeVa);
        float offsetY = getOffsetY(ocomShapeVa);
        float offsetZ = getOffsetZ(ocomShapeVa);
        Vec3 result = new Vec3(offsetX, offsetY, offsetZ);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createShape(
            long baseShapeVa, float offsetX, float offsetY, float offsetZ);

    native private static float getOffsetX(long ocomShapeVa);

    native private static float getOffsetY(long ocomShapeVa);

    native private static float getOffsetZ(long ocomShapeVa);
}
