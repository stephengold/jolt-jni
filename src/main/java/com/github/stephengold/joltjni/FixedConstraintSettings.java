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
 * Settings used to construct a {@code FixedConstraint}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class FixedConstraintSettings extends TwoBodyConstraintSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified native object assigned but not owned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    FixedConstraintSettings(long settingsVa) {
        super(settingsVa);
        setSubType(EConstraintSubType.Fixed);
    }

    /**
     * Instantiate default settings.
     */
    public FixedConstraintSettings() {
        long settingsVa = createFixedConstraintSettings();
        setVirtualAddress(settingsVa, null); // not owner due to ref counting
        setSubType(EConstraintSubType.Fixed);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Test whether to configure the attachment points based on the positions of
     * the bodies when the constraint is created. The settings are unaffected.
     * (native field: mAutoDetectPoint)
     *
     * @return true to use body positions, false for explicit configuration
     */
    public boolean getAutoDetectPoint() {
        long settingsVa = va();
        boolean result = getAutoDetectPoint(settingsVa);

        return result;
    }

    /**
     * Copy the X axis for body 1. The settings are unaffected. (native field:
     * mAxisX1)
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
     * Copy the X axis for body 2. The settings are unaffected. (native field:
     * mAxisX2)
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
     * Copy the Y axis for body 1. The settings are unaffected. (native field:
     * mAxisY1)
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
     * Copy the Y axis for body 2. The settings are unaffected. (native field:
     * mAxisY2)
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
     * Copy the constraint location for body 1. The settings are unaffected.
     * (native field: mPoint1)
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
     * (native field: mPoint2)
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
     * Alter whether to configure the attachment points based on the positions
     * of the bodies when the constraint is created. (native field:
     * mAutoDetectPoint)
     *
     * @param setting true to use body positions, false for explicit
     * configuration (default=false)
     */
    public void setAutoDetectPoint(boolean setting) {
        long settingsVa = va();
        setAutoDetectPoint(settingsVa, setting);
    }

    /**
     * Alter the X axis for body 1. (native field: mAxisX1)
     *
     * @param axis the desired direction vector (default=(1,0,0))
     */
    public void setAxisX1(Vec3Arg axis) {
        long settingsVa = va();
        float x = axis.getX();
        float y = axis.getY();
        float z = axis.getZ();
        setAxisX1(settingsVa, x, y, z);
    }

    /**
     * Alter the X axis for body 2. (native field: mAxisX2)
     *
     * @param axis the desired direction vector (default=(1,0,0))
     */
    public void setAxisX2(Vec3Arg axis) {
        long settingsVa = va();
        float x = axis.getX();
        float y = axis.getY();
        float z = axis.getZ();
        setAxisX2(settingsVa, x, y, z);
    }

    /**
     * Alter the Y axis for body 1. (native field: mAxisY1)
     *
     * @param axis the desired direction vector (default=(0,1,0))
     */
    public void setAxisY1(Vec3Arg axis) {
        long settingsVa = va();
        float x = axis.getX();
        float y = axis.getY();
        float z = axis.getZ();
        setAxisY1(settingsVa, x, y, z);
    }

    /**
     * Alter the Y axis for body 2. (native field: mAxisY2)
     *
     * @param axis the desired direction vector (default=(0,1,0))
     */
    public void setAxisY2(Vec3Arg axis) {
        long settingsVa = va();
        float x = axis.getX();
        float y = axis.getY();
        float z = axis.getZ();
        setAxisY2(settingsVa, x, y, z);
    }

    /**
     * Alter the constraint location for body 1. (native field: mPoint1)
     *
     * @param location the desired location (not null, unaffected,
     * default=(0,0,0))
     */
    public void setPoint1(RVec3Arg location) {
        long settingsVa = va();
        double x = location.xx();
        double y = location.yy();
        double z = location.zz();
        setPoint1(settingsVa, x, y, z);
    }

    /**
     * Alter the constraint location for body 2. (native field: mPoint2)
     *
     * @param location the desired location (not null, unaffected,
     * default=(0,0,0))
     */
    public void setPoint2(RVec3Arg location) {
        long settingsVa = va();
        double x = location.xx();
        double y = location.yy();
        double z = location.zz();
        setPoint2(settingsVa, x, y, z);
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
    // *************************************************************************
    // native private methods

    native private static long createFixedConstraintSettings();

    native private static boolean getAutoDetectPoint(long settingsVa);

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

    native private static double getPoint1X(long settingsVa);

    native private static double getPoint1Y(long settingsVa);

    native private static double getPoint1Z(long settingsVa);

    native private static double getPoint2X(long settingsVa);

    native private static double getPoint2Y(long settingsVa);

    native private static double getPoint2Z(long settingsVa);

    native private static int getSpace(long settingsVa);

    native private static void setAutoDetectPoint(
            long settingsVa, boolean setting);

    native private static void setAxisX1(
            long settingsVa, float x, float y, float z);

    native private static void setAxisX2(
            long settingsVa, float x, float y, float z);

    native private static void setAxisY1(
            long settingsVa, float x, float y, float z);

    native private static void setAxisY2(
            long settingsVa, float x, float y, float z);

    native private static void setPoint1(
            long settingsVa, double x, double y, double z);

    native private static void setPoint2(
            long settingsVa, double x, double y, double z);

    native private static void setSpace(long settingsVa, int ordinal);
}
