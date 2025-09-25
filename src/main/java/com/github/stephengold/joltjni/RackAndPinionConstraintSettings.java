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

/**
 * Settings used to construct a {@code RackAndPinionConstraint}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class RackAndPinionConstraintSettings extends TwoBodyConstraintSettings {
    // *************************************************************************
    // constructors

    /**
     * Instantiate default settings.
     */
    public RackAndPinionConstraintSettings() {
        long settingsVa = createDefault();
        setVirtualAddressAsCoOwner(
                settingsVa, EConstraintSubType.RackAndPinion);
    }

    /**
     * Instantiate with the specified native object assigned.
     *
     * @param settingsVa the virtual address of the native object to assign (not
     * zero)
     */
    RackAndPinionConstraintSettings(long settingsVa) {
        setVirtualAddressAsCoOwner(
                settingsVa, EConstraintSubType.RackAndPinion);
    }

    /**
     * Instantiate a copy of the specified settings.
     *
     * @param original the settings to copy (not {@code null}, unaffected)
     */
    public RackAndPinionConstraintSettings(
            RackAndPinionConstraintSettings original) {
        long originalVa = original.va();
        long copyVa = createCopy(originalVa);
        setVirtualAddressAsCoOwner(copyVa, EConstraintSubType.RackAndPinion);
    }
    // *************************************************************************
    // new public methods

    /**
     * Copy the hinge axis of the pinion. The settings are unaffected. (native
     * attribute: mHingeAxis)
     *
     * @return a new direction vector
     */
    public Vec3 getHingeAxis() {
        long settingsVa = va();
        float dx = getHingeAxisX(settingsVa);
        float dy = getHingeAxisY(settingsVa);
        float dz = getHingeAxisZ(settingsVa);
        Vec3 result = new Vec3(dx, dy, dz);

        return result;
    }

    /**
     * Return the ratio of pinion rotation divided rack translation. The
     * settings are unaffected. (native attribute: mRatio)
     *
     * @return the ratio (in radians per meter)
     */
    public float getRatio() {
        long settingsVa = va();
        float result = getRatio(settingsVa);

        return result;
    }

    /**
     * Copy the translation axis of the slider. The settings are unaffected.
     * (native attribute: mSliderAxis)
     *
     * @return a new direction vector
     */
    public Vec3 getSliderAxis() {
        long settingsVa = va();
        float dx = getSliderAxisX(settingsVa);
        float dy = getSliderAxisY(settingsVa);
        float dz = getSliderAxisZ(settingsVa);
        Vec3 result = new Vec3(dx, dy, dz);

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
     * Alter the hinge axis of the pinion. (native attribute: mHingeAxis)
     *
     * @param direction the desired axis direction (not {@code null},
     * unaffected, default=(1,0,0))
     * @return the argument, for chaining
     */
    public Vec3Arg setHingeAxis(Vec3Arg direction) {
        long settingsVa = va();
        float dx = direction.getX();
        float dy = direction.getY();
        float dz = direction.getZ();
        setHingeAxis(settingsVa, dx, dy, dz);

        return direction;
    }

    /**
     * Alter the ratio of pinion rotation to rack translation. (native
     * attribute: mRatio)
     *
     * @param ratio the desired radio (in radians/meter)
     */
    public void setRatio(float ratio) {
        long settingsVa = va();
        setRatioDirectly(settingsVa, ratio);
    }

    /**
     * Update the ratio of pinion rotation to rack translation.
     *
     * @param rackTeeth the number of teeth in the rack
     * @param rackLength the length of the rack (in meters)
     * @param pinionTeeth the number of teeth in the pinion
     */
    public void setRatio(int rackTeeth, float rackLength, int pinionTeeth) {
        long settingsVa = va();
        setRatio(settingsVa, rackTeeth, rackLength, pinionTeeth);
    }

    /**
     * Alter the translation axis of the rack. (native attribute: mSliderAxis)
     *
     * @param direction the desired axis direction (not {@code null},
     * unaffected, default=(1,0,0))
     * @return the argument, for chaining
     */
    public Vec3Arg setSliderAxis(Vec3Arg direction) {
        long settingsVa = va();
        float dx = direction.getX();
        float dy = direction.getY();
        float dz = direction.getZ();
        setSliderAxis(settingsVa, dx, dy, dz);

        return direction;
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
    // *************************************************************************
    // native private methods

    native private static long createCopy(long originalVa);

    native private static long createDefault();

    native private static float getHingeAxisX(long settingsVa);

    native private static float getHingeAxisY(long settingsVa);

    native private static float getHingeAxisZ(long settingsVa);

    native private static float getRatio(long settingsVa);

    native private static float getSliderAxisX(long settingsVa);

    native private static float getSliderAxisY(long settingsVa);

    native private static float getSliderAxisZ(long settingsVa);

    native private static int getSpace(long settingsVa);

    native private static void setHingeAxis(
            long settingsVa, float dx, float dy, float dz);

    native private static void setRatio(
            long settingsVa, int rackTeeth, float rackLength, int pinionTeeth);

    native private static void setRatioDirectly(long settingsVa, float ratio);

    native private static void setSliderAxis(
            long settingsVa, float dx, float dy, float dz);

    native private static void setSpace(long settingsVa, int ordinal);
}
