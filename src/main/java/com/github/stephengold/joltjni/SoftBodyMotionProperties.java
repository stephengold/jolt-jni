/*
Copyright (c) 2025 Stephen Gold

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

import com.github.stephengold.joltjni.readonly.ConstSoftBodyMotionProperties;
import com.github.stephengold.joltjni.readonly.ConstSoftBodySharedSettings;
import com.github.stephengold.joltjni.readonly.Mat44Arg;
import com.github.stephengold.joltjni.readonly.RMat44Arg;
import com.github.stephengold.joltjni.readonly.RVec3Arg;
import java.nio.FloatBuffer;

/**
 * The motion properties of a soft body.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class SoftBodyMotionProperties
        extends MotionProperties
        implements ConstSoftBodyMotionProperties {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the default properties.
     */
    public SoftBodyMotionProperties() {
        super(false);
        long propertiesVa = createDefault();
        setVirtualAddress(propertiesVa, () -> free(propertiesVa));
    }

    /**
     * Instantiate with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param propertiesVa the virtual address of the native object to assign
     * (not zero)
     */
    SoftBodyMotionProperties(JoltPhysicsObject container, long propertiesVa) {
        super(container, propertiesVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Update the specified soft body without updating the rest of the physics
     * system.
     *
     * @param deltaTime the total time to advance (in seconds)
     * @param softBody the body to update (not null)
     * @param system the system that contains the body (not null)
     */
    public void customUpdate(
            float deltaTime, Body softBody, PhysicsSystem system) {
        long propertiesVa = va();
        long bodyVa = softBody.va();
        long systemVa = system.va();
        customUpdate(propertiesVa, deltaTime, bodyVa, systemVa);
    }

    /**
     * Enable or disable any skinning constraints.
     *
     * @param enable {@code true} to enable any skinning constraints,
     * {@code false} to disable them (default=true)
     */
    public void setEnableSkinConstraints(boolean enable) {
        long propertiesVa = va();
        setEnableSkinConstraints(propertiesVa, enable);
    }

    /**
     * Alter the number of solver iterations.
     *
     * @param numIterations the desired number of iterations (default=0)
     */
    public void setNumIterations(int numIterations) {
        long propertiesVa = va();
        setNumIterations(propertiesVa, numIterations);
    }

    /**
     * Alter the maximum distance multiplier for skinned vertices.
     *
     * @param multiplier the desired multiplier (default=1)
     */
    public void setSkinnedMaxDistanceMultiplier(float multiplier) {
        long propertiesVa = va();
        setSkinnedMaxDistanceMultiplier(propertiesVa, multiplier);
    }

    /**
     * Skin vertices to the specified joints.
     *
     * @param comTransform the body's center-of-mass transform (not null,
     * unaffected)
     * @param jointMatrices the joint matrices (relative to the center-of-mass
     * transform, not null, length&ge;numJoints, unaffected)
     * @param numJoints the number of joints (&ge;0)
     * @param hardSkinAll {@code true} to reposition all vertices to their
     * skinned locations
     * @param allocator for temporary allocations (not null)
     */
    public void skinVertices(
            RMat44Arg comTransform, Mat44Arg[] jointMatrices, int numJoints,
            boolean hardSkinAll, TempAllocator allocator) {
        long propertiesVa = va();
        long comTransformVa = comTransform.targetVa();
        long[] jointMatrixVas = new long[numJoints];
        for (int i = 0; i < numJoints; ++i) {
            jointMatrixVas[i] = jointMatrices[i].targetVa();
        }
        long allocatorVa = allocator.va();
        skinVertices(propertiesVa, comTransformVa, jointMatrixVas,
                hardSkinAll, allocatorVa);
    }
    // *************************************************************************
    // ConstSoftBodyMotionProperties methods

    /**
     * Test whether skinning constraints are enabled. The properties are
     * unaffected.
     *
     * @return {@code true} if enabled, {@code false} if disabled
     */
    @Override
    public boolean getEnableSkinConstraints() {
        long propertiesVa = va();
        boolean result = getEnableSkinConstraints(propertiesVa);

        return result;
    }

    /**
     * Access the specified face.
     *
     * @param index the index of the face (&ge;0)
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public Face getFace(int index) {
        long propertiesVa = va();
        long faceVa = getFace(propertiesVa, index);
        Face result = new Face(this, faceVa);

        return result;
    }

    /**
     * Enumerate all faces in the soft body.
     *
     * @return a new array of new JVM objects with pre-existing native objects
     * assigned
     */
    @Override
    public Face[] getFaces() {
        long propertiesVa = va();
        int numFaces = countFaces(propertiesVa);
        Face[] result = new Face[numFaces];
        for (int i = 0; i < numFaces; ++i) {
            long faceVa = getFace(propertiesVa, i);
            result[i] = new Face(this, faceVa);
        }

        return result;
    }

    /**
     * Return the number of solver iterations. The properties are unaffected.
     *
     * @return the number of iterations (&ge;0)
     */
    @Override
    public int getNumIterations() {
        long propertiesVa = va();
        int result = getNumIterations(propertiesVa);

        return result;
    }

    /**
     * Access the shared settings.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public ConstSoftBodySharedSettings getSettings() {
        long propertiesVa = va();
        long settingsVa = getSettings(propertiesVa);
        ConstSoftBodySharedSettings result
                = new SoftBodySharedSettings(settingsVa);

        return result;
    }

    /**
     * Return the maximum distance multiplier for skinned vertices. The
     * properties are unaffected.
     *
     * @return the multiplier
     */
    @Override
    public float getSkinnedMaxDistanceMultiplier() {
        long propertiesVa = va();
        float result = getSkinnedMaxDistanceMultiplier(propertiesVa);

        return result;
    }

    /**
     * Access the specified vertex.
     *
     * @param index the index of the vertex (&ge;0)
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public SoftBodyVertex getVertex(int index) {
        long propertiesVa = va();
        JoltPhysicsObject container = getContainingObject();
        if (container == null) {
            container = this;
        }
        long vertexVa = getVertex(propertiesVa, index);
        SoftBodyVertex result = new SoftBodyVertex(container, vertexVa);

        return result;
    }

    /**
     * Enumerate all vertices in the soft body.
     *
     * @return a new array of new JVM objects with pre-existing native objects
     * assigned
     */
    @Override
    public SoftBodyVertex[] getVertices() {
        long propertiesVa = va();
        int numVertices = countVertices(propertiesVa);
        SoftBodyVertex[] result = new SoftBodyVertex[numVertices];
        for (int i = 0; i < numVertices; ++i) {
            long vertexVa = getVertex(propertiesVa, i);
            result[i] = new SoftBodyVertex(this, vertexVa);
        }

        return result;
    }

    /**
     * Write the locations of all pinned vertices to the specified buffer and
     * advance the buffer's position. The properties are unaffected.
     *
     * @param comLocation the location of the body's center of mass (not null)
     * @param storeFloats the destination buffer (not null, modified)
     */
    @Override
    public void putPinLocations(RVec3Arg comLocation, FloatBuffer storeFloats) {
        long propertiesVa = va();
        float x = comLocation.x();
        float y = comLocation.y();
        float z = comLocation.z();
        int bufferPosition = storeFloats.position();
        bufferPosition = putPinLocations(
                propertiesVa, x, y, z, bufferPosition, storeFloats);
        storeFloats.position(bufferPosition);
    }

    /**
     * Write the locations of all vertices to the specified buffer and advance
     * the buffer's position. The properties are unaffected.
     *
     * @param comLocation the location of the body's center of mass (not null)
     * @param storeFloats the destination buffer (not null, modified)
     */
    @Override
    public void putVertexLocations(
            RVec3Arg comLocation, FloatBuffer storeFloats) {
        long propertiesVa = va();
        float x = comLocation.x();
        float y = comLocation.y();
        float z = comLocation.z();
        int bufferPosition = storeFloats.position();
        bufferPosition = putVertexLocations(
                propertiesVa, x, y, z, bufferPosition, storeFloats);
        storeFloats.position(bufferPosition);
    }
    // *************************************************************************
    // native private methods

    native private static int countFaces(long propertiesVa);

    native private static int countVertices(long propertiesVa);

    native private static long createDefault();

    native private static void customUpdate(
            long propertiesVa, float deltaTime, long bodyVa, long systemVa);

    native private static void free(long propertiesVa);

    native private static boolean getEnableSkinConstraints(long propertiesVa);

    native private static long getFace(long propertiesVa, int index);

    native private static int getNumIterations(long propertiesVa);

    native private static long getSettings(long propertiesVa);

    native private static float getSkinnedMaxDistanceMultiplier(
            long propertiesVa);

    native private static long getVertex(long propertiesVa, int index);

    native private static int putPinLocations(long propertiesVa, float x,
            float y, float z, int bufferPosition, FloatBuffer storeFloats);

    native private static int putVertexLocations(long propertiesVa, float x,
            float y, float z, int bufferPosition, FloatBuffer storeFloats);

    native private static void setEnableSkinConstraints(
            long propertiesVa, boolean enable);

    native private static void setNumIterations(
            long propertiesVa, int numIterations);

    native private static void setSkinnedMaxDistanceMultiplier(
            long propertiesVa, float multiplier);

    native private static void skinVertices(
            long propertiesVa, long comTransformVa, long[] jointMatrixVas,
            boolean hardSkinAll, long allocatorVa);
}
