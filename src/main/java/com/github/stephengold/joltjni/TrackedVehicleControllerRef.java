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

/**
 * A counted reference to a {@code TrackedVehicleController}. (native type:
 * {@code Ref<TrackedVehicleController>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class TrackedVehicleControllerRef extends Ref {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a reference with the specified native object assigned.
     *
     * @param refVa the virtual address of the native object to assign (not
     * zero)
     * @param owner {@code true} &rarr; make the JVM object the owner,
     * {@code false} &rarr; it isn't the owner
     */
    TrackedVehicleControllerRef(long refVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(refVa) : null;
        setVirtualAddress(refVa, freeingAction);
    }
    // *************************************************************************
    // Ref methods

    /**
     * Temporarily access the referenced {@code WheeledVehicleController}.
     *
     * @return a new JVM object that refers to the pre-existing native object
     */
    @Override
    public TrackedVehicleController getPtr() {
        long refVa = va();
        long controllerVa = getPtr(refVa);
        TrackedVehicleController result
                = new TrackedVehicleController(null, controllerVa);

        return result;
    }

    /**
     * Create a counted reference to the native
     * {@code TrackedVehicleController}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public TrackedVehicleControllerRef toRef() {
        long refVa = va();
        long copyVa = copy(refVa);
        TrackedVehicleControllerRef result
                = new TrackedVehicleControllerRef(copyVa, true);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long copy(long refVa);

    native private static void free(long refVa);

    native private static long getPtr(long refVa);
}
