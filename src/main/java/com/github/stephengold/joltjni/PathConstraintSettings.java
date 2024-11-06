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

import com.github.stephengold.joltjni.enumerate.EConstraintSubType;
import com.github.stephengold.joltjni.enumerate.EPathRotationConstraintType;
import com.github.stephengold.joltjni.readonly.QuatArg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * Settings used to construct a {@code PathConstraint}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class PathConstraintSettings extends TwoBodyConstraintSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate default settings.
     */
    public PathConstraintSettings() {
        long settingsVa = createDefault();
        setVirtualAddress(settingsVa, null); // not owner due to ref counting
        setSubType(EConstraintSubType.Path);
    }

    /**
     * Instantiate with the specified native object assigned but not owned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    PathConstraintSettings(long settingsVa) {
        super(settingsVa);
        setSubType(EConstraintSubType.Path);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the maximum friction force when not driven by a motor. The
     * settings are unaffected. (native field: mMaxFrictionForce)
     *
     * @return the force (in Newtons)
     */
    public float getMaxFrictionForce() {
        long settingsVa = va();
        float result = getMaxFrictionForce(settingsVa);

        return result;
    }

    /**
     * Access the path. (native field: mPath)
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public PathConstraintPath getPath() {
        long settingsVa = va();
        long pathVa = getPath(settingsVa);
        PathConstraintPath result = new PathConstraintPath(pathVa);

        return result;
    }

    /**
     * Return how far the body is along the path initially. The settings are
     * unaffected. (native field: mPathFraction)
     *
     * @return the amount
     */
    public float getPathFraction() {
        long settingsVa = va();
        float result = getPathFraction(settingsVa);

        return result;
    }

    /**
     * Copy the starting location. The settings are unaffected. (native field:
     * mPathPosition)
     *
     * @return a new vector
     */
    public Vec3 getPathPosition() {
        long settingsVa = va();
        float x = getPathPositionX(settingsVa);
        float y = getPathPositionY(settingsVa);
        float z = getPathPositionZ(settingsVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Copy the initial rotation. The path is unaffected. (native field:
     * mPathRotation)
     *
     * @return a new quaternion
     */
    public Quat getPathRotation() {
        long settingsVa = va();
        float qw = getPathRotationW(settingsVa);
        float qx = getPathRotationX(settingsVa);
        float qy = getPathRotationY(settingsVa);
        float qz = getPathRotationZ(settingsVa);
        Quat result = new Quat(qx, qy, qz, qw);

        return result;
    }

    /**
     * Access the settings of the position motor. (native field:
     * mPositionMotorSettings)
     *
     * @return new JVM object with the pre-existing native object assigned
     */
    public MotorSettings getPositionMotorSettings() {
        long constraintSettingsVa = va();
        long motorSettingsVa = getPositionMotorSettings(constraintSettingsVa);
        MotorSettings result = new MotorSettings(this, motorSettingsVa);

        return result;
    }

    /**
     * The settings are unaffected. (native field: mRotationConstraintType)
     *
     * @return the enum value (not null)
     */
    public EPathRotationConstraintType getRotationConstraintType() {
        long settingsVa = va();
        int ordinal = getRotationConstraintType(settingsVa);
        EPathRotationConstraintType result
                = EPathRotationConstraintType.values()[ordinal];

        return result;
    }

    /**
     * Alter the maximum friction force when not driven by a motor. (native
     * field: mMaxFrictionForce)
     *
     * @param force the desired force (in Newtons)
     */
    public void setMaxFrictionForce(float force) {
        long settingsVa = va();
        setMaxFrictionForce(settingsVa, force);
    }

    /**
     * Replace the current path with the specified one. (native field: mPath)
     *
     * @param path the desired path (not null)
     */
    public void setPath(PathConstraintPath path) {
        long settingsVa = va();
        long pathVa = path.va();
        setPath(settingsVa, pathVa);
    }

    /**
     * Alter how far the body is along the path initially. (native field:
     * mPathFraction)
     *
     * @param amount the desired amount (&ge;0)
     */
    public void setPathFraction(float amount) {
        long settingsVa = va();
        setPathFraction(settingsVa, amount);
    }

    /**
     * Alter the initial location. (native field: mPathPosition)
     *
     * @param location the desired location (not null, unaffected)
     */
    public void setPathPosition(Vec3Arg location) {
        long settingsVa = va();
        float locX = location.getX();
        float locY = location.getY();
        float locZ = location.getZ();
        setPathPosition(settingsVa, locX, locY, locZ);
    }

    /**
     * Alter the initial orientation. (native field: mPathRotation)
     *
     * @param orientation the desired orientation (not null, unaffected)
     */
    public void setPathRotation(QuatArg orientation) {
        long settingsVa = va();
        float qw = orientation.getW();
        float qx = orientation.getX();
        float qy = orientation.getY();
        float qz = orientation.getZ();
        setPathRotation(settingsVa, qx, qy, qz, qw);
    }

    /**
     * Alter the motor settings. (native field: mPositionMotorSettings)
     *
     * @param motorSettings the desired settings (not null, unaffected)
     */
    public void setPositionMotorSettings(MotorSettings motorSettings) {
        long constraintSettingsVa = va();
        long motorSettingsVa = motorSettings.va();
        setPositionMotorSettings(constraintSettingsVa, motorSettingsVa);
    }

    /**
     * Alter the (native field: mRotationConstraintType)
     *
     * @param type the desired type (not null)
     */
    public void setRotationConstraintType(EPathRotationConstraintType type) {
        long settingsVa = va();
        int ordinal = type.ordinal();
        setRotationConstraintType(settingsVa, ordinal);
    }
    // *************************************************************************
    // native private methods

    native private static long createDefault();

    native private static float getMaxFrictionForce(long settingsVa);

    native private static long getPath(long settingsVa);

    native private static float getPathFraction(long settingsVa);

    native private static float getPathPositionX(long settingsVa);

    native private static float getPathPositionY(long settingsVa);

    native private static float getPathPositionZ(long settingsVa);

    native private static float getPathRotationW(long settingsVa);

    native private static float getPathRotationX(long settingsVa);

    native private static float getPathRotationY(long settingsVa);

    native private static float getPathRotationZ(long settingsVa);

    native private static long getPositionMotorSettings(
            long constraintSettingsVa);

    native private static int getRotationConstraintType(long settingsVa);

    native private static void setMaxFrictionForce(
            long settingsVa, float force);

    native private static void setPath(long settingsVa, long pathVa);

    native private static void setPathFraction(long settingsVa, float amount);

    native private static void setPathPosition(
            long settingsVa, float locX, float locY, float locZ);

    native private static void setPathRotation(
            long settingsVa, float qx, float qy, float qz, float qw);

    native private static void setPositionMotorSettings(
            long constraintSettingsVa, long motorSettingsVa);

    native private static void setRotationConstraintType(
            long settingsVa, int ordinal);
}
