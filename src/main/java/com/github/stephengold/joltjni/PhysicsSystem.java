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

        long bodyInterfaceVa = getBodyInterface(systemVa);
        this.bodyInterface = new BodyInterface(this, bodyInterfaceVa);
    }
    // *************************************************************************
    // new methods exposed

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
     * Access the system's {@code BodyInterface}.
     *
     * @return the pre-existing JVM object (not null)
     */
    public BodyInterface getBodyInterface() {
        assert bodyInterface != null;
        return bodyInterface;
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

    native private static void getActiveBodies(
            long systemVa, int ordinal, long vectorVa);

    native private static void getBodies(long systemVa, long vectorVa);

    native private static long createPhysicsSystem();

    native private static long getBodyInterface(long systemVa);

    native private static long getBodyLockInterface(long systemVa);

    native private static long getBounds(long systemVa);

    native private static long getCombineFriction(long systemVa);

    native private static long getCombineRestitution(long systemVa);

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

    native private static void setCombineFriction(
            long systemVa, long functionVa);

    native private static void setCombineRestitution(
            long systemVa, long functionVa);

    native private static void setGravity(
            long systemVa, float x, float y, float z);

    native private static void setPhysicsSettings(
            long systemVa, long settingsVa);

    native private static int update(long physicsSystemVa, float deltaTime,
            int collisionSteps, long allocatorVa, long jobSystemVa);
}
