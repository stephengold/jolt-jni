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

import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * Interface for crude collision detection against the bounding boxes in a
 * {@code PhysicsSpace}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class BroadPhaseQuery extends NonCopyable {
    // *************************************************************************
    // fields

    /**
     * prevent premature garbage collection of the underlying
     * {@code PhysicsSystem}
     */
    final private PhysicsSystem system;
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified native object assigned but not owned.
     *
     * @param system the underlying {@code PhysicsSystem} (not null)
     * @param queryVa the virtual address of the native object to assign (not
     * zero)
     */
    BroadPhaseQuery(PhysicsSystem system, long queryVa) {
        this.system = system;
        setVirtualAddress(queryVa, null);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Cast a ray and collect the resulting hits.
     *
     * @param raycast the test ray (not null, unaffected)
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     * @param olFilter the object-layer filter to apply (not null, unaffected)
     */
    public void castRay(RayCast raycast, RayCastBodyCollector collector,
            BroadPhaseLayerFilter bplFilter, ObjectLayerFilter olFilter) {
        long queryVa = va();
        long raycastVa = raycast.va();
        long collectorVa = collector.va();
        long bplFilterVa = bplFilter.va();
        long olFilterVa = olFilter.va();
        castRay(queryVa, raycastVa, collectorVa, bplFilterVa, olFilterVa);
    }

    /**
     * Collect bodies whose bounding boxes overlap with the specified test box.
     *
     * @param box the test box (not null, unaffected)
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     * @param olFilter the object-layer filter to apply (not null, unaffected)
     */
    public void collideAaBox(AaBox box, CollideShapeBodyCollector collector,
            BroadPhaseLayerFilter bplFilter, ObjectLayerFilter olFilter) {
        long queryVa = va();
        long boxVa = box.va();
        long collectorVa = collector.va();
        long bplFilterVa = bplFilter.va();
        long olFilterVa = olFilter.va();
        collideAaBox(queryVa, boxVa, collectorVa, bplFilterVa, olFilterVa);
    }

    /**
     * Collect bodies whose bounding boxes intersect the specified point.
     *
     * @param point the location to test (not null, unaffected)
     * @param collector the hit collector to use (not null)
     * @param bplFilter the broadphase-layer filter to apply (not null,
     * unaffected)
     * @param olFilter the object-layer filter to apply (not null, unaffected)
     */
    public void collidePoint(Vec3Arg point, CollideShapeBodyCollector collector,
            BroadPhaseLayerFilter bplFilter, ObjectLayerFilter olFilter) {
        long queryVa = va();
        float pointX = point.getX();
        float pointY = point.getY();
        float pointZ = point.getZ();
        long collectorVa = collector.va();
        long bplFilterVa = bplFilter.va();
        long olFilterVa = olFilter.va();
        collidePoint(queryVa, pointX, pointY, pointZ, collectorVa, bplFilterVa,
                olFilterVa);
    }

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
    public void collideSphere(Vec3Arg center, float radius,
            CollideShapeBodyCollector collector,
            BroadPhaseLayerFilter bplFilter, ObjectLayerFilter olFilter) {
        long queryVa = va();
        float centerX = center.getX();
        float centerY = center.getY();
        float centerZ = center.getZ();
        long collectorVa = collector.va();
        long bplFilterVa = bplFilter.va();
        long olFilterVa = olFilter.va();
        collideSphere(queryVa, centerX, centerY, centerZ, radius, collectorVa,
                bplFilterVa, olFilterVa);
    }

    /**
     * Access the underlying {@code PhysicsSystem}.
     *
     * @return the pre-existing instance
     */
    public PhysicsSystem getSystem() {
        return system;
    }
    // *************************************************************************
    // native private methods

    native private static void castRay(long queryVa, long raycastVa,
            long collectorVa, long bplFilterVa, long olFilterVa);

    native private static void collideAaBox(long queryVa, long boxVa,
            long collectorVa, long bplFilterVa, long olFilterVa);

    native private static void collidePoint(long queryVa, float pointX,
            float pointY, float pointZ, long collectorVa, long bplFilterVa,
            long olFilterVa);

    native private static void collideSphere(long queryVa, float centerX,
            float centerY, float centerZ, float radius, long collectorVa,
            long bplFilterVa, long olFilterVa);
}
