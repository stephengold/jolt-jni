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

import com.github.stephengold.joltjni.template.Ref;
import com.github.stephengold.joltjni.template.RefTarget;

/**
 * Control the acceleration and deceleration of a vehicle.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class VehicleController extends NonCopyable implements RefTarget {
    // *************************************************************************
    // fields

    /**
     * prevent premature garbage collection of the underlying
     * {@code VehicleConstraint}
     */
    VehicleConstraint constraint;
    // *************************************************************************
    // constructors

    /**
     * Instantiate with no native object assigned.
     *
     * @param constraint the underlying {@code VehicleConstraint} (not null)
     */
    VehicleController(VehicleConstraint constraint) {
        this.constraint = constraint;
    }

    /**
     * Instantiate with the specified native object assigned but not owned.
     *
     * @param constraint the underlying {@code VehicleConstraint} (not null)
     * @param controllerVa the virtual address of the native object to assign
     * (not zero)
     */
    VehicleController(VehicleConstraint constraint, long controllerVa) {
        this.constraint = constraint;
        setVirtualAddress(controllerVa, null);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Access the underlying constraint.
     *
     * @return the pre-existing object (not null)
     */
    public VehicleConstraint getConstraint() {
        return constraint;
    }
    // *************************************************************************
    // RefTarget methods

    /**
     * Count the active references to the native {@code VehicleController}. The
     * controller is unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int getRefCount() {
        long controllerVa = va();
        int result = getRefCount(controllerVa);

        return result;
    }

    /**
     * Mark the native {@code VehicleController} as embedded.
     */
    @Override
    public void setEmbedded() {
        long controllerVa = va();
        setEmbedded(controllerVa);
    }

    /**
     * Create a counted reference to the native {@code VehicleController}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public Ref toRef() {
        long controllerVa = va();
        long copyVa = toRef(controllerVa);
        Ref result = new VehicleControllerRef(copyVa, true);

        return result;
    }
    // *************************************************************************
    // native methods

    native private static int getRefCount(long controllerVa);

    native private static void setEmbedded(long controllerVa);

    native static long toRef(long controllerVa);
}
