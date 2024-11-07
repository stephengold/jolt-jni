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
 * Apply scaling to an existing {@code Shape}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class ScaledShape extends DecoratedShape {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a shape based on the specified shape and scale factors.
     *
     * @param baseShape the unscaled base shape (not null)
     * @param scaleFactors the desired scale factors (not null)
     */
    public ScaledShape(ConstShape baseShape, Vec3Arg scaleFactors) {
        long baseShapeVa = baseShape.targetVa();
        float scaleX = scaleFactors.getX();
        float scaleY = scaleFactors.getY();
        float scaleZ = scaleFactors.getZ();
        long scaledShapeVa
                = createScaledShape(baseShapeVa, scaleX, scaleY, scaleZ);
        setVirtualAddress(scaledShapeVa, null); // no owner due to ref counting
    }

    /**
     * Instantiate a shape with the specified native object assigned but not
     * owned.
     *
     * @param shapeVa the virtual address of the native object to assign (not
     * zero)
     */
    ScaledShape(long shapeVa) {
        super(shapeVa);
    }

    /**
     * Instantiate a shape based on the specified shape reference and scale
     * factors.
     *
     * @param baseShapeRef a reference to the unscaled base shape (not null)
     * @param scaleFactors the desired scale factors (not null)
     */
    public ScaledShape(ShapeRefC baseShapeRef, Vec3Arg scaleFactors) {
        ConstShape baseShape = baseShapeRef.getPtr();
        long baseShapeVa = baseShape.targetVa();
        float scaleX = scaleFactors.getX();
        float scaleY = scaleFactors.getY();
        float scaleZ = scaleFactors.getZ();
        long scaledShapeVa
                = createScaledShape(baseShapeVa, scaleX, scaleY, scaleZ);
        setVirtualAddress(scaledShapeVa, null); // no owner due to ref counting
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Copy the shape's scale factors. The shape is unaffected.
     *
     * @return a new scaling vector
     */
    public Vec3 getScale() {
        long scaledVa = va();
        float scaleX = getScaleX(scaledVa);
        float scaleY = getScaleY(scaledVa);
        float scaleZ = getScaleZ(scaledVa);
        Vec3 result = new Vec3(scaleX, scaleY, scaleZ);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createScaledShape(
            long baseShapeVa, float scaleX, float scaleY, float scaleZ);

    native private static float getScaleX(long scaledVa);

    native private static float getScaleY(long scaledVa);

    native private static float getScaleZ(long scaledVa);
}
