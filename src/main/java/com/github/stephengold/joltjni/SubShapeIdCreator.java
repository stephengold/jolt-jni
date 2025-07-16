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
 * Generate sub shape IDs by traversing the shape hierarchy. (native type:
 * SubShapeIDCreator)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class SubShapeIdCreator extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default creator.
     */
    public SubShapeIdCreator() {
        long creatorId = createDefault();
        setVirtualAddress(creatorId, () -> free(creatorId));
    }

    /**
     * Instantiate a box with the specified native object assigned.
     *
     * @param creatorVa the virtual address of the native object to assign (not
     * zero)
     * @param owner {@code true} &rarr; make the JVM object the owner,
     * {@code false} &rarr; it isn't the owner
     */
    SubShapeIdCreator(long creatorVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(creatorVa) : null;
        setVirtualAddress(creatorVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the resulting subshape ID.
     *
     * @return the ID value
     */
    public int getId() {
        long creatorVa = va();
        int result = getId(creatorVa);

        return result;
    }

    /**
     * Return the number of bits written to the subshape ID so far.
     *
     * @return the bit count
     */
    public int getNumBitsWritten() {
        long creatorVa = va();
        int result = getNumBitsWritten(creatorVa);

        return result;
    }

    /**
     * Push a new ID to the chain.
     *
     * @param value the value to push
     * @param bits the number of bits
     * @return the modified chain
     */
    public SubShapeIdCreator pushId(int value, int bits) {
        long creatorVa = va();
        long resultVa = pushId(creatorVa, value, bits);
        SubShapeIdCreator result = new SubShapeIdCreator(resultVa, true);

        return result;
    }
    // *************************************************************************
    // native methods

    native static long createDefault();

    native private static void free(long creatorId);

    native private static int getId(long creatorVa);

    native private static int getNumBitsWritten(long creatorVa);

    native private static long pushId(long creatorVa, int value, int bits);
}
