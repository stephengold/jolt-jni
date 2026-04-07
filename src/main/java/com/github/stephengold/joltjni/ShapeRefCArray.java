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
package com.github.stephengold.joltjni;

import com.github.stephengold.joltjni.readonly.ConstShape;

/**
 * A fixed-length array of counted references to shapes. (native type:
 * {@code ShapeRefC[]})
 *
 * @author xI-Mx-Ix
 */
public class ShapeRefCArray extends JoltPhysicsObject {
    // *************************************************************************
    // fields

    /**
     * length (in references)
     */
    final private int length;
    // *************************************************************************
    // constructors

    /**
     * Instantiate an array with the specified length.
     *
     * @param length the desired number of references (&gt;0)
     */
    public ShapeRefCArray(int length) {
        assert length > 0 : "length=" + length;

        this.length = length;
        long arrayVa = create(length);
        setVirtualAddress(arrayVa, () -> free(arrayVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Copy the reference at the specified index.
     *
     * @param elementIndex the index (&ge;0, &lt;length)
     * @return a new JVM object with a new native object assigned
     */
    public ShapeRefC get(int elementIndex) {
        assert elementIndex >= 0 && elementIndex < length :
                "Out of range:  index=" + elementIndex + " length=" + length;

        long arrayVa = va();
        long refVa = getRef(arrayVa, elementIndex); // the pre-existing ref
        long targetVa = ShapeRefC.getPtr(refVa);

        ShapeRefC result;
        if (targetVa == 0L) {
            result = new ShapeRefC();
        } else {
            long copyVa = ShapeRefC.copy(refVa);
            ConstShape target = Shape.newShape(targetVa);
            result = new ShapeRefC(copyVa, target);
        }

        return result;
    }

    /**
     * Return the length of the array.
     *
     * @return the length (in references, &gt;0)
     */
    public int length() {
        return length;
    }

    /**
     * Store the specified reference at the specified index.
     *
     * @param elementIndex the index at which to store the reference (&ge;0,
     * &lt;length)
     * @param ref the reference to store (not {@code null}, unaffected)
     */
    public void set(int elementIndex, ShapeRefC ref) {
        assert elementIndex >= 0 && elementIndex < length :
                "Out of range:  index=" + elementIndex + " length=" + length;

        long arrayVa = va();
        long refVa = ref.va();
        setRef(arrayVa, elementIndex, refVa);
    }
    // *************************************************************************
    // native private methods

    native private static long create(int length);

    native private static void free(long arrayVa);

    native private static long getRef(long arrayVa, int elementIndex);

    native private static void setRef(
            long arrayVa, int elementIndex, long refVa);
}
