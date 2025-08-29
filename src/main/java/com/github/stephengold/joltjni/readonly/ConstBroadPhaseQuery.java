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

import com.github.stephengold.joltjni.AaBoxCast;
import com.github.stephengold.joltjni.BroadPhaseLayerFilter;
import com.github.stephengold.joltjni.CastShapeBodyCollector;
import com.github.stephengold.joltjni.CollideShapeBodyCollector;
import com.github.stephengold.joltjni.ObjectLayerFilter;
import com.github.stephengold.joltjni.RayCast;
import com.github.stephengold.joltjni.RayCastBodyCollector;

/**
 * Read-only access to a {@code BroadPhaseQuery}. (native type:
 * {@code const BroadPhaseQuery})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstBroadPhaseQuery extends ConstJoltPhysicsObject {
    /**
     * Cast a box along a line segment and collect the resulting hits.
     *
     * @param boxCast the test box and route (not null, unaffected)
     * @param collector the hit collector to use (not null)
     */
    void castAaBox(AaBoxCast boxCast, CastShapeBodyCollector collector);

    /**
     * Cast a box along a line segment and collect the resulting hits.
     *
     * @param boxCast the test box and route (not null, unaffected)
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     */
    void castAaBox(AaBoxCast boxCast, CastShapeBodyCollector collector,
            BroadPhaseLayerFilter bplFilter);

    /**
     * Cast a box along a line segment and collect the resulting hits.
     *
     * @param boxCast the test box and route (not null, unaffected)
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     * @param olFilter the object-layer filter to apply (not null, unaffected)
     */
    void castAaBox(AaBoxCast boxCast, CastShapeBodyCollector collector,
            BroadPhaseLayerFilter bplFilter, ObjectLayerFilter olFilter);

    /**
     * Cast a ray and collect the resulting hits.
     *
     * @param raycast the test ray (not null, unaffected)
     * @param collector the hit collector to use (not null)
     */
    void castRay(RayCast raycast, RayCastBodyCollector collector);

    /**
     * Cast a ray and collect the resulting hits.
     *
     * @param raycast the test ray (not null, unaffected)
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     */
    void castRay(RayCast raycast, RayCastBodyCollector collector,
            BroadPhaseLayerFilter bplFilter);

    /**
     * Cast a ray and collect the resulting hits.
     *
     * @param raycast the test ray (not null, unaffected)
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     * @param olFilter the object-layer filter to apply (not null, unaffected)
     */
    void castRay(RayCast raycast, RayCastBodyCollector collector,
            BroadPhaseLayerFilter bplFilter, ObjectLayerFilter olFilter);

    /**
     * Collect bodies whose bounding boxes overlap with the specified test box.
     *
     * @param box the test box (not null, unaffected)
     * @param collector the hit collector to use (not null)
     */
    void collideAaBox(ConstAaBox box, CollideShapeBodyCollector collector);

    /**
     * Collect bodies whose bounding boxes overlap with the specified test box.
     *
     * @param box the test box (not null, unaffected)
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     */
    void collideAaBox(ConstAaBox box, CollideShapeBodyCollector collector,
            BroadPhaseLayerFilter bplFilter);

    /**
     * Collect bodies whose bounding boxes overlap with the specified test box.
     *
     * @param box the test box (not null, unaffected)
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     * @param olFilter the object-layer filter to apply (not null, unaffected)
     */
    void collideAaBox(ConstAaBox box, CollideShapeBodyCollector collector,
            BroadPhaseLayerFilter bplFilter, ObjectLayerFilter olFilter);

    /**
     * Collect bodies whose bounding boxes intersect the specified oriented box.
     *
     * @param box the box to test (not null, unaffected)
     * @param collector the hit collector to use (not null)
     */
    void collideOrientedBox(
            ConstOrientedBox box, CollideShapeBodyCollector collector);

    /**
     * Collect bodies whose bounding boxes intersect the specified oriented box.
     *
     * @param box the box to test (not null, unaffected)
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     */
    void collideOrientedBox(
            ConstOrientedBox box, CollideShapeBodyCollector collector,
            BroadPhaseLayerFilter bplFilter);

    /**
     * Collect bodies whose bounding boxes intersect the specified oriented box.
     *
     * @param box the box to test (not null, unaffected)
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     * @param olFilter the object-layer filter to apply (not null, unaffected)
     */
    void collideOrientedBox(
            ConstOrientedBox box, CollideShapeBodyCollector collector,
            BroadPhaseLayerFilter bplFilter, ObjectLayerFilter olFilter);

    /**
     * Collect bodies whose bounding boxes intersect the specified point.
     *
     * @param point the location to test (not null, unaffected)
     * @param collector the hit collector to use (not null)
     */
    void collidePoint(Vec3Arg point, CollideShapeBodyCollector collector);

    /**
     * Collect bodies whose bounding boxes intersect the specified point.
     *
     * @param point the location to test (not null, unaffected)
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     */
    void collidePoint(Vec3Arg point, CollideShapeBodyCollector collector,
            BroadPhaseLayerFilter bplFilter);

    /**
     * Collect bodies whose bounding boxes intersect the specified point.
     *
     * @param point the location to test (not null, unaffected)
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     * @param olFilter the object-layer filter to apply (not null, unaffected)
     */
    void collidePoint(Vec3Arg point, CollideShapeBodyCollector collector,
            BroadPhaseLayerFilter bplFilter, ObjectLayerFilter olFilter);

    /**
     * Collect bodies whose bounding boxes intersect the specified test sphere.
     *
     * @param center the center of the test sphere (not null, unaffected)
     * @param radius the radius of the test sphere
     * @param collector the hit collector to use (not null)
     */
    void collideSphere(
            Vec3Arg center, float radius, CollideShapeBodyCollector collector);

    /**
     * Collect bodies whose bounding boxes intersect the specified test sphere.
     *
     * @param center the center of the test sphere (not null, unaffected)
     * @param radius the radius of the test sphere
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     */
    void collideSphere(
            Vec3Arg center, float radius, CollideShapeBodyCollector collector,
            BroadPhaseLayerFilter bplFilter);

    /**
     * Collect bodies whose bounding boxes intersect the specified test sphere.
     *
     * @param center the center of the test sphere (not null, unaffected)
     * @param radius the radius of the test sphere
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     * @param olFilter the object-layer filter to apply (not null, unaffected)
     */
    void collideSphere(
            Vec3Arg center, float radius, CollideShapeBodyCollector collector,
            BroadPhaseLayerFilter bplFilter, ObjectLayerFilter olFilter);
}
