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

import com.github.stephengold.joltjni.readonly.ConstAaBox;
import com.github.stephengold.joltjni.readonly.ConstShape;
import com.github.stephengold.joltjni.readonly.RMat44Arg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.FloatBuffer;

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
        long shapeVa = shape.targetVa();
        float sx = scale.getX();
        float sy = scale.getY();
        float sz = scale.getZ();
        long comStartVa = comStart.targetVa();
        float dx = offset.getX();
        float dy = offset.getY();
        float dz = offset.getZ();
        long castVa = createRShapeCastNoBounds(
                shapeVa, sx, sy, sz, comStartVa, dx, dy, dz);
        setVirtualAddress(castVa, () -> free(castVa));
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
        long shapeVa = shape.targetVa();
        float sx = scale.getX();
        float sy = scale.getY();
        float sz = scale.getZ();
        long comStartVa = comStart.targetVa();
        float dx = offset.getX();
        float dy = offset.getY();
        float dz = offset.getZ();
        long boundsVa = wsBounds.targetVa();
        long castVa = createRShapeCast(
                shapeVa, sx, sy, sz, comStartVa, dx, dy, dz, boundsVa);
        setVirtualAddress(castVa, () -> free(castVa));
    }

    /**
     * Instantiate a shape cast with the specified native object assigned.
     *
     * @param castVa the virtual address of the native object to assign (not
     * zero)
     * @param owner {@code true} &rarr; make the JVM object the owner,
     * {@code false} &rarr; it isn't the owner
     */
    RShapeCast(long castVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(castVa) : null;
        setVirtualAddress(castVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Create a shape cast using a world transform instead of a center-of-mass
     * transform.
     *
     * @param shape the shape to use (not null, unaffected)
     * @param scale the scale to apply (not null, unaffected)
     * @param worldTransform the world transform to use (not null, unaffected)
     * @param direction the direction of casting (not null, unaffected)
     * @return a new object
     */
    public static RShapeCast sFromWorldTransform(ConstShape shape,
            Vec3Arg scale, RMat44Arg worldTransform, Vec3Arg direction) {
        long shapeVa = shape.targetVa();
        float sx = scale.getX();
        float sy = scale.getY();
        float sz = scale.getZ();
        long transformVa = worldTransform.targetVa();
        float dx = direction.getX();
        float dy = direction.getY();
        float dz = direction.getZ();
        long castVa = createFromWorldTransform(
                shapeVa, sx, sy, sz, transformVa, dx, dy, dz);
        RShapeCast result = new RShapeCast(castVa, true);

        return result;
    }

    /**
     * Copy the starting center of mass. The shape cast is unaffected. (native
     * attribute: mCenterOfMassStart)
     *
     * @return a new location vector
     */
    public RMat44 getCenterOfMassStart() {
        long castVa = va();
        long resultVa = getCenterOfMassStart(castVa);
        RMat44 result = new RMat44(resultVa, true);

        return result;
    }

    /**
     * Copy the direction. The shape cast is unaffected. (native attribute:
     * mDirection)
     *
     * @return a new direction vector
     */
    public Vec3 getDirection() {
        long castVa = va();
        FloatBuffer storeFloats = Jolt.newDirectFloatBuffer(3);
        getDirection(castVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

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

    /**
     * Access the shape. The shape cast is unaffected. (native attribute:
     * mShape)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public ConstShape getShape() {
        long castVa = va();
        long shapeVa = getShape(castVa);
        ConstShape result = Shape.newShape(shapeVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createFromWorldTransform(long shapeVa, float sx,
            float sy, float sz, long transformVa, float dx, float dy, float dz);

    native private static long createRShapeCast(
            long shapeVa, float sx, float sy, float sz, long comStartVa,
            float dx, float dy, float dz, long boundsVa);

    native private static long createRShapeCastNoBounds(
            long shapeVa, float sx, float sy, float sz, long comStartVa,
            float dx, float dy, float dz);

    native private static void free(long castVa);

    native private static long getCenterOfMassStart(long castVa);

    native private static void getDirection(
            long castVa, FloatBuffer storeFloats);

    native private static double getPointOnRayX(long castVa, float fraction);

    native private static double getPointOnRayY(long castVa, float fraction);

    native private static double getPointOnRayZ(long castVa, float fraction);

    native private static long getShape(long castVa);
}
