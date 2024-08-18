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
 * Settings used to construct a constraint motor.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class MotorSettings extends JoltPhysicsObject {
    // *************************************************************************
    // fields

    /**
     * prevent premature garbage collection of the underlying {@code Constraint}
     * if any
     */
    final private ConstraintRef constraintRef;
    /**
     * prevent premature garbage collection of the underlying
     * {@code ConstraintSettings} if any
     */
    final private ConstraintSettingsRef constraintSettingsRef;
    // *************************************************************************
    // constructors

    /**
     * Instantiate settings with the specified native object assigned but not
     * owned.
     *
     * @param constraint the underlying {@code Constraint} (not null)
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    MotorSettings(Constraint constraint, long settingsVa) {
        this.constraintRef = constraint.toRef();
        this.constraintSettingsRef = null;

        setVirtualAddress(settingsVa, null);
    }

    /**
     * Instantiate settings with the specified native object assigned but not
     * owned.
     *
     * @param constraintSettings the underlying {@code ConstraintSettings} (not
     * null)
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    MotorSettings(ConstraintSettings constraintSettings, long settingsVa) {
        this.constraintRef = null;
        this.constraintSettingsRef = constraintSettings.toRef();

        setVirtualAddress(settingsVa, null);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Access the underlying {@code Constraint}, if any.
     *
     * @return the pre-existing instance, or null if none
     */
    public Constraint getConstraint() {
        return constraintRef.getPtr();
    }

    /**
     * Access the underlying {@code ConstraintSettings}, if any.
     *
     * @return the pre-existing instance, or null if none
     */
    public ConstraintSettings getConstraintSettings() {
        return constraintSettingsRef.getPtr();
    }

    /**
     * Return the maximum force to apply in a linear constraint. Ignored in an
     * angular motor. The settings are unaffected. (native field:
     * mMaxForceLimit)
     *
     * @return the amount of force (in Newtons, typically positive)
     */
    public float getMaxForceLimit() {
        long settingsVa = va();
        float result = getMaxForceLimit(settingsVa);

        return result;
    }

    /**
     * Return the maximum torque to apply in an angular constraint. Ignored in a
     * linear motor. The settings are unaffected. (native field:
     * mMaxTorqueLimit)
     *
     * @return the amount of torque (in Newton-meters, typically positive)
     */
    public float getMaxTorqueLimit() {
        long settingsVa = va();
        float result = getMaxTorqueLimit(settingsVa);

        return result;
    }

    /**
     * Return the minimum force to apply in a linear constraint. Ignored in an
     * angular motor. The settings are unaffected. (native field:
     * mMinForceLimit)
     *
     * @return the amount of force (in Newtons, typically negative)
     */
    public float getMinForceLimit() {
        long settingsVa = va();
        float result = getMinForceLimit(settingsVa);

        return result;
    }

    /**
     * Return the minimum torque to apply in an angular constraint. Ignored in a
     * linear motor. The settings are unaffected. (native field:
     * mMinTorqueLimit)
     *
     * @return the amount of torque (in Newton-meters, typically negative)
     */
    public float getMinTorqueLimit() {
        long settingsVa = va();
        float result = getMinTorqueLimit(settingsVa);

        return result;
    }

    /**
     * Access the settings for the spring used to drive to the position target.
     * Ignored in a velocity motor. The motor settings are unaffected. (native
     * field: mSpringSettings)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
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
     * @return true if valid, otherwise false
     */
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
     * angular motor. (native field: mMaxForceLimit)
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
     * linear motor. (native field: mMaxTorqueLimit)
     *
     * @param torque the desired amount of torque (in Newton-meters, typically
     * positive, default=MAX_VALUE)
     */
    public void setMaxTorqueLimit(float torque) {
        long settingsVa = va();
        setMaxTorqueLimit(settingsVa, torque);
    }

    /**
     * Alter the minimum force to apply in a linear constraint. Ignored in an
     * angular motor. (native field: mMinForceLimit)
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
     * linear motor. (native field: mMinTorqueLimit)
     *
     * @param torque the desired amount of torque (in Newton-meters, typically
     * negative, default=-MAX_VALUE)
     */
    public void setMinTorqueLimit(float torque) {
        long settingsVa = va();
        setMinTorqueLimit(settingsVa, torque);
    }

    /**
     * Alter both torque limits of an angular motor to be symmetrical.
     *
     * @param limit the desired upper limit (in Newton-meters)
     */
    public void setTorqueLimit(float limit) {
        long settingsVa = va();
        setTorqueLimit(settingsVa, limit);
    }

    /**
     * Alter both torque limits of an angular motor.
     *
     * @param min the desired lower limit (in Newton-meters, default=-MAX_FLOAT)
     * @param max the desired upper limit (in Newton-meters, default=MAX_FLOAT)
     */
    public void setTorqueLimits(float min, float max) {
        long settingsVa = va();
        setTorqueLimits(settingsVa, min, max);
    }
    // *************************************************************************
    // native private methods

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
