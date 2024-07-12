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
abstract public class Shape extends NonCopyable implements ConstShape {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a shape with no native object assigned.
     */
    protected Shape() {
    }

    /**
     * Instantiate a shape with the specified native object assigned.
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
     * Instantiate a Shape from its virtual address.
     *
     * @param shapeVa the virtual address of the native object, or zero
     * @return a new instance, or {@code null} if the argument was zero
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
            case Sphere:
                result = new SphereShape(shapeVa);
                break;
            default:
                throw new IllegalArgumentException("type = " + subType);
        }
        return result;
    }
    // *************************************************************************
    // ConstShape methods

    /**
     * Copy the vertex coordinates of the shape's debug mesh to the specified
     * buffer.
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
     * Return the number of triangles in the shape's debug mesh.
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
     * Copy the shape's mass properties.
     *
     * @return a new instance
     */
    @Override
    public MassProperties getMassProperties() {
        long shapeVa = va();
        long propertiesVa = getMassProperties(shapeVa);
        MassProperties result = new MassProperties(propertiesVa);

        return result;
    }

    /**
     * Return the shape's subtype.
     *
     * @return an enum value
     */
    @Override
    public EShapeSubType getSubType() {
        long shapeVa = va();
        int ordinal = getSubType(shapeVa);
        EShapeSubType result = EShapeSubType.values()[ordinal];

        return result;
    }

    /**
     * Return the shape's type.
     *
     * @return an enum value
     */
    @Override
    public EShapeType getType() {
        long shapeVa = va();
        int ordinal = getType(shapeVa);
        EShapeType result = EShapeType.values()[ordinal];

        return result;
    }

    /**
     * Test whether the shape can be used in a dynamic/kinematic body.
     *
     * @return true if it can be only be static, otherwise false
     */
    @Override
    public boolean mustBeStatic() {
        long shapeVa = va();
        boolean result = mustBeStatic(shapeVa);
        return result;
    }
    // *************************************************************************
    // native private methods

    native private static void copyDebugTriangles(
            long shapeVa, int numTriangles, FloatBuffer storeBuffer);

    native private static int countDebugTriangles(long shapeVa);

    native private static long getMassProperties(long shapeVa);

    native private static int getSubType(long shapeVa);

    native private static int getType(long shapeVa);

    native private static boolean mustBeStatic(long shapeVa);
}
