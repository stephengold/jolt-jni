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

/**
 * Interface for doing precise collision detection against a
 * {@code PhysicsSpace}, using a broad-phase query followed by a narrow-phase
 * query.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class NarrowPhaseQuery extends NonCopyable {
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
    NarrowPhaseQuery(PhysicsSystem system, long queryVa) {
        this.system = system;
        setVirtualAddress(queryVa, null);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Cast a ray and obtain the nearest hit, if any.
     *
     * @param raycast the desired ray (not null, unaffected)
     * @param hitResult storage for information about the hit, if any (not null,
     * may be modified)
     * @return true if a hit was found, otherwise false
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
     * @return true if a hit was found, otherwise false
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
     * @return true if a hit was found, otherwise false
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
     * @return true if a hit was found, otherwise false
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
     * Access the underlying {@code PhysicsSystem}.
     *
     * @return the pre-existing instance
     */
    public PhysicsSystem getSystem() {
        return system;
    }
    // *************************************************************************
    // native private methods

    native private static boolean castRay(long queryVa, long raycastVa,
            long castResultVa, long bplFilterVa, long olFilterVa,
            long bodyFilterVa);

    native private static void castRay(long queryVa, long raycastVa,
            long settingsVa, long collectorVa, long bplFilterVa,
            long olFilterVa, long bodyFilterVa, long shapeFilterVa);
}
