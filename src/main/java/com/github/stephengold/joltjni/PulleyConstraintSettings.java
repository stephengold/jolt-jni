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

import com.github.stephengold.joltjni.enumerate.EConstraintSpace;
import com.github.stephengold.joltjni.enumerate.EConstraintSubType;
import com.github.stephengold.joltjni.readonly.RVec3Arg;

/**
 * Settings used to construct a {@code PulleyConstraint}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class PulleyConstraintSettings extends TwoBodyConstraintSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate default settings.
     */
    public PulleyConstraintSettings() {
        long settingsVa = createDefault();
        setVirtualAddressAsCoOwner(settingsVa, EConstraintSubType.Pulley);
    }

    /**
     * Instantiate with the specified native object assigned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    PulleyConstraintSettings(long settingsVa) {
        setVirtualAddressAsCoOwner(settingsVa, EConstraintSubType.Pulley);
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public PulleyConstraintSettings(PulleyConstraintSettings original) {
        long originalVa = original.targetVa();
        long copyVa = createCopy(originalVa);
        setVirtualAddressAsCoOwner(copyVa, EConstraintSubType.Pulley);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Copy the location at which the constraint will attach to body 1. The
     * settings are unaffected. (native attribute: mBodyPoint1)
     *
     * @return a location vector
     */
    public RVec3 getBodyPoint1() {
        long settingsVa = va();
        double x = getBodyPoint1X(settingsVa);
        double y = getBodyPoint1Y(settingsVa);
        double z = getBodyPoint1Z(settingsVa);
        RVec3 result = new RVec3(x, y, z);

        return result;
    }

    /**
     * Copy the location at which the constraint will attach to body 2. The
     * settings are unaffected. (native attribute: mBodyPoint2)
     *
     * @return a location vector
     */
    public RVec3 getBodyPoint2() {
        long settingsVa = va();
        double x = getBodyPoint2X(settingsVa);
        double y = getBodyPoint2Y(settingsVa);
        double z = getBodyPoint2Z(settingsVa);
        RVec3 result = new RVec3(x, y, z);

        return result;
    }

    /**
     * Copy the location to which body 1 is connected. The settings are
     * unaffected. (native attribute: mFixedPoint1)
     *
     * @return a location vector (in system coordinates)
     */
    public RVec3 getFixedPoint1() {
        long settingsVa = va();
        double x = getFixedPoint1X(settingsVa);
        double y = getFixedPoint1Y(settingsVa);
        double z = getFixedPoint1Z(settingsVa);
        RVec3 result = new RVec3(x, y, z);

        return result;
    }

    /**
     * Copy the location to which body 2 is connected. The settings are
     * unaffected. (native attribute: mFixedPoint2)
     *
     * @return a location vector (in system coordinates)
     */
    public RVec3 getFixedPoint2() {
        long settingsVa = va();
        double x = getFixedPoint2X(settingsVa);
        double y = getFixedPoint2Y(settingsVa);
        double z = getFixedPoint2Z(settingsVa);
        RVec3 result = new RVec3(x, y, z);

        return result;
    }

    /**
     * Return the maximum length of the line segments. The settings are
     * unaffected. (native attribute: mMaxLength)
     *
     * @return the length (in meters, or -1 to use the creation length)
     */
    public float getMaxLength() {
        long settingsVa = va();
        float result = getMaxLength(settingsVa);

        return result;
    }

    /**
     * Return the minimum length of the line segments. The settings are
     * unaffected. (native attribute: mMinLength)
     *
     * @return the length (in meters, or -1 to use the creation length)
     */
    public float getMinLength() {
        long settingsVa = va();
        float result = getMinLength(settingsVa);

        return result;
    }

    /**
     * Return the ratio between the 2 line segments. The settings are
     * unaffected. (native attribute: mRatio)
     *
     * @return the ratio
     */
    public float getRatio() {
        long settingsVa = va();
        float result = getRatio(settingsVa);

        return result;
    }

    /**
     * Return which space the body points are specified in. The settings are
     * unaffected. (native attribute: mSpace)
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
     * Alter the location at which the constraint will attach to body 1. (native
     * attribute: mBodyPoint1)
     *
     * @param location the desired location (not null, unaffected,
     * default=(0,0,0))
     */
    public void setBodyPoint1(RVec3Arg location) {
        long settingsVa = va();
        double locX = location.xx();
        double locY = location.yy();
        double locZ = location.zz();
        setBodyPoint1(settingsVa, locX, locY, locZ);
    }

    /**
     * Alter the location at which the constraint will attach to body 2. (native
     * attribute: mBodyPoint2)
     *
     * @param location the desired location (not null, unaffected,
     * default=(0,0,0))
     */
    public void setBodyPoint2(RVec3Arg location) {
        long settingsVa = va();
        double locX = location.xx();
        double locY = location.yy();
        double locZ = location.zz();
        setBodyPoint2(settingsVa, locX, locY, locZ);
    }

    /**
     * Alter the location to which body 1 is connected. (native attribute:
     * mFixedPoint1)
     *
     * @param location the desired location (in system coordinates, not null,
     * unaffected, default=(0,0,0))
     */
    public void setFixedPoint1(RVec3Arg location) {
        long settingsVa = va();
        double locX = location.xx();
        double locY = location.yy();
        double locZ = location.zz();
        setFixedPoint1(settingsVa, locX, locY, locZ);
    }

    /**
     * Alter the location to which body 2 is connected. (native attribute:
     * mFixedPoint2)
     *
     * @param location the desired location (in system coordinates, not null,
     * unaffected, default=(0,0,0))
     */
    public void setFixedPoint2(RVec3Arg location) {
        long settingsVa = va();
        double locX = location.xx();
        double locY = location.yy();
        double locZ = location.zz();
        setFixedPoint2(settingsVa, locX, locY, locZ);
    }

    /**
     * Alter the maximum length of the line segments. (native attribute:
     * mMaxLength)
     *
     * @param length (in meters, or -1 to use the creation length, default=-1)
     * @return the argument, for chaining
     */
    public float setMaxLength(float length) {
        long settingsVa = va();
        setMaxLength(settingsVa, length);

        return length;
    }

    /**
     * Alter the minimum length of the line segments. (native attribute:
     * mMinLength)
     *
     * @param length (in meters, or -1 to use the creation length, default=0)
     * @return the argument, for chaining
     */
    public float setMinLength(float length) {
        long settingsVa = va();
        setMinLength(settingsVa, length);

        return length;
    }

    /**
     * Alter the ratio between the 2 line segments. (native attribute: mRatio)
     *
     * @param ratio the desired ratio (default=1)
     */
    public void setRatio(float ratio) {
        long settingsVa = va();
        setRatio(settingsVa, ratio);
    }

    /**
     * Alter which space the body points are specified in. (native attribute:
     * mSpace)
     *
     * @param space the desired constraint space (not null, default=WorldSpace)
     */
    public void setSpace(EConstraintSpace space) {
        long settingsVa = va();
        int ordinal = space.ordinal();
        setSpace(settingsVa, ordinal);
    }
    // *************************************************************************
    // native private methods

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static double getBodyPoint1X(long settingsVa);

    native private static double getBodyPoint1Y(long settingsVa);

    native private static double getBodyPoint1Z(long settingsVa);

    native private static double getBodyPoint2X(long settingsVa);

    native private static double getBodyPoint2Y(long settingsVa);

    native private static double getBodyPoint2Z(long settingsVa);

    native private static double getFixedPoint1X(long settingsVa);

    native private static double getFixedPoint1Y(long settingsVa);

    native private static double getFixedPoint1Z(long settingsVa);

    native private static double getFixedPoint2X(long settingsVa);

    native private static double getFixedPoint2Y(long settingsVa);

    native private static double getFixedPoint2Z(long settingsVa);

    native private static float getMaxLength(long settingsVa);

    native private static float getMinLength(long settingsVa);

    native private static float getRatio(long settingsVa);

    native private static int getSpace(long settingsVa);

    native private static void setBodyPoint1(
            long settingsVa, double locX, double locY, double locZ);

    native private static void setBodyPoint2(
            long settingsVa, double locX, double locY, double locZ);

    native private static void setFixedPoint1(
            long settingsVa, double locX, double locY, double locZ);

    native private static void setFixedPoint2(
            long settingsVa, double locX, double locY, double locZ);

    native private static void setMaxLength(long settingsVa, float length);

    native private static void setMinLength(long settingsVa, float length);

    native private static void setRatio(long settingsVa, float ratio);

    native private static void setSpace(long settingsVa, int ordinal);
}
