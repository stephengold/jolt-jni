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

import com.github.stephengold.joltjni.enumerate.EShapeSubType;

/**
 * Settings used to construct a {@code StaticCompoundShape}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class StaticCompoundShapeSettings extends CompoundShapeSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty settings object with no sub-shapes.
     */
    public StaticCompoundShapeSettings() {
        long settingsVa = createStaticCompoundShapeSettings();
        setVirtualAddressAsCoOwner(settingsVa, EShapeSubType.StaticCompound);
    }

    /**
     * Instantiate with the specified native object assigned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    StaticCompoundShapeSettings(long settingsVa) {
        setVirtualAddressAsCoOwner(settingsVa, EShapeSubType.StaticCompound);
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public StaticCompoundShapeSettings(StaticCompoundShapeSettings original) {
        long originalVa = original.va();
        long copyVa = createCopy(originalVa);
        setVirtualAddressAsCoOwner(copyVa, EShapeSubType.StaticCompound);
    }
    // *************************************************************************
    // native private methods

    native private static long createCopy(long originalVa);

    native private static long createStaticCompoundShapeSettings();
}
