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

/**
 * An element of a compound shape. (native type:
 * {@code CompoundShape::SubShape})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class SubShape extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a sub-shape with the specified native object assigned but not
     * owned.
     *
     * @param subshapeVa the virtual address of the native object to assign (not
     * zero)
     */
    SubShape(long subshapeVa) {
        setVirtualAddress(subshapeVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Calculate the local transform for this shape, given the scale of the
     * child. The sub-shape is unaffected.
     *
     * @param scale the scale of the child in the local space of this shape (not
     * null, unaffected)
     * @return a new transform matrix
     */
    public Mat44 getLocalTransformNoScale(Vec3Arg scale) {
        long subshapeVa = va();
        float sx = scale.getX();
        float sy = scale.getY();
        float sz = scale.getZ();
        long resultVa = getLocalTransformNoScale(subshapeVa, sx, sy, sz);
        Mat44 result = new Mat44(resultVa, true);

        return result;
    }

    /**
     * Copy the center-of-mass location. The sub-shape is unaffected. (native
     * function: GetPositionCOM)
     *
     * @return a new vector
     */
    public Vec3 getPositionCom() {
        long subshapeVa = va();
        float x = getPositionComX(subshapeVa);
        float y = getPositionComY(subshapeVa);
        float z = getPositionComZ(subshapeVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Copy the rotation. The sub-shape is unaffected.
     *
     * @return a new rotation quaternion
     */
    public Quat getRotation() {
        long subShapeVa = va();
        float w = getRotationW(subShapeVa);
        float x = getRotationX(subShapeVa);
        float y = getRotationY(subShapeVa);
        float z = getRotationZ(subShapeVa);
        Quat result = new Quat(x, y, z, w);

        return result;
    }

    /**
     * Access the child shape. The sub-shape is unaffected.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public ConstShape getShape() {
        long subShapeVa = va();
        long childShapeVa = getShape(subShapeVa);
        ConstShape result = Shape.newShape(childShapeVa);
        return result;
    }

    /**
     * Test whether the specified scale is valid for the sub-shape. The
     * sub-shape is unaffected.
     *
     * @param scale the scale factors to validate (not null, unaffected)
     * @return {@code true} if valid, otherwise {@code false}
     */
    public boolean isValidScale(Vec3Arg scale) {
        long subshapeVa = va();
        float sx = scale.getX();
        float sy = scale.getY();
        float sz = scale.getZ();
        boolean result = isValidScale(subshapeVa, sx, sy, sz);

        return result;
    }

    /**
     * Alter the center-of-mass location. (native function: SetPositionCOM)
     *
     * @param location the desired location (not null, unaffected)
     */
    public void setPositionCom(Vec3Arg location) {
        long subshapeVa = va();
        float x = location.getX();
        float y = location.getY();
        float z = location.getZ();
        setPositionCom(subshapeVa, x, y, z);
    }

    /**
     * Alter the rotation.
     *
     * @param rotation the desired rotation (not null, unaffected)
     */
    public void setRotation(QuatArg rotation) {
        long subshapeVa = va();
        float qw = rotation.getW();
        float qx = rotation.getX();
        float qy = rotation.getY();
        float qz = rotation.getZ();
        setRotation(subshapeVa, qx, qy, qz, qw);
    }

    /**
     * Alter the coordinate transform.
     *
     * @param offset the desired translation (not null, unaffected)
     * @param rotation the desired rotation (not null, unaffected)
     * @param centerOfMass the desired center of mass (not null, unaffected)
     */
    public void setTransform(
            Vec3Arg offset, QuatArg rotation, Vec3Arg centerOfMass) {
        long subshapeVa = va();
        float ox = offset.getX();
        float oy = offset.getY();
        float oz = offset.getZ();
        float qw = rotation.getW();
        float qx = rotation.getX();
        float qy = rotation.getY();
        float qz = rotation.getZ();
        float cx = centerOfMass.getX();
        float cy = centerOfMass.getY();
        float cz = centerOfMass.getZ();
        setTransform(subshapeVa, ox, oy, oz, qx, qy, qz, qw, cx, cy, cz);
    }

    /**
     * Transform the specified scale to the local space of the child.
     *
     * @param scale the scale to transform (not null, unaffected)
     * @return a new vector
     */
    public Vec3 transformScale(Vec3Arg scale) {
        long subshapeVa = va();
        float sx = scale.getX();
        float sy = scale.getY();
        float sz = scale.getZ();
        float[] storeFloats = new float[3];
        transformScale(subshapeVa, sx, sy, sz, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long getLocalTransformNoScale(
            long subshapeVa, float sx, float sy, float sz);

    native private static float getPositionComX(long subShapeVa);

    native private static float getPositionComY(long subShapeVa);

    native private static float getPositionComZ(long subShapeVa);

    native private static float getRotationW(long subShapeVa);

    native private static float getRotationX(long subShapeVa);

    native private static float getRotationY(long subShapeVa);

    native private static float getRotationZ(long subShapeVa);

    native private static long getShape(long subShapeVa);

    native private static boolean isValidScale(
            long subShapeVa, float sx, float sy, float sz);

    native private static void setPositionCom(
            long subshapeVa, float x, float y, float z);

    native private static void setRotation(
            long subshapeVa, float qx, float qy, float qz, float qw);

    native private static void setTransform(long subshapeVa, float ox, float oy,
            float oz, float qx, float qy, float qz, float qw, float cx,
            float cy, float cz);

    native private static void transformScale(
            long subshapeVa, float sx, float sy, float sz, float[] storeFloats);
}
