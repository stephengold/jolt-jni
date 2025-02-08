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
import com.github.stephengold.joltjni.readonly.ConstBodyCreationSettings;
import com.github.stephengold.joltjni.readonly.ConstBodyId;
import com.github.stephengold.joltjni.readonly.ConstShape;
import com.github.stephengold.joltjni.readonly.ConstSoftBodyCreationSettings;
import com.github.stephengold.joltjni.readonly.QuatArg;
import com.github.stephengold.joltjni.readonly.RVec3Arg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * An interface to a {@code PhysicsSystem} that's used to create, add, modify,
 * query, remove, and delete bodies.
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
     * Activate the specified body.
     *
     * @param bodyId the ID of the body to activate (not null, unaffected)
     */
    public void activateBody(ConstBodyId bodyId) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        activateBody(bodyInterfaceVa, bodyIdVa);
    }

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
     * Add the specified body to the physics system.
     *
     * @param bodyId the ID of the body to add (not null, unaffected)
     * @param activation whether to activate the body (not null)
     */
    public void addBody(ConstBodyId bodyId, EActivation activation) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        int activationOrdinal = activation.ordinal();
        addBody(bodyInterfaceVa, bodyIdVa, activationOrdinal);
    }

    /**
     * Apply the specified force to the specified body's center of mass.
     *
     * @param bodyId the ID of the body (not null, unaffected)
     * @param force the force vector (not null, unaffected)
     */
    public void addForce(ConstBodyId bodyId, Vec3Arg force) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        float fx = force.getX();
        float fy = force.getY();
        float fz = force.getZ();
        addForce(bodyInterfaceVa, bodyIdVa, fx, fy, fz);
    }

    /**
     * Apply the specified force to the specified body at the specified
     * location.
     *
     * @param bodyId the ID of the body (not null, unaffected)
     * @param force the force vector (not null, unaffected)
     * @param location where to apply the force (not null, unaffected)
     */
    public void addForce(ConstBodyId bodyId, Vec3Arg force, RVec3Arg location) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        float fx = force.getX();
        float fy = force.getY();
        float fz = force.getZ();
        double locX = location.xx();
        double locY = location.yy();
        double locZ = location.zz();
        addForce(bodyInterfaceVa, bodyIdVa, fx, fy, fz, locX, locY, locZ);
    }

    /**
     * Apply the specified impulse to the specified body's center of mass.
     *
     * @param bodyId the ID of the body (not null, unaffected)
     * @param impulse the impulse vector (not null, unaffected)
     */
    public void addImpulse(ConstBodyId bodyId, Vec3Arg impulse) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        float jx = impulse.getX();
        float jy = impulse.getY();
        float jz = impulse.getZ();
        addImpulse(bodyInterfaceVa, bodyIdVa, jx, jy, jz);
    }

    /**
     * Apply the specified impulse to the specified body at the specified
     * location.
     *
     * @param bodyId the ID of the body (not null, unaffected)
     * @param impulse the impulse vector (not null, unaffected)
     * @param location where to apply the impulse (not null, unaffected)
     */
    public void addImpulse(ConstBodyId bodyId, Vec3Arg impulse,
            RVec3Arg location) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        float jx = impulse.getX();
        float jy = impulse.getY();
        float jz = impulse.getZ();
        double locX = location.xx();
        double locY = location.yy();
        double locZ = location.zz();
        addImpulse(bodyInterfaceVa, bodyIdVa, jx, jy, jz, locX, locY, locZ);
    }

    /**
     * Apply the specified torque to the specified body.
     *
     * @param bodyId the ID of the body (not null, unaffected)
     * @param torque the torque vector (not null, unaffected)
     */
    public void addTorque(ConstBodyId bodyId, Vec3Arg torque) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        float x = torque.getX();
        float y = torque.getY();
        float z = torque.getZ();
        addTorque(bodyInterfaceVa, bodyIdVa, x, y, z);
    }

    /**
     * Create a rigid body and add it to the physics system.
     *
     * @param settings the settings to use (not null, unaffected)
     * @param activationMode whether to activate the body (not null)
     * @return the ID of the created body, or an invalid ID when out of bodies
     */
    public BodyId createAndAddBody(
            BodyCreationSettings settings, EActivation activationMode) {
        Body body = createBody(settings);
        BodyId result = body.getId();
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
    public BodyId createAndAddSoftBody(ConstSoftBodyCreationSettings settings,
            EActivation activationMode) {
        Body body = createSoftBody(settings);
        BodyId result = body.getId();
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
     * @param body1Id the ID of the first body (not null, unaffected)
     * @param body2Id the ID of the 2nd body (not null, unaffected)
     * @return the new constraint
     */
    public TwoBodyConstraint createConstraint(
            TwoBodyConstraintSettings settings,
            ConstBodyId body1Id, ConstBodyId body2Id) {
        long bodyInterfaceVa = va();
        long settingsVa = settings.va();
        long body1IdVa = body1Id.targetVa();
        long body2IdVa = body2Id.targetVa();
        long constraintVa = createConstraint(
                bodyInterfaceVa, settingsVa, body1IdVa, body2IdVa);
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
     * @param bodyId the ID of the body to deactivate (not null, unaffected)
     */
    public void deactivateBody(ConstBodyId bodyId) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        deactivateBody(bodyInterfaceVa, bodyIdVa);
    }

    /**
     * Destroy the specified body.
     *
     * @param bodyId the ID of the body to destroy (not null)
     */
    public void destroyBody(ConstBodyId bodyId) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        destroyBody(bodyInterfaceVa, bodyIdVa);
    }

    /**
     * Return the angular velocity of the specified body.
     *
     * @param bodyId the ID of the body (not null)
     * @return a new velocity vector (radians per second in physics-system
     * coordinates)
     */
    public Vec3 getAngularVelocity(ConstBodyId bodyId) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        float x = getAngularVelocityX(bodyInterfaceVa, bodyIdVa);
        float y = getAngularVelocityY(bodyInterfaceVa, bodyIdVa);
        float z = getAngularVelocityZ(bodyInterfaceVa, bodyIdVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Return the type of the specified body.
     *
     * @param bodyId the ID of the body to query (not null)
     * @return an enum value (not null)
     */
    public EBodyType getBodyType(ConstBodyId bodyId) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        int ordinal = getBodyType(bodyInterfaceVa, bodyIdVa);
        EBodyType result = EBodyType.values()[ordinal];

        return result;
    }

    /**
     * Locate the center of mass of the specified body.
     *
     * @param bodyId the ID of the body to locate (not null)
     * @return a new location vector (in physics-system coordinates)
     */
    public RVec3 getCenterOfMassPosition(ConstBodyId bodyId) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        double xx = getCenterOfMassPositionX(bodyInterfaceVa, bodyIdVa);
        double yy = getCenterOfMassPositionY(bodyInterfaceVa, bodyIdVa);
        double zz = getCenterOfMassPositionZ(bodyInterfaceVa, bodyIdVa);
        RVec3 result = new RVec3(xx, yy, zz);

        return result;
    }

    /**
     * Return the center-of-mass transform of the specified body.
     *
     * @param bodyId the ID of the body to locate (not null)
     * @return a new transform matrix (relative to physics-system coordinates)
     */
    public RMat44 getCenterOfMassTransform(ConstBodyId bodyId) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        long matrixVa = getCenterOfMassTransform(bodyInterfaceVa, bodyIdVa);
        RMat44 result = new RMat44(matrixVa, true);

        return result;
    }

    /**
     * Return the friction ratio of the specified body.
     *
     * @param bodyId the ID of the body (not null)
     * @return the ratio
     */
    public float getFriction(ConstBodyId bodyId) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        float result = getFriction(bodyInterfaceVa, bodyIdVa);
        return result;
    }

    /**
     * Return the gravity factor of the specified body.
     *
     * @param bodyId the ID of the body (not null)
     * @return the factor
     */
    public float getGravityFactor(ConstBodyId bodyId) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        float result = getGravityFactor(bodyInterfaceVa, bodyIdVa);

        return result;
    }

    /**
     * Return the linear velocity of the specified body.
     *
     * @param bodyId the ID of the body (not null)
     * @return a new velocity vector (meters per second in physics-system
     * coordinates)
     */
    public Vec3 getLinearVelocity(ConstBodyId bodyId) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        float x = getLinearVelocityX(bodyInterfaceVa, bodyIdVa);
        float y = getLinearVelocityY(bodyInterfaceVa, bodyIdVa);
        float z = getLinearVelocityZ(bodyInterfaceVa, bodyIdVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Return the motion quality
     *
     * @param bodyId the ID of the body to query (not null, unaffected)
     * @return an enum value (not null)
     */
    public EMotionQuality getMotionQuality(ConstBodyId bodyId) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        int ordinal = getMotionQuality(bodyInterfaceVa, bodyIdVa);
        EMotionQuality result = EMotionQuality.values()[ordinal];

        return result;
    }

    /**
     * Return the motion type of the specified body.
     *
     * @param bodyId the ID of the body to query (not null, unaffected)
     * @return an enum value (not null)
     */
    public EMotionType getMotionType(ConstBodyId bodyId) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        int ordinal = getMotionType(bodyInterfaceVa, bodyIdVa);
        EMotionType result = EMotionType.values()[ordinal];

        return result;
    }

    /**
     * Return the object layer of the specified body.
     *
     * @param bodyId the ID of the body to query (not null, unaffected)
     * @return an object-layer index
     */
    public int getObjectLayer(ConstBodyId bodyId) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        int result = getObjectLayer(bodyInterfaceVa, bodyIdVa);

        return result;
    }

    /**
     * Locate the specified body.
     *
     * @param bodyId the ID of the body to locate (not null, unaffected)
     * @return a new location vector (in physics-system coordinates)
     */
    public RVec3 getPosition(ConstBodyId bodyId) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        double xx = getPositionX(bodyInterfaceVa, bodyIdVa);
        double yy = getPositionY(bodyInterfaceVa, bodyIdVa);
        double zz = getPositionZ(bodyInterfaceVa, bodyIdVa);
        RVec3 result = new RVec3(xx, yy, zz);

        return result;
    }

    /**
     * Copy the location and orientation of the specified body.
     *
     * @param bodyId the ID of the body to locate (not null, unaffected)
     * @param storeLocation storage for the location (not null, modified)
     * @param storeOrientation storage for the orientation (not null, modified)
     */
    public void getPositionAndRotation(
            ConstBodyId bodyId, RVec3 storeLocation, Quat storeOrientation) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();

        double xx = getPositionX(bodyInterfaceVa, bodyIdVa);
        double yy = getPositionY(bodyInterfaceVa, bodyIdVa);
        double zz = getPositionZ(bodyInterfaceVa, bodyIdVa);
        storeLocation.set(xx, yy, zz);

        float qw = getRotationW(bodyInterfaceVa, bodyIdVa);
        float qx = getRotationX(bodyInterfaceVa, bodyIdVa);
        float qy = getRotationY(bodyInterfaceVa, bodyIdVa);
        float qz = getRotationZ(bodyInterfaceVa, bodyIdVa);
        storeOrientation.set(qx, qy, qz, qw);
    }

    /**
     * Return the restitution ratio of the specified body.
     *
     * @param bodyId the ID of the body to query (not null)
     * @return the value (typically &ge;0 and &le;1)
     */
    public float getRestitution(ConstBodyId bodyId) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        float result = getRestitution(bodyInterfaceVa, bodyIdVa);

        return result;
    }

    /**
     * Copy the orientation of the specified body.
     *
     * @param bodyId the ID of the body (not null, unaffected)
     * @return a new rotation quaternion
     */
    public Quat getRotation(ConstBodyId bodyId) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();

        float qw = getRotationW(bodyInterfaceVa, bodyIdVa);
        float qx = getRotationX(bodyInterfaceVa, bodyIdVa);
        float qy = getRotationY(bodyInterfaceVa, bodyIdVa);
        float qz = getRotationZ(bodyInterfaceVa, bodyIdVa);
        Quat result = new Quat(qx, qy, qz, qw);

        return result;
    }

    /**
     * Access the body's shape.
     *
     * @param bodyId the ID of the body (not null, unaffected)
     * @return a new reference
     */
    public ShapeRefC getShape(ConstBodyId bodyId) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        long shapeRefVa = getShape(bodyInterfaceVa, bodyIdVa);
        ShapeRefC result = new ShapeRefC(shapeRefVa, true);

        return result;
    }

    /**
     * Access the underlying {@code PhysicsSystem}.
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
     * @param bodyId the ID of the body (not null, unaffected)
     * @return the value
     */
    public long getUserData(ConstBodyId bodyId) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        long result = getUserData(bodyInterfaceVa, bodyIdVa);

        return result;
    }

    /**
     * Test whether the specified body is active.
     *
     * @param bodyId the ID of the body to test (not null, unaffected)
     * @return {@code true} if active, otherwise {@code false}
     */
    public boolean isActive(ConstBodyId bodyId) {
        long bodyIdVa = bodyId.targetVa();
        boolean result = isActive(va(), bodyIdVa);
        return result;
    }

    /**
     * Test whether the specified body is added to the system.
     *
     * @param bodyId the ID of the body to test (not null, unaffected)
     * @return {@code true} if added, otherwise {@code false}
     */
    public boolean isAdded(ConstBodyId bodyId) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        boolean result = isAdded(bodyInterfaceVa, bodyIdVa);
        return result;
    }

    /**
     * Reposition the specified body, assuming it's kinematic.
     *
     * @param bodyId the ID of the body to reposition (not null, unaffected)
     * @param location the desired location (in physics-system coordinates, not
     * null, unaffected)
     * @param orientation the desired orientation (relative to the
     * physics-system axes, not null, unaffected)
     * @param deltaTime time until the desired position is reached (in seconds,
     * &gt;0)
     */
    public void moveKinematic(ConstBodyId bodyId,
            RVec3Arg location, QuatArg orientation, float deltaTime) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        double xx = location.xx();
        double yy = location.yy();
        double zz = location.zz();
        float qw = orientation.getW();
        float qx = orientation.getX();
        float qy = orientation.getY();
        float qz = orientation.getZ();
        moveKinematic(bodyInterfaceVa, bodyIdVa, xx, yy, zz,
                qx, qy, qz, qw, deltaTime);
    }

    /**
     * Notify all systems that the shape of the specified body has changed.
     *
     * @param bodyId the ID of the affected body (not null, unaffected)
     * @param prevCom center of mass prior to the change (not null, unaffected)
     * @param updateMassProperties {@code true} to recalculate mass and inertia
     * @param activation whether to activate the body (not null)
     */
    public void notifyShapeChanged(ConstBodyId bodyId, Vec3Arg prevCom,
            boolean updateMassProperties, EActivation activation) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        int activationOrdinal = activation.ordinal();
        notifyShapeChanged(bodyInterfaceVa, bodyIdVa, prevCom.getX(),
                prevCom.getY(), prevCom.getZ(), updateMassProperties,
                activationOrdinal);
    }

    /**
     * Remove the specified body from the physics system.
     *
     * @param bodyId the ID of the body to remove (not null, unaffected)
     */
    public void removeBody(ConstBodyId bodyId) {
        long bodyIdVa = bodyId.targetVa();
        removeBody(va(), bodyIdVa);
    }

    /**
     * Alter the linear velocity of the specified body.
     *
     * @param bodyId the ID of the body to modify (not null, unaffected)
     * @param omega the desired rates (not null, unaffected)
     */
    public void setAngularVelocity(ConstBodyId bodyId, Vec3Arg omega) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        setAngularVelocity(bodyInterfaceVa, bodyIdVa,
                omega.getX(), omega.getY(), omega.getZ());
    }

    /**
     * Alter the friction ratio of the specified body.
     *
     * @param bodyId the ID of the body to modify (not null, unaffected)
     * @param friction the desired ratio (typically &ge;0 and &le;1,
     * default=0.2)
     */
    public void setFriction(ConstBodyId bodyId, float friction) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        setFriction(bodyInterfaceVa, bodyIdVa, friction);
    }

    /**
     * Alter the gravity factor of the specified body.
     *
     * @param bodyId the ID of the body to modify (not null, unaffected)
     * @param factor the desired factor (default=1)
     */
    public void setGravityFactor(ConstBodyId bodyId, float factor) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        setGravityFactor(bodyInterfaceVa, bodyIdVa, factor);
    }

    /**
     * Alter the linear and angular velocities of the specified body.
     *
     * @param bodyId the ID of the body to modify (not null, unaffected)
     * @param linearVelocity the desired linear velocity of body's the center of
     * mass (not null, unaffected)
     * @param angularVelocity the desired angular velocity (not null,
     * unaffected)
     */
    public void setLinearAndAngularVelocity(ConstBodyId bodyId,
            Vec3Arg linearVelocity, Vec3Arg angularVelocity) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        float vx = linearVelocity.getX();
        float vy = linearVelocity.getY();
        float vz = linearVelocity.getZ();
        float wx = angularVelocity.getX();
        float wy = angularVelocity.getY();
        float wz = angularVelocity.getZ();
        setLinearAndAngularVelocity(
                bodyInterfaceVa, bodyIdVa, vx, vy, vz, wx, wy, wz);
    }

    /**
     * Alter the linear velocity of the specified body.
     *
     * @param bodyId the ID of the body to modify (not null, unaffected)
     * @param velocity the desired velocity (not null, unaffected)
     */
    public void setLinearVelocity(ConstBodyId bodyId, Vec3Arg velocity) {
        long bodyIdVa = bodyId.targetVa();
        setLinearVelocity(va(), bodyIdVa,
                velocity.getX(), velocity.getY(), velocity.getZ());
    }

    /**
     * Alter the motion quality of the specified body.
     *
     * @param bodyId the ID of the body to modify (not null, unaffected)
     * @param quality the desired level of quality (not null)
     */
    public void setMotionQuality(ConstBodyId bodyId, EMotionQuality quality) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        int ordinal = quality.ordinal();
        setMotionQuality(bodyInterfaceVa, bodyIdVa, ordinal);
    }

    /**
     * Alter the motion type of the specified body.
     *
     * @param bodyId the ID of the body to modify (not null, unaffected)
     * @param motionType the desired motion type (not null)
     * @param activationMode whether to activate the body (not null)
     */
    public void setMotionType(ConstBodyId bodyId, EMotionType motionType,
            EActivation activationMode) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        int motionOrdinal = motionType.ordinal();
        int activationOrdinal = activationMode.ordinal();
        setMotionType(
                bodyInterfaceVa, bodyIdVa, motionOrdinal, activationOrdinal);
    }

    /**
     * Alter the object layer of the specified body.
     *
     * @param bodyId the ID of the body to modify (not null, unaffected)
     * @param layer the index of the desired object layer
     */
    public void setObjectLayer(ConstBodyId bodyId, int layer) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        setObjectLayer(bodyInterfaceVa, bodyIdVa, layer);
    }

    /**
     * Alter the location of the specified body.
     *
     * @param bodyId the ID of the body to modify (not null, unaffected)
     * @param location the desired location (not null, unaffected)
     * @param activationMode whether to activate the body (not null)
     */
    public void setPosition(
            ConstBodyId bodyId, RVec3Arg location, EActivation activationMode) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        double locX = location.xx();
        double locY = location.yy();
        double locZ = location.zz();
        int ordinal = activationMode.ordinal();
        setPosition(bodyInterfaceVa, bodyIdVa, locX, locY, locZ, ordinal);
    }

    /**
     * Alter the location and orientation of the specified body.
     *
     * @param bodyId the ID of the body to modify (not null, unaffected)
     * @param location the desired location (not null, unaffected)
     * @param orientation the desired orientation (not null, unaffected)
     * @param activationMode whether to activate the body (not null)
     */
    public void setPositionAndRotation(ConstBodyId bodyId, RVec3Arg location,
            QuatArg orientation, EActivation activationMode) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        double locX = location.xx();
        double locY = location.yy();
        double locZ = location.zz();
        float qw = orientation.getW();
        float qx = orientation.getX();
        float qy = orientation.getY();
        float qz = orientation.getZ();
        int ordinal = activationMode.ordinal();
        setPositionAndRotation(bodyInterfaceVa, bodyIdVa, locX, locY, locZ,
                qx, qy, qz, qw, ordinal);
    }

    /**
     * Alter the restitution ratio of the specified body.
     *
     * @param bodyId the ID of the body to modify (not null, unaffected)
     * @param restitution the desired ratio (typically &ge;0 and &le;1,
     * default=0)
     */
    public void setRestitution(ConstBodyId bodyId, float restitution) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        setRestitution(bodyInterfaceVa, bodyIdVa, restitution);
    }

    /**
     * Replace the shape of the specified body.
     *
     * @param bodyId the ID of the body to modify (not null, unaffected)
     * @param shape the desired shape to apply (not null)
     * @param updateMassProperties if {@code true}, recalculate the body's mass
     * and inertia, otherwise don't recalculate
     * @param activation whether to activate the body (not null)
     */
    public void setShape(ConstBodyId bodyId, ConstShape shape,
            boolean updateMassProperties, EActivation activation) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        long shapeVa = shape.targetVa();
        int ordinal = activation.ordinal();
        setShape(bodyInterfaceVa, bodyIdVa, shapeVa, updateMassProperties,
                ordinal);
    }

    /**
     * Alter the user data of the specified body.
     *
     * @param bodyId the ID of the body to modify (not null, unaffected)
     * @param value the desired value
     */
    public void setUserData(ConstBodyId bodyId, long value) {
        long bodyInterfaceVa = va();
        long bodyIdVa = bodyId.targetVa();
        setUserData(bodyInterfaceVa, bodyIdVa, value);
    }
    // *************************************************************************
    // native private methods

    native private static void activateBody(
            long bodyInterfaceVa, long bodyIdVa);

    native private static void activateBodiesInAaBox(long bodyInterfaceVa,
            long boxVa, long bplFilterVa, long olFilterVa);

    native private static void addBody(
            long bodyInterfaceVa, long bodyIdVa, int activationOrdinal);

    native private static void addForce(
            long bodyInterfaceVa, long bodyIdVa, float fx, float fy, float fz);

    native private static void addForce(
            long bodyInterfaceVa, long bodyIdVa, float fx, float fy, float fz,
            double locX, double locY, double locZ);

    native private static void addImpulse(
            long bodyInterfaceVa, long bodyIdVa, float jx, float jy, float jz);

    native private static void addImpulse(
            long bodyInterfaceVa, long bodyIdVa, float jx, float jy, float jz,
            double locX, double locY, double locZ);

    native private static void addTorque(
            long bodyInterfaceVa, long bodyIdVa, float x, float y, float z);

    native private static long createBody(
            long bodyInterfaceVa, long settingsVa);

    native private static long createConstraint(long bodyInterfaceVa,
            long settingsVa, long body1IdVa, long body2IdVa);

    native private static long createSoftBody(
            long bodyInterfaceVa, long settingsVa);

    native private static void deactivateBody(
            long bodyInterfaceVa, long bodyIdVa);

    native private static void destroyBody(long bodyInterfaceVa, long bodyIdVa);

    native private static float getAngularVelocityX(
            long bodyInterfaceVa, long bodyIdVa);

    native private static float getAngularVelocityY(
            long bodyInterfaceVa, long bodyIdVa);

    native private static float getAngularVelocityZ(
            long bodyInterfaceVa, long bodyIdVa);

    native private static int getBodyType(long bodyInterfaceVa, long bodyIdVa);

    native private static double getCenterOfMassPositionX(
            long bodyInterfaceVa, long bodyIdVa);

    native private static double getCenterOfMassPositionY(
            long bodyInterfaceVa, long bodyIdVa);

    native private static double getCenterOfMassPositionZ(
            long bodyInterfaceVa, long bodyIdVa);

    native private static long getCenterOfMassTransform(
            long bodyInterfaceVa, long bodyIdVa);

    native private static float getFriction(
            long bodyInterfaceVa, long bodyIdVa);

    native private static float getGravityFactor(
            long bodyInterfaceVa, long bodyIdVa);

    native private static float getLinearVelocityX(
            long bodyInterfaceVa, long bodyIdVa);

    native private static float getLinearVelocityY(
            long bodyInterfaceVa, long bodyIdVa);

    native private static float getLinearVelocityZ(
            long bodyInterfaceVa, long bodyIdVa);

    native private static int getMotionQuality(
            long bodyInterfaceVa, long bodyIdVa);

    native private static int getMotionType(
            long bodyInterfaceVa, long bodyIdVa);

    native private static int getObjectLayer(
            long bodyInterfaceVa, long bodyIdVa);

    native private static double getPositionX(
            long bodyInterfaceVa, long bodyIdVa);

    native private static double getPositionY(
            long bodyInterfaceVa, long bodyIdVa);

    native private static double getPositionZ(
            long bodyInterfaceVa, long bodyIdVa);

    native private static float getRestitution(
            long bodyInterfaceVa, long bodyIdVa);

    native private static float getRotationW(
            long bodyInterfaceVa, long bodyIdVa);

    native private static float getRotationX(
            long bodyInterfaceVa, long bodyIdVa);

    native private static float getRotationY(
            long bodyInterfaceVa, long bodyIdVa);

    native private static float getRotationZ(
            long bodyInterfaceVa, long bodyIdVa);

    native private static long getShape(long bodyInterfaceVa, long bodyIdVa);

    native private static long getUserData(long bodyInterfaceVa, long bodyIdVa);

    native private static boolean isActive(long bodyInterfaceVa, long bodyIdVa);

    native private static boolean isAdded(long bodyInterfaceVa, long bodyIdVa);

    native private static void moveKinematic(long bodyInterfaceVa,
            long bodyIdVa, double xx, double yy, double zz,
            float qx, float qy, float qz, float qw, float deltaTime);

    native private static void notifyShapeChanged(long bodyInterfaceVa,
            long bodyIdVa, float prevX, float prevY, float prevZ,
            boolean updateMassProperties, int activationOrdinal);

    native private static void removeBody(long bodyInterfaceVa, long bodyIdVa);

    native private static void setLinearAndAngularVelocity(
            long bodyInterfaceVa, long bodyIdVa, float vx, float vy, float vz,
            float wx, float wy, float wz);

    native private static void setAngularVelocity(
            long bodyInterfaceVa, long bodyIdVa, float wx, float wy, float wz);

    native private static void setFriction(
            long bodyInterfaceVa, long bodyIdVa, float friction);

    native private static void setGravityFactor(
            long bodyInterfaceVa, long bodyIdVa, float factor);

    native private static void setLinearVelocity(
            long bodyInterfaceVa, long bodyIdVa, float vx, float vy, float vz);

    native private static void setMotionQuality(
            long bodyInterfaceVa, long bodyIdVa, int ordinal);

    native private static void setMotionType(long bodyInterfaceVa,
            long bodyIdVa, int motionOrdinal, int activationOrdinal);

    native private static void setObjectLayer(
            long bodyInterfaceVa, long bodyIdVa, int layer);

    native private static void setPosition(long bodyInterfaceVa,
            long bodyIdVa, double locX, double locY, double locZ, int ordinal);

    native private static void setPositionAndRotation(long bodyInterfaceVa,
            long bodyIdVa, double locX, double locY, double locZ,
            float qx, float qy, float qz, float qw, int ordinal);

    native private static void setRestitution(
            long bodyInterfaceVa, long bodyIdVa, float restitution);

    native private static void setShape(long bodyInterfaceVa, long bodyIdVa,
            long shapeVa, boolean updateMassProperties, int ordinal);

    native private static void setUserData(
            long bodyInterfaceVa, long bodyIdVa, long value);
}
