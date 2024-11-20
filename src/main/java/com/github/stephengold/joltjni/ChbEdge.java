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
 * An edge in a {@code ConvexHullBuilder}. (native type:
 * {@code ConvexHullBuilder::Edge})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class ChbEdge extends NonCopyable {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an edge with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param edgeVa the virtual address of the native object to assign (not
     * zero)
     */
    ChbEdge(JoltPhysicsObject container, long edgeVa) {
        super(container, edgeVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Access the next edge. The current edge is unaffected. (native attribute:
     * mNextEdge)
     *
     * @return a new JVM object with the pre-existing native object assigned, or
     * {@code null} if none
     */
    public ChbEdge getNextEdge() {
        long edgeVa = va();
        long nextEdgeVa = getNextEdge(edgeVa);
        ChbEdge result;
        if (nextEdgeVa == 0L) {
            result = null;
        } else {
            JoltPhysicsObject container = getContainingObject();
            result = new ChbEdge(container, nextEdgeVa);
        }

        return result;
    }

    /**
     * Return the index of the vertex from which the edge originates. The edge
     * is unaffected. (native attribute: mStartIdx)
     *
     * @return an index into {@code mPositions} (&ge;0)
     */
    public int getStartIdx() {
        long edgeVa = va();
        int result = getStartIdx(edgeVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long getNextEdge(long edgeVa);

    native private static int getStartIdx(long edgeVa);
}
