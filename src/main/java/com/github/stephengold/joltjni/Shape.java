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

import com.github.stephengold.joltjni.enumerate.EShapeSubType;
import com.github.stephengold.joltjni.enumerate.EShapeType;
import com.github.stephengold.joltjni.readonly.ConstColor;
import com.github.stephengold.joltjni.readonly.ConstPhysicsMaterial;
import com.github.stephengold.joltjni.readonly.ConstShape;
import com.github.stephengold.joltjni.readonly.Mat44Arg;
import com.github.stephengold.joltjni.readonly.RMat44Arg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import com.github.stephengold.joltjni.template.RefTarget;
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
    Shape() {
    }

    /**
     * Temporarily access the specified shape.
     *
     * @param shapeVa the virtual address of the native object to assign (not
     * zero)
     */
    Shape(long shapeVa) {
        setVirtualAddressAsCoOwner(shapeVa);
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
            case Empty:
                result = new EmptyShape(shapeVa);
                break;
            case HeightField:
                result = new HeightFieldShape(shapeVa);
                break;
            case Mesh:
                result = new MeshShape(shapeVa);
                break;
            case MutableCompound:
                result = new MutableCompoundShape(shapeVa);
                break;
            case OffsetCenterOfMass:
                result = new OffsetCenterOfMassShape(shapeVa);
                break;
            case Plane:
                result = new PlaneShape(shapeVa);
                break;
            case RotatedTranslated:
                result = new RotatedTranslatedShape(shapeVa);
                break;
            case Scaled:
                result = new ScaledShape(shapeVa);
                break;
            case SoftBody:
                result = new SoftBodyShape(shapeVa);
                break;
            case Sphere:
                result = new SphereShape(shapeVa);
                break;
            case StaticCompound:
                result = new StaticCompoundShape(shapeVa);
                break;
            case TaperedCapsule:
                result = new TaperedCapsuleShape(shapeVa);
                break;
            case TaperedCylinder:
                result = new TaperedCylinderShape(shapeVa);
                break;
            case Triangle:
                result = new TriangleShape(shapeVa);
                break;
            default:
                throw new IllegalArgumentException("subType = " + subType);
        }
        return result;
    }

    /**
     * Recreate a shape from the specified stream.
     *
     * @param stream the stream to read (not null)
     * @return a new object
     */
    public static ShapeResult sRestoreFromBinaryState(StreamIn stream) {
        long streamVa = stream.va();
        long resultVa = sRestoreFromBinaryState(streamVa);
        ShapeResult result = new ShapeResult(resultVa, true);

        return result;
    }
    // *************************************************************************
    // new protected methods

    /**
     * Assign a native object (assuming there's none already assigned) and
     * designate the JVM object as a co-owner.
     *
     * @param shapeVa the virtual address of the native object to assign (not
     * zero)
     */
    final protected void setVirtualAddressAsCoOwner(long shapeVa) {
        long refVa = toRef(shapeVa);
        Runnable freeingAction = () -> ShapeRef.free(refVa);
        setVirtualAddress(shapeVa, freeingAction);
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
     * Count the triangles in the shape's debug mesh. The shape is unaffected.
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
     * Draw the shape using the specified renderer. The shape is unaffected.
     *
     * @param renderer the renderer to use (not null)
     * @param comTransform the coordinate transform from the shape's center of
     * mass to system coordinates (not null, unaffected)
     * @param scale the desired scaling (not null, unaffected)
     * @param color the desired color if {@code useMaterialColors} is false (not
     * null, unaffected)
     * @param useMaterialColors {@code true} to use the color in the shape's
     * material
     * @param wireframe {@code true} to draw a wire frame, {@code false} for
     * solid triangles
     */
    @Override
    public void draw(DebugRenderer renderer, RMat44Arg comTransform,
            Vec3Arg scale, ConstColor color, boolean useMaterialColors,
            boolean wireframe) {
        long shapeVa = va();
        long rendererVa = renderer.va();
        long transformVa = comTransform.targetVa();
        float scaleX = scale.getX();
        float scaleY = scale.getY();
        float scaleZ = scale.getZ();
        int colorInt = color.getUInt32();
        draw(shapeVa, rendererVa, transformVa, scaleX, scaleY, scaleZ,
                colorInt, useMaterialColors, wireframe);
    }

    /**
     * Draw the support function with the convex radius added back on. The shape
     * is unaffected.
     *
     * @param renderer the renderer to use (not null)
     * @param comTransform transform to apply (not null, unaffected)
     * @param scale scaling to apply (not null, unaffected)
     * @param color the color using for drawing (not null, unaffected)
     * @param drawSupportDirection the direction to draw
     */
    @Override
    public void drawGetSupportFunction(
            DebugRenderer renderer, RMat44Arg comTransform, Vec3Arg scale,
            ConstColor color, boolean drawSupportDirection) {
        long shapeVa = va();
        long rendererVa = renderer.va();
        long transformVa = comTransform.targetVa();
        float scaleX = scale.getX();
        float scaleY = scale.getY();
        float scaleZ = scale.getZ();
        int colorInt = color.getUInt32();
        drawGetSupportFunction(shapeVa, rendererVa, transformVa, scaleX, scaleY,
                scaleZ, colorInt, drawSupportDirection);
    }

    /**
     * Locate the shape's center of mass. The shape is unaffected.
     *
     * @return a new location vector
     */
    @Override
    public Vec3 getCenterOfMass() {
        long shapeVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getCenterOfMass(shapeVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

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
     * Access the leaf shape for the specified sub-shape ID.
     *
     * @param subShapeId a sub-shape ID that specifies the path to the desired
     * leaf shape
     * @param storeRemainderId storage for the remainder of the sub-shape ID
     * after removing the path to the leaf shape (not null, modified)
     * @return a new JVM object with the pre-existing native object assigned, or
     * {@code null} if the ID is invalid
     */
    @Override
    public ConstShape getLeafShape(int subShapeId, int[] storeRemainderId) {
        long currentVa = va();
        long leafVa = getLeafShape(currentVa, subShapeId, storeRemainderId);
        ConstShape result = Shape.newShape(leafVa);

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
     * Access the material of the specified sub-shape. The shape is unaffected.
     *
     * @param subShapeId which sub-shape
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public ConstPhysicsMaterial getMaterial(int subShapeId) {
        long shapeVa = va();
        long materialVa = getMaterial(shapeVa, subShapeId);
        ConstPhysicsMaterial result = new PhysicsMaterial(this, materialVa);

        return result;
    }

    /**
     * Return the shape's revision count, which is automatically incremented
     * each time the shape is altered. The shape is unaffected.
     *
     * @return the count
     */
    @Override
    public long getRevisionCount() {
        long shapeVa = va();
        long result = getUserData(shapeVa);

        return result;
    }

    /**
     * Copy the statistics. The shape is unaffected.
     *
     * @return a new object
     */
    @Override
    public Stats getStats() {
        long shapeVa = va();
        long statsVa = getStats(shapeVa);
        Stats result = new Stats(statsVa, true);

        return result;
    }

    /**
     * Count how many bits are used to address leaf shapes. The shape is
     * unaffected.
     *
     * @return the number of bits (&ge;0)
     */
    @Override
    public int getSubShapeIdBitsRecursive() {
        long shapeVa = va();
        int result = getSubShapeIdBitsRecursive(shapeVa);

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
        long matrixVa = comTransform.targetVa();
        float sx = scale.getX();
        float sy = scale.getY();
        float sz = scale.getZ();
        long boxVa = getWorldSpaceBounds(shapeVa, matrixVa, sx, sy, sz);
        AaBox result = new AaBox(boxVa, true);

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
    public AaBox getWorldSpaceBounds(RMat44Arg comTransform, Vec3Arg scale) {
        long shapeVa = va();
        long rMat44Va = comTransform.targetVa();
        float sx = scale.getX();
        float sy = scale.getY();
        float sz = scale.getZ();
        long boxVa = getWorldSpaceBoundsReal(shapeVa, rMat44Va, sx, sy, sz);
        AaBox result = new AaBox(boxVa, true);

        return result;
    }

    /**
     * Test whether the specified scale vector is valid for wrapping the current
     * shape in a {@code ScaledShape}. The current shape is unaffected.
     *
     * @param scale the proposed scale vector (not null, unaffected)
     * @return {@code true} if valid, otherwise {@code false}
     */
    @Override
    public boolean isValidScale(Vec3Arg scale) {
        long shapeVa = va();
        float sx = scale.getX();
        float sy = scale.getY();
        float sz = scale.getZ();
        boolean result = isValidScale(shapeVa, sx, sy, sz);

        return result;
    }

    /**
     * Transform the specified scale vector such that it will be valid for
     * wrapping the current shape in a {@code ScaledShape}. The current shape is
     * unaffected.
     *
     * @param scale the proposed scale vector (not null, unaffected)
     * @return a new scale vector
     */
    @Override
    public Vec3 makeScaleValid(Vec3Arg scale) {
        long shapeVa = va();
        FloatBuffer floatBuffer = Temporaries.floatBuffer1.get();
        scale.copyTo(floatBuffer);
        makeScaleValid(shapeVa, floatBuffer);
        Vec3 result = new Vec3(floatBuffer);

        return result;
    }

    /**
     * Test whether the shape can be used in a dynamic/kinematic body. The shape
     * is unaffected.
     *
     * @return {@code true} if it can be only be static, otherwise {@code false}
     */
    @Override
    public boolean mustBeStatic() {
        long shapeVa = va();
        boolean result = mustBeStatic(shapeVa);

        return result;
    }

    /**
     * Save the shape to the specified binary stream. The shape is unaffected.
     *
     * @param stream the stream to write to (not null)
     */
    @Override
    public void saveBinaryState(StreamOut stream) {
        long shapeVa = va();
        long streamVa = stream.va();
        saveBinaryState(shapeVa, streamVa);
    }

    /**
     * Scale the current shape, which is unaffected.
     *
     * @param scaleFactors the desired scaling on each axis (not null,
     * unaffected)
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public ShapeResult scaleShape(Vec3Arg scaleFactors) {
        long shapeVa = va();
        float sx = scaleFactors.getX();
        float sy = scaleFactors.getY();
        float sz = scaleFactors.getZ();
        long resultVa = scaleShape(shapeVa, sx, sy, sz);
        ShapeResult result = new ShapeResult(resultVa, true);

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
     * Count the active references to the native {@code Shape}. The shape is
     * unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int getRefCount() {
        long shapeVa = va();
        int result = getRefCount(shapeVa);

        return result;
    }

    /**
     * Mark the native {@code Shape} as embedded.
     */
    @Override
    public void setEmbedded() {
        long shapeVa = va();
        setEmbedded(shapeVa);
    }

    /**
     * Create a counted reference to the native {@code Shape}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public ShapeRef toRef() {
        long shapeVa = va();
        long copyVa = toRef(shapeVa);
        ShapeRef result = new ShapeRef(copyVa, true);

        return result;
    }
    // *************************************************************************
    // native methods

    native static void copyDebugTriangles(
            long shapeVa, int numTriangles, FloatBuffer storeBuffer);

    native static int countDebugTriangles(long shapeVa);

    native static void draw(long shapeVa, long rendererVa,
            long transformVa, float scaleX, float scaleY, float scaleZ,
            int colorInt, boolean useMaterialColors, boolean wireframe);

    native static void drawGetSupportFunction(long shapeVa, long rendererVa,
            long transformVa, float scaleX, float scaleY, float scaleZ,
            int colorInt, boolean drawSupportDirection);

    native static void getCenterOfMass(long shapeVa, FloatBuffer storeFloats);

    native static float getInnerRadius(long shapeVa);

    native static long getLeafShape(
            long currentVa, int subShapeId, int[] storeRemainderId);

    native static long getLocalBounds(long shapeVa);

    native static long getMassProperties(long shapeVa);

    native static long getMaterial(long shapeVa, int subShapeId);

    native static int getRefCount(long shapeVa);

    native static long getStats(long shapeVa);

    native static int getSubShapeIdBitsRecursive(long shapeVa);

    native static int getSubType(long shapeVa);

    native static int getType(long shapeVa);

    native static long getUserData(long shapeVa);

    native static long getWorldSpaceBounds(
            long shapeVa, long matrixVa, float sx, float sy, float sz);

    native static long getWorldSpaceBoundsReal(
            long shapeVa, long rMat44Va, float sx, float sy, float sz);

    native static boolean isValidScale(
            long shapeVa, float sx, float sy, float sz);

    native static void makeScaleValid(long shapeVa, FloatBuffer floatBuffer);

    native static boolean mustBeStatic(long shapeVa);

    native static void saveBinaryState(long shapeVa, long streamVa);

    native static long scaleShape(long shapeVa, float sx, float sy, float sz);

    native private static void setEmbedded(long shapeVa);

    native private static long sRestoreFromBinaryState(long streamVa);

    native private static long toRef(long shapeVa);

    native private static long toRefC(long shapeVa);
}
