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

import com.github.stephengold.joltjni.enumerate.EActivation;
import com.github.stephengold.joltjni.template.Ref;

/**
 * A counted reference to a {@code Ragdoll}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class RagdollRef extends Ref {
    // *************************************************************************
    // fields

    /**
     * where to add the bodies and constraints (may be null)
     */
    final private PhysicsSystem system;
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty reference.
     */
    public RagdollRef() {
        this.system = null;
        long refVa = createEmpty();
        setVirtualAddress(refVa, () -> free(refVa));
    }

    /**
     * Instantiate a reference with the specified native object assigned.
     *
     * @param refVa the virtual address of the native object to assign (not
     * zero)
     * @param physicsSystem where to add the bodies and constraints
     */
    RagdollRef(long refVa, PhysicsSystem physicsSystem) {
        this.system = physicsSystem;
        Runnable freeingAction = () -> freeWithSystem(refVa, physicsSystem);
        /*
         * Passing physicsSystem to the Runnable ensures that the underlying
         * system won't get cleaned before the ragdoll.
         *
         * See JoltPhysics issue #1428 for more information.
         */
        setVirtualAddress(refVa, freeingAction);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Add bodies and constraints to the physics system and optionally activate
     * the bodies.
     *
     * @param activation whether to activate bodies (not null)
     */
    public void addToPhysicsSystem(EActivation activation) {
        long ragdollVa = targetVa();
        int ordinal = activation.ordinal();
        Ragdoll.addToPhysicsSystem(ragdollVa, ordinal);
    }

    /**
     * Drive the ragdoll to the specified pose by setting velocities.
     *
     * @param pose the desired pose
     * @param time time to achieve the pose (in seconds)
     */
    public void driveToPoseUsingKinematics(SkeletonPose pose, float time) {
        driveToPoseUsingKinematics(pose, time, true);
    }

    /**
     * Drive the ragdoll to the specified pose by setting velocities.
     *
     * @param pose the desired pose
     * @param time time to achieve the pose (in seconds)
     * @param lockBodies (default=true)
     */
    public void driveToPoseUsingKinematics(
            SkeletonPose pose, float time, boolean lockBodies) {
        long ragdollVa = targetVa();
        long poseVa = pose.va();
        Ragdoll.driveToPoseUsingKinematics(ragdollVa, poseVa, time, lockBodies);
    }

    /**
     * Drive the ragdoll to the specified pose using motors.
     *
     * @param pose the desired pose (not null, unaffected)
     */
    public void driveToPoseUsingMotors(SkeletonPose pose) {
        long ragdollVa = targetVa();
        long poseVa = pose.va();
        Ragdoll.driveToPoseUsingMotors(ragdollVa, poseVa);
    }

    /**
     * Count how many bodies are in the ragdoll, which is unaffected
     *
     * @return the count (&ge;0)
     */
    public int getBodyCount() {
        long ragdollVa = targetVa();
        int result = Ragdoll.getBodyCount(ragdollVa);

        return result;
    }

    /**
     * Enumerate all bodies in the ragdoll, which is unaffected. (native method:
     * GetBodyIDs)
     *
     * @return a new array of body IDs
     */
    public int[] getBodyIds() {
        long ragdollVa = targetVa();
        int numBodies = Ragdoll.getBodyCount(ragdollVa);
        int[] storeIds = new int[numBodies];
        Ragdoll.getBodyIds(ragdollVa, storeIds);

        return storeIds;
    }

    /**
     * Count how many constraints are in the ragdoll, which is unaffected.
     *
     * @return the count (&ge;0)
     */
    public int getConstraintCount() {
        long ragdollVa = targetVa();
        int result = Ragdoll.getConstraintCount(ragdollVa);

        return result;
    }

    /**
     * Copy the low-level pose using a locking body interface.
     *
     * @param storeRootOffset storage for the root offset (not null, modified)
     * @param storeJointMatrices storage for the joint matrices (not null,
     * modified)
     */
    public void getPose(RVec3 storeRootOffset, Mat44Array storeJointMatrices) {
        getPose(storeRootOffset, storeJointMatrices, true);
    }

    /**
     * Copy the low-level pose.
     *
     * @param storeRootOffset storage for the root offset (not null, modified)
     * @param storeJointMatrices storage for the joint matrices (not null,
     * modified)
     * @param lockBodies {@code true} &rarr; use the locking body interface,
     * {@code false} &rarr; use the non-locking body interface (default=true)
     */
    public void getPose(RVec3 storeRootOffset, Mat44Array storeJointMatrices,
            boolean lockBodies) {
        long ragdollVa = targetVa();
        double[] storeDoubles = new double[3];
        long storeMatsVa = storeJointMatrices.va();
        Ragdoll.getPose(ragdollVa, storeDoubles, storeMatsVa, lockBodies);
        storeRootOffset.set(storeDoubles);
    }

    /**
     * Copy the transform of the ragdoll's root, using the locking body
     * interface. The ragdoll is unaffected.
     *
     * @param storeLocation storage for the root location (not null, modified)
     * @param storeOrientation storage for the root orientation (not null,
     * modified)
     */
    public void getRootTransform(RVec3 storeLocation, Quat storeOrientation) {
        getRootTransform(storeLocation, storeOrientation, true);
    }

    /**
     * Copy the transform of the ragdoll's root. The ragdoll is unaffected.
     *
     * @param storeLocation storage for the root location (not null, modified)
     * @param storeOrientation storage for the root orientation (not null,
     * modified)
     * @param lockBodies {@code true} &rarr; use the locking body interface,
     * {@code false} &rarr; use the non-locking body interface (default=true)
     */
    public void getRootTransform(
            RVec3 storeLocation, Quat storeOrientation, boolean lockBodies) {
        long ragdollVa = targetVa();
        double[] storeDoubles = new double[3];
        float[] storeFloats = new float[4];
        Ragdoll.getRootTransform(
                ragdollVa, storeDoubles, storeFloats, lockBodies);
        storeLocation.set(storeDoubles);
        storeOrientation.set(storeFloats);
    }

    /**
     * Remove bodies and constraints from the physics system.
     */
    public void removeFromPhysicsSystem() {
        long ragdollVa = targetVa();
        Ragdoll.removeFromPhysicsSystem(ragdollVa);
    }

    /**
     * Alter the ragdoll's pose using the locking body interface.
     *
     * @param pose the desired pose (not null, unaffected)
     */
    public void setPose(SkeletonPose pose) {
        setPose(pose, true);
    }

    /**
     * Alter the ragdoll's pose.
     *
     * @param pose the desired pose (not null, unaffected)
     * @param lockBodies true&rarr;use the locking body interface,
     * false&rarr;use the non-locking body interface
     */
    public void setPose(SkeletonPose pose, boolean lockBodies) {
        long ragdollVa = targetVa();
        long poseVa = pose.va();
        Ragdoll.setPose(ragdollVa, poseVa, lockBodies);
    }
    // *************************************************************************
    // Ref methods

    /**
     * Temporarily access the referenced {@code Ragdoll}.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public Ragdoll getPtr() {
        long ragdollVa = targetVa();
        Ragdoll result = new Ragdoll(ragdollVa, system);

        return result;
    }

    /**
     * Return the address of the native {@code Ragdoll}. No objects are
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
     * Create another counted reference to the native {@code Ragdoll}.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public RagdollRef toRef() {
        long refVa = va();
        long copyVa = copy(refVa);
        RagdollRef result = new RagdollRef(copyVa, system);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long copy(long refVa);

    native private static long createEmpty();

    native private static void free(long refVa);

    native private static void freeWithSystem(long refVa, PhysicsSystem unused);

    native private static long getPtr(long refVa);
}
