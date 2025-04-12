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
package com.github.stephengold.joltjni.readonly;

import com.github.stephengold.joltjni.AaBox;
import com.github.stephengold.joltjni.DebugRenderer;
import com.github.stephengold.joltjni.MassProperties;
import com.github.stephengold.joltjni.ShapeRefC;
import com.github.stephengold.joltjni.Stats;
import com.github.stephengold.joltjni.StreamOut;
import com.github.stephengold.joltjni.SubShapeId;
import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.enumerate.EShapeSubType;
import com.github.stephengold.joltjni.enumerate.EShapeType;
import java.nio.FloatBuffer;

/**
 * Read-only access to a {@code Shape}. (native type: const Shape)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstShape extends ConstJoltPhysicsObject {
    // *************************************************************************
    // new methods exposed

    /**
     * Copy the vertex coordinates of the shape's debug mesh to the specified
     * buffer. The shape is unaffected.
     *
     * @param storeBuffer the buffer to fill with vertex coordinates (not null,
     * modified)
     */
    void copyDebugTriangles(FloatBuffer storeBuffer);

    /**
     * Count the triangles in the shape's debug mesh. The shape is unaffected.
     *
     * @return the count (&gt;0)
     */
    int countDebugTriangles();

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
    void draw(DebugRenderer renderer, RMat44Arg comTransform, Vec3Arg scale,
            ConstColor color, boolean useMaterialColors, boolean wireframe);

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
    void drawGetSupportFunction(
            DebugRenderer renderer, RMat44Arg comTransform, Vec3Arg scale,
            ConstColor color, boolean drawSupportDirection);

    /**
     * Locate the shape's center of mass. The shape is unaffected.
     *
     * @return a new location vector
     */
    Vec3 getCenterOfMass();

    /**
     * Return the radius of the largest sphere that fits inside the shape. The
     * shape is unaffected.
     *
     * @return the radius (&ge;0)
     */
    float getInnerRadius();

    /**
     * Access the leaf shape for the specified sub-shape ID.
     *
     * @param id an ID that indicates the path to the desired leaf shape (not
     * null, unaffected)
     * @param storeRemainder storage for the remainder of the ID after removing
     * the path to the leaf shape (not null, modified)
     * @return a new JVM object with the pre-existing native object assigned, or
     * {@code null} if the ID is invalid
     */
    ConstShape getLeafShape(ConstSubShapeId id, SubShapeId storeRemainder);

    /**
     * Return a bounding box that includes the convex radius. The shape is
     * unaffected.
     *
     * @return a new, mutable box (relative to the shape's center of mass)
     */
    AaBox getLocalBounds();

    /**
     * Copy the shape's mass properties. The shape is unaffected.
     *
     * @return a new, mutable properties object
     */
    MassProperties getMassProperties();

    /**
     * Access the material of the specified sub-shape. The shape is unaffected.
     *
     * @param id which sub-shape (not null, unaffected)
     * @return a new JVM object with the pre-existing native object assigned
     */
    ConstPhysicsMaterial getMaterial(ConstSubShapeId id);

    /**
     * Return the shape's revision count, which is automatically incremented
     * each time the shape is altered. The shape is unaffected.
     *
     * @return the count
     */
    long getRevisionCount();

    /**
     * Copy the statistics. The shape is unaffected.
     *
     * @return a new object
     */
    Stats getStats();

    /**
     * Count how many bits are used to address leaf shapes. The shape is
     * unaffected.
     *
     * @return the number of bits (&ge;0)
     */
    int getSubShapeIdBitsRecursive();

    /**
     * Return the shape's subtype. The shape is unaffected.
     *
     * @return an enum value (not null)
     */
    EShapeSubType getSubType();

    /**
     * Return the shape's type. The shape is unaffected.
     *
     * @return an enum value (not null)
     */
    EShapeType getType();

    /**
     * Return the bounding box including convex radius. The shape is unaffected.
     *
     * @param comTransform the center-of-mass transform to apply to the shape
     * (not null, unaffected)
     * @param scale the scale factors to apply to the shape (not null,
     * unaffected)
     * @return a new, mutable box (in system coordinates)
     */
    AaBox getWorldSpaceBounds(Mat44Arg comTransform, Vec3Arg scale);

    /**
     * Return the bounding box including convex radius. The shape is unaffected.
     *
     * @param comTransform the center-of-mass transform to apply to the shape
     * (not null, unaffected)
     * @param scale the scale factors to apply to the shape (not null,
     * unaffected)
     * @return a new, mutable box (in system coordinates)
     */
    AaBox getWorldSpaceBounds(RMat44Arg comTransform, Vec3Arg scale);

    /**
     * Test whether the specified scale vector is valid for wrapping the current
     * shape in a {@code ScaledShape}. The current shape is unaffected.
     *
     * @param scale the proposed scale vector (not null, unaffected)
     * @return {@code true} if valid, otherwise {@code false}
     */
    boolean isValidScale(Vec3Arg scale);

    /**
     * Transform the specified scale vector such that it will be valid for
     * wrapping the current shape in a {@code ScaledShape}. The current shape is
     * unaffected.
     *
     * @param scale the proposed scale vector (not null, unaffected)
     * @return a new scale vector
     */
    Vec3 makeScaleValid(Vec3Arg scale);

    /**
     * Test whether the shape can be used in a dynamic/kinematic body. The shape
     * is unaffected.
     *
     * @return {@code true} if it can be only be static, otherwise {@code false}
     */
    boolean mustBeStatic();

    /**
     * Save the shape to the specified binary stream. The shape is unaffected.
     *
     * @param stream the stream to write to (not null)
     */
    void saveBinaryState(StreamOut stream);

    /**
     * Create a counted reference to the native {@code Shape}.
     *
     * @return a new JVM object with a new native object assigned
     */
    ShapeRefC toRefC();
}
