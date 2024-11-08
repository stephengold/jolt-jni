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
import com.github.stephengold.joltjni.template.Ref;

/**
 * A counted reference to a {@code Ragdoll}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class RagdollRef extends Ref {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an empty reference.
     */
    public RagdollRef() {
        long refVa = createEmpty();
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
    RagdollRef(long refVa, boolean owner) {
        Runnable freeingAction = owner ? () -> free(refVa) : null;
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
        Ragdoll result = new Ragdoll(ragdollVa);

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
        RagdollRef result = new RagdollRef(copyVa, true);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long copy(long refVa);

    native private static long createEmpty();

    native private static void free(long refVa);

    native private static long getPtr(long refVa);
}
