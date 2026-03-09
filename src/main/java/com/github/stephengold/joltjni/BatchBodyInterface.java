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
import com.github.stephengold.joltjni.readonly.ConstBodyIdArray;
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
     * @param bodyIds the IDs of the bodies to test (not {@code null},
     * unaffected)
     * @param storeStatus storage for the statuses (not {@code null}, 1 for
     * active, 0 for inactive, modified)
     */
    public void areActive(ConstBodyIdArray bodyIds, ByteBuffer storeStatus) {
        int numBodies = bodyIds.length();
        assert storeStatus.capacity() >= numBodies;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.targetVa();
        areActive(bodyInterfaceVa, arrayVa, numBodies, storeStatus);
    }

    /**
     * Test whether the specified bodies are added to the system.
     *
     * @param bodyIds the IDs of the bodies to search for (not {@code null},
     * unaffected)
     * @param storeStatus storage for the statuses (not {@code null}, 1 for
     * added, 0 for not added, modified)
     */
    public void areAdded(ConstBodyIdArray bodyIds, ByteBuffer storeStatus) {
        int numBodies = bodyIds.length();
        assert storeStatus.capacity() >= numBodies;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.targetVa();
        areAdded(bodyInterfaceVa, arrayVa, numBodies, storeStatus);
    }

    /**
     * Test whether the specified bodies are sensors.
     *
     * @param bodyIds the IDs of the bodies to test (not {@code null},
     * unaffected)
     * @param storeStatus storage for the statuses (not {@code null}, 1 for
     * sensor, 0 for non-sensor, modified)
     */
    public void areSensors(ConstBodyIdArray bodyIds, ByteBuffer storeStatus) {
        int numBodies = bodyIds.length();
        assert storeStatus.capacity() >= numBodies;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.targetVa();
        areSensors(bodyInterfaceVa, arrayVa, numBodies, storeStatus);
    }

    /**
     * Copy the angular velocities of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies (not {@code null}, unaffected)
     * @param storeVelocities storage for the velocities (not {@code null},
     * interleaved X,Y,Z, capacity&ge;3*numBodies, modified)
     */
    public void getAngularVelocities(
            ConstBodyIdArray bodyIds, FloatBuffer storeVelocities) {
        int numBodies = bodyIds.length();
        assert storeVelocities.capacity() >= numBodies * 3;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.targetVa();
        getAngularVelocities(
                bodyInterfaceVa, arrayVa, numBodies, storeVelocities);
    }

    /**
     * Return the types of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies to query (not {@code null},
     * unaffected)
     * @param storeTypes storage for the ordinals (not {@code null},
     * capacity&ge;numBodies, modified)
     */
    public void getBodyTypes(ConstBodyIdArray bodyIds, ByteBuffer storeTypes) {
        int numBodies = bodyIds.length();
        assert storeTypes.capacity() >= numBodies;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.targetVa();
        getBodyTypes(bodyInterfaceVa, arrayVa, numBodies, storeTypes);
    }

    /**
     * Locate the centers of mass of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies to locate (not {@code null},
     * unaffected)
     * @param storePositions storage for the locations (not {@code null},
     * interleaved X,Y,Z, capacity&ge;3*numBodies, modified)
     */
    public void getCenterOfMassPositions(
            ConstBodyIdArray bodyIds, DoubleBuffer storePositions) {
        int numBodies = bodyIds.length();
        assert storePositions.capacity() >= numBodies * 3;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.targetVa();
        getCenterOfMassPositions(
                bodyInterfaceVa, arrayVa, numBodies, storePositions);
    }

    /**
     * Return the center-of-mass transforms of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies to locate (not {@code null},
     * unaffected)
     * @param storeMatrices storage for the matrices (not {@code null},
     * modified)
     */
    public void getCenterOfMassTransforms(
            ConstBodyIdArray bodyIds, RMat44Array storeMatrices) {
        int numBodies = bodyIds.length();
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.targetVa();
        long matricesVa = storeMatrices.va();
        getCenterOfMassTransforms(
                bodyInterfaceVa, arrayVa, numBodies, matricesVa);
    }

    /**
     * Return the friction ratios of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies (not {@code null}, unaffected)
     * @param storeFrictions storage for the values (not {@code null},
     * capacity&ge;numBodies, modified)
     */
    public void getFrictions(
            ConstBodyIdArray bodyIds, FloatBuffer storeFrictions) {
        int numBodies = bodyIds.length();
        assert storeFrictions.capacity() >= numBodies;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.targetVa();
        getFrictions(
                bodyInterfaceVa, arrayVa, numBodies, storeFrictions);
    }

    /**
     * Return the gravity factors of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies (not {@code null}, unaffected)
     * @param storeFactors storage for the values (not {@code null},
     * capacity&ge;numBodies, modified)
     */
    public void getGravityFactors(
            ConstBodyIdArray bodyIds, FloatBuffer storeFactors) {
        int numBodies = bodyIds.length();
        assert storeFactors.capacity() >= numBodies;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.targetVa();
        getGravityFactors(
                bodyInterfaceVa, arrayVa, numBodies, storeFactors);
    }

    /**
     * Copy the inverses of the inertia tensors in system coordinates.
     *
     * @param bodyIds the IDs of the bodies to query (not {@code null},
     * unaffected)
     * @param storeMatrices storage for the matrices (not {@code null},
     * modified)
     */
    public void getInverseInertias(
            ConstBodyIdArray bodyIds, Mat44Array storeMatrices) {
        int numBodies = bodyIds.length();
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.targetVa();
        long matricesVa = storeMatrices.va();
        getInverseInertias(
                bodyInterfaceVa, arrayVa, numBodies, matricesVa);
    }

    /**
     * Copy the linear velocities of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies (not {@code null}, unaffected)
     * @param storeVelocities storage for the velocities (not {@code null},
     * interleaved X,Y,Z, capacity&ge;3*numBodies, modified)
     */
    public void getLinearVelocities(
            ConstBodyIdArray bodyIds, FloatBuffer storeVelocities) {
        int numBodies = bodyIds.length();
        assert storeVelocities.capacity() >= numBodies * 3;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.targetVa();
        getLinearVelocities(
                bodyInterfaceVa, arrayVa, numBodies, storeVelocities);
    }

    /**
     * Return the motion qualities of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies to query (not {@code null},
     * unaffected)
     * @param storeQualities storage for the ordinals (not {@code null},
     * capacity&ge;numBodies, modified)
     */
    public void getMotionQualities(
            ConstBodyIdArray bodyIds, ByteBuffer storeQualities) {
        int numBodies = bodyIds.length();
        assert storeQualities.capacity() >= numBodies;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.targetVa();
        getMotionQualities(
                bodyInterfaceVa, arrayVa, numBodies, storeQualities);
    }

    /**
     * Return the motion types of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies to query (not {@code null},
     * unaffected)
     * @param storeTypes storage for the ordinals (not {@code null},
     * capacity&ge;numBodies, modified)
     */
    public void getMotionTypes(
            ConstBodyIdArray bodyIds, ByteBuffer storeTypes) {
        int numBodies = bodyIds.length();
        assert storeTypes.capacity() >= numBodies;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.targetVa();
        getMotionTypes(bodyInterfaceVa, arrayVa, numBodies, storeTypes);
    }

    /**
     * Return the object layers of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies to query (not {@code null},
     * unaffected)
     * @param storeLayers storage for the layers (not {@code null},
     * capacity&ge;numBodies, modified)
     */
    public void getObjectLayers(
            ConstBodyIdArray bodyIds, IntBuffer storeLayers) {
        int numBodies = bodyIds.length();
        assert storeLayers.capacity() >= numBodies;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.targetVa();
        getObjectLayers(bodyInterfaceVa, arrayVa, numBodies, storeLayers);
    }

    /**
     * Copy the locations of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies to locate (not {@code null},
     * unaffected)
     * @param storeLocations storage for the locations (not {@code null},
     * interleaved X,Y,Z, capacity&ge;3*numBodies, modified)
     */
    public void getPositions(
            ConstBodyIdArray bodyIds, DoubleBuffer storeLocations) {
        int numBodies = bodyIds.length();
        assert storeLocations.capacity() >= numBodies * 3;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.targetVa();
        getPositions(bodyInterfaceVa, arrayVa, numBodies, storeLocations);
    }

    /**
     * Return the restitution ratios of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies to query (not {@code null},
     * unaffected)
     * @param storeRestitutions storage for the values (not {@code null},
     * capacity&ge;numBodies, modified)
     */
    public void getRestitutions(
            ConstBodyIdArray bodyIds, FloatBuffer storeRestitutions) {
        int numBodies = bodyIds.length();
        assert storeRestitutions.capacity() >= numBodies;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.targetVa();
        getRestitutions(
                bodyInterfaceVa, arrayVa, numBodies, storeRestitutions);
    }

    /**
     * Copy the orientations of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies (not {@code null}, unaffected)
     * @param storeOrientations storage for the rotations (not {@code null},
     * interleaved X,Y,Z,W, capacity&ge;4*numBodies, modified)
     */
    public void getRotations(
            ConstBodyIdArray bodyIds, FloatBuffer storeOrientations) {
        int numBodies = bodyIds.length();
        assert storeOrientations.capacity() >= numBodies * 4;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.targetVa();
        getRotations(bodyInterfaceVa, arrayVa, numBodies, storeOrientations);
    }

    /**
     * Access the shapes of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies (not {@code null}, unaffected)
     * @param storeShapes storage for the references (not {@code null},
     * modified)
     */
    public void getShapes(
            ConstBodyIdArray bodyIds, ShapeRefCArray storeShapes) {
        int numBodies = bodyIds.length();
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.targetVa();
        long shapesVa = storeShapes.va();
        getShapes(bodyInterfaceVa, arrayVa, numBodies, shapesVa);
    }

    /**
     * Generate transformed shapes for the specified bodies.
     *
     * @param bodyIds the IDs of the bodies (not {@code null}, unaffected)
     * @param storeShapes storage for the transformed shapes (not {@code null},
     * modified)
     */
    public void getTransformedShapes(
            ConstBodyIdArray bodyIds, TransformedShapeArray storeShapes) {
        int numBodies = bodyIds.length();
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.targetVa();
        long shapesVa = storeShapes.va();
        getTransformedShapes(
                bodyInterfaceVa, arrayVa, numBodies, shapesVa);
    }

    /**
     * Test whether manifold reduction is enabled for the specified bodies.
     *
     * @param bodyIds the IDs of the bodies (not {@code null}, unaffected)
     * @param storeStatus storage for the statuses (not {@code null}, 1 for
     * enabled, 0 for disabled, capacity&ge;numBodies, modified)
     */
    public void getUseManifoldReductions(
            ConstBodyIdArray bodyIds, ByteBuffer storeStatus) {
        int numBodies = bodyIds.length();
        assert storeStatus.capacity() >= numBodies;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.targetVa();
        getUseManifoldReductions(
                bodyInterfaceVa, arrayVa, numBodies, storeStatus);
    }

    /**
     * Return the user data of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies (not {@code null}, unaffected)
     * @param storeData storage for the values (not {@code null},
     * capacity&ge;numBodies, modified)
     */
    public void getUserData(
            ConstBodyIdArray bodyIds, LongBuffer storeData) {
        int numBodies = bodyIds.length();
        assert storeData.capacity() >= numBodies;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.targetVa();
        getUserData(bodyInterfaceVa, arrayVa, numBodies, storeData);
    }

    /**
     * Alter the angular velocities of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies (not {@code null}, unaffected)
     * @param velocities the desired velocities (not {@code null}, interleaved
     * X,Y,Z, capacity&ge;3*numBodies, unaffected)
     */
    public void setAngularVelocities(
            ConstBodyIdArray bodyIds, FloatBuffer velocities) {
        int numBodies = bodyIds.length();
        assert velocities.capacity() >= numBodies * 3;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.targetVa();
        setAngularVelocities(
                bodyInterfaceVa, arrayVa, numBodies, velocities);
    }

    /**
     * Alter the friction ratios of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies (not {@code null}, unaffected)
     * @param frictions the desired values (not {@code null},
     * capacity&ge;numBodies)
     */
    public void setFrictions(ConstBodyIdArray bodyIds, FloatBuffer frictions) {
        int numBodies = bodyIds.length();
        assert frictions.capacity() >= numBodies;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.targetVa();
        setFrictions(bodyInterfaceVa, arrayVa, numBodies, frictions);
    }

    /**
     * Alter the gravity factors of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies (not {@code null}, unaffected)
     * @param factors the desired values (not {@code null},
     * capacity&ge;numBodies, unaffected)
     */
    public void setGravityFactors(
            ConstBodyIdArray bodyIds, FloatBuffer factors) {
        int numBodies = bodyIds.length();
        assert factors.capacity() >= numBodies;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.targetVa();
        setGravityFactors(bodyInterfaceVa, arrayVa, numBodies, factors);
    }

    /**
     * Alter the linear velocities of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies (not {@code null}, unaffected)
     * @param velocities the desired velocities (not {@code null}, interleaved
     * X,Y,Z, capacity&ge;3*numBodies, unaffected)
     */
    public void setLinearVelocities(
            ConstBodyIdArray bodyIds, FloatBuffer velocities) {
        int numBodies = bodyIds.length();
        assert velocities.capacity() >= numBodies * 3;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.targetVa();
        setLinearVelocities(
                bodyInterfaceVa, arrayVa, numBodies, velocities);
    }

    /**
     * Alter the object layers of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies (not {@code null}, unaffected)
     * @param layers the desired layers (not {@code null},
     * capacity&ge;numBodies, unaffected)
     */
    public void setObjectLayers(
        int numBodies = bodyIds.length();
        assert layers.capacity() >= numBodies;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.targetVa();
        setObjectLayers(bodyInterfaceVa, arrayVa, numBodies, layers);
    }

    /**
     * Relocate the specified bodies.
     *
     * @param bodyIds the IDs of the bodies (not {@code null}, unaffected)
     * @param locations the desired locations (not {@code null}, interleaved
     * X,Y,Z, capacity&ge;3*numBodies, unaffected)
     * @param activation whether to activate the bodies (not {@code null})
     */
    public void setPositions(ConstBodyIdArray bodyIds, DoubleBuffer locations,
            EActivation activation) {
        int numBodies = bodyIds.length();
        assert locations.capacity() >= numBodies * 3;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.targetVa();
        int activationOrdinal = activation.ordinal();
        setPositions(bodyInterfaceVa, arrayVa, numBodies, locations,
                activationOrdinal);
    }

    /**
     * Alter the restitution ratios of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies (not {@code null}, unaffected)
     * @param restitutions the desired values (not {@code null},
     * capacity&ge;numBodies, unaffected)
     */
    public void setRestitutions(
            ConstBodyIdArray bodyIds, FloatBuffer restitutions) {
        int numBodies = bodyIds.length();
        assert restitutions.capacity() >= numBodies;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.targetVa();
        setRestitutions(
                bodyInterfaceVa, arrayVa, numBodies, restitutions);
    }

    /**
     * Alter the user data of the specified bodies.
     *
     * @param bodyIds the IDs of the bodies (not {@code null}, unaffected)
     * @param data the desired values (not {@code null}, capacity&ge;numBodies,
     * unaffected)
     */
    public void setUserData(ConstBodyIdArray bodyIds, LongBuffer data) {
        int numBodies = bodyIds.length();
        assert data.capacity() >= numBodies;
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.targetVa();
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

    native private static void getCenterOfMassTransforms(
            long bodyInterfaceVa, long arrayVa, int numBodies, long matricesVa);

    native private static void getFrictions(long bodyInterfaceVa, long arrayVa,
            int numBodies, FloatBuffer storeFrictions);

    native private static void getGravityFactors(long bodyInterfaceVa,
            long arrayVa, int numBodies, FloatBuffer storeFactors);

    native private static void getInverseInertias(
            long bodyInterfaceVa, long arrayVa, int numBodies, long matricesVa);

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

    native private static void getShapes(
            long bodyInterfaceVa, long arrayVa, int numBodies, long shapesVa);

    native private static void getTransformedShapes(long bodyInterfaceVa,
            long arrayVa, int numBodies, long shapesVa);

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

    native private static void setUserData(
            long bodyInterfaceVa, long arrayVa, int numBodies, LongBuffer data);
}
