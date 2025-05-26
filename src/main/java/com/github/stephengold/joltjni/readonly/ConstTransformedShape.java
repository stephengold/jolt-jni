/*
Copyright (c) 2025 Stephen Gold

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
import com.github.stephengold.joltjni.CastRayCollector;
import com.github.stephengold.joltjni.CastShapeCollector;
import com.github.stephengold.joltjni.CollidePointCollector;
import com.github.stephengold.joltjni.CollideShapeCollector;
import com.github.stephengold.joltjni.CollideShapeSettings;
import com.github.stephengold.joltjni.Float3;
import com.github.stephengold.joltjni.GetTrianglesContext;
import com.github.stephengold.joltjni.Quat;
import com.github.stephengold.joltjni.RRayCast;
import com.github.stephengold.joltjni.RShapeCast;
import com.github.stephengold.joltjni.RVec3;
import com.github.stephengold.joltjni.RayCastResult;
import com.github.stephengold.joltjni.RayCastSettings;
import com.github.stephengold.joltjni.ShapeCastSettings;
import com.github.stephengold.joltjni.ShapeFilter;
import com.github.stephengold.joltjni.SupportingFace;
import com.github.stephengold.joltjni.TransformedShapeCollector;
import java.nio.FloatBuffer;

/**
 * Read-only access to a {@code TransformedShape}. (native type: const
 * TransformedShape)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstTransformedShape extends ConstJoltPhysicsObject {
    /**
     * Cast a ray and find the closest hit. The shape is unaffected.
     *
     * @param raycast the test ray (not null, unaffected)
     * @param storeResult storage for the result (not null, modified)
     * @return {@code true} if a hit was found, otherwise {@code false}
     */
    boolean castRay(RRayCast raycast, RayCastResult storeResult);

    /**
     * Cast a narrow-phase ray and collect any hits. The shape is unaffected.
     *
     * @param raycast the test ray (not null, unaffected)
     * @param settings the raycast configuration options to use (not null,
     * unaffected)
     * @param collector the hit collector to use (not null)
     * @param shapeFilter the shape filter to apply (not null, unaffected)
     */
    void castRay(RRayCast raycast, RayCastSettings settings,
            CastRayCollector collector, ShapeFilter shapeFilter);

    /**
     * Cast a narrow-phase shape and collect any hits. The shape is unaffected.
     *
     * @param shapeCast the desired shape cast (not null, unaffected)
     * @param settings the collision settings to use (not null, unaffected)
     * @param base the base location for reporting hits (not null, unaffected,
     * (0,0,0)&rarr;world coordinates)
     * @param collector the hit collector to use (not null)
     * @param shapeFilter the shape filter to apply (not null, unaffected)
     */
    void castShape(RShapeCast shapeCast, ShapeCastSettings settings,
            RVec3Arg base, CastShapeCollector collector,
            ShapeFilter shapeFilter);

    /**
     * Collect transformed shapes for all leaf shapes of the current shape that
     * collide with the specified bounding box. The shape is unaffected.
     *
     * @param box the region of interest (in system coordinates)
     * @param collector the hit collector to use (not null)
     * @param shapeFilter the shape filter to apply (not null, unaffected)
     */
    void collectTransformedShapes(ConstAaBox box,
            TransformedShapeCollector collector, ShapeFilter shapeFilter);

    /**
     * Collect collisions with the specified point. The shape is unaffected.
     *
     * @param point the location of the point to test (not null, unaffected)
     * @param collector the hit collector to use (not null)
     * @param shapeFilter the shape filter to apply (not null, unaffected)
     */
    void collidePoint(RVec3Arg point, CollidePointCollector collector,
            ShapeFilter shapeFilter);

    /**
     * Collect collisions with the specified shape. The shape is unaffected.
     *
     * @param testShape the shape to test (not null, unaffected)
     * @param shapeScale the scaling vector for the test shape (not null,
     * unaffected)
     * @param comTransform the coordinate transform to apply to the test shape's
     * center of mass (not null, unaffected)
     * @param settings the collision settings to use (not null, unaffected)
     * @param base the base location for reporting hits (not null, unaffected,
     * (0,0,0)&rarr;world coordinates)
     * @param collector the hit collector to use (not null)
     */
    void collideShape(ConstShape testShape, Vec3Arg shapeScale,
            RMat44Arg comTransform, CollideShapeSettings settings,
            RVec3Arg base, CollideShapeCollector collector);

    /**
     * Collect collisions with the specified shape. The shape is unaffected.
     *
     * @param testShape the shape to test (not null, unaffected)
     * @param shapeScale the scaling vector for the test shape (not null,
     * unaffected)
     * @param comTransform the coordinate transform to apply to the test shape's
     * center of mass (not null, unaffected)
     * @param settings the collision settings to use (not null, unaffected)
     * @param base the base location for reporting hits (not null, unaffected,
     * (0,0,0)&rarr;world coordinates)
     * @param collector the hit collector to use (not null)
     * @param shapeFilter the shape filter to apply (not null, unaffected)
     */
    void collideShape(ConstShape testShape, Vec3Arg shapeScale,
            RMat44Arg comTransform, CollideShapeSettings settings,
            RVec3Arg base, CollideShapeCollector collector,
            ShapeFilter shapeFilter);

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
     * Access the underlying coordinate transform. The shape is unaffected.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    RMat44Arg getCenterOfMassTransform();

    /**
     * Access the underlying collision shape. The current instance is
     * unaffected.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    ConstShape getShape();

    /**
     * Copy the location of the center of mass. The shape is unaffected.
     *
     * @return a new object
     */
    RVec3 getShapePositionCom();

    /**
     * Copy the rotation of the shape. The shape is unaffected.
     *
     * @return a new object
     */
    Quat getShapeRotation();

    /**
     * Copy the scale factors of the shape. The shape is unaffected.
     *
     * @return a new object
     */
    Float3 getShapeScale();

    /**
     * Get the vertices of the face that provides support in the specified
     * direction. The shape is unaffected.
     *
     * @param subShapeId which sub-shape to use
     * @param direction the test direction (in world coordinates, not null,
     * unaffected)
     * @param base the base location for reporting face vertices (not null,
     * unaffected, (0,0,0)&rarr;world coordinates)
     * @param storeFace storage for face vertices (not null)
     */
    void getSupportingFace(int subShapeId, Vec3Arg direction,
            RVec3Arg base, SupportingFace storeFace);

    /**
     * Collect triangles for debug visualization. The shape is unaffected.
     *
     * @param storeContext storage for communication with
     * {@code getTrianglesStart()}
     * @param maxTriangles the maximum number of triangles to collect
     * @param storeVertices storage for triangle vertices
     * @return the number of triangles collected, or 0 if no more triangles are
     * available
     */
    int getTrianglesNext(GetTrianglesContext storeContext,
            int maxTriangles, FloatBuffer storeVertices);

    /**
     * Prepare to collect triangles in the specified box for debug
     * visualization. The shape is unaffected.
     *
     * @param storeContext storage for communication with
     * {@code getTrianglesNext()}
     * @param box the region of interest (in system coordinates, not null,
     * unaffected)
     * @param base the base location for reporting triangle vertices (not null,
     * unaffected, (0,0,0)&rarr;world coordinates)
     */
    void getTrianglesStart(GetTrianglesContext storeContext,
            ConstAaBox box, RVec3Arg base);

    /**
     * Return the bounding box including convex radius. The shape is unaffected.
     *
     * @return a new, mutable box (in system coordinates)
     */
    AaBox getWorldSpaceBounds();
}
