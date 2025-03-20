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

import com.github.stephengold.joltjni.readonly.ConstAaBox;
import com.github.stephengold.joltjni.readonly.ConstShape;
import com.github.stephengold.joltjni.readonly.RMat44Arg;
import com.github.stephengold.joltjni.readonly.RVec3Arg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * Interface for precise collision detection against the bodies in a
 * {@code PhysicsSpace}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class NarrowPhaseQuery extends NonCopyable {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified native object assigned but not owned.
     *
     * @param system the containing object, or {@code null} if none
     * @param queryVa the virtual address of the native object to assign (not
     * zero)
     */
    NarrowPhaseQuery(PhysicsSystem system, long queryVa) {
        super(system, queryVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Cast a ray and obtain the nearest hit, if any.
     *
     * @param raycast the desired ray (not null, unaffected)
     * @param hitResult storage for information about the hit, if any (not null,
     * may be modified)
     * @return {@code true} if a hit was found, otherwise {@code false}
     */
    public boolean castRay(RRayCast raycast, RayCastResult hitResult) {
        boolean result
                = castRay(raycast, hitResult, new BroadPhaseLayerFilter());
        return result;
    }

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
    public boolean castRay(RRayCast raycast, RayCastResult hitResult,
            BroadPhaseLayerFilter bplFilter) {
        boolean result = castRay(raycast, hitResult, bplFilter,
                new ObjectLayerFilter());
        return result;
    }

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
    public boolean castRay(RRayCast raycast, RayCastResult hitResult,
            BroadPhaseLayerFilter bplFilter, ObjectLayerFilter olFilter) {
        boolean result = castRay(
                raycast, hitResult, bplFilter, olFilter, new BodyFilter());
        return result;
    }

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
    public boolean castRay(RRayCast raycast, RayCastResult hitResult,
            BroadPhaseLayerFilter bplFilter,
            ObjectLayerFilter olFilter, BodyFilter bodyFilter) {
        long queryVa = va();
        long raycastVa = raycast.va();
        long hitResultVa = hitResult.va();
        long bplFilterVa = bplFilter.va();
        long olFilterVa = olFilter.va();
        long bodyFilterVa = bodyFilter.va();
        boolean result = castRay(queryVa, raycastVa, hitResultVa, bplFilterVa,
                olFilterVa, bodyFilterVa);

        return result;
    }

    /**
     * Cast a ray and collect the resulting hits.
     *
     * @param raycast the desired ray (not null, unaffected)
     * @param settings the raycast configuration options to use (not null,
     * unaffected)
     * @param collector the hit collector to use (not null)
     */
    public void castRay(RRayCast raycast, RayCastSettings settings,
            CastRayCollector collector) {
        castRay(raycast, settings, collector, new BroadPhaseLayerFilter());
    }

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
    public void castRay(RRayCast raycast, RayCastSettings settings,
            CastRayCollector collector, BroadPhaseLayerFilter bplFilter) {
        castRay(raycast, settings, collector, bplFilter,
                new ObjectLayerFilter());
    }

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
    public void castRay(RRayCast raycast, RayCastSettings settings,
            CastRayCollector collector, BroadPhaseLayerFilter bplFilter,
            ObjectLayerFilter olFilter) {
        castRay(raycast, settings, collector, bplFilter, olFilter,
                new BodyFilter());
    }

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
    public void castRay(RRayCast raycast, RayCastSettings settings,
            CastRayCollector collector, BroadPhaseLayerFilter bplFilter,
            ObjectLayerFilter olFilter, BodyFilter bodyFilter) {
        castRay(raycast, settings, collector, bplFilter, olFilter, bodyFilter,
                new ShapeFilter());
    }

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
    public void castRay(RRayCast raycast, RayCastSettings settings,
            CastRayCollector collector, BroadPhaseLayerFilter bplFilter,
            ObjectLayerFilter olFilter, BodyFilter bodyFilter,
            ShapeFilter shapeFilter) {
        long queryVa = va();
        long raycastVa = raycast.va();
        long settingsVa = settings.va();
        long collectorVa = collector.va();
        long bplFilterVa = bplFilter.va();
        long olFilterVa = olFilter.va();
        long bodyFilterVa = bodyFilter.va();
        long shapeFilterVa = shapeFilter.va();
        castRay(queryVa, raycastVa, settingsVa, collectorVa, bplFilterVa,
                olFilterVa, bodyFilterVa, shapeFilterVa);
    }

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
    public void castShape(RShapeCast shapeCast, ShapeCastSettings settings,
            RVec3Arg base, CastShapeCollector collector) {
        castShape(shapeCast, settings, base, collector,
                new BroadPhaseLayerFilter());
    }

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
    public void castShape(RShapeCast shapeCast, ShapeCastSettings settings,
            RVec3Arg base, CastShapeCollector collector,
            BroadPhaseLayerFilter bplFilter) {
        castShape(shapeCast, settings, base, collector, bplFilter,
                new ObjectLayerFilter());
    }

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
    public void castShape(RShapeCast shapeCast, ShapeCastSettings settings,
            RVec3Arg base, CastShapeCollector collector,
            BroadPhaseLayerFilter bplFilter, ObjectLayerFilter olFilter) {
        castShape(shapeCast, settings, base, collector, bplFilter, olFilter,
                new BodyFilter());
    }

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
    public void castShape(RShapeCast shapeCast, ShapeCastSettings settings,
            RVec3Arg base, CastShapeCollector collector,
            BroadPhaseLayerFilter bplFilter, ObjectLayerFilter olFilter,
            BodyFilter bodyFilter) {
        castShape(shapeCast, settings, base, collector, bplFilter, olFilter,
                bodyFilter, new ShapeFilter());
    }

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
    public void castShape(RShapeCast shapeCast, ShapeCastSettings settings,
            RVec3Arg base, CastShapeCollector collector,
            BroadPhaseLayerFilter bplFilter, ObjectLayerFilter olFilter,
            BodyFilter bodyFilter, ShapeFilter shapeFilter) {
        long queryVa = va();
        long shapeCastVa = shapeCast.va();
        long settingsVa = settings.va();
        double baseX = base.xx();
        double baseY = base.yy();
        double baseZ = base.zz();
        long collectorVa = collector.va();
        long bplFilterVa = bplFilter.va();
        long olFilterVa = olFilter.va();
        long bodyFilterVa = bodyFilter.va();
        long shapeFilterVa = shapeFilter.va();
        castShape(queryVa, shapeCastVa, settingsVa, baseX, baseY, baseZ,
                collectorVa, bplFilterVa, olFilterVa, bodyFilterVa,
                shapeFilterVa);
    }

    /**
     * Collect leaf shapes that lie within the specified bounds.
     *
     * @param box the bounds (in system coordinates, not null, unaffected)
     * @param collector the hit collector to use (not null)
     */
    public void collectTransformedShapes(
            ConstAaBox box, TransformedShapeCollector collector) {
        collectTransformedShapes(box, collector, new BroadPhaseLayerFilter());
    }

    /**
     * Collect leaf shapes that lie within the specified bounds.
     *
     * @param box the bounds (in system coordinates, not null, unaffected)
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     */
    public void collectTransformedShapes(
            ConstAaBox box, TransformedShapeCollector collector,
            BroadPhaseLayerFilter bplFilter) {
        collectTransformedShapes(
                box, collector, bplFilter, new ObjectLayerFilter());
    }

    /**
     * Collect leaf shapes that lie within the specified bounds.
     *
     * @param box the bounds (in system coordinates, not null, unaffected)
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     * @param olFilter the object-layer filter to apply (not null, unaffected)
     */
    public void collectTransformedShapes(
            ConstAaBox box, TransformedShapeCollector collector,
            BroadPhaseLayerFilter bplFilter, ObjectLayerFilter olFilter) {
        collectTransformedShapes(
                box, collector, bplFilter, olFilter, new BodyFilter());
    }

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
    public void collectTransformedShapes(
            ConstAaBox box, TransformedShapeCollector collector,
            BroadPhaseLayerFilter bplFilter, ObjectLayerFilter olFilter,
            BodyFilter bodyFilter) {
        collectTransformedShapes(box, collector, bplFilter, olFilter,
                bodyFilter, new ShapeFilter());
    }

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
    public void collectTransformedShapes(
            ConstAaBox box, TransformedShapeCollector collector,
            BroadPhaseLayerFilter bplFilter, ObjectLayerFilter olFilter,
            BodyFilter bodyFilter, ShapeFilter shapeFilter) {
        long queryVa = va();
        long boxVa = box.targetVa();
        long collectorVa = collector.va();
        long bplFilterVa = bplFilter.va();
        long olFilterVa = olFilter.va();
        long bodyFilterVa = bodyFilter.va();
        long shapeFilterVa = shapeFilter.va();
        collectTransformedShapes(queryVa, boxVa, collectorVa, bplFilterVa,
                olFilterVa, bodyFilterVa, shapeFilterVa);
    }

    /**
     * Collect collisions with the specified point.
     *
     * @param point the location of the point to test (not null, unaffected)
     * @param collector the hit collector to use (not null)
     */
    public void collidePoint(RVec3Arg point, CollidePointCollector collector) {
        collidePoint(point, collector, new BroadPhaseLayerFilter());
    }

    /**
     * Collect collisions with the specified point.
     *
     * @param point the location of the point to test (not null, unaffected)
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     */
    public void collidePoint(RVec3Arg point, CollidePointCollector collector,
            BroadPhaseLayerFilter bplFilter) {
        collidePoint(point, collector, bplFilter, new ObjectLayerFilter());
    }

    /**
     * Collect collisions with the specified point.
     *
     * @param point the location of the point to test (not null, unaffected)
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     * @param olFilter the object-layer filter to apply (not null, unaffected)
     */
    public void collidePoint(RVec3Arg point, CollidePointCollector collector,
            BroadPhaseLayerFilter bplFilter, ObjectLayerFilter olFilter) {
        collidePoint(point, collector, bplFilter, olFilter, new BodyFilter());
    }

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
    public void collidePoint(RVec3Arg point, CollidePointCollector collector,
            BroadPhaseLayerFilter bplFilter, ObjectLayerFilter olFilter,
            BodyFilter bodyFilter) {
        collidePoint(point, collector, bplFilter, olFilter, bodyFilter,
                new ShapeFilter());
    }

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
    public void collidePoint(RVec3Arg point, CollidePointCollector collector,
            BroadPhaseLayerFilter bplFilter, ObjectLayerFilter olFilter,
            BodyFilter bodyFilter, ShapeFilter shapeFilter) {
        long queryVa = va();
        double xx = point.xx();
        double yy = point.yy();
        double zz = point.zz();
        long collectorVa = collector.va();
        long bplFilterVa = bplFilter.va();
        long olFilterVa = olFilter.va();
        long bodyFilterVa = bodyFilter.va();
        long shapeFilterVa = shapeFilter.va();
        collidePoint(queryVa, xx, yy, zz, collectorVa, bplFilterVa,
                olFilterVa, bodyFilterVa, shapeFilterVa);
    }

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
    public void collideShape(ConstShape shape, Vec3Arg shapeScale,
            RMat44Arg comTransform, CollideShapeSettings settings,
            RVec3Arg base, CollideShapeCollector collector) {
        collideShape(shape, shapeScale, comTransform, settings, base, collector,
                new BroadPhaseLayerFilter());
    }

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
    public void collideShape(ConstShape shape, Vec3Arg shapeScale,
            RMat44Arg comTransform, CollideShapeSettings settings,
            RVec3Arg base, CollideShapeCollector collector,
            BroadPhaseLayerFilter bplFilter) {
        collideShape(shape, shapeScale, comTransform, settings, base, collector,
                bplFilter, new ObjectLayerFilter());
    }

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
    public void collideShape(ConstShape shape, Vec3Arg shapeScale,
            RMat44Arg comTransform, CollideShapeSettings settings,
            RVec3Arg base, CollideShapeCollector collector,
            BroadPhaseLayerFilter bplFilter, ObjectLayerFilter olFilter) {
        collideShape(shape, shapeScale, comTransform, settings, base, collector,
                bplFilter, olFilter, new BodyFilter());
    }

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
    public void collideShape(ConstShape shape, Vec3Arg shapeScale,
            RMat44Arg comTransform, CollideShapeSettings settings,
            RVec3Arg base, CollideShapeCollector collector,
            BroadPhaseLayerFilter bplFilter, ObjectLayerFilter olFilter,
            BodyFilter bodyFilter) {
        collideShape(shape, shapeScale, comTransform, settings, base, collector,
                bplFilter, olFilter, bodyFilter, new ShapeFilter());
    }

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
    public void collideShape(ConstShape shape, Vec3Arg shapeScale,
            RMat44Arg comTransform, CollideShapeSettings settings,
            RVec3Arg base, CollideShapeCollector collector,
            BroadPhaseLayerFilter bplFilter, ObjectLayerFilter olFilter,
            BodyFilter bodyFilter, ShapeFilter shapeFilter) {
        long queryVa = va();
        long shapeVa = shape.targetVa();
        float sx = shapeScale.getX();
        float sy = shapeScale.getY();
        float sz = shapeScale.getZ();
        long transformVa = comTransform.targetVa();
        long settingsVa = settings.va();
        double baseX = base.xx();
        double baseY = base.yy();
        double baseZ = base.zz();
        long collectorVa = collector.va();
        long bplFilterVa = bplFilter.va();
        long olFilterVa = olFilter.va();
        long bodyFilterVa = bodyFilter.va();
        long shapeFilterVa = shapeFilter.va();
        collideShape(queryVa, shapeVa, sx, sy, sz, transformVa, settingsVa,
                baseX, baseY, baseZ, collectorVa, bplFilterVa,
                olFilterVa, bodyFilterVa, shapeFilterVa);
    }

    /**
     * Access the underlying {@code PhysicsSystem}.
     *
     * @return the pre-existing instance
     */
    public PhysicsSystem getSystem() {
        return (PhysicsSystem) getContainingObject();
    }
    // *************************************************************************
    // native private methods

    native private static boolean castRay(long queryVa, long raycastVa,
            long castResultVa, long bplFilterVa, long olFilterVa,
            long bodyFilterVa);

    native private static void castRay(long queryVa, long raycastVa,
            long settingsVa, long collectorVa, long bplFilterVa,
            long olFilterVa, long bodyFilterVa, long shapeFilterVa);

    native private static void castShape(long queryVa, long shapeCastVa,
            long settingsVa, double baseX, double baseY, double baseZ,
            long collectorVa, long bplFilterVa, long olFilterVa,
            long bodyFilterVa, long shapeFilterVa);

    native private static void collectTransformedShapes(
            long queryVa, long boxVa, long collectorVa, long bplFilterVa,
            long olFilterVa, long bodyFilterVa, long shapeFilterVa);

    native private static void collidePoint(long queryVa, double xx, double yy,
            double zz, long collectorVa, long bplFilterVa,
            long olFilterVa, long bodyFilterVa, long shapeFilterVa);

    native private static void collideShape(long queryVa, long shapeVa,
            float sx, float sy, float sz, long transformVa, long settingsVa,
            double baseX, double baseY, double baseZ, long collectorVa,
            long bplFilterVa, long olFilterVa, long bodyFilterVa,
            long shapeFilterVa);
}
