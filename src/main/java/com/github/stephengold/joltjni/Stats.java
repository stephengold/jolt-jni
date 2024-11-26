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
 * Statistics derived from a {@code Shape}. (native type: {@code Shape::Stats})
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class Stats extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified native object assigned.
     *
     * @param statsVa the virtual address of the native object to assign (not
     * zero)
     * @param owner {@code true} &rarr; make the JVM object the owner,
     * {@code false} &rarr; it isn't the owner
     */
    Stats(long statsVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(statsVa) : null;
        setVirtualAddress(statsVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the number in the shape, if any. The stats are unaffected. (native
     * attribute: {@code mNumTriangles})
     *
     * @return the number of triangles (&ge;0)
     */
    public int getNumTriangles() {
        long statsVa = va();
        int result = getNumTriangles(statsVa);

        return result;
    }

    /**
     * Return the amount of memory used by the shape. The stats are unaffected.
     * (native attribute: {@code mSizeBytes})
     *
     * @return the number of bytes (&ge;0)
     */
    public int getSizeBytes() {
        long statsVa = va();
        int result = getSizeBytes(statsVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static void free(long statsVa);

    native private static int getNumTriangles(long statsVa);

    native private static int getSizeBytes(long statsVa);
}
