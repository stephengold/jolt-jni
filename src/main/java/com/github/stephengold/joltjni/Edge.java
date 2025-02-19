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
 * Join 2 soft-body vertices with a spring. (native type:
 * {@code SoftBodySharedSettings::Edge})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Edge extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a default edge constraint.
     */
    public Edge() {
        long edgeVa = createDefault();
        setVirtualAddress(edgeVa, () -> free(edgeVa));
    }

    /**
     * Instantiate an edge with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param edgeVa the virtual address of the native object to assign (not
     * zero)
     */
    Edge(JoltPhysicsObject container, long edgeVa) {
        super(container, edgeVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the inverse of the spring's stiffness. The edge is unaffected.
     * (native attribute: mCompliance)
     *
     * @return the compliance value
     */
    public float getCompliance() {
        long edgeVa = va();
        float result = getCompliance(edgeVa);

        return result;
    }

    /**
     * Return the rest length of the spring. The edge is unaffected. (native
     * attribute: mRestLength)
     *
     * @return the length (in meters)
     */
    public float getRestLength() {
        long edgeVa = va();
        float result = getRestLength(edgeVa);

        return result;
    }

    /**
     * Return the mesh vertex at the specified end. (native attribute: mVertex)
     *
     * @param indexInEdge which end of the edge (0 or 1)
     * @return the mesh index of the vertex (&ge;0)
     */
    public int getVertex(int indexInEdge) {
        long edgeVa = va();
        int result = getVertex(edgeVa, indexInEdge);

        return result;
    }

    /**
     * Alter the stiffness of the spring. (native attribute: mCompliance)
     *
     * @param compliance the inverse of the desired stiffness (default=0)
     */
    public void setCompliance(float compliance) {
        long edgeVa = va();
        setCompliance(edgeVa, compliance);
    }

    /**
     * Alter the rest length of the spring. (native attribute: mRestLength)
     *
     * @param length the desired length (in meters, default=1)
     */
    public void setRestLength(float length) {
        long edgeVa = va();
        setRestLength(edgeVa, length);
    }

    /**
     * Assign the specified mesh vertex to the edge. (native attribute: mVertex)
     *
     * @param indexInEdge which end of the edge (0 or 1)
     * @param indexInMesh the index of the vertex to assign (&ge;0)
     */
    public void setVertex(int indexInEdge, int indexInMesh) {
        long edgeVa = va();
        setVertex(edgeVa, indexInEdge, indexInMesh);
    }
    // *************************************************************************
    // native private methods

    native private static long createDefault();

    native private static void free(long edgeVa);

    native private static float getCompliance(long edgeVa);

    native private static float getRestLength(long edgeVa);

    native private static int getVertex(long edgeVa, int indexInEdge);

    native private static void setCompliance(long edgeVa, float compliance);

    native private static void setRestLength(long edgeVa, float length);

    native private static void setVertex(
            long edgeVa, int indexInEdge, int indexInMesh);
}
