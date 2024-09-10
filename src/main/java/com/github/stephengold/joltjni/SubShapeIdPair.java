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

import com.github.stephengold.joltjni.readonly.ConstBodyId;
import com.github.stephengold.joltjni.readonly.ConstSubShapeId;
import com.github.stephengold.joltjni.readonly.ConstSubShapeIdPair;

/**
 * Identify a pair of colliding subshapes. (native type: SubShapeIDPair)
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
        setVirtualAddress(pairVa, null);
    }
    // *************************************************************************
    // ConstSubShapeIdPair methods

    /**
     * Return the ID of the first body.
     *
     * @return a new object
     */
    @Override
    public ConstBodyId getBody1Id() {
        long pairVa = va();
        long bodyIdVa = getBody1Id(pairVa);
        ConstBodyId result = new BodyId(bodyIdVa, true);

        return result;
    }

    /**
     * Return the ID of the 2nd body.
     *
     * @return a new object
     */
    @Override
    public ConstBodyId getBody2Id() {
        long pairVa = va();
        long bodyIdVa = getBody2Id(pairVa);
        ConstBodyId result = new BodyId(bodyIdVa, true);

        return result;
    }

    /**
     * Return the hashcode for the pair.
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
     * Return the ID of the first subshape.
     *
     * @return a new object
     */
    @Override
    public ConstSubShapeId getSubShapeId1() {
        long pairVa = va();
        long bodyIdVa = getSubShapeId1(pairVa);
        ConstSubShapeId result = new SubShapeId(bodyIdVa, true);

        return result;
    }

    /**
     * Return the ID of the 2nd subshape.
     *
     * @return a new object
     */
    @Override
    public ConstSubShapeId getSubShapeId2() {
        long pairVa = va();
        long bodyIdVa = getSubShapeId2(pairVa);
        ConstSubShapeId result = new SubShapeId(bodyIdVa, true);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long getBody1Id(long pairVa);

    native private static long getBody2Id(long pairVa);

    native private static long getHash(long pairVa);

    native private static long getSubShapeId1(long pairVa);

    native private static long getSubShapeId2(long pairVa);
}
