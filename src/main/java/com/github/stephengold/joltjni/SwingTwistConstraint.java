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
import com.github.stephengold.joltjni.readonly.QuatArg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * A {@code TwoBodyConstraint} that only allows limited rotation.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class SwingTwistConstraint extends TwoBodyConstraint {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a constraint with the specified native object assigned but
     * not owned.
     *
     * @param constraintVa the virtual address of the native object to assign
     * (not zero)
     */
    SwingTwistConstraint(long constraintVa) {
        super(constraintVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Access the settings of the swing motor.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public MotorSettings getSwingMotorSettings() {
        long constraintVa = va();
        long settingsVa = getSwingMotorSettings(constraintVa);
        MotorSettings result = new MotorSettings(this, settingsVa);
        return result;
    }

    /**
     * Access the settings of the twist motor.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public MotorSettings getTwistMotorSettings() {
        long constraintVa = va();
        long settingsVa = getTwistMotorSettings(constraintVa);
        MotorSettings result = new MotorSettings(this, settingsVa);

        return result;
    }

    /**
     * Alter the maximum friction torque when not driven by a motor.
     *
     * @param torque the desired limit (in Newton-meters)
     */
    public void setMaxFrictionTorque(float torque) {
        long settingsVa = va();
        setMaxFrictionTorque(settingsVa, torque);
    }

    /**
     * Alter the normal half-cone angle.
     *
     * @param angle the desired angle (in radians)
     */
    public void setNormalHalfConeAngle(float angle) {
        long settingsVa = va();
        setNormalHalfConeAngle(settingsVa, angle);
    }

    /**
     * Alter the half-angle of the plane cone.
     *
     * @param angle the desired limit (in radians)
     */
    public void setPlaneHalfConeAngle(float angle) {
        long settingsVa = va();
        setPlaneHalfConeAngle(settingsVa, angle);
    }

    /**
     * Alter the state of the swing motor.
     *
     * @param motorState an enum value (not null)
     */
    public void setSwingMotorState(EMotorState motorState) {
        long settingsVa = va();
        int ordinal = motorState.ordinal();
        setSwingMotorState(settingsVa, ordinal);
    }

    /**
     * Alter the maximum twist angle.
     *
     * @param angle the desired limit (in radians, &ge;-Pi, &le;Pi)
     */
    public void setTwistMaxAngle(float angle) {
        long settingsVa = va();
        setTwistMaxAngle(settingsVa, angle);
    }

    /**
     * Alter the minimum twist angle.
     *
     * @param angle the desired limit (in radians &ge;-Pi, &le;Pi)
     */
    public void setTwistMinAngle(float angle) {
        long settingsVa = va();
        setTwistMinAngle(settingsVa, angle);
    }

    /**
     * Alter the state of the twist motor.
     *
     * @param motorState an enum value (not null)
     */
    public void setTwistMotorState(EMotorState motorState) {
        long settingsVa = va();
        int ordinal = motorState.ordinal();
        setTwistMotorState(settingsVa, ordinal);
    }

    /**
     * Alter the target angular velocity of body 2 in the constraint space of
     * body 2.
     *
     * @param omega the desired angular velocity (in radians per second, not
     * null, unaffected)
     */
    public void setTargetAngularVelocityCs(Vec3Arg omega) {
        long settingsVa = va();
        float wx = omega.getX();
        float wy = omega.getY();
        float wz = omega.getZ();
        setTargetAngularVelocityCs(settingsVa, wx, wy, wz);
    }

    /**
     * Alter the target orientation in constraint space.
     *
     * @param orientation the desired orientation (not null, unaffected)
     */
    public void setTargetOrientationCs(QuatArg orientation) {
        long settingsVa = va();
        float qw = orientation.getW();
        float qx = orientation.getX();
        float qy = orientation.getY();
        float qz = orientation.getZ();
        setTargetOrientationCs(settingsVa, qx, qy, qz, qw);
    }
    // *************************************************************************
    // native private methods

    native private static long getSwingMotorSettings(long constraintVa);

    native private static long getTwistMotorSettings(long constraintVa);

    native private static void setMaxFrictionTorque(
            long settingsVa, float torque);

    native private static void setNormalHalfConeAngle(
            long settingsVa, float angle);

    native private static void setPlaneHalfConeAngle(
            long settingsVa, float angle);

    native private static void setSwingMotorState(long settingsVa, int ordinal);

    native private static void setTwistMaxAngle(long settingsVa, float angle);

    native private static void setTwistMinAngle(long settingsVa, float angle);

    native private static void setTwistMotorState(long settingsVa, int ordinal);

    native private static void setTargetAngularVelocityCs(
            long settingsVa, float wx, float wy, float wz);

    native private static void setTargetOrientationCs(
            long settingsVa, float qx, float qy, float qz, float qw);
}
