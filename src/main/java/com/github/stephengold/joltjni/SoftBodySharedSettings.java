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

import com.github.stephengold.joltjni.enumerate.EBendType;
import com.github.stephengold.joltjni.readonly.ConstFace;
import com.github.stephengold.joltjni.readonly.ConstSoftBodySharedSettings;
import com.github.stephengold.joltjni.readonly.ConstVertexAttributes;
import com.github.stephengold.joltjni.template.RefTarget;
import java.nio.IntBuffer;

/**
 * Properties of a soft body that may be shared among multiple bodies.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class SoftBodySharedSettings
        extends JoltPhysicsObject
        implements ConstSoftBodySharedSettings, RefTarget {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the default settings.
     */
    public SoftBodySharedSettings() {
        long settingsVa = createDefault();
        setVirtualAddress(settingsVa); // not owner due to ref counting
    }

    /**
     * Instantiate with the specified native object assigned but not owned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    SoftBodySharedSettings(long settingsVa) {
        setVirtualAddress(settingsVa); // not owner due to ref counting
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Add the specified edge constraint.
     *
     * @param edge the edge to add (not null, unaffected)
     */
    public void addEdgeConstraint(Edge edge) {
        long settingsVa = va();
        long edgeVa = edge.va();
        addEdgeConstraint(settingsVa, edgeVa);
    }

    /**
     * Add the specified face.
     *
     * @param face the face to add (not null, unaffected)
     */
    public void addFace(ConstFace face) {
        long settingsVa = va();
        long faceVa = face.targetVa();
        addFace(settingsVa, faceVa);
    }

    /**
     * Append the specified inverse-bind matrix. (native property:
     * mInvBindMatrices)
     *
     * @param invBind the matrix to add (not null)
     */
    public void addInvBindMatrix(InvBind invBind) {
        long settingsVa = va();
        long invBindVa = invBind.va();
        addInvBindMatrix(settingsVa, invBindVa);
    }

    /**
     * Append the specified skinning constraint. (native property:
     * mSkinnedConstraints)
     *
     * @param skinned the constraint to add (not null)
     */
    public void addSkinnedConstraint(Skinned skinned) {
        long settingsVa = va();
        long skinnedVa = skinned.va();
        addSkinnedConstraint(settingsVa, skinnedVa);
    }

    /**
     * Add the specified vertex. (native attribute: mVertices)
     *
     * @param vertex the vertex to add (not null, unaffected)
     */
    public void addVertex(Vertex vertex) {
        long settingsVa = va();
        long vertexVa = vertex.va();
        addVertex(settingsVa, vertexVa);
    }

    /**
     * Add the specified volume constraint.
     *
     * @param volume the constraint to add (not null, unaffected)
     */
    public void addVolumeConstraint(Volume volume) {
        long settingsVa = va();
        long volumeVa = volume.va();
        addVolumeConstraint(settingsVa, volumeVa);
    }

    /**
     * Calculate the initial lengths of all edges in the body.
     */
    public void calculateEdgeLengths() {
        long settingsVa = va();
        calculateEdgeLengths(settingsVa);
    }

    /**
     * Calculate the information needed for skinned constraint normals.
     */
    public void calculateSkinnedConstraintNormals() {
        long settingsVa = va();
        calculateSkinnedConstraintNormals(settingsVa);
    }

    /**
     * Calculate the initial volumes of all tetrahedra in the body.
     */
    public void calculateVolumeConstraintVolumes() {
        long settingsVa = va();
        calculateVolumeConstraintVolumes(settingsVa);
    }

    /**
     * Automatically generate constraints based on the faces.
     *
     * @param vertexAttributes the desired attributes (one for each vertex)
     * @param numAttributes the number of attributes provided (&ge;0)
     * @param bendType the desired type of bend constraint (not null,
     * default=Distance)
     */
    public void createConstraints(ConstVertexAttributes vertexAttributes,
            int numAttributes, EBendType bendType) {
        createConstraints(
                new ConstVertexAttributes[]{vertexAttributes}, bendType);
    }

    /**
     * Automatically generate constraints based on the faces.
     *
     * @param vertexAttributes the desired attributes (one for each vertex)
     * @param bendType the desired type of bend constraint (not null,
     * default=Distance)
     */
    public void createConstraints(
            ConstVertexAttributes[] vertexAttributes, EBendType bendType) {
        createConstraints(
                vertexAttributes, bendType, Jolt.degreesToRadians(8f));
    }

    /**
     * Automatically generate constraints based on the faces.
     *
     * @param vertexAttributes the desired attributes (one for each vertex)
     * @param bendType the desired type of bend constraint (not null,
     * default=Distance)
     * @param angleTolerance the desired tolerance for creating shear edges (in
     * radians, default=2*Pi/45)
     */
    public void createConstraints(ConstVertexAttributes[] vertexAttributes,
            EBendType bendType, float angleTolerance) {
        long settingsVa = va();
        int numAttributes = vertexAttributes.length;
        long[] attributeVas = new long[numAttributes];
        for (int i = 0; i < numAttributes; ++i) {
            attributeVas[i] = vertexAttributes[i].targetVa();
        }
        int ordinal = bendType.ordinal();
        createConstraints(settingsVa, attributeVas, ordinal, angleTolerance);
    }

    /**
     * Enumerate all edge constraints in the settings. (native attribute:
     * mEdgeConstraints)
     *
     * @return a new array of new JVM objects with the pre-existing native
     * objects assigned
     */
    public Edge[] getEdgeConstraints() {
        long settingsVa = va();
        int numEdges = countEdgeConstraints(settingsVa);
        Edge[] result = new Edge[numEdges];
        for (int index = 0; index < numEdges; ++index) {
            long edgeVa = getEdgeConstraint(settingsVa, index);
            result[index] = new Edge(this, edgeVa);
        }

        return result;
    }

    /**
     * Access the specified vertex.
     *
     * @param index the index of the vertex (&ge;0)
     * @return a new JVM object with the pre-existing native object assigned
     */
    public Vertex getVertex(int index) {
        long settingsVa = va();
        long vertexVa = getVertex(settingsVa, index);
        Vertex result = new Vertex(this, vertexVa);

        return result;
    }

    /**
     * Enumerate all vertices in the settings. (native attribute: mVertices)
     *
     * @return a new array of new JVM objects with pre-existing native objects
     * assigned
     */
    public Vertex[] getVertices() {
        long settingsVa = va();
        int numVertices = countVertices(settingsVa);
        Vertex[] result = new Vertex[numVertices];
        for (int index = 0; index < numVertices; ++index) {
            long vertexVa = getVertex(settingsVa, index);
            result[index] = new Vertex(this, vertexVa);
        }

        return result;
    }

    /**
     * Optimize the settings without writing any results.
     */
    public void optimize() {
        long settingsVa = va();
        optimize(settingsVa);
    }

    /**
     * Create a cube with edge constraints, volume constraints, and faces.
     *
     * @param gridSize the desired number of points on each axis (&ge;1)
     * @param gridSpacing the distance between adjacent points
     * @return a counted reference to new settings
     */
    public static SoftBodySharedSettingsRef sCreateCube(
            int gridSize, float gridSpacing) {
        long refVa = sCreateCubeNative(gridSize, gridSpacing);
        SoftBodySharedSettingsRef result
                = new SoftBodySharedSettingsRef(refVa, true);

        return result;
    }

    /**
     * Replace the materials. (native attribute: mMaterials)
     *
     * @param material the desired material, or {@code null}
     */
    public void setMaterials(PhysicsMaterial material) {
        long settingsVa = va();
        long materialVa = (material == null) ? 0L : material.va();
        setMaterialsSingle(settingsVa, materialVa);
    }

    /**
     * Alter the size of every particle.
     *
     * @param radius the desired radius (&ge;0, default=0)
     */
    public void setVertexRadius(float radius) {
        long settingsVa = va();
        setVertexRadius(settingsVa, radius);
    }
    // *************************************************************************
    // ConstSoftBodySharedSettings methods

    /**
     * Count the edge constraints. The settings are unaffected. (native
     * attribute: mEdgeConstraints)
     *
     * @return the count (&ge;0)
     */
    @Override
    public int countEdgeConstraints() {
        long settingsVa = va();
        int result = countEdgeConstraints(settingsVa);

        return result;
    }

    /**
     * Count the faces. The settings are unaffected. (native attribute: mFaces)
     *
     * @return the count (&ge;0)
     */
    @Override
    public int countFaces() {
        long settingsVa = va();
        int result = countFaces(settingsVa);

        return result;
    }

    /**
     * Count the pinned vertices. The settings are unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int countPinnedVertices() {
        long settingsVa = va();
        int result = countPinnedVertices(settingsVa);

        return result;
    }

    /**
     * Count the vertices. The settings are unaffected. (native attribute:
     * mVertices)
     *
     * @return the count (&ge;0)
     */
    @Override
    public int countVertices() {
        long settingsVa = va();
        int result = countVertices(settingsVa);

        return result;
    }

    /**
     * Count the volume constraints. The settings are unaffected. (native
     * attribute: mVolumeConstraints)
     *
     * @return the count (&ge;0)
     */
    @Override
    public int countVolumeConstraints() {
        long settingsVa = va();
        int result = countVolumeConstraints(settingsVa);

        return result;
    }

    /**
     * Return the radius of each particle. The settings are unaffected. (native
     * attribute: mVertexRadius)
     *
     * @return the radius (in meters)
     */
    @Override
    public float getVertexRadius() {
        long settingsVa = va();
        float result = getVertexRadius(settingsVa);

        return result;
    }

    /**
     * Write the vertex indices of all edges to the specified buffer and advance
     * the buffer's position. The settings are unaffected.
     *
     * @param storeIndices the destination buffer (not null, modified)
     */
    @Override
    public void putEdgeIndices(IntBuffer storeIndices) {
        long settingsVa = va();
        int bufferPosition = storeIndices.position();
        bufferPosition = putEdgeIndices(
                settingsVa, bufferPosition, storeIndices);
        storeIndices.position(bufferPosition);
    }

    /**
     * Write the vertex indices of all faces to the specified buffer and advance
     * the buffer's position. The settings are unaffected.
     *
     * @param storeIndices the destination buffer (not null, modified)
     */
    @Override
    public void putFaceIndices(IntBuffer storeIndices) {
        long settingsVa = va();
        int bufferPosition = storeIndices.position();
        bufferPosition = putFaceIndices(
                settingsVa, bufferPosition, storeIndices);
        storeIndices.position(bufferPosition);
    }
    // *************************************************************************
    // RefTarget methods

    /**
     * Count the active references to the native {@code SoftBodySharedSettings}.
     * The settings are unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int getRefCount() {
        long settingsVa = va();
        int result = getRefCount(settingsVa);

        return result;
    }

    /**
     * Mark the native {@code SoftBodySharedSettings} as embedded.
     */
    @Override
    public void setEmbedded() {
        long settingsVa = va();
        setEmbedded(settingsVa);
    }

    /**
     * Create a counted reference to the native {@code SoftBodySharedSettings}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public SoftBodySharedSettingsRef toRef() {
        long settingsVa = va();
        long refVa = toRef(settingsVa);
        SoftBodySharedSettingsRef result
                = new SoftBodySharedSettingsRef(refVa, true);

        return result;
    }
    // *************************************************************************
    // native methods

    native static void addEdgeConstraint(long settingsVa, long edgeVa);

    native static void addFace(long settingsVa, long faceVa);

    native static void addInvBindMatrix(long settingsVa, long invBindVa);

    native static void addSkinnedConstraint(long settingsVa, long skinnedVa);

    native static void addVertex(long settingsVa, long vertexVa);

    native static void addVolumeConstraint(long settingsVa, long volumeVa);

    native static void calculateEdgeLengths(long settingsVa);

    native static void calculateSkinnedConstraintNormals(long settingsVa);

    native static void calculateVolumeConstraintVolumes(long settingsVa);

    native static int countEdgeConstraints(long settingsVa);

    native static int countFaces(long settingsVa);

    native static int countPinnedVertices(long settingsVa);

    native static int countVertices(long settingsVa);

    native static int countVolumeConstraints(long settingsVa);

    native static void createConstraints(long settingsVa,
            long[] attributeVas, int ordinal, float angleTolerance);

    native private static long createDefault();

    native private static long getEdgeConstraint(long settingsVa, int index);

    native private static int getRefCount(long settingsVa);

    native private static long getVertex(long settingsVa, int index);

    native static float getVertexRadius(long settingsVa);

    native static void optimize(long settingsVa);

    native static int putEdgeIndices(
            long settingsVa, int bufferPosition, IntBuffer storeIndices);

    native static int putFaceIndices(
            long settingsVa, int bufferPosition, IntBuffer storeIndices);

    native private static void setEmbedded(long settingsVa);

    native private static long sCreateCubeNative(
            int gridSize, float gridSpacing);

    native static void setMaterialsSingle(long settingsVa, long materialVa);

    native static void setVertexRadius(long settingsVa, float radius);

    native private static long toRef(long settingsVa);
}
