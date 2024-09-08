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

import com.github.stephengold.joltjni.enumerate.EShapeSubType;
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
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    MeshShapeSettings(long settingsVa) {
        super(settingsVa);
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
     * Count the triangles in the mesh. The settings are unaffected.
     *
     * @return the count (&ge;0)
     */
    public int countTriangles() {
        long settingsVa = va();
        int result = countTriangles(settingsVa);

        return result;
    }

    /**
     * Count the vertices in the mesh. The settings are unaffected.
     *
     * @return the count (&ge;0)
     */
    public int countTriangleVertices() {
        long settingsVa = va();
        int result = countTriangleVertices(settingsVa);

        return result;
    }

    /**
     * Return the cosine of the active-edge threshold angle. The settings are
     * unaffected. (native field: mActiveEdgeCosThresholdAngle)
     *
     * @return the cosine
     */
    public float getActiveEdgeCosThresholdAngle() {
        long settingsVa = va();
        float result = getActiveEdgeCosThresholdAngle(settingsVa);

        return result;
    }

    /**
     * Return the maximum number of triangles per leaf. The settings are
     * unaffected. (native field: mMaxTrianglesPerLeaf)
     *
     * @return the maximum number
     */
    public int getMaxTrianglesPerLeaf() {
        long settingsVa = va();
        int result = getMaxTrianglesPerLeaf(settingsVa);

        return result;
    }

    /**
     * Test whether each triangle will include user data. The settings are
     * unaffected. (native field: mPerTriangleUserData)
     *
     * @return true if per-triangle data is included, otherwise false
     */
    public boolean getPerTriangleUserData() {
        long settingsVa = va();
        boolean result = getPerTriangleUserData(settingsVa);

        return result;
    }

    /**
     * Alter the active-edge threshold angle. (native field:
     * mActiveEdgeCosThresholdAngle)
     *
     * @param cosine the cosine of the desired angle (default=0.996195)
     */
    public void setActiveEdgeCosThresholdAngle(float cosine) {
        long settingsVa = va();
        setActiveEdgeCosThresholdAngle(settingsVa, cosine);
    }

    /**
     * Alter the maximum number of triangles per leaf. (native field:
     * mMaxTrianglesPerLeaf)
     *
     * @param numTriangles the desired number (default=8)
     */
    public void setMaxTrianglesPerLeaf(int numTriangles) {
        long settingsVa = va();
        setMaxTrianglesPerLeaf(settingsVa, numTriangles);
    }

    /**
     * Alter whether each triangle will include user data. (native field:
     * mPerTriangleUserData)
     *
     * @param include true to include per-triangle data, false to omit it
     * (default=false)
     */
    public void setPerTriangleUserData(boolean include) {
        long settingsVa = va();
        setPerTriangleUserData(settingsVa, include);
    }
    // *************************************************************************
    // native private methods

    native private static long createMeshShapeSettings(
            int numVertices, FloatBuffer vertices, long indicesVa);

    native private static int countTriangles(long settingsVa);

    native private static int countTriangleVertices(long settingsVa);

    native private static float getActiveEdgeCosThresholdAngle(long settingsVa);

    native private static int getMaxTrianglesPerLeaf(long settingsVa);

    native private static boolean getPerTriangleUserData(long settingsVa);

    native private static void setActiveEdgeCosThresholdAngle(
            long settingsVa, float cosine);

    native private static void setMaxTrianglesPerLeaf(
            long settingsVa, int numTriangles);

    native private static void setPerTriangleUserData(
            long settingsVa, boolean include);
}
