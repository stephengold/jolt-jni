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

import com.github.stephengold.joltjni.enumerate.EConstraintSpace;
import com.github.stephengold.joltjni.enumerate.EConstraintSubType;
import com.github.stephengold.joltjni.readonly.RVec3Arg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * Settings used to construct a {@code SliderConstraint}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class SliderConstraintSettings extends TwoBodyConstraintSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified native object assigned but not owned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    SliderConstraintSettings(long settingsVa) {
        super(settingsVa);
        setSubType(EConstraintSubType.Slider);
    }

    /**
     * Instantiate default settings.
     */
    public SliderConstraintSettings() {
        long settingsVa = createSliderConstraintSettings();
        setVirtualAddress(settingsVa); // not owner due to ref counting
        setSubType(EConstraintSubType.Slider);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Test whether to configure the attachment points based on the positions of
     * the bodies when the constraint is created. The settings are unaffected.
     * (native attribute: mAutoDetectPoint)
     *
     * @return {@code true} to use body positions, {@code false} for explicit
     * configuration
     */
    public boolean getAutoDetectPoint() {
        long settingsVa = va();
        boolean result = getAutoDetectPoint(settingsVa);

        return result;
    }

    /**
     * Return the upper limit of translation. The settings are unaffected.
     * (native attribute: mLimitsMax)
     *
     * @return the upper limit
     */
    public float getLimitsMax() {
        long settingsVa = va();
        float result = getLimitsMax(settingsVa);

        return result;
    }

    /**
     * Return the lower limit of translation. The settings are unaffected.
     * (native attribute: mLimitsMin)
     *
     * @return the lower limit
     */
    public float getLimitsMin() {
        long settingsVa = va();
        float result = getLimitsMin(settingsVa);

        return result;
    }

    /**
     * Access the spring settings. (native attribute: mLimitsSpringSettings)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public SpringSettings getLimitsSpringSettings() {
        long constraintSettingsVa = va();
        long springSettingsVa
                = getLimitsSpringSettings(constraintSettingsVa);
        SpringSettings result = new SpringSettings(this, springSettingsVa);

        return result;
    }

    /**
     * Return the maximum friction force when not driven by a motor. The
     * settings are unaffected. (native attribute: mMaxFrictionForce)
     *
     * @return the force (in Newtons)
     */
    public float getMaxFrictionForce() {
        long settingsVa = va();
        float result = getMaxFrictionForce(settingsVa);

        return result;
    }

    /**
     * Access the motor settings. (native attribute: mMotorSettings)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public MotorSettings getMotorSettings() {
        long constraintSettingsVa = va();
        long motorSettingsVa
                = getMotorSettings(constraintSettingsVa);
        MotorSettings result = new MotorSettings(this, motorSettingsVa);

        return result;
    }

    /**
     * Copy the normal axis of body 1. The settings are unaffected. (native
     * attribute: mNormalAxis1)
     *
     * @return a new direction vector
     */
    public Vec3 getNormalAxis1() {
        long settingsVa = va();
        float x = getNormalAxis1X(settingsVa);
        float y = getNormalAxis1Y(settingsVa);
        float z = getNormalAxis1Z(settingsVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Copy the normal axis of body 2. The settings are unaffected. (native
     * attribute: mNormalAxis2)
     *
     * @return a new direction vector
     */
    public Vec3 getNormalAxis2() {
        long settingsVa = va();
        float x = getNormalAxis2X(settingsVa);
        float y = getNormalAxis2Y(settingsVa);
        float z = getNormalAxis2Z(settingsVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Copy the constraint location for body 1. The settings are unaffected.
     * (native attribute: mPoint1)
     *
     * @return a new location vector
     */
    public RVec3 getPoint1() {
        long settingsVa = va();
        double x = getPoint1X(settingsVa);
        double y = getPoint1Y(settingsVa);
        double z = getPoint1Z(settingsVa);
        RVec3 result = new RVec3(x, y, z);

        return result;
    }

    /**
     * Copy the constraint location for body 2. The settings are unaffected.
     * (native attribute: mPoint2)
     *
     * @return a new location vector
     */
    public RVec3 getPoint2() {
        long settingsVa = va();
        double x = getPoint2X(settingsVa);
        double y = getPoint2Y(settingsVa);
        double z = getPoint2Z(settingsVa);
        RVec3 result = new RVec3(x, y, z);

        return result;
    }

    /**
     * Copy the slider axis of body 1. The settings are unaffected. (native
     * attribute: mSliderAxis1)
     *
     * @return a new direction vector
     */
    public Vec3 getSliderAxis1() {
        long settingsVa = va();
        float x = getSliderAxis1X(settingsVa);
        float y = getSliderAxis1Y(settingsVa);
        float z = getSliderAxis1Z(settingsVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Copy the slider axis of body 2. The settings are unaffected. (native
     * attribute: mSliderAxis2)
     *
     * @return a new direction vector
     */
    public Vec3 getSliderAxis2() {
        long settingsVa = va();
        float x = getSliderAxis2X(settingsVa);
        float y = getSliderAxis2Y(settingsVa);
        float z = getSliderAxis2Z(settingsVa);
        Vec3 result = new Vec3(x, y, z);

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
     * Alter whether to configure the attachment points based on the positions
     * of the bodies when the constraint is created. (native attribute:
     * mAutoDetectPoint)
     *
     * @param setting {@code true} to use body positions, {@code false} for
     * explicit configuration (default=false)
     */
    public void setAutoDetectPoint(boolean setting) {
        long settingsVa = va();
        setAutoDetectPoint(settingsVa, setting);
    }

    /**
     * Alter the upper limit of translation. (native attribute: mLimitsMax)
     *
     * @param limit the desired upper limit (default=MAX_VALUE)
     */
    public void setLimitsMax(float limit) {
        long settingsVa = va();
        setLimitsMax(settingsVa, limit);
    }

    /**
     * Alter the lower limit of translation. (native attribute: mLimitsMin)
     *
     * @param limit the desired lower limit (default=-MAX_VALUE)
     */
    public void setLimitsMin(float limit) {
        long settingsVa = va();
        setLimitsMin(settingsVa, limit);
    }

    /**
     * Alter the maximum friction force when not driven by a motor. (native
     * attribute: mMaxFrictionForce)
     *
     * @param force the desired force (in Newtons, default=0)
     */
    public void setMaxFrictionForce(float force) {
        long settingsVa = va();
        setMaxFrictionForce(settingsVa, force);
    }

    /**
     * Alter the normal axis of body 1. (native attribute: mNormalAxis1)
     *
     * @param direction the desired axis direction (not null, unaffected,
     * default=(0,1,0))
     * @return the argument, for chaining
     */
    public Vec3Arg setNormalAxis1(Vec3Arg direction) {
        long settingsVa = va();
        float x = direction.getX();
        float y = direction.getY();
        float z = direction.getZ();
        setNormalAxis1(settingsVa, x, y, z);

        return direction;
    }

    /**
     * Alter the normal axis of body 2. (native attribute: mNormalAxis2)
     *
     * @param direction the desired axis direction (not null, unaffected,
     * default=(0,1,0))
     * @return the argument, for chaining
     */
    public Vec3Arg setNormalAxis2(Vec3Arg direction) {
        long settingsVa = va();
        float x = direction.getX();
        float y = direction.getY();
        float z = direction.getZ();
        setNormalAxis2(settingsVa, x, y, z);

        return direction;
    }

    /**
     * Alter the constraint location for body 1. (native attribute: mPoint1)
     *
     * @param location the desired location (not null, unaffected,
     * default=(0,0,0))
     * @return the argument, for chaining
     */
    public RVec3Arg setPoint1(RVec3Arg location) {
        long settingsVa = va();
        double x = location.xx();
        double y = location.yy();
        double z = location.zz();
        setPoint1(settingsVa, x, y, z);

        return location;
    }

    /**
     * Alter the constraint location for body 2. (native attribute: mPoint2)
     *
     * @param location the desired location (not null, unaffected,
     * default=(0,0,0))
     * @return the argument, for chaining
     */
    public RVec3Arg setPoint2(RVec3Arg location) {
        long settingsVa = va();
        double x = location.xx();
        double y = location.yy();
        double z = location.zz();
        setPoint2(settingsVa, x, y, z);

        return location;
    }

    /**
     * Alter the slider and normal axes, assuming the bodies are correctly
     * oriented.
     *
     * @param sliderAxis the desired slider axis direction (in system
     * coordinates, not null, unaffected)
     */
    public void setSliderAxis(Vec3Arg sliderAxis) {
        long settingsVa = va();
        float dx = sliderAxis.getX();
        float dy = sliderAxis.getY();
        float dz = sliderAxis.getZ();
        setSliderAxis(settingsVa, dx, dy, dz);
    }

    /**
     * Alter the slider axis of body 1. (native attribute: mSliderAxis1)
     *
     * @param direction the desired axis direction (not null, unaffected,
     * default=(1,0,0))
     * @return the argument, for chaining
     */
    public Vec3Arg setSliderAxis1(Vec3Arg direction) {
        long settingsVa = va();
        float x = direction.getX();
        float y = direction.getY();
        float z = direction.getZ();
        setSliderAxis1(settingsVa, x, y, z);

        return direction;
    }

    /**
     * Alter the slider axis of body 2. (native attribute: mSliderAxis2)
     *
     * @param direction the desired axis direction (not null, unaffected,
     * default=(1,0,0))
     * @return the argument, for chaining
     */
    public Vec3Arg setSliderAxis2(Vec3Arg direction) {
        long settingsVa = va();
        float x = direction.getX();
        float y = direction.getY();
        float z = direction.getZ();
        setSliderAxis2(settingsVa, x, y, z);

        return direction;
    }

    /**
     * Alter which space the other properties are specified in. (native
     * attribute: mSpace)
     *
     * @param space enum value (not null, default=WorldSpace)
     */
    public void setSpace(EConstraintSpace space) {
        long settingsVa = va();
        int ordinal = space.ordinal();
        setSpace(settingsVa, ordinal);
    }
    // *************************************************************************
    // native private methods

    native private static long createSliderConstraintSettings();

    native private static boolean getAutoDetectPoint(long settingsVa);

    native private static float getLimitsMax(long settingsVa);

    native private static float getLimitsMin(long settingsVa);

    native private static long getLimitsSpringSettings(
            long constraintSettingsVa);

    native private static float getMaxFrictionForce(long settingsVa);

    native private static long getMotorSettings(long constraintSettingsVa);

    native private static float getNormalAxis1X(long settingsVa);

    native private static float getNormalAxis1Y(long settingsVa);

    native private static float getNormalAxis1Z(long settingsVa);

    native private static float getNormalAxis2X(long settingsVa);

    native private static float getNormalAxis2Y(long settingsVa);

    native private static float getNormalAxis2Z(long settingsVa);

    native private static double getPoint1X(long settingsVa);

    native private static double getPoint1Y(long settingsVa);

    native private static double getPoint1Z(long settingsVa);

    native private static double getPoint2X(long settingsVa);

    native private static double getPoint2Y(long settingsVa);

    native private static double getPoint2Z(long settingsVa);

    native private static float getSliderAxis1X(long settingsVa);

    native private static float getSliderAxis1Y(long settingsVa);

    native private static float getSliderAxis1Z(long settingsVa);

    native private static float getSliderAxis2X(long settingsVa);

    native private static float getSliderAxis2Y(long settingsVa);

    native private static float getSliderAxis2Z(long settingsVa);

    native private static int getSpace(long settingsVa);

    native private static void setAutoDetectPoint(
            long settingsVa, boolean setting);

    native private static void setLimitsMax(long settingsVa, float limit);

    native private static void setLimitsMin(long settingsVa, float limit);

    native private static void setMaxFrictionForce(
            long settingsVa, float force);

    native private static void setNormalAxis1(
            long settingsVa, float x, float y, float z);

    native private static void setNormalAxis2(
            long settingsVa, float x, float y, float z);

    native private static void setPoint1(
            long settingsVa, double x, double y, double z);

    native private static void setPoint2(
            long settingsVa, double x, double y, double z);

    native private static void setSliderAxis(
            long settingsVa, float dx, float dy, float dz);

    native private static void setSliderAxis1(
            long settingsVa, float x, float y, float z);

    native private static void setSliderAxis2(
            long settingsVa, float x, float y, float z);

    native private static void setSpace(long settingsVa, int ordinal);
}
