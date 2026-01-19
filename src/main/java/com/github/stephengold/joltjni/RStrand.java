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

/**
 * A rendered strand of hair. (native type: {@code HairSettings::RStrand})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class RStrand extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default strand.
     */
    public RStrand() {
        long strandVa = createDefault();
        setVirtualAddress(strandVa, () -> free(strandVa));
    }

    /**
     * Instantiate a strand with no native object assigned.
     *
     * @param dummy unused argument to distinguish from the zero-arg constructor
     */
    RStrand(boolean dummy) {
    }

    /**
     * Instantiate a strand with the specified vertices.
     *
     * @param startVertex the index of the first vertex (&ge;0)
     * @param endVertex the index of the last vertex (&ge;0)
     */
    public RStrand(int startVertex, int endVertex) {
        long strandVa = create(startVertex, endVertex);
        setVirtualAddress(strandVa, () -> free(strandVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the index of the ending vertex. The strand is unaffected. (native
     * attribute: mEndVtx)
     *
     * @return the vertex index
     */
    public int getEndVtx() {
        long strandVa = va();
        int result = getEndVtx(strandVa);

        return result;
    }

    /**
     * Return the index of the starting vertex. The strand is unaffected.
     * (native attribute: mStartVtx)
     *
     * @return the vertex index
     */
    public int getStartVtx() {
        long strandVa = va();
        int result = getStartVtx(strandVa);

        return result;
    }

    /**
     * Return the number of vertices in the strand.
     *
     * @return the count
     */
    public int vertexCount() {
        long strandVa = va();
        int result = vertexCount(strandVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long create(int startVertex, int endVertex);

    native private static long createDefault();

    native private static void free(long strandVa);

    native private static int getEndVtx(long strandVa);

    native private static int getStartVtx(long strandVa);

    native private static int vertexCount(long strandVa);
}
