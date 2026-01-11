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

import com.github.stephengold.joltjni.enumerate.EActivation;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import com.github.stephengold.joltjni.template.RefTarget;

/**
 * Runtime information to simulate a ragdoll composed of bodies connected by
 * constraints.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Ragdoll extends NonCopyable implements RefTarget {
    // *************************************************************************
    // fields

    /**
     * where to add the bodies and constraints (not {@code null})
     */
    final private PhysicsSystem system;
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified native object assigned.
     *
     * @param ragdollVa the virtual address of the native object to assign (not
     * zero)
     * @param physicsSystem where to add the bodies and constraints (not
     * {@code null})
     */
    Ragdoll(long ragdollVa, PhysicsSystem physicsSystem) {
        this.system = physicsSystem;
        long refVa = toRef(ragdollVa);
        Runnable freeingAction
                = () -> RagdollRef.freeWithSystem(refVa, physicsSystem);
        setVirtualAddress(ragdollVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Add to the linear velocity of all bodies in the ragdoll.
     *
     * @param deltaV the desired change in velocity (meters per second in system
     * coordinates, not {@code null}, unaffected)
     */
    public void addLinearVelocity(Vec3Arg deltaV) {
        addLinearVelocity(deltaV, true);
    }

    /**
     * Add to the linear velocity of all bodies in the ragdoll.
     *
     * @param deltaV the desired change in velocity (meters per second in system
     * coordinates, not {@code null}, unaffected)
     * @param lockBodies (default=true)
     */
    public void addLinearVelocity(Vec3Arg deltaV, boolean lockBodies) {
        long ragdollVa = va();
        float dx = deltaV.getX();
        float dy = deltaV.getY();
        float dz = deltaV.getZ();
        addLinearVelocity(ragdollVa, dx, dy, dz, lockBodies);
    }

    /**
     * Add bodies and constraints to the physics system and optionally activate
     * the bodies.
     *
     * @param activation whether to activate bodies (not {@code null})
     */
    public void addToPhysicsSystem(EActivation activation) {
        long ragdollVa = va();
        int ordinal = activation.ordinal();
        addToPhysicsSystem(ragdollVa, ordinal);
    }

    /**
     * Drive the ragdoll to the specified pose by setting velocities.
     *
     * @param pose the desired pose
     * @param time the time interval for achieving the pose (in seconds)
     */
    public void driveToPoseUsingKinematics(SkeletonPose pose, float time) {
        driveToPoseUsingKinematics(pose, time, true);
    }

    /**
     * Drive the ragdoll to the specified pose by setting velocities.
     *
     * @param pose the desired pose
     * @param time the time interval for achieving the pose (in seconds)
     * @param lockBodies (default=true)
     */
    public void driveToPoseUsingKinematics(
            SkeletonPose pose, float time, boolean lockBodies) {
        long ragdollVa = va();
        long poseVa = pose.va();
        driveToPoseUsingKinematics(ragdollVa, poseVa, time, lockBodies);
    }

    /**
     * Drive the ragdoll to the specified pose using motors.
     *
     * @param pose the desired pose (not {@code null}, unaffected)
     */
    public void driveToPoseUsingMotors(SkeletonPose pose) {
        long ragdollVa = va();
        long poseVa = pose.va();
        driveToPoseUsingMotors(ragdollVa, poseVa);
    }

    /**
     * Count how many bodies are in the ragdoll, which is unaffected
     *
     * @return the count (&ge;0)
     */
    public int getBodyCount() {
        long ragdollVa = va();
        int result = getBodyCount(ragdollVa);

        return result;
    }

    /**
     * Enumerate all bodies in the ragdoll, which is unaffected. (native
     * function: GetBodyIDs)
     *
     * @return a new array of body IDs
     */
    public int[] getBodyIds() {
        long ragdollVa = va();
        int numBodies = getBodyCount(ragdollVa);
        int[] storeIds = new int[numBodies];
        getBodyIds(ragdollVa, storeIds);

        return storeIds;
    }

    /**
     * Count how many constraints are in the ragdoll, which is unaffected.
     *
     * @return the count (&ge;0)
     */
    public int getConstraintCount() {
        long ragdollVa = va();
        int result = getConstraintCount(ragdollVa);

        return result;
    }

    /**
     * Copy the low-level pose using a locking body interface.
     *
     * @param storeRootOffset storage for the root offset (not {@code null},
     * modified)
     * @param storeJointMatrices storage for the joint matrices (not
     * {@code null}, modified)
     */
    public void getPose(RVec3 storeRootOffset, Mat44Array storeJointMatrices) {
        getPose(storeRootOffset, storeJointMatrices, true);
    }

    /**
     * Copy the low-level pose.
     *
     * @param storeRootOffset storage for the root offset (not {@code null},
     * modified)
     * @param storeJointMatrices storage for the joint matrices (not
     * {@code null}, modified)
     * @param lockBodies {@code true} &rarr; use the locking body interface,
     * {@code false} &rarr; use the non-locking body interface (default=true)
     */
    public void getPose(RVec3 storeRootOffset, Mat44Array storeJointMatrices,
            boolean lockBodies) {
        long ragdollVa = va();
        double[] storeDoubles = new double[3];
        long storeMatsVa = storeJointMatrices.va();
        getPose(ragdollVa, storeDoubles, storeMatsVa, lockBodies);
        storeRootOffset.set(storeDoubles);
    }

    /**
     * Copy the ragdoll's pose using the locking body interface.
     *
     * @param storePose storage for the pose (not {@code null}, modified)
     */
    public void getPose(SkeletonPose storePose) {
        getPose(storePose, true);
    }

    /**
     * Copy the ragdoll's pose.
     *
     * @param storePose storage for the pose (not {@code null}, modified)
     * @param lockBodies true&rarr;use the locking body interface,
     * false&rarr;use the non-locking body interface
     */
    public void getPose(SkeletonPose storePose, boolean lockBodies) {
        long ragdollVa = targetVa();
        long poseVa = storePose.va();
        getPoseToObject(ragdollVa, poseVa, lockBodies);
    }

    /**
     * Copy the transform of the ragdoll's root, using the locking body
     * interface. The ragdoll is unaffected.
     *
     * @param storeLocation storage for the root location (not {@code null},
     * modified)
     * @param storeOrientation storage for the root orientation (not
     * {@code null}, modified)
     */
    public void getRootTransform(RVec3 storeLocation, Quat storeOrientation) {
        getRootTransform(storeLocation, storeOrientation, true);
    }

    /**
     * Copy the transform of the ragdoll's root. The ragdoll is unaffected.
     *
     * @param storeLocation storage for the root location (not {@code null},
     * modified)
     * @param storeOrientation storage for the root orientation (not
     * {@code null}, modified)
     * @param lockBodies {@code true} &rarr; use the locking body interface,
     * {@code false} &rarr; use the non-locking body interface (default=true)
     */
    public void getRootTransform(
            RVec3 storeLocation, Quat storeOrientation, boolean lockBodies) {
        long ragdollVa = va();
        double[] storeDoubles = new double[3];
        float[] storeFloats = new float[4];
        getRootTransform(ragdollVa, storeDoubles, storeFloats, lockBodies);
        storeLocation.set(storeDoubles);
        storeOrientation.set(storeFloats);
    }

    /**
     * Remove bodies and constraints from the physics system.
     */
    public void removeFromPhysicsSystem() {
        long ragdollVa = va();
        removeFromPhysicsSystem(ragdollVa);
    }

    /**
     * Alter the ragdoll's pose using the locking body interface.
     *
     * @param pose the desired pose (not {@code null}, unaffected)
     */
    public void setPose(SkeletonPose pose) {
        setPose(pose, true);
    }

    /**
     * Alter the ragdoll's pose.
     *
     * @param pose the desired pose (not {@code null}, unaffected)
     * @param lockBodies true&rarr;use the locking body interface,
     * false&rarr;use the non-locking body interface
     */
    public void setPose(SkeletonPose pose, boolean lockBodies) {
        long ragdollVa = va();
        long poseVa = pose.va();
        setPose(ragdollVa, poseVa, lockBodies);
    }
    // *************************************************************************
    // RefTarget methods

    /**
     * Count the active references to the native {@code Ragdoll}. The ragdoll is
     * unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int getRefCount() {
        long ragdollVa = va();
        int result = getRefCount(ragdollVa);

        return result;
    }

    /**
     * Mark the native {@code Ragdoll} as embedded.
     */
    @Override
    public void setEmbedded() {
        long ragdollVa = va();
        setEmbedded(ragdollVa);
    }

    /**
     * Create a counted reference to the native {@code Ragdoll}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public RagdollRef toRef() {
        long ragdollVa = va();
        long refVa = toRef(ragdollVa);
        RagdollRef result = new RagdollRef(refVa, system);

        return result;
    }
    // *************************************************************************
    // native methods

    native static void addLinearVelocity(
            long ragdollVa, float dx, float dy, float dz, boolean lockBodies);

    native static void addToPhysicsSystem(long ragdollVa, int ordinal);

    native static void driveToPoseUsingKinematics(
            long ragdollVa, long poseVa, float time, boolean lockBodies);

    native static void driveToPoseUsingMotors(long ragdollVa, long poseVa);

    native static int getBodyCount(long ragdollVa);

    native static void getBodyIds(long ragdollVa, int[] storeIds);

    native static int getConstraintCount(long ragdollVa);

    native static void getPose(long ragdollVa, double[] storeDoubles,
            long storeMatsVa, boolean lockBodies);

    native static void getPoseToObject(
            long ragdollVa, long poseVa, boolean lockBodies);

    native private static int getRefCount(long ragdollVa);

    native static void getRootTransform(long ragdollVa, double[] storeDoubles,
            float[] storeFloats, boolean lockBodies);

    native static void removeFromPhysicsSystem(long ragdollVa);

    native private static void setEmbedded(long ragdollVa);

    native static void setPose(long ragdollVa, long poseVa, boolean lockBodies);

    native private static long toRef(long ragdollVa);
}
