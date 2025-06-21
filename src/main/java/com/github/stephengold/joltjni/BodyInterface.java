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
import com.github.stephengold.joltjni.enumerate.EBodyType;
import com.github.stephengold.joltjni.enumerate.EMotionQuality;
import com.github.stephengold.joltjni.enumerate.EMotionType;
import com.github.stephengold.joltjni.readonly.ConstAaBox;
import com.github.stephengold.joltjni.readonly.ConstBody;
import com.github.stephengold.joltjni.readonly.ConstBodyCreationSettings;
import com.github.stephengold.joltjni.readonly.ConstShape;
import com.github.stephengold.joltjni.readonly.ConstSoftBodyCreationSettings;
import com.github.stephengold.joltjni.readonly.ConstTwoBodyConstraint;
import com.github.stephengold.joltjni.readonly.QuatArg;
import com.github.stephengold.joltjni.readonly.RVec3Arg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;

/**
 * An interface to a {@code PhysicsSystem}, used to create, add, modify, query,
 * remove, and delete bodies.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class BodyInterface extends NonCopyable {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified container and native object.
     *
     * @param system the containing object, or {@code null} if none
     * @param bodyInterfaceVa the virtual address of the native object to assign
     * (not zero)
     */
    BodyInterface(PhysicsSystem system, long bodyInterfaceVa) {
        super(system, bodyInterfaceVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Active all bodies within the specified bounds that satisfy the specified
     * filters.
     *
     * @param box the bounds to use (not null, unaffected)
     * @param bplFilter the broadphase layer filter to apply (not null,
     * unaffected)
     * @param olFilter the object-layer filter to apply (not null, unaffected)
     */
    public void activateBodiesInAaBox(ConstAaBox box,
            BroadPhaseLayerFilter bplFilter, ObjectLayerFilter olFilter) {
        long bodyInterfaceVa = va();
        long boxVa = box.targetVa();
        long bplFilterVa = bplFilter.va();
        long olFilterVa = olFilter.va();
        activateBodiesInAaBox(bodyInterfaceVa, boxVa, bplFilterVa, olFilterVa);
    }

    /**
     * Activate the specified body.
     *
     * @param bodyId the ID of the body to activate
     */
    public void activateBody(int bodyId) {
        long bodyInterfaceVa = va();
        activateBody(bodyInterfaceVa, bodyId);
    }

    /**
     * Activate all non-static bodies attached to the specified constraint.
     *
     * @param constraint the constraint to activate (not null)
     */
    public void activateConstraint(ConstTwoBodyConstraint constraint) {
        long bodyInterfaceVa = va();
        long constraintVa = constraint.targetVa();
        activateConstraint(bodyInterfaceVa, constraintVa);
    }

    /**
     * Apply the specified angular impulse to the specified body.
     *
     * @param bodyId the ID of the body
     * @param angularImpulse the impulse vector (not null, unaffected)
     */
    public void addAngularImpulse(int bodyId, Vec3Arg angularImpulse) {
        long bodyInterfaceVa = va();
        float lx = angularImpulse.getX();
        float ly = angularImpulse.getY();
        float lz = angularImpulse.getZ();
        addAngularImpulse(bodyInterfaceVa, bodyId, lx, ly, lz);
    }

    /**
     * Abort adding bodies to the physics system.
     *
     * @param bodyIds the IDs of the bodies to be added (not null, unmodified
     * since the handle was created)
     * @param numBodies the number of bodies to be added (&ge;0)
     * @param addState the handle returned by {@code addBodiesPrepare()}
     */
    public void addBodiesAbort(
            BodyIdArray bodyIds, int numBodies, long addState) {
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.va();
        addBodiesAbort(bodyInterfaceVa, arrayVa, numBodies, addState);
    }

    /**
     * Finish adding bodies to the physics system.
     *
     * @param bodyIds the IDs of the bodies to be added (not null, unmodified
     * since the handle was created)
     * @param numBodies the number of bodies to be added (&ge;0)
     * @param addState the handle returned by {@code addBodiesPrepare()}
     * @param activation whether to activate the bodies (not null)
     */
    public void addBodiesFinalize(BodyIdArray bodyIds, int numBodies,
            long addState, EActivation activation) {
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.va();
        int activationOrdinal = activation.ordinal();
        addBodiesFinalize(bodyInterfaceVa, arrayVa, numBodies, addState,
                activationOrdinal);
    }

    /**
     * Prepare to add a batch of bodies to the physics system.
     *
     * @param bodyIds the IDs of the bodies to be added (not null, possibly
     * shuffled)
     * @param numBodies the number of bodies to be added (&ge;0)
     * @return a handle to be passed to {@code addBodiesFinalize()} or
     * {@code addBodiesAbort()}
     */
    public long addBodiesPrepare(BodyIdArray bodyIds, int numBodies) {
        long bodyInterfaceVa = va();
        long arrayVa = bodyIds.va();
        long result = addBodiesPrepare(bodyInterfaceVa, arrayVa, numBodies);

        return result;
    }

    /**
     * Add the specified body to the physics system.
     * <p>
     * To add many bodies at once, use {@code addBodiesPrepare()} followed by
     * {@code addBodiesFinalize()}.
     *
     * @param body the body to add (not null)
     * @param activation whether to activate the body (not null)
     */
    public void addBody(ConstBody body, EActivation activation) {
        long bodyInterfaceVa = va();
        long bodyVa = body.targetVa();
        int bodyId = Body.getId(bodyVa);
        int activationOrdinal = activation.ordinal();
        addBody(bodyInterfaceVa, bodyId, activationOrdinal);
    }

    /**
     * Add the specified body to the physics system.
     *
     * @param bodyId the ID of the body to add
     * @param activation whether to activate the body (not null)
     */
    public void addBody(int bodyId, EActivation activation) {
        long bodyInterfaceVa = va();
        int activationOrdinal = activation.ordinal();
        addBody(bodyInterfaceVa, bodyId, activationOrdinal);
    }

    /**
     * Apply the specified force to the specified body's center of mass.
     *
     * @param bodyId the ID of the body
     * @param force the force vector (not null, unaffected)
     */
    public void addForce(int bodyId, Vec3Arg force) {
        long bodyInterfaceVa = va();
        float fx = force.getX();
        float fy = force.getY();
        float fz = force.getZ();
        addForce(bodyInterfaceVa, bodyId, fx, fy, fz);
    }

    /**
     * Apply the specified force to the specified body at the specified
     * location.
     *
     * @param bodyId the ID of the body
     * @param force the force vector (not null, unaffected)
     * @param location where to apply the force (not null, unaffected)
     */
    public void addForce(int bodyId, Vec3Arg force, RVec3Arg location) {
        long bodyInterfaceVa = va();
        float fx = force.getX();
        float fy = force.getY();
        float fz = force.getZ();
        double locX = location.xx();
        double locY = location.yy();
        double locZ = location.zz();
        addForce(bodyInterfaceVa, bodyId, fx, fy, fz, locX, locY, locZ);
    }

    /**
     * Apply the specified impulse to the specified body's center of mass.
     *
     * @param bodyId the ID of the body
     * @param impulse the impulse vector (not null, unaffected)
     */
    public void addImpulse(int bodyId, Vec3Arg impulse) {
        long bodyInterfaceVa = va();
        float jx = impulse.getX();
        float jy = impulse.getY();
        float jz = impulse.getZ();
        addImpulse(bodyInterfaceVa, bodyId, jx, jy, jz);
    }

    /**
     * Apply the specified impulse to the specified body at the specified
     * location.
     *
     * @param bodyId the ID of the body
     * @param impulse the impulse vector (not null, unaffected)
     * @param location where to apply the impulse (not null, unaffected)
     */
    public void addImpulse(int bodyId, Vec3Arg impulse, RVec3Arg location) {
        long bodyInterfaceVa = va();
        float jx = impulse.getX();
        float jy = impulse.getY();
        float jz = impulse.getZ();
        double locX = location.xx();
        double locY = location.yy();
        double locZ = location.zz();
        addImpulse(bodyInterfaceVa, bodyId, jx, jy, jz, locX, locY, locZ);
    }

    /**
     * Apply the specified torque to the specified body.
     *
     * @param bodyId the ID of the body
     * @param torque the torque vector (not null, unaffected)
     */
    public void addTorque(int bodyId, Vec3Arg torque) {
        long bodyInterfaceVa = va();
        float x = torque.getX();
        float y = torque.getY();
        float z = torque.getZ();
        addTorque(bodyInterfaceVa, bodyId, x, y, z);
    }

    /**
     * Create a rigid body and add it to the physics system.
     *
     * @param settings the settings to use (not null, unaffected)
     * @param activationMode whether to activate the body (not null)
     * @return the ID of the created body, or an invalid ID when out of bodies
     */
    public int createAndAddBody(
            ConstBodyCreationSettings settings, EActivation activationMode) {
        ConstBody body = createBody(settings);
        int result = body.getId();
        addBody(result, activationMode);

        return result;
    }

    /**
     * Create a soft body and add it to the physics system.
     *
     * @param settings the settings to use (not null, unaffected)
     * @param activationMode whether to activate the body (not null)
     * @return the ID of the created body, or an invalid ID when out of bodies
     */
    public int createAndAddSoftBody(ConstSoftBodyCreationSettings settings,
            EActivation activationMode) {
        ConstBody body = createSoftBody(settings);
        int result = body.getId();
        addBody(result, activationMode);

        return result;
    }

    /**
     * Create a rigid body using the specified settings.
     *
     * @param settings the settings to use (not null, unaffected)
     * @return the new body
     */
    public Body createBody(ConstBodyCreationSettings settings) {
        long bodyInterfaceVa = va();
        long settingsVa = settings.targetVa();
        long bodyVa = createBody(bodyInterfaceVa, settingsVa);
        if (bodyVa == 0L) {
            throw new IllegalStateException("ran out of bodies");
        }
        PhysicsSystem system = getSystem();
        Body result = new Body(system, bodyVa);

        return result;
    }

    /**
     * Create a two-body constraint using the specified settings.
     *
     * @param settings the settings to use (not null, unaffected)
     * @param body1Id the ID of the first body
     * @param body2Id the ID of the 2nd body
     * @return the new constraint
     */
    public TwoBodyConstraint createConstraint(
            TwoBodyConstraintSettings settings, int body1Id, int body2Id) {
        long bodyInterfaceVa = va();
        long settingsVa = settings.va();
        long constraintVa = createConstraint(
                bodyInterfaceVa, settingsVa, body1Id, body2Id);
        TwoBodyConstraint result
                = (TwoBodyConstraint) Constraint.newConstraint(constraintVa);

        return result;
    }

    /**
     * Create a soft body using the specified settings.
     *
     * @param settings the settings to use (not null, unaffected)
     * @return the new body
     */
    public Body createSoftBody(ConstSoftBodyCreationSettings settings) {
        long bodyInterfaceVa = va();
        long settingsVa = settings.targetVa();
        long bodyVa = createSoftBody(bodyInterfaceVa, settingsVa);
        if (bodyVa == 0L) {
            throw new IllegalStateException("ran out of bodies");
        }
        PhysicsSystem system = getSystem();
        Body result = new Body(system, bodyVa);

        return result;
    }

    /**
     * Deactivate the specified body.
     *
     * @param bodyId the ID of the body to deactivate
     */
    public void deactivateBody(int bodyId) {
        long bodyInterfaceVa = va();
        deactivateBody(bodyInterfaceVa, bodyId);
    }

    /**
     * Destroy the specified body. Don't use this on a body that has been added
     * but not removed yet!
     *
     * @param bodyId the ID of the body to destroy
     */
    public void destroyBody(int bodyId) {
        long bodyInterfaceVa = va();
        destroyBody(bodyInterfaceVa, bodyId);
    }

    /**
     * Return the angular velocity of the specified body.
     *
     * @param bodyId the ID of the body
     * @return a new velocity vector (radians per second in system coordinates)
     */
    public Vec3 getAngularVelocity(int bodyId) {
        long bodyInterfaceVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getAngularVelocity(bodyInterfaceVa, bodyId, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Return the type of the specified body.
     *
     * @param bodyId the ID of the body to query
     * @return an enum value (not null)
     */
    public EBodyType getBodyType(int bodyId) {
        long bodyInterfaceVa = va();
        int ordinal = getBodyType(bodyInterfaceVa, bodyId);
        EBodyType result = EBodyType.values()[ordinal];

        return result;
    }

    /**
     * Locate the center of mass of the specified body.
     *
     * @param bodyId the ID of the body to locate
     * @return a new location vector (in system coordinates)
     */
    public RVec3 getCenterOfMassPosition(int bodyId) {
        long bodyInterfaceVa = va();
        DoubleBuffer storeDoubles = Temporaries.doubleBuffer1.get();
        getCenterOfMassPosition(bodyInterfaceVa, bodyId, storeDoubles);
        RVec3 result = new RVec3(storeDoubles);

        return result;
    }

    /**
     * Return the center-of-mass transform of the specified body.
     *
     * @param bodyId the ID of the body to locate
     * @return a new transform matrix (relative to system coordinates)
     */
    public RMat44 getCenterOfMassTransform(int bodyId) {
        long bodyInterfaceVa = va();
        long matrixVa = getCenterOfMassTransform(bodyInterfaceVa, bodyId);
        RMat44 result = new RMat44(matrixVa, true);

        return result;
    }

    /**
     * Return the friction ratio of the specified body.
     *
     * @param bodyId the ID of the body
     * @return the ratio
     */
    public float getFriction(int bodyId) {
        long bodyInterfaceVa = va();
        float result = getFriction(bodyInterfaceVa, bodyId);
        return result;
    }

    /**
     * Return the gravity factor of the specified body.
     *
     * @param bodyId the ID of the body
     * @return the factor
     */
    public float getGravityFactor(int bodyId) {
        long bodyInterfaceVa = va();
        float result = getGravityFactor(bodyInterfaceVa, bodyId);

        return result;
    }

    /**
     * Return the linear velocity of the specified body.
     *
     * @param bodyId the ID of the body
     * @return a new velocity vector (meters per second in system coordinates)
     */
    public Vec3 getLinearVelocity(int bodyId) {
        long bodyInterfaceVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getLinearVelocity(bodyInterfaceVa, bodyId, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Return the motion quality.
     *
     * @param bodyId the ID of the body to query
     * @return an enum value (not null)
     */
    public EMotionQuality getMotionQuality(int bodyId) {
        long bodyInterfaceVa = va();
        int ordinal = getMotionQuality(bodyInterfaceVa, bodyId);
        EMotionQuality result = EMotionQuality.values()[ordinal];

        return result;
    }

    /**
     * Return the motion type of the specified body.
     *
     * @param bodyId the ID of the body to query
     * @return an enum value (not null)
     */
    public EMotionType getMotionType(int bodyId) {
        long bodyInterfaceVa = va();
        int ordinal = getMotionType(bodyInterfaceVa, bodyId);
        EMotionType result = EMotionType.values()[ordinal];

        return result;
    }

    /**
     * Return the object layer of the specified body.
     *
     * @param bodyId the ID of the body to query
     * @return an object-layer index
     */
    public int getObjectLayer(int bodyId) {
        long bodyInterfaceVa = va();
        int result = getObjectLayer(bodyInterfaceVa, bodyId);

        return result;
    }

    /**
     * Locate the specified body.
     *
     * @param bodyId the ID of the body to locate
     * @return a new location vector (in system coordinates)
     */
    public RVec3 getPosition(int bodyId) {
        long bodyInterfaceVa = va();
        DoubleBuffer storeDoubles = Temporaries.doubleBuffer1.get();
        getPosition(bodyInterfaceVa, bodyId, storeDoubles);
        RVec3 result = new RVec3(storeDoubles);

        return result;
    }

    /**
     * Copy the location and orientation of the specified body.
     *
     * @param bodyId the ID of the body to locate
     * @param storeLocation storage for the location (not null, modified)
     * @param storeOrientation storage for the orientation (not null, modified)
     */
    public void getPositionAndRotation(
            int bodyId, RVec3 storeLocation, Quat storeOrientation) {
        long bodyInterfaceVa = va();
        DoubleBuffer storeDoubles = Temporaries.doubleBuffer1.get();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();

        getPositionAndRotation(
                bodyInterfaceVa, bodyId, storeDoubles, storeFloats);
        storeLocation.set(storeDoubles);
        storeOrientation.set(storeFloats);
    }

    /**
     * Return the restitution ratio of the specified body.
     *
     * @param bodyId the ID of the body to query
     * @return the value (typically &ge;0 and &le;1)
     */
    public float getRestitution(int bodyId) {
        long bodyInterfaceVa = va();
        float result = getRestitution(bodyInterfaceVa, bodyId);

        return result;
    }

    /**
     * Copy the orientation of the specified body.
     *
     * @param bodyId the ID of the body
     * @return a new rotation quaternion
     */
    public Quat getRotation(int bodyId) {
        long bodyInterfaceVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();

        getRotation(bodyInterfaceVa, bodyId, storeFloats);
        Quat result = new Quat(storeFloats);

        return result;
    }

    /**
     * Access the body's shape.
     *
     * @param bodyId the ID of the body
     * @return a new reference
     */
    public ShapeRefC getShape(int bodyId) {
        long bodyInterfaceVa = va();
        long shapeRefVa = getShape(bodyInterfaceVa, bodyId);
        ShapeRefC result = new ShapeRefC(shapeRefVa, true);

        return result;
    }

    /**
     * Access the containing {@code PhysicsSystem}.
     *
     * @return the pre-existing instance
     */
    public PhysicsSystem getSystem() {
        PhysicsSystem result = (PhysicsSystem) getContainingObject();
        return result;
    }

    /**
     * Return the user data of the specified body.
     *
     * @param bodyId the ID of the body
     * @return the value
     */
    public long getUserData(int bodyId) {
        long bodyInterfaceVa = va();
        long result = getUserData(bodyInterfaceVa, bodyId);

        return result;
    }

    /**
     * Test whether the specified body is active.
     *
     * @param bodyId the ID of the body to test
     * @return {@code true} if active, otherwise {@code false}
     */
    public boolean isActive(int bodyId) {
        long bodyInterfaceVa = va();
        boolean result = isActive(bodyInterfaceVa, bodyId);
        return result;
    }

    /**
     * Test whether the specified body is added to the system.
     *
     * @param bodyId the ID of the body to search for
     * @return {@code true} if added, otherwise {@code false}
     */
    public boolean isAdded(int bodyId) {
        long bodyInterfaceVa = va();
        boolean result = isAdded(bodyInterfaceVa, bodyId);
        return result;
    }

    /**
     * Test whether the specified body is a sensor.
     *
     * @param bodyId the ID of the body to test
     * @return {@code true} if a sensor, otherwise {@code false}
     */
    public boolean isSensor(int bodyId) {
        long bodyInterfaceVa = va();
        boolean result = isSensor(bodyInterfaceVa, bodyId);
        return result;
    }

    /**
     * Reposition the specified body, assuming it's kinematic.
     *
     * @param bodyId the ID of the body to reposition
     * @param location the desired location (in system coordinates, not null,
     * unaffected)
     * @param orientation the desired orientation (relative to the system axes,
     * not null, unaffected)
     * @param deltaTime time until the desired position is reached (in seconds,
     * &gt;0)
     */
    public void moveKinematic(int bodyId,
            RVec3Arg location, QuatArg orientation, float deltaTime) {
        long bodyInterfaceVa = va();
        double xx = location.xx();
        double yy = location.yy();
        double zz = location.zz();
        float qw = orientation.getW();
        float qx = orientation.getX();
        float qy = orientation.getY();
        float qz = orientation.getZ();
        moveKinematic(bodyInterfaceVa, bodyId, xx, yy, zz,
                qx, qy, qz, qw, deltaTime);
    }

    /**
     * Notify all systems that the shape of the specified body has changed.
     *
     * @param bodyId the ID of the affected body
     * @param prevCom center of mass prior to the change (not null, unaffected)
     * @param updateMassProperties {@code true} to recalculate mass and inertia
     * @param activation whether to activate the body (not null)
     */
    public void notifyShapeChanged(int bodyId, Vec3Arg prevCom,
            boolean updateMassProperties, EActivation activation) {
        long bodyInterfaceVa = va();
        int activationOrdinal = activation.ordinal();
        notifyShapeChanged(bodyInterfaceVa, bodyId, prevCom.getX(),
                prevCom.getY(), prevCom.getZ(), updateMassProperties,
                activationOrdinal);
    }

    /**
     * Remove the specified body from the physics system, but don't destroy it.
     *
     * @param bodyId the ID of the body to remove
     */
    public void removeBody(int bodyId) {
        long bodyInterfaceVa = va();
        removeBody(bodyInterfaceVa, bodyId);
    }

    /**
     * Alter the linear velocity of the specified body.
     *
     * @param bodyId the ID of the body to modify
     * @param omega the desired rates (not null, unaffected)
     */
    public void setAngularVelocity(int bodyId, Vec3Arg omega) {
        long bodyInterfaceVa = va();
        setAngularVelocity(bodyInterfaceVa, bodyId,
                omega.getX(), omega.getY(), omega.getZ());
    }

    /**
     * Alter the friction ratio of the specified body.
     *
     * @param bodyId the ID of the body to modify
     * @param friction the desired ratio (typically &ge;0 and &le;1,
     * default=0.2)
     */
    public void setFriction(int bodyId, float friction) {
        long bodyInterfaceVa = va();
        setFriction(bodyInterfaceVa, bodyId, friction);
    }

    /**
     * Alter the gravity factor of the specified body.
     *
     * @param bodyId the ID of the body to modify
     * @param factor the desired factor (default=1)
     */
    public void setGravityFactor(int bodyId, float factor) {
        long bodyInterfaceVa = va();
        setGravityFactor(bodyInterfaceVa, bodyId, factor);
    }

    /**
     * Alter whether the specified body is a sensor.
     *
     * @param bodyId the ID of the body to modify
     * @param setting {@code true} for a sensor, or {@code false} for a
     * non-sensor
     */
    public void setIsSensor(int bodyId, boolean setting) {
        long bodyInterfaceVa = va();
        setIsSensor(bodyInterfaceVa, bodyId, setting);
    }

    /**
     * Alter the linear and angular velocities of the specified body.
     *
     * @param bodyId the ID of the body to modify
     * @param linearVelocity the desired linear velocity of the body's center of
     * mass (not null, unaffected)
     * @param angularVelocity the desired angular velocity (not null,
     * unaffected)
     */
    public void setLinearAndAngularVelocity(int bodyId,
            Vec3Arg linearVelocity, Vec3Arg angularVelocity) {
        long bodyInterfaceVa = va();
        float vx = linearVelocity.getX();
        float vy = linearVelocity.getY();
        float vz = linearVelocity.getZ();
        float wx = angularVelocity.getX();
        float wy = angularVelocity.getY();
        float wz = angularVelocity.getZ();
        setLinearAndAngularVelocity(
                bodyInterfaceVa, bodyId, vx, vy, vz, wx, wy, wz);
    }

    /**
     * Alter the linear velocity of the specified body.
     *
     * @param bodyId the ID of the body to modify
     * @param velocity the desired velocity (not null, unaffected)
     */
    public void setLinearVelocity(int bodyId, Vec3Arg velocity) {
        long bodyInterfaceVa = va();
        setLinearVelocity(bodyInterfaceVa, bodyId,
                velocity.getX(), velocity.getY(), velocity.getZ());
    }

    /**
     * Alter the motion quality of the specified body.
     *
     * @param bodyId the ID of the body to modify
     * @param quality the desired level of quality (not null)
     */
    public void setMotionQuality(int bodyId, EMotionQuality quality) {
        long bodyInterfaceVa = va();
        int ordinal = quality.ordinal();
        setMotionQuality(bodyInterfaceVa, bodyId, ordinal);
    }

    /**
     * Alter the motion type of the specified body.
     *
     * @param bodyId the ID of the body to modify
     * @param motionType the desired motion type (not null)
     * @param activationMode whether to activate the body (not null)
     */
    public void setMotionType(int bodyId, EMotionType motionType,
            EActivation activationMode) {
        long bodyInterfaceVa = va();
        int motionOrdinal = motionType.ordinal();
        int activationOrdinal = activationMode.ordinal();
        setMotionType(
                bodyInterfaceVa, bodyId, motionOrdinal, activationOrdinal);
    }

    /**
     * Alter the object layer of the specified body.
     *
     * @param bodyId the ID of the body to modify
     * @param layer the index of the desired object layer (&ge;0,
     * &lt;numObjectLayers, &lt;65536, default=0)
     */
    public void setObjectLayer(int bodyId, int layer) {
        assert layer >= 0 && layer < 65_536 : "layer = " + layer;

        long bodyInterfaceVa = va();
        setObjectLayer(bodyInterfaceVa, bodyId, layer);
    }

    /**
     * Relocate the specified body.
     *
     * @param bodyId the ID of the body to relocate
     * @param location the desired location (not null, unaffected)
     * @param activationMode whether to activate the body (not null)
     */
    public void setPosition(
            int bodyId, RVec3Arg location, EActivation activationMode) {
        long bodyInterfaceVa = va();
        double locX = location.xx();
        double locY = location.yy();
        double locZ = location.zz();
        int ordinal = activationMode.ordinal();
        setPosition(bodyInterfaceVa, bodyId, locX, locY, locZ, ordinal);
    }

    /**
     * Reposition the specified body.
     *
     * @param bodyId the ID of the body to modify
     * @param location the desired location (not null, unaffected)
     * @param orientation the desired orientation (not null, unaffected)
     * @param activationMode whether to activate the body (not null)
     */
    public void setPositionAndRotation(int bodyId, RVec3Arg location,
            QuatArg orientation, EActivation activationMode) {
        long bodyInterfaceVa = va();
        double locX = location.xx();
        double locY = location.yy();
        double locZ = location.zz();
        float qw = orientation.getW();
        float qx = orientation.getX();
        float qy = orientation.getY();
        float qz = orientation.getZ();
        int ordinal = activationMode.ordinal();
        setPositionAndRotation(bodyInterfaceVa, bodyId, locX, locY, locZ,
                qx, qy, qz, qw, ordinal);
    }

    /**
     * Alter the restitution ratio of the specified body.
     *
     * @param bodyId the ID of the body to modify
     * @param restitution the desired ratio (typically &ge;0 and &le;1,
     * default=0)
     */
    public void setRestitution(int bodyId, float restitution) {
        long bodyInterfaceVa = va();
        setRestitution(bodyInterfaceVa, bodyId, restitution);
    }

    /**
     * Replace the shape of the specified body.
     *
     * @param bodyId the ID of the body to reshape
     * @param shape the desired shape to apply (not null)
     * @param updateMassProperties if {@code true}, recalculate the body's mass
     * and inertia, otherwise don't recalculate
     * @param activation whether to activate the body (not null)
     */
    public void setShape(int bodyId, ConstShape shape,
            boolean updateMassProperties, EActivation activation) {
        long bodyInterfaceVa = va();
        long shapeVa = shape.targetVa();
        int ordinal = activation.ordinal();
        setShape(bodyInterfaceVa, bodyId, shapeVa, updateMassProperties,
                ordinal);
    }

    /**
     * Alter the user data of the specified body.
     *
     * @param bodyId the ID of the body to modify
     * @param value the desired value
     */
    public void setUserData(int bodyId, long value) {
        long bodyInterfaceVa = va();
        setUserData(bodyInterfaceVa, bodyId, value);
    }
    // *************************************************************************
    // native private methods

    native private static void activateBodiesInAaBox(long bodyInterfaceVa,
            long boxVa, long bplFilterVa, long olFilterVa);

    native private static void activateBody(long bodyInterfaceVa, int bodyId);

    native private static void activateConstraint(
            long bodyInterfaceVa, long constraintVa);

    native private static void addAngularImpulse(
            long bodyInterfaceVa, int bodyId, float lx, float ly, float lz);

    native private static void addBodiesAbort(
            long bodyInterfaceVa, long arrayVa, int numBodies, long addState);

    native private static void addBodiesFinalize(long bodyInterfaceVa,
            long arrayVa, int numBodies, long addState, int activationOrdinal);

    native private static long addBodiesPrepare(
            long bodyInterfaceVa, long arrayVa, int numBodies);

    native private static void addBody(
            long bodyInterfaceVa, int bodyId, int activationOrdinal);

    native private static void addForce(
            long bodyInterfaceVa, int bodyId, float fx, float fy, float fz);

    native private static void addForce(
            long bodyInterfaceVa, int bodyId, float fx, float fy, float fz,
            double locX, double locY, double locZ);

    native private static void addImpulse(
            long bodyInterfaceVa, int bodyId, float jx, float jy, float jz);

    native private static void addImpulse(
            long bodyInterfaceVa, int bodyId, float jx, float jy, float jz,
            double locX, double locY, double locZ);

    native private static void addTorque(
            long bodyInterfaceVa, int bodyId, float x, float y, float z);

    native private static long createBody(
            long bodyInterfaceVa, long settingsVa);

    native private static long createConstraint(long bodyInterfaceVa,
            long settingsVa, int body1Id, int body2Id);

    native private static long createSoftBody(
            long bodyInterfaceVa, long settingsVa);

    native private static void deactivateBody(long bodyInterfaceVa, int bodyId);

    native private static void destroyBody(long bodyInterfaceVa, int bodyId);

    native private static void getAngularVelocity(
            long bodyInterfaceVa, int bodyId, FloatBuffer storeFloats);

    native private static int getBodyType(long bodyInterfaceVa, int bodyId);

    native private static void getCenterOfMassPosition(
            long bodyInterfaceVa, int bodyId, DoubleBuffer storeDoubles);

    native private static long getCenterOfMassTransform(
            long bodyInterfaceVa, int bodyId);

    native private static float getFriction(long bodyInterfaceVa, int bodyId);

    native private static float getGravityFactor(
            long bodyInterfaceVa, int bodyId);

    native private static void getLinearVelocity(
            long bodyInterfaceVa, int bodyId, FloatBuffer storeFloats);

    native private static int getMotionQuality(
            long bodyInterfaceVa, int bodyId);

    native private static int getMotionType(long bodyInterfaceVa, int bodyId);

    native private static int getObjectLayer(long bodyInterfaceVa, int bodyId);

    native private static void getPosition(
            long bodyInterfaceVa, int bodyId, DoubleBuffer storeDoubles);

    native private static void getPositionAndRotation(long bodyInterfaceVa,
            int bodyId, DoubleBuffer storeDoubles, FloatBuffer storeFloats);

    native private static float getRestitution(
            long bodyInterfaceVa, int bodyId);

    native private static void getRotation(
            long bodyInterfaceVa, int bodyId, FloatBuffer storeFloats);

    native private static long getShape(long bodyInterfaceVa, int bodyId);

    native private static long getUserData(long bodyInterfaceVa, int bodyId);

    native private static boolean isActive(long bodyInterfaceVa, int bodyId);

    native private static boolean isAdded(long bodyInterfaceVa, int bodyId);

    native private static boolean isSensor(long bodyInterfaceVa, int bodyId);

    native private static void moveKinematic(long bodyInterfaceVa,
            int bodyId, double xx, double yy, double zz,
            float qx, float qy, float qz, float qw, float deltaTime);

    native private static void notifyShapeChanged(long bodyInterfaceVa,
            int bodyId, float prevX, float prevY, float prevZ,
            boolean updateMassProperties, int activationOrdinal);

    native private static void removeBody(long bodyInterfaceVa, int bodyId);

    native private static void setAngularVelocity(
            long bodyInterfaceVa, int bodyId, float wx, float wy, float wz);

    native private static void setFriction(
            long bodyInterfaceVa, int bodyId, float friction);

    native private static void setGravityFactor(
            long bodyInterfaceVa, int bodyId, float factor);

    native private static void setIsSensor(
            long bodyInterfaceVa, int bodyId, boolean setting);

    native private static void setLinearAndAngularVelocity(
            long bodyInterfaceVa, int bodyId, float vx, float vy, float vz,
            float wx, float wy, float wz);

    native private static void setLinearVelocity(
            long bodyInterfaceVa, int bodyId, float vx, float vy, float vz);

    native private static void setMotionQuality(
            long bodyInterfaceVa, int bodyId, int ordinal);

    native private static void setMotionType(long bodyInterfaceVa,
            int bodyId, int motionOrdinal, int activationOrdinal);

    native private static void setObjectLayer(
            long bodyInterfaceVa, int bodyId, int layer);

    native private static void setPosition(long bodyInterfaceVa,
            int bodyId, double locX, double locY, double locZ, int ordinal);

    native private static void setPositionAndRotation(long bodyInterfaceVa,
            int bodyId, double locX, double locY, double locZ,
            float qx, float qy, float qz, float qw, int ordinal);

    native private static void setRestitution(
            long bodyInterfaceVa, int bodyId, float restitution);

    native private static void setShape(long bodyInterfaceVa, int bodyId,
            long shapeVa, boolean updateMassProperties, int ordinal);

    native private static void setUserData(
            long bodyInterfaceVa, int bodyId, long value);
}
