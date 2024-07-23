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

import java.nio.FloatBuffer;

/**
 * The abstract base class for collision shapes.
 *
 * @author Stephen Gold sgold@sonic.net
 */
abstract public class Shape extends NonCopyable
        implements ConstShape, RefTarget {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a shape with no native object assigned.
     */
    protected Shape() {
    }

    /**
     * Instantiate a shape with the specified native object assigned but not
     * owned.
     *
     * @param shapeVa the virtual address of the native object to assign (not
     * zero)
     */
    protected Shape(long shapeVa) {
        super(shapeVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Instantiate a {@code Shape} from its virtual address.
     *
     * @param shapeVa the virtual address of the native object, or zero
     * @return a new JVM object, or {@code null} if the argument was zero
     */
    static Shape newShape(long shapeVa) {
        if (shapeVa == 0L) {
            return null;
        }

        int ordinal = getSubType(shapeVa);
        EShapeSubType subType = EShapeSubType.values()[ordinal];
        Shape result;
        switch (subType) {
            case Box:
                result = new BoxShape(shapeVa);
                break;
            case Capsule:
                result = new CapsuleShape(shapeVa);
                break;
            case ConvexHull:
                result = new ConvexHullShape(shapeVa);
                break;
            case Cylinder:
                result = new CylinderShape(shapeVa);
                break;
            case HeightField:
                result = new HeightFieldShape(shapeVa);
                break;
            case Mesh:
                result = new MeshShape(shapeVa);
                break;
            case Scaled:
                result = new ScaledShape(shapeVa);
                break;
            case Sphere:
                result = new SphereShape(shapeVa);
                break;
            default:
                throw new IllegalArgumentException("type = " + subType);
        }
        return result;
    }

    /**
     * Alter the shape's user data.
     *
     * @param value the desired value (default=0)
     */
    public void setUserData(long value) {
        long shapeVa = va();
        setUserData(shapeVa, value);
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
        long shapeVa = va();
        int numTriangles = storeBuffer.capacity() / 9;
        copyDebugTriangles(shapeVa, numTriangles, storeBuffer);
    }

    /**
     * Return the number of triangles in the shape's debug mesh. The shape is
     * unaffected.
     *
     * @return the count (&gt;0)
     */
    @Override
    public int countDebugTriangles() {
        long shapeVa = va();
        int result = countDebugTriangles(shapeVa);

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
        long shapeVa = va();
        float x = getCenterOfMassX(shapeVa);
        float y = getCenterOfMassY(shapeVa);
        float z = getCenterOfMassZ(shapeVa);
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
        long shapeVa = va();
        float result = getInnerRadius(shapeVa);

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
        long shapeVa = va();
        long boxVa = getLocalBounds(shapeVa);
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
        long shapeVa = va();
        long propertiesVa = getMassProperties(shapeVa);
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
        long shapeVa = va();
        int ordinal = getSubType(shapeVa);
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
        long shapeVa = va();
        int ordinal = getType(shapeVa);
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
        long shapeVa = va();
        long result = getUserData(shapeVa);

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
        long shapeVa = va();
        long matrixVa = comTransform.va();
        float sx = scale.getX();
        float sy = scale.getY();
        float sz = scale.getZ();
        long boxVa = getWorldSpaceBounds(shapeVa, matrixVa, sx, sy, sz);
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
        long shapeVa = va();
        boolean result = mustBeStatic(shapeVa);

        return result;
    }

    /**
     * Create a counted reference to the native {@code Shape}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public ShapeRefC toRefC() {
        long shapeVa = va();
        long refVa = toRefC(shapeVa);
        ShapeRefC result = new ShapeRefC(refVa, true);

        return result;
    }
    // *************************************************************************
    // RefTarget methods

    /**
     * Count known references to the shape.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int getRefCount() {
        long shapeVa = va();
        int result = getRefCount(shapeVa);

        return result;
    }
    // *************************************************************************
    // native methods

    native static void copyDebugTriangles(
            long shapeVa, int numTriangles, FloatBuffer storeBuffer);

    native static int countDebugTriangles(long shapeVa);

    native static float getCenterOfMassX(long shapeVa);

    native static float getCenterOfMassY(long shapeVa);

    native static float getCenterOfMassZ(long shapeVa);

    native static float getInnerRadius(long shapeVa);

    native static long getLocalBounds(long shapeVa);

    native static long getMassProperties(long shapeVa);

    native private static int getRefCount(long shapeVa);

    native static int getSubType(long shapeVa);

    native static int getType(long shapeVa);

    native static long getUserData(long shapeVa);

    native static long getWorldSpaceBounds(
            long shapeVa, long matrixVa, float sx, float sy, float sz);

    native static boolean mustBeStatic(long shapeVa);

    native static void setUserData(long shapeVa, long value);

    native private static long toRefC(long shapeVa);
}
