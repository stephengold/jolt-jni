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

/**
 * An object with mass, position, and shape that can be added to a
 * {@code PhysicsSystem}. Bodies may be dynamic, kinematic, or static.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Body extends NonCopyable {
    // *************************************************************************
    // constructors

    Body(long bodyVa) {
        super(bodyVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the net force acting on the body.
     *
     * @return a new vector
     */
    public Vec3 getAccumulatedForce() {
        long bodyVa = va();
        float x = getAccumulatedForceX(bodyVa);
        float y = getAccumulatedForceY(bodyVa);
        float z = getAccumulatedForceZ(bodyVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Return the net torque acting on the body.
     *
     * @return a new vector
     */
    public Vec3 getAccumulatedTorque() {
        long bodyVa = va();
        float x = getAccumulatedTorqueX(bodyVa);
        float y = getAccumulatedTorqueY(bodyVa);
        float z = getAccumulatedTorqueZ(bodyVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Return the body's angular velocity.
     *
     * @return a new vector in physics-system coordinates
     */
    public Vec3 getAngularVelocity() {
        long bodyVa = va();
        float wx = getAngularVelocityX(bodyVa);
        float wy = getAngularVelocityY(bodyVa);
        float wz = getAngularVelocityZ(bodyVa);
        Vec3 result = new Vec3(wx, wy, wz);

        return result;
    }

    /**
     * Return the location of the body's center of mass (which might differ from
     * its origin).
     *
     * @return a new vector in physics-system coordinates (all components
     * finite)
     */
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
     * Return the body's friction ratio.
     *
     * @return the ratio
     */
    public float getFriction() {
        long bodyVa = va();
        float result = getFriction(bodyVa);
        return result;
    }

    /**
     * Acquire the body's ID for use with {@code BodyInterface}.
     *
     * @return the value
     */
    public BodyId getId() {
        long bodyVa = va();
        long bodyIdVa = getId(bodyVa);
        BodyId result = new BodyId(bodyIdVa);

        return result;
    }

    /**
     * Return the body's linear velocity.
     *
     * @return a new vector in physics-system coordinates
     */
    public Vec3 getLinearVelocity() {
        long bodyVa = va();
        float vx = getLinearVelocityX(bodyVa);
        float vy = getLinearVelocityY(bodyVa);
        float vz = getLinearVelocityZ(bodyVa);
        Vec3 result = new Vec3(vx, vy, vz);

        return result;
    }

    /**
     * Acquire the body's motion properties.
     *
     * @return a new instance, or null if none
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
     * Return the body's motion type.
     *
     * @return an enum value (not null)
     */
    public EMotionType getMotionType() {
        long bodyVa = va();
        int ordinal = getMotionType(bodyVa);
        EMotionType result = EMotionType.values()[ordinal];

        return result;
    }

    /**
     * Return the location of the body's origin (which might differ from its
     * center of mass).
     *
     * @return a new vector in physics-system coordinates (all components
     * finite)
     */
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
     * Return the body's restitution ratio.
     *
     * @return the value (typically &ge;0 and &le;1)
     */
    public float getRestitution() {
        long bodyVa = va();
        float result = getRestitution(bodyVa);

        return result;
    }

    /**
     * Return the body's orientation relative to the physics-system axes.
     *
     * @return a new quaternion
     */
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
     * Test whether the body is deactivated.
     *
     * @return false if deactivated, otherwise true
     */
    public boolean isActive() {
        long bodyVa = va();
        boolean result = isActive(bodyVa);

        return result;
    }

    /**
     * Test whether the body is static.
     *
     * @return true if static, otherwise false
     */
    public boolean isStatic() {
        long bodyVa = va();
        boolean result = isStatic(bodyVa);

        return result;
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
     * @param velocity the desired linear velocity (not null, unaffected,
     * default=(0,0,0))
     */
    public void setLinearVelocity(Vec3Arg velocity) {
        long bodyVa = va();
        float vx = velocity.getX();
        float vy = velocity.getY();
        float vz = velocity.getZ();
        setLinearVelocity(bodyVa, vx, vy, vz);
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
    // *************************************************************************
    // native private methods

    native private static float getAccumulatedForceX(long bodyVa);

    native private static float getAccumulatedForceY(long bodyVa);

    native private static float getAccumulatedForceZ(long bodyVa);

    native private static float getAccumulatedTorqueX(long bodyVa);

    native private static float getAccumulatedTorqueY(long bodyVa);

    native private static float getAccumulatedTorqueZ(long bodyVa);

    native private static float getAngularVelocityX(long bodyVa);

    native private static float getAngularVelocityY(long bodyVa);

    native private static float getAngularVelocityZ(long bodyVa);

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

    native private static double getPositionX(long bodyVa);

    native private static double getPositionY(long bodyVa);

    native private static double getPositionZ(long bodyVa);

    native private static float getRestitution(long bodyVa);

    native private static float getRotationX(long bodyVa);

    native private static float getRotationY(long bodyVa);

    native private static float getRotationZ(long bodyVa);

    native private static float getRotationW(long bodyVa);

    native private static boolean isActive(long bodyVa);

    native private static boolean isStatic(long bodyVa);

    native private static void setAngularVelocity(
            long bodyVa, float wx, float wy, float wz);

    native private static void setFriction(long bodyVa, float friction);

    native private static void setLinearVelocity(
            long bodyVa, float vx, float vy, float vz);

    native private static void setRestitution(long bodyVa, float restitution);
}
