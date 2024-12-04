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
import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * Settings used to construct a {@code SixDofConstraint}. (native type:
 * SixDOFConstraintSettings)
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
        setVirtualAddress(settingsVa); // not owner due to ref counting
        setSubType(EConstraintSubType.SixDof);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Copy the X axis for body 1. The settings are unaffected. (native
     * attribute: mAxisX1)
     *
     * @return a new direction vector
     */
    public Vec3 getAxisX1() {
        long settingsVa = va();
        float x = getAxisX1X(settingsVa);
        float y = getAxisX1Y(settingsVa);
        float z = getAxisX1Z(settingsVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Copy the X axis for body 2. The settings are unaffected. (native
     * attribute: mAxisX2)
     *
     * @return a new direction vector
     */
    public Vec3 getAxisX2() {
        long settingsVa = va();
        float x = getAxisX2X(settingsVa);
        float y = getAxisX2Y(settingsVa);
        float z = getAxisX2Z(settingsVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Copy the Y axis for body 1. The settings are unaffected. (native
     * attribute: mAxisY1)
     *
     * @return a new direction vector
     */
    public Vec3 getAxisY1() {
        long settingsVa = va();
        float x = getAxisY1X(settingsVa);
        float y = getAxisY1Y(settingsVa);
        float z = getAxisY1Z(settingsVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Copy the Y axis for body 2. The settings are unaffected. (native
     * attribute: mAxisY2)
     *
     * @return a new direction vector
     */
    public Vec3 getAxisY2() {
        long settingsVa = va();
        float x = getAxisY2X(settingsVa);
        float y = getAxisY2Y(settingsVa);
        float z = getAxisY2Z(settingsVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Return the upper limit of the specified degree of freedom. The settings
     * are unaffected. (native attribute: mLimitMax)
     *
     * @param dof which DOF (not null)
     * @return the upper limit
     */
    public float getLimitMax(EAxis dof) {
        int dofIndex = dof.ordinal();
        float result = getLimitMax(dofIndex);

        return result;
    }

    /**
     * Return the upper limit of the specified degree of freedom. The settings
     * are unaffected. (native attribute: mLimitMax)
     *
     * @param dofIndex which DOF (&ge;0, &lt;6)
     * @return the upper limit
     */
    public float getLimitMax(int dofIndex) {
        long settingsVa = va();
        float result = getLimitMax(settingsVa, dofIndex);

        return result;
    }

    /**
     * Return the lower limit of the specified degree of freedom. The settings
     * are unaffected. (native attribute: mLimitMin)
     *
     * @param dof which DOF (not null)
     * @return the lower limit
     */
    public float getLimitMin(EAxis dof) {
        int dofIndex = dof.ordinal();
        float result = getLimitMin(dofIndex);

        return result;
    }

    /**
     * Return the lower limit of the specified degree of freedom. The settings
     * are unaffected. (native attribute: mLimitMin)
     *
     * @param dofIndex which DOF (&ge;0,&lt;6)
     * @return the lower limit
     */
    public float getLimitMin(int dofIndex) {
        long settingsVa = va();
        float result = getLimitMin(settingsVa, dofIndex);

        return result;
    }

    /**
     * Access the spring settings for the specified degree of freedom. The
     * constraint settings are unaffected. (native attribute:
     * mLimitsSpringSettings)
     *
     * @param translationDof which DOF (not null, not a rotation DOF)
     * @return a new JVM object with the pre-existing native object assigned
     */
    public SpringSettings getLimitsSpringSettings(EAxis translationDof) {
        long constraintSettingsVa = va();
        int dofIndex = translationDof.ordinal();
        assert dofIndex < 3 : dofIndex;
        long springSettingsVa
                = getLimitsSpringSettings(constraintSettingsVa, dofIndex);
        SpringSettings result = new SpringSettings(this, springSettingsVa);

        return result;
    }

    /**
     * Return the maximum friction of the specified degree of freedom. The
     * settings are unaffected. (native attribute: mMaxFriction)
     *
     * @param dof which DOF (not null)
     * @return the maximum friction value
     */
    public float getMaxFriction(EAxis dof) {
        long settingsVa = va();
        int dofIndex = dof.ordinal();
        float result = getMaxFriction(settingsVa, dofIndex);

        return result;
    }

    /**
     * Access the motor settings for the specified degree of freedom. (native
     * attribute: mMotorSettings)
     *
     * @param dof which DOF (not null)
     * @return a new JVM object with the pre-existing native object assigned
     */
    public MotorSettings getMotorSettings(EAxis dof) {
        int dofIndex = dof.ordinal();
        MotorSettings result = getMotorSettings(dofIndex);

        return result;
    }

    /**
     * Access the motor settings for the specified degree of freedom. (native
     * attribute: mMotorSettings)
     *
     * @param dofIndex which DOF (&ge;0, &lt;6)
     * @return a new JVM object with the pre-existing native object assigned
     */
    public MotorSettings getMotorSettings(int dofIndex) {
        long constraintSettingsVa = va();
        long motorSettingsVa
                = getMotorSettings(constraintSettingsVa, dofIndex);
        MotorSettings result = new MotorSettings(this, motorSettingsVa);

        return result;
    }

    /**
     * Copy the constraint location for body 1. The settings are unaffected.
     * (native attribute: mPosition1)
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
     * Copy the constraint location for body 2. The settings are unaffected.
     * (native attribute: mPosition2)
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
     * are unaffected. (native attribute: mSpace)
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
     * attribute: mSwingType)
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
     * Test whether the specified degree of freedom is fixed. The settings are
     * unaffected.
     *
     * @param dof which DOF to test (not null)
     * @return {@code true} if fixed, otherwise {@code false}
     */
    public boolean isFixedAxis(EAxis dof) {
        long settingsVa = va();
        int dofIndex = dof.ordinal();
        boolean result = isFixedAxis(settingsVa, dofIndex);

        return result;
    }

    /**
     * Test whether the specified degree of freedom is free. The settings are
     * unaffected.
     *
     * @param dof which DOF to test (not null)
     * @return {@code true} if free, otherwise {@code false}
     */
    public boolean isFreeAxis(EAxis dof) {
        long settingsVa = va();
        int dofIndex = dof.ordinal();
        boolean result = isFreeAxis(settingsVa, dofIndex);

        return result;
    }

    /**
     * Make the specified degree of freedom a fixed one.
     *
     * @param dof which DOF to modify (not null)
     */
    public void makeFixedAxis(EAxis dof) {
        int dofIndex = dof.ordinal();
        makeFixedAxis(dofIndex);
    }

    /**
     * Make the specified degree of freedom a fixed one.
     *
     * @param dofIndex which DOF to modify (&ge;0, &lt;6)
     */
    public void makeFixedAxis(int dofIndex) {
        long settingsVa = va();
        makeFixedAxis(settingsVa, dofIndex);
    }

    /**
     * Make the specified degree of freedom a free one.
     *
     * @param dof which DOF to modify (not null)
     */
    public void makeFreeAxis(EAxis dof) {
        int dofIndex = dof.ordinal();
        makeFreeAxis(dofIndex);
    }

    /**
     * Make the specified degree of freedom a free one.
     *
     * @param dofIndex which DOF to modify (&ge;0, &lt;5)
     */
    public void makeFreeAxis(int dofIndex) {
        long settingsVa = va();
        makeFreeAxis(settingsVa, dofIndex);
    }

    /**
     * Alter the X axis for body 1. (native attribute: mAxisX1)
     *
     * @param axis the desired direction vector (default=(1,0,0))
     * @return the argument, for chaining
     */
    public Vec3Arg setAxisX1(Vec3Arg axis) {
        long settingsVa = va();
        float x = axis.getX();
        float y = axis.getY();
        float z = axis.getZ();
        setAxisX1(settingsVa, x, y, z);

        return axis;
    }

    /**
     * Alter the X axis for body 2. (native attribute: mAxisX2)
     *
     * @param axis the desired direction vector (default=(1,0,0))
     * @return the argument, for chaining
     */
    public Vec3Arg setAxisX2(Vec3Arg axis) {
        long settingsVa = va();
        float x = axis.getX();
        float y = axis.getY();
        float z = axis.getZ();
        setAxisX2(settingsVa, x, y, z);

        return axis;
    }

    /**
     * Alter the Y axis for body 1. (native attribute: mAxisY1)
     *
     * @param axis the desired direction vector (default=(0,1,0))
     * @return the argument, for chaining
     */
    public Vec3Arg setAxisY1(Vec3Arg axis) {
        long settingsVa = va();
        float x = axis.getX();
        float y = axis.getY();
        float z = axis.getZ();
        setAxisY1(settingsVa, x, y, z);

        return axis;
    }

    /**
     * Alter the Y axis for body 2. (native attribute: mAxisY2)
     *
     * @param axis the desired direction vector (default=(0,1,0))
     * @return the argument, for chaining
     */
    public Vec3Arg setAxisY2(Vec3Arg axis) {
        long settingsVa = va();
        float x = axis.getX();
        float y = axis.getY();
        float z = axis.getZ();
        setAxisY2(settingsVa, x, y, z);

        return axis;
    }

    /**
     * Alter the limits of the specified degree of freedom.
     *
     * @param dof which DOF to modify (not null)
     * @param min the desired minimum value
     * @param max the desired maximum value
     */
    public void setLimitedAxis(EAxis dof, float min, float max) {
        int dofIndex = dof.ordinal();
        setLimitedAxis(dofIndex, min, max);
    }

    /**
     * Alter the limits of the specified degree of freedom.
     *
     * @param dofIndex which DOF to modify (&ge;0, &lt;6)
     * @param min the desired minimum value
     * @param max the desired maximum value
     */
    public void setLimitedAxis(int dofIndex, float min, float max) {
        long settingsVa = va();
        setLimitedAxis(settingsVa, dofIndex, min, max);
    }

    /**
     * Alter the upper limit of the specified degree of freedom. (native
     * attribute: mLimitMax)
     *
     * @param dof which DOF to modify (not null)
     * @param max the desired limit value (default=MAX_VALUE)
     */
    public void setLimitMax(EAxis dof, float max) {
        long settingsVa = va();
        int dofIndex = dof.ordinal();
        setLimitMax(settingsVa, dofIndex, max);
    }

    /**
     * Alter the lower limit of the specified DOF. (native attribute: mLimitMin)
     *
     * @param dof which DOF to modify (not null)
     * @param min the desired limit value (default=-MAX_VALUE)
     */
    public void setLimitMin(EAxis dof, float min) {
        long settingsVa = va();
        int dofIndex = dof.ordinal();
        setLimitMin(settingsVa, dofIndex, min);
    }

    /**
     * Alter the maximum friction of the specified degree of freedom. (native
     * attribute: mMaxFriction)
     *
     * @param dof which DOF to modify (not null)
     * @param friction the desired value (default=0)
     */
    public void setMaxFriction(EAxis dof, float friction) {
        long settingsVa = va();
        int dofIndex = dof.ordinal();
        setMaxFriction(settingsVa, dofIndex, friction);
    }

    /**
     * Alter the motor settings for the specified degree of freedom. (native
     * attribute: mMotorSettings)
     *
     * @param dof which DOF (not null)
     * @param motorSettings the settings to copy (not null, not affected)
     */
    public void setMotorSettings(EAxis dof, MotorSettings motorSettings) {
        int dofIndex = dof.ordinal();
        setMotorSettings(dofIndex, motorSettings);
    }

    /**
     * Alter the motor settings for the specified degree of freedom. (native
     * attribute: mMotorSettings)
     *
     * @param dofIndex which DOF (&ge;0, &lt;6)
     * @param motorSettings the settings to copy (not null, not affected)
     */
    public void setMotorSettings(int dofIndex, MotorSettings motorSettings) {
        long constraintSettingsVa = va();
        long motorSettingsVa = motorSettings.va();
        setMotorSettings(constraintSettingsVa, dofIndex, motorSettingsVa);
    }

    /**
     * Alter the constraint location for body 1. (native attribute: mPosition1)
     *
     * @param location the desired location (not null, unaffected,
     * default=(0,0,0))
     * @return the argument, for chaining
     */
    public RVec3Arg setPosition1(RVec3Arg location) {
        long settingsVa = va();
        double x = location.xx();
        double y = location.yy();
        double z = location.zz();
        setPosition1(settingsVa, x, y, z);

        return location;
    }

    /**
     * Alter the constraint location for body 2. (native attribute: mPosition2)
     *
     * @param location the desired location (not null, unaffected,
     * default=(0,0,0))
     * @return the argument, for chaining
     */
    public RVec3Arg setPosition2(RVec3Arg location) {
        long settingsVa = va();
        double x = location.xx();
        double y = location.yy();
        double z = location.zz();
        setPosition2(settingsVa, x, y, z);

        return location;
    }

    /**
     * Alter which space the other properties are specified in. (native
     * attribute: mSpace)
     *
     * @param space an enum value (not null, default=WorldSpace)
     */
    public void setSpace(EConstraintSpace space) {
        long settingsVa = va();
        int ordinal = space.ordinal();
        setSpace(settingsVa, ordinal);
    }

    /**
     * Alter the type of swing constraint. (native attribute: mSwingType)
     *
     * @param swingType an enum value (not null, default=Cone)
     */
    public void setSwingType(ESwingType swingType) {
        long settingsVa = va();
        int ordinal = swingType.ordinal();
        setSwingType(settingsVa, ordinal);
    }
    // *************************************************************************
    // native private methods

    native private static long createSixDofConstraintSettings();

    native private static float getAxisX1X(long settingsVa);

    native private static float getAxisX1Y(long settingsVa);

    native private static float getAxisX1Z(long settingsVa);

    native private static float getAxisX2X(long settingsVa);

    native private static float getAxisX2Y(long settingsVa);

    native private static float getAxisX2Z(long settingsVa);

    native private static float getAxisY1X(long settingsVa);

    native private static float getAxisY1Y(long settingsVa);

    native private static float getAxisY1Z(long settingsVa);

    native private static float getAxisY2X(long settingsVa);

    native private static float getAxisY2Y(long settingsVa);

    native private static float getAxisY2Z(long settingsVa);

    native private static float getLimitMax(long settingsVa, int dofIndex);

    native private static float getLimitMin(long settingsVa, int dofIndex);

    native private static long getLimitsSpringSettings(
            long settingsVa, int dofIndex);

    native private static float getMaxFriction(long settingsVa, int dofIndex);

    native private static long getMotorSettings(long settingsVa, int dofIndex);

    native private static double getPosition1X(long settingsVa);

    native private static double getPosition1Y(long settingsVa);

    native private static double getPosition1Z(long settingsVa);

    native private static double getPosition2X(long settingsVa);

    native private static double getPosition2Y(long settingsVa);

    native private static double getPosition2Z(long settingsVa);

    native private static int getSpace(long settingsVa);

    native private static int getSwingType(long settingsVa);

    native private static boolean isFixedAxis(long settingsVa, int dofIndex);

    native private static boolean isFreeAxis(long settingsVa, int dofIndex);

    native private static void makeFixedAxis(long settingsVa, int dofIndex);

    native private static void makeFreeAxis(long settingsVa, int dofIndex);

    native private static void setAxisX1(
            long settingsVa, float x, float y, float z);

    native private static void setAxisX2(
            long settingsVa, float x, float y, float z);

    native private static void setAxisY1(
            long settingsVa, float x, float y, float z);

    native private static void setAxisY2(
            long settingsVa, float x, float y, float z);

    native private static void setLimitedAxis(
            long settingsVa, int dofIndex, float min, float max);

    native private static void setLimitMax(
            long settingsVa, int dofIndex, float max);

    native private static void setLimitMin(
            long settingsVa, int dofIndex, float min);

    native private static void setMaxFriction(
            long settingsVa, int dofIndex, float friction);

    native private static void setMotorSettings(
            long constraintSettingsVa, int dofIndex, long motorSettingsVa);

    native private static void setPosition1(
            long settingsVa, double x, double y, double z);

    native private static void setPosition2(
            long settingsVa, double x, double y, double z);

    native private static void setSpace(long settingsVa, int ordinal);

    native private static void setSwingType(long settingsVa, int ordinal);
}
