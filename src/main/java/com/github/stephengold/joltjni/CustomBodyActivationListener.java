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
 * A customizable {@code BodyActivationListener}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class CustomBodyActivationListener
        extends BodyActivationListener {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a customizable listener.
     */
    public CustomBodyActivationListener() {
        long listenerVa = createCustomBodyActivationListener();
        setVirtualAddressAsOwner(listenerVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Callback invoked (by native code) each time a body is activated. Meant to
     * be overridden.
     *
     * @param bodyId the body's ID
     * @param bodyUserData the body's user data
     */
    public void onBodyActivated(int bodyId, long bodyUserData) {
        // do nothing
    }

    /**
     * Callback invoked (by native code) each time a body is deactivated. Meant
     * to be overridden.
     *
     * @param bodyId the body's ID
     * @param bodyUserData the body's user data
     */
    public void onBodyDeactivated(int bodyId, long bodyUserData) {
        // do nothing
    }
    // *************************************************************************
    // native private methods

    native private long createCustomBodyActivationListener();
}
