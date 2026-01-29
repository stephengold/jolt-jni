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
import com.github.stephengold.joltjni.template.RefTarget;
import java.nio.FloatBuffer;
import java.nio.LongBuffer;

/**
 * Settings used to construct a hair simulation.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class HairSettings
        extends JoltPhysicsObject
        implements ConstHairSettings, RefTarget {
    // *************************************************************************
    // constants

    /**
     * default simulation rate for hair (in iterations per second)
     */
    final public static int cDefaultIterationsPerSecond = 360;
    // *************************************************************************
    // constructors

    /**
     * Instantiate default settings.
     */
    public HairSettings() {
        long settingsVa = createDefault();
        long refVa = toRef(settingsVa);
        setVirtualAddress(settingsVa, () -> HairSettingsRef.free(refVa));
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public HairSettings(ConstHairSettings original) {
        long originalVa = original.targetVa();
        long copyVa = createCopy(originalVa);
        long refVa = toRef(copyVa);
        Runnable freeingAction = () -> HairSettingsRef.free(refVa);
        setVirtualAddress(copyVa, freeingAction);
    }

    /**
     * Instantiate settings with the specified native object assigned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    HairSettings(long settingsVa) {
        long refVa = toRef(settingsVa);
        Runnable freeingAction = () -> HairSettingsRef.free(refVa);
        setVirtualAddress(settingsVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Append the specified material. (native member: mMaterial)
     *
     * @param material the material to add (not {@code null})
     */
    public void addMaterial(ConstHairMaterial material) {
        long settingsVa = va();
        long materialVa = material.targetVa();
        addMaterial(settingsVa, materialVa);
    }

    /**
     * Access the materials. (native member: mMaterial)
     *
     * @return a new array containing new JVM objects with the pre-existing
     * native objects assigned
     */
    public HairMaterial[] getMaterials() {
        long settingsVa = va();
        int numMaterials = countMaterials(settingsVa);
        HairMaterial[] result = new HairMaterial[numMaterials];
        for (int index = 0; index < numMaterials; ++index) {
            long materialVa = getMaterial(settingsVa, index);
            result[index] = new HairMaterial(this, materialVa);
        }

        return result;
    }

    /**
     * Access the render strands. (native member: mRenderStrands)
     *
     * @return a new array containing new JVM objects with the pre-existing
     * native objects assigned
     */
    public RStrand[] getRenderStrands() {
        long settingsVa = va();
        int numStrands = countRenderStrands(settingsVa);
        RStrand[] result = new RStrand[numStrands];
        for (int index = 0; index < numStrands; ++index) {
            long strandVa = getRenderStrand(settingsVa, index);
            result[index] = new RStrand(this, strandVa);
        }

        return result;
    }

    /**
     * Access the vertex indices of the triangles in the scalp mesh. (native
     * member: mScalpTriangles)
     *
     * @return a new array containing new JVM objects with the pre-existing
     * native objects assigned
     */
    public IndexedTriangleNoMaterial[] getScalpTriangles() {
        long settingsVa = va();
        int numTriangles = countScalpTriangles(settingsVa);
        IndexedTriangleNoMaterial[] result
                = new IndexedTriangleNoMaterial[numTriangles];
        for (int index = 0; index < numTriangles; ++index) {
            long triangleVa = getScalpTriangle(settingsVa, index);
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
        long settingsVa = va();
        maxDistSqHairToScalp[0] = init(settingsVa);
    }

    /**
     * Further initialization to set up compute buffers. Invoke after
     * {@code init()}.
     *
     * @param system the compute system to use (not {@code null})
     */
    public void initCompute(ComputeSystem system) {
        long settingsVa = va();
        long systemVa = system.va();
        initCompute(settingsVa, systemVa);
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
        long settingsVa = va();
        long verticesVa = vertices.va();
        long strandsVa = strands.va();
        initRenderAndSimulationStrands(settingsVa, verticesVa, strandsVa);
    }

    /**
     * Read the state of this object from the specified stream, excluding the
     * shape and group filter.
     *
     * @param streamIn where to read objects from (not {@code null})
     */
    public void restoreBinaryState(StreamIn streamIn) {
        long settingsVa = va();
        long streamVa = streamIn.va();
        restoreBinaryState(settingsVa, streamVa);
    }

    /**
     * Alter the initial gravity vector. (native member: mInitialGravity)
     *
     * @param gravity the desired acceleration vector (meters per second squared
     * in system coordinates, not {@code null}, default=(0,-9.81,0))
     * @return the modified settings, for chaining
     */
    public HairSettings setInitialGravity(Vec3Arg gravity) {
        long settingsVa = va();
        float gx = gravity.getX();
        float gy = gravity.getY();
        float gz = gravity.getZ();
        setInitialGravity(settingsVa, gx, gy, gz);

        return this;
    }

    /**
     * Alter the iteration rate. (native member: mNumIterationsPerSecond)
     *
     * @param numIterations the desired number of iterations (&ge;1,
     * default=360)
     * @return the modified settings, for chaining
     */
    public HairSettings setNumIterationsPerSecond(int numIterations) {
        long settingsVa = va();
        setNumIterationsPerSecond(settingsVa, numIterations);

        return this;
    }

    /**
     * Alter the scalp mesh-vertex locations in bind pose. (native member:
     * mScalpInverseBindPose)
     *
     * @param pose the desired pose (not {@code null}, unaffected)
     * @return the modified settings, for chaining
     */
    public HairSettings setScalpInverseBindPose(Mat44Arg... pose) {
        long settingsVa = va();
        int numJoints = pose.length;
        LongBuffer poseVas = Jolt.newDirectLongBuffer(numJoints);
        for (int i = 0; i < numJoints; ++i) {
            poseVas.put(i, pose[i].targetVa());
        }
        setScalpInverseBindPose(settingsVa, poseVas);

        return this;
    }

    /**
     * Alter the number of skinning weights per vertex. (native member:
     * mScalpNumSkinWeightsPerVertex)
     *
     * @param numWeights the desired number of weights (&ge;0, default=0)
     * @return the modified settings, for chaining
     */
    public HairSettings setScalpNumSkinWeightsPerVertex(int numWeights) {
        long settingsVa = va();
        setScalpNumSkinWeightsPerVertex(settingsVa, numWeights);

        return this;
    }

    /**
     * Alter the scalp skinning weights. (native member: mScalpSkinWeights)
     *
     * @param weights the desired weights of the scalp mesh (not {@code null},
     * {@code mScalpNumSkinWeightsPerVertex} elements per vertex, unaffected)
     * @return the modified settings, for chaining
     */
    public HairSettings setScalpSkinWeights(HairSkinWeight... weights) {
        long settingsVa = va();
        int numWeights = weights.length;
        LongBuffer weightVas = Jolt.newDirectLongBuffer(numWeights);
        for (int i = 0; i < numWeights; ++i) {
            weightVas.put(i, weights[i].va());
        }
        setScalpSkinWeights(settingsVa, weightVas);

        return this;
    }

    /**
     * Alter the scalp mesh-triangle indices. (native member: mScalpTriangles)
     *
     * @param triangles the desired vertex indices (not {@code null},
     * unaffected)
     * @return the modified settings, for chaining
     */
    public HairSettings setScalpTriangles(
            ConstIndexedTriangleNoMaterial... triangles) {
        long settingsVa = va();
        int numTriangles = triangles.length;
        LongBuffer triangleVas = Jolt.newDirectLongBuffer(numTriangles);
        for (int i = 0; i < numTriangles; ++i) {
            triangleVas.put(i, triangles[i].targetVa());
        }
        setScalpTriangles(settingsVa, triangleVas);

        return this;
    }

    /**
     * Alter the scalp mesh-vertex locations. (native member: mScalpVertices)
     *
     * @param vertices the desired locations (not {@code null}, unaffected)
     * @return the modified settings, for chaining
     */
    public HairSettings setScalpVertices(ConstFloat3... vertices) {
        long settingsVa = va();
        int numFloats = 3 * vertices.length;
        FloatBuffer buffer = Jolt.newDirectFloatBuffer(numFloats);
        for (ConstFloat3 vertex : vertices) {
            vertex.put(buffer);
        }
        setScalpVertices(settingsVa, buffer);

        return this;
    }

    /**
     * Alter how much padding to add on all sides of the neutral pose when
     * determining the bounds of the simulation grid. (native member:
     * mSimulationBoundsPadding)
     *
     * @param padding the desired amount of padding along each axis (in meters,
     * not {@code null})
     * @return the modified settings, for chaining
     */
    public HairSettings setSimulationBoundsPadding(Vec3Arg padding) {
        long settingsVa = va();
        float px = padding.getX();
        float py = padding.getY();
        float pz = padding.getZ();
        setSimulationBoundsPadding(settingsVa, px, py, pz);

        return this;
    }

    /**
     * Resample the hairs to the specified number of vertices per strand. Invoke
     * before {@code init()}.
     *
     * @param vertices the vertex list to use (not {@code null})
     * @param strands the strands to use (not {@code null})
     * @param numVerticesPerStrand the desired number of vertices per strand
     */
    public static void sResample(SVertexList vertices, SStrandList strands,
            int numVerticesPerStrand) {
        long verticesVa = vertices.va();
        long strandsVa = strands.va();
        sResample(verticesVa, strandsVa, numVerticesPerStrand);
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
        long settingsVa = va();
        int result = countMaterials(settingsVa);

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
        long settingsVa = va();
        int result = countRenderStrands(settingsVa);

        return result;
    }

    /**
     * Return the number of render vertices. The settings are unaffected.
     * (native member: mRenderVertices)
     *
     * @return the count (&ge;0)
     */
    @Override
    public int countRenderVertices() {
        long settingsVa = va();
        int result = countRenderVertices(settingsVa);

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
        long settingsVa = va();
        int result = countScalpTriangles(settingsVa);

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
        long settingsVa = va();
        int result = countScalpVertices(settingsVa);

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
        long settingsVa = va();
        int result = countSimStrands(settingsVa);

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
        long settingsVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getInitialGravity(settingsVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Access the specified material. (native member: mMaterial)
     *
     * @param index the index of the material to access (&ge;0)
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public HairMaterial getMaterial(int index) {
        long settingsVa = va();
        long materialVa = getMaterial(settingsVa, index);
        HairMaterial result = new HairMaterial(this, materialVa);

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
        long settingsVa = va();
        int result = getNumIterationsPerSecond(settingsVa);

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
        long settingsVa = va();
        long strandVa = getRenderStrand(settingsVa, index);
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
        long settingsVa = va();
        int result = getScalpNumSkinWeightsPerVertex(settingsVa);

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
        long settingsVa = va();
        long triangleVa = getScalpTriangle(settingsVa, triangleIndex);
        IndexedTriangleNoMaterial result
                = new IndexedTriangleNoMaterial(this, triangleVa);

        return result;
    }

    /**
     * Copy the specified vertex in the scalp mesh. The settings are unaffected.
     * (native member: mScalpVertices)
     *
     * @param vertexIndex the index of the vertex to access (&ge;0)
     * @return a new vector
     */
    @Override
    public Float3 getScalpVertex(int vertexIndex) {
        long settingsVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getScalpVertex(settingsVa, vertexIndex, storeFloats);
        Float3 result = new Float3(storeFloats);

        return result;
    }

    /**
     * Access the specified simulation strand. (native member: mSimStrands)
     *
     * @param strandIndex the index of the strand to access (&ge;0)
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public SStrand getSimStrand(int strandIndex) {
        long settingsVa = va();
        long strandVa = getSimStrand(settingsVa, strandIndex);
        SStrand result = new SStrand(this, strandVa);

        return result;
    }

    /**
     * Access the simulation bounds. (native member: mSimulationBounds)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public AaBox getSimulationBounds() {
        long settingsVa = va();
        long resultVa = getSimulationBounds(settingsVa);
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
    public void saveBinaryState(StreamOut streamOut) {
        long settingsVa = va();
        long streamVa = streamOut.va();
        saveBinaryState(settingsVa, streamVa);
    }
    // *************************************************************************
    // RefTarget methods

    /**
     * Count the active references to the native {@code HairSettings}. The scene
     * is unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int getRefCount() {
        long sceneVa = va();
        int result = getRefCount(sceneVa);

        return result;
    }

    /**
     * Mark the native {@code HairSettings} as embedded.
     */
    @Override
    public void setEmbedded() {
        long sceneVa = va();
        setEmbedded(sceneVa);
    }

    /**
     * Create a counted reference to the native {@code HairSettings}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public HairSettingsRef toRef() {
        long settingsVa = va();
        long refVa = toRef(settingsVa);
        HairSettingsRef result = new HairSettingsRef(refVa, true);

        return result;
    }
    // *************************************************************************
    // native methods

    native static void addMaterial(long settingsVa, long materialVa);

    native static int countMaterials(long settingsVa);

    native static int countRenderStrands(long settingsVa);

    native static int countRenderVertices(long settingsVa);

    native static int countScalpTriangles(long settingsVa);

    native static int countScalpVertices(long settingsVa);

    native static int countSimStrands(long settingsVa);

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native static void getInitialGravity(
            long settingsVa, FloatBuffer storeFloats);

    native static long getMaterial(long settingsVa, int index);

    native static int getNumIterationsPerSecond(long settingsVa);

    native private static int getRefCount(long settingsVa);

    native static long getRenderStrand(long settingsVa, int index);

    native static int getScalpNumSkinWeightsPerVertex(long settingsVa);

    native static long getScalpTriangle(long settingsVa, int index);

    native static void getScalpVertex(
            long settingsVa, int vertexIndex, FloatBuffer storeFloats);

    native static long getSimStrand(long settingsVa, int strandIndex);

    native static long getSimulationBounds(long settingsVa);

    native static float init(long settingsVa);

    native static void initCompute(long settingsVa, long systemVa);

    native static void initRenderAndSimulationStrands(
            long settingsVa, long verticesVa, long strandsVa);

    native static void restoreBinaryState(long settingsVa, long streamVa);

    native static void saveBinaryState(long settingsVa, long streamVa);

    native private static void setEmbedded(long settingsVa);

    native static void setInitialGravity(
            long settingsVa, float gx, float gy, float gz);

    native static void setNumIterationsPerSecond(
            long settingsVa, int numIterations);

    native static void setScalpInverseBindPose(
            long settingsVa, LongBuffer poseVas);

    native static void setScalpNumSkinWeightsPerVertex(
            long settingsVa, int numWeights);

    native static void setScalpSkinWeights(
            long settingsVa, LongBuffer weightVas);

    native static void setScalpTriangles(
            long settingsVa, LongBuffer triangleVas);

    native static void setScalpVertices(long settingsVa, FloatBuffer buffer);

    native static void setSimulationBoundsPadding(
            long settingsVa, float px, float py, float pz);

    native static void sResample(
            long verticesVa, long strandsVa, int numVerticesPerStrand);

    native private static long toRef(long settingsVa);
}
