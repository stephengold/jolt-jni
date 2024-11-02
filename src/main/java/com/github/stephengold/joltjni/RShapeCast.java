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

import com.github.stephengold.joltjni.readonly.ConstAaBox;
import com.github.stephengold.joltjni.readonly.ConstShape;
import com.github.stephengold.joltjni.readonly.RMat44Arg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * A shape cast with an {@code RVec3} start location.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class RShapeCast extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a shape cast with the specified parameters.
     *
     * @param shape the shape to use (not null, unaffected)
     * @param scale the scale vector to use (not null, unaffected)
     * @param comStart the desired start position (not null, unaffected)
     * @param offset the desired end offset from the start (not null,
     * unaffected)
     */
    public RShapeCast(ConstShape shape, Vec3Arg scale, RMat44Arg comStart,
            Vec3Arg offset) {
        long shapeVa = shape.va();
        float sx = scale.getX();
        float sy = scale.getY();
        float sz = scale.getZ();
        long comStartVa = comStart.va();
        float dx = offset.getX();
        float dy = offset.getY();
        float dz = offset.getZ();
        long shapeCastVa = createRShapeCastNoBounds(
                shapeVa, sx, sy, sz, comStartVa, dx, dy, dz);
        setVirtualAddress(shapeCastVa, () -> free(shapeCastVa));
    }

    /**
     * Instantiate a shape cast with the specified parameters.
     *
     * @param shape the shape to use (not null, unaffected)
     * @param scale the scale vector to use (not null, unaffected)
     * @param comStart the desired start position (not null, unaffected)
     * @param offset the desired end offset from the start (not null,
     * unaffected)
     * @param wsBounds the world-space bounds to use (not null, unaffected)
     */
    public RShapeCast(ConstShape shape, Vec3Arg scale, RMat44Arg comStart,
            Vec3Arg offset, ConstAaBox wsBounds) {
        long shapeVa = shape.va();
        float sx = scale.getX();
        float sy = scale.getY();
        float sz = scale.getZ();
        long comStartVa = comStart.va();
        float dx = offset.getX();
        float dy = offset.getY();
        float dz = offset.getZ();
        long boundsVa = wsBounds.va();
        long shapeCastVa = createRShapeCast(
                shapeVa, sx, sy, sz, comStartVa, dx, dy, dz, boundsVa);
        setVirtualAddress(shapeCastVa, () -> free(shapeCastVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return a point on the ray with the specified fraction. The shape cast is
     * unaffected.
     *
     * @param fraction (0&rarr;start of ray, 1&rarr;end of ray)
     * @return a new location vector
     */
    public RVec3 getPointOnRay(float fraction) {
        long castVa = va();
        double xx = getPointOnRayX(castVa, fraction);
        double yy = getPointOnRayY(castVa, fraction);
        double zz = getPointOnRayZ(castVa, fraction);
        RVec3 result = new RVec3(xx, yy, zz);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createRShapeCast(
            long shapeVa, float sx, float sy, float sz, long comStartVa,
            float dx, float dy, float dz, long boundsVa);

    native private static long createRShapeCastNoBounds(
            long shapeVa, float sx, float sy, float sz, long comStartVa,
            float dx, float dy, float dz);

    native private static void free(long shapeCastVa);

    native private static double getPointOnRayX(long castVa, float fraction);

    native private static double getPointOnRayY(long castVa, float fraction);

    native private static double getPointOnRayZ(long castVa, float fraction);
}
