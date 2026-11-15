/*
Copyright (c) 2024-2026 Stephen Gold

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

import com.github.stephengold.joltjni.enumerate.EBuildQuality;
import com.github.stephengold.joltjni.enumerate.EShapeSubType;
import com.github.stephengold.joltjni.readonly.ConstFloat3;
import com.github.stephengold.joltjni.readonly.ConstMeshShapeSettings;
import com.github.stephengold.joltjni.readonly.ConstTriangle;
import com.github.stephengold.joltjni.readonly.ConstVertexList;
import java.nio.FloatBuffer;
import java.util.List;

/**
 * Settings used to construct a {@code MeshShape}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class MeshShapeSettings
        extends ShapeSettings
        implements ConstMeshShapeSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate the default settings.
     */
    public MeshShapeSettings() {
        long settingsVa = createDefault();
        setVirtualAddressAsCoOwner(settingsVa, EShapeSubType.Mesh);
    }

    /**
     * Instantiate settings for the specified vertices and triangles.
     *
     * @param vertices the array of vertices (not {@code null}, unaffected)
     * @param indices the list of triangles (not {@code null}, unaffected)
     */
    public MeshShapeSettings(
            ConstFloat3[] vertices, IndexedTriangleList indices) {
        int numVertices = vertices.length;
        int numFloats = 3 * numVertices;
        FloatBuffer vBuffer = Jolt.newDirectFloatBuffer(numFloats);
        for (ConstFloat3 vertex : vertices) {
            vertex.put(vBuffer);
        }
        long indicesVa = indices.va();
        long settingsVa
                = createMeshShapeSettings(numVertices, vBuffer, indicesVa);
        setVirtualAddressAsCoOwner(settingsVa, EShapeSubType.Mesh);
    }

    /**
     * Instantiate settings for the specified vertices and triangles.
     *
     * @param vertexArray the array of vertices (not {@code null}, unaffected)
     * @param itArray the array of triangles (not {@code null}, unaffected)
     */
    public MeshShapeSettings(
            ConstFloat3[] vertexArray, IndexedTriangle... itArray) {
        int numVertices = vertexArray.length;
        int numFloats = 3 * numVertices;
        FloatBuffer vBuffer = Jolt.newDirectFloatBuffer(numFloats);
        for (ConstFloat3 vertex : vertexArray) {
            vertex.put(vBuffer);
        }

        IndexedTriangleList itList = new IndexedTriangleList();
        for (IndexedTriangle triangle : itArray) {
            itList.pushBack(triangle);
        }
        long indicesVa = itList.va();
        long settingsVa
                = createMeshShapeSettings(numVertices, vBuffer, indicesVa);
        setVirtualAddressAsCoOwner(settingsVa, EShapeSubType.Mesh);
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public MeshShapeSettings(ConstMeshShapeSettings original) {
        long originalVa = original.targetVa();
        long copyVa = createCopy(originalVa);
        setVirtualAddressAsCoOwner(copyVa, EShapeSubType.Mesh);
    }

    /**
     * Instantiate settings for the specified array of triangles.
     *
     * @param triangleArray the array of triangles (not {@code null},
     * unaffected)
     */
    public MeshShapeSettings(ConstTriangle... triangleArray) {
        int numTriangles = triangleArray.length;
        int numVertices = 3 * numTriangles;
        int numFloats = 3 * numVertices;
        FloatBuffer buffer = Jolt.newDirectFloatBuffer(numFloats);
        for (ConstTriangle triangle : triangleArray) {
            triangle.putVertices(buffer);
        }
        long settingsVa = createFromTrianglesNoMats(numTriangles, buffer);
        setVirtualAddressAsCoOwner(settingsVa, EShapeSubType.Mesh);
    }

    /**
     * Instantiate settings for the specified parameters.
     *
     * @param triangleArray the array of triangles (not {@code null},
     * unaffected)
     * @param materials the desired surface properties (not {@code null},
     * unaffected)
     */
    public MeshShapeSettings(
            ConstTriangle[] triangleArray, PhysicsMaterialList materials) {
        int numTriangles = triangleArray.length;
        int numVertices = 3 * numTriangles;
        int numFloats = 3 * numVertices;
        FloatBuffer buffer = Jolt.newDirectFloatBuffer(numFloats);
        for (ConstTriangle triangle : triangleArray) {
            triangle.putVertices(buffer);
        }
        long materialsVa = materials.va();
        long settingsVa = createSettingsFromTriangles(
                numTriangles, buffer, materialsVa);
        setVirtualAddressAsCoOwner(settingsVa, EShapeSubType.Mesh);
    }

    /**
     * Instantiate settings for the specified vertices and indices.
     *
     * @param vertices list of vertex locations (not {@code null}, unaffected)
     * @param indices list of triangles that use those vertices (not
     * {@code null})
     */
    public MeshShapeSettings(
            ConstVertexList vertices, IndexedTriangleList indices) {
        int numVertices = vertices.size();
        FloatBuffer buffer = vertices.toDirectBuffer();
        long indicesVa = indices.va();
        long settingsVa
                = createMeshShapeSettings(numVertices, buffer, indicesVa);
        setVirtualAddressAsCoOwner(settingsVa, EShapeSubType.Mesh);
    }

    /**
     * Instantiate settings for the specified vertices without indices.
     *
     * @param positionBuffer the vertex positions (not {@code null}, capacity a
     * multiple of 9, unaffected)
     */
    public MeshShapeSettings(FloatBuffer positionBuffer) {
        int numFloats = positionBuffer.capacity();
        assert numFloats % 9 == 0 : "numFloats = " + numFloats;
        int numVertices = numFloats / 3;
        int numTriangles = numVertices / 3;

        long settingsVa
                = createFromTrianglesNoMats(numTriangles, positionBuffer);
        setVirtualAddressAsCoOwner(settingsVa, EShapeSubType.Mesh);
    }

    /**
     * Instantiate settings for the specified lists of vertices and triangles.
     *
     * @param vertices the list of vertices (not {@code null}, unaffected)
     * @param indices the list of triangles (not {@code null}, unaffected)
     */
    public MeshShapeSettings(
            List<ConstFloat3> vertices, IndexedTriangleList indices) {
        int numVertices = vertices.size();
        int numFloats = 3 * numVertices;
        FloatBuffer vBuffer = Jolt.newDirectFloatBuffer(numFloats);
        for (ConstFloat3 vertex : vertices) {
            vertex.put(vBuffer);
        }
        long indicesVa = indices.va();
        long settingsVa
                = createMeshShapeSettings(numVertices, vBuffer, indicesVa);
        setVirtualAddressAsCoOwner(settingsVa, EShapeSubType.Mesh);
    }

    /**
     * Instantiate settings for the specified list of triangles.
     *
     * @param triangleList the list of triangles (not {@code null}, unaffected)
     */
    public MeshShapeSettings(List<? extends ConstTriangle> triangleList) {
        int numTriangles = triangleList.size();
        int numVertices = 3 * numTriangles;
        int numFloats = 3 * numVertices;
        FloatBuffer buffer = Jolt.newDirectFloatBuffer(numFloats);
        for (ConstTriangle triangle : triangleList) {
            triangle.putVertices(buffer);
        }
        long settingsVa = createFromTrianglesNoMats(numTriangles, buffer);
        setVirtualAddressAsCoOwner(settingsVa, EShapeSubType.Mesh);
    }

    /**
     * Instantiate settings for the specified parameters.
     *
     * @param triangleList the list of triangles (not {@code null}, unaffected)
     * @param materials the desired surface properties (not {@code null},
     * unaffected)
     */
    public MeshShapeSettings(List<? extends ConstTriangle> triangleList,
            PhysicsMaterialList materials) {
        int numTriangles = triangleList.size();
        int numVertices = 3 * numTriangles;
        int numFloats = 3 * numVertices;
        FloatBuffer buffer = Jolt.newDirectFloatBuffer(numFloats);
        for (ConstTriangle triangle : triangleList) {
            triangle.putVertices(buffer);
        }
        long materialsVa = materials.va();
        long settingsVa = createSettingsFromTriangles(
                numTriangles, buffer, materialsVa);
        setVirtualAddressAsCoOwner(settingsVa, EShapeSubType.Mesh);
    }

    /**
     * Instantiate with the specified native object assigned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    MeshShapeSettings(long settingsVa) {
        setVirtualAddressAsCoOwner(settingsVa, EShapeSubType.Mesh);
    }

    /**
     * Instantiate settings for the specified list of triangles.
     *
     * @param triangleList the list of triangles (not {@code null}, unaffected)
     */
    public MeshShapeSettings(TriangleList triangleList) {
        long listVa = triangleList.va();
        long settingsVa = createFromTriangleList(listVa);
        setVirtualAddressAsCoOwner(settingsVa, EShapeSubType.Mesh);
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
     * @param vertex the location of the vertex to add (not {@code null},
     * unaffected)
     */
    public void addTriangleVertex(ConstFloat3 vertex) {
        long settingsVa = va();
        float x = vertex.x();
        float y = vertex.y();
        float z = vertex.z();
        addTriangleVertex(settingsVa, x, y, z);
    }

    /**
     * Access the list of indexed triangles. (native attribute:
     * mIndexedTriangles)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public IndexedTriangleList getIndexedTriangles() {
        long settingsVa = va();
        long resultVa = getIndexedTriangles(settingsVa);
        IndexedTriangleList result = new IndexedTriangleList(this, resultVa);

        return result;
    }

    /**
     * Access the list of physics materials. (native attribute: mMaterials)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public PhysicsMaterialList getMaterials() {
        long settingsVa = va();
        long resultVa = getMaterials(settingsVa);
        PhysicsMaterialList result = new PhysicsMaterialList(this, resultVa);

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
     * Sanitize the mesh data to remove degenerate and duplicate triangles.
     */
    public void sanitize() {
        long settingsVa = va();
        sanitize(settingsVa);
    }

    /**
     * Copy the argument to the current settings.
     *
     * @param source the settings to copy (not {@code null}, unaffected)
     */
    public void set(ConstMeshShapeSettings source) {
        long targetVa = va();
        long sourceVa = source.targetVa();
        assign(targetVa, sourceVa);
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
     * Alter the build quality. (native attribute: mBuildQuality)
     *
     * @param quality the desired quality (not {@code null},
     * default=FavorRuntimePerformance)
     */
    public void setBuildQuality(EBuildQuality quality) {
        long settingsVa = va();
        int ordinal = quality.ordinal();
        setBuildQuality(settingsVa, ordinal);
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
    // ConstMeshShapeSettings methods

    /**
     * Count the physics materials. The settings are unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int countMaterials() {
        long settingsVa = va();
        int result = countMaterials(settingsVa);

        return result;
    }

    /**
     * Count the triangles in the mesh. The settings are unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
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
    @Override
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
    @Override
    public float getActiveEdgeCosThresholdAngle() {
        long settingsVa = va();
        float result = getActiveEdgeCosThresholdAngle(settingsVa);

        return result;
    }

    /**
     * Return the build quality. The settings are unaffected. (native attribute:
     * mBuildQuality)
     *
     * @return an enum value (not {@code null})
     */
    @Override
    public EBuildQuality getBuildQuality() {
        long settingsVa = va();
        int ordinal = getBuildQuality(settingsVa);
        EBuildQuality result = EBuildQuality.values()[ordinal];

        return result;
    }

    /**
     * Copy the specified vertex. The settings are unaffected.
     *
     * @param vertexIndex the index of the vertex to copy (&ge;0)
     * @return a new vector
     */
    @Override
    public Float3 getTriangleVertex(int vertexIndex) {
        long settingsVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getTriangleVertex(settingsVa, vertexIndex, storeFloats);
        Float3 result = new Float3(storeFloats);

        return result;
    }

    /**
     * Copy the vertex list. The settings are unaffected. (native attribute:
     * mTriangleVertices)
     *
     * @return a new object
     */
    @Override
    public VertexList getTriangleVertices() {
        VertexList result = new VertexList();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();

        long settingsVa = va();
        long numVertices = countTriangleVertices(settingsVa);
        for (int vertexIndex = 0; vertexIndex < numVertices; ++vertexIndex) {
            getTriangleVertex(settingsVa, vertexIndex, storeFloats);
            result.set(vertexIndex, storeFloats);
        }

        return result;
    }

    /**
     * Return the maximum number of triangles per leaf. The settings are
     * unaffected. (native attribute: mMaxTrianglesPerLeaf)
     *
     * @return the maximum number
     */
    @Override
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
    @Override
    public boolean getPerTriangleUserData() {
        long settingsVa = va();
        boolean result = getPerTriangleUserData(settingsVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static void addIndexedTriangle(
            long settingsVa, int vi0, int vi1, int vi2);

    native private static void addTriangleVertex(
            long settingsVa, float x, float y, float z);

    native private static void assign(long targetVa, long sourceVa);

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static long createFromTriangleList(long listVa);

    native private static long createFromTrianglesNoMats(
            int numTriangles, FloatBuffer positionBuffer);

    native private static long createMeshShapeSettings(
            int numVertices, FloatBuffer vertices, long indicesVa);

    native private static long createSettingsFromTriangles(
            int numTriangles, FloatBuffer buffer, long materialsVa);

    native private static int countMaterials(long settingsVa);

    native private static int countTriangles(long settingsVa);

    native private static int countTriangleVertices(long settingsVa);

    native private static float getActiveEdgeCosThresholdAngle(long settingsVa);

    native private static int getBuildQuality(long settingsVa);

    native private static long getIndexedTriangles(long settingsVa);

    native private static long getMaterials(long settingsVa);

    native private static int getMaxTrianglesPerLeaf(long settingsVa);

    native private static boolean getPerTriangleUserData(long settingsVa);

    native private static void getTriangleVertex(
            long settingsVa, int vertexIndex, FloatBuffer storeFloats);

    native private static void reserveIndexedTriangles(
            long settingsVa, int numTriangles);

    native private static void reserveTriangleVertices(
            long settingsVa, int numVertices);

    native private static void sanitize(long settingsVa);

    native private static void setActiveEdgeCosThresholdAngle(
            long settingsVa, float cosine);

    native private static void setBuildQuality(long settingsVa, int ordinal);

    native private static void setMaxTrianglesPerLeaf(
            long settingsVa, int numTriangles);

    native private static void setPerTriangleUserData(
            long settingsVa, boolean include);
}
