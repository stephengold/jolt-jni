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

    /**
     * Instantiate a tester with the specified native object assigned.
     *
     * @param testerVa the virtual address of the native object to assign (not
     * zero)
     */
    VehicleCollisionTester(long testerVa) {
        setVirtualAddressAsCoOwner(testerVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Alter the object layer used for collision detection when no filters are
     * overridden.
     *
     * @param objectLayer the index of the desired layer
     */
    public void setObjectLayer(int objectLayer) {
        long testerVa = va();
        setObjectLayer(testerVa, objectLayer);
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
     * Return the object layer used for collision detection when no filters are
     * overridden.
     *
     * @return the index of the layer
     */
    @Override
    public int getObjectLayer() {
        long testerVa = va();
        int result = getObjectLayer(testerVa);

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
     * Create a counted reference to the native {@code VehicleCollisionTester}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public Ref toRef() {
        long testerVa = va();
        long refVa = toRef(testerVa);
        VehicleCollisionTesterRef result
                = new VehicleCollisionTesterRef(refVa, true);

        return result;
    }
    // *************************************************************************
    // native methods

    native private static int getObjectLayer(long testerVa);

    native private static int getRefCount(long testerVa);

    native private static void setEmbedded(long testerVa);

    native private static void setObjectLayer(long testerVa, int layer);

    native static long toRef(long testerVa);
}
