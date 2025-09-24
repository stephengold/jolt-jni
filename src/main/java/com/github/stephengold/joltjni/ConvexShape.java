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

import com.github.stephengold.joltjni.enumerate.ESupportMode;
import com.github.stephengold.joltjni.readonly.ConstConvexShape;
import com.github.stephengold.joltjni.readonly.ConstPlane;
import com.github.stephengold.joltjni.readonly.Mat44Arg;
import com.github.stephengold.joltjni.readonly.RVec3Arg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.FloatBuffer;

/**
 * A type of {@code Shape} that inherently possesses the convex property.
 *
 * @author Stephen Gold sgold@sonic.net
 */
abstract public class ConvexShape extends Shape implements ConstConvexShape {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a shape with no native object assigned.
     */
    ConvexShape() {
    }

    /**
     * Temporarily access the specified shape.
     *
     * @param shapeVa the virtual address of the native object to assign (not
     * zero)
     */
    ConvexShape(long shapeVa) {
        super(shapeVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Alter the density.
     *
     * @param density the desired density
     */
    public void setDensity(float density) {
        long shapeVa = va();
        setDensity(shapeVa, density);
    }
    // *************************************************************************
    // ConstConvexShape methods

    /**
     * Return the density.
     *
     * @return the density
     */
    @Override
    public float getDensity() {
        long shapeVa = va();
        float result = getDensity(shapeVa);

        return result;
    }

    /**
     * Calculate the shape's volume, submerged volume, and center of buoyancy.
     *
     * @param comTransform the local-to-system transform (not null, unaffected)
     * @param scale the scale vector (not null, unaffected)
     * @param surface the boundary of the submerged region (not null,
     * unaffected)
     * @param storeTotalVolume storage for the total volume, or {@code null}
     * @param storeSubmergedVolume storage for the submerged volume, or
     * {@code null}
     * @param storeCenterOfBuoyancy storage for the center of buoyancy, or
     * {@code null}
     * @param baseOffset the base offset to use (ignored if the native library
     * doesn't implement debug rendering)
     */
    @Override
    public void getSubmergedVolume(
            Mat44Arg comTransform, Vec3Arg scale, ConstPlane surface,
            float[] storeTotalVolume, float[] storeSubmergedVolume,
            Vec3 storeCenterOfBuoyancy, RVec3Arg baseOffset) {
        final long shapeVa = va();
        final long comTransformVa = comTransform.targetVa();
        FloatBuffer floatBuffer = Temporaries.floatBuffer1.get();
        floatBuffer.reset();
        scale.put(floatBuffer);
        surface.put(floatBuffer);
        boolean useBase = Jolt.implementsDebugRendering();
        double baseX = useBase ? baseOffset.xx() : 0.;
        double baseY = useBase ? baseOffset.yy() : 0.;
        double baseZ = useBase ? baseOffset.zz() : 0.;
        getSubmergedVolume(
                shapeVa, comTransformVa, floatBuffer, baseX, baseY, baseZ);
        if (storeTotalVolume != null && storeTotalVolume.length > 0) {
            storeTotalVolume[0] = floatBuffer.get(0);
        }
        if (storeSubmergedVolume != null && storeSubmergedVolume.length > 0) {
            storeSubmergedVolume[0] = floatBuffer.get(1);
        }
        if (storeCenterOfBuoyancy != null) {
            storeCenterOfBuoyancy.set(floatBuffer, 2);
        }
    }

    /**
     * Generate a support function for the shape.
     *
     * @param supportMode how to handle convex radius (not null)
     * @param buffer buffer storage (not null)
     * @param scale scale factors to apply (in local coordinates, not null,
     * unaffected)
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public Support getSupportFunction(
            ESupportMode supportMode, SupportBuffer buffer, Vec3Arg scale) {
        long shapeVa = va();
        int ordinal = supportMode.ordinal();
        long bufferVa = buffer.va();
        float sx = scale.getX();
        float sy = scale.getY();
        float sz = scale.getZ();
        long supportVa
                = getSupportFunction(shapeVa, ordinal, bufferVa, sx, sy, sz);
        Support result = new Support(buffer, supportVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static float getDensity(long shapeVa);

    native private static void getSubmergedVolume(
            long shapeVa, long comTransformVa, FloatBuffer floatBuffer,
            double baseX, double baseY, double baseZ);

    native private static long getSupportFunction(long shapeVa, int ordinal,
            long bufferVa, float sx, float sy, float sz);

    native private static void setDensity(long shapeVa, float density);
}
