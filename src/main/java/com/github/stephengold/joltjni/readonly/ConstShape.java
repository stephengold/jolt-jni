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
package com.github.stephengold.joltjni.readonly;

import com.github.stephengold.joltjni.AaBox;
import com.github.stephengold.joltjni.DebugRenderer;
import com.github.stephengold.joltjni.MassProperties;
import com.github.stephengold.joltjni.ShapeRefC;
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
     * Return the number of triangles in the shape's debug mesh. The shape is
     * unaffected.
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
     * @param useMaterialColors true to use the color in the shape's material
     * @param wireframe true to draw a wire frame, false for solid triangles
     */
    void draw(DebugRenderer renderer, RMat44Arg comTransform,
            Vec3Arg scale, ConstColor color, boolean useMaterialColors,
            boolean wireframe);

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
     * Return the shape's user data: can be used for anything. The shape is
     * unaffected.
     *
     * @return the value
     */
    long getUserData();

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
     * Test whether the shape can be used in a dynamic/kinematic body. The shape
     * is unaffected.
     *
     * @return true if it can be only be static, otherwise false
     */
    boolean mustBeStatic();

    /**
     * Create a counted reference to the native {@code Shape}.
     *
     * @return a new JVM object with a new native object assigned
     */
    ShapeRefC toRefC();
}
