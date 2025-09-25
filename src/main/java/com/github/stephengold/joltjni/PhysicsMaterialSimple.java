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

import com.github.stephengold.joltjni.readonly.ConstColor;

/**
 * A straightforward implementation of {@code PhysicsMaterial}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class PhysicsMaterialSimple extends PhysicsMaterial {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default material.
     */
    public PhysicsMaterialSimple() {
        long materialVa = createDefault();
        setVirtualAddressAsCoOwner(materialVa);
    }

    /**
     * Instantiate a material with the specified native object assigned.
     *
     * @param materialVa the virtual address of the native object to assign (not
     * zero)
     */
    PhysicsMaterialSimple(long materialVa) {
        setVirtualAddressAsCoOwner(materialVa);
    }

    /**
     * Instantiate a copy of the specified material.
     *
     * @param original the material to copy (not {@code null}, unaffected)
     */
    public PhysicsMaterialSimple(PhysicsMaterialSimple original) {
        long originalVa = original.va();
        long copyVa = createCopy(originalVa);
        setVirtualAddressAsCoOwner(copyVa);
    }

    /**
     * Instantiate a material with the specified properties.
     *
     * @param name the desired name
     * @param color the desired color (not null, unaffected)
     */
    public PhysicsMaterialSimple(String name, ConstColor color) {
        int colorInt = color.getUInt32();
        long materialVa = create(name, colorInt);
        setVirtualAddressAsCoOwner(materialVa);
    }
    // *************************************************************************
    // native private methods

    native private static long create(String name, int colorInt);

    native private static long createCopy(long originalVa);

    native private static long createDefault();
}
