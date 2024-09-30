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

import com.github.stephengold.joltjni.readonly.ConstWheelSettings;
import com.github.stephengold.joltjni.template.Ref;

/**
 * A single wheel of a vehicle.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Wheel extends NonCopyable {
    // *************************************************************************
    // fields

    /**
     * prevent premature garbage collection of the settings
     */
    private Ref settings;
    // *************************************************************************
    // constructors

    /**
     * Instantiate with no native object assigned.
     */
    Wheel() {
    }

    /**
     * Instantiate with the specified native object assigned but not owned.
     *
     * @param wheelVa the virtual address of the native object to assign (not
     * zero)
     */
    Wheel(long wheelVa) {
        setVirtualAddress(wheelVa, null);
        long settingsVa = getSettings(wheelVa);
        if (this instanceof WheelWv) {
            this.settings = new WheelSettingsWv(settingsVa).toRef();
        } else {
            throw new IllegalStateException(this.getClass().getSimpleName());
        }
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Access the settings used to create this wheel.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public ConstWheelSettings getSettings() {
        WheelSettings result = (WheelSettings) settings.getPtr();
        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long getSettings(long wheelVa);
}