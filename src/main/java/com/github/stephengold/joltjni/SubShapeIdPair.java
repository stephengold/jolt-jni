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

import com.github.stephengold.joltjni.readonly.ConstSubShapeIdPair;

/**
 * Identify a pair of colliding sub-shapes. (native type: SubShapeIDPair)
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class SubShapeIdPair
        extends JoltPhysicsObject
        implements ConstSubShapeIdPair {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a pair with the specified native object assigned but not
     * owned.
     *
     * @param pairVa the virtual address of the native object to assign (not
     * zero)
     */
    public SubShapeIdPair(long pairVa) {
        setVirtualAddress(pairVa);
    }
    // *************************************************************************
    // ConstSubShapeIdPair methods

    /**
     * Return the ID of the first body. The pair is unaffected. (native method:
     * GetBody1ID)
     *
     * @return the {@code BodyID} value
     */
    @Override
    public int getBody1Id() {
        long pairVa = va();
        int result = getBody1Id(pairVa);

        return result;
    }

    /**
     * Return the ID of the 2nd body. The pair is unaffected. (native method:
     * GetBody2ID)
     *
     * @return the {@code BodyID} value
     */
    @Override
    public int getBody2Id() {
        long pairVa = va();
        int result = getBody2Id(pairVa);

        return result;
    }

    /**
     * Return the hashcode for the pair. The pair is unaffected.
     *
     * @return the value
     */
    @Override
    public long getHash() {
        long pairVa = va();
        long result = getHash(pairVa);

        return result;
    }

    /**
     * Return the ID of the first sub-shape. The pair is unaffected. (native
     * method: GetSubShapeID1)
     *
     * @return a {@code SubShapeID} value
     */
    @Override
    public int getSubShapeId1() {
        long pairVa = va();
        int result = getSubShapeId1(pairVa);

        return result;
    }

    /**
     * Return the ID of the 2nd sub-shape. The pair is unaffected. (native
     * method: GetSubShapeID2)
     *
     * @return a {@code SubShapeID} value
     */
    @Override
    public int getSubShapeId2() {
        long pairVa = va();
        int result = getSubShapeId2(pairVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static int getBody1Id(long pairVa);

    native private static int getBody2Id(long pairVa);

    native private static long getHash(long pairVa);

    native private static int getSubShapeId1(long pairVa);

    native private static int getSubShapeId2(long pairVa);
}
