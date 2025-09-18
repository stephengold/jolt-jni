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
package com.github.stephengold.joltjni;

import com.github.stephengold.joltjni.enumerate.EFilterMode;
import com.github.stephengold.joltjni.enumerate.ValidateResult;

/**
 * A customizable {@code ContactListener} with configurable filtering.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class FilteredContactListener extends ContactListener {
    // *************************************************************************
    // fields

    /**
     * protect the assigned {@code BodyFilter} from garbage collection
     */
    private BodyFilter bodyFilter;
    /**
     * protect the assigned {@code BroadPhaseLayerFilter} from garbage
     * collection
     */
    private BroadPhaseLayerFilter bplFilter;
    /**
     * protect the assigned {@code ObjectLayerPairFilter} from garbage
     * collection
     */
    private ObjectLayerPairFilter olpFilter;
    // *************************************************************************
    // constructors

    /**
     * Instantiate a listener with no filters.
     */
    public FilteredContactListener() {
        long listenerVa = createDefault();
        setVirtualAddressAsOwner(listenerVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Access the body filter.
     *
     * @return the pre-existing instance, or {@code null} if none
     */
    public BodyFilter getBodyFilter() {
        return bodyFilter;
    }

    /**
     * Return the body-filter mode.
     *
     * @return a enum value (not {@code null})
     */
    public EFilterMode getBodyFilterMode() {
        long listenerVa = va();
        int ordinal = getBodyFilterMode(listenerVa);
        EFilterMode result = EFilterMode.values()[ordinal];

        return result;
    }

    /**
     * Access the broadphase-layer filter
     *
     * @return the pre-existing instance, or {@code null} if none
     */
    public BroadPhaseLayerFilter getBroadPhaseLayerFilter() {
        return bplFilter;
    }

    /**
     * Return the broadphase-layer filter mode.
     *
     * @return a enum value (not {@code null})
     */
    public EFilterMode getBroadPhaseLayerFilterMode() {
        long listenerVa = va();
        int ordinal = getBroadPhaseLayerFilterMode(listenerVa);
        EFilterMode result = EFilterMode.values()[ordinal];

        return result;
    }

    /**
     * Access the object-layer pair filter.
     *
     * @return the pre-existing instance, or {@code null} if none
     */
    public ObjectLayerPairFilter getObjectLayerPairFilter() {
        return olpFilter;
    }

    /**
     * Return the object-layer filter mode.
     *
     * @return a enum value (not {@code null})
     */
    public EFilterMode getObjectLayerFilterMode() {
        long listenerVa = va();
        int ordinal = getObjectLayerFilterMode(listenerVa);
        EFilterMode result = EFilterMode.values()[ordinal];

        return result;
    }

    /**
     * Callback invoked (by native code) each time a new contact point is
     * detected. Meant to be overridden.
     *
     * @param body1Va the virtual address of the first body in contact (not
     * zero)
     * @param body2Va the virtual address of the 2nd body in contact (not zero)
     * @param manifoldVa the virtual address of the contact manifold (not zero)
     * @param settingsVa the virtual address of the contact settings (not zero)
     */
    public void onContactAdded(
            long body1Va, long body2Va, long manifoldVa, long settingsVa) {
        // do nothing
    }

    /**
     * Callback invoked (by native code) each time a contact is detected that
     * was also detected during the previous update. Meant to be overridden.
     *
     * @param body1Va the virtual address of the first body in contact (not
     * zero)
     * @param body2Va the virtual address of the 2nd body in contact (not zero)
     * @param manifoldVa the virtual address of the contact manifold (not zero)
     * @param settingsVa the virtual address of the contact settings (not zero)
     */
    public void onContactPersisted(
            long body1Va, long body2Va, long manifoldVa, long settingsVa) {
        // do nothing
    }

    /**
     * Callback invoked (by native code) each time a contact that was detected
     * during the previous update is no longer detected. Meant to be overridden.
     *
     * @param pairVa the virtual address of the {@code SubShapeIDPair} (not
     * zero)
     */
    public void onContactRemoved(long pairVa) {
        // do nothing
    }

    /**
     * Callback invoked (by native code) after detecting collision between a
     * pair of bodies, but before invoking {@code onContactAdded()} and before
     * adding the contact constraint. Meant to be overridden.
     *
     * @param body1Va the virtual address of the first body in contact (not
     * zero)
     * @param body2Va the virtual address of the 2nd body in contact (not zero)
     * @param baseOffsetX the X component of the base offset
     * @param baseOffsetY the Y component of the base offset
     * @param baseOffsetZ the Z component of the base offset
     * @param collisionResultVa the virtual address of the
     * {@code CollideShapeResult} (not zero)
     * @return how to the contact should be processed (an ordinal of
     * {@code ValidateResult})
     */
    public int onContactValidate(long body1Va, long body2Va, double baseOffsetX,
            double baseOffsetY, double baseOffsetZ, long collisionResultVa) {
        int result = ValidateResult.AcceptAllContactsForThisBodyPair.ordinal();
        return result;
    }

    /**
     * Replace the listener's body filter.
     *
     * @param bodyFilter the desired filter, or {@code null} for none
     * (default=none)
     * @return the modified listener, for chaining
     */
    public FilteredContactListener setBodyFilter(BodyFilter bodyFilter) {
        this.bodyFilter = bodyFilter;

        long listenerVa = va();
        long bodyFilterVa = bodyFilter.va();
        setBodyFilter(listenerVa, bodyFilterVa);

        return this;
    }

    /**
     * Alter the body-filter mode.
     *
     * @param mode the desired mode (not {@code null}, default=Both)
     * @return the modified listener, for chaining
     */
    public FilteredContactListener setBodyFilterMode(EFilterMode mode) {
        long listenerVa = va();
        int ordinal = mode.ordinal();
        setBodyFilterMode(listenerVa, ordinal);

        return this;
    }

    /**
     * Replace the listener's broadphase-layer filter.
     *
     * @param bplFilter the desired filter, or {@code null} for none
     * (default=none)
     * @return the modified listener, for chaining
     */
    public FilteredContactListener setBroadPhaseLayerFilter(
            BroadPhaseLayerFilter bplFilter) {
        this.bplFilter = bplFilter;

        long listenerVa = va();
        long bplFilterVa = bplFilter.va();
        setBroadPhaseLayerFilter(listenerVa, bplFilterVa);

        return this;
    }

    /**
     * Alter the broadphase-layer filter mode.
     *
     * @param mode the desired mode (not {@code null}, default=Both)
     * @return the modified listener, for chaining
     */
    public FilteredContactListener setBroadPhaseLayerFilterMode(
            EFilterMode mode) {
        long listenerVa = va();
        int ordinal = mode.ordinal();
        setBroadPhaseLayerFilterMode(listenerVa, ordinal);

        return this;
    }

    /**
     * Replace the listener's layer-pair filter.
     *
     * @param olpFilter the desired filter, or {@code null} for none
     * (default=none)
     * @return the modified listener, for chaining
     */
    public FilteredContactListener setObjectLayerPairFilter(
            ObjectLayerPairFilter olpFilter) {
        this.olpFilter = olpFilter;

        long listenerVa = va();
        long olpFilterVa = olpFilter.va();
        setObjectLayerPairFilter(listenerVa, olpFilterVa);

        return this;
    }
    // *************************************************************************
    // native private methods

    native private long createDefault();

    native private int getBodyFilterMode(long listenerVa);

    native private int getBroadPhaseLayerFilterMode(long listenerVa);

    native private int getObjectLayerFilterMode(long listenerVa);

    native private void setBodyFilter(long listenerVa, long bodyFilterVa);

    native private void setBodyFilterMode(long listenerVa, int ordinal);

    native private void setBroadPhaseLayerFilter(
            long listenerVa, long bplFilterVa);

    native private void setBroadPhaseLayerFilterMode(
            long listenerVa, int ordinal);

    native private void setObjectLayerPairFilter(
            long listenerVa, long olpFilterVa);
}
