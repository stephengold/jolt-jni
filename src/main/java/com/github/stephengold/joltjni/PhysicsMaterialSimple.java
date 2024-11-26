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
        setVirtualAddress(materialVa, null); // not owner due to ref counting
    }

    /**
     * Instantiate a material.
     *
     * @param name the desired name
     * @param color the desired color (not null, unaffected)
     */
    public PhysicsMaterialSimple(String name, ConstColor color) {
        int colorInt = color.getUInt32();
        long materialVa = create(name, colorInt);
        setVirtualAddress(materialVa, null); // not owner due to ref counting
    }
    // *************************************************************************
    // native private methods

    native private static long create(String name, int colorInt);

    native private static long createDefault();
}
