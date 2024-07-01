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
 * A component of a {@code PhysicsSystem}, used to configure simulation
 * parameters.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class PhysicsSettings extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified native object assigned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    PhysicsSettings(long settingsVa) {
        setVirtualAddress(settingsVa, false);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Alter the number of position steps.
     *
     * @param numSteps the desired number (default=2)
     */
    public void setNumPositionSteps(int numSteps) {
        long settingsVa = va();
        setNumPositionSteps(settingsVa, numSteps);
    }

    /**
     * Alter the number of velocity steps.
     *
     * @param numSteps the desired number (default=10)
     */
    public void setNumVelocitySteps(int numSteps) {
        long settingsVa = va();
        setNumVelocitySteps(settingsVa, numSteps);
    }
    // *************************************************************************
    // JoltPhysicsObject methods

    /**
     * Unassign the assigned native object, assuming there is one. Free the
     * native object if the current instance owns it.
     */
    @Override
    public void close() {
        if (ownsNativeObject()) {
            long virtualAddress = va();
            free(virtualAddress);
        }

        unassignNativeObject();
    }
    // *************************************************************************
    // native private methods

    native private static void free(long virtualAddress);

    native private static void setNumPositionSteps(
            long settingsVa, int numSteps);

    native private static void setNumVelocitySteps(
            long settingsVa, int numSteps);
}
