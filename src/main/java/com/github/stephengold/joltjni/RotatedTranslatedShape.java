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
import com.github.stephengold.joltjni.readonly.QuatArg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * Apply rotation followed by translation to an existing {@code Shape} to create
 * a new shape.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class RotatedTranslatedShape extends DecoratedShape {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a shape with the specified native object assigned but not
     * owned.
     *
     * @param shapeVa the virtual address of the native object to assign (not
     * zero)
     */
    RotatedTranslatedShape(long shapeVa) {
        super(shapeVa);
    }

    /**
     * Instantiate a shape on the specified offset, rotation, and base shape.
     *
     * @param offset (not null, unaffected)
     * @param rotation (not null, not zero, unaffected)
     * @param baseShape the base shape (not null)
     */
    public RotatedTranslatedShape(
            Vec3Arg offset, QuatArg rotation, ConstShape baseShape) {
        float offsetX = offset.getX();
        float offsetY = offset.getY();
        float offsetZ = offset.getZ();
        float rotW = rotation.getW();
        float rotX = rotation.getX();
        float rotY = rotation.getY();
        float rotZ = rotation.getZ();
        long baseShapeVa = baseShape.va();
        long scaledShapeVa = createRotatedTranslatedShape(offsetX, offsetY,
                offsetZ, rotX, rotY, rotZ, rotW, baseShapeVa);
        setVirtualAddress(scaledShapeVa, null); // no owner due to ref counting
    }

    /**
     * Instantiate a with on the specified offset, rotation, and base shape.
     *
     * @param offset (not null, unaffected)
     * @param rotation (not null, not zero, unaffected)
     * @param baseShapeRef a reference to the base shape (not null)
     */
    public RotatedTranslatedShape(
            Vec3Arg offset, QuatArg rotation, ShapeRefC baseShapeRef) {
        float offsetX = offset.getX();
        float offsetY = offset.getY();
        float offsetZ = offset.getZ();
        float rotW = rotation.getW();
        float rotX = rotation.getX();
        float rotY = rotation.getY();
        float rotZ = rotation.getZ();
        ConstShape baseShape = baseShapeRef.getPtr();
        long baseShapeVa = baseShape.va();
        long scaledShapeVa = createRotatedTranslatedShape(offsetX, offsetY,
                offsetZ, rotX, rotY, rotZ, rotW, baseShapeVa);
        setVirtualAddress(scaledShapeVa, null); // no owner due to ref counting
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Copy the shape's offset relative to the base shape. The shape is
     * unaffected.
     *
     * @return a new, mutable offset vector
     */
    public Vec3 getPosition() {
        long rtsVa = va();
        float offsetX = getPositionX(rtsVa);
        float offsetY = getPositionY(rtsVa);
        float offsetZ = getPositionZ(rtsVa);
        Vec3 result = new Vec3(offsetX, offsetY, offsetZ);

        return result;
    }

    /**
     * Copy the shape's rotation relative to the base shape. The shape is
     * unaffected.
     *
     * @return a new, mutable rotation quaternion
     */
    public Quat getRotation() {
        long rtsVa = va();
        float rotW = getRotationW(rtsVa);
        float rotX = getRotationX(rtsVa);
        float rotY = getRotationY(rtsVa);
        float rotZ = getRotationZ(rtsVa);
        Quat result = new Quat(rotX, rotY, rotZ, rotW);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createRotatedTranslatedShape(
            float offsetX, float offsetY, float offsetZ,
            float rotX, float rotY, float rotZ, float rotW, long baseShapeVa);

    native private static float getPositionX(long rtsVa);

    native private static float getPositionY(long rtsVa);

    native private static float getPositionZ(long rtsVa);

    native private static float getRotationW(long rtsVa);

    native private static float getRotationX(long rtsVa);

    native private static float getRotationY(long rtsVa);

    native private static float getRotationZ(long rtsVa);
}
