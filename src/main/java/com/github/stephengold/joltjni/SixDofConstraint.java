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

import com.github.stephengold.joltjni.enumerate.EAxis;
import com.github.stephengold.joltjni.enumerate.EMotorState;
import com.github.stephengold.joltjni.readonly.QuatArg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * A {@code TwoBodyConstraint} that can constrain all 6 degrees of freedom.
 * (native type: SixDOFConstraint)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class SixDofConstraint extends TwoBodyConstraint {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a constraint with the specified native object assigned but
     * not owned.
     *
     * @param constraintVa the virtual address of the native object to assign
     * (not zero)
     */
    SixDofConstraint(long constraintVa) {
        super(constraintVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the upper limit of the specified degree of freedom. The constraint
     * is unaffected.
     *
     * @param axis which axis to query (not null)
     * @return the limit value
     */
    public float getLimitsMax(EAxis axis) {
        long constraintVa = va();
        int axisOrdinal = axis.ordinal();
        float result = getLimitsMax(constraintVa, axisOrdinal);

        return result;
    }

    /**
     * Return the lower limit of the specified degree of freedom. The constraint
     * is unaffected.
     *
     * @param axis which axis to query (not null)
     * @return the limit value
     */
    public float getLimitsMin(EAxis axis) {
        long constraintVa = va();
        int axisOrdinal = axis.ordinal();
        float result = getLimitsMin(constraintVa, axisOrdinal);

        return result;
    }

    /**
     * Access the spring settings of the specified degree of freedom. The
     * constraint is unaffected.
     *
     * @param axis which axis to query (not null)
     * @return a new JVM object with the pre-existing native object assigned
     */
    public SpringSettings getLimitsSpringSettings(EAxis axis) {
        long constraintVa = va();
        int axisOrdinal = axis.ordinal();
        long settingsVa = getLimitsSpringSettings(constraintVa, axisOrdinal);
        SpringSettings result = new SpringSettings(this, settingsVa);

        return result;
    }

    /**
     * Return the maximum friction of the specified degree of freedom. The
     * constraint is unaffected.
     *
     * @param axis which axis to alter (not null)
     * @return the friction value
     */
    public float getMaxFriction(EAxis axis) {
        long constraintVa = va();
        int axisOrdinal = axis.ordinal();
        float result = getMaxFriction(constraintVa, axisOrdinal);

        return result;
    }

    /**
     * Access the motor settings of the specified degree of freedom. The
     * constraint is unaffected.
     *
     * @param axis which axis to query (not null)
     * @return a new JVM object with the pre-existing native object assigned
     */
    public MotorSettings getMotorSettings(EAxis axis) {
        long constraintVa = va();
        int axisOrdinal = axis.ordinal();
        long settingsVa = getMotorSettings(constraintVa, axisOrdinal);
        MotorSettings result = new MotorSettings(this, settingsVa);

        return result;
    }

    /**
     * Return the motor state of the specified degree of freedom.
     *
     * @param axis which axis to alter (not null)
     * @return an enum value (not null)
     */
    public EMotorState getMotorState(EAxis axis) {
        long constraintVa = va();
        int axisOrdinal = axis.ordinal();
        int stateOrdinal = getMotorState(constraintVa, axisOrdinal);
        EMotorState result = EMotorState.values()[stateOrdinal];

        return result;
    }

    /**
     * Copy the rotation of the constraint. The constraint is unaffected.
     *
     * @return a new rotation quaternion
     */
    public Quat getRotationInConstraintSpace() {
        long constraintVa = va();
        float qw = getRotationInConstraintSpaceW(constraintVa);
        float qx = getRotationInConstraintSpaceX(constraintVa);
        float qy = getRotationInConstraintSpaceY(constraintVa);
        float qz = getRotationInConstraintSpaceZ(constraintVa);
        Quat result = new Quat(qx, qy, qz, qw);

        return result;
    }

    /**
     * Return the rotation upper limits. The constraint is unaffected.
     *
     * @return the upper limit for each axis (not null)
     */
    public Vec3 getRotationLimitsMax() {
        long constraintVa = va();
        float x = getRotationLimitsMaxX(constraintVa);
        float y = getRotationLimitsMaxY(constraintVa);
        float z = getRotationLimitsMaxZ(constraintVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Return the rotation lower limits. The constraint is unaffected.
     *
     * @return the lower limit for each axis (not null)
     */
    public Vec3 getRotationLimitsMin() {
        long constraintVa = va();
        float x = getRotationLimitsMinX(constraintVa);
        float y = getRotationLimitsMinY(constraintVa);
        float z = getRotationLimitsMinZ(constraintVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Copy the target velocities of the angular motors. The constraint is
     * unaffected.
     *
     * @return a new angular velocity vector (radians per second in body 2
     * constraint space)
     */
    public Vec3 getTargetAngularVelocityCs() {
        long constraintVa = va();
        float wx = getTargetAngularVelocityCsX(constraintVa);
        float wy = getTargetAngularVelocityCsY(constraintVa);
        float wz = getTargetAngularVelocityCsZ(constraintVa);
        Vec3 result = new Vec3(wx, wy, wz);

        return result;
    }

    /**
     * Copy the target position of the linear motors.
     *
     * @return a new offset vector (meters in body 1 constraint space)
     */
    public Vec3 getTargetPositionCs() {
        long constraintVa = va();
        float x = getTargetPositionCsX(constraintVa);
        float y = getTargetPositionCsY(constraintVa);
        float z = getTargetPositionCsZ(constraintVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Copy the target orientation of the angular motors.
     *
     * @return the target orientation (in constraint space)
     */
    public Quat getTargetOrientationCs() {
        long constraintVa = va();
        float qw = getTargetOrientationCsW(constraintVa);
        float qx = getTargetOrientationCsX(constraintVa);
        float qy = getTargetOrientationCsY(constraintVa);
        float qz = getTargetOrientationCsZ(constraintVa);
        Quat result = new Quat(qx, qy, qz, qw);

        return result;
    }

    /**
     * Copy the target velocities of the linear motors. The constraint is
     * unaffected.
     *
     * @return a new linear velocity vector (meters per second in body 1
     * constraint space)
     */
    public Vec3 getTargetVelocityCs() {
        long constraintVa = va();
        float vx = getTargetVelocityCsX(constraintVa);
        float vy = getTargetVelocityCsY(constraintVa);
        float vz = getTargetVelocityCsZ(constraintVa);
        Vec3 result = new Vec3(vx, vy, vz);

        return result;
    }

    /**
     * Return the translation upper limits. The constraint is unaffected.
     *
     * @return the upper limit for each axis (not null)
     */
    public Vec3 getTranslationLimitsMax() {
        long constraintVa = va();
        float x = getTranslationLimitsMaxX(constraintVa);
        float y = getTranslationLimitsMaxY(constraintVa);
        float z = getTranslationLimitsMaxZ(constraintVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Return the translation lower limits. The constraint is unaffected.
     *
     * @return the lower limit for each axis (not null)
     */
    public Vec3 getTranslationLimitsMin() {
        long constraintVa = va();
        float x = getTranslationLimitsMinX(constraintVa);
        float y = getTranslationLimitsMinY(constraintVa);
        float z = getTranslationLimitsMinZ(constraintVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Test whether the specified degree of freedom is fixed. The constraint is
     * unaffected.
     *
     * @param axis which axis to query (not null)
     * @return {@code true} if fixed, otherwise {@code false}
     */
    public boolean isFixedAxis(EAxis axis) {
        long constraintVa = va();
        int axisOrdinal = axis.ordinal();
        boolean result = isFixedAxis(constraintVa, axisOrdinal);

        return result;
    }

    /**
     * Test whether the specified degree of freedom is free. The constraint is
     * unaffected.
     *
     * @param axis which axis to query (not null)
     * @return {@code true} if free, otherwise {@code false}
     */
    public boolean isFreeAxis(EAxis axis) {
        long constraintVa = va();
        int axisOrdinal = axis.ordinal();
        boolean result = isFreeAxis(constraintVa, axisOrdinal);

        return result;
    }

    /**
     * Alter the spring settings of the specified degree of freedom.
     *
     * @param axis which axis to alter (not null)
     * @param springSettings the desired settings (not null, unaffected)
     */
    public void setLimitsSpringSettings(
            EAxis axis, SpringSettings springSettings) {
        long constraintVa = va();
        int axisOrdinal = axis.ordinal();
        long settingsVa = springSettings.va();
        setLimitsSpringSettings(constraintVa, axisOrdinal, settingsVa);
    }

    /**
     * Alter the maximum friction of the specified degree of freedom.
     *
     * @param axis which axis to alter (not null)
     * @param friction the desired value
     */
    public void setMaxFriction(EAxis axis, float friction) {
        long constraintVa = va();
        int axisOrdinal = axis.ordinal();
        setMaxFriction(constraintVa, axisOrdinal, friction);
    }

    /**
     * Alter the motor state of the specified degree of freedom.
     *
     * @param axis which axis to alter (not null)
     * @param state the desired state (not null)
     */
    public void setMotorState(EAxis axis, EMotorState state) {
        long constraintVa = va();
        int axisOrdinal = axis.ordinal();
        int stateOrdinal = state.ordinal();
        setMotorState(constraintVa, axisOrdinal, stateOrdinal);
    }

    /**
     * Alter the rotation limits.
     *
     * @param min the lower limit for each axis (not null, unaffected)
     * @param max the upper limit for each axis (not null, unaffected)
     */
    public void setRotationLimits(Vec3Arg min, Vec3Arg max) {
        long constraintVa = va();
        float minX = min.getX();
        float minY = min.getY();
        float minZ = min.getZ();
        float maxX = max.getX();
        float maxY = max.getY();
        float maxZ = max.getZ();
        setRotationLimits(constraintVa, minX, minY, minZ, maxX, maxY, maxZ);
    }

    /**
     * Alter the target velocities of the linear motors.
     *
     * @param velocity the desired linear velocity (meters per second in body 1
     * constraint space, not null)
     */
    public void setTargetVelocityCs(Vec3Arg velocity) {
        long constraintVa = va();
        float vx = velocity.getX();
        float vy = velocity.getY();
        float vz = velocity.getZ();
        setTargetVelocityCs(constraintVa, vx, vy, vz);
    }

    /**
     * Alter the target velocities of the angular motors.
     *
     * @param omega the desired angular velocity (radians per second in body 2
     * constraint space, not null)
     */
    public void setTargetAngularVelocityCs(Vec3Arg omega) {
        long constraintVa = va();
        float wx = omega.getX();
        float wy = omega.getY();
        float wz = omega.getZ();
        setTargetAngularVelocityCs(constraintVa, wx, wy, wz);
    }

    /**
     * Alter the target position of the linear motors.
     *
     * @param offsets the desired offsets (meters in body 1 constraint space)
     */
    public void setTargetPositionCs(Vec3Arg offsets) {
        long constraintVa = va();
        float x = offsets.getX();
        float y = offsets.getY();
        float z = offsets.getZ();
        setTargetPositionCs(constraintVa, x, y, z);
    }

    /**
     * Alter the target orientation of the angular motors in body space.
     *
     * @param orientation the desired target orientation (not null, unaffected)
     */
    public void setTargetOrientationBs(QuatArg orientation) {
        long constraintVa = va();
        float qw = orientation.getW();
        float qx = orientation.getX();
        float qy = orientation.getY();
        float qz = orientation.getZ();
        setTargetOrientationBs(constraintVa, qx, qy, qz, qw);
    }

    /**
     * Alter the target orientation of the angular motors.
     *
     * @param orientation the desired orientation (in body 1 constraint space)
     */
    public void setTargetOrientationCs(QuatArg orientation) {
        long constraintVa = va();
        float qw = orientation.getW();
        float qx = orientation.getX();
        float qy = orientation.getY();
        float qz = orientation.getZ();
        setTargetOrientationCs(constraintVa, qx, qy, qz, qw);
    }

    /**
     * Alter the translation limits.
     *
     * @param min the lower limit for each axis (not null, unaffected)
     * @param max the upper limit for each axis (not null, unaffected)
     */
    public void setTranslationLimits(Vec3Arg min, Vec3Arg max) {
        long constraintVa = va();
        float minX = min.getX();
        float minY = min.getY();
        float minZ = min.getZ();
        float maxX = max.getX();
        float maxY = max.getY();
        float maxZ = max.getZ();
        setTranslationLimits(constraintVa, minX, minY, minZ, maxX, maxY, maxZ);
    }
    // *************************************************************************
    // native private methods

    native private static float getLimitsMax(
            long constraintVa, int axisOrdinal);

    native private static float getLimitsMin(
            long constraintVa, int axisOrdinal);

    native private static long getLimitsSpringSettings(
            long constraintVa, int axisOrdinal);

    native private static float getMaxFriction(
            long constraintVa, int axisOrdinal);

    native private static long getMotorSettings(
            long constraintVa, int axisOrdinal);

    native private static int getMotorState(
            long constraintVa, int axisOrdinal);

    native private static float getRotationInConstraintSpaceW(
            long constraintVa);

    native private static float getRotationInConstraintSpaceX(
            long constraintVa);

    native private static float getRotationInConstraintSpaceY(
            long constraintVa);

    native private static float getRotationInConstraintSpaceZ(
            long constraintVa);

    native private static float getRotationLimitsMaxX(long constraintVa);

    native private static float getRotationLimitsMaxY(long constraintVa);

    native private static float getRotationLimitsMaxZ(long constraintVa);

    native private static float getRotationLimitsMinX(long constraintVa);

    native private static float getRotationLimitsMinY(long constraintVa);

    native private static float getRotationLimitsMinZ(long constraintVa);

    native private static float getTargetAngularVelocityCsX(long constraintVa);

    native private static float getTargetAngularVelocityCsY(long constraintVa);

    native private static float getTargetAngularVelocityCsZ(long constraintVa);

    native private static float getTargetPositionCsX(long constraintVa);

    native private static float getTargetPositionCsY(long constraintVa);

    native private static float getTargetPositionCsZ(long constraintVa);

    native private static float getTargetOrientationCsW(long constraintVa);

    native private static float getTargetOrientationCsX(long constraintVa);

    native private static float getTargetOrientationCsY(long constraintVa);

    native private static float getTargetOrientationCsZ(long constraintVa);

    native private static float getTargetVelocityCsX(long constraintVa);

    native private static float getTargetVelocityCsY(long constraintVa);

    native private static float getTargetVelocityCsZ(long constraintVa);

    native private static float getTranslationLimitsMaxX(long constraintVa);

    native private static float getTranslationLimitsMaxY(long constraintVa);

    native private static float getTranslationLimitsMaxZ(long constraintVa);

    native private static float getTranslationLimitsMinX(long constraintVa);

    native private static float getTranslationLimitsMinY(long constraintVa);

    native private static float getTranslationLimitsMinZ(long constraintVa);

    native private static boolean isFixedAxis(
            long constraintVa, int axisOrdinal);

    native private static boolean isFreeAxis(
            long constraintVa, int axisOrdinal);

    native private static void setLimitsSpringSettings(
            long constraintVa, int axisOrdinal, long springSettingsVa);

    native private static void setMaxFriction(
            long constraintVa, int axisOrdinal, float friction);

    native private static void setMotorState(
            long constraintVa, int axisOrdinal, int stateOrdinal);

    native private static void setRotationLimits(long constraintVa, float minX,
            float minY, float minZ, float maxX, float maxY, float maxZ);

    native private static void setTargetAngularVelocityCs(
            long constraintVa, float omegaX, float omegaY, float omegaZ);

    native private static void setTargetPositionCs(
            long constraintVa, float offsetX, float offsetY, float offsetZ);

    native private static void setTargetOrientationBs(
            long constraintVa, float qx, float qy, float qz, float qw);

    native private static void setTargetOrientationCs(
            long constraintVa, float qx, float qy, float qz, float qw);

    native private static void setTargetVelocityCs(
            long constraintVa, float vx, float vy, float vz);

    native private static void setTranslationLimits(
            long constraintVa, float minX, float minY, float minZ,
            float maxX, float maxY, float maxZ);
}
