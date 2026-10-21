/*
Copyright (c) 2026 Stephen Gold

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
package com.github.stephengold.joltjni.std;

import com.github.stephengold.joltjni.JoltPhysicsObject;

/**
 * A pair of integer values. (native type: {@code std::pair<int, int>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class IntPair extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a pair with the values
     *
     * @param v0 the value for index=0
     * @param v1 the value for index=1
     */
    public IntPair(int v0, int v1) {
        long pairVa = create(v0, v1);
        setVirtualAddress(pairVa, () -> free(pairVa));
    }

    /**
     * Instantiate with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param pairVa the virtual address of the native object to assign (not
     * zero)
     */
    public IntPair(JoltPhysicsObject container, long pairVa) {
        super(container, pairVa);
    }

    /**
     * Return the value at the specified index.
     *
     * @param index the index from which to read the value (0 or 1)
     * @return the stored value
     */
    public int get(int index) {
        assert index == 0 || index == 1 : "Out of range:  index=" + index;

        long pairVa = va();
        int result = get(pairVa, index);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long create(int v0, int v1);

    native private static void free(long pairVa);

    native private static int get(long pairVa, int index);
}
