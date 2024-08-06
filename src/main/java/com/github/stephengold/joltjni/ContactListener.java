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
 * Receive collision contact events.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class ContactListener extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a listener with no native object assigned.
     */
    protected ContactListener() {
    }

    /**
     * Instantiate a listener with the specified native object assigned.
     *
     * @param listenerVa the virtual address of the native object to assign (not
     * zero)
     * @param owner true &rarr; make the JVM object the owner, false &rarr; it
     * isn't the owner
     */
    ContactListener(long listenerVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(listenerVa) : null;
        setVirtualAddress(listenerVa, freeingAction);
    }
    // *************************************************************************
    // new protected methods

    /**
     * Assign a native object, assuming there's none already assigned.
     *
     * @param virtualAddress the virtual address of the native object to assign
     * (not zero)
     * @param owner true &rarr; make the JVM object the owner, false &rarr; it
     * isn't the owner
     */
    protected void setVirtualAddress(long virtualAddress, boolean owner) {
        Runnable freeingAction = owner ? () -> free(virtualAddress) : null;
        setVirtualAddress(virtualAddress, freeingAction);
    }
    // *************************************************************************
    // native private methods

    native private static void free(long listenerVa);
}
