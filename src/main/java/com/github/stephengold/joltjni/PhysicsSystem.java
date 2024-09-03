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

import com.github.stephengold.joltjni.enumerate.EBodyType;
import com.github.stephengold.joltjni.enumerate.EStateRecorderState;
import com.github.stephengold.joltjni.readonly.ConstBroadPhaseLayerInterface;
import com.github.stephengold.joltjni.readonly.ConstObjectLayerPairFilter;
import com.github.stephengold.joltjni.readonly.ConstObjectVsBroadPhaseLayerFilter;
import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * Perform simulation on a collection of physics objects. Bodies are added via a
 * separate interface.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class PhysicsSystem extends NonCopyable {
    // *************************************************************************
    // fields

    /**
     * manage bodies associated with this system (not null)
     */
    final private BodyInterface bodyInterface;
    /**
     * manage bodies associated with this system (not null)
     */
    final private BodyInterface bodyInterfaceNoLock;
    /**
     * protect the BroadPhaseLayerInterface from garbage collection
     */
    private ConstBroadPhaseLayerInterface map;
    /**
     * protect the ObjectVsBroadPhaseLayerFilter from garbage collection
     */
    private ConstObjectVsBroadPhaseLayerFilter ovbFilter;
    /**
     * protect the ObjectLayerPairFilter from garbage collection
     */
    private ConstObjectLayerPairFilter ovoFilter;
    // *************************************************************************
    // constructors

    /**
     * Instantiate an uninitialized physics system.
     */
    public PhysicsSystem() {
        long systemVa = createPhysicsSystem();
        setVirtualAddress(systemVa, true);

        long lockingVa = getBodyInterface(systemVa);
        this.bodyInterface = new BodyInterface(this, lockingVa);

        long noLockVa = getBodyInterfaceNoLock(systemVa);
        this.bodyInterfaceNoLock = new BodyInterface(this, noLockVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Add the specified constraint to the system.
     *
     * @param constraint the constraint to add (not null)
     */
    public void addConstraint(Constraint constraint) {
        long systemVa = va();
        long constraintVa = constraint.va();
        addConstraint(systemVa, constraintVa);
    }

    /**
     * Render the state of the system, for debugging purposes.
     *
     * @param settings the settings to use (not null)
     * @param renderer the renderer to use (not null)
     */
    public void drawBodies(DrawSettings settings, DebugRenderer renderer) {
        long systemVa = va();
        long settingsVa = settings.va();
        long rendererVa = renderer.va();
        drawBodies(systemVa, settingsVa, rendererVa);
    }

    /**
     * Enumerate bodies of the specified type to the specified vector.
     *
     * @param bodyType (not null)
     * @param storeResult storage for the result (not null)
     */
    public void getActiveBodies(EBodyType bodyType, BodyIdVector storeResult) {
        long systemVa = va();
        int ordinal = bodyType.ordinal();
        long vectorVa = storeResult.va();
        getActiveBodies(systemVa, ordinal, vectorVa);
    }

    /**
     * Enumerate all bodies to the specified vector.
     *
     * @param storeResult storage for the result (not null)
     */
    public void getBodies(BodyIdVector storeResult) {
        long systemVa = va();
        long resultVa = storeResult.va();
        getBodies(systemVa, resultVa);
    }

    /**
     * Access the system's {@code BodyActivationListener}.
     *
     * @return a new JVM object with the pre-existing native object assigned, or
     * {@code null} if none
     */
    public BodyActivationListener getBodyActivationListener() {
        long systemVa = va();
        long listenerVa = getBodyActivationListener(systemVa);

        BodyActivationListener result;
        if (listenerVa == 0L) {
            result = null;
        } else {
            result = new BodyActivationListener(listenerVa, false);
        }

        return result;
    }

    /**
     * Access the system's {@code BodyInterface}.
     *
     * @return the pre-existing JVM object (not null)
     */
    public BodyInterface getBodyInterface() {
        assert bodyInterface != null;
        return bodyInterface;
    }

    /**
     * Access a version of the system's {@code BodyInterface} that does not use
     * locks.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public BodyInterface getBodyInterfaceNoLock() {
        assert bodyInterfaceNoLock != null;
        return bodyInterfaceNoLock;
    }

    /**
     * Access the system's {@code BodyLockInterfaceLocking}.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public BodyLockInterfaceLocking getBodyLockInterface() {
        long systemVa = va();
        long interfaceVa = getBodyLockInterface(systemVa);
        BodyLockInterfaceLocking result
                = new BodyLockInterfaceLocking(this, interfaceVa);

        return result;
    }

    /**
     * Access the system's {@code BodyLockInterfaceNoLock}.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public BodyLockInterfaceNoLock getBodyLockInterfaceNoLock() {
        long systemVa = va();
        long interfaceVa = getBodyLockInterfaceNoLock(systemVa);
        BodyLockInterfaceNoLock result
                = new BodyLockInterfaceNoLock(this, interfaceVa);

        return result;
    }

    /**
     * Return a bounding box that contains all the bodies in the system.
     *
     * @return a new box
     */
    public AaBox getBounds() {
        long systemVa = va();
        long boxVa = getBounds(systemVa);
        AaBox result = new AaBox(boxVa, true);

        return result;
    }

    /**
     * Access the system's {@code BroadPhaseLayerInterface}.
     *
     * @return the pre-existing instance, or {@code null} if none
     */
    public ConstBroadPhaseLayerInterface getBroadPhaseLayerInterface() {
        return map;
    }

    /**
     * Access the combining function for friction.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public CombineFunction getCombineFriction() {
        long systemVa = va();
        long functionVa = getCombineFriction(systemVa);
        CombineFunction result = new CombineFunction(functionVa);

        return result;
    }

    /**
     * Access the combining function for restitutions.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public CombineFunction getCombineRestitution() {
        long systemVa = va();
        long functionVa = getCombineRestitution(systemVa);
        CombineFunction result = new CombineFunction(functionVa);

        return result;
    }

    /**
     * Enumerate the constraints in the system.
     *
     * @return a new object
     */
    public Constraints getConstraints() {
        long systemVa = va();
        long resultVa = getConstraints(systemVa);
        Constraints result = new Constraints(resultVa, true);

        return result;
    }

    /**
     * Access the system's {@code ContactListener}.
     *
     * @return a new JVM object with the pre-existing native object assigned, or
     * {@code null} if none
     */
    public ContactListener getContactListener() {
        long systemVa = va();
        long listenerVa = getContactListener(systemVa);

        ContactListener result;
        if (listenerVa == 0L) {
            result = null;
        } else {
            result = new ContactListener(listenerVa, false);
        }

        return result;
    }

    /**
     * Copy the gravity vector. The physics system is unaffected.
     *
     * @return a new acceleration vector (meters per second squared in
     * physics-system coordinates)
     */
    public Vec3 getGravity() {
        long systemVa = va();
        float x = getGravityX(systemVa);
        float y = getGravityY(systemVa);
        float z = getGravityZ(systemVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Return the maximum number of bodies the system supports. The physics
     * system is unaffected.
     *
     * @return the count (&ge;0)
     */
    public int getMaxBodies() {
        long systemVa = va();
        int result = getMaxBodies(systemVa);

        return result;
    }

    /**
     * Count how many active bodies of the specified type there are in the body
     * manager. The physics system is unaffected.
     *
     * @param bodyType which type of body to count (not null)
     * @return the count (&ge;0, &le;maxBodies)
     */
    public int getNumActiveBodies(EBodyType bodyType) {
        long systemVa = va();
        int typeOrdinal = bodyType.ordinal();
        int result = getNumActiveBodies(systemVa, typeOrdinal);

        return result;
    }

    /**
     * Count how many bodies there are in the body manager. The physics system
     * is unaffected.
     *
     * @return the count (&ge;0, &le;maxBodies)
     */
    public int getNumBodies() {
        long systemVa = va();
        int result = getNumBodies(systemVa);

        return result;
    }

    /**
     * Access the {@code ObjectLayerPairFilter}.
     *
     * @return the pre-existing instance, or {@code null} if none
     */
    public ConstObjectVsBroadPhaseLayerFilter getOvbFilter() {
        return ovbFilter;
    }

    /**
     * Access the {@code ObjectLayerPairFilter}.
     *
     * @return the pre-existing instance, or {@code null} if none
     */
    public ConstObjectLayerPairFilter getOvoFilter() {
        return ovoFilter;
    }

    /**
     * Copy the system's settings.
     *
     * @return a new JVM object with a new native object assigned
     *
     * @see setPhysicsSettings
     */
    public PhysicsSettings getPhysicsSettings() {
        long systemVa = va();
        long settingsVa = getPhysicsSettings(systemVa);
        PhysicsSettings result = new PhysicsSettings(settingsVa, true);

        return result;
    }

    /**
     * Initialize the physics system with the specified limits.
     *
     * @param maxBodies the desired maximum number of rigid bodies that can be
     * added
     * @param numBodyMutexes the desired number of mutexes to allocate, or 0 for
     * the default number
     * @param maxBodyPairs the desired maximum number of body pairs that can be
     * queued at a time
     * @param maxContactConstraints the desired capacity of the
     * contact-constraint buffer
     * @param map (not null, alias created)
     * @param ovbFilter (not null, alias created)
     * @param ovoFilter (not null, alias created)
     */
    public void init(int maxBodies, int numBodyMutexes, int maxBodyPairs,
            int maxContactConstraints, ConstBroadPhaseLayerInterface map,
            ConstObjectVsBroadPhaseLayerFilter ovbFilter,
            ConstObjectLayerPairFilter ovoFilter) {
        this.map = map;
        this.ovbFilter = ovbFilter;
        this.ovoFilter = ovoFilter;

        long systemVa = va();
        long mapVa = map.va();
        long ovbFilterVa = ovbFilter.va();
        long ovoFilterVa = ovoFilter.va();
        init(systemVa, maxBodies, numBodyMutexes, maxBodyPairs,
                maxContactConstraints, mapVa, ovbFilterVa, ovoFilterVa);
    }

    /**
     * Improve the performance of future collision detections.
     */
    public void optimizeBroadPhase() {
        optimizeBroadPhase(va());
    }

    /**
     * Remove the specified constraint from the system.
     *
     * @param constraint the constraint to remove (not null)
     */
    public void removeConstraint(Constraint constraint) {
        long systemVa = va();
        long constraintVa = constraint.va();
        removeConstraint(systemVa, constraintVa);
    }

    /**
     * Restore the system's state from the specified recorder, for replay.
     *
     * @param recorder where to read the state from (not null)
     * @return true if successful, otherwise false
     */
    public boolean restoreState(StateRecorder recorder) {
        long systemVa = va();
        long recorderVa = recorder.va();
        boolean result = restoreState(systemVa, recorderVa);

        return result;
    }

    /**
     * Save the system's state to be replayed later.
     *
     * @param recorder where to save the state (not null)
     */
    public void saveState(StateRecorder recorder) {
        saveState(recorder, EStateRecorderState.All);
    }

    /**
     * Save the aspects of the system's state to be replayed later.
     *
     * @param recorder where to save the state (not null)
     * @param bitmask which aspects of the simulation to save
     */
    public void saveState(StateRecorder recorder, int bitmask) {
        saveState(recorder, bitmask, null);
    }

    /**
     * Save aspects of the system's state to be replayed later.
     *
     * @param recorder where to save the state (not null)
     * @param bitmask which aspects of the simulation to save
     * @param filter select which parts to save (may be null, unaffected)
     */
    public void saveState(
            StateRecorder recorder, int bitmask, StateRecorderFilter filter) {
        long systemVa = va();
        long recorderVa = recorder.va();
        long filterVa = (filter == null) ? 0L : filter.va();
        saveState(systemVa, recorderVa, bitmask, filterVa);
    }

    /**
     * Replace the system's {@code BodyActivationListener}.
     *
     * @param listener the desired listener
     */
    public void setBodyActivationListener(BodyActivationListener listener) {
        long systemVa = va();
        long listenerVa = listener.va();
        setBodyActivationListener(systemVa, listenerVa);
    }

    /**
     * Replace the combining function for friction.
     *
     * @param function the desired function (not null)
     */
    public void setCombineFriction(CombineFunction function) {
        long systemVa = va();
        long functionVa = function.va();
        setCombineFriction(systemVa, functionVa);
    }

    /**
     * Replace the combining function for restitutions.
     *
     * @param function the desired function (not null)
     */
    public void setCombineRestitution(CombineFunction function) {
        long systemVa = va();
        long functionVa = function.va();
        setCombineRestitution(systemVa, functionVa);
    }

    /**
     * Replace the system's contact listener.
     *
     * @param listener the desired listener
     */
    public void setContactListener(ContactListener listener) {
        long systemVa = va();
        long listenerVa = listener.va();
        setContactListener(systemVa, listenerVa);
    }

    /**
     * Alter the system's gravity vector.
     *
     * @param gravity (not null, unaffected)
     */
    public void setGravity(Vec3Arg gravity) {
        long systemVa = va();
        float x = gravity.getX();
        float y = gravity.getY();
        float z = gravity.getZ();
        setGravity(systemVa, x, y, z);
    }

    /**
     * Copy the specified settings to the system.
     *
     * @param settings the desired settings (not null, unaffected)
     *
     * @see getPhysicsSettings
     */
    public void setPhysicsSettings(PhysicsSettings settings) {
        long systemVa = va();
        long settingsVa = settings.va();
        setPhysicsSettings(systemVa, settingsVa);
    }

    /**
     * Advance the simulation by the specified amount.
     *
     * @param deltaTime the total time to advance (in seconds)
     * @param collisionSteps the number of simulation steps to perform
     * @param tempAllocator the allocator to use (not null)
     * @param jobSystem the job system to use (not null)
     * @return a bitmask of error conditions, or-ed together
     *
     * @see com.github.stephengold.joltjni.enumerate.EPhysicsUpdateError
     */
    public int update(float deltaTime, int collisionSteps,
            TempAllocator tempAllocator, JobSystem jobSystem) {
        long physicsSystemVa = va();
        long allocatorVa = tempAllocator.va();
        long jobSystemVa = jobSystem.va();
        int result = update(physicsSystemVa, deltaTime, collisionSteps,
                allocatorVa, jobSystemVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static void addConstraint(long systemVa, long constraintVa);

    native private static long createPhysicsSystem();

    native private static void drawBodies(
            long systemVa, long settingsVa, long rendererVa);

    native private static void getActiveBodies(
            long systemVa, int ordinal, long vectorVa);

    native private static void getBodies(long systemVa, long vectorVa);

    native private static long getBodyActivationListener(long systemVa);

    native private static long getBodyInterface(long systemVa);

    native private static long getBodyInterfaceNoLock(long systemVa);

    native private static long getBodyLockInterface(long systemVa);

    native private static long getBodyLockInterfaceNoLock(long systemVa);

    native private static long getBounds(long systemVa);

    native private static long getCombineFriction(long systemVa);

    native private static long getCombineRestitution(long systemVa);

    native private static long getConstraints(long systemVa);

    native private static long getContactListener(long systemVa);

    native private static float getGravityX(long systemVa);

    native private static float getGravityY(long systemVa);

    native private static float getGravityZ(long systemVa);

    native private static int getMaxBodies(long systemVa);

    native private static int getNumActiveBodies(
            long systemVa, int typeOrdinal);

    native private static int getNumBodies(long systemVa);

    native private static long getPhysicsSettings(long systemVa);

    native private static void init(long systemVa, int maxBodies,
            int numBodyMutexes, int maxBodyPairs, int maxContactConstraints,
            long mapVa, long ovbFilterVa, long ovoFilterVa);

    native private static void optimizeBroadPhase(long systemVa);

    native private static void removeConstraint(
            long systemVa, long constraintVa);

    native private static boolean restoreState(long systemVa, long recorderVa);

    native private static void saveState(
            long systemVa, long recorderVa, int bitmask, long filterVa);

    native private static void setBodyActivationListener(
            long systemVa, long listenerVa);

    native private static void setCombineFriction(
            long systemVa, long functionVa);

    native private static void setCombineRestitution(
            long systemVa, long functionVa);

    native private static void setContactListener(
            long systemVa, long listenerVa);

    native private static void setGravity(
            long systemVa, float x, float y, float z);

    native private static void setPhysicsSettings(
            long systemVa, long settingsVa);

    native private static int update(long physicsSystemVa, float deltaTime,
            int collisionSteps, long allocatorVa, long jobSystemVa);
}
