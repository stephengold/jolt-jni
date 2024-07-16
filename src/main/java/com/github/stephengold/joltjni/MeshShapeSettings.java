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

import java.nio.FloatBuffer;

/**
 * Settings used to construct a {@code MeshShape}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class MeshShapeSettings extends ShapeSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified native object assigned but not owned.
     *
     * @param virtualAddress the virtual address of the native object to assign
     * (not zero)
     */
    MeshShapeSettings(long virtualAddress) {
        super(virtualAddress);
        setSubType(EShapeSubType.Mesh);
    }

    /**
     * Instantiate settings for the specified vertices and indices.
     *
     * @param vertices list of vertex locations (not null)
     * @param indices list of triangles that use those vertices (not null)
     */
    public MeshShapeSettings(VertexList vertices, IndexedTriangleList indices) {
        int numVertices = vertices.size();
        FloatBuffer buffer = vertices.toDirectBuffer();
        long indicesVa = indices.va();
        long settingsVa
                = createMeshShapeSettings(numVertices, buffer, indicesVa);
        setVirtualAddress(settingsVa, null); // not owner due to ref counting
        setSubType(EShapeSubType.Mesh);
    }
    // *************************************************************************
    // new public methods

    /**
     * Alter the maximum number of triangles per leaf.
     *
     * @param numTriangles the desired number (default=8)
     */
    public void setMaxTrianglesPerLeaf(int numTriangles) {
        long settingsVa = va();
        setMaxTrianglesPerLeaf(settingsVa, numTriangles);
    }
    // *************************************************************************
    // native private methods

    native private static long createMeshShapeSettings(
            int numVertices, FloatBuffer vertices, long indicesVa);

    native private static void setMaxTrianglesPerLeaf(
            long settingsVa, int numTriangles);
}
