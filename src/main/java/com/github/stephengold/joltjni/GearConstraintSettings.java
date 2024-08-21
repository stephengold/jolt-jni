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
import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * Settings used to construct a {@code GearConstraint}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class GearConstraintSettings extends TwoBodyConstraintSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate with the specified native object assigned but not owned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    GearConstraintSettings(long settingsVa) {
        super(settingsVa);
        setSubType(EConstraintSubType.Gear);
    }

    /**
     * Instantiate default settings.
     */
    public GearConstraintSettings() {
        long settingsVa = createGearConstraintSettings();
        setVirtualAddress(settingsVa, null); // not owner due to ref counting
        setSubType(EConstraintSubType.Gear);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Copy the hinge axis for body 1. The settings are unaffected. (native
     * field: mHingeAxis1)
     *
     * @return a new direction vector
     */
    public Vec3 getHingeAxis1() {
        long settingsVa = va();
        float x = getHingeAxis1X(settingsVa);
        float y = getHingeAxis1Y(settingsVa);
        float z = getHingeAxis1Z(settingsVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Copy the hinge axis for body 2. The settings are unaffected. (native
     * field: mHingeAxis2)
     *
     * @return a new direction vector
     */
    public Vec3 getHingeAxis2() {
        long settingsVa = va();
        float x = getHingeAxis2X(settingsVa);
        float y = getHingeAxis2Y(settingsVa);
        float z = getHingeAxis2Z(settingsVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Return the gear ratio.
     *
     * @return the ratio
     */
    public float getRatio() {
        long settingsVa = va();
        float result = getRatio(settingsVa);

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
     * Alter the hinge axis for body 1. (native field: mHingeAxis1)
     *
     * @param axis the desired direction vector (default=(1,0,0))
     */
    public void setHingeAxis1(Vec3Arg axis) {
        long settingsVa = va();
        float x = axis.getX();
        float y = axis.getY();
        float z = axis.getZ();
        setHingeAxis1(settingsVa, x, y, z);
    }

    /**
     * Alter the hinge axis for body 2. (native field: mHingeAxis2)
     *
     * @param axis the desired direction vector (default=(1,0,0))
     */
    public void setHingeAxis2(Vec3Arg axis) {
        long settingsVa = va();
        float x = axis.getX();
        float y = axis.getY();
        float z = axis.getZ();
        setHingeAxis2(settingsVa, x, y, z);
    }

    /**
     * Alter the gear ratio.
     *
     * @param ratio the desired ratio (default=1)
     */
    public void setRatio(float ratio) {
        long settingsVa = va();
        setRatio(settingsVa, ratio);
    }

    /**
     * Alter the gear ratio.
     *
     * @param numTeeth1 the number of teeth in the body 1 gear
     * @param numTeeth2 the number of teeth in the body 2 gear
     */
    public void setRatio(int numTeeth1, int numTeeth2) {
        long settingsVa = va();
        setRatio(settingsVa, numTeeth1, numTeeth2);
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

    native private static long createGearConstraintSettings();

    native private static float getHingeAxis1X(long settingsVa);

    native private static float getHingeAxis1Y(long settingsVa);

    native private static float getHingeAxis1Z(long settingsVa);

    native private static float getHingeAxis2X(long settingsVa);

    native private static float getHingeAxis2Y(long settingsVa);

    native private static float getHingeAxis2Z(long settingsVa);

    native private static float getRatio(long settingsVa);

    native private static int getSpace(long settingsVa);

    native private static void setHingeAxis1(
            long settingsVa, float x, float y, float z);

    native private static void setHingeAxis2(
            long settingsVa, float x, float y, float z);

    native private static void setRatio(long settingsVa, float ratio);

    native private static void setRatio(
            long settingsVa, int numTeeth1, int numTeeth2);

    native private static void setSpace(long settingsVa, int ordinal);
}
