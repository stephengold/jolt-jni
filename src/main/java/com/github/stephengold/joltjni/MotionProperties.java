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

import com.github.stephengold.joltjni.enumerate.EAllowedDofs;
import com.github.stephengold.joltjni.enumerate.EMotionQuality;
import com.github.stephengold.joltjni.readonly.ConstMassProperties;
import com.github.stephengold.joltjni.readonly.ConstMotionProperties;
import com.github.stephengold.joltjni.readonly.QuatArg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.FloatBuffer;

/**
 * Additional state for a {@code Body} that moves.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class MotionProperties
        extends JoltPhysicsObject
        implements ConstMotionProperties {
    // *************************************************************************
    // constructors

    /**
     * Instantiate default properties.
     */
    public MotionProperties() {
        long propertiesVa = createDefault();
        setVirtualAddress(propertiesVa, () -> free(propertiesVa));
    }

    /**
     * Instantiate properties with no containing object and no native object
     * assigned.
     *
     * @param dummy unused argument to distinguish from the zero-arg constructor
     */
    MotionProperties(boolean dummy) {
    }

    /**
     * Instantiate a copy of the specified properties.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public MotionProperties(ConstMotionProperties original) {
        long originalVa = original.targetVa();
        long copyVa = createCopy(originalVa);
        setVirtualAddress(copyVa, () -> free(copyVa));
    }

    /**
     * Instantiate with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param propertiesVa the virtual address of the native object to assign
     * (not zero)
     */
    MotionProperties(JoltPhysicsObject container, long propertiesVa) {
        super(container, propertiesVa);
    }
    // *************************************************************************
    // new methods exposed

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
     * @param damping the desired coefficient value (in units of per second,
     * &ge;0, &le;1, default=0)
     */
    public void setAngularDamping(float damping) {
        long propertiesVa = va();
        setAngularDamping(propertiesVa, damping);
    }

    /**
     * Directly alter the angular velocity.
     *
     * @param omega the desired velocity (meters per second in system
     * coordinates, not null, unaffected, default=(0,0,0))
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
     * @param factor the desired factor (default=0)
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
     * @param damping the desired value (in units of per second, &ge;0, &le;1,
     * default=0)
     */
    public void setLinearDamping(float damping) {
        long propertiesVa = va();
        setLinearDamping(propertiesVa, damping);
    }

    /**
     * Directly alter the linear velocity.
     *
     * @param velocity the desired velocity (meters per second in system
     * coordinates, not null, unaffected, default=(0,0,0))
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
     * (default=All)
     * @param massProperties the desired mass properties (not null, unaffected)
     */
    public void setMassProperties(
            int allowedDofs, ConstMassProperties massProperties) {
        long propertiesVa = va();
        long massPropsVa = massProperties.targetVa();
        setMassProperties(propertiesVa, allowedDofs, massPropsVa);
    }

    /**
     * Return the maximum angular speed that the body can achieve.
     *
     * @param maxSpeed the speed limit (in radians per second, default=0)
     */
    public void setMaxAngularVelocity(float maxSpeed) {
        long propertiesVa = va();
        setMaxAngularVelocity(propertiesVa, maxSpeed);
    }

    /**
     * Return the maximum linear speed that the body can achieve.
     *
     * @param maxSpeed the speed limit (in meters per second, default=0)
     */
    public void setMaxLinearVelocity(float maxSpeed) {
        long propertiesVa = va();
        setMaxLinearVelocity(propertiesVa, maxSpeed);
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
    public void setNumVelocityStepsOverride(int numIterations) {
        long propertiesVa = va();
        setNumVelocityStepsOverride(propertiesVa, numIterations);
    }
    // *************************************************************************
    // ConstMotionProperties methods

    /**
     * Copy the net force acting on the body. The properties are unaffected.
     *
     * @return a new force vector (Newtons in system coordinates)
     */
    @Override
    public Vec3 getAccumulatedForce() {
        long propertiesVa = va();
        float x = getAccumulatedForceX(propertiesVa);
        float y = getAccumulatedForceY(propertiesVa);
        float z = getAccumulatedForceZ(propertiesVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Copy the net torque acting on the body. The properties are unaffected.
     *
     * @return a new torque vector (Newton.meters in system coordinates)
     */
    @Override
    public Vec3 getAccumulatedTorque() {
        long propertiesVa = va();
        float x = getAccumulatedTorqueX(propertiesVa);
        float y = getAccumulatedTorqueY(propertiesVa);
        float z = getAccumulatedTorqueZ(propertiesVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Return the allowed degrees of freedom. The properties are unaffected.
     *
     * @return logical OR of values defined in {@code EAllowedDofs}
     */
    @Override
    public int getAllowedDofs() {
        long propertiesVa = va();
        int result = getAllowedDofs(propertiesVa);

        return result;
    }

    /**
     * Test whether the body is allowed to fall asleep. The properties are
     * unaffected.
     *
     * @return {@code true} if allowed, otherwise {@code false}
     */
    @Override
    public boolean getAllowSleeping() {
        long propertiesVa = va();
        boolean result = getAllowSleeping(propertiesVa);

        return result;
    }

    /**
     * Return the angular damping coefficient. The properties are unaffected.
     *
     * @return the coefficient value (in units of per second, &ge;0, &le;1)
     */
    @Override
    public float getAngularDamping() {
        long propertiesVa = va();
        float result = getAngularDamping(propertiesVa);

        return result;
    }

    /**
     * Generate a vector in which the disabled angular components are set to
     * zero and enabled ones are set to -1. The properties are unaffected.
     *
     * @return a new vector
     */
    @Override
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
     * Copy the angular velocity. The properties are unaffected.
     *
     * @return a new velocity vector (radians per second in system coordinates)
     */
    @Override
    public Vec3 getAngularVelocity() {
        long propertiesVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getAngularVelocity(propertiesVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Return the gravity factor. The properties are unaffected.
     *
     * @return the factor
     */
    @Override
    public float getGravityFactor() {
        long propertiesVa = va();
        float result = getGravityFactor(propertiesVa);

        return result;
    }

    /**
     * Copy the rotation that takes the inverse-inertia diagonal to local
     * coordinates. The properties are unaffected.
     *
     * @return a new object
     */
    @Override
    public Quat getInertiaRotation() {
        long propertiesVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getInertiaRotation(propertiesVa, storeFloats);
        Quat result = new Quat(storeFloats);

        return result;
    }

    /**
     * Copy the diagonal components of the inverse inertia matrix, assuming a
     * dynamic body. The properties are unaffected.
     *
     * @return a new vector (all components &ge;0)
     */
    @Override
    public Vec3 getInverseInertiaDiagonal() {
        long propertiesVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getInverseInertiaDiagonal(propertiesVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Return the body's inverse mass. The properties are unaffected.
     *
     * @return the value (&ge;0)
     */
    @Override
    public float getInverseMass() {
        long propertiesVa = va();
        float result = getInverseMass(propertiesVa);

        return result;
    }

    /**
     * Return the body's inverse mass. The properties are unaffected.
     *
     * @return the value (&ge;0)
     */
    @Override
    public float getInverseMassUnchecked() {
        long propertiesVa = va();
        float result = getInverseMassUnchecked(propertiesVa);

        return result;
    }

    /**
     * Return the linear damping coefficient. The properties are unaffected.
     *
     * @return the coefficient value (in units of per second, &ge;0, &le;1)
     */
    @Override
    public float getLinearDamping() {
        long propertiesVa = va();
        float result = getLinearDamping(propertiesVa);

        return result;
    }

    /**
     * Copy the linear velocity. The properties are unaffected.
     *
     * @return a new velocity vector (meters per second in system coordinates)
     */
    @Override
    public Vec3 getLinearVelocity() {
        long propertiesVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getLinearVelocity(propertiesVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Copy the inverse-inertia matrix. The properties are unaffected.
     *
     * @return a new object
     */
    @Override
    public Mat44 getLocalSpaceInverseInertia() {
        long propertiesVa = va();
        long matrixVa = getLocalSpaceInverseInertia(propertiesVa);
        Mat44 result = new Mat44(matrixVa, true);

        return result;
    }

    /**
     * Return the maximum angular speed that the body can achieve. The
     * properties are unaffected.
     *
     * @return the speed limit (in radians per second)
     */
    @Override
    public float getMaxAngularVelocity() {
        long propertiesVa = va();
        float result = getMaxAngularVelocity(propertiesVa);

        return result;
    }

    /**
     * Return the maximum linear speed that the body can achieve. The properties
     * are unaffected.
     *
     * @return the speed limit (in meters per second)
     */
    @Override
    public float getMaxLinearVelocity() {
        long propertiesVa = va();
        float result = getMaxLinearVelocity(propertiesVa);

        return result;
    }

    /**
     * Return the motion quality. The properties are unaffected.
     *
     * @return an enum value (not null)
     */
    @Override
    public EMotionQuality getMotionQuality() {
        long propertiesVa = va();
        int ordinal = getMotionQuality(propertiesVa);
        EMotionQuality result = EMotionQuality.values()[ordinal];

        return result;
    }

    /**
     * Return the number of position iterations used in the solver. The
     * properties are unaffected.
     *
     * @return the count (&gt;0) or 0 to use number specified in the
     * {@code PhysicsSettings}
     */
    @Override
    public int getNumPositionStepsOverride() {
        long propertiesVa = va();
        int result = getNumPositionStepsOverride(propertiesVa);

        return result;
    }

    /**
     * Return the number of velocity iterations used in the solver. The
     * properties are unaffected.
     *
     * @return the count (&gt;0) or 0 to use number specified in the
     * {@code PhysicsSettings}
     */
    @Override
    public int getNumVelocityStepsOverride() {
        long propertiesVa = va();
        int result = getNumVelocityStepsOverride(propertiesVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static void free(long propertiesVa);

    native private static float getAccumulatedForceX(long propertiesVa);

    native private static float getAccumulatedForceY(long propertiesVa);

    native private static float getAccumulatedForceZ(long propertiesVa);

    native private static float getAccumulatedTorqueX(long propertiesVa);

    native private static float getAccumulatedTorqueY(long propertiesVa);

    native private static float getAccumulatedTorqueZ(long propertiesVa);

    native private static int getAllowedDofs(long propertiesVa);

    native private static boolean getAllowSleeping(long propertiesVa);

    native private static float getAngularDamping(long propertiesVa);

    native private static void getAngularVelocity(
            long propertiesVa, FloatBuffer storeFloats);

    native private static float getGravityFactor(long propertiesVa);

    native private static void getInertiaRotation(
            long propertiesVa, FloatBuffer storeFloats);

    native private static void getInverseInertiaDiagonal(
            long propertiesVa, FloatBuffer storeFloats);

    native private static float getInverseMass(long propertiesVa);

    native private static float getInverseMassUnchecked(long propertiesVa);

    native private static float getLinearDamping(long propertiesVa);

    native private static void getLinearVelocity(
            long propertiesVa, FloatBuffer storeFloats);

    native private static long getLocalSpaceInverseInertia(long propertiesVa);

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

    native private static void setMaxAngularVelocity(
            long propertiesVa, float maxSpeed);

    native private static void setMaxLinearVelocity(
            long propertiesVa, float maxSpeed);

    native private static void setNumPositionStepsOverride(
            long propertiesVa, int numIterations);

    native private static void setNumVelocityStepsOverride(
            long propertiesVa, int numIterations);
}
