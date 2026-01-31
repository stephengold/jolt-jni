/*
Copyright (c) 2024-2026 Stephen Gold

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
import com.github.stephengold.joltjni.enumerate.ESwingType;
import com.github.stephengold.joltjni.readonly.RVec3Arg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;

/**
 * Settings used to construct a {@code SwingTwistConstraint}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class SwingTwistConstraintSettings extends TwoBodyConstraintSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate default settings.
     */
    public SwingTwistConstraintSettings() {
        long settingsVa = createDefault();
        setVirtualAddressAsCoOwner(settingsVa, EConstraintSubType.SwingTwist);
    }

    /**
     * Instantiate with the specified native object assigned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    SwingTwistConstraintSettings(long settingsVa) {
        setVirtualAddressAsCoOwner(settingsVa, EConstraintSubType.SwingTwist);
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public SwingTwistConstraintSettings(SwingTwistConstraintSettings original) {
        long originalVa = original.targetVa();
        long copyVa = createCopy(originalVa);
        setVirtualAddressAsCoOwner(copyVa, EConstraintSubType.SwingTwist);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the maximum friction torque when not driven by a motor. The
     * settings are unaffected. (native attribute: mMaxFrictionTorque)
     *
     * @return the torque (in Newton meters)
     */
    public float getMaxFrictionTorque() {
        long settingsVa = va();
        float result = getMaxFrictionTorque(settingsVa);

        return result;
    }

    /**
     * Return the half angle of the normal cone. The settings are unaffected.
     * (native attribute: mNormalHalfConeAngle)
     *
     * @return the half angle (in radians)
     */
    public float getNormalHalfConeAngle() {
        long settingsVa = va();
        float result = getNormalHalfConeAngle(settingsVa);

        return result;
    }

    /**
     * Copy the plane axis of body 1. The settings are unaffected. (native
     * attribute: mPlaneAxis1)
     *
     * @return a new direction vector
     */
    public Vec3 getPlaneAxis1() {
        long settingsVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getPlaneAxis1(settingsVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Copy the plane axis of body 2. The settings are unaffected. (native
     * attribute: mPlaneAxis2)
     *
     * @return a new direction vector
     */
    public Vec3 getPlaneAxis2() {
        long settingsVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getPlaneAxis2(settingsVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Return one-half the maximum angle between the twist axes of body 1 and
     * body 2. The settings are unaffected. (native attribute:
     * mPlaneHalfConeAngle)
     *
     * @return the half angle (in radians)
     */
    public float getPlaneHalfConeAngle() {
        long settingsVa = va();
        float result = getPlaneHalfConeAngle(settingsVa);

        return result;
    }

    /**
     * Copy the initial location of body 1. The settings are unaffected. (native
     * attribute: mPosition1)
     *
     * @return a new location vector
     */
    public RVec3 getPosition1() {
        long settingsVa = va();
        DoubleBuffer storeDoubles = Temporaries.doubleBuffer1.get();
        getPosition1(settingsVa, storeDoubles);
        RVec3 result = new RVec3(storeDoubles);

        return result;
    }

    /**
     * Copy the initial location of body 2. The settings are unaffected. (native
     * attribute: mPosition2)
     *
     * @return a new location vector
     */
    public RVec3 getPosition2() {
        long settingsVa = va();
        DoubleBuffer storeDoubles = Temporaries.doubleBuffer1.get();
        getPosition2(settingsVa, storeDoubles);
        RVec3 result = new RVec3(storeDoubles);

        return result;
    }

    /**
     * Return which space the other properties are specified in. The settings
     * are unaffected. (native attribute: mSpace)
     *
     * @return an enum value (not {@code null})
     */
    public EConstraintSpace getSpace() {
        long settingsVa = va();
        int ordinal = getSpace(settingsVa);
        EConstraintSpace result = EConstraintSpace.values()[ordinal];

        return result;
    }

    /**
     * Access the swing motor settings. (native attribute: mSwingMotorSettings)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public MotorSettings getSwingMotorSettings() {
        long constraintSettingsVa = va();
        long motorSettingsVa = getSwingMotorSettings(constraintSettingsVa);
        MotorSettings result = new MotorSettings(this, motorSettingsVa);

        return result;
    }

    /**
     * Return type of swing constraint. The settings are unaffected. (native
     * attribute: mSwingType)
     *
     * @return the enum value (not {@code null})
     */
    public ESwingType getSwingType() {
        long settingsVa = va();
        int ordinal = getSwingType(settingsVa);
        ESwingType result = ESwingType.values()[ordinal];

        return result;
    }

    /**
     * Copy the twist axis of body 1. The settings are unaffected. (native
     * attribute: mTwistAxis1)
     *
     * @return a new direction vector
     */
    public Vec3 getTwistAxis1() {
        long settingsVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getTwistAxis1(settingsVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Copy the twist axis of body 2. The settings are unaffected. (native
     * attribute: mTwistAxis2)
     *
     * @return a new direction vector
     */
    public Vec3 getTwistAxis2() {
        long settingsVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getTwistAxis2(settingsVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Return the maximum twist angle. The settings are unaffected. (native
     * attribute: mTwistMaxAngle)
     *
     * @return the angle (in radians)
     */
    public float getTwistMaxAngle() {
        long settingsVa = va();
        float result = getTwistMaxAngle(settingsVa);

        return result;
    }

    /**
     * Return the minimum twist angle. The settings are unaffected. (native
     * attribute: mTwistMinAngle)
     *
     * @return the angle (in radians)
     */
    public float getTwistMinAngle() {
        long settingsVa = va();
        float result = getTwistMinAngle(settingsVa);

        return result;
    }

    /**
     * Access the twist motor settings. (native attribute: mTwistMotorSettings)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public MotorSettings getTwistMotorSettings() {
        long constraintSettingsVa = va();
        long motorSettingsVa = getTwistMotorSettings(constraintSettingsVa);
        MotorSettings result = new MotorSettings(this, motorSettingsVa);

        return result;
    }

    /**
     * Alter the maximum friction torque when not driven by a motor. (native
     * attribute: mMaxFrictionTorque)
     *
     * @param torque the desired limit (in Newton meters, default=0)
     */
    public void setMaxFrictionTorque(float torque) {
        long settingsVa = va();
        setMaxFrictionTorque(settingsVa, torque);
    }

    /**
     * Alter the half angle of the normal cone. (native attribute:
     * mNormalHalfConeAngle)
     *
     * @param angle the desired half angle (in radians, default=0)
     */
    public void setNormalHalfConeAngle(float angle) {
        long settingsVa = va();
        setNormalHalfConeAngle(settingsVa, angle);
    }

    /**
     * Alter the plane axis of body 1. (native attribute: mPlaneAxis1)
     *
     * @param axisDirection the desired direction (not {@code null}, unaffected,
     * default=(0,1,0))
     * @return the argument, for chaining
     */
    public Vec3Arg setPlaneAxis1(Vec3Arg axisDirection) {
        long settingsVa = va();
        float dx = axisDirection.getX();
        float dy = axisDirection.getY();
        float dz = axisDirection.getZ();
        setPlaneAxis1(settingsVa, dx, dy, dz);

        return axisDirection;
    }

    /**
     * Alter the plane axis of body 2. (native attribute: mPlaneAxis2)
     *
     * @param axisDirection the desired direction (not {@code null}, unaffected,
     * default=(0,1,0))
     * @return the argument, for chaining
     */
    public Vec3Arg setPlaneAxis2(Vec3Arg axisDirection) {
        long settingsVa = va();
        float dx = axisDirection.getX();
        float dy = axisDirection.getY();
        float dz = axisDirection.getZ();
        setPlaneAxis2(settingsVa, dx, dy, dz);

        return axisDirection;
    }

    /**
     * Alter the half-angle of the plane cone. (native attribute:
     * mPlaneHalfConeAngle)
     *
     * @param angle the desired limit (in radians, default=0)
     */
    public void setPlaneHalfConeAngle(float angle) {
        long settingsVa = va();
        setPlaneHalfConeAngle(settingsVa, angle);
    }

    /**
     * Alter the initial location of the body 1. (native attribute: mPosition1)
     *
     * @param location the desired location (not {@code null}, unaffected,
     * default=(0,0,0))
     * @return the argument, for chaining
     */
    public RVec3Arg setPosition1(RVec3Arg location) {
        long settingsVa = va();
        double locX = location.xx();
        double locY = location.yy();
        double locZ = location.zz();
        setPosition1(settingsVa, locX, locY, locZ);

        return location;
    }

    /**
     * Alter the initial location of body 2. (native attribute: mPosition2)
     *
     * @param location the desired location (not {@code null}, unaffected,
     * default=(0,0,0))
     * @return the argument, for chaining
     */
    public RVec3Arg setPosition2(RVec3Arg location) {
        long settingsVa = va();
        double locX = location.xx();
        double locY = location.yy();
        double locZ = location.zz();
        setPosition2(settingsVa, locX, locY, locZ);

        return location;
    }

    /**
     * Alter which space the other properties are specified in. (native
     * attribute: mSpace)
     *
     * @param space enum value (not {@code null}, default=WorldSpace)
     */
    public void setSpace(EConstraintSpace space) {
        long settingsVa = va();
        int ordinal = space.ordinal();
        setSpace(settingsVa, ordinal);
    }

    /**
     * Alter the settings for the swing motor. (native attribute:
     * mSwingMotorSettings)
     *
     * @param motorSettings the desired settings (not {@code null}, unaffected)
     * @return the argument, for chaining
     */
    public MotorSettings setSwingMotorSettings(MotorSettings motorSettings) {
        long constraintSettingsVa = va();
        long motorSettingsVa = motorSettings.va();
        setSwingMotorSettings(constraintSettingsVa, motorSettingsVa);

        return motorSettings;
    }

    /**
     * Alter the type of swing constraint. (native attribute: mSwingType)
     *
     * @param swingType the desired enum value (not {@code null}, default=Cone)
     */
    public void setSwingType(ESwingType swingType) {
        long settingsVa = va();
        int ordinal = swingType.ordinal();
        setSwingType(settingsVa, ordinal);
    }

    /**
     * Alter the twist axis of the body 1. (native attribute: mTwistAxis1)
     *
     * @param axisDirection the desired direction (not {@code null}, unaffected,
     * default=(1,0,0))
     * @return the argument, for chaining
     */
    public Vec3Arg setTwistAxis1(Vec3Arg axisDirection) {
        long settingsVa = va();
        float dx = axisDirection.getX();
        float dy = axisDirection.getY();
        float dz = axisDirection.getZ();
        setTwistAxis1(settingsVa, dx, dy, dz);

        return axisDirection;
    }

    /**
     * Alter the twist axis of body 2. (native attribute: mTwistAxis2)
     *
     * @param axisDirection the desired direction (not {@code null}, unaffected,
     * default=(1,0,0))
     * @return the argument, for chaining
     */
    public Vec3Arg setTwistAxis2(Vec3Arg axisDirection) {
        long settingsVa = va();
        float dx = axisDirection.getX();
        float dy = axisDirection.getY();
        float dz = axisDirection.getZ();
        setTwistAxis2(settingsVa, dx, dy, dz);

        return axisDirection;
    }

    /**
     * Alter the maximum twist angle. (native attribute: mTwistMaxAngle)
     *
     * @param angle the desired limit (in radians, &ge;-Pi, &le;Pi, default=0)
     */
    public void setTwistMaxAngle(float angle) {
        long settingsVa = va();
        setTwistMaxAngle(settingsVa, angle);
    }

    /**
     * Alter the minimum twist angle. (native attribute: mTwistMinAngle)
     *
     * @param angle the desired limit (in radians, &ge;-Pi, &le;Pi, default=0)
     */
    public void setTwistMinAngle(float angle) {
        long settingsVa = va();
        setTwistMinAngle(settingsVa, angle);
    }

    /**
     * Alter the settings for the twist motor. (native attribute:
     * mTwistMotorSetting)
     *
     * @param motorSettings the desired settings (not {@code null}, unaffected)
     * @return the argument, for chaining
     */
    public MotorSettings setTwistMotorSettings(MotorSettings motorSettings) {
        long constraintSettingsVa = va();
        long motorSettingsVa = motorSettings.va();
        setTwistMotorSettings(constraintSettingsVa, motorSettingsVa);

        return motorSettings;
    }
    // *************************************************************************
    // native private methods

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static float getMaxFrictionTorque(long settingsVa);

    native private static float getNormalHalfConeAngle(long settingsVa);

    native private static void getPlaneAxis1(
            long settingsVa, FloatBuffer storeFloats);

    native private static void getPlaneAxis2(
            long settingsVa, FloatBuffer storeFloats);

    native private static float getPlaneHalfConeAngle(long settingsVa);

    native private static void getPosition1(
            long settingsVa, DoubleBuffer storeDoubles);

    native private static void getPosition2(
            long settingsVa, DoubleBuffer storeDoubles);

    native private static int getSpace(long settingsVa);

    native private static long getSwingMotorSettings(long constraintSettingsVa);

    native private static int getSwingType(long settingsVa);

    native private static void getTwistAxis1(
            long settingsVa, FloatBuffer storeFloats);

    native private static void getTwistAxis2(
            long settingsVa, FloatBuffer storeFloats);

    native private static float getTwistMaxAngle(long settingsVa);

    native private static float getTwistMinAngle(long settingsVa);

    native private static long getTwistMotorSettings(long constraintSettingsVa);

    native private static void setMaxFrictionTorque(
            long settingsVa, float torque);

    native private static void setNormalHalfConeAngle(
            long settingsVa, float angle);

    native private static void setPlaneAxis1(
            long settingsVa, float dx, float dy, float dz);

    native private static void setPlaneAxis2(
            long settingsVa, float dx, float dy, float dz);

    native private static void setPlaneHalfConeAngle(
            long settingsVa, float angle);

    native private static void setPosition1(
            long settingsVa, double locX, double locY, double locZ);

    native private static void setPosition2(
            long settingsVa, double locX, double locY, double locZ);

    native private static void setSpace(long settingsVa, int ordinal);

    native private static void setSwingMotorSettings(
            long constraintSettingsVa, long motorSettingsVa);

    native private static void setSwingType(long settingsVa, int ordinal);

    native private static void setTwistAxis1(
            long settingsVa, float dx, float dy, float dz);

    native private static void setTwistAxis2(
            long settingsVa, float dx, float dy, float dz);

    native private static void setTwistMaxAngle(long settingsVa, float angle);

    native private static void setTwistMinAngle(long settingsVa, float angle);

    native private static void setTwistMotorSettings(
            long constraintSettingsVa, long motorSettingsVa);
}
