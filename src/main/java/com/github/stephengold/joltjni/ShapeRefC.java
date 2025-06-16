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
import java.nio.FloatBuffer;

/**
 * A counted reference to a {@code ConstShape}. (native type:
 * {@code RefConst<Shape>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class ShapeRefC extends JoltPhysicsObject implements ConstShape {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty reference.
     */
    public ShapeRefC() {
        long refVa = createEmpty();
        setVirtualAddress(refVa, () -> free(refVa));
    }

    /**
     * Instantiate a reference with the specified native object assigned.
     *
     * @param refVa the virtual address of the native object to assign (not
     * zero)
     * @param owner {@code true} &rarr; make the JVM object the owner,
     * {@code false} &rarr; it isn't the owner
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
     * @return a new JVM object with the pre-existing native object assigned
     */
    public ConstShape getPtr() {
        long shapeVa = targetVa();
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
        long shapeVa = targetVa();
        int numTriangles = storeBuffer.capacity() / 9;
        Shape.copyDebugTriangles(shapeVa, numTriangles, storeBuffer);
    }

    /**
     * Count the triangles in the shape's debug mesh. The shape is unaffected.
     *
     * @return the count (&gt;0)
     */
    @Override
    public int countDebugTriangles() {
        long shapeVa = targetVa();
        int result = Shape.countDebugTriangles(shapeVa);

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
        long shapeVa = targetVa();
        long rendererVa = renderer.va();
        long transformVa = comTransform.targetVa();
        float scaleX = scale.getX();
        float scaleY = scale.getY();
        float scaleZ = scale.getZ();
        int colorInt = color.getUInt32();
        Shape.draw(shapeVa, rendererVa, transformVa, scaleX, scaleY, scaleZ,
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
        long shapeVa = targetVa();
        long rendererVa = renderer.va();
        long transformVa = comTransform.targetVa();
        float scaleX = scale.getX();
        float scaleY = scale.getY();
        float scaleZ = scale.getZ();
        int colorInt = color.getUInt32();
        Shape.drawGetSupportFunction(shapeVa, rendererVa, transformVa,
                scaleX, scaleY, scaleZ, colorInt, drawSupportDirection);
    }

    /**
     * Locate the shape's center of mass. The shape is unaffected.
     *
     * @return a new location vector
     */
    @Override
    public Vec3 getCenterOfMass() {
        long shapeVa = targetVa();
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
        long shapeVa = targetVa();
        float result = Shape.getInnerRadius(shapeVa);

        return result;
    }

    /**
     * Access the leaf shape for the specified sub-shape ID.
     *
     * @param subShapeId an ID that indicates the path to the desired leaf shape
     * (not null, unaffected)
     * @param storeRemainderId storage for the remainder of the ID after
     * removing the path to the leaf shape (not null, modified)
     * @return a new JVM object with the pre-existing native object assigned, or
     * {@code null} if the ID is invalid
     */
    @Override
    public ConstShape getLeafShape(int subShapeId, int[] storeRemainderId) {
        long currentVa = targetVa();
        long leafVa
                = Shape.getLeafShape(currentVa, subShapeId, storeRemainderId);
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
        long shapeVa = targetVa();
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
        long shapeVa = targetVa();
        long propertiesVa = Shape.getMassProperties(shapeVa);
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
        long shapeVa = targetVa();
        long materialVa = Shape.getMaterial(shapeVa, subShapeId);
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
        long shapeVa = targetVa();
        long result = Shape.getUserData(shapeVa);

        return result;
    }

    /**
     * Copy the statistics. The shape is unaffected.
     *
     * @return a new object
     */
    @Override
    public Stats getStats() {
        long shapeVa = targetVa();
        long statsVa = Shape.getStats(shapeVa);
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
        long shapeVa = targetVa();
        int result = Shape.getSubShapeIdBitsRecursive(shapeVa);

        return result;
    }

    /**
     * Return the shape's subtype. The shape is unaffected.
     *
     * @return an enum value (not null)
     */
    @Override
    public EShapeSubType getSubType() {
        long shapeVa = targetVa();
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
        long shapeVa = targetVa();
        int ordinal = Shape.getType(shapeVa);
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
        long shapeVa = targetVa();
        long matrixVa = comTransform.targetVa();
        float sx = scale.getX();
        float sy = scale.getY();
        float sz = scale.getZ();
        long boxVa = Shape.getWorldSpaceBounds(shapeVa, matrixVa, sx, sy, sz);
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
        long shapeVa = targetVa();
        long rMat44Va = comTransform.targetVa();
        float sx = scale.getX();
        float sy = scale.getY();
        float sz = scale.getZ();
        long boxVa
                = Shape.getWorldSpaceBoundsReal(shapeVa, rMat44Va, sx, sy, sz);
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
        long shapeVa = targetVa();
        float sx = scale.getX();
        float sy = scale.getY();
        float sz = scale.getZ();
        boolean result = Shape.isValidScale(shapeVa, sx, sy, sz);

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
        long shapeVa = targetVa();
        FloatBuffer floatBuffer = scale.toBuffer();
        Shape.makeScaleValid(shapeVa, floatBuffer);
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
        long shapeVa = targetVa();
        boolean result = Shape.mustBeStatic(shapeVa);

        return result;
    }

    /**
     * Save the shape to the specified binary stream. The shape is unaffected.
     *
     * @param stream the stream to write to (not null)
     */
    @Override
    public void saveBinaryState(StreamOut stream) {
        long sceneVa = targetVa();
        long streamVa = stream.va();
        Shape.saveBinaryState(sceneVa, streamVa);
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
        long shapeVa = targetVa();
        float sx = scaleFactors.getX();
        float sy = scaleFactors.getY();
        float sz = scaleFactors.getZ();
        long resultVa = Shape.scaleShape(shapeVa, sx, sy, sz);
        ShapeResult result = new ShapeResult(resultVa, true);

        return result;
    }

    /**
     * Return the address of the native {@code Shape}. No objects are affected.
     *
     * @return a virtual address (not zero)
     */
    @Override
    public long targetVa() {
        long refVa = va();
        long result = getPtr(refVa);

        return result;
    }

    /**
     * Create another counted reference to the native {@code Shape}.
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

    native private static long createEmpty();

    native private static void free(long refVa);

    native private static long getPtr(long refVa);
}
