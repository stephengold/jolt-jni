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

import com.github.stephengold.joltjni.readonly.ConstMotorSettings;

/**
 * Settings used to construct a constraint motor.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class MotorSettings
        extends JoltPhysicsObject
        implements ConstMotorSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate default settings.
     */
    public MotorSettings() {
        long settingsVa = createDefault();
        setVirtualAddress(settingsVa, () -> free(settingsVa));
    }

    /**
     * Instantiate settings for the specified spring parameters with no limits.
     *
     * @param frequency the desired spring frequency (in Hertz)
     * @param damping the desired damping
     */
    public MotorSettings(float frequency, float damping) {
        long settingsVa = createUnlimited(frequency, damping);
        setVirtualAddress(settingsVa, () -> free(settingsVa));
    }

    /**
     * Instantiate settings for the specified spring parameters and limits.
     *
     * @param frequency the desired spring frequency (in Hertz)
     * @param damping the desired damping
     * @param forceLimit the desired force limit
     * @param torqueLimit the desired torque limit
     */
    public MotorSettings(float frequency, float damping, float forceLimit,
            float torqueLimit) {
        long settingsVa
                = createLimited(frequency, damping, forceLimit, torqueLimit);
        setVirtualAddress(settingsVa, () -> free(settingsVa));
    }

    /**
     * Instantiate settings with the specified container and native object.
     *
     * @param container the containing object, or {@code null} if none
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    MotorSettings(JoltPhysicsObject container, long settingsVa) {
        super(container, settingsVa);
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public MotorSettings(ConstMotorSettings original) {
        long originalVa = original.targetVa();
        long copyVa = createCopy(originalVa);
        setVirtualAddress(copyVa, () -> free(copyVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Access the underlying {@code Constraint}, if any.
     *
     * @return the pre-existing instance, or {@code null} if none
     */
    @Override
    public Constraint getConstraint() {
        JoltPhysicsObject container = getContainingObject();
        Constraint result;
        if (container instanceof ConstraintRef) {
            result = ((ConstraintRef) container).getPtr();
        } else {
            result = null;
        }

        return result;
    }

    /**
     * Access the underlying {@code ConstraintSettings}, if any.
     *
     * @return the pre-existing instance, or {@code null} if none
     */
    @Override
    public ConstraintSettings getConstraintSettings() {
        JoltPhysicsObject container = getContainingObject();
        ConstraintSettings result;
        if (container instanceof ConstraintSettingsRef) {
            result = ((ConstraintSettingsRef) container).getPtr();
        } else {
            result = null;
        }

        return result;
    }

    /**
     * Return the maximum force to apply in a linear constraint. Ignored in an
     * angular motor. The settings are unaffected. (native attribute:
     * mMaxForceLimit)
     *
     * @return the amount of force (in Newtons, typically positive)
     */
    @Override
    public float getMaxForceLimit() {
        long settingsVa = va();
        float result = getMaxForceLimit(settingsVa);

        return result;
    }

    /**
     * Return the maximum torque to apply in an angular constraint. Ignored in a
     * linear motor. The settings are unaffected. (native attribute:
     * mMaxTorqueLimit)
     *
     * @return the amount of torque (in Newton.meters, typically positive)
     */
    @Override
    public float getMaxTorqueLimit() {
        long settingsVa = va();
        float result = getMaxTorqueLimit(settingsVa);

        return result;
    }

    /**
     * Return the minimum force to apply in a linear constraint. Ignored in an
     * angular motor. The settings are unaffected. (native attribute:
     * mMinForceLimit)
     *
     * @return the amount of force (in Newtons, typically negative)
     */
    @Override
    public float getMinForceLimit() {
        long settingsVa = va();
        float result = getMinForceLimit(settingsVa);

        return result;
    }

    /**
     * Return the minimum torque to apply in an angular constraint. Ignored in a
     * linear motor. The settings are unaffected. (native attribute:
     * mMinTorqueLimit)
     *
     * @return the amount of torque (in Newton.meters, typically negative)
     */
    @Override
    public float getMinTorqueLimit() {
        long settingsVa = va();
        float result = getMinTorqueLimit(settingsVa);

        return result;
    }

    /**
     * Access the settings for the spring used to drive to the position target.
     * Ignored in a velocity motor. The motor settings are unaffected. (native
     * attribute: mSpringSettings)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    @Override
    public SpringSettings getSpringSettings() {
        long motorSettingsVa = va();
        long springSettingsVa = getSpringSettings(motorSettingsVa);
        ConstraintSettings constraintSettings = getConstraintSettings();
        SpringSettings result
                = new SpringSettings(constraintSettings, springSettingsVa);

        return result;
    }

    /**
     * Test whether the settings are valid. They are unaffected.
     *
     * @return {@code true} if valid, otherwise {@code false}
     */
    @Override
    public boolean isValid() {
        long settingsVa = va();
        boolean result = isValid(settingsVa);

        return result;
    }

    /**
     * Alter both force limits of a linear motor to be symmetrical.
     *
     * @param limit the desired limit (in Newtons)
     */
    public void setForceLimit(float limit) {
        long settingsVa = va();
        setForceLimit(settingsVa, limit);
    }

    /**
     * Alter both force limits of a linear motor.
     *
     * @param min the desired lower limit (in Newtons, default=-MAX_FLOAT)
     * @param max the desired upper limit (in Newtons, default=MAX_FLOAT)
     */
    public void setForceLimits(float min, float max) {
        long settingsVa = va();
        setForceLimits(settingsVa, min, max);
    }

    /**
     * Alter the maximum force to apply in a linear constraint. Ignored in an
     * angular motor. (native attribute: mMaxForceLimit)
     *
     * @param force the desired amount of force (in Newtons, typically positive,
     * default=MAX_VALUE)
     */
    public void setMaxForceLimit(float force) {
        long settingsVa = va();
        setMaxForceLimit(settingsVa, force);
    }

    /**
     * Alter the maximum torque to apply in an angular constraint. Ignored in a
     * linear motor. (native attribute: mMaxTorqueLimit)
     *
     * @param torque the desired amount of torque (in Newton.meters, typically
     * positive, default=MAX_VALUE)
     */
    public void setMaxTorqueLimit(float torque) {
        long settingsVa = va();
        setMaxTorqueLimit(settingsVa, torque);
    }

    /**
     * Alter the minimum force to apply in a linear constraint. Ignored in an
     * angular motor. (native attribute: mMinForceLimit)
     *
     * @param force the desired amount of force (in Newtons, typically negative,
     * default=-MAX_VALUE)
     */
    public void setMinForceLimit(float force) {
        long settingsVa = va();
        setMinForceLimit(settingsVa, force);
    }

    /**
     * Alter the minimum torque to apply in an angular constraint. Ignored in a
     * linear motor. (native attribute: mMinTorqueLimit)
     *
     * @param torque the desired amount of torque (in Newton.meters, typically
     * negative, default=-MAX_VALUE)
     */
    public void setMinTorqueLimit(float torque) {
        long settingsVa = va();
        setMinTorqueLimit(settingsVa, torque);
    }

    /**
     * Alter both torque limits of an angular motor to be symmetrical.
     *
     * @param limit the desired upper limit (in Newton.meters)
     */
    public void setTorqueLimit(float limit) {
        long settingsVa = va();
        setTorqueLimit(settingsVa, limit);
    }

    /**
     * Alter both torque limits of an angular motor.
     *
     * @param min the desired lower limit (in Newton.meters, default=-MAX_FLOAT)
     * @param max the desired upper limit (in Newton.meters, default=MAX_FLOAT)
     */
    public void setTorqueLimits(float min, float max) {
        long settingsVa = va();
        setTorqueLimits(settingsVa, min, max);
    }
    // *************************************************************************
    // native private methods

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static long createLimited(float frequency, float damping,
            float forceLimit, float torqueLimit);

    native private static long createUnlimited(float frequency, float damping);

    native private static void free(long settingsVa);

    native private static float getMaxForceLimit(long settingsVa);

    native private static float getMaxTorqueLimit(long settingsVa);

    native private static float getMinForceLimit(long settingsVa);

    native private static float getMinTorqueLimit(long settingsVa);

    native private static long getSpringSettings(long motorSettingsVa);

    native private static boolean isValid(long settingsVa);

    native private static void setForceLimit(long settingsVa, float limit);

    native private static void setForceLimits(
            long settingsVa, float min, float max);

    native private static void setMaxForceLimit(long settingsVa, float force);

    native private static void setMaxTorqueLimit(long settingsVa, float torque);

    native private static void setMinForceLimit(long settingsVa, float force);

    native private static void setMinTorqueLimit(long settingsVa, float torque);

    native private static void setTorqueLimit(long settingsVa, float limit);

    native private static void setTorqueLimits(
            long settingsVa, float min, float max);
}
