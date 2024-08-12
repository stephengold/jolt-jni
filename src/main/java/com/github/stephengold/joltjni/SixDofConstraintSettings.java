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
import com.github.stephengold.joltjni.enumerate.EConstraintSpace;
import com.github.stephengold.joltjni.enumerate.EConstraintSubType;
import com.github.stephengold.joltjni.enumerate.ESwingType;
import com.github.stephengold.joltjni.readonly.RVec3Arg;

/**
 * Settings used to construct a {@code SixDofConstraint}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class SixDofConstraintSettings extends TwoBodyConstraintSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified native object assigned but not owned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    SixDofConstraintSettings(long settingsVa) {
        super(settingsVa);
        setSubType(EConstraintSubType.SixDof);
    }

    /**
     * Instantiate default settings.
     */
    public SixDofConstraintSettings() {
        long settingsVa = createSixDofConstraintSettings();
        setVirtualAddress(settingsVa, null); // not owner due to ref counting
        setSubType(EConstraintSubType.SixDof);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the upper limit of the specified axis. The settings are
     * unaffected. (native field: mLimitMax)
     *
     * @param dof which axis (not null)
     * @return the upper limit
     */
    public float getLimitMax(EAxis dof) {
        long settingsVa = va();
        int ordinal = dof.ordinal();
        float result = getLimitMax(settingsVa, ordinal);

        return result;
    }

    /**
     * Return the lower limit of the specified axis. The settings are
     * unaffected. (native field: mLimitMin)
     *
     * @param dof which axis (not null)
     * @return the lower limit
     */
    public float getLimitMin(EAxis dof) {
        long settingsVa = va();
        int ordinal = dof.ordinal();
        float result = getLimitMin(settingsVa, ordinal);

        return result;
    }

    /**
     * Access the spring settings for the specified axis. The constraint
     * settings are unaffected. (native field: mLimitsSpringSettings)
     *
     * @param translationAxis which axis (not null, not a rotation axis)
     * @return a new JVM object with the pre-existing native object assigned
     */
    public SpringSettings getLimitsSpringSettings(EAxis translationAxis) {
        long constraintSettingsVa = va();
        int ordinal = translationAxis.ordinal();
        assert ordinal < 3 : ordinal;
        long springSettingsVa
                = getLimitsSpringSettings(constraintSettingsVa, ordinal);
        SpringSettings result = new SpringSettings(this, springSettingsVa);

        return result;
    }

    /**
     * Return the maximum friction of the specified axis. The settings are
     * unaffected. (native field: mMaxFriction)
     *
     * @param dof which axis (not null)
     * @return the maximum friction value
     */
    public float getMaxFriction(EAxis dof) {
        long settingsVa = va();
        int ordinal = dof.ordinal();
        float result = getMaxFriction(settingsVa, ordinal);

        return result;
    }

    /**
     * Access the motor settings for the specified axis. The constraint settings
     * are unaffected. (native field: mMotorSettings)
     *
     * @param dof which axis (not null)
     * @return a new JVM object with the pre-existing native object assigned
     */
    public MotorSettings getMotorSettings(EAxis dof) {
        long constraintSettingsVa = va();
        int ordinal = dof.ordinal();
        long motorSettingsVa
                = getMotorSettings(constraintSettingsVa, ordinal);
        MotorSettings result = new MotorSettings(this, motorSettingsVa);

        return result;
    }

    /**
     * Return the constraint location for body 1. The settings are unaffected.
     * (native field: mPosition1)
     *
     * @return a new location vector
     */
    public RVec3 getPosition1() {
        long settingsVa = va();
        double x = getPosition1X(settingsVa);
        double y = getPosition1Y(settingsVa);
        double z = getPosition1Z(settingsVa);
        RVec3 result = new RVec3(x, y, z);

        return result;
    }

    /**
     * Return the constraint location for body 2. The settings are unaffected.
     * (native field: mPosition2)
     *
     * @return a new location vector
     */
    public RVec3 getPosition2() {
        long settingsVa = va();
        double x = getPosition2X(settingsVa);
        double y = getPosition2Y(settingsVa);
        double z = getPosition2Z(settingsVa);
        RVec3 result = new RVec3(x, y, z);

        return result;
    }

    /**
     * Return which space the other properties are specified in. The settings
     * are unaffected. (native field: mSpace)
     *
     * @return an enum value (not null)
     */
    public EConstraintSpace getSpace() {
        long settingsVa = va();
        int ordinal = getSpace(settingsVa);
        EConstraintSpace result = EConstraintSpace.values()[ordinal];

        return result;
    }

    /**
     * Return type of swing constraint. The settings are unaffected. (native
     * field: mSwingType)
     *
     * @return an enum value (not null)
     */
    public ESwingType getSwingType() {
        long settingsVa = va();
        int ordinal = getSwingType(settingsVa);
        ESwingType result = ESwingType.values()[ordinal];

        return result;
    }

    /**
     * Test whether the specified axis is fixed. The settings are unaffected.
     *
     * @param dof which axis to test (not null)
     * @return true if fixed, otherwise false
     */
    public boolean isFixedAxis(EAxis dof) {
        long settingsVa = va();
        int ordinal = dof.ordinal();
        boolean result = isFixedAxis(settingsVa, ordinal);

        return result;
    }

    /**
     * Test whether the specified axis is free. The settings are unaffected.
     *
     * @param dof which axis to test (not null)
     * @return true if free, otherwise false
     */
    public boolean isFreeAxis(EAxis dof) {
        long settingsVa = va();
        int ordinal = dof.ordinal();
        boolean result = isFreeAxis(settingsVa, ordinal);

        return result;
    }

    /**
     * Make the specified axis a fixed one.
     *
     * @param dof which axis to modify (not null)
     */
    public void makeFixedAxis(EAxis dof) {
        long settingsVa = va();
        int ordinal = dof.ordinal();
        makeFixedAxis(settingsVa, ordinal);
    }

    /**
     * Make the specified axis a free one.
     *
     * @param dof which axis to modify (not null)
     */
    public void makeFreeAxis(EAxis dof) {
        long settingsVa = va();
        int ordinal = dof.ordinal();
        makeFreeAxis(settingsVa, ordinal);
    }

    /**
     * Alter the limits of the specified axis.
     *
     * @param dof which axis to modify (not null)
     * @param min the desired minimum value
     * @param max the desired maximum value
     */
    public void setLimitedAxis(EAxis dof, float min, float max) {
        long settingsVa = va();
        int ordinal = dof.ordinal();
        setLimitedAxis(settingsVa, ordinal, min, max);
    }

    /**
     * Alter the upper limit of the specified axis. (native field: mLimitMax)
     *
     * @param dof which axis to modify (not null)
     * @param max the desired limit value (default=MAX_VALUE)
     */
    public void setLimitMax(EAxis dof, float max) {
        long settingsVa = va();
        int ordinal = dof.ordinal();
        setLimitMax(settingsVa, ordinal, max);
    }

    /**
     * Alter the lower limit of the specified axis. (native field: mLimitMin)
     *
     * @param dof which axis to modify (not null)
     * @param min the desired limit value (default=-MAX_VALUE)
     */
    public void setLimitMin(EAxis dof, float min) {
        long settingsVa = va();
        int ordinal = dof.ordinal();
        setLimitMin(settingsVa, ordinal, min);
    }

    /**
     * Alter the maximum friction of the specified axis. (native field:
     * mMaxFriction)
     *
     * @param dof which axis to modify (not null)
     * @param friction the desired value (default=0)
     */
    public void setMaxFriction(EAxis dof, float friction) {
        long settingsVa = va();
        int ordinal = dof.ordinal();
        setMaxFriction(settingsVa, ordinal, friction);
    }

    /**
     * Alter the constraint location for body 1. (native field: mPosition1)
     *
     * @param location the desired location (not null, unaffected,
     * default=(0,0,0))
     */
    public void setPosition1(RVec3Arg location) {
        long settingsVa = va();
        double x = location.xx();
        double y = location.yy();
        double z = location.zz();
        setPosition1(settingsVa, x, y, z);
    }

    /**
     * Alter the constraint location for body 2. (native field: mPosition2)
     *
     * @param location the desired location (not null, unaffected,
     * default=(0,0,0))
     */
    public void setPosition2(RVec3Arg location) {
        long settingsVa = va();
        double x = location.xx();
        double y = location.yy();
        double z = location.zz();
        setPosition2(settingsVa, x, y, z);
    }

    /**
     * Alter which space the other properties are specified in. (native field:
     * mSpace)
     *
     * @param space enum value (not null, default=WorldSpace)
     */
    public void setSpace(EConstraintSpace space) {
        long settingsVa = va();
        int ordinal = space.ordinal();
        setSpace(settingsVa, ordinal);
    }

    /**
     * Alter the type of swing constraint. (native field: mSwingType)
     *
     * @param swingType enum value (not null, default=Cone)
     */
    public void setSwingType(ESwingType swingType) {
        long settingsVa = va();
        int ordinal = swingType.ordinal();
        setSwingType(settingsVa, ordinal);
    }
    // *************************************************************************
    // native private methods

    native private static long createSixDofConstraintSettings();

    native private static float getMaxFriction(long settingsVa, int dof);

    native private static float getLimitMax(long settingsVa, int dof);

    native private static float getLimitMin(long settingsVa, int dof);

    native private static long getLimitsSpringSettings(
            long settingsVa, int dof);

    native private static long getMotorSettings(long settingsVa, int dof);

    native private static double getPosition1X(long settingsVa);

    native private static double getPosition1Y(long settingsVa);

    native private static double getPosition1Z(long settingsVa);

    native private static double getPosition2X(long settingsVa);

    native private static double getPosition2Y(long settingsVa);

    native private static double getPosition2Z(long settingsVa);

    native private static int getSpace(long settingsVa);

    native private static int getSwingType(long settingsVa);

    native private static boolean isFixedAxis(long settingsVa, int ordinal);

    native private static boolean isFreeAxis(long settingsVa, int ordinal);

    native private static void makeFixedAxis(long settingsVa, int ordinal);

    native private static void makeFreeAxis(long settingsVa, int ordinal);

    native private static void setLimitedAxis(
            long settingsVa, int ordinal, float min, float max);

    native private static void setLimitMax(
            long settingsVa, int dof, float max);

    native private static void setLimitMin(
            long settingsVa, int dof, float min);

    native private static void setMaxFriction(
            long settingsVa, int dof, float friction);

    native private static void setPosition1(
            long settingsVa, double x, double y, double z);

    native private static void setPosition2(
            long settingsVa, double x, double y, double z);

    native private static void setSpace(long settingsVa, int ordinal);

    native private static void setSwingType(long settingsVa, int ordinal);
}
