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
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.FloatBuffer;

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
        setVirtualAddressAsCoOwner(scaledShapeVa);
    }

    /**
     * Instantiate a shape with the specified native object assigned.
     *
     * @param shapeVa the virtual address of the native object to assign (not
     * zero)
     */
    ScaledShape(long shapeVa) {
        setVirtualAddressAsCoOwner(shapeVa);
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
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getScale(scaledVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createScaledShape(
            long baseShapeVa, float scaleX, float scaleY, float scaleZ);

    native private static void getScale(long scaledVa, FloatBuffer storeFloats);
}
