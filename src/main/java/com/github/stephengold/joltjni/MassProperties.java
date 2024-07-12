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
 * The mass and inertial tensor of a {@code Body}. Used only during
 * construction.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class MassProperties extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified native object assigned.
     *
     * @param propertiesVa the virtual address of the native object to assign
     * (not zero)
     */
    MassProperties(long propertiesVa) {
        super(propertiesVa);
    }
    // *************************************************************************
    // JoltPhysicsObject methods

    /**
     * Unassign the assigned native object, assuming there is one. Free the
     * native object if the properties instance owns it.
     */
    @Override
    public void close() {
        if (ownsNativeObject()) {
            long settingsVa = va();
            free(settingsVa);
        }

        unassignNativeObject();
    }
    // *************************************************************************
    // native private methods

    native private static void free(long propertiesVa);
}
