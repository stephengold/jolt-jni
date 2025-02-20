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

/**
 * A joint and its skin weight, for use in skinning calculations. (native type:
 * {@code SoftBodySharedSettings::SkinWeight})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class SkinWeight extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default instance.
     */
    public SkinWeight() {
        long weightVa = createDefault();
        setVirtualAddress(weightVa, () -> free(weightVa));
    }

    /**
     * Instantiate with the specified index and weight.
     *
     * @param invBindIndex the desired index into the array of inverse-bind
     * matrices (&ge;0)
     * @param weight the desired weight (relative influence) for skinning
     */
    public SkinWeight(int invBindIndex, float weight) {
        long weightVa = create(invBindIndex, weight);
        setVirtualAddress(weightVa, () -> free(weightVa));
    }

    /**
     * Instantiate with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param weightVa the virtual address of the native object to assign (not
     * zero)
     */
    SkinWeight(JoltPhysicsObject container, long weightVa) {
        super(container, weightVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the index into the array of inverse-bind matrices.
     *
     * @return the index (&ge;0)
     */
    public int getInvBindIndex() {
        long weightVa = va();
        int result = getInvBindIndex(weightVa);

        return result;
    }

    /**
     * Return the weight (relative influence).
     *
     * @return the weight value for skinning
     */
    public float getWeight() {
        long weightVa = va();
        float result = getWeight(weightVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long create(int invBindIndex, float weight);

    native private static long createDefault();

    native private static void free(long weightVa);

    native private static int getInvBindIndex(long weightVa);

    native private static float getWeight(long weightVa);
}
