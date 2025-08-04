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

import com.github.stephengold.joltjni.enumerate.EAxis;
import com.github.stephengold.joltjni.enumerate.EMotorState;
import com.github.stephengold.joltjni.readonly.QuatArg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.FloatBuffer;

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
     * @param dof which DOF to query (not null)
     * @return the limit value
     */
    public float getLimitsMax(EAxis dof) {
        long constraintVa = va();
        int dofIndex = dof.ordinal();
        float result = getLimitsMax(constraintVa, dofIndex);

        return result;
    }

    /**
     * Return the lower limit of the specified degree of freedom. The constraint
     * is unaffected.
     *
     * @param dof which DOF to query (not null)
     * @return the limit value
     */
    public float getLimitsMin(EAxis dof) {
        long constraintVa = va();
        int dofOrdinal = dof.ordinal();
        float result = getLimitsMin(constraintVa, dofOrdinal);

        return result;
    }

    /**
     * Access the spring settings of the specified degree of freedom. The
     * constraint is unaffected.
     *
     * @param dof which degree of freedom to query (not null)
     * @return a new JVM object with the pre-existing native object assigned
     */
    public SpringSettings getLimitsSpringSettings(EAxis dof) {
        long constraintVa = va();
        int dofOrdinal = dof.ordinal();
        long settingsVa = getLimitsSpringSettings(constraintVa, dofOrdinal);
        SpringSettings result = new SpringSettings(this, settingsVa);

        return result;
    }

    /**
     * Return the maximum friction of the specified degree of freedom. The
     * constraint is unaffected.
     *
     * @param dof which DPF to alter (not null)
     * @return the friction value
     */
    public float getMaxFriction(EAxis dof) {
        long constraintVa = va();
        int dofIndex = dof.ordinal();
        float result = getMaxFriction(constraintVa, dofIndex);

        return result;
    }

    /**
     * Access the motor settings of the specified degree of freedom. The
     * constraint is unaffected.
     *
     * @param dof which degree of freedom to query (not null)
     * @return a new JVM object with the pre-existing native object assigned
     */
    public MotorSettings getMotorSettings(EAxis dof) {
        long constraintVa = va();
        int dofIndex = dof.ordinal();
        long settingsVa = getMotorSettings(constraintVa, dofIndex);
        MotorSettings result = new MotorSettings(this, settingsVa);

        return result;
    }

    /**
     * Return the motor state of the specified degree of freedom.
     *
     * @param dof which DOF to alter (not null)
     * @return an enum value (not null)
     */
    public EMotorState getMotorState(EAxis dof) {
        long constraintVa = va();
        int dofIndex = dof.ordinal();
        int stateOrdinal = getMotorState(constraintVa, dofIndex);
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
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getRotationInConstraintSpace(constraintVa, storeFloats);
        Quat result = new Quat(storeFloats);

        return result;
    }

    /**
     * Return the rotation upper limits. The constraint is unaffected.
     *
     * @return the upper limit for each axis (not null)
     */
    public Vec3 getRotationLimitsMax() {
        long constraintVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getRotationLimitsMax(constraintVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Return the rotation lower limits. The constraint is unaffected.
     *
     * @return the lower limit for each axis (not null)
     */
    public Vec3 getRotationLimitsMin() {
        long constraintVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getRotationLimitsMin(constraintVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Copy the target velocities of the angular motors. The constraint is
     * unaffected. (native method: GetTargetAngularVelocityCS)
     *
     * @return a new angular velocity vector (radians per second in body 2
     * constraint space)
     */
    public Vec3 getTargetAngularVelocityCs() {
        long constraintVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getTargetAngularVelocityCs(constraintVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Copy the target position of the linear motors. The constraint is
     * unaffected. (native method: GetTargetPositionCS)
     *
     * @return a new offset vector (meters in body 1 constraint space)
     */
    public Vec3 getTargetPositionCs() {
        long constraintVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getTargetPositionCs(constraintVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Copy the target orientation of the angular motors. The constraint is
     * unaffected. (native method: GetTargetOrientationCS)
     *
     * @return the target orientation (in constraint space)
     */
    public Quat getTargetOrientationCs() {
        long constraintVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getTargetOrientationCs(constraintVa, storeFloats);
        Quat result = new Quat(storeFloats);

        return result;
    }

    /**
     * Copy the target velocities of the linear motors. The constraint is
     * unaffected. (native method: GetTargetVelocityCS)
     *
     * @return a new linear velocity vector (meters per second in body 1
     * constraint space)
     */
    public Vec3 getTargetVelocityCs() {
        long constraintVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getTargetVelocityCs(constraintVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Return the translation upper limits. The constraint is unaffected.
     *
     * @return the upper limit for each axis (not null)
     */
    public Vec3 getTranslationLimitsMax() {
        long constraintVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getTranslationLimitsMax(constraintVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Return the translation lower limits. The constraint is unaffected.
     *
     * @return the lower limit for each axis (not null)
     */
    public Vec3 getTranslationLimitsMin() {
        long constraintVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getTranslationLimitsMin(constraintVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Test whether the specified degree of freedom is fixed. The constraint is
     * unaffected.
     *
     * @param dof which DOF to query (not null)
     * @return {@code true} if fixed, otherwise {@code false}
     */
    public boolean isFixedAxis(EAxis dof) {
        long constraintVa = va();
        int dofIndex = dof.ordinal();
        boolean result = isFixedAxis(constraintVa, dofIndex);

        return result;
    }

    /**
     * Test whether the specified degree of freedom is free. The constraint is
     * unaffected.
     *
     * @param dof which DOF to query (not null)
     * @return {@code true} if free, otherwise {@code false}
     */
    public boolean isFreeAxis(EAxis dof) {
        long constraintVa = va();
        int dofIndex = dof.ordinal();
        boolean result = isFreeAxis(constraintVa, dofIndex);

        return result;
    }

    /**
     * Alter the spring settings of the specified degree of freedom.
     *
     * @param dof which DOF to alter (not null)
     * @param springSettings the desired settings (not null, unaffected)
     */
    public void setLimitsSpringSettings(
            EAxis dof, SpringSettings springSettings) {
        long constraintVa = va();
        int dofIndex = dof.ordinal();
        long settingsVa = springSettings.va();
        setLimitsSpringSettings(constraintVa, dofIndex, settingsVa);
    }

    /**
     * Alter the maximum friction of the specified degree of freedom.
     *
     * @param dof which DOF to alter (not null)
     * @param friction the desired value
     */
    public void setMaxFriction(EAxis dof, float friction) {
        long constraintVa = va();
        int dofIndex = dof.ordinal();
        setMaxFriction(constraintVa, dofIndex, friction);
    }

    /**
     * Alter the motor state of the specified degree of freedom.
     *
     * @param dof which DOF to alter (not null)
     * @param state the desired state (not null)
     */
    public void setMotorState(EAxis dof, EMotorState state) {
        long constraintVa = va();
        int dofIndex = dof.ordinal();
        int stateOrdinal = state.ordinal();
        setMotorState(constraintVa, dofIndex, stateOrdinal);
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
     * Alter the target velocities of the linear motors. (native method:
     * SetTargetVelocityCS)
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
     * Alter the target velocities of the angular motors. (native method:
     * SetTargetAngularVelocityCS)
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
     * Alter the target position of the linear motors. (native method:
     * SetTargetPositionCS)
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
     * Alter the target orientation of the angular motors in body space. (native
     * method: SetTargetOrientationBS)
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
     * Alter the target orientation of the angular motors. (native method:
     * SetTargetOrientationCS)
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

    native private static float getLimitsMax(long constraintVa, int dofIndex);

    native private static float getLimitsMin(long constraintVa, int dofIndex);

    native private static long getLimitsSpringSettings(
            long constraintVa, int dofIndex);

    native private static float getMaxFriction(
            long constraintVa, int dofIndex);

    native private static long getMotorSettings(
            long constraintVa, int dofIndex);

    native private static int getMotorState(long constraintVa, int dofIndex);

    native private static void getRotationInConstraintSpace(
            long constraintVa, FloatBuffer storeFloats);

    native private static void getRotationLimitsMax(
            long constraintVa, FloatBuffer storeFloats);

    native private static void getRotationLimitsMin(
            long constraintVa, FloatBuffer storeFloats);

    native private static void getTargetAngularVelocityCs(
            long constraintVa, FloatBuffer storeFloats);

    native private static void getTargetPositionCs(
            long constraintVa, FloatBuffer storeFloats);

    native private static void getTargetOrientationCs(
            long constraintVa, FloatBuffer storeFloats);

    native private static void getTargetVelocityCs(
            long constraintVa, FloatBuffer storeFloats);

    native private static void getTranslationLimitsMax(
            long constraintVa, FloatBuffer storeFloats);

    native private static void getTranslationLimitsMin(
            long constraintVa, FloatBuffer storeFloats);

    native private static boolean isFixedAxis(
            long constraintVa, int dofIndex);

    native private static boolean isFreeAxis(
            long constraintVa, int dofIndex);

    native private static void setLimitsSpringSettings(
            long constraintVa, int dofIndex, long springSettingsVa);

    native private static void setMaxFriction(
            long constraintVa, int dofIndex, float friction);

    native private static void setMotorState(
            long constraintVa, int dofIndex, int stateOrdinal);

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
