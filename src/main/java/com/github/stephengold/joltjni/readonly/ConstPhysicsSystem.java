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
package com.github.stephengold.joltjni.readonly;

import com.github.stephengold.joltjni.AaBox;
import com.github.stephengold.joltjni.BodyActivationListener;
import com.github.stephengold.joltjni.BodyIdVector;
import com.github.stephengold.joltjni.CombineFunction;
import com.github.stephengold.joltjni.Constraints;
import com.github.stephengold.joltjni.ContactListener;
import com.github.stephengold.joltjni.DefaultBroadPhaseLayerFilter;
import com.github.stephengold.joltjni.DefaultObjectLayerFilter;
import com.github.stephengold.joltjni.PhysicsSettings;
import com.github.stephengold.joltjni.SoftBodyContactListener;
import com.github.stephengold.joltjni.StateRecorder;
import com.github.stephengold.joltjni.StateRecorderFilter;
import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.enumerate.EBodyType;

/**
 * Read-only access to a {@code PhysicsSystem}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public interface ConstPhysicsSystem extends ConstJoltPhysicsObject {
    /**
     * Test whether the system contains the specified constraint. The system is
     * unaffected.
     *
     * @param constraint the constraint to search for (not {@code null},
     * unaffected)
     * @return {@code true} if found, otherwise {@code false}
     */
    boolean containsConstraint(ConstConstraint constraint);

    /**
     * Enumerate all bodies of the specified type to the specified vector.
     *
     * @param bodyType (not {@code null})
     * @param storeResult storage for the result (not {@code null})
     */
    void getActiveBodies(EBodyType bodyType, BodyIdVector storeResult);

    /**
     * Enumerate all bodies to the specified variable-length vector. The system
     * is unaffected.
     *
     * @param storeResult storage for the result (not {@code null})
     */
    void getBodies(BodyIdVector storeResult);

    /**
     * Access the system's {@code BodyActivationListener}.
     *
     * @return a new JVM object with the pre-existing native object assigned, or
     * {@code null} if none
     */
    BodyActivationListener getBodyActivationListener();

    /**
     * Access the system's {@code BodyLockInterfaceLocking}.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    ConstBodyLockInterfaceLocking getBodyLockInterface();

    /**
     * Access the system's {@code BodyLockInterfaceNoLock}.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    ConstBodyLockInterfaceNoLock getBodyLockInterfaceNoLock();

    /**
     * Generate a bounding box that contains all the bodies in the system. The
     * system is unaffected.
     *
     * @return a new box
     */
    AaBox getBounds();

    /**
     * Access the system's interface for coarse collision queries.
     *
     * @return the pre-existing JVM object, or {@code null} if the system hasn't
     * been initialized yet
     */
    ConstBroadPhaseQuery getBroadPhaseQuery();

    /**
     * Access the combining function for friction.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    CombineFunction getCombineFriction();

    /**
     * Access the combining function for restitutions.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    CombineFunction getCombineRestitution();

    /**
     * Enumerate all constraints in the system. The system is unaffected.
     *
     * @return a new object
     */
    Constraints getConstraints();

    /**
     * Access the (application-provided) contact listener.
     *
     * @return the pre-existing instance, or {@code null} if none
     */
    ContactListener getContactListener();

    /**
     * Generate a broad-phase layer filter using the current pair filter and the
     * specified layer index.
     *
     * @param objectLayer the index of the object layer to use
     * @return a new filter
     */
    DefaultBroadPhaseLayerFilter getDefaultBroadPhaseLayerFilter(
            int objectLayer);

    /**
     * Generate an object layer filter using the current pair filter and the
     * specified layer index.
     *
     * @param objectLayer the index of the object layer to use
     * @return a new filter
     */
    DefaultObjectLayerFilter getDefaultLayerFilter(int objectLayer);

    /**
     * Copy the gravity vector. The system is unaffected.
     *
     * @return a new acceleration vector (meters per second squared in system
     * coordinates)
     */
    Vec3 getGravity();

    /**
     * Return the maximum number of bodies the system supports. The system is
     * unaffected.
     *
     * @return the count (&ge;0)
     */
    int getMaxBodies();

    /**
     * Access the system's interface for fine collision queries.
     *
     * @return the pre-existing JVM object (not {@code null})
     */
    ConstNarrowPhaseQuery getNarrowPhaseQuery();

    /**
     * Access a version of the system's {@code NarrowPhaseQuery} that does not
     * lock the bodies.
     *
     * @return the pre-existing JVM object (not {@code null})
     */
    ConstNarrowPhaseQuery getNarrowPhaseQueryNoLock();

    /**
     * Count how many active bodies of the specified type there are in the body
     * manager. The system is unaffected.
     *
     * @param bodyType which type of body to count (not {@code null})
     * @return the count (&ge;0, &le;maxBodies)
     */
    int getNumActiveBodies(EBodyType bodyType);

    /**
     * Count how many bodies there are in the body manager. The system is
     * unaffected.
     *
     * @return the count (&ge;0, &le;maxBodies)
     */
    int getNumBodies();

    /**
     * Access the (application-provided) interface for testing whether 2 objects
     * can collide, based on their object layers.
     *
     * @return the pre-existing instance, or {@code null} if none
     */
    ConstObjectLayerPairFilter getObjectLayerPairFilter();

    /**
     * Access the (application-provided) interface for testing whether an object
     * can collide with a broad-phase layer.
     *
     * @return the pre-existing instance, or {@code null} if none
     */
    ConstObjectVsBroadPhaseLayerFilter getObjectVsBroadPhaseLayerFilter();

    /**
     * Copy the system's settings. The system is unaffected.
     * <p>
     * Different semantics from the native {@code GetPhysicsSettings()}, which
     * returns a const reference, not a copy.
     *
     * @return a new JVM object with a new native object assigned
     */
    PhysicsSettings getPhysicsSettings();

    /**
     * Access the (application-provided) soft-body contact listener.
     *
     * @return the pre-existing instance, or {@code null} if none
     */
    SoftBodyContactListener getSoftBodyContactListener();

    /**
     * Save the system's state to be replayed later.
     *
     * @param recorder where to save the state (not {@code null})
     */
    void saveState(StateRecorder recorder);

    /**
     * Save the aspects of the system's state to be replayed later.
     *
     * @param recorder where to save the state (not {@code null})
     * @param bitmask which aspects of the simulation to save
     */
    void saveState(StateRecorder recorder, int bitmask);

    /**
     * Save aspects of the system's state to be replayed later.
     *
     * @param recorder where to save the state (not {@code null})
     * @param bitmask which aspects of the simulation to save
     * @param filter select which parts to save (unaffected) or {@code null}
     */
    void saveState(
            StateRecorder recorder, int bitmask, StateRecorderFilter filter);
}
