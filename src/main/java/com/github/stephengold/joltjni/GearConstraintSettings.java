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
import com.github.stephengold.joltjni.readonly.Vec3Arg;
import java.nio.FloatBuffer;

/**
 * Settings used to construct a {@code GearConstraint}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class GearConstraintSettings extends TwoBodyConstraintSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate default settings.
     */
    public GearConstraintSettings() {
        long settingsVa = createDefault();
        setVirtualAddress(settingsVa); // not owner due to ref counting
        setSubType(EConstraintSubType.Gear);
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public GearConstraintSettings(GearConstraintSettings original) {
        long originalVa = original.va();
        long copyVa = createCopy(originalVa);
        setVirtualAddress(copyVa); // not owner due to ref counting
        setSubType(EConstraintSubType.Gear);
    }

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
    // *************************************************************************
    // new methods exposed

    /**
     * Copy the hinge axis for body 1. The settings are unaffected. (native
     * attribute: mHingeAxis1)
     *
     * @return a new direction vector
     */
    public Vec3 getHingeAxis1() {
        long settingsVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getHingeAxis1(settingsVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

        return result;
    }

    /**
     * Copy the hinge axis for body 2. The settings are unaffected. (native
     * attribute: mHingeAxis2)
     *
     * @return a new direction vector
     */
    public Vec3 getHingeAxis2() {
        long settingsVa = va();
        FloatBuffer storeFloats = Temporaries.floatBuffer1.get();
        getHingeAxis2(settingsVa, storeFloats);
        Vec3 result = new Vec3(storeFloats);

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
     * Alter the hinge axis for body 1. (native attribute: mHingeAxis1)
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
     * Alter the hinge axis for body 2. (native attribute: mHingeAxis2)
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

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static void getHingeAxis1(
            long settingsVa, FloatBuffer storeFloats);

    native private static void getHingeAxis2(
            long settingsVa, FloatBuffer storeFloats);

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
