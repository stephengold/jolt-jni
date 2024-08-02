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

import com.github.stephengold.joltjni.enumerate.EShapeSubType;
import com.github.stephengold.joltjni.enumerate.EShapeType;
import com.github.stephengold.joltjni.readonly.ConstShape;
import com.github.stephengold.joltjni.readonly.Mat44Arg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.FloatBuffer;

/**
 * A counted reference to a {@code ConstShape}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class ShapeRefC extends JoltPhysicsObject implements ConstShape {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a reference with the specified native object assigned.
     *
     * @param refVa the virtual address of the native object to assign (not
     * zero)
     * @param owner true &rarr; make the current object the owner, false &rarr;
     * the current object isn't the owner
     */
    ShapeRefC(long refVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(refVa) : null;
        setVirtualAddress(refVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Temporarily access the referenced {@code ConstShape}.
     *
     * @return a new JVM object that refers to the pre-existing native object
     */
    public ConstShape getPtr() {
        long refVa = va();
        long shapeVa = getPtr(refVa);
        ConstShape result = Shape.newShape(shapeVa);

        return result;
    }
    // *************************************************************************
    // ConstShape methods

    /**
     * Copy the vertex coordinates of the shape's debug mesh to the specified
     * buffer. The shape is unaffected.
     *
     * @param storeBuffer the buffer to fill with vertex coordinates (not null,
     * modified)
     */
    @Override
    public void copyDebugTriangles(FloatBuffer storeBuffer) {
        long refVa = va();
        long shapeVa = getPtr(refVa);
        int numTriangles = storeBuffer.capacity() / 9;
        Shape.copyDebugTriangles(shapeVa, numTriangles, storeBuffer);
    }

    /**
     * Return the number of triangles in the shape's debug mesh. The shape is
     * unaffected.
     *
     * @return the count (&gt;0)
     */
    @Override
    public int countDebugTriangles() {
        long refVa = va();
        long shapeVa = getPtr(refVa);
        int result = Shape.countDebugTriangles(shapeVa);

        assert result > 0 : "result = " + result;
        return result;
    }

    /**
     * Locate the shape's center of mass. The shape is unaffected.
     *
     * @return a new location vector
     */
    @Override
    public Vec3 getCenterOfMass() {
        long refVa = va();
        long shapeVa = getPtr(refVa);
        float x = Shape.getCenterOfMassX(shapeVa);
        float y = Shape.getCenterOfMassY(shapeVa);
        float z = Shape.getCenterOfMassZ(shapeVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Return the radius of the largest sphere that fits inside the shape. The
     * shape is unaffected.
     *
     * @return the radius (&ge;0)
     */
    @Override
    public float getInnerRadius() {
        long refVa = va();
        long shapeVa = getPtr(refVa);
        float result = Shape.getInnerRadius(shapeVa);

        return result;
    }

    /**
     * Return a bounding box that includes the convex radius. The shape is
     * unaffected.
     *
     * @return a new, mutable box (relative to the shape's center of mass)
     */
    @Override
    public AaBox getLocalBounds() {
        long refVa = va();
        long shapeVa = getPtr(refVa);
        long boxVa = Shape.getLocalBounds(shapeVa);
        AaBox result = new AaBox(boxVa, true);

        return result;
    }

    /**
     * Copy the shape's mass properties. The shape is unaffected.
     *
     * @return a new, mutable properties object
     */
    @Override
    public MassProperties getMassProperties() {
        long refVa = va();
        long shapeVa = getPtr(refVa);
        long propertiesVa = Shape.getMassProperties(shapeVa);
        MassProperties result = new MassProperties(propertiesVa, true);

        return result;
    }

    /**
     * Return the shape's subtype. The shape is unaffected.
     *
     * @return an enum value (not null)
     */
    @Override
    public EShapeSubType getSubType() {
        long refVa = va();
        long shapeVa = getPtr(refVa);
        int ordinal = Shape.getSubType(shapeVa);
        EShapeSubType result = EShapeSubType.values()[ordinal];

        return result;
    }

    /**
     * Return the shape's type. The shape is unaffected.
     *
     * @return an enum value (not null)
     */
    @Override
    public EShapeType getType() {
        long refVa = va();
        long shapeVa = getPtr(refVa);
        int ordinal = Shape.getType(shapeVa);
        EShapeType result = EShapeType.values()[ordinal];

        return result;
    }

    /**
     * Return the shape's user data: can be used for anything. The shape is
     * unaffected.
     *
     * @return the value
     */
    @Override
    public long getUserData() {
        long refVa = va();
        long shapeVa = getPtr(refVa);
        long result = Shape.getUserData(shapeVa);

        return result;
    }

    /**
     * Return the bounding box including convex radius. The shape is unaffected.
     *
     * @param comTransform the center-of-mass transform to apply to the shape
     * (not null, unaffected)
     * @param scale the scale factors to apply to the shape (not null,
     * unaffected)
     * @return a new, mutable box (in system coordinates)
     */
    @Override
    public AaBox getWorldSpaceBounds(Mat44Arg comTransform, Vec3Arg scale) {
        long refVa = va();
        long shapeVa = getPtr(refVa);
        long matrixVa = comTransform.va();
        float sx = scale.getX();
        float sy = scale.getY();
        float sz = scale.getZ();
        long boxVa = Shape.getWorldSpaceBounds(shapeVa, matrixVa, sx, sy, sz);
        AaBox result = new AaBox(boxVa, true);

        return result;
    }

    /**
     * Test whether the shape can be used in a dynamic/kinematic body. The shape
     * is unaffected.
     *
     * @return true if it can be only be static, otherwise false
     */
    @Override
    public boolean mustBeStatic() {
        long refVa = va();
        long shapeVa = getPtr(refVa);
        boolean result = Shape.mustBeStatic(shapeVa);

        return result;
    }

    /**
     * Create a counted reference to the native {@code Shape}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public ShapeRefC toRefC() {
        long refVa = va();
        long copyVa = copy(refVa);
        ShapeRefC result = new ShapeRefC(copyVa, true);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long copy(long refVa);

    native private static void free(long refVa);

    native private static long getPtr(long refVa);
}
