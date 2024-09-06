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

import com.github.stephengold.joltjni.enumerate.EActivation;
import com.github.stephengold.joltjni.template.RefTarget;

/**
 * Runtime information to simulate a ragdoll composed of bodies connected by
 * constraints.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Ragdoll extends NonCopyable implements RefTarget {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified native object assigned but not owned.
     *
     * @param ragdollVa the virtual address of the native object to assign (not
     * zero)
     */
    Ragdoll(long ragdollVa) {
        super(ragdollVa);
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
        long ragdollVa = va();
        int ordinal = activation.ordinal();
        addToPhysicsSystem(ragdollVa, ordinal);
    }

    /**
     * Drive the ragdoll to the specified pose using motors.
     *
     * @param pose the desired pose (not null, unaffected)
     */
    public void driveToPoseUsingMotors(SkeletonPose pose) {
        long ragdollVa = va();
        long poseVa = pose.va();
        driveToPoseUsingMotors(ragdollVa, poseVa);
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
        long ragdollVa = va();
        long poseVa = pose.va();
        setPose(ragdollVa, poseVa, lockBodies);
    }
    // *************************************************************************
    // RefTarget methods

    /**
     * Count the active references to the ragdoll. The ragdoll is unaffected.
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
     * Create a counted reference to the ragdoll.
     *
     * @return a new JVM object with a new native object assigned
     */
    @Override
    public RagdollRef toRef() {
        long ragdollVa = va();
        long refVa = toRef(ragdollVa);
        RagdollRef result = new RagdollRef(refVa, true);

        return result;
    }
    // *************************************************************************
    // native methods

    native static void addToPhysicsSystem(long ragdollVa, int ordinal);

    native static void driveToPoseUsingMotors(long ragdollVa, long poseVa);

    native private static int getRefCount(long ragdollVa);

    native static void removeFromPhysicsSystem(long ragdollVa);

    native static void setPose(long ragdollVa, long poseVa, boolean lockBodies);

    native private static long toRef(long ragdollVa);
}
