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

import com.github.stephengold.joltjni.enumerate.EActivation;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;

/**
 * Extended interface of {@link BodyInterface} providing additional batch-based
 * query operations.
 *
 * <p>
 * This interface inherits all operations from {@code BodyInterface} and adds
 * methods that allow performing multiple body queries in a single call.
 * </p>
 *
 * <p>
 * The primary purpose of these operations is to reduce the number of JNI calls
 * by grouping multiple body interactions into one native invocation.
 * </p>
 *
 * @author xI-Mx-Ix
 */
public class BatchBodyInterface extends BodyInterface {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified container and native object.
     *
     * @param system the containing object, or {@code null} if none
     * @param bodyInterfaceVa the virtual address of the native object to assign
     * (not zero)
     */
    BatchBodyInterface(PhysicsSystem system, long bodyInterfaceVa) {
        super(system, bodyInterfaceVa);
    }

    // *************************************************************************
    // new methods exposed

    /**
     * Test whether the specified bodies are active.
     *
     * @param bodyIds the IDs of the bodies to test (not null)
     * @param storeStatus storage for the statuses (not null, 1 for active,
     * 0 for inactive)
     */
    public void areActive(BodyIdArray bodyIds, ByteBuffer storeStatus) {
        int numBodies = bodyIds.length();
        assert storeStatus.capacity() >= numBodies;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.va();
        areActive(bodyInterfaceVa, arrayVa, numBodies, storeStatus);
    }

    /**
     * Test whether the specified bodies are added to the system.
     *
     * @param bodyIds the IDs of the bodies to search for (not null)
     * @param storeStatus storage for the statuses (not null, 1 for added, 0 for
     * not added)
     */
    public void areAdded(BodyIdArray bodyIds, ByteBuffer storeStatus) {
        int numBodies = bodyIds.length();
        assert storeStatus.capacity() >= numBodies;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.va();
        areAdded(bodyInterfaceVa, arrayVa, numBodies, storeStatus);
    }

    /**
     * Test whether the specified bodies are sensors.
     *
     * @param bodyIds the IDs of the bodies to test (not null)
     * @param storeStatus storage for the statuses (not null, 1 for sensor,
     * 0 for non-sensor)
     */
    public void areSensors(BodyIdArray bodyIds, ByteBuffer storeStatus) {
        int numBodies = bodyIds.length();
        assert storeStatus.capacity() >= numBodies;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.va();
        areSensors(bodyInterfaceVa, arrayVa, numBodies, storeStatus);
    }

    /**
     * Copy the angular velocities of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies (not null)
     * @param storeVelocities storage for the velocities (not null, interleaved
     * X,Y,Z, size >= 3*numBodies, modified)
     */
    public void getAngularVelocities(
            BodyIdArray bodyIds, FloatBuffer storeVelocities) {
        int numBodies = bodyIds.length();
        assert storeVelocities.capacity() >= numBodies * 3;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.va();
        getAngularVelocities(
                bodyInterfaceVa, arrayVa, numBodies, storeVelocities);
    }

    /**
     * Return the types of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies to query (not null)
     * @param storeTypes storage for the ordinals (not null, size >= numBodies,
     * modified)
     */
    public void getBodyTypes(BodyIdArray bodyIds, ByteBuffer storeTypes) {
        int numBodies = bodyIds.length();
        assert storeTypes.capacity() >= numBodies;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.va();
        getBodyTypes(bodyInterfaceVa, arrayVa, numBodies, storeTypes);
    }

    /**
     * Locate the centers of mass of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies to locate (not null)
     * @param storePositions storage for the locations (not null, interleaved
     * X,Y,Z, size >= 3*numBodies, modified)
     */
    public void getCenterOfMassPositions(
            BodyIdArray bodyIds, DoubleBuffer storePositions) {
        int numBodies = bodyIds.length();
        assert storePositions.capacity() >= numBodies * 3;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.va();
        getCenterOfMassPositions(
                bodyInterfaceVa, arrayVa, numBodies, storePositions);
    }

    /**
     * Return the center-of-mass transforms of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies to locate (not null)
     * @param storeMatrices storage for the matrices (not null, 16 doubles per
     * body, interleaved, modified)
     */
    public void getCenterOfMassTransforms(
            BodyIdArray bodyIds, DoubleBuffer storeMatrices) {
        int numBodies = bodyIds.length();
        assert storeMatrices.capacity() >= numBodies * 16;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.va();
        getCenterOfMassTransforms(
                bodyInterfaceVa, arrayVa, numBodies, storeMatrices);
    }

    /**
     * Return the friction ratios of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies (not null)
     * @param storeFrictions storage for the values
     * (not null, size >= numBodies, modified)
     */
    public void getFrictions(BodyIdArray bodyIds, FloatBuffer storeFrictions) {
        int numBodies = bodyIds.length();
        assert storeFrictions.capacity() >= numBodies;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.va();
        getFrictions(
                bodyInterfaceVa, arrayVa, numBodies, storeFrictions);
    }

    /**
     * Return the gravity factors of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies (not null)
     * @param storeFactors storage for the values (not null, size >= numBodies,
     * modified)
     */
    public void getGravityFactors(BodyIdArray bodyIds,
            FloatBuffer storeFactors) {
        int numBodies = bodyIds.length();
        assert storeFactors.capacity() >= numBodies;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.va();
        getGravityFactors(
                bodyInterfaceVa, arrayVa, numBodies, storeFactors);
    }

    /**
     * Copy the inverses of the inertia tensors in system coordinates.
     *
     * @param bodyIds the IDs of the bodies to query (not null)
     * @param storeMatrices storage for the matrices (not null, 16 floats per
     * body, interleaved, modified)
     */
    public void getInverseInertias(
            BodyIdArray bodyIds, FloatBuffer storeMatrices) {
        int numBodies = bodyIds.length();
        assert storeMatrices.capacity() >= numBodies * 16;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.va();
        getInverseInertias(
                bodyInterfaceVa, arrayVa, numBodies, storeMatrices);
    }

    /**
     * Copy the linear velocities of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies (not null)
     * @param storeVelocities storage for the velocities (not null, interleaved
     * X,Y,Z, size >= 3*numBodies, modified)
     */
    public void getLinearVelocities(
            BodyIdArray bodyIds, FloatBuffer storeVelocities) {
        int numBodies = bodyIds.length();
        assert storeVelocities.capacity() >= numBodies * 3;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.va();
        getLinearVelocities(
                bodyInterfaceVa, arrayVa, numBodies, storeVelocities);
    }

    /**
     * Return the motion qualities of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies to query (not null)
     * @param storeQualities storage for the ordinals
     * (not null, size >= numBodies, modified)
     */
    public void getMotionQualities(
            BodyIdArray bodyIds, ByteBuffer storeQualities) {
        int numBodies = bodyIds.length();
        assert storeQualities.capacity() >= numBodies;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.va();
        getMotionQualities(
                bodyInterfaceVa, arrayVa, numBodies, storeQualities);
    }

    /**
     * Return the motion types of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies to query (not null)
     * @param storeTypes storage for the ordinals (not null, size >= numBodies,
     * modified)
     */
    public void getMotionTypes(BodyIdArray bodyIds, ByteBuffer storeTypes) {
        int numBodies = bodyIds.length();
        assert storeTypes.capacity() >= numBodies;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.va();
        getMotionTypes(bodyInterfaceVa, arrayVa, numBodies, storeTypes);
    }

    /**
     * Return the object layers of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies to query (not null)
     * @param storeLayers storage for the layers (not null, size >= numBodies,
     * modified)
     */
    public void getObjectLayers(BodyIdArray bodyIds, IntBuffer storeLayers) {
        int numBodies = bodyIds.length();
        assert storeLayers.capacity() >= numBodies;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.va();
        getObjectLayers(bodyInterfaceVa, arrayVa, numBodies, storeLayers);
    }

    /**
     * Copy the locations of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies to locate (not null)
     * @param storeLocations storage for the locations (not null, interleaved
     * X,Y,Z, size >= 3*numBodies, modified)
     */
    public void getPositions(BodyIdArray bodyIds, DoubleBuffer storeLocations) {
        int numBodies = bodyIds.length();
        assert storeLocations.capacity() >= numBodies * 3;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.va();
        getPositions(bodyInterfaceVa, arrayVa, numBodies, storeLocations);
    }

    /**
     * Return the restitution ratios of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies to query (not null)
     * @param storeRestitutions storage for the values
     * (not null, size >= numBodies, modified)
     */
    public void getRestitutions(
            BodyIdArray bodyIds, FloatBuffer storeRestitutions) {
        int numBodies = bodyIds.length();
        assert storeRestitutions.capacity() >= numBodies;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.va();
        getRestitutions(
                bodyInterfaceVa, arrayVa, numBodies, storeRestitutions);
    }

    /**
     * Copy the orientations of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies (not null)
     * @param storeOrientations storage for the rotations (not null, interleaved
     * X,Y,Z,W, size >= 4*numBodies, modified)
     */
    public void getRotations(
            BodyIdArray bodyIds, FloatBuffer storeOrientations) {
        int numBodies = bodyIds.length();
        assert storeOrientations.capacity() >= numBodies * 4;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.va();
        getRotations(bodyInterfaceVa, arrayVa, numBodies, storeOrientations);
    }

    /**
     * Access the shapes of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies (not null)
     * @return a new array of new references
     */
    public ShapeRefC[] getShapes(BodyIdArray bodyIds) {
        int numBodies = bodyIds.length();
        LongBuffer storeShapeVas = Jolt.newDirectLongBuffer(numBodies);
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.va();
        getShapes(bodyInterfaceVa, arrayVa, numBodies, storeShapeVas);
        ShapeRefC[] result = new ShapeRefC[numBodies];
        for (int i = 0; i < numBodies; ++i) {
            long va = storeShapeVas.get(i);
            result[i] = new ShapeRefC(va, true);
        }
        return result;
    }

    /**
     * Generate transformed shapes for the specified bodies.
     *
     * @param bodyIds the IDs of the bodies (not null)
     * @return a new array of new objects
     */
    public TransformedShape[] getTransformedShapes(BodyIdArray bodyIds) {
        int numBodies = bodyIds.length();
        LongBuffer storeShapeVas = Jolt.newDirectLongBuffer(numBodies);
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.va();
        getTransformedShapes(
                bodyInterfaceVa, arrayVa, numBodies, storeShapeVas);
        TransformedShape[] result = new TransformedShape[numBodies];
        for (int i = 0; i < numBodies; ++i) {
            long va = storeShapeVas.get(i);
            result[i] = new TransformedShape(va, true);
        }
        return result;
    }

    /**
     * Test whether manifold reduction is enabled for the specified bodies.
     *
     * @param bodyIds the IDs of the bodies (not null)
     * @param storeStatus storage for the statuses (not null, 1 for enabled,
     * 0 for disabled, size >= numBodies, modified)
     */
    public void getUseManifoldReductions(
            BodyIdArray bodyIds, ByteBuffer storeStatus) {
        int numBodies = bodyIds.length();
        assert storeStatus.capacity() >= numBodies;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.va();
        getUseManifoldReductions(
                bodyInterfaceVa, arrayVa, numBodies, storeStatus);
    }

    /**
     * Return the user data of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies (not null)
     * @param storeData storage for the values (not null, size >= numBodies,
     * modified)
     */
    public void getUserData(BodyIdArray bodyIds, LongBuffer storeData) {
        int numBodies = bodyIds.length();
        assert storeData.capacity() >= numBodies;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.va();
        getUserData(bodyInterfaceVa, arrayVa, numBodies, storeData);
    }

    /**
     * Alter the angular velocities of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies (not null)
     * @param velocities the desired velocities (not null, interleaved
     * X,Y,Z, size >= 3*numBodies)
     */
    public void setAngularVelocities(
            BodyIdArray bodyIds, FloatBuffer velocities) {
        int numBodies = bodyIds.length();
        assert velocities.capacity() >= numBodies * 3;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.va();
        setAngularVelocities(
                bodyInterfaceVa, arrayVa, numBodies, velocities);
    }

    /**
     * Alter the friction ratios of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies (not null)
     * @param frictions the desired values (not null, size >= numBodies)
     */
    public void setFrictions(BodyIdArray bodyIds, FloatBuffer frictions) {
        int numBodies = bodyIds.length();
        assert frictions.capacity() >= numBodies;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.va();
        setFrictions(bodyInterfaceVa, arrayVa, numBodies, frictions);
    }

    /**
     * Alter the gravity factors of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies (not null)
     * @param factors the desired values (not null, size >= numBodies)
     */
    public void setGravityFactors(BodyIdArray bodyIds, FloatBuffer factors) {
        int numBodies = bodyIds.length();
        assert factors.capacity() >= numBodies;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.va();
        setGravityFactors(bodyInterfaceVa, arrayVa, numBodies, factors);
    }

    /**
     * Alter the linear velocities of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies (not null)
     * @param velocities the desired velocities (not null, interleaved
     * X,Y,Z, size >= 3*numBodies)
     */
    public void setLinearVelocities(
            BodyIdArray bodyIds, FloatBuffer velocities) {
        int numBodies = bodyIds.length();
        assert velocities.capacity() >= numBodies * 3;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.va();
        setLinearVelocities(
                bodyInterfaceVa, arrayVa, numBodies, velocities);
    }

    /**
     * Alter the object layers of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies (not null)
     * @param layers the desired layers (not null, size >= numBodies)
     */
    public void setObjectLayers(BodyIdArray bodyIds, IntBuffer layers) {
        int numBodies = bodyIds.length();
        assert layers.capacity() >= numBodies;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.va();
        setObjectLayers(bodyInterfaceVa, arrayVa, numBodies, layers);
    }

    /**
     * Relocate the specified bodies.
     *
     * @param bodyIds the IDs of the bodies (not null)
     * @param locations the desired locations (not null, interleaved
     * X,Y,Z, size >= 3*numBodies)
     * @param activation whether to activate the bodies (not null)
     */
    public void setPositions(BodyIdArray bodyIds, DoubleBuffer locations,
            EActivation activation) {
        int numBodies = bodyIds.length();
        assert locations.capacity() >= numBodies * 3;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.va();
        int activationOrdinal = activation.ordinal();
        setPositions(bodyInterfaceVa, arrayVa, numBodies, locations,
                activationOrdinal);
    }

    /**
     * Alter the restitution ratios of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies (not null)
     * @param restitutions the desired values (not null, size >= numBodies)
     */
    public void setRestitutions(
            BodyIdArray bodyIds, FloatBuffer restitutions) {
        int numBodies = bodyIds.length();
        assert restitutions.capacity() >= numBodies;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.va();
        setRestitutions(
                bodyInterfaceVa, arrayVa, numBodies, restitutions);
    }

    /**
     * Alter the user data of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies (not null)
     * @param data the desired values (not null, size >= numBodies)
     */
    public void setUserData(BodyIdArray bodyIds, LongBuffer data) {
        int numBodies = bodyIds.length();
        assert data.capacity() >= numBodies;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.va();
        setUserData(bodyInterfaceVa, arrayVa, numBodies, data);
    }

    // *************************************************************************
    // native private methods

    native private static void areActive(long bodyInterfaceVa, long arrayVa,
            int numBodies, ByteBuffer storeStatus);

    native private static void areAdded(long bodyInterfaceVa, long arrayVa,
            int numBodies, ByteBuffer storeStatus);

    native private static void areSensors(long bodyInterfaceVa, long arrayVa,
            int numBodies, ByteBuffer storeStatus);

    native private static void getAngularVelocities(long bodyInterfaceVa,
            long arrayVa, int numBodies, FloatBuffer storeVelocities);

    native private static void getBodyTypes(long bodyInterfaceVa, long arrayVa,
            int numBodies, ByteBuffer storeTypes);

    native private static void getCenterOfMassPositions(long bodyInterfaceVa,
            long arrayVa, int numBodies, DoubleBuffer storePositions);

    native private static void getCenterOfMassTransforms(long bodyInterfaceVa,
            long arrayVa, int numBodies, DoubleBuffer storeMatrices);

    native private static void getFrictions(long bodyInterfaceVa, long arrayVa,
            int numBodies, FloatBuffer storeFrictions);

    native private static void getGravityFactors(long bodyInterfaceVa,
            long arrayVa, int numBodies, FloatBuffer storeFactors);

    native private static void getInverseInertias(long bodyInterfaceVa,
            long arrayVa, int numBodies, FloatBuffer storeMatrices);

    native private static void getLinearVelocities(long bodyInterfaceVa,
            long arrayVa, int numBodies, FloatBuffer storeVelocities);

    native private static void getMotionQualities(long bodyInterfaceVa,
            long arrayVa, int numBodies, ByteBuffer storeQualities);

    native private static void getMotionTypes(long bodyInterfaceVa,
            long arrayVa, int numBodies, ByteBuffer storeTypes);

    native private static void getObjectLayers(long bodyInterfaceVa,
            long arrayVa, int numBodies, IntBuffer storeLayers);

    native private static void getPositions(long bodyInterfaceVa, long arrayVa,
            int numBodies, DoubleBuffer storeLocations);

    native private static void getRestitutions(long bodyInterfaceVa,
            long arrayVa, int numBodies, FloatBuffer storeRestitutions);

    native private static void getRotations(long bodyInterfaceVa, long arrayVa,
            int numBodies, FloatBuffer storeOrientations);

    native private static void getShapes(long bodyInterfaceVa, long arrayVa,
            int numBodies, LongBuffer storeShapeVas);

    native private static void getTransformedShapes(long bodyInterfaceVa,
            long arrayVa, int numBodies, LongBuffer storeShapeVas);

    native private static void getUseManifoldReductions(long bodyInterfaceVa,
            long arrayVa, int numBodies, ByteBuffer storeStatus);

    native private static void getUserData(long bodyInterfaceVa, long arrayVa,
            int numBodies, LongBuffer storeData);

    native private static void setAngularVelocities(long bodyInterfaceVa,
            long arrayVa, int numBodies, FloatBuffer velocities);

    native private static void setFrictions(long bodyInterfaceVa, long arrayVa,
            int numBodies, FloatBuffer frictions);

    native private static void setGravityFactors(long bodyInterfaceVa,
            long arrayVa, int numBodies, FloatBuffer factors);

    native private static void setLinearVelocities(long bodyInterfaceVa,
            long arrayVa, int numBodies, FloatBuffer velocities);

    native private static void setObjectLayers(long bodyInterfaceVa,
            long arrayVa, int numBodies, IntBuffer layers);

    native private static void setPositions(long bodyInterfaceVa, long arrayVa,
            int numBodies, DoubleBuffer locations, int activationOrdinal);

    native private static void setRestitutions(long bodyInterfaceVa,
            long arrayVa, int numBodies, FloatBuffer restitutions);

    native private static void setUserData(long bodyInterfaceVa, long arrayVa,
            int numBodies, LongBuffer data);
}
