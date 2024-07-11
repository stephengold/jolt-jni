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

/**
 * Identify a particular {@code Body} to a {@code BodyInterface}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class BodyId extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified native object assigned.
     *
     * @param virtualAddress the virtual address of the native object to assign
     * (not zero)
     */
    BodyId(long virtualAddress) {
        super(virtualAddress);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the body's index in the body array.
     *
     * @return the index (&ge;0)
     */
    public int getIndex() {
        long idVa = va();
        int result = getIndex(idVa);

        return result;
    }

    /**
     * Return the body's sequence number.
     *
     * @return the sequence number (&ge;0)
     */
    public int getSequenceNumber() {
        long idVa = va();
        int result = getSequenceNumber(idVa);

        return result;
    }

    /**
     * Test whether the ID is invalid.
     *
     * @return true if invalid, false if valid
     */
    public boolean isInvalid() {
        long idVa = va();
        boolean result = isInvalid(idVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static int getIndex(long idVa);

    native private static int getSequenceNumber(long idVa);

    native private static boolean isInvalid(long idVa);
}
