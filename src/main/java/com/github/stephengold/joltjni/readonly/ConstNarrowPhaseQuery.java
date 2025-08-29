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

import com.github.stephengold.joltjni.BodyFilter;
import com.github.stephengold.joltjni.BroadPhaseLayerFilter;
import com.github.stephengold.joltjni.CastRayCollector;
import com.github.stephengold.joltjni.CastShapeCollector;
import com.github.stephengold.joltjni.CollidePointCollector;
import com.github.stephengold.joltjni.CollideShapeCollector;
import com.github.stephengold.joltjni.CollideShapeSettings;
import com.github.stephengold.joltjni.ObjectLayerFilter;
import com.github.stephengold.joltjni.RRayCast;
import com.github.stephengold.joltjni.RShapeCast;
import com.github.stephengold.joltjni.RayCastResult;
import com.github.stephengold.joltjni.RayCastSettings;
import com.github.stephengold.joltjni.ShapeCastSettings;
import com.github.stephengold.joltjni.ShapeFilter;
import com.github.stephengold.joltjni.TransformedShapeCollector;

/**
 * Read-only access to a {@code NarrowPhaseQuery}. (native type:
 * {@code const NarrowPhaseQuery})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstNarrowPhaseQuery extends ConstJoltPhysicsObject {
    /**
     * Cast a ray and obtain the nearest hit, if any.
     *
     * @param raycast the desired ray (not null, unaffected)
     * @param hitResult storage for information about the hit, if any (not null,
     * may be modified)
     * @return {@code true} if a hit was found, otherwise {@code false}
     */
    boolean castRay(RRayCast raycast, RayCastResult hitResult);

    /**
     * Cast a ray and obtain the nearest hit, if any.
     *
     * @param raycast the desired ray (not null, unaffected)
     * @param hitResult storage for information about the hit, if any (not null,
     * may be modified)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     * @return {@code true} if a hit was found, otherwise {@code false}
     */
    boolean castRay(RRayCast raycast, RayCastResult hitResult,
            BroadPhaseLayerFilter bplFilter);

    /**
     * Cast a ray and obtain the nearest hit, if any.
     *
     * @param raycast the desired ray (not null, unaffected)
     * @param hitResult storage for information about the hit, if any (not null,
     * may be modified)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     * @param olFilter the object-layer filter to apply (not null, unaffected)
     * @return {@code true} if a hit was found, otherwise {@code false}
     */
    boolean castRay(RRayCast raycast, RayCastResult hitResult,
            BroadPhaseLayerFilter bplFilter, ObjectLayerFilter olFilter);

    /**
     * Cast a ray and obtain the nearest hit, if any.
     *
     * @param raycast the desired ray (not null, unaffected)
     * @param hitResult storage for information about the hit, if any (not null,
     * may be modified)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     * @param olFilter the object-layer filter to apply (not null, unaffected)
     * @param bodyFilter the body filter to apply (not null, unaffected)
     * @return {@code true} if a hit was found, otherwise {@code false}
     */
    boolean castRay(RRayCast raycast, RayCastResult hitResult,
            BroadPhaseLayerFilter bplFilter,
            ObjectLayerFilter olFilter, BodyFilter bodyFilter);

    /**
     * Cast a ray and collect the resulting hits.
     *
     * @param raycast the desired ray (not null, unaffected)
     * @param settings the raycast configuration options to use (not null,
     * unaffected)
     * @param collector the hit collector to use (not null)
     */
    void castRay(RRayCast raycast, RayCastSettings settings,
            CastRayCollector collector);

    /**
     * Cast a ray and collect the resulting hits.
     *
     * @param raycast the desired ray (not null, unaffected)
     * @param settings the raycast configuration options to use (not null,
     * unaffected)
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     */
    void castRay(RRayCast raycast, RayCastSettings settings,
            CastRayCollector collector, BroadPhaseLayerFilter bplFilter);

    /**
     * Cast a ray and collect the resulting hits.
     *
     * @param raycast the desired ray (not null, unaffected)
     * @param settings the raycast configuration options to use (not null,
     * unaffected)
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     * @param olFilter the object-layer filter to apply (not null, unaffected)
     */
    void castRay(RRayCast raycast, RayCastSettings settings,
            CastRayCollector collector, BroadPhaseLayerFilter bplFilter,
            ObjectLayerFilter olFilter);

    /**
     * Cast a ray and collect the resulting hits.
     *
     * @param raycast the desired ray (not null, unaffected)
     * @param settings the raycast configuration options to use (not null,
     * unaffected)
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     * @param olFilter the object-layer filter to apply (not null, unaffected)
     * @param bodyFilter the body filter to apply (not null, unaffected)
     */
    void castRay(RRayCast raycast, RayCastSettings settings,
            CastRayCollector collector, BroadPhaseLayerFilter bplFilter,
            ObjectLayerFilter olFilter, BodyFilter bodyFilter);

    /**
     * Cast a ray and collect the resulting hits.
     *
     * @param raycast the desired ray (not null, unaffected)
     * @param settings the raycast configuration options to use (not null,
     * unaffected)
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     * @param olFilter the object-layer filter to apply (not null, unaffected)
     * @param bodyFilter the body filter to apply (not null, unaffected)
     * @param shapeFilter the shape filter to apply (not null, unaffected)
     */
    void castRay(RRayCast raycast, RayCastSettings settings,
            CastRayCollector collector, BroadPhaseLayerFilter bplFilter,
            ObjectLayerFilter olFilter, BodyFilter bodyFilter,
            ShapeFilter shapeFilter);

    /**
     * Cast a shape and collect the resulting hits.
     *
     * @param shapeCast the desired shape cast (not null, unaffected)
     * @param settings the shape-cast configuration options to use (not null,
     * unaffected)
     * @param base the base location for reporting hits (not null, unaffected,
     * (0,0,0)&rarr;world coordinates)
     * @param collector the hit collector to use (not null)
     */
    void castShape(RShapeCast shapeCast, ShapeCastSettings settings,
            RVec3Arg base, CastShapeCollector collector);

    /**
     * Cast a shape and collect the resulting hits.
     *
     * @param shapeCast the desired shape cast (not null, unaffected)
     * @param settings the shape-cast configuration options to use (not null,
     * unaffected)
     * @param base the base location for reporting hits (not null, unaffected,
     * (0,0,0)&rarr;world coordinates)
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     */
    void castShape(RShapeCast shapeCast, ShapeCastSettings settings,
            RVec3Arg base, CastShapeCollector collector,
            BroadPhaseLayerFilter bplFilter);

    /**
     * Cast a shape and collect the resulting hits.
     *
     * @param shapeCast the desired shape cast (not null, unaffected)
     * @param settings the shape-cast configuration options to use (not null,
     * unaffected)
     * @param base the base location for reporting hits (not null, unaffected,
     * (0,0,0)&rarr;world coordinates)
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     * @param olFilter the object-layer filter to apply (not null, unaffected)
     */
    void castShape(RShapeCast shapeCast, ShapeCastSettings settings,
            RVec3Arg base, CastShapeCollector collector,
            BroadPhaseLayerFilter bplFilter, ObjectLayerFilter olFilter);

    /**
     * Cast a shape and collect the resulting hits.
     *
     * @param shapeCast the desired shape cast (not null, unaffected)
     * @param settings the shape-cast configuration options to use (not null,
     * unaffected)
     * @param base the base location for reporting hits (not null, unaffected,
     * (0,0,0)&rarr;world coordinates)
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     * @param olFilter the object-layer filter to apply (not null, unaffected)
     * @param bodyFilter the body filter to apply (not null, unaffected)
     */
    void castShape(RShapeCast shapeCast, ShapeCastSettings settings,
            RVec3Arg base, CastShapeCollector collector,
            BroadPhaseLayerFilter bplFilter, ObjectLayerFilter olFilter,
            BodyFilter bodyFilter);

    /**
     * Cast a shape and collect the resulting hits.
     *
     * @param shapeCast the desired shape cast (not null, unaffected)
     * @param settings the shape-cast configuration options to use (not null,
     * unaffected)
     * @param base the base location for reporting hits (not null, unaffected,
     * (0,0,0)&rarr;world coordinates)
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     * @param olFilter the object-layer filter to apply (not null, unaffected)
     * @param bodyFilter the body filter to apply (not null, unaffected)
     * @param shapeFilter the shape filter to apply (not null, unaffected)
     */
    void castShape(RShapeCast shapeCast, ShapeCastSettings settings,
            RVec3Arg base, CastShapeCollector collector,
            BroadPhaseLayerFilter bplFilter, ObjectLayerFilter olFilter,
            BodyFilter bodyFilter, ShapeFilter shapeFilter);

    /**
     * Collect leaf shapes that lie within the specified bounds.
     *
     * @param box the bounds (in system coordinates, not null, unaffected)
     * @param collector the hit collector to use (not null)
     */
    void collectTransformedShapes(
            ConstAaBox box, TransformedShapeCollector collector);

    /**
     * Collect leaf shapes that lie within the specified bounds.
     *
     * @param box the bounds (in system coordinates, not null, unaffected)
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     */
    void collectTransformedShapes(
            ConstAaBox box, TransformedShapeCollector collector,
            BroadPhaseLayerFilter bplFilter);

    /**
     * Collect leaf shapes that lie within the specified bounds.
     *
     * @param box the bounds (in system coordinates, not null, unaffected)
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     * @param olFilter the object-layer filter to apply (not null, unaffected)
     */
    void collectTransformedShapes(
            ConstAaBox box, TransformedShapeCollector collector,
            BroadPhaseLayerFilter bplFilter, ObjectLayerFilter olFilter);

    /**
     * Collect leaf shapes that lie within the specified bounds.
     *
     * @param box the bounds (in system coordinates, not null, unaffected)
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     * @param olFilter the object-layer filter to apply (not null, unaffected)
     * @param bodyFilter the body filter to apply (not null, unaffected)
     */
    void collectTransformedShapes(
            ConstAaBox box, TransformedShapeCollector collector,
            BroadPhaseLayerFilter bplFilter, ObjectLayerFilter olFilter,
            BodyFilter bodyFilter);

    /**
     * Collect leaf shapes that lie within the specified bounds.
     *
     * @param box the bounds (in system coordinates, not null, unaffected)
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     * @param olFilter the object-layer filter to apply (not null, unaffected)
     * @param bodyFilter the body filter to apply (not null, unaffected)
     * @param shapeFilter the shape filter to apply (not null, unaffected)
     */
    void collectTransformedShapes(
            ConstAaBox box, TransformedShapeCollector collector,
            BroadPhaseLayerFilter bplFilter, ObjectLayerFilter olFilter,
            BodyFilter bodyFilter, ShapeFilter shapeFilter);

    /**
     * Collect collisions with the specified point.
     *
     * @param point the location of the point to test (not null, unaffected)
     * @param collector the hit collector to use (not null)
     */
    void collidePoint(RVec3Arg point, CollidePointCollector collector);

    /**
     * Collect collisions with the specified point.
     *
     * @param point the location of the point to test (not null, unaffected)
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     */
    void collidePoint(RVec3Arg point, CollidePointCollector collector,
            BroadPhaseLayerFilter bplFilter);

    /**
     * Collect collisions with the specified point.
     *
     * @param point the location of the point to test (not null, unaffected)
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     * @param olFilter the object-layer filter to apply (not null, unaffected)
     */
    void collidePoint(RVec3Arg point, CollidePointCollector collector,
            BroadPhaseLayerFilter bplFilter, ObjectLayerFilter olFilter);

    /**
     * Collect collisions with the specified point.
     *
     * @param point the location of the point to test (not null, unaffected)
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     * @param olFilter the object-layer filter to apply (not null, unaffected)
     * @param bodyFilter the body filter to apply (not null, unaffected)
     */
    void collidePoint(RVec3Arg point, CollidePointCollector collector,
            BroadPhaseLayerFilter bplFilter, ObjectLayerFilter olFilter,
            BodyFilter bodyFilter);

    /**
     * Collect collisions with the specified point.
     *
     * @param point the location of the point to test (not null, unaffected)
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     * @param olFilter the object-layer filter to apply (not null, unaffected)
     * @param bodyFilter the body filter to apply (not null, unaffected)
     * @param shapeFilter the shape filter to apply (not null, unaffected)
     */
    void collidePoint(RVec3Arg point, CollidePointCollector collector,
            BroadPhaseLayerFilter bplFilter, ObjectLayerFilter olFilter,
            BodyFilter bodyFilter, ShapeFilter shapeFilter);

    /**
     * Collect collisions with the specified shape.
     *
     * @param shape the shape to test (not null, unaffected)
     * @param shapeScale the scaling vector for the shape (not null, unaffected)
     * @param comTransform the coordinate transform to apply to the shape's
     * center of mass (not null, unaffected)
     * @param settings the collision settings to use (not null, unaffected)
     * @param base the base location for reporting hits (not null, unaffected,
     * (0,0,0)&rarr;world coordinates)
     * @param collector the hit collector to use (not null)
     */
    void collideShape(ConstShape shape, Vec3Arg shapeScale,
            RMat44Arg comTransform, CollideShapeSettings settings,
            RVec3Arg base, CollideShapeCollector collector);

    /**
     * Collect collisions with the specified shape.
     *
     * @param shape the shape to test (not null, unaffected)
     * @param shapeScale the scaling vector for the shape (not null, unaffected)
     * @param comTransform the coordinate transform to apply to the shape's
     * center of mass (not null, unaffected)
     * @param settings the collision settings to use (not null, unaffected)
     * @param base the base location for reporting hits (not null, unaffected,
     * (0,0,0)&rarr;world coordinates)
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     */
    void collideShape(ConstShape shape, Vec3Arg shapeScale,
            RMat44Arg comTransform, CollideShapeSettings settings,
            RVec3Arg base, CollideShapeCollector collector,
            BroadPhaseLayerFilter bplFilter);

    /**
     * Collect collisions with the specified shape.
     *
     * @param shape the shape to test (not null, unaffected)
     * @param shapeScale the scaling vector for the shape (not null, unaffected)
     * @param comTransform the coordinate transform to apply to the shape's
     * center of mass (not null, unaffected)
     * @param settings the collision settings to use (not null, unaffected)
     * @param base the base location for reporting hits (not null, unaffected,
     * (0,0,0)&rarr;world coordinates)
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     * @param olFilter the object-layer filter to apply (not null, unaffected)
     */
    void collideShape(ConstShape shape, Vec3Arg shapeScale,
            RMat44Arg comTransform, CollideShapeSettings settings,
            RVec3Arg base, CollideShapeCollector collector,
            BroadPhaseLayerFilter bplFilter, ObjectLayerFilter olFilter);

    /**
     * Collect collisions with the specified shape.
     *
     * @param shape the shape to test (not null, unaffected)
     * @param shapeScale the scaling vector for the shape (not null, unaffected)
     * @param comTransform the coordinate transform to apply to the shape's
     * center of mass (not null, unaffected)
     * @param settings the collision settings to use (not null, unaffected)
     * @param base the base location for reporting hits (not null, unaffected,
     * (0,0,0)&rarr;world coordinates)
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     * @param olFilter the object-layer filter to apply (not null, unaffected)
     * @param bodyFilter the body filter to apply (not null, unaffected)
     */
    void collideShape(ConstShape shape, Vec3Arg shapeScale,
            RMat44Arg comTransform, CollideShapeSettings settings,
            RVec3Arg base, CollideShapeCollector collector,
            BroadPhaseLayerFilter bplFilter, ObjectLayerFilter olFilter,
            BodyFilter bodyFilter);

    /**
     * Collect collisions with the specified shape.
     *
     * @param shape the shape to test (not null, unaffected)
     * @param shapeScale the scaling vector for the shape (not null, unaffected)
     * @param comTransform the coordinate transform to apply to the shape's
     * center of mass (not null, unaffected)
     * @param settings the collision settings to use (not null, unaffected)
     * @param base the base location for reporting hits (not null, unaffected,
     * (0,0,0)&rarr;world coordinates)
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     * @param olFilter the object-layer filter to apply (not null, unaffected)
     * @param bodyFilter the body filter to apply (not null, unaffected)
     * @param shapeFilter the shape filter to apply (not null, unaffected)
     */
    void collideShape(ConstShape shape, Vec3Arg shapeScale,
            RMat44Arg comTransform, CollideShapeSettings settings,
            RVec3Arg base, CollideShapeCollector collector,
            BroadPhaseLayerFilter bplFilter, ObjectLayerFilter olFilter,
            BodyFilter bodyFilter, ShapeFilter shapeFilter);
}
