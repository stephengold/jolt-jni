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
 * Settings used to construct a {@code DistanceConstraint}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class DistanceConstraintSettings extends TwoBodyConstraintSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate default settings.
     */
    public DistanceConstraintSettings() {
        long settingsVa = createDistanceConstraintSettings();
        setVirtualAddress(settingsVa); // not owner due to ref counting
        setSubType(EConstraintSubType.Distance);
    }

    /**
     * Instantiate with the specified native object assigned but not owned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    DistanceConstraintSettings(long settingsVa) {
        super(settingsVa);
        setSubType(EConstraintSubType.Distance);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Access the spring settings. The constraint settings are unaffected.
     * (native attribute: mLimitsSpringSettings)
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
     * Return the upper limit on distance. The settings are unaffected. (native
     * attribute: mMaxDistance)
     *
     * @return the upper limit
     */
    public float getMaxDistance() {
        long settingsVa = va();
        float result = getMaxDistance(settingsVa);

        return result;
    }

    /**
     * Return the lower limit on distance. The settings are unaffected. (native
     * attribute: mMinDistance)
     *
     * @return the lower limit
     */
    public float getMinDistance() {
        long settingsVa = va();
        float result = getMinDistance(settingsVa);

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
     * Alter the upper limit on distance. (native attribute: mMaxDistance)
     *
     * @param distance the upper limit (default=-1)
     */
    public void setMaxDistance(float distance) {
        long settingsVa = va();
        setMaxDistance(settingsVa, distance);
    }

    /**
     * Alter the lower limit on distance. (native attribute: mMinDistance)
     *
     * @param distance the upper limit (default=-1)
     */
    public void setMinDistance(float distance) {
        long settingsVa = va();
        setMinDistance(settingsVa, distance);
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

    native private static long createDistanceConstraintSettings();

    native private static long getLimitsSpringSettings(
            long constraintSettingsVa);

    native private static float getMaxDistance(long settingsVa);

    native private static float getMinDistance(long settingsVa);

    native private static double getPoint1X(long settingsVa);

    native private static double getPoint1Y(long settingsVa);

    native private static double getPoint1Z(long settingsVa);

    native private static double getPoint2X(long settingsVa);

    native private static double getPoint2Y(long settingsVa);

    native private static double getPoint2Z(long settingsVa);

    native private static int getSpace(long settingsVa);

    native private static void setMaxDistance(long settingsVa, float distance);

    native private static void setMinDistance(long settingsVa, float distance);

    native private static void setPoint1(
            long settingsVa, double x, double y, double z);

    native private static void setPoint2(
            long settingsVa, double x, double y, double z);

    native private static void setSpace(long settingsVa, int ordinal);
}
