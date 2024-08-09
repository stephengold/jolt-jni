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

import com.github.stephengold.joltjni.enumerate.EMotionType;
import com.github.stephengold.joltjni.readonly.ConstAaBox;
import com.github.stephengold.joltjni.readonly.ConstBody;
import com.github.stephengold.joltjni.readonly.ConstBodyId;
import com.github.stephengold.joltjni.readonly.ConstShape;
import com.github.stephengold.joltjni.readonly.QuatArg;
import com.github.stephengold.joltjni.readonly.RVec3Arg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * An object with mass, position, and shape that can be added to a
 * {@code PhysicsSystem}. Bodies may be dynamic, kinematic, or static.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Body extends NonCopyable implements ConstBody {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a body with the specified native object assigned but not
     * owned.
     *
     * @param bodyVa the virtual address of the native object to assign (not
     * zero)
     */
    public Body(long bodyVa) {
        super(bodyVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Apply the specified angular impulse to the body.
     *
     * @param impulse the impulse vector (Newton.meter.seconds in system
     * coordinates, not null, unaffected)
     */
    public void addAngularImpulse(Vec3Arg impulse) {
        long bodyVa = va();
        float x = impulse.getX();
        float y = impulse.getY();
        float z = impulse.getZ();
        addAngularImpulse(bodyVa, x, y, z);
    }

    /**
     * Apply the specified force to the body's center of mass.
     *
     * @param force the force vector (Newtons in system coordinates, not null,
     * unaffected)
     */
    public void addForce(Vec3Arg force) {
        long bodyVa = va();
        float fx = force.getX();
        float fy = force.getY();
        float fz = force.getZ();
        addForce(bodyVa, fx, fy, fz);
    }

    /**
     * Apply the specified force at the specified location.
     *
     * @param force the force vector (not null, unaffected)
     * @param location where to apply the force (Newtons in system coordinates
     * not null, unaffected)
     */
    public void addForce(Vec3Arg force, RVec3Arg location) {
        long bodyVa = va();
        float fx = force.getX();
        float fy = force.getY();
        float fz = force.getZ();
        double locX = location.xx();
        double locY = location.yy();
        double locZ = location.zz();
        addForce(bodyVa, fx, fy, fz, locX, locY, locZ);
    }

    /**
     * Apply the specified impulse to the body's center of mass.
     *
     * @param impulse the impulse vector (kilogram.meters per second in system
     * coordinates, not null, unaffected)
     */
    public void addImpulse(Vec3Arg impulse) {
        long bodyVa = va();
        float jx = impulse.getX();
        float jy = impulse.getY();
        float jz = impulse.getZ();
        addImpulse(bodyVa, jx, jy, jz);
    }

    /**
     * Apply the specified impulse at the specified location.
     *
     * @param impulse the impulse vector (kilogram.meters per second in system
     * coordinates, not null, unaffected)
     * @param location where to apply the impulse (not null, unaffected)
     */
    public void addImpulse(Vec3Arg impulse, RVec3Arg location) {
        long bodyVa = va();
        float jx = impulse.getX();
        float jy = impulse.getY();
        float jz = impulse.getZ();
        double locX = location.xx();
        double locY = location.yy();
        double locZ = location.zz();
        addImpulse(bodyVa, jx, jy, jz, locX, locY, locZ);
    }

    /**
     * Apply the specified torque to the body.
     *
     * @param torque the torque vector (Newton.meters in system coordinates, not
     * null, unaffected)
     */
    public void addTorque(Vec3Arg torque) {
        long bodyVa = va();
        float x = torque.getX();
        float y = torque.getY();
        float z = torque.getZ();
        addTorque(bodyVa, x, y, z);
    }

    /**
     * Access the body's motion properties.
     *
     * @return a new JVM object with the pre-existing native object assigned, or
     * {@code null} if none
     */
    public MotionProperties getMotionProperties() {
        MotionProperties result;
        long bodyVa = va();
        if (isStatic(bodyVa)) {
            result = null;
        } else {
            long propertiesVa = getMotionProperties(bodyVa);
            result = new MotionProperties(propertiesVa);
        }

        return result;
    }

    /**
     * Reposition the body, assuming it's kinematic.
     *
     * @param location the desired location (in system coordinates, not null,
     * unaffected)
     * @param orientation the desired orientation (relative to the system axes,
     * not null, unaffected)
     * @param deltaTime time until the desired position is reached (in seconds,
     * &gt;0)
     */
    public void moveKinematic(
            RVec3Arg location, QuatArg orientation, float deltaTime) {
        long bodyVa = va();
        double xx = location.xx();
        double yy = location.yy();
        double zz = location.zz();
        float qw = orientation.getW();
        float qx = orientation.getX();
        float qy = orientation.getY();
        float qz = orientation.getZ();
        moveKinematic(bodyVa, xx, yy, zz, qx, qy, qz, qw, deltaTime);
    }

    /**
     * Reset the body's sleep timer. This does not activate the body, but allows
     * resetting the system that detects sleeping bodies.
     */
    public void resetSleepTimer() {
        long bodyVa = va();
        resetSleepTimer(bodyVa);
    }

    /**
     * Alter whether the body is allowed to fall asleep.
     *
     * @param allow true to allow, false to inhibit
     */
    public void setAllowSleeping(boolean allow) {
        long bodyVa = va();
        setAllowSleeping(bodyVa, allow);
    }

    /**
     * Directly alter the body's angular velocity.
     *
     * @param omega the desired angular velocity (not null, unaffected,
     * default=(0,0,0))
     */
    public void setAngularVelocity(Vec3Arg omega) {
        long bodyVa = va();
        float wx = omega.getX();
        float wy = omega.getY();
        float wz = omega.getZ();
        setAngularVelocity(bodyVa, wx, wy, wz);
    }

    /**
     * Alter the body's angular velocity within limits.
     *
     * @param omega the desired angular velocity (not null, unaffected,
     * default=(0,0,0))
     */
    public void setAngularVelocityClamped(Vec3Arg omega) {
        long bodyVa = va();
        float wx = omega.getX();
        float wy = omega.getY();
        float wz = omega.getZ();
        setAngularVelocityClamped(bodyVa, wx, wy, wz);
    }

    /**
     * Alter the body's friction ratio.
     *
     * @param friction the desired ratio (typically &ge;0 and &le;1,
     * default=0.2)
     */
    public void setFriction(float friction) {
        long bodyVa = va();
        setFriction(bodyVa, friction);
    }

    /**
     * Directly alter the body's linear velocity.
     *
     * @param velocity the desired linear velocity (in meters/second, not null,
     * unaffected, default=(0,0,0))
     */
    public void setLinearVelocity(Vec3Arg velocity) {
        long bodyVa = va();
        float vx = velocity.getX();
        float vy = velocity.getY();
        float vz = velocity.getZ();
        setLinearVelocity(bodyVa, vx, vy, vz);
    }

    /**
     * Alter the body's linear velocity within limits.
     *
     * @param velocity the desired linear velocity (in meters/second, not null,
     * unaffected, default=(0,0,0))
     */
    public void setLinearVelocityClamped(Vec3Arg velocity) {
        long bodyVa = va();
        float vx = velocity.getX();
        float vy = velocity.getY();
        float vz = velocity.getZ();
        setLinearVelocityClamped(bodyVa, vx, vy, vz);
    }

    /**
     * Alter the body's motion type.
     *
     * @param motionType the desired value (not null)
     */
    public void setMotionType(EMotionType motionType) {
        long bodyVa = va();
        int ordinal = motionType.ordinal();
        setMotionType(bodyVa, ordinal);
    }

    /**
     * Alter the body's restitution ratio.
     *
     * @param restitution the desired ratio (typically &ge;0 and &le;1,
     * default=0)
     */
    public void setRestitution(float restitution) {
        long bodyVa = va();
        setRestitution(bodyVa, restitution);
    }

    /**
     * Alter the body's user data.
     *
     * @param value the desired value (default=0)
     */
    public void setUserData(long value) {
        long bodyVa = va();
        setUserData(bodyVa, value);
    }

    /**
     * Create a dummy body that can be used to attach a constraint to the world.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public static Body sFixedToWorld() {
        long bodyVa = createFixedToWorld();
        Body result = new Body(bodyVa);

        return result;
    }
    // *************************************************************************
    // ConstBody methods

    /**
     * Test whether the body could be made kinematic or dynamic. The body is
     * unaffected.
     *
     * @return true if possible, otherwise false
     */
    @Override
    public boolean canBeKinematicOrDynamic() {
        long bodyVa = va();
        boolean result = canBeKinematicOrDynamic(bodyVa);

        return result;
    }

    /**
     * Return the net force acting on the body. The body is unaffected.
     *
     * @return a new force vector (Newtons in system coordinates)
     */
    @Override
    public Vec3 getAccumulatedForce() {
        long bodyVa = va();
        float x = getAccumulatedForceX(bodyVa);
        float y = getAccumulatedForceY(bodyVa);
        float z = getAccumulatedForceZ(bodyVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Return the net torque acting on the body. The body is unaffected.
     *
     * @return a new torque vector (Newton.meters in system coordinates)
     */
    @Override
    public Vec3 getAccumulatedTorque() {
        long bodyVa = va();
        float x = getAccumulatedTorqueX(bodyVa);
        float y = getAccumulatedTorqueY(bodyVa);
        float z = getAccumulatedTorqueZ(bodyVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Test whether the body is allowed to fall asleep. The body is unaffected.
     *
     * @return true if allowed, otherwise false
     */
    @Override
    public boolean getAllowSleeping() {
        long bodyVa = va();
        boolean result = getAllowSleeping(bodyVa);

        return result;
    }

    /**
     * Return the body's angular velocity. The body is unaffected.
     *
     * @return a new velocity vector (radians per second in system coordinates)
     */
    @Override
    public Vec3 getAngularVelocity() {
        long bodyVa = va();
        float wx = getAngularVelocityX(bodyVa);
        float wy = getAngularVelocityY(bodyVa);
        float wz = getAngularVelocityZ(bodyVa);
        Vec3 result = new Vec3(wx, wy, wz);

        return result;
    }

    /**
     * Convert the body to a {@code BodyCreationSettings} object.
     *
     * @return a new object
     */
    @Override
    public BodyCreationSettings getBodyCreationSettings() {
        long bodyVa = va();
        long bodySettingsVa = getBodyCreationSettings(bodyVa);
        BodyCreationSettings result
                = new BodyCreationSettings(bodySettingsVa, true);

        return result;
    }

    /**
     * Return the location of the body's center of mass (which might not
     * coincide with its origin). The body is unaffected.
     *
     * @return a new location vector (in system coordinates, all components
     * finite)
     */
    @Override
    public RVec3 getCenterOfMassPosition() {
        long bodyVa = va();

        double xx = getCenterOfMassPositionX(bodyVa);
        assert Double.isFinite(xx) : "xx = " + xx;

        double yy = getCenterOfMassPositionY(bodyVa);
        assert Double.isFinite(yy) : "yy = " + yy;

        double zz = getCenterOfMassPositionZ(bodyVa);
        assert Double.isFinite(zz) : "zz = " + zz;

        RVec3 result = new RVec3(xx, yy, zz);

        return result;
    }

    /**
     * Return the body's friction ratio. The body is unaffected.
     *
     * @return the ratio
     */
    @Override
    public float getFriction() {
        long bodyVa = va();
        float result = getFriction(bodyVa);
        return result;
    }

    /**
     * Access the body's ID for use with {@code BodyInterface}. The body is
     * unaffected.
     *
     * @return a new immutable JVM object with the pre-existing native object
     * assigned
     */
    @Override
    public ConstBodyId getId() {
        long bodyVa = va();
        long bodyIdVa = getId(bodyVa);
        ConstBodyId result = new BodyId(bodyIdVa, false);

        return result;
    }

    /**
     * Return the body's linear velocity. The body is unaffected.
     *
     * @return a new velocity vector (meters per second in system coordinates)
     */
    @Override
    public Vec3 getLinearVelocity() {
        long bodyVa = va();
        float vx = getLinearVelocityX(bodyVa);
        float vy = getLinearVelocityY(bodyVa);
        float vz = getLinearVelocityZ(bodyVa);
        Vec3 result = new Vec3(vx, vy, vz);

        return result;
    }

    /**
     * Return the body's motion type. The body is unaffected.
     *
     * @return an enum value (not null)
     */
    @Override
    public EMotionType getMotionType() {
        long bodyVa = va();
        int ordinal = getMotionType(bodyVa);
        EMotionType result = EMotionType.values()[ordinal];

        return result;
    }

    /**
     * Return the body's object layer. The body is unaffected.
     *
     * @return a layer index (&ge;0)
     */
    @Override
    public int getObjectLayer() {
        long bodyVa = va();
        int result = getObjectLayer(bodyVa);

        return result;
    }

    /**
     * Return the location of the body's origin (which not coincide with its
     * center of mass). The body is unaffected.
     *
     * @return a new location vector (in system coordinates, all components
     * finite)
     */
    @Override
    public RVec3 getPosition() {
        long bodyVa = va();

        double xx = getPositionX(bodyVa);
        assert Double.isFinite(xx) : "xx = " + xx;

        double yy = getPositionY(bodyVa);
        assert Double.isFinite(yy) : "yy = " + yy;

        double zz = getPositionZ(bodyVa);
        assert Double.isFinite(zz) : "zz = " + zz;

        RVec3 result = new RVec3(xx, yy, zz);

        return result;
    }

    /**
     * Return the body's restitution ratio. The body is unaffected.
     *
     * @return the value (typically &ge;0 and &le;1)
     */
    @Override
    public float getRestitution() {
        long bodyVa = va();
        float result = getRestitution(bodyVa);

        return result;
    }

    /**
     * Return the body's orientation. The body is unaffected.
     *
     * @return a new rotation quaternion (relative to the system axes)
     */
    @Override
    public Quat getRotation() {
        long bodyVa = va();
        float qx = getRotationX(bodyVa);
        float qy = getRotationY(bodyVa);
        float qz = getRotationZ(bodyVa);
        float qw = getRotationW(bodyVa);
        Quat result = new Quat(qx, qy, qz, qw);

        return result;
    }

    /**
     * Access the body's shape.
     *
     * @return a new immutable JVM object with the pre-existing native object
     * assigned, or {@code null} if none
     */
    @Override
    public ConstShape getShape() {
        long bodyVa = va();
        long shapeVa = getShape(bodyVa);
        ConstShape result = Shape.newShape(shapeVa);

        return result;
    }

    /**
     * Return the body's user data: can be used for anything. The body is
     * unaffected.
     *
     * @return the value
     */
    @Override
    public long getUserData() {
        long bodyVa = va();
        long result = getUserData(bodyVa);

        return result;
    }

    /**
     * Access the body's bounding box. The body is unaffected.
     *
     * @return a new immutable JVM object with the pre-existing native object
     * assigned
     */
    @Override
    public ConstAaBox getWorldSpaceBounds() {
        long bodyVa = va();
        long boxVa = getWorldSpaceBounds(bodyVa);
        ConstAaBox result = new AaBox(boxVa, false);

        return result;
    }

    /**
     * Test whether the body is deactivated. The body is unaffected.
     *
     * @return false if deactivated, otherwise true
     */
    @Override
    public boolean isActive() {
        long bodyVa = va();
        boolean result = isActive(bodyVa);

        return result;
    }

    /**
     * Test whether the body is dynamic. The body is unaffected.
     *
     * @return true if dynamic, otherwise false
     */
    @Override
    public boolean isDynamic() {
        long bodyVa = va();
        boolean result = isDynamic(bodyVa);

        return result;
    }

    /**
     * Test whether the body is kinematic. The body is unaffected.
     *
     * @return true if kinematic, otherwise false
     */
    @Override
    public boolean isKinematic() {
        long bodyVa = va();
        boolean result = isKinematic(bodyVa);

        return result;
    }

    /**
     * Test whether the body is a rigid body. The body is unaffected.
     *
     * @return true if rigid body, otherwise false
     */
    @Override
    public boolean isRigidBody() {
        long bodyVa = va();
        boolean result = isRigidBody(bodyVa);

        return result;
    }

    /**
     * Test whether the body is a sensor. The body is unaffected.
     *
     * @return true if a sensor, otherwise false
     */
    @Override
    public boolean isSensor() {
        long bodyVa = va();
        boolean result = isSensor(bodyVa);

        return result;
    }

    /**
     * Test whether the body is static. The body is unaffected.
     *
     * @return true if static, otherwise false
     */
    @Override
    public boolean isStatic() {
        long bodyVa = va();
        boolean result = isStatic(bodyVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static void addAngularImpulse(
            long bodyVa, float x, float y, float z);

    native private static void addForce(
            long bodyVa, float fx, float fy, float fz);

    native private static void addForce(long bodyVa, float fx, float fy,
            float fz, double locX, double locY, double locZ);

    native private static void addImpulse(
            long bodyVa, float jx, float jy, float jz);

    native private static void addImpulse(long bodyVa, float jx, float jy,
            float jz, double locX, double locY, double locZ);

    native private static void addTorque(
            long bodyVa, float x, float y, float z);

    native private static boolean canBeKinematicOrDynamic(long bodyVa);

    native private static long createFixedToWorld();

    native private static float getAccumulatedForceX(long bodyVa);

    native private static float getAccumulatedForceY(long bodyVa);

    native private static float getAccumulatedForceZ(long bodyVa);

    native private static float getAccumulatedTorqueX(long bodyVa);

    native private static float getAccumulatedTorqueY(long bodyVa);

    native private static float getAccumulatedTorqueZ(long bodyVa);

    native private static boolean getAllowSleeping(long bodyVa);

    native private static float getAngularVelocityX(long bodyVa);

    native private static float getAngularVelocityY(long bodyVa);

    native private static float getAngularVelocityZ(long bodyVa);

    native private static long getBodyCreationSettings(long bodyVa);

    native private static double getCenterOfMassPositionX(long bodyVa);

    native private static double getCenterOfMassPositionY(long bodyVa);

    native private static double getCenterOfMassPositionZ(long bodyVa);

    native private static float getFriction(long bodyVa);

    native private static long getId(long bodyVa);

    native private static float getLinearVelocityX(long bodyVa);

    native private static float getLinearVelocityY(long bodyVa);

    native private static float getLinearVelocityZ(long bodyVa);

    native private static long getMotionProperties(long bodyVa);

    native private static int getMotionType(long bodyVa);

    native private static int getObjectLayer(long bodyVa);

    native private static double getPositionX(long bodyVa);

    native private static double getPositionY(long bodyVa);

    native private static double getPositionZ(long bodyVa);

    native private static float getRestitution(long bodyVa);

    native private static float getRotationX(long bodyVa);

    native private static float getRotationY(long bodyVa);

    native private static float getRotationZ(long bodyVa);

    native private static float getRotationW(long bodyVa);

    native private static long getShape(long bodyVa);

    native private static long getUserData(long bodyVa);

    native private static long getWorldSpaceBounds(long bodyVa);

    native private static boolean isActive(long bodyVa);

    native private static boolean isDynamic(long bodyVa);

    native private static boolean isKinematic(long bodyVa);

    native private static boolean isRigidBody(long bodyVa);

    native private static boolean isSensor(long bodyVa);

    native private static boolean isStatic(long bodyVa);

    native private static void moveKinematic(long bodyVa, double xx, double yy,
            double zz, float qx, float qy, float qz, float qw, float deltaTime);

    native private static void resetSleepTimer(long bodyVa);

    native private static void setAllowSleeping(long bodyVa, boolean allow);

    native private static void setAngularVelocity(
            long bodyVa, float wx, float wy, float wz);

    native private static void setAngularVelocityClamped(
            long bodyVa, float wx, float wy, float wz);

    native private static void setFriction(long bodyVa, float friction);

    native private static void setLinearVelocity(
            long bodyVa, float vx, float vy, float vz);

    native private static void setLinearVelocityClamped(
            long bodyVa, float vx, float vy, float vz);

    native private static void setMotionType(long bodyVa, int ordinal);

    native private static void setRestitution(long bodyVa, float restitution);

    native private static void setUserData(long bodyVa, long value);
}
