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
 * A {@code TwoBodyConstraint} that allows translation only along one axis and
 * disables rotation, eliminating 5 degrees of freedom.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class SliderConstraint extends TwoBodyConstraint {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a constraint with the specified native object assigned but
     * not owned.
     *
     * @param constraintVa the virtual address of the native object to assign
     * (not zero)
     */
    SliderConstraint(long constraintVa) {
        super(constraintVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the current offset from rest position. The constraint is
     * unaffected.
     *
     * @return the offset (in meters)
     */
    public float getCurrentPosition() {
        long constraintVa = va();
        float result = getCurrentPosition(constraintVa);

        return result;
    }

    /**
     * Return the upper limit of translation. The constraint is unaffected.
     *
     * @return the upper limit (in meters)
     */
    public float getLimitsMax() {
        long constraintVa = va();
        float result = getLimitsMax(constraintVa);

        return result;
    }

    /**
     * Return the lower limit of translation. The constraint is unaffected.
     *
     * @return the lower limit (in meters)
     */
    public float getLimitsMin() {
        long constraintVa = va();
        float result = getLimitsMin(constraintVa);

        return result;
    }

    /**
     * Access the spring settings.
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
     * Return the maximum friction force when not driven by a motor. The
     * constraint is unaffected.
     *
     * @return the force (in Newtons)
     */
    public float getMaxFrictionForce() {
        long constraintVa = va();
        float result = getMaxFrictionForce(constraintVa);

        return result;
    }

    /**
     * Access the motor settings.
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
     * Return the target position of the motor. The constraint is unaffected.
     *
     * @return the linear offset (in meters)
     */
    public float getTargetPosition() {
        long constraintVa = va();
        float result = getTargetPosition(constraintVa);

        return result;
    }

    /**
     * Return the target velocity of the motor. The constraint is unaffected.
     *
     * @return the velocity (in meters/second)
     */
    public float getTargetVelocity() {
        long constraintVa = va();
        float result = getTargetVelocity(constraintVa);

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
     * @param min the desired lower limit (in meters)
     * @param max the desired upper limit (in meters)
     */
    public void setLimits(float min, float max) {
        long constraintVa = va();
        setLimits(constraintVa, min, max);
    }

    /**
     * Alter the maximum friction force when not driven by a motor.
     *
     * @param force the desired force (in Newtons)
     */
    public void setMaxFrictionForce(float force) {
        long constraintVa = va();
        setMaxFrictionForce(constraintVa, force);
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
     * Alter the target position of the motor.
     *
     * @param offset the desired linear offset (in meters)
     */
    public void setTargetPosition(float offset) {
        long constraintVa = va();
        setTargetPosition(constraintVa, offset);
    }

    /**
     * Alter the target velocity of the motor.
     *
     * @param velocity the desired linear velocity (in meters per second)
     */
    public void setTargetVelocity(float velocity) {
        long constraintVa = va();
        setTargetVelocity(constraintVa, velocity);
    }
    // *************************************************************************
    // native private methods

    native private static float getCurrentPosition(long constraintVa);

    native private static float getLimitsMax(long constraintVa);

    native private static float getLimitsMin(long constraintVa);

    native private static long getLimitsSpringSettings(long constraintVa);

    native private static float getMaxFrictionForce(long constraintVa);

    native private static long getMotorSettings(long constraintVa);

    native private static int getMotorState(long constraintVa);

    native private static float getTargetPosition(long constraintVa);

    native private static float getTargetVelocity(long constraintVa);

    native private static boolean hasLimits(long constraintVa);

    native private static void setLimits(
            long constraintVa, float min, float max);

    native private static void setMaxFrictionForce(
            long constraintVa, float force);

    native private static void setMotorState(long constraintVa, int ordinal);

    native private static void setTargetPosition(
            long constraintVa, float position);

    native private static void setTargetVelocity(
            long constraintVa, float velocity);
}
