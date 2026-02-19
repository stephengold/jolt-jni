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

import com.github.stephengold.joltjni.enumerate.EBodyType;
import com.github.stephengold.joltjni.enumerate.EStateRecorderState;
import com.github.stephengold.joltjni.readonly.ConstBodyLockInterfaceLocking;
import com.github.stephengold.joltjni.readonly.ConstBodyLockInterfaceNoLock;
import com.github.stephengold.joltjni.readonly.ConstBroadPhaseLayerInterface;
import com.github.stephengold.joltjni.readonly.ConstBroadPhaseQuery;
import com.github.stephengold.joltjni.readonly.ConstConstraint;
import com.github.stephengold.joltjni.readonly.ConstNarrowPhaseQuery;
import com.github.stephengold.joltjni.readonly.ConstObjectLayerPairFilter;
import com.github.stephengold.joltjni.readonly.ConstObjectVsBroadPhaseLayerFilter;
import com.github.stephengold.joltjni.readonly.ConstPhysicsSettings;
import com.github.stephengold.joltjni.readonly.ConstPhysicsSystem;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * Perform simulation on a collection of physics objects. Bodies are added by
 * way of a separate interface.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class PhysicsSystem extends NonCopyable implements ConstPhysicsSystem {
    // *************************************************************************
    // fields

    /**
     * cached reference to the system's locking {@code BatchBodyInterface}
     */
    final private BatchBodyInterface bodyInterface;
    /**
     * cached reference to the system's no-lock {@code BatchBodyInterface}
     */
    final private BatchBodyInterface bodyInterfaceNoLock;
    /**
     * protect the BroadPhaseLayerInterface from garbage collection
     */
    private ConstBroadPhaseLayerInterface layerMap;
    /**
     * protect the ObjectLayerPairFilter from garbage collection
     */
    private ConstObjectLayerPairFilter ovoFilter;
    /**
     * protect the ObjectVsBroadPhaseLayerFilter from garbage collection
     */
    private ConstObjectVsBroadPhaseLayerFilter ovbFilter;
    /**
     * protect the contact listener (if any) from garbage collection
     */
    private ContactListener contactListener;
    /**
     * protect the step listeners (if any) from garbage collection
     */
    final private Map<Long, PhysicsStepListener> stepListeners
            = new HashMap<>(16);
    /**
     * map virtual {@code PhysicsSystem} addresses back to JVM objects
     */
    final private static Map<Long, PhysicsSystem> va2ps = new HashMap<>(32);
    /**
     * cached reference to the system's locking {@code NarrowPhaseQuery}
     */
    final private ConstNarrowPhaseQuery narrowPhaseQuery;
    /**
     * cached reference to the system's no-lock {@code NarrowPhaseQuery}
     */
    final private ConstNarrowPhaseQuery narrowQueryNoLock;
    /**
     * protect the {@code SimShapeFilter} (if any) from garbage collection
     */
    private SimShapeFilter simShapeFilter;
    /**
     * protect the soft-body contact listener (if any) from garbage collection
     */
    private SoftBodyContactListener softContactListener;
    // *************************************************************************
    // constructors

    /**
     * Instantiate an uninitialized physics system.
     */
    public PhysicsSystem() {
        long systemVa = createDefault();
        setVirtualAddressAsOwner(systemVa);
        va2ps.put(systemVa, this);

        long lockingVa = getBodyInterface(systemVa);
        this.bodyInterface = new BatchBodyInterface(this, lockingVa);

        long noLockVa = getBodyInterfaceNoLock(systemVa);
        this.bodyInterfaceNoLock = new BatchBodyInterface(this, noLockVa);

        lockingVa = getNarrowPhaseQuery(systemVa);
        this.narrowPhaseQuery = new NarrowPhaseQuery(this, lockingVa);

        noLockVa = getNarrowPhaseQueryNoLock(systemVa);
        this.narrowQueryNoLock = new NarrowPhaseQuery(this, noLockVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Add the specified constraint to the system.
     *
     * @param constraint the constraint to add (not {@code null})
     */
    public void addConstraint(Constraint constraint) {
        long systemVa = va();
        long constraintVa = constraint.va();
        addConstraint(systemVa, constraintVa);
    }

    /**
     * Add the specified constraint to the system.
     *
     * @param constraintRef a counted reference to the constraint to add (not
     * null)
     */
    public void addConstraint(TwoBodyConstraintRef constraintRef) {
        long systemVa = va();
        long constraintVa = constraintRef.targetVa();
        addConstraint(systemVa, constraintVa);
    }

    /**
     * Add the specified constraint to the system.
     *
     * @param constraintRef a counted reference to the constraint to add (not
     * null)
     */
    public void addConstraint(VehicleConstraintRef constraintRef) {
        long systemVa = va();
        long constraintVa = constraintRef.targetVa();
        addConstraint(systemVa, constraintVa);
    }

    /**
     * Add the specified step listener to the system.
     * <p>
     * Step listeners are limited in what actions they can perform.
     *
     * @param listener the listener to add (not {@code null}, alias created)
     */
    public void addStepListener(PhysicsStepListener listener) {
        long systemVa = va();
        long listenerVa = listener.targetVa();
        stepListeners.put(listenerVa, listener);
        addStepListener(systemVa, listenerVa);
    }

    /**
     * Remove and destroy all bodies in the system.
     *
     * @return the number of bodies destroyed (&ge;0)
     */
    public int destroyAllBodies() {
        long systemVa = va();
        int result = destroyAllBodies(systemVa);

        return result;
    }

    /**
     * Render the bodies in the system, for debugging purposes.
     *
     * @param settings the settings to use (not {@code null})
     * @param renderer the renderer to use (not {@code null})
     */
    public void drawBodies(
            BodyManagerDrawSettings settings, DebugRenderer renderer) {
        long systemVa = va();
        long settingsVa = settings.va();
        long rendererVa = renderer.va();
        drawBodies(systemVa, settingsVa, rendererVa);
    }

    /**
     * Render all constraints in the system, for debugging purposes.
     *
     * @param renderer the renderer to use (not {@code null})
     */
    public void drawConstraints(DebugRenderer renderer) {
        long systemVa = va();
        long rendererVa = renderer.va();
        drawConstraints(systemVa, rendererVa);
    }

    /**
     * Render the limits of all constraints in the system, for debugging
     * purposes.
     *
     * @param renderer the renderer to use (not {@code null})
     */
    public void drawConstraintLimits(DebugRenderer renderer) {
        long systemVa = va();
        long rendererVa = renderer.va();
        drawConstraintLimits(systemVa, rendererVa);
    }

    /**
     * Render the reference frames of all constraints in the system, for
     * debugging purposes.
     *
     * @param renderer the renderer to use (not {@code null})
     */
    public void drawConstraintReferenceFrame(DebugRenderer renderer) {
        long systemVa = va();
        long rendererVa = renderer.va();
        drawConstraintReferenceFrame(systemVa, rendererVa);
    }

    /**
     * Find a pre-existing system given its virtual address.
     *
     * @param systemVa the address to search for
     * @return the pre-existing object, or {@code null} if not found
     * @see #forgetMe()
     */
    public static PhysicsSystem find(long systemVa) {
        PhysicsSystem result = va2ps.get(systemVa);
        return result;
    }

    /**
     * Make the current system un-findable, so it can be garbage collected.
     *
     * @see #find(long)
     */
    public void forgetMe() {
        long systemVa = va();
        va2ps.remove(systemVa);
    }

    /**
     * Access the system's {@code BatchBodyInterface}.
     *
     * @return the pre-existing JVM object (not {@code null})
     */
    public BatchBodyInterface getBodyInterface() {
        assert bodyInterface != null;
        return bodyInterface;
    }

    /**
     * Access a version of the system's {@code BatchBodyInterface} that does not
     * use locks.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public BatchBodyInterface getBodyInterfaceNoLock() {
        assert bodyInterfaceNoLock != null;
        return bodyInterfaceNoLock;
    }

    /**
     * Access the (application-provided) interface for mapping object layers to
     * broadphase layers.
     *
     * @return the pre-existing instance, or {@code null} if none
     */
    public ConstBroadPhaseLayerInterface getBroadPhaseLayerInterface() {
        return layerMap;
    }

    /**
     * Initialize the system with the specified limits.
     *
     * @param maxBodies the desired maximum number of rigid bodies that can be
     * added
     * @param numBodyMutexes the desired number of mutexes to allocate, or 0 for
     * the default number
     * @param maxBodyPairs the desired maximum number of body pairs that can be
     * queued at a time (&ge;3)
     * @param maxContactConstraints the desired capacity of the
     * contact-constraint buffer
     * @param map (not {@code null}, alias created)
     * @param ovbFilter (not {@code null}, alias created)
     * @param ovoFilter (not {@code null}, alias created)
     * @return the current system (for chaining)
     */
    public PhysicsSystem init(
            int maxBodies, int numBodyMutexes, int maxBodyPairs,
            int maxContactConstraints, ConstBroadPhaseLayerInterface map,
            ConstObjectVsBroadPhaseLayerFilter ovbFilter,
            ConstObjectLayerPairFilter ovoFilter) {
        this.layerMap = map;
        this.ovbFilter = ovbFilter;
        this.ovoFilter = ovoFilter;

        long systemVa = va();
        long mapVa = map.targetVa();
        long ovbFilterVa = ovbFilter.targetVa();
        long ovoFilterVa = ovoFilter.targetVa();
        init(systemVa, maxBodies, numBodyMutexes, maxBodyPairs,
                maxContactConstraints, mapVa, ovbFilterVa, ovoFilterVa);

        return this;
    }

    /**
     * Improve the performance of future collision detections.
     */
    public void optimizeBroadPhase() {
        optimizeBroadPhase(va());
    }

    /**
     * Remove all bodies from the system, but don't destroy them.
     *
     * @return the number of bodies removed (&ge;0)
     * @see com.github.stephengold.joltjni.BodyInterface#removeBody(int)
     */
    public int removeAllBodies() {
        long systemVa = va();
        int result = removeAllBodies(systemVa);

        return result;
    }

    /**
     * Remove all constraints from the system.
     *
     * @return the number of constraints removed (&ge;0)
     */
    public int removeAllConstraints() {
        long systemVa = va();
        int result = removeAllConstraints(systemVa);

        return result;
    }

    /**
     * Remove the specified constraint from the system.
     *
     * @param constraint the constraint to remove (not {@code null})
     */
    public void removeConstraint(Constraint constraint) {
        long systemVa = va();
        long constraintVa = constraint.va();
        removeConstraint(systemVa, constraintVa);
    }

    /**
     * Remove the specified step listener from the system.
     *
     * @param listener the listener to remove (not {@code null})
     */
    public void removeStepListener(PhysicsStepListener listener) {
        long systemVa = va();
        long listenerVa = listener.targetVa();
        stepListeners.remove(listenerVa, listener);
        removeStepListener(systemVa, listenerVa);
    }

    /**
     * Restore the system's state from the specified recorder, for replay.
     *
     * @param recorder where to read the state from (not {@code null})
     * @return {@code true} if successful, otherwise {@code false}
     */
    public boolean restoreState(StateRecorder recorder) {
        long systemVa = va();
        long recorderVa = recorder.va();
        boolean result = restoreState(systemVa, recorderVa);

        return result;
    }

    /**
     * Replace the system's {@code BodyActivationListener}.
     *
     * @param listener the desired listener, or {@code null} for none
     */
    public void setBodyActivationListener(BodyActivationListener listener) {
        long systemVa = va();
        long listenerVa = (listener == null) ? 0L : listener.va();
        setBodyActivationListener(systemVa, listenerVa);
    }

    /**
     * Replace the combining function for friction.
     *
     * @param function the desired function (not {@code null},
     * default=geometricMean)
     */
    public void setCombineFriction(CombineFunction function) {
        long systemVa = va();
        long functionVa = function.va();
        setCombineFriction(systemVa, functionVa);
    }

    /**
     * Replace the combining function for restitutions.
     *
     * @param function the desired function (not {@code null}, default=max)
     */
    public void setCombineRestitution(CombineFunction function) {
        long systemVa = va();
        long functionVa = function.va();
        setCombineRestitution(systemVa, functionVa);
    }

    /**
     * Replace or remove the system's contact listener.
     *
     * @param listener the desired listener, or {@code null} for none
     */
    public void setContactListener(ContactListener listener) {
        this.contactListener = listener;
        long systemVa = va();
        long listenerVa = (listener == null) ? 0L : listener.va();
        setContactListener(systemVa, listenerVa);
    }

    /**
     * Alter the system's gravity vector.
     *
     * @param x the X component of the desired acceleration vector (in system
     * coordinates)
     * @param y the Y component of the desired acceleration vector (in system
     * coordinates)
     * @param z the Z component of the desired acceleration vector (in system
     * coordinates)
     */
    public void setGravity(float x, float y, float z) {
        long systemVa = va();
        setGravity(systemVa, x, y, z);
    }

    /**
     * Alter the system's gravity vector.
     *
     * @param gravity the desired acceleration vector (in system coordinates,
     * not {@code null}, unaffected, default=(0,-9.81,0))
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
     * @param settings the desired settings (not {@code null}, unaffected)
     *
     * @see #getPhysicsSettings()
     */
    public void setPhysicsSettings(ConstPhysicsSettings settings) {
        long systemVa = va();
        long settingsVa = settings.targetVa();
        setPhysicsSettings(systemVa, settingsVa);
    }

    /**
     * Replace the system's body-vs-body collide function.
     *
     * @param bodyVsBody the desired function (not {@code null})
     */
    public void setSimCollideBodyVsBody(SimCollideBodyVsBody bodyVsBody) {
        long systemVa = va();
        long colliderVa = bodyVsBody.va();
        setSimCollideBodyVsBody(systemVa, colliderVa);
    }

    /**
     * Replace or remove the shape filter that will be used during simulation.
     *
     * @param shapeFilter the desired filter, or {@code null} to remove a filter
     * previously set
     */
    public void setSimShapeFilter(SimShapeFilter shapeFilter) {
        this.simShapeFilter = shapeFilter;
        long systemVa = va();
        long filterVa = (shapeFilter == null) ? 0L : shapeFilter.va();
        setSimShapeFilter(systemVa, filterVa);
    }

    /**
     * Replace or remove the system's soft-body contact listener.
     *
     * @param listener the desired listener, or {@code null} for none
     */
    public void setSoftBodyContactListener(SoftBodyContactListener listener) {
        this.softContactListener = listener;
        long systemVa = va();
        long listenerVa = (listener == null) ? 0L : listener.va();
        setSoftBodyContactListener(systemVa, listenerVa);
    }

    /**
     * Advance the simulation by the specified amount.
     *
     * @param deltaTime the total time to advance (in seconds)
     * @param collisionSteps the number of simulation steps to perform
     * @param tempAllocator the allocator to use (not {@code null})
     * @param jobSystem the job system to use (not {@code null})
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
    // protected methods

    /**
     * Assign a native object (assuming there's none already assigned) and
     * designate the JVM object as the owner.
     *
     * @param systemVa the virtual address of the native object to assign (not
     * zero)
     */
    final void setVirtualAddressAsOwner(long systemVa) {
        Runnable freeingAction = () -> free(systemVa);
        setVirtualAddress(systemVa, freeingAction);
    }
    // *************************************************************************
    // ConstPhysicsSystem methods

    /**
     * Test whether the system contains the specified constraint. The system is
     * unaffected.
     *
     * @param constraint the constraint to search for (not {@code null},
     * unaffected)
     * @return {@code true} if found, otherwise {@code false}
     */
    @Override
    public boolean containsConstraint(ConstConstraint constraint) {
        long systemVa = va();
        long constraintVa = constraint.targetVa();
        boolean result = containsConstraint(systemVa, constraintVa);

        return result;
    }

    /**
     * Enumerate all bodies of the specified type to the specified vector.
     *
     * @param bodyType (not {@code null})
     * @param storeResult storage for the result (not {@code null})
     */
    @Override
    public void getActiveBodies(EBodyType bodyType, BodyIdVector storeResult) {
        long systemVa = va();
        int ordinal = bodyType.ordinal();
        long vectorVa = storeResult.va();
        getActiveBodies(systemVa, ordinal, vectorVa);
    }

    /**
     * Enumerate all bodies to the specified variable-length vector. The system
     * is unaffected.
     *
     * @param storeResult storage for the result (not {@code null})
     */
    @Override
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
    @Override
    public BodyActivationListener getBodyActivationListener() {
        long systemVa = va();
        long listenerVa = getBodyActivationListener(systemVa);

        BodyActivationListener result;
        if (listenerVa == 0L) {
            result = null;
        } else {
            result = new BodyActivationListener(listenerVa);
        }

        return result;
    }

    /**
     * Access the system's {@code BodyLockInterfaceLocking}.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public ConstBodyLockInterfaceLocking getBodyLockInterface() {
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
    @Override
    public ConstBodyLockInterfaceNoLock getBodyLockInterfaceNoLock() {
        long systemVa = va();
        long interfaceVa = getBodyLockInterfaceNoLock(systemVa);
        BodyLockInterfaceNoLock result
                = new BodyLockInterfaceNoLock(this, interfaceVa);

        return result;
    }

    /**
     * Generate a bounding box that contains all the bodies in the system. The
     * system is unaffected.
     *
     * @return a new box
     */
    @Override
    public AaBox getBounds() {
        long systemVa = va();
        long boxVa = getBounds(systemVa);
        AaBox result = new AaBox(boxVa, true);

        return result;
    }

    /**
     * Access the system's interface for coarse collision queries.
     *
     * @return the pre-existing JVM object, or {@code null} if the system hasn't
     * been initialized yet
     */
    @Override
    public ConstBroadPhaseQuery getBroadPhaseQuery() {
        long systemVa = va();
        long broadVa = getBroadPhaseQuery(systemVa);
        BroadPhaseQuery result;
        if (broadVa == 0L) {
            result = null;
        } else {
            result = new BroadPhaseQuery(this, broadVa);
        }

        return result;
    }

    /**
     * Access the combining function for friction.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
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
    @Override
    public CombineFunction getCombineRestitution() {
        long systemVa = va();
        long functionVa = getCombineRestitution(systemVa);
        CombineFunction result = new CombineFunction(functionVa);

        return result;
    }

    /**
     * Enumerate all constraints in the system. The system is unaffected.
     *
     * @return a new object
     */
    @Override
    public Constraints getConstraints() {
        long systemVa = va();
        long resultVa = getConstraints(systemVa);
        Constraints result = new Constraints(resultVa, true);

        return result;
    }

    /**
     * Access the (application-provided) contact listener.
     *
     * @return the pre-existing instance, or {@code null} if none
     */
    @Override
    public ContactListener getContactListener() {
        return contactListener;
    }

    /**
     * Generate a broad-phase layer filter using the current pair filter and the
     * specified layer index.
     *
     * @param objectLayer the index of the object layer to use
     * @return a new filter
     */
    @Override
    public DefaultBroadPhaseLayerFilter getDefaultBroadPhaseLayerFilter(
            int objectLayer) {
        DefaultBroadPhaseLayerFilter result
                = new DefaultBroadPhaseLayerFilter(ovbFilter, objectLayer);
        return result;
    }

    /**
     * Generate an object layer filter using the current pair filter and the
     * specified layer index.
     *
     * @param objectLayer the index of the object layer to use
     * @return a new filter
     */
    @Override
    public DefaultObjectLayerFilter getDefaultLayerFilter(int objectLayer) {
        DefaultObjectLayerFilter result
                = new DefaultObjectLayerFilter(ovoFilter, objectLayer);
        return result;
    }

    /**
     * Copy the gravity vector. The system is unaffected.
     *
     * @return a new acceleration vector (meters per second squared in system
     * coordinates)
     */
    @Override
    public Vec3 getGravity() {
        long systemVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getGravity(systemVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Return the maximum number of bodies the system supports. The system is
     * unaffected.
     *
     * @return the count (&ge;0)
     */
    @Override
    public int getMaxBodies() {
        long systemVa = va();
        int result = getMaxBodies(systemVa);

        return result;
    }

    /**
     * Access the system's interface for fine collision queries.
     *
     * @return the pre-existing JVM object (not {@code null})
     */
    @Override
    public ConstNarrowPhaseQuery getNarrowPhaseQuery() {
        return narrowPhaseQuery;
    }

    /**
     * Access a version of the system's {@code NarrowPhaseQuery} that does not
     * lock the bodies.
     *
     * @return the pre-existing JVM object (not {@code null})
     */
    @Override
    public ConstNarrowPhaseQuery getNarrowPhaseQueryNoLock() {
        return narrowQueryNoLock;
    }

    /**
     * Count how many active bodies of the specified type there are in the body
     * manager. The system is unaffected.
     *
     * @param bodyType which type of body to count (not {@code null})
     * @return the count (&ge;0, &le;maxBodies)
     */
    @Override
    public int getNumActiveBodies(EBodyType bodyType) {
        long systemVa = va();
        int typeOrdinal = bodyType.ordinal();
        int result = getNumActiveBodies(systemVa, typeOrdinal);

        return result;
    }

    /**
     * Count how many bodies there are in the body manager. The system is
     * unaffected.
     *
     * @return the count (&ge;0, &le;maxBodies)
     */
    @Override
    public int getNumBodies() {
        long systemVa = va();
        int result = getNumBodies(systemVa);

        return result;
    }

    /**
     * Access the (application-provided) interface for testing whether 2 objects
     * can collide, based on their object layers.
     *
     * @return the pre-existing instance, or {@code null} if none
     */
    @Override
    public ConstObjectLayerPairFilter getObjectLayerPairFilter() {
        return ovoFilter;
    }

    /**
     * Access the (application-provided) interface for testing whether an object
     * can collide with a broad-phase layer.
     *
     * @return the pre-existing instance, or {@code null} if none
     */
    @Override
    public ConstObjectVsBroadPhaseLayerFilter
            getObjectVsBroadPhaseLayerFilter() {
        return ovbFilter;
    }

    /**
     * Copy the system's settings. The system is unaffected.
     * <p>
     * Different semantics from the native {@code GetPhysicsSettings()}, which
     * returns a const reference, not a copy.
     *
     * @return a new JVM object with a new native object assigned
     *
     * @see #setPhysicsSettings(
     * com.github.stephengold.joltjni.readonly.ConstPhysicsSettings)
     */
    @Override
    public PhysicsSettings getPhysicsSettings() {
        long systemVa = va();
        long settingsVa = getPhysicsSettings(systemVa);
        PhysicsSettings result = new PhysicsSettings(settingsVa, true);

        return result;
    }

    /**
     * Access the (application-provided) soft-body contact listener.
     *
     * @return the pre-existing instance, or {@code null} if none
     */
    @Override
    public SoftBodyContactListener getSoftBodyContactListener() {
        return softContactListener;
    }

    /**
     * Save the system's state to be replayed later.
     *
     * @param recorder where to save the state (not {@code null})
     */
    @Override
    public void saveState(StateRecorder recorder) {
        saveState(recorder, EStateRecorderState.All);
    }

    /**
     * Save the aspects of the system's state to be replayed later.
     *
     * @param recorder where to save the state (not {@code null})
     * @param bitmask which aspects of the simulation to save
     */
    @Override
    public void saveState(StateRecorder recorder, int bitmask) {
        saveState(recorder, bitmask, null);
    }

    /**
     * Save aspects of the system's state to be replayed later.
     *
     * @param recorder where to save the state (not {@code null})
     * @param bitmask which aspects of the simulation to save
     * @param filter select which parts to save (unaffected) or {@code null}
     */
    @Override
    public void saveState(
            StateRecorder recorder, int bitmask, StateRecorderFilter filter) {
        long systemVa = va();
        long recorderVa = recorder.va();
        long filterVa = (filter == null) ? 0L : filter.va();
        saveState(systemVa, recorderVa, bitmask, filterVa);
    }
    // *************************************************************************
    // native private methods

    native private static void addConstraint(long systemVa, long constraintVa);

    native private static void addStepListener(long systemVa, long listenerVa);

    native private static boolean containsConstraint(
            long systemVa, long constraintVa);

    native private static long createDefault();

    native private static int destroyAllBodies(long systemVa);

    native private static void drawBodies(
            long systemVa, long settingsVa, long rendererVa);

    native private static void drawConstraints(long systemVa, long rendererVa);

    native private static void drawConstraintLimits(
            long systemVa, long rendererVa);

    native private static void drawConstraintReferenceFrame(
            long systemVa, long rendererVa);

    native private static void free(long systemVa);

    native private static void getActiveBodies(
            long systemVa, int ordinal, long vectorVa);

    native private static void getBodies(long systemVa, long vectorVa);

    native private static long getBodyActivationListener(long systemVa);

    native private static long getBodyInterface(long systemVa);

    native private static long getBodyInterfaceNoLock(long systemVa);

    native private static long getBodyLockInterface(long systemVa);

    native private static long getBodyLockInterfaceNoLock(long systemVa);

    native private static long getBounds(long systemVa);

    native private static long getBroadPhaseQuery(long systemVa);

    native private static long getCombineFriction(long systemVa);

    native private static long getCombineRestitution(long systemVa);

    native private static long getConstraints(long systemVa);

    native private static void getGravity(
            long systemVa, FloatBuffer storeFloats);

    native private static long getNarrowPhaseQuery(long systemVa);

    native private static long getNarrowPhaseQueryNoLock(long systemVa);

    native private static int getMaxBodies(long systemVa);

    native private static int getNumActiveBodies(
            long systemVa, int typeOrdinal);

    native private static int getNumBodies(long systemVa);

    native private static long getPhysicsSettings(long systemVa);

    native private static void init(long systemVa, int maxBodies,
            int numBodyMutexes, int maxBodyPairs, int maxContactConstraints,
            long mapVa, long ovbFilterVa, long ovoFilterVa);

    native private static void optimizeBroadPhase(long systemVa);

    native private static int removeAllBodies(long systemVa);

    native private static int removeAllConstraints(long systemVa);

    native private static void removeConstraint(
            long systemVa, long constraintVa);

    native private static void removeStepListener(
            long systemVa, long listenerVa);

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

    native private static void setSimCollideBodyVsBody(
            long systemVa, long functionVa);

    native private static void setSimShapeFilter(long systemVa, long filterVa);

    native private static void setSoftBodyContactListener(
            long systemVa, long listenerVa);

    native private static int update(long physicsSystemVa, float deltaTime,
            int collisionSteps, long allocatorVa, long jobSystemVa);
}
