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

import com.github.stephengold.joltjni.enumerate.EMotorState;

/**
 * A {@code TwoBodyConstraint} that allows rotation only along one axis and
 * disables translation, eliminating 5 degrees of freedom.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class HingeConstraint extends TwoBodyConstraint {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a constraint with the specified native object assigned but
     * not owned.
     *
     * @param constraintVa the virtual address of the native object to assign
     * (not zero)
     */
    HingeConstraint(long constraintVa) {
        super(constraintVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the current angle from rest orientation. The constraint is
     * unaffected.
     *
     * @return the angle (in radians)
     */
    public float getCurrentAngle() {
        long constraintVa = va();
        float result = getCurrentAngle(constraintVa);

        return result;
    }

    /**
     * Return the upper limit of rotation. The constraint is unaffected.
     *
     * @return the upper limit (in radians)
     */
    public float getLimitsMax() {
        long constraintVa = va();
        float result = getLimitsMax(constraintVa);

        return result;
    }

    /**
     * Return the lower limit of rotation. The constraint is unaffected.
     *
     * @return the lower limit (in radians)
     */
    public float getLimitsMin() {
        long constraintVa = va();
        float result = getLimitsMin(constraintVa);

        return result;
    }

    /**
     * Access the spring settings. The constraint is unaffected.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public SpringSettings getLimitsSpringSettings() {
        long constraintVa = va();
        long settingsVa = getLimitsSpringSettings(constraintVa);
        SpringSettings result = new SpringSettings(this, settingsVa);

        return result;
    }

    /**
     * Return the maximum friction torque when not driven by a motor. The
     * constraint is unaffected.
     *
     * @return the torque (in Newton-meters)
     */
    public float getMaxFrictionTorque() {
        long constraintVa = va();
        float result = getMaxFrictionTorque(constraintVa);

        return result;
    }

    /**
     * Access the motor settings. The constraint is unaffected.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public MotorSettings getMotorSettings() {
        long constraintVa = va();
        long settingsVa = getMotorSettings(constraintVa);
        MotorSettings result = new MotorSettings(this, settingsVa);

        return result;
    }

    /**
     * Return the state of the motor. The constraint is unaffected.
     *
     * @return an enum value (not null)
     */
    public EMotorState getMotorState() {
        long constraintVa = va();
        int ordinal = getMotorState(constraintVa);
        EMotorState result = EMotorState.values()[ordinal];

        return result;
    }

    /**
     * Return the target angle of the motor. The constraint is unaffected.
     *
     * @return the angle (in radians)
     */
    public float getTargetAngle() {
        long constraintVa = va();
        float result = getTargetAngle(constraintVa);

        return result;
    }

    /**
     * Return the target angular velocity of the motor. The constraint is
     * unaffected.
     *
     * @return the angular velocity (in radians/second)
     */
    public float getTargetAngularVelocity() {
        long constraintVa = va();
        float result = getTargetAngularVelocity(constraintVa);

        return result;
    }

    /**
     * Test whether the constraint has limits. The constraint is unaffected.
     *
     * @return true if limited, otherwise false
     */
    public boolean hasLimits() {
        long constraintVa = va();
        boolean result = hasLimits(constraintVa);

        return result;
    }

    /**
     * Alter the limits.
     *
     * @param min the desired lower limit (in radians)
     * @param max the desired upper limit (in radians)
     */
    public void setLimits(float min, float max) {
        long constraintVa = va();
        setLimits(constraintVa, min, max);
    }

    /**
     * Alter the maximum friction torque when not driven by a motor.
     *
     * @param torque the desired torque (in Newton-meters)
     */
    public void setMaxFrictionTorque(float torque) {
        long constraintVa = va();
        setMaxFrictionTorque(constraintVa, torque);
    }

    /**
     * Alter the state of the motor.
     *
     * @param state the desired state (not null)
     */
    public void setMotorState(EMotorState state) {
        long constraintVa = va();
        int ordinal = state.ordinal();
        setMotorState(constraintVa, ordinal);
    }

    /**
     * Alter the target angle of the motor.
     *
     * @param angle the desired angle (in radians)
     */
    public void setTargetAngle(float angle) {
        long constraintVa = va();
        setTargetAngle(constraintVa, angle);
    }

    /**
     * Alter the target angular velocity of the motor.
     *
     * @param omega the desired angular velocity (in radians per second)
     */
    public void setTargetAngularVelocity(float omega) {
        long constraintVa = va();
        setTargetAngularVelocity(constraintVa, omega);
    }
    // *************************************************************************
    // native private methods

    native private static float getCurrentAngle(long constraintVa);

    native private static float getLimitsMax(long constraintVa);

    native private static float getLimitsMin(long constraintVa);

    native private static long getLimitsSpringSettings(long constraintVa);

    native private static float getMaxFrictionTorque(long constraintVa);

    native private static long getMotorSettings(long constraintVa);

    native private static int getMotorState(long constraintVa);

    native private static float getTargetAngle(long constraintVa);

    native private static float getTargetAngularVelocity(long constraintVa);

    native private static boolean hasLimits(long constraintVa);

    native private static void setLimits(
            long constraintVa, float min, float max);

    native private static void setMaxFrictionTorque(
            long constraintVa, float torque);

    native private static void setMotorState(long constraintVa, int ordinal);

    native private static void setTargetAngle(
            long constraintVa, float angle);

    native private static void setTargetAngularVelocity(
            long constraintVa, float omega);
}
