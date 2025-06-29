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

/**
 * Receive events when a body is activated or deactivated.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class BodyActivationListener extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a listener with no native object assigned.
     */
    BodyActivationListener() {
    }

    /**
     * Instantiate a listener with the specified native object assigned but not
     * owned.
     *
     * @param listenerVa the virtual address of the native object to assign (not
     * zero)
     */
    BodyActivationListener(long listenerVa) {
        setVirtualAddress(listenerVa);
    }
    // *************************************************************************
    // new protected methods

    /**
     * Assign a native object (assuming there's none already assigned) and
     * designate the JVM object as the owner.
     *
     * @param listenerVa the virtual address of the native object to assign (not
     * zero)
     */
    final void setVirtualAddressAsOwner(long listenerVa) {
        Runnable freeingAction = () -> free(listenerVa);
        setVirtualAddress(listenerVa, freeingAction);
    }
    // *************************************************************************
    // native private methods

    native private static void free(long listenerVa);
}
