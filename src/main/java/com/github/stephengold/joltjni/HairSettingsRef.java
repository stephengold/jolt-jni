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

import com.github.stephengold.joltjni.readonly.ConstFloat3;
import com.github.stephengold.joltjni.readonly.ConstHairMaterial;
import com.github.stephengold.joltjni.readonly.ConstHairSettings;
import com.github.stephengold.joltjni.readonly.ConstIndexedTriangleNoMaterial;
import com.github.stephengold.joltjni.readonly.Mat44Arg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import com.github.stephengold.joltjni.template.Ref;
import java.nio.FloatBuffer;
import java.nio.LongBuffer;

/**
 * A counted reference to a {@code HairSettings} object. (native type:
 * {@code Ref<HairSettings>})
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class HairSettingsRef extends Ref implements ConstHairSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty reference.
     */
    public HairSettingsRef() {
        long refVa = createDefault();
        setVirtualAddress(refVa, () -> free(refVa));
    }

    /**
     * Instantiate a reference with the specified native object assigned.
     *
     * @param refVa the virtual address of the native object to assign (not
     * zero)
     * @param owner {@code true} &rarr; make the JVM object the owner,
     * {@code false} &rarr; it isn't the owner
     */
    HairSettingsRef(long refVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(refVa) : null;
        setVirtualAddress(refVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Append the specified material. (native member: mMaterial)
     *
     * @param material the material to add (not {@code null})
     */
    public void addMaterial(ConstHairMaterial material) {
        long settingsVa = targetVa();
        long materialVa = material.targetVa();
        HairSettings.addMaterial(settingsVa, materialVa);
    }

    /**
     * Access the render strands. (native member: mRenderStrands)
     *
     * @return a new array containing new JVM objects with the pre-existing
     * native object assigned
     */
    public RStrand[] getRenderStrands() {
        long settingsVa = targetVa();
        int numStrands = HairSettings.countRenderStrands(settingsVa);
        RStrand[] result = new RStrand[numStrands];
        for (int index = 0; index < numStrands; ++index) {
            long strandVa = HairSettings.getRenderStrand(settingsVa, index);
            result[index] = new RStrand(this, strandVa);
        }

        return result;
    }

    /**
     * Access the vertex indices of the triangles in the scalp mesh. (native
     * member: mScalpTriangles)
     *
     * @return a new array containing new JVM objects with the pre-existing
     * native object assigned
     */
    public IndexedTriangleNoMaterial[] getScalpTriangles() {
        long settingsVa = targetVa();
        int numTriangles = HairSettings.countScalpTriangles(settingsVa);
        IndexedTriangleNoMaterial[] result
                = new IndexedTriangleNoMaterial[numTriangles];
        for (int index = 0; index < numTriangles; ++index) {
            long triangleVa = HairSettings.getScalpTriangle(settingsVa, index);
            result[index] = new IndexedTriangleNoMaterial(this, triangleVa);
        }

        return result;
    }

    /**
     * Calculate simulation bounds and vertex properties.
     *
     * @param maxDistSqHairToScalp storage for the maximum squared distance of a
     * root vertex from the scalp (not {@code null}, length&gt;0)
     */
    public void init(float[] maxDistSqHairToScalp) {
        long settingsVa = targetVa();
        maxDistSqHairToScalp[0] = HairSettings.init(settingsVa);
    }

    /**
     * Further initialization to set up compute buffers. Invoke after
     * {@code init()}.
     *
     * @param system the compute system to use (not {@code null})
     */
    public void initCompute(ComputeSystem system) {
        long settingsVa = targetVa();
        long systemVa = system.va();
        HairSettings.initCompute(settingsVa, systemVa);
    }

    /**
     * Split the supplied strands into render and simulation strands and
     * calculate connections between them.
     *
     * @param vertices the vertices to be used in strands (not {@code null})
     * @param strands the strands to use (not {@code null})
     */
    public void initRenderAndSimulationStrands(
            SVertexList vertices, SStrandList strands) {
        long settingsVa = targetVa();
        long verticesVa = vertices.va();
        long strandsVa = strands.va();
        HairSettings.initRenderAndSimulationStrands(
                settingsVa, verticesVa, strandsVa);
    }

    /**
     * Read the state of this object from the specified stream, excluding the
     * shape and group filter.
     *
     * @param streamIn where to read objects from (not {@code null})
     */
    public void restoreBinaryState(StreamInWrapper streamIn) {
        long settingsVa = targetVa();
        long streamVa = streamIn.va();
        HairSettings.restoreBinaryState(settingsVa, streamVa);
    }

    /**
     * Alter the initial gravity vector. (native member: mInitialGravity)
     *
     * @param gravity the desired acceleration vector (meters per second squared
     * in system coordinates, not {@code null}, unaffected, default=(0,-9.81,0))
     * @return the current reference, for chaining
     */
    public HairSettingsRef setInitialGravity(Vec3Arg gravity) {
        long settingsVa = targetVa();
        float gx = gravity.getX();
        float gy = gravity.getY();
        float gz = gravity.getZ();
        HairSettings.setInitialGravity(settingsVa, gx, gy, gz);

        return this;
    }

    /**
     * Alter the iteration rate. (native member: mNumIterationsPerSecond)
     *
     * @param numIterations the desired number of iterations (&ge;1,
     * default=360)
     * @return the current reference, for chaining
     */
    public HairSettingsRef setNumIterationsPerSecond(int numIterations) {
        long settingsVa = targetVa();
        HairSettings.setNumIterationsPerSecond(settingsVa, numIterations);

        return this;
    }

    /**
     * Alter the scalp mesh-vertex locations in bind pose. (native member:
     * mScalpInverseBindPose)
     *
     * @param pose the desired pose (not {@code null}, unaffected)
     * @return the current reference, for chaining
     */
    public HairSettingsRef setScalpInverseBindPose(Mat44Arg... pose) {
        long settingsVa = targetVa();
        int numJoints = pose.length;
        LongBuffer poseVas = Jolt.newDirectLongBuffer(numJoints);
        for (int i = 0; i < numJoints; ++i) {
            poseVas.put(i, pose[i].targetVa());
        }
        HairSettings.setScalpInverseBindPose(settingsVa, poseVas);

        return this;
    }

    /**
     * Alter the number of skinning weights per vertex. (native member:
     * mScalpNumSkinWeightsPerVertex)
     *
     * @param numWeights the desired number of weights (&ge;0, default=0)
     * @return the current reference, for chaining
     */
    public HairSettingsRef setScalpNumSkinWeightsPerVertex(int numWeights) {
        long settingsVa = targetVa();
        HairSettings.setScalpNumSkinWeightsPerVertex(settingsVa, numWeights);

        return this;
    }

    /**
     * Alter the scalp skinning weights. (native member: mScalpSkinWeights)
     *
     * @param weights the desired weights of the scalp mesh (not {@code null},
     * {@code mScalpNumSkinWeightsPerVertex} elements per vertex, unaffected)
     * @return the current reference, for chaining
     */
    public HairSettingsRef setScalpSkinWeights(HairSkinWeight... weights) {
        long settingsVa = targetVa();
        int numWeights = weights.length;
        LongBuffer weightVas = Jolt.newDirectLongBuffer(numWeights);
        for (int i = 0; i < numWeights; ++i) {
            weightVas.put(i, weights[i].va());
        }
        HairSettings.setScalpSkinWeights(settingsVa, weightVas);

        return this;
    }

    /**
     * Alter the scalp mesh-triangle indices. (native member: mScalpTriangles)
     *
     * @param triangles the desired vertex indices (not {@code null},
     * unaffected)
     * @return the current reference, for chaining
     */
    public HairSettingsRef setScalpTriangles(
            ConstIndexedTriangleNoMaterial... triangles) {
        long settingsVa = targetVa();
        int numTriangles = triangles.length;
        LongBuffer triangleVas = Jolt.newDirectLongBuffer(numTriangles);
        for (int i = 0; i < numTriangles; ++i) {
            triangleVas.put(i, triangles[i].targetVa());
        }
        HairSettings.setScalpTriangles(settingsVa, triangleVas);

        return this;
    }

    /**
     * Alter the scalp mesh-vertex locations. (native member: mScalpVertices)
     *
     * @param vertices the desired locations (not {@code null}, unaffected)
     * @return the current reference, for chaining
     */
    public HairSettingsRef setScalpVertices(ConstFloat3... vertices) {
        long settingsVa = targetVa();
        int numFloats = 3 * vertices.length;
        FloatBuffer buffer = Jolt.newDirectFloatBuffer(numFloats);
        for (ConstFloat3 vertex : vertices) {
            vertex.put(buffer);
        }
        HairSettings.setScalpVertices(settingsVa, buffer);

        return this;
    }

    /**
     * Alter how much padding to add on all sides of the neutral pose when
     * determining the bounds of the simulation grid. (native member:
     * mSimulationBoundsPadding)
     *
     * @param padding the desired amount of padding along each axis (in meters,
     * not {@code null})
     * @return the current reference, for chaining
     */
    public HairSettingsRef setSimulationBoundsPadding(Vec3Arg padding) {
        long settingsVa = targetVa();
        float px = padding.getX();
        float py = padding.getY();
        float pz = padding.getZ();
        HairSettings.setSimulationBoundsPadding(settingsVa, px, py, pz);

        return this;
    }
    // *************************************************************************
    // ConstHairSettings methods

    /**
     * Return the number of materials. The settings are unaffected. (native
     * member: mMaterial)
     *
     * @return the count (&ge;0)
     */
    @Override
    public int countMaterials() {
        long settingsVa = targetVa();
        int result = HairSettings.countMaterials(settingsVa);

        return result;
    }

    /**
     * Return the number of render strands. The settings are unaffected. (native
     * member: mRenderStrands)
     *
     * @return the count (&ge;0)
     */
    @Override
    public int countRenderStrands() {
        long settingsVa = targetVa();
        int result = HairSettings.countRenderStrands(settingsVa);

        return result;
    }

    /**
     * Return the number of triangles in the scalp mesh. The settings are
     * unaffected. (native member: mScalpTriangles)
     *
     * @return the count (&ge;0)
     */
    @Override
    public int countScalpTriangles() {
        long settingsVa = targetVa();
        int result = HairSettings.countScalpTriangles(settingsVa);

        return result;
    }

    /**
     * Return the number of vertices in the scalp mesh. The settings are
     * unaffected. (native member: mScalpVertices)
     *
     * @return the count (&ge;0)
     */
    @Override
    public int countScalpVertices() {
        long settingsVa = targetVa();
        int result = HairSettings.countScalpVertices(settingsVa);

        return result;
    }

    /**
     * Return the number of simulation strands. The settings are unaffected.
     * (native member: mSimStrands)
     *
     * @return the count (&ge;0)
     */
    @Override
    public int countSimStrands() {
        long settingsVa = targetVa();
        int result = HairSettings.countSimStrands(settingsVa);

        return result;
    }

    /**
     * Copy the initial gravity vector. The settings are unaffected. (native
     * member: mInitialGravity)
     *
     * @return a new vector (in meters per second squared)
     */
    @Override
    public Vec3 getInitialGravity() {
        long settingsVa = targetVa();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        HairSettings.getInitialGravity(settingsVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Return the iteration rate. The settings are unaffected. (native member:
     * mNumIterationsPerSecond)
     *
     * @return the number of iterations per second (&ge;1)
     */
    @Override
    public int getNumIterationsPerSecond() {
        long settingsVa = targetVa();
        int result = HairSettings.getNumIterationsPerSecond(settingsVa);

        return result;
    }

    /**
     * Access the specified render strand. (native member: mRenderStrands)
     *
     * @param index the index of the strand to access (&ge;0)
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public RStrand getRenderStrand(int index) {
        long settingsVa = targetVa();
        long strandVa = HairSettings.getRenderStrand(settingsVa, index);
        RStrand result = new RStrand(this, strandVa);

        return result;
    }

    /**
     * Alter the number of skinning weights per vertex. The settings are
     * unaffected. (native member: mScalpNumSkinWeightsPerVertex)
     *
     * @return the number of weights per vertex
     */
    @Override
    public int getScalpNumSkinWeightsPerVertex() {
        long settingsVa = targetVa();
        int result = HairSettings.getScalpNumSkinWeightsPerVertex(settingsVa);

        return result;
    }

    /**
     * Access the vertex indices of the specified triangle in the scalp mesh.
     * (native member: mScalpTriangles)
     *
     * @param triangleIndex the index of the triangle to access (&ge;0)
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public IndexedTriangleNoMaterial getScalpTriangle(int triangleIndex) {
        long settingsVa = targetVa();
        long triangleVa
                = HairSettings.getScalpTriangle(settingsVa, triangleIndex);
        IndexedTriangleNoMaterial result
                = new IndexedTriangleNoMaterial(this, triangleVa);

        return result;
    }

    /**
     * Access the simulation bounds. (native member: mSimulationBounds)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public AaBox getSimulationBounds() {
        long settingsVa = targetVa();
        long resultVa = HairSettings.getSimulationBounds(settingsVa);
        AaBox result = new AaBox(this, resultVa);

        return result;
    }

    /**
     * Write the state of this object to the specified stream, excluding the
     * compute buffers. The settings are unaffected.
     *
     * @param streamOut where to write objects (not {@code null})
     */
    @Override
    public void saveBinaryState(StreamOutWrapper streamOut) {
        long settingsVa = targetVa();
        long streamVa = streamOut.va();
        HairSettings.saveBinaryState(settingsVa, streamVa);
    }
    // *************************************************************************
    // Ref methods

    /**
     * Temporarily access the referenced {@code HairSettings}.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public HairSettings getPtr() {
        long settingsVa = targetVa();
        HairSettings result = new HairSettings(settingsVa);

        return result;
    }

    /**
     * Return the address of the native {@code HairSettings}. No objects are
     * affected.
     *
     * @return a virtual address (not zero)
     */
    @Override
    public long targetVa() {
        long refVa = va();
        long result = getPtr(refVa);

        return result;
    }

    /**
     * Create another counted reference to the native {@code HairSettings}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public HairSettingsRef toRef() {
        long refVa = va();
        long copyVa = copy(refVa);
        HairSettingsRef result = new HairSettingsRef(copyVa, true);

        return result;
    }
    // *************************************************************************
    // native methods

    native private static long copy(long refVa);

    native private static long createDefault();

    native static void free(long refVa);

    native private static long getPtr(long refVa);
}
