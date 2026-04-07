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

import com.github.stephengold.joltjni.enumerate.EBendType;
import com.github.stephengold.joltjni.readonly.ConstEdge;
import com.github.stephengold.joltjni.readonly.ConstFace;
import com.github.stephengold.joltjni.readonly.ConstRodBendTwist;
import com.github.stephengold.joltjni.readonly.ConstRodStretchShear;
import com.github.stephengold.joltjni.readonly.ConstSoftBodySharedSettings;
import com.github.stephengold.joltjni.readonly.ConstVertex;
import com.github.stephengold.joltjni.readonly.ConstVertexAttributes;
import com.github.stephengold.joltjni.readonly.ConstVolume;
import com.github.stephengold.joltjni.streamutils.MaterialToIdMap;
import com.github.stephengold.joltjni.streamutils.SharedSettingsToIdMap;
import com.github.stephengold.joltjni.template.Ref;
import java.nio.IntBuffer;

/**
 * A counted reference to a {@code SoftBodySharedSettings} object. (native type:
 * {@code Ref<SoftBodySharedSettings>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class SoftBodySharedSettingsRef
        extends Ref
        implements ConstSoftBodySharedSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty reference.
     */
    public SoftBodySharedSettingsRef() {
        long refVa = createDefault();
        setVirtualAddress(refVa, () -> free(refVa));
    }

    /**
     * Instantiate an empty reference with the specified native object assigned.
     *
     * @param refVa the virtual address of the native object to assign (not
     * zero)
     */
    SoftBodySharedSettingsRef(long refVa) {
        assert getPtr(refVa) == 0L;

        Runnable freeingAction = () -> free(refVa);
        setVirtualAddress(refVa, freeingAction);
    }

    /**
     * Instantiate a counted reference to the specified settings.
     *
     * @param refVa the virtual address of the native object to assign (not
     * zero)
     * @param settings the settings to target (not {@code null})
     */
    SoftBodySharedSettingsRef(long refVa, SoftBodySharedSettings settings) {
        assert settings != null;

        this.ptr = settings;
        Runnable freeingAction = () -> free(refVa);
        setVirtualAddress(refVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Add the specified edge constraint.
     *
     * @param edge the edge to add (not null, unaffected)
     */
    public void addEdgeConstraint(ConstEdge edge) {
        long settingsVa = targetVa();
        long edgeVa = edge.targetVa();
        SoftBodySharedSettings.addEdgeConstraint(settingsVa, edgeVa);
    }

    /**
     * Add the specified face.
     *
     * @param face the face to add (not null, unaffected)
     */
    public void addFace(ConstFace face) {
        long settingsVa = targetVa();
        long faceVa = face.targetVa();
        SoftBodySharedSettings.addFace(settingsVa, faceVa);
    }

    /**
     * Append the specified inverse-bind matrix. (native member:
     * mInvBindMatrices)
     *
     * @param invBind the matrix to add (not null)
     */
    public void addInvBindMatrix(InvBind invBind) {
        long settingsVa = targetVa();
        long invBindVa = invBind.va();
        SoftBodySharedSettings.addInvBindMatrix(settingsVa, invBindVa);
    }

    /**
     * Append the specified bend-twist constraint. (native member:
     * mRodBendTwistConstraints)
     *
     * @param constraint the constraint to add (not null)
     */
    public void addRodBendTwistConstraint(ConstRodBendTwist constraint) {
        long settingsVa = targetVa();
        long constraintVa = constraint.targetVa();
        SoftBodySharedSettings.addRodBendTwistConstraint(
                settingsVa, constraintVa);
    }

    /**
     * Append the specified discrete Cosserat rod. (native member:
     * mRodStretchShearConstraints)
     *
     * @param rod the rod to add (not null)
     */
    public void addRodStretchShearConstraint(ConstRodStretchShear rod) {
        long settingsVa = targetVa();
        long rodVa = rod.targetVa();
        SoftBodySharedSettings.addRodStretchShearConstraint(settingsVa, rodVa);
    }

    /**
     * Append the specified skinning constraint. (native member:
     * mSkinnedConstraints)
     *
     * @param skinned the constraint to add (not null)
     */
    public void addSkinnedConstraint(Skinned skinned) {
        long settingsVa = targetVa();
        long skinnedVa = skinned.va();
        SoftBodySharedSettings.addSkinnedConstraint(settingsVa, skinnedVa);
    }

    /**
     * Add the specified vertex. (native member: mVertices)
     *
     * @param vertex the vertex to add (not null, unaffected)
     */
    public void addVertex(ConstVertex vertex) {
        long settingsVa = targetVa();
        long vertexVa = vertex.targetVa();
        SoftBodySharedSettings.addVertex(settingsVa, vertexVa);
    }

    /**
     * Add the specified volume constraint. (native member: mVolumeConstraints)
     *
     * @param volume the constraint to add (not null, unaffected)
     */
    public void addVolumeConstraint(ConstVolume volume) {
        long settingsVa = targetVa();
        long volumeVa = volume.targetVa();
        SoftBodySharedSettings.addVolumeConstraint(settingsVa, volumeVa);
    }

    /**
     * Calculate the initial lengths of all edges in the body.
     */
    public void calculateEdgeLengths() {
        long settingsVa = targetVa();
        SoftBodySharedSettings.calculateEdgeLengths(settingsVa);
    }

    /**
     * Calculate the information needed to simulate Cosserat rods.
     */
    public void calculateRodProperties() {
        long settingsVa = targetVa();
        SoftBodySharedSettings.calculateRodProperties(settingsVa);
    }

    /**
     * Calculate the information needed for skinned constraint normals.
     */
    public void calculateSkinnedConstraintNormals() {
        long settingsVa = targetVa();
        SoftBodySharedSettings.calculateSkinnedConstraintNormals(settingsVa);
    }

    /**
     * Calculate the initial volumes of all tetrahedra in the body.
     */
    public void calculateVolumeConstraintVolumes() {
        long settingsVa = targetVa();
        SoftBodySharedSettings.calculateVolumeConstraintVolumes(settingsVa);
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
        long settingsVa = targetVa();
        int numAttributes = vertexAttributes.length;
        long[] attributeVas = new long[numAttributes];
        for (int i = 0; i < numAttributes; ++i) {
            attributeVas[i] = vertexAttributes[i].targetVa();
        }
        int ordinal = bendType.ordinal();
        SoftBodySharedSettings.createConstraints(
                settingsVa, attributeVas, ordinal, angleTolerance);
    }

    /**
     * Optimize the settings without writing any results.
     */
    public void optimize() {
        long settingsVa = targetVa();
        SoftBodySharedSettings.optimize(settingsVa);
    }

    /**
     * Replace the materials. (native member: mMaterials)
     *
     * @param material the desired material, or {@code null}
     */
    public void setMaterials(PhysicsMaterial material) {
        long settingsVa = targetVa();
        long materialVa = (material == null) ? 0L : material.va();
        SoftBodySharedSettings.setMaterialsSingle(settingsVa, materialVa);
    }
    // *************************************************************************
    // new protected methods

    /**
     * Update the cached target.
     */
    void updatePtr() {
        long refVa = va();
        long targetVa = getPtr(refVa);
        if (targetVa == 0L) {
            this.ptr = null;
        } else {
            this.ptr = new SoftBodySharedSettings(targetVa);
        }
    }
    // *************************************************************************
    // ConstSoftBodySharedSettings methods

    /**
     * Count the edge constraints. The settings are unaffected. (native member:
     * mEdgeConstraints)
     *
     * @return the count (&ge;0)
     */
    @Override
    public int countEdgeConstraints() {
        long settingsVa = targetVa();
        int result = SoftBodySharedSettings.countEdgeConstraints(settingsVa);

        return result;
    }

    /**
     * Count the faces. The settings are unaffected. (native member: mFaces)
     *
     * @return the count (&ge;0)
     */
    @Override
    public int countFaces() {
        long settingsVa = targetVa();
        int result = SoftBodySharedSettings.countFaces(settingsVa);

        return result;
    }

    /**
     * Count the pinned vertices. The settings are unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int countPinnedVertices() {
        long settingsVa = targetVa();
        int result = SoftBodySharedSettings.countPinnedVertices(settingsVa);

        return result;
    }

    /**
     * Count the bend-twist constraints. The settings are unaffected. (native
     * member: mRodBendTwistConstraint)
     *
     * @return the count (&ge;0)
     */
    @Override
    public int countRodBendTwistConstraints() {
        long settingsVa = targetVa();
        int result = SoftBodySharedSettings.countRodBendTwistConstraints(
                settingsVa);

        return result;
    }

    /**
     * Count the discrete Cosserat rods. The settings are unaffected. (native
     * member: mRodStretchShearConstraint)
     *
     * @return the count (&ge;0)
     */
    @Override
    public int countRodStretchShearConstraints() {
        long settingsVa = targetVa();
        int result = SoftBodySharedSettings.countRodStretchShearConstraints(
                settingsVa);

        return result;
    }

    /**
     * Count the vertices. The settings are unaffected. (native member:
     * mVertices)
     *
     * @return the count (&ge;0)
     */
    @Override
    public int countVertices() {
        long settingsVa = targetVa();
        int result = SoftBodySharedSettings.countVertices(settingsVa);

        return result;
    }

    /**
     * Count the volume constraints. The settings are unaffected. (native
     * member: mVolumeConstraints)
     *
     * @return the count (&ge;0)
     */
    @Override
    public int countVolumeConstraints() {
        long settingsVa = targetVa();
        int result = SoftBodySharedSettings.countVolumeConstraints(settingsVa);

        return result;
    }

    /**
     * Enumerate all edge constraints in the settings. (native member:
     * mEdgeConstraints)
     *
     * @return a new array of new JVM objects with the pre-existing native
     * objects assigned
     */
    @Override
    public Edge[] getEdgeConstraints() {
        long settingsVa = targetVa();
        int numEdges = SoftBodySharedSettings.countEdgeConstraints(settingsVa);
        Edge[] result = new Edge[numEdges];
        for (int index = 0; index < numEdges; ++index) {
            long edgeVa = SoftBodySharedSettings
                    .getEdgeConstraint(settingsVa, index);
            result[index] = new Edge(this, edgeVa);
        }

        return result;
    }

    /**
     * Enumerate all bend-twist constraints in the settings. (native member:
     * mRodBendTwistConstraints)
     *
     * @return a new array of new JVM objects with the pre-existing native
     * objects assigned
     */
    @Override
    public RodBendTwist[] getRodBendTwistConstraints() {
        long settingsVa = targetVa();
        int numConstraints = SoftBodySharedSettings
                .countRodBendTwistConstraints(settingsVa);
        RodBendTwist[] result = new RodBendTwist[numConstraints];
        for (int index = 0; index < numConstraints; ++index) {
            long edgeVa = SoftBodySharedSettings
                    .getRodBendTwistConstraint(settingsVa, index);
            result[index] = new RodBendTwist(this, edgeVa);
        }

        return result;
    }

    /**
     * Enumerate all Cosserat rods in the settings. (native member:
     * mRodStretchShearConstraints)
     *
     * @return a new array of new JVM objects with the pre-existing native
     * objects assigned
     */
    @Override
    public RodStretchShear[] getRodStretchShearConstraints() {
        long settingsVa = targetVa();
        int numRods = SoftBodySharedSettings.countRodBendTwistConstraints(
                settingsVa);
        RodStretchShear[] result = new RodStretchShear[numRods];
        for (int index = 0; index < numRods; ++index) {
            long rodVa = SoftBodySharedSettings.getRodStretchShearConstraint(
                    settingsVa, index);
            result[index] = new RodStretchShear(this, rodVa);
        }

        return result;
    }

    /**
     * Access the specified vertex.
     *
     * @param index the index of the vertex (&ge;0)
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public Vertex getVertex(int index) {
        long settingsVa = targetVa();
        long vertexVa = SoftBodySharedSettings.getVertex(settingsVa, index);
        Vertex result = new Vertex(this, vertexVa);

        return result;
    }

    /**
     * Enumerate all vertices in the settings. (native member: mVertices)
     *
     * @return a new array of new JVM objects with pre-existing native objects
     * assigned
     */
    @Override
    public Vertex[] getVertices() {
        long settingsVa = targetVa();
        int numVertices = SoftBodySharedSettings.countVertices(settingsVa);
        Vertex[] result = new Vertex[numVertices];
        for (int index = 0; index < numVertices; ++index) {
            long vertexVa = SoftBodySharedSettings.getVertex(settingsVa, index);
            result[index] = new Vertex(this, vertexVa);
        }

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
        long settingsVa = targetVa();
        int bufferPosition = storeIndices.position();
        bufferPosition = SoftBodySharedSettings.putEdgeIndices(
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
        long settingsVa = targetVa();
        int bufferPosition = storeIndices.position();
        bufferPosition = SoftBodySharedSettings.putFaceIndices(
                settingsVa, bufferPosition, storeIndices);
        storeIndices.position(bufferPosition);
    }

    /**
     * Write the vertex indices of all Cosserat rods to the specified buffer and
     * advance the buffer's position. The settings are unaffected.
     *
     * @param storeIndices the destination buffer (not null, modified)
     */
    @Override
    public void putRodIndices(IntBuffer storeIndices) {
        long settingsVa = targetVa();
        int bufferPosition = storeIndices.position();
        bufferPosition = SoftBodySharedSettings.putRodIndices(
                settingsVa, bufferPosition, storeIndices);
        storeIndices.position(bufferPosition);
    }

    /**
     * Write the state of this object to the specified stream, excluding the
     * materials. The settings are unaffected.
     *
     * @param stream where to write objects (not {@code null})
     */
    @Override
    public void saveBinaryState(StreamOut stream) {
        long settingsVa = targetVa();
        long streamVa = stream.va();
        SoftBodySharedSettings.saveBinaryState(settingsVa, streamVa);
    }

    /**
     * Write the state of this object to the specified stream. The settings are
     * unaffected.
     *
     * @param stream where to write objects (not {@code null})
     * @param settingsMap track multiple uses of shared settings (not
     * {@code null})
     * @param materialMap track multiple uses of physics materials (not
     * {@code null})
     */
    @Override
    public void saveWithMaterials(StreamOut stream,
            SharedSettingsToIdMap settingsMap, MaterialToIdMap materialMap) {
        long settingsVa = targetVa();
        long streamVa = stream.va();
        long settingsMapVa = settingsMap.va();
        long materialMapVa = materialMap.va();
        SoftBodySharedSettings.saveWithMaterials(
                settingsVa, streamVa, settingsMapVa, materialMapVa);
    }
    // *************************************************************************
    // Ref methods

    /**
     * Access the targeted settings, if any.
     *
     * @return the pre-existing object, or {@code null} if the reference is
     * empty
     */
    @Override
    public SoftBodySharedSettings getPtr() {
        SoftBodySharedSettings result = (SoftBodySharedSettings) ptr;
        return result;
    }

    /**
     * Return the address of the native {@code SoftBodySharedSettings}. No
     * objects are affected.
     *
     * @return the virtual address, or zero if the reference is empty
     */
    @Override
    public long targetVa() {
        long refVa = va();
        long result = getPtr(refVa);
        assert result == (ptr == null ? 0L : getPtr().va());

        return result;
    }

    /**
     * Create another counted reference to the targeted settings.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public SoftBodySharedSettingsRef toRef() {
        SoftBodySharedSettingsRef result;
        if (ptr == null) {
            result = new SoftBodySharedSettingsRef();
        } else {
            long refVa = va();
            long copyVa = copy(refVa);
            SoftBodySharedSettings settings = (SoftBodySharedSettings) ptr;
            result = new SoftBodySharedSettingsRef(copyVa, settings);
        }

        return result;
    }
    // *************************************************************************
    // native methods

    native private static long copy(long refVa);

    native private static long createDefault();

    native static void free(long refVa);

    native static long getPtr(long refVa);
}
