/*
Copyright (c) 2024-2026 Stephen Gold

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

import com.github.stephengold.joltjni.readonly.ConstVehicleCollisionTester;
import com.github.stephengold.joltjni.template.Ref;
import com.github.stephengold.joltjni.template.RefTarget;

/**
 * Detect collisions between vehicle wheels and the environment.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class VehicleCollisionTester
        extends NonCopyable
        implements ConstVehicleCollisionTester, RefTarget {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a tester with no native object assigned.
     */
    VehicleCollisionTester() {
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Replace the body filter.
     *
     * @param filter the desired filter, or {@code null} for none (default=null)
     */
    public void setBodyFilter(BodyFilter filter) {
        long testerVa = va();
        long filterVa = (filter == null) ? 0L : filter.va();
        setBodyFilter(testerVa, filterVa);
    }

    /**
     * Replace the broadphase-layer filter.
     *
     * @param filter the desired filter, or {@code null} for none (default=null)
     */
    public void setBroadPhaseLayerFilter(BroadPhaseLayerFilter filter) {
        long testerVa = va();
        long filterVa = (filter == null) ? 0L : filter.va();
        setBroadPhaseLayerFilter(testerVa, filterVa);
    }

    /**
     * Replace the object layer used for collision detection when no filters are
     * overridden.
     *
     * @param objectLayer the index of the desired layer (default=-1)
     */
    public void setObjectLayer(int objectLayer) {
        long testerVa = va();
        setObjectLayer(testerVa, objectLayer);
    }

    /**
     * Replace the object-layer filter.
     *
     * @param filter the desired filter, or {@code null} for none (default=null)
     */
    public void setObjectLayerFilter(ObjectLayerFilter filter) {
        long testerVa = va();
        long filterVa = (filter == null) ? 0L : filter.va();
        setObjectLayerFilter(testerVa, filterVa);
    }
    // *************************************************************************
    // new protected methods

    /**
     * Assign a native object (assuming there's none already assigned) and
     * designate the JVM object as a co-owner.
     *
     * @param testerVa the virtual address of the native object to assign (not
     * zero)
     */
    final protected void setVirtualAddressAsCoOwner(long testerVa) {
        long refVa = toRef(testerVa);
        Runnable freeingAction = () -> VehicleCollisionTesterRef.free(refVa);
        setVirtualAddress(testerVa, freeingAction);
    }
    // *************************************************************************
    // ConstVehicleCollisionTester methods

    /**
     * Access the body filter. The tester is unaffected.
     *
     * @return a new JVM object with the pre-existing native object assigned, or
     * {@code null} if none
     */
    @Override
    public BodyFilter getBodyFilter() {
        long testerVa = va();
        long resultVa = getBodyFilter(testerVa);
        BodyFilter result = (resultVa == 0L) ? null : new BodyFilter(resultVa);

        return result;
    }

    /**
     * Access the broadphase-layer filter. The tester is unaffected.
     *
     * @return a new JVM object with the pre-existing native object assigned, or
     * {@code null} if none
     */
    @Override
    public BroadPhaseLayerFilter getBroadPhaseLayerFilter() {
        long testerVa = va();
        long resultVa = getBroadPhaseLayerFilter(testerVa);
        BroadPhaseLayerFilter result = (resultVa == 0L) ? null
                : new BroadPhaseLayerFilter(resultVa);

        return result;
    }

    /**
     * Return the object layer used for collision detection when no filters are
     * overridden. The tester is unaffected.
     *
     * @return the index of the layer
     */
    @Override
    public int getObjectLayer() {
        long testerVa = va();
        int result = getObjectLayer(testerVa);

        return result;
    }

    /**
     * Access the object-layer filter. The tester is unaffected.
     *
     * @return a new JVM object with the pre-existing native object assigned, or
     * {@code null} if none
     */
    @Override
    public ObjectLayerFilter getObjectLayerFilter() {
        long testerVa = va();
        long resultVa = getObjectLayerFilter(testerVa);
        ObjectLayerFilter result = (resultVa == 0L) ? null
                : new ObjectLayerFilter(resultVa);

        return result;
    }
    // *************************************************************************
    // RefTarget methods

    /**
     * Count the active references to the native {@code VehicleCollisionTester}.
     * The tester is unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int getRefCount() {
        long testerVa = va();
        int result = getRefCount(testerVa);

        return result;
    }

    /**
     * Mark the native {@code VehicleCollisionTester} as embedded.
     */
    @Override
    public void setEmbedded() {
        long testerVa = va();
        setEmbedded(testerVa);
    }

    /**
     * Create a counted reference to the current tester.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public Ref toRef() {
        long testerVa = va();
        long refVa = toRef(testerVa);
        VehicleCollisionTesterRef result
                = new VehicleCollisionTesterRef(refVa, this);

        return result;
    }
    // *************************************************************************
    // native methods

    native private static long getBodyFilter(long testerVa);

    native private static long getBroadPhaseLayerFilter(long testerVa);

    native private static int getObjectLayer(long testerVa);

    native private static long getObjectLayerFilter(long testerVa);

    native private static int getRefCount(long testerVa);

    native private static void setBodyFilter(long testerVa, long filterVa);

    native private static void setBroadPhaseLayerFilter(
            long testerVa, long filterVa);

    native private static void setEmbedded(long testerVa);

    native private static void setObjectLayer(long testerVa, int layer);

    native private static void setObjectLayerFilter(
            long testerVa, long filterVa);

    native static long toRef(long testerVa);
}
