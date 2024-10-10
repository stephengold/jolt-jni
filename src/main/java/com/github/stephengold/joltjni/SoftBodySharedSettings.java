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

import com.github.stephengold.joltjni.enumerate.EBendType;
import com.github.stephengold.joltjni.readonly.ConstSoftBodySharedSettings;
import com.github.stephengold.joltjni.readonly.ConstVertexAttributes;
import com.github.stephengold.joltjni.template.RefTarget;

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
        setVirtualAddress(settingsVa, () -> free(settingsVa));
    }

    /**
     * Instantiate with the specified native object assigned but not owned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    SoftBodySharedSettings(long settingsVa) {
        setVirtualAddress(settingsVa, null);
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
    public void addFace(Face face) {
        long settingsVa = va();
        long faceVa = face.va();
        addFace(settingsVa, faceVa);
    }

    /**
     * Add the specified vertex.
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
     * radians, default=2/45)
     */
    public void createConstraints(ConstVertexAttributes[] vertexAttributes,
            EBendType bendType, float angleTolerance) {
        long settingsVa = va();
        int numAttributes = vertexAttributes.length;
        long[] attributeVas = new long[numAttributes];
        for (int i = 0; i < numAttributes; ++i) {
            attributeVas[i] = vertexAttributes[i].va();
        }
        int ordinal = bendType.ordinal();
        createConstraints(settingsVa, attributeVas, ordinal, angleTolerance);
    }

    /**
     * Optimize the settings without writing any results.
     */
    public void optimize() {
        long settingsVa = va();
        optimize(settingsVa);
    }
    // *************************************************************************
    // ConstSoftBodySharedSettings methods

    /**
     * Count the edge constraints. The settings are unaffected.
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
     * Count the faces. The settings are unaffected.
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
     * Count the vertices. The settings are unaffected.
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
     * Count the volume constraints. The settings are unaffected.
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

    native private static void addEdgeConstraint(long settingsVa, long edgeVa);

    native private static void addFace(long settingsVa, long faceVa);

    native private static void addVertex(long settingsVa, long vertexVa);

    native private static void addVolumeConstraint(
            long settingsVa, long volumeVa);

    native private static void calculateEdgeLengths(long settingsVa);

    native private static void calculateVolumeConstraintVolumes(
            long settingsVa);

    native private static int countEdgeConstraints(long settingsVa);

    native private static int countFaces(long settingsVa);

    native private static int countVertices(long settingsVa);

    native private static int countVolumeConstraints(long settingsVa);

    native private static void createConstraints(long settingsVa,
            long[] attributeVas, int ordinal, float angleTolerance);

    native private static long createDefault();

    native private static void free(long settingsVa);

    native private static int getRefCount(long settingsVa);

    native private static float getVertexRadius(long settingsVa);

    native private static void optimize(long settingsVa);

    native private static void setEmbedded(long settingsVa);

    native private static long toRef(long settingsVa);
}
