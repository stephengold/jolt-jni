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
 * Additional state for a {@code Body} that moves.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class MotionProperties extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    MotionProperties(long propertiesVa) {
        super(propertiesVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the net force acting on the body.
     *
     * @return a new force vector (Newtons in system coordinates)
     */
    public Vec3 getAccumulatedForce() {
        long propertiesVa = va();
        float x = getAccumulatedForceX(propertiesVa);
        float y = getAccumulatedForceY(propertiesVa);
        float z = getAccumulatedForceZ(propertiesVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Return the net torque acting on the body.
     *
     * @return a new torque vector (Newton.meters in system coordinates)
     */
    public Vec3 getAccumulatedTorque() {
        long propertiesVa = va();
        float x = getAccumulatedTorqueX(propertiesVa);
        float y = getAccumulatedTorqueY(propertiesVa);
        float z = getAccumulatedTorqueZ(propertiesVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Return the allowed degrees of freedom.
     *
     * @return logical OR of values defined in {@code EAllowedDofs}
     */
    public int getAllowedDofs() {
        long propertiesVa = va();
        int result = getAllowedDofs(propertiesVa);

        return result;
    }

    /**
     * Test whether the body is allowed to fall asleep.
     *
     * @return true if allowed, otherwise false
     */
    public boolean getAllowSleeping() {
        long propertiesVa = va();
        boolean result = getAllowSleeping(propertiesVa);

        return result;
    }

    /**
     * Return the angular damping coefficient.
     *
     * @return the coefficient value
     */
    public float getAngularDamping() {
        long propertiesVa = va();
        float result = getAngularDamping(propertiesVa);

        return result;
    }

    /**
     * Returns a vector in which the disabled angular components are set to zero
     * and enabled ones are set to -1.
     *
     * @return a new vector
     */
    public UVec4 getAngularDofsMask() {
        long propertiesVa = va();
        int dofs = getAllowedDofs(propertiesVa);
        UVec4 result = new UVec4();

        if ((dofs & EAllowedDofs.RotationX) != 0) {
            result.setX(-1);
        }
        if ((dofs & EAllowedDofs.RotationY) != 0) {
            result.setY(-1);
        }
        if ((dofs & EAllowedDofs.RotationZ) != 0) {
            result.setZ(-1);
        }

        return result;
    }

    /**
     * Return the angular velocity.
     *
     * @return a new vector in physics-system coordinates
     */
    public Vec3 getAngularVelocity() {
        long propertiesVa = va();
        float wx = getAngularVelocityX(propertiesVa);
        float wy = getAngularVelocityY(propertiesVa);
        float wz = getAngularVelocityZ(propertiesVa);
        Vec3 result = new Vec3(wx, wy, wz);

        return result;
    }

    /**
     * Return the gravity factor.
     *
     * @return the factor
     */
    public float getGravityFactor() {
        long propertiesVa = va();
        float result = getGravityFactor(propertiesVa);

        return result;
    }

    /**
     * Return the diagonal components of the inverse inertia matrix, assuming a
     * dynamic body.
     *
     * @return a new vector (all components &ge;0)
     */
    public Vec3 getInverseInertiaDiagonal() {
        long propertiesVa = va();
        float dx = getInverseInertiaXX(propertiesVa);
        float dy = getInverseInertiaYY(propertiesVa);
        float dz = getInverseInertiaZZ(propertiesVa);
        Vec3 result = new Vec3(dx, dy, dz);

        return result;
    }

    /**
     * Return the body's inverse mass.
     *
     * @return the value (&ge;0)
     */
    public float getInverseMass() {
        long propertiesVa = va();
        float result = getInverseMass(propertiesVa);

        return result;
    }

    /**
     * Return the linear damping coefficient.
     *
     * @return the coefficient value
     */
    public float getLinearDamping() {
        long propertiesVa = va();
        float result = getLinearDamping(propertiesVa);

        return result;
    }

    /**
     * Return the linear velocity.
     *
     * @return a new vector in physics-system coordinates
     */
    public Vec3 getLinearVelocity() {
        long propertiesVa = va();
        float vx = getLinearVelocityX(propertiesVa);
        float vy = getLinearVelocityY(propertiesVa);
        float vz = getLinearVelocityZ(propertiesVa);
        Vec3 result = new Vec3(vx, vy, vz);

        return result;
    }

    /**
     * Return the maximum angular speed that the body can achieve.
     *
     * @return the speed limit (in radians per second)
     */
    public float getMaxAngularVelocity() {
        long propertiesVa = va();
        float result = getMaxAngularVelocity(propertiesVa);

        return result;
    }

    /**
     * Return the maximum linear speed that the body can achieve.
     *
     * @return the speed limit (distance per second)
     */
    public float getMaxLinearVelocity() {
        long propertiesVa = va();
        float result = getMaxLinearVelocity(propertiesVa);

        return result;
    }

    /**
     * Return the motion quality.
     *
     * @return an enum value (not null)
     */
    public EMotionQuality getMotionQuality() {
        long propertiesVa = va();
        int ordinal = getMotionQuality(propertiesVa);
        EMotionQuality result = EMotionQuality.values()[ordinal];

        return result;
    }

    /**
     * Return the number of position iterations used in the solver.
     *
     * @return the count (&gt;0) or 0 to use number specified in the
     * {@code PhysicsSettings}
     */
    public int getNumPositionStepsOverride() {
        long propertiesVa = va();
        int result = getNumPositionStepsOverride(propertiesVa);

        return result;
    }

    /**
     * Return the number of velocity iterations used in the solver, or 0 to use
     * number specified in the {@code PhysicsSettings}.
     *
     * @return the count (&gt;0) or 0 to use number specified in the
     * {@code PhysicsSettings}
     */
    public int getNumVelocityStepsOverride() {
        long propertiesVa = va();
        int result = getNumVelocityStepsOverride(propertiesVa);

        return result;
    }

    /**
     * Reposition the body, assuming it's kinematic.
     *
     * @param offset the desired offset (from the current location, not null,
     * unaffected)
     * @param rotation the desired rotation (relative to the current
     * orientation, not null, unaffected)
     * @param deltaTime time until the desired position is reached (in seconds,
     * &gt;0)
     */
    public void moveKinematic(
            Vec3Arg offset, QuatArg rotation, float deltaTime) {
        long propertiesVa = va();
        float dx = offset.getX();
        float dy = offset.getY();
        float dz = offset.getZ();
        float qw = rotation.getW();
        float qx = rotation.getX();
        float qy = rotation.getY();
        float qz = rotation.getZ();
        moveKinematic(propertiesVa, dx, dy, dz, qx, qy, qz, qw, deltaTime);
    }

    /**
     * Zero out the accumulated force.
     */
    public void resetForce() {
        long propertiesVa = va();
        resetForce(propertiesVa);
    }

    /**
     * Zero out the accumulated torque.
     */
    public void resetTorque() {
        long propertiesVa = va();
        resetTorque(propertiesVa);
    }

    /**
     * Alter the angular damping.
     *
     * @param damping the desired coefficient value (in units of 1/second,
     * &ge;0, &le;1, default=0.05)
     */
    public void setAngularDamping(float damping) {
        long propertiesVa = va();
        setAngularDamping(propertiesVa, damping);
    }

    /**
     * Directly alter the angular velocity.
     *
     * @param omega the desired velocity (in physics-system coordinates, not
     * null, unaffected, default=(0,0,0))
     */
    public void setAngularVelocity(Vec3Arg omega) {
        long propertiesVa = va();
        float wx = omega.getX();
        float wy = omega.getY();
        float wz = omega.getZ();
        setAngularVelocity(propertiesVa, wx, wy, wz);
    }

    /**
     * Alter the gravity factor.
     *
     * @param factor the desired factor (default=1)
     */
    public void setGravityFactor(float factor) {
        long propertiesVa = va();
        setGravityFactor(propertiesVa, factor);
    }

    /**
     * Alter the diagonal components of the inverse inertia matrix of the body.
     * If inertia changes, mass should probably change as well.
     *
     * @param diagonal the desired diagonal elements (not null, unaffected)
     * @param rotation the desired orientation of the principal axes (not null,
     * unaffected)
     */
    public void setInverseInertia(Vec3Arg diagonal, QuatArg rotation) {
        long propertiesVa = va();
        float dx = diagonal.getX();
        float dy = diagonal.getY();
        float dz = diagonal.getZ();
        float rw = rotation.getW();
        float rx = rotation.getX();
        float ry = rotation.getY();
        float rz = rotation.getZ();
        setInverseInertia(propertiesVa, dx, dy, dz, rx, ry, rz, rw);
    }

    /**
     * Alter the inverse mass. If mass changes, inertia should probably change
     * as well.
     *
     * @param inverseMass the desired value (in units of 1/mass)
     */
    public void setInverseMass(float inverseMass) {
        long propertiesVa = va();
        setInverseMass(propertiesVa, inverseMass);
    }

    /**
     * Alter the linear damping.
     *
     * @param damping the desired value (in units of 1/second, &ge;0, &le;1,
     * default=0.05)
     */
    public void setLinearDamping(float damping) {
        long propertiesVa = va();
        setLinearDamping(propertiesVa, damping);
    }

    /**
     * Directly alter the linear velocity.
     *
     * @param velocity the desired velocity (in physics-system coordinates, not
     * null, unaffected, default=(0,0,0))
     */
    public void setLinearVelocity(Vec3Arg velocity) {
        long propertiesVa = va();
        float vx = velocity.getX();
        float vy = velocity.getY();
        float vz = velocity.getZ();
        setLinearVelocity(propertiesVa, vx, vy, vz);
    }

    /**
     * Alter the mass properties.
     *
     * @param allowedDofs logical OR of values defined in {@code EAllowedDofs}
     * @param massProperties the desired mass properties (not null, unaffected)
     */
    public void setMassProperties(
            int allowedDofs, ConstMassProperties massProperties) {
        long propertiesVa = va();
        long massPropsVa = massProperties.va();
        setMassProperties(propertiesVa, allowedDofs, massPropsVa);
    }

    /**
     * Alter the number of position iterations to use in the solver
     *
     * @param numIterations the desired count (&gt;0) or 0 to use the number
     * specified in the {@code PhysicsSettings}
     */
    public void setNumPositionStepsOverride(int numIterations) {
        long propertiesVa = va();
        setNumPositionStepsOverride(propertiesVa, numIterations);
    }

    /**
     * Alter the number of velocity iterations to use in the solver
     *
     * @param numIterations the desired count (&gt;0) or 0 to use the number
     * specified in the {@code PhysicsSettings}
     */
    public void getNumVelocityStepsOverride(int numIterations) {
        long propertiesVa = va();
        setNumVelocityStepsOverride(propertiesVa, numIterations);
    }
    // *************************************************************************
    // native private methods

    native private static float getAccumulatedForceX(long propertiesVa);

    native private static float getAccumulatedForceY(long propertiesVa);

    native private static float getAccumulatedForceZ(long propertiesVa);

    native private static float getAccumulatedTorqueX(long propertiesVa);

    native private static float getAccumulatedTorqueY(long propertiesVa);

    native private static float getAccumulatedTorqueZ(long propertiesVa);

    native private static int getAllowedDofs(long propertiesVa);

    native private static boolean getAllowSleeping(long propertiesVa);

    native private static float getAngularDamping(long propertiesVa);

    native private static float getAngularVelocityX(long propertiesVa);

    native private static float getAngularVelocityY(long propertiesVa);

    native private static float getAngularVelocityZ(long propertiesVa);

    native private static float getGravityFactor(long propertiesVa);

    native private static float getInverseInertiaXX(long propertiesVa);

    native private static float getInverseInertiaYY(long propertiesVa);

    native private static float getInverseInertiaZZ(long propertiesVa);

    native private static float getInverseMass(long propertiesVa);

    native private static float getLinearDamping(long propertiesVa);

    native private static float getLinearVelocityX(long propertiesVa);

    native private static float getLinearVelocityY(long propertiesVa);

    native private static float getLinearVelocityZ(long propertiesVa);

    native private static float getMaxAngularVelocity(long propertiesVa);

    native private static float getMaxLinearVelocity(long propertiesVa);

    native private static int getMotionQuality(long propertiesVa);

    native private static int getNumPositionStepsOverride(long propertiesVa);

    native private static int getNumVelocityStepsOverride(long propertiesVa);

    native private static void moveKinematic(long propertiesVa,
            float dx, float dy, float dz,
            float qx, float qy, float qz, float qw, float deltaTime);

    native private static void resetForce(long propertiesVa);

    native private static void resetTorque(long propertiesVa);

    native private static void setAngularDamping(
            long propertiesVa, float damping);

    native private static void setAngularVelocity(
            long propertiesVa, float wx, float wy, float wz);

    native private static void setGravityFactor(
            long propertiesVa, float factor);

    native private static void setInverseInertia(long propertiesVa, float dx,
            float dy, float dz, float rx, float ry, float rz, float rw);

    native private static void setInverseMass(
            long propertiesVa, float inverseMass);

    native private static void setLinearDamping(
            long propertiesVa, float damping);

    native private static void setLinearVelocity(
            long propertiesVa, float vx, float vy, float vz);

    native private static void setMassProperties(
            long propertiesVa, int allowedDofs, long massPropsVa);

    native private static void setNumPositionStepsOverride(
            long propertiesVa, int numIterations);

    native private static void setNumVelocityStepsOverride(
            long propertiesVa, int numIterations);
}
