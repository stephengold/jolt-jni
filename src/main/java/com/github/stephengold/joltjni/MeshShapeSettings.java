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

import com.github.stephengold.joltjni.enumerate.EShapeSubType;
import java.nio.FloatBuffer;
import java.util.List;

/**
 * Settings used to construct a {@code MeshShape}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class MeshShapeSettings extends ShapeSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate the default settings.
     */
    public MeshShapeSettings() {
        long settingsVa = createDefault();
        setVirtualAddress(settingsVa); // not owner due to ref counting
        setSubType(EShapeSubType.Mesh);
    }

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
     * Instantiate settings for the specified vertices and triangles.
     *
     * @param vertices the array of vertices (not null, unaffected)
     * @param indices the list of triangles (not null, unaffected)
     */
    public MeshShapeSettings(Float3[] vertices, IndexedTriangleList indices) {
        int numVertices = vertices.length;
        int numFloats = 3 * numVertices;
        FloatBuffer vBuffer = Jolt.newDirectFloatBuffer(numFloats);
        for (Float3 vertex : vertices) {
            vertex.put(vBuffer);
        }
        long indicesVa = indices.va();
        long settingsVa
                = createMeshShapeSettings(numVertices, vBuffer, indicesVa);
        setVirtualAddress(settingsVa); // not owner due to ref counting
        setSubType(EShapeSubType.Mesh);
    }

    /**
     * Instantiate settings for the specified vertices and triangles.
     *
     * @param vertexArray the array of vertices (not null, unaffected)
     * @param itArray the array of triangles (not null, unaffected)
     */
    public MeshShapeSettings(Float3[] vertexArray, IndexedTriangle... itArray) {
        int numVertices = vertexArray.length;
        int numFloats = 3 * numVertices;
        FloatBuffer vBuffer = Jolt.newDirectFloatBuffer(numFloats);
        for (Float3 vertex : vertexArray) {
            vertex.put(vBuffer);
        }

        IndexedTriangleList itList = new IndexedTriangleList();
        for (IndexedTriangle triangle : itArray) {
            itList.pushBack(triangle);
        }
        long indicesVa = itList.va();
        long settingsVa
                = createMeshShapeSettings(numVertices, vBuffer, indicesVa);
        setVirtualAddress(settingsVa); // not owner due to ref counting
        setSubType(EShapeSubType.Mesh);
    }

    /**
     * Instantiate settings for the specified vertices without indices.
     *
     * @param positionBuffer the vertex positions (not null, capacity a multiple
     * of 9, unaffected)
     */
    public MeshShapeSettings(FloatBuffer positionBuffer) {
        int numFloats = positionBuffer.capacity();
        assert numFloats % 9 == 0 : "numFloats = " + numFloats;
        int numVertices = numFloats / 3;
        int numTriangles = numVertices / 3;

        PhysicsMaterialList materials = new PhysicsMaterialList();
        long materialsVa = materials.va();
        long settingsVa = createSettingsFromTriangles(
                numTriangles, positionBuffer, materialsVa);
        setVirtualAddress(settingsVa); // not owner due to ref counting
        setSubType(EShapeSubType.Mesh);
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public MeshShapeSettings(MeshShapeSettings original) {
        long originalVa = original.va();
        long copyVa = createCopy(originalVa);
        setVirtualAddress(copyVa); // not owner due to ref counting
        setSubType(EShapeSubType.Mesh);
    }

    /**
     * Instantiate settings for the specified lists of vertices and triangles.
     *
     * @param vertices the list of vertices (not null, unaffected)
     * @param indices the list of triangles (not null, unaffected)
     */
    public MeshShapeSettings(
            List<Float3> vertices, IndexedTriangleList indices) {
        int numVertices = vertices.size();
        int numFloats = 3 * numVertices;
        FloatBuffer vBuffer = Jolt.newDirectFloatBuffer(numFloats);
        for (Float3 vertex : vertices) {
            vertex.put(vBuffer);
        }
        long indicesVa = indices.va();
        long settingsVa
                = createMeshShapeSettings(numVertices, vBuffer, indicesVa);
        setVirtualAddress(settingsVa); // not owner due to ref counting
        setSubType(EShapeSubType.Mesh);
    }

    /**
     * Instantiate settings for the specified list of triangles.
     *
     * @param triangleList the list of triangles (not null, unaffected)
     */
    public MeshShapeSettings(List<Triangle> triangleList) {
        this(triangleList, new PhysicsMaterialList());
    }

    /**
     * Instantiate settings for the specified parameters.
     *
     * @param triangleList the list of triangles (not null, unaffected)
     * @param materials the desired surface properties (not null, unaffected)
     */
    public MeshShapeSettings(
            List<Triangle> triangleList, PhysicsMaterialList materials) {
        int numTriangles = triangleList.size();
        int numVertices = 3 * numTriangles;
        int numFloats = 3 * numVertices;
        FloatBuffer buffer = Jolt.newDirectFloatBuffer(numFloats);
        for (Triangle triangle : triangleList) {
            triangle.putVertices(buffer);
        }
        long materialsVa = materials.va();
        long settingsVa = createSettingsFromTriangles(
                numTriangles, buffer, materialsVa);
        setVirtualAddress(settingsVa); // not owner due to ref counting
        setSubType(EShapeSubType.Mesh);
    }

    /**
     * Instantiate settings for the specified vertices and indices.
     *
     * @param vertices list of vertex locations (not null, unaffected)
     * @param indices list of triangles that use those vertices (not null)
     */
    public MeshShapeSettings(VertexList vertices, IndexedTriangleList indices) {
        int numVertices = vertices.size();
        FloatBuffer buffer = vertices.toDirectBuffer();
        long indicesVa = indices.va();
        long settingsVa
                = createMeshShapeSettings(numVertices, buffer, indicesVa);
        setVirtualAddress(settingsVa); // not owner due to ref counting
        setSubType(EShapeSubType.Mesh);
    }

    /**
     * Instantiate settings for the specified array of triangles.
     *
     * @param triangleArray the array of triangles (not null, unaffected)
     */
    public MeshShapeSettings(Triangle... triangleArray) {
        this(triangleArray, new PhysicsMaterialList());
    }

    /**
     * Instantiate settings for the specified parameters.
     *
     * @param triangleArray the array of triangles (not null, unaffected)
     * @param materials the desired surface properties (not null, unaffected)
     */
    public MeshShapeSettings(
            Triangle[] triangleArray, PhysicsMaterialList materials) {
        int numTriangles = triangleArray.length;
        int numVertices = 3 * numTriangles;
        int numFloats = 3 * numVertices;
        FloatBuffer buffer = Jolt.newDirectFloatBuffer(numFloats);
        for (Triangle triangle : triangleArray) {
            triangle.putVertices(buffer);
        }
        long materialsVa = materials.va();
        long settingsVa = createSettingsFromTriangles(
                numTriangles, buffer, materialsVa);
        setVirtualAddress(settingsVa); // not owner due to ref counting
        setSubType(EShapeSubType.Mesh);
    }
    // *************************************************************************
    // new public methods

    /**
     * Append the specified triangle to the internal array.
     *
     * @param vi0 the desired first mesh-vertex index
     * @param vi1 the desired 2nd mesh-vertex index
     * @param vi2 the desired 3rd mesh-vertex index
     */
    public void addIndexedTriangle(int vi0, int vi1, int vi2) {
        long settingsVa = va();
        addIndexedTriangle(settingsVa, vi0, vi1, vi2);
    }

    /**
     * Append the specified vertex to the internal array.
     *
     * @param vertex the location of the vertex to add (not null, unaffected)
     */
    public void addTriangleVertex(Float3 vertex) {
        long settingsVa = va();
        addTriangleVertex(settingsVa, vertex.x, vertex.y, vertex.z);
    }

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
     * unaffected. (native attribute: mActiveEdgeCosThresholdAngle)
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
     * unaffected. (native attribute: mMaxTrianglesPerLeaf)
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
     * unaffected. (native attribute: mPerTriangleUserData)
     *
     * @return {@code true} if per-triangle data is included, otherwise
     * {@code false}
     */
    public boolean getPerTriangleUserData() {
        long settingsVa = va();
        boolean result = getPerTriangleUserData(settingsVa);

        return result;
    }

    /**
     * Reserve storage for the specified number of triangles.
     *
     * @param numTriangles the desired capacity
     */
    public void reserveIndexedTriangles(int numTriangles) {
        long settingsVa = va();
        reserveIndexedTriangles(settingsVa, numTriangles);
    }

    /**
     * Reserve storage for the specified number of vertices.
     *
     * @param numVertices the desired capacity
     */
    public void reserveTriangleVertices(int numVertices) {
        long settingsVa = va();
        reserveTriangleVertices(settingsVa, numVertices);
    }

    /**
     * Alter the active-edge threshold angle. (native attribute:
     * mActiveEdgeCosThresholdAngle)
     *
     * @param cosine the cosine of the desired angle (default=0.996195)
     */
    public void setActiveEdgeCosThresholdAngle(float cosine) {
        long settingsVa = va();
        setActiveEdgeCosThresholdAngle(settingsVa, cosine);
    }

    /**
     * Alter the maximum number of triangles per leaf. (native attribute:
     * mMaxTrianglesPerLeaf)
     *
     * @param numTriangles the desired number (default=8)
     */
    public void setMaxTrianglesPerLeaf(int numTriangles) {
        long settingsVa = va();
        setMaxTrianglesPerLeaf(settingsVa, numTriangles);
    }

    /**
     * Alter whether each triangle will include user data. (native attribute:
     * mPerTriangleUserData)
     *
     * @param include {@code true} to include per-triangle data, {@code false}
     * to omit it (default=false)
     */
    public void setPerTriangleUserData(boolean include) {
        long settingsVa = va();
        setPerTriangleUserData(settingsVa, include);
    }
    // *************************************************************************
    // native private methods

    native private static void addIndexedTriangle(
            long settingsVa, int vi0, int vi1, int vi2);

    native private static void addTriangleVertex(
            long settingsVa, float x, float y, float z);

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static long createMeshShapeSettings(
            int numVertices, FloatBuffer vertices, long indicesVa);

    native private static long createSettingsFromTriangles(
            int numTriangles, FloatBuffer buffer, long materialsVa);

    native private static int countTriangles(long settingsVa);

    native private static int countTriangleVertices(long settingsVa);

    native private static float getActiveEdgeCosThresholdAngle(long settingsVa);

    native private static int getMaxTrianglesPerLeaf(long settingsVa);

    native private static boolean getPerTriangleUserData(long settingsVa);

    native private static void reserveIndexedTriangles(
            long settingsVa, int numTriangles);

    native private static void reserveTriangleVertices(
            long settingsVa, int numVertices);

    native private static void setActiveEdgeCosThresholdAngle(
            long settingsVa, float cosine);

    native private static void setMaxTrianglesPerLeaf(
            long settingsVa, int numTriangles);

    native private static void setPerTriangleUserData(
            long settingsVa, boolean include);
}
