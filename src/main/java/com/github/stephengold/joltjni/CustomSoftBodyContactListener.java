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

import com.github.stephengold.joltjni.enumerate.SoftBodyValidateResult;

/**
 * A customizable {@code SoftBodyContactListener}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class CustomSoftBodyContactListener
        extends SoftBodyContactListener {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a customizable listener.
     */
    public CustomSoftBodyContactListener() {
        long listenerVa = createDefault();
        setVirtualAddressAsOwner(listenerVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Callback invoked (by native code) after all contact points for a soft
     * body have been handled. Meant to be overridden.
     *
     * @param bodyVa the virtual address of the soft body in contact (not zero)
     * @param manifoldVa the virtual address of the contact manifold (not zero)
     */
    public void onSoftBodyContactAdded(long bodyVa, long manifoldVa) {
        // do nothing
    }

    /**
     * Callback invoked (by native code) whenever a soft body's bounding box
     * overlaps another body's bounding box. Meant to be overridden.
     *
     * @param softBodyVa the virtual address of the soft body (not zero)
     * @param otherBodyVa the virtual address of the other body (not zero)
     * @param settingsVa the virtual address of the
     * {@code SoftBodyContactSettings} (not zero)
     * @return how to the contact should be processed (an ordinal of
     * {@code SoftBodyValidateResult})
     */
    public int onSoftBodyContactValidate(
            long softBodyVa, long otherBodyVa, long settingsVa) {
        int result = SoftBodyValidateResult.AcceptContact.ordinal();
        return result;
    }
    // *************************************************************************
    // native private methods

    native private long createDefault();
}
