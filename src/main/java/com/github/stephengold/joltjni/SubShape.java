/*
Copyright (c) 2024-2026 Stephen Gold

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
import com.github.stephengold.joltjni.readonly.ConstSubShape;
import com.github.stephengold.joltjni.readonly.QuatArg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.FloatBuffer;

/**
 * An element of a compound shape. (native type:
 * {@code CompoundShape::SubShape})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class SubShape extends JoltPhysicsObject implements ConstSubShape {
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
     * Alter the center-of-mass location. (native function: SetPositionCOM)
     *
     * @param location the desired location (not {@code null}, unaffected)
     */
    public void setPositionCom(Vec3Arg location) {
        setPositionCom(location.getX(), location.getY(), location.getZ());
    }

    /**
     * Alter the center-of-mass location. (native function: SetPositionCOM)
     *
     * @param x the desired X coordinate of the center of mass
     * @param y the desired Y coordinate of the center of mass
     * @param z the desired Z coordinate of the center of mass
     */
    public void setPositionCom(float x, float y, float z) {
        long subshapeVa = va();
        setPositionCom(subshapeVa, x, y, z);
    }

    /**
     * Alter the rotation.
     *
     * @param rotation the desired rotation (not {@code null}, unaffected)
     */
    public void setRotation(QuatArg rotation) {
        setRotation(rotation.getX(), rotation.getY(), rotation.getZ(),
                rotation.getW());
    }

    /**
     * Alter the rotation.
     *
     * @param qx the X component of the desired rotation
     * @param qy the Y component of the desired rotation
     * @param qz the Z component of the desired rotation
     * @param qw the W component of the desired rotation
     */
    public void setRotation(float qx, float qy, float qz, float qw) {
        long subshapeVa = va();
        setRotation(subshapeVa, qx, qy, qz, qw);
    }

    /**
     * Alter the coordinate transform.
     *
     * @param offset the desired translation (not {@code null}, unaffected)
     * @param rotation the desired rotation (not {@code null}, unaffected)
     * @param centerOfMass the desired center of mass (not {@code null},
     * unaffected)
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
    // *************************************************************************
    // ConstSubShape methods

    /**
     * Calculate the local transform for this shape, given the scale of the
     * child. The sub-shape is unaffected.
     *
     * @param scale the scale of the child in the local space of this shape (not
     * null, unaffected)
     * @return a new transform matrix
     */
    @Override
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
    @Override
    public Vec3 getPositionCom() {
        Vec3 result = new Vec3();
        getPositionCom(result);
        return result;
    }

    /**
     * Copy the center-of-mass location. The sub-shape is unaffected. (native
     * function: GetPositionCOM)
     *
     * @param out storage for the location (not {@code null}, modified)
     */
    @Override
    public void getPositionCom(Vec3 out) {
        long subshapeVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getPositionCom(subshapeVa, storeFloats);
        out.set(storeFloats);
    }

    /**
     * Copy the rotation. The sub-shape is unaffected.
     *
     * @return a new rotation quaternion
     */
    @Override
    public Quat getRotation() {
        Quat result = new Quat();
        getRotation(result);
        return result;
    }

    /**
     * Copy the rotation. The sub-shape is unaffected.
     *
     * @param out storage for the rotation (not {@code null}, modified)
     */
    @Override
    public void getRotation(Quat out) {
        long subShapeVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getRotation(subShapeVa, storeFloats);
        out.set(storeFloats);
    }

    /**
     * Access the child shape. The sub-shape is unaffected. (native field:
     * mShape)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
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
     * @param scale the scale factors to validate (not {@code null}, unaffected)
     * @return {@code true} if valid, otherwise {@code false}
     */
    @Override
    public boolean isValidScale(Vec3Arg scale) {
        long subshapeVa = va();
        float sx = scale.getX();
        float sy = scale.getY();
        float sz = scale.getZ();
        boolean result = isValidScale(subshapeVa, sx, sy, sz);

        return result;
    }

    /**
     * Transform the specified scale to the local space of the child. The
     * sub-shape is unaffected.
     *
     * @param scale the scale to transform (not {@code null}, unaffected)
     * @return a new vector
     */
    @Override
    public Vec3 transformScale(Vec3Arg scale) {
        long subshapeVa = va();
        float sx = scale.getX();
        float sy = scale.getY();
        float sz = scale.getZ();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        transformScale(subshapeVa, sx, sy, sz, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long getLocalTransformNoScale(
            long subshapeVa, float sx, float sy, float sz);

    native private static void getPositionCom(
            long subShapeVa, FloatBuffer storeFloats);

    native private static void getRotation(
            long subShapeVa, FloatBuffer storeFloats);

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

    native private static void transformScale(long subshapeVa, float sx,
            float sy, float sz, FloatBuffer storeFloats);
}
